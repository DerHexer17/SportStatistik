package android.ht.sportstatistik.datahandling;

/**
 * Created by HT on 18.03.2015.
 */
public class Spieler {

    private String vorname;
    private String nachname;
    private int nummmer;

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
