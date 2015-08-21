package android.ht.sportstatistik.datahandling;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HT on 21.08.2015.
 */
public class SpielerEreignisZuordnung {

    private Spieler spieler;
    private Map<String, Integer> ereignisse;

    public SpielerEreignisZuordnung(){
        ereignisse = new HashMap<String, Integer>();
    }

    public Map<String, Integer> getEreignisse() {
        return ereignisse;
    }

    public void setEreignisse(Map<String, Integer> ereignisse) {
        this.ereignisse = ereignisse;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }
}
