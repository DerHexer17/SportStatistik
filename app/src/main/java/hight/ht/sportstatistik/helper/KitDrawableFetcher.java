package hight.ht.sportstatistik.helper;

import hight.ht.sportstatistik.datahandling.Team;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by heth on 09.02.2016.
 */
public class KitDrawableFetcher {

    Team team;
    private Map<String, String> kitColors = new TreeMap<String, String>();

    public KitDrawableFetcher(Team t){
        this.team = t;
        kitColors.put("red", "Rot");
        kitColors.put("light_red", "Hellrot");
        kitColors.put("blue", "Blau");
        kitColors.put("light_blue", "Hellblau");
        kitColors.put("green", "Grün");
        kitColors.put("light_green", "Hellgrün");
        kitColors.put("yellow", "Gelb");
        kitColors.put("white", "Weiß");
        kitColors.put("black", "Schwarz");
        kitColors.put("grey", "Grau");

    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Map<String, String> getKitColors() {
        return kitColors;
    }

    public int fetchDrawableId(String color){
        int i = 0;

        switch(color){
            case "red":
                i = hight.ht.sportstatistik.R.drawable.kit_red;
                break;
            case "light_blue":
                i = hight.ht.sportstatistik.R.drawable.kit_light_blue;
                break;
            case "black":
                i = hight.ht.sportstatistik.R.drawable.kit_black;
                break;
            case "green":
                i = hight.ht.sportstatistik.R.drawable.kit_green;
                break;
            case "grey":
                i = hight.ht.sportstatistik.R.drawable.kit_grey;
                break;
            case "light_green":
                i = hight.ht.sportstatistik.R.drawable.kit_light_green;
                break;
            case "light_red":
                i = hight.ht.sportstatistik.R.drawable.kit_light_red;
                break;
            case "yellow":
                i = hight.ht.sportstatistik.R.drawable.kit_yellow;
                break;
            case "white":
                i = hight.ht.sportstatistik.R.drawable.kit_white;
                break;
            case "blue":
                i = hight.ht.sportstatistik.R.drawable.kit_blue;
                break;
        }

        return i;
    }
}
