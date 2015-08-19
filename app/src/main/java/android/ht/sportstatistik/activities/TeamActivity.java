package android.ht.sportstatistik.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.Team;
import android.ht.sportstatistik.helper.SpielerAdapter;
import android.ht.sportstatistik.helper.SpielerInTeamAdapter;
import android.ht.sportstatistik.helper.SpielerZuTeamAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.ht.sportstatistik.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends ActionBarActivity implements SpielerInTeamAdapter.SpielerInTeamAdapterCallback{

    Team team;
    DatabaseHelper dbh;
    List<Spieler> ausgewaehlteSpieler;
    SpielerInTeamAdapter spielerAdapter;
    SpielerZuTeamAdapter spielerAuswahlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        dbh = DatabaseHelper.getInstance(this);
        team = dbh.getTeam(getIntent().getIntExtra("teamId", 0));
        ausgewaehlteSpieler = new ArrayList<Spieler>();
        setTitle(team.getKurz_name());
        TextView titel = (TextView) findViewById(R.id.teamTitel);
        titel.setText(team.getLang_name());
        ListView lv = (ListView) findViewById(R.id.teamSpieler);
        spielerAdapter = new SpielerInTeamAdapter(this, R.id.label ,dbh.getAllPlayersFromTeam(team.getId()));
        spielerAdapter.setNotifyOnChange(true);
        spielerAdapter.setCallback(this);
        spielerAuswahlAdapter = new SpielerZuTeamAdapter(this, R.id.cbSpieler, dbh.getAllPlayersNotFromTeam(team.getId()));
        spielerAuswahlAdapter.setNotifyOnChange(true);
        lv.setAdapter(spielerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team, menu);
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

    public void addSpieler(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Spieler hinzufügen");
        alert.setMessage("Welche Spieler gehören zu diesem Team?");

        // Set an EditText view to get user input
        final ListView lvSpielerHinzu = new ListView(this);
        lvSpielerHinzu.setMinimumHeight(50);
        lvSpielerHinzu.setAdapter(spielerAuswahlAdapter);


        alert.setView(lvSpielerHinzu);

        alert.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                List<Spieler> selectedPlayers = new ArrayList<Spieler>();
                int c = 0;
                Log.d("addSpieler", "Größe selectedSpieler: "+spielerAuswahlAdapter.getSelectedSpieler().size());
                Toast toast = Toast.makeText(getApplicationContext(), "Adapter: "+spielerAuswahlAdapter.getClass(), Toast.LENGTH_SHORT);
                for(Spieler s : spielerAuswahlAdapter.getSelectedSpieler()){
                    long l = dbh.addSpielerToTeam(s, team);
                    Log.d("addSpieler", "dbh return: "+l);
                    spielerAdapter.add(s);
                    spielerAuswahlAdapter.remove(s);

                }

            }
        });

        alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        alert.show();
    }

    @Override
    public void spielerRemove(int position) {
        spielerAuswahlAdapter.add(spielerAdapter.getItem(position));
        int i = dbh.removePlayerFromTeam(spielerAdapter.getItem(position), team);
        spielerAdapter.remove(spielerAdapter.getItem(position));


    }
}
