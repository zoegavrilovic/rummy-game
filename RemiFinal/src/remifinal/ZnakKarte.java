package remifinal;

public enum ZnakKarte {

	HERC(1), PIK(2), TREF(3), KARO(4);

	final int znak;
	
	ZnakKarte(int z) {
		this.znak = z;
	}

	public int getZnak() {
		return znak;
	}
}
