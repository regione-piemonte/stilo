package it.eng.core.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe di utilita' per le regular expression
 * @author upescato
 */

//Va scritto, per ogni pattern regex definito, il corrispondente metodo statico che effettua 
//il check di validita' di una stringa in input rispetto al pattern
public class RegexUtil {

	//Pattern regex attualmente definiti
	private static final String E_MAIL = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
	private static final String COD_FISCALE = "^[A-Z]{6}[\\d]{2}[A-Z][\\d]{2}[A-Z][\\d]{3}[A-Z]$"; 

	
	//Metodo generico che evita di riscrivere sempre le righe di codice di pattern e matcher
	private static boolean check(String regex, String input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	/**
	 * Metodo che verifica se la stringa passata in input corrisponde ad un indirizzo email sintatticamente valido
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		return check(E_MAIL, email);
	}

	/**
	 * Metodo che verifica se la stringa passata in input corrisponde ad un codice fiscale sintatticamente valido
	 * @param codiceFiscale
	 * @return
	 */
	public static boolean checkCodiceFiscale(String codiceFiscale) {
		return check(COD_FISCALE, codiceFiscale);
	}
}
