package com.mobile.simulado_estacionamento.ui.home.veiculo;

public class Veiculo {

    private int id; // ID agora é numérico
    private String placa;
    private String entrada;
    private String saida;
    private boolean inside;

    public Veiculo(String placa, String entrada) {
        this.id = 1;
        this.placa = placa;
        this.entrada = entrada;
        this.inside = true;
    }

    public Veiculo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlaca() { return placa; }
    public String getEntrada() { return entrada; }

    public String getSaida() { return saida; }
    public void setSaida(String saida) { this.saida = saida; }

    public boolean isInside() { return inside; }
    public void setInside(boolean inside ) { this.inside = inside; }
}
