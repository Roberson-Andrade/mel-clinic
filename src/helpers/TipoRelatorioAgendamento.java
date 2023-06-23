/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package helpers;

/**
 *
 * @author rober
 */
public enum TipoRelatorioAgendamento {
    POR_DIA("Por dia"),
    POR_PROFISSIONAL("Por profissional");
    

    private final String tipo;

    private TipoRelatorioAgendamento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}


