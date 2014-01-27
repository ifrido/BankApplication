package application;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse implementiert das Konto.
 *
 * @author Frido Zurlinden / Christian Kiss
 * @version 1.0 / 27.01.2014
 */
public class Konto implements Serializable {
  private static final long serialVersionUID = 9194785062997852540L;
  private String nummer;
  private List<Buchung> buchungen;
  
  public Konto() {
    buchungen = new ArrayList<Buchung>();
  }

  public Konto(String nummer) {
    this();
    this.nummer = nummer;
  }

  public String getNummer() {
    return nummer;
  }

  public List<Buchung> getBuchungen() {
    return buchungen;
  }

  public String toString() {
    return nummer;
  }
}
