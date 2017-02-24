package hight.ht.sportstatistik.activities;

import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Stats;
import hight.ht.sportstatistik.datahandling.Team;
import hight.ht.sportstatistik.helper.StatsTeamAdaper;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.helper.TeamStatsFilterSpinner;
import hight.ht.sportstatistik.helper.TeamsArrayAdapter;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamStatsActivity extends ActionBarActivity {

    DatabaseHelper dbh;
    private StatsTeamAdaper adapter;
    List<Integer> statGroupsInteger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Auswertung");

        final ExpandableListView elv = (ExpandableListView) findViewById(R.id.expendableStatsTeam);
        dbh = DatabaseHelper.getInstance(getApplicationContext());
        statGroupsInteger = dbh.getAllActionsFromTeam(null);

        Spinner teamSpinner = (Spinner) findViewById(R.id.spinnerTeamStatsFilter);
        TeamStatsFilterSpinner teamsSpinnerAdapter = new TeamStatsFilterSpinner(this, dbh.getAllTeams());
        teamSpinner.setAdapter(teamsSpinnerAdapter);
        /*
        teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Team team = (Team) parent.getAdapter().getItem(position);
                StatsTeamAdaper teamFilterAdapter = new StatsTeamAdaper(getApplicationContext(), dbh.getAllActionsFromTeam(team), statItems());
                elv.setAdapter(teamFilterAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                StatsTeamAdaper teamFilterAdapter = new StatsTeamAdaper(getApplicationContext(), dbh.getAllActionsFromTeam(null), statItems());
                elv.setAdapter(teamFilterAdapter);
            }
        });
            */

        //testGroups = statTestGroup();

        adapter = new StatsTeamAdaper(getApplicationContext(), statGroupsInteger, statItems());
        Log.d("Stats", "Größe Adapter " + adapter.getGroupCount());
        elv.setAdapter(adapter);
    }

    public List<Integer> statTestGroup(){
        List<Integer> groupStats = new ArrayList<Integer>();

        groupStats.add(1);
        groupStats.add(2);
        groupStats.add(3);
        groupStats.add(4);
        return groupStats;
    }

    public HashMap<Integer, List<Stats>> statItems(){
        HashMap<Integer, List<Stats>> itemStats = new HashMap<Integer, List<Stats>>();
        for(Integer i : statGroupsInteger){
            itemStats.put(i, dbh.getStatsFromPlayerForAction(i, null));
        }


        return itemStats;
    }

    public void toggleTeamFilter(View v){
        RelativeLayout teamFilter = (RelativeLayout) findViewById(R.id.layoutTeamFilters);
        if(teamFilter.getVisibility() == View.GONE) {
            teamFilter.setVisibility(View.VISIBLE);
        }else{
            teamFilter.setVisibility(View.GONE);
        }
    }



}
