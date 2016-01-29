package android.ht.sportstatistik.activities;

import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Stats;
import android.ht.sportstatistik.helper.StatsTeamAdaper;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.ht.sportstatistik.R;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsActivity extends ActionBarActivity {

    DatabaseHelper dbh;
    private StatsTeamAdaper adapter;
    List<Integer> statGroupsInteger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.expendableStatsTeam);
        dbh = DatabaseHelper.getInstance(getApplicationContext());
        statGroupsInteger = dbh.getAllActionsFromTeam(null);

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

}
