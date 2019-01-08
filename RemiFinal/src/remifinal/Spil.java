package remifinal;

import java.io.File;


import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Spil {

	public ArrayList<Karta> spil;

	public Spil() {
		this.spil = new ArrayList<Karta>();
		try {
			kreirajSpil();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Greska!");
			e.printStackTrace();
		}
	}

	public void remove(int i) {
		spil.remove(spil.get(i));
	}
	
	public Karta get(int v) {
		return spil.get(v);
	}

	public boolean dovoljanBrojKarata() {
		if (spil.size() == 112)
			return true;
		else
			return false;
	}

	public void mesanje() {
		for (int i = spil.size() - 1; i > 0; i--) {
			int rand = (int) (Math.random() * (i + 1));
			Karta temp = spil.get(i);
			spil.set(i, spil.get(rand));
			spil.set(rand, temp);
		}
	}

	public int preostaliBroj() {
		return spil.size();
	}

	public Karta deli() {
		if (spil.size() > 0) {
			Karta pom = spil.get(preostaliBroj() - 1);
			spil.remove(preostaliBroj() - 1);
			return pom;
		} else
			return null;
	}

	public void kreirajSpil() throws IOException {
		int k = 0;
		String imeSlike = null;
		while (k < 2) {
			for (ZnakKarte z : ZnakKarte.values()) {
				for (BrojKarte b : BrojKarte.values()) {
					try {
						imeSlike = b.toString().toUpperCase() + "" + z.toString().toLowerCase() + ".png";
						System.out.println("Pravi se: " + imeSlike);
						ImageIcon slika = new ImageIcon(ImageIO.read(new File(imeSlike)));

						this.spil.add(new Karta(z, b, slika));
					} catch (Exception e) {
						System.out.println(imeSlike + " - nije napravljeno!");
					}
				}
			}
			k++;
		}
	}

	public void reset() {
		spil.clear();
		try {
			kreirajSpil();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Nemoguce kreirati spil!", "Err.005", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public String toString() {
		String prikaz = "";
		int i = 0;
		for (Karta k : this.spil) {
			prikaz += "\n" + (i + 1) + ".  " + k.toString();
			i++;
		}
		return prikaz;
	}
}
