/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.ProfissionalDAO;
import entidades.Profissional;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaNovoProfissionalController implements Initializable {

    final ProfissionalDAO profissionalDao = new ProfissionalDAO();
    @FXML
    private TextField textName;
    @FXML
    private TextField textCellphone;
    @FXML
    private TextField textEspecialidade;
    @FXML
    private TextField textCodigo;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private Label errorLabel;

    ObservableList<Profissional> profissionais;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setProfissionais(ObservableList<Profissional> profissionais) {
        this.profissionais = profissionais;
    }

    @FXML
    private void onClickCancelButton(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    private void onSubmit(ActionEvent event) throws Exception {
//        @FXML
//        private TextField textName;
//        @FXML
//        private TextField textCellphone;
//        @FXML
//        private TextField textEspecialidade;
//        @FXML
//        private TextField textCodigo;

        String name = textName.getText();
        String cellphone = textCellphone.getText();
        String especialidade = textEspecialidade.getText();
        //String codigo = textCodigo.getText();

        Profissional novoProfissional = new Profissional();
        novoProfissional.setNome(name);
        novoProfissional.setTelefone(cellphone);
        novoProfissional.setEspecialidade(especialidade);

        profissionalDao.add(novoProfissional);

        textName.setText("");
        textCellphone.setText("");
        textEspecialidade.setText("");
        textCodigo.setText("");
        errorLabel.setText("");

        List<Profissional> results = profissionalDao.findAllPessoas();

        profissionais.clear();
        profissionais.addAll(results);
    }

}
