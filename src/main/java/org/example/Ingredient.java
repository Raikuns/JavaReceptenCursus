package org.example;

public class Ingredient {
    private final String naam;
    private final double aantal;
    private final String eenheid;

    // We splitten hier de lijst van ingredienten.
    // Omdat we een veld terug krijgen met de volgende volgorde:
    // 50 g Suiker
    // Ben ik genoodzaakt om de string te splitten zodat we deze kunnen opslaan in de juist columnem in de database.
    public Ingredient(String data) {
        String[] dataArray = data.split(" ");

        this.aantal = Double.parseDouble(dataArray[0]);
        this.eenheid = dataArray[1];
        this.naam = dataArray[2];
    }

    public Ingredient(double aantal, String eenheid, String naam) {
        this.aantal = aantal;
        this.eenheid = eenheid;
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public double getAantal() {
        return aantal;
    }

    public String getGewichtseenheid() {
        return eenheid;
    }
}
