    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.PessoaDAO;
import entidades.Pessoa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private TextField textUf;
    @FXML
    private TextField textCellphone;
    @FXML
    private TextField textCpf;
    @FXML
    private TextField textRg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSubmit(ActionEvent event) throws Exception {
        String name = textName.getText();
        String address = textAddress.getText();
        String city = textCity.getText();
        String uf = textUf.getText();
        String cellphone = textCellphone.getText();
        
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome(name);
        novaPessoa.setEndereco(address);
        novaPessoa.setCidade(city);
        novaPessoa.setUf(uf);
        novaPessoa.setFoneContato(cellphone);
        
        pessoaDao.add(novaPessoa);
    }
}
