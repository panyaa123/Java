
package modules;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import placementfx.MainController;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class CompanyController implements Initializable {

    @FXML
    private MaterialDesignIconView iconClose;
    @FXML
    private JFXButton btnViewTrainingCompanies;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtContact;
    @FXML
    private JFXTextField txtWebsite;
    @FXML
    private JFXButton btnSaveTrainingCompany;
    private Connection conn;


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       

    }

    @FXML
    private void closeStage(MouseEvent event) {
        iconClose.getScene().getWindow().hide();
    }

    @FXML
    private void viewTraingCompanies(ActionEvent event) throws IOException {
        //Dim stage
        Region veil = new Region();
        veil.setPrefSize(1100, 650);
        veil.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
        btnSaveTrainingCompany.getScene().getWindow().hide();
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/tables/HiringsTable.fxml"));
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (MainController.mainRootPane != null) {
                    MainController.mainRootPane.getChildren().add(veil);
                }
            } else if (MainController.mainRootPane.getChildren().contains(veil)) {
                MainController.mainRootPane.getChildren().remove(veil);
            }

        });

        newStage.show();
    }

    @FXML
    private void saveTraining(ActionEvent event) throws SQLException {
         Connection con ;
         String n=txtName.getText();
         String a=txtAddress.getText();
         String c=txtContact.getText();
         String w=txtWebsite.getText();
         
                 
         
       try
       { Class.forName("com.mysql.jdbc.Driver");
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
        String query = "INSERT INTO company(name,address,contact,website) "
                + "VALUES (?,?,?,?)";
        
        PreparedStatement ps= con.prepareStatement(query);
        ps.setString(1, txtName.getText().toUpperCase());
        ps.setString(2, txtAddress.getText());
        ps.setString(3, txtContact.getText());
        ps.setString(4, txtWebsite.getText().toLowerCase());
        if (ps.executeUpdate() == 1) {
            System.out.println("Saved successfully");
            txtAddress.setText("");
            txtContact.setText("");
            txtName.setText("");
            txtWebsite.setText("");
        }
        String query1=" create table "+n +
"    (" +
"    id varchar(10) primary key," +
"    name varchar(50) not null," +
"    email varchar(40) not null," +
"    phoneno varchar(10) unique key," +
"    sem1 varchar(5)," +
"    sem2 varchar(5) ," +
"    sem3 varchar(5) ," +
"    sem4 varchar(5)," +
"    sem5 varchar(5)," +
"    sem6 varchar(5)," +
"    10th varchar(5)," +
"    12th varchar(5)," +
"    backlog varchar(5)," +
"    department varchar(50)," +
"    applicable nvarchar(50)," +
"    interview nvarchar(50)," +
"    selected nvarchar(50)" +
"    );";
                                         
           PreparedStatement ps1= con.prepareStatement(query1);
           ps1.executeUpdate();
           
        
       }
       catch(Exception ex)
       {
           System.out.println(ex);
       }
    }

}
