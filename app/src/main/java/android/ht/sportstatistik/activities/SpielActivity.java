package android.ht.sportstatistik.activities;

import android.app.AlertDialog;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Ereignis;
import android.ht.sportstatistik.datahandling.EreignisZuordnung;
import android.ht.sportstatistik.datahandling.Spiel;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.SpielerEreignisZuordnung;
import android.ht.sportstatistik.helper.ActionDelecteAdapter;
import android.ht.sportstatistik.helper.EreignisAdapter;
import android.ht.sportstatistik.helper.SpielerInSpielAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.ht.sportstatistik.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpielActivity extends ActionBarActivity implements SpielerInSpielAdapter.SpielerInSpielAdapterCallback, EreignisAdapter.EreignisAdapterCallback, ActionDelecteAdapter.ActionDeleteAdapterCallback{

    Spiel spiel;
    DatabaseHelper dbh;
    SpielerInSpielAdapter spieler;
    List<SpielerEreignisZuordnung> tempSpielerListe;
    EreignisAdapter newActionAdapter;
    ActionDelecteAdapter deleteActionAdapter;
    AlertDialog alertAddAction;
    AlertDialog alertDeleteAction;

    private Spieler gewaehlterSpieler;
    private Ereignis gewaehltesEreignis;
    private int gewaehltePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiel);
        dbh = DatabaseHelper.getInstance(getApplicationContext());
        Log.d("spiel", "Spiel ID: "+getIntent().getIntExtra("spielId", 0));
        spiel = dbh.getSpiel(getIntent().getIntExtra("spielId", 0));

        setTitle(spiel.getHeimteam().getKurz_name() + "-" + spiel.getGastteam());
        TextView titel = (TextView) findViewById(R.id.spielTitel);
        titel.setText(spiel.getHeimteam().getLang_name()+" - "+spiel.getGastteam());

        tempSpielerListe = new ArrayList<SpielerEreignisZuordnung>();
        for(Spieler s : dbh.getAllPlayersFromTeam(spiel.getHeimteam().getId())){
            SpielerEreignisZuordnung sez = new SpielerEreignisZuordnung();
            sez.setSpieler(s);
            tempSpielerListe.add(sez);
        }

        spieler = new SpielerInSpielAdapter(getApplicationContext(), R.id.label, dbh.getAlleSpielEreignisse(dbh.getAllPlayersFromTeam(spiel.getHeimteam().getId()), spiel));
        spieler.setNotifyOnChange(true);
        spieler.setCallback(this);
        ListView lv = (ListView) findViewById(R.id.spielListSpieler);
        lv.setAdapter(spieler);

        newActionAdapter = new EreignisAdapter(getApplicationContext(), R.id.gridButton, dbh.getAlleEreignisse());
        Ereignis e1 = new Ereignis();
        e1.setName("Test");
        newActionAdapter.add(e1);
        Ereignis e2 = new Ereignis();
        e1.setName("Test");
        newActionAdapter.add(e2);
        Ereignis e3 = new Ereignis();
        e1.setName("Test");
        newActionAdapter.add(e3);
        


        newActionAdapter.setCallback(this);
        deleteActionAdapter = new ActionDelecteAdapter(getApplicationContext(), R.id.gridButton, dbh.getAlleEreignisse());
        deleteActionAdapter.setCallback((ActionDelecteAdapter.ActionDeleteAdapterCallback) this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertAddAction = builder.create();
        alertDeleteAction = builder.create();
        setupAlert();

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
        if (id == R.id.action_settings) {
            return true;
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

        final LinearLayout ll = new LinearLayout(this);

        final GridView gridNewAction = new GridView(this);
        gridNewAction.setNumColumns(3);
        //gridNewAction.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
        gridNewAction.setGravity(Gravity.CENTER);
        gridNewAction.setAdapter(newActionAdapter);
        ll.addView(gridNewAction);
        alertAddAction.setView(ll,2,2,2,2);

        final GridView gridDeleteAction = new GridView(this);
        gridDeleteAction.setNumColumns(3);
        gridDeleteAction.setAdapter(deleteActionAdapter);
        alertDeleteAction.setView(gridDeleteAction);
    }
}
