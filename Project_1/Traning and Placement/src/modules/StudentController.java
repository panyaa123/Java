/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * FXML Controller class
 *
 * @author admin
 */
public class StudentController implements Initializable {

    @FXML
    private TextField second;
    @FXML
    private TextField email;
    @FXML
    private TextField contact;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField sixth;
    @FXML
    private TextField first;
    @FXML
    private TextField fifth;
    @FXML
    private TextField twelth;
    @FXML
    private TextField fourth;
    @FXML
    private TextField third;
    @FXML
    private TextField tenth;
    @FXML
    private Button reset;
    @FXML
    private Button register;
    @FXML
    private TextField backlog;
   
    @FXML
    private JFXComboBox<String> ComboBox;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Button Close;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
   ComboBox.getItems().addAll("Mechanical","Civil","Pharmacy","Computer","Information Technology","Electrical","Electronices","Plastic&Polymer");    
    
    }  

    @FXML
    private void register(ActionEvent event) throws IOException {
           int w = 0;
       String i =id.getText();
       String n=name.getText();
       String e= email.getText();    
       String p =contact.getText();
       String  sem1 =first.getText();
       String sem2 =second.getText();
       String sem3=third.getText();
       String  sem4 =fourth.getText();
       String sem5 =fifth.getText();
       String sem6 =sixth.getText();
       String  back =backlog.getText();  
       String ten =tenth.getText();
       String twelthth =twelth.getText();
       String db= ComboBox.getValue();
       Connection con ;
       if(id.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter Id of student");
           return;
       }
        if(name.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter Name of student");
           return;
       }
         if(email.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter Email-Id of student");
           return;
       }
          if(contact.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter Contact of student");
           return;
       }
        if(first.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter SEM1 marks");
           return;
       } 
         if(second.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter SEM2 marks");
           return;
       }
          if(third.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter SEM4 marks");
           return;
       }
           if(third.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter SEM3 marks");
           return;
       }
          
        if(fourth.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter SEM4 marks");
           return;
       }
         if(fifth.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter SEM5 marks");
           return;
       }
         if(backlog.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter numbers of backlog");
           return;
       }
         if(tenth.getText().isEmpty())
       {
           showAlert(Alert.AlertType.ERROR,anchorpane.getScene().getWindow(),"form error","please enter 10th marks");
           return;
       }
              
             
          
       try
       { Class.forName("com.mysql.jdbc.Driver");
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
           String sql ="Insert into stud (id,name,email,phoneno,sem1,sem2,sem3,sem4,sem5,sem6,10th,12th,backlog,department)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         PreparedStatement ps= con.prepareStatement(sql);
           ps.setString(1,i);
           ps.setString(2,n);
           ps.setString(3,e);
           ps.setString(4,p);
           ps.setString(5,sem1);
           ps.setString(6,sem2);
           ps.setString(7,sem3);
           ps.setString(8,sem4);
           ps.setString(9,sem5);
           ps.setString(10,sem6);
           ps.setString(11,ten);
           ps.setString(12,twelthth);
           ps.setString(13,back);
         ps.setString(14,db);
     
           w = ps.executeUpdate();
           
       }
       catch(Exception ex) 
       {
          System.out.println(ex);
       }
     if(w==1)
     {
  //     FXMLLoader loader =new FXMLLoader(getClass().getResource("main.fxml"));  
       Stage stage= (Stage) register.getScene().getWindow();
     //  stage.setScene(new Scene((Parent)loader.load()));
       stage.close();
     }
    }

    @FXML
    private void reset(ActionEvent event) {
                        
        id.clear();
        name.clear();
        email.clear();
        contact.clear();
        first.clear();
        second.clear();
        third.clear();
        fourth.clear();
        fifth.clear();
        sixth.clear();
        tenth.clear();
        twelth.clear();
        backlog.clear();
     //   reset.getScene().getWindow().hide();
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
       Alert alert =new Alert(alertType);
       alert.setTitle(title);
       alert.setHeaderText(null);
       alert.setContentText(message);
       alert.initOwner(owner);
       alert.show();
    }

    @FXML
    private void Close(ActionEvent event) {
           Stage stage= (Stage) Close.getScene().getWindow();
     //  stage.setScene(new Scene((Parent)loader.load()));
       stage.close();
    }

   

}
