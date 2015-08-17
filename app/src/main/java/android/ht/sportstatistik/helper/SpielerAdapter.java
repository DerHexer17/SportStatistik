package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
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
     DatabaseHelper dbh;
    boolean complex;
     public SpielerAdapter(Context context, List<Spieler> spieler, boolean complex){
          this.context = context;
          this.spieler = spieler;
          dbh = DatabaseHelper.getInstance(context);
          this.complex=complex;
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
                if(complex == true) {
                    LayoutInflater mInflater = (LayoutInflater)
                            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = mInflater.inflate(R.layout.list_item_spieler, null);

                    TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
                    TextView txtMannschaft = (TextView) convertView.findViewById(R.id.spielerListTeam);

                    txtTitle.setText(spieler.get(position).getVorname()+" "+
                            spieler.get(position).getNachname()+" ("+
                            spieler.get(position).getNummmer()+")");
                    //txtTitle.setText(spiele.get(position).getNachname());
                    if(dbh.getAllTeamsFromPlayer(spieler.get(position).getId()).size() == 0){
                        txtMannschaft.setText("Ohne Mannschaft");
                    }else if(dbh.getAllTeamsFromPlayer(spieler.get(position).getId()).size() == 1){
                        txtMannschaft.setText(dbh.getAllTeamsFromPlayer(spieler.get(position).getId()).get(0).getLang_name());
                    }else{
                        txtMannschaft.setText("Mehrere Mannschaften");
                    }
                }else{
                    LayoutInflater mInflater = (LayoutInflater)
                            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = mInflater.inflate(R.layout.list_item, null);

                    TextView txtTitle = (TextView) convertView.findViewById(R.id.label);


                    txtTitle.setText(spieler.get(position).getVorname()+" "+
                            spieler.get(position).getNachname()+" ("+
                            spieler.get(position).getNummmer()+")");
                    //txtTitle.setText(spiele.get(position).getNachname());

                }
            }





            return convertView;
        }

}
