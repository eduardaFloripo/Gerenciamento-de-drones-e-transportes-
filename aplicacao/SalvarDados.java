package aplicacao;

import dados.Drone;
import dados.Transporte;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SalvarDados {

    private ArrayList<Drone> drones;
    private ArrayList<Transporte> transportes;

    public SalvarDados(ArrayList<Drone> drones, ArrayList<Transporte> transportes) {
        this.drones = drones;
        this.transportes = transportes;
    }

    public void salvar() {
        // Solicita ao usuário o nome do arquivo
        String nomeArquivo = JOptionPane.showInputDialog(null,
                "Digite o nome do arquivo (sem extensão):",
                "Salvar Dados", JOptionPane.PLAIN_MESSAGE);

        if (nomeArquivo == null || nomeArquivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Erro: Nome de arquivo inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Adiciona a extensão .txt ao nome do arquivo
        nomeArquivo = nomeArquivo.trim() + ".txt";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            // Salvar informações dos drones
            writer.write("=== Drones ===\n");
            if (drones.isEmpty()) {
                writer.write("Nenhum drone cadastrado.\n");
            } else {
                for (Drone drone : drones) {
                    writer.write("Código: " + drone.getCodigo() + "\n");
                    writer.write("Autonomia: " + drone.getAutonomia() + "\n");
                    writer.write("Disponível: " + (drone.isDisponivel() ? "Sim" : "Não") + "\n");

                    if (drone instanceof dados.DronePessoal) {
                        writer.write("Quantidade Máxima de Pessoas: " +
                                ((dados.DronePessoal) drone).getQtdMaxPessoas() + "\n");
                    } else if (drone instanceof dados.DroneCargaInanimada) {
                        writer.write("Peso Máximo: " +
                                ((dados.DroneCargaInanimada) drone).getPesoMaximo() + "\n");
                        writer.write("Proteção: " +
                                (((dados.DroneCargaInanimada) drone).getProtecao() ? "Sim" : "Não") + "\n");
                    } else if (drone instanceof dados.DroneCargaViva) {
                        writer.write("Peso Máximo: " +
                                ((dados.DroneCargaViva) drone).getPesoMaximo() + "\n");
                        writer.write("Climatizado: " +
                                (((dados.DroneCargaViva) drone).getClimatizado() ? "Sim" : "Não") + "\n");
                    }

                    writer.write("---------------------------------\n");
                }
            }

            // Salvar informações dos transportes
            writer.write("\n=== Transportes ===\n");
            if (transportes.isEmpty()) {
                writer.write("Nenhum transporte cadastrado.\n");
            } else {
                for (Transporte transporte : transportes) {
                    writer.write("Número: " + transporte.getNumero() + "\n");
                    writer.write("Cliente: " + transporte.getNomeCliente() + "\n");
                    writer.write("Descrição: " + transporte.getDescricao() + "\n");
                    writer.write("Peso: " + transporte.getPeso() + "kg\n");
                    writer.write("Origem: (" + transporte.getLatitudeOrigem() + ", " + transporte.getLongitudeOrigem() + ")\n");
                    writer.write("Destino: (" + transporte.getLatitudeDestino() + ", " + transporte.getLongitudeDestino() + ")\n");
                    writer.write("Situação: " + transporte.getSituacao() + "\n");

                    if (transporte.getDrone() != null) {
                        writer.write("Drone Alocado: " + transporte.getDrone().getCodigo() + "\n");
                    } else {
                        writer.write("Drone Alocado: Nenhum\n");
                    }

                    writer.write("Custo: R$" + transporte.calculaCusto() + "\n");
                    writer.write("---------------------------------\n");
                }
            }

            JOptionPane.showMessageDialog(null,
                    "Dados salvos com sucesso em " + nomeArquivo,
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao salvar os dados: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
