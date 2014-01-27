package application;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Kunde einer Bank.
 *
 * @author Frido Zurlinden / Christian Kiss
 * @version 1.0 / 27.01.2014
 */
public class Kunde implements Serializable {
  private static final long serialVersionUID = 1660302109623167466L;
  private String kundennummer;
  private String passwort;
  private String name;
  private List<Konto> konten;
  
  public Kunde() {
    konten = new ArrayList<Konto>();
  }

  public Kunde(String kundennummer, String passwort, String name) {
    this();
    this.kundennummer = kundennummer;
    this.passwort = passwort;
    this.name = name;
    konten.add(Bank.getInstance().neuesKonto(0.0));
  }

  public String getKundennummer() {
    return kundennummer;
  }

  public void setKundennummer(String kundennummer) {
    this.kundennummer = kundennummer;
  }

  public String getPasswort() {
    return passwort;
  }

  public void setPasswort(String passwort) {
    this.passwort = passwort;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Konto> getKonten() {
    return konten;
  }

  public void setKonten(List<Konto> konten) {
    this.konten = konten;
  }

  public void addKonto(Konto konto) {
    konten.add(konto);
  }
}
