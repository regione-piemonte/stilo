package it.eng.document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoData {

	static final String PATTERN_DATA = "dd/MM/yyyy HH:mm";
	static final String PATTERN_DATA_SENZA_ORA = "dd/MM/yyyy";
	static final String PATTERN_DATA_ESTESA = "dd/MM/yyyy HH:mm:ss";

	Tipo tipo();

	public enum Tipo {
		DATA(PATTERN_DATA), DATA_SENZA_ORA(PATTERN_DATA_SENZA_ORA), DATA_ESTESA(PATTERN_DATA_ESTESA);
		private String pattern;

		private Tipo(String pPattern) {
			pattern = pPattern;
		}

		public String getPattern() {
			return pattern;
		}
	}
}
