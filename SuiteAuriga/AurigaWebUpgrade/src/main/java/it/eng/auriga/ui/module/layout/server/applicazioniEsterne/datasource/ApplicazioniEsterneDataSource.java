/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDapplicazioneesternaBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIuapplicazioneesternaBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettapplicazioneesternaBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovaapplicazioneesternaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.applicazioniEsterne.datasource.bean.ApplEstBean;
import it.eng.client.DmpkDefSecurityDapplicazioneesterna;
import it.eng.client.DmpkDefSecurityIuapplicazioneesterna;
import it.eng.client.DmpkDefSecurityLoaddettapplicazioneesterna;
import it.eng.client.DmpkDefSecurityTrovaapplicazioneesterna;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author ottavio passalacqua
 *
 */

@Datasource(id = "ApplicazioniEsterneDataSource")
public class ApplicazioniEsterneDataSource extends AbstractFetchDataSource<ApplEstBean> {

	@Override
	public String getNomeEntita() {
		return "applicazioni_esterne";
	};

	@Override
	public PaginatorBean<ApplEstBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
	
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		BigDecimal flgIncludiAnnullati = null;
		String  nomeApplicazione = null;
		String  codIstanza = null;
		String  codApplicazione = null;
		Integer flgUsaCredenzialiProprie = null;
		Integer flgSenzaPaginazione = new Integer(1); // 1 : Lista non paginata

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("codApplicazione".equals(crit.getFieldName())) {
					codApplicazione = getTextFilterValue(crit);
				} else if ("codIstanza".equals(crit.getFieldName())) {
					codIstanza = getTextFilterValue(crit);
				} else if ("nomeApplicazione".equals(crit.getFieldName())) {
					nomeApplicazione = getTextFilterValue(crit);
				} else if ("flgUsaCredenzialiProprie".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {						
						flgUsaCredenzialiProprie = (String.valueOf(crit.getValue())).equalsIgnoreCase("true") ? 1 : 0;
					}	
				} else if ("flgIncludiAnnullati".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgIncludiAnnullati = new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(1) : new BigDecimal(0);
					}
				}
			}
		}

		// Inizializzo l'INPUT
		DmpkDefSecurityTrovaapplicazioneesternaBean input = new DmpkDefSecurityTrovaapplicazioneesternaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(null);              
		input.setFlgdescorderbyio(null);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(null);
		input.setBachsizeio(null);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		
		// Setto i filtri all'input del servizio
		input.setCodapplicazioneio(codApplicazione);
		input.setCodistanzaapplicazioneio(codIstanza);
		input.setDescrizioneapplistanzaio(nomeApplicazione);
		input.setFlgusacredenzialiproprieio(flgUsaCredenzialiProprie);
		input.setFlginclannullatiio(flgIncludiAnnullati);
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityTrovaapplicazioneesterna lDmpkDefSecurityTrovaapplicazioneesterna = new DmpkDefSecurityTrovaapplicazioneesterna();
		StoreResultBean<DmpkDefSecurityTrovaapplicazioneesternaBean> output = lDmpkDefSecurityTrovaapplicazioneesterna.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		List<ApplEstBean> data = new ArrayList<ApplEstBean>();
		
	    if (output.getResultBean().getListaxmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					ApplEstBean bean = new ApplEstBean();
					String idApplEsterna = v.get(0);
					if(v.get(1) != null && !v.get(1).equalsIgnoreCase("")){
						idApplEsterna = idApplEsterna + "|*|" + v.get(1);
					}
					bean.setIdApplEsterna(idApplEsterna);
					bean.setCodApplicazione(v.get(0));
					bean.setCodIstanza(v.get(1));
					bean.setNome(v.get(2));
					bean.setFlgUsaCredenzialiDiverse(v.get(3).equalsIgnoreCase("1") ? true : false);
					bean.setValido(v.get(4).equalsIgnoreCase("0") ? true : false);
					bean.setDtCensimento(v.get(7) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(7)) : null);
					bean.setUtenteCensimento(v.get(8));
					bean.setDtUltimoAggiornamento(v.get(9) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(9)) : null);
					bean.setUtenteUltimoAggiornamento(v.get(10));
					bean.setFlgSistema(false);
					data.add(bean);
				}
			}
		}
		PaginatorBean<ApplEstBean> lPaginatorBean = new PaginatorBean<ApplEstBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	@Override
	public ApplEstBean get(ApplEstBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityLoaddettapplicazioneesternaBean input = new DmpkDefSecurityLoaddettapplicazioneesternaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodapplicazionein(bean.getCodApplicazione());
		input.setCodistanzaapplicazionein(bean.getCodIstanza());
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityLoaddettapplicazioneesterna service = new DmpkDefSecurityLoaddettapplicazioneesterna();
		StoreResultBean<DmpkDefSecurityLoaddettapplicazioneesternaBean> output = service.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		ApplEstBean result = new ApplEstBean();
		result.setIdApplEsterna(bean.getIdApplEsterna());
		result.setCodApplicazione(bean.getCodApplicazione());
		result.setCodIstanza(bean.getCodIstanza());
		result.setNome(output.getResultBean().getDescrizioneapplistanzaout());
		result.setFlgUsaCredenzialiDiverse(output.getResultBean().getFlgusacredenzialiproprieout() != null && output.getResultBean().getFlgusacredenzialiproprieout() == 1 ? true : false);
		result.setValido(bean.getValido());
		result.setDtCensimento(bean.getDtCensimento());
		result.setUtenteCensimento(bean.getUtenteCensimento());
		result.setDtUltimoAggiornamento(bean.getDtUltimoAggiornamento());
		result.setUtenteUltimoAggiornamento(bean.getUtenteUltimoAggiornamento());
		result.setFlgSistema(bean.getFlgSistema());
		return result;
	}

	@Override
	public ApplEstBean add(ApplEstBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityIuapplicazioneesternaBean input = new DmpkDefSecurityIuapplicazioneesternaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodapplicazioneio(bean.getCodApplicazione());
		input.setCodistanzaapplicazioneio(bean.getCodIstanza());
		input.setDescrizioneapplistanzain(bean.getNome());
		input.setFlgusacredenzialipropriein(bean.getFlgUsaCredenzialiDiverse() != null && bean.getFlgUsaCredenzialiDiverse() ? new Integer(1) : new Integer(0));
		input.setFlgignorewarningin(new Integer(1));
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityIuapplicazioneesterna service = new DmpkDefSecurityIuapplicazioneesterna();
		StoreResultBean<DmpkDefSecurityIuapplicazioneesternaBean> result = service.execute(getLocale(), loginBean, input);
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
	public ApplEstBean update(ApplEstBean bean, ApplEstBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityIuapplicazioneesternaBean input = new DmpkDefSecurityIuapplicazioneesternaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodapplicazioneio(bean.getCodApplicazione());
		input.setCodistanzaapplicazioneio(bean.getCodIstanza());
		input.setDescrizioneapplistanzain(bean.getNome());
		input.setFlgusacredenzialipropriein(bean.getFlgUsaCredenzialiDiverse() != null && bean.getFlgUsaCredenzialiDiverse() ? new Integer(1) : new Integer(0));
		input.setFlgignorewarningin(new Integer(1));
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityIuapplicazioneesterna service = new DmpkDefSecurityIuapplicazioneesterna();
		StoreResultBean<DmpkDefSecurityIuapplicazioneesternaBean> result = service.execute(getLocale(), loginBean, input);
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
	public ApplEstBean remove(ApplEstBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Inizializzo l'INPUT
		DmpkDefSecurityDapplicazioneesternaBean input = new DmpkDefSecurityDapplicazioneesternaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodapplicazionein(bean.getCodApplicazione());
		input.setCodistanzaapplicazionein(bean.getCodIstanza());
		input.setFlgcancfisicain(null);
		
		// Inizializzo l'OUTPUT
		DmpkDefSecurityDapplicazioneesterna service = new DmpkDefSecurityDapplicazioneesterna();
		StoreResultBean<DmpkDefSecurityDapplicazioneesternaBean> result = service.execute(getLocale(), loginBean, input);
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
}