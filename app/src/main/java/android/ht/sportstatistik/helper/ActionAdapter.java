package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.activities.SpielActivity;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Ereignis;
import android.ht.sportstatistik.datahandling.Spiel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hendrik on 03.09.2015.
 */
public class ActionAdapter extends ArrayAdapter<Ereignis> {
    DatabaseHelper dbh;
    ActionAdapterCallback callback;

    public ActionAdapter(Context context, int resource, List<Ereignis> objects) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_actions, null);
        }

        ImageButton icon = (ImageButton) convertView.findViewById(R.id.icon);
        try{
            icon.setImageResource(getItem(position).getBild());
        }catch(Exception e){
            icon.setImageResource(R.drawable.default_icon);

        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
        txtTitle.setText(getItem(position).getName());

        final Switch actionSwitch = (Switch) convertView.findViewById(R.id.actionActive);

        if(getItem(position).isActive()){
            actionSwitch.setChecked(true);
            txtTitle.setTextColor(Color.BLACK);
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
            actionSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionSwitch.setChecked(true);
                    callback.changeActionStatus(position, actionSwitch.isChecked());
                }
            });
        }


        /*
        actionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                callback.changeActionStatus(position, isChecked);

            }
        });*/

        return convertView;
    }

    public void setCallback(ActionAdapterCallback callback){
        this.callback = callback;
    }

    public interface ActionAdapterCallback{
        public void changeActionStatus(int position, boolean isChecked);
    }
}
