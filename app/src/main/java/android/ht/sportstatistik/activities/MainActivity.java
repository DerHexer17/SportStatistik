package android.ht.sportstatistik.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.ht.sportstatistik.TestDataActivity;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Spiel;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.Team;
import android.ht.sportstatistik.helper.SpielerAdapter;
import android.ht.sportstatistik.helper.TeamsArrayAdapter;
import android.ht.sportstatistik.helper.Testdaten;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.ht.sportstatistik.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.ht.sportstatistik.activities.TeamFragment.*;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SpielerFragment.OnFragmentInteractionListener, SpielFragment.OnFragmentInteractionListener, ActionFragment.OnFragmentInteractionListener, OnFragmentInteractionListener, StatsFragment.OnFragmentInteractionListener, SpielerAdapter.SpielerAdapterCallback {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    DatabaseHelper dbh;

    private String[] navMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        //setTitle("Sport Statistik App");
        mTitle = "Sport Statistik";


        dbh = DatabaseHelper.getInstance(this);
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
                fragment = new SpielFragment();
                break;
            case 1:
                fragment = new TeamFragment();
                break;
            case 2:
                fragment = new SpielerFragment();
                break;
            case 3:
                fragment = new ActionFragment();
                break;
            case 4:
                fragment = new StatsFragment();
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

    }

    public void onSectionAttached(int number) {

        //mTitle = navMenuTitles[number-1];

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
    public void dialogPlayer(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        AlertDialog d = builder.create();
        TextView delete = new TextView(getApplicationContext());
        delete.setText("Spieler löschen");
        d.setView(delete, 5,5,5,5);
        d.show();
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

    public void neuesSpiel(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Spiel anlegen");
        alert.setMessage("Leg ein neues Spiel an");

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

        alert.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Spiel s = new Spiel();
                s.setBeendet("Nein");
                s.setGastteam(String.valueOf(gegner.getText()));
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

                //Einmal initial werden einfach alle Spieler des Teams dem Spiel zugeordnet
                for(Spieler spieler : dbh.getAllPlayersFromTeam(s.getHeimteam().getId())){
                    dbh.addSpielerToSpiel(spieler, s, spieler.getNummmer());
                }

                adapter.add(s);
                adapter.notifyDataSetChanged();
                // Do something with value!
            }
        });

        alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
        ListView lv = (ListView) findViewById(R.id.listViewSpiele);


    }

    public void neuesTeam(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Team anlegen");
        alert.setMessage("Leg ein neues Team an");

        // Set an EditText view to get user input
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        final EditText titel_kurz = new EditText(this);
        titel_kurz.setHint("Kurztitel (z.B. esv_vlm)");
        final EditText titel_lang = new EditText(this);
        titel_lang.setHint("Längerer Titel (z.B. ESV Dresden 1. Herren)");
        final EditText beschreibung = new EditText(this);
        beschreibung.setHint("Beschreibung (Optional)");
        //final Spinner datum = new Spinner(this);
        //datum.setAdapter(new DatePicker(this));
        ll.addView(titel_kurz);
        ll.addView(titel_lang);
        ll.addView(beschreibung);
        alert.setView(ll);

        alert.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Team t = new Team();
                t.setKurz_name(String.valueOf(titel_kurz.getText()).trim());
                t.setLang_name(String.valueOf(titel_lang.getText()).trim());
                try{
                    t.setBeschreibung(String.valueOf(beschreibung.getText()));
                }catch (Exception e){
                    t.setBeschreibung("");
                }

                t.setId((int) dbh.addTeam(t));
                ListView lv = (ListView) findViewById(R.id.listViewTeams);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
                adapter.add(t);
                Log.d("Adapter", "Objekt t geadded. Kurztitel: " + t.getKurz_name());
                adapter.notifyDataSetChanged();
                // Do something with value!
            }
        });

        alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });


        alert.show();

        //this.recreate();

    }

    public void neuerSpieler(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Context context = this;
        alert.setTitle("Spieler anlegen");
        alert.setMessage("Leg einen neuen Spieler an");

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_player, null);
        final EditText vorname = (EditText) alertLayout.findViewById(R.id.addPlayerEditFirstname);
        final EditText nachname = (EditText) alertLayout.findViewById(R.id.addPlayerEditLastname);
        final EditText nummer = (EditText) alertLayout.findViewById(R.id.addPlayerEditNumber);
        final CheckBox goalie = (CheckBox) alertLayout.findViewById(R.id.addPlayerCheckboxGoalie);
        alert.setView(alertLayout);

        alert.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Spieler sp = new Spieler();

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
                    Log.d("Neuer Spieler", e.getMessage());
                }

                dbh.addSpieler(sp);
                ListView lv = (ListView) findViewById(R.id.listViewSpieler);
                ArrayAdapter adapter = (ArrayAdapter) lv.getAdapter();
                adapter.add(sp);
                adapter.notifyDataSetChanged();
                // Do something with value!
            }
        });

        alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
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
}
