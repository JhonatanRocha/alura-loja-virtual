package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.modelo.Categoria;
import br.com.caelum.jdbc.modelo.Produto;

public class ProdutosDAO {

	private Connection con;	
	
	public ProdutosDAO(Connection con) {
		this.con = con;
	}
	
	public void salva(Produto produto) throws SQLException {
		String sql = "insert into produto (nome, descricao) values (?, ?)";
		
		try (PreparedStatement stmt = con.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getDescricao());
			stmt.execute();
		}
	}

	public List<Produto> lista() throws SQLException {		
		String sql = "select * from produto";
		List<Produto> produtos = new ArrayList<Produto>();
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.execute();
			transformaResultadoEmProdutos(stmt, produtos);
		}
		
		return produtos;
	}

	public List<Produto> busca(Categoria categoria) throws SQLException {
		System.out.println("Executandi uma Query");
		String sql = "select * from produto where categoria_id = ?";
		List<Produto> produtos = new ArrayList<Produto>();
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, categoria.getId());
			stmt.execute();
			transformaResultadoEmProdutos(stmt, produtos);
		}
		return produtos;
	}

	private void transformaResultadoEmProdutos(PreparedStatement stmt,
			List<Produto> produtos) throws SQLException {
			
		try(ResultSet rs = stmt.getResultSet()){
			while(rs.next()){
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				Produto produto = new Produto(nome, descricao);
				produto.setId(id);
				produtos.add(produto);
			}
		}
	}

}
