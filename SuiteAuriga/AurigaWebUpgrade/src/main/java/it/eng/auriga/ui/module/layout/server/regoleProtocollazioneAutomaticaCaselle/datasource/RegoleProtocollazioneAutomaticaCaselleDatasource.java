/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailDregolaregautoBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailIuregolaregautoBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLoaddettregolaregautoBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovaregoleregautoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificazioneFascicoloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.BodyEmailMatch;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.CaratteristicheEmailProtocollazioneAutomaticaCaselleBean;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.ClassifFolderTit;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.DatiSegnatura;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.FolderCustom;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.IndirizziDestinatari;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.OggettoEmailMatch;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.RegistrazioneDestinatari;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.RegoleProtocollazioneAutomaticaCaselleBean;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.RegoleProtocollazioneAutomaticaCaselleFiltriXmlBean;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.RegoleProtocollazioneAutomaticaCaselleXMLDatiRegInSezioneCacheBean;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.RegoleProtocollazioneAutomaticaCaselleXMLFiltroInSezioneCacheBean;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.RegoleProtocollazioneAutomaticaCaselleXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean.UoRegProtAutoCaselleBean;
import it.eng.client.DmpkIntMgoEmailDregolaregauto;
import it.eng.client.DmpkIntMgoEmailIuregolaregauto;
import it.eng.client.DmpkIntMgoEmailLoaddettregolaregauto;
import it.eng.client.DmpkIntMgoEmailTrovaregoleregauto;
import it.eng.document.function.bean.BodyEmailMatchListBean;
import it.eng.document.function.bean.ClassifFolderTitRegoleProtAutoBean;
import it.eng.document.function.bean.DatiSegnaturaRegoleProtAutoBean;
import it.eng.document.function.bean.DestinatariDatiRegRegoleProtAutoBean;
import it.eng.document.function.bean.DestinatariRegoleProtAutoBean;
import it.eng.document.function.bean.OggettoEmailMatchListBean;
import it.eng.document.function.bean.XMLDatiRegOutRegoleProtAutoCaselleBean;
import it.eng.document.function.bean.XmlFiltroAutoRegoleProtAutoCaselleBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="RegoleProtocollazioneAutomaticaCaselleDatasource")
public class RegoleProtocollazioneAutomaticaCaselleDatasource extends AurigaAbstractFetchDatasource<RegoleProtocollazioneAutomaticaCaselleBean> {


	@Override
	public String getNomeEntita() {
		return "regole_protocollazione_automatica_caselle_pec_peo";
	};


	@Override
	public PaginatorBean<RegoleProtocollazioneAutomaticaCaselleBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT
		DmpkIntMgoEmailTrovaregoleregautoBean input =  createFetchInput(criteria, token, idUserLavoro);
		
		// Inizializzo l'OUTPUT
		DmpkIntMgoEmailTrovaregoleregauto lservice = new DmpkIntMgoEmailTrovaregoleregauto();
		StoreResultBean<DmpkIntMgoEmailTrovaregoleregautoBean> output = lservice.execute(getLocale(), loginBean, input);
		
		boolean overflow = false;
		
		// Leggo il result
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} else {
				overflow = manageOverflow(defaultMessage);
			}
		}
		
		// SETTO L'OUTPUT
		List<RegoleProtocollazioneAutomaticaCaselleBean> data = new ArrayList<RegoleProtocollazioneAutomaticaCaselleBean>();
		if (output.getResultBean().getResultout()!=null && !output.getResultBean().getResultout().equalsIgnoreCase("") && output.getResultBean().getNrototrecout() != null && output.getResultBean().getNrototrecout() > 0){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getResultout(), RegoleProtocollazioneAutomaticaCaselleBean.class, new RegoleProtocollazioneAutomaticaCaselleXmlBeanDeserializationHelper(createRemapConditions()));
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
		
		PaginatorBean<RegoleProtocollazioneAutomaticaCaselleBean> lPaginatorBean = new PaginatorBean<RegoleProtocollazioneAutomaticaCaselleBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
		
	}

	@Override
	public RegoleProtocollazioneAutomaticaCaselleBean get(RegoleProtocollazioneAutomaticaCaselleBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkIntMgoEmailLoaddettregolaregauto store = new DmpkIntMgoEmailLoaddettregolaregauto();
		DmpkIntMgoEmailLoaddettregolaregautoBean input = new DmpkIntMgoEmailLoaddettregolaregautoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdregolain(StringUtils.isNotBlank(bean.getIdRegola()) ? new BigDecimal(bean.getIdRegola()) : null);
		
		StoreResultBean<DmpkIntMgoEmailLoaddettregolaregautoBean> output = store.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
		DmpkIntMgoEmailLoaddettregolaregautoBean resultBean = output.getResultBean();
		if (resultBean != null) {
			getDettaglioFromStore(resultBean, bean);
		}
		return bean;
	}
	
	private void getDettaglioFromStore (DmpkIntMgoEmailLoaddettregolaregautoBean resultBean, RegoleProtocollazioneAutomaticaCaselleBean bean) throws ParseException, Exception {
		bean.setNomeRegola(resultBean.getNomeregolaout());
		bean.setDescrizioneRegola(resultBean.getDesregolaout());// new SimpleDateFormat(FMT_STD_DATA).parse(tsValidaDal)
		bean.setStatoRegola(resultBean.getStatoout());
		bean.setDtAttivazione(resultBean.getDtattivazioneout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(resultBean.getDtattivazioneout()) : null );
		bean.setDtCessazione(resultBean.getDtcessazioneout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(resultBean.getDtcessazioneout()) : null );
		bean.setDtFineSospensione(resultBean.getDtfinesospensioneout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(resultBean.getDtfinesospensioneout()) : null);
		bean.setDtInizioSospensione(resultBean.getDtfinesospensioneout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(resultBean.getDtfinesospensioneout()) : null);
		
		String xmlfiltroout = resultBean.getXmlfiltroout();
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
		XmlFiltroAutoRegoleProtAutoCaselleBean lXmlFiltroAutoRegoleProtAutoCaselleBean = lXmlUtility.unbindXml(xmlfiltroout, XmlFiltroAutoRegoleProtAutoCaselleBean.class);
		
		if (lXmlFiltroAutoRegoleProtAutoCaselleBean.getAccountRicezione()!=null) {
			List<String> listaAccountRicezione = new ArrayList<String>();
			StringSplitterServer stAccountRicezione = new StringSplitterServer(
					lXmlFiltroAutoRegoleProtAutoCaselleBean.getAccountRicezione(), ";");
			if (stAccountRicezione.countTokens() > 1) {
				while (stAccountRicezione.hasMoreTokens()) {
					listaAccountRicezione.add(stAccountRicezione.nextToken());
				}
			} else {
				listaAccountRicezione.add(lXmlFiltroAutoRegoleProtAutoCaselleBean.getAccountRicezione());
			} 
			bean.setCaselle(listaAccountRicezione);
		}
		
		if (lXmlFiltroAutoRegoleProtAutoCaselleBean.getAccountMittente()!=null) {
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaIndirizziMittenti = new ArrayList<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean>();
			StringSplitterServer stAccountMittente = new StringSplitterServer(
					lXmlFiltroAutoRegoleProtAutoCaselleBean.getAccountRicezione(), ";");
			if (stAccountMittente.countTokens() > 1) {
				while (stAccountMittente.hasMoreTokens()) {
					CaratteristicheEmailProtocollazioneAutomaticaCaselleBean lCaratteristicheEmailProtocollazioneAutomaticaCaselleBean = new CaratteristicheEmailProtocollazioneAutomaticaCaselleBean();
					lCaratteristicheEmailProtocollazioneAutomaticaCaselleBean.setIndirizzoMittente(stAccountMittente.nextToken());
					listaIndirizziMittenti.add(lCaratteristicheEmailProtocollazioneAutomaticaCaselleBean);
				}
			} else {
				CaratteristicheEmailProtocollazioneAutomaticaCaselleBean lCaratteristicheEmailProtocollazioneAutomaticaCaselleBean = new CaratteristicheEmailProtocollazioneAutomaticaCaselleBean();
				lCaratteristicheEmailProtocollazioneAutomaticaCaselleBean.setIndirizzoMittente(lXmlFiltroAutoRegoleProtAutoCaselleBean.getAccountRicezione());
				listaIndirizziMittenti.add(lCaratteristicheEmailProtocollazioneAutomaticaCaselleBean);
			} 
			bean.setListaIndirizziMittenti(listaIndirizziMittenti);
		}
		
		if(lXmlFiltroAutoRegoleProtAutoCaselleBean.getOggettoEmailMatchList()!=null && lXmlFiltroAutoRegoleProtAutoCaselleBean.getOggettoEmailMatchList().size()>0) {
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaParoleInOggetto = new ArrayList<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean>();
			for (OggettoEmailMatchListBean parolaInOggetto : lXmlFiltroAutoRegoleProtAutoCaselleBean.getOggettoEmailMatchList()) {
				CaratteristicheEmailProtocollazioneAutomaticaCaselleBean lParolaInOggetto = new CaratteristicheEmailProtocollazioneAutomaticaCaselleBean();
				lParolaInOggetto.setParolaInOggettoMail(parolaInOggetto.getParoleInOggetto());
				listaParoleInOggetto.add(lParolaInOggetto);
			}
			bean.setListaParoleInOggettoMail(listaParoleInOggetto);
		}
		
		if(lXmlFiltroAutoRegoleProtAutoCaselleBean.getBodyEmailMatchList()!=null && lXmlFiltroAutoRegoleProtAutoCaselleBean.getBodyEmailMatchList().size()>0) {
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaParoleInTesto = new ArrayList<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean>();
			for (BodyEmailMatchListBean parolaInTesto : lXmlFiltroAutoRegoleProtAutoCaselleBean.getBodyEmailMatchList()) {
				CaratteristicheEmailProtocollazioneAutomaticaCaselleBean lParolaInTesto = new CaratteristicheEmailProtocollazioneAutomaticaCaselleBean();
				lParolaInTesto.setParolaInTestoMail(parolaInTesto.getParoleInTesto());
				listaParoleInTesto.add(lParolaInTesto);
			}
			bean.setListaParoleInTestoMail(listaParoleInTesto);
		}
		
		if(lXmlFiltroAutoRegoleProtAutoCaselleBean.getDestinatari()!=null && lXmlFiltroAutoRegoleProtAutoCaselleBean.getDestinatari().size()>0) {
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaDestinatari = new ArrayList<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean>();
			for (DestinatariRegoleProtAutoBean dest : lXmlFiltroAutoRegoleProtAutoCaselleBean.getDestinatari()) {
				CaratteristicheEmailProtocollazioneAutomaticaCaselleBean lDestinatario = new CaratteristicheEmailProtocollazioneAutomaticaCaselleBean();
				lDestinatario.setIndirizzoDestinatario(dest.getIndirizzi());
				lDestinatario.setFlgTipoEmailDestinatario(dest.getTipo());
				listaDestinatari.add(lDestinatario);
			}
			bean.setListaEmailDestinatari(listaDestinatari);
		}	
		
		if(lXmlFiltroAutoRegoleProtAutoCaselleBean.getTipoMail()!=null) {
			bean.setFlgTipoEmailEntrata(lXmlFiltroAutoRegoleProtAutoCaselleBean.getTipoMail());
		}
		
		if(lXmlFiltroAutoRegoleProtAutoCaselleBean.getTipoAccountRicezione()!=null) {
			bean.setFlgTipoEmailRicezione(lXmlFiltroAutoRegoleProtAutoCaselleBean.getTipoAccountRicezione());
		}
		
		if(lXmlFiltroAutoRegoleProtAutoCaselleBean.getDatiSegnatura()!=null && lXmlFiltroAutoRegoleProtAutoCaselleBean.getDatiSegnatura().size()>0) {
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaDatiSegnatura = new ArrayList<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean>();
			for (DatiSegnaturaRegoleProtAutoBean datiSegnatura : lXmlFiltroAutoRegoleProtAutoCaselleBean.getDatiSegnatura()) {
				CaratteristicheEmailProtocollazioneAutomaticaCaselleBean lDatiSegnatura = new CaratteristicheEmailProtocollazioneAutomaticaCaselleBean();
				lDatiSegnatura.setCodAmmMittente(datiSegnatura.getCodAmministrazione());
				lDatiSegnatura.setCodAooMittente(datiSegnatura.getCodAoo());
				lDatiSegnatura.setCodRegistroMittente(datiSegnatura.getSiglaRegistro());
				listaDatiSegnatura.add(lDatiSegnatura);
			}
			bean.setListaDatiSegnatura(listaDatiSegnatura);
		}
		
		String xmldatiregout = resultBean.getXmldatiregout();
		XMLDatiRegOutRegoleProtAutoCaselleBean lXMLDatiRegOutRegoleProtAutoCaselleBean = lXmlUtility.unbindXml(xmldatiregout, XMLDatiRegOutRegoleProtAutoCaselleBean.class);
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getIdUORegistrazione()!=null) {
			List<UoRegProtAutoCaselleBean> listaUoReg = new ArrayList<UoRegProtAutoCaselleBean>();
			UoRegProtAutoCaselleBean uoRegistrazione = new UoRegProtAutoCaselleBean();
			uoRegistrazione.setIdUo(lXMLDatiRegOutRegoleProtAutoCaselleBean.getIdUORegistrazione());
			if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getCodRapidoUORegistrazione()!=null) {
				uoRegistrazione.setCodRapido(lXMLDatiRegOutRegoleProtAutoCaselleBean.getCodRapidoUORegistrazione());
			}
			if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDenomUORegistrazione()!=null) {
				uoRegistrazione.setDescrizione(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDenomUORegistrazione());
			}
			listaUoReg.add(uoRegistrazione);
			bean.setUoRegistrazione(listaUoReg);
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getIdUserRegistrazione()!=null) {
			bean.setIdUtente(lXMLDatiRegOutRegoleProtAutoCaselleBean.getIdUserRegistrazione());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDesUserRegistrazione()!=null) {
			bean.setDesUtenteRegistrazione(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDesUserRegistrazione());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getCodCategoriaReg()!=null) {
			bean.setFlgTipoRegistrazione(lXMLDatiRegOutRegoleProtAutoCaselleBean.getCodCategoriaReg());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getSiglaRegistro()!=null) {
			bean.setRepertorio(lXMLDatiRegOutRegoleProtAutoCaselleBean.getSiglaRegistro());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getIdMittenteInRubrica()!=null) {
			List<MittenteProtBean> listaMittReg = new ArrayList<MittenteProtBean>();
			MittenteProtBean mittInRub = new MittenteProtBean();
			mittInRub.setIdSoggetto(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDenomMittente());
			if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDenomMittente()!=null) {
				mittInRub.setDenominazioneMittente(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDenomMittente());
			}
			listaMittReg.add(mittInRub);
			bean.setMittenteRegistrazione(listaMittReg);
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDestinatari()!=null && lXMLDatiRegOutRegoleProtAutoCaselleBean.getDestinatari().size()>0) {
			List<UoRegProtAutoCaselleBean> listaDest = new ArrayList<UoRegProtAutoCaselleBean>();
			for (DestinatariDatiRegRegoleProtAutoBean dest : lXMLDatiRegOutRegoleProtAutoCaselleBean.getDestinatari()) {
				UoRegProtAutoCaselleBean lDestinatario = new UoRegProtAutoCaselleBean();
				lDestinatario.setTypeNodo(dest.getTypeNodo());
				lDestinatario.setIdUo(dest.getIdUoSv());
				if(dest.getTipoAssegnazione()!=null) {
					if(dest.getTipoAssegnazione().equals("ASS")) {
						lDestinatario.setFlgAssDestinatario(true);
					} else {
						lDestinatario.setFlgCcDestinatario(true);
					}
				} 
				lDestinatario.setCodRapido(dest.getCodRapido());
				lDestinatario.setDescrizione(dest.getDenDescUoSv());
				listaDest.add(lDestinatario);
			}
			bean.setListaUoDestinatarie(listaDest);
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getClassifFolderTit()!=null && lXMLDatiRegOutRegoleProtAutoCaselleBean.getClassifFolderTit().size()>0) {
			List<ClassificazioneFascicoloBean> listaClassifFolderTit = new ArrayList<ClassificazioneFascicoloBean>();
			for (ClassifFolderTitRegoleProtAutoBean classif : lXMLDatiRegOutRegoleProtAutoCaselleBean.getClassifFolderTit()) {
				ClassificazioneFascicoloBean lClassFasc = new ClassificazioneFascicoloBean();
				lClassFasc.setIdFolderFascicolo(classif.getIdFolder());
				lClassFasc.setIdClassifica(classif.getIdClassfica());
				lClassFasc.setNomeFascicolo(classif.getNomeFolder());
				lClassFasc.setAnnoFascicolo(classif.getAnnoApertura());
				lClassFasc.setNroFascicolo(classif.getNroProgFasc());
				lClassFasc.setNroSottofascicolo(classif.getNroSottoFasc());
				lClassFasc.setNroInserto(classif.getNroInserto());
				lClassFasc.setCodice(classif.getCodSecondario());
				lClassFasc.setIndice(classif.getIndiceClass());
				lClassFasc.setDescrizioneClassifica(classif.getDescClass());
				lClassFasc.setCapofila(classif.getFascCapofila());
				listaClassifFolderTit.add(lClassFasc);
			}
			bean.setClassificazioneFascicolazione(listaClassifFolderTit);
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getFlgRiservatezza()!=null) {
			bean.setFlgRiservatezza(lXMLDatiRegOutRegoleProtAutoCaselleBean.getFlgRiservatezza());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getOpzTimbratura()!=null) {
			bean.setFlgTimbratura(lXMLDatiRegOutRegoleProtAutoCaselleBean.getOpzTimbratura());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getFlgInvioConfermaReg()!=null) {
			bean.setFlgNotificaConferma(lXMLDatiRegOutRegoleProtAutoCaselleBean.getFlgInvioConfermaReg());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDestinatarioConfermaReg()!=null) {
			bean.setIndirizzoDestinatarioRisposta(lXMLDatiRegOutRegoleProtAutoCaselleBean.getDestinatarioConfermaReg());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getOggettoConfermaReg()!=null) {
			bean.setOggettoRisposta(lXMLDatiRegOutRegoleProtAutoCaselleBean.getOggettoConfermaReg());
		}
		
		if(lXMLDatiRegOutRegoleProtAutoCaselleBean.getTestoConfermaReg()!=null) {
			bean.setTestoRisposta(lXMLDatiRegOutRegoleProtAutoCaselleBean.getTestoConfermaReg());
		}
		
	}

	@Override
	public RegoleProtocollazioneAutomaticaCaselleBean remove(RegoleProtocollazioneAutomaticaCaselleBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT		
		DmpkIntMgoEmailDregolaregautoBean input = new DmpkIntMgoEmailDregolaregautoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdregolain(StringUtils.isNotBlank(bean.getIdRegola()) ? new BigDecimal(bean.getIdRegola()) : null);
		input.setMotivoin(bean.getMotivoCancellazione());     
		
		// Eseguo il servizio
		DmpkIntMgoEmailDregolaregauto lservice = new DmpkIntMgoEmailDregolaregauto();
		StoreResultBean<DmpkIntMgoEmailDregolaregautoBean> output = lservice.execute(getLocale(), loginBean, input);
		
		// Leggo il result
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
	public RegoleProtocollazioneAutomaticaCaselleBean add(RegoleProtocollazioneAutomaticaCaselleBean bean)
			throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		DmpkIntMgoEmailIuregolaregautoBean inputBean = new DmpkIntMgoEmailIuregolaregautoBean();
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		inputBean.setCodidconnectiontokenin(token);
		inputBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		if(bean.getIdRegola()!=null && !bean.getIdRegola().equals("")) {
			inputBean.setIdregolaio(new BigDecimal(bean.getIdRegola()));
		}
		inputBean.setNomeregolain(bean.getNomeRegola());
		inputBean.setDesregolain(bean.getDescrizioneRegola());
		inputBean.setDtattivazionein(bean.getDtAttivazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtAttivazione()) : null);
		inputBean.setDtcessazionein(bean.getDtCessazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtCessazione()) : null);
		inputBean.setDtiniziosospensionein(bean.getDtInizioSospensione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtInizioSospensione()) : null);
		inputBean.setDtfinesospensionein(bean.getDtFineSospensione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFineSospensione()) : null);

		String xmlFiltro = getXmlFiltro(bean);
		String xmlDatiReg = getXmlDatiReg(bean);
		
		inputBean.setXmlfiltroin(xmlFiltro);
		inputBean.setXmldatiregin(xmlDatiReg);
		
		DmpkIntMgoEmailIuregolaregauto store = new DmpkIntMgoEmailIuregolaregauto(); 
		StoreResultBean<DmpkIntMgoEmailIuregolaregautoBean> output  = store.execute(getLocale(), loginBean, inputBean);
		// Leggo il result
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		DmpkIntMgoEmailIuregolaregautoBean resultBean = null;
		if (output.getResultBean() != null) {
			resultBean = output.getResultBean();
			bean.setIdRegola(resultBean.getIdregolaio() != null ? resultBean.getIdregolaio().toString() : null);
		}
		
		return bean;
	}

	protected String getXmlFiltro(RegoleProtocollazioneAutomaticaCaselleBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException,
	NoSuchMethodException {
		String xmlFiltro = "";
		RegoleProtocollazioneAutomaticaCaselleXMLFiltroInSezioneCacheBean xmlCacheBean = new RegoleProtocollazioneAutomaticaCaselleXMLFiltroInSezioneCacheBean();
		
		List<BodyEmailMatch> listaBem = new ArrayList<BodyEmailMatch>();
		List<DatiSegnatura> listaDs = new ArrayList<DatiSegnatura>();
		List<IndirizziDestinatari> listaId = new ArrayList<IndirizziDestinatari>();
		List<OggettoEmailMatch> listaOem = new ArrayList<OggettoEmailMatch>();
		
		if (bean.getListaParoleInTestoMail() != null) {
			for (CaratteristicheEmailProtocollazioneAutomaticaCaselleBean paroleInTestoMail : bean.getListaParoleInTestoMail()) {
				BodyEmailMatch body = new BodyEmailMatch();
				body.setBodyEmail(paroleInTestoMail.getParolaInTestoMail());
				listaBem.add(body);
			}
		}
		
		if (bean.getListaParoleInOggettoMail() != null) {
			for(CaratteristicheEmailProtocollazioneAutomaticaCaselleBean paroleInOggettoMail : bean.getListaParoleInOggettoMail()) {
				OggettoEmailMatch oggetto = new OggettoEmailMatch ();
				oggetto.setOggettoEmail(paroleInOggettoMail.getParolaInOggettoMail());
				listaOem.add(oggetto);
			}
		}
		
		if (bean.getListaDatiSegnatura() != null) {
			for (CaratteristicheEmailProtocollazioneAutomaticaCaselleBean datiSegnatura : bean.getListaDatiSegnatura()) {
				DatiSegnatura datoSegnatura = new DatiSegnatura();
				datoSegnatura.setCodAmministrazione(datiSegnatura.getCodAmmMittente());
				datoSegnatura.setCodAoo(datiSegnatura.getCodAooMittente());
				datoSegnatura.setSiglaRegistro(datiSegnatura.getCodRegistroMittente());
				listaDs.add(datoSegnatura);
			}
		}
		
		if (bean.getListaEmailDestinatari() != null) {
			for (CaratteristicheEmailProtocollazioneAutomaticaCaselleBean emailDestinatari : bean.getListaEmailDestinatari()) {
				IndirizziDestinatari indirizzoDestinatari = new IndirizziDestinatari();
				indirizzoDestinatari.setIndirizzo(emailDestinatari.getIndirizzoDestinatario());
				indirizzoDestinatari.setTipoIndirizzo(emailDestinatari.getFlgTipoEmailDestinatario());
				listaId.add(indirizzoDestinatari);
			}
		}
		
		xmlCacheBean.setListaBodyEmail(listaBem);
		xmlCacheBean.setListaDatiSegnatura(listaDs);
		xmlCacheBean.setListaIndirizziDestinatari(listaId);
		xmlCacheBean.setListaOggettoEmail(listaOem);
		
		String accountMittente = "";
		if (bean.getCaselle() != null) {
			for (String casella : bean.getCaselle()) {
				accountMittente += ";";
				accountMittente += casella;
			}
		}
		xmlCacheBean.setAccountMittente(accountMittente);
		
		String indirizzoMittente = "";
		if (bean.getListaIndirizziMittenti() != null) {
			for (CaratteristicheEmailProtocollazioneAutomaticaCaselleBean indirizzo : bean.getListaIndirizziMittenti()) {
				indirizzoMittente += ";";
				indirizzoMittente += indirizzo.getIndirizzoMittente();
			}
		}
		
		xmlCacheBean.setAccountRicezione(indirizzoMittente);
		
		xmlCacheBean.setTipoAccountRicezione(bean.getFlgTipoEmailRicezione());
		
		xmlCacheBean.setTipoMail(bean.getFlgTipoEmailEntrata());
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlFiltro = lXmlUtilitySerializer.bindXml(xmlCacheBean);
		return xmlFiltro;
	}
	
	protected String getXmlDatiReg(RegoleProtocollazioneAutomaticaCaselleBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException,
	NoSuchMethodException {
		String xmlDatiReg = "";
		RegoleProtocollazioneAutomaticaCaselleXMLDatiRegInSezioneCacheBean xmlCacheBean = new RegoleProtocollazioneAutomaticaCaselleXMLDatiRegInSezioneCacheBean();
		
		List<RegistrazioneDestinatari> listaRd = new ArrayList<RegistrazioneDestinatari>();
		List<ClassifFolderTit> listaCft = new ArrayList<ClassifFolderTit>();
		// listaFolderCustom id, path, flag?
		List<FolderCustom> listaFc = new ArrayList<FolderCustom>();
		
		if (bean.getListaUoDestinatarie() != null) {
			for (UoRegProtAutoCaselleBean uoDestinatarie : bean.getListaUoDestinatarie()) {
				RegistrazioneDestinatari rd = new RegistrazioneDestinatari();
				rd.setIdUoOScrivania(uoDestinatarie.getIdUo());
				rd.setTipoDestinatario(uoDestinatarie.getTypeNodo());
				if (uoDestinatarie.getFlgAssDestinatario() != null && uoDestinatarie.getFlgAssDestinatario() || uoDestinatarie.getFlgCcDestinatario() != null && uoDestinatarie.getFlgCcDestinatario()) {
					rd.setValoreCCOAss(uoDestinatarie.getFlgAssDestinatario() ? "ASS" : "CC");
				}
				listaRd.add(rd);
			}
		}
		
		if (bean.getClassificazioneFascicolazione() != null) {
			for (ClassificazioneFascicoloBean classificazione : bean.getClassificazioneFascicolazione()) {
				ClassifFolderTit classif = new ClassifFolderTit ();
				classif.setFlagOperazione("E");
				classif.setAnnoApertura(classificazione.getAnnoFascicolo());
				classif.setIdClassifica(classificazione.getIdClassifica());
				classif.setIdFolder(classificazione.getIdFolderFascicolo());
				classif.setNomeFolder(classificazione.getNomeFascicolo());
				listaCft.add(classif);
			}
		}
		
		xmlCacheBean.setListaIndirizziDestinatari(listaRd);
		xmlCacheBean.setListClassifFolderTit(listaCft);
//		xmlCacheBean.setListFolderCustom(listaFc);
		
		xmlCacheBean.setCodCategoriaReg(bean.getFlgTipoRegistrazione());
		if (bean.getMittenteRegistrazione() != null && bean.getMittenteRegistrazione().get(0)!=null) {	
			if(bean.getMittenteRegistrazione().get(0).getDenominazioneMittente()!=null) {
				xmlCacheBean.setDenomMittente(bean.getMittenteRegistrazione().get(0).getDenominazioneMittente());
			} else if(bean.getMittenteRegistrazione().get(0).getCognomeMittente()!=null && bean.getMittenteRegistrazione().get(0).getNomeMittente()!=null) {
				xmlCacheBean.setDenomMittente(bean.getMittenteRegistrazione().get(0).getCognomeMittente() + " " + bean.getMittenteRegistrazione().get(0).getNomeMittente());
			}
			
		}
		xmlCacheBean.setDestinatarioConfermaReg(bean.getIndirizzoDestinatarioRisposta());
		
		if (bean.getMittenteRegistrazione() != null && bean.getMittenteRegistrazione().get(0)!=null) {
			xmlCacheBean.setIdMittenteInRubrica(bean.getMittenteRegistrazione().get(0).getIdSoggetto());
		}
		
		if (bean.getUoRegistrazione() != null && bean.getUoRegistrazione().get(0)!=null) {
			xmlCacheBean.setIdUORegistrazione(bean.getUoRegistrazione().get(0).getIdUo());
		}
		
		xmlCacheBean.setIdUserRegistrazione(bean.getIdUtente());
		xmlCacheBean.setInvioConfermaReg(bean.getFlgNotificaConferma() != null && bean.getFlgNotificaConferma().equals("1") ? bean.getFlgNotificaConferma() : null);
		xmlCacheBean.setOggettoConfermaReg(bean.getOggettoRisposta());
		xmlCacheBean.setOpzTimbratura(bean.getFlgTimbratura());
		xmlCacheBean.setRiservatezza(bean.getFlgRiservatezza() != null && bean.getFlgRiservatezza().equals("1") ? bean.getFlgRiservatezza() : null);
		
		xmlCacheBean.setSiglaRegistro(bean.getRepertorio());
		xmlCacheBean.setTestoConfermaReg(bean.getTestoRisposta());
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlDatiReg = lXmlUtilitySerializer.bindXml(xmlCacheBean);
		return xmlDatiReg;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT	
		DmpkIntMgoEmailTrovaregoleregautoBean input = createFetchInput(criteria, token, idUserLavoro);
		
		//non voglio overflow
		input.setOverflowlimitin(-1);
		
		// Inizializzo l'OUTPUT		
		DmpkIntMgoEmailTrovaregoleregauto lservice = new DmpkIntMgoEmailTrovaregoleregauto();
		StoreResultBean<DmpkIntMgoEmailTrovaregoleregautoBean> output = lservice.execute(getLocale(), loginBean, input);
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if(output.isInError()) {
					throw new StoreException(output);		
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
		}
		bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		return bean;
	}	

	
	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT		
		DmpkIntMgoEmailTrovaregoleregautoBean input = createFetchInput(criteria, token, idUserLavoro);
		input.setOverflowlimitin(-2);
		
		// Inizializzo l'OUTPUT
		DmpkIntMgoEmailTrovaregoleregauto lservice = new DmpkIntMgoEmailTrovaregoleregauto();
		StoreResultBean<DmpkIntMgoEmailTrovaregoleregautoBean> output = lservice.execute(getLocale(), loginBean, input);
				
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
				if(output.isInError()) {
					throw new StoreException(defaultMessage);		
				} 
		}		
		
		//imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), RegoleProtocollazioneAutomaticaCaselleBean.class.getName());
		

		// salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), RegoleProtocollazioneAutomaticaCaselleXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());
		
	    if(jobId!=null && jobId > 0){
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString() + " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}
		return bean;
	}

	private Map<String, String> createRemapConditions() {
		return new HashMap<String, String>();
	}

	
	protected DmpkIntMgoEmailTrovaregoleregautoBean createFetchInput(AdvancedCriteria criteria, String token, String idUserLavoro) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		
		String nomeRegola = null;
		String descrizioneRegola = null;
		String statiRegola = null;
		
		
		RegoleProtocollazioneAutomaticaCaselleFiltriXmlBean lFiltriXmlBean = new RegoleProtocollazioneAutomaticaCaselleFiltriXmlBean();
		
		// Inizializzo l'INPUT		
		DmpkIntMgoEmailTrovaregoleregautoBean input = new DmpkIntMgoEmailTrovaregoleregautoBean();
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {				
				if ("nomeRegola".equals(crit.getFieldName())) {
					nomeRegola = getTextFilterValue(crit);
				}
				else if ("descrizioneRegola".equals(crit.getFieldName())) {
					descrizioneRegola = getTextFilterValue(crit);
				}
				else if ("statiRegola".equals(crit.getFieldName())) {
					statiRegola = getTextFilterValue(crit);
				}
			}
		}
		
		
		if(StringUtils.isNotBlank(nomeRegola)) {
			lFiltriXmlBean.setNomeRegola(nomeRegola);
		}
		
		if(StringUtils.isNotBlank(descrizioneRegola)) {
			lFiltriXmlBean.setDescrizioneRegola(descrizioneRegola);
		}
		if(StringUtils.isNotBlank(statiRegola)) {
			lFiltriXmlBean.setStatiRegola(statiRegola);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlFilters = lXmlUtilitySerializer.bindXml(lFiltriXmlBean);
		input.setFiltriio(xmlFilters);
		
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);		
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		
		return input;
	}

}
