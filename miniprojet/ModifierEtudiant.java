package miniprojet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModifierEtudiant extends JFrame {
    private JTextField textFieldNom, textFieldPrenom, textFieldDateNaissance, textFieldNote1, textFieldNote2, textFieldNote3;
    private JButton btnSauvegarder, btnAnnuler;

    public ModifierEtudiant(String nom) {
        setTitle("Modifier Étudiant");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textFieldNom = new JTextField(20);
        textFieldPrenom = new JTextField(20);
        textFieldDateNaissance = new JTextField(20);
        textFieldNote1 = new JTextField(20);
        textFieldNote2 = new JTextField(20);
        textFieldNote3 = new JTextField(20);
        btnSauvegarder = new JButton("Sauvegarder");
        btnAnnuler = new JButton("Annuler");

        setLayout(new GridLayout(4, 2));
        add(new JLabel("Nom:"));
        add(textFieldNom);
        add(new JLabel("Prénom:"));
        add(textFieldPrenom);
        add(new JLabel("Date de Naissance:"));
        add(textFieldDateNaissance);
        add(new JLabel("Note 1:"));
        add(textFieldNote1);
        add(new JLabel("Note 2:"));
        add(textFieldNote2);
        add(new JLabel("Note 3:"));
        add(textFieldNote3);
        add(btnSauvegarder);
        add(btnAnnuler);

        
        btnSauvegarder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sauvegarderModifications(nom);
            }
        });
        btnAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void sauvegarderModifications(String name) {
    	String nom = textFieldNom.getText();
        String prenom = textFieldPrenom.getText();
        String ddn = textFieldDateNaissance.getText();
        String notestr1 = textFieldNote1.getText();
        float note1 = Float.parseFloat(notestr1);
        String notestr2 = textFieldNote2.getText();
        float note2 = Float.parseFloat(notestr1);
        String notestr3 = textFieldNote3.getText();
        float note3 = Float.parseFloat(notestr1);
        
        

        ConnectionEtudiant cn = new ConnectionEtudiant();
        Connection conn = cn.maConnection();

        if (conn != null) {
            try {
                String query = "UPDATE table_etudiant SET prenom=?, ddn=?, nom=?, note1=?, note2=?, note3=? WHERE nom=?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, prenom);
                statement.setString(2, ddn);
                statement.setString(3, nom);
                statement.setFloat(4, note1);
                statement.setFloat(5, note2);
                statement.setFloat(6, note3);
                statement.setString(7, name);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Modifications sauvegardées avec succès !");
                } else {
                    JOptionPane.showMessageDialog(null, "Échec de la sauvegarde des modifications", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                statement.close();
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour des informations de l'étudiant : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
