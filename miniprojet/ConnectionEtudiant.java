package miniprojet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionEtudiant {
	Connection cn;
	public ConnectionEtudiant() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/projectdb";
            String username = "root";
            String password = "";
			cn = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Etablie");
		} catch(Exception e) {
			System.out.println("Erreur de connection");
			e.printStackTrace();
			
		}
		
	}
	public Connection maConnection() {
		return cn;
	}
	public void fermerConnexion() {
        if (cn != null) {
            try {
                cn.close();
                System.out.println("Connexion fermée avec succès !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion !");
                e.printStackTrace();
            }
        }
    }
}