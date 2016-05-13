package hight.ht.sportstatistik.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Player;
import hight.ht.sportstatistik.datahandling.Stats;

public class PlayerStatsActivity extends ActionBarActivity {

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbh = DatabaseHelper.getInstance(getApplicationContext());
        Player p = dbh.getPlayer(1);
        TextView txtTitle = (TextView) findViewById(R.id.playerStatsTitleLabel);
        txtTitle.setText(p.getVorname()+ " " + p.getNachname());

        List<Stats> playerStats = dbh.getStatsFromPlayerForGames(p.getId());
        Toast t = Toast.makeText(getApplicationContext(), "Größe der Stats Liste: "+playerStats.size(), Toast.LENGTH_LONG);
        t.show();

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
        int i = 0;
        for(Stats s : playerStats){

            new DataPoint(i, s.getSum());
            i++;
        }
        DataPoint[] testPointsArray = new DataPoint[testPoints.size()];
        int k = 0;
        for(DataPoint dp : testPoints){
            testPointsArray[k] = dp;
            k++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(testPointsArray);
        graph.addSeries(series);
    }

}
