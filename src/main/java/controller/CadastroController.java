package controller;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Users;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {

    @FXML
    TextField textID;
    @FXML
    TextField textName;
    @FXML
    TextField textAge;
    @FXML
    TextField textLogin;
    @FXML
    TextField textPassword;
    @FXML
    TableView<Users> usersTable;
    @FXML
    TableColumn<Users, String> columnName;
    @FXML
    TableColumn<Users, Integer> columnAge;
    @FXML
    TableColumn<Users, String> columnID;
    @FXML
    Button btnIniciar;

    public void save(ActionEvent event){}
    public void retrieve(ActionEvent event) throws SQLException {
        if(!textID.getText().isEmpty()){
            int id = Integer.parseInt(textID.getText().toString());
            UsersDAO usersDAO = new UsersDAO();
            Users user = usersDAO.select(id);
            textName.setText(user.getName());
        }else{
            System.out.println("Error");
        }

    }
    public void cancel(ActionEvent event){
        //clear all fields
        textName.setText("");
        textID.setText("");
        textAge.setText("");
        textLogin.setText("");
        textPassword.setText("");
    }
    public void delete(ActionEvent event){}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        try {
            usersTable.setItems(usersList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private ObservableList<Users> usersList() throws SQLException {
        return FXCollections.observableArrayList(new UsersDAO().listAll());
    }
}
