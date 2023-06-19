/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import entidades.Pessoa;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaClientesController implements Initializable {
    @FXML
    private TableView<Pessoa> TableViewClientes;

    
    private ObservableList pessoas;
    
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MelClinicPU");
    final EntityManager em = emf.createEntityManager();

    public TelaClientesController() {
        System.out.println("TelaClientesController instantiated.");
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("aaaaaaaaaaa");
        updateClientes(); //getting all clients
        
        setupTable(); //retrieving data from DB
    }    
    private void updateClientes() {
        Query rq = em.createNamedQuery("Pessoa.findAll");
        List results = rq.getResultList();
        System.out.println("tamanho do resultados" + results.size()); //checar
        if(pessoas == null) //checar se pessoas eh nulo para inicializar a lista de pessoas
            pessoas = FXCollections.observableArrayList(results);
        else { //caso contrario limpa para adicionar a nova lista atualizada
            pessoas.clear();
            pessoas.addAll(pessoas);
        }
    }

    private void setupTable() {
        TableColumn ClienteID = new TableColumn<Pessoa,Integer>();
        ClienteID.setText("id");
        ClienteID.setMinWidth(100);
        ClienteID.setCellValueFactory(new PropertyValueFactory("id"));//o id da classe da entidade
        TableColumn ClienteNome = new TableColumn<Pessoa,String>();
        ClienteNome.setMinWidth(100);
        ClienteNome.setText("abluble");
        ClienteNome.setCellValueFactory(new PropertyValueFactory("nome"));
        
        TableViewClientes.getColumns().addAll(ClienteID, ClienteNome);
        TableViewClientes.setItems(pessoas);
    }
    
}
