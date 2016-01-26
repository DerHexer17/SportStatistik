package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Spiel;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.SpielerEreignisZuordnung;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by heth on 25.01.2016.
 */
public class SpielerInSpielUpdateAdapter extends ArrayAdapter<Spieler> {

    DatabaseHelper dbh;
    SpielerInSpielAdapterCallback callback;
    Spiel spiel;

    public SpielerInSpielUpdateAdapter(Context context, int resource, List<Spieler> objects, Spiel spiel) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
        this.spiel = spiel;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_update_spieler_in_spiel, null);
        }

        final TextView txtName = (TextView) convertView.findViewById(R.id.name);
        final EditText editNumber = (EditText) convertView.findViewById(R.id.number);
        txtName.setText(getItem(position).getVorname().substring(0, 1)+". "+getItem(position).getNachname());
        editNumber.setText(String.valueOf(getItem(position).getNummmer()));

        final Switch actionSwitch = (Switch) convertView.findViewById(R.id.switchUpdatePlayer);

        if(dbh.isPlayerInGame(getItem(position), spiel)){
            actionSwitch.setChecked(true);
            txtName.setTextColor(Color.BLACK);
            editNumber.setTextColor(Color.BLACK);
            actionSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionSwitch.setChecked(false);
                    txtName.setTextColor(Color.GRAY);
                    callback.changeSwitchStatus(position, false, Integer.parseInt(String.valueOf(editNumber.getText())));
                }
            });
        }else{
            actionSwitch.setChecked(false);
            txtName.setTextColor(Color.GRAY);
            editNumber.setTextColor(Color.GRAY);
            actionSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionSwitch.setChecked(true);
                    txtName.setTextColor(Color.BLACK);
                    callback.changeSwitchStatus(position, true, Integer.parseInt(String.valueOf(editNumber.getText())));
                }
            });
        }


        /*
        if(getItem(position).isActive()){
            actionSwitch.setChecked(true);
            txtTitle.setTextColor(Color.BLACK);
            icon.setVisibility(View.VISIBLE);
            actionSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionSwitch.setChecked(false);
                    callback.changeActionStatus(position, actionSwitch.isChecked());
                }
            });
        }else{
            actionSwitch.setChecked(false);
            txtTitle.setTextColor(Color.GRAY);
            icon.setVisibility(View.INVISIBLE);
            actionSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionSwitch.setChecked(true);
                    callback.changeActionStatus(position, actionSwitch.isChecked());
                }
            });
        }*/



        /*
        Hier müssen die Callbacks noch definiert werden


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
        public void changeSwitchStatus(int position, boolean status, int number);
    }
}
