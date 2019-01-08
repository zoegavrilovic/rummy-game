package remifinal;

import javax.swing.ImageIcon;

public class Karta {
	private boolean stih;
	private boolean joker;
	private BrojKarte brojKarte;
	private ZnakKarte znakKarte;
	public ImageIcon slika;
	private boolean uNizu = false;
	private boolean uTercu = false;
	private int vrednost;

	public Karta(ZnakKarte znakKarte, BrojKarte brojKarte, ImageIcon slika) {
		
		this.znakKarte = znakKarte;
		this.brojKarte = brojKarte;
		this.slika = slika;
	
		if(brojKarte == BrojKarte.A || brojKarte == BrojKarte.J || brojKarte == BrojKarte.Q 
				|| brojKarte == BrojKarte.K || brojKarte == BrojKarte.DESET) {
			this.stih=true;
		}
		else
			this.stih=false;
		
		if(brojKarte == BrojKarte.JOKER) 
			this.joker = true;
		else this.joker = false;		
	}
	
	public int getVrednost() {
		if(getStih()) {
			vrednost = 10;
		} else if(getJoker()) {
			vrednost = 20;
		} else {
			vrednost = getBrojKarte().brojKarte();
		}
		return vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}

	public BrojKarte getBrojKarte() {
		return this.brojKarte;
	}
	
	public ZnakKarte getZnakKarte() {
		return this.znakKarte;
	}
	
	public void setBrojKarte(BrojKarte brojKarte) {
		this.brojKarte = brojKarte;
	}

	public void setZnakKarte(ZnakKarte znakKarte) {
		this.znakKarte = znakKarte;
	}
	
	public boolean getStih() {
		return this.stih;
	}
	
	public boolean getJoker() {
		return this.joker;
	}
	
	public void setJoker(boolean joker) {
		this.joker = joker;
	}

	public boolean getUNizu() {
		return uNizu;
	}

	public void setUNizu(boolean uNizu) {
		this.uNizu = uNizu;
	}


	public boolean getUTercu() {
		return uTercu;
	}


	public void setUTercu(boolean uTercu) {
		this.uTercu = uTercu;
	}
	
	public String toString(){
		return this.znakKarte.toString() + " - " + this.brojKarte.toString();
	}
	
}
