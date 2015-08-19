package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Spieler;
import android.ht.sportstatistik.datahandling.Team;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by heth on 30.03.2015.
 */
public class SpielerAdapter extends ArrayAdapter<Spieler> {


     DatabaseHelper dbh;


     /*public SpielerAdapter(Context context, List<Spieler> spieler){
          this.context = context;
          this.spieler = spieler;
          dbh = DatabaseHelper.getInstance(context);
          this.complex=complex;
        }*/

    public SpielerAdapter(Context context, int resource, List<Spieler> spieler) {
        super(context, resource, spieler);
        dbh=DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*if (v == null) {
            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item_movie_medium, null);
        }*/
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_spieler, null);
        }

                    TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
                    TextView txtMannschaft = (TextView) convertView.findViewById(R.id.spielerListTeam);

                    txtTitle.setText(getItem(position).getVorname()+" "+
                            getItem(position).getNachname()+" ("+
                            getItem(position).getNummmer()+")");
                    if(dbh.getAllTeamsFromPlayer(getItem(position).getId()).size() == 0){
                        txtMannschaft.setText("Ohne Mannschaft");
                    }else if(dbh.getAllTeamsFromPlayer(getItem(position).getId()).size() == 1){
                        txtMannschaft.setText(dbh.getAllTeamsFromPlayer(getItem(position).getId()).get(0).getLang_name());
                    }else{
                        String textMehrereMannschaften = "";
                        for(Team t : dbh.getAllTeamsFromPlayer(getItem(position).getId())){
                            textMehrereMannschaften = textMehrereMannschaften+" | "+t.getKurz_name();
                        }
                        txtMannschaft.setText(textMehrereMannschaften);
                    }







            return convertView;
        }

}
