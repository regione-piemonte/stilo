/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

public class SezioneCacheAttributiDinamici {
	
	public static final String  FMT_STD_DATA = "dd/MM/yyyy";
	public static final String  FMT_STD_TIMESTAMP = "dd/MM/yyyy HH:mm";
	public static final String  FMT_STD_TIMESTAMP_WITH_SEC = "dd/MM/yyyy HH:mm:ss";
	public static final String  DATETIME_ATTR_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String  DATE_ATTR_FORMAT = "yyyy-MM-dd";

	private static Logger mLogger = Logger.getLogger(SezioneCacheAttributiDinamici.class);
	private static StorageService storageService;

	public static SezioneCache createSezioneCacheAttributiDinamici(String idProcess, Map<String, Object> values, Map<String, String> types, HttpSession session)
			throws Exception {

		// Inizializzo lo storage
		initStorageService();

		SezioneCache scAttributi = new SezioneCache();
		if (values != null && types != null) {
			for (String attrName : types.keySet()) {
				// se contiene il punto è l'attributo relativo alla colonna di un attributo lista, quindi non lo considero
				if(!attrName.contains(".")) {
					Object attrValue = values.get(attrName);
					String tipo = types.get(attrName);
					Variabile var = new Variabile();
					var.setNome(attrName);
					if (attrValue != null) {
						if ("LISTA".equals(tipo)) {
							Lista listaValori = new Lista();
							for (HashMap<String, Object> map : (List<HashMap<String, Object>>) attrValue) {
								it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga riga = new it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga();
								for (String nro : map.keySet()) {
									it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna col = new it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna();
									col.setNro(BigInteger.valueOf(Integer.parseInt(nro)));
									String tipoCol = types.get(attrName + "." + nro);
									if (map.get(nro) != null) {
										if ("DATE".equals(tipoCol)) {
											col.setContent(new SimpleDateFormat( FMT_STD_DATA).format(new SimpleDateFormat(
													 DATE_ATTR_FORMAT).parse(String.valueOf(map.get(nro)))));
										} else if ("DATETIME".equals(tipoCol)) {
											col.setContent(new SimpleDateFormat( FMT_STD_TIMESTAMP).format(new SimpleDateFormat(
													 DATETIME_ATTR_FORMAT).parse(String.valueOf(map.get(nro)))));
										} else if ("CHECK".equals(tipoCol)) {
											boolean value = map.get(nro) != null ? (Boolean) map.get(nro) : false;
											col.setContent(value ? "1" : "0");
										} else {
											col.setContent(String.valueOf(map.get(nro)));
										}
									} else {
										col.setContent("");
									}
									riga.getColonna().add(col);
								}
								listaValori.getRiga().add(riga);
							}
							var.setLista(listaValori);
						} else if ("DATE".equals(tipo)) {
							var.setValoreSemplice(new SimpleDateFormat( FMT_STD_DATA).format(new SimpleDateFormat(
									 DATE_ATTR_FORMAT).parse(String.valueOf(attrValue))));
						} else if ("DATETIME".equals(tipo)) {
							var.setValoreSemplice(new SimpleDateFormat( FMT_STD_TIMESTAMP).format(new SimpleDateFormat(
									 DATETIME_ATTR_FORMAT).parse(String.valueOf(attrValue))));
						} else if ("CHECK".equals(tipo)) {
							boolean value = (Boolean) attrValue;
							var.setValoreSemplice(value ? "1" : "0");
						} else {
							var.setValoreSemplice(String.valueOf(attrValue));
						}
					} else {
						var.setValoreSemplice("");
					}
					if (!var.getNome().toUpperCase().equals("__GWT_OBJECTID")) {
						scAttributi.getVariabile().add(var);
					}
				}
			}
		}
		return scAttributi;
	}

	public static Variabile createVariabileSemplice(String nome, String valoreSemplice) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setValoreSemplice(valoreSemplice);
		return var;
	}

	public static Variabile createVariabileLista(String nome, Lista lista) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setLista(lista);
		return var;
	}


	private static void initStorageService() {
		if (storageService == null) {
			storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {

				public String getUtilizzatoreStorageId() {
					ModuleConfig mc = (ModuleConfig) SpringAppContext.getContext().getBean("moduleConfig");
					mLogger.debug("Recuperato module config");
					if (mc != null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
						mLogger.debug("Id Storage vale " + mc.getIdDbStorage());
						return mc.getIdDbStorage();
					} else {
						mLogger.error("L'identificativo del DB di storage non è correttamente configurato, "
								+ "controllare il file di configurazione del modulo.");
						return null;
					}
				}
			});
		}
	}

}
