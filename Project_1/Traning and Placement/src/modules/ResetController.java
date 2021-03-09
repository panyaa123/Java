/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ResetController implements Initializable {

    @FXML
    private JFXTextField oldpass;
    @FXML
    private JFXTextField newpass;
    @FXML
    private JFXTextField newpass1;
    @FXML
    private Button Close;
    @FXML
    private Button submit;
    @FXML
    private AnchorPane anchorpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Close(ActionEvent event) {
           Stage stage= (Stage) Close.getScene().getWindow();
     //  stage.setScene(new Scene((Parent)loader.load()));
       stage.close();
    }

    @FXML
    private void submit(ActionEvent event) throws ClassNotFoundException, SQLException {
        String a= oldpass.getText();
        String b= newpass.getText();
        String c=newpass1.getText();
        if(oldpass.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter old password");
           return;
       }
        if(newpass.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter new password");
           return;
       }
        if(newpass1.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","Password Doesn't Match!");
           return;
       }
        if(!newpass.getText().equals(newpass1.getText()))
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","Password doesn't match re-enter");
           return;
       }
        else
        {
          Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
       String sql="update user set password = '"+newpass.getText()+"' where user = 'admin'"; 
       PreparedStatement st=con.prepareStatement(sql);
            int n= st.executeUpdate();
       if(n==1)
       {
            
           Stage stage= (Stage) submit.getScene().getWindow();
     //  stage.setScene(new Scene((Parent)loader.load()));
       stage.close();
        }
        }
    }
     private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
       Alert alert =new Alert(alertType);
       alert.setTitle(title);
       alert.setHeaderText(null);
       alert.setContentText(message);
       alert.initOwner(owner);
       alert.show();
    }
}
