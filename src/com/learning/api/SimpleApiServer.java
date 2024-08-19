package com.learning.api;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SimpleApiServer {

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
		server.createContext("/movies", new MovieHandler());
		server.createContext("/addmovie", new MovieHandler());
		server.setExecutor(null);
		server.start();
		System.out.println("Server started on port 80");
	}

	static class MovieHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if ("GET".equals(exchange.getRequestMethod())) {
				List<String> movies = get_10_movies();

				String response = movies.toString();
				exchange.sendResponseHeaders(200, response.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			} else {
				exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
			}
		}

		private List<String> get_10_movies() {
			List<String> movies = new ArrayList<>();
			String sql = "SELECT film_id, title FROM film LIMIT 2";


			try (Connection conn = DBConnection.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					String s = "hi";
					movies.add(new Movie(rs.getInt("film_id"), rs.getString("title")).toString());
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return movies;
		}

	}

}
