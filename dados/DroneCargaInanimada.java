package dados;

public class DroneCargaInanimada extends DroneCarga {

    private boolean protecao;

    public DroneCargaInanimada(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean protecao) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
        this.protecao = protecao;
    }

    @Override
    public double calculaCustoKm() {
        return getCustoFixo() + custoVariado();
    }

    public double custoVariado(){
        if(protecao == true){
            return 10;
        }

        return 5;
    }

    public boolean getProtecao(){
        return protecao;
    }
}