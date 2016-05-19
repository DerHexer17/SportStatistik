package hight.ht.sportstatistik.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.datahandling.Action;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Player;
import hight.ht.sportstatistik.datahandling.Stats;
import hight.ht.sportstatistik.helper.ActionSpinnerAdapter;
import hight.ht.sportstatistik.helper.PlayerSpinnerAdapter;

public class PlayerStatsActivity extends ActionBarActivity {

    DatabaseHelper dbh;
    TextView txtTitle;
    Spinner actionSpinner;
    Spinner playerSpinner;
    Player player;
    Action action;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.playerStatsTitle);
        txtTitle = (TextView) findViewById(R.id.playerStatsTitleLabel);
        actionSpinner = (Spinner) findViewById(R.id.playerStatsSpinnerAction);
        playerSpinner = (Spinner) findViewById(R.id.playerStatsSpinnerPlayer);
        graph = (GraphView) findViewById(R.id.graph);
        dbh = DatabaseHelper.getInstance(getApplicationContext());

        ActionSpinnerAdapter actionSpinnerAdapter = new ActionSpinnerAdapter(getApplicationContext(), dbh.getAllActiveActions());
        PlayerSpinnerAdapter playerSpinnerAdapter = new PlayerSpinnerAdapter(getApplicationContext(), dbh.getAllPlayers());

        actionSpinner.setAdapter(actionSpinnerAdapter);
        actionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                action = (Action) actionSpinner.getSelectedItem();
                setGraph(player, action);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        playerSpinner.setAdapter(playerSpinnerAdapter);
        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                player = (Player) playerSpinner.getSelectedItem();
                setGraph(player, action);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        player = dbh.getPlayer(1);
        action = dbh.getAllActiveActions().get(0);

        setGraph(player, action);


    }

    public void setGraph(Player p, Action a){
        txtTitle.setText(a.getName()+"-Statistik für "+p.getVorname()+ " " + p.getNachname());
        graph.removeAllSeries();

        List<Stats> playerStats = dbh.getStatsForPlayerAndAction(p, a);
        //Toast t = Toast.makeText(getApplicationContext(), "Größe der Stats Liste: "+playerStats.size(), Toast.LENGTH_LONG);
        //t.show();

        for(Stats stats : playerStats){
            Log.d("STAT", "Der Stat Eintrag: Spiel = "+stats.getGame().getHeimteam().getLang_name()+" vs. "+stats.getGame().getGastteam()+", Summe = "+stats.getSum()+", Spieler = "+stats.getPlayer().getNachname());
        }


        int max = 0;


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 6);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.YEAR, 2016);
        DataPoint point1 = new DataPoint(new Date(calendar.getTimeInMillis()), 4);
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.YEAR, 2016);
        DataPoint point2 = new DataPoint(new Date(calendar.getTimeInMillis()), 4);

        DataPoint[] dataPointsArray = new DataPoint[playerStats.size()];
        int i = 0;
        for(Stats s : playerStats){
            dataPointsArray[i] = new DataPoint(i, s.getSum());
            if(s.getSum() > max){
                max = s.getSum();
            }
            Log.d("STAT", "So sieht der DataPoint aus: DataPoint("+i+", "+s.getSum()+")");
            i++;
        }

        if(playerStats.size() > 1) {
            LineGraphSeries<DataPoint> lineSeries = new LineGraphSeries<DataPoint>(dataPointsArray);
            graph.addSeries(lineSeries);
        }else{
            PointsGraphSeries<DataPoint> pointSeries = new PointsGraphSeries<DataPoint>(dataPointsArray);
            graph.addSeries(pointSeries);
        }

        //max = max+2;
        Log.d("STAT", "X-Max = "+max);
        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        int xhigh = playerStats.size();
        graph.getViewport().setMaxX(xhigh);

        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(max);
    }

}
