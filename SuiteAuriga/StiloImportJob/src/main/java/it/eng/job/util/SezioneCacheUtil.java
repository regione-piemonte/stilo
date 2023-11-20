/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;

public class SezioneCacheUtil {

	final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static final String getValoreSemplice(SezioneCache sezioneCache, String nomeVariabile) {
		for (Variabile var : sezioneCache.getVariabile()) {
			if (var.getNome().equalsIgnoreCase(nomeVariabile)) {
				return var.getValoreSemplice();
			}
		}
		return null;
	}

	public static final void addValoreSemplice(SezioneCache sezioneCache, String nomeVariabile, String valore) {
		final Variabile variabile = new Variabile();
		variabile.setNome(nomeVariabile);
		variabile.setValoreSemplice(valore);
		sezioneCache.getVariabile().add(variabile);
	}

	public static final SezioneCache.Variabile.Lista getLista(SezioneCache sezioneCache, String nomeVariabile) {
		for (Variabile var : sezioneCache.getVariabile()) {
			if (var.getNome().equalsIgnoreCase(nomeVariabile)) {
				return var.getLista();
			}
		}
		return null;
	}

	/**
	 * restituisce la stringa che rappresenta quella sezionecache
	 * 
	 * @param sezioneCache
	 * @return
	 */
	public static final String sezioneCacheToString(SezioneCache sezioneCache) {
		if (sezioneCache == null)
			return null;
		try {
			Marshaller marshaller = SingletonJAXBContext.getInstance().createMarshaller();
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(sezioneCache, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			logger.error(
					String.format("SezioneCacheError.sezioneCacheToString: si è verificata la seguente eccezione %1$s", ExceptionUtils.getFullStackTrace(e)));
			return null;
		}

	}

	/**
	 * restituisce la SezioneCache che rappresenta quella string che deve contenere una SezioneCache
	 * 
	 * @param string
	 * @return
	 */
	public static final SezioneCache stringToSezioneCache(String sezioneCache) {
		if (sezioneCache == null)
			return null;
		try {
			StringReader stringReader = new StringReader(sezioneCache);
			return (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(stringReader);
		} catch (Exception e) {
			logger.error(
					String.format("SezioneCacheError.stringToSezioneCache: si è verificata la seguente eccezione %1$s", ExceptionUtils.getFullStackTrace(e)));
			return null;
		}
	}

	/**
	 * crea una SezioneCache da un file di input
	 * 
	 * @param filePath
	 *            il percorso al file di input
	 * @return
	 */
	public static final SezioneCache fileToSezioneCache(String filePath) {
		if (filePath == null)
			return null;
		File fileSezioneCache = new File(filePath);
		if (!fileSezioneCache.exists())
			return null;
		try (InputStream inputStream = new FileInputStream(fileSezioneCache); Reader reader = new InputStreamReader(inputStream, "UTF-8");) {
			JAXBContext jaxbContext = JAXBContext.newInstance(SezioneCache.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (SezioneCache) jaxbUnmarshaller.unmarshal(reader);
		} catch (Exception e) {
			logger.error(
					String.format("SezioneCacheError.fileToSezioneCache: si è verificata la seguente eccezione %1$s", ExceptionUtils.getFullStackTrace(e)));
		}
		return null;
	}

	/**
	 * restituisce una sezioneCache che è il merge delle 2 sezioniCache in input
	 * 
	 * @param sezioneCacheA
	 * @param sezioneCacheB
	 * @return
	 */
	public static final SezioneCache merge2SezioneCache(SezioneCache sezioneCacheA, SezioneCache sezioneCacheB) {
		if (sezioneCacheA == null)
			return sezioneCacheB;
		if (sezioneCacheB == null)
			return sezioneCacheA;
		for (Variabile tempB : sezioneCacheB.getVariabile()) {
			for (Variabile tempA : sezioneCacheA.getVariabile()) {
				if (tempB.getNome().equalsIgnoreCase(tempA.getNome())) {
					sezioneCacheA.getVariabile().remove(tempA);
					break;
				}
			}
			sezioneCacheA.getVariabile().add(tempB);
		}
		return sezioneCacheA;
	}

	/**
	 * effettua il replace della variabile dentro una SezioneCache ed aggiunge se non presente in base al parametro
	 * 
	 * @param sezioneCache
	 * @param variabile
	 * @param ifNotPresentAdd
	 * @return
	 */
	public static final SezioneCache replaceVariabileIntoSezioneCache(SezioneCache sezioneCache, Variabile variabile, boolean ifNotPresentAdd) {
		if (variabile == null) {
			return sezioneCache;
		}
		boolean isPresent = Boolean.FALSE;
		SezioneCache result = new SezioneCache();
		for (Variabile variabileTemp : sezioneCache.getVariabile()) {
			if (variabileTemp.getNome().equalsIgnoreCase(variabile.getNome())) {
				result.getVariabile().add(variabile);
			} else
				result.getVariabile().add(variabileTemp);
		}
		if (!isPresent && ifNotPresentAdd) {
			result.getVariabile().add(variabile);
		}
		return result;
	}

	/**
	 * effettua il replace della variabile dentro una SezioneCache ed aggiunge se non presente in base al parametro
	 * 
	 * @param sezioneCache
	 * @param variabile
	 * @return
	 */
	public static final Variabile getVariabileFromSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {
		if (nomeVariabile == null || sezioneCache == null) {
			return null;
		}
		for (Variabile variabileTemp : sezioneCache.getVariabile()) {
			if (variabileTemp.getNome().equalsIgnoreCase(nomeVariabile)) {
				return variabileTemp;
			}
		}
		return null;
	}

	/**
	 * restituisce la string che rappresenta la Lista in input
	 * 
	 * @param lista
	 * @return
	 */
	public static final String listaToString(it.eng.jaxb.variabili.Lista lista) {
		if (lista == null) return null;
		try {
			Marshaller marshaller = SingletonJAXBContext.getInstance().createMarshaller();
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(lista, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			logger.error(String.format("SezioneCacheError.listaToString: si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e)));
			return null;
		}
	}
}
