/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package helpers;

/**
 *
 * @author rober
 */
public enum ScenePath {

    AGENDA("/telas/TelaAgenda.fxml"),
    CLIENTES("/telas/TelaClientes.fxml"),
    NOVO_CLIENTE("/telas/TelaNovoCliente.fxml");

    private final String path;

    private ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
