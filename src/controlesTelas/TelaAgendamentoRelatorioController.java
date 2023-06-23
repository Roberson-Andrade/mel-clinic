/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.AgendamentoDAO;
import DAO.ProfissionalDAO;
import entidades.Agendamento;
import entidades.Especie;
import entidades.Profissional;
import helpers.TipoRelatorioAgendamento;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class TelaAgendamentoRelatorioController implements Initializable {

    @FXML
    private TableView<Agendamento> TableViewAgendamentoRelatorio;
    @FXML
    private ComboBox<TipoRelatorioAgendamento> comboBoxTipoRelatorio;
    
    final private AgendamentoDAO agendamentoDao = new AgendamentoDAO();
    final private ProfissionalDAO profissionalDao = new ProfissionalDAO();
    private ObservableList<Agendamento> agendamentos;
    
    @FXML
    private DatePicker datePickerDia;
    @FXML
    private Button buttonBuscar;
    @FXML
    private ComboBox<Profissional> comboBoxProfissional;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupComboBoxes();
    }
    
    private void setupComboBoxes() {
        TipoRelatorioAgendamento[] tipos = {TipoRelatorioAgendamento.POR_DIA, TipoRelatorioAgendamento.POR_PROFISSIONAL};
        ObservableList<TipoRelatorioAgendamento> tipoRelatorio = FXCollections.observableArrayList(tipos);
        comboBoxTipoRelatorio.setItems(tipoRelatorio);
        
        try {
            List<Profissional> profissionais = profissionalDao.findAllProfissionais();
            ObservableList<Profissional> observableListProfisisonais = FXCollections.observableArrayList(profissionais);
            comboBoxProfissional.setItems(observableListProfisisonais);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void findAgendamentoByCondition(TipoRelatorioAgendamento tipo) {
        try {
            List<Agendamento> results = new ArrayList<>();
            
            if(tipo.equals(TipoRelatorioAgendamento.POR_DIA)) {
               results = agendamentoDao.findAllAgendamentosByDate(new Date());
            } else {
               results = agendamentoDao.findAllAgendamentosByProfissional(comboBoxProfissional.getValue().getId());
            }
                    
            if(agendamentos == null){ //checar se pessoas eh nulo para inicializar a lista de pessoas
                agendamentos = FXCollections.observableArrayList(results);
            } else { //caso contrario limpa para adicionar a nova lista atualizada
                agendamentos.clear();
                agendamentos.addAll(results);
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
    

    private void setupTable() {
        TableViewAgendamentoRelatorio.getColumns().clear();
         
        TableColumn<Agendamento, String> colAnimal = createTableColumn("Animal", 150, "animalId");
        TableColumn<Agendamento, String> colProfissional = createTableColumn("Profissional", 150, "profissionalId");
        TableColumn<Agendamento, String> colProcedimento = createTableColumn("Procedimento", 200, "procedimentoId");
        TableColumn<Agendamento, String> colDataAgendamento = createTableColumn("Data do Agendamento", 150, "dataAgendamento");

        TableViewAgendamentoRelatorio.getColumns().addAll(colAnimal, colProfissional, colProcedimento, colDataAgendamento);
        TableViewAgendamentoRelatorio.setItems(agendamentos);
    }    

    @FXML
    private void onChangeTipoRelatorio(ActionEvent event) {
        buttonBuscar.setVisible(true);
        
        if(comboBoxTipoRelatorio.getValue().equals(TipoRelatorioAgendamento.POR_DIA)) {
            datePickerDia.setVisible(true);
            comboBoxProfissional.setVisible(false);
        } else {
            datePickerDia.setVisible(false);
            comboBoxProfissional.setVisible(true);
        }
    }

    @FXML
    private void onClickBuscar(ActionEvent event) {       
        if(comboBoxTipoRelatorio.getValue().equals(TipoRelatorioAgendamento.POR_DIA)) {
            findAgendamentoByCondition(TipoRelatorioAgendamento.POR_DIA);
        } else {
            findAgendamentoByCondition(TipoRelatorioAgendamento.POR_PROFISSIONAL);
        }
        
        setupTable();
    } 
}
