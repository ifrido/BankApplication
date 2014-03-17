package application;

import java.io.Serializable;
import java.util.Date;

/**
 * Buchung auf ein Bankkonto.
 * 
 * @author Frido Zurlinden / Christian Kiss
 * @version 1.0 / 27.01.2014
 */
public class Buchung implements Serializable {
  private static final long serialVersionUID = 5650666995154951130L;

  private double betrag;
  private Date ausfuehrung;
  private String text;

  public Buchung() {
  }

  public Buchung(double betrag, Date ausfuehrung, String text) {
    this.betrag = betrag;
    this.ausfuehrung = ausfuehrung;
    this.text = text;
  }

  public Date getAusfuehrung() {
    return ausfuehrung;
  }

  public double getBetrag() {
    return betrag;
  }

  public String getText() {
    return text;
  }
}
