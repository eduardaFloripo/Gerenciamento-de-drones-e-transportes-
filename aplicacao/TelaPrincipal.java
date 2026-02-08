
package aplicacao;

import dados.Drone;
import dados.Transporte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TelaPrincipal {

    private ArrayList<Drone> drones = new ArrayList<>();
    private ArrayList<Transporte> transportesCadastrados = new ArrayList<>();
    private Queue<Transporte> filaTransportesPendentes = new LinkedList<>();

    public TelaPrincipal() {
        JFrame frame = new JFrame("Tela Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500); // Ajuste o tamanho da janela conforme necessário

        JLabel titulo = new JLabel("Menu", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20)); // Usando FlowLayout no centro

        // Botão para abrir a janela de cadastro de transporte
        JButton cadastroTransporte = new JButton("Cadastro Transporte");
        cadastroTransporte.setPreferredSize(new Dimension(300, 50));
        cadastroTransporte.addActionListener(e -> new CadastroTransporte(transportesCadastrados, filaTransportesPendentes));


        // Botão para abrir a janela de cadastro de drone
        JButton cadastroDrone = new JButton("Cadastro Drone");
        cadastroDrone.setPreferredSize(new Dimension(300, 50));
        cadastroDrone.addActionListener(e -> new CadastroDrone(drones));

        // Botão para abrir a janela de Mostrar Transportes
        JButton mostrarTransportes = new JButton("Mostrar Transportes");
        mostrarTransportes.setPreferredSize(new Dimension(300, 50));
        mostrarTransportes.addActionListener(e -> new MostrarTransporte(transportesCadastrados));


        // Botão para abrir a janela de Alterar Situação de um transporte
        JButton alterarSituacao = new JButton("Alterar Situação Transporte");
        alterarSituacao.setPreferredSize(new Dimension(300, 50));
        alterarSituacao.addActionListener(e -> new AlterarSituacaoTransporte(transportesCadastrados));

        // Botão para abrir a janela de Processar Transportes Pendentes
        JButton processarTransportes = new JButton("Processar Transportes");
        processarTransportes.setPreferredSize(new Dimension(300, 50));
        processarTransportes.addActionListener(e -> new ProcessarTransportesPendentes(drones, filaTransportesPendentes));

        // Botão para abrir a janela de Mostrar Relatório Geral
        JButton relatorioGeral = new JButton("Relatório Geral");
        relatorioGeral.setPreferredSize(new Dimension(300, 50));
        relatorioGeral.addActionListener(e -> new MostrarRelatorioGeral(drones, transportesCadastrados));

        // Botão para salvar dados
        JButton salvarDados = new JButton("Salvar Dados");
        salvarDados.setPreferredSize(new Dimension(300, 50));
        salvarDados.addActionListener(e -> {
            SalvarDados salvador = new SalvarDados(drones, transportesCadastrados);
            salvador.salvar();
        });

        JButton leituraDeDados = new JButton ("Leitura de dados");
        leituraDeDados.setPreferredSize(new Dimension(300,50));
        leituraDeDados.addActionListener(e -> new LeituraDeDados(drones, transportesCadastrados, filaTransportesPendentes));

        // Botão para carregar dados
        JButton carregarDados = new JButton("Carregar Dados");
        carregarDados.setPreferredSize(new Dimension(300, 50));
        carregarDados.addActionListener(e -> {
            CarregarDados carregador = new CarregarDados(drones, transportesCadastrados);
            carregador.carregar();
        });

        // Botão para finalizar o sistema
        JButton finalizarSistema = new JButton("Finalizar Sistema");
        finalizarSistema.setPreferredSize(new Dimension(300, 50));
        finalizarSistema.addActionListener(e -> {
            int confirmacao = JOptionPane.showConfirmDialog(
                    null,
                    "Você tem certeza que deseja finalizar o sistema?",
                    "Confirmar Saída",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                System.exit(0); // Encerra a execução do sistema
            }
        });

        panelBotoes.add(finalizarSistema);


        panelBotoes.add(cadastroTransporte);
        panelBotoes.add(cadastroDrone);
        panelBotoes.add(mostrarTransportes);
        panelBotoes.add(alterarSituacao);
        panelBotoes.add(processarTransportes);
        panelBotoes.add(relatorioGeral);
        panelBotoes.add(salvarDados);
        panelBotoes.add(leituraDeDados);
        panelBotoes.add(carregarDados);
        panelBotoes.add(finalizarSistema);

        JPanel panelPrincipal = new JPanel(new BorderLayout(20, 20));
        panelPrincipal.add(titulo, BorderLayout.NORTH); // Adiciona o título no topo
        panelPrincipal.add(panelBotoes, BorderLayout.CENTER); // Adiciona os botões no centro

        // Adiciona o painel ao frame
        frame.add(panelPrincipal);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}