package hight.ht.sportstatistik.datahandling;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HT on 21.08.2015.
 */
public class PlayerToActionMapping {

    private Player player;
    private Map<String, Integer> ereignisse;

    public PlayerToActionMapping(){
        ereignisse = new HashMap<String, Integer>();
    }

    public Map<String, Integer> getEreignisse() {
        return ereignisse;
    }

    public void setEreignisse(Map<String, Integer> ereignisse) {
        this.ereignisse = ereignisse;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
