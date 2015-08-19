package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Spieler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hendrik on 19.08.2015.
 */
public class SpielerInSpielAdapter extends SpielerAdapter {

    DatabaseHelper dbh;
    SpielerInSpielAdapterCallback callback;

    public SpielerInSpielAdapter(Context context, int resource, List<Spieler> spieler) {
        super(context, resource, spieler);
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

        txtTitle.setText(getItem(position).getVorname().substring(0,1)+". "+getItem(position).getNachname()+
            " ("+getItem(position).getNummmer()+")");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.neuesEreignisPopUp(position);
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
    }
}
