package android.ht.sportstatistik.datahandling;

/**
 * Created by HT on 18.03.2015.
 */
public class Action {

    private String name;
    private String beschreibung;
    private int bild;
    private TypeOfSport typeOfSport;
    private boolean active;
    private int id;

    public Action(){

    }

    public Action(String name, String beschreibung){
        this.name=name;
        this.beschreibung=beschreibung;
    }

    public TypeOfSport getTypeOfSport() {
        return typeOfSport;
    }

    public void setTypeOfSport(TypeOfSport typeOfSport) {
        this.typeOfSport = typeOfSport;
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
