package aplicacao;
import dados.Transporte;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MostrarTransporte extends JFrame{
    public MostrarTransporte(List<Transporte> transportesCadastrados) {
        setTitle("Lista de Transportes");
        setSize(800, 400);
        setLayout(new BorderLayout());

        // Configurando os dados para a tabela
        String[] colunas = {"Número", "Cliente", "Descrição", "Peso (kg)", "Origem", "Destino", "Situação", "Drone Alocado", "Custo Total"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        // Obter a lista de transportes

        if (transportesCadastrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum transporte cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            dispose(); // Fecha a janela se não houver transportes
            return;
        }

        for (Transporte transporte : transportesCadastrados) {
            String origem = "(" + transporte.getLatitudeOrigem() + ", " + transporte.getLongitudeOrigem() + ")";
            String destino = "(" + transporte.getLatitudeDestino() + ", " + transporte.getLongitudeDestino() + ")";
            String droneAlocado = transporte.getDrone() != null ? String.valueOf(transporte.getDrone().getCodigo()) : "Nenhum";
            String custoTotal = transporte.getDrone() != null ? String.format("R$ %.2f", transporte.calculaCusto()) : "-";

            modelo.addRow(new Object[]{
                    transporte.getNumero(),
                    transporte.getNomeCliente(),
                    transporte.getDescricao(),
                    transporte.getPeso(),
                    origem,
                    destino,
                    transporte.getSituacao(),
                    droneAlocado,
                    custoTotal
            });
        }

        // Criando a tabela
        JTable tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);

        // Adicionar tabela à janela
        add(scrollPane, BorderLayout.CENTER);

        // Configuração da janela
        setLocationRelativeTo(null);
        setVisible(true);
    }

}