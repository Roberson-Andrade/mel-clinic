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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    PessoaDAO proprietario = new PessoaDAO();
    @FXML
    private TextField textNome;
    @FXML
    private TextField textRaca;
    @FXML
    private TextField textSexo;
    @FXML
    private DatePicker textNascimento;
    @FXML
    private TextField textDono;
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
        System.out.println(textNome + " aaaaaaaaaaaaaa");
        String name = textNome.getText();
        String raca = textRaca.getText();
        String sexo = textSexo.getText();
        String dono = textDono.getText();
        Date nascimento = null;
        if (textNascimento.getValue() != null) {
            nascimento = java.sql.Date.valueOf(textNascimento.getValue());
        }
        
        Pessoa proprietario2 = proprietario.findPessoaByNome(dono);    
        
        Animal novoAnimal = new Animal();
        novoAnimal.setNome(name);
        novoAnimal.setRaca(raca);
        novoAnimal.setSexo(sexo);
        novoAnimal.setNascimento(nascimento);
        novoAnimal.setProprietarioId(proprietario2);
        
        animalDao.add(novoAnimal);
        
        textRaca.setText("");
        textSexo.setText("");
        textNascimento.setValue(null);
        
        List<Animal> results = animalDao.findAllAnimais();
        animais.clear();
        animais.addAll(results);
        

    }

}
