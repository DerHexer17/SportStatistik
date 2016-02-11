package hight.ht.sportstatistik.datahandling;

/**
 * Created by Hendrik on 19.08.2015.
 */
public class TypeOfSport {

    private int id;
    private String titel;
    private String beschreibung;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
