package bancorrw.dao;

import bancorrw.cliente.Cliente;
import bancorrw.conta.ContaInvestimento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaInvestimentoDaoSql implements ContaInvestimentoDao {

    private ContaInvestimentoDaoSql() {
    }

    private static ContaInvestimentoDaoSql dao;

    public static ContaInvestimentoDaoSql getContaInvestimentoDaoSql() {
        if (dao == null) {
            return dao = new ContaInvestimentoDaoSql();
        } else {
            return dao;
        }
    }

    private String insertContaInvstimento =
            "INSERT INTO " +
                    "contas_investimento " +
                    "(id_conta," +
                    "taxa_remuneracao_investimento," +
                    "montante_minimo," +
                    "deposito_minimo) " +
                    "VALUES" +
                    "(?,?,?,?)";
    private String insertConta =
            "INSERT INTO " +
                    "contas " +
                    "(id_cliente," +
                    "saldo) " +
                    "VALUES" +
                    "(?,?)";
    private String selectAll =
            "SELECT " +
                    "contas_investimento.id_conta, " +
                    "saldo, " +
                    "taxa_remuneracao_investimento, " +
                    "montante_minimo, " +
                    "deposito_minimo, " +
                    "clientes.id_cliente," +
                    "nome, " +
                    "cpf, " +
                    "data_nascimento, " +
                    "cartao_credito " +
                    "FROM " +
                    "contas " +
                    "INNER JOIN " +
                    "contas_investimento " +
                    "ON " +
                    "contas.id_conta=contas_investimento.id_conta " +
                    "INNER JOIN " +
                    "clientes " +
                    "ON " +
                    "contas.id_cliente=clientes.id_cliente ";
    private String selectById =
            "SELECT " +
                    "contas_investimento.id_conta, " +
                    "saldo, " +
                    "taxa_remuneracao_investimento, " +
                    "montante_minimo, " +
                    "deposito_minimo, " +
                    "clientes.id_cliente," +
                    "nome, " +
                    "cpf, " +
                    "data_nascimento, " +
                    "cartao_credito " +
                    "FROM " +
                    "contas " +
                    "INNER JOIN " +
                    "contas_investimento " +
                    "ON " +
                    "contas.id_conta=contas_investimento.id_conta " +
                    "INNER JOIN " +
                    "clientes " +
                    "ON " +
                    "contas.id_cliente=clientes.id_cliente " +
                    "WHERE " +
                    "contas.id_conta=?";
    private String selectByCliente =
            "SELECT " +
                    "contas_investimento.id_conta, " +
                    "saldo, " +
                    "taxa_remuneracao_investimento, " +
                    "montante_minimo, " +
                    "deposito_minimo, " +
                    "clientes.id_cliente," +
                    "nome, " +
                    "cpf, " +
                    "data_nascimento, " +
                    "cartao_credito " +
                    "FROM " +
                    "contas " +
                    "INNER JOIN " +
                    "contas_investimento " +
                    "ON " +
                    "contas.id_conta=contas_investimento.id_conta " +
                    "INNER JOIN " +
                    "clientes " +
                    "ON " +
                    "contas.id_cliente=clientes.id_cliente " +
                    "WHERE " +
                    "contas.id_cliente=?";

    private String updateContaInvestimento =
            "UPDATE " +
                    "contas_investimento " +
                    "SET " +
                    "taxa_remuneracao_investimento=? ," +
                    "montante_minimo=? ," +
                    "deposito_minimo=? " +
                    "WHERE id_conta = ?";
    private String updateConta =
            "UPDATE " +
                    "contas " +
                    "SET " +
                    "saldo=? " +
                    "WHERE id_conta = ?";
    private String deleteById =
            "DELETE FROM " +
                    "contas " +
                    "WHERE " +
                    "id_conta=?";
    private String deleteAll =
            "DELETE " +
                    "contas,contas_investimento " +
                    "FROM " +
                    "contas " +
                    "INNER JOIN " +
                    "contas_investimento " +
                    "ON " +
                    "contas.id_conta=contas_investimento.id_conta ";
    private final String ressetAIContas = "ALTER TABLE contas AUTO_INCREMENT =1";

    @Override
    public void add(ContaInvestimento conta) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmtConta = connection.prepareStatement(insertConta, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtContaInvestimento = connection.prepareStatement(insertContaInvstimento)) {
            // Inicia a transação

            connection.setAutoCommit(false);

            // Insere na tabela `contas`
            stmtConta.setLong(1, conta.getCliente().getId());
            stmtConta.setDouble(2, conta.getSaldo());
            stmtConta.execute();

            // Recupera o id da conta gerada
            ResultSet rs = stmtConta.getGeneratedKeys();
            rs.next();
            long idConta = rs.getLong(1);
            conta.setId(idConta);

            // Insere na tabela `contas_investimento`
            stmtContaInvestimento.setLong(1, idConta);
            stmtContaInvestimento.setDouble(2, conta.getTaxaRemuneracaoInvestimento());
            stmtContaInvestimento.setDouble(3, conta.getMontanteMinimo());
            stmtContaInvestimento.setDouble(4, conta.getDepositoMinimo());
            stmtContaInvestimento.execute();
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<ContaInvestimento> getAll() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectAll);
             ResultSet rs = stmt.executeQuery()) {

            List<ContaInvestimento> contasInvestimento = new ArrayList<>();
            while (rs.next()) {
                long idConta = rs.getLong("id_conta");
                double saldo = rs.getDouble("saldo");
                double taxaRemuneracao = rs.getDouble("taxa_remuneracao_investimento");
                double montanteMinimo = rs.getDouble("montante_minimo");
                double depositoMinimo = rs.getDouble("deposito_minimo");
                long idCliente = rs.getLong("id_cliente");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                String cartaoCredito = rs.getString("cartao_credito");

                Cliente cliente = new Cliente(idCliente, nome, cpf, dataNascimento, cartaoCredito);
                ContaInvestimento contaInvestimento = new ContaInvestimento(taxaRemuneracao, montanteMinimo, depositoMinimo, saldo, idConta, cliente);
                contasInvestimento.add(contaInvestimento);
            }
            return contasInvestimento;
        }
    }


    @Override
    public ContaInvestimento getById(long id) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectById)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double saldo = rs.getDouble("saldo");
                    double taxaRemuneracao = rs.getDouble("taxa_remuneracao_investimento");
                    double montanteMinimo = rs.getDouble("montante_minimo");
                    double depositoMinimo = rs.getDouble("deposito_minimo");
                    long idCliente = rs.getLong("id_cliente");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                    String cartaoCredito = rs.getString("cartao_credito");

                    Cliente cliente = new Cliente(idCliente, nome, cpf, dataNascimento, cartaoCredito);
                    return new ContaInvestimento(taxaRemuneracao, montanteMinimo, depositoMinimo, saldo, id, cliente);
                } else {
                    throw new Exception("Conta de investimento não encontrada com id = " + id);
                }
            }
        }
    }


    @Override
    public void update(ContaInvestimento conta) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Inicia a transação
            connection.setAutoCommit(false);

            try (PreparedStatement stmtConta = connection.prepareStatement(updateConta);
                 PreparedStatement stmtContaInvestimento = connection.prepareStatement(updateContaInvestimento)) {

                // Atualiza a tabela `contas`
                stmtConta.setDouble(1, conta.getSaldo());
                stmtConta.setLong(2, conta.getId());
                stmtConta.executeUpdate();

                // Atualiza a tabela `contas_investimento`
                stmtContaInvestimento.setDouble(1, conta.getTaxaRemuneracaoInvestimento());
                stmtContaInvestimento.setDouble(2, conta.getMontanteMinimo());
                stmtContaInvestimento.setDouble(3, conta.getDepositoMinimo());
                stmtContaInvestimento.setLong(4, conta.getId());
                stmtContaInvestimento.executeUpdate();

                // Confirma a transação
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }


    @Override
    public void delete(ContaInvestimento conta) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteById)) {
            stmt.setLong(1, conta.getId());
            stmt.executeUpdate();
        }
    }


    @Override
    public void deleteAll() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmtDeleteAll = connection.prepareStatement(deleteAll);
             PreparedStatement stmtResetAI = connection.prepareStatement(ressetAIContas)) {
            stmtDeleteAll.executeUpdate();
            stmtResetAI.executeUpdate();
        }
    }


    @Override
    public List<ContaInvestimento> getContasInvestimentoByCliente(Cliente cliente) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectByCliente)) {
            stmt.setLong(1, cliente.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                List<ContaInvestimento> contasInvestimento = new ArrayList<>();
                while (rs.next()) {
                    long idConta = rs.getLong("id_conta");
                    double saldo = rs.getDouble("saldo");
                    double taxaRemuneracao = rs.getDouble("taxa_remuneracao_investimento");
                    double montanteMinimo = rs.getDouble("montante_minimo");
                    double depositoMinimo = rs.getDouble("deposito_minimo");

                    contasInvestimento.add(new ContaInvestimento(taxaRemuneracao, montanteMinimo, depositoMinimo, saldo, idConta, cliente));
                }
                cliente.setContasInvestimento(contasInvestimento);
                return contasInvestimento;
            }
        }
    }

}
