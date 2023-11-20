/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class DateTimeUtils {

	final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static final String FORMAT_DATESTAMP = "yyyyMMdd";
	public static final String FORMAT_DATE_HUMAN = "dd/MM/yyyy";
	public static final String FORMAT_DATETIME_HUMAN = "dd/MM/yyyy HH:mm:ss";
	public static final String FORMAT_TIMESTAMP = "yyyyMMddHHmmssS";
	public static final String FORMAT_XML_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String FORMAT_XML_DATE = "yyyy-MM-dd";

	public static final DatatypeFactory datatypeFactory;
	private static final DateTimeFormatter xmlDatetimeFormatter;
	private static PeriodFormatter pfOreMinSec;
	private static PeriodFormatter pfGiorniOreMinSec;
	static {
		PeriodFormatterBuilder pfbOMS = new PeriodFormatterBuilder();
		pfbOMS.minimumPrintedDigits(2).printZeroAlways();
		pfbOMS.appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSeconds();
		pfOreMinSec = pfbOMS.toFormatter();

		PeriodFormatterBuilder pfbGOMS = new PeriodFormatterBuilder();
		pfbGOMS.minimumPrintedDigits(2).printZeroAlways();
		pfbGOMS.appendDays().appendLiteral("g ").appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSeconds();
		pfGiorniOreMinSec = pfbGOMS.toFormatter();

		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		xmlDatetimeFormatter = DateTimeFormat.forPattern(FORMAT_XML_DATE);
	}

	/**
	 * 
	 * @return il formatter per trasformare in hh:mm:ss
	 */
	public static PeriodFormatter getFmtOreMinSec() {
		return pfOreMinSec;
	}

	/**
	 * 
	 * @return il formatter per trasformare in gg hh:mm:ss
	 */
	public static PeriodFormatter getFmtGiorniOreMinSec() {
		return pfGiorniOreMinSec;
	}

	/**
	 * Formatta un testo da gg hh:mm:ss ad hh:mm:ss
	 * 
	 * @param tempo
	 * @return tempo in formato hh:mm:ss
	 * @throws IllegalArgumentException
	 */
	public static String toOreMinSec(String tempo) throws IllegalArgumentException {
		return pfOreMinSec.print(toPeriod(tempo).normalizedStandard(PeriodType.time()));
	}

	/**
	 * Formatta i {@code secondi} in hh:mm:ss
	 * 
	 * @param secondi
	 * @return tempo in formato hh:mm:ss
	 * @throws IllegalArgumentException
	 */
	public static String toOreMinSec(int secondi) throws IllegalArgumentException {
		return pfOreMinSec.print(Period.seconds(secondi).normalizedStandard(PeriodType.time()));
	}

	/**
	 * Formatta un testo da hh:mm:ss a gg hh:mm:ss
	 * 
	 * @param tempo
	 * @return tempo in formato gg hh:mm:ss
	 * @throws IllegalArgumentException
	 */
	public static String toGiorniOreMinSec(String tempo) throws IllegalArgumentException {
		return pfGiorniOreMinSec.print(toPeriod(tempo).normalizedStandard(PeriodType.dayTime()));
	}

	/**
	 * Formatta i {@code secondi} in gg hh:mm:ss
	 * 
	 * @param secondi
	 * @return tempo in formato gg hh:mm:ss
	 * @throws IllegalArgumentException
	 */
	public static String toGiorniOreMinSec(int secondi) throws IllegalArgumentException {
		return pfGiorniOreMinSec.print(Period.seconds(secondi).normalizedStandard(PeriodType.dayTime()));
	}

	/**
	 * Restituisce i secondi rappresentati dal parametro {@code tempo}
	 * 
	 * @param tempo
	 * @return tempo in secondi
	 * @throws IllegalArgumentException
	 */
	public static int toSeconds(String tempo) throws IllegalArgumentException {
		Period periodo = toPeriod(tempo);
		return periodo.toStandardSeconds().getSeconds();
	}

	/**
	 * Restituisce i minuti rappresentati dal parametro {@code tempo}
	 * 
	 * @param tempo
	 * @return tempo in minuti
	 * @throws IllegalArgumentException
	 */
	public static int toMinutes(String tempo) throws IllegalArgumentException {
		Period periodo = toPeriod(tempo);
		return periodo.toStandardMinutes().getMinutes();
	}

	/**
	 * Trasforma un testo in un periodo temporale
	 * 
	 * @param tempo
	 * @return periodo temporale
	 */
	private static Period toPeriod(String tempo) {
		Period periodo;
		try {
			periodo = pfGiorniOreMinSec.parsePeriod(tempo);
		} catch (IllegalArgumentException e) {
			periodo = pfOreMinSec.parsePeriod(tempo);
		}
		return periodo;
	}

	/**
	 * Formatta una data in {@code yyyy/MM/dd}
	 * 
	 * @param data
	 * @return la data formattata
	 */
	public static String formatDatestamp(Date data) {
		return new SimpleDateFormat(FORMAT_DATESTAMP).format(data);
	}

	/**
	 * Formatta una data in {@code dd/MM/yyyy}
	 * 
	 * @param data
	 * @return la data formattata
	 */
	public static String formatDateH(Date data) {
		return new SimpleDateFormat(FORMAT_DATE_HUMAN).format(data);
	}

	/**
	 * Formatta una data in {@code dd/MM/yyyy HH:mm:ss}
	 * 
	 * @param data
	 * @return la data formattata
	 */
	public static String formatDateTimeH(Date data) {
		return new SimpleDateFormat(FORMAT_DATETIME_HUMAN).format(data);
	}

	/**
	 * Formatta una data in {@code yyyyMMddHHmmssS}
	 * 
	 * @param data
	 * @return la data formattata
	 */
	public static String formatTimestamp(Date data) {
		return new SimpleDateFormat(FORMAT_TIMESTAMP).format(data);
	}

	/**
	 * Trasforma un testo in formato {@code yyyy/MM/dd} in un oggetto Date
	 * 
	 * @param data
	 * @return la data
	 * @throws ParseException
	 */
	public static Date parseDatestamp(String data) throws ParseException {
		return new SimpleDateFormat(FORMAT_DATESTAMP).parse(data);
	}

	/**
	 * Trasforma un testo in formato {@code dd/MM/yyyy} in un oggetto Date
	 * 
	 * @param data
	 * @return la data
	 * @throws ParseException
	 */
	public static Date parseDateH(String data) throws ParseException {
		return new SimpleDateFormat(FORMAT_DATE_HUMAN).parse(data);
	}

	/**
	 * Trasforma un testo in formato {@code dd/MM/yyyy HH:mm:ss} in un oggetto Date
	 * 
	 * @param data
	 * @return la data
	 * @throws ParseException
	 */
	public static Date parseDateTimeH(String data) throws ParseException {
		return new SimpleDateFormat(FORMAT_DATETIME_HUMAN).parse(data);
	}

	/**
	 * Trasforma una data in formato xml
	 * 
	 * @param data
	 * @param formatter
	 *            della data da interpretare
	 * @return la data in formato XMLGregorianCalendar
	 */
	public static final XMLGregorianCalendar getXMLGregorianCalendarFromDate(Date data, String formatter) {
		if (data != null) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
				XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(simpleDateFormat.format(data));
				cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				return cal;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public static final XMLGregorianCalendar getXMLGregorianCalendarFromDate(String iso8601Date) {
		final LocalDate date = (LocalDate) xmlDatetimeFormatter.parseLocalDate(iso8601Date);
		final GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		cal.setTime(date.toDate());
		final XMLGregorianCalendar xmlgc = datatypeFactory.newXMLGregorianCalendar(cal);
		xmlgc.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		return xmlgc;
	}

	public static final XMLGregorianCalendar getXMLGregorianCalendarFromYear(int year) {
		final XMLGregorianCalendar xmlgc = datatypeFactory.newXMLGregorianCalendarDate(year, DatatypeConstants.FIELD_UNDEFINED,
				DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
		return xmlgc;
	}

	/**
	 * Confronta se il valore di una determinata data soddisfa il tipo di formato scelto
	 * 
	 * @param format
	 *            il tipo di formato per la data scelto
	 * @param value
	 *            la data da verificare
	 * @return valore booleano che identifica se la data in input corrisponde al tipo di formato scelto
	 */
	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			logger.error("Errore durante la conversione della data");
			return false;
		} catch (IllegalArgumentException ex) {
			logger.error("Errore, formato non valido");
			return false;
		}
		return date != null;
	}

	public static DateTimeFormatter[] retrieveDateTimeFormatters(String... pattern) {
		if (pattern == null)
			return new DateTimeFormatter[0];
		final DateTimeFormatter[] fmts = new DateTimeFormatter[pattern.length];
		for (int i = 0; i < fmts.length; i++) {
			fmts[i] = DateTimeFormat.forPattern(pattern[i]);
		}
		return fmts;
	}

	public static DateTime tryParse(String dateStr, DateTimeFormatter... fmt) {
		if (StringUtils.isBlank(dateStr))
			return null;
		for (DateTimeFormatter dtfmt : fmt) {
			try {
				return dtfmt.parseDateTime(dateStr);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return null;
	}

	/*
	 * public static void main(String[] args) { String tempoOMS = "30:02:01"; System.out.println("tempoOMS : " + tempoOMS);
	 * System.out.println("pfOreMinSec secondi : " + toSeconds(tempoOMS)); System.out.println("pfGiorniOreMinSec string : " + toGiorniOreMinSec(tempoOMS));
	 * System.out.println("pfGiorniOreMinSec string sec : " + toGiorniOreMinSec(toSeconds(tempoOMS)));
	 * 
	 * String tempoGOMS = "40g 10:02:01"; System.out.println("tempoGOMS : " + tempoGOMS); System.out.println("pfGiorniOreMinSec secondi : " +
	 * toSeconds(tempoGOMS)); System.out.println("pfOreMinSec string : " + toOreMinSec(tempoGOMS)); System.out.println("pfOreMinSec string sec : " +
	 * toOreMinSec(toSeconds(tempoGOMS)));
	 * 
	 * String tempo = "01/01/2017 15:30:00"; String formatSql = "DD/MM/YYYY HH24:MI:SS"; System.out.println("Prova: " + isValidFormat(FORMAT_DATETIME_HUMAN,
	 * tempo)); System.out.println("Prova: " + isValidFormat(formatSql, tempo)); }
	 */

}
