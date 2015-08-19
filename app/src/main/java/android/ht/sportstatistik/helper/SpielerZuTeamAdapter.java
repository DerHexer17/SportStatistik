package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.Spieler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heth on 30.03.2015.
 */
public class SpielerZuTeamAdapter extends SpielerAdapter {
    List<Spieler> selectedSpieler;

    public SpielerZuTeamAdapter(Context context, int resource, List<Spieler> objects) {
        super(context, resource, objects);
        selectedSpieler = new ArrayList<Spieler>();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_checkbox, null);
        }

        CheckBox cb = (CheckBox) convertView.findViewById(R.id.cbSpieler);
        cb.setText(getItem(position).getVorname()+" "+
                getItem(position).getNachname()+" ("+
                getItem(position).getNummmer()+")");

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedSpieler.add(getItem(position));
                    Log.d("addSpieler", "position: "+position);
                    Log.d("addSpieler", "Spieler ID: "+getItem(position).getId());
                } else {
                    selectedSpieler.remove(getItem(position));
                }

                Toast toast = Toast.makeText(getContext(), "CheckBox gedrückt, aktueller size selectexSpieler: "+selectedSpieler.size(), Toast.LENGTH_SHORT);
            }
        });


        return convertView;
    }

    public List<Spieler> getSelectedSpieler(){
        return selectedSpieler;
    }

    public void addSelectedSpieler(Spieler s){
        selectedSpieler.add(s);
    }

}
