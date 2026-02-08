package dados;

import java.util.Collection;

public abstract class Drone {
    private int codigo;
    private double custoFixo;
    private double autonomia;
    private boolean disponivel;
    private double capacidadeCarga;
    private String identificador;
    private String modelo;
    private Collection<Transporte> transporte;

    public Drone(int codigo, double custoFixo, double autonomia) {
        this.codigo = codigo;
        this.custoFixo = custoFixo;
        this.autonomia = autonomia;
    }

    public Drone(String text) {
    }

    public int getCodigo() {
        return codigo;
    }

    public double getCustoFixo() {
        return custoFixo;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public abstract double calculaCustoKm();

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getModelo() {
        return modelo;
    }
}
