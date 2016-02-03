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
import java.util.Calendar;
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
    private static final int DATABASE_VERSION = 11;

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
    private static final String SPIEL_SPIELER_SPIELER = "spiel_spieler_spieler";
    private static final String SPIEL_SPIELER_NUMMER = "spiel_spieler_nummer";

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
            SPIEL_GASTTEAM + " TEXT, " + SPIEL_DATUM + " DATE, " + SPIEL_BEENDET + " INTEGER, " +
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
            SPIEL_SPIELER_SPIELER + " INTEGER, " + SPIEL_SPIELER_NUMMER + " INTEGER, " +
            KEY_CREATED_AT + " DATE" + ")";

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

    public int deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SPIEL, null, null);
        db.delete(TABLE_SPIEL_SPIELER, null, null);
        db.delete(TABLE_STATISTIK, null, null);
        db.delete(TABLE_TEAM, null, null);
        db.delete(TABLE_TEAM_SPIELER, null, null);
        db.delete(TABLE_SPIEL_SPIELER, null, null);

        return 17;
    }

    // ADDER
    public long addSpieler(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPIELER_VORNAME, player.getVorname());
        values.put(SPIELER_NACHNAME, player.getNachname());
        values.put(SPIELER_NUMMER, player.getNummmer());
        if(player.isTorwart()){
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

    public long addSpiel(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(game.isBeendet()) {
            values.put(SPIEL_BEENDET, 1);
        }else{
            values.put(SPIEL_BEENDET, 0);
        }
        values.put(SPIEL_GASTTEAM, game.getGastteam());
        values.put(SPIEL_HEIMTEAM, game.getHeimteam().getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        Calendar datum = game.getDatum();
        values.put(SPIEL_DATUM, formatter.format(datum.getTime()));
        values.put(KEY_CREATED_AT, formatter.format(new Date()));

        long l = db.insert(TABLE_SPIEL, null, values);
        db.close();
        return l;

    }

    public long addEreignis(Action e){
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
        //values.put(EREIGNIS_SPORTART, e.getTypeOfSport().getId());

        long l = db.insert(TABLE_EREIGNIS, null, values);
        db.close();
        return l;
    }

    public long addSpielerToTeam(Player s, Team t){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEAM_SPIELER_SPIELER, s.getId());
        values.put(TEAM_SPIELER_TEAM, t.getId());
        values.put(TEAM_SPIELER_NUMMER, s.getNummmer());

        long l = db.insert(TABLE_TEAM_SPIELER, null, values);
        db.close();
        return l;
    }

    public long addStatistik(ActionMapping stat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATISTIK_SPIEL, stat.getGame().getId());
        values.put(STATISTIK_SPIELER, stat.getPlayer().getId());
        values.put(STATISTIK_EREIGNIS, stat.getAction().getId());

        long l = db.insert(TABLE_STATISTIK, null, values);
        db.close();
        return l;
    }

    public long addSpielerToSpiel(Player sp, Game s, int number){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPIEL_SPIELER_SPIEL, s.getId());
        values.put(SPIEL_SPIELER_SPIELER, sp.getId());
        values.put(SPIEL_SPIELER_NUMMER, number);

        long l = db.insert(TABLE_SPIEL_SPIELER, null, values);
        db.close();
        return l;
    }

    // ALL GETTER

    public Player getPlayer(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_SPIELER + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Player s = new Player();
        if(c.moveToFirst()){

            s.setId(c.getInt(0));
            s.setNachname(c.getString(c.getColumnIndex(SPIELER_NACHNAME)));
            s.setVorname(c.getString(c.getColumnIndex(SPIELER_VORNAME)));
            s.setNummmer(c.getInt(c.getColumnIndex(SPIELER_NUMMER)));
            if(c.getString(c.getColumnIndex(SPIELER_TORWART)).equals("Ja")){
                s.setTorwart(true);
            }else{
                s.setTorwart(false);
            }

        }
        c.close();
        return s;
    }
    public List<Player> getAllPlayers() {
        List<Player> allPlayer = new ArrayList<Player>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPIELER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Player s = new Player();

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
                allPlayer.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return allPlayer;
    }

    public List<Player> getAllPlayersFromTeam(int id) {
        List<Player> allPlayerFromTeam = new ArrayList<Player>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPIELER + ", " + TABLE_TEAM_SPIELER +
                " WHERE " + TABLE_SPIELER + "." + KEY_ID + " = " + TEAM_SPIELER_SPIELER +
                " AND " + TEAM_SPIELER_TEAM + " = " + id + " ORDER BY " +
                TEAM_SPIELER_TEAM + ", " + SPIELER_NUMMER + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Player s = new Player();

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
                allPlayerFromTeam.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return allPlayerFromTeam;
    }

    public List<Player> getAllPlayersNotFromTeam(int id) {
        String selectQuery = "SELECT * FROM " + TABLE_SPIELER + " LEFT JOIN " + TABLE_TEAM_SPIELER +
                " ON " + TABLE_SPIELER + "." + KEY_ID + " = " + TEAM_SPIELER_SPIELER +
                " WHERE " + TEAM_SPIELER_TEAM + " IS NULL OR " +
                TEAM_SPIELER_TEAM + " != " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        String TAG = "allPlayersNotFromTeam";
        List<Player> allPlayerFromTeam = new ArrayList<Player>();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Player s = new Player();

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
                allPlayerFromTeam.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return allPlayerFromTeam;
    }

    public List<Player> getAllPlayersFromGame(int spielId) {
        List<Player> allPlayerFromGame = new ArrayList<Player>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPIELER + ", " + TABLE_SPIEL_SPIELER +
                " WHERE " + TABLE_SPIELER + "." + KEY_ID + " = " + SPIEL_SPIELER_SPIELER +
                " AND " + SPIEL_SPIELER_SPIEL + " = " + spielId + " ORDER BY " +
                SPIELER_NUMMER + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Player s = new Player();

                /*try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY);
                    s.setDate(formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATE))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*/

                s.setVorname(c.getString(c.getColumnIndex(SPIELER_VORNAME)));
                s.setNachname(c.getString(c.getColumnIndex(SPIELER_NACHNAME)));
                s.setNummmer(c.getInt(c.getColumnIndex(SPIEL_SPIELER_NUMMER)));
                s.setId(c.getInt(0));
                if(c.getString(c.getColumnIndex(SPIELER_TORWART)).equals("Ja")){
                    s.setTorwart(true);
                }else{
                    s.setTorwart(false);
                }
                allPlayerFromGame.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return allPlayerFromGame;
    }

    public int isPlayerInGame(Player sp, Game s){
        String selectQuery = "SELECT  * FROM " + TABLE_SPIEL_SPIELER + " WHERE " + SPIEL_SPIELER_SPIELER + " = " + sp.getId() +
                " AND " + SPIEL_SPIELER_SPIEL + " = " + s.getId();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.getCount() == 0) {
            return -1;
        }else{
            c.moveToFirst();
            return c.getInt(c.getColumnIndex(SPIEL_SPIELER_NUMMER));
        }

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

    public Game getSpiel(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_SPIEL + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Game sp = new Game();
        if(c.moveToFirst()){



            Team t = getTeam(c.getInt(c.getColumnIndex(SPIEL_HEIMTEAM)));

            sp.setGastteam(c.getString(c.getColumnIndex(SPIEL_GASTTEAM)));
            sp.setHeimteam(t);
            if(c.getInt(c.getColumnIndex(SPIEL_BEENDET)) == 1) {
                sp.setBeendet(true);
            }else{
                sp.setBeendet(false);
            }
            sp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar datum = Calendar.getInstance();
            datum.set(Calendar.YEAR, 1970);
            try {
                Date tempDate = formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATUM)));

                datum.setTime(tempDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            sp.setDatum(datum);

        }
        return sp;
    }

    public List<Game> getAllGames() {
        List<Game> alleSpiele = new ArrayList<Game>();
        String selectQuery = "SELECT  * FROM " + TABLE_SPIEL + ", " + TABLE_TEAM + " WHERE " +
                SPIEL_HEIMTEAM + " = " + TABLE_TEAM + "." + KEY_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Game s = new Game();

                Team t = getTeam(c.getInt(c.getColumnIndex(SPIEL_HEIMTEAM)));

                s.setGastteam(c.getString(c.getColumnIndex(SPIEL_GASTTEAM)));
                s.setHeimteam(t);
                if(c.getInt(c.getColumnIndex(SPIEL_BEENDET)) == 1) {
                    s.setBeendet(true);
                }else{
                    s.setBeendet(false);
                }
                s.setId(c.getInt(0));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Calendar datum = Calendar.getInstance();
                datum.set(Calendar.YEAR, 1970);
                try {
                    Date tempDate = formatter.parse(c.getString(c.getColumnIndex(SPIEL_DATUM)));

                    datum.setTime(tempDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                s.setDatum(datum);

                alleSpiele.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return alleSpiele;
    }



    public Action getEreignis(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_EREIGNIS + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Action e = new Action();
        if(c.moveToFirst()){

            e.setName(c.getString(c.getColumnIndex(EREIGNIS_NAME)));
            e.setBeschreibung(c.getString(c.getColumnIndex(EREIGNIS_BESCHREIBUNG)));
            e.setBild(c.getInt(c.getColumnIndex(EREIGNIS_BILD)));
            if(c.getInt(c.getColumnIndex(EREIGNIS_AKTIV)) == 1){
                e.setActive(true);
            }else{
                e.setActive(false);
            }
            //e.setTypeOfSport();
            e.setId(c.getInt(c.getColumnIndex(KEY_ID)));

        }
        return e;
    }

    public List<Action> getAlleEreignisse() {
        List<Action> alleEreignisse = new ArrayList<Action>();
        String selectQuery = "SELECT  * FROM " + TABLE_EREIGNIS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Action e = new Action();
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

    public List<Action> getAllActiveActions() {
        List<Action> alleEreignisse = new ArrayList<Action>();
        String selectQuery = "SELECT  * FROM " + TABLE_EREIGNIS + " WHERE " + EREIGNIS_AKTIV + " = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Action e = new Action();
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

    public List<PlayerToActionMapping> getAlleSpielEreignisse(List<Player> player, Game sp) {
        List<PlayerToActionMapping> alleSpielerMitEreignissen = new ArrayList<PlayerToActionMapping>();
        SQLiteDatabase db = this.getReadableDatabase();

        for(Player s : player) {
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
            PlayerToActionMapping sez = new PlayerToActionMapping();
            sez.setEreignisse(map);
            sez.setPlayer(s);
            alleSpielerMitEreignissen.add(sez);
        }

        return alleSpielerMitEreignissen;
    }

    public List<Integer> getAllActionsFromTeam(Team t){
        List<Integer> actions = new ArrayList<Integer>();
        String selectQuery = "SELECT " + STATISTIK_EREIGNIS + " FROM " + TABLE_STATISTIK +
                " GROUP BY " + STATISTIK_EREIGNIS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                actions.add(c.getInt(0));
            }while (c.moveToNext());
        }
        return actions;
    }

    public Stats getStatsActionForTeam(int id, Team t){
        List<Stats> allTeamStats = new ArrayList<Stats>();
        String selectQuery;
        if(t == null){
            selectQuery = "SELECT " + STATISTIK_EREIGNIS + ", COUNT(" + STATISTIK_SPIELER + ")" +
                    " FROM " + TABLE_STATISTIK +
                    " WHERE " + STATISTIK_EREIGNIS + " = " + id +
                    " GROUP BY " + STATISTIK_EREIGNIS;
        }else {
            selectQuery = "SELECT " + STATISTIK_EREIGNIS + ", COUNT(" + STATISTIK_SPIELER + ")" +
                    " FROM " + TABLE_STATISTIK +
                    " WHERE " + STATISTIK_EREIGNIS + " = " + id +
                    " GROUP BY " + STATISTIK_EREIGNIS;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Stats stat = new Stats();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            stat.setTitle(getEreignis(id).getName());

            stat.setSum(c.getInt(1));
            //stat.setAverage(c.getDouble(c.getColumnIndex("AVG")));

        }else{
            stat.setTitle(getEreignis(id).getName());

            stat.setSum(0);
        }

        if(t == null){
            selectQuery = "SELECT " + STATISTIK_SPIEL +
                    " FROM " + TABLE_STATISTIK +
                    " WHERE " + STATISTIK_EREIGNIS + " = " + id +
                    " GROUP BY " + STATISTIK_SPIEL;
        }else {
            selectQuery = "SELECT " + STATISTIK_SPIEL +
                    " FROM " + TABLE_STATISTIK +
                    " WHERE " + STATISTIK_EREIGNIS + " = " + id +
                    " GROUP BY " + STATISTIK_SPIEL;
        }

        c = db.rawQuery(selectQuery, null);
        stat.setAverage(new Double(stat.getSum())/new Double(c.getCount()));
        Log.d("Stats", "Average Berechnung: Sum = "+stat.getSum()+", Count = "+c.getCount());


        c.close();
        return stat;
    }

    public List<Stats> getStatsFromPlayerForAction(int id, Team t){
        List<Stats> allPlayerStatsForAction = new ArrayList<Stats>();
        String selectQuery;
        String selectQuery_avg;
        if(t == null){
            selectQuery = "SELECT " + STATISTIK_SPIELER + ", COUNT(" + STATISTIK_SPIEL + ")" +
                    " FROM " + TABLE_STATISTIK +
                    " WHERE " + STATISTIK_EREIGNIS + " = " + id +
                    " GROUP BY " + STATISTIK_SPIELER;
        }else {
            selectQuery = "SELECT " + STATISTIK_SPIELER + ", COUNT(" + STATISTIK_SPIEL + ")" +
                    " FROM " + TABLE_STATISTIK +
                    " WHERE " + STATISTIK_EREIGNIS + " = " + id +
                    " GROUP BY " + STATISTIK_SPIELER;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {

            do {
                Stats stat = new Stats();
                stat.setPlayer(getPlayer(c.getInt(0)));

                try{
                    stat.setSum(c.getInt(1));
                }catch(Exception e){
                    stat.setSum(0);
                }


                if(t == null){
                    selectQuery_avg = "SELECT " + STATISTIK_SPIEL +
                            " FROM " + TABLE_STATISTIK +
                            " WHERE " + STATISTIK_SPIELER + " = " + stat.getPlayer().getId() +
                            " GROUP BY " + STATISTIK_SPIEL;
                }else {
                    selectQuery_avg = "SELECT " + STATISTIK_SPIEL +
                            " FROM " + TABLE_STATISTIK +
                            " WHERE " + STATISTIK_SPIELER + " = " + stat.getPlayer().getId() +
                            " GROUP BY " + STATISTIK_SPIEL;
                }

                Cursor c_avg = db.rawQuery(selectQuery_avg, null);
                try{
                    stat.setAverage(new Double(stat.getSum())/new Double(c_avg.getCount()));
                }catch(Exception e){
                    stat.setAverage(0);
                }


                allPlayerStatsForAction.add(stat);
            } while (c.moveToNext());
        }

            //stat.setAverage(c.getDouble(c.getColumnIndex("AVG")));





        c.close();
        return allPlayerStatsForAction;
    }

    //Alle DELETER

    public int removePlayerFromTeam(Player s, Team t){
        SQLiteDatabase db = this.getReadableDatabase();
        
        return db.delete(TABLE_TEAM_SPIELER, TEAM_SPIELER_SPIELER + " = " + s.getId() +
                " AND " + TEAM_SPIELER_TEAM + " = " + t.getId(), null);
    }

    public int deleteActionFromGame(ActionMapping e){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_STATISTIK + " WHERE " + STATISTIK_SPIEL + " = " + e.getGame().getId() + " AND " +
                STATISTIK_SPIELER + " = " + e.getPlayer().getId() + " AND " + STATISTIK_EREIGNIS + " = " + e.getAction().getId();

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            Log.d("db_deleteAction", "Anzahl der in Frage kommenden Ereignisse: "+c.getCount());
            return db.delete(TABLE_STATISTIK, KEY_ID + " = " + c.getInt(0), null);
        }else{
            return 0;
        }

    }

    public int deletePlayerFromGame(Player sp, Game s){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.delete(TABLE_SPIEL_SPIELER, SPIEL_SPIELER_SPIEL + " = " + s.getId() +
                " AND " + SPIEL_SPIELER_SPIELER + " = " + sp.getId(), null);
    }

    public int deleteAllPlayersFromGame(int spielId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_SPIEL_SPIELER, SPIEL_SPIELER_SPIEL + " = " + spielId, null);
    }

    public int deleteAllActions(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_EREIGNIS, null, null);
    }

    public int deleteAllStatsFromGame(int spielId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_STATISTIK, STATISTIK_SPIEL + " = " + spielId, null);
    }


    public int deleteGame(int spielId){
        SQLiteDatabase db = this.getReadableDatabase();
        deleteAllPlayersFromGame(spielId);
        deleteAllStatsFromGame(spielId);
        return db.delete(TABLE_SPIEL, KEY_ID + " = " + spielId, null);
    }

    public int deletePlayer(int playerId){
        SQLiteDatabase db = this.getReadableDatabase();
        deleteAllStatsFromPlayer(playerId);
        deleteAllGamesFromPlayer(playerId);
        return db.delete(TABLE_SPIELER, KEY_ID + " = " + playerId, null);
    }

    public int deleteAllStatsFromPlayer(int playerId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_STATISTIK, STATISTIK_SPIELER + " = " + playerId, null);
    }

    public int deleteAllGamesFromPlayer(int playerId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_SPIEL_SPIELER, SPIEL_SPIELER_SPIELER + " = " + playerId, null);
    }

    public int deleteAllPlayersFromTeam(int teamId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_TEAM_SPIELER, TEAM_SPIELER_TEAM + " = " + teamId, null);
    }

    public int deleteTeam(int teamId){
        SQLiteDatabase db = this.getReadableDatabase();
        deleteAllPlayersFromTeam(teamId);
        return db.delete(TABLE_TEAM, KEY_ID + " = " + teamId, null);
    }

    //Alle UPDATER

    public int updateAction(Action e){
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

    public int updateGame(Game s){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        if(s.isBeendet()) {
            values.put(SPIEL_BEENDET, 1);
        }else{
            values.put(SPIEL_BEENDET, 0);
        }
        values.put(SPIEL_GASTTEAM, s.getGastteam());
        values.put(SPIEL_HEIMTEAM, s.getHeimteam().getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        Calendar datum = s.getDatum();
        values.put(SPIEL_DATUM, formatter.format(datum.getTime()));
        //values.put(KEY_CREATED_AT, formatter.format(new Date()));

        int i = db.update(TABLE_SPIEL, values, KEY_ID + " = " + s.getId(), null);

        db.close();

        return i;
    }

    public int updatePlayerInGame(Player sp, Game s, int number){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(SPIEL_SPIELER_SPIEL, s.getId());
        values.put(SPIEL_SPIELER_SPIELER, sp.getId());
        values.put(SPIEL_SPIELER_NUMMER, number);

        int i = db.update(TABLE_SPIEL_SPIELER, values, SPIEL_SPIELER_SPIEL + " = " + s.getId() +
                " AND " + SPIEL_SPIELER_SPIELER + " = " + sp.getId(), null);

        db.close();

        return i;
    }


}
