package hight.ht.sportstatistik.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Game;
import hight.ht.sportstatistik.datahandling.Player;
import hight.ht.sportstatistik.datahandling.Team;
import hight.ht.sportstatistik.helper.GameAdapter;
import hight.ht.sportstatistik.helper.KitDrawableFetcher;
import hight.ht.sportstatistik.helper.PlayerAdapter;
import hight.ht.sportstatistik.helper.TeamColorSpinnerAdapter;
import hight.ht.sportstatistik.helper.TeamsArrayAdapter;
import hight.ht.sportstatistik.helper.Testdaten;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import hight.ht.sportstatistik.R;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, PlayerFragment.OnFragmentInteractionListener, GameFragment.OnFragmentInteractionListener, ActionFragment.OnFragmentInteractionListener, TeamFragment.OnFragmentInteractionListener, StatsFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener, PlayerAdapter.SpielerAdapterCallback, GameAdapter.SpielAdapterCallback {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    DatabaseHelper dbh;
    GameAdapter spieler;

    public String[] navMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        //setTitle("Sport Statistik App");
        mTitle = "Handball Statistik";
        dbh = DatabaseHelper.getInstance(this);
        spieler = new GameAdapter(getApplicationContext(), R.id.label, (List<Game>) dbh.getAllGames());


        if(dbh.getAlleEreignisse().size() == 0){
            new Testdaten(getApplicationContext()).ereignisseEinspielen();
        }

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = null;


        switch(position){
            case 0:
                fragment = new GameFragment();
                break;
            case 1:
                fragment = new TeamFragment();
                break;
            case 2:
                fragment = new PlayerFragment();

                /*Bundle b = new Bundle();
                b.putParcelable(spieler);
                fragment.setArguments();*/
                break;
            case 3:
                fragment = new ActionFragment();
                break;
            case 4:
                fragment = new StatsFragment();
                break;
            case 5:
                fragment = new AboutFragment();
                break;
            default:
                fragment = PlaceholderFragment.newInstance(position+1);
                break;
        }

        if(fragment != null){
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();


        }

        try{
            mTitle = navMenuTitles[position];
        }catch(Exception e) {
            e.printStackTrace();
            mTitle = "Handball Statistik";
            Log.d("mTitle", e.getMessage());
        }


        }

    public void onSectionAttached(int number) {

        try{
            mTitle = navMenuTitles[number-1];
        }catch(Exception e){
            e.printStackTrace();
            Log.d("mTitle", e.getMessage());
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){


            case R.id.testdatenEinspielen:
                new Testdaten(getApplicationContext()).testdatenEinspielen();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int position) {
        setTitle(navMenuTitles[position]);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public void deleteTeam(final int teamId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Game löschen");
        //AlertDialog d = builder.create();
        TextView delete = new TextView(getApplicationContext());
        delete.setText(getResources().getString(R.string.deleteTeam));
        delete.setTextColor(Color.BLACK);
        delete.setGravity(Gravity.CENTER);
        delete.setPadding(10,10,10,10);
        builder.setView(delete);
        builder.setPositiveButton(getResources().getString(R.string.deletePositiveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                dbh.deleteTeam(teamId);
                ListView lv = (ListView) findViewById(R.id.listViewTeams);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton(getResources().getString(R.string.deleteNegativeButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        builder.show();
    }

    @Override
    public void deletePlayer(final int playerId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Game löschen");
        //AlertDialog d = builder.create();
        TextView delete = new TextView(getApplicationContext());
        delete.setText(getResources().getString(R.string.deletePlayer));
        delete.setTextColor(Color.BLACK);
        delete.setGravity(Gravity.CENTER);
        delete.setPadding(10, 10, 10, 10);
        builder.setView(delete);
        builder.setPositiveButton(getResources().getString(R.string.deletePositiveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                dbh.deletePlayer(playerId);
                ListView lv = (ListView) findViewById(R.id.listViewSpieler);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton(getResources().getString(R.string.deleteNegativeButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        builder.show();
    }

    @Override
    public void showSpinner() {

    }

    @Override
    public void deleteGame(final int spielId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Game löschen");
        //AlertDialog d = builder.create();
        TextView delete = new TextView(getApplicationContext());
        delete.setText(getResources().getString(R.string.deleteGame));
        delete.setTextColor(Color.BLACK);
        delete.setGravity(Gravity.CENTER);
        delete.setPadding(10, 10, 10, 10);
        builder.setView(delete);
        builder.setPositiveButton(getResources().getString(R.string.deletePositiveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.d("Delete", "Game ID: " + spielId);
                dbh.deleteGame(spielId);
                ListView lv = (ListView) findViewById(R.id.listViewSpiele);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton(getResources().getString(R.string.deleteNegativeButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        builder.show();
    }

    @Override
    public void setGameActive(final int spielId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Game löschen");
        //AlertDialog d = builder.create();
        TextView delete = new TextView(getApplicationContext());
        delete.setText(getResources().getString(R.string.setGameActive));
        delete.setTextColor(Color.BLACK);
        delete.setGravity(Gravity.CENTER);
        delete.setPadding(10, 10, 10, 10);
        builder.setView(delete);
        builder.setPositiveButton(getResources().getString(R.string.deletePositiveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.d("Delete", "Game ID: " + spielId);
                ListView lv = (ListView) findViewById(R.id.listViewSpiele);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
                Game game = (Game) adapter.getItem(position);
                game.setBeendet(false);
                dbh.updateGame(game);
                ((Game) adapter.getItem(position)).setBeendet(false);
                adapter.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton(getResources().getString(R.string.deleteNegativeButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        builder.show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void newGame(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getResources().getString(R.string.newGameTitle));
        alert.setMessage(getResources().getString(R.string.newGameMessage));

        final Spinner heimteam = new Spinner(this);
        /*List<String> heimteams = new ArrayList<String>();
        for(Team t : dbh.getAllTeams()){
            heimteams.add(t.getLang_name());
        }*/

        TeamsArrayAdapter teamsAdapter = new TeamsArrayAdapter(this, android.R.layout.simple_spinner_item, dbh.getAllTeams());
       // ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, heimteams);
        teamsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heimteam.setAdapter(teamsAdapter);

        // Set an EditText view to get user input
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        final EditText gegner = new EditText(this);
        gegner.setHint("Gegner");
        final EditText datum = new EditText(this);
        final DatePicker date = new DatePicker(this);

        ll.addView(heimteam);
        ll.addView(gegner);
        ll.addView(date);
        alert.setView(ll);

        alert.setPositiveButton(getResources().getString(R.string.saveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Game s = new Game();
                s.setBeendet(false);
                s.setGastteam(String.valueOf(gegner.getText()).trim());
                Team t = (Team) heimteam.getSelectedItem();
                s.setHeimteam(t);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
                calendar.set(Calendar.MONTH, date.getMonth());
                calendar.set(Calendar.YEAR, date.getYear());
                s.setDatum(calendar);

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Log.d("DatePicker", "Gewähltes Datum: "+sdf.format(s.getDatum().getTime()));
                s.setId((int) dbh.addSpiel(s));
                ListView lv = (ListView) findViewById(R.id.listViewSpiele);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();

                //Einmal initial werden einfach alle Player des Teams dem Game zugeordnet
                for(Player player : dbh.getAllPlayersFromTeam(s.getHeimteam().getId())){
                    dbh.addSpielerToSpiel(player, s, player.getNummmer());
                }

                adapter.add(s);
                adapter.notifyDataSetChanged();
                // Do something with value!
            }
        });

        alert.setNegativeButton(getResources().getString(R.string.cancelButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
        ListView lv = (ListView) findViewById(R.id.listViewSpiele);


    }

    public void newTeam(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Context context = this;
        alert.setTitle(getResources().getString(R.string.newTeamTitle));
        alert.setMessage(getResources().getString(R.string.newTeamMessage));

        /*
        // Set an EditText view to get user input
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        final EditText titel_kurz = new EditText(this);
        titel_kurz.setHint(getResources().getString(R.string.newTeamHintShort));
        final EditText titel_lang = new EditText(this);
        titel_lang.setHint(getResources().getString(R.string.newTeamHintLong));
        final EditText beschreibung = new EditText(this);
        beschreibung.setHint(getResources().getString(R.string.newTeamHintDescription));
        //final Spinner datum = new Spinner(this);
        //datum.setAdapter(new DatePicker(this));
        ll.addView(titel_kurz);
        ll.addView(titel_lang);
        ll.addView(beschreibung);
        alert.setView(ll);*/

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_team, null);
        final EditText teamLong = (EditText) alertLayout.findViewById(R.id.addTeamLong);
        final EditText teamShort = (EditText) alertLayout.findViewById(R.id.addTeamShort);
        final EditText teamDescription = (EditText) alertLayout.findViewById(R.id.addTeamDescription);
        final Spinner teamColor = (Spinner) alertLayout.findViewById(R.id.spinnerTeamColor);
        final Spinner teamGoalieColor = (Spinner) alertLayout.findViewById(R.id.spinnerTeamGoalieColor);

        ArrayList<String> colorlist = new ArrayList<String>();
        KitDrawableFetcher fetcher = new KitDrawableFetcher(null);
        for(Map.Entry entry : fetcher.getKitColors().entrySet()){
            colorlist.add((String) entry.getKey());
        }
        TeamColorSpinnerAdapter colorAdapter = new TeamColorSpinnerAdapter(context, colorlist, fetcher.getKitColors());
        teamColor.setAdapter(colorAdapter);
        teamGoalieColor.setAdapter(colorAdapter);


        alert.setView(alertLayout);

        alert.setPositiveButton(getResources().getString(R.string.saveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Team t = new Team();
                t.setKurz_name(String.valueOf(teamShort.getText()).trim());
                t.setLang_name(String.valueOf(teamLong.getText()).trim());
                try{
                    t.setBeschreibung(String.valueOf(teamDescription.getText()));
                }catch (Exception e){
                    t.setBeschreibung("");
                }
                t.setColor((String) teamColor.getSelectedItem());
                t.setGoalieColor((String) teamGoalieColor.getSelectedItem());

                t.setId((int) dbh.addTeam(t));
                ListView lv = (ListView) findViewById(R.id.listViewTeams);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
                adapter.add(t);
                Log.d("Adapter", "Objekt t geadded. Kurztitel: " + t.getKurz_name());
                adapter.notifyDataSetChanged();
                // Do something with value!
            }
        });

        alert.setNegativeButton(getResources().getString(R.string.cancelButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        alert.show();

        //this.recreate();

    }

    public void newPlayer(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Context context = this;
        alert.setTitle(getResources().getString(R.string.newPlayerTitle));
        alert.setMessage(getResources().getString(R.string.newPlayerMessage));

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_player, null);
        final EditText vorname = (EditText) alertLayout.findViewById(R.id.addPlayerEditFirstname);
        final EditText nachname = (EditText) alertLayout.findViewById(R.id.addPlayerEditLastname);
        final EditText nummer = (EditText) alertLayout.findViewById(R.id.addPlayerEditNumber);
        final CheckBox goalie = (CheckBox) alertLayout.findViewById(R.id.addPlayerCheckboxGoalie);
        alert.setView(alertLayout);

        alert.setPositiveButton(getResources().getString(R.string.saveButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Player sp = new Player();

                try{
                    sp.setVorname(String.valueOf(vorname.getText()).trim());
                    sp.setNachname(String.valueOf(nachname.getText()).trim());
                    sp.setNummmer(Integer.parseInt(String.valueOf(nummer.getText())));
                    if(goalie.isChecked()){
                        sp.setTorwart(true);
                    }else{
                        sp.setTorwart(false);
                    }
                }catch (Exception e){
                    Log.d("Neuer Player", e.getMessage());
                }

                dbh.addSpieler(sp);
                ListView lv = (ListView) findViewById(R.id.listViewSpieler);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
                adapter.add(sp);
                adapter.notifyDataSetChanged();
                // Do something with value!
            }
        });

        alert.setNegativeButton(getResources().getString(R.string.cancelButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        alert.show();

        //this.recreate();

    }

    public void cancel(View view){
        Log.d("Cancel", "Button gedrückt"+view.getParent().toString());
    }

    public void teamStats(View view){
        Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
        startActivity(intent);
    }

    public void csvExport(View view) {

        String csv = "id;Ereignis;Vorname;Nachname;Nummer;Verein;Gegner;Datum";
        Cursor c = dbh.csvExport();
        if(c.moveToFirst()){
            do{
                String row = "";
                for(int i = 0; i < c.getColumnCount(); i++){
                    row = row + ";" + String.valueOf(c.getString(i));
                }
                row = row.substring(1);
                Log.d("csv", row);
                csv = csv + "\n" + row;
            }while(c.moveToNext());


        }

        BufferedWriter bufferedWriter = null;
        File exportDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()+"/Export");
        if (!exportDir.exists()){
            exportDir.mkdirs();
        }


        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(exportDir.getPath() + File.separator + "csvExport.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter.write(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast t = Toast.makeText(getApplicationContext(), "CSV Export wurde erfolgreich gespeichert", Toast.LENGTH_LONG);
        t.show();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "CSV Export von SportStatistik");
        intent.putExtra(Intent.EXTRA_TEXT, "Anhängend der CSV Export der SportStatistik App");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new
                File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Export/" + "csvExport.txt")));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
