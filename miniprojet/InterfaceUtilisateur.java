package miniprojet;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InterfaceUtilisateur {
	private JTextField nomf;
	private JTextField prenomf;
	private JTextField ddnf;
	private JTextField notesf;
	ConnectionEtudiant cn = new ConnectionEtudiant();
	InterfaceUtilisateur(){
		JFrame frame=new JFrame("User-Interface:Saisie des donnees");
		JPanel panel=new JPanel(new GridLayout(5,2,4,4));
		
		JLabel noml=new JLabel("Nom:");
		nomf=new JTextField();
		panel.add(noml);
		panel.add(nomf);
		
		JLabel prenoml=new JLabel("Prenom:");
		prenomf=new JTextField();
		panel.add(prenoml);
		panel.add(prenomf);
		
		JLabel ddnl=new JLabel("Date de Naissaince :"); 
		ddnf=new JTextField();
		panel.add(ddnl);
		panel.add(ddnf);
		
		JLabel notesl=new JLabel("Les notes des matieres:"); //3 NOTES AU MAX SEPAREE PAR UN VIRGULE
		notesf=new JTextField();
		panel.add(notesl);
		panel.add(notesf);
		
		
		JButton save=new JButton("Sauvegarder"); 
		panel.add(save);
		save.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {
                save(e);
            }
        });
		
		frame.add(panel);   
		frame.setLocationRelativeTo(null);
		frame.setSize(300,200);   
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
		
	}
	public void save(ActionEvent e) {
	    if (cn == null || cn.maConnection() == null) {
	        JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données", null, JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    String nom = nomf.getText();
	    String prenom = prenomf.getText();
	    String ddn = ddnf.getText();
	    String notes = notesf.getText();
	    List<Float> listeNotes = new ArrayList<>();
	    String[] sousChaines = notes.split(",");
	    for (String sousChaine : sousChaines) {
	        try {
	            float note = Float.parseFloat(sousChaine.trim());
	            listeNotes.add(note);
	        } catch (NumberFormatException f) {
	            System.err.println("Erreur de conversion pour la sous-chaîne: " + sousChaine);
	        }
	    }
	    
	    
	    
	    if (nom.isEmpty() || prenom.isEmpty() || ddn.isEmpty() || notes.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs", null, JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    String rq = "INSERT INTO table_etudiant (nom, prenom, ddn, note1, note2, note3) VALUES (?, ?, ?, ?, ?, ?)";
	    try {
	        PreparedStatement ps = cn.maConnection().prepareStatement(rq);
	        ps.setString(1, nom);
	        ps.setString(2, prenom);
	        ps.setString(3, ddn);
	        ps.setFloat(4, listeNotes.get(0));
	        ps.setFloat(5, listeNotes.get(1));
	        ps.setFloat(6, listeNotes.get(2));
	        ps.executeUpdate();
	        JOptionPane.showMessageDialog(null, "Étudiant enregistré avec succès !", null, JOptionPane.INFORMATION_MESSAGE);
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion de l'étudiant : " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
	    }
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceUtilisateur();
            }
        });
    }
	
}
