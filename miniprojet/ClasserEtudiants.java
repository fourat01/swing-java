package miniprojet;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClasserEtudiants extends JFrame {
    private JTextArea textArea;

    public ClasserEtudiants() {
        super("Classement des étudiants par nom");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        getContentPane().add(panel);

        setVisible(true);

        classerEtudiantsParNom();
    }

    public void classerEtudiantsParNom() {
        ConnectionEtudiant cn = new ConnectionEtudiant();
        if (cn == null || cn.maConnection() == null) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données", null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT nom, prenom FROM table_etudiant ORDER BY nom ASC";
            PreparedStatement statement = cn.maConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<String> nomsEtudiants = new ArrayList<>();

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                nomsEtudiants.add(nom + " " + prenom);
            }

            resultSet.close();
            statement.close();
            cn.maConnection().close();

            StringBuilder sb = new StringBuilder();
            for (String nomEtudiant : nomsEtudiants) {
                sb.append(nomEtudiant).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du classement des étudiants : " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClasserEtudiants::new);
    }
}

