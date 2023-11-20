/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGrantprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityRevokeprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_tipi_reg_num_ud.bean.DmpkTipiRegNumUdDtiporegnumBean;
import it.eng.auriga.database.store.dmpk_tipi_reg_num_ud.bean.DmpkTipiRegNumUdIutiporegnumBean;
import it.eng.auriga.database.store.dmpk_tipi_reg_num_ud.bean.DmpkTipiRegNumUdLoaddetttiporegnumBean;
import it.eng.auriga.database.store.dmpk_tipi_reg_num_ud.bean.DmpkTipiRegNumUdTrovatipiregnumBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.registriNumerazione.datasource.bean.ListaRegistriNumerazioneBean;
import it.eng.auriga.ui.module.layout.server.registriNumerazione.datasource.bean.RegistriNumerazioneBean;
import it.eng.auriga.ui.module.layout.server.registriNumerazione.datasource.bean.RegistriNumerazioneXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.registriNumerazione.datasource.bean.TipiDocAmmEscBean;
import it.eng.client.DmpkDefSecurityGrantprivsudefcontesto;
import it.eng.client.DmpkDefSecurityRevokeprivsudefcontesto;
import it.eng.client.DmpkTipiRegNumUdDtiporegnum;
import it.eng.client.DmpkTipiRegNumUdIutiporegnum;
import it.eng.client.DmpkTipiRegNumUdLoaddetttiporegnum;
import it.eng.client.DmpkTipiRegNumUdTrovatipiregnum;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "RegistriNumerazioneDataSource")
public class RegistriNumerazioneDataSource extends AurigaAbstractFetchDatasource<RegistriNumerazioneBean>  {

	private static final Logger log = Logger.getLogger(RegistriNumerazioneDataSource.class);

	@Override
	public String getNomeEntita() {
		return "registri_numerazione";
	};

	@Override
	public PaginatorBean<RegistriNumerazioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT
		DmpkTipiRegNumUdTrovatipiregnumBean input =  createFetchInput(criteria, token, idUserLavoro);
		
		
		 // Inizializzo l'OUTPUT
		DmpkTipiRegNumUdTrovatipiregnum DmpkDefSecurityTrovagruppiprivilegiBean = new DmpkTipiRegNumUdTrovatipiregnum();
		StoreResultBean<DmpkTipiRegNumUdTrovatipiregnumBean> output = DmpkDefSecurityTrovagruppiprivilegiBean.execute(getLocale(), loginBean, input);
		
		boolean overflow = false;
		
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} else {
				overflow = manageOverflow(defaultMessage);
			}
		}
		
		// SETTO L'OUTPUT
		List<RegistriNumerazioneBean> data = new ArrayList<RegistriNumerazioneBean>();
		if (output.getResultBean().getNrototrecout() != null){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), RegistriNumerazioneBean.class, new RegistriNumerazioneXmlBeanDeserializationHelper(createRemapConditions()));
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
		
		PaginatorBean<RegistriNumerazioneBean> lPaginatorBean = new PaginatorBean<RegistriNumerazioneBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

	@Override
	public RegistriNumerazioneBean get(RegistriNumerazioneBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkTipiRegNumUdLoaddetttiporegnumBean input = new DmpkTipiRegNumUdLoaddetttiporegnumBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdtiporennumio(bean.getIdTipoRegNum() != null? new BigDecimal(bean.getIdTipoRegNum()) : null);

		DmpkTipiRegNumUdLoaddetttiporegnum loadDettAttr = new DmpkTipiRegNumUdLoaddetttiporegnum();
		StoreResultBean<DmpkTipiRegNumUdLoaddetttiporegnumBean> output = loadDettAttr.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		RegistriNumerazioneBean result = new RegistriNumerazioneBean();
		
		// id
		result.setIdTipoRegNum(output.getResultBean().getIdtiporennumio().toString());
		
		// categoria
		result.setCodCategoria(output.getResultBean().getCodcategoriaout());                   
		
		// sigla
		result.setSiglaTipoRegNum(output.getResultBean().getSiglaout());
		
		// descrizione
		result.setDescrizione(output.getResultBean().getDescrizioneout());                                           
		
		// Numerazione data in automatico a sistema
		result.setFlgInterna(output.getResultBean().getFlginternaout() != null && output.getResultBean().getFlginternaout() == 1 ? true : false);  
		
		// Numerazione senza soluzione di continuità
		result.setFlgNumerazioneSenzaContinuita(output.getResultBean().getFlgbuchiammessiout() != null && output.getResultBean().getFlgbuchiammessiout() == 1 ? false : true);

		// Rinnovo numerazione (ogni quanti anni)
		result.setNrAnniRinnovaNumerazione((output.getResultBean().getPeriodicitadianniout() != null && output.getResultBean().getPeriodicitadianniout() > 0 ) ? output.getResultBean().getPeriodicitadianniout() : null);

		// Anno inizio numerazione
		result.setAnnoInizioNum((output.getResultBean().getAnnoinizionumerazioneout() != null && output.getResultBean().getAnnoinizionumerazioneout() > 0 ) ? output.getResultBean().getAnnoinizionumerazioneout().toString() : null);
		
		// 1° numero da dare
		result.setNroIniziale(output.getResultBean().getNroinizialeout() != null ? output.getResultBean().getNroinizialeout() : null);
		
		// Ultimo numero
		result.setNrUltimaReg(output.getResultBean().getUltimonrogenearatoout() != null ? output.getResultBean().getUltimonrogenearatoout().intValue() : null);
		
		// Data ultimo numero
		result.setDtUltimaReg(output.getResultBean().getDataultnrogeneratoout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDataultnrogeneratoout()) : null);
		
		// Restrizioni
		result.setFlgRestrizioniVersoRegE(false);
		result.setFlgRestrizioniVersoRegU(false);
		result.setFlgRestrizioniVersoRegI(false);
		if (output.getResultBean().getRestrizioniversoregout() != null && !output.getResultBean().getRestrizioniversoregout().equalsIgnoreCase("")){			
			String[] restrizioni = output.getResultBean().getRestrizioniversoregout().split(";");
			if (restrizioni.length > 0) {			
				for (int i = 0 ; i < restrizioni.length; i++ ){
					// entrata
					if (restrizioni[i].equalsIgnoreCase("E")){
						result.setFlgRestrizioniVersoRegE(true);
					}
					// uscita
					if (restrizioni[i].equalsIgnoreCase("U")){
						result.setFlgRestrizioniVersoRegU(true);
					}
					// interna
					if (restrizioni[i].equalsIgnoreCase("I")){
						result.setFlgRestrizioniVersoRegI(true);
					}
				}
			}
		}

		// Selezionato in base alla U.O. mittente della registrazione
		result.setFlgCtrAbilUOMitt(output.getResultBean().getFlgctrabiluomittout() != null && output.getResultBean().getFlgctrabiluomittout().intValue() == 1 ? true : false);  
		
		// Gruppo di registri
		result.setGruppoRegistriApp(output.getResultBean().getGruppoappout());
		
		// Stato (A=aperto, C=chiuso)
		result.setFlgStatoRegistro(output.getResultBean().getFlgstatoregistroout());
		
		// Data ultimo cambio stato
		result.setDtUltimoCambioStato(output.getResultBean().getTsiniziostatoregistroout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getTsiniziostatoregistroout()) : null );
		
		// Validità dal		
		result.setDtInizioVld(output.getResultBean().getDtiniziovldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtiniziovldout()) : null);
		
		// Validità al
		result.setDtFineVld(output.getResultBean().getDtfinevldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtfinevldout()) : null);
		
		// per visualizzare doc. registrati nel registro
		result.setFlgRichAbilVis(output.getResultBean().getFlgrichabilxvisualizzout() != null && output.getResultBean().getFlgrichabilxvisualizzout()== 1 ? true : false);
		
		// per numerare nel registro
		result.setFlgRichAbilXAssegn(output.getResultBean().getFlgrichabilxassegnout() != null && output.getResultBean().getFlgrichabilxassegnout()== 1 ? true : false);

		// -- (valori A/E) Indica se nel successivo argomento XMLTipiDocAmmEscOut saranno specificati i tipi di documenti ammessi (=A) o esclusi (=E) alla/dalla registrazione/numerazione 
		result.setFlgAmmEscXTipiDoc(output.getResultBean().getFlgammescxtipidocout());
				
		// Lista con i tipi di documenti ammessi (se FlgAmmEscXTipiDocIn=A)
		result.setListaTipiDocAmmEsc(getListaDocumentiAmmEsc(output));
		
		return result;
	}

	@Override
	public RegistriNumerazioneBean add(RegistriNumerazioneBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT
		DmpkTipiRegNumUdIutiporegnumBean input = new DmpkTipiRegNumUdIutiporegnumBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				
		// categoria
		input.setCodcategoriain(bean.getCodCategoria()!=null ? bean.getCodCategoria() : null);
				
		// sigla
		input.setSiglain(bean.getSiglaTipoRegNum()!=null ? bean.getSiglaTipoRegNum() : null);
				
		// descrizione
		input.setDescrizionein(bean.getDescrizione());
				
		// Numerazione data in automatico a sistema
		input.setFlginternain(bean.getFlgInterna() != null && bean.getFlgInterna() ? 1 : null);
		
		// Numerazione senza soluzione di continuità (true => 0, false => 1)
		input.setFlgbuchiammessiin(bean.getFlgNumerazioneSenzaContinuita() != null && bean.getFlgNumerazioneSenzaContinuita() ? 0 : 1);
		
		// Rinnovo numerazione (ogni quanti anni)
		input.setPeriodicitadianniin(bean.getNrAnniRinnovaNumerazione() !=null ? bean.getNrAnniRinnovaNumerazione() : null);
		
		// Anno inizio numerazione
		input.setAnnoinizionumerazionein((bean.getAnnoInizioNum() != null && !bean.getAnnoInizioNum().equalsIgnoreCase("")) ? Integer.parseInt(bean.getAnnoInizioNum()) : null);
		
		// 1° numero da dare
		input.setNroinizialein(bean.getNroIniziale() != null ? bean.getNroIniziale() : null);
		
		// Restrizioni
		//input.setul(value);(bean.getNroIniziale() != null ? bean.getNroIniziale() : null);
		
		// Selezionato in base alla U.O. mittente della registrazione
		input.setFlgctrabiluomittin(bean.getFlgCtrAbilUOMitt() != null && bean.getFlgCtrAbilUOMitt() ? new BigDecimal(1) : null);

		// Gruppo di registri
		input.setGruppoappin(bean.getGruppoRegistriApp() != null ? bean.getGruppoRegistriApp() : null);

		// Restrizioni (la concatenazione di U, E, I separati da ;) 
		String restrizioniVersoReg = "";
		if (bean.getFlgRestrizioniVersoRegE())
			restrizioniVersoReg+= restrizioniVersoReg + "E;";
		
		if (bean.getFlgRestrizioniVersoRegU())
			restrizioniVersoReg+= restrizioniVersoReg + "U;";
		
		if (bean.getFlgRestrizioniVersoRegI())
			restrizioniVersoReg+= restrizioniVersoReg + "I;";
		
		input.setRestrizioniversoregin(restrizioniVersoReg);
		
		// Stato (A=aperto, C=chiuso)
		input.setFlgstatoregistroin(bean.getFlgStatoRegistro() != null ? bean.getFlgStatoRegistro() : "A");
		
		// Validità dal		
		input.setDtiniziovldin(bean.getDtInizioVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtInizioVld()) : null);
		
		// Validità al
		input.setDtfinevldin(bean.getDtFineVld() != null ?     new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFineVld())   : null);
		
		// per visualizzare doc. registrati nel registro
		input.setFlgrichabilxvisualizzin(bean.getFlgRichAbilVis() != null && bean.getFlgRichAbilVis() ? 1 : null);
		
		// per numerare nel registro
		input.setFlgrichabilxassegnin(bean.getFlgRichAbilXAssegn() != null && bean.getFlgRichAbilXAssegn() ? 1 : null);
				
		// Lista con i tipi di documenti ammessi (se FlgAmmEscXTipiDocIn=A)
		// -- (valori A/E) Indica se nel successivo argomento XMLTipiDocAmmEscOut saranno specificati i tipi di documenti ammessi (=A) o esclusi (=E) alla/dalla registrazione/numerazione
		input.setFlgammescxtipidocin("A");
			
		// -- (valori I/C) Indica se i tipi di documenti indicati nell'argomento successivo sono forniti in modo incrementale (=I) (solo quelle da inserire/modificare/cancellare) oppure completo (=C), vale a dire che dovranno soppiantare tutti quelli già specificati (e relativi al dominio di lavoro) in caso di tipo di registrazione/numerazione da aggiornare
		input.setFlgmodtipidocammesclin("C");
		input.setXmltipidocammescin(createXmlTipiDocAmmEsc(bean.getListaTipiDocAmmEsc()));
				
		// Eseguo il servizio
		DmpkTipiRegNumUdIutiporegnum dmpkTipiRegNumUdIutiporegnum = new DmpkTipiRegNumUdIutiporegnum();
		StoreResultBean<DmpkTipiRegNumUdIutiporegnumBean> result = dmpkTipiRegNumUdIutiporegnum.execute(getLocale(), loginBean, input);

		// Leggo il result
		if (StringUtils.isNotBlank(result.getResultBean().getWarningmsgout())) {
			addMessage(result.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		else {
			bean.setIdTipoRegNum(result.getResultBean().getIdtiporennumio() != null ? result.getResultBean().getIdtiporennumio().toString() : null);
		}
		
		return bean;		
	}

	@Override
	public RegistriNumerazioneBean update(RegistriNumerazioneBean bean, RegistriNumerazioneBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT
		DmpkTipiRegNumUdIutiporegnumBean input = new DmpkTipiRegNumUdIutiporegnumBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				
		// id
		input.setIdtiporennumio(bean.getIdTipoRegNum() != null ? new BigDecimal(bean.getIdTipoRegNum()) : null);
		
		// categoria
		input.setCodcategoriain(bean.getCodCategoria()!=null ? bean.getCodCategoria() : null);
				
		// sigla
		input.setSiglain(bean.getSiglaTipoRegNum()!=null ? bean.getSiglaTipoRegNum() : null);
				
		// descrizione
		input.setDescrizionein(bean.getDescrizione());
				
		// Numerazione data in automatico a sistema
		input.setFlginternain(bean.getFlgInterna() != null && bean.getFlgInterna() ? 1 : null);
		
		// Numerazione senza soluzione di continuità (true => 0, false => 1)
		input.setFlgbuchiammessiin(bean.getFlgNumerazioneSenzaContinuita() != null && bean.getFlgNumerazioneSenzaContinuita() ? 0 : 1);
		
		// Rinnovo numerazione (ogni quanti anni)
		input.setPeriodicitadianniin(bean.getNrAnniRinnovaNumerazione() !=null ? bean.getNrAnniRinnovaNumerazione() : null);
		
		// Anno inizio numerazione
		input.setAnnoinizionumerazionein((bean.getAnnoInizioNum() != null && !bean.getAnnoInizioNum().equalsIgnoreCase("")) ? Integer.parseInt(bean.getAnnoInizioNum()) : null);
		
		// 1° numero da dare
		input.setNroinizialein(bean.getNroIniziale() != null ? bean.getNroIniziale() : null);
		
		// Restrizioni
		//input.setul(value);(bean.getNroIniziale() != null ? bean.getNroIniziale() : null);
		
		// Selezionato in base alla U.O. mittente della registrazione
		input.setFlgctrabiluomittin(bean.getFlgCtrAbilUOMitt() != null && bean.getFlgCtrAbilUOMitt() ? new BigDecimal(1) : null);

		// Gruppo di registri
		input.setGruppoappin(bean.getGruppoRegistriApp() != null ? bean.getGruppoRegistriApp() : null);

		// Restrizioni (la concatenazione di U, E, I separati da ;) 
		String restrizioniVersoReg = "";
		if (bean.getFlgRestrizioniVersoRegE())
			restrizioniVersoReg=restrizioniVersoReg + "E;";
		
		if (bean.getFlgRestrizioniVersoRegU())
			restrizioniVersoReg=restrizioniVersoReg + "U;";
		
		if (bean.getFlgRestrizioniVersoRegI())
			restrizioniVersoReg=restrizioniVersoReg + "I;";
		
		input.setRestrizioniversoregin(restrizioniVersoReg);
		
		// Stato (A=aperto, C=chiuso)
		input.setFlgstatoregistroin(bean.getFlgStatoRegistro() != null ? bean.getFlgStatoRegistro() : "A");
		
		// Validità dal		
		input.setDtiniziovldin(bean.getDtInizioVld() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtInizioVld()) : null);
		
		// Validità al
		input.setDtfinevldin(bean.getDtFineVld() != null ?     new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFineVld())   : null);
		
		// per visualizzare doc. registrati nel registro
		input.setFlgrichabilxvisualizzin(bean.getFlgRichAbilVis() != null && bean.getFlgRichAbilVis() ? 1 : null);
		
		// per numerare nel registro
		input.setFlgrichabilxassegnin(bean.getFlgRichAbilXAssegn() != null && bean.getFlgRichAbilXAssegn() ? 1 : null);
				
		// Lista con i tipi di documenti ammessi (se FlgAmmEscXTipiDocIn=A)
		// -- (valori A/E) Indica se nel successivo argomento XMLTipiDocAmmEscOut saranno specificati i tipi di documenti ammessi (=A) o esclusi (=E) alla/dalla registrazione/numerazione
		input.setFlgammescxtipidocin("A");
			
		// -- (valori I/C) Indica se i tipi di documenti indicati nell'argomento successivo sono forniti in modo incrementale (=I) (solo quelle da inserire/modificare/cancellare) oppure completo (=C), vale a dire che dovranno soppiantare tutti quelli già specificati (e relativi al dominio di lavoro) in caso di tipo di registrazione/numerazione da aggiornare
		input.setFlgmodtipidocammesclin("C");
		input.setXmltipidocammescin(createXmlTipiDocAmmEsc(bean.getListaTipiDocAmmEsc()));
						
		// Eseguo il servizio
		DmpkTipiRegNumUdIutiporegnum dmpkTipiRegNumUdIutiporegnum = new DmpkTipiRegNumUdIutiporegnum();
		StoreResultBean<DmpkTipiRegNumUdIutiporegnumBean> result = dmpkTipiRegNumUdIutiporegnum.execute(getLocale(), loginBean, input);

		// Leggo il result
		if (StringUtils.isNotBlank(result.getResultBean().getWarningmsgout())) {
			addMessage(result.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		return bean;
	}

	@Override
	public RegistriNumerazioneBean remove(RegistriNumerazioneBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkTipiRegNumUdDtiporegnumBean input = new DmpkTipiRegNumUdDtiporegnumBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(new Integer(1));
		input.setIdtiporennumin(bean.getIdTipoRegNum() != null ? new BigDecimal(bean.getIdTipoRegNum()) : null);
		
		DmpkTipiRegNumUdDtiporegnum dmpkTipiRegNumUdDtiporegnum = new DmpkTipiRegNumUdDtiporegnum();
		StoreResultBean<DmpkTipiRegNumUdDtiporegnumBean> output = dmpkTipiRegNumUdDtiporegnum.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	private List<TipiDocAmmEscBean> getListaDocumentiAmmEsc (StoreResultBean<DmpkTipiRegNumUdLoaddetttiporegnumBean> output) {
		List<TipiDocAmmEscBean> listaDocAmmEsc = new ArrayList<>();
		if (output.getResultBean().getXmltipidocammescout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getXmltipidocammescout());
			try {
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						TipiDocAmmEscBean docBean = new TipiDocAmmEscBean();

						docBean.setIdTipoDocumento(v.get(0));
						docBean.setDescTipoDocumento(v.get(1));

						listaDocAmmEsc.add(docBean);
					}
				}
			} catch (JAXBException e) {
				log.error("Errore nell'unmarshaller", e);
			}
		}
		return listaDocAmmEsc;
	}
	
	public ListaRegistriNumerazioneBean aggiungiRegistroAUO(ListaRegistriNumerazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityGrantprivsudefcontestoBean input = new DmpkDefSecurityGrantprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));
		
		// FlgTpObjToGrantIn si passa TR per i registri
		input.setFlgtpobjtograntin("TR");
		
		// ListaPrivilegiIn si passa A;M;F per i registri
		input.setListaprivilegiin("A;M;F");
		
		Lista lista = new Lista();
		for (RegistriNumerazioneBean funzione : bean.getListaRegistriNumerazione()) {
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent(funzione.getIdTipoRegNum());
			riga.getColonna().add(col1);

			lista.getRiga().add(riga);
		}

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);
		input.setListaobjtograntxmlin(sw.toString());

		DmpkDefSecurityGrantprivsudefcontesto grantPrivSuDefContesto = new DmpkDefSecurityGrantprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityGrantprivsudefcontestoBean> output = grantPrivSuDefContesto.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}
	
	public ListaRegistriNumerazioneBean rimuoviRegistroDaUO(ListaRegistriNumerazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityRevokeprivsudefcontestoBean input = new DmpkDefSecurityRevokeprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));
		input.setListaprivilegiin("");

		Lista lista = new Lista();
		for (RegistriNumerazioneBean funzione : bean.getListaRegistriNumerazione()) {
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent("TR");
			riga.getColonna().add(col1);
			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));
			col2.setContent(funzione.getIdTipoRegNum());
			riga.getColonna().add(col2);
			lista.getRiga().add(riga);
		}

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);
		input.setListaobjtorevokexmlin(sw.toString());

		DmpkDefSecurityRevokeprivsudefcontesto revokePrivSuDefContesto = new DmpkDefSecurityRevokeprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityRevokeprivsudefcontestoBean> output = revokePrivSuDefContesto.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}
	
	
	protected DmpkTipiRegNumUdTrovatipiregnumBean createFetchInput(AdvancedCriteria criteria, String token, String idUserLavoro) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		
		// Inizializzo l'INPUT		
		DmpkTipiRegNumUdTrovatipiregnumBean input = new DmpkTipiRegNumUdTrovatipiregnumBean();
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {				
				if ("categoria".equals(crit.getFieldName())) {
					input.setCodcategoriaio(getTextFilterValue(crit));
				} else if ("sigla".equals(crit.getFieldName())) {
					input.setSiglaio(getTextFilterValue(crit));
				} else if ("descrizione".equals(crit.getFieldName())) {
					input.setDescrizioneio(getTextFilterValue(crit));
				} else if ("statoRegistro".equals(crit.getFieldName())) {
					input.setFlgstatoregistroio(getTextFilterValue(crit));
				} else if ("flgSoloValidi".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						input.setFlgsolovldio((new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(1) : new BigDecimal(0)));
					}
				}
			}
		}
		
		if (getExtraparams().get("callFrom") != null && getExtraparams().get("callFrom").equalsIgnoreCase("organigramma") ) {
			String flgStatoIn   = getExtraparams().get("flgStatoAbilitazioneIn");
			String flgTipDestIn = getExtraparams().get("flgTipologiaDestAbilIn");
			String idUO         = getExtraparams().get("idUo");
			String opzioniAbil  = getExtraparams().get("opzioniAbil");
			input.setFlgstatoabilin(flgStatoIn != null && flgStatoIn.length() > 0 ? Integer.decode(flgStatoIn) : null);
			input.setFlgtpdestabilin(flgTipDestIn);
			input.setIddestabilin(idUO != null && idUO.length() > 0 ? new BigDecimal(idUO) : null);
			input.setOpzioniabilin(opzioniAbil);
		}
		
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

	
	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT	
		DmpkTipiRegNumUdTrovatipiregnumBean input = createFetchInput(criteria, token, idUserLavoro);
		
		//non voglio overflow
		input.setOverflowlimitin(-1);
		
		// Inizializzo l'OUTPUT		
		DmpkTipiRegNumUdTrovatipiregnum lservice = new DmpkTipiRegNumUdTrovatipiregnum();
		StoreResultBean<DmpkTipiRegNumUdTrovatipiregnumBean> output = lservice.execute(getLocale(), loginBean, input);
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
		DmpkTipiRegNumUdTrovatipiregnumBean input = createFetchInput(criteria, token, idUserLavoro);
		input.setOverflowlimitin(-2);
		
		// Inizializzo l'OUTPUT
		DmpkTipiRegNumUdTrovatipiregnum lservice = new DmpkTipiRegNumUdTrovatipiregnum();
		StoreResultBean<DmpkTipiRegNumUdTrovatipiregnumBean> output = lservice.execute(getLocale(), loginBean, input);
				
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
				if(output.isInError()) {
					throw new StoreException(defaultMessage);		
				} 
		}		
		
		//imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), RegistriNumerazioneBean.class.getName());
		

		// salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), RegistriNumerazioneXmlBeanDeserializationHelper.class);

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
	
	protected String createXmlTipiDocAmmEsc(List<TipiDocAmmEscBean> listaTipiDocAmmEscIn) throws Exception {
		String xmlTipiDocAmmEsc = "";
		List<TipiDocAmmEscBean> listaTipiDocAmmEscOut = new ArrayList<TipiDocAmmEscBean>();
		if (listaTipiDocAmmEscIn!=null && listaTipiDocAmmEscIn.size()>0){
			for (TipiDocAmmEscBean lTipiDocAmmEscBean : listaTipiDocAmmEscIn) {
				listaTipiDocAmmEscOut.add(lTipiDocAmmEscBean);
			}
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlTipiDocAmmEsc = lXmlUtilitySerializer.bindXmlList(listaTipiDocAmmEscOut);
		return xmlTipiDocAmmEsc;
	}
}