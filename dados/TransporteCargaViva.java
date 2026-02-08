package dados;

public class TransporteCargaViva extends Transporte {

    private double temperaturaMinima;
    private double temperaturaMaxima;

    public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, Estado situacao, double temperaturaMinima, double temperaturaMaxima) {
        super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino, situacao);
        this.temperaturaMinima = temperaturaMinima;
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public double acrescimos(){
        double diferencaTemperatura = temperaturaMaxima - temperaturaMinima;
        if(diferencaTemperatura > 10){
            return 1000;
        }
        return 0;
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

    public double calculaCusto() {
        return (getDrone().calculaCustoKm() * calcularDistancia()) + acrescimos();
    }




}