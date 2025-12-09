package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_NAME = "banco";

    public static void executeSqlScript(String filepath) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

            stmt.close();
            conn.close();

            conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();

            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            StringBuilder sqlStatement = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(";")) {
                    stmt.execute(sqlStatement.toString());
                    sqlStatement = new StringBuilder();
                }
            }
            reader.close();

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        executeSqlScript("schema.sql");
    }
}
