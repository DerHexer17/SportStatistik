package hight.ht.sportstatistik.datahandling;

import java.util.Calendar;

/**
 * Created by HT on 18.03.2015.
 */
public class Game {

    private int id;
    private Team heimteam;
    private String gastteam;
    private Calendar datum;
    private boolean beendet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getHeimteam() {
        return heimteam;
    }

    public void setHeimteam(Team heimteam) {
        this.heimteam = heimteam;
    }

    public String getGastteam() {
        return gastteam;
    }

    public void setGastteam(String gastteam) {
        this.gastteam = gastteam;
    }

    public Calendar getDatum() {
        return datum;
    }

    public void setDatum(Calendar datum) {
        this.datum = datum;
    }

    public Boolean isBeendet() {
        return beendet;
    }

    public void setBeendet(Boolean beendet) {
        this.beendet = beendet;
    }


}
