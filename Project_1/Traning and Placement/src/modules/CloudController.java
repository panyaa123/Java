/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import tables.CoursesTableController;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class CloudController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    private Text txtStatus;
    @FXML
    private JFXComboBox<String> combobox;
    @FXML
    private TableView<ObservableList> tableview;
    @FXML
    private Button Close;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try{
             tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);      
       Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
       String sql="Select name from company";
       Statement st=con.createStatement();
       ResultSet rs=st.executeQuery(sql);
       while(rs.next())
       {
         combobox.getItems().addAll(rs.getString(1));
    
       }
            combobox.getSelectionModel().select("Select Company");
       }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println(ex);
        }
      

    }

  
 
    @FXML
    private void ok(ActionEvent event) {
        try
        {
        String reg =combobox.getValue();
         String sql="SELECT id,name,email,phoneno,sem1,sem2,sem3,sem4,sem5,sem6,10th,12th,backlog,department FROM "+reg+" where applicable='1' ";
         Class.forName("com.mysql.jdbc.Driver");
         Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
       Statement st= con.createStatement();
       ResultSet rs=  st.executeQuery(sql);
       ObservableList<ObservableList> data;
            data = FXCollections.observableArrayList();
            tableview.getColumns().clear();
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
                tableview.getColumns().addAll(col);
            }
          
            while (rs.next()) {
                
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int k = 1; k <= rs.getMetaData().getColumnCount(); k++) {
                    row.add(rs.getString(k));
            
                }
                data.add(row);
                
            }
          tableview.setItems(data);
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
        for(int j=0;j<tableview.getColumns().size();j++)
        {
            row.createCell(j).setCellValue(tableview.getColumns().get(j).getText());
            System.out.println(tableview.getColumns().get(j).getText());
        }
        for(int i=0;i<tableview.getItems().size();i++)
        {
            row=spreadsheet.createRow(i+1);
            for(int j=0;j<tableview.getColumns().size();j++)
            {
                if(tableview.getColumns().get(j).getCellData(i)!=null)
                {
                    row.createCell(j).setCellValue(tableview.getColumns().get(j).getCellData(i).toString());
                    System.out.println(tableview.getColumns().get(j).getCellData(i).toString());
                }
                else
                {
                    row.createCell(j).setCellValue("");
                }
            }
        }
        FileOutputStream fileout = new FileOutputStream("Interview.xls");
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
                            allRows = tableview.getItems();
                            row = tableview.getSelectionModel().getSelectedItems(); 
                            
                            ObservableList <Integer> indices  = tableview.getSelectionModel().getSelectedIndices();                            
                            
                            for(Integer p : indices)
                            {
                                //JOptionPane.showMessageDialog(null, p.intValue());
                                int myindex = p.intValue();
                                String myrow = tableview.getItems().get(myindex).toString();
                                int i = myrow.indexOf(',');
                                String id = myrow.substring(1, i);
                                String sql="update "+combobox.getValue()+" set interview='1' where id='"+id+"'";
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
    private void close(ActionEvent event) {
      Stage stage= (Stage) Close.getScene().getWindow();
     //  stage.setScene(new Scene((Parent)loader.load()));
      stage.close();
    }
}
