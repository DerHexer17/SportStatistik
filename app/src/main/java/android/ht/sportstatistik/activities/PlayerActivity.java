package android.ht.sportstatistik.activities;

import android.content.Intent;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Player;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.ht.sportstatistik.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PlayerActivity extends ActionBarActivity {

    Player player;
    DatabaseHelper dbh;
    EditText firstname;
    EditText lastname;
    EditText number;
    private static int RESULT_LOAD_IMG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbh = DatabaseHelper.getInstance(getApplicationContext());
        player = dbh.getPlayer(getIntent().getIntExtra("player", 0));

        setTitle(player.getVorname()+" "+player.getNachname());

        this.firstname = (EditText) findViewById(R.id.playerEditFirstname);
        this.lastname = (EditText) findViewById(R.id.playerEditLastname);
        this.number = (EditText) findViewById(R.id.playerEditNumber);
        firstname.setText(player.getVorname());
        lastname.setText(player.getNachname());
        number.setText(String.valueOf(player.getNummmer()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.saveEditedPlayer:
                updatePlayer();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updatePlayer(){
        Player editedPlayer = this.player;
        editedPlayer.setNachname(String.valueOf(lastname.getText()));
        editedPlayer.setVorname(String.valueOf(firstname.getText()));
        if(isNumber(String.valueOf(number.getText()))) {
            editedPlayer.setNummmer(Integer.parseInt(String.valueOf(number.getText())));
            dbh.updatePlayer(editedPlayer);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "NUMMER!", Toast.LENGTH_LONG);
        }

    }

    public boolean isNumber(String s){
        int i;
        boolean b;
        try{
            i = Integer.parseInt(s);
            b = true;
        }catch(NumberFormatException e){
            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
            b = false;
        }
        return b;
    }

    public void selectPicture(View view){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

}
