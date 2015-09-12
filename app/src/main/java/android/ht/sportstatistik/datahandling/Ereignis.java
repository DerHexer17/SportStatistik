package android.ht.sportstatistik.datahandling;

/**
 * Created by HT on 18.03.2015.
 */
public class Ereignis {

    private String name;
    private String beschreibung;
    private int bild;
    private Sportart sportart;
    private boolean active;
    private int id;

    public Ereignis(){

    }

    public Ereignis(String name, String beschreibung){
        this.name=name;
        this.beschreibung=beschreibung;
    }

    public Sportart getSportart() {
        return sportart;
    }

    public void setSportart(Sportart sportart) {
        this.sportart = sportart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getBild() {
        return bild;
    }

    public void setBild(int bild) {
        this.bild = bild;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
