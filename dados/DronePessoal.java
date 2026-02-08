package dados;

public class DronePessoal extends Drone {

    private int qtdMaxPessoas;

    public DronePessoal(int codigo, double custoFixo, double autonomia, int qtdMaxPessoas) {
        super(codigo, custoFixo, autonomia);
        this.qtdMaxPessoas = qtdMaxPessoas;
    }

    @Override
    public double calculaCustoKm() {
        return getCustoFixo() + custoVariado();
    }

    public double custoVariado(){
        return qtdMaxPessoas * 2;
    }

    public int getQtdMaxPessoas() {
        return qtdMaxPessoas;
    }
}