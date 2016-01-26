package android.ht.sportstatistik.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Ereignis;
import android.ht.sportstatistik.datahandling.EreignisZuordnung;
import android.ht.sportstatistik.datahandling.Spiel;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.SpielerEreignisZuordnung;
import android.ht.sportstatistik.helper.ActionDelecteAdapter;
import android.ht.sportstatistik.helper.ActionInGameAdapter;
import android.ht.sportstatistik.helper.SpielerInSpielAdapter;
import android.ht.sportstatistik.helper.SpielerInSpielUpdateAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.ht.sportstatistik.R;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpielActivity extends ActionBarActivity implements SpielerInSpielAdapter.SpielerInSpielAdapterCallback, ActionInGameAdapter.EreignisAdapterCallback, ActionDelecteAdapter.ActionDeleteAdapterCallback, SpielerInSpielUpdateAdapter.SpielerInSpielAdapterCallback{

    Spiel spiel;
    DatabaseHelper dbh;
    SpielerInSpielAdapter spieler;
    List<SpielerEreignisZuordnung> tempSpielerListe;
    ActionInGameAdapter newActionAdapter;
    ActionDelecteAdapter deleteActionAdapter;
    AlertDialog alertAddAction;
    AlertDialog alertDeleteAction;
    SpielerInSpielUpdateAdapter updateSpieler;

    private Spieler gewaehlterSpieler;
    private Ereignis gewaehltesEreignis;
    private int gewaehltePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiel);
        dbh = DatabaseHelper.getInstance(getApplicationContext());
        Log.d("spiel", "Spiel ID: " + getIntent().getIntExtra("spielId", 0));
        spiel = dbh.getSpiel(getIntent().getIntExtra("spielId", 0));

        //Überschrift festlegen
        setTitle(spiel.getHeimteam().getKurz_name() + " - " + spiel.getGastteam());
        TextView titel = (TextView) findViewById(R.id.spielTitel);
        titel.setText(spiel.getHeimteam().getLang_name()+" - "+spiel.getGastteam());

        tempSpielerListe = new ArrayList<SpielerEreignisZuordnung>();
        for(Spieler s : dbh.getAllPlayersFromTeam(spiel.getHeimteam().getId())){
            SpielerEreignisZuordnung sez = new SpielerEreignisZuordnung();
            sez.setSpieler(s);
            tempSpielerListe.add(sez);
        }

        //Holen aller Spieler des Teams und erstellen der Liste
        spieler = new SpielerInSpielAdapter(getApplicationContext(), R.id.label, dbh.getAlleSpielEreignisse(dbh.getAllPlayersFromGame(spiel.getId()), spiel));
        spieler.setNotifyOnChange(true);
        spieler.setCallback(this);
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

        updateSpieler = new SpielerInSpielUpdateAdapter(getApplicationContext(), R.id.name, dbh.getAllPlayersFromTeam(spiel.getHeimteam().getId()), spiel);
        updateSpieler.setCallback(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spiel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.playerToGame) {
            updatePlayerInGame();
        }

        return super.onOptionsItemSelected(item);
    }

    public void chooseAction(int position, boolean add){
        gewaehltePosition = position;
        gewaehlterSpieler = spieler.getItem(position).getSpieler();
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
        gewaehltesEreignis = newActionAdapter.getItem(position);
        alertAddAction.dismiss();
        if(spieler.getItem(gewaehltePosition).getEreignisse().containsKey(gewaehltesEreignis.getName())){
            int value = spieler.getItem(gewaehltePosition).getEreignisse().get(gewaehltesEreignis.getName());
            spieler.getItem(gewaehltePosition).getEreignisse().remove(gewaehltesEreignis.getName());
            spieler.getItem(gewaehltePosition).getEreignisse().put(gewaehltesEreignis.getName(), value+1);
        }else{
            spieler.getItem(gewaehltePosition).getEreignisse().put(gewaehltesEreignis.getName(), 1);
        }
        spieler.notifyDataSetChanged();
        long l = dbh.addStatistik(new EreignisZuordnung(spiel, gewaehlterSpieler, gewaehltesEreignis));
        Log.d("ereignis", "Spieler: " + gewaehlterSpieler.getVorname() + " , Ereignis: " + gewaehltesEreignis.getName() + ", Position: " + gewaehltePosition);
    }
    /*
    The Callback from the ActionDelete Interface. Gives us the action, we want to reduce, meaning delete
     */
    @Override
    public void deleteAction(int position) {
        gewaehltesEreignis = deleteActionAdapter.getItem(position);
        alertDeleteAction.dismiss();
        if(spieler.getItem(gewaehltePosition).getEreignisse().containsKey(gewaehltesEreignis.getName())){
            int value = spieler.getItem(gewaehltePosition).getEreignisse().get(gewaehltesEreignis.getName());
            if(value == 1){
                spieler.getItem(gewaehltePosition).getEreignisse().remove(gewaehltesEreignis.getName());
            }else {
                spieler.getItem(gewaehltePosition).getEreignisse().remove(gewaehltesEreignis.getName());
                spieler.getItem(gewaehltePosition).getEreignisse().put(gewaehltesEreignis.getName(), value - 1);
            }
            dbh.deleteActionFromGame(new EreignisZuordnung(spiel, gewaehlterSpieler, gewaehltesEreignis));
        }
        spieler.notifyDataSetChanged();
        Log.d("DeleteAction", "Folgendes wird entfernt: " + gewaehltesEreignis.getName() + " von " + gewaehlterSpieler.getVorname());

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
        alertAddAction.setTitle("Ereignis hinzufügen");
        alertDeleteAction.setTitle("Ereignis entfernen");
        //alert.setMessage("Leg ein neues Spiel an");

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
        gridDeleteAction.setAdapter(deleteActionAdapter);
        alertDeleteAction.setView(gridDeleteAction);
    }

    public void updatePlayerInGame(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Context context = this;
        alert.setTitle("Spieler bearbeiten");
        alert.setMessage("Bearbeite die teilnehmenden Spieler");

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.update_player_in_game, null);
        final ListView list = (ListView) alertLayout.findViewById(R.id.listPlayerInGame);

        list.setAdapter(updateSpieler);
        alert.setView(alertLayout);

        alert.setPositiveButton("Schließen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int c = 0;
                while (c < updateSpieler.getCount()) {
                    Log.d("Number", "Nummer: " + String.valueOf(updateSpieler.getItem(c).getNummmer()));

                    c++;
                }

                spieler.clear();
                spieler.addAll(dbh.getAlleSpielEreignisse(dbh.getAllPlayersFromGame(spiel.getId()), spiel));
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
            dbh.deletePlayerFromGame(updateSpieler.getItem(position), spiel);
            updateSpieler.notifyDataSetChanged();
        }else{
            dbh.addSpielerToSpiel(updateSpieler.getItem(position), spiel, updateSpieler.getItem(position).getNummmer());
            updateSpieler.notifyDataSetChanged();
        }
    }
}
