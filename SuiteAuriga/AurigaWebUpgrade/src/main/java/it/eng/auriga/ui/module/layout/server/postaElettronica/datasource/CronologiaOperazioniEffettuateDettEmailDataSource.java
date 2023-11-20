/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetlogemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.CronologiaOperazioniEffettuateDettEmailBean;
import it.eng.client.DmpkIntMgoEmailGetlogemail;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "CronologiaOperazioniEffettuateDettEmailDataSource")
public class CronologiaOperazioniEffettuateDettEmailDataSource extends AbstractFetchDataSource<CronologiaOperazioniEffettuateDettEmailBean> {

	private static Logger mLogger = Logger.getLogger(CronologiaOperazioniEffettuateDettEmailDataSource.class);

	@Override
	public PaginatorBean<CronologiaOperazioniEffettuateDettEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String idEmail = getExtraparams().get("idEmail");

		DmpkIntMgoEmailGetlogemailBean input = new DmpkIntMgoEmailGetlogemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdemailin(idEmail);
		input.setFlgincludiopfallitein(null);

		DmpkIntMgoEmailGetlogemail dmpkIntMgoEmailGetlogemail = new DmpkIntMgoEmailGetlogemail();
		StoreResultBean<DmpkIntMgoEmailGetlogemailBean> output = dmpkIntMgoEmailGetlogemail.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		List<CronologiaOperazioniEffettuateDettEmailBean> listaOperazioni = new ArrayList<CronologiaOperazioniEffettuateDettEmailBean>();
		if (output.getResultBean().getListalogout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListalogout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

					CronologiaOperazioniEffettuateDettEmailBean operazione = new CronologiaOperazioniEffettuateDettEmailBean();
					operazione.setIdEmailIn(idEmail);

					// colonna 1 dell'xml
					try{
						operazione.setTsOperazione(v.get(0) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP_WITH_SEC).parse(v.get(0)) : null);
					}catch(ParseException pe){
						operazione.setTsOperazione(v.get(0) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(0)) : null);
					}
					
					operazione.setProgressivoAzione(v.get(1) != null ? v.get(1) : ""); // colonna 2 dell'xml
					
					// l'ordinamento della colonna tsOperazione va fatto sul progressivo
					String tsOperazioneXOrd = v.get(1) != null ? v.get(1) : ""; // colonna 2 dell'xml
					int length = tsOperazioneXOrd.length();
					for(int n = 0; n < (10 - length); n++) {
						tsOperazioneXOrd = "0" + tsOperazioneXOrd;
					}
					operazione.setTsOperazioneXOrd(tsOperazioneXOrd != null ? tsOperazioneXOrd : null); 

					operazione.setIdUtenteOperazione(v.get(2) != null ? v.get(2) : ""); // colonna 3 dell'xml
					operazione.setDecodificaIdUtenteOperazione(v.get(3) != null ? v.get(3) : ""); // colonna 4 dell'xml

					operazione.setIdUtenteOperazioneSecondario(v.get(4) != null ? v.get(4) : ""); // colonna 5 dell'xml
					operazione.setDecodificaIdUtenteOperazioneSecondario(v.get(5) != null ? v.get(5) : ""); // colonna 6 dell'xml

					operazione.setTipoOperazione(v.get(6) != null ? v.get(6) : ""); // colonna 7 dell'xml
					operazione.setDecodificaTipoOperazione(v.get(7) != null ? v.get(7) : ""); // colonna 8 dell'xml

					operazione.setDettaglioOperazione(v.get(8) != null ? v.get(8) : ""); // colonna 9 dell'xml

					operazione.setEsitoOperazione(v.get(9) != null ? v.get(9) : ""); // colonna 10 dell'xml

					operazione.setMsgErrore(v.get(10) != null ? v.get(10) : ""); // colonna 11 dell'xml

					listaOperazioni.add(operazione);
				}
			}
		}

		PaginatorBean<CronologiaOperazioniEffettuateDettEmailBean> lPaginatorBean = new PaginatorBean<CronologiaOperazioniEffettuateDettEmailBean>();
		lPaginatorBean.setData(listaOperazioni);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? listaOperazioni.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(listaOperazioni.size());

		return lPaginatorBean;
	}

	@Override
	public CronologiaOperazioniEffettuateDettEmailBean get(CronologiaOperazioniEffettuateDettEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public CronologiaOperazioniEffettuateDettEmailBean remove(CronologiaOperazioniEffettuateDettEmailBean bean) throws Exception {
		
		return null;
	}

	@Override
	public CronologiaOperazioniEffettuateDettEmailBean add(CronologiaOperazioniEffettuateDettEmailBean bean) throws Exception {
		
		return bean;
	}

	@Override
	public CronologiaOperazioniEffettuateDettEmailBean update(CronologiaOperazioniEffettuateDettEmailBean bean,
			CronologiaOperazioniEffettuateDettEmailBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public Map<String, ErrorBean> validate(CronologiaOperazioniEffettuateDettEmailBean bean) throws Exception {
		
		return null;
	}
}
