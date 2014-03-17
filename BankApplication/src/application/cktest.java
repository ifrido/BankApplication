package application;

import java.util.Calendar;
import java.util.List;

public class cktest {

	public static void main(String[] args) {
		
		Bank bank = Bank.getInstance();
		
		//bank.holeKonto(kontonummer)
		

	    
	    
//	    // Neuen Kunden inkl erstes Bankkonto hinzufügen 
//	    for (int i=0; i<5; i++) {
//	    	bank.neuerKunde("abc"+i, "passwort", "Kunde"+i);
//	    }
	    
		
		// Konto anlegen
		// Problem, legt nur ein Konto an ?!?
//		Kunde kundeKonto = bank.holeKunde("abc0");
//	    Konto neuesKonto = bank.neuesKonto(0);
//	    kundeKonto.addKonto(neuesKonto);
//	    
//
//		// Geld einzahlen
//		String einzahlenKontonummer = "262-651117801";
////		
//		double einzahlenBetrag = 100.00;
//		bank.einzahlen(einzahlenKontonummer, einzahlenBetrag);
//		
//		
//		double abhebenBetrag = 50.00;
//		bank.abheben(einzahlenKontonummer, abhebenBetrag);
////		
//
////		
////		List<Buchung> buchungen = bank.holeKonto(einzahlenKontonummer).getBuchungen();
////		for (Buchung q : buchungen) {
////			System.out.println(q.getBetrag() + " " + q.getText() + " " + q.getAusfuehrung());
////		}
//
//		
//
//		
//	    //bank.einzahlen(kontonummer, betrag);
//	    //System.out.println(bank.holeKonten(bank.holeKunde("abc2")));
//
//	    
//	    
//	    // Original
//	    // http://stackoverflow.com/questions/9705440/prefill-listview-in-an-application-with-fxml
//       
//	    //String bla = "Max Müller - 123456123456";
//	    //System.out.println(bla);
//	    //System.out.println(bla.replaceAll("[^\\d]", ""));
//	    
//	    
//	    
	    
	    //bank.holeKunde("111").addKonto(bank.holeKonto(bank.neuesKonto(0).getNummer()));
	    //bank.holeKunde("12").addKonto(new Konto());
	    //System.out.println(bank.holeKunde("12").getKonten());
	    

		//alle Kunden auflisten
//	    for (Kunde q : bank.holeKunden()) {
//	        System.out.println("Name: " + q.getName() + "\nKundennummer: " + q.getKundennummer());
//	        System.out.println(bank.holeKonten(q));
//	        System.out.println();
//	      }
	    
	    
//	    for (Konto q : bank.holeKunden()) {
//	        System.out.println(q.getNummer());
//	        System.out.println();
//	      }
//	    
//	    
//	 
		
		Calendar jetzt = Calendar.getInstance();
		System.out.println(jetzt.get(Calendar.YEAR)); // 2007
		System.out.println(jetzt.get(Calendar.MONTH)); // 10
		System.out.println(jetzt.get(Calendar.HOUR)); // 18
		
		while(true){
			System.out.println(jetzt.get(Calendar.SECOND));
		}
	    
	    
		
	}
	
	
	
}
