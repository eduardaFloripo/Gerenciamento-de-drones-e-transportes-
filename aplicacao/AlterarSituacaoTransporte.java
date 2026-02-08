package aplicacao;
import dados.Transporte;
import dados.Estado;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AlterarSituacaoTransporte extends JFrame {

    public AlterarSituacaoTransporte(ArrayList<Transporte> transportesCadastrados) {
        setTitle("Alterar Situação de Transporte");
        setSize(500, 400);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Campos de entrada
        JTextField txtNumeroTransporte = new JTextField();
        JTextArea txtDetalhesTransporte = new JTextArea();
        txtDetalhesTransporte.setEditable(false); // Apenas exibição

        JComboBox<String> cmbNovaSituacao = new JComboBox<>(new String[]{"PENDENTE", "ALOCADO", "TERMINADO", "CANCELADO"});
        JButton btnBuscar = new JButton("Buscar Transporte");
        JButton btnAlterar = new JButton("Alterar Situação");

        // Painel para exibição e interação
        add(new JLabel("Número do Transporte:"));
        add(txtNumeroTransporte);
        add(new JLabel("Detalhes do Transporte:"));
        add(new JScrollPane(txtDetalhesTransporte));
        add(new JLabel("Nova Situação:"));
        add(cmbNovaSituacao);
        add(btnBuscar);
        add(btnAlterar);

        // Ação para buscar transporte
        btnBuscar.addActionListener(e -> {
            try {
                int numeroTransporte = Integer.parseInt(txtNumeroTransporte.getText().trim());


                Transporte transporte = transportesCadastrados.stream()
                        .filter(t -> t.getNumero() == numeroTransporte)
                        .findFirst()
                        .orElse(null);

                if (transporte == null) {
                    txtDetalhesTransporte.setText("Transporte não encontrado.");
                    return;
                }

                // Exibir os detalhes do transporte
                txtDetalhesTransporte.setText(
                        "Número: " + transporte.getNumero() + "\n" +
                                "Cliente: " + transporte.getNomeCliente() + "\n" +
                                "Descrição: " + transporte.getDescricao() + "\n" +
                                "Peso: " + transporte.getPeso() + " kg\n" +
                                "Origem: (" + transporte.getLatitudeOrigem() + ", " + transporte.getLongitudeOrigem() + ")\n" +
                                "Destino: (" + transporte.getLatitudeDestino() + ", " + transporte.getLongitudeDestino() + ")\n" +
                                "Situação Atual: " + transporte.getSituacao()
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O número do transporte deve ser um valor inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação para alterar a situação
        btnAlterar.addActionListener(e -> {
            try {
                int numeroTransporte = Integer.parseInt(txtNumeroTransporte.getText().trim());


                Transporte transporte = transportesCadastrados.stream()
                        .filter(t -> t.getNumero() == numeroTransporte)
                        .findFirst()
                        .orElse(null);

                if (transporte == null) {
                    JOptionPane.showMessageDialog(this, "Transporte não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar situação atual
                if (transporte.getSituacao() == Estado.TERMINADO || transporte.getSituacao() == Estado.CANCELADO) {
                    JOptionPane.showMessageDialog(this, "Não é possível alterar a situação de um transporte TERMINADO ou CANCELADO.", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Alterar situação
                Estado novaSituacao = Estado.valueOf((String) cmbNovaSituacao.getSelectedItem());
                transporte.setSituacao(novaSituacao);

                JOptionPane.showMessageDialog(this, "Situação alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtDetalhesTransporte.setText("Situação atualizada para: " + novaSituacao);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O número do transporte deve ser um valor inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao alterar situação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Configurar e exibir a janela
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

