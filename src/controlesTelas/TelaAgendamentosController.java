/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.AgendamentoDAO;
import entidades.Agendamento;
import helpers.GenericCreateTableButton;
import helpers.ScenePath;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
public class TelaAgendamentosController implements Initializable {

    @FXML
    private TableView<Agendamento> tableViewAgendamentos;
    @FXML
    private Button novoAgendamento;

    private ObservableList<Agendamento> agendamentos;
    private final AgendamentoDAO agendamentoDao = new AgendamentoDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateAgendamentos();
        setupTable();
    }

    private void updateAgendamentos() {
        try {
            List<Agendamento> results = agendamentoDao.findAllAgendamentos();
            if (agendamentos == null) {
                agendamentos = FXCollections.observableArrayList(results);
            } else {
                agendamentos.clear();
                agendamentos.addAll(results);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TableColumn<Agendamento, String> createTableColumn(String columnText, double minWidth, String propertyName) {
        TableColumn<Agendamento, String> column = new TableColumn<>(columnText);
        column.setMinWidth(minWidth);

        switch (propertyName) {
            case "animalId":
                column.setCellValueFactory(cellData -> {
                    Agendamento agendamento = cellData.getValue();
                    String animalNome = (agendamento.getAnimalId() != null) ? agendamento.getAnimalId().getNome() : "";
                    return new SimpleStringProperty(animalNome);
                });
                break;
            case "profissionalId":
                column.setCellValueFactory(cellData -> {
                    Agendamento agendamento = cellData.getValue();
                    String profissionalNome = (agendamento.getProfissionalId() != null) ? agendamento.getProfissionalId().getNome() : "";
                    return new SimpleStringProperty(profissionalNome);
                });
                break;
            case "procedimentoId":
                column.setCellValueFactory(cellData -> {
                    Agendamento agendamento = cellData.getValue();
                    String procedimentoDescricao = (agendamento.getProcedimentoId() != null) ? agendamento.getProcedimentoId().getDescricao() : "";
                    return new SimpleStringProperty(procedimentoDescricao);
                });
                break;
            case "dataAgendamento":
                column.setCellValueFactory(cellData -> {
                    Agendamento agendamento = cellData.getValue();
                    Date dataAgendamento = agendamento.getDataAgendamento();
                    String formattedDate = (dataAgendamento != null) ? new SimpleDateFormat("dd/MM/yyyy").format(dataAgendamento) : "";
                    return new SimpleStringProperty(formattedDate);
                });
                break;
            default:
                column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                break;
        }

        return column;
    }

    private TableColumn<Agendamento, Void> createAgendamentoButtonColumn() {
        return GenericCreateTableButton.createButtonColumn(agendamento -> {
            try {
                agendamentoDao.delete(agendamento.getId());
                updateAgendamentos();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private void setupTable() {
        TableColumn<Agendamento, String> colAnimal = createTableColumn("Animal", 150, "animalId");
        TableColumn<Agendamento, String> colProfissional = createTableColumn("Profissional", 150, "profissionalId");
        TableColumn<Agendamento, String> colProcedimento = createTableColumn("Procedimento", 200, "procedimentoId");
        TableColumn<Agendamento, String> colDataAgendamento = createTableColumn("Data do Agendamento", 150, "dataAgendamento");
        TableColumn<Agendamento, Void> buttonColumn = createAgendamentoButtonColumn();

        tableViewAgendamentos.getColumns().addAll(colAnimal, colProfissional, colProcedimento, colDataAgendamento, buttonColumn);
        tableViewAgendamentos.setItems(agendamentos);
    }

    @FXML
    private void onClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath.NOVO_AGENDAMENTO.getPath()));
        AnchorPane root = (AnchorPane) loader.load();

        TelaNovoAgendamentoController novoAgendamentoController = loader.getController();
        novoAgendamentoController.setAgendamentos(agendamentos);

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Novo Agendamento");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
