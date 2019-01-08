package remifinal;

import java.awt.Color;

import java.awt.Cursor;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Remi extends JFrame {

	private JFrame frmRemi;
	private JPanel contentPane;
	private JRadioButton radioButtonIgracNaRedu;
	private JRadioButton radioButtonProtivnikNaRedu;
	private JLayeredPane layeredPaneIgrac;
	private JLayeredPane layeredPaneProtivnik;
	private JButton btnSpil;
	private JButton btnGomila;
	private JLayeredPane layeredPaneIgracOtvaranje;
	private JLayeredPane layeredPaneProtivnikOtvaranje;
	private JButton btnPresecenakarta;
	private JLabel lblImeIgraca;
	private JLabel lblImeProtivnika;
	private Igrac igrac;
	private Igrac protivnik;
	private Spil spil;
	private ArrayList<JButton> dugmad;
	private ArrayList<Karta> bacene;
	private ButtonGroup bttns;
	private Karta pomocna;
	private Karta zamena;
	private int ukupno;

	Border blackline = BorderFactory.createLineBorder(Color.black);
	Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Remi frame = new Remi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Nemoguce pokrenuti igru!", "Err.001!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public Remi() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1019, 753);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(34, 139, 34));
		contentPane.setLayout(null);
		contentPane.repaint();

		layeredPaneIgrac = new JLayeredPane();
		layeredPaneIgrac.setBounds(224, 520, 559, 162);
		contentPane.add(layeredPaneIgrac);

		layeredPaneProtivnik = new JLayeredPane();
		layeredPaneProtivnik.setBounds(224, 13, 559, 162);
		contentPane.add(layeredPaneProtivnik);

		btnSpil = new JButton();
		btnSpil.setBounds(24, 311, 97, 152);
		contentPane.add(btnSpil);

		btnGomila = new JButton();
		btnGomila.setBounds(133, 311, 97, 152);
		contentPane.add(btnGomila);

		btnPresecenakarta = new JButton();
		btnPresecenakarta.setBounds(24, 275, 97, 152);
		contentPane.add(btnPresecenakarta);

		radioButtonProtivnikNaRedu = new JRadioButton();
		radioButtonProtivnikNaRedu.setBounds(814, 124, 25, 25);
		radioButtonProtivnikNaRedu.setBackground(new Color(34, 139, 34));
		contentPane.add(radioButtonProtivnikNaRedu);

		radioButtonIgracNaRedu = new JRadioButton();
		radioButtonIgracNaRedu.setBounds(814, 631, 25, 25);
		radioButtonIgracNaRedu.setBackground(new Color(34, 139, 34));
		contentPane.add(radioButtonIgracNaRedu);

		lblImeIgraca = new JLabel();
		lblImeIgraca.setBounds(800, 656, 97, 16);
		contentPane.add(lblImeIgraca);
		lblImeIgraca.repaint();

		lblImeProtivnika = new JLabel();
		lblImeProtivnika.setBounds(800, 149, 97, 16);
		contentPane.add(lblImeProtivnika);
		lblImeProtivnika.repaint();

		layeredPaneIgracOtvaranje = new JLayeredPane();
		layeredPaneIgracOtvaranje.setBounds(335, 357, 559, 162); // y sredite kasnije
		layeredPaneIgracOtvaranje.setBorder(blackline);
		contentPane.add(layeredPaneIgracOtvaranje);

		layeredPaneProtivnikOtvaranje = new JLayeredPane();
		layeredPaneProtivnikOtvaranje.setBounds(335, 185, 559, 162); // y sredite kasnije
		layeredPaneProtivnikOtvaranje.setBorder(blackline);
		contentPane.add(layeredPaneProtivnikOtvaranje);

		bttns = new ButtonGroup();
		bttns.add(radioButtonIgracNaRedu);
		bttns.add(radioButtonProtivnikNaRedu);

		contentPane.repaint();

		initialize();
	}

	private void kreiranjeIgraca() {

		String ime = "";
		do {
			ime = JOptionPane.showInputDialog(null, "Vase ime:", "Ime", JOptionPane.QUESTION_MESSAGE);
		} while (ime.equals(""));

		igrac = new Igrac(ime);
		SecureRandom rand = new SecureRandom();

		do {
			ime = Imena.getImeProtivnika(rand.nextInt(14));
		} while (ime == igrac.ime);

		protivnik = new Igrac(ime);

		lblImeIgraca.setText(igrac.ime);
		lblImeProtivnika.setText(protivnik.ime);

		contentPane.repaint();
	}

	private void napraviSpil() {
		spil = new Spil();

		try {
			btnSpil.setIcon(new ImageIcon(ImageIO.read(new File("pozadina.png"))));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Neuspesno postavljanje pozadine za spil!");
			e.printStackTrace();
		}
	}

	private void promesajSpil() {
		spil.mesanje();
	}

	private Karta uzmi() {
		if (spil.preostaliBroj() == 0) {
			JOptionPane.showMessageDialog(null,
					"Potrosene su sve karte iz spila!\nPravi se novi spil i igra se nastavlja!", "Igra se nastavlja!",
					JOptionPane.WARNING_MESSAGE);
			napraviSpil();
			promesajSpil();
			return spil.deli();
		} else
			System.out.println("Izvukli ste: " + spil.deli());
			return spil.deli();
	}

	private void deliKarteIgracima() {

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 2; j++) {
				igrac.ruka.add(uzmi());
				protivnik.ruka.add(uzmi());
			}
		}

		System.out.println("Igrac ima karata: " + igrac.ruka.size());
		System.out.println("Protivnik ima karata: " + protivnik.ruka.size());
		if (naPotezu() == igrac && igrac.brKarataURuci() <= 14) {
			igrac.ruka.add(uzmi());
		} else if (naPotezu() == protivnik && protivnik.brKarataURuci() <= 14) {
			protivnik.ruka.add(uzmi());
		}
		System.out.println("Next Igrac ima karata: " + igrac.ruka.size());
		System.out.println("Next Protivnik ima karata: " + protivnik.ruka.size());

	}

	private void napraviDugmad() {
		dugmad = new ArrayList<JButton>();
		dugmad.clear();
	}

	private void napraviDugmadOtvaranje(Igrac who) {
		who.dugmadOtvaranje = new ArrayList<JButton>();
		who.dugmadOtvaranje.clear();
	}

	private void napraviGomilu() {
		bacene = new ArrayList<Karta>();
		bacene.clear();
	}

	private void dugmadRefill() {
		dugmad.clear();

		if (igrac.ruka.isEmpty()) {
			dugmad.clear();
		} else {
			for (int i = 0; i < igrac.ruka.size(); i++) {
				JButton b = new JButton();
				b.setBounds(10, 11, 97, 152);
				b.repaint();
				dugmad.add(b);
			}
		}
	}

	private void dugmadOtvaranjeRefill(Igrac who) {
		who.dugmadOtvaranje.clear();
		for (int i = 0; i < who.ruka.size(); i++) {
			JButton b = new JButton();
			b.setBounds(10, 11, 97, 152);
			b.repaint();
			who.dugmadOtvaranje.add(b);
		}
	}

	private void baciNaGomilu() {
		bacene.add(spil.deli());
	}

	private void gomilaUpdate() {
		btnGomila.setIcon(bacene.get(bacene.size() - 1).slika);
		btnGomila.repaint();
		contentPane.repaint();
	}

	private Karta naGomili() {
		return bacene.get(bacene.size() - 1);
	}

	private void novaPartija() {
		spil.reset();
		igrac.ruka.clear();
		protivnik.ruka.clear();
		btnGomila.setIcon(null);
		promesajSpil();
		deliKarteIgracima();
		dugmadRefill();
		baciNaGomilu();
		gomilaUpdate();
		radioButtonIgracNaRedu.setSelected(true);
		lpRepaint();
	}

	private void novaIgra() {
		kreiranjeIgraca();
		napraviSpil();
		promesajSpil();
		radioButtonIgracNaRedu.setSelected(true);
		deliKarteIgracima();
		postaviPresecenu();
		napraviDugmad();
		napraviDugmadOtvaranje(igrac);
		napraviDugmadOtvaranje(protivnik);
		napraviGomilu();
		dugmadRefill();
		baciNaGomilu();
		gomilaUpdate();
		lpRepaint();
	}

	private void sortiranje(Igrac who) {
		Karta pom1, pom2;

		for (int i = 0; i < who.ruka.size(); i++) {
			for (int j = 0; j < who.ruka.size(); j++) {
				if ((who.ruka.get(i).getBrojKarte().brojKarte() == who.ruka.get(j).getBrojKarte().brojKarte())
						&& (who.ruka.get(i).getZnakKarte().getZnak() < who.ruka.get(j).getZnakKarte().getZnak())) {
					pom1 = who.ruka.get(i);
					pom2 = who.ruka.get(j);
					who.ruka.set(i, pom2);
					who.ruka.set(j, pom1);
				} else if (who.ruka.get(i).getBrojKarte().brojKarte() < who.ruka.get(j).getBrojKarte().brojKarte()) {
					pom1 = who.ruka.get(i);
					pom2 = who.ruka.get(j);
					who.ruka.set(i, pom2);
					who.ruka.set(j, pom1);
				}
			}
		}
		
		ispisNizova(who);
		ispisTercova(who);
		otvaranje();
		obrisiNizove(who);
		obrisiTercove(who);
		ukupno = 0;
		layeredPaneIgrac.repaint();
		layeredPaneProtivnik.repaint();

	}

	private void postaviPresecenu() {
		Random rand = new Random();
		int v = rand.nextInt(spil.preostaliBroj());
		btnPresecenakarta.setIcon(spil.get(v).slika);
		btnPresecenakarta.repaint();
		contentPane.repaint();
		spil.remove(v);
	}

	private Igrac naPotezu() {
		if (radioButtonIgracNaRedu.isSelected())
			return igrac;
		else
			return protivnik;
	}

	private void baci(int k, Igrac prvi, Igrac drugi) {
		if (prvi.ruka.size() == 15 && drugi.ruka.size() == 14) {
			bacene.add(prvi.ruka.get(k));
			prvi.ruka.remove(k);
			gomilaUpdate();
			dugmadRefill();
			if (naPotezu() == igrac) {
				radioButtonProtivnikNaRedu.setSelected(true);
			} else
				radioButtonIgracNaRedu.setSelected(true);
			lpRepaint();
		}
	}

	// Trazenje nizova i tercova medju kartama

	private void nadjiNizove(Igrac who) {
		obrisiNizove(who);
		for (int i = 0; i < who.ruka.size(); i++) {
			who.ruka.get(i).setUNizu(false);
			int j = proveriNiz(i + 1, who.ruka.get(i).getZnakKarte(), who.ruka.get(i).getBrojKarte(), who);
			if (j > 0) {
				int k = proveriNiz(j + 1, who.ruka.get(j).getZnakKarte(), who.ruka.get(j).getBrojKarte(), who);
				if (k > 0) {
					int l = proveriNiz(k + 1, who.ruka.get(k).getZnakKarte(), who.ruka.get(k).getBrojKarte(), who);
					if (l > 0) {
						int m = proveriNiz(l + 1, who.ruka.get(l).getZnakKarte(), who.ruka.get(l).getBrojKarte(), who);
						if (m > 0) {
							int n = proveriNiz(m + 1, who.ruka.get(m).getZnakKarte(), who.ruka.get(m).getBrojKarte(),
									who);
							if (n > 0) {
								int o = proveriNiz(n + 1, who.ruka.get(n).getZnakKarte(),
										who.ruka.get(n).getBrojKarte(), who);
								if (o > 0) {
									int p = proveriNiz(o + 1, who.ruka.get(o).getZnakKarte(),
											who.ruka.get(o).getBrojKarte(), who);
									if (p > 0) {
										int r = proveriNiz(p + 1, who.ruka.get(p).getZnakKarte(),
												who.ruka.get(p).getBrojKarte(), who);
										if (r > 0) {
											int s = proveriNiz(r + 1, who.ruka.get(r).getZnakKarte(),
													who.ruka.get(r).getBrojKarte(), who);
											if (s > 0) {
												int t = proveriNiz(s + 1, who.ruka.get(s).getZnakKarte(),
														who.ruka.get(s).getBrojKarte(), who);
												if (t > 0) {
													int u = proveriNiz(t + 1, who.ruka.get(t).getZnakKarte(),
															who.ruka.get(t).getBrojKarte(), who);
													if (u > 0) {
														int v = proveriNiz(u + 1, who.ruka.get(u).getZnakKarte(),
																who.ruka.get(u).getBrojKarte(), who);
														if (v > 0) {
															dodajNiz(i, j, k, l, m, n, o, p, r, s, t, u, v, who);
														} else
															dodajNiz(i, j, k, l, m, n, o, p, r, s, t, u, who);
													} else
														dodajNiz(i, j, k, l, m, n, o, p, r, s, t, who);
												} else
													dodajNiz(i, j, k, l, m, n, o, p, r, s, who);
											} else
												dodajNiz(i, j, k, l, m, n, o, p, r, who);
										} else
											dodajNiz(i, j, k, l, m, n, o, p, who);
									} else
										dodajNiz(i, j, k, l, m, n, o, who);
								} else
									dodajNiz(i, j, k, l, m, n, who);
							} else
								dodajNiz(i, j, k, l, m, who);
						} else
							dodajNiz(i, j, k, l, who);
					} else
						dodajNiz(i, j, k, who);
				}
			}
		}
	}

	private int proveriNiz(int poz, ZnakKarte z, BrojKarte b, Igrac who) {
		int najdalji = poz + 4;
		for (; (poz < najdalji) && (poz < who.ruka.size()); poz++) {
			if (who.ruka.get(poz).getZnakKarte() == z
					&& who.ruka.get(poz).getBrojKarte().brojKarte() == (b.brojKarte() + 1)
					&& who.ruka.get(poz).getUNizu() == false && who.ruka.get(poz).getUTercu() == false) {
				return poz;
			}
		}
		return 0;
	}

	private void dodajNiz(int i, int j, int k, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, int n, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);
		who.nizovi[who.getBrNizova()][5] = who.ruka.get(n);
		who.ruka.get(n).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, int n, int o, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);
		who.nizovi[who.getBrNizova()][5] = who.ruka.get(n);
		who.ruka.get(n).setUNizu(true);
		who.nizovi[who.getBrNizova()][6] = who.ruka.get(o);
		who.ruka.get(o).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, int n, int o, int p, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);
		who.nizovi[who.getBrNizova()][5] = who.ruka.get(n);
		who.ruka.get(n).setUNizu(true);
		who.nizovi[who.getBrNizova()][6] = who.ruka.get(o);
		who.ruka.get(o).setUNizu(true);
		who.nizovi[who.getBrNizova()][7] = who.ruka.get(p);
		who.ruka.get(p).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, int n, int o, int p, int r, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);
		who.nizovi[who.getBrNizova()][5] = who.ruka.get(n);
		who.ruka.get(n).setUNizu(true);
		who.nizovi[who.getBrNizova()][6] = who.ruka.get(o);
		who.ruka.get(o).setUNizu(true);
		who.nizovi[who.getBrNizova()][7] = who.ruka.get(p);
		who.ruka.get(p).setUNizu(true);
		who.nizovi[who.getBrNizova()][8] = who.ruka.get(r);
		who.ruka.get(r).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, int n, int o, int p, int r, int s, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);
		who.nizovi[who.getBrNizova()][5] = who.ruka.get(n);
		who.ruka.get(n).setUNizu(true);
		who.nizovi[who.getBrNizova()][6] = who.ruka.get(o);
		who.ruka.get(o).setUNizu(true);
		who.nizovi[who.getBrNizova()][7] = who.ruka.get(p);
		who.ruka.get(p).setUNizu(true);
		who.nizovi[who.getBrNizova()][8] = who.ruka.get(r);
		who.ruka.get(r).setUNizu(true);
		who.nizovi[who.getBrNizova()][9] = who.ruka.get(s);
		who.ruka.get(s).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, int n, int o, int p, int r, int s, int t, Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);
		who.nizovi[who.getBrNizova()][5] = who.ruka.get(n);
		who.ruka.get(n).setUNizu(true);
		who.nizovi[who.getBrNizova()][6] = who.ruka.get(o);
		who.ruka.get(o).setUNizu(true);
		who.nizovi[who.getBrNizova()][7] = who.ruka.get(p);
		who.ruka.get(p).setUNizu(true);
		who.nizovi[who.getBrNizova()][8] = who.ruka.get(r);
		who.ruka.get(r).setUNizu(true);
		who.nizovi[who.getBrNizova()][9] = who.ruka.get(s);
		who.ruka.get(s).setUNizu(true);
		who.nizovi[who.getBrNizova()][10] = who.ruka.get(t);
		who.ruka.get(t).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, int n, int o, int p, int r, int s, int t, int u,
			Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);
		who.nizovi[who.getBrNizova()][5] = who.ruka.get(n);
		who.ruka.get(n).setUNizu(true);
		who.nizovi[who.getBrNizova()][6] = who.ruka.get(o);
		who.ruka.get(o).setUNizu(true);
		who.nizovi[who.getBrNizova()][7] = who.ruka.get(p);
		who.ruka.get(p).setUNizu(true);
		who.nizovi[who.getBrNizova()][8] = who.ruka.get(r);
		who.ruka.get(r).setUNizu(true);
		who.nizovi[who.getBrNizova()][9] = who.ruka.get(s);
		who.ruka.get(s).setUNizu(true);
		who.nizovi[who.getBrNizova()][10] = who.ruka.get(t);
		who.ruka.get(t).setUNizu(true);
		who.nizovi[who.getBrNizova()][11] = who.ruka.get(u);
		who.ruka.get(u).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void dodajNiz(int i, int j, int k, int l, int m, int n, int o, int p, int r, int s, int t, int u, int v,
			Igrac who) {
		who.nizovi[who.getBrNizova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUNizu(true);
		who.nizovi[who.getBrNizova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUNizu(true);
		who.nizovi[who.getBrNizova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUNizu(true);
		who.nizovi[who.getBrNizova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUNizu(true);
		who.nizovi[who.getBrNizova()][4] = who.ruka.get(m);
		who.ruka.get(m).setUNizu(true);
		who.nizovi[who.getBrNizova()][5] = who.ruka.get(n);
		who.ruka.get(n).setUNizu(true);
		who.nizovi[who.getBrNizova()][6] = who.ruka.get(o);
		who.ruka.get(o).setUNizu(true);
		who.nizovi[who.getBrNizova()][7] = who.ruka.get(p);
		who.ruka.get(p).setUNizu(true);
		who.nizovi[who.getBrNizova()][8] = who.ruka.get(r);
		who.ruka.get(r).setUNizu(true);
		who.nizovi[who.getBrNizova()][9] = who.ruka.get(s);
		who.ruka.get(s).setUNizu(true);
		who.nizovi[who.getBrNizova()][10] = who.ruka.get(t);
		who.ruka.get(t).setUNizu(true);
		who.nizovi[who.getBrNizova()][11] = who.ruka.get(u);
		who.ruka.get(u).setUNizu(true);
		who.nizovi[who.getBrNizova()][12] = who.ruka.get(v);
		who.ruka.get(v).setUNizu(true);

		who.setBrNizova(who.getBrNizova() + 1);
	}

	private void ispisNizova(Igrac who) {
		nadjiNizove(who);
		for (int i = 0; i < who.nizovi.length; i++) {
			for (int j = 0; j < who.nizovi.length; j++) {
				if (who.nizovi[i][j] != null) {
					System.out.println(" " + who.nizovi[i][j]);
				
				}
			}
		}
	}

	public void obrisiNizove(Igrac who) {
		for (int i = 0; i < who.ruka.size(); i++) {
			who.ruka.get(i).setUNizu(false);
		}
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 4; k++) {
				who.nizovi[j][k] = null;
			}
		}
		who.setBrNizova(0);
	}

	public void nadjiTercove(Igrac who) {
		obrisiTercove(who);
		for (int i = 0; i < who.ruka.size(); i++) {
			who.ruka.get(i).setUNizu(false);
			int j = proveriTerc(i + 1, who.ruka.get(i).getZnakKarte(), who.ruka.get(i).getBrojKarte(), who);
			if (j > 0) {
				int k = proveriTerc(j + 1, who.ruka.get(j).getZnakKarte(), who.ruka.get(j).getBrojKarte(), who);
				if (k > 0) {
					int l = proveriTerc(k + 1, who.ruka.get(k).getZnakKarte(), who.ruka.get(k).getBrojKarte(), who);
					if (l > 0) {
						dodajTerc(i, j, k, l, who);
					} else {
						dodajTerc(i, j, k, who);
					}
				}
			}
		}
	}

	private int proveriTerc(int poz, ZnakKarte z, BrojKarte b, Igrac who) {
		int najdalji = poz + 4;
		for (; (poz < najdalji) && (poz < who.ruka.size()); poz++) {
			if (who.ruka.get(poz).getBrojKarte() == b) {
				if (who.ruka.get(poz).getZnakKarte().getZnak() != z.getZnak() && who.ruka.get(poz).getUNizu() == false
						&& who.ruka.get(poz).getUTercu() == false) {
					return poz;
				}
			}
		}
		return 0;
	}

	private void dodajTerc(int i, int j, int k, Igrac who) {
		who.tercovi[who.getBrTercova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUTercu(true);
		who.tercovi[who.getBrTercova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUTercu(true);
		who.tercovi[who.getBrTercova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUTercu(true);

		who.setBrTercova(who.getBrTercova() + 1);
	}

	private void dodajTerc(int i, int j, int k, int l, Igrac who) {
		who.tercovi[who.getBrTercova()][0] = who.ruka.get(i);
		who.ruka.get(i).setUTercu(true);
		who.tercovi[who.getBrTercova()][1] = who.ruka.get(j);
		who.ruka.get(j).setUTercu(true);
		who.tercovi[who.getBrTercova()][2] = who.ruka.get(k);
		who.ruka.get(k).setUTercu(true);
		who.tercovi[who.getBrTercova()][3] = who.ruka.get(l);
		who.ruka.get(l).setUTercu(true);

		who.setBrTercova(who.getBrTercova() + 1);
	}

	private void ispisTercova(Igrac who) {
		nadjiTercove(who);
		for (int i = 0; i < who.tercovi.length; i++) {
			for (int j = 0; j < who.tercovi.length; j++) {
				if (who.tercovi[i][j] != null) {
					System.out.println(" " + who.tercovi[i][j]);
				}
			}
		}
	}

	public void obrisiTercove(Igrac who) {
		for (int i = 0; i < who.ruka.size(); i++) {
			who.ruka.get(i).setUTercu(false);
		}
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 4; k++) {
				who.tercovi[j][k] = null;
			}
		}
		who.setBrTercova(0);
	}

	private void baciNaSto(int k, Igrac who) {
		//who.ruka.remove(k);
		dugmadRefill();
		dugmadOtvaranjeRefill(who);
		layeredPaneIgrac.repaint();

		//System.out.println("\n\nBacene: " + who.otvaranje);
		//lpRepaint();
	}

	private boolean moguceOtvaranje(Igrac who) {

		int i, j;
		for (i = 0; i < who.nizovi.length; i++) {
			for (j = 0; j < who.nizovi.length; j++) {
				if (who.nizovi[i][j] != null)
					ukupno = ukupno + who.nizovi[i][j].getVrednost();
			}
		}
		for (i = 0; i < who.tercovi.length; i++) {
			for (j = 0; j < who.tercovi.length; j++) {
				if (who.tercovi[i][j] != null)
					ukupno = ukupno + who.tercovi[i][j].getVrednost();
			}
		}
		System.out.println("\n\nUKUPNO JE: " + ukupno);

		if (ukupno >= 51) {
			return true;
		} else {
			return false;
		}
	}

	private void zamena(Karta pomocna) {
		if (pomocna.getJoker() || pomocna.equals(zamena)) {
			try {
				ZnakKarte znak = ZnakKarte.valueOf(
						JOptionPane.showInputDialog("Uneti znak karte za zamenu dzokera: \n(herc, tref, pik, karo) ")
								.toUpperCase());
				BrojKarte broj = BrojKarte.valueOf(JOptionPane
						.showInputDialog("Uneti broj karte za zamenu dzokera: \n(dva, tri, cetiri, pet, sest,"
								+ "sedam, osam, devet, deset, a, j, q, k)")
						.toUpperCase());
				pomocna.setZnakKarte(znak);
				pomocna.setBrojKarte(broj);
				zamena = pomocna;
				dugmadRefill();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Greska u kucanju, uneti ponovo.");
			}
		}
	}

	//Ovo ne moze
	private void otvaranje() {
		layeredPaneIgracOtvaranje.removeAll();
		if (moguceOtvaranje(igrac)) {

			JButton btnOtvaranje = new JButton("Otvaranje");
			btnOtvaranje.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int brDugmadOtvaranje = igrac.dugmadOtvaranje.size();
					
					for(int i = 0; i < igrac.ruka.size(); i++) {
						
						nadjiNizove(igrac);
						nadjiTercove(igrac);
						if(igrac.ruka.get(i).getUTercu() || igrac.ruka.get(i).getUNizu()) {
		
						//igrac.otvaranje.add(igrac.ruka.get(i));
							baciNaSto(i, igrac);
							
							//igrac.ruka.remove(i);
							dugmadRefill();
							layeredPaneIgrac.repaint();
							System.out.println("Reachable code? BR DUGMAD OTVARANJE: " + brDugmadOtvaranje);
							if (brDugmadOtvaranje == 0) {
								//layeredPaneIgracOtvaranje.removeAll();
								layeredPaneIgracOtvaranje.repaint();
							} else {
								int simetral = layeredPaneIgracOtvaranje.getWidth() / brDugmadOtvaranje;
								

									JButton b = new JButton();
									b.setText(null);
									b.setBounds(i * (simetral - (layeredPaneIgracOtvaranje.getWidth() / 97 - 16 / 13)),
											11, 97, 152);
									layeredPaneIgracOtvaranje.add(b);
									b.setVisible(true);
									b.setIcon(igrac.ruka.get(i).slika);
									b.repaint();
									layeredPaneIgracOtvaranje.repaint();
									layeredPaneProtivnikOtvaranje.repaint();
									
									b.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											
											//igrac.ruka.add(igrac.otvaranje.get(k));
											b.setVisible(false);
											b.setIcon(null);
											b.repaint();
											remove(b);
											layeredPaneIgracOtvaranje.repaint();
											
										}
									});
									
									
									
										
									
								
							}
						}
					}
						layeredPaneIgracOtvaranje.repaint();
						
						dugmadOtvaranjeRefill(igrac);
						layeredPaneIgrac.repaint();
						contentPane.repaint();
					
				}
			});
			btnOtvaranje.setBounds(823, 565, 97, 25);
			contentPane.add(btnOtvaranje);
			btnOtvaranje.setVisible(true);
			btnOtvaranje.setBorder(blackline);
			contentPane.repaint();
		} 
	}

	private void lpRepaint() {
		
		layeredPaneIgrac.removeAll();
		if (naPotezu() == igrac) {
			System.out.println("Nizovi/tercovi igaca: ");
			sortiranje(igrac);
		} else {
			System.out.println("Nizovi/tercovi protivnika: ");
			sortiranje(protivnik);
		}
		

		dugmadRefill();
		dugmadOtvaranjeRefill(igrac);
		if (naPotezu() == protivnik) {
			protivnik.ruka.add(uzmi());
			System.out.println("\nVuce protivnik: ");
			System.out.println("Next Igrac ima karata: " + igrac.ruka.size());
			System.out.println("Next Protivnik ima karata: " + protivnik.ruka.size());
		}

		int brDugmad = dugmad.size();
		int layeredPaneIgracIgracWidth = layeredPaneIgrac.getWidth();

		if (brDugmad == 0) {
			layeredPaneIgrac.removeAll();
			layeredPaneIgrac.repaint();
			JOptionPane.showMessageDialog(null, "Dobijena partija");
		} else {
			int simetral = layeredPaneIgracIgracWidth / brDugmad;
			for (int i = 0; i < brDugmad; i++) {
				int k = i;

				JButton b = new JButton();
				b.setText(null);
				b.setBounds(i * (simetral - (layeredPaneIgracIgracWidth / 97 - 16 / 13)), 11, 97, 152);

				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (naPotezu() == igrac) {
							pomocna = igrac.ruka.get(k);
							zamena(pomocna);
							if (!pomocna.getJoker()) {
								baci(k, igrac, protivnik);
							}
						}
					}
				});

				layeredPaneIgrac.add(b);
				b.setVisible(true);
				b.setIcon(igrac.ruka.get(i).slika);
				b.repaint();
				layeredPaneIgrac.repaint();
				layeredPaneProtivnik.repaint();
			}
			layeredPaneIgrac.repaint();
			layeredPaneProtivnik.repaint();
			layeredPaneProtivnik.removeAll();

			if (protivnik.ruka.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Izgubili ste");
				novaPartija();
			}
			for (int i = 0; i < protivnik.ruka.size(); i++) {

				int simetralProtivnik = layeredPaneProtivnik.getWidth() / protivnik.ruka.size();
				int k = i;
				JButton b = new JButton();
				b.setText(null);
				b.setBounds(i * (simetralProtivnik - (layeredPaneProtivnik.getWidth() / 97 - 16 / 13)), 11, 97, 152);

				if (naPotezu() == protivnik) {
					if (protivnik.ruka.get(k).getUNizu() == true || protivnik.ruka.get(k).getUTercu() == true) {
						System.out.println("Ne bacati.");
					} else {
						baci(k, protivnik, igrac);
						System.out.println("Protivnik je bacio nesto sto nije u nizu ni tercu.");
					}
				}

				layeredPaneProtivnik.add(b);
				b.setVisible(true);

				try {
					b.setIcon(new ImageIcon(ImageIO.read(new File("pozadina.png"))));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Neuspesno postavljanje pozadine za dugme!");
					e.printStackTrace();
				}

				b.repaint();
				layeredPaneIgrac.repaint();
				layeredPaneProtivnik.repaint();

			}
			layeredPaneIgrac.repaint();
			layeredPaneProtivnik.repaint();
		}

		btnSpil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (radioButtonIgracNaRedu.isSelected() && igrac.ruka.size() <= 14) {
					igrac.ruka.add(uzmi());
					dugmadRefill();
					lpRepaint();
				}
			}
		});

		btnGomila.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (moguceOtvaranje(igrac) && radioButtonIgracNaRedu.isSelected() && igrac.ruka.size() <= 14) {
					igrac.ruka.add(naGomili());
					dugmadRefill();
					bacene.remove(bacene.size() - 1);
					gomilaUpdate();
					lpRepaint();
				}
			}
		});

		System.out.println("\n\nPozvan lpRepaint i: ");
		System.out.println("Next Igrac ima karata: " + igrac.ruka.size());
		System.out.println("Next Protivnik ima karata: " + protivnik.ruka.size());

		if (brDugmad == 0) {
			layeredPaneIgrac.removeAll();
			layeredPaneIgrac.repaint();

			
		}
		if (protivnik.ruka.isEmpty()) {

			novaPartija();
		}

		layeredPaneIgrac.repaint();
		layeredPaneProtivnik.repaint();

		layeredPaneIgracOtvaranje.repaint();
		layeredPaneProtivnikOtvaranje.repaint();

	}

	private void initialize() {

		frmRemi = new JFrame();
		frmRemi.setTitle("Remi");
		frmRemi.setBounds(100, 100, 1151, 645);
		frmRemi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRemi.setLocale(new Locale("sr", "RS"));
		frmRemi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		frmRemi.setResizable(false);
		frmRemi.setLocationByPlatform(true);
		frmRemi.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		frmRemi.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		frmRemi.getContentPane().setLayout(new BoxLayout(frmRemi.getContentPane(), BoxLayout.X_AXIS));

		novaIgra();
	}
}
