package aplicacao;
import dados.Drone;
import dados.DroneCarga;
import dados.DroneCargaInanimada;
import dados.DroneCargaViva;
import dados.DronePessoal;
import dados.Transporte;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MostrarRelatorioGeral extends JFrame{
    public MostrarRelatorioGeral(ArrayList<Drone> drones, ArrayList<Transporte> transportesCadastrados) {


        JFrame frame = new JFrame("Relatório Geral");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Verificar se há dados cadastrados
        if (drones.isEmpty() && transportesCadastrados.isEmpty()) {
            textArea.setText("Erro: Não há dados cadastrados.\n");
        } else {
            // Relatório de Drones
            if (!drones.isEmpty()) {
                textArea.append("=== Drones ===\n");
                for (Drone drone : drones) {
                    textArea.append("Código: " + drone.getCodigo() + "\n");
                    textArea.append("Capacidade de Carga: " + drone.getAutonomia() + "kg\n");
                    textArea.append("Disponível: " + (drone.isDisponivel() ? "Sim" : "Não") + "\n");
                    // textArea.append("Modelo: " + drone.getModelo() + "\n");

                    if(drone instanceof DronePessoal){
                        DronePessoal dronePessoal = (DronePessoal) drone;
                        textArea.append("Quantidade maxima pessoas: " + dronePessoal.getQtdMaxPessoas() + "\n");
                    }

                    if(drone instanceof DroneCarga){
                        DroneCarga droneCarga = (DroneCarga) drone;
                        textArea.append("Peso máximo: "+ droneCarga.getPesoMaximo() + "\n");
                        if(drone instanceof DroneCargaInanimada){
                            DroneCargaInanimada droneCargaInanimada = (DroneCargaInanimada) drone;
                            if(droneCargaInanimada.getProtecao()){
                                textArea.append("Proteção: " + " Sim" + "\n");
                            }
                            else{
                                textArea.append("Proteção: " + "Não" + "\n");
                            }
                        }

                        if(drone instanceof DroneCargaViva){
                            DroneCargaViva droneCargaViva = (DroneCargaViva) drone;
                            if(droneCargaViva.getClimatizado()){
                                textArea.append("Climatizado: " + "Sim" + "\n");
                            }
                            else{
                                textArea.append("Climatizado: " + "Não" + "\n");
                            }
                        }
                    }
                    textArea.append("---------------------------------\n");
                }
            } else {
                textArea.append("Nenhum drone cadastrado.\n");
            }

            textArea.append("\n");

            // Relatório de Transportes
            if (!transportesCadastrados.isEmpty()) {
                textArea.append("=== Transportes ===\n");
                for (Transporte transporte : transportesCadastrados) {
                    textArea.append("Número: " + transporte.getNumero() + "\n");
                    textArea.append("Cliente: " + transporte.getNomeCliente() + "\n");
                    textArea.append("Descrição: " + transporte.getDescricao() + "\n");
                    textArea.append("Peso: " + transporte.getPeso() + "kg\n");
                    textArea.append("Origem (Lat, Long): (" + transporte.getLatitudeOrigem() + ", " + transporte.getLongitudeOrigem() + ")\n");
                    textArea.append("Destino (Lat, Long): (" + transporte.getLatitudeDestino() + ", " + transporte.getLongitudeDestino() + ")\n");
                    textArea.append("Situação: " + transporte.getSituacao() + "\n");
                    if (transporte.getDrone() != null) {
                        textArea.append("Drone Alocado: " + transporte.getDrone().getCodigo() + "\n");
                    } else {
                        textArea.append("Drone Alocado: Nenhum\n");
                    }
                    textArea.append("Custo: R$" + transporte.calculaCusto() + "\n");
                    textArea.append("---------------------------------\n");
                }
            } else {
                textArea.append("Nenhum transporte cadastrado.\n");
            }
        }

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}