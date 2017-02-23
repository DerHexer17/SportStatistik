package hight.ht.sportstatistik.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Action;
import hight.ht.sportstatistik.datahandling.ActionMapping;
import hight.ht.sportstatistik.datahandling.Game;
import hight.ht.sportstatistik.datahandling.Player;
import hight.ht.sportstatistik.datahandling.PlayerToActionMapping;
import hight.ht.sportstatistik.helper.ActionDelecteAdapter;
import hight.ht.sportstatistik.helper.ActionInGameAdapter;
import hight.ht.sportstatistik.helper.PlayerInGameAdapter;
import hight.ht.sportstatistik.helper.PlayerInGameUpdateAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import hight.ht.sportstatistik.R;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class GameActivity extends ActionBarActivity implements PlayerInGameAdapter.SpielerInSpielAdapterCallback, ActionInGameAdapter.EreignisAdapterCallback, ActionDelecteAdapter.ActionDeleteAdapterCallback, PlayerInGameUpdateAdapter.SpielerInSpielAdapterCallback{

    Game game;
    DatabaseHelper dbh;
    PlayerInGameAdapter spieler;
    List<PlayerToActionMapping> tempSpielerListe;
    ActionInGameAdapter newActionAdapter;
    ActionDelecteAdapter deleteActionAdapter;
    AlertDialog alertAddAction;
    AlertDialog alertDeleteAction;
    PlayerInGameUpdateAdapter updateSpieler;

    private Player chosenPlayer;
    private Action selectedAction;
    private int gewaehltePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        dbh = DatabaseHelper.getInstance(getApplicationContext());
        Log.d("game", "Game ID: " + getIntent().getIntExtra("spielId", 0));
        game = dbh.getSpiel(getIntent().getIntExtra("spielId", 0));

        //Überschrift festlegen
        setTitle(game.getHeimteam().getKurz_name() + " - " + game.getGastteam());
        TextView titel = (TextView) findViewById(R.id.spielTitel);
        titel.setText(game.getHeimteam().getLang_name()+" - "+ game.getGastteam());

        /*tempSpielerListe = new ArrayList<PlayerToActionMapping>();
        for(Player s : dbh.getAllPlayersFromTeam(game.getHeimteam().getId())){
            PlayerToActionMapping sez = new PlayerToActionMapping();
            sez.setPlayer(s);
            tempSpielerListe.add(sez);
        }*/

        //Holen aller Player des Teams und erstellen der Liste
        spieler = new PlayerInGameAdapter(getApplicationContext(), R.id.label, dbh.getAlleSpielEreignisse(dbh.getAllPlayersFromGame(game.getId()), game));
        spieler.setNotifyOnChange(true);
        spieler.setCallback(this);
        spieler.setFinished(game.isBeendet());
        ListView lv = (ListView) findViewById(R.id.spielListSpieler);
        lv.setAdapter(spieler);

        //Vorbereiten der Pop-Ups für neue Ereignisse
        newActionAdapter = new ActionInGameAdapter(getApplicationContext(), R.id.gridButton, dbh.getAllActiveActions());
        newActionAdapter.setCallback(this);
        deleteActionAdapter = new ActionDelecteAdapter(getApplicationContext(), R.id.gridButton, dbh.getAllActiveActions());
        deleteActionAdapter.setCallback((ActionDelecteAdapter.ActionDeleteAdapterCallback) this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);

        alertAddAction = builder.create();
        alertDeleteAction = builder.create();
        setupAlert();

        updateSpieler = new PlayerInGameUpdateAdapter(getApplicationContext(), R.id.name, dbh.getAllPlayersFromTeam(game.getHeimteam().getId()), game);
        updateSpieler.setCallback(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(!game.isBeendet()){
            getMenuInflater().inflate(R.menu.menu_game, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.playerToGame:
                updatePlayerInGame();
                break;
            case R.id.gameIsFinished:
                setGameInactive();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void chooseAction(int position, boolean add){
        gewaehltePosition = position;
        chosenPlayer = spieler.getItem(position).getPlayer();
        if(add){
            alertAddAction.show();
        }else{
            alertDeleteAction.show();
        }

    }
    /*
    The Callback from the Action Interface. Gives us the action, we want to add to a player
     */
    @Override
    public void addAction(int position){
        selectedAction = newActionAdapter.getItem(position);
        alertAddAction.dismiss();
        if(spieler.getItem(gewaehltePosition).getEreignisse().containsKey(selectedAction.getName())){
            int value = spieler.getItem(gewaehltePosition).getEreignisse().get(selectedAction.getName());
            spieler.getItem(gewaehltePosition).getEreignisse().remove(selectedAction.getName());
            spieler.getItem(gewaehltePosition).getEreignisse().put(selectedAction.getName(), value+1);
        }else{
            spieler.getItem(gewaehltePosition).getEreignisse().put(selectedAction.getName(), 1);
        }
        spieler.notifyDataSetChanged();
        long l = dbh.addStatistik(new ActionMapping(game, chosenPlayer, selectedAction));
        Log.d("ereignis", "Player: " + chosenPlayer.getVorname() + " , Action: " + selectedAction.getName() + ", Position: " + gewaehltePosition);
    }
    /*
    The Callback from the ActionDelete Interface. Gives us the action, we want to reduce, meaning delete
     */
    @Override
    public void deleteAction(int position) {
        selectedAction = deleteActionAdapter.getItem(position);
        alertDeleteAction.dismiss();
        if(spieler.getItem(gewaehltePosition).getEreignisse().containsKey(selectedAction.getName())){
            int value = spieler.getItem(gewaehltePosition).getEreignisse().get(selectedAction.getName());
            if(value == 1){
                spieler.getItem(gewaehltePosition).getEreignisse().remove(selectedAction.getName());
            }else {
                spieler.getItem(gewaehltePosition).getEreignisse().remove(selectedAction.getName());
                spieler.getItem(gewaehltePosition).getEreignisse().put(selectedAction.getName(), value - 1);
            }
            dbh.deleteActionFromGame(new ActionMapping(game, chosenPlayer, selectedAction));
        }
        spieler.notifyDataSetChanged();
        Log.d("DeleteAction", "Folgendes wird entfernt: " + selectedAction.getName() + " von " + chosenPlayer.getVorname());

    }

    /*
    The Callback from the PlayerAdapter Interface. Gives us the player we chose. The Pop Up does not need this, but we need to know which player.
     */
    @Override
    public void neuesEreignisPopUp(int position) {
        chooseAction(position, true);

    }

    @Override
    public void deleteActionPopUp(int position){
        chooseAction(position, false);
    }






    public void setupAlert(){
        alertAddAction.setTitle(getResources().getString(R.string.addActionTitle));
        alertDeleteAction.setTitle(getResources().getString(R.string.deleteActionTitle));
        //alert.setMessage("Leg ein neues Game an");

        //final LinearLayout ll = new LinearLayout(this);

        final GridView gridNewAction = new GridView(this);
        gridNewAction.setNumColumns(3);
        gridNewAction.setHorizontalSpacing(5);
        gridNewAction.setVerticalSpacing(5);
        gridNewAction.setPadding(5, 5, 5, 5);
        gridNewAction.setBackgroundColor(Color.WHITE);
        //gridNewAction.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
        gridNewAction.setGravity(Gravity.CENTER);
        gridNewAction.setAdapter(newActionAdapter);
        gridNewAction.setMinimumHeight(800);

        //ll.addView(gridNewAction);
        alertAddAction.setView(gridNewAction, 2, 2, 2, 2);

        final GridView gridDeleteAction = new GridView(this);
        gridDeleteAction.setNumColumns(3);
        gridDeleteAction.setHorizontalSpacing(5);
        gridDeleteAction.setVerticalSpacing(5);
        gridDeleteAction.setPadding(5,5,5,5);
        gridDeleteAction.setBackgroundColor(Color.WHITE);
        gridDeleteAction.setGravity(Gravity.CENTER);
        gridDeleteAction.setAdapter(deleteActionAdapter);
        alertDeleteAction.setView(gridDeleteAction);
    }

    public void updatePlayerInGame(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Context context = this;
        alert.setTitle(getResources().getString(R.string.updatePlayerInGameTitle));
        alert.setMessage(getResources().getString(R.string.updatePlayerInGameMessage));

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.update_player_in_game, null);
        final ListView list = (ListView) alertLayout.findViewById(R.id.listPlayerInGame);

        list.setAdapter(updateSpieler);

        alert.setView(alertLayout);


        alert.setPositiveButton(getResources().getString(R.string.closeButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int c = 0;
                while (c < updateSpieler.getCount()) {
                    Log.d("Number", "Nummer: " + String.valueOf(updateSpieler.getItem(c).getNummmer()));

                    if(updateSpieler.getItem(c).getNummmer() != dbh.getPlayer(updateSpieler.getItem(c).getId()).getNummmer()){
                        dbh.updatePlayerInGame(updateSpieler.getItem(c), game, updateSpieler.getItem(c).getNummmer());
                    }
                    c++;
                }

                spieler.clear();
                spieler.addAll(dbh.getAlleSpielEreignisse(dbh.getAllPlayersFromGame(game.getId()), game));
                spieler.notifyDataSetChanged();

            }
        });

        /*alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });*/


        alert.show();

        //this.recreate();

    }

    public void dataSet(){
        spieler.notifyDataSetChanged();
    }

    @Override
    public void changeSwitchStatus(int position, boolean status, int number) {
        if(status == false){
            dbh.deletePlayerFromGame(updateSpieler.getItem(position), game);
            updateSpieler.notifyDataSetChanged();
        }else{
            dbh.addSpielerToSpiel(updateSpieler.getItem(position), game, updateSpieler.getItem(position).getNummmer());
            updateSpieler.notifyDataSetChanged();
        }
    }

    public void setGameInactive(){
        game.setBeendet(true);
        dbh.updateGame(game);

        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }
}
