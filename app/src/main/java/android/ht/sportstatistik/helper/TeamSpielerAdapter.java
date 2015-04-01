package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.Spieler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heth on 30.03.2015.
 */
public class TeamSpielerAdapter extends SpielerAdapter {
    List<Spieler> selectedSpieler;

    public TeamSpielerAdapter(Context context, List<Spieler> spieler) {
        super(context, spieler);
        selectedSpieler = new ArrayList<Spieler>();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_checkbox, null);
        }

        CheckBox cb = (CheckBox) convertView.findViewById(R.id.cbSpieler);
        cb.setText(spieler.get(position).getVorname()+" "+
                spieler.get(position).getNachname()+" ("+
                spieler.get(position).getNummmer()+")");

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedSpieler.add(spieler.get(position));
                } else {
                    selectedSpieler.remove(spieler.get(position));
                }

            }
        });

        return convertView;
    }

    public List<Spieler> getSelectedSpieler(){
        return selectedSpieler;
    }

}
