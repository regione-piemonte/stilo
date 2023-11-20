/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindsoggettoinrubricaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DatiSoggXMLIOBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FindSoggettoBean;
import it.eng.client.DmpkUtilityFindsoggettoinrubrica;
import it.eng.document.NumeroColonna;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Riga;
import it.eng.jaxb.variabili.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "FindSoggettoDatasource")
public class FindSoggettoDatasource extends AbstractServiceDataSource<FindSoggettoBean, FindSoggettoBean> {

	private static Logger mLogger = Logger.getLogger(FindSoggettoDatasource.class);
	
	@Override
	public FindSoggettoBean call(FindSoggettoBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		FindSoggettoBean soggettoInfo = new FindSoggettoBean();
		
		try {

			// ***********************************
			// INPUT:
			// ***********************************
			// IdDominioIn
			// FlgSoloVldIn = 1
			// TsRifIn = null
			// FlgCompletaDatiRubrIn = 1
			// DatiSoggXMLIO ( settare solo quelli valorizzati) :
			// - codice rapido
			// - denominazione
			// - nome
			// - codice fiscale
			// - tipologia
			DmpkUtilityFindsoggettoinrubricaBean input = new DmpkUtilityFindsoggettoinrubricaBean();
			input.setIddominioin(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio());
			input.setFlgsolovldin(getExtraparams().get("flgsolovldin") != null ? Integer.parseInt(getExtraparams().get(
					"flgsolovldin")) : 1);
			input.setTsrifin(null);
			input.setFlgcompletadatidarubrin(1);

			/**
			 * IdUserLavoro valorizzato = Utente delegante ( se si lavora in delega ) IdUser valorizzato = Utente loggatgo( se non si lavora in delega )
			 */
			BigDecimal idUser = lAurigaLoginBean.getIdUser() != null ? lAurigaLoginBean.getIdUser() : null;
			input.setIduserlavoroin(StringUtils.isNotBlank(lAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(lAurigaLoginBean.getIdUserLavoro()) : idUser);

			if (StringUtils.isNotBlank(bean.getFlgInOrganigramma())) {
				input.setFlginorganigrammain(bean.getFlgInOrganigramma());				
			}
			
			if (StringUtils.isNotBlank(getExtraparams().get("idUd"))) {			
				input.setIdudin(new BigDecimal(getExtraparams().get("idUd")));
			}
			
			if (StringUtils.isNotBlank(getExtraparams().get("finalita"))) {			
				input.setFinalitain(getExtraparams().get("finalita"));
			}

			DatiSoggXMLIOBean lDatiSoggXMLIOBean = new DatiSoggXMLIOBean();
			if (bean.getCodRapidoSoggetto() != null && !bean.getCodRapidoSoggetto().equalsIgnoreCase("")) {
				lDatiSoggXMLIOBean.setCodRapidoSoggetto(bean.getCodRapidoSoggetto());
			}
			if (bean.getDenominazioneSoggetto() != null && !bean.getDenominazioneSoggetto().equalsIgnoreCase("")) {
				lDatiSoggXMLIOBean.setDenominazioneSoggetto(bean.getDenominazioneSoggetto() + "%");
			}
			if (bean.getCognomeSoggetto() != null && !bean.getCognomeSoggetto().equalsIgnoreCase("")) {
				lDatiSoggXMLIOBean.setCognomeSoggetto(bean.getCognomeSoggetto() + "%");
			}
			if (bean.getNomeSoggetto() != null && !bean.getNomeSoggetto().equalsIgnoreCase("")) {
				lDatiSoggXMLIOBean.setNomeSoggetto(bean.getNomeSoggetto() + "%");
			}
			if (bean.getCodfiscaleSoggetto() != null && !bean.getCodfiscaleSoggetto().equalsIgnoreCase("")) {
				lDatiSoggXMLIOBean.setCodfiscaleSoggetto(bean.getCodfiscaleSoggetto() + "%");
			}
			if (bean.getCodTipoSoggetto() != null && !bean.getCodTipoSoggetto().equalsIgnoreCase("")) {
				lDatiSoggXMLIOBean.setTipologiaSoggetto(getTipologiaFromTipoSoggetto(bean.getCodTipoSoggetto()));
			}
			if (bean.getTipologiaSoggetto() != null && !bean.getTipologiaSoggetto().equalsIgnoreCase("")) {
				lDatiSoggXMLIOBean.setTipologiaSoggetto(bean.getTipologiaSoggetto());
			}
			if (bean.getIdUoSoggetto() != null && !bean.getIdUoSoggetto().equalsIgnoreCase("")) {
				lDatiSoggXMLIOBean.setIdUoSoggetto(bean.getIdUoSoggetto());
			}
			
			if (StringUtils.isBlank(lDatiSoggXMLIOBean.getTipologiaSoggetto())) {
				if (StringUtils.isNotBlank(getExtraparams().get("tipiAmmessi"))) {			
					StringSplitterServer st = new StringSplitterServer(getExtraparams().get("tipiAmmessi"), ",");
					String tipologieSoggetto = null;
					while(st.hasMoreTokens()) {
						String tipo = st.nextToken();
						if(StringUtils.isNotBlank(tipo)) {
							if(tipologieSoggetto == null) {
								tipologieSoggetto = getTipologiaFromTipoSoggetto(tipo);
							} else {
								tipologieSoggetto += "," + getTipologiaFromTipoSoggetto(tipo);
							}
						}
					}
					lDatiSoggXMLIOBean.setTipologiaSoggetto(tipologieSoggetto);
				}
			}
			
			/**
			 * INDIRIZZO
			 */
			if (bean.getCodToponomastica() != null && !"".equals(bean.getCodToponomastica())) {
				lDatiSoggXMLIOBean.setCodToponomastica(bean.getCodToponomastica());
			}
			if (bean.getIndirizzo() != null && !"".equals(bean.getIndirizzo())) {
				lDatiSoggXMLIOBean.setIndirizzo(bean.getIndirizzo());
			}
			if (bean.getCivico() != null && !"".equals(bean.getCivico())) {
				lDatiSoggXMLIOBean.setCivico(bean.getCivico());
			}
			if (bean.getLocalitaFrazione() != null && !"".equals(bean.getLocalitaFrazione())) {
				lDatiSoggXMLIOBean.setLocalitaFrazione(bean.getLocalitaFrazione());
			}
			if (bean.getCap() != null && !"".equals(bean.getCap())) {
				lDatiSoggXMLIOBean.setCap(bean.getCap());
			}			
			if (bean.getComune() != null && !"".equals(bean.getComune())) {
				lDatiSoggXMLIOBean.setCodComune(bean.getComune());
			}
			if (bean.getNomeComune() != null && !"".equals(bean.getNomeComune())) {
				lDatiSoggXMLIOBean.setNomeComune(bean.getNomeComune());
			}
			if (bean.getCodStato() != null && !"".equals(bean.getCodStato())) {
				lDatiSoggXMLIOBean.setCodStato(bean.getCodStato());
			}
			if (bean.getNomeStato() != null && !"".equals(bean.getNomeStato())) {
				lDatiSoggXMLIOBean.setNomeStato(bean.getNomeStato());
			}
			if (bean.getZona() != null && !"".equals(bean.getZona())) {
				lDatiSoggXMLIOBean.setZona(bean.getZona());
			}
			if (bean.getComplementoIndirizzo() != null && !"".equals(bean.getComplementoIndirizzo())) {
				lDatiSoggXMLIOBean.setComplementoIndirizzo(bean.getComplementoIndirizzo());
			}
			if (bean.getTipoToponimo() != null && !"".equals(bean.getTipoToponimo())) {
				lDatiSoggXMLIOBean.setTipoToponimo(bean.getTipoToponimo());
			}
			if (bean.getAppendici() != null && !"".equals(bean.getAppendici())) {
				lDatiSoggXMLIOBean.setAppendici(bean.getAppendici());
			}

			Riga lRigaIn = null;
			try {
				BeanWrapperImpl wrappedDatiSoggXMLIOBean = BeanPropertyUtility.getBeanWrapper(lDatiSoggXMLIOBean);
				lRigaIn = buildRiga(lDatiSoggXMLIOBean, wrappedDatiSoggXMLIOBean);
			} catch (IllegalAccessException e) {
				mLogger.warn(e);
			} catch (InvocationTargetException e) {
				mLogger.warn(e);
			} catch (NoSuchMethodException e) {
				mLogger.warn(e);
			}
			input.setDatisoggxmlio(transformRigaToXml(lRigaIn));

			// ***********************************
			// OUTPUT
			// ***********************************
			
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
			DmpkUtilityFindsoggettoinrubrica lDmpkUtilityFindsoggettoinrubrica = new DmpkUtilityFindsoggettoinrubrica();
			StoreResultBean<DmpkUtilityFindsoggettoinrubricaBean> output = lDmpkUtilityFindsoggettoinrubrica.execute(getLocale(), lSchemaBean, input);
			if (output.isInError()) {
				FindSoggettoBean soggetto = new FindSoggettoBean();
				if (bean.getCodRapidoSoggetto() != null && !bean.getCodRapidoSoggetto().equalsIgnoreCase("")) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				}
				if (StringUtils.isNotBlank(output.getDefaultMessage()) && (output.getDefaultMessage().toLowerCase().indexOf("univocamente") != -1)){
					soggetto.setTrovatiSoggMultipliInRicerca(true); 
				}
				return soggetto;
			}

			Riga lRiga = transformXmlToRiga(output.getResultBean().getDatisoggxmlio());
			soggettoInfo = populateSoggetto(lRiga);
			soggettoInfo.setIdSoggetto(output.getResultBean().getIdsogginrubricaout()); // id soggetto out

		} catch (Exception e) {
			throw e;
		}
		return soggettoInfo;
	}

	private String getTipologiaFromTipoSoggetto(String tipoSoggetto) {
		if (tipoSoggetto != null && !"".equals(tipoSoggetto)) {
			tipoSoggetto = tipoSoggetto.trim();
			if ("PA".equals(tipoSoggetto)) {
				return "#APA";
			} else if ("AOOI".equals(tipoSoggetto)) {
				return "#IAMM";
			} else if ("UOI".equals(tipoSoggetto)) {
				return "UO;UOI";
			} else if ("UP".equals(tipoSoggetto)) {
				return "UP";
			} else if ("PF".equals(tipoSoggetto)) {
				return "#AF";
			} else if ("PG".equals(tipoSoggetto)) {
				return "#AG";
			}
		}
		return null;
	}

	private FindSoggettoBean populateSoggetto(Riga lRiga) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		FindSoggettoBean soggettoInfo = new FindSoggettoBean();
		String nroColonna;
		String valueColonna = "";
		
		for (int i = 0; i < lRiga.getColonna().size(); i++) {
			nroColonna = lRiga.getColonna().get(i).getNro().toString();
			valueColonna = lRiga.getColonna().get(i).getContent();
			if (valueColonna == null)
				valueColonna = "";
			switch (Integer.parseInt(nroColonna)) {
			case 1: // colonna 1 : denominazione/cognome
				soggettoInfo.setDenominazioneSoggetto(valueColonna);
				soggettoInfo.setCognomeSoggetto(valueColonna);
				break;
			case 2: // colonna 2 : nome
				soggettoInfo.setNomeSoggetto(valueColonna);
				break;
			case 3: // colonna 3 : flag persona fisica (0/1)
				soggettoInfo.setFlgPersFisica(valueColonna);
				break;
			case 4: // colonna 4 : codice fiscale
				soggettoInfo.setCodfiscaleSoggetto(valueColonna);
				break;
			case 15:
				soggettoInfo.setCodToponomastica(valueColonna);
				break;
			case 16:
				soggettoInfo.setIndirizzo(valueColonna);
				break;
			case 17:
				soggettoInfo.setCivico(valueColonna);
				break;
			case 18:
				soggettoInfo.setLocalitaFrazione(valueColonna);
				break;
			case 19:
				soggettoInfo.setComune(valueColonna);
				break;
			case 20:
				soggettoInfo.setNomeComune(valueColonna);
				break;
			case 21:
				soggettoInfo.setCodStato(valueColonna);
				break;
			case 22:
				soggettoInfo.setNomeStato(valueColonna);
				break;
			case 23:
				soggettoInfo.setZona(valueColonna);
				break;
			case 24:
				soggettoInfo.setComplementoIndirizzo(valueColonna);
				break;
			case 25:
				soggettoInfo.setTipoToponimo(valueColonna);
				break;
			case 26:
				soggettoInfo.setAppendici(valueColonna);
				break;
			case 27: // colonna 27: codice rapido
				soggettoInfo.setCodRapidoSoggetto(valueColonna);
				break;
			case 28: // colonna 28: codice tipo soggetto
				soggettoInfo.setCodTipoSoggetto(valueColonna);
				break;
			case 31: // colonna 31: id uo della UO che corrisponde al soggetto
				soggettoInfo.setIdUoSoggetto(valueColonna);
				break;
			case 32: // colonna 32: id user dell'utente che corrisponde al soggetto
				soggettoInfo.setIdUserSoggetto(valueColonna);
				break;
			case 33: // colonna 33: id scrivania virtuale che corrisponde al soggetto
				soggettoInfo.setIdScrivaniaSoggetto(valueColonna);
				break;
			case 34: // colonna 34: tipologia soggetto
				soggettoInfo.setTipologiaSoggetto(valueColonna);
				break;
			case 38: // colonna 38: username soggetto
				soggettoInfo.setUsernameSoggetto(valueColonna);
				break;
			case 39: // colonna 39: flag soggetto selezionabile per l'assegnazione
				soggettoInfo.setFlgSelXAssegnazione(valueColonna);
				break;
			case 42: // colonna 42: indirizzo PEC soggetto
				soggettoInfo.setIndirizzoPecSoggetto(valueColonna);
				break;
			case 43: // colonna 43: indirizzo PEO soggetto
				soggettoInfo.setIndirizzoPeoSoggetto(valueColonna);
				break;
			case 44: // colonna 44: indirizzo mail PEC/PEO soggetto
				soggettoInfo.setIndirizzoMailSoggetto(valueColonna);
				break;
			case 45: // colonna 45: CAP
				soggettoInfo.setCap(valueColonna);
				break;
			}
		}
		
		if ("0".equals(soggettoInfo.getFlgPersFisica())) {
			soggettoInfo.setCognomeSoggetto(null);
		} else if ("1".equals(soggettoInfo.getFlgPersFisica())) {
			soggettoInfo.setDenominazioneSoggetto(null);
		}

		return soggettoInfo;
	}

	private Riga transformXmlToRiga(String xmlStringIn) throws JAXBException {
		Riga rigaOut = (Riga) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(xmlStringIn));
		return rigaOut;
	}

	private String transformRigaToXml(Riga lRigaIn) throws JAXBException {
		StringWriter lStringWriter = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lRigaIn, lStringWriter);
		return lStringWriter.toString();
	}

	private Riga buildRiga(DatiSoggXMLIOBean lDatiSoggettoBean, BeanWrapperImpl wrappedDatiSoggettoBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Riga lRiga = new Riga();
		Field[] lFields = lDatiSoggettoBean.getClass().getDeclaredFields();
		for (Field lField : lFields) {
			NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
			if (lNumeroColonna != null) {
				String value = BeanPropertyUtility.getPropertyValueAsString(lDatiSoggettoBean, wrappedDatiSoggettoBean, lField.getName());
				// String value = BeanUtilsBean2.getInstance().getProperty(lDatiSoggettoBean, lField.getName());
				if (value != null && !value.equalsIgnoreCase("")) {
					Colonna lColonna = new Colonna();
					lColonna.setContent(value);
					lColonna.setNro(new BigInteger(lNumeroColonna.numero()));
					lRiga.getColonna().add(lColonna);
				}
			}
		}
		return lRiga;
	}
}
