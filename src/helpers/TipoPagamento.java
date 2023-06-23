/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package helpers;

/**
 *
 * @author rober
 */
public enum TipoPagamento {
    CARTAO_CREDITO("CARTAO_CREDITO"),
    DINHEIRO("DINHEIRO"),
    DEPOSITO("DEPOSITO"),
    CONVENIO("CONVENIO");
    

    private final String tipo;

    private TipoPagamento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
