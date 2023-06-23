/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.ProcedimentoDAO;
import entidades.Procedimento;
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
public class TelaProcedimentosController implements Initializable {

    @FXML
    private TableView<Procedimento> TableViewProcedimentos;
    
    private ObservableList procedimentos;
    final ProcedimentoDAO procedimentoDao = new ProcedimentoDAO();
    @FXML
    private Button novoProcedimento;

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
            List<Procedimento> results = procedimentoDao.findAllProcedimentos();
        
            if(procedimentos == null){ //checar se pessoas eh nulo para inicializar a lista de pessoas
                procedimentos = FXCollections.observableArrayList(results);
            } else { //caso contrario limpa para adicionar a nova lista atualizada
                procedimentos.clear();
                procedimentos.addAll(results);
            }
            
        } catch (Exception e) {
        }
    }
     
     private TableColumn createTableColumn(String columnText,double minWidth, String propertyName) {
        TableColumn column = new TableColumn<Procedimento,Integer>();
        column.setText(columnText);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(new PropertyValueFactory(propertyName));
        
        return column;
    }
    
    private TableColumn<Procedimento, Void> createEspecieButtonColumn() {
        return GenericCreateTableButton.createButtonColumn(procedimento -> {
            try {
                procedimentoDao.delete(procedimento.getId());
                updateEspecies();
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            return null;
        });
    }

    private void setupTable() {
        TableColumn procedimentoId = createTableColumn("id", 30, "id");
        TableColumn procedimentoCodigo = createTableColumn("Código", 100, "codigo");
        TableColumn procedimentoDescricao = createTableColumn("Descricão", 100, "descricao");
        TableColumn procedimentoNumSessoes = createTableColumn("Número de sessões", 100, "numSessao");
        TableColumn procedimentoOrientacoes = createTableColumn("Orientações", 100, "orientacoes");
        TableColumn procedimentoValor = createTableColumn("Valor", 100, "valor");
        TableColumn<Procedimento, Void> buttonColumn = createEspecieButtonColumn();
        
        
        TableViewProcedimentos.getColumns().addAll(procedimentoId, procedimentoCodigo, procedimentoDescricao, procedimentoNumSessoes, procedimentoOrientacoes, procedimentoValor, buttonColumn);
        TableViewProcedimentos.setItems(procedimentos);
    }
    
    @FXML
    private void onClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath.NOVO_PROCEDIMENTO.getPath()));
        AnchorPane root = (AnchorPane) loader.load();
        
        // Get the controller of the new stage
        TelaNovoProcedimentoController novoEspecieController = loader.getController();
        // Pass the observable list to the controller
        novoEspecieController.setProcedimentos(procedimentos);
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Nova procedimento");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
}
