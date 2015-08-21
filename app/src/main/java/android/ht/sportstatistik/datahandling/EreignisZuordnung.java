package android.ht.sportstatistik.datahandling;

/**
 * Created by HT on 21.08.2015.
 */
public class EreignisZuordnung {
    private Spiel spiel;
    private Spieler spieler;
    private Ereignis ereignis;

    public EreignisZuordnung(Spiel s, Spieler sp, Ereignis e){
        this.spiel = s;
        this.spieler = sp;
        this.ereignis = e;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    public Ereignis getEreignis() {
        return ereignis;
    }

    public void setEreignis(Ereignis ereignis) {
        this.ereignis = ereignis;
    }
}
