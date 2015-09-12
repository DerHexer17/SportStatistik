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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by HT on 18.03.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;
    private SQLiteDatabase db = null;

    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 10;

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
    private static final String TABLE_SPORTART = "sportart";

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
    //private static final String EREIGNIS_EINHEIT = "ereignis_einheit";
    private static final String EREIGNIS_BESCHREIBUNG = "ereignis_beschreibung";
    private static final String EREIGNIS_BILD = "ereignis_bild";
    private static final String EREIGNIS_SPORTART = "ereignis_sportart";
    private static final String EREIGNIS_AKTIV = "ereignis_aktiv";

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

    //SPORTART Table - column names
    private static final String SPORTART_TITEL = "sportart_titel";
    private static final String SPORTART_BESCHREIBUNG = "sportart_beschreibung";

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
            EREIGNIS_BESCHREIBUNG + " TEXT, " + EREIGNIS_BILD + " INTEGER, " +
            EREIGNIS_SPORTART + " INTEGER, " + EREIGNIS_AKTIV + " INTEGER, " + KEY_CREATED_AT + " DATE" + ")";

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

    private static final String CREATE_TABLE_SPORTART = "CREATE TABLE " + TABLE_SPORTART + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " + SPORTART_TITEL + " TEXT, " +
            SPORTART_BESCHREIBUNG + " TEXT, " + KEY_CREATED_AT + " DATE" + ")";

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
        db.execSQL(CREATE_TABLE_SPORTART);

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPORTART);
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
        Log.d(TAG, "addSpieler durchgeführt, ID: " + id);
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

    public long addSpiel(Spiel spiel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPIEL_BEENDET, spiel.getBeendet());
        values.put(SPIEL_GASTTEAM, spiel.getGastteam());
        values.put(SPIEL_HEIMTEAM, spiel.getHeimteam().getId());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        values.put(SPIEL_DATUM, formatter.format(spiel.getDatum()));
        values.put(KEY_CREATED_AT, formatter.format(new Date()));

        long l = db.insert(TABLE_SPIEL, null, values);
        db.close();
        return l;

    }

    public long addEreignis(Ereignis e){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EREIGNIS_NAME, e.getName());
        values.put(EREIGNIS_BESCHREIBUNG, e.getBeschreibung());
        values.put(EREIGNIS_BILD, e.getBild());
        if(e.isActive()){
            values.put(EREIGNIS_AKTIV, 1);
        }else{
            values.put(EREIGNIS_AKTIV, 0);
        }
        //values.put(EREIGNIS_SPORTART, e.getSportart().getId());

        long l = db.insert(TABLE_EREIGNIS, null, values);
        db.close();
        return l;
    }

    public long addSpielerToTeam(Spieler s, Team t){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEAM_SPIELER_SPIELER, s.getId());
        values.put(TEAM_SPIELER_TEAM, t.getId());
        values.put(TEAM_SPIELER_NUMMER, s.getNummmer());

        long l = db.insert(TABLE_TEAM_SPIELER, null, values);
        db.close();
        return l;
    }

    public long addStatistik(EreignisZuordnung stat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATISTIK_SPIEL, stat.getSpiel().getId());
        values.put(STATISTIK_SPIELER, stat.getSpieler().getId());
        values.put(STATISTIK_EREIGNIS, stat.getEreignis().getId());

        long l = db.insert(TABLE_STATISTIK, null, values);
        db.close();
        return l;
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
                s.setId(c.getInt(0));
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

        String TAG = "allPlayersNotFromTeam";
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
                s.setId(c.getInt(0));
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

    public Spiel getSpiel(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_SPIEL + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Spiel sp = new Spiel();
        if(c.moveToFirst()){

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);

            Team t = getTeam(c.getInt(c.getColumnIndex(SPIEL_HEIMTEAM)));

            sp.setGastteam(c.getString(c.getColumnIndex(SPIEL_GASTTEAM)));
            sp.setHeimteam(t);
            sp.setBeendet(c.getString(c.getColumnIndex(SPIEL_BEENDET)));
            sp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            try {
                sp.setDatum(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATUM))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return sp;
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
                Team t = getTeam(c.getInt(c.getColumnIndex(SPIEL_HEIMTEAM)));

                s.setGastteam(c.getString(c.getColumnIndex(SPIEL_GASTTEAM)));
                s.setHeimteam(t);
                s.setBeendet(c.getString(c.getColumnIndex(SPIEL_BEENDET)));
                s.setId(c.getInt(0));
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



    public Ereignis getEreignis(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_EREIGNIS + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Ereignis e = new Ereignis();
        if(c.moveToFirst()){

            e.setName(c.getString(c.getColumnIndex(EREIGNIS_NAME)));
            e.setBeschreibung(c.getString(c.getColumnIndex(EREIGNIS_BESCHREIBUNG)));
            e.setBild(c.getInt(c.getColumnIndex(EREIGNIS_BILD)));
            if(c.getInt(c.getColumnIndex(EREIGNIS_AKTIV)) == 1){
                e.setActive(true);
            }else{
                e.setActive(false);
            }
            //e.setSportart();
            e.setId(c.getInt(c.getColumnIndex(KEY_ID)));

        }
        return e;
    }

    public List<Ereignis> getAlleEreignisse() {
        List<Ereignis> alleEreignisse = new ArrayList<Ereignis>();
        String selectQuery = "SELECT  * FROM " + TABLE_EREIGNIS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Ereignis e = new Ereignis();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/
                e.setId(c.getInt(0));
                e.setName(c.getString(c.getColumnIndex(EREIGNIS_NAME)));
                e.setBeschreibung(c.getString(c.getColumnIndex(EREIGNIS_BESCHREIBUNG)));
                e.setBild(c.getInt(c.getColumnIndex(EREIGNIS_BESCHREIBUNG)));
                if(c.getInt(c.getColumnIndex(EREIGNIS_AKTIV)) == 1){
                    e.setActive(true);
                }else{
                    e.setActive(false);
                }
                //SPORTART!

                alleEreignisse.add(e);
            } while (c.moveToNext());
        }
        c.close();
        return alleEreignisse;
    }

    public List<Ereignis> getAllActiveActions() {
        List<Ereignis> alleEreignisse = new ArrayList<Ereignis>();
        String selectQuery = "SELECT  * FROM " + TABLE_EREIGNIS + " WHERE " + EREIGNIS_AKTIV + " = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Ereignis e = new Ereignis();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/
                e.setId(c.getInt(0));
                e.setName(c.getString(c.getColumnIndex(EREIGNIS_NAME)));
                e.setBeschreibung(c.getString(c.getColumnIndex(EREIGNIS_BESCHREIBUNG)));
                e.setBild(c.getInt(c.getColumnIndex(EREIGNIS_BESCHREIBUNG)));
                if(c.getInt(c.getColumnIndex(EREIGNIS_AKTIV)) == 1){
                    e.setActive(true);
                }else{
                    e.setActive(false);
                }
                //SPORTART!

                alleEreignisse.add(e);
            } while (c.moveToNext());
        }
        c.close();
        return alleEreignisse;
    }

    public List<SpielerEreignisZuordnung> getAlleSpielEreignisse(List<Spieler> spieler, Spiel sp) {
        List<SpielerEreignisZuordnung> alleSpielerMitEreignissen = new ArrayList<SpielerEreignisZuordnung>();
        SQLiteDatabase db = this.getReadableDatabase();

        for(Spieler s : spieler) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            String selectQuery = "SELECT " + STATISTIK_EREIGNIS + ", COUNT(" + STATISTIK_SPIELER + ")  FROM " + TABLE_STATISTIK +
                    " WHERE " + STATISTIK_SPIEL + " = " + sp.getId() + " AND " + STATISTIK_SPIELER + " = " + s.getId() +
                    " GROUP BY " + STATISTIK_EREIGNIS;

            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    map.put(getEreignis(c.getInt(0)).getName(), c.getInt(1));
                } while(c.moveToNext());

            }
            c.close();
            SpielerEreignisZuordnung sez = new SpielerEreignisZuordnung();
            sez.setEreignisse(map);
            sez.setSpieler(s);
            alleSpielerMitEreignissen.add(sez);
        }

        return alleSpielerMitEreignissen;
    }

    public int removePlayerFromTeam(Spieler s, Team t){
        SQLiteDatabase db = this.getReadableDatabase();
        
        return db.delete(TABLE_TEAM_SPIELER, TEAM_SPIELER_SPIELER + " = " + s.getId() +
                " AND " + TEAM_SPIELER_TEAM + " = " + t.getId(), null);
    }

    public int deleteActionFromGame(EreignisZuordnung e){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_STATISTIK + " WHERE " + STATISTIK_SPIEL + " = " + e.getSpiel().getId() + " AND " +
                STATISTIK_SPIELER + " = " + e.getSpieler().getId() + " AND " + STATISTIK_EREIGNIS + " = " + e.getEreignis().getId();

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            Log.d("db_deleteAction", "Anzahl der in Frage kommenden Ereignisse: "+c.getCount());
            return db.delete(TABLE_STATISTIK, KEY_ID + " = " + c.getInt(0), null);
        }else{
            return 0;
        }


    }

    public int updateAction(Ereignis e){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(EREIGNIS_NAME, e.getName());
        values.put(EREIGNIS_BESCHREIBUNG, e.getBeschreibung());
        values.put(EREIGNIS_BILD, e.getBild());
        if(e.isActive()){
            values.put(EREIGNIS_AKTIV, 1);
        }else{
            values.put(EREIGNIS_AKTIV, 0);
        }

        int i = db.update(TABLE_EREIGNIS, values, KEY_ID + " = " + e.getId(), null);

        db.close();

        return i;
    }
}
