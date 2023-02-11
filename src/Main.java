import java.io.*;
import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/Task6";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static final String FR = "INSERT INTO questions (questions) " +
            "VALUES (?)";

    public static void main(String[] args) {

        registerDriver();

        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(FR);

            statement.addBatch("INSERT INTO test(name, age, phone) VALUES ('Alan', 18, '+380562354125')");
            statement.addBatch("INSERT INTO test(name, age, phone) VALUES ('Bob', 19, '+380562354126')");
            statement.addBatch("INSERT INTO test(name, age, phone) VALUES ('Mary', 20, '+380562354127')");

            statement.executeBatch();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String age = resultSet.getString("age");
                String phone = resultSet.getString("phone");
//
                System.out.println(id + " " + name + " " + age + " " + phone);
            }

            FileReader flrdr = new FileReader("D:\\programs\\IDE\\JDBC Hibernate\\hwjdbc\\hw1\\TestTable\\questions.txt");
            BufferedReader brdr = new BufferedReader(flrdr);
            String que = null;
            while ((que = brdr.readLine()) != null) {
                preparedStatement.setString(1, que);
                System.out.println(que);
                preparedStatement.execute();
            }

            flrdr.close();
            brdr.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
                statement.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}