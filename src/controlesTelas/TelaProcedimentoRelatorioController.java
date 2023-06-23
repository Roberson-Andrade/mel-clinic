/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.AgendamentoDAO;
import DAO.AnimaisDAO;
import DAO.ProfissionalDAO;
import entidades.Agendamento;
import entidades.Animal;
import entidades.Profissional;
import helpers.TipoRelatorioProcedimento;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class TelaProcedimentoRelatorioController implements Initializable {

    @FXML
    private TableView<Agendamento> TableViewAgendamentoRelatorio;
    @FXML
    private ComboBox<TipoRelatorioProcedimento> comboBoxTipoRelatorio;
    
    final private AgendamentoDAO agendamentoDao = new AgendamentoDAO();
    final private AnimaisDAO animalDao = new AnimaisDAO();
    final private ProfissionalDAO profissionalDao = new ProfissionalDAO();
    private ObservableList<Agendamento> agendamentos;
    
    @FXML
    private Button buttonBuscar;
    @FXML
    private ComboBox<Animal> comboBoxAnimal;
    @FXML
    private ComboBox<Profissional> comboBoxProfissional;
    @FXML
    private ComboBox<Integer> comboBoxAno;
    @FXML
    private ComboBox<Month> comboBoxMes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupComboBoxes();
    }
    
    private List<Integer> getYears() {
        List<Integer> years = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();

        for (int i = currentYear; i >= currentYear - 100; i--) {
            years.add(i);
        }

        return years;
    }
    
    private void setupComboBoxes() {
        TipoRelatorioProcedimento[] tipos = {TipoRelatorioProcedimento.POR_ANIMAL, TipoRelatorioProcedimento.POR_PROFISSIONAL};
        ObservableList<TipoRelatorioProcedimento> tipoRelatorio = FXCollections.observableArrayList(tipos);
        comboBoxTipoRelatorio.setItems(tipoRelatorio);
        
        comboBoxMes.setItems(FXCollections.observableArrayList(Month.values()));
        
        comboBoxAno.setItems(FXCollections.observableArrayList(getYears()));
        
        try {
            List<Animal> animais = animalDao.findAllAnimais();
            ObservableList<Animal> observableListAnimais = FXCollections.observableArrayList(animais);
            comboBoxAnimal.setItems(observableListAnimais);
            
            List<Profissional> profissionais = profissionalDao.findAllProfissionais();
            ObservableList<Profissional> observableListProfisisonais = FXCollections.observableArrayList(profissionais);
            comboBoxProfissional.setItems(observableListProfisisonais);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void findProcedimentoByCondition(TipoRelatorioProcedimento tipo) {
        try {
            List<Agendamento> results = new ArrayList<>();
            
            if(tipo.equals(TipoRelatorioProcedimento.POR_ANIMAL)) {
               results = agendamentoDao.findAllAgendamentosByAnimal(comboBoxProfissional.getValue().getId());
            } else {
                Month selectedMonth = comboBoxMes.getValue();
                int selectedYear = comboBoxAno.getValue();

               LocalDate date = LocalDate.of(selectedYear, selectedMonth, 1);
               results = agendamentoDao.findAllAgendamentosByProfissionalAndMonth(comboBoxProfissional.getValue().getId(), date);
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
        TableColumn<Agendamento, String> column = new TableColumn<>(columnText);
        column.setMinWidth(minWidth);
        
        switch (propertyName) {
            case "dataAgendamento":
                column.setCellValueFactory(cellData -> {
                    Agendamento agendamento = cellData.getValue();
                    Date dataAgendamento = agendamento.getDataAgendamento();
                    String formattedDate = (dataAgendamento != null) ? new SimpleDateFormat("dd/MM/yyyy").format(dataAgendamento) : "";
                    return new SimpleStringProperty(formattedDate);
                }); break;
            case "valorProcedimento":
                column.setCellValueFactory(cellData -> {
                    Agendamento agendamento = cellData.getValue();
                    String procedimentoDescricao = (agendamento.getProcedimentoId() != null) ? agendamento.getProcedimentoId().getValor().toString() : "";
                    return new SimpleStringProperty(procedimentoDescricao);
                }); break;
            case "valorCobrado":
                column.setCellValueFactory(cellData -> {
                    Agendamento agendamento = cellData.getValue();
                    String procedimentoDescricao = (agendamento.getProcedimentoId() != null) ? agendamento.getValorCobrado().toString() : "";
                    return new SimpleStringProperty(procedimentoDescricao);
                }); break;
            default:
                column.setCellValueFactory(new PropertyValueFactory(propertyName));
                break;
        }
         
        return column;
    }
    

    private void setupTable(TipoRelatorioProcedimento tipo) {
        TableViewAgendamentoRelatorio.getColumns().clear();
         
        TableColumn<Agendamento, String> colAnimal = createTableColumn("Animal", 150, "animalId");
        TableColumn<Agendamento, String> colProfissional = createTableColumn("Profissional", 150, "profissionalId");
        TableColumn<Agendamento, String> colProcedimento = createTableColumn("Procedimento", 200, "procedimentoId");
        TableColumn<Agendamento, String> colValor = createTableColumn("Valor", 200, "valorProcedimento");
        TableColumn<Agendamento, String> colValorCobrado = createTableColumn("Valor cobrado", 200, "valorCobrado");
        TableColumn<Agendamento, String> colDataAgendamento = createTableColumn("Data realizada", 150, "dataAgendamento");
        
        if(tipo.equals(TipoRelatorioProcedimento.POR_ANIMAL)) {
            TableViewAgendamentoRelatorio.getColumns().addAll(colProcedimento,colAnimal, colDataAgendamento);
        } else {
            TableViewAgendamentoRelatorio.getColumns().addAll(colProcedimento, colProfissional, colValor, colValorCobrado, colDataAgendamento);
        }

        TableViewAgendamentoRelatorio.setItems(agendamentos);

    }    

    @FXML
    private void onChangeTipoRelatorio(ActionEvent event) {
        buttonBuscar.setVisible(true);
        
        if(comboBoxTipoRelatorio.getValue().equals(TipoRelatorioProcedimento.POR_ANIMAL)) {
            comboBoxAnimal.setVisible(true);
            comboBoxProfissional.setVisible(false);
            comboBoxAno.setVisible(false);
            comboBoxMes.setVisible(false);
        } else {
            comboBoxAnimal.setVisible(false);
            comboBoxProfissional.setVisible(true);
            comboBoxAno.setVisible(true);
            comboBoxMes.setVisible(true);
        }
    }

    @FXML
    private void onClickBuscar(ActionEvent event) {       
        if(comboBoxTipoRelatorio.getValue().equals(TipoRelatorioProcedimento.POR_ANIMAL)) {
            findProcedimentoByCondition(TipoRelatorioProcedimento.POR_ANIMAL);
        } else {
            findProcedimentoByCondition(TipoRelatorioProcedimento.POR_PROFISSIONAL);
        }
        
        setupTable(comboBoxTipoRelatorio.getValue());
    } 
}
