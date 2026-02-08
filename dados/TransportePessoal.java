package dados;

public class TransportePessoal extends Transporte {

    private int qtdPessoas;

    public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, Estado situacao, int qtdPessoas) {
        super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino, situacao);
        this.qtdPessoas = qtdPessoas;
    }

    public int getQtdPessoas() {
        return qtdPessoas;
    }

    public double calculaCusto() {
        return (getDrone().calculaCustoKm() * calcularDistancia()) + acrescimos();
    }

    public double acrescimos(){
        return qtdPessoas * 10;
    }

    public double calcularDistancia() {
        double RAIO_TERRA_KM = 6371.0;

        double latOrigemRad = Math.toRadians(getLatitudeOrigem());
        double lonOrigemRad = Math.toRadians(getLongitudeOrigem());
        double latDestinoRad = Math.toRadians(getLatitudeDestino());
        double lonDestinoRad = Math.toRadians(getLongitudeDestino());


        double deltaLat = latDestinoRad - latOrigemRad;
        double deltaLon = lonDestinoRad - lonOrigemRad;


        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(latOrigemRad) * Math.cos(latDestinoRad) *
                        Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RAIO_TERRA_KM * c;
    }
}