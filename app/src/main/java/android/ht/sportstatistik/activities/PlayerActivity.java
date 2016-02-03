package android.ht.sportstatistik.activities;

import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Player;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.ht.sportstatistik.R;

public class PlayerActivity extends ActionBarActivity {

    Player player;
    DatabaseHelper dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbh = DatabaseHelper.getInstance(getApplicationContext());
        player = dbh.getPlayer(getIntent().getIntExtra("player", 0));

        setTitle(player.getVorname()+" "+player.getNachname());
    }

}
