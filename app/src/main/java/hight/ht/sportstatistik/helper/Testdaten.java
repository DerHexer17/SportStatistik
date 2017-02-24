package hight.ht.sportstatistik.helper;

import android.content.Context;

import hight.ht.sportstatistik.datahandling.Action;
import hight.ht.sportstatistik.datahandling.ActionMapping;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Game;
import hight.ht.sportstatistik.datahandling.Player;
import hight.ht.sportstatistik.datahandling.Team;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
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

        List<Player> player = new ArrayList<Player>();
        dbh.deleteAll();

        Team t1 = createTestTeams().get(0);

        Log.d("testdaten", "t1 ID: "+ t1.getId());
        createTestPlayers(t1);

        createTestGames(t1);

        createTestStats();

        Toast t = Toast.makeText(context, "Die Testdaten wurden erfolgreich eingespielt", Toast.LENGTH_LONG);
        t.show();

    }

    public List<Team> createTestTeams(){
        List<Team> teams = new ArrayList<Team>();

        Team t1 = new Team("sg", "SG Flensburg-Handewitt", "");
        t1.setColor("blue");
        t1.setGoalieColor("yellow");
        t1.setId((int) dbh.addTeam(t1));
        teams.add(t1);
        Team t2 = new Team("foxes", "Füchse Berlin", "");
        t2.setId((int) dbh.addTeam(t2));
        teams.add(t2);
        Team t3 = new Team("bvb", "Borussia Dortmund", "");
        t3.setColor("yellow");
        t3.setId((int) dbh.addTeam(t3));
        teams.add(t3);

        return teams;
    }

    public void createTestPlayers(Team t1){
        /*Player s1 = new Player("Hendrik", "Thüs", 18, false);
        s1.setId((int) dbh.addSpieler(s1));
        dbh.addSpielerToTeam(s1, t1);
        Player s2 = new Player("Jörg", "Ehrlich", 12, true);
        s2.setId((int) dbh.addSpieler(s2));
        dbh.addSpielerToTeam(s2, t1);
        Player s3 = new Player("Kevin", "Püchl", 1, true);
        s3.setId((int) dbh.addSpieler(s3));
        dbh.addSpielerToTeam(s3, t1);
        Player s4 = new Player("Marco", "Weithäuser", 2, false);
        s4.setId((int) dbh.addSpieler(s4));
        dbh.addSpielerToTeam(s4, t1);
        Player s5 = new Player("Sebastian", "Sonntag", 3, false);
        s5.setId((int) dbh.addSpieler(s5));
        dbh.addSpielerToTeam(s5, t1);
        Player s6 = new Player("Konrad", "Ehelebe", 4, false);
        s6.setId((int) dbh.addSpieler(s6));
        dbh.addSpielerToTeam(s6, t1);
        Player s7 = new Player("Matti", "Fiehler", 5, false);
        s7.setId((int) dbh.addSpieler(s7));
        dbh.addSpielerToTeam(s7, t1);
        Player s8 = new Player("Nico", "Betzien", 7, false);
        s8.setId((int) dbh.addSpieler(s8));
        dbh.addSpielerToTeam(s8, t1);
        Player s9 = new Player("Marius", "Dietrich", 8, false);
        s9.setId((int) dbh.addSpieler(s9));
        dbh.addSpielerToTeam(s9, t1);
        Player s10 = new Player("Christian", "Melzer", 10, false);
        s10.setId((int) dbh.addSpieler(s10));
        dbh.addSpielerToTeam(s10, t1);
        Player s11 = new Player("Robin", "Gaida", 11, false);
        s11.setId((int) dbh.addSpieler(s11));
        dbh.addSpielerToTeam(s11, t1);
        Player s12 = new Player("Ronny", "Reinhardt", 14, false);
        s12.setId((int) dbh.addSpieler(s12));
        dbh.addSpielerToTeam(s12, t1);
        Player s13 = new Player("Paul", "Hohlfeld", 15, false);
        s13.setId((int) dbh.addSpieler(s13));
        dbh.addSpielerToTeam(s13, t1);
        Player s14 = new Player("Alexander", "Schmidt", 17, false);
        s14.setId((int) dbh.addSpieler(s14));
        dbh.addSpielerToTeam(s14, t1);
        */

        Player s1 = new Player("Matthias", "Andersson", 1, true);
        s1.setId((int) dbh.addSpieler(s1));
        dbh.addSpielerToTeam(s1, t1);

        Player s2 = new Player("Kevin", "Möller", 16, true);
        s2.setId((int) dbh.addSpieler(s2));
        dbh.addSpielerToTeam(s2, t1);
        Player s3 = new Player("Tobias", "Karlsson", 3, false);
        s3.setId((int) dbh.addSpieler(s3));
        dbh.addSpielerToTeam(s3, t1);
        Player s4 = new Player("Thomas ", "Mogensen", 10, false);
        s4.setId((int) dbh.addSpieler(s4));
        dbh.addSpielerToTeam(s4, t1);
        Player s5 = new Player("Holger", "Glandorf", 9, false);
        s5.setId((int) dbh.addSpieler(s5));
        dbh.addSpielerToTeam(s5, t1);
        Player s6 = new Player("Hampus", "Wanne", 14, false);
        s6.setId((int) dbh.addSpieler(s6));
        dbh.addSpielerToTeam(s6, t1);
        Player s7 = new Player("Rasmus", "Lauge Schmidt", 25, false);
        s7.setId((int) dbh.addSpieler(s7));
        dbh.addSpielerToTeam(s7, t1);
        /*
        Player s8 = new Player("Silvio", "Heinevetter", 12, true);	s8.setId((int) dbh.addSpieler(s8));	dbh.addSpielerToTeam(s8, t2);
        Player s9 = new Player("Paul", "Drux", 95, false);	s9.setId((int) dbh.addSpieler(s9));	dbh.addSpielerToTeam(s9, t2);
        Player s10 = new Player("Jakov", "Gojun", 10, false);	s10.setId((int) dbh.addSpieler(s10));	dbh.addSpielerToTeam(s10, t2);
        Player s11 = new Player("Clara ", "Woltering", 1, true);	s11.setId((int) dbh.addSpieler(s11));	dbh.addSpielerToTeam(s11, t3);
        Player s12 = new Player("Anne", "Müller", 8, false);	s12.setId((int) dbh.addSpieler(s12));	dbh.addSpielerToTeam(s12, t3);
        Player s13 = new Player("Leoni", "Oehme", 27, false);	s13.setId((int) dbh.addSpieler(s13));	dbh.addSpielerToTeam(s13, t3);
        */
    }

    public void createTestGames(Team t1){
        Game g1 = new Game();
        Calendar d = Calendar.getInstance();
        d.set(Calendar.DAY_OF_MONTH, 17);
        d.set(Calendar.MONTH, 10);
        d.set(Calendar.YEAR, 1988);
        g1.setDatum(d);
        g1.setHeimteam(t1);
        g1.setGastteam("THW Kiel");
        g1.setBeendet(false);
        dbh.addSpiel(g1);

        Game g2 = new Game();
        d.set(Calendar.DAY_OF_MONTH, 20);
        d.set(Calendar.MONTH, 11);
        d.set(Calendar.YEAR, 1991);
        g2.setDatum(d);
        g2.setHeimteam(t1);
        g2.setGastteam("ESV Dresden");
        g2.setBeendet(false);
        dbh.addSpiel(g2);

        Game g3 = new Game();
        d.set(Calendar.DAY_OF_MONTH, 14);
        d.set(Calendar.MONTH, 4);
        d.set(Calendar.YEAR, 2016);
        g3.setDatum(d);
        g3.setHeimteam(t1);
        g3.setGastteam("Kielce");
        g3.setBeendet(false);
        dbh.addSpiel(g3);
    }

    public void createTestStats(){
        List<Game> games = dbh.getAllGames();
        List<Player> players = dbh.getAllPlayers();
        List<Action> actions = dbh.getAllActiveActions();
        for(int i = 0; i<= 200; i++){
            int zufallszahlGame = (int)(Math.random() * games.size());
            int zufallszahlAction = (int)(Math.random() * actions.size());
            int zufallszahlSpieler = (int)(Math.random() * players.size());
            dbh.addStatistik(new ActionMapping(games.get(zufallszahlGame), players.get(zufallszahlSpieler), actions.get(zufallszahlAction)));
            dbh.updatePlayerInGame(players.get(zufallszahlSpieler), games.get(zufallszahlGame), players.get(zufallszahlSpieler).getNummmer());
        }
    }

    public void ereignisseEinspielen(){
        int i = dbh.deleteAllActions();
        Log.d("Test", "Anzahl gelöschter Ereignisse: " + i);

        Action e = new Action("Tor", "Tor geworfen");
        e.setBild(hight.ht.sportstatistik.R.drawable.goal_icon);
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("Fehlwurf");
        e.setBeschreibung("Wurfversucht ohne Torerfolg");
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("Assist");
        e.setBeschreibung("Torerfolg vorbereitet");
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("Technischer Fehler");
        e.setBeschreibung("Ballverlust wegen technischem Fehler");
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("Block");
        e.setBeschreibung("Gegnerischen Wurf geblockt");
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("Gelbe Karte");
        e.setBeschreibung("Gelbe Karte erhalten");
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("2 Minuten");
        e.setBeschreibung("2 Minuten Zeitstrafe erhalten");
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("Rote Karte");
        e.setBeschreibung("Rote Karte erhalten");
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("Gehalten");
        e.setBeschreibung("Gegnerischen Wurf pariert");
        e.setActive(true);
        dbh.addEreignis(e);

        e.setName("Gegentor");
        e.setBeschreibung("Einen Torwurf kassiert");
        e.setActive(true);
        dbh.addEreignis(e);
    }
}
