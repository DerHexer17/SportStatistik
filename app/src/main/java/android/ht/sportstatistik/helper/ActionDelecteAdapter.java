package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Ereignis;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Hendrik on 03.09.2015.
 */
public class ActionDelecteAdapter extends EreignisAdapter {

    DatabaseHelper dbh;
    ActionDeleteAdapterCallback callback;
    public ActionDelecteAdapter(Context context, int resource, List<Ereignis> objects) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.grid_item_ereignis, null);
        }

        Button button = (Button) convertView.findViewById(R.id.gridButton);

        button.setText("- "+getItem(position).getName());
        //button.setBackground(getContext().getDrawable(R.drawable.drawer_shadow));
        button.setTextColor(Color.RED);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deleteAction(position);
            }
        });



        return convertView;
    }

    public void setCallback(ActionDeleteAdapterCallback callback){

        this.callback = callback;
    }
    public interface ActionDeleteAdapterCallback{
        public void deleteAction(int position);
    }
}
