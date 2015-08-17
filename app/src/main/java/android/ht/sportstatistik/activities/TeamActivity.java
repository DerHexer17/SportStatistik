package android.ht.sportstatistik.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.Team;
import android.ht.sportstatistik.helper.SpielerAdapter;
import android.ht.sportstatistik.helper.TeamSpielerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.ht.sportstatistik.R;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends ActionBarActivity {

    Team team;
    DatabaseHelper dbh;
    List<Spieler> ausgewaehlteSpieler;
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
        lv.setAdapter(new SpielerAdapter(this, R.id.spielerListTeam ,dbh.getAllPlayersFromTeam(team.getId())));
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
        final ListView lv = new ListView(this);
        lv.setMinimumHeight(50);
        lv.setAdapter(new TeamSpielerAdapter(this, R.id.spielerListTeam, dbh.getAllPlayersNotFromTeam(team.getId())));


        alert.setView(lv);

        AlertDialog.Builder speichern = alert.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                TeamSpielerAdapter tsa = (TeamSpielerAdapter) lv.getAdapter();
                for(Spieler s : tsa.getSelectedSpieler()){
                    dbh.addSpielerToTeam(s, team);
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

}
