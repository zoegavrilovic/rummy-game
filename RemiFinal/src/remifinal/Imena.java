package remifinal;

public enum Imena {
	Mina, Biba, Zo, Razumenka, Bole, DuskaBubble, Olga, Milan, Djurdja, Sara, Slavko, Stefan, Marko, Zuca, Aljosa;

	public static String getImeProtivnika(int i) {
		switch (i) {
		case 0:
			return String.valueOf(Imena.Mina);
		case 1:
			return String.valueOf(Imena.Biba);
		case 2:
			return String.valueOf(Imena.Zo);
		case 3:
			return String.valueOf(Imena.Razumenka);
		case 4:
			return String.valueOf(Imena.Bole);
		case 5:
			return String.valueOf(Imena.DuskaBubble);
		case 6:
			return String.valueOf(Imena.Olga);
		case 7:
			return String.valueOf(Imena.Milan);
		case 8:
			return String.valueOf(Imena.Djurdja);
		case 9:
			return String.valueOf(Imena.Sara);
		case 10:
			return String.valueOf(Imena.Slavko);
		case 11:
			return String.valueOf(Imena.Stefan);
		case 12:
			return String.valueOf(Imena.Marko);
		case 13:
			return String.valueOf(Imena.Zuca);
		case 14:
			return String.valueOf(Imena.Aljosa);

		default:
			return "Robot";
		}
	}
}
