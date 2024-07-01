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

    public void aplicaJuros() {
        // Implementação do método para aplicar juros na conta de investimento
        double juros = getSaldo() * (taxaRemuneracaoInvestimento / 100);
        try {
            deposita(juros);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saca(double valor) {
        // Implementação do método para sacar da conta de investimento
        if (valor <= getSaldo()) {
            try {
                super.saca(valor);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Saque não permitido. Saldo insuficiente.");
        }
    }

    @Override
    public void deposita(double valor) throws Exception {
        
        super.deposita(valor);
    }
}