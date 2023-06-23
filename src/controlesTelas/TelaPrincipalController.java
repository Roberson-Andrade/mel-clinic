/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

import helpers.ScenePath;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaPrincipalController implements Initializable {

    @FXML
    private Button botaoAgenda;
    @FXML
    private BorderPane container;
    @FXML
    private Button botaoClientes;
    @FXML
    private Button botaoAnimais;
    @FXML
    private Button botaoProfissionais;
    @FXML
    private Button botaoEspecies;
    @FXML
    private Button botaoProcedimentos;
    @FXML
    private Button botaoAgendamentosRelatorio;
    @FXML
    private Button botaoProcedimentosRelatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void onClickBotaoAgenda(ActionEvent event) throws IOException {
        AnchorPane agendaView = FXMLLoader.load(getClass().getResource(ScenePath.AGENDAMENTOS.getPath()));
        
        container.setCenter(agendaView);
    }

    @FXML
    private void onClickBotaoClientes(ActionEvent event) throws IOException {
        AnchorPane clientesView = FXMLLoader.load(getClass().getResource(ScenePath.CLIENTES.getPath()));
        
        container.setCenter(clientesView);
    }

    @FXML
    private void onClickBotaoAnimais(ActionEvent event) throws IOException {
        AnchorPane animaisView = FXMLLoader.load(getClass().getResource(ScenePath.ANIMAIS.getPath()));
        
        container.setCenter(animaisView);
    }

    @FXML
    private void onClickBotaoProfissionais(ActionEvent event) throws IOException {
        AnchorPane profissionaisView = FXMLLoader.load(getClass().getResource(ScenePath.PROFISSIONAIS.getPath()));
        
        container.setCenter(profissionaisView);
    }

    @FXML
    private void onClickBotaoEspecies(ActionEvent event) throws IOException {
        AnchorPane especiesView = FXMLLoader.load(getClass().getResource(ScenePath.ESPECIES.getPath()));
        
        container.setCenter(especiesView);
    }

    @FXML
    private void onClickBotaoProcedimentos(ActionEvent event) throws IOException {
        AnchorPane procedimentosView = FXMLLoader.load(getClass().getResource(ScenePath.PROCEDIMENTOS.getPath()));
        
        container.setCenter(procedimentosView);
    }

    @FXML
    private void onClickBotaoAgendamentosRelatorio(ActionEvent event) throws IOException {
        AnchorPane agendamentosRelatoriosView = FXMLLoader.load(getClass().getResource(ScenePath.AGENDAMENTOS_RELATORIO.getPath()));
        
        container.setCenter(agendamentosRelatoriosView);
    }

    @FXML
    private void onClickBotaoProcedimentosRelatorio(ActionEvent event) throws IOException {
        AnchorPane procedimentosRelatorioView = FXMLLoader.load(getClass().getResource(ScenePath.PROCEDIMENTOS_RELATORIO.getPath()));
        
        container.setCenter(procedimentosRelatorioView);
    }
    
    
}
