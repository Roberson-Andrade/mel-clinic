/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.AnimaisDAO;
import DAO.PessoaDAO;
import entidades.Animal;
import entidades.Pessoa;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
public class TelaNovoAnimalController implements Initializable {

    ObservableList<Animal> animais;
    final AnimaisDAO animalDao = new AnimaisDAO();
    final PessoaDAO donoDao = new PessoaDAO();
    
    @FXML
    private TextField textNome;
    @FXML
    private TextField textRaca;
    @FXML
    private TextField textSexo;
    @FXML
    private DatePicker textNascimento;
    @FXML
    private ComboBox<Pessoa> comboBoxDono;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private Label errorLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDonos();
    }
    
    private void loadDonos() {
        try {
            List<Pessoa> donos = donoDao.findAllPessoas();
        
            ObservableList<Pessoa> observableDono = FXCollections.observableArrayList(donos);
        
            comboBoxDono.setItems(observableDono);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
        private boolean validateInputs() {
        if(textNome.getText().equals("")) {
            errorLabel.setText("*Preencha os campos obrigatórios.");
            return false;
        }
        
        if(comboBoxDono.getValue() == null) {
            errorLabel.setText("*Animal deve ter um dono.");
            return false;
        }
        
        return true;
    }

    void setAnimais(ObservableList<Animal> animais) {
        this.animais = animais;
    }

    @FXML
    private void onClickCancelButton(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    private void onSubmit(ActionEvent event) throws Exception {
        boolean isInputValid = validateInputs();
        
        if(!isInputValid) {
            return;
        }
        
        String name = textNome.getText();
        String raca = textRaca.getText();
        String sexo = textSexo.getText();
        Pessoa dono = comboBoxDono.getValue();
        Date nascimento = null;
        
        
        if (textNascimento.getValue() != null) {
            nascimento = java.sql.Date.valueOf(textNascimento.getValue());
        }
                
        Animal novoAnimal = new Animal();
        novoAnimal.setNome(name);
        novoAnimal.setRaca(raca);
        novoAnimal.setSexo(sexo);
        novoAnimal.setProprietarioId(dono);
        
        if(nascimento != null) {
            novoAnimal.setNascimento(nascimento);
        }
        
        System.err.println(novoAnimal.getNome());
        
        animalDao.add(novoAnimal);
        
        textNome.setText("");
        textRaca.setText("");
        textSexo.setText("");
        comboBoxDono.setValue(null);
        textNascimento.setValue(null);
        
        List<Animal> results = animalDao.findAllAnimais();
        animais.clear();
        animais.addAll(results);
        

    }

}
