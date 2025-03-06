package it.eng.utility.cryptosigner.controller.impl.timestamp;

import it.eng.utility.cryptosigner.controller.ITimeStampValidator;
import it.eng.utility.cryptosigner.controller.bean.TimeStampValidityBean;

import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.tsp.TimeStampToken;

/**
 * Esegue la validazione corrente di una marca temporale
 * basandosi sulla data recuperata dalla chiamata Calendar.getInstance()
 * @author Administrator
 *
 */
public class TimeStampValidator implements ITimeStampValidator {

	public boolean isTimeStampCurrentlyValid(TimeStampToken timeStamp,
			TimeStampValidityBean timeStampValidity) {
		Calendar currentCalendar = Calendar.getInstance(); 
		return isTimeStampValidAtDate(timeStamp, timeStampValidity, currentCalendar.getTime());
	}

	
	
	public boolean isTimeStampExtended(TimeStampToken timeStampToValidate,
			TimeStampValidityBean timeStampToValidateValidity,
			TimeStampToken timeStampExtension,
			TimeStampValidityBean timeStampExtensionValidity) {
		// Controllo se il primo timestamp e correntemente valido
//		if (isTimeStampCurrentlyValid(timeStampToValidate, timeStampToValidateValidity))
//			return true;
		//Controllo se l'estensione del timestamp e correntemente valida
//		if (!isTimeStampCurrentlyValid(timeStampExtension, timeStampExtensionValidity))
//			return false;
	
		Calendar timeStampToValidateCalendar = Calendar.getInstance();
		timeStampToValidateCalendar.setTime(timeStampToValidate.getTimeStampInfo().getGenTime());
		
		timeStampToValidateCalendar.add(Calendar.YEAR, timeStampToValidateValidity.getYears());
		
		//FIXME: cambiare year..
//		timeStampToValidateCalendar.add(Calendar.MILLISECOND, timeStampToValidateValidity.getYears());
		
		Calendar timeStampExtensionCalendar = Calendar.getInstance();
		timeStampExtensionCalendar.setTime(timeStampExtension.getTimeStampInfo().getGenTime());
		
		if (timeStampExtensionCalendar.after(timeStampToValidateCalendar))
			return false;
		
		return true;
	}

	public boolean isTimeStampValidAtDate(TimeStampToken timeStamp,
			TimeStampValidityBean timeStampValidity, Date referenceDate) {
		
		Date timeStampDate = new Date();
		if (timeStamp!=null) {
			timeStampDate = timeStamp.getTimeStampInfo().getGenTime();	
		}
		
		
		Calendar timeStampCalendar = Calendar.getInstance();
		timeStampCalendar.setTime(timeStampDate);
		
		// Controllo che la data del timestamp sia successiva 
		// all'entrata in vigore del periodo di validita
		if (timeStampValidity!=null) {
			Date validityBegin = timeStampValidity.getBegin();
			if (validityBegin!= null) {
				Calendar validityBeginCal = Calendar.getInstance();
				validityBeginCal.setTime(validityBegin);
				if (validityBeginCal.after(timeStampCalendar))
					return false;
			}
		}
		if (timeStampValidity!=null) {
			timeStampCalendar.add(Calendar.YEAR, timeStampValidity.getYears());
		}
		//FIXME: cambiare year..
//		timeStampCalendar.add(Calendar.MINUTE, timeStampValidity.getYears());
		
		if (referenceDate.after(timeStampCalendar.getTime()))
			return false;
		
		return true;
	}

}
