package aplicacao;
import dados.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Queue;

    public class LeituraDeDados extends JFrame {

        private JTextArea terminalArea; // Área de texto para simular o terminal

        public LeituraDeDados(ArrayList<Drone> dronesCadastrados, ArrayList<Transporte> transportesPendentes, Queue<Transporte> filaTransportesPendentes) {
            // Configuração da janela "terminal"
            setTitle("Terminal de Simulação");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            terminalArea = new JTextArea();
            terminalArea.setEditable(false);
            terminalArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(terminalArea);
            add(scrollPane, BorderLayout.CENTER);

            setLocationRelativeTo(null);
            setVisible(true);

            // Iniciar o processo de carregamento
            carregarDados(dronesCadastrados, transportesPendentes, filaTransportesPendentes);
        }

        private void carregarDados(ArrayList<Drone> dronesCadastrados, ArrayList<Transporte> transportesPendentes, Queue<Transporte> filaTransportesPendentes) {
            // Pergunta ao usuário se deseja carregar dados de drones ou transportes
            String[] options = {"Drones", "Transportes"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Escolha o tipo de dados para carregar:",
                    "Carregar Dados",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            // Carregar dados de drones ou transportes conforme a escolha do usuário
            if (escolha == 0) { // Drones
                String arquivoDrones = JOptionPane.showInputDialog(
                        this,
                        "Digite o nome do arquivo de drones (sem extensão):",
                        "Entrada de Dados - Drones",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (arquivoDrones == null || arquivoDrones.trim().isEmpty()) {
                    adicionarMensagem("Operação cancelada pelo usuário ao inserir arquivo de drones.");
                } else {
                    carregarArquivoDrones(arquivoDrones + ".csv", dronesCadastrados);
                }
            } else if (escolha == 1) { // Transportes
                String arquivoTransportes = JOptionPane.showInputDialog(
                        this,
                        "Digite o nome do arquivo de transportes (sem extensão):",
                        "Entrada de Dados - Transportes",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (arquivoTransportes == null || arquivoTransportes.trim().isEmpty()) {
                    adicionarMensagem("Operação cancelada pelo usuário ao inserir arquivo de transportes.");
                } else {
                    carregarArquivoTransportes(arquivoTransportes + ".csv", transportesPendentes, filaTransportesPendentes);
                }
            }
        }

        private void carregarArquivoDrones(String nomeArquivo, ArrayList<Drone> dronesCadastrados) {
            adicionarMensagem("Iniciando a leitura do arquivo de drones: " + nomeArquivo);

            try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    try {
                        Drone drone = processarDrone(linha);
                        dronesCadastrados.add(drone); // Armazena na lista de drones
                        adicionarMensagem("Drone carregado: " + drone);
                    } catch (Exception e) {
                        adicionarMensagem("Erro ao processar linha do arquivo de drones: " + linha + " - " + e.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                adicionarMensagem("Arquivo de drones não encontrado: " + nomeArquivo);
            } catch (IOException e) {
                adicionarMensagem("Erro ao ler o arquivo de drones: " + nomeArquivo);
            }
        }

        private void carregarArquivoTransportes(String nomeArquivo, ArrayList<Transporte> transportesPendentes, Queue<Transporte> filaTransportesPendentes) {
            adicionarMensagem("Iniciando a leitura do arquivo de transportes: " + nomeArquivo);

            try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    try {
                        Transporte transporte = processarTransporte(linha);
                        transporte.setSituacao(Estado.PENDENTE);
                        transportesPendentes.add(transporte);
                        filaTransportesPendentes.add(transporte);
                        adicionarMensagem("Transporte carregado: " + transporte);
                    } catch (Exception e) {
                        adicionarMensagem("Erro ao processar linha do arquivo de transportes: " + linha + " - " + e.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                adicionarMensagem("Arquivo de transportes não encontrado: " + nomeArquivo);
            } catch (IOException e) {
                adicionarMensagem("Erro ao ler o arquivo de transportes: " + nomeArquivo);
            }
        }

        private void adicionarMensagem(String mensagem) {
            terminalArea.append(mensagem + "\n");
        }

        private Drone processarDrone(String linha) {
            String[] partes = linha.split(";");
            if (partes.length < 5) throw new IllegalArgumentException("Formato de drone inválido");

            int tipo = Integer.parseInt(partes[0]);
            int codigo = Integer.parseInt(partes[1]);
            double custo = Double.parseDouble(partes[2]);
            double autonomia = Double.parseDouble(partes[3]);

            switch (tipo) {
                case 1: // DronePessoal
                    int qtdMaxPessoas = Integer.parseInt(partes[4]);
                    return new DronePessoal(codigo, custo, autonomia, qtdMaxPessoas);

                case 2: // DroneCargaInanimada
                    double pesoMaximoInanimada = Double.parseDouble(partes[4]);
                    boolean protecao = Boolean.parseBoolean(partes[5]);
                    return new DroneCargaInanimada(codigo, custo, autonomia, pesoMaximoInanimada, protecao);

                case 3: // DroneCargaViva
                    double pesoMaximoViva = Double.parseDouble(partes[4]);
                    boolean climatizado = Boolean.parseBoolean(partes[5]);
                    return new DroneCargaViva(codigo, custo, autonomia, pesoMaximoViva, climatizado);

                default:
                    throw new IllegalArgumentException("Tipo de drone inválido: " + tipo);
            }
        }

        private Transporte processarTransporte(String linha) {
            String[] partes = linha.split(";");
            if (partes.length < 10) throw new IllegalArgumentException("Formato de transporte inválido");

            int tipo = Integer.parseInt(partes[0]);
            int numero = Integer.parseInt(partes[1]);
            String nomeCliente = partes[2];
            String descricao = partes[3];
            double peso = Double.parseDouble(partes[4]);
            double latOrigem = Double.parseDouble(partes[5]);
            double longOrigem = Double.parseDouble(partes[6]);
            double latDestino = Double.parseDouble(partes[7]);
            double longDestino = Double.parseDouble(partes[8]);

            switch (tipo) {
                case 1: // Transporte Pessoal
                    int qtdPessoas = Integer.parseInt(partes[9]);
                    return new TransportePessoal(numero, nomeCliente, descricao, peso, latOrigem, longOrigem, latDestino, longDestino, qtdPessoas);

                case 2: // Transporte Perigoso
                    boolean perigoso = Boolean.parseBoolean(partes[9]);
                    return new TransporteCargaInanimada(numero, nomeCliente, descricao, peso, latOrigem, longOrigem, latDestino, longDestino, perigoso);

                case 3: // Transporte com Temperatura Controlada
                    double tempMin = Double.parseDouble(partes[9]);
                    double tempMax = Double.parseDouble(partes[10]);
                    return new TransporteCargaViva(numero, nomeCliente, descricao, peso, latOrigem, longOrigem, latDestino, longDestino, tempMin, tempMax);

                default:
                    throw new IllegalArgumentException("Tipo de transporte inválido: " + tipo);
            }
        }
    }

