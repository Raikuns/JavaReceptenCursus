package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    // Hier gebeuren wat leuke dingen om connectie te maken met de database.. thanks AI
    public static Connection connectToDatabase() {
        String DB_URL = "jdbc:mysql://localhost:3306/recepten_database";
        String USER = "root";
        String PASSWORD = "Beer";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();

            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Kan geen connectie maken met de database!");
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public static void OpslaanVanRecepten(
            String titel,
            String beschrijving,
            String keuken,
            String bereidingstijd,
            String moeilijkheid,
            String bereidingswijze,
            String ingredienten,
            String allergieOptie
    ) throws SQLException {
        String sqlQuery = "INSERT INTO recepten (recept_naam, recept_beschrijving, recept_keuken, recept_bereidingtijd, recept_moeilijkheid,recept_bereidingswijze) VALUES (?,?,?,?,?,?)";
        Connection connection = connectToDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, titel);
            statement.setString(2, beschrijving);
            statement.setString(3, keuken);
            statement.setString(4, bereidingstijd);
            statement.setString(5, moeilijkheid);
            statement.setString(6, bereidingswijze);

            statement.executeUpdate();

            try (ResultSet currentReceptId = statement.getGeneratedKeys()) {
                if (currentReceptId.next()) {
                    int receptId = currentReceptId.getInt(1);
                    ingredientenKoppelen(receptId, ingredienten, connection);

                    if (!allergieOptie.equals("niks")) {
                        allergieKoppelen(receptId, allergieOptie, connection);
                    }

                    System.out.println("Inserted record ID: " + receptId);
                } else {
                    throw new SQLException("Inserting recepten failed, no ID obtained.");
                }
            }
        }
    }

    private static void allergieKoppelen(int receptId, String allergieOptie, Connection connection) throws SQLException {
        // Hier voegen we de allergie opties toe aan de allergie tabel en de koppel tabel
        String allergieQuery = "INSERT INTO allergienopties (allergieoptie_naam) VALUES (?)";
        String allergieKoppelQuery = "INSERT INTO allergieaanrecept (recept_id, allergieoptie_id) VALUES (?,?)";

        // We voegen de allergieOptie toe aan allergie tabel
        try (PreparedStatement allergieOptieStatement = connection.prepareStatement(allergieQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement allergieKoppelStatement = connection.prepareStatement(allergieKoppelQuery)
        ) {
            allergieOptieStatement.setString(1, allergieOptie);
            allergieOptieStatement.executeUpdate();

            // Hier koppelen we de AllergieOptie aan receptId aanvankelijk dat we de allergieId hebben
            try (ResultSet allergieId = allergieOptieStatement.getGeneratedKeys()) {
                while (allergieId.next()) {
                    allergieKoppelStatement.setInt(1, receptId);
                    allergieKoppelStatement.setInt(2, allergieId.getInt(1));
                    allergieKoppelStatement.addBatch();
                }
                allergieKoppelStatement.executeBatch();
            }
        }
    }

    private static void ingredientenKoppelen(int receptId, String ingredienten, Connection connection) throws SQLException {
        // 2 queries die we gebruiken om ingredienten toe te voegen aan de Ingredienten,
        // tabel en de 2e query zorgt dat de ingredientenAanRecept koppel tabel wordt gevuld
        String ingredientSql = "INSERT INTO ingredienten (ingredient_naam) VALUES (?)";
        String ingredientReceptQuery = """
                INSERT INTO ingredientaanrecept (recept_id,ingredient_id,hoeveelheid,gewichtseenheid)
                VALUES (?,?,?,?)""";

        // prepare statement zet de query klaar, en met de RETURN_GENERATED_KEYS krijgen we de ID terug waar hij is ingestopt.
        try (PreparedStatement preparedStatement = connection.prepareStatement(ingredientSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement ingredientQuery = connection.prepareStatement(ingredientReceptQuery)
        ) {
            // split elke regel naar een array
            String[] ingredientArray = ingredienten.split("\n");
            Ingredient[] ingredients = new Ingredient[ingredientArray.length];

            for (int i = 0; i < ingredientArray.length; i++) {
                ingredients[i] = new Ingredient(ingredientArray[i]);
                // wij willen alleen de ingredient naam in de column stoppen.
                preparedStatement.setString(1, ingredients[i].getNaam());
                preparedStatement.addBatch();
            }
            // execute batch is de uitvoering van de query
            preparedStatement.executeBatch();

            int ingredientIndex = 0;
            try (ResultSet ingredientIds = preparedStatement.getGeneratedKeys()) {
                while (ingredientIds.next()) {
                    int ingredientId = ingredientIds.getInt(1);

                    Ingredient ingredient = ingredients[ingredientIndex];

                    // We vullen hier het koppel tabel met receptId, ingredientId, hoeveelheid en gewichtseenheid.
                    ingredientQuery.setInt(1, receptId);
                    ingredientQuery.setInt(2, ingredientId);
                    ingredientQuery.setDouble(3, ingredient.getAantal());
                    ingredientQuery.setString(4, ingredient.getGewichtseenheid());
                    ingredientQuery.addBatch();

                    ingredientIndex++;
                }
                // We voeren de query uit.
                ingredientQuery.executeBatch();
            }
        }
    }

    public static ArrayList<String> ZoekRecepten() throws SQLException {
        String query = """
                SELECT recept_id, CONCAT(UCASE(LEFT(recept_naam, 1)), LCASE(SUBSTRING(recept_naam, 2))) AS recept_naam
                FROM recepten
                ORDER BY recept_id""";

        Connection connection = connectToDatabase();

        PreparedStatement statement = connection.prepareStatement(query);

        // We willen het resultaat in een arraylist stoppen
        ResultSet rs = statement.executeQuery();

        // We voegen elke row die we terug krijgen toe aan de lijst
        ArrayList<String> lijstRecepten = new ArrayList<>();
        while (rs.next()) {
            lijstRecepten.add(rs.getString("recept_naam"));
        }

        connection.close();

        // We geven de lijst terug
        return lijstRecepten;
    }
}
