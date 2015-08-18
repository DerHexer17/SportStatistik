package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.activities.TeamActivity;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Team;
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

    public TeamAdapter(Context context, int resource, List<Team> teams) {
        super(context, resource, teams);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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


        return convertView;
    }
}
