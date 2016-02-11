package android.ht.sportstatistik.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Player;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.ht.sportstatistik.R;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class PlayerActivity extends ActionBarActivity {

    Player player;
    DatabaseHelper dbh;
    EditText firstname;
    EditText lastname;
    EditText number;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    ImageView playerPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbh = DatabaseHelper.getInstance(getApplicationContext());
        player = dbh.getPlayer(getIntent().getIntExtra("player", 0));
        playerPicture = (ImageView) findViewById(R.id.playerEditPicture);

        setTitle(player.getVorname()+" "+player.getNachname());

        this.firstname = (EditText) findViewById(R.id.playerEditFirstname);
        this.lastname = (EditText) findViewById(R.id.playerEditLastname);
        this.number = (EditText) findViewById(R.id.playerEditNumber);
        firstname.setText(player.getVorname());
        lastname.setText(player.getNachname());
        number.setText(String.valueOf(player.getNummmer()));

        try{
            Bitmap bmp = BitmapFactory.decodeFile(player.getPicture());
            storeImage(bmp);
            playerPicture.setImageBitmap(bmp);
        }catch(Exception e){
            Log.d("BMP", e.getMessage());
            playerPicture.setImageDrawable(getDrawable(R.drawable.player_no_picture));
        }


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();


                Bitmap bmp = BitmapFactory.decodeFile(imgDecodableString);
                storeImage(bmp);
                playerPicture.setImageBitmap(bmp);

                //We need to store the image and link it to the player

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name

        File mediaFile;
        String mImageName="Player_"+player.getId()+"_Picture";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("BMP",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            Log.d("BMP", "Pfad: " + pictureFile.getAbsolutePath());
            dbh.updatePlayerPicture(pictureFile.getAbsolutePath(), player);
        } catch (FileNotFoundException e) {
            Log.d("BMP", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("BMP", "Error accessing file: " + e.getMessage());
        }
    }

}
