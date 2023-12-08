import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {

    public static void main(String[] args) throws SQLException {

        // 1. Habilitar o driver JDBC a partir da aplicação cliente;
        //      ok

        // 2. Estabelecer uma conexão entre a aplicação cliente e servidor do banco de dados;
        String paramsConexao = "jdbc:h2:mem:testdb";
        String usuario = "";
        String senha = "";
        Connection conexao = DriverManager.getConnection(paramsConexao, usuario, senha);

        // 3. Montar e executar a consulta SQL desejada;
        String criacaoSql = "create table pessoa (id int primary key, nome varchar(150), qtdAcesso int, naturalidade varchar(75) )";
        Statement stmtC = conexao.createStatement();
        stmtC.executeUpdate(criacaoSql);
        stmtC.close();

        // Inserir duas pessoas no banco de dados
        inserirPessoa(conexao, 1, "João", 5, "Brasileiro");
        inserirPessoa(conexao, 2, "Maria", 10, "Brasileira");


        String consulta = "select * from pessoa";
        //consulta = "select nome as nome_completo, naturalidade from pessoa";
        Statement stmt = conexao.createStatement();
        ResultSet resultado = stmt.executeQuery(consulta);

        // 4. Processar no cliente o resultado da consulta.
        while (resultado.next()) {
            String nome = resultado.getString("nome");
            Integer quantidade = resultado.getInt("qtdAcesso");
            String naturalidade = resultado.getString("naturalidade");

            Pessoa pessoa = new Pessoa();
            pessoa.setNome(nome);
            pessoa.setQuantidadeAcesso(quantidade);
            pessoa.setNaturalidade(naturalidade);

            // Exibir os dados da pessoa
            System.out.println(pessoa);
            System.out.println("Nome: " + pessoa.getNome());
            System.out.println("Quantidade de acessos: " + pessoa.getQuantidadeAcesso());
            System.out.println("Naturalidade: " + pessoa.getNaturalidade());

        }

        System.out.println("\nTchau, até a próxima\n\n\t\t:-)");
    }

    private static void inserirPessoa(Connection conexao, Integer id, String nome, Integer qtdAcesso, String naturalidade) throws SQLException {
        String insercao = "insert into pessoa (id, nome, qtdAcesso, naturalidade) values (" + id + ", '" + nome + "', " + qtdAcesso + ", '" + naturalidade + "')";
        Statement stmt = conexao.createStatement();
        stmt.executeUpdate(insercao);
        stmt.close();
    }

}
