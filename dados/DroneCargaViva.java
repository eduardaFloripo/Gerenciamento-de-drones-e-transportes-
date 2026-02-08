package dados;

public class DroneCargaViva extends DroneCarga {

    private boolean climatizado;

    public DroneCargaViva(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean climatizado) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
        this.climatizado = climatizado;
    }

    @Override
    public double calculaCustoKm() {
        return getCustoFixo() + custoVariado();
    }

    public double custoVariado(){
        if(climatizado == true){
            return 20;
        }
        return 10;
    }

    public boolean getClimatizado(){
        return climatizado;
    }
}
