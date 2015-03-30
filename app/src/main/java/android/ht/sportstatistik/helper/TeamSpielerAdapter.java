package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.Spieler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by heth on 30.03.2015.
 */
public class TeamSpielerAdapter extends SpielerAdapter {
    public TeamSpielerAdapter(Context context, List<Spieler> spieler) {
        super(context, spieler);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_checkbox, null);
        }


        TextView txtTitle = (TextView) convertView.findViewById(R.id.labelSpieler);

        txtTitle.setText(spieler.get(position).getVorname()+" "+
                spieler.get(position).getNachname()+" ("+
                spieler.get(position).getNummmer()+")");
        //txtTitle.setText(spiele.get(position).getNachname());

        return convertView;
    }


}
