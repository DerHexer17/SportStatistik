package android.ht.sportstatistik.datahandling;

import java.util.Date;

/**
 * Created by HT on 18.03.2015.
 */
public class Spiel {

    private int heimteam;
    private String gastteam;
    private Date datum;
    private String beendet;

    public int getHeimteam() {
        return heimteam;
    }

    public void setHeimteam(int heimteam) {
        this.heimteam = heimteam;
    }

    public String getGastteam() {
        return gastteam;
    }

    public void setGastteam(String gastteam) {
        this.gastteam = gastteam;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getBeendet() {
        return beendet;
    }

    public void setBeendet(String beendet) {
        this.beendet = beendet;
    }
}
