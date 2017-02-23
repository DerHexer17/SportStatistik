package hight.ht.sportstatistik.activities;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.datahandling.Action;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Game;
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

        //final List<Stats> playerStats = dbh.getStatsForPlayerAndAction(p, a);
        final List<Stats> playerStats = addGamesWithZeroStat(p, a);
        //Toast t = Toast.makeText(getApplicationContext(), "Größe der Stats Liste: "+playerStats.size(), Toast.LENGTH_LONG);
        //t.show();
        if(playerStats.size()==0){
            graph.setVisibility(View.INVISIBLE);
            return;
        }else{
            graph.setVisibility(View.VISIBLE);
        }
        for(Stats stats : playerStats){
            //Log.d("STAT", "Der Stat Eintrag: Spiel = "+stats.getGame().getHeimteam().getLang_name()+" vs. "+stats.getGame().getGastteam()+", Summe = "+stats.getSum()+", Spieler = "+stats.getPlayer().getNachname());
        }


        int max = 0;


        DataPoint[] dataPointsArray = new DataPoint[playerStats.size()];
        int i = 0;
        int k = 1;
        for(Stats s : playerStats){

            dataPointsArray[i] = new DataPoint(k, s.getSum());
            if(s.getSum() > max){
                max = s.getSum();
            }
            Log.d("STAT", "So sieht der DataPoint aus: DataPoint("+k+", "+s.getSum()+")");
            i++;
            k++;
        }

        if(playerStats.size() > 1) {
            LineGraphSeries<DataPoint> lineSeries = new LineGraphSeries<DataPoint>(dataPointsArray);
            PointsGraphSeries<DataPoint> pointSeries = new PointsGraphSeries<DataPoint>(dataPointsArray);

            pointSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Game g = playerStats.get((int) (dataPoint.getX()-1)).getGame();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    Toast.makeText(getApplicationContext(), g.getHeimteam().getLang_name()+" - "+g.getGastteam()+
                           "\n"+"("+sdf.format(g.getDatum().getTime())+")", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
                }
            });

            graph.addSeries(pointSeries);
            graph.addSeries(lineSeries);
        }else{
            PointsGraphSeries<DataPoint> pointSeries = new PointsGraphSeries<DataPoint>(dataPointsArray);
            graph.addSeries(pointSeries);
        }

        /*
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space


        Dies ist wahrscheinlich nur vom Date Tipp, muss mein unten eingestelltes nicht überschreiben

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true); */

        //max = max+2;
        max++;
        Log.d("STAT", "y-Max = "+max);
        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        int xhigh = playerStats.size();
        graph.getViewport().setMaxX(xhigh);
        graph.getGridLabelRenderer().setNumHorizontalLabels(xhigh);

        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);

        if((max%2)==0){
           graph.getGridLabelRenderer().setNumVerticalLabels(max);
        }else{
           graph.getGridLabelRenderer().setNumVerticalLabels(max+1);
        }

        graph.getViewport().setMaxY(max);
    }

    public List<Stats> addGamesWithZeroStat(Player p, Action a){
        List<Game> allPlayedGames = dbh.getAllPlayedGamesFromPlayer(p);
        List <Stats> tempPlayerStats = dbh.getStatsForPlayerAndAction(p, a);
        Log.d("Graph", "Alle gespielten Spiele Listgröße: "+allPlayedGames.size());
        try {
            if (allPlayedGames.size() != tempPlayerStats.size()) {
                int index = 0;
                for (Game g : allPlayedGames) {
                    if (g.getId() != tempPlayerStats.get(index).getGame().getId()) {
                        Stats tempStat = new Stats();
                        tempStat.setGame(dbh.getSpiel(g.getId()));
                        tempStat.setSum(0);
                        tempPlayerStats.add(index-1, tempStat);
                    }
                    index++;
                }
            }
        }catch(Exception e){

        }

        return tempPlayerStats;
    }

}
