
package aplicacao;

import javax.swing.*;
import dados.Estado;
import dados.Transporte;
import dados.TransporteCargaInanimada;
import dados.TransporteCargaViva;
import dados.TransportePessoal;
import java.util.ArrayList;
import java.util.Queue;

public class CadastroTransporte {

    public CadastroTransporte(ArrayList<Transporte> transportesCadastrados, Queue<Transporte> filaTransportesPendentes) {
        abrirJanelaEscolhaTipoTransporte(transportesCadastrados, filaTransportesPendentes);
    }

    private void abrirJanelaEscolhaTipoTransporte(ArrayList<Transporte> transportesCadastrados, Queue<Transporte> filaTransportesPendentes) {
        String[] opcoes = {"Transporte Pessoal", "Transporte Carga Inanimada", "Transporte Carga Viva"};
        int escolha = JOptionPane.showOptionDialog(null, "Escolha o tipo de transporte",
                "Escolher Tipo de Transporte", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opcoes, opcoes[0]);

        if (escolha != -1) {
            abrirJanelaCadastroTransporte(escolha, transportesCadastrados, filaTransportesPendentes);
        }
    }

    private void abrirJanelaCadastroTransporte(int tipoTransporte, ArrayList<Transporte> transportesCadastrados,
                                               Queue<Transporte> filaTransportesPendentes) {
        JTextField txtNumeroTransporte = new JTextField();
        JTextField txtNomeCliente = new JTextField();
        JTextField txtDescricao = new JTextField();
        JTextField txtPeso = new JTextField();
        JTextField txtLatitudeOrigem = new JTextField();
        JTextField txtLatitudeDestino = new JTextField();
        JTextField txtLongitudeOrigem = new JTextField();
        JTextField txtLongitudeDestino = new JTextField();
        JComboBox<Estado> cmbSituacao = new JComboBox<>(new Estado[]{Estado.PENDENTE});
        cmbSituacao.setEnabled(false);

        JTextField txtQuantidadePessoas = new JTextField();
        JCheckBox chkCargaPerigosa = new JCheckBox("Carga Perigosa");
        JTextField txtTemperaturaMinima = new JTextField();
        JTextField txtTemperaturaMaxima = new JTextField();

        while (true) {
            Object[] campos = {
                    "Número do Transporte:", txtNumeroTransporte,
                    "Nome do Cliente:", txtNomeCliente,
                    "Descrição:", txtDescricao,
                    "Peso:", txtPeso,
                    "Latitude de Origem:", txtLatitudeOrigem,
                    "Latitude de Destino:", txtLatitudeDestino,
                    "Longitude de Origem:", txtLongitudeOrigem,
                    "Longitude de Destino:", txtLongitudeDestino,
                    "Situação:", cmbSituacao
            };

            if (tipoTransporte == 0) {
                campos = adicionarCamposTransportePessoal(campos, txtQuantidadePessoas);
            } else if (tipoTransporte == 1) {
                campos = adicionarCamposTransporteCargaInanimada(campos, chkCargaPerigosa);
            } else if (tipoTransporte == 2) {
                campos = adicionarCamposTransporteCargaViva(campos, txtTemperaturaMinima, txtTemperaturaMaxima);
            }

            int opcao = JOptionPane.showOptionDialog(null, campos, "Cadastro de Transporte",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    new Object[]{"Cadastrar", "Limpar", "Voltar"}, "Cadastrar");

            if (opcao == 0) { // Cadastrar
                try {
                    int numeroTransporte = Integer.parseInt(txtNumeroTransporte.getText().trim());
                    String nomeCliente = txtNomeCliente.getText().trim();
                    String descricao = txtDescricao.getText().trim();
                    double peso = Double.parseDouble(txtPeso.getText().trim());
                    double latitudeOrigem = Double.parseDouble(txtLatitudeOrigem.getText().trim());
                    double latitudeDestino = Double.parseDouble(txtLatitudeDestino.getText().trim());
                    double longitudeOrigem = Double.parseDouble(txtLongitudeOrigem.getText().trim());
                    double longitudeDestino = Double.parseDouble(txtLongitudeDestino.getText().trim());
                    Estado situacao = (Estado) cmbSituacao.getSelectedItem();
                    int quantidadePessoas = tipoTransporte == 0 ? Integer.parseInt(txtQuantidadePessoas.getText().trim()) : 0;
                    boolean cargaPerigosa = chkCargaPerigosa.isSelected();
                    double temperaturaMinima = tipoTransporte == 2 ? Double.parseDouble(txtTemperaturaMinima.getText().trim()) : 0;
                    double temperaturaMaxima = tipoTransporte == 2 ? Double.parseDouble(txtTemperaturaMaxima.getText().trim()) : 0;

                    Transporte transporte = criarTransporte(tipoTransporte, numeroTransporte, nomeCliente, descricao, peso,
                            latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino, situacao,
                            quantidadePessoas, cargaPerigosa, temperaturaMinima, temperaturaMaxima);

                    if (verificarCodigoExistente(numeroTransporte, transportesCadastrados)) {
                        JOptionPane.showMessageDialog(null, "Erro: Já existe um transporte com este número.");
                        continue;
                    }

                    transportesCadastrados.add(transporte);
                    filaTransportesPendentes.add(transporte);
                    JOptionPane.showMessageDialog(null, "Transporte cadastrado com sucesso!");

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Erro: Um ou mais campos possuem dados inválidos.");
                }
            } else if (opcao == 1) { // Limpar
                txtNumeroTransporte.setText("");
                txtNomeCliente.setText("");
                txtDescricao.setText("");
                txtPeso.setText("");
                txtLatitudeOrigem.setText("");
                txtLatitudeDestino.setText("");
                txtLongitudeOrigem.setText("");
                txtLongitudeDestino.setText("");
                cmbSituacao.setSelectedIndex(0);
                txtQuantidadePessoas.setText("");
                chkCargaPerigosa.setSelected(false);
                txtTemperaturaMinima.setText("");
                txtTemperaturaMaxima.setText("");
            } else { // Voltar
                break;
            }
        }
    }

    private Object[] adicionarCamposTransportePessoal(Object[] campos, JTextField txtQuantidadePessoas) {
        Object[] novosCampos = new Object[campos.length + 2];
        System.arraycopy(campos, 0, novosCampos, 0, campos.length);
        novosCampos[novosCampos.length - 2] = "Quantidade de Pessoas:";
        novosCampos[novosCampos.length - 1] = txtQuantidadePessoas;
        return novosCampos;
    }

    private Object[] adicionarCamposTransporteCargaInanimada(Object[] campos, JCheckBox chkCargaPerigosa) {
        Object[] novosCampos = new Object[campos.length + 2];
        System.arraycopy(campos, 0, novosCampos, 0, campos.length);
        novosCampos[novosCampos.length - 2] = "Carga Perigosa:";
        novosCampos[novosCampos.length - 1] = chkCargaPerigosa;
        return novosCampos;
    }

    private Object[] adicionarCamposTransporteCargaViva(Object[] campos, JTextField txtTemperaturaMinima, JTextField txtTemperaturaMaxima) {
        Object[] novosCampos = new Object[campos.length + 2];
        System.arraycopy(campos, 0, novosCampos, 0, campos.length);
        novosCampos[novosCampos.length - 2] = "Temperatura Mínima:";
        novosCampos[novosCampos.length - 1] = txtTemperaturaMinima;
        return novosCampos;
    }

    private boolean verificarCodigoExistente(int numeroTransporte, ArrayList<Transporte> transportesCadastrados) {
        for (Transporte transporte : transportesCadastrados) {
            if (transporte.getNumero() == numeroTransporte) {
                return true;
            }
        }
        return false;
    }

    private Transporte criarTransporte(int tipoTransporte, int numeroTransporte, String nomeCliente, String descricao,
                                       double peso, double latitudeOrigem, double latitudeDestino,
                                       double longitudeOrigem, double longitudeDestino, Estado situacao,
                                       int quantidadePessoas, boolean cargaPerigosa, double temperaturaMinima,
                                       double temperaturaMaxima) {
        if (tipoTransporte == 0) {
            return new TransportePessoal(numeroTransporte, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino,
                    longitudeOrigem, longitudeDestino, situacao, quantidadePessoas);
        } else if (tipoTransporte == 1) {
            return new TransporteCargaInanimada(numeroTransporte, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino,
                    longitudeOrigem, longitudeDestino, situacao, cargaPerigosa);
        } else {
            return new TransporteCargaViva(numeroTransporte, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino,
                    longitudeOrigem, longitudeDestino, situacao, temperaturaMinima, temperaturaMaxima);
        }
    }
}