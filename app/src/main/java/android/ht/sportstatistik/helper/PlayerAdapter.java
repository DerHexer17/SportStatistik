package android.ht.sportstatistik.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.activities.PlayerActivity;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Player;
import android.ht.sportstatistik.datahandling.Team;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
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


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /*if (v == null) {
            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item_movie_medium, null);
        }*/
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_player, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
        TextView txtMannschaft = (TextView) convertView.findViewById(R.id.spielerListTeam);
        TextView number = (TextView) convertView.findViewById(R.id.numberKit);

        if(dbh.getAllTeamsFromPlayer(getItem(position).getId()).size() == 0){
            txtMannschaft.setText("Ohne Mannschaft");
            number.setBackground(null);
        }else if(dbh.getAllTeamsFromPlayer(getItem(position).getId()).size() == 1){
            txtMannschaft.setText(dbh.getAllTeamsFromPlayer(getItem(position).getId()).get(0).getLang_name());
            KitDrawableFetcher kitFetcher = new KitDrawableFetcher(dbh.getAllTeamsFromPlayer(getItem(position).getId()).get(0));
            try{
                if(!getItem(position).isTorwart()){
                    number.setBackground(getContext().getResources().getDrawable(kitFetcher.fetchDrawableId(kitFetcher.getTeam().getColor())));
                    if(kitFetcher.getTeam().getColor().equals("black")){
                        number.setTextColor(Color.WHITE);
                    }else{
                        number.setTextColor(Color.BLACK);
                    }

                }else{

                    number.setBackground(getContext().getResources().getDrawable(kitFetcher.fetchDrawableId(kitFetcher.getTeam().getGoalieColor())));
                    if(kitFetcher.getTeam().getGoalieColor().equals("black")){
                        number.setTextColor(Color.WHITE);
                    }else {
                        number.setTextColor(Color.BLACK);
                    }
                }
            }catch(Exception e){
                Log.d("Kit", "Trikotprobleme mit Spieler "+getItem(position).toString());
            }

        }else{
            String textMehrereMannschaften = "";
            for(Team t : dbh.getAllTeamsFromPlayer(getItem(position).getId())){
                textMehrereMannschaften = textMehrereMannschaften+" | "+t.getKurz_name();
            }
            txtMannschaft.setText(textMehrereMannschaften);
            KitDrawableFetcher kitFetcher = new KitDrawableFetcher(dbh.getAllTeamsFromPlayer(getItem(position).getId()).get(0));
            try{
                if(!getItem(position).isTorwart()){
                    number.setBackground(getContext().getResources().getDrawable(kitFetcher.fetchDrawableId(kitFetcher.getTeam().getColor())));
                    if(kitFetcher.getTeam().getColor().equals("black")){
                        number.setTextColor(Color.WHITE);
                    }else {
                        number.setTextColor(Color.BLACK);
                    }
                }else{
                    number.setBackground(getContext().getResources().getDrawable(kitFetcher.fetchDrawableId(kitFetcher.getTeam().getGoalieColor())));
                    if(kitFetcher.getTeam().getGoalieColor().equals("black")){
                        number.setTextColor(Color.WHITE);
                    }else {
                        number.setTextColor(Color.BLACK);
                    }
                }
            }catch(Exception e){
                Log.d("Kit", "Trikotprobleme mit Spieler "+getItem(position).toString());
            }
            if(kitFetcher.getTeam().getColor().equals("black")){
                number.setTextColor(Color.WHITE);
            }
        }


        number.setText(String.valueOf(getItem(position).getNummmer()));


        txtTitle.setText(getItem(position).getVorname() + " " +
                getItem(position).getNachname() );



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.showSpinner();
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
        public void showSpinner();
    }

}
