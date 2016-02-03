package android.ht.sportstatistik.datahandling;

/**
 * Created by HT on 18.03.2015.
 */
public class Player {

    private int id;
    private String vorname;
    private String nachname;
    private int nummmer;
    private boolean torwart;

    public Player(String vorname, String nachname, int nummer, boolean torwart){
        this.vorname=vorname;
        this.nachname=nachname;
        this.nummmer=nummer;
        this.torwart=torwart;
    }

    public Player(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public boolean isTorwart() {
        return torwart;
    }

    public void setTorwart(boolean torwart) {
        this.torwart = torwart;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getNummmer() {
        return nummmer;
    }

    public void setNummmer(int nummmer) {
        this.nummmer = nummmer;
    }
}
