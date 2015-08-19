package android.ht.sportstatistik.datahandling;

/**
 * Created by HT on 18.03.2015.
 */
public class Ereignis {

    private String name;
    private String beschreibung;
    private String bild;
    private Sportart sportart;
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

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
