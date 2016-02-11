package hight.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.PlayerToActionMapping;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Hendrik on 19.08.2015.
 */
public class PlayerInGameAdapter extends ArrayAdapter<PlayerToActionMapping> {

    DatabaseHelper dbh;
    SpielerInSpielAdapterCallback callback;
    Boolean finished;

    public PlayerInGameAdapter(Context context, int resource, List<PlayerToActionMapping> objects) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_games_player, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
        TextView txtEreignisse = (TextView) convertView.findViewById(R.id.spielEreignisse);

        txtTitle.setText(getItem(position).getPlayer().getVorname().substring(0, 1)+". "+getItem(position).getPlayer().getNachname()+
            " ("+getItem(position).getPlayer().getNummmer()+")");

        txtEreignisse.setText("");
        for(Map.Entry<String, Integer> e : getItem(position).getEreignisse().entrySet()){
            txtEreignisse.setText(txtEreignisse.getText()+" | "+e.getKey()+": "+e.getValue());
        }

        if(!finished){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.neuesEreignisPopUp(position);
                }
            });
        }

        if(!finished) {
            convertView.setLongClickable(true);
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callback.deleteActionPopUp(position);
                    return true;
                }
            });
        }
        /*convertView.setContentDescription(String.valueOf(getItem(position).getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GameActivity.class);
                intent.putExtra("spielId", Integer.parseInt((String) v.getContentDescription()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });*/


        return convertView;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public void setCallback(SpielerInSpielAdapterCallback callback){

        this.callback = callback;
    }
    public interface SpielerInSpielAdapterCallback{
        public void neuesEreignisPopUp(int position);
        public void deleteActionPopUp(int position);
    }
}
