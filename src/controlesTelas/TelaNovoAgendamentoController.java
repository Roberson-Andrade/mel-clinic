/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.AgendamentoDAO;
import DAO.AnimaisDAO;
import DAO.ProfissionalDAO;
//import DAO.ProcedimentoDAO;
import entidades.Agendamento;
import entidades.Animal;
import entidades.Profissional;
import entidades.Procedimento;
import helpers.ScenePath;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaNovoAgendamentoController implements Initializable {

    @FXML
    private TextField textSessionNum;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox<Animal> comboBoxNomeAnimal;
    @FXML
    private ComboBox<Profissional> comboBoxNomeProfissional;
    @FXML
    private TextField textValorCobrado;
    @FXML
    private ComboBox<Procedimento> comboBoxProcedimento;

    private ObservableList<Animal> animais;
    private ObservableList<Profissional> profissionais;
    private ObservableList<Procedimento> procedimentos;

    private ObservableList<Agendamento> agendamentos;

    private final AgendamentoDAO agendamentoDao = new AgendamentoDAO();
    @FXML
    private ComboBox<?> comboBoxPagamento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupComboBoxes();
    }

    private void setupComboBoxes() {
        AnimaisDAO animalDao = new AnimaisDAO();
        ProfissionalDAO profissionalDao = new ProfissionalDAO();
        //ProcedimentoDAO procedimentoDao = new ProcedimentoDAO();

        try {
            List<Animal> animalList = animalDao.findAllAnimais();
            animais = FXCollections.observableArrayList(animalList);
            comboBoxNomeAnimal.setItems(animais);

            List<Profissional> profissionalList = profissionalDao.findAllPessoas();
            profissionais = FXCollections.observableArrayList(profissionalList);
            comboBoxNomeProfissional.setItems(profissionais);

            //List<Procedimento> procedimentoList = procedimentoDao.findAllProcedimentos();
            //procedimentos = FXCollections.observableArrayList(procedimentoList);
            //comboBoxProcedimento.setItems(procedimentos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSubmit(ActionEvent event) throws Exception {
        String sessionNum = textSessionNum.getText();
        String pagamento = (String) comboBoxPagamento.getValue();
        LocalDate date = datePicker.getValue();
        Animal animal = comboBoxNomeAnimal.getValue();
        Profissional profissional = comboBoxNomeProfissional.getValue();
        Procedimento procedimento = comboBoxProcedimento.getValue();
        String valorCobrado = textValorCobrado.getText();

        Agendamento agendamento = new Agendamento();
        agendamento.setNumSessao(Integer.valueOf(sessionNum));
        agendamento.setPagamento(pagamento);
        agendamento.setDataAgendamento(java.sql.Date.valueOf(date));
        agendamento.setAnimalId(animal);
        agendamento.setProfissionalId(profissional);
        agendamento.setProcedimentoId(procedimento);
        agendamento.setValorCobrado(new BigDecimal(valorCobrado));

        agendamentoDao.add(agendamento);

        textSessionNum.clear();
        datePicker.setValue(null);
        comboBoxNomeAnimal.setValue(null);
        comboBoxNomeProfissional.setValue(null);
        comboBoxProcedimento.setValue(null);
        textValorCobrado.clear();
        comboBoxPagamento.setValue(null);
        errorLabel.setText("");

    }

    private boolean validateFields(String sessionNum, String codigo, LocalDate date, Animal animal, Profissional profissional, Procedimento procedimento, String valorCobrado) {
        if (sessionNum.isEmpty() || codigo.isEmpty() || date == null || animal == null || profissional == null || procedimento == null || valorCobrado.isEmpty()) {
            errorLabel.setText("Preencha todos os campos.");
            return false;
        }

        try {
            Double.valueOf(valorCobrado);
        } catch (NumberFormatException e) {
            errorLabel.setText("Valor cobrado inválido.");
            return false;
        }

        return true;
    }

    public void setAgendamentos(ObservableList<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
