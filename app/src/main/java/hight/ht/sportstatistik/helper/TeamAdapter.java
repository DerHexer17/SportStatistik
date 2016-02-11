package hight.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.activities.TeamActivity;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Team;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hendrik on 17.08.2015.
 */
public class TeamAdapter extends ArrayAdapter<Team> {

    DatabaseHelper dbh;
    TeamAdapterCallback callback;

    public TeamAdapter(Context context, int resource, List<Team> teams) {
        super(context, resource, teams);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_team, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);

        txtTitle.setText(getItem(position).getLang_name());

        convertView.setContentDescription(String.valueOf(getItem(position).getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TeamActivity.class);
                intent.putExtra("teamId", Integer.parseInt((String) v.getContentDescription()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callback.deleteTeam(getItem(position).getId(), position);
                return true;
            }
        });


        return convertView;
    }

    public void setCallback(TeamAdapterCallback callback){
        this.callback = callback;
    }

    public interface TeamAdapterCallback{
        public void deleteTeam(int teamId, int position);
    }
}
