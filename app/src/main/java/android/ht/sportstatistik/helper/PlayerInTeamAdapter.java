package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.Player;
import android.ht.sportstatistik.datahandling.Team;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by heth on 18.08.2015.
 */
public class PlayerInTeamAdapter extends PlayerAdapter {
    private Team team;
    private SpielerInTeamAdapterCallback callback;

    public PlayerInTeamAdapter(Context context, int resource, List<Player> player) {
        super(context, resource, player);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /*if (v == null) {
            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item_movie_medium, null);
        }*/
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_player_in_team, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
        Button entfernen = (Button) convertView.findViewById(R.id.buttonSpielerInTeamEntfernen);
        entfernen.setContentDescription(String.valueOf(getItem(position).getId()));

        txtTitle.setText(getItem(position).getVorname()+" "+
                getItem(position).getNachname()+" ("+
                getItem(position).getNummmer()+")");

        entfernen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.spielerRemove(position);

            }
        });







        return convertView;
    }

    public void setCallback(SpielerInTeamAdapterCallback callback){

        this.callback = callback;
    }
    public interface SpielerInTeamAdapterCallback{
        public void spielerRemove(int position);
    }
}
