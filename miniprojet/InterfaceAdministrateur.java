package miniprojet;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.*;

import miniprojet.AffichageEtudiant;
import miniprojet.ModifierEtudiant;
import miniprojet.ClasserEtudiants;

public class InterfaceAdministrateur extends JFrame{
	private Connection cn;
	private JLabel labelNom, labelPrenom, labelDdn, labelNotes;
	InterfaceAdministrateur(){
		JFrame frame=new JFrame("Admin-Interface");
		JPanel panel=new JPanel(new GridLayout(5,1,5,5));
		JButton afficher=new JButton("Afficher les etudiants");
		JButton modifier=new JButton("Modifier etudiant");
		JButton supprimer=new JButton("Supprimer etudiant");
		JButton classer=new JButton("Trier les etudiants");
		JButton exit=new JButton("Exit");
		
	afficher.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	afficherEtudiants();
        }
    });
	modifier.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			modifierEtudiant();
		}
	});
	supprimer.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			supprimerEtudiant();
		}
	});
	classer.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			classerEtudiant();
		}
	});
	exit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	});
	panel.add(afficher);
	panel.add(modifier);
	panel.add(supprimer);
	panel.add(classer);
	panel.add(exit);
	
	frame.add(panel);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setLocation(450,250);
	frame.setSize(300,200);
	frame.setVisible(true);
	
}
	private boolean existeEtudiant(String name) {
        ConnectionEtudiant cn = new ConnectionEtudiant();
        if (cn == null || cn.maConnection() == null) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données", null, JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            String query = "SELECT COUNT(*) FROM table_etudiant WHERE nom=?";
            PreparedStatement statement = cn.maConnection().prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
            resultSet.close();
            statement.close();
            cn.maConnection().close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la vérification de l'existence de l'étudiant : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
	public void afficherEtudiants() {
		AffichageEtudiant affichage = new AffichageEtudiant();
        affichage.afficherEtudiants();
	}
	public void supprimerEtudiant() {
        String nom = JOptionPane.showInputDialog("Entrer le nom de l'étudiant à supprimer :");
        if (nom != null && !nom.isEmpty()) {
            if (existeEtudiant(nom)) {
                SuppressionEtudiant.supprimerEtudiant(nom);
            } else {
                JOptionPane.showMessageDialog(null, "Aucun étudiant trouvé avec le nom spécifié", "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nom d'étudiant invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    
	public void classerEtudiant() {
		ClasserEtudiants classerEtudiantsParNom = new ClasserEtudiants();
		classerEtudiantsParNom.setVisible(true);
	}
	public void modifierEtudiant() {
	    String nom = JOptionPane.showInputDialog("Entrer le nom de l'étudiant à modifier :");
	    if (nom != null && !nom.isEmpty()) {
	        if (existeEtudiant(nom)) {
	            new ModifierEtudiant(nom);
	        } else {
	            JOptionPane.showMessageDialog(null, "Aucun étudiant trouvé avec le nom spécifié", "Avertissement", JOptionPane.WARNING_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Nom d'étudiant invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
}
