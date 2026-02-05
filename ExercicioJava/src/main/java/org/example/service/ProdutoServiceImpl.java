package org.example.service;

import org.example.model.Produto;
import org.example.repository.ProdutoRepository;
import org.example.repository.ProdutoRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class ProdutoServiceImpl implements ProdutoService{

    ProdutoRepository repository = new ProdutoRepositoryImpl();

    @Override
    public Produto cadastrarProduto(Produto produto) throws SQLException {


        if (produto.getPreco() < 0){
            throw new IllegalArgumentException("c");
        }

        return repository.save(produto);

    }

    @Override
    public List<Produto> listarProdutos() throws SQLException {

        return repository.findAll();
    }

    @Override
    public Produto buscarPorId(int id) throws SQLException {
        return null;
        //n mexer
    }

    @Override
    public Produto atualizarProduto(Produto produto, int id) throws SQLException {
        Produto produtoOld = repository.findById(id);
        if(produtoOld == null){
            throw new IllegalArgumentException();
        }
        produto.setId(id);

        produto = repository.update(produto);

        if(produto == null){
            throw new RuntimeException("Erro de execução!");
        }
        return produto;
    }

    @Override
    public boolean excluirProduto(int id) throws SQLException {
        Produto produtoVelho = repository.findById(id);

        if (produtoVelho == null){

            throw new RuntimeException("ProdutoNãoExisteException");
        }
        repository.deleteById(id);


    return true;
    }
}
