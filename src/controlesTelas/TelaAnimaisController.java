/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.AnimaisDAO;
import entidades.Animal;
import entidades.Pessoa;
import helpers.ScenePath;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
 * @author Emerson
 */
public class TelaAnimaisController implements Initializable {

    @FXML
    private TableView<Animal> TableViewAnimais;
    @FXML
    private Button novoAnimal;

    private ObservableList animais;
    final AnimaisDAO animalDao = new AnimaisDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateAnimais(); //getting all animals
        
        setupTable(); //retrieving data from DB
        // TODO
    }
    
    private void updateAnimais() {
        try {
            List<Animal> results = animalDao.findAllAnimais();
            //como funciona isso, se for nulo ele atribui os resultados da pesquisa
            //mas se nao for nula ele limpa, zera e depois adiciona oq? o vazio?
            if(animais == null) 
                animais = FXCollections.observableArrayList(results);
            else {
                animais.clear();
                animais.addAll(animais);
            }
        } catch (Exception e) {
            
        }
    }

//    private TableColumn createTableColumn(String columnText, double minWidth, String propertyName) {
//        //pq eh integer ali?? eh integer pra tudo?
//        TableColumn column = new TableColumn<Animal, String>();
//        column.setText(columnText);
//        column.setMinWidth(minWidth);
//        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
//
//        return column;
//    } //metodo original com base no telaclientescontroller
    private TableColumn createTableColumn(String columnText, double minWidth, String propertyName) {
    TableColumn<Animal, String> column = new TableColumn<>(columnText);
    //se coloca o <Animal, String> pro outro lado para de funcionar?
    column.setMinWidth(minWidth);
    
    if (propertyName.equals("proprietarioId")) {
        column.setCellValueFactory(cellData -> {
            Animal animal = cellData.getValue();
            Pessoa proprietario = animal.getProprietarioId();
            String proprietarioName = (proprietario != null) ? proprietario.getNome() : "";
            return new SimpleStringProperty(proprietarioName);
        });
    } else {
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
    }

    return column;
}
    
//    private TableColumn createTableColumn2(String columnText, double minWidth, String propertyName) {
//    TableColumn<Animal, String> column = new TableColumn<>(columnText);
//    column.setMinWidth(minWidth);
//    column.setCellValueFactory(cellData -> {
//        Animal animal = cellData.getValue();
//        Pessoa proprietario = animal.getProprietarioId();
//        String proprietarioName = (proprietario != null) ? proprietario.getNome() : "";
//        return new SimpleStringProperty(proprietarioName);
//    });
//    return column;
//} //uma forma de fazer, usando um metodo so para essa coluna especifica

    private void setupTable() {
//segue a forma de um metodos so para a coluna especifica se colocar o 2 no final do createtablecolumn  
    TableColumn<Animal, String> AnimalDono = createTableColumn("Dono", 100, "proprietarioId");
    TableColumn<Animal, String> AnimalNome = createTableColumn("Nome", 100, "nome");
    TableColumn<Animal, String> AnimalRaca = createTableColumn("Raca", 100, "raca");
    TableColumn<Animal, String> AnimalSexo = createTableColumn("Sexo", 100, "sexo");
    TableColumn<Animal, LocalDate> AnimalNascimento = createTableColumn("Nascimento", 100, "nascimento");
        //ali funciona tanto LocalDate quanto String
    
        TableViewAnimais.getColumns().addAll(AnimalDono,AnimalNome, AnimalRaca, AnimalSexo, AnimalNascimento);
        TableViewAnimais.setItems(animais);
    }

    @FXML
    private void onClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath.NOVO_ANIMAL.getPath()));
        AnchorPane root = (AnchorPane) loader.load();

        TelaNovoAnimalController novoAnimalController = loader.getController();

        novoAnimalController.setAnimais(animais);

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Novo Animal");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

}
