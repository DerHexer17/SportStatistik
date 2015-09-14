package android.ht.sportstatistik.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.ht.sportstatistik.R;
import android.ht.sportstatistik.activities.SpielActivity;
import android.ht.sportstatistik.activities.TeamActivity;
import android.ht.sportstatistik.datahandling.DatabaseHelper;
import android.ht.sportstatistik.datahandling.Spiel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hendrik on 19.08.2015.
 */
public class SpielAdapter extends ArrayAdapter<Spiel> {

    DatabaseHelper dbh;


    public SpielAdapter(Context context, int resource, List<Spiel> spiele) {
        super(context, resource, spiele);
        dbh = DatabaseHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_spiele, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.label);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        Calendar temp = getItem(position).getDatum();
        String datum = null;
        try{
            datum = temp.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.SHORT, Locale.GERMANY)+"."+
                    temp.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.GERMANY)+"."+
                    temp.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.GERMANY);
        }catch (Exception e){
            e.printStackTrace();
            datum = "Kein Datum gespeichert";
        }


        txtTitle.setText(getItem(position).getHeimteam().getLang_name()+" - "+
                getItem(position).getGastteam()+" ("+datum+")");

        final Spiel spiel = getItem(position);
        convertView.setContentDescription(String.valueOf(getItem(position).getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SpielActivity.class);
                intent.putExtra("spielId", spiel.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}
