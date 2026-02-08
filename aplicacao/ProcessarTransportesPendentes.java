package aplicacao;

import dados.Transporte;
import dados.Drone;
import dados.Estado;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;

public class ProcessarTransportesPendentes extends JFrame{
    public ProcessarTransportesPendentes(ArrayList<Drone> dronesDisponiveis, Queue<Transporte> filaTransportesPendentes) {


        JFrame frame = new JFrame("Processar Transportes Pendentes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton btnProcessar = new JButton("Processar Transportes");
        btnProcessar.addActionListener(e -> {
            if (filaTransportesPendentes.isEmpty()) {
                textArea.append("Erro: Não há transportes na fila de transportes pendentes.\n");
                return;
            }

            int transportesProcessados = 0;

            // Processa cada transporte da fila
            int tamanhoFila = filaTransportesPendentes.size();
            for (int i = 0; i < tamanhoFila; i++) {
                Transporte transporte = filaTransportesPendentes.poll();
                Drone droneDisponivel = buscarDroneDisponivel(transporte, dronesDisponiveis);

                if (droneDisponivel != null) {
                    transporte.setDrone(droneDisponivel);
                    transporte.setSituacao(Estado.ALOCADO);
                    droneDisponivel.setDisponivel(false);
                    textArea.append("Transporte " + transporte.getNumero() + " alocado ao drone " + droneDisponivel.getCodigo() + ".\n");
                    transportesProcessados++;
                } else {
                    filaTransportesPendentes.add(transporte);
                    textArea.append("Nenhum drone disponível para o transporte " + transporte.getNumero() + ". Retornando à fila.\n");
                }
            }

            if (transportesProcessados == 0) {
                textArea.append("Nenhum transporte foi alocado.\n");
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnProcessar, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Drone buscarDroneDisponivel(Transporte transporte, List<Drone> dronesDisponiveis) {
        for (Drone drone : dronesDisponiveis) {
            if (drone.isDisponivel()) {
                return drone;
            }
        }
        return null;
    }
}

