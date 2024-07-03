package bancorrw.conta;

import bancorrw.cliente.Cliente;

public class ContaInvestimento extends Conta {
    private double taxaRemuneracaoInvestimento;
    private double montanteMinimo;
    private double depositoMinimo;

    public ContaInvestimento(double taxaRemuneracaoInvestimento, double montanteMinimo,
                             double depositoMinimo, double saldo, long id, Cliente cliente) throws Exception {
        super(id, cliente, saldo);
        this.taxaRemuneracaoInvestimento = taxaRemuneracaoInvestimento;
        if (saldo < montanteMinimo) {
            throw new Exception("Saldo não pode ser menor que montante mínimo.");
        }
        this.montanteMinimo = montanteMinimo;
        this.depositoMinimo = depositoMinimo;
    }


    public double getTaxaRemuneracaoInvestimento() {
        return taxaRemuneracaoInvestimento;
    }

    public void setTaxaRemuneracaoInvestimento(double taxaRemuneracaoInvestimento) {
        this.taxaRemuneracaoInvestimento = taxaRemuneracaoInvestimento;
    }

    public double getMontanteMinimo() {
        return montanteMinimo;
    }

    public void setMontanteMinimo(double montanteMinimo) {
        this.montanteMinimo = montanteMinimo;
    }

    public double getDepositoMinimo() {
        return depositoMinimo;
    }

    public void setDepositoMinimo(double depositoMinimo) {
        this.depositoMinimo = depositoMinimo;
    }

    @Override
    public void aplicaJuros() throws Exception {
        if (getSaldo() >= 0){
            double saldoJuros = (taxaRemuneracaoInvestimento * getSaldo());
            super.deposita(saldoJuros);
        }
    }

    public void saca(double valor) {
        // Implementação do método para sacar da conta de investimento
        if (valor > getMontanteMinimo()) {
            throw new RuntimeException("Saldo insuficiente para saque. Valor Saque=" + valor + " Saldo=" + getSaldo() + " Montante Minimo=" + getMontanteMinimo());
        }
        super.saca(valor);

    }

    public void deposita(double valor) throws Exception {
        if (valor >= depositoMinimo && valor <= montanteMinimo) {
            super.deposita(valor);
        }
        if (valor < depositoMinimo) {
            throw new RuntimeException("Valor do depóstio não atingiu o mínimo. Valor Depósito=" + valor + " Depóstio Mínimo=" + getDepositoMinimo());
        }
//        super.deposita(valor);
    }
}