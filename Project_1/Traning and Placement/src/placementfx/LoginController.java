/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package placementfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class LoginController implements Initializable {

    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXTextField userid;
    @FXML
    private JFXPasswordField password;
    @FXML
    private AnchorPane anchorpane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void doLogin(ActionEvent event) throws IOException,ClassNotFoundException,SQLException {
      String a=userid.getText();
       String c="";
        String d="";
        String b=password.getText();
          if(userid.getText().isEmpty())
                      {
                      showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter userid");
                      return;
                       }
                         if(password.getText().isEmpty())
                       {
                          showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter password");
                         return;  
                       }
        try{
        Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
       String sql="Select * from user";
       Statement st=con.createStatement();
       ResultSet rs=st.executeQuery(sql);
       while(rs.next())
       {
        c=rs.getString(1);
        d=rs.getString(2);
       } 
       if(a.equals(c) && b.equals(d))
        {
            
        btnLogin.getScene().getWindow().hide();
        Parent root=FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage mainStage=new Stage();
        Scene scene=new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
     }
        else
        {
            showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","invalid user & password");
                         return;       
                         
        }
      
        rs.close();
        st.close();
              con.close();}
        catch(Exception ex)
        {
            System.out.println(ex);
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
