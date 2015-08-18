package android.ht.sportstatistik.datahandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by HT on 18.03.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;
    private SQLiteDatabase db = null;

    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "sportStatistik";

    // Table Names
    private static final String TABLE_TEAM = "team";
    private static final String TABLE_LOG = "log";
    private static final String TABLE_SPIEL = "spiel";
    private static final String TABLE_EREIGNIS = "ereignis";
    private static final String TABLE_SPIELER = "spieler";
    private static final String TABLE_TEAM_SPIELER = "team_spieler";
    private static final String TABLE_SPIEL_SPIELER = "spiel_spieler";
    private static final String TABLE_STATISTIK = "statistik";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // TEAM Table - column names
    private static final String TEAM_KURZ = "team_kurz";
    private static final String TEAM_LANG = "team_lang";
    private static final String TEAM_BESCHREIBUNG = "team_beschreibung";

    // LOG Table - column names
    private static final String LOG_ACTIVITY = "log_activity";
    private static final String LOG_DATE = "log_date";

    // SPIEL Table - column names
    private static final String SPIEL_HEIMTEAM = "spiel_heimteam";
    private static final String SPIEL_GASTTEAM = "spiel_gastteam";
    private static final String SPIEL_DATUM = "spiel_datum";
    private static final String SPIEL_BEENDET = "spiel_beendet";

    // EREIGNIS Table - column names
    private static final String EREIGNIS_NAME = "ereignis_name";
    private static final String EREIGNIS_BESCHREIBUNG = "ereignis_beschreibung";
    private static final String EREIGNIS_BILD = "ereignis_bild";

    // SPIELER Table - column names
    private static final String SPIELER_VORNAME = "spieler_vorname";
    private static final String SPIELER_NACHNAME = "spieler_nachname";
    private static final String SPIELER_NUMMER = "spieler_nummer";
    private static final String SPIELER_TORWART = "spieler_torwart";

    // TEAM_SPIELER Table - column names
    private static final String TEAM_SPIELER_TEAM = "team_spieler_team";
    private static final String TEAM_SPIELER_SPIELER = "team_spieler_spieler";
    private static final String TEAM_SPIELER_NUMMER = "team_spieler_nummer";

    // SPIEL_SPIELER Table - column names
    private static final String SPIEL_SPIELER_SPIEL = "spiel_spieler_spiel";
    private static final String SPIEL_SPIELER_TEAM_SPIELER = "spiel_spieler_team_spieler";

    // STATISTIK Table - column names
    private static final String STATISTIK_SPIEL = "statistik_spiel";
    private static final String STATISTIK_SPIELER = "statistik_spieler";
    private static final String STATISTIK_EREIGNIS = "statistik_ereignis";

    // Table Create Statements
    private static final String CREATE_TABLE_TEAM = "CREATE TABLE " + TABLE_TEAM + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + TEAM_KURZ + " TEXT, " +
            TEAM_LANG + " TEXT, " + TEAM_BESCHREIBUNG + " TEXT, " +
            KEY_CREATED_AT + " DATE" + ")";

    private static final String CREATE_TABLE_LOG = "CREATE TABLE " + TABLE_LOG + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + LOG_ACTIVITY + " TEXT, " +
            LOG_DATE + " DATE, " + KEY_CREATED_AT + " DATE" + ")";

    private static final String CREATE_TABLE_SPIEL = "CREATE TABLE " + TABLE_SPIEL + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + SPIEL_HEIMTEAM + " INTEGER, " +
            SPIEL_GASTTEAM + " TEXT, " + SPIEL_DATUM + " DATE, " + SPIEL_BEENDET + " TEXT, " +
            KEY_CREATED_AT + " DATE" + ")";

    private static final String CREATE_TABLE_EREIGNIS = "CREATE TABLE " + TABLE_EREIGNIS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + EREIGNIS_NAME + " TEXT, " +
            EREIGNIS_BESCHREIBUNG + " TEXT, " + EREIGNIS_BILD + " TEXT, " +
            KEY_CREATED_AT + " DATE" + ")";

    private static final String CREATE_TABLE_SPIELER = "CREATE TABLE " + TABLE_SPIELER + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + SPIELER_VORNAME + " TEXT, " +
            SPIELER_NACHNAME + " TEXT, " + SPIELER_NUMMER + " INTEGER, " +
            SPIELER_TORWART + " TEXT, " + KEY_CREATED_AT + " DATE" + ")";

    private static final String CREATE_TABLE_TEAM_SPIELER = "CREATE TABLE " + TABLE_TEAM_SPIELER + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + TEAM_SPIELER_TEAM + " INTEGER, " +
            TEAM_SPIELER_SPIELER + " INTEGER, " + TEAM_SPIELER_NUMMER + " INTEGER, " +
            KEY_CREATED_AT + " DATE" + ")";

    private static final String CREATE_TABLE_SPIEL_SPIELER = "CREATE TABLE " + TABLE_SPIEL_SPIELER + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + SPIEL_SPIELER_SPIEL + " INTEGER, " +
            SPIEL_SPIELER_TEAM_SPIELER + " INTEGER, " + KEY_CREATED_AT + " DATE" + ")";

    private static final String CREATE_TABLE_STATISTIK = "CREATE TABLE " + TABLE_STATISTIK + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + STATISTIK_SPIEL + " INTEGER, " +
            STATISTIK_SPIELER + " INTEGER, " + STATISTIK_EREIGNIS + " INTEGER, " +
            KEY_CREATED_AT + " DATE" + ")";

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_SPIELER);
        db.execSQL(CREATE_TABLE_EREIGNIS);
        db.execSQL(CREATE_TABLE_LOG);
        db.execSQL(CREATE_TABLE_SPIEL);
        db.execSQL(CREATE_TABLE_SPIEL_SPIELER);
        db.execSQL(CREATE_TABLE_STATISTIK);
        db.execSQL(CREATE_TABLE_TEAM);
        db.execSQL(CREATE_TABLE_TEAM_SPIELER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPIELER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EREIGNIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPIEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPIEL_SPIELER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTIK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM_SPIELER);
        // create new tables
        onCreate(db);
    }

    // ADDER
    public long addSpieler(Spieler spieler) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPIELER_VORNAME, spieler.getVorname());
        values.put(SPIELER_NACHNAME, spieler.getNachname());
        values.put(SPIELER_NUMMER, spieler.getNummmer());
        if(spieler.isTorwart()){
            values.put(SPIELER_TORWART, "Ja");
        }else{
            values.put(SPIELER_TORWART, "Nein");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
        values.put(KEY_CREATED_AT, formatter.format(new Date()));

        long id = db.insert(TABLE_SPIELER, null, values);
        db.close();
        Log.d(TAG, "addSpieler durchgeführt");
        return id;

    }

    public long addTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEAM_KURZ, team.getKurz_name());
        values.put(TEAM_LANG, team.getLang_name());
        values.put(TEAM_BESCHREIBUNG, team.getBeschreibung());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
        values.put(KEY_CREATED_AT, formatter.format(new Date()));

        long id = db.insert(TABLE_TEAM, null, values);
        db.close();
        return id;

    }

    public void addSpiel(Spiel spiel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPIEL_BEENDET, spiel.getBeendet());
        values.put(SPIEL_GASTTEAM, spiel.getGastteam());
        values.put(SPIEL_HEIMTEAM, spiel.getHeimteam_id());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
        values.put(SPIEL_DATUM, formatter.format(spiel.getDatum()));
        values.put(KEY_CREATED_AT, formatter.format(new Date()));

        db.insert(TABLE_SPIEL, null, values);
        db.close();

    }

    public boolean addSpielerToTeam(Spieler s, Team t){
        SQLiteDatabase db = this.getWritableDatabase();

        if(getAllTeamsFromPlayer(s.getId()).contains(t)){
            return false;
        }else {
            ContentValues values = new ContentValues();
            values.put(TEAM_SPIELER_SPIELER, s.getId());
            values.put(TEAM_SPIELER_TEAM, t.getId());
            values.put(TEAM_SPIELER_NUMMER, s.getNummmer());

            db.insert(TABLE_TEAM_SPIELER, null, values);
            db.close();
            return true;
        }
    }

    // ALL GETTER
    public List<Spieler> getAllPlayers() {
        List<Spieler> alleSpieler = new ArrayList<Spieler>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPIELER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Spieler s = new Spieler();

                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/

                s.setVorname(c.getString(c.getColumnIndex(SPIELER_VORNAME)));
                s.setNachname(c.getString(c.getColumnIndex(SPIELER_NACHNAME)));
                s.setNummmer(c.getInt(c.getColumnIndex(SPIELER_NUMMER)));
                s.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                if(c.getString(c.getColumnIndex(SPIELER_TORWART)).equals("Ja")){
                    s.setTorwart(true);
                }else{
                    s.setTorwart(false);
                }
                alleSpieler.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return alleSpieler;
    }

    public List<Spieler> getAllPlayersFromTeam(int id) {
        List<Spieler> alleSpielerFromTeam = new ArrayList<Spieler>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPIELER + ", " + TABLE_TEAM_SPIELER +
                " WHERE " + TABLE_SPIELER + "." + KEY_ID + " = " + TEAM_SPIELER_SPIELER +
                " AND " + TEAM_SPIELER_TEAM + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Spieler s = new Spieler();

                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/

                s.setVorname(c.getString(c.getColumnIndex(SPIELER_VORNAME)));
                s.setNachname(c.getString(c.getColumnIndex(SPIELER_NACHNAME)));
                s.setNummmer(c.getInt(c.getColumnIndex(SPIELER_NUMMER)));
                s.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                if(c.getString(c.getColumnIndex(SPIELER_TORWART)).equals("Ja")){
                    s.setTorwart(true);
                }else{
                    s.setTorwart(false);
                }
                alleSpielerFromTeam.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return alleSpielerFromTeam;
    }

    public List<Spieler> getAllPlayersNotFromTeam(int id) {
        String selectQuery = "SELECT * FROM " + TABLE_SPIELER + " LEFT JOIN " + TABLE_TEAM_SPIELER +
                " ON " + TABLE_SPIELER + "." + KEY_ID + " = " + TEAM_SPIELER_SPIELER +
                " WHERE " + TEAM_SPIELER_TEAM + " IS NULL OR " +
                TEAM_SPIELER_TEAM + " != " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        List<Spieler> alleSpielerFromTeam = new ArrayList<Spieler>();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Spieler s = new Spieler();

                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/

                s.setVorname(c.getString(c.getColumnIndex(SPIELER_VORNAME)));
                s.setNachname(c.getString(c.getColumnIndex(SPIELER_NACHNAME)));
                s.setNummmer(c.getInt(c.getColumnIndex(SPIELER_NUMMER)));
                s.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                if(c.getString(c.getColumnIndex(SPIELER_TORWART)).equals("Ja")){
                    s.setTorwart(true);
                }else{
                    s.setTorwart(false);
                }
                alleSpielerFromTeam.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return alleSpielerFromTeam;
    }

    public Team getTeam(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_TEAM + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Team t = new Team();
        t.setLang_name("DEFAULT");
        if(c.moveToFirst()){

            t.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            t.setKurz_name(c.getString(c.getColumnIndex(TEAM_KURZ)));
            t.setLang_name(c.getString(c.getColumnIndex(TEAM_LANG)));
            t.setBeschreibung(c.getString(c.getColumnIndex(TEAM_BESCHREIBUNG)));
        }
        return t;
    }

    public List<Team> getAllTeams() {
        List<Team> alleTeams = new ArrayList<Team>();
        String selectQuery = "SELECT  * FROM " + TABLE_TEAM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Team t = new Team();

                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/
                t.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                t.setKurz_name(c.getString(c.getColumnIndex(TEAM_KURZ)));
                t.setLang_name(c.getString(c.getColumnIndex(TEAM_LANG)));
                t.setBeschreibung(c.getString(c.getColumnIndex(TEAM_BESCHREIBUNG)));

                alleTeams.add(t);
            } while (c.moveToNext());
        }
        c.close();
        return alleTeams;
    }

    public List<Team> getAllTeamsFromPlayer(int id) {
        List<Team> alleTeamsFromPlayer = new ArrayList<Team>();
        String selectQuery = "SELECT  * FROM " + TABLE_TEAM_SPIELER + ", " + TABLE_TEAM +
                " WHERE " + TABLE_TEAM + "." + KEY_ID + " = " + TEAM_SPIELER_TEAM +
                " AND " + TEAM_SPIELER_SPIELER + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Team t = new Team();

                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/

                t.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                t.setKurz_name(c.getString(c.getColumnIndex(TEAM_KURZ)));
                t.setLang_name(c.getString(c.getColumnIndex(TEAM_LANG)));
                t.setBeschreibung(c.getString(c.getColumnIndex(TEAM_BESCHREIBUNG)));
                alleTeamsFromPlayer.add(t);
            } while (c.moveToNext());
        }
        c.close();
        return alleTeamsFromPlayer;
    }

    public List<Spiel> getAllGames() {
        List<Spiel> alleSpiele = new ArrayList<Spiel>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPIEL + ", " + TABLE_TEAM + " WHERE " +
                SPIEL_HEIMTEAM + " = " + TABLE_TEAM + "." + KEY_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Spiel s = new Spiel();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/

                s.setGastteam(c.getString(c.getColumnIndex(SPIEL_GASTTEAM)));
                s.setHeimteam_id(c.getInt(c.getColumnIndex(SPIEL_HEIMTEAM)));
                s.setHeimteam_titel(c.getString(c.getColumnIndex(TABLE_TEAM+"."+TEAM_KURZ)));
                s.setBeendet(c.getString(c.getColumnIndex(SPIEL_BEENDET)));
                s.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                try {
                    s.setDatum(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATUM))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                alleSpiele.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return alleSpiele;
    }

    public int removePlayerFromTeam(Spieler s, Team t){
        SQLiteDatabase db = this.getReadableDatabase();
        
        return db.delete(TABLE_TEAM_SPIELER, TEAM_SPIELER_SPIELER + " = " + s.getId() +
                " AND " + TEAM_SPIELER_TEAM + " = " + t.getId(), null);
    }
}
