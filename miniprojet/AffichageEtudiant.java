package miniprojet;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AffichageEtudiant extends JFrame {
    private JTextArea textArea;

    public AffichageEtudiant() {
        super("Détails des étudiants");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textArea = new JTextArea();
        textArea.setEditable(false);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        getContentPane().add(panel);

        setVisible(true);
    }

    public void afficherEtudiants() {
        ConnectionEtudiant cn = new ConnectionEtudiant();
        if (cn == null || cn.maConnection() == null) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données", null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT * FROM table_etudiant";
            PreparedStatement statement = cn.maConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String ddn = resultSet.getString("ddn");
                Float note1 = resultSet.getFloat("note1");
                Float note2 = resultSet.getFloat("note2");
                Float note3 = resultSet.getFloat("note3");
                

                sb.append("Nom: ").append(nom).append(", Prénom: ").append(prenom)
                        .append(", Date de naissance: ").append(ddn).append(", Note 1: ").append(note1).append(", Note 2: ").append(note2).append(", Note 3: ").append(note3).append("\n");
            }
            textArea.setText(sb.toString());
            resultSet.close();
            statement.close();
            cn.maConnection().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des étudiants : " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AffichageEtudiant affichage = new AffichageEtudiant();
            affichage.afficherEtudiants();
        });
    }
}
