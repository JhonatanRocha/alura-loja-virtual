package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.modelo.Categoria;
import br.com.caelum.jdbc.modelo.Produto;

public class CategoriasDAO {
	
	private Connection con;
	
	public CategoriasDAO(Connection con){
		this.con = con;
	}

	public List<Categoria> lista() throws SQLException {
		System.out.println("Executandi uma Query");
		String sql = "select * from categoria";
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.execute();
			
			try(ResultSet rs = stmt.getResultSet()){
				while(rs.next()){
					int id = rs.getInt("id");
					String nome = rs.getString("nome");
					Categoria categoria = new Categoria(id ,nome);
					categorias.add(categoria);
				}
			}
		}
		
		return categorias;
	}

	public List<Categoria> listaComProdutos() throws SQLException {
		List<Categoria> categorias = new ArrayList<Categoria>();
		String sql = "select c.id as c_id, c.nome as c_nome, p.id as p_id, p.nome as p_nome, p.descricao as p_descricao " +
						"from Categoria as c join Produto as p on p.categoria_id = c.id";
		Categoria ultima = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.execute();
			
			try(ResultSet rs = stmt.getResultSet()){
				while(rs.next()){
					int id = rs.getInt("c_id");
					String nome = rs.getString("c_nome");
					if(ultima == null || !ultima.getNome().equals(nome)){
						Categoria categoria = new Categoria(id ,nome);
						categorias.add(categoria);
						ultima = categoria;
					}
					String nomeDoProduto = rs.getString("p_nome");
					String descricaoProduto = rs.getString("p_descricao");
					int idProduto = rs.getInt("p_id");
					
					Produto p = new Produto(nomeDoProduto, descricaoProduto);
					p.setId(idProduto);
					ultima.adiciona(p);
				}
			}
		}
		
		return categorias;
	}
}
