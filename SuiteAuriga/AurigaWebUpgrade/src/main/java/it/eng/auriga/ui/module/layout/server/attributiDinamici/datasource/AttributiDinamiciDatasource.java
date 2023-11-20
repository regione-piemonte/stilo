/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciGetattrdinxricerca_jBean;
import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciLoadattrdinamiciBean;
import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciLoadattrdinamicolistaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciOutputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoXRicercaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.client.DmpkAttributiDinamiciGetattrdinxricerca_j;
import it.eng.client.DmpkAttributiDinamiciLoadattrdinamici;
import it.eng.client.DmpkAttributiDinamiciLoadattrdinamicolista;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "AttributiDinamiciDatasource")
public class AttributiDinamiciDatasource extends AbstractServiceDataSource<AttributiDinamiciInputBean, AttributiDinamiciOutputBean> {

	private AurigaLoginBean loginBean;
	
	public AttributiDinamiciOutputBean call(AttributiDinamiciInputBean input) throws Exception {
		
		PerformanceLogger lPerformanceLogger = new PerformanceLogger("AttributiDinamiciDatasource call nomeTabella: " + input.getNomeTabella() + " rowid: " + input.getRowId() + " tipoEntita: " + input.getTipoEntita());
		lPerformanceLogger.start();				

		String suffisso = getExtraparams().get("suffisso");
		String categoria = getExtraparams().get("categoria");
		boolean flgNomeAttrConSuff = getExtraparams().get("flgNomeAttrConSuff") != null && getExtraparams().get("flgNomeAttrConSuff").equalsIgnoreCase("true");
		boolean flgDatiStorici = getExtraparams().get("flgDatiStorici") != null && getExtraparams().get("flgDatiStorici").equalsIgnoreCase("true");
		boolean flgDettaglioUdAtto = getExtraparams().get("flgDettaglioUdAtto") != null && getExtraparams().get("flgDettaglioUdAtto").equalsIgnoreCase("true");

		AurigaLoginBean loginBean = getLoginBean();

		DmpkAttributiDinamiciLoadattrdinamiciBean loadattrdinamiciBean = new DmpkAttributiDinamiciLoadattrdinamiciBean();
		loadattrdinamiciBean.setCodidconnectiontokenin(loginBean.getToken());
		loadattrdinamiciBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		loadattrdinamiciBean.setNometabellain(input.getNomeTabella());
		loadattrdinamiciBean.setRowidin(input.getRowId());
		loadattrdinamiciBean.setCitipoentitain(input.getTipoEntita());
		loadattrdinamiciBean.setListatipiregin(null);
		loadattrdinamiciBean.setAttrvaluesxmlin(null);
		loadattrdinamiciBean.setFlgmostratuttiin(new Integer(1));
		loadattrdinamiciBean.setFlgnomeattrconsuffin(new Integer(1));
		// se non setto questo flag a 1 gli attributi mi vengono fuori senza suffisso _Doc o _Ud nel nome
		if (flgNomeAttrConSuff) {
			loadattrdinamiciBean.setFlgnomeattrconsuffin(new Integer(1));
		} else {
			loadattrdinamiciBean.setFlgnomeattrconsuffin(new Integer(0));
		}
		// loadattrdinamiciBean.setAttrnameappin(input.getAttrNameApp());
		// loadattrdinamiciBean.setIdvalorelistain(input.getIdValoreLista());
		loadattrdinamiciBean.setNomeflussowfin(getExtraparams().get("nomeFlussoWF"));
		loadattrdinamiciBean.setProcessnamewfin(getExtraparams().get("processNameWF"));
		loadattrdinamiciBean.setActivitynamewfin(getExtraparams().get("activityNameWF"));
		if (flgDatiStorici) {
			loadattrdinamiciBean.setFlgdatistoricitaskin(new Integer(1));
		}
		if (flgDettaglioUdAtto) {
			loadattrdinamiciBean.setFlgdettudconiterwfin(new Integer(1));
		}

		DmpkAttributiDinamiciLoadattrdinamici loadattrdinamici = new DmpkAttributiDinamiciLoadattrdinamici();
		StoreResultBean<DmpkAttributiDinamiciLoadattrdinamiciBean> result = loadattrdinamici.execute(getLocale(), loginBean, loadattrdinamiciBean);

		if (result.isInError()) {
			throw new StoreException(result);
		}
		AttributiDinamiciOutputBean output = new AttributiDinamiciOutputBean();

		output.setRowId(result.getResultBean().getRowidin());

		if (StringUtils.isNotBlank(result.getResultBean().getAttributiaddout())) {


			StringReader sr = new StringReader(result.getResultBean().getAttributiaddout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			output.setAttributiAdd(recuperaLista(lista, AttributoBean.class));

			HashMap<String, List<DettColonnaAttributoListaBean>> mappaDettAttrLista = new HashMap<String, List<DettColonnaAttributoListaBean>>();
			HashMap<String, List<HashMap<String, String>>> mappaValoriAttrLista = new HashMap<String, List<HashMap<String, String>>>();
			HashMap<String, List<HashMap<String, String>>> mappaVariazioniAttrLista = new HashMap<String, List<HashMap<String, String>>>();
			// HashMap<String, List<String>>> mappaIdValoriLista = new HashMap<String, List<String>>>();
			HashMap<String, DocumentBean> mappaDocumenti = new HashMap<String, DocumentBean>();

			String valoriInErrorCatasti = null;

			if (StringUtils.isNotBlank(categoria)) {
				List<AttributoBean> attributiAddCategoria = null;
				if (output.getAttributiAdd() != null) {
					attributiAddCategoria = new ArrayList<AttributoBean>();
					for (AttributoBean attr : output.getAttributiAdd()) {
						if (attr.getCategoria() != null && attr.getCategoria().equalsIgnoreCase(categoria)) {
							attributiAddCategoria.add(attr);
						}
					}
				}
				output.setAttributiAdd(attributiAddCategoria);
			}
			
			if(input.getListaCategorie() != null) {
				List<AttributoBean> attributiAddListaCategorie = null;
				if (output.getAttributiAdd() != null) {
					attributiAddListaCategorie = new ArrayList<AttributoBean>();
					// considero solo gli attributi che devono essere mostrati nei tab dinamici o nell'header, gli altri li ignoro
					for (AttributoBean attr : output.getAttributiAdd()) {
						if(StringUtils.isBlank(attr.getCategoria()) || attr.getCategoria().startsWith("HEADER_") || input.getListaCategorie().contains(attr.getCategoria())) {
							attributiAddListaCategorie.add(attr);
						}
					}
				}
				output.setAttributiAdd(attributiAddListaCategorie);
			}

			if (StringUtils.isNotBlank(suffisso)) {
				List<AttributoBean> attributiAddWithSuffisso = null;
				if (output.getAttributiAdd() != null) {
					attributiAddWithSuffisso = new ArrayList<AttributoBean>();
					for (AttributoBean attr : output.getAttributiAdd()) {
						// if(attr.getNome().toUpperCase().endsWith("_DOC")) {
						// attr.setNome(attr.getNome().substring(0, attr.getNome().length() - 4));
						// }
						if (attr.getNome().toUpperCase().endsWith(suffisso.toUpperCase())) {
							attributiAddWithSuffisso.add(attr);
						}
					}
				}
				output.setAttributiAdd(attributiAddWithSuffisso);
			}

			for (AttributoBean attr : output.getAttributiAdd()) {
				
				if ("LISTA".equals(attr.getTipo())) {
					
					DmpkAttributiDinamiciLoadattrdinamicolistaBean loadattrdinamicolistaBean = new DmpkAttributiDinamiciLoadattrdinamicolistaBean();
					loadattrdinamicolistaBean.setCodidconnectiontokenin(loginBean.getToken());
					loadattrdinamicolistaBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean
							.getIdUserLavoro()) : null);
					loadattrdinamicolistaBean.setNometabellain(input.getNomeTabella());
					loadattrdinamicolistaBean.setRowidin(input.getRowId());
					loadattrdinamicolistaBean.setNomeattrlistain(attr.getNome());
					// loadattrdinamicolistaBean.setIdvaloreattrappin();
					loadattrdinamicolistaBean.setNomeflussowfin(getExtraparams().get("nomeFlussoWF"));
					loadattrdinamicolistaBean.setProcessnamewfin(getExtraparams().get("processNameWF"));
					loadattrdinamicolistaBean.setActivitynamewfin(getExtraparams().get("activityNameWF"));
					if (flgDatiStorici) {
						loadattrdinamicolistaBean.setFlgdatistoricitaskin(new Integer(1));
					}

					DmpkAttributiDinamiciLoadattrdinamicolista loadattrdinamicolista = new DmpkAttributiDinamiciLoadattrdinamicolista();
					StoreResultBean<DmpkAttributiDinamiciLoadattrdinamicolistaBean> resultLista = loadattrdinamicolista.execute(getLocale(), loginBean,
							loadattrdinamicolistaBean);

					if (StringUtils.isNotBlank(resultLista.getResultBean().getDatidettlistaout())) {

						StringReader srDettLista = new StringReader(resultLista.getResultBean().getDatidettlistaout());
						Lista listaDettLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srDettLista);
						List<DettColonnaAttributoListaBean> datiDettLista = recuperaLista(listaDettLista, DettColonnaAttributoListaBean.class);

						mappaDettAttrLista.put(attr.getNome(), datiDettLista);
					}

					if (StringUtils.isNotBlank(resultLista.getResultBean().getValorilistaout())) {
						StringReader srValoriLista = new StringReader(resultLista.getResultBean().getValorilistaout());
						Lista listaValoriLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srValoriLista);
						Lista listaIdValoriLista = null;
						// if(StringUtils.isNotBlank(resultLista.getResultBean().getIdvalorilistaout())) {
						// StringReader srIdValoriLista = new StringReader(resultLista.getResultBean().getIdvalorilistaout());
						// listaIdValoriLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srIdValoriLista);
						// }
						List<HashMap<String, String>> valoriLista = recuperaListaValori(listaValoriLista, listaIdValoriLista, mappaDettAttrLista.get(attr.getNome()), mappaDocumenti);
						mappaValoriAttrLista.put(attr.getNome(), valoriLista);
					}

					if (StringUtils.isNotBlank(resultLista.getResultBean().getFlgdativariatiout())) {
						StringReader srVariazioniLista = new StringReader(resultLista.getResultBean().getFlgdativariatiout());
						Lista listaVariazioniLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(srVariazioniLista);
						List<HashMap<String, String>> variazioniLista = recuperaListaVariazioni(listaVariazioniLista, mappaDettAttrLista.get(attr.getNome()));
						mappaVariazioniAttrLista.put(attr.getNome(), variazioniLista);
					}

				} else if ("DOCUMENT".equals(attr.getTipo())) {
					DocumentBean document = buildDocumentBean(attr.getValore());
					attr.setValore(document.getIdDoc());
					mappaDocumenti.put(document.getIdDoc(), document);
				} else if ("EURO".equals(attr.getTipo()) || "DECIMAL".equals(attr.getTipo())) {
					String valore = attr.getValore();
					// if(StringUtils.isNotBlank(valore)) {
					// valore = valore.replaceAll("\\.", "");
					// }
					attr.setValore(valore);
				}
			}

			if (StringUtils.isNotBlank(valoriInErrorCatasti)) {
				addMessage("Errore durante il recupero degli attributi " + valoriInErrorCatasti + " dai catasti", "", MessageType.ERROR);
			}

			output.setMappaDettAttrLista(mappaDettAttrLista);
			output.setMappaValoriAttrLista(mappaValoriAttrLista);
			output.setMappaVariazioniAttrLista(mappaVariazioniAttrLista);
			output.setMappaDocumenti(mappaDocumenti);
		}

		lPerformanceLogger.end();				

		return output;
	}

	public AttributiDinamiciOutputBean getAttributiDinamiciXRicerca(AttributiDinamiciInputBean input) throws Exception {

		AurigaLoginBean loginBean = getLoginBean();

		// I tipi che restituisco sono:
		// CHECK = Casella di spunta
		// DATE = Data (con o senza ora)
		// DATETIME = Data e ora
		// NUMBER = Numerico (inclusi importi in Euro)
		// EURO = Importo in Euro
		// TEXT-BOX = Alfanumerico su una riga
		// TEXT-AREA = Alfanumerico su più righe
		// COMBO-BOX = Lista di scelta
		// RADIO = Radio group
		//
		// Per i tipi TEXT-BOX e TEXT-AREA il filtro è di tipo stringa, con 4 operatori se colonna 4 =0, con 5 se colonna 4 =1
		// Per i tipi COMBO-BOX e RADIO il filtro è una select a scelta singola. Per popolare la select dei valori facciamo come fai già nelle maschere
		// dinamiche

		DmpkAttributiDinamiciGetattrdinxricerca_jBean getattrdinxricerca_jBean = new DmpkAttributiDinamiciGetattrdinxricerca_jBean();
		getattrdinxricerca_jBean.setIddominioautin(loginBean.getSpecializzazioneBean().getIdDominio());
		getattrdinxricerca_jBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro())
				: loginBean.getIdUser());
		getattrdinxricerca_jBean.setNometabellain(input.getNomeTabella());
		getattrdinxricerca_jBean.setCitipoentitain(input.getTipoEntita());
		getattrdinxricerca_jBean.setFlgsoloattrmultivalin(null);
		getattrdinxricerca_jBean.setTipoattrin(null);

		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(loginBean.getSchema());

		DmpkAttributiDinamiciGetattrdinxricerca_j getattrdinxricerca_j = new DmpkAttributiDinamiciGetattrdinxricerca_j();
		StoreResultBean<DmpkAttributiDinamiciGetattrdinxricerca_jBean> result = getattrdinxricerca_j.execute(getLocale(), schemaBean, getattrdinxricerca_jBean);

		if (result.isInError()) {
			throw new StoreException(result);
		}
		AttributiDinamiciOutputBean output = new AttributiDinamiciOutputBean();

		if (StringUtils.isNotBlank(result.getResultBean().getAttribtabout())) {
			StringReader sr = new StringReader(result.getResultBean().getAttribtabout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			output.setAttributiXRicerca(recuperaLista(lista, AttributoXRicercaBean.class));
		}

		return output;
	}

	public static DocumentBean buildDocumentBean(String valore) {
		String[] tokens = valore.split("\\|\\*\\|"); // split con separatore |*|
		DocumentBean document = new DocumentBean();
		String idDoc = getToken(0, tokens);
		String idUd = getToken(1, tokens);
		String uriFile = getToken(2, tokens);
		String nomeFile = getToken(3, tokens);
		if (StringUtils.isNotBlank(idDoc) && StringUtils.isNotBlank(idUd) && StringUtils.isNotBlank(uriFile) && StringUtils.isNotBlank(nomeFile)) {
			document.setIdDoc(idDoc);
			document.setIdUd(idUd);
			document.setUriFile(uriFile);
			document.setNomeFile(nomeFile);
			document.setRemoteUri(true);
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean.setImpronta(getToken(4, tokens));
			lMimeTypeFirmaBean.setCorrectFileName(document.getNomeFile());
			lMimeTypeFirmaBean.setFirmato(StringUtils.isNotBlank(getToken(5, tokens)) && "1".equals(getToken(5, tokens)));
			lMimeTypeFirmaBean.setFirmaValida(StringUtils.isNotBlank(getToken(6, tokens)) && "1".equals(getToken(6, tokens)));
			lMimeTypeFirmaBean.setConvertibile(StringUtils.isNotBlank(getToken(7, tokens)) && "1".equals(getToken(7, tokens)));
			lMimeTypeFirmaBean.setDaScansione(false);
			lMimeTypeFirmaBean.setMimetype(getToken(8, tokens));
			lMimeTypeFirmaBean.setBytes(StringUtils.isNotBlank(getToken(9, tokens)) ? new Long(getToken(9, tokens)) : 0);
			if (lMimeTypeFirmaBean.isFirmato()) {
				lMimeTypeFirmaBean
						.setTipoFirma(document.getNomeFile().toUpperCase().endsWith("P7M") || document.getNomeFile().toUpperCase().endsWith("TSD") ? "CAdES_BES"
								: "PDF");
				lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(getToken(10, tokens)) ? getToken(10, tokens).split(",") : null);
			}
			document.setInfoFile(lMimeTypeFirmaBean);
		}
		return document;
	}

	private static String getToken(int i, String[] tokens) {
		if (i >= tokens.length)
			return null;
		return tokens[i];
	}

	private String readFile(String fileName) throws Exception {
		String url = URLDecoder.decode(getClass().getClassLoader().getResource(fileName).getFile(), "UTF-8");
		BufferedReader br = new BufferedReader(new FileReader(url));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	private List<HashMap<String, String>> recuperaListaVariazioni(Lista lLista, List<DettColonnaAttributoListaBean> dettAttrLista) throws Exception {
		List<HashMap<String, String>> lList = new ArrayList<HashMap<String, String>>();
		HashMap<Integer, String> mappa = new HashMap<Integer, String>();
		for (int i = 0; i < dettAttrLista.size(); i++) {
			mappa.put(new Integer(dettAttrLista.get(i).getNumero()), dettAttrLista.get(i).getNome());
		}
		if (lLista != null) {
			List<Riga> righe = lLista.getRiga();
			for (Riga lRiga : righe) {
				HashMap<String, String> lMap = new HashMap<String, String>();
				for (Colonna lColonna : lRiga.getColonna()) {
					int nroColonna = lColonna.getNro().intValue();
					lMap.put(mappa.get(nroColonna), lColonna.getContent());
				}
				lList.add(lMap);
			}
		}
		return lList;
	}

	private List<HashMap<String, String>> recuperaListaValori(Lista lLista, Lista lListaIdValori, List<DettColonnaAttributoListaBean> dettAttrLista, HashMap<String, DocumentBean> mappaDocumenti) throws Exception {
		List<HashMap<String, String>> lList = new ArrayList<HashMap<String, String>>();
		HashMap<Integer, String> mappa = new HashMap<Integer, String>();
		HashMap<Integer, String> mappaTipi = new HashMap<Integer, String>();
		// HashMap<Integer,String> mappaValoriCatasti = new HashMap<Integer, String>();
		for (int i = 0; i < dettAttrLista.size(); i++) {
			mappa.put(new Integer(dettAttrLista.get(i).getNumero()), dettAttrLista.get(i).getNome());
			mappaTipi.put(new Integer(dettAttrLista.get(i).getNumero()), dettAttrLista.get(i).getTipo());
			// if(StringUtils.isNotBlank(dettAttrLista.get(i).getPropertyCatasti()) && caratterizzazioneOst != null) {
			// String valoreCatasti = BeanUtilsBean2.getInstance().getProperty(caratterizzazioneOst, dettAttrLista.get(i).getPropertyCatasti());
			// if(StringUtils.isNotBlank(valoreCatasti)) {
			// mappaValoriCatasti.put(new Integer(dettAttrLista.get(i).getNumero()), valoreCatasti);
			// }
			// }
		}
		List<String> lListIdValori = new ArrayList<String>();
		if (lListaIdValori != null) {
			List<Riga> righe = lLista.getRiga();
			for (Riga lRiga : righe) {
				String idValoreLista = lRiga.getColonna().get(0).getContent();
				lListIdValori.add(idValoreLista);
			}
		}
		if (lLista != null) {
			List<Riga> righe = lLista.getRiga();
			for (Riga lRiga : righe) {
				HashMap<String, String> lMap = new HashMap<String, String>();
				for (Colonna lColonna : lRiga.getColonna()) {
					int nroColonna = lColonna.getNro().intValue();
					String tipoColonna = mappaTipi.get(nroColonna);
					if ("DOCUMENT".equals(tipoColonna)) {
						DocumentBean document = buildDocumentBean(lColonna.getContent());
						lMap.put(mappa.get(nroColonna), document.getIdDoc());
						mappaDocumenti.put(document.getIdDoc(), document);
					} else if ("EURO".equals(tipoColonna) || "DECIMAL".equals(tipoColonna)) {
						String content = lColonna.getContent();
						// if (StringUtils.isNotBlank(content)) {
						// content = content.replaceAll("\\.", "");
						// }
						lMap.put(mappa.get(nroColonna), content);
					}
					// else if(mappaValoriCatasti.get(nroColonna) != null) {
					// lMap.put(mappa.get(nroColonna), mappaValoriCatasti.get(nroColonna));
					// }
					else {
						lMap.put(mappa.get(nroColonna), lColonna.getContent());
					}
				}
				try {
					lMap.put("idValoreLista", lListIdValori.get(lList.size()));
				} catch (Exception e) {
				}
				lList.add(lMap);
			}
		}
		return lList;
	}

	private <T> List<T> recuperaLista(Lista lLista, Class<T> lClass) throws Exception {
		List<T> lList = new ArrayList<T>();
		if (lLista != null) {
			List<Riga> righe = lLista.getRiga();
			Field[] lFieldsLista = retrieveFields(lClass);
			BeanWrapperImpl wrapperObjectLista = BeanPropertyUtility.getBeanWrapper();
			for (Riga lRiga : righe) {
				T lObjectLista = lClass.newInstance();
				wrapperObjectLista = BeanPropertyUtility.updateBeanWrapper(wrapperObjectLista, lObjectLista);
				for (Field lFieldLista : lFieldsLista) {
					NumeroColonna lNumeroColonna = lFieldLista.getAnnotation(NumeroColonna.class);
					if (lNumeroColonna != null) {
						int index = Integer.valueOf(lNumeroColonna.numero());
						for (Colonna lColonna : lRiga.getColonna()) {
							if (lColonna.getNro().intValue() == index) {
								String value = lColonna.getContent();
								setValueOnBean(lObjectLista, lFieldLista, lFieldLista.getName(), value, wrapperObjectLista);
							}
						}
					}
				}
				lList.add(lObjectLista);
			}
		}
		return lList;
	}

	private Field[] retrieveFields(Class lClass) {
		Field[] lFieldsLista = lClass.getDeclaredFields();
		;
		if (lClass.getSuperclass() != null && lClass.getSuperclass() != java.lang.Object.class) {
			Field[] inherited = lClass.getSuperclass().getDeclaredFields();
			Field[] original = lFieldsLista;
			lFieldsLista = new Field[inherited.length + original.length];
			int k = 0;
			for (Field lFieldInherited : inherited) {
				lFieldsLista[k] = lFieldInherited;
				k++;
			}
			for (Field lFieldOriginal : original) {
				lFieldsLista[k] = lFieldOriginal;
				k++;
			}
		}
		return lFieldsLista;
	}

	private void setValueOnBean(Object nested, Field lFieldNest, String propertyNested, String valore, BeanWrapperImpl wrapperNested) throws Exception {
		if (lFieldNest.getType().isEnum()) {
			BeanWrapperImpl wrappedObjectEnum = BeanPropertyUtility.getBeanWrapper();
			for (Object lObjectEnum : lFieldNest.getType().getEnumConstants()) {
				wrappedObjectEnum = BeanPropertyUtility.updateBeanWrapper(wrappedObjectEnum, lObjectEnum);
				String value = BeanPropertyUtility.getPropertyValueAsString(lObjectEnum, wrappedObjectEnum, "dbValue");
				// String value = BeanUtilsBean2.getInstance().getProperty(lObjectEnum, "dbValue");
				if (value == null) {
					if (valore == null) {
						BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lObjectEnum);
						// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
					} else {

					}
				} else if (value.equals(valore)) {
					BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lObjectEnum);
					//  BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
				}
			}
		} else if (Date.class.isAssignableFrom(lFieldNest.getType())) {
			TipoData lTipoData = lFieldNest.getAnnotation(TipoData.class);
			SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
			if (StringUtils.isNotBlank(valore)) {
				Date lDate = lSimpleDateFormat.parse(valore);
				BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lDate);
				// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lDate);
			}
		} else {
			BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, valore);
			// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, valore);
		}
	}

	public AurigaLoginBean getLoginBean() {
		if (loginBean == null && getSession() != null) {
			loginBean = AurigaUserUtil.getLoginInfo(getSession());
		}
		return loginBean;
	}

	public void setLoginBean(AurigaLoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
}
