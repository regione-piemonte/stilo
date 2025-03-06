package it.eng.utility.cryptosigner.controller.bean;

import java.util.Calendar;
import java.util.Date;

/**
 * Bean contenente le informazioni sul periodo di validita di una marca temporale.
 * Detto T il momento di applicazione della marca temporale, questa risulta valida se:<br/>
 * 	begin &le; T &lt; end AND T + years &ge; [Data Attuale] 
 * @author Stefano Zennaro
 *
 */
public class TimeStampValidityBean implements Comparable<TimeStampValidityBean> {

	// Inizio validita
	private Date	begin = null;
	
	// Fine validita
	private Date	end = null;
	
	// Anni di validita
	private int		years;

	
	/**
	 * Recupera la durata della validita in anni
	 * @return int
	 */
	public int getYears() {
		return years;
	}

	/**
	 * Definisce la durata della validita in anni 
	 * @param years
	 */
	public void setYears(int years) {
		this.years = years;
	}

	/**
	 * Recupera la data di scadenza del tipo di marca temporale
	 * @return date
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * Definisce la data di scadenza del tipo di marca temporale
	 * @param end
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * Recupera la data di entrata in vigore del tipo di marca temporale
	 * @return date
	 */
	public Date getBegin() {
		return begin;
	}

	/**
	 * Definisce la data di entrata in vigore del tipo di marca temporale
	 * @param begin
	 */
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	
	public int compareTo(TimeStampValidityBean o) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		if (o == null)
			return 1;
		if (this.begin==null) {
			if (o.begin==null) {
				if (this.end == null) {
					if (o.end==null)
						return 0;
					else 
						return 1;
				}
				else {
					if (o.end==null)
						return -1;
					else{
						cal1.setTime(this.end);
						cal2.setTime(o.end);
						return cal1.before(cal2) ? -1 : 1;
					}
				}
			} else {
				return -1;
			}
		} else {
			if (o.begin==null)
				return 1;
			else{
				cal1.setTime(this.begin);
				cal2.setTime(o.begin);
				return cal1.before(cal2) ? -1 : 1;
			}
		}
	}
	
	
	public String toString() {
		return "begin: " + this.begin + ", end: " + this.end + ", years: " + this.years;
	}
}
