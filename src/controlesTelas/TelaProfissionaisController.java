/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.ProfissionalDAO;
import entidades.Profissional;
import helpers.GenericCreateTableButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        updateProfissionais();

        setupTable();
    }

    private void updateProfissionais() {
        try {
            //findallpessoas kkkkkkkkkk
            List<Profissional> results = profissionalDao.findAllPessoas();

            if (profissionais == null) { //checar se pessoas eh nulo para inicializar a lista de pessoas
                System.out.println("IF");
                profissionais = FXCollections.observableArrayList(results);
            } else { //caso contrario limpa para adicionar a nova lista atualizada
                System.out.println("ELSE");
                profissionais.clear();
                profissionais.addAll(results);
            }

        } catch (Exception e) {
        }
    }

    private TableColumn createTableColumn(String columnText, double minWidth, String propertyName) {
        TableColumn column = new TableColumn<Profissional, Integer>();
        column.setText(columnText);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(new PropertyValueFactory(propertyName));

        return column;
    }

    private TableColumn<Profissional, Void> createProfissionalButtonColumn() {
        return GenericCreateTableButton.createButtonColumn(profissional -> {
            try {
                profissionalDao.delete(profissional.getId());
                updateProfissionais();
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            return null;
        });
    }

    private void setupTable() {
        TableColumn<Profissional, String> profissionalNome = createTableColumn("Nome", 100, "nome");
        TableColumn<Profissional, String> profissionalTelefone = createTableColumn("Telefone", 100, "telefone");
        TableColumn<Profissional, String> profissionalEspecialidade = createTableColumn("Especialidade", 100, "especialidade");
        TableColumn<Profissional, Void> buttonColumn = createProfissionalButtonColumn();

        TableViewProfissionais.getColumns().addAll(profissionalNome, profissionalTelefone, profissionalEspecialidade, buttonColumn);
        TableViewProfissionais.setItems(profissionais);
    }

    @FXML
    private void onClick(ActionEvent event) {

    }

}
