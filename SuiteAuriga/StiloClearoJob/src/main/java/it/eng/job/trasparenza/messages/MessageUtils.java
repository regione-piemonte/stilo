/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class MessageUtils {

	private static final String bundleName = MessageUtils.class.getPackage().getName() + ".Messages";

	// private static final Map<String, String> messageCache = new ConcurrentHashMap<String, String>(0);

	private MessageUtils() {
	}

	public static final String getMessaggio(MessageCodeEnum messageCode, Object... listaParametri) {
		/*
		 * StringBuilder sbk = new StringBuilder(messageCode.name()).append("v#v").append(StringUtils.join(listaParametri, "^#^")); String k = sbk.toString();
		 * if (messageCache.containsKey(k)) { System.out.println("from cache: " + messageCache.get(k)); return messageCache.get(k); }
		 */

		ResourceBundle resBundle = ResourceBundle.getBundle(bundleName);
		String message = resBundle.getString(messageCode.name());
		String v = MessageFormat.format(risolviDipendenze(message), listaParametri);
		// messageCache.put(k, v);
		return v;
	}

	private static String risolviDipendenze(String message) {
		Pattern pat = Pattern.compile("\\{([^\\d\\{\\}][^\\{\\}]*)\\}");
		Matcher matcher = pat.matcher(message);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, getMessaggio(MessageCodeEnum.valueOf(matcher.group(1))));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static final String getMessaggioColonna(Object numColonna, MessageCodeEnum messageCode, Object... listaParametri) {
		/*
		 * StringBuilder sbk = new
		 * StringBuilder("COLONNA").append(numColonna).append("v#v").append(messageCode.name()).append("v#v").append(StringUtils.join(listaParametri, "^#^"));
		 * String k = sbk.toString(); if (messageCache.containsKey(k)) { System.out.println("from cache: " + messageCache.get(k)); return messageCache.get(k); }
		 */

		ResourceBundle resBundle = ResourceBundle.getBundle(bundleName);
		StringBuffer sb = new StringBuffer();
		MessageFormat mf = new MessageFormat(resBundle.getString(MessageCodeEnum.COLONNA.name()));
		mf.format(new Object[] { numColonna }, sb, null);
		sb.append(", ");
		mf.applyPattern(StringUtils.uncapitalize(resBundle.getString(messageCode.name())));
		mf.format(listaParametri, sb, null);
		String v = sb.toString();
		// messageCache.put(k, v);
		return v;
	}

	public static void main(String[] args) {
		/*
		 * System.out.println(getMessaggio(MessageCodeEnum.LOTTO_DEL, "nome_lotto.txt", new Date())); System.out.println();
		 * System.out.println(getMessaggio(MessageCodeEnum.MAIL_FILE_NON_CANCELLATI, "fileNonCanc.txt")); System.out.println();
		 * System.out.println(getMessaggio(MessageCodeEnum.ERRORE_SEZIONALE_NON_TROVATO)); System.out.println();
		 * System.out.println(getMessaggio(MessageCodeEnum.ERRORE_SEZIONALE_NON_TROVATO)); System.out.println();
		 * System.out.println(getMessaggio(MessageCodeEnum.ERRORE_DATA_FUTURA, getMessaggio(MessageCodeEnum.DATA_FATTURA))); System.out.println();
		 * System.out.println(getMessaggio(MessageCodeEnum.ERRORE_DATA_FUTURA, getMessaggio(MessageCodeEnum.DATA_FATTURA))); System.out.println(); int
		 * numColonna = 1; System.out.println(getMessaggioColonna(numColonna, MessageCodeEnum.ERRORE_SEZIONALE_NON_TROVATO)); System.out.println();
		 * System.out.println(getMessaggioColonna(numColonna, MessageCodeEnum.ERRORE_DATA_FUTURA, getMessaggio(MessageCodeEnum.DATA_FATTURA)));
		 * System.out.println(); System.out.println(getMessaggioColonna(numColonna, MessageCodeEnum.ERRORE_DATA_FUTURA,
		 * getMessaggio(MessageCodeEnum.DATA_FATTURA))); System.out.println();
		 * System.out.println(getMessaggio(MessageCodeEnum.MAIL_ALLEGATO_DOCUMENTI_PRESENTE));
		 */
	}
}
