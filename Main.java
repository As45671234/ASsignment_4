import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/simplebd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "01082006As";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Connected to PostgreSQL!");

            // Step 1: Create Table
            createTable(connection);

            // Step 2: Insert Sample Data
            List<Animal> animals = new ArrayList<>();
            animals.add(new Bird("Sparrow", "Bird", 2));
            animals.add(new Mammal("Dog", "Mammal", 3));
            insertAnimals(connection, animals);

            // Step 3: Update an Animal's Age
            updateAnimalAge(connection, "Sparrow", 5);

            // Step 4: Delete an Animal
            deleteAnimal(connection, "Dog");

            // Step 5: Fetch All Animals
            fetchAnimals(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Animal (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "species VARCHAR(100) NOT NULL, " +
                "age INT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("✅ Table 'Animal' is ready!");
        }
    }
    private static void insertAnimals(Connection connection, List<Animal> animals) throws SQLException {
        String sql = "INSERT INTO Animal (name, species, age) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Animal animal : animals) {
                // Check if any value is null
                if (animal.getName() == null || animal.getSpecies() == null) {
                    System.out.println("❌ Skipping insertion: Null values detected.");
                    continue; // Skip inserting null data
                }

                pstmt.setString(1, animal.getName());
                pstmt.setString(2, animal.getSpecies());
                pstmt.setInt(3, animal.getAge());
                pstmt.executeUpdate();
                System.out.println("✔ Inserted: " + animal.getName());
            }
        }
    }


    private static void updateAnimalAge(Connection connection, String name, int newAge) throws SQLException {
        String sql = "UPDATE Animal SET age = ? WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newAge);
            pstmt.setString(2, name);
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("✔ Updated " + name + " to age " + newAge);
        }
    }

    private static void deleteAnimal(Connection connection, String name) throws SQLException {
        String sql = "DELETE FROM Animal WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("✔ Deleted " + name);
        }
    }

    private static void fetchAnimals(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Animal";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Species: " + rs.getString("species") +
                        ", Age: " + rs.getInt("age"));
            }
        }
    }
}
