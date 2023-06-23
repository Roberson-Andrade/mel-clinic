/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import DAO.ProcedimentoDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import entidades.Procedimento;
import java.math.BigDecimal;
import java.util.List;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class TelaNovoProcedimentoController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private Label errorLabel;
    
    final ProcedimentoDAO procedimentoDao = new ProcedimentoDAO();
    ObservableList<Procedimento> procedimentos;
    @FXML
    private TextField textCodigo;
    @FXML
    private TextField textOrientacoes;
    @FXML
    private TextField textNumSessoes;
    @FXML
    private TextField textValue;
    @FXML
    private TextField textDescricao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        textNumSessoes.setText("1");
    }
    
    void setProcedimentos(ObservableList<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }
    
    private boolean validateInputs() {
        if(textCodigo.getText().equals("") || textNumSessoes.getText().equals("") || textValue.getText().equals("")) {
            errorLabel.setText("*Preencha os campos obrigatórios.");
            return false;
        }
        
        try{
            Integer.valueOf(textValue.getText());
        }
        catch (NumberFormatException ex){
            errorLabel.setText("Campo valor deve ser um número");
            return false;
        }
        
        try{
            Integer.valueOf(textCodigo.getText());
        }
        catch (NumberFormatException ex){
            errorLabel.setText("Campo codigo deve ser um número");
            return false;
        }
        
        try{
            Integer.valueOf(textNumSessoes.getText());
        }
        catch (NumberFormatException ex){
            errorLabel.setText("Campo número de sessões deve ser um número");
            return false;
        }
        
        
        return true;
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
        
        String codigo = textCodigo.getText();
        String descricao = textDescricao.getText();
        String numSessoes = textNumSessoes.getText();
        String orientacoes = textOrientacoes.getText();
        String value = textValue.getText();
        
        Procedimento novoProcedimento = new Procedimento();
        novoProcedimento.setCodigo(Integer.parseInt(codigo));
        novoProcedimento.setNumSessao(Integer.parseInt(numSessoes));
        novoProcedimento.setValor(new BigDecimal(value));
        
        if(!descricao.equals("")) {
            novoProcedimento.setDescricao(descricao);
        }
        
        if(!orientacoes.equals("")) {
            novoProcedimento.setOrientacoes(orientacoes);
        }
        
        procedimentoDao.add(novoProcedimento);
        textCodigo.setText("");
        textDescricao.setText("");
        textNumSessoes.setText("");
        textOrientacoes.setText("");
        textValue.setText("");
        
        List<Procedimento> results = procedimentoDao.findAllProcedimentos();
        
        procedimentos.clear();
        procedimentos.addAll(results);
    }
    
}
