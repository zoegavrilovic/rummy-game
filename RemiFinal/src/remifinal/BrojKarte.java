package remifinal;

public enum BrojKarte {

	JOKER(20), DVA(2), TRI(3), CETIRI(4), PET(5), SEST(6), SEDAM(7), OSAM(8), DEVET(9), DESET(10), J(11), Q(
			12), K(13), A(14); 

	private final int broj;

	BrojKarte(int b) {
		this.broj = b;
	}

	public int brojKarte() {
		return broj;
	}
}
