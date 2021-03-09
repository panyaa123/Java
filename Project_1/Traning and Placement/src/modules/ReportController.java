/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import com.jfoenix.controls.JFXButton;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import tables.CoursesTableController;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class ReportController implements Initializable {
    private   ObservableList<ObservableList> data;
    private ResultSet rs;
    @FXML
    private ComboBox<String> company;
    @FXML
    private CheckBox mech;
    @FXML
    private CheckBox civil;
    @FXML
    private CheckBox electric;
    @FXML
    private CheckBox comp;
    @FXML
    private CheckBox it;
    @FXML
    private CheckBox extc;
    @FXML
    private TableView<ObservableList> tabel;
    @FXML
    private JFXButton print;
    private CheckBox checkBox;
    @FXML
    private CheckBox pharmacy;
    @FXML
    private CheckBox plastic;
    @FXML
    private JFXButton Close;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     try{
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
    private void generate(ActionEvent event) {
          String dept="";
        try{
       Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
    if(mech.isSelected())
       {
      dept=dept+"'Mechanical',";
       }
    if(civil.isSelected())
       {
      dept=dept+"'Civil',";
       }
    if(electric.isSelected())
       {
      dept=dept+"'Electrical',";
       }
    if(extc.isSelected())
       {
      dept=dept+"'Electronices',";
       }
    if(it.isSelected())
       {
      dept=dept+"'Information Technology',";
       }
    if(pharmacy.isSelected())
       {
      dept=dept+"'Pharmacy',";
       }
     if(plastic.isSelected())
       {
      dept=dept+"'Plastic Polymer',";
       }   
     if(comp.isSelected())
       {
      dept=dept+"'Computer',";
       }
      String reg = dept.substring(0, dept.length()-",".length());
 
       String sql="SELECT * FROM stud where department in ("+reg.trim()+")";
  
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
               //    TableColumn select = new TableColumn("CheckBox");
           //    TableColumn<ObservableList,Boolean> select;
          //    select = new TableColumn<>();
            //        TableColumn select =(TableColumn) checkBox.getUserData();
    /*    select.setMinWidth(200);
        select.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<ObservableList, CheckBox> arg0) {
             ObservableList user = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {
                    for(ObservableList table : tabel.getItems())
                    {
                        //     ((CheckBox)  select.getCellData(table).getChildrenUnmodifiable().get(0)).setSelected(new_val);
                     
                        Object cellData = select.getCellData(table);
                    }
                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        }); */
        //    tabel.getColumns().addAll( select);
            
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
        FileOutputStream fileout = new FileOutputStream("ReportGeneration.xls");
        workbook.write(fileout);
        fileout.close();
        System.out.println("helloo");
    }


    @FXML
    private void save(ActionEvent event) {
        String dept="";
          try{
               if(mech.isSelected())
       {
      dept=dept+"'Mechanical',";
       }
    if(civil.isSelected())
       {
      dept=dept+"'Civil',";
       }
    if(electric.isSelected())
       {
      dept=dept+"'Electrical',";
       }
    if(extc.isSelected())
       {
      dept=dept+"'Electronices',";
       }
    if(it.isSelected())
       {
      dept=dept+"'Information Technology',";
       }
    if(pharmacy.isSelected())
       {
      dept=dept+"'Pharmacy',";
       }
     if(plastic.isSelected())
       {
      dept=dept+"'Plastic Polymer',";
       }   
     if(comp.isSelected())
       {
      dept=dept+"'Computer',";
      
       }
      Class.forName("com.mysql.jdbc.Driver");
       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement","root","RUTU");
      String reg = dept.substring(0, dept.length()-",".length());
 
       String sql="SELECT * FROM stud where department in ("+reg.trim()+")";
  
      PreparedStatement ps1= con.prepareStatement(sql);
      ResultSet rs1=  ps1.executeQuery(sql);
    
      while(rs1.next())       
      {
          String id = rs1.getString("id");
          String name = rs1.getString("name");
          String email = rs1.getString("email");
          String phoneno = rs1.getString("phoneno");
          float sem1 = rs1.getFloat("sem1");
           float sem2 = rs1.getFloat("sem2");
            float sem3 = rs1.getFloat("sem3");
             float sem4 = rs1.getFloat("sem4");
              float sem5 = rs1.getFloat("sem5");
               float sem6 = rs1.getFloat("sem6");
                float ten = rs1.getFloat("10th");
                 float twelve = rs1.getFloat("12th");
                 int backlog =rs1.getInt("backlog");
               String department = rs1.getString("department");  
             
               
               String a=company.getValue();
              // System.out.println("id = "+id);
             // String sql1="Insert into TCS values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           String sql1 = "insert into "+a+" values ('"+rs1.getString(1)+"','"+rs1.getString(2)+"','"+rs1.getString(3)+"','"+rs1.getString(4)+"',"+rs1.getFloat(5)+","+rs1.getFloat(6)+","+rs1.getFloat(7)+","+rs1.getFloat(8)+","+rs1.getFloat(9)+","+rs1.getFloat(10)+","+rs1.getFloat(11)+","+rs1.getFloat(12)+","+rs1.getInt(13)+",'"+rs1.getString(14)+"','"+null+"','"+null+"','"+null+"')";
         
             System.out.println(sql1);

            //String sql1 ="insert into "+a+" values ('"+rs1.getString(1)+"','"+rs1.getString(2)+"','"+rs1.getString(3)+"','"+rs1.getString(4)+"',"+rs1.getFloat(5)+","+rs1.getFloat(6)+","+rs1.getFloat(7)+","+rs1.getFloat(8)+","+rs1.getFloat(9)+","+rs1.getFloat(10)+","+rs1.getFloat(11)+","+rs1.getFloat(12)+","+rs1.getInt(13)+",'"+rs1.getString(14)+"','"+rs1.getString(15)+"','"+rs1.getString(16)+"','"+rs1.getString(17)+"')";
          //String sql1 ="insert into "+a+" values ('"+rs1.getString(1)+"','"+rs1.getString(2)+"','"+rs1.getString(3)+"','"+rs1.getString(4)+"',"+rs1.getFloat(5)+","+rs1.getFloat(6)+","+rs1.getFloat(7)+","+rs1.getFloat(8)+","+rs1.getFloat(9)+","+rs1.getFloat(10)+","+rs1.getFloat(11)+","+rs1.getFloat(12)+","+rs1.getInt(13)+",'"+rs1.getString(14)+"',?,?,?)";
                 
            PreparedStatement ps = con.prepareStatement(sql1);
              /* ps.setString(15,null); 
               ps.setString(16,null);
               ps.setString(17,null);*/
              /*
               ps.setString(1,id);
           ps.setString(2,name);
           ps.setString(3,email);
           ps.setString(4, phoneno);
           ps.setFloat(5,sem1);        
        ps.setFloat(6,sem2);
        ps.setFloat(7,sem3);
        ps.setFloat(8,sem4);
        ps.setFloat(9,sem5);
        ps.setFloat(10,sem6);
        ps.setFloat(11,ten);
           ps.setFloat(12,twelve);
           ps.setInt(13,backlog);
          ps.setString(14,department);
              */
           int n = ps.executeUpdate(sql1);
           ps.close();
        System.out.println("finish");
       System.out.println("n = "+n);
      }
      /*
      
       System.out.println("hello");
       //rs1.beforeFirst();
          while(rs1.next())
      {
          System.out.println("Yes");
          String a=company.getValue();
       //String sql1="Insert into "+a+" (id,name,email,phoneno,sem1,sem2,sem3,sem4,sem5,sem6,10th,12th,backlog,department)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
       //String sql1="Insert into "+a+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
       String sql1 = "insert into TCS values ('"+rs1.getString(1)+",'"+rs.getString(2)+",'"+rs.getString(3)+"','"+rs.getString(4)+"',"+rs.getFloat(5)+","+rs.getFloat(6)+","+rs.getFloat(7)+","+rs.getFloat(8)+","+rs.getFloat(9)+","+rs.getFloat(10)+","+rs.getFloat(11)+","+rs.getFloat(12)+","+rs.getInt(13)+",'"+rs.getString(14)+"')";
       System.out.println(sql1);
       PreparedStatement ps;
                   ps = con.prepareStatement(sql1);
        ps.setString(1,rs1.getString(1));
           ps.setString(2,rs1.getString(2));
           ps.setString(3,rs1.getString(3));
           ps.setString(4,rs1.getString(4));
        ps.setFloat(5,rs1.getFloat(5));
        ps.setFloat(6,rs1.getFloat(6));
        ps.setFloat(7,rs1.getFloat(7));
        ps.setFloat(8,rs1.getFloat(8));
        ps.setFloat(9,rs1.getFloat(9));
        ps.setFloat(10,rs1.getFloat(10));
        ps.setFloat(11,rs1.getFloat(11));
           ps.setFloat(12,rs1.getFloat(12));
           ps.setInt(13,rs1.getInt(13));
          ps.setString(14,rs1.getString(14));

        System.out.println(sql1);
                 int n = ps.executeUpdate(sql1);
        System.out.println("finish");
       System.out.println("n = "+n);
       
      }*/
       
          }
          catch(ClassNotFoundException | SQLException ex)
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
