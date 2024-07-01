package bancorrw.conta;

import bancorrw.cliente.Cliente;

public class ContaCorrente extends Conta {

    private double limite;
    private double taxaJurosLimite;

    public ContaCorrente(double limite, double taxaJurosLimite, long id, Cliente cliente, double saldo) throws Exception {
        super(id, cliente, saldo);
        this.limite = limite;
        this.taxaJurosLimite = taxaJurosLimite;
        cliente.setContaCorrente(this);
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getTaxaJurosLimite() {
        return taxaJurosLimite;
    }

    public void setTaxaJurosLimite(double taxaJuros) {
        this.taxaJurosLimite = taxaJuros;
    }

    public void aplicaJuros() {
        if (getSaldo() < 0) {
            double juros = getSaldo() * taxaJurosLimite;
            super.saca(juros); // Aplica juros como um saque no saldo
        }
    }

    public void saca(double valor) {
        if (valor <= 0) {
            throw new RuntimeException("Valor do saque não pode ser negativo ou zero. Valor="+valor);
        }

        if (getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente na conta."+
                    "\nValor saque=1300.0"+
                    "\nSaldo="+getSaldo()+
                    "\nLimite="+getLimite());
        }
    }

}
