package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Bank implementiert die Business-Schicht 
 * und gibt die Resultate als Strings zurück.
 *
 * @author Frido Zurlinden / Christian Kiss
 * @version 1.0 / 27.01.2014
 */
public class Bank {
  /**
   * Name der Datei, die die Kunden speichert.
   */
  private static final String DATEINAME = "bank.dat";
  /**
   * Stammnummer der Bank.
   */
  private static String kontonummer = "262-65111780";
  /**
   * Automatisch generierte Kontonummer am Ende der Stammnummer.
   */
  private static long aktuelleNr = 0;
  private static Bank bank = null;
  private List<Kunde> kunden;
  private boolean admin;
  private File datei;

  /**
   * Konstruktor, in dem die Variablen instantiiert werden.
   */
  private Bank() {
    admin = false;
    kunden = new ArrayList<Kunde>();
    datei = new File(DATEINAME);
    einlesen();
  }

  /**
   * Holt alle Kunden.
   * @return Liste aller Kunden
   */
  public List<Kunde> holeKunden() { 
    if (kunden.size() == 0) {
      return null;
    }
    return kunden;
  }

  /**
   * Holt alle Konten eines Kunden.
   * @param kunde Kunde der Konten
   * @return Liste der Konten des Kunden
   */
  public List<Konto> holeKonten(Kunde kunde) { 
    return kunde.getKonten();
  }

  /**
   * Authentifiziert einen Administrator oder einen Kunden.
   * @param kundennummer Nummer des Kunden bei der Bank
   * @param passwort Passwort des Kunden (nicht verschlüsselt!)
   * @return Gibt den identifizierten Kunden oder null zurück
   */
  public Kunde login(String kundennummer, String passwort) {
    if (kundennummer.equals("admin") && passwort.equals("admin")) {
      admin = true;
    }
    for (Kunde k : kunden) {
      if (k.getKundennummer().equals(kundennummer) && k.getPasswort().equals(passwort)) {
        return k;
      }
    }
    return null;
  }
  
  /**
   * Abmelden eines Kunden oder Administrators.
   */
  public void logout() {
    admin = false;
  }
  
  /**
   * Ein neuer Kunde wird aufgenommen.
   * @param kundennummer Nummer des Kunden bei der Bank
   * @param passwort Passwort des Kunden (nicht verschlüsselt!)
   * @param name Name des Kunden
   * @return Gibt den neuen Kunden zurück
   */
  public Kunde neuerKunde(String kundennummer, String passwort, String name) {
    Kunde kunde = new Kunde(kundennummer, passwort, name);
    kunden.add(kunde);
    speichern();
    return kunde;
  }
  
  /**
   * Ein neues Konto wird eröffnet.
   * @param betrag Eröffnungsbetrag
   * @return Das neue Konto wird zurückgegeben
   */
  public Konto neuesKonto(double betrag) { 
    Konto konto = new Konto(kontonummer + ++aktuelleNr);
    konto.getBuchungen().add(new Buchung(betrag, new Date(), "Neues Konto"));
    speichern();
    return konto;
  }

  /**
   * Auf die mitgegebene Kontonummer wird der Betrag eingezahlt.
   * @param kontonummer Kontonummer zu einem Konto
   * @param betrag Betrag, der einzuzahlen ist
   * @return Das modifizierte Konto wird zurück gegeben
   */
  public Konto einzahlen(String kontonummer, double betrag) { 
    Konto konto = sucheKontonummer(kontonummer);
    if (konto != null) {
      konto.getBuchungen().add(new Buchung(betrag, new Date(), "Eingezahlt"));
      speichern();
    }
    return konto;
  }

  /**
   * Auf die mitgegebene Kontonummer wird der Betrag abgehoben.
   * @param kontonummer Kontonummer zu einem Konto
   * @param betrag Betrag, der abzuheben ist
   * @return Das modifizierte Konto wird zurück gegeben
   */
  public Konto abheben(String kontonummer, double betrag) { 
    Konto konto = sucheKontonummer(kontonummer);
    if (konto != null) {
      konto.getBuchungen().add(new Buchung(-betrag, new Date(), "Abgehoben"));
      speichern();
    }
    return konto;
  }


  /**
   * Erweiterung Christian Kiss
   * Geld überweisen
   * @param kontonummer Kontonummer zu einem Konto
   * @param betrag Betrag, der einzuzahlen ist
   * @return Das modifizierte Konto wird zurück gegeben
   */

  public Konto ueberweisung(String kontonummer, double betrag, String zielkonto, String mitteilung) { 
	    
	  	// Konto von dem der Abgang stattfindet
	  	Konto konto = sucheKontonummer(kontonummer);
	  	if (konto != null) {
		      
	  		konto.getBuchungen().add(new Buchung(-betrag, new Date(), "Überweisung " + mitteilung));
		      

	  		// Zielkontonummer, falls auf der selben Bank, dann überweisung intern
			Konto kontoZiel = sucheKontonummer(zielkonto);
			if (kontoZiel != null) {
				kontoZiel.getBuchungen().add(new Buchung(betrag, new Date(), "Überweisung " + mitteilung));
			}
			 
			speichern();
	  	}
	  	
	    return konto;
	  }
  
 
  
  /**
   * Holt das Konto für die mitgegebene Kontonummer.
   * @param kontonummer Kontonummer zu einem Konto
   * @return Das Konto wird zurück gegeben oder null
   */
  public Konto holeKonto(String kontonummer) { 
    Konto konto = sucheKontonummer(kontonummer);
    if (konto != null) {
      return konto;
    }
    return null;
  }

  /**
   * Holt den Kunden für die mitgegebene Kundennummer.
   * @param kundennummer Kundennummer eines Kunden
   * @return Der Kunde wird zurück gegeben oder null
   */
  public Kunde holeKunde(String kundennummer) {
    for (Kunde k : kunden) {
      if (k.getKundennummer().equals(kundennummer)) {
        return k;
      }
    }
    return null;
  }

  /**
   * Aus der Kundendatei werden die Kunden eingelesen.
   */
  @SuppressWarnings("unchecked")
  private void einlesen() { 
    if (datei.exists()) {
      try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datei));
        kunden = (List<Kunde>)ois.readObject();
        ois.close();
        // aktuelle Kontonummer berechnen
        long groessteNummer = 0;
        for (Kunde ku : kunden ) {
          for (Konto ko : ku.getKonten()) {
            String letzteNummer = ko.getNummer();
            int index = letzteNummer.indexOf("0");
            long koNr = Long.parseLong(letzteNummer.substring(index + 1));
            if (koNr > groessteNummer) {
              groessteNummer = koNr;
            }
          }
        }
        aktuelleNr = groessteNummer;
      }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
  }

  
  // musste in Public geändert werden, da sonst Kundenänderungen nicht gespeichert werden können!
  // Christian Kiss
  /**
   * In die Kundendatei werden die Kunden eingeschrieben.
   */
  public void speichern() { 
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(datei));
      oos.writeObject(kunden);
      oos.close();
    }
    catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Sucht das Konto mittels der Kontonummer.
   * @param kontonummer Nummer eines Konto
   * @return Gibt das Konto oder null zurück
   */
  private Konto sucheKontonummer(String kontonummer) {
    for (Kunde ku : kunden) {
      for (Konto ko : ku.getKonten()) {
        if (ko.getNummer().equals(kontonummer)) {
          return ko;
        }
      }
    }
    return null;
  }
  
  public boolean isAdmin() {
    return admin;
  }

  public static Bank getInstance() {
    if (bank == null) {
      bank = new Bank();
    }
    return bank;
  }

  /**
   * Sortiert die Buchungen aufsteigend nach Datum.
   *
   * @author Ruedi Baumann
   * @date 17.02.2010
   * @version 1.0
   */
  class BuchungComparator implements Comparator<Buchung>, Serializable { 
    private static final long serialVersionUID = -6584678583506641314L;

    public int compare(Buchung b1, Buchung b2) {      
      if (b1.getAusfuehrung().before(b2.getAusfuehrung())) {
        return -1;
      }
      else if (b1.getAusfuehrung().after(b2.getAusfuehrung())) {
        return 1;
      }
      return 0;
    }
  }  
}