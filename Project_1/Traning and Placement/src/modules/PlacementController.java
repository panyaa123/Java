    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import placementfx.DbHandler;
import placementfx.MainController;
import tables.CoursesTableController;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class PlacementController implements Initializable {
     
  private   ObservableList<ObservableList> data;
    private ResultSet rs;
    @FXML
    private JFXComboBox<String> company;
    @FXML
    private TableView<ObservableList> tabel;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton Close;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try{
               tabel.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 
       Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
       String sql="Select name from company";
       Statement st=con.createStatement();
       ResultSet rs=st.executeQuery(sql);
       while(rs.next())
       {
         company.getItems().addAll(rs.getString(1));
    
       }
            company.getSelectionModel().select("Select Company");
       }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println(ex);
        }
    }    
       
    


    @FXML
    private void ok(ActionEvent event) {
         try{
       Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
       String reg=company.getValue();
         String sql="SELECT id,name,email,phoneno,sem1,sem2,sem3,sem4,sem5,sem6,10th,12th,backlog,department FROM "+reg+" where interview = '1' ";
  
      PreparedStatement ps= con.prepareStatement(sql);
       rs=  ps.executeQuery(sql);
        
       data = FXCollections.observableArrayList();
            tabel.getColumns().clear();
            int cols = rs.getMetaData().getColumnCount();

            for (int i = 0; i < cols; i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
 
                    }
                });
           
                col.setPrefWidth(130);
                tabel.getColumns().addAll(col);
            }
                
            while (rs.next()) {
                
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                    row.add(rs.getString(k));
            
                }
                data.add(row);
                
            }
          tabel.setItems(data);
        } 
        catch ( ClassNotFoundException | SQLException ex) {
              Logger.getLogger(CoursesTableController.class.getName()).log(Level.SEVERE, null, ex);
         
               }
}

    @FXML
    private void print(ActionEvent event) throws FileNotFoundException, IOException {
          HSSFWorkbook workbook =new HSSFWorkbook();
        HSSFSheet spreadsheet =workbook.createSheet("sample");
        HSSFRow row=spreadsheet.createRow(0);
        for(int j=0;j<tabel.getColumns().size();j++)
        {
            row.createCell(j).setCellValue(tabel.getColumns().get(j).getText());
            System.out.println(tabel.getColumns().get(j).getText());
        }
        for(int i=0;i<tabel.getItems().size();i++)
        {
            row=spreadsheet.createRow(i+1);
            for(int j=0;j<tabel.getColumns().size();j++)
            {
                if(tabel.getColumns().get(j).getCellData(i)!=null)
                {
                    row.createCell(j).setCellValue(tabel.getColumns().get(j).getCellData(i).toString());
                    System.out.println(tabel.getColumns().get(j).getCellData(i).toString());
                }
                else
                {
                    row.createCell(j).setCellValue("");
                }
            }
        }
        FileOutputStream fileout = new FileOutputStream("SelectedStudents.xls");
        workbook.write(fileout);
        fileout.close();
        System.out.println("helloo");
    }


   
    @FXML
    private void getSelectedRow(ActionEvent event) {
                try
{
        Connection con;
         Class.forName("com.mysql.jdbc.Driver");
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
        ObservableList<ObservableList> row , allRows;
                            allRows = tabel.getItems();
                            row = tabel.getSelectionModel().getSelectedItems(); 
                            
                            ObservableList <Integer> indices  = tabel.getSelectionModel().getSelectedIndices();                            
                            
                            for(Integer p : indices)
                            {
                                //JOptionPane.showMessageDialog(null, p.intValue());
                                int myindex = p.intValue();
                                String myrow = tabel.getItems().get(myindex).toString();
                                int i = myrow.indexOf(',');
                                String id = myrow.substring(1, i);
                                String sql="update "+company.getValue()+" set selected='1' where id='"+id+"'";
                                Statement St= con.createStatement();
                                int n= St.executeUpdate(sql);
                                System.out.println(n);
                                St.close();                                
                            }
                            con.close();
                            
 }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
           
    }

    @FXML
    private void Close(ActionEvent event) {
           Stage stage= (Stage) Close.getScene().getWindow();
     //  stage.setScene(new Scene((Parent)loader.load()));
       stage.close();
    }

    }

   
 



