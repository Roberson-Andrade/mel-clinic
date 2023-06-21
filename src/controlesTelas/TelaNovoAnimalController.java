/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import entidades.Animal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaNovoAnimalController implements Initializable {

    
    ObservableList<Animal> animais;
    @FXML
    private TextField textName;
    @FXML
    private TextField textRaca;
    @FXML
    private TextField textSexo;
    @FXML
    private TextField textNascimento;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private Label errorLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setAnimais(ObservableList<Animal> animais) {
        this.animais = animais;
    }

    @FXML
    private void onClickCancelButton(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    private void onSubmit(ActionEvent event) {
        
        
    }
    
}
