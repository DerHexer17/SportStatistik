package hight.ht.sportstatistik.helper;

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
                result = hight.ht.sportstatistik.R.drawable.goal_icon;
                break;
            case "Gelbe Karte":
                result = hight.ht.sportstatistik.R.drawable.yellow_card_icon;
                break;
            case "Rote Karte":
                result = hight.ht.sportstatistik.R.drawable.red_card_icon;
                break;
            case "Fehlwurf":
                result = hight.ht.sportstatistik.R.drawable.no_goal_icon;
                break;
            case "Assist":
                result = hight.ht.sportstatistik.R.drawable.assist_icon;
                break;
            case "Technischer Fehler":
                result = hight.ht.sportstatistik.R.drawable.technical_error_icon;
                break;
            case "2 Minuten":
                result = hight.ht.sportstatistik.R.drawable.two_minutes_icon;
                break;
            case "Gehalten":
                result = hight.ht.sportstatistik.R.drawable.goalie_defended_icon;
                break;
            case "Block":
                result = hight.ht.sportstatistik.R.drawable.block_icon;
                break;
            case "Gegentor":
                result = hight.ht.sportstatistik.R.drawable.goalie_not_defended_icon;
                break;
            default:
                result = hight.ht.sportstatistik.R.drawable.default_icon;
        }

        return result;
    }
}
