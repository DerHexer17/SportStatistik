package android.ht.sportstatistik.helper;

import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.Team;

/**
 * Created by heth on 09.02.2016.
 */
public class KitDrawableFetcher {

    Team team;

    public KitDrawableFetcher(Team t){
        this.team = t;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int fetchDrawableId(String color){
        int i = 0;

        switch(color){
            case "red":
                i = R.drawable.kit_red;
                break;
            case "light_blue":
                i = R.drawable.kit_light_blue;
                break;
            case "black":
                i = R.drawable.kit_black;
                break;
        }

        return i;
    }
}
