package hight.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.datahandling.Player;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heth on 30.03.2015.
 */
public class PlayerForTeamAdapter extends PlayerAdapter {
    List<Player> selectedPlayer;

    public PlayerForTeamAdapter(Context context, int resource, List<Player> objects) {
        super(context, resource, objects);
        selectedPlayer = new ArrayList<Player>();
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
                    selectedPlayer.add(getItem(position));
                    Log.d("addSpieler", "position: "+position);
                    Log.d("addSpieler", "Player ID: "+getItem(position).getId());
                } else {
                    selectedPlayer.remove(getItem(position));
                }

                Toast toast = Toast.makeText(getContext(), "CheckBox gedrückt, aktueller size selectexSpieler: "+ selectedPlayer.size(), Toast.LENGTH_SHORT);
            }
        });


        return convertView;
    }

    public List<Player> getSelectedPlayer(){
        return selectedPlayer;
    }

    public void addSelectedSpieler(Player s){
        selectedPlayer.add(s);
    }

}
