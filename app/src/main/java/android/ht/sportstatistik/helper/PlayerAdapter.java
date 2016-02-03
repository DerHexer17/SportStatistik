package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.activities.PlayerActivity;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Player;
import android.ht.sportstatistik.datahandling.Team;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by heth on 30.03.2015.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {


     DatabaseHelper dbh;
    SpielerAdapterCallback callback;

     /*public PlayerAdapter(Context context, List<Player> spieler){
          this.context = context;
          this.spieler = spieler;
          dbh = DatabaseHelper.getInstance(context);
          this.complex=complex;
        }*/

    public PlayerAdapter(Context context, int resource, List<Player> player) {
        super(context, resource, player);
        dbh=DatabaseHelper.getInstance(context);
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
            convertView = mInflater.inflate(R.layout.list_item_spieler, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
        TextView txtMannschaft = (TextView) convertView.findViewById(R.id.spielerListTeam);

        txtTitle.setText(getItem(position).getVorname() + " " +
                getItem(position).getNachname() + " (" +
                getItem(position).getNummmer() + ")");
        if(dbh.getAllTeamsFromPlayer(getItem(position).getId()).size() == 0){
            txtMannschaft.setText("Ohne Mannschaft");
        }else if(dbh.getAllTeamsFromPlayer(getItem(position).getId()).size() == 1){
            txtMannschaft.setText(dbh.getAllTeamsFromPlayer(getItem(position).getId()).get(0).getLang_name());
        }else{
            String textMehrereMannschaften = "";
            for(Team t : dbh.getAllTeamsFromPlayer(getItem(position).getId())){
                textMehrereMannschaften = textMehrereMannschaften+" | "+t.getKurz_name();
            }
            txtMannschaft.setText(textMehrereMannschaften);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlayerActivity.class);
                Player p = getItem(position);
                intent.putExtra("player", p.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callback.deletePlayer(getItem(position).getId(), position);
                return true;
            }
        });




        return convertView;
        }

    public void setCallback(SpielerAdapterCallback callback){
        this.callback = callback;
    }

    public interface SpielerAdapterCallback{
        public void deletePlayer(int playerId, int position);
    }

}
