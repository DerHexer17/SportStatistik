package android.ht.sportstatistik.helper;

import android.ht.sportstatistik.R;

/**
 * Created by Hendrik on 14.09.2015.
 */
public class IconDrawableFetcher {

    String action;

    public IconDrawableFetcher(String action){
        this.action = action;
    }

    public int fetchId(){
        int result = 0;

        switch(this.action){
            case "Tor":
                result = R.drawable.goal_icon;
                break;
            case "Gelbe Karte":
                result = R.drawable.yellow_card_icon;
                break;
            case "Rote Karte":
                result = R.drawable.red_card_icon;
                break;
            case "Fehlwurf":
                result = R.drawable.no_goal_icon;
                break;
            case "Assist":
                result = R.drawable.assist_icon;
                break;
            case "Technischer Fehler":
                result = R.drawable.technical_error_icon;
                break;
            case "2 Minuten":
                result = R.drawable.two_minutes_icon;
                break;
            case "Gehalten":
                result = R.drawable.goalie_defended_icon;
                break;
            case "Block":
                result = R.drawable.block_icon;
                break;
            case "Gegentor":
                result = R.drawable.goalie_not_defended_icon;
                break;
            default:
                result = R.drawable.default_icon;
        }

        return result;
    }
}
