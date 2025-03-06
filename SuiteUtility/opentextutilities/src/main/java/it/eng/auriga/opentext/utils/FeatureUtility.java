package it.eng.auriga.opentext.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.LoggerFactory;

import it.eng.auriga.opentext.service.cs.impl.AuthenticationCSServiceImpl;

public class FeatureUtility {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationCSServiceImpl.class);

	/**
	 * costruisce il pathFolder per il COntent Server a partire dall' anno e dal
	 * mese
	 * 
	 * @return
	 */
	public static List<String> buildAnnoMese() {
		Calendar today = Calendar.getInstance();
		int anno = today.get(Calendar.YEAR);
		int mese = today.get(Calendar.MONTH);
		mese++;

		List<String> pathAnnoMese = new ArrayList<String>();
		pathAnnoMese.add(String.valueOf(anno));
		pathAnnoMese.add(mese < 10 ? "0" + String.valueOf(mese) : String.valueOf(mese));
		return pathAnnoMese;

	}

	/**
	 * costruisce il pathFolder per il COntent Server a partire dall' anno e dal
	 * mese
	 * 
	 * @return
	 */
	public static List<String> buildAnnoMeseGiorno() {
		Calendar today = Calendar.getInstance();
		int anno = today.get(Calendar.YEAR);
		int mese = today.get(Calendar.MONTH);
		int giorno = today.get(Calendar.DAY_OF_MONTH);
		mese++;
		List<String> pathAnnoMeseGiorno = new ArrayList<String>();
		pathAnnoMeseGiorno.add(String.valueOf(anno));
		pathAnnoMeseGiorno.add(mese < 10 ? "0" + String.valueOf(mese) : String.valueOf(mese));
		pathAnnoMeseGiorno.add(giorno < 10 ? "0" + String.valueOf(giorno) : String.valueOf(giorno));
		return pathAnnoMeseGiorno;

	}

	/**
	 * costruisce il path sul ContentServer fino alla folder che equivale alla plant
	 * 
	 * @param rootFolder
	 * @param plant
	 * @return
	 */
	public static List<String> buildPlantPath(String rootFolder, String plant) {
		List<String> plantPath = new ArrayList<String>();

		plantPath.add(rootFolder);
		plantPath.add(plant);

		return plantPath;

	}

	/**
	 * per ogni numero, restituisce il carattere dell'alfabeto corrispondente
	 * 
	 * @param i
	 * @return
	 */
	public static String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
	}

	/**
	 * codifica la password che arriva dal front end per confrontarla con quella presente salvata sul DB ed inviata da PMS
	 * @param clearTextPassword
	 * @return
	 */
	public static String getEncodedSHA256Password(String clearTextPassword) {

		MessageDigest digest;
		StringBuffer hexString = new StringBuffer();
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(clearTextPassword.getBytes("UTF-8"));
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			logger.error("Errore nel criptare la password in Sha-256 " + e.getMessage(), e);
			hexString.append("");
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("Errore nel criptare la password in Sha-256 " + e.getMessage(), e);
			hexString.append("");
		}

		return hexString.toString();
	}

	
	public static void main(String[] args) {
		System.out.println(FeatureUtility.buildAnnoMeseGiorno());
	}
}
