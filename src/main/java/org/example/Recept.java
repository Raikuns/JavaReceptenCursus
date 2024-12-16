package org.example;

public class Recept {
    private String receptNaam;
    private String korteBeschrijving;
    private String bereidingstijd;
    private String keuken;
    private String moeilijkheid;
    private String allergieOptie;
    private Ingredient[] ingredienten;
    private String bereidingswijze;

    public Recept(
            String receptNaam,
            String korteBeschrijving,
            String bereidingstijd,
            String moeilijkheid,
            String keuken,
            String allergieOptie,
            String bereidingswijze) {
        this.receptNaam = receptNaam;
        this.korteBeschrijving = korteBeschrijving;
        this.bereidingstijd = bereidingstijd;
        this.moeilijkheid = moeilijkheid;
        this.keuken = keuken;
        this.allergieOptie = allergieOptie;
        this.bereidingswijze = bereidingswijze;
    }

    public String getReceptNaam() {
        return receptNaam;
    }

    public void setReceptNaam(String receptNaam) {
        this.receptNaam = receptNaam;
    }

    public String getKorteBeschrijving() {
        return korteBeschrijving;
    }

    public void setKorteBeschrijving(String korteBeschrijving) {
        this.korteBeschrijving = korteBeschrijving;
    }

    public String getBereidingstijd() {
        return bereidingstijd;
    }

    public void setBereidingstijd(String bereidingstijd) {
        this.bereidingstijd = bereidingstijd;
    }

    public String getMoeilijkheid() {
        return moeilijkheid;
    }

    public void setMoeilijkheid(String moeilijkheid) {
        this.moeilijkheid = moeilijkheid;
    }

    public String getKeuken() {
        return keuken;
    }

    public void setKeuken(String keuken) {
        this.keuken = keuken;
    }

    public String getAllergieOptie() {
        return allergieOptie;
    }

    public void setAllergieOptie(String allergieOptie) {
        this.allergieOptie = allergieOptie;
    }

    public Ingredient[] getIngredienten() {
        return ingredienten;
    }

    public void setIngredienten(Ingredient[] ingredienten) {
        this.ingredienten = ingredienten;
    }

    public String getBereidingswijze() {
        return bereidingswijze;
    }

    public void setBereidingswijze(String bereidingswijze) {
        this.bereidingswijze = bereidingswijze;
    }

}
