package remifinal;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;


public class Igrac {
	
	public String ime;
	public ArrayList<Karta> ruka;
	public ArrayList <JButton> dugmadOtvaranje;
	public ArrayList <Karta> otvaranje;
	public Karta[][] nizovi = new Karta[12][12];
	public Karta[][] tercovi = new Karta[4][4];
	private int brNizova = 0;
	private int brTercova = 0;
	

	public int getBrNizova() {
		return brNizova;
	}

	public void setBrNizova(int brNizova) {
		this.brNizova = brNizova;
	}

	public int getBrTercova() {
		return brTercova;
	}

	public void setBrTercova(int brTercova) {
		this.brTercova = brTercova;
	}

	public Igrac(String ime) {
		this.ime=ime;
		ruka = new ArrayList<Karta>();
	}

	public ArrayList<Karta> getRuka() {
		return ruka;
	}
	
	public void setRuka(ArrayList<Karta> ruka) {
		this.ruka = ruka;
	}
	
	public int getUkVrednostRuke() {
		return ukVrednostRuke();
	}
	
	public int ukVrednostRuke() {
	
		int suma = 0;
		for(Karta k : ruka) {
			suma+=k.getBrojKarte().brojKarte();
		}
		return suma;
	}

	public int brKarataURuci() {
		return ruka.size();
	}
	
	public void predajSe() {
		JOptionPane.showMessageDialog(null,"Predali ste se.","Igra je gotova.",JOptionPane.INFORMATION_MESSAGE);
		ruka.clear();	
	}
}
