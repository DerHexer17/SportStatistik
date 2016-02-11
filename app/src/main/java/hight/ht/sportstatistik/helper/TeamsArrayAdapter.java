package hight.ht.sportstatistik.helper;

import android.content.Context;
import hight.ht.sportstatistik.datahandling.Team;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by HT on 24.03.2015.
 */
public class TeamsArrayAdapter extends ArrayAdapter {

    List<Team> teams;

    public TeamsArrayAdapter(Context context, int resource, List<Team> teams) {
        super(context, resource, teams);
        this.teams = teams;
    }


}
