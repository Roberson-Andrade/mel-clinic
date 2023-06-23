/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package helpers;

/**
 *
 * @author rober
 */
public enum TipoRelatorioProcedimento {
    POR_ANIMAL("Por animal"),
    POR_PROFISSIONAL("Por profissional");
    

    private final String tipo;

    private TipoRelatorioProcedimento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
