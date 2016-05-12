package hight.ht.sportstatistik.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

import hight.ht.sportstatistik.R;

public class PlayerStatsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {

                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

}
