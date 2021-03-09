/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package placementfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author danml
 */
public class MainController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane menuAbout;
    @FXML
    private AnchorPane menuCompany;
    @FXML
    private AnchorPane menuCourse;
    @FXML
    private AnchorPane menuStudent;
    @FXML
    private AnchorPane menuSynch;
    @FXML
    private AnchorPane menuTraining;
    @FXML
    private AnchorPane menuScheduling;
    @FXML
    private AnchorPane menuReports;
    @FXML
    private AnchorPane menuDatabase;
    @FXML
    private VBox groupCompany;
    @FXML
    private VBox groupTraining;
    @FXML
    private VBox groupReports;
    @FXML
    private VBox groupSettings;
    @FXML
    private VBox groupAbout;
    @FXML
    private AnchorPane menuSettings;
    public static AnchorPane staticPane;
    public static StackPane mainRootPane;
    @FXML
    private Pane popUpHolder;
    @FXML
    private JFXButton btnLogoff;
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnAccount;
    @FXML
    private JFXButton Reset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staticPane=menuStudent;
        mainRootPane=rootPane;
        setUpRipples();
    }

    @FXML
    public void showAbout() {
        setStage("/modules/About.fxml");
    }

    @FXML
    private void showCompanies(MouseEvent event) {
        setStage("/modules/Company.fxml");

    }

    @FXML     //applicable student
    private void showCourse(MouseEvent event) {
        setStage("/modules/Course.fxml");
    }

    @FXML
    private void showStudent(MouseEvent event) throws IOException {
        setStage("/modules/Student.fxml");

    }

    private void setUpRipples() {
        JFXRippler fXRippler = new JFXRippler(menuAbout, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler2 = new JFXRippler(menuDatabase, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        groupAbout.getChildren().addAll(fXRippler2, fXRippler);

        JFXRippler fXRippler3 = new JFXRippler(menuSettings, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler4 = new JFXRippler(menuStudent, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        groupSettings.getChildren().addAll(fXRippler3, fXRippler4);

        JFXRippler fXRippler5 = new JFXRippler(menuReports, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler6 = new JFXRippler(menuCourse, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        groupReports.getChildren().addAll(fXRippler5, fXRippler6);

        JFXRippler fXRippler7 = new JFXRippler(menuTraining, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler8 = new JFXRippler(menuScheduling, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        groupTraining.getChildren().addAll(fXRippler7, fXRippler8);

        JFXRippler fXRippler9 = new JFXRippler(menuCompany, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler fXRippler10 = new JFXRippler(menuSynch, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        groupCompany.getChildren().addAll(fXRippler9, fXRippler10);

    }

    @FXML   //selected student
    private void showScheduling(MouseEvent event) throws IOException {
        setStage("/modules/Placement.fxml");
    }

    @FXML
    private void showTraining(MouseEvent event) throws IOException {
        setStage("/modules/Training.fxml");
    }

    @FXML
    private void synchroniseOnline(MouseEvent event) {
        setStage("/modules/Cloud.fxml");
    }

    private void setStage(String fxml) {
        try {
            //dim overlay on new stage opening
            Region veil = new Region();
            veil.setPrefSize(1100, 650);
            veil.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
            Stage newStage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource(fxml));
            
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.getScene().getRoot().setEffect(new DropShadow());
            newStage.showingProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    rootPane.getChildren().add(veil);
                } else if (rootPane.getChildren().contains(veil)) {
                    rootPane.getChildren().removeAll(veil);
                }
                
            });
            newStage.centerOnScreen();
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showReports(MouseEvent event) {
         setStage("/modules/Report.fxml");
    }

    @FXML
    private void showSettings(MouseEvent event) {
          setStage("/modules/Seleted.fxml");
        
    }

    @FXML
    private void showBackUps(MouseEvent event) {
        setStage("/modules/BackUps.fxml");
    }

    @FXML
    private void loggOff(ActionEvent event) throws IOException {
        btnAccount.getScene().getWindow().hide();
        Stage news=new Stage();
        Parent root=FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene s=new Scene(root);
        news.setScene(s);
        news.show();
    }

    @FXML
    private void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void showActions(ActionEvent event) {
        JFXPopup popup=new JFXPopup();
        popup.setPopupContent(popUpHolder);
        popup.show(rootPane, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -45, 65);
    }

    @FXML
    private void reset(ActionEvent event) {
          setStage("/modules/Reset.fxml");
    }

}
