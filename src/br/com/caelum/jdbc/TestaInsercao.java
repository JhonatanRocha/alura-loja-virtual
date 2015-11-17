package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {
	public static void main(String[] args) throws SQLException {
		
		ConnectionPool database = new ConnectionPool();
		
		try(Connection connection = database.getConnection();) {
		connection.setAutoCommit(false);
		String sqlStatement = "insert into produto (nome, descricao) values (?, ?)";
		
			try(PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)){
				adiciona("TV LCD", "32 polegadas", statement);
				adiciona("Blueray", "Full HDMI", statement);
				connection.commit();
			}catch(Exception e){
				e.printStackTrace();
				connection.rollback();
				System.out.println("Rollback Efetuado!");
			}
		}
	}

	private static void adiciona(String nome, String descricao,
			PreparedStatement statement) throws SQLException {
				
		if(nome.equals("Blueray")){
			throw new IllegalArgumentException("Problema ocorreu!");
		}
		
		statement.setString(1, nome);
		statement.setString(2, descricao);

		boolean resultado = statement.execute();
		
		System.out.println(resultado);
		
		ResultSet resultSet = statement.getGeneratedKeys();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            System.out.println(id + " gerado");
        }
        resultSet.close();
	}
}
