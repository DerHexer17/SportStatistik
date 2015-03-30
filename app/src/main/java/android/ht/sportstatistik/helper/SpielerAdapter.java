package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.Spieler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by heth on 30.03.2015.
 */
public class SpielerAdapter extends BaseAdapter{

     Context context;
     List<Spieler> spieler;
     public SpielerAdapter(Context context, List<Spieler> spieler){
          this.context = context;
            this.spieler = spieler;
        }
        @Override
        public int getCount() {
            return spieler.size();
        }

        @Override
        public Object getItem(int position) {
            return spieler.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.list_item, null);
            }


            TextView txtTitle = (TextView) convertView.findViewById(R.id.label);

            txtTitle.setText(spieler.get(position).getVorname()+" "+
                    spieler.get(position).getNachname()+" ("+
                    spieler.get(position).getNummmer()+")");
            //txtTitle.setText(spiele.get(position).getNachname());

            return convertView;
        }

}
