/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancorrw.cliente;

import bancorrw.conta.ContaCorrente;
import bancorrw.conta.ContaInvestimento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rafae
 */
public class Cliente extends Pessoa {

    private List<ContaInvestimento> contasInvestimento;
    private ContaCorrente contaCorrente;
    private String cartaoCredito;

    public Cliente(long id, String nome, String cpf, LocalDate dataNascimento, String cartaoCredito) {
        super(id, nome, cpf, dataNascimento);
        this.cartaoCredito = cartaoCredito;
        this.contasInvestimento = new ArrayList<>();
//        this.contasInvestimento = contasInvestimento;
    }

    public List<ContaInvestimento> getContasInvestimento() {
//        if (this.contasInvestimento == null) {
//            return Collections.emptyList();
//        }
        return Collections.unmodifiableList(this.contasInvestimento);
    }

    public void setContasInvestimento(List<ContaInvestimento> contasInvestimento) {
        this.contasInvestimento = contasInvestimento;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) throws Exception {
        if (this.contaCorrente != null && this.contaCorrente.getSaldo() != 0) {
            throw new Exception("Não pode modificar a conta corrente, pois saldo da original não está zerado. Para fazer isso primeiro zere o saldo da conta do cliente. Saldo=" + this.contaCorrente.getSaldo());
        }

        this.contaCorrente = contaCorrente;
        if (this.contaCorrente != null) {
            this.contaCorrente.setCliente(this);
        }
    }

    public String getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(String cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public double getSaldoTotalCliente() {
        double saldoTotal = 0.0;
        try {
            if (contaCorrente != null) {
                saldoTotal += contaCorrente.getSaldo();
                // Verifica se contaCorrente não é nula antes de acessar o número
                if (contaCorrente.getNumero() != 0) {
                    saldoTotal += contaCorrente.getNumero();
                }
            }
            for (ContaInvestimento conta : contasInvestimento) {
                saldoTotal += conta.getSaldo();
            }
        } catch (NullPointerException e) {
            System.out.println("Não pode modificar a conta corrente, pois saldo da original não está zerado. \"\n" +
                    "                    + \"Para fazer isso primeiro zere o saldo da conta do cliente. Saldo="+getSaldoTotalCliente());
            saldoTotal = 0.0; // Definindo saldo total como zero em caso de exceção
        }
        return saldoTotal;
    }

    public void addContaInvestimento(ContaInvestimento contaInvestimento) throws Exception {
//        if (this.getId() <= 0) {
//            throw new Exception("Conta Investimento " + this.getId() + ";" + this.getNome() + "Não está persistindo no BD. Precisa chamar o DAO Cliente antes de adicionar Conta Investimento a ela.");
//        }
//        if (contaInvestimento.getId() <= 0) {
//            throw new Exception("ContaInvestimento " + contaInvestimento.getId() + ";" + contaInvestimento.getCliente() + "não está persistido no BD. Precisa chamar a DAO de Conta Investimento antes de adicionar Conta Investimento a Conta");
//        }
//        if (this.getContasInvestimento().equals(contaInvestimento))
//            return;
//        this.contasInvestimento.add(contaInvestimento);
        this.contasInvestimento.add(contaInvestimento);

    }

}
