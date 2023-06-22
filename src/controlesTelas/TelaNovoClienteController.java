    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.PessoaDAO;
import entidades.Pessoa;
import helpers.UF;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class TelaNovoClienteController implements Initializable {
    
    final PessoaDAO pessoaDao = new PessoaDAO();
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private TextField textName;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textCity;
    @FXML
    private ComboBox<String> textUf;
    @FXML
    private TextField textCellphone;
    @FXML
    private TextField textCpf;
    @FXML
    private TextField textRg;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField textCEP;
    
    ObservableList<Pessoa> pessoas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        textUf.setItems(UF.LISTA_UFS);
    }
    
    public void setPessoas(ObservableList<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
    private boolean validateInputs() {
        if(textName.getText().equals("")) {
            errorLabel.setText("*Preencha os campos obrigatórios.");
            return false;
        }
        
        return true;
    }


    @FXML
    private void onSubmit(ActionEvent event) throws Exception {
        boolean isInputValid = validateInputs();
        
        if(!isInputValid) {
            return;
        }
        
        String name = textName.getText();
        String address = textAddress.getText();
        String city = textCity.getText();
        String uf = textUf.getValue();
        String cellphone = textCellphone.getText();
        String rg = textRg.getText();
        String cep = textCEP.getText();
        String cpf = textCpf.getText();
        
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome(name);
        
        if(!address.equals("")) {
            novaPessoa.setEndereco(address);
        }
        
        if(!city.equals("")) {
            novaPessoa.setCidade(city);
        }
        
        if(uf != null) {
            novaPessoa.setUf(uf);
        }
        
        if(!cellphone.equals("")) {
            novaPessoa.setFoneContato(cellphone);
        }
        
        if(!cep.equals("")) {
            novaPessoa.setCep(Integer.valueOf(cep));
        }
        

        pessoaDao.add(novaPessoa);
        
        textName.setText("");
        textAddress.setText("");
        textCity.setText("");
        textCellphone.setText("");
        errorLabel.setText("");
        textRg.setText("");
        textCpf.setText("");
        
        List<Pessoa> results = pessoaDao.findAllPessoas();
        
        pessoas.clear();
        pessoas.addAll(results);
    }

    @FXML
    private void onClickCancelButton(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }
}
