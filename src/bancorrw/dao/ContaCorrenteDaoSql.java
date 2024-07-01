package bancorrw.dao;

import bancorrw.cliente.Cliente;
import bancorrw.conta.ContaCorrente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaCorrenteDaoSql implements ContaCorrenteDao {
    private ContaCorrenteDaoSql() {
    }

    private static ContaCorrenteDaoSql dao;

    public static ContaCorrenteDaoSql getContaCorrenteDaoSql() {
        if (dao == null) {
            dao = new ContaCorrenteDaoSql();
        }
        return dao;
    }

    private final String insertContaCorrente =
            "INSERT INTO contas_corrente (id_conta, limite, taxa_juros_limite) VALUES (?, ?, ?)";
    private final String insertConta =
            "INSERT INTO contas (id_cliente, saldo) VALUES (?, ?)";
    private final String updateClienteIdContaCorrente =
            "UPDATE clientes SET id_conta_corrente = ? WHERE id_cliente = ?";
    private final String updateContaCorrente =
            "UPDATE contas_corrente SET limite = ?, taxa_juros_limite = ? WHERE id_conta = ?";
    private final String updateConta =
            "UPDATE contas SET saldo = ? WHERE id_conta = ?";
    private final String selectByCliente =
            "SELECT contas_corrente.id_conta, saldo, limite, taxa_juros_limite, clientes.id_cliente, nome, cpf, data_nascimento, cartao_credito " +
                    "FROM contas " +
                    "INNER JOIN contas_corrente ON contas.id_conta = contas_corrente.id_conta " +
                    "INNER JOIN clientes ON contas.id_conta = clientes.id_conta_corrente " +
                    "WHERE contas.id_cliente = ?";
    private String selectById =
            "SELECT " +
                    "contas_corrente.id_conta, " +
                    "saldo, " +
                    "limite, " +
                    "taxa_juros_limite, " +
                    "clientes.id_cliente," +
                    "nome, " +
                    "cpf, " +
                    "data_nascimento, " +
                    "cartao_credito " +
                    "FROM " +
                    "contas " +
                    "INNER JOIN " +
                    "contas_corrente " +
                    "ON " +
                    "contas.id_conta=contas_corrente.id_conta " +
                    "INNER JOIN " +
                    "clientes " +
                    "ON " +
                    "contas.id_conta=clientes.id_conta_corrente " +
                    "WHERE " +
                    "contas.id_conta=?";
    private final String selectAll =
            "SELECT contas_corrente.id_conta, saldo, limite, taxa_juros_limite, clientes.id_cliente, nome, cpf, data_nascimento, cartao_credito " +
                    "FROM contas " +
                    "INNER JOIN contas_corrente ON contas.id_conta = contas_corrente.id_conta " +
                    "INNER JOIN clientes ON contas.id_conta = clientes.id_conta_corrente";
    private final String deleteById =
            "DELETE FROM contas WHERE id_conta = ?";
    private final String deleteAll =
            "DELETE contas, contas_corrente " +
                    "FROM contas " +
                    "INNER JOIN contas_corrente ON contas.id_conta = contas_corrente.id_conta";
    private final String resetAIContas = "ALTER TABLE contas AUTO_INCREMENT = 1";

    @Override
    public void add(ContaCorrente contaCorrente) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmtAdicionarConta = connection.prepareStatement(insertConta, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtAdicionaContaCorrente = connection.prepareStatement(insertContaCorrente)) {

            // Inserir na tabela 'contas'
            stmtAdicionarConta.setLong(1, contaCorrente.getCliente().getId());
            stmtAdicionarConta.setDouble(2, contaCorrente.getSaldo());
            stmtAdicionarConta.execute();

            ResultSet rs = stmtAdicionarConta.getGeneratedKeys();
            rs.next();
            long idConta = rs.getLong(1);
            contaCorrente.setId(idConta);

            // Inserir na tabela 'contas_corrente'
            stmtAdicionaContaCorrente.setLong(1, idConta);
            stmtAdicionaContaCorrente.setDouble(2, contaCorrente.getLimite());
            stmtAdicionaContaCorrente.setDouble(3, contaCorrente.getTaxaJurosLimite());
            stmtAdicionaContaCorrente.execute();


            // Atualizar id_conta_corrente na tabela 'clientes'
            PreparedStatement stmtAtualizaCliente = connection.prepareStatement(updateClienteIdContaCorrente);
            stmtAtualizaCliente.setLong(1, contaCorrente.getId());
            stmtAtualizaCliente.setLong(2, contaCorrente.getCliente().getId());
            stmtAtualizaCliente.executeUpdate();

        }
    }


    @Override
    public List<ContaCorrente> getAll() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(selectAll);
             ResultSet rs = stmtLista.executeQuery()) {

            List<ContaCorrente> contaCorrentes = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id_conta");
                double saldo = rs.getDouble("saldo");
                double limite = rs.getDouble("limite");
                double taxaJuros = rs.getDouble("taxa_juros_limite");

                long idCliente = rs.getLong("id_cliente");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                String cartaoCredito = rs.getString("cartao_credito");

                Cliente cliente = new Cliente(idCliente, nome, cpf, dataNascimento, cartaoCredito);

                ContaCorrente contaCorrente = new ContaCorrente(limite, taxaJuros, id, cliente, saldo);
                contaCorrentes.add(contaCorrente);
            }
            return contaCorrentes;
        }
    }

    @Override
    public ContaCorrente getById(long id) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(selectById)) {

            stmtLista.setLong(1, id);

            ResultSet rs = stmtLista.executeQuery();
            rs.next();
            double saldo = rs.getDouble("saldo");
            double limite = rs.getDouble("limite");
            double taxaJuros = rs.getDouble("taxa_juros_limite");

            long idCliente = rs.getLong("id_cliente");
            String nome = rs.getString("nome");
            String cpf = rs.getString("cpf");
            LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
            String cartaoCredito = rs.getString("cartao_credito");

            Cliente cliente = new Cliente(idCliente, nome, cpf, dataNascimento, cartaoCredito);

            return new ContaCorrente(limite, taxaJuros, id, cliente, saldo);

        }
    }


    @Override
    public void update(ContaCorrente contaCorrente) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmtUpdateConta = connection.prepareStatement(updateConta);
             PreparedStatement stmtUpdateContaCorrente = connection.prepareStatement(updateContaCorrente)) {

            // Atualizar tabela 'contas'
            stmtUpdateConta.setDouble(1, contaCorrente.getSaldo());
            stmtUpdateConta.setLong(2, contaCorrente.getId());
            stmtUpdateConta.executeUpdate();

            // Atualizar tabela 'contas_corrente'
            stmtUpdateContaCorrente.setDouble(1, contaCorrente.getLimite());
            stmtUpdateContaCorrente.setDouble(2, contaCorrente.getTaxaJurosLimite());
            stmtUpdateContaCorrente.setLong(3, contaCorrente.getId());
            stmtUpdateContaCorrente.executeUpdate();
        }
    }

    @Override
    public void delete(ContaCorrente contaCorrente) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmtDelete = connection.prepareStatement(deleteById)) {
            stmtDelete.setLong(1, contaCorrente.getId());
            stmtDelete.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmtDeleteAll = connection.createStatement()) {
            stmtDeleteAll.executeUpdate(deleteAll);
            stmtDeleteAll.executeUpdate(resetAIContas);
        }
    }

    @Override
    public ContaCorrente getContaCorrenteByCliente(Cliente cliente) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(selectByCliente)) {
            stmtLista.setLong(1, cliente.getId());

            try (ResultSet rs = stmtLista.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong("id_conta");
                    double saldo = rs.getDouble("saldo");
                    double limite = rs.getDouble("limite");
                    double taxaJurosLimite = rs.getDouble("taxa_juros_limite");

                    return new ContaCorrente(limite, taxaJurosLimite, id, cliente, saldo);
                }
            }
        }
        return null;
    }
}
