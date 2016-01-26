package android.ht.sportstatistik.helper;

import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Ereignis;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.Team;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hendrik on 16.08.2015.
 */
public class Testdaten {

    Context context;
    DatabaseHelper dbh;

    public Testdaten(Context c){
        this.context=c;
        this.dbh = DatabaseHelper.getInstance(c);
    }
    public void testdatenEinspielen(){
        List<Team> teams = new ArrayList<Team>();
        List<Spieler> spieler = new ArrayList<Spieler>();
        dbh.deleteAll();


        Team t1 = new Team("esv_vlm", "ESV 1. Herren", "");
        t1.setId((int) dbh.addTeam(t1));
        teams.add(t1);
        Team t2 = new Team("esv_blm", "ESV 2. Herren", "");
        t2.setId((int) dbh.addTeam(t2));
        teams.add(t2);
        Team t3 = new Team("esv_blf", "ESV 1. Damen", "");
        t3.setId((int) dbh.addTeam(t3));
        teams.add(t3);

        Spieler s1 = new Spieler("Hendrik", "Thüs", 18, false);
        s1.setId((int) dbh.addSpieler(s1));
        dbh.addSpielerToTeam(s1, t1);
        Spieler s2 = new Spieler("Jörg", "Ehrlich", 12, true);
        s2.setId((int) dbh.addSpieler(s2));
        dbh.addSpielerToTeam(s2, t1);
        Spieler s3 = new Spieler("Kevin", "Püchl", 1, true);
        s3.setId((int) dbh.addSpieler(s3));
        dbh.addSpielerToTeam(s3, t1);
        Spieler s4 = new Spieler("Marco", "Weithäuser", 2, false);
        s4.setId((int) dbh.addSpieler(s4));
        dbh.addSpielerToTeam(s4, t1);
        Spieler s5 = new Spieler("Sebastian", "Sonntag", 3, false);
        s5.setId((int) dbh.addSpieler(s5));
        dbh.addSpielerToTeam(s5, t1);
        Spieler s6 = new Spieler("Konrad", "Ehelebe", 4, false);
        s6.setId((int) dbh.addSpieler(s6));
        dbh.addSpielerToTeam(s6, t1);
        Spieler s7 = new Spieler("Matti", "Fiehler", 5, false);
        s7.setId((int) dbh.addSpieler(s7));
        dbh.addSpielerToTeam(s7, t1);
        Spieler s8 = new Spieler("Nico", "Betzien", 7, false);
        s8.setId((int) dbh.addSpieler(s8));
        dbh.addSpielerToTeam(s8, t1);
        Spieler s9 = new Spieler("Marius", "Dietrich", 8, false);
        s9.setId((int) dbh.addSpieler(s9));
        dbh.addSpielerToTeam(s9, t1);
        Spieler s10 = new Spieler("Christian", "Melzer", 10, false);
        s10.setId((int) dbh.addSpieler(s10));
        dbh.addSpielerToTeam(s10, t1);
        Spieler s11 = new Spieler("Robin", "Gaida", 11, false);
        s11.setId((int) dbh.addSpieler(s11));
        dbh.addSpielerToTeam(s11, t1);
        Spieler s12 = new Spieler("Ronny", "Reinhardt", 14, false);
        s12.setId((int) dbh.addSpieler(s12));
        dbh.addSpielerToTeam(s12, t1);
        Spieler s13 = new Spieler("Paul", "Hohlfeld", 15, false);
        s13.setId((int) dbh.addSpieler(s13));
        dbh.addSpielerToTeam(s13, t1);
        Spieler s14 = new Spieler("Alexander", "Schmidt", 17, false);
        s14.setId((int) dbh.addSpieler(s14));
        dbh.addSpielerToTeam(s14, t1);



    }

    public void ereignisseEinspielen(){
        int i = dbh.deleteAllActions();
        Log.d("Test", "Anzahl gelöschter Ereignisse: " + i);

        Ereignis e = new Ereignis("Tor", "Tor geworfen");
        e.setBild(R.drawable.goal_icon);
        dbh.addEreignis(e);

        e.setName("Assist");
        e.setBeschreibung("Torerfolg vorbereitet");
        dbh.addEreignis(e);

        e.setName("Technischer Fehler");
        e.setBeschreibung("Ballverlust wegen technischem Fehler");
        dbh.addEreignis(e);

        e.setName("Fehlwurf");
        e.setBeschreibung("Wurfversucht ohne Torerfolg");
        dbh.addEreignis(e);

        e.setName("Gelbe Karte");
        e.setBeschreibung("Gelbe Karte erhalten");
        e.setBild(R.drawable.yellow_card_icon);
        dbh.addEreignis(e);

        e.setName("2 Minuten");
        e.setBeschreibung("2 Minuten Zeitstrafe erhalten");
        dbh.addEreignis(e);

        e.setName("Rote Karte");
        e.setBeschreibung("Rote Karte erhalten");
        dbh.addEreignis(e);

        e.setName("Gehalten");
        e.setBeschreibung("Gegnerischen Wurf pariert");
        dbh.addEreignis(e);

        e.setName("Block");
        e.setBeschreibung("Gegnerischen Wurf geblockt");
        dbh.addEreignis(e);
    }
}
