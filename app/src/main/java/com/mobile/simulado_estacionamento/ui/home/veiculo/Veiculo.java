package com.mobile.simulado_estacionamento.ui.home.veiculo;

public class Veiculo {
    private String placa;
    private String dataHora;

    public Veiculo(String placa, String dataHora) {
        this.placa = placa;
        this.dataHora = dataHora;
    }

    public String getPlaca() { return placa; }
    public String getDataHora() { return dataHora; }
}
