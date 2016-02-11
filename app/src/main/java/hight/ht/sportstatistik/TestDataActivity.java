package hight.ht.sportstatistik;

import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Player;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class TestDataActivity extends ActionBarActivity {

    DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data);
        dbh = DatabaseHelper.getInstance(getApplicationContext());
        String alleSpieler = "";
        for(Player s : dbh.getAllPlayers()){
            alleSpieler = alleSpieler+s.getVorname()+" "+s.getNachname()+" ("+
            s.getNummmer()+")"+"\n";
        }
        TextView alle = (TextView) findViewById(R.id.textAlleSpieler);
        alle.setText(alleSpieler);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_data, menu);
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

    public void neuerSpieler(View v){
        Player player = new Player();
        EditText et = (EditText) findViewById(R.id.vorname);
        player.setVorname(String.valueOf(et.getText()));
        et = (EditText) findViewById(R.id.nachname);
        player.setNachname(String.valueOf(et.getText()));
        et = (EditText) findViewById(R.id.nummer);
        player.setNummmer(Integer.parseInt(String.valueOf(et.getText())));

        dbh.addSpieler(player);
        this.recreate();

    }
}
