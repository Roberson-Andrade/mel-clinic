/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.PessoaDAO;
import entidades.Pessoa;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaClientesController implements Initializable {
    @FXML
    private TableView<Pessoa> TableViewClientes;

    
    private ObservableList pessoas;
    final PessoaDAO pessoaDao = new PessoaDAO();
    
    @FXML
    private Button novoCliente;

    public TelaClientesController() {
        System.out.println("TelaClientesController instantiated.");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateClientes(); //getting all clients
     
        setupTable(); //retrieving data from DB
    }    
    private void updateClientes() {
        try {
            List<Pessoa> results = pessoaDao.findAllPessoas();
        
            if(pessoas == null){ //checar se pessoas eh nulo para inicializar a lista de pessoas
                System.out.println("IF");
                pessoas = FXCollections.observableArrayList(results);
            } else { //caso contrario limpa para adicionar a nova lista atualizada
                    System.out.println("ELSE");
                pessoas.clear();
                pessoas.addAll(results);
            }
            
        } catch (Exception e) {
        }
    }
    
    private TableColumn createTableColumn(String columnText,double minWidth, String propertyName) {
        TableColumn column = new TableColumn<Pessoa,Integer>();
        column.setText(columnText);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(new PropertyValueFactory(propertyName));
        
        return column;
    }
    
    private TableColumn<Pessoa, Void> createButtonColumn() {
        TableColumn<Pessoa, Void> buttonColumn = new TableColumn<>("Action");

        buttonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Click");

            {
                button.setOnAction(event -> {
                    Pessoa pessoa = getTableRow().getItem();
                    
                    if (pessoa != null) {
                        try {
                            pessoaDao.delete(pessoa.getId());
                                                        
                            updateClientes();
                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        return buttonColumn;
    }   

    private void setupTable() {
        TableColumn ClienteID = createTableColumn("id", 30, "id");
        TableColumn ClienteNome = createTableColumn("Nome", 100, "nome");
        TableColumn ClienteTelefone = createTableColumn("Telefone", 100, "foneContato");
        TableColumn ClienteEndereco = createTableColumn("Endereço", 100, "endereco");
        TableColumn ClienteCEP = createTableColumn("CEP", 100, "cep");
        TableColumn ClienteCidade = createTableColumn("Cidade", 100, "cidade");
        TableColumn ClienteUf = createTableColumn("UF", 30, "uf");
        TableColumn<Pessoa, Void> buttonColumn = createButtonColumn();
        
        
        TableViewClientes.getColumns().addAll(ClienteID, ClienteNome, ClienteTelefone,  ClienteEndereco, ClienteCEP, ClienteCidade, ClienteUf, buttonColumn);
        TableViewClientes.setItems(pessoas);
    }

    @FXML
    private void onClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath.NOVO_CLIENTE.getPath()));
        AnchorPane root = (AnchorPane) loader.load();
        
        // Get the controller of the new stage
        TelaNovoClienteController novoClienteController = loader.getController();
        // Pass the observable list to the controller
        novoClienteController.setPessoas(pessoas);
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setTitle("Novo cliente");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
}
