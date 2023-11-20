/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaDclienteBean;
import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaIuclienteBean;
import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaLoaddettclienteBean;
import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaTrovaclientiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AltraDenominazioneSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaSoggettiBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaSoggettiXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AttributiDinamiciSoggettiXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.ContattoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.ContattoSoggettoXmlBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoXmlBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.LoadAttrDinamicoListaDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaOutputBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.client.DmpkAnagraficaDcliente;
import it.eng.client.DmpkAnagraficaIucliente;
import it.eng.client.DmpkAnagraficaLoaddettcliente;
import it.eng.client.DmpkAnagraficaTrovaclienti;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AnagraficaClientiDataSource")
public class AnagraficaClientiDataSource extends AurigaAbstractFetchDatasource<AnagraficaSoggettiBean> {

	protected String colsToReturn = "1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,26,27,28,29,30,31,32,33,34,35,36,37,38,40,41,43,44,45,46,47,49,50,51,53,54,55,56,57,58,59,60,61,62";

	protected XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();

	@Override
	public String getNomeEntita() {
		return "anagrafiche_clienti";
	}

	@Override
	public AnagraficaSoggettiBean get(AnagraficaSoggettiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkAnagraficaLoaddettclienteBean input = new DmpkAnagraficaLoaddettclienteBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdclienteio(new BigDecimal(bean.getIdCliente()));

		DmpkAnagraficaLoaddettcliente loaddettsoggetto = new DmpkAnagraficaLoaddettcliente();
		StoreResultBean<DmpkAnagraficaLoaddettclienteBean> output = loaddettsoggetto.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		AnagraficaSoggettiBean res = new AnagraficaSoggettiBean();
		res.setIdSoggetto(String.valueOf(output.getResultBean().getIdsoggrubrout()));
		res.setIdCliente(String.valueOf(output.getResultBean().getIdclienteio()));
		res.setTipo(output.getResultBean().getCodtiposottotipoout());
		if (res.getTipo().endsWith(";"))
			res.setTipo(res.getTipo().substring(0, res.getTipo().length() - 1));
		if (res.getTipo() != null && (res.getTipo().startsWith("PA+") || res.getTipo().startsWith("#IAMM") || res.getTipo().startsWith("#AG"))) {
			int pos = output.getResultBean().getCodtiposottotipoout().indexOf(";");
			if (pos != -1) {
				res.setTipo(output.getResultBean().getCodtiposottotipoout().substring(0, pos));
				res.setSottotipo(output.getResultBean().getCodtiposottotipoout().substring(pos + 1));
			}
		}
		res.setCognome(output.getResultBean().getDenomcognomeout());
		res.setNome(output.getResultBean().getNomeout());
		res.setTitolo(output.getResultBean().getTitoloout());
		res.setSesso(output.getResultBean().getFlgsexout());
		res.setCittadinanza(output.getResultBean().getCodistatstatocittout());

		if (res.getTipo() != null && (res.getTipo().equals("UP") || res.getTipo().equals("#AF"))) {
			res.setDenominazione(output.getResultBean().getDenomcognomeout() + " " + output.getResultBean().getNomeout());
		} else {
			res.setDenominazione(output.getResultBean().getDenomcognomeout());
		}

		res.setPartitaIva(output.getResultBean().getPivaout());
		res.setCondizioneGiuridica(output.getResultBean().getCodcondgiuridicaout());
		res.setCodiceIpa(output.getResultBean().getCodindpaout()); // Codice IPA

		res.setCodiceRapido(output.getResultBean().getCodrapidoout());
		res.setCodiceFiscale(output.getResultBean().getCfout());
		res.setDataNascitaIstituzione(StringUtils.isNotBlank(output.getResultBean().getDtnascitaout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output
				.getResultBean().getDtnascitaout()) : null);
		res.setStatoNascitaIstituzione(output.getResultBean().getCodistatstatonascout());
		if (StringUtils.isNotBlank(res.getStatoNascitaIstituzione())) {
			if ("200".equals(res.getStatoNascitaIstituzione())) {
				res.setComuneNascitaIstituzione(output.getResultBean().getCodistatcomunenascout());
				res.setNomeComuneNascitaIstituzione(output.getResultBean().getNomecomunenascout());
				res.setProvNascitaIstituzione(output.getResultBean().getTargaprovnascout());
			} else {
				res.setCittaNascitaIstituzione(output.getResultBean().getNomecomunenascout());
			}
		}
		res.setDataCessazione(StringUtils.isNotBlank(output.getResultBean().getDtcessazioneout()) ? new SimpleDateFormat(FMT_STD_DATA).parse(output
				.getResultBean().getDtcessazioneout()) : null);
		res.setCausaleCessazione(output.getResultBean().getCodcausalecessazioneout());
		res.setFlgDiSistema(output.getResultBean().getFlglockedout() != null ? output.getResultBean().getFlglockedout().intValue() : null);
		res.setFlgValido(output.getResultBean().getFlgattivoout() != null ? output.getResultBean().getFlgattivoout().intValue() : null);

		List<IndirizzoSoggettoBean> listaIndirizzi = new ArrayList<IndirizzoSoggettoBean>();
		if (output.getResultBean().getIndirizziout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getIndirizziout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					IndirizzoSoggettoBean indirizzoBean = new IndirizzoSoggettoBean();
					indirizzoBean.setRowId(v.get(0)); // colonna 1 dell'xml : Identificativo dell'indirizzo/luogo (rowid)
					indirizzoBean.setTipo(v.get(1)); // colonna 2 dell'xml : Codice del tipo di indirizzo/luogo
					indirizzoBean.setDataValidoDal(v.get(6) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(6)) : null); // colonna 7 dell'xml : Data
																																	// di inzio validità 
																																	// dell'indirizzo/luogo (nel
																																	// formato dato dal
																																	// parametro di conf.
																																	// FMT_STD_DATA)
					indirizzoBean.setDataValidoFinoAl(v.get(7) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(7)) : null); // colonna 8 dell'xml :
																																		// Data di fine
																																		// validità 
																																		// dell'indirizzo/luogo
																																		// (nel formato dato dal
																																		// parametro di conf.
																																		// FMT_STD_DATA)
					indirizzoBean.setStato(v.get(18)); // colonna 19 dell'xml : Codice ISTAT dello stato (viene considerato solo se è valorizzata anche la
														// colonna successiva)
					indirizzoBean.setTipoToponimo(v.get(23)); // colonna 24 dell'xml : Tipo toponimo (i.e. via, piazza ecc) dell'indirizzo
					String toponimoIndirizzo = v.get(9); // colonna 10 dell'xml : Indirizzo (senza civico) (alternativo o ridondante rispetto al campo
															// precedente) e senza tipo toponimo se questo è separato in colonna 24 (che quindi è
															// valorizzata)
					if (StringUtils.isBlank(indirizzoBean.getStato()) || "200".equals(indirizzoBean.getStato())) {
						indirizzoBean.setToponimo(toponimoIndirizzo);
					} else {
						indirizzoBean.setIndirizzo(toponimoIndirizzo);
					}
					indirizzoBean.setCivico(v.get(10)); // colonna 11 dell'xml : N° civico (senza eventuali appendici)
					indirizzoBean.setInterno(v.get(11)); // colonna 12 dell'xml : Interno
					if (StringUtils.isBlank(indirizzoBean.getStato()) || "200".equals(indirizzoBean.getStato())) {
						indirizzoBean.setComune(v.get(16)); // colonna 17 dell'xml : Codice ISTAT del comune italiano (viene considerato solo se è
															// valorizzata anche la colonna successiva)
						indirizzoBean.setNomeComune(v.get(17)); // colonna 18 dell'xml : Nome del comune italiano o della città  (se estera)
					} else {
						indirizzoBean.setCitta(v.get(17)); // colonna 18 dell'xml : Nome del comune italiano o della città  (se estera)
					}
					indirizzoBean.setProvincia(v.get(20)); // colonna 21 dell'xml : Targa provincia
					indirizzoBean.setFrazione(v.get(15)); // colonna 16 dell'xml : Frazione
					indirizzoBean.setCap(v.get(14)); // colonna 15 dell'xml : CAP
					indirizzoBean.setZona(v.get(21)); // colonna 22 dell'xml : Zona dell'indirizzo
					indirizzoBean.setComplementoIndirizzo(v.get(22)); // colonna 23 dell'xml : Complemento dell'indirizzo
					indirizzoBean.setAppendici(v.get(24)); // colonna 25 dell'xml : Appendici del civico
					listaIndirizzi.add(indirizzoBean);
				}
			}
		}
		res.setListaIndirizzi(listaIndirizzi);

		List<ContattoSoggettoBean> listaContatti = new ArrayList<ContattoSoggettoBean>();
		if (output.getResultBean().getXmlcontattiout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getXmlcontattiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					ContattoSoggettoBean contattoBean = new ContattoSoggettoBean();
					contattoBean.setRowId(v.get(0)); // colonna 1 dell'xml : Identificativo (ROWID preceduto da Tipo - T/F/E) con cui il contatto è già 
														// stata registrato in banca dati
					contattoBean.setTipo(v.get(1)); // colonna 2 dell'xml : Flag che indica il tipo di contatto. Valori possibili: T=Telefono; F=Fax; E=e-Mail
					String emailTelFax = v.get(2); // colonna 3 dell'xml : N.ro di telefono/fax o indirizzo e-mail
					if ("E".equals(contattoBean.getTipo())) {
						contattoBean.setEmail(emailTelFax);
					} else {
						contattoBean.setTelFax(emailTelFax);
					}
					contattoBean.setTipoTel(v.get(3)); // colonna 4 dell'xml : Tipo di numero di telefono (abitazione, ufficio, cell. ecc.) Valori da apposita
														// dictionary entry
					contattoBean.setFlgPec(v.get(4) != null && "1".equals(v.get(4))); // colonna 5 dell'xml : (valori 1/0) Flag di indirizzo e-mail di Posta
																						// Elettronica Certificata
					contattoBean.setFlgDichIpa(v.get(5) != null && "1".equals(v.get(5))); // colonna 6 dell'xml : (valori 1/0) Flag di indirizzo e-mail
																							// dichiarato all'Indice PA
					contattoBean.setFlgCasellaIstituz(v.get(6) != null && "1".equals(v.get(6))); // colonna 7 dell'xml : (valori 1/0) Flag di indirizzo e-mail
																									// che è Casella Istituzionale di una PA
					listaContatti.add(contattoBean);
				}
			}
		}
		res.setListaContatti(listaContatti);

		List<AltraDenominazioneSoggettoBean> listaAltreDenominazioni = new ArrayList<AltraDenominazioneSoggettoBean>();
		if (output.getResultBean().getXmlaltredenominazioniout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getXmlaltredenominazioniout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					AltraDenominazioneSoggettoBean altraDenominazioneBean = new AltraDenominazioneSoggettoBean();
					altraDenominazioneBean.setRowId(v.get(0)); // colonna 1 dell'xml : Identificativo (ROWID) con cui la denominazione è già  stata
																// registrata in banca dati
					altraDenominazioneBean.setTipo(v.get(1)); // colonna 2 dell'xml : Tipo di denominazione (decodifica) (parallela, correlata, pseudonimo ecc.)
					altraDenominazioneBean.setDenominazione(v.get(2)); // colonna 3 dell'xml : Denominazione
					listaAltreDenominazioni.add(altraDenominazioneBean);
				}
			}
		}
		res.setListaAltreDenominazioni(listaAltreDenominazioni);
		res.setIpa(output.getResultBean().getCodindpaout() != null ? String.valueOf(output.getResultBean().getCodindpaout()) : ""); // Cod.IPA

		// Leggo gli attributi custom
		LoadAttrDinamicoListaOutputBean lAttributiDinamici = new LoadAttrDinamicoListaOutputBean();
		List<DettColonnaAttributoListaBean> ldatiDettLista = new ArrayList<DettColonnaAttributoListaBean>();
		List<AttributoBean> listaAttributi = new ArrayList<AttributoBean>();
		String xmlListaAttributi = output.getResultBean().getAttributiaddout();
		try {
			listaAttributi = XmlListaUtility.recuperaLista(xmlListaAttributi, AttributoBean.class);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		if (listaAttributi != null && listaAttributi.size() > 0) {
			for (AttributoBean lAttributoBean : listaAttributi) {
				LoadAttrDinamicoListaOutputBean lLoadAttrDinamicoListaOutputBean = new LoadAttrDinamicoListaOutputBean();
				// Per ogni attributo chiamo il servizo che mi restituisce la lista dei valori
				lLoadAttrDinamicoListaOutputBean = leggiListaValoriAttributi("DMT_CLIENTI", lAttributoBean.getNome(), output.getResultBean().getRowidout());
				List<HashMap<String, String>> valoriLista = new ArrayList<HashMap<String, String>>();
				valoriLista = lLoadAttrDinamicoListaOutputBean.getValoriLista();
				// Leggo i valori degli attributi
				for (HashMap<String, String> valori : valoriLista) {
					for (String key : valori.keySet()) {

						String valoreAttributo = valori.get(key);
						if (valoreAttributo != null && !valoreAttributo.equalsIgnoreCase("")) {
							DettColonnaAttributoListaBean lDettColonnaAttributoListaBean = new DettColonnaAttributoListaBean();
							lDettColonnaAttributoListaBean.setAltezza(lAttributoBean.getAltezza());
							lDettColonnaAttributoListaBean.setAttributiFiltriLookup(lAttributoBean.getAttributiFiltriLookup());
							lDettColonnaAttributoListaBean.setAttributiIndiciDaLookup(lAttributoBean.getAttributiIndiciDaLookup());
							lDettColonnaAttributoListaBean.setCodListaLookup(lAttributoBean.getCodListaLookup());
							lDettColonnaAttributoListaBean.setLabel(lAttributoBean.getLabel());
							lDettColonnaAttributoListaBean.setLarghezza(lAttributoBean.getLarghezza());
							lDettColonnaAttributoListaBean.setModificabile(lAttributoBean.getModificabile());
							lDettColonnaAttributoListaBean.setNumCifreDecimali(lAttributoBean.getNumCifreDecimali());
							lDettColonnaAttributoListaBean.setNumero(lAttributoBean.getNumero());
							lDettColonnaAttributoListaBean.setNumMaxCaratteri(lAttributoBean.getNumMaxCaratteri());
							lDettColonnaAttributoListaBean.setObbligatorio(lAttributoBean.getObbligatorio());
							lDettColonnaAttributoListaBean.setTipo(lAttributoBean.getTipo());
							lDettColonnaAttributoListaBean.setNome(lAttributoBean.getNome());
							lDettColonnaAttributoListaBean.setValoreDefault(valoreAttributo);

							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("BENEFICIARIO_CLIENTE")) {
								res.setBeneficiario(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("ISTITUTO_FINANZ_CLIENTE")) {
								res.setIstitutoFinanziario(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("IBAN_CLIENTE")) {
								res.setIban(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("ABI_CLIENTE")) {
								res.setAbi(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("CAB_CLIENTE")) {
								res.setCab(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("BA_CLIENTE")) {
								res.setBillingAccount(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("CID_CLIENTE")) {
								res.setCid(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("ODA_NR_CONTRATTO_CLIENTE")) {
								res.setOdaNrContratto(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("ODA_CIG_CLIENTE")) {
								res.setOdaCig(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("ODA_CUP_CLIENTE")) {
								res.setOdaCup(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("ODA_RIF_AMM_INPS_CLIENTE")) {
								res.setOdaRifAmmInps(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("FLG_TIPO_ID_FISCALE_CLIENTE")) {
								res.setFlgTipoIdFiscale(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("POS_FIN_CLIENTE")) {
								res.setPosizioneFinanziaria(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("ANN_FIN_CLIENTE")) {
								res.setAnnoPosizioneFinanziaria(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("ESIGIBILITA_IVA_CLIENTE")) {
								res.setEsigibilitaIva(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("FLG_SEGNO_IMPORTI_CLIENTE")) {
								res.setFlgSegnoImporti(lAttributoBean.getValore() != null ? new Boolean(lAttributoBean.getValore().equalsIgnoreCase("1"))
										: false);
							}
							
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("CIG_CLIENTE")) {
								res.setOdaCig(lAttributoBean.getValore());
							}

							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("CUP_CLIENTE")) {
								res.setOdaCup(lAttributoBean.getValore());
							}
							if (lAttributoBean.getNome() != null && lAttributoBean.getNome().equalsIgnoreCase("PEC")) {
								res.setEmailPec(lAttributoBean.getValore());
							}
							
							
							ldatiDettLista.add(lDettColonnaAttributoListaBean);
						}
					}
				}
			}
		}
		lAttributiDinamici.setDatiDettLista(ldatiDettLista);
		res.setAttributiDinamici(lAttributiDinamici);
		res.setFlgIgnoreWarning(new Integer(0));
		res.setFlgInOrganigramma(bean.getFlgInOrganigramma());
		return res;
	}

	@Override
	public PaginatorBean<AnagraficaSoggettiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		boolean overflow = false;

		PaginatorBean<AnagraficaSoggettiBean> lPaginatorBean = new PaginatorBean<AnagraficaSoggettiBean>();
		List<AnagraficaSoggettiBean> data = new ArrayList<AnagraficaSoggettiBean>();

		Map<String, Object> advancedCriteriaData = extractAdvancedCriteriaData(criteria);

		List<CriteriPersonalizzati> listCustomFilters = createCriteriPersonalizzati(criteria);

		String filtroFullText = (String) advancedCriteriaData.get("advancedCriteriaData");

		String[] checkAttributes = (String[]) advancedCriteriaData.get("checkAttributes");

		// Controllo se e' stato inserito almeno un filtro full-text
		if (StringUtils.isNotBlank(filtroFullText) && (checkAttributes == null || checkAttributes.length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);
			lPaginatorBean.setData(data);
			lPaginatorBean.setStartRow(startRow);
			lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
			lPaginatorBean.setTotalRows(data.size());
			return lPaginatorBean;
		}

		DmpkAnagraficaTrovaclientiBean input = createDmpkAnagraficaTrovaclientiBean(advancedCriteriaData, loginBean, listCustomFilters);

		// Inizializzo l'OUTPUT
		DmpkAnagraficaTrovaclienti lDmpkAnagraficaTrovaclienti = new DmpkAnagraficaTrovaclienti();
		StoreResultBean<DmpkAnagraficaTrovaclientiBean> output = lDmpkAnagraficaTrovaclienti.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				GenericConfigBean config = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");

				Boolean showOverflow = config.getShowAlertMaxRecord();

				if (showOverflow != null && showOverflow)
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				// else
				overflow = true;

				// addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if (output.getResultBean().getNrototrecout() != null) {
			data = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), AnagraficaSoggettiBean.class);
		}

		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

	private DmpkAnagraficaTrovaclientiBean createDmpkAnagraficaTrovaclientiBean(Map<String, Object> advancedCriteriaData, AurigaLoginBean loginBean,
			List<CriteriPersonalizzati> listCustomFilters) throws Exception {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1); // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;

		DmpkAnagraficaTrovaclientiBean input = new DmpkAnagraficaTrovaclientiBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		String idUserLavoro = loginBean.getIdUserLavoro();
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);
		input.setFlgsenzapaginazionein(flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);

		input.setPivaio((String) advancedCriteriaData.get("partitaIva"));
		input.setCfio((String) advancedCriteriaData.get("codiceFiscale"));
		input.setCodaooipaio((String) advancedCriteriaData.get("codiceIpa"));
		input.setCodrapidoio((String) advancedCriteriaData.get("codiceRapido"));

		// Setto i filtri all'input del servizio
		String tipoSoggetto = StringUtils.isNotBlank(getExtraparams().get("tipoSoggetto")) ? getExtraparams().get("tipoSoggetto") : "";

		String listaCodTipiSottoTipi = (String) advancedCriteriaData.get("listaCodTipiSottoTipi");
		if (!tipoSoggetto.equalsIgnoreCase("")) {
			listaCodTipiSottoTipi = tipoSoggetto;
		}
		input.setListacodtipisottotipiio(listaCodTipiSottoTipi);

		String iniziale = (String) advancedCriteriaData.get("iniziale");
		input.setDenominazioneio(iniziale != null ? iniziale + "%" : null);
		input.setStrindenominazionein((String) advancedCriteriaData.get("strInDenominazione"));
		input.setFlgsolovldio((Integer) advancedCriteriaData.get("flgSoloVld"));
		input.setColtoreturnin(colsToReturn);
		input.setFinalitain((String) advancedCriteriaData.get("finalita"));
		input.setFlginclannullatiio((Integer) advancedCriteriaData.get("flgIncludiAnnullati"));
		input.setCriteripersonalizzatiio(lXmlUtilitySerializer.bindXmlList(listCustomFilters));

		return input;
	}

	private List<CriteriPersonalizzati> createCriteriPersonalizzati(AdvancedCriteria criteria) {

		List<CriteriPersonalizzati> retValue = new ArrayList<CriteriPersonalizzati>();

		if (criteria != null && criteria.getCriteria() != null) {

			for (Criterion crit : criteria.getCriteria()) {

				if ("beneficiario".equals(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("BENEFICIARIO_CLIENTE");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				} else if ("istitutoFinanziario".equals(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("ISTITUTO_FINANZ_CLIENTE");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				} else if ("iban".equals(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("IBAN_CLIENTE");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				} else if ("abi".equals(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("ABI_CLIENTE");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				} else if ("cab".equals(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("CAB_CLIENTE");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				} else if ("billingAccount".equals(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("BA_CLIENTE");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				} else if ("cid".equals(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("CID_CLIENTE");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				} else if ("societa".equals(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("CID_APPL_SOCIETA");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				} else if ("gruppoDiRiferimento".equalsIgnoreCase(crit.getFieldName())) {
					CriteriPersonalizzati criteriPersonalizzati = new CriteriPersonalizzati();
					criteriPersonalizzati.setAttrName("COD_GRUPPO_RIF");
					criteriPersonalizzati.setOperator(getDecodeCritOperator(crit.getOperator()));
					criteriPersonalizzati.setValue1(getTextFilterValue(crit));
					retValue.add(criteriPersonalizzati);
				}
			}
		}

		return retValue;
	}

	private Map<String, Object> extractAdvancedCriteriaData(AdvancedCriteria criteria) {

		Map<String, Object> retValue = new HashMap<String, Object>();

		if (criteria != null && criteria.getCriteria() != null) {

			for (int i = criteria.getCriteria().size() - 1; i > -1; i--) {

				Criterion crit = criteria.getCriteria().get(i);

				if ("iniziale".equals(crit.getFieldName())) {

					if (StringUtils.isNotBlank((String) crit.getValue())) {
						retValue.put("iniziale", String.valueOf(crit.getValue()));
					}
					criteria.getCriteria().remove(i);
				} else if ("finalita".equals(crit.getFieldName())) {

					if (StringUtils.isNotBlank((String) crit.getValue())) {
						retValue.put("finalita", String.valueOf(crit.getValue()));
					}
					criteria.getCriteria().remove(i);
				} else if ("searchFulltext".equals(crit.getFieldName())) {

					if (crit.getValue() != null) {

						Map map = (Map) crit.getValue();

						retValue.put("filtroFullText", (String) map.get("parole"));

						ArrayList<String> lArrayList = (ArrayList<String>) map.get("attributi");
						retValue.put("checkAttributes", lArrayList != null ? lArrayList.toArray(new String[] {}) : null);

						Integer searchAllTerms = null;
						String operator = crit.getOperator();
						if (StringUtils.isNotBlank(operator)) {
							if ("allTheWords".equals(operator)) {
								searchAllTerms = Integer.valueOf("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = Integer.valueOf("0");
							}
						}
						retValue.put("searchAllTerms", searchAllTerms);
					}
					criteria.getCriteria().remove(i);
				} else if ("tipologia".equals(crit.getFieldName())) {

					String value = getTextFilterValue(crit);

					if (value != null) {
						retValue.put("listaCodTipiSottoTipi", value);
					}
					criteria.getCriteria().remove(i);
				} else if ("flgSoloVld".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						retValue.put("flgSoloVld", Boolean.valueOf(String.valueOf(crit.getValue())) ? 1 : 0);
					}
					criteria.getCriteria().remove(i);
				} else if ("flgIncludiAnnullati".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						retValue.put("flgIncludiAnnullati", Boolean.valueOf(String.valueOf(crit.getValue())) ? 1 : 0);
					}
					criteria.getCriteria().remove(i);
				} else if ("strInDenominazione".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						if (StringUtils.isNotBlank((String) crit.getValue())) {
							retValue.put("strInDenominazione", String.valueOf(crit.getValue()));
						}
					}
					criteria.getCriteria().remove(i);
				} else if ("partitaIva".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						if (StringUtils.isNotBlank((String) crit.getValue())) {
							retValue.put("partitaIva", String.valueOf(getTextFilterValue(crit)));
						}
					}
					criteria.getCriteria().remove(i);
				} else if ("codiceFiscale".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						if (StringUtils.isNotBlank((String) crit.getValue())) {
							retValue.put("codiceFiscale", String.valueOf(getTextFilterValue(crit)));
						}
					}
					criteria.getCriteria().remove(i);
				} else if ("codiceIpa".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						if (StringUtils.isNotBlank((String) crit.getValue())) {
							retValue.put("codiceIpa", String.valueOf(getTextFilterValue(crit)));
						}
					}
					criteria.getCriteria().remove(i);
				} else if ("codiceRapido".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						if (StringUtils.isNotBlank((String) crit.getValue())) {
							retValue.put("codiceRapido", String.valueOf(getTextFilterValue(crit)));
						}
					}
					criteria.getCriteria().remove(i);
				}
			}
		}

		return retValue;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {

		AdvancedCriteria criteria = bean.getCriteria();

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String idUserLavoro = loginBean.getIdUserLavoro();

		Map<String, Object> advancedCriteriaData = extractAdvancedCriteriaData(criteria);

		List<CriteriPersonalizzati> listCustomFilters = createCriteriPersonalizzati(criteria);

		DmpkAnagraficaTrovaclientiBean input = createDmpkAnagraficaTrovaclientiBean(advancedCriteriaData, loginBean, listCustomFilters);
		input.setOverflowlimitin(-2);

		// Inizializzo l'OUTPUT
		DmpkAnagraficaTrovaclienti lDmpkAnagraficaTrovaclienti = new DmpkAnagraficaTrovaclienti();
		StoreResultBean<DmpkAnagraficaTrovaclientiBean> output = lDmpkAnagraficaTrovaclienti.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			}
		}

		// imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, idUserLavoro, AnagraficaSoggettiXmlBean.class.getName());

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return bean;
	}

	@Override
	public AnagraficaSoggettiBean add(AnagraficaSoggettiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkAnagraficaIuclienteBean input = new DmpkAnagraficaIuclienteBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setFlgmodindirizziin("C");
		input.setFlgmodcontattiin("C");
		input.setFlgmodaltredenomin("C");
		if (StringUtils.isNotBlank(bean.getSottotipo()) && bean.getTipo() != null
				&& (bean.getTipo().equals("PA+") || bean.getTipo().equals("#IAMM") || bean.getTipo().equals("#AG"))) {
			input.setCodtiposottotipoin(bean.getTipo() + ";" + bean.getSottotipo());
		} else {
			input.setCodtiposottotipoin(bean.getTipo());
		}
		if (bean.getTipo() != null && (bean.getTipo().equals("UP") || bean.getTipo().equals("#AF"))) {
			bean.setDenominazione(bean.getCognome() + " " + bean.getNome());
			input.setFlgpersonafisicain(new BigDecimal(1));
			input.setDenomcognomein(bean.getCognome());
			input.setNomein(bean.getNome());
			input.setTitoloin(bean.getTitolo());
			input.setFlgsexin(bean.getSesso());
			input.setCodistatstatocittin(bean.getCittadinanza());
		} else {
			input.setDenomcognomein(bean.getDenominazione());
			input.setPivain(bean.getPartitaIva());
			input.setCodcondgiuridicain(bean.getCondizioneGiuridica());
		}

		input.setCodrapidoin(bean.getCodiceRapido());
		input.setCfin(bean.getCodiceFiscale());
		input.setDtnascitain(bean.getDataNascitaIstituzione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataNascitaIstituzione()) : null);
		if (StringUtils.isNotBlank(bean.getStatoNascitaIstituzione())) {
			input.setCodistatstatonascin(bean.getStatoNascitaIstituzione());
			if ("200".equals(bean.getStatoNascitaIstituzione())) {
				input.setCodistatcomunenascin(bean.getComuneNascitaIstituzione());
			} else {
				input.setNomecomunenascin(bean.getCittaNascitaIstituzione());
			}
		}
		input.setDtcessazionein(bean.getDataCessazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataCessazione()) : null);
		input.setCodcausalecessazionein(bean.getCausaleCessazione());

		String xmlIndirizzi = null;
		if (bean.getListaIndirizzi() != null && bean.getListaIndirizzi().size() > 0) {
			xmlIndirizzi = getXmlIndirizzi(bean);
		}
		input.setXmlindirizziin(xmlIndirizzi);

		String xmlContatti = null;
		if (bean.getListaContatti() != null && bean.getListaContatti().size() > 0) {
			xmlContatti = getXmlContatti(bean);
		}
		input.setXmlcontattiin(xmlContatti);

		String xmlAltreDenominazioni = null;
		if (bean.getListaAltreDenominazioni() != null && bean.getListaAltreDenominazioni().size() > 0) {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			xmlAltreDenominazioni = lXmlUtilitySerializer.bindXmlList(bean.getListaAltreDenominazioni());
		}
		input.setXmlaltredenominazioniin(xmlAltreDenominazioni);
		input.setCodindpain(bean.getCodiceIpa()); // Codice IPA

		// Attributi dinamici
		// BENEFICIARIO_CLIENTE
		// ISTITUTO_FINANZ_CLIENTE
		// IBAN_CLIENTE
		// ABI_CLIENTE
		// CAB_CLIENTE
		// ESIGIBILITA_IVA_CLIENTE
		// CIG_CLIENTE
		// CUP_CLIENTE
		// PEC
		String xmlAttributiDinamici = null;
		xmlAttributiDinamici = setXmlAttributiDinamici(bean);
		input.setAttributiaddin(xmlAttributiDinamici);

		DmpkAnagraficaIucliente iusoggetto = new DmpkAnagraficaIucliente();
		StoreResultBean<DmpkAnagraficaIuclienteBean> output = iusoggetto.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
			bean.setFlgIgnoreWarning(new Integer(1));
		} else {
			bean.setIdSoggetto(String.valueOf(output.getResultBean().getIdsoggrubrio()));
			bean.setIdCliente(String.valueOf(output.getResultBean().getIdclienteio()));
			bean.setFlgIgnoreWarning(new Integer(0));
		}
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	@Override
	public AnagraficaSoggettiBean update(AnagraficaSoggettiBean bean, AnagraficaSoggettiBean oldvalue) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkAnagraficaIuclienteBean input = new DmpkAnagraficaIuclienteBean();
		input.setIdsoggrubrio(new BigDecimal(bean.getIdSoggetto()));
		input.setIdclienteio(new BigDecimal(bean.getIdCliente()));
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(bean.getFlgIgnoreWarning());
		input.setFlgmodindirizziin("C");
		input.setFlgmodcontattiin("C");
		input.setFlgmodaltredenomin("C");
		if (StringUtils.isNotBlank(bean.getSottotipo()) && bean.getTipo() != null
				&& (bean.getTipo().equals("PA+") || bean.getTipo().equals("#IAMM") || bean.getTipo().equals("#AG"))) {
			input.setCodtiposottotipoin(bean.getTipo() + ";" + bean.getSottotipo());
		} else {
			input.setCodtiposottotipoin(bean.getTipo());
		}
		if (bean.getTipo() != null && (bean.getTipo().equals("UP") || bean.getTipo().equals("#AF"))) {
			bean.setDenominazione(bean.getCognome() + " " + bean.getNome());
			input.setFlgpersonafisicain(new BigDecimal(1));
			input.setDenomcognomein(bean.getCognome());
			input.setNomein(bean.getNome());
			input.setTitoloin(bean.getTitolo());
			input.setFlgsexin(bean.getSesso());
			input.setCodistatstatocittin(bean.getCittadinanza());
		} else {
			input.setDenomcognomein(bean.getDenominazione());
			input.setPivain(bean.getPartitaIva());
			input.setCodcondgiuridicain(bean.getCondizioneGiuridica());
		}

		input.setCodrapidoin(bean.getCodiceRapido());
		input.setCfin(bean.getCodiceFiscale());
		input.setDtnascitain(bean.getDataNascitaIstituzione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataNascitaIstituzione()) : null);
		if (StringUtils.isNotBlank(bean.getStatoNascitaIstituzione())) {
			input.setCodistatstatonascin(bean.getStatoNascitaIstituzione());
			if ("200".equals(bean.getStatoNascitaIstituzione())) {
				input.setCodistatcomunenascin(bean.getComuneNascitaIstituzione());
			} else {
				input.setNomecomunenascin(bean.getCittaNascitaIstituzione());
			}
		}
		input.setDtcessazionein(bean.getDataCessazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataCessazione()) : null);
		input.setCodcausalecessazionein(bean.getCausaleCessazione());

		String xmlIndirizzi = null;
		if (bean.getListaIndirizzi() != null && bean.getListaIndirizzi().size() > 0) {
			xmlIndirizzi = getXmlIndirizzi(bean);
		}
		input.setXmlindirizziin(xmlIndirizzi);

		String xmlContatti = null;
		if (bean.getListaContatti() != null && bean.getListaContatti().size() > 0) {
			xmlContatti = getXmlContatti(bean);
		}
		input.setXmlcontattiin(xmlContatti);

		String xmlAltreDenominazioni = null;
		if (bean.getListaAltreDenominazioni() != null && bean.getListaAltreDenominazioni().size() > 0) {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			xmlAltreDenominazioni = lXmlUtilitySerializer.bindXmlList(bean.getListaAltreDenominazioni());
		}
		input.setXmlaltredenominazioniin(xmlAltreDenominazioni);
		input.setCodindpain(bean.getCodiceIpa()); // Codice IPA

		// Attributi dinamici
		// BENEFICIARIO_CLIENTE
		// ISTITUTO_FINANZ_CLIENTE
		// IBAN_CLIENTE
		// ABI_CLIENTE
		// CAB_CLIENTE
		// ESIGIBILITA_IVA_CLIENTE
		// CIG_CLIENTE
		// CUP_CLIENTE
		// PEC
		String xmlAttributiDinamici = null;
		xmlAttributiDinamici = setXmlAttributiDinamici(bean);
		input.setAttributiaddin(xmlAttributiDinamici);

		DmpkAnagraficaIucliente iusoggetto = new DmpkAnagraficaIucliente();
		StoreResultBean<DmpkAnagraficaIuclienteBean> output = iusoggetto.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
			bean.setFlgIgnoreWarning(new Integer(1));
		} else {
			bean.setFlgIgnoreWarning(new Integer(0));
		}

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	@Override
	public AnagraficaSoggettiBean remove(AnagraficaSoggettiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkAnagraficaDclienteBean input = new DmpkAnagraficaDclienteBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdclientein(new BigDecimal(bean.getIdCliente()));
		input.setMotiviin(null);
		input.setFlgcancfisicain(null);
		input.setFlgignorewarningin(new Integer(1));
		DmpkAnagraficaDcliente dsoggetto = new DmpkAnagraficaDcliente();
		StoreResultBean<DmpkAnagraficaDclienteBean> output = dsoggetto.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	protected String getXmlContatti(AnagraficaSoggettiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String xmlContatti;
		List<ContattoSoggettoXmlBean> lList = new ArrayList<ContattoSoggettoXmlBean>();
		for (ContattoSoggettoBean lContattoSoggettoBean : bean.getListaContatti()) {
			ContattoSoggettoXmlBean lContattoSoggettoXmlBean = new ContattoSoggettoXmlBean();
			lContattoSoggettoXmlBean.setRowId(lContattoSoggettoBean.getRowId());
			lContattoSoggettoXmlBean.setTipo(lContattoSoggettoBean.getTipo());
			if ("E".equals(lContattoSoggettoBean.getTipo())) {
				lContattoSoggettoXmlBean.setEmailTelFax(lContattoSoggettoBean.getEmail());
				lContattoSoggettoXmlBean.setFlgPec(lContattoSoggettoBean.getFlgPec() != null && lContattoSoggettoBean.getFlgPec() ? "1" : "0");
				lContattoSoggettoXmlBean.setFlgDichIpa(lContattoSoggettoBean.getFlgDichIpa() != null && lContattoSoggettoBean.getFlgDichIpa() ? "1" : "0");
				lContattoSoggettoXmlBean.setFlgCasellaIstituz(lContattoSoggettoBean.getFlgCasellaIstituz() != null
						&& lContattoSoggettoBean.getFlgCasellaIstituz() ? "1" : "0");
			} else {
				lContattoSoggettoXmlBean.setEmailTelFax(lContattoSoggettoBean.getTelFax());
				lContattoSoggettoXmlBean.setTipoTel(lContattoSoggettoBean.getTipoTel());
			}
			lList.add(lContattoSoggettoXmlBean);
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlContatti = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlContatti;
	}

	protected String getXmlIndirizzi(AnagraficaSoggettiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String xmlIndirizzi;
		List<IndirizzoSoggettoXmlBean> lListIndirizzi = new ArrayList<IndirizzoSoggettoXmlBean>();
		for (IndirizzoSoggettoBean lIndirizzoSoggettoBean : bean.getListaIndirizzi()) {
			IndirizzoSoggettoXmlBean lIndirizzoSoggettoXmlBean = new IndirizzoSoggettoXmlBean();
			lIndirizzoSoggettoXmlBean.setRowId(lIndirizzoSoggettoBean.getRowId());
			lIndirizzoSoggettoXmlBean.setTipo(lIndirizzoSoggettoBean.getTipo());
			lIndirizzoSoggettoXmlBean.setDataValidoDal(lIndirizzoSoggettoBean.getDataValidoDal());
			lIndirizzoSoggettoXmlBean.setDataValidoFinoAl(lIndirizzoSoggettoBean.getDataValidoFinoAl());
			lIndirizzoSoggettoXmlBean.setStato(lIndirizzoSoggettoBean.getStato());
			if (StringUtils.isBlank(lIndirizzoSoggettoBean.getStato()) || "200".equals(lIndirizzoSoggettoBean.getStato())) {
				lIndirizzoSoggettoXmlBean.setTipoToponimo(lIndirizzoSoggettoBean.getTipoToponimo());
				lIndirizzoSoggettoXmlBean.setToponimoIndirizzo(lIndirizzoSoggettoBean.getToponimo());
				lIndirizzoSoggettoXmlBean.setComune(lIndirizzoSoggettoBean.getComune());
				lIndirizzoSoggettoXmlBean.setProvincia(lIndirizzoSoggettoBean.getProvincia());
				lIndirizzoSoggettoXmlBean.setCap(lIndirizzoSoggettoBean.getCap());
			} else {
				lIndirizzoSoggettoXmlBean.setToponimoIndirizzo(lIndirizzoSoggettoBean.getIndirizzo());
				lIndirizzoSoggettoXmlBean.setNomeComuneCitta(lIndirizzoSoggettoBean.getCitta());
			}
			lIndirizzoSoggettoXmlBean.setCivico(lIndirizzoSoggettoBean.getCivico());
			lIndirizzoSoggettoXmlBean.setInterno(lIndirizzoSoggettoBean.getInterno());
			lIndirizzoSoggettoXmlBean.setFrazione(lIndirizzoSoggettoBean.getFrazione());
			lIndirizzoSoggettoXmlBean.setZona(lIndirizzoSoggettoBean.getZona());
			lIndirizzoSoggettoXmlBean.setComplementoIndirizzo(lIndirizzoSoggettoBean.getComplementoIndirizzo());
			lIndirizzoSoggettoXmlBean.setAppendici(lIndirizzoSoggettoBean.getAppendici());
			lListIndirizzi.add(lIndirizzoSoggettoXmlBean);
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlIndirizzi = lXmlUtilitySerializer.bindXmlList(lListIndirizzi);
		return xmlIndirizzi;
	}

	private LoadAttrDinamicoListaOutputBean leggiListaValoriAttributi(String nomeTabella, String nomeAttrLista, String rowId) throws Exception {
		LoadAttrDinamicoListaOutputBean result = new LoadAttrDinamicoListaOutputBean();
		if (nomeTabella != null && !nomeTabella.equalsIgnoreCase("") && nomeAttrLista != null && !nomeAttrLista.equalsIgnoreCase("") && rowId != null
				&& !rowId.equalsIgnoreCase("")) {
			// Leggo la lista degli attributi
			LoadAttrDinamicoListaInputBean inputDataSourceBean = new LoadAttrDinamicoListaInputBean();
			inputDataSourceBean.setNomeAttrLista(nomeAttrLista);
			inputDataSourceBean.setNomeTabella(nomeTabella);
			inputDataSourceBean.setRowId(rowId);
			LoadAttrDinamicoListaDatasource lDataSource = new LoadAttrDinamicoListaDatasource();
			try {
				lDataSource.setSession(getSession());
				result = lDataSource.call(inputDataSourceBean);
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
		return result;
	}

	protected String setXmlAttributiDinamici(AnagraficaSoggettiBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		AttributiDinamiciSoggettiXmlBean lAttributiDinamiciSoggettiXmlBean = new AttributiDinamiciSoggettiXmlBean();
		// Salvo gli attributi dinamici :
		// BENEFICIARIO_CLIENTE
		// ISTITUTO_FINANZ_CLIENTE
		// IBAN_CLIENTE
		// ABI_CLIENTE
		// CAB_CLIENTE
		// ESIGIBILITA_IVA_CLIENTE
		// CIG_CLIENTE
		// CUP_CLIENTE
		// PEC
		lAttributiDinamiciSoggettiXmlBean.setBENEFICIARIO_CLIENTE(bean.getBeneficiario());
		lAttributiDinamiciSoggettiXmlBean.setISTITUTO_FINANZ_CLIENTE(bean.getIstitutoFinanziario());
		lAttributiDinamiciSoggettiXmlBean.setIBAN_CLIENTE(bean.getIban());
		lAttributiDinamiciSoggettiXmlBean.setABI_CLIENTE(bean.getAbi());
		lAttributiDinamiciSoggettiXmlBean.setCAB_CLIENTE(bean.getCab());
		lAttributiDinamiciSoggettiXmlBean.setESIGIBILITA_IVA_CLIENTE(bean.getEsigibilitaIva());
		lAttributiDinamiciSoggettiXmlBean.setCIG_CLIENTE(bean.getOdaCig());
		lAttributiDinamiciSoggettiXmlBean.setCUP_CLIENTE(bean.getOdaCup());
		lAttributiDinamiciSoggettiXmlBean.setPEC(bean.getEmailPec());
		
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXml(lAttributiDinamiciSoggettiXmlBean);
		return xmlAttributiDinamici;
	}

	private String getDecodeCritOperator(String operator) {

		String out = "";

		if (operator == null)
			return out;

		if (operator.equalsIgnoreCase("iContains")) {
			out = "simile a (case-insensitive)";
		} else if (operator.equalsIgnoreCase("iEquals")) {
			out = "simile a (case-insensitive)";
		} else if (operator.equalsIgnoreCase("iStartsWith")) {
			out = "simile a (case-insensitive)";
		} else if (operator.equalsIgnoreCase("iEndsWith")) {
			out = "simile a (case-insensitive)";
		} else if (operator.equalsIgnoreCase("equals")) {
			out = "uguale";
		} else if (operator.equalsIgnoreCase("greaterThan")) {
			out = "maggiore";
		} else if (operator.equalsIgnoreCase("greaterOrEqual")) {
			out = "maggiore o uguale";
		} else if (operator.equalsIgnoreCase("lessThan")) {
			out = "minore";
		} else if (operator.equalsIgnoreCase("lessOrEqual")) {
			out = "minore o uguale";
		} else if (operator.equalsIgnoreCase("betweenInclusive")) {
			out = "tra";
		}
		return out;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean filterBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		Map<String, Object> advancedCriteriaData = extractAdvancedCriteriaData(filterBean.getCriteria());

		List<CriteriPersonalizzati> listCustomFilters = createCriteriPersonalizzati(filterBean.getCriteria());

		DmpkAnagraficaTrovaclientiBean input = createDmpkAnagraficaTrovaclientiBean(advancedCriteriaData, loginBean, listCustomFilters);
		input.setOverflowlimitin(-1);

		// Inizializzo l'OUTPUT
		DmpkAnagraficaTrovaclienti lDmpkAnagraficaTrovaclienti = new DmpkAnagraficaTrovaclienti();
		StoreResultBean<DmpkAnagraficaTrovaclientiBean> output = lDmpkAnagraficaTrovaclienti.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		NroRecordTotBean retValue = new NroRecordTotBean();

		retValue.setNroRecordTot(output.getResultBean().getNrototrecout());

		return retValue;

	}
}