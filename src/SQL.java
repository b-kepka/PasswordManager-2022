import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SQL {
    Connection con;
    String url = "jdbc:sqlite:bases/";
    String fileName = "menadzer.db";
    ArrayList<String[]> values;
    public SQL() throws SQLException {
        con = null;
        con = DriverManager.getConnection(url+fileName);
        createTable();
    }
    private void createTable() {
        String sql = "CREATE TABLE if not exists Passwords (" +
                "id integer PRIMARY KEY," +
                "login text not null," +
                "password text not null," +
                "url text" +
                ");";
        try(Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addPassword(String login, String password, String url) {
        String sql = "INSERT INTO Passwords(login, password, url)" +
                "VALUES(?, ?, ?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, url);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteData(String login, String password) {
        String sql = "DELETE FROM Passwords WHERE login = ? AND password = ?;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getPasswordsByLogin(String login) {
        String sql = "SELECT * " +
                "FROM Passwords " +
                "WHERE login like ?;";
        values = new ArrayList<String[]>();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,login);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String valid = Integer.toString(rs.getInt("id"));
                String vallogin = rs.getString("login");
                String vallpassword = rs.getString("password");
                String valurl = rs.getString("url");
                values.add(new String[]{valid, vallogin, vallpassword, valurl});
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void getPasswordsByAdres(String adres) {
        String sql = "SELECT * " +
                "FROM Passwords " +
                "WHERE url like ?;";
        values = new ArrayList<String[]>();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,adres);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String valid = Integer.toString(rs.getInt("id"));
                String vallogin = rs.getString("login");
                String vallpassword = rs.getString("password");
                String valurl = rs.getString("url");
                values.add(new String[]{valid, vallogin, vallpassword, valurl});
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
