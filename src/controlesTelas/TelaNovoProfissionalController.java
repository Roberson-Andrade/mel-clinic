/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import entidades.Profissional;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaNovoProfissionalController implements Initializable {

    @FXML
    private TextField textName;
    @FXML
    private TextField textCellphone;
    @FXML
    private TextField textEspecialidade;
    @FXML
    private TextField textCodigo;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private Label errorLabel;
    
    ObservableList<Profissional> profissionais;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setProfissionais(ObservableList<Profissional> profissionais) {
        this.profissionais = profissionais;
    }

    @FXML
    private void onClickCancelButton(ActionEvent event) {
    }

    @FXML
    private void onSubmit(ActionEvent event) {
    }
    
}
