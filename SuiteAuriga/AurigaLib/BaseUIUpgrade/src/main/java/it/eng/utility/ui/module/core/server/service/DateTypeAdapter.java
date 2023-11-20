/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	public static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

	public static final SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");

	public static final SimpleDateFormat datetime_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public static final SimpleDateFormat datetime_format_toda = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

	public static final SimpleDateFormat display_date_format = new SimpleDateFormat("dd/MM/yyyy");

	public static final SimpleDateFormat display_datetime_format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public static final SimpleDateFormat upload_datetime_format_italian = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.ITALIAN);
	
	public static final SimpleDateFormat upload_datetime_format_english = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.ENGLISH);

	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}

		/**
		 * COMMENTATO PERCHE' HA ANCORA PROBLEMI CON IL TIMEZONE - LE DATE NON VENGONO CONVERTITE CORRETTAMENTE
		 * 
		String date = json.getAsString();
		
		try {
			datetime_format.setTimeZone(TimeZone.getTimeZone("UTC"));
			return datetime_format.parse(date);
		} catch (ParseException e) {
		}
		try {
			date_format.setTimeZone(TimeZone.getTimeZone("UTC"));
			return date_format.parse(date);
		} catch (ParseException e) {
		}
		try {
			display_datetime_format.setTimeZone(TimeZone.getTimeZone("UTC"));
			return display_datetime_format.parse(date);
		} catch (ParseException e) {
		}
		try {
			display_date_format.setTimeZone(TimeZone.getTimeZone("UTC"));
			return display_date_format.parse(date);
		} catch (ParseException e) {
		}
		**/
		
		try {
			return datetime_format.parse(json.getAsString());
		} catch (ParseException e) {}	
		try {
			return date_format.parse(json.getAsString());
		} catch (ParseException e) {}		
		try {
			return display_datetime_format.parse(json.getAsString());
		} catch (ParseException e) {}	
		try {
			return display_date_format.parse(json.getAsString());
		} catch (ParseException e) {}	
		try {
			return upload_datetime_format_italian.parse(json.getAsString());
		} catch (ParseException e) {}	
		try {
			return upload_datetime_format_english.parse(json.getAsString());
		} catch (ParseException e) {}	
		
		throw new JsonParseException("Unparseable date " + json.getAsString());
	}

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

		String dateFormatAsString = display_datetime_format.format(src);
		return new JsonPrimitive(dateFormatAsString);
	}
}