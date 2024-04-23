package miniprojet;
import javax.swing.*;
import java.sql.*;

public class SuppressionEtudiant {
    public static void supprimerEtudiant(String name) {
        ConnectionEtudiant cn = new ConnectionEtudiant();
        if (cn == null || cn.maConnection() == null) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données", null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "DELETE FROM table_etudiant WHERE nom=?";
            PreparedStatement statement = cn.maConnection().prepareStatement(query);
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Étudiant supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Aucun étudiant trouvé avec le nom spécifié", "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
            statement.close();
            cn.maConnection().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'étudiant : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        supprimerEtudiant("NomEtudiant");
    }
}
