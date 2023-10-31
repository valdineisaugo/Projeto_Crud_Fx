package dao;

import database.DbConnection;
import model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    static Connection connection;
    public UsersDAO(){
        try {
            connection = DbConnection.getConnectionSqlite();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(Users user) throws SQLException {
        PreparedStatement stmt;
        String save = "insert into users (name, age, login, password) values (?, ?, ?, ?)";
        stmt = connection.prepareStatement(save);
        //Receber dados do parâmetro
        stmt.setString(1, user.getName());
        stmt.setInt(2, user.getAge());
        stmt.setString(3, user.getLogin());
        stmt.setString(4, user.getPassword());
        //Executar a instrução
        stmt.execute();
        connection.close();
    }

    public Users select(int id) throws SQLException {
        PreparedStatement stmt;
        ResultSet rst;
        String search = "select *from users where id = ?";
        stmt = connection.prepareStatement(search);
        stmt.setInt(1, id);
        //Receber resultado da consulta
        rst = stmt.executeQuery();
        Users user = new Users();
        while(rst.next()){
            user.setId(rst.getInt("id"));
            user.setName(rst.getString("name"));
            user.setAge(rst.getInt("age"));
            user.setPassword(rst.getString("password"));
            user.setLogin(rst.getString("login"));
        }
        rst.close();
        connection.close();
        return user;
    }

    public  void update(Users user) throws SQLException {
        PreparedStatement stmt;
        String update = "update users set name = ?, age = ?, login = ?, password = ? where id = ?";
        stmt = connection.prepareStatement(update);
        stmt.setString(1, user.getName());
        stmt.setInt(2, user.getAge());
        stmt.setString(3, user.getLogin());
        stmt.setString(4, user.getPassword());
        stmt.setInt(5, user.getId());
        stmt.close();

    }

    public  List<Users> listAll() throws SQLException {
        connection = DbConnection.getConnectionSqlite();
        Statement stmt;
        ResultSet rst;
        stmt = connection.createStatement();
        rst = stmt.executeQuery("select *from users");
        ArrayList<Users> listUsers = new ArrayList<Users>();
        //Pegar os itens do resultset e inserir na lista
        while(rst.next()){
            Users u = new Users();
            u.setId(rst.getInt("id"));
            u.setName(rst.getString("name"));
            u.setAge(rst.getInt("age"));
            u.setLogin(rst.getString("login"));
            u.setPassword(rst.getString("password"));
            listUsers.add(u);
        }
        rst.close();
        connection.close();
        return listUsers;
    }
    public boolean login(String login, String senha) throws SQLException {
        boolean status = false;
        String loginSQL = "select *from users where login = ? and password = ?";
        PreparedStatement stmt = connection.prepareStatement(loginSQL);
        stmt.setString(1, login);
        stmt.setString(2, senha);
        ResultSet rst = stmt.executeQuery();
        if(rst.next()){
            status = true;
        }
        return status;
    }
}
