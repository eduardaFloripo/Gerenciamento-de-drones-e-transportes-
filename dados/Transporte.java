package dados;

public abstract class Transporte {

    private int numero;
    private String nomeCliente;
    private String descricao;
    private double peso;
    private double latitudeOrigem;
    private double latitudeDestino;
    private double longitudeOrigem;
    private double longitudeDestino;
    private Estado situacao;
    private Drone drone;

    public Transporte(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, Estado situacao) {
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.peso = peso;
        this.latitudeOrigem = latitudeOrigem;
        this.latitudeDestino = latitudeDestino;
        this.longitudeOrigem = longitudeOrigem;
        this.longitudeDestino = longitudeDestino;
        this.situacao = Estado.PENDENTE;
    }

    public int getNumero() {
        return numero;
    }

    public Drone getDrone() {
        return drone;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPeso() {
        return peso;
    }

    public double getLatitudeOrigem() {
        return latitudeOrigem;
    }

    public double getLatitudeDestino() {
        return latitudeDestino;
    }

    public double getLongitudeOrigem() {
        return longitudeOrigem;
    }

    public double getLongitudeDestino() {
        return longitudeDestino;
    }

    public Estado getSituacao() {
        return situacao;
    }

    public void setSituacao(Estado situacao) {
        this.situacao = situacao;
    }

    public abstract double calculaCusto();

    public void setDrone(Drone drone) {
        this.drone = drone;
    }



    public abstract double acrescimos();

}