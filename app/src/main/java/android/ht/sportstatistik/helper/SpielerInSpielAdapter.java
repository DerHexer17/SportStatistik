package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.EreignisZuordnung;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.SpielerEreignisZuordnung;
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
public class SpielerInSpielAdapter extends ArrayAdapter<SpielerEreignisZuordnung> {

    DatabaseHelper dbh;
    SpielerInSpielAdapterCallback callback;

    public SpielerInSpielAdapter(Context context, int resource, List<SpielerEreignisZuordnung> objects) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_spiele_spieler, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
        TextView txtEreignisse = (TextView) convertView.findViewById(R.id.spielEreignisse);

        txtTitle.setText(getItem(position).getSpieler().getVorname().substring(0, 1)+". "+getItem(position).getSpieler().getNachname()+
            " ("+getItem(position).getSpieler().getNummmer()+")");

        txtEreignisse.setText("");
        for(Map.Entry<String, Integer> e : getItem(position).getEreignisse().entrySet()){
            txtEreignisse.setText(txtEreignisse.getText()+" | "+e.getKey()+": "+e.getValue());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.neuesEreignisPopUp(position);
            }
        });

        convertView.setLongClickable(true);
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callback.deleteActionPopUp(position);
                return true;
            }
        });
        /*convertView.setContentDescription(String.valueOf(getItem(position).getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SpielActivity.class);
                intent.putExtra("spielId", Integer.parseInt((String) v.getContentDescription()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });*/


        return convertView;
    }

    public void setCallback(SpielerInSpielAdapterCallback callback){

        this.callback = callback;
    }
    public interface SpielerInSpielAdapterCallback{
        public void neuesEreignisPopUp(int position);
        public void deleteActionPopUp(int position);
    }
}
