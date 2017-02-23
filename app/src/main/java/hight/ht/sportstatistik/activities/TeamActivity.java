package hight.ht.sportstatistik.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Player;
import hight.ht.sportstatistik.datahandling.Team;
import hight.ht.sportstatistik.helper.KitDrawableFetcher;
import hight.ht.sportstatistik.helper.PlayerForTeamAdapter;
import hight.ht.sportstatistik.helper.PlayerInTeamAdapter;
import hight.ht.sportstatistik.helper.TeamColorSpinnerAdapter;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamActivity extends ActionBarActivity implements PlayerInTeamAdapter.SpielerInTeamAdapterCallback {

    Team team;
    DatabaseHelper dbh;
    List<Player> chosenPlayer;
    PlayerInTeamAdapter spielerAdapter;
    PlayerForTeamAdapter spielerAuswahlAdapter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(hight.ht.sportstatistik.R.layout.activity_team);
        dbh = DatabaseHelper.getInstance(this);
        team = dbh.getTeam(getIntent().getIntExtra("teamId", 0));

        chosenPlayer = new ArrayList<Player>();
        setTitle(team.getKurz_name());
        TextView titel = (TextView) findViewById(hight.ht.sportstatistik.R.id.teamTitel);
        titel.setText(team.getLang_name());

        TextView txtKit = (TextView) findViewById(hight.ht.sportstatistik.R.id.teamKit);
        try{
            txtKit.setBackground(getResources().getDrawable(new KitDrawableFetcher(team).fetchDrawableId(team.getColor())));
        }catch(Exception e){
            Log.d("Kit", "Entweder wurde keine Farbe festgelegt oder das Trikot gibt es noch nicht!");
        }

        ListView lv = (ListView) findViewById(hight.ht.sportstatistik.R.id.teamSpieler);
        spielerAdapter = new PlayerInTeamAdapter(this, hight.ht.sportstatistik.R.id.label ,dbh.getAllPlayersFromTeam(team.getId()));
        spielerAdapter.setNotifyOnChange(true);
        spielerAdapter.setCallback(this);
        spielerAuswahlAdapter = new PlayerForTeamAdapter(this, hight.ht.sportstatistik.R.id.cbSpieler, dbh.getAllPlayersNotFromTeam(team.getId()));
        spielerAuswahlAdapter.setNotifyOnChange(true);
        lv.setAdapter(spielerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(hight.ht.sportstatistik.R.menu.menu_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == hight.ht.sportstatistik.R.id.menuTeamEdit) {
            updateTeam(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addSpieler(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getResources().getString(hight.ht.sportstatistik.R.string.addPlayerTitle));
        alert.setMessage(getResources().getString(hight.ht.sportstatistik.R.string.addPlayerMessage));

        // Set an EditText view to get user input
        final ListView lvSpielerHinzu = new ListView(this);
        lvSpielerHinzu.setMinimumHeight(50);
        lvSpielerHinzu.setAdapter(spielerAuswahlAdapter);


        alert.setView(lvSpielerHinzu);

        alert.setPositiveButton(getResources().getString(hight.ht.sportstatistik.R.string.saveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                List<Player> selectedPlayers = new ArrayList<Player>();
                int c = 0;
                Log.d("addSpieler", "Größe selectedSpieler: "+spielerAuswahlAdapter.getSelectedPlayer().size());
                Toast toast = Toast.makeText(getApplicationContext(), "Adapter: "+spielerAuswahlAdapter.getClass(), Toast.LENGTH_SHORT);
                for(Player s : spielerAuswahlAdapter.getSelectedPlayer()){
                    long l = dbh.addSpielerToTeam(s, team);
                    Log.d("addSpieler", "dbh return: "+l);
                    spielerAdapter.add(s);
                    spielerAuswahlAdapter.remove(s);

                }

            }
        });

        alert.setNegativeButton(getResources().getString(hight.ht.sportstatistik.R.string.cancelButton), new DialogInterface.OnClickListener() {
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

    public void updateTeam(final Activity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Context context = this;
        alert.setTitle(getResources().getString(hight.ht.sportstatistik.R.string.newTeamTitle));
        alert.setMessage(getResources().getString(hight.ht.sportstatistik.R.string.newTeamMessage));

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(hight.ht.sportstatistik.R.layout.add_team, null);
        final EditText teamLong = (EditText) alertLayout.findViewById(hight.ht.sportstatistik.R.id.addTeamLong);
        final EditText teamShort = (EditText) alertLayout.findViewById(hight.ht.sportstatistik.R.id.addTeamShort);
        final EditText teamDescription = (EditText) alertLayout.findViewById(hight.ht.sportstatistik.R.id.addTeamDescription);
        final Spinner teamColor = (Spinner) alertLayout.findViewById(hight.ht.sportstatistik.R.id.spinnerTeamColor);
        final Spinner teamGoalieColor = (Spinner) alertLayout.findViewById(hight.ht.sportstatistik.R.id.spinnerTeamGoalieColor);

        teamLong.setText(team.getLang_name());
        teamShort.setText(team.getKurz_name());
        teamDescription.setText(team.getBeschreibung());


        ArrayList<String> colorlist = new ArrayList<String>();
        KitDrawableFetcher fetcher = new KitDrawableFetcher(null);
        for(Map.Entry entry : fetcher.getKitColors().entrySet()){
            colorlist.add((String) entry.getKey());
        }
        TeamColorSpinnerAdapter colorAdapter = new TeamColorSpinnerAdapter(context, colorlist, fetcher.getKitColors());

        teamColor.setAdapter(colorAdapter);
        teamColor.setSelection(colorAdapter.getIdForColor(team.getColor()));
        teamGoalieColor.setAdapter(colorAdapter);
        teamGoalieColor.setSelection(colorAdapter.getIdForColor(team.getGoalieColor()));


        alert.setView(alertLayout);

        alert.setPositiveButton(getResources().getString(hight.ht.sportstatistik.R.string.saveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Team t = new Team();
                t.setKurz_name(String.valueOf(teamShort.getText()).trim());
                t.setLang_name(String.valueOf(teamLong.getText()).trim());
                try{
                    t.setBeschreibung(String.valueOf(teamDescription.getText()));
                }catch (Exception e){
                    t.setBeschreibung("");
                }
                t.setColor((String) teamColor.getSelectedItem());
                t.setGoalieColor((String) teamGoalieColor.getSelectedItem());
                t.setId(team.getId());
                dbh.updateTeam(t);

                Intent refresh = new Intent(activity, TeamActivity.class);
                refresh.putExtra("teamId", team.getId());
                startActivity(refresh);
                activity.finish();

                // Do something with value!
            }
        });

        alert.setNegativeButton(getResources().getString(hight.ht.sportstatistik.R.string.cancelButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        alert.show();

        //this.recreate();

    }
}
