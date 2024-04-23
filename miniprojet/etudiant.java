package miniprojet;
import java.util.*;
public class etudiant {
	private String nom,prenom,ddn;
	public ArrayList<Float> notes;
	etudiant(String nom,String prenom,String ddn){
		this.nom=nom;
		this.prenom=prenom;
		this.ddn=ddn;
		this.notes=new ArrayList<Float>();
	}
	public String getnom() {
		return this.nom;
	}
	public String getprenom() {
		return this.prenom;
	}
	public String getddn() {
		return this.ddn;
	}
	public Float getnote(int i) {
		return notes.get(i);
	}
	public void setnom(String nom) {
		this.nom=nom;
	}
	public void setprenom(String prenom) {
		this.prenom=prenom;
	}
	public void setdd(String ddn) {
		this.ddn=ddn;
	}
	public void setnotes(ArrayList<Float>notes) {
		this.notes=notes;
	}
	public String toString() {
		return "Nom:"+nom+"-"+"Prenom:"+prenom+"-"+"Date de Naissaince:"+ddn+"-"+"Notes"+notes;
	}
}
