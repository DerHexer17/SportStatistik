package android.ht.sportstatistik;

import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Spieler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class TestDataActivity extends ActionBarActivity {

    DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data);
        dbh = DatabaseHelper.getInstance(getApplicationContext());
        String alleSpieler = "";
        for(Spieler s : dbh.getAllPlayers()){
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
        Spieler spieler = new Spieler();
        EditText et = (EditText) findViewById(R.id.vorname);
        spieler.setVorname(String.valueOf(et.getText()));
        et = (EditText) findViewById(R.id.nachname);
        spieler.setNachname(String.valueOf(et.getText()));
        et = (EditText) findViewById(R.id.nummer);
        spieler.setNummmer(Integer.parseInt(String.valueOf(et.getText())));

        dbh.addSpieler(spieler);
        this.recreate();

    }
}
