/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.ProfissionalDAO;
import entidades.Profissional;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaProfissionaisController implements Initializable {

    @FXML
    private TableView<Profissional> TableViewProfissionais;
    @FXML
    private Button novoProfissional;
    
    private ObservableList profissionais;
    final ProfissionalDAO profissionalDao = new ProfissionalDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onClick(ActionEvent event) {
        
    }
    
}
