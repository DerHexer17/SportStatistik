package android.ht.sportstatistik.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Ereignis;
import android.ht.sportstatistik.datahandling.Spiel;
import android.ht.sportstatistik.datahandling.Team;
import android.ht.sportstatistik.helper.EreignisAdapter;
import android.ht.sportstatistik.helper.SpielerInSpielAdapter;
import android.ht.sportstatistik.helper.TeamsArrayAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.ht.sportstatistik.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpielActivity extends ActionBarActivity implements SpielerInSpielAdapter.SpielerInSpielAdapterCallback, EreignisAdapter.EreignisAdapterCallback{

    Spiel spiel;
    DatabaseHelper dbh;
    SpielerInSpielAdapter spieler;
    EreignisAdapter ereignisse;
    AlertDialog alert;

    private int gewaehlterSpieler;
    private int gewaehltesEreignis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiel);
        dbh = DatabaseHelper.getInstance(getApplicationContext());
        spiel = dbh.getSpiel(getIntent().getIntExtra("spielId", 0));

        setTitle(spiel.getHeimteam().getKurz_name()+"-"+spiel.getGastteam());
        TextView titel = (TextView) findViewById(R.id.spielTitel);
        titel.setText(spiel.getHeimteam().getLang_name()+" - "+spiel.getGastteam());

        spieler = new SpielerInSpielAdapter(getApplicationContext(), R.id.label, dbh.getAllPlayersFromTeam(spiel.getHeimteam().getId()));
        spieler.setNotifyOnChange(true);
        spieler.setCallback(this);
        ListView lv = (ListView) findViewById(R.id.spielListSpieler);
        lv.setAdapter(spieler);

        List<Ereignis> ereignisListe = new ArrayList<Ereignis>();
        for(int i = 0; i<10; i++){
            Ereignis e = new Ereignis();
            e.setName("Ereignis"+i);
            ereignisListe.add(e);
        }
        ereignisse = new EreignisAdapter(getApplicationContext(), R.id.gridButton, dbh.getAlleEreignisse());
        ereignisse.setCallback(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alert = builder.create();
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

    public void neuesEreignisWaehlen(int position){

        gewaehlterSpieler = spieler.getItem(position).getId();
        alert.show();

    }

    public void neuesEreignisSpeichern(int position){
        gewaehltesEreignis = ereignisse.getItem(position).getId();
        alert.dismiss();
        Log.d("ereignis", "Folgendes Ereignis gespeichert: Spieler ID " + gewaehlterSpieler + ", Ereignis ID " + gewaehltesEreignis);
    }

    @Override
    public void neuesEreignisPopUp(int position) {
        neuesEreignisWaehlen(position);

    }

    @Override
    public void neuesEreignisHinzufuegen(int position){
        neuesEreignisSpeichern(position);
    }

    public void setupAlert(){
        alert.setTitle("Ereignis hinzufügen");
        //alert.setMessage("Leg ein neues Spiel an");

        final GridView grid = new GridView(this);
        grid.setNumColumns(3);


        grid.setAdapter(ereignisse);

        alert.setView(grid);
    }
}
