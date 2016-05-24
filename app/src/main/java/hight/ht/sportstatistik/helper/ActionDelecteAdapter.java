package hight.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.datahandling.Action;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.List;

/**
 * Created by Hendrik on 03.09.2015.
 */
public class ActionDelecteAdapter extends ActionInGameAdapter {

    DatabaseHelper dbh;
    ActionDeleteAdapterCallback callback;
    public ActionDelecteAdapter(Context context, int resource, List<Action> objects) {
        super(context, resource, objects);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.grid_item_ereignis, null);
            convertView.setLayoutParams(new GridView.LayoutParams(200, 200));
        }

        ImageButton button = (ImageButton) convertView.findViewById(R.id.gridButton);
        button.setBackgroundResource(R.drawable.delete_action_background);

        try{
            Drawable icon = getContext().getResources().getDrawable(new IconDrawableFetcher(getItem(position).getName()).fetchId());
            button.setImageDrawable(icon);
        }catch(Exception e){
            button.setImageResource(R.drawable.default_icon);
        }

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
