package org.example.repository;

import org.example.model.Produto;
import org.example.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepository {

    @Override
    public Produto save(Produto produto) throws SQLException {

        String comando = "INSERT INTO produto (nome, preco, quantidade, categoria) VALUES (?,?,?,?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setString(4, produto.getCategoria());
            stmt.execute();


            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                produto.setId(rs.getInt(1));
            }
        }
        return produto;
    }

    @Override
    public List<Produto> findAll() throws SQLException {

        String query = "SELECT * FROM produto";

        ArrayList<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getNString("nome");
                double preco = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");
                String categoria = rs.getNString("categoria");

                var produto = new Produto(id, nome, preco, quantidade, categoria);
                produtos.add(produto);
            }

        }
        return produtos;

    }

    @Override
    public Produto findById(int id) throws SQLException {
        return null;

        //ta dando certo, n mexer
    }

    @Override
    public Produto update(Produto produto) throws SQLException {
        String command = """
                UPDATE produto
                set nome = ?,preco = ?, quantidade = ?, categoria = ?
                WHERE id = ?
                """;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setString(4, produto.getCategoria());
            stmt.setInt(5, produto.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            return null;
        }

        return produto;


}

    @Override
    public void deleteById(int id) throws SQLException {

        String comando = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(comando)){
                 stmt.setInt(1, id);
                 stmt.execute();
        }


    }
}
