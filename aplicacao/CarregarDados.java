package aplicacao;

import dados.Drone;
import dados.DroneCargaInanimada;
import dados.DroneCargaViva;
import dados.DronePessoal;
import dados.Transporte;
import dados.TransportePessoal;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CarregarDados {
    private ArrayList<Drone> drones;
    private ArrayList<Transporte> transportes;

    public CarregarDados(ArrayList<Drone> drones, ArrayList<Transporte> transportes) {
        this.drones = drones;
        this.transportes = transportes;
    }

    public void carregar() {
        // Solicita ao usuário o nome do arquivo
        String nomeArquivo = JOptionPane.showInputDialog(null,
                "Digite o nome do arquivo (sem extensão):",
                "Carregar Dados", JOptionPane.PLAIN_MESSAGE);

        if (nomeArquivo == null || nomeArquivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Erro: Nome de arquivo inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        nomeArquivo = nomeArquivo.trim() + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Drone:")) {
                    // Carrega informações de drones
                    Drone drone = carregarDrone(reader);
                    if (drone != null) {
                        drones.add(drone);
                    }
                } else if (linha.startsWith("Transporte:")) {
                    // Carrega informações de transportes
                    Transporte transporte = carregarTransporte(reader);
                    if (transporte != null) {
                        transportes.add(transporte);
                    }
                }
            }

            JOptionPane.showMessageDialog(null,
                    "Dados carregados com sucesso do arquivo " + nomeArquivo,
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar os dados: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Drone carregarDrone(BufferedReader reader) throws IOException {
        String linha;
        int codigo = 0;
        double autonomia = 0;
        boolean disponivel = true;
        String tipo = "";

        while ((linha = reader.readLine()) != null && !linha.isEmpty()) {
            if (linha.startsWith("Código:")) {
                codigo = Integer.parseInt(linha.split(":")[1].trim());
            } else if (linha.startsWith("Autonomia:")) {
                autonomia = Double.parseDouble(linha.split(":")[1].trim());
            } else if (linha.startsWith("Disponível:")) {
                disponivel = linha.split(":")[1].trim().equalsIgnoreCase("Sim");
            } else if (linha.startsWith("Tipo:")) {
                tipo = linha.split(":")[1].trim();
            }
        }

        if (tipo.equals("Pessoal")) {
            return new DronePessoal(codigo, 0, autonomia, 0); // Ajuste os parâmetros conforme necessário
        } else if (tipo.equals("Carga Inanimada")) {
            return new DroneCargaInanimada(codigo, 0, autonomia, 0, true);
        } else if (tipo.equals("Carga Viva")) {
            return new DroneCargaViva(codigo, 0, autonomia, 0, true);
        }

        return null;
    }

    private Transporte carregarTransporte(BufferedReader reader) throws IOException {
        String linha;
        int numero = 0;
        String cliente = "";
        String descricao = "";
        double peso = 0;

        while ((linha = reader.readLine()) != null && !linha.isEmpty()) {
            if (linha.startsWith("Número:")) {
                numero = Integer.parseInt(linha.split(":")[1].trim());
            } else if (linha.startsWith("Cliente:")) {
                cliente = linha.split(":")[1].trim();
            } else if (linha.startsWith("Descrição:")) {
                descricao = linha.split(":")[1].trim();
            } else if (linha.startsWith("Peso:")) {
                peso = Double.parseDouble(linha.split(":")[1].trim());
            }
        }

        return new TransportePessoal(numero, cliente, descricao, peso, 0, 0, 0, 0, null, 0);
    }
}

