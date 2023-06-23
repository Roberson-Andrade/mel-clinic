/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.EspecieDAO;
import entidades.Especie;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class TelaNovaEspecieController implements Initializable {

    @FXML
    private TextField textNome;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private Label errorLabel;
    
    final EspecieDAO especieDao = new EspecieDAO();
    ObservableList<Especie> especies;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onClickCancelButton(ActionEvent event) {
    }
    
    void setEspecies(ObservableList<Especie> especies) {
        this.especies = especies;
    }
    
    private boolean validateInputs() {
        if(textNome.getText().equals("")) {
            errorLabel.setText("*Preencha os campos obrigatórios.");
            return false;
        }
        
        return true;
    }

    @FXML
    private void onSubmit(ActionEvent event) throws Exception {
        boolean isInputValid = validateInputs();
        
        if(!isInputValid) {
            return;
        }
        
        String name = textNome.getText();
        
        Especie novaEspecie = new Especie();
        novaEspecie.setNome(name);
        
        especieDao.add(novaEspecie);
        
        textNome.setText("");
        
        List<Especie> results = especieDao.findAllEspecies();
        especies.clear();
        especies.addAll(results);
    }
    
}
