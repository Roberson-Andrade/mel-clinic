/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.EspecieDAO;
import entidades.Especie;
import helpers.GenericCreateTableButton;
import helpers.ScenePath;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class TelaEspeciesController implements Initializable {

    @FXML
    private TableView<Especie> TableViewEspecies;
    
    private ObservableList especies;
    final EspecieDAO especieDAO = new EspecieDAO();
    @FXML
    private Button novoEspecie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateEspecies(); //getting all clients
     
        setupTable(); //retrieving data from DB
    }   
    
     private void updateEspecies() {
        try {
            List<Especie> results = especieDAO.findAllEspecies();
        
            if(especies == null){ //checar se pessoas eh nulo para inicializar a lista de pessoas
                especies = FXCollections.observableArrayList(results);
            } else { //caso contrario limpa para adicionar a nova lista atualizada
                especies.clear();
                especies.addAll(results);
            }
            
        } catch (Exception e) {
        }
    }
     
     private TableColumn createTableColumn(String columnText,double minWidth, String propertyName) {
        TableColumn column = new TableColumn<Especie,Integer>();
        column.setText(columnText);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(new PropertyValueFactory(propertyName));
        
        return column;
    }
    
    private TableColumn<Especie, Void> createEspecieButtonColumn() {
        return GenericCreateTableButton.createButtonColumn(especie -> {
            try {
                especieDAO.delete(especie.getId());
                updateEspecies();
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            return null;
        });
    }

    private void setupTable() {
        TableColumn EspecieId = createTableColumn("id", 30, "id");
        TableColumn EspecieNome = createTableColumn("Nome", 100, "nome");
        TableColumn<Especie, Void> buttonColumn = createEspecieButtonColumn();
        
        
        TableViewEspecies.getColumns().addAll(EspecieId, EspecieNome, buttonColumn);
        TableViewEspecies.setItems(especies);
    }

    @FXML
    private void onClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath.NOVA_ESPECIE.getPath()));
        AnchorPane root = (AnchorPane) loader.load();
        
        // Get the controller of the new stage
        TelaNovaEspecieController novoEspecieController = loader.getController();
        // Pass the observable list to the controller
        novoEspecieController.setEspecies(especies);
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Nova espécie");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
}
