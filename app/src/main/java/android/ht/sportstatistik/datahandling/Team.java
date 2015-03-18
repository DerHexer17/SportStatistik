package android.ht.sportstatistik.datahandling;

/**
 * Created by HT on 18.03.2015.
 */
public class Team {

    private String kurz_name;
    private String lang_name;
    private String beschreibung;

    public String getKurz_name() {
        return kurz_name;
    }

    public void setKurz_name(String kurz_name) {
        this.kurz_name = kurz_name;
    }

    public String getLang_name() {
        return lang_name;
    }

    public void setLang_name(String lang_name) {
        this.lang_name = lang_name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
