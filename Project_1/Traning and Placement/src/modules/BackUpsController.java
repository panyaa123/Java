/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXButton;
//import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileInputStream;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;




/**
 * FXML Controller class
 *
 * @author danml
 */
public class BackUpsController implements Initializable {

   
    @FXML
    private JFXButton btnRestore;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label path;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Button cancel;
  
 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    



    @FXML
    private void restoreDB(ActionEvent event) {
          //  PreparedStatement ps = null ;
        FileChooser filechooser =new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Excel files (*.xlsx)","*.xlsx");
        filechooser.setTitle("Select Excel File");
             Stage  stage = (Stage)AnchorPane.getScene().getWindow();
       // filechooser.showOpenDialog(stage);
        filechooser.getExtensionFilters().add(ext); 
        File file =filechooser.showOpenDialog(stage);
         try 
         {
            Class.forName("com.mysql.jdbc.Driver");
       Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");             
             String query ="Insert into stud(id,name,email,phoneno,sem1,sem2,sem3,sem4,sem5,sem6,10th,12th,backlog,department)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";                        
       //   String query="insert into stud(id,name,email,phoneno,sem1)values(?,?,?,?,?)";   
        FileInputStream fileIn = new FileInputStream(file);
             path.setText(file.getPath());
             XSSFWorkbook wb = new XSSFWorkbook(fileIn);                
             XSSFSheet sheet = wb.getSheetAt(0);
             XSSFRow row;  
             PreparedStatement   ps = con.prepareStatement(query);              
            for(int i=1; i<=sheet.getLastRowNum(); i++)
        {                   
               row = sheet.getRow(i); 
               System.out.println(sheet.getLastRowNum());
       
            
               ps.setString(1,row.getCell(0).getStringCellValue());
               System.out.println(row.getCell(0).getStringCellValue());
               
               ps.setString(2,row.getCell(1).getStringCellValue());
               System.out.println(row.getCell(1).getStringCellValue());
      
               ps.setString(3,row.getCell(2).getStringCellValue());
               System.out.println(row.getCell(2).getStringCellValue());
            
               ps.setDouble(4, (double) row.getCell(3).getNumericCellValue());
               System.out.println(row.getCell(3).getNumericCellValue());
             
               ps.setFloat(5, (float) row.getCell(4).getNumericCellValue());
               System.out.println(row.getCell(4).getNumericCellValue());
             
               ps.setFloat(6, (float) row.getCell(5).getNumericCellValue());
               System.out.println(row.getCell(5).getNumericCellValue());
             
               ps.setFloat(7, (float) row.getCell(6).getNumericCellValue());
               System.out.println(row.getCell(6).getNumericCellValue());
             
               ps.setFloat(8, (float) row.getCell(7).getNumericCellValue());
               System.out.println(row.getCell(7).getNumericCellValue());
             
               ps.setFloat(9, (float) row.getCell(8).getNumericCellValue());
               System.out.println(row.getCell(8).getNumericCellValue());
             
               ps.setFloat(10, (float) row.getCell(9).getNumericCellValue());
               System.out.println(row.getCell(9).getNumericCellValue());
             
               ps.setFloat(11, (float) row.getCell(10).getNumericCellValue());
               System.out.println(row.getCell(10).getNumericCellValue());
             
               ps.setFloat(12, (float) row.getCell(11).getNumericCellValue());
               System.out.println(row.getCell(11).getNumericCellValue());
             
               ps.setInt(13, (int) row.getCell(12).getNumericCellValue());
               System.out.println(row.getCell(12).getNumericCellValue());
                
               ps.setString(14,row.getCell(13).getStringCellValue());
               System.out.println(row.getCell(13).getStringCellValue());
             
               ps.executeUpdate();
               System.out.println(query);
               
         }                         
             fileIn.close();
                 ps.close();         
            }   
catch (SQLException | FileNotFoundException e) 
{               
} 
catch ( IOException | ClassNotFoundException ex) 
{               
}   
         catch(Exception a)
         {
             
         }
}    

    @FXML
    private void cancel(ActionEvent event) {
           Stage stage= (Stage) cancel.getScene().getWindow();
     //  stage.setScene(new Scene((Parent)loader.load()));
       stage.close();
    }
}    

