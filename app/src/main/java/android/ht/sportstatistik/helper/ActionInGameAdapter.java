package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Ereignis;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.List;

/**
 * Created by Hendrik on 19.08.2015.
 */
public class ActionInGameAdapter extends ArrayAdapter<Ereignis> {

    DatabaseHelper dbh;
    EreignisAdapterCallback callback;

    public ActionInGameAdapter(Context context, int resource, List<Ereignis> objects) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.grid_item_ereignis, null);
            convertView.setLayoutParams(new GridView.LayoutParams(300, 200));
        }

        ImageButton button = (ImageButton) convertView.findViewById(R.id.gridButton);
        button.setBackgroundResource(R.drawable.add_action_background);

        try{

            Drawable icon = getContext().getResources().getDrawable(new IconDrawableFetcher(getItem(position).getName()).fetchId());
            button.setImageDrawable(icon);
        }catch(Exception e){
            button.setImageResource(R.drawable.default_icon);
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.addAction(position);
            }
        });

        /*
        convertView.setContentDescription(String.valueOf(getItem(position).getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TeamActivity.class);
                intent.putExtra("teamId", Integer.parseInt((String) v.getContentDescription()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });*/


        return convertView;
    }

    public void setCallback(EreignisAdapterCallback callback){

        this.callback = callback;
    }
    public interface EreignisAdapterCallback{
        public void addAction(int position);
    }
}
