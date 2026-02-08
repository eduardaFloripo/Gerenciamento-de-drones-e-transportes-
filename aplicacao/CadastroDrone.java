
package aplicacao;

import javax.swing.*;

import dados.Drone;
import dados.DroneCargaInanimada;
import dados.DroneCargaViva;
import dados.DronePessoal;

import java.util.ArrayList;

public class CadastroDrone {


    public CadastroDrone(ArrayList<Drone> dronesCadastrados) {
        abrirJanelaEscolhaTipoDrone(dronesCadastrados);
    }


    private void abrirJanelaEscolhaTipoDrone(ArrayList<Drone> dronesCadastrados) {
        String[] opcoes = {"Drone Pessoal", "Drone Carga Inanimada", "Drone Carga Viva"};

        int escolha = JOptionPane.showOptionDialog(null, "Escolha o tipo de drone",
                "Escolher Tipo de Drone", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opcoes, opcoes[0]);

        if (escolha != -1) {
            abrirJanelaCadastroDrone(escolha, dronesCadastrados);
        }
    }

    private void abrirJanelaCadastroDrone(int tipoDrone, ArrayList<Drone> dronesCadastrados) {
        JTextField textoCodigo = new JTextField();
        JTextField textoCusto = new JTextField();
        JTextField textoAutonomia = new JTextField();
        JTextField textoQuantidadeMaximaPessoas = new JTextField();
        JTextField textoPesoMaximo = new JTextField();
        JCheckBox boxProtecao = new JCheckBox("Proteção");
        JCheckBox boxClimatizado = new JCheckBox("Climatizado");

        while (true) {
            Object[] campos = {
                    "Código:", textoCodigo,
                    "Custo:", textoCusto,
                    "Autonomia:", textoAutonomia
            };

            if (tipoDrone == 0) {
                campos = adicionarCamposDronePessoal(campos, textoQuantidadeMaximaPessoas);
            } else if (tipoDrone == 1) {
                campos = adicionarCamposDroneCargaInanimada(campos, textoPesoMaximo, boxProtecao);
            } else if (tipoDrone == 2) {
                campos = adicionarCamposDroneCargaViva(campos, textoPesoMaximo, boxClimatizado);
            }

            int opcao = JOptionPane.showOptionDialog(null, campos, "Cadastro de Drone",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    new Object[]{"Cadastrar", "Limpar", "Voltar"}, "Cadastrar");

            if (opcao == 0) { // Cadastrar
                try {
                    int codigo = Integer.parseInt(textoCodigo.getText().trim());
                    double custo = Double.parseDouble(textoCusto.getText().trim());
                    double autonomia = Double.parseDouble(textoAutonomia.getText().trim());
                    int quantidadeMaximaPessoas = 0;
                    if (tipoDrone == 0) { // Drone Pessoal
                        quantidadeMaximaPessoas = Integer.parseInt(textoQuantidadeMaximaPessoas.getText().trim());
                    }
                    double pesoMaximo = 0;
                    if (tipoDrone == 1 || tipoDrone == 2) {
                        pesoMaximo = Double.parseDouble(textoPesoMaximo.getText().trim());
                    }
                    boolean protecao = boxProtecao.isSelected();
                    boolean climatizado = boxClimatizado.isSelected();

                    if (verificarCodigoExistente(codigo, dronesCadastrados)) {
                        JOptionPane.showMessageDialog(null, "Erro: Já existe um drone com este código.");
                        continue;
                    }

                    Drone drone = criarDrone(tipoDrone, codigo, custo, autonomia, quantidadeMaximaPessoas, pesoMaximo, protecao, climatizado);
                    if (drone != null) {
                        drone.setDisponivel(true);
                        dronesCadastrados.add(drone);
                        JOptionPane.showMessageDialog(null, "Drone cadastrado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao criar o drone. Verifique os dados.");
                    }

                } catch (NumberFormatException e) {
                    // Exibindo a mensagem de erro mais detalhada
                    JOptionPane.showMessageDialog(null, "Erro: Um ou mais campos possuem dados inválidos. " +
                            "Certifique-se de preencher os campos corretamente.\n" +
                            "Detalhes do erro: " + e.getMessage());
                }
            } else if (opcao == 1) { // Limpar
                textoCodigo.setText("");
                textoCusto.setText("");
                textoAutonomia.setText("");
                textoQuantidadeMaximaPessoas.setText("");
                textoPesoMaximo.setText("");
                boxProtecao.setSelected(false);
                boxClimatizado.setSelected(false);
            } else { // Voltar
                break;
            }
        }
    }

    private Object[] adicionarCamposDronePessoal(Object[] campos, JTextField txtQuantidadeMaximaPessoas) {
        Object[] novosCampos = new Object[campos.length + 2];
        System.arraycopy(campos, 0, novosCampos, 0, campos.length);
        novosCampos[novosCampos.length - 2] = "Quantidade Máxima de Pessoas:";
        novosCampos[novosCampos.length - 1] = txtQuantidadeMaximaPessoas;
        return novosCampos;
    }

    private Object[] adicionarCamposDroneCargaInanimada(Object[] campos, JTextField txtPesoMaximo, JCheckBox chkProtecao) {
        Object[] novosCampos = new Object[campos.length + 2];
        System.arraycopy(campos, 0, novosCampos, 0, campos.length);
        novosCampos[novosCampos.length - 2] = "Peso Máximo:";
        novosCampos[novosCampos.length - 1] = txtPesoMaximo;
        novosCampos = adicionarCamposDroneProtecao(novosCampos, chkProtecao);
        return novosCampos;
    }

    private Object[] adicionarCamposDroneCargaViva(Object[] campos, JTextField txtPesoMaximo, JCheckBox chkClimatizado) {
        Object[] novosCampos = new Object[campos.length + 2];
        System.arraycopy(campos, 0, novosCampos, 0, campos.length);
        novosCampos[novosCampos.length - 2] = "Peso Máximo:";
        novosCampos[novosCampos.length - 1] = txtPesoMaximo;
        novosCampos = adicionarCamposDroneClimatizado(novosCampos, chkClimatizado);
        return novosCampos;
    }

    private Object[] adicionarCamposDroneProtecao(Object[] campos, JCheckBox chkProtecao) {
        Object[] novosCampos = new Object[campos.length + 2];
        System.arraycopy(campos, 0, novosCampos, 0, campos.length);
        novosCampos[novosCampos.length - 2] = "Proteção:";
        novosCampos[novosCampos.length - 1] = chkProtecao;
        return novosCampos;
    }

    private Object[] adicionarCamposDroneClimatizado(Object[] campos, JCheckBox chkClimatizado) {
        Object[] novosCampos = new Object[campos.length + 2];
        System.arraycopy(campos, 0, novosCampos, 0, campos.length);
        novosCampos[novosCampos.length - 2] = "Climatizado:";
        novosCampos[novosCampos.length - 1] = chkClimatizado;
        return novosCampos;
    }

    private boolean verificarCodigoExistente(int codigo, ArrayList<Drone> dronesCadastrados) {
        for (Drone drone : dronesCadastrados) {
            if (drone.getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    private Drone criarDrone(int tipoDrone, int codigo, double custo, double autonomia, int quantidadeMaximaPessoas, double pesoMaximo, boolean protecao, boolean climatizado) {
        if (tipoDrone == 0) {
            return new DronePessoal(codigo, custo, autonomia, quantidadeMaximaPessoas);
        } else if (tipoDrone == 1) {
            return new DroneCargaInanimada(codigo, custo, autonomia, pesoMaximo, protecao);
        } else if (tipoDrone == 2) {
            return new DroneCargaViva(codigo, custo, autonomia, pesoMaximo, climatizado);
        }
        return null;
    }
}