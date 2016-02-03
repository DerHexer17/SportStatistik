package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Game;
import android.ht.sportstatistik.datahandling.Player;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by heth on 25.01.2016.
 */
public class SpielerInSpielUpdateAdapter extends ArrayAdapter<Player> {

    DatabaseHelper dbh;
    SpielerInSpielAdapterCallback callback;
    Game game;

    public SpielerInSpielUpdateAdapter(Context context, int resource, List<Player> objects, Game game) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
        this.game = game;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_update_spieler_in_spiel, null);
        }

        final TextView txtName = (TextView) convertView.findViewById(R.id.name);
        final TextView txtNumber = (TextView) convertView.findViewById(R.id.number);
        final Button minus = (Button) convertView.findViewById(R.id.minus);
        final Button plus = (Button) convertView.findViewById(R.id.plus);
        txtName.setText(getItem(position).getVorname().substring(0, 1)+". "+getItem(position).getNachname());
        if(dbh.isPlayerInGame(getItem(position), game) >= 0) {
            txtNumber.setText(String.valueOf(dbh.isPlayerInGame(getItem(position), game)));
        }else{
            txtNumber.setText(String.valueOf(getItem(position).getNummmer()));
        }

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt((String) txtNumber.getText()) >= 1 & dbh.isPlayerInGame(getItem(position), game) >= 0){
                    txtNumber.setText(String.valueOf(Integer.parseInt((String) txtNumber.getText()) - 1));
                    dbh.updatePlayerInGame(getItem(position), game, Integer.parseInt((String) txtNumber.getText()));
                }

            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbh.isPlayerInGame(getItem(position), game) >=0){
                    txtNumber.setText(String.valueOf(Integer.parseInt((String) txtNumber.getText()) + 1));
                    dbh.updatePlayerInGame(getItem(position), game, Integer.parseInt((String) txtNumber.getText()));
                }

            }
        });

        final Switch actionSwitch = (Switch) convertView.findViewById(R.id.switchUpdatePlayer);

        if(dbh.isPlayerInGame(getItem(position), game) >= 0){
            actionSwitch.setChecked(true);
            txtName.setTextColor(Color.BLACK);
            txtNumber.setTextColor(Color.BLACK);
            actionSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionSwitch.setChecked(false);
                    txtName.setTextColor(Color.GRAY);
                    callback.changeSwitchStatus(position, false, Integer.parseInt(String.valueOf(txtNumber.getText())));
                }
            });
        }else{
            actionSwitch.setChecked(false);
            txtName.setTextColor(Color.GRAY);
            txtNumber.setTextColor(Color.GRAY);
            actionSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionSwitch.setChecked(true);
                    txtName.setTextColor(Color.BLACK);
                    callback.changeSwitchStatus(position, true, Integer.parseInt(String.valueOf(txtNumber.getText())));
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
                Intent intent = new Intent(getContext(), GameActivity.class);
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
