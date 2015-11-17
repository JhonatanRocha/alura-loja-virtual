package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestaConexao {

	public static void main(String[] args) throws SQLException {
		//Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/loja-virtual", "root", "root");
		Connection connection = new ConnectionPool().getConnection();
		System.out.println("Abrinco uma conexao com sucesso!");
		connection.close();
	}

}
