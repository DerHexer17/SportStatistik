package android.ht.sportstatistik.datahandling;

import java.util.Date;

/**
 * Created by HT on 18.03.2015.
 */
public class Spiel {

    private int heimteam_id;
    private String heimteam_titel;

    public int getHeimteam_id() {
        return heimteam_id;
    }

    public void setHeimteam_id(int heimteam_id) {
        this.heimteam_id = heimteam_id;
    }

    public String getHeimteam_titel() {
        return heimteam_titel;
    }

    public void setHeimteam_titel(String heimteam_titel) {
        this.heimteam_titel = heimteam_titel;
    }

    private String gastteam;
    private Date datum;
    private String beendet;


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
