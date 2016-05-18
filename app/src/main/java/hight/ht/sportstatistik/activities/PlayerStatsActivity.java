package hight.ht.sportstatistik.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
    Spinner actionPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTitle = (TextView) findViewById(R.id.playerStatsTitleLabel);
        actionSpinner = (Spinner) findViewById(R.id.playerStatsSpinnerAction);
        actionPlayer = (Spinner) findViewById(R.id.playerStatsSpinnerPlayer);
        dbh = DatabaseHelper.getInstance(getApplicationContext());

        ActionSpinnerAdapter actionSpinnerAdapter = new ActionSpinnerAdapter(getApplicationContext(), dbh.getAllActiveActions());
        PlayerSpinnerAdapter playerSpinnerAdapter = new PlayerSpinnerAdapter(getApplicationContext(), dbh.getAllPlayers());

        actionSpinner.setAdapter(actionSpinnerAdapter);
        actionPlayer.setAdapter(playerSpinnerAdapter);

        Player tempp = dbh.getPlayer(1);
        Action tempa = dbh.getAllActiveActions().get(0);

        setGraph(tempp, tempa);


    }

    public void setGraph(Player p, Action a){
        txtTitle.setText(a.getName()+"-Statistik für "+p.getVorname()+ " " + p.getNachname());

        List<Stats> playerStats = dbh.getStatsForPlayerAndAction(p, a);
        Toast t = Toast.makeText(getApplicationContext(), "Größe der Stats Liste: "+playerStats.size(), Toast.LENGTH_LONG);
        t.show();

        for(Stats stats : playerStats){
            Log.d("STAT", "Der Stat Eintrag: Spiel = "+stats.getGame().getDatum()+", Summe = "+stats.getSum()+", Spieler = "+stats.getPlayer().getNachname());
        }


        GraphView graph = (GraphView) findViewById(R.id.graph);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 6);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.YEAR, 2016);
        DataPoint point1 = new DataPoint(new Date(calendar.getTimeInMillis()), 4);
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.YEAR, 2016);
        DataPoint point2 = new DataPoint(new Date(calendar.getTimeInMillis()), 4);
        List<DataPoint> testPoints = new ArrayList<DataPoint>();



        DataPoint[] dataPointsArray = new DataPoint[playerStats.size()];
        int i = 0;
        for(Stats s : playerStats){
            dataPointsArray[i] = new DataPoint(i, s.getSum());
            i++;
        }


        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPointsArray);

        graph.addSeries(series);

        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(playerStats.size());

        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        //graph.getViewport().setMaxY(8);
    }

}
