package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.ConnectionManager.ConnectionManager;
import ru.innopolis.stc9.ConnectionManager.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO{
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();

    public boolean addAdmin(Admin admin){
        if(getAdminById(admin.getId()) != null)return false;
        String sqlRequest = "INSERT INTO admins (fio,login,passw) VALUES(?,?,?)";
        int result = 0;
        Connection connection = connectionManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, admin.getFIO());
            statement.setString(2, admin.getLogin());
            statement.setString(3, admin.getPassw());
            result = statement.executeUpdate();
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(result > 0)return true;
        else return false;
    }

    public Admin getAdminById(int id) {
        Admin admin = null;
        String sqlRequest = "SELECT * FROM admins WHERE adm_id = ?";
        Connection connection = connectionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                admin = new Admin(
                        resultSet.getInt("adm_id"),
                        resultSet.getString("fio"),
                        resultSet.getString("login"),
                        resultSet.getString("passw"));
            }
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public boolean updateAdmin(Admin admin){
        String sqlRequest = "UPDATE admins SET fio = ?, login = ?, passw = ? WHERE adm_id = ?";
        int result = 0;
        Connection connection = connectionManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, admin.getFIO());
            statement.setString(2, admin.getLogin());
            statement.setString(3, admin.getPassw());
            statement.setInt(4, admin.getId());
            result = statement.executeUpdate();
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(result > 0)return true;
        else return false;
    }

    public boolean deleteAdminById (int id){
        String sqlRequest = "DELETE FROM admins " +
                "WHERE adm_id = ?";
        int result = 0;
        Connection connection = connectionManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, id);
            result = statement.executeUpdate();
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(result > 0)return true;
        else return false;
    }
}
