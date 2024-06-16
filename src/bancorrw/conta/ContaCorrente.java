/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancorrw.conta;

import bancorrw.cliente.Cliente;

/**
 *
 * @author rafae
 */
public class ContaCorrente extends Conta{

    private double limite;
    private double taxaJuros;

    public ContaCorrente(long id, Cliente cliente, double saldo, double limite, double taxaJuros) {
        super(id, cliente, saldo);
        this.limite = limite;
        this.taxaJuros = taxaJuros;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public void aplicarJuros(){

    }

    public void sacar(double valor){
        
    }
}
