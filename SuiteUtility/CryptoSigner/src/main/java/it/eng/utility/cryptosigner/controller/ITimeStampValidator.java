package it.eng.utility.cryptosigner.controller;

import it.eng.utility.cryptosigner.controller.bean.TimeStampValidityBean;

import java.util.Date;

import org.bouncycastle.tsp.TimeStampToken;

/**
 * Definisce l'interfaccia di una classe che implementa i controlli 
 * sulla validita temporale di una marca. 
 * @author Stefano Zennaro
 *
 */
public interface ITimeStampValidator {

	/**
	 * Controlla se il timeStamp in input e attualmente valido 
	 * rispetto al periodo di validita specificato in input.
	 * Una tipica implementazione prevede il recupero della data attuale 
	 * da una fonte attendibile e successivamente il suo confronto con la data
	 * riportata nella marca temporale tenendo in considerazione il periodo di validita.
	 * @param timeStamp
	 * @param timeStampValidity
	 * @return true se la marca temporale in input e attualmente valida
	 */
	public boolean isTimeStampCurrentlyValid(TimeStampToken timeStamp, TimeStampValidityBean timeStampValidity);
	

	/**
	 * Controlla se il periodo di validita di un timestamp e esteso 
	 * correttamente da un ulteriore timestamp.<br/>
	 * Attenzione: il controllo di corretta associazione tra 
	 * tra il timestamp e la sue estensione non e previsto e 
	 * deve essere implementato a parte.
	 * @param timeStampToValidate timestamp su cui validare l'estensione
	 * @param timeStampToValidateValidity periodo di validita del timestamp da validare
	 * @param timeStampExtension estensione del timestamp
	 * @param timeStampExtensionValidity  periodo di validita dell'estensione del timestamp
	 * @return boolean
	 */
	public boolean isTimeStampExtended(TimeStampToken timeStampToValidate, TimeStampValidityBean timeStampToValidateValidity,
			TimeStampToken timeStampExtension, TimeStampValidityBean timeStampExtensionValidity);
	
	/**
	 * Controlla se il timestamp in input era valido nella data 
	 * specificata rispetto al suo periodo di validita.
	 * Verifica cioe se il riferimento temporale ricade nel periodo di
	 * validita del timestamp 
	 * @param timeStamp timestamp da validare
	 * @param timeStampValidity periodo di validita del timestamp
	 * @param referenceDate data di riferimento
	 * @return boolean
	 */
	public boolean isTimeStampValidAtDate(TimeStampToken timeStamp, TimeStampValidityBean timeStampValidity, Date referenceDate);
}
