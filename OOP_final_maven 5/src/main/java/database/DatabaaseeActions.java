package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import uni.Organisation;
import users.Student;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface DatabaaseeActions {


    static void saveUser(String userName, String password, String name, String userType) {
        String query =  "INSERT INTO users (name,user_type,username,password) VALUES (?, ?, ?, ?)";



        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Заполнить значения в PreparedStatement
            statement.setString(1, name);
            statement.setString(2, userType);
            statement.setString(3, userName);
            statement.setString(4, password);

            statement.executeUpdate();
            System.out.println("Entity saved to table: users");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getPlaceholders(int count) {
        return String.join(", ", "?".repeat(count).split(""));
    }




    public static void save(String table, Object value) {
        table = table.toLowerCase();
        String query = buildSaveQuery(table, value);

        if (query == null) {
            throw new IllegalArgumentException("Unsupported object type: " + value.getClass().getSimpleName());
        }

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Заполнить значения в PreparedStatement
            fillSaveStatement(statement, value);

            statement.executeUpdate();
            System.out.println("Entity saved to table: " + table);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String buildSaveQuery(String table, Object value) {


        if (value instanceof Student) {
            return "INSERT INTO " + table + " (id, name, email) VALUES (?, ?, ?)";

        } else if (value instanceof Organisation) {
            return "INSERT INTO " + table + " (id, name, address) VALUES (?, ?, ?)";
        }
        // Добавляйте другие типы объектов здесь
        return null;
    }
    private static void fillSaveStatement(PreparedStatement statement, Object value) throws SQLException {
        try {
            if (value instanceof Student student) {
                statement.setInt(1, student.getId());
                statement.setString(2, student.getName());
                statement.setString(3, student.getEmail());
            } else if (value instanceof Organisation organization) {
                statement.setInt(1, organization.getId());
                statement.setString(2, organization.getName());
                statement.setString(3, organization.getAddress());
            }
            // Добавляйте другие типы объектов здесь
        } catch (Exception e) {
            throw new SQLException("Failed to map object fields to query parameters.", e);
        }
    }



    // Save an entity to the table
    static void save(String table,Object value,int a) {
//        String table = getTableName(value.getClass());
        if (table == null) {
            throw new IllegalArgumentException("No table mapping found for class: " + value.getClass().getSimpleName());
        }

        // Get fields and values using reflection
        Field[] fields = value.getClass().getDeclaredFields();
        String columnNames = getColumnNames(fields);
        String placeholders = getPlaceholders(fields.length);

        String query = "INSERT INTO " + table + " (" + columnNames + ") VALUES (" + placeholders + ")";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true); // Allow access to private fields
                statement.setObject(i + 1, fields[i].get(value));
            }
            statement.executeUpdate();
            System.out.println("Entity saved to table: " + table);

        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    static void save(String table, Object values) {



//    }

    // Find an entity by its ID
    static ResultSet findById(String table, int id) {
        String query = "SELECT * FROM ? WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,table);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
//                System.out.println("Entity found in table: " + table + ", ID: " + id);
                return resultSet;
            } else {
//                System.out.println("No entity found in table: " + table + ", ID: " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // Update an existing entity
    static void update(String table, String columnName, Object value, int id) {
        String query = "UPDATE " + table + " SET " + columnName + " = ? WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, value);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Entity updated in table: " + table + ", ID: " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void update(String table,Object value) {
//        String query = "UPDATE " + table + " SET " + columnName + " = ? WHERE id = ?";
//        try (Connection connection = connect();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setObject(1, value);
//            statement.setInt(2, id);
//            statement.executeUpdate();
//            System.out.println("Entity updated in table: " + table + ", ID: " + id);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    // Delete an entity by its ID
    static void delete(String table, int id) {
        String query = "DELETE FROM ? WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,table);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();            System.out.println("Entity deleted from table: " + table + ", ID: " + id);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    // Retrieve all entities from the table
    static List<ResultSet> findAll(String table) {
        String query = "SELECT * FROM ?" ;
        List<ResultSet> results = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {


            statement.setString(1,table);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                results.add(resultSet);
            }
//            System.out.println("All entities retrieved from table: " + table);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }




    // Connection Pool
    class ConnectionPool {
        private static final HikariDataSource dataSource;

        static {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/university");
            config.setUsername("postgres");
            config.setPassword("!234");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(20000);
            config.setMaxLifetime(1800000);
            dataSource = new HikariDataSource(config);
        }

        public static DataSource getDataSource() {
            return dataSource;
        }
    }

    // Establish a connection using the pool
    static Connection connect() {
        try {
            return ConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            System.err.println("Failed to obtain connection: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }




}
