/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlesTelas;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("aaaaaaaaaaaaa");
    }    

    @FXML
    private void onClickBotaoAgenda(ActionEvent event) throws IOException {
        AnchorPane agendaView = FXMLLoader.load(getClass().getResource("/telas/TelaAgenda.fxml"));
        
        container.setCenter(agendaView);
    }

    @FXML
    private void onClickBotaoClientes(ActionEvent event) throws IOException {
        AnchorPane clientesView = FXMLLoader.load(getClass().getResource("/telas/TelaClientes.fxml"));
        
        container.setCenter(clientesView);
    }
    
}
