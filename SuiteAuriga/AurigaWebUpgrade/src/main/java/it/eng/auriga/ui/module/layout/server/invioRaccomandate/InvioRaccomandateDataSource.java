/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_raccomandate.bean.DmpkRaccomandateRic_raccomandateBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.invioRaccomandate.bean.InvioRaccomandateBean;
import it.eng.auriga.ui.module.layout.shared.invioRaccomandate.ETypePoste;
import it.eng.auriga.ui.module.layout.shared.invioRaccomandate.OperatoriPostelId;
import it.eng.client.DmpkRaccomandateRic_raccomandate;
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

@Datasource(id = "InvioRaccomandateDataSource")
public class InvioRaccomandateDataSource extends AbstractFetchDataSource<InvioRaccomandateBean>  {
	
	private static Logger mLogger = Logger.getLogger(InvioRaccomandateDataSource.class);

	@Override
	public PaginatorBean<InvioRaccomandateBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());
 
		List<InvioRaccomandateBean> data = new ArrayList<InvioRaccomandateBean>();
		
		boolean overflow = false;

		DmpkRaccomandateRic_raccomandateBean lFindRaccomandateObjectBean = createFindRaccomandateObjectBean(criteria, loginBean);
		
		DmpkRaccomandateRic_raccomandate lDmpkRaccomandateRicRaccomandate = new DmpkRaccomandateRic_raccomandate();
		StoreResultBean<DmpkRaccomandateRic_raccomandateBean> output = lDmpkRaccomandateRicRaccomandate.execute(getLocale(), lSchemaBean, lFindRaccomandateObjectBean);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage()) && !output.getDefaultMessage().contains("ORA-01461")) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output.getDefaultMessage());
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if (output.getResultBean().getResultout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getResultout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					InvioRaccomandateBean bean = new InvioRaccomandateBean();
					bean.setId_busta(v.get(0)); //Sarebbe l'id_raccomandata, id_busta è più generico e lo uso sia per le raccomandate che per le lettere
					bean.setIdPoste(v.get(2));
					bean.setNumeroProtocollo(v.get(3));					
					bean.setDataProtocollo(v.get(4) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(4)) : null);
					bean.setAnnoProtocollo(v.get(5));
					bean.setDataInvio(v.get(6) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(6)) : null);
					bean.setStatoLavorazioneRacc(v.get(7));
					bean.setDataAggiornamentoStato(v.get(8) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(8)) : null);
					bean.setDatiMittente(v.get(9));
					bean.setImportoTotale(v.get(10) != null ? Double.valueOf(v.get(10)) : null);
					bean.setImponibile(v.get(11) != null ? Double.valueOf(v.get(11)) : null);					
					bean.setIva(v.get(12));
					bean.setIdRicevuta(v.get(13));
					bean.setDestinatario(v.get(14));
					bean.setNroRaccomandata(v.get(15));
					bean.setNroLettera(v.get(16));
					bean.setEpm_key(v.get(17));
					bean.setEpm_ts(v.get(18) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(18)) : null);
					bean.setDataRaccomandata(v.get(18) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(18)) : null);
					if(v.get(19) != null && !"".equalsIgnoreCase(v.get(19))){
						if(ETypePoste.RACCOMANDATA.value().equalsIgnoreCase(v.get(19))) bean.setTipo(ETypePoste.RACCOMANDATA.name());
						if(ETypePoste.POSTA_PRIORITARIA.value().equalsIgnoreCase(v.get(19))) bean.setTipo(ETypePoste.POSTA_PRIORITARIA.name());
					}else {
						bean.setTipo(v.get(19));
					}
//					bean.setStatoVerifica(v.get(18));
		        	data.add(bean);
				}
			}
		}
		
		PaginatorBean<InvioRaccomandateBean> lPaginatorBean = new PaginatorBean<InvioRaccomandateBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);

		return lPaginatorBean;		
	}

	
	protected DmpkRaccomandateRic_raccomandateBean createFindRaccomandateObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {

		Date dtRaccomandataDal = null;
		Date dtRaccomandataAl = null;
		String nroRaccomandata = null;
		String nroLettera = null;
		String tipo = null;
		Date tsRegistrazioneDa = null;
		Date tsRegistrazioneA = null;
		Date tsInvioDa = null;
		Date tsInvioA = null;
		Date tsAggiornamentoStatoDa = null;
		Date tsAggiornamentoStatoA = null;
		String statoLavorazione = null;
		String idPoste = null;
		String criterio = "";

		DmpkRaccomandateRic_raccomandateBean lFindRaccomandateObjectBean = new DmpkRaccomandateRic_raccomandateBean();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		if (criteria != null && criteria.getCriteria() != null) {
			
			int i = 0;
			
			for (Criterion crit : criteria.getCriteria()) {
				
				if (i!=0) {
					criterio = criterio.concat("|£|");
				}
				
				if ("dtRaccomandata".equals(crit.getFieldName())) {
					String startDate = null;
					String endDate = null;
					criterio = criterio.concat("DATA_RACCOMANDATA");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					Date[] estremiDataRaccomandata = getDateFilterValuePoste(crit);
					tsInvioDa = estremiDataRaccomandata[0];
					if(tsInvioDa!=null) {
						startDate = format.format(tsInvioDa);
					}
					tsInvioA = estremiDataRaccomandata[1];
					if(tsInvioA!=null) {
						endDate = format.format(tsInvioA);
					}
					if (startDate != null && endDate != null && !startDate.equalsIgnoreCase(endDate)) {
						if (estremiDataRaccomandata[0].after(estremiDataRaccomandata[1])) throw new Exception("Intervallo di date non corretto");
						criterio = criterio.concat(startDate);
						criterio = criterio.concat("$");
						criterio = criterio.concat(endDate);
					}
					if (startDate != null && endDate == null) {
						criterio = criterio.concat(startDate);
					}
					if (startDate == null && endDate != null) {
						criterio = criterio.concat(endDate);
					}
					if (startDate != null && endDate != null && startDate.equalsIgnoreCase(endDate)) {
						criterio = criterio.concat(startDate);
					}
				} else if ("tipo".equals(crit.getFieldName())) {
					criterio = criterio.concat("TIPO");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					tipo = getTextFilterValue(crit);
					criterio = criterio.concat(tipo);
				} else if ("nroRaccomandata".equals(crit.getFieldName())) {
					criterio = criterio.concat("NUM_RACCOMANDATA");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					nroRaccomandata = getTextFilterValue(crit);
					criterio = criterio.concat(nroRaccomandata);
				} else if ("nroLettera".equals(crit.getFieldName())) {
					criterio = criterio.concat("NRO_LETTERA");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					nroLettera = getTextFilterValue(crit);
					criterio = criterio.concat(nroLettera);
				} else if ("tsRegistrazione".equals(crit.getFieldName())) {
					String startDate = null;
					String endDate = null;
					criterio = criterio.concat("DATA_PROTOCOLLO");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					Date[] estremiDataReg = getDateFilterValuePoste(crit);
					if (estremiDataReg[0] != null && estremiDataReg[1] != null) {
						tsRegistrazioneDa = estremiDataReg[0];
						startDate = format.format(tsRegistrazioneDa);
						criterio = criterio.concat(startDate);
						criterio = criterio.concat("$");
						tsRegistrazioneA = estremiDataReg[1];
						endDate = format.format(tsRegistrazioneA);
						criterio = criterio.concat(endDate);
					}
					if (estremiDataReg[0] != null && estremiDataReg[1] == null) {
						tsRegistrazioneDa = estremiDataReg[0];
						startDate = format.format(tsRegistrazioneDa);
						criterio = criterio.concat(startDate);
					}
					if (estremiDataReg[0] == null && estremiDataReg[1] != null) {
						tsRegistrazioneA = estremiDataReg[1];
						endDate = format.format(tsRegistrazioneA);
						criterio = criterio.concat(endDate);
					}
				} else if ("annoProt".equals(crit.getFieldName())) {
					criterio = criterio.concat("ANNO_PROTOCOLLO");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					String[] estremiAnnoProt = getNumberFilterValuePoste(crit);
					if (estremiAnnoProt[0] != null && estremiAnnoProt[1] != null && estremiAnnoProt[0] != estremiAnnoProt[1]) {
						criterio = criterio.concat(estremiAnnoProt[0]);
						criterio = criterio.concat("$");
						criterio = criterio.concat(estremiAnnoProt[1]);
					}
					if (estremiAnnoProt[0] != null && estremiAnnoProt[1] == null) {
						criterio = criterio.concat(estremiAnnoProt[0]);
					}
					if (estremiAnnoProt[0] == null && estremiAnnoProt[1] != null) {
						criterio = criterio.concat(estremiAnnoProt[1]);	
					}
					if (estremiAnnoProt[0] != null && estremiAnnoProt[1] != null && estremiAnnoProt[0] == estremiAnnoProt[1]) {
						criterio = criterio.concat(estremiAnnoProt[0]);
					}
				} else if ("nroProt".equals(crit.getFieldName())) {
					String[] estremiNroProt = getNumberFilterValuePoste(crit);
					criterio = criterio.concat("NUMERO_PROTOCOLLO");
					criterio = criterio.concat("|*|");
					if (estremiNroProt[0] != null && estremiNroProt[1] != null && estremiNroProt[0] == estremiNroProt[1]) {
						criterio = criterio.concat("5");
					} else {
						criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					}
					criterio = criterio.concat("|*|");
					if (estremiNroProt[0] != null && estremiNroProt[1] != null && estremiNroProt[0] != estremiNroProt[1]) {
						if (Integer.parseInt(estremiNroProt[0]) > Integer.parseInt(estremiNroProt[1])) throw new Exception("Intervallo di numeri non corretto");
						criterio = criterio.concat(estremiNroProt[0]);
						criterio = criterio.concat("$");
						criterio = criterio.concat(estremiNroProt[1]);
					}
					if (estremiNroProt[0] != null && estremiNroProt[1] != null && estremiNroProt[0] == estremiNroProt[1]) {
						criterio = criterio.concat(estremiNroProt[0]);
					}
					if (estremiNroProt[0] != null && estremiNroProt[1] == null) {
						criterio = criterio.concat(estremiNroProt[0]);
					}
					if (estremiNroProt[0] == null && estremiNroProt[1] != null) {
						criterio = criterio.concat(estremiNroProt[1]);
					}
				} else if ("statoLavorazioneRaccomandata".equals(crit.getFieldName())) {
					criterio = criterio.concat("STATO_DELLA_LAVORAZIONE");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					statoLavorazione = getTextFilterValue(crit);
					criterio = criterio.concat(statoLavorazione);
				} else if ("IdPoste".equals(crit.getFieldName())) {
					criterio = criterio.concat("IDPOSTE");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					idPoste = getTextFilterValue(crit);
					criterio = criterio.concat(idPoste);
				} else if ("dataLavorazione".equals(crit.getFieldName())) {
					String startDate = null;
					String endDate = null;
					criterio = criterio.concat("DATA_AGGIORNAMENTO_STATO");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					Date[] estremiAggiornamentoStato = getDateFilterValuePoste(crit);
					tsInvioDa = estremiAggiornamentoStato[0];
					if(tsInvioDa!=null) {
						startDate = format.format(tsInvioDa);
					}
					tsInvioA = estremiAggiornamentoStato[1];
					if(tsInvioA!=null) {
						endDate = format.format(tsInvioA);
					}
					if (startDate != null && endDate != null && !startDate.equalsIgnoreCase(endDate)) {
						if (estremiAggiornamentoStato[0].after(estremiAggiornamentoStato[1])) throw new Exception("Intervallo di date non corretto");
						criterio = criterio.concat(startDate);
						criterio = criterio.concat("$");
						criterio = criterio.concat(endDate);
					}
					if (startDate != null && endDate == null) {
						criterio = criterio.concat(startDate);
					}
					if (startDate == null && endDate != null) {
						criterio = criterio.concat(endDate);
					}
					if (startDate != null && endDate != null && startDate.equalsIgnoreCase(endDate)) {
						criterio = criterio.concat(startDate);
					}
				} else if ("dataInvio".equals(crit.getFieldName())) {
					String startDate = null;
					String endDate = null;
					criterio = criterio.concat("DATA_INVIO");
					criterio = criterio.concat("|*|");
					criterio = criterio.concat(findRelativeOperatorPerRaccomandate(crit.getOperator()));
					criterio = criterio.concat("|*|");
					Date[] estremiDataInvio = getDateFilterValuePoste(crit);
					tsInvioDa = estremiDataInvio[0];
					if(tsInvioDa!=null) {
						startDate = format.format(tsInvioDa);
					}
					tsInvioA = estremiDataInvio[1];
					if(tsInvioA!=null) {
						endDate = format.format(tsInvioA);
					}
					if (startDate != null && endDate != null && !startDate.equalsIgnoreCase(endDate)) {
						if (estremiDataInvio[0].after(estremiDataInvio[1])) throw new Exception("Intervallo di date non corretto");
						criterio = criterio.concat(startDate);
						criterio = criterio.concat("$");
						criterio = criterio.concat(endDate);
					}
					if (startDate != null && endDate == null) {
						criterio = criterio.concat(startDate);
					}
					if (startDate == null && endDate != null) {
						criterio = criterio.concat(endDate);
					}
					if (startDate != null && endDate != null && startDate.equalsIgnoreCase(endDate)) {
						criterio = criterio.concat(startDate);
					}
				}
				
				i++;
			}
		}

		lFindRaccomandateObjectBean.setV_ric(criterio);
		return lFindRaccomandateObjectBean;
	}
	
	protected String findRelativeOperatorPerRaccomandate(String value) {
		for (OperatoriPostelId lOperatorId : OperatoriPostelId.values()) {
			if (lOperatorId.name().toString().toLowerCase().equals(value.toLowerCase())) {
				return lOperatorId.getValue().toString();
			}
		}
		return OperatoriPostelId.EQUALS.getValue().toString();
	}
	
	public InvioRaccomandateBean isPossibleToPostel(InvioRaccomandateBean bean) throws Exception{
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());
		boolean isPossibleToPostel = false;
		
		String criterio = "ID_UD" + "|*|5|*|" + bean.getIdUd().toString() + "|£|" + "TIPO" + "|*|5|*|" + bean.getTipo();
	
		DmpkRaccomandateRic_raccomandateBean lDmpkRaccomandateRic_raccomandateBean = new DmpkRaccomandateRic_raccomandateBean();
		lDmpkRaccomandateRic_raccomandateBean.setV_ric(criterio);
		
		DmpkRaccomandateRic_raccomandate lDmpkRaccomandateRicRaccomandate = new DmpkRaccomandateRic_raccomandate();
		StoreResultBean<DmpkRaccomandateRic_raccomandateBean> output = lDmpkRaccomandateRicRaccomandate.execute(getLocale(), lSchemaBean, lDmpkRaccomandateRic_raccomandateBean);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output.getDefaultMessage());
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if (output.getResultBean().getResultout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getResultout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null && lista.getRiga().size() != 0) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					if ("FIXABLE".equalsIgnoreCase(v.get(7))) {
						isPossibleToPostel = true;
					}
				}
			}else {
				isPossibleToPostel = true;
			}
		}else {
			isPossibleToPostel = true;
		}
		
		bean.setIsDaPostalizzare(isPossibleToPostel);
		return bean;
	}
	
	
	protected String[] getNumberFilterValuePoste(Criterion criterion) throws Exception {
		String[] result = new String[2];
		result[0] = null;
		result[1] = null;
		if (criterion.getValue() != null) {
			if (criterion.getOperator().equals("equals")) {
				result[0] = (String) criterion.getValue();
				result[1] = (String) criterion.getValue();
			} else if (criterion.getOperator().equals("lessOrEqual")) {
				result[1] = (String) criterion.getValue();
			} else if (criterion.getOperator().equals("greaterOrEqual")) {
				result[0] = (String) criterion.getValue();
			} else if (criterion.getOperator().equals("lessThan")) {
				int lValue = Integer.valueOf((String) criterion.getValue());
				result[1] = Integer.toString(lValue);
			} else if (criterion.getOperator().equals("greaterThan")) {
				int lValue = Integer.valueOf((String) criterion.getValue());
				result[0] = Integer.toString(lValue);
			}
		} else if (criterion.getStart() != null || criterion.getEnd() != null) {
			if (criterion.getOperator().equals("betweenInclusive")) {
				if (criterion.getStart() != null) {
					result[0] = (String) criterion.getStart();
				}
				if (criterion.getEnd() != null) {
					result[1] = (String) criterion.getEnd();
				}
			}
		}
		return result;
	}
	
	protected Date[] getDateFilterValuePoste(Criterion criterion) throws Exception {
    	Date[] result = new Date[2];
    	if (criterion.getValue() != null && parseDate((String) criterion.getValue()) != null) {
    		if (criterion.getOperator().equals("equals")) {
    			GregorianCalendar start = new GregorianCalendar();
    			start.setTime(parseDate((String) criterion.getValue()));
    			start.set(Calendar.HOUR_OF_DAY, 0);
    			start.set(Calendar.MINUTE, 0);
    			start.set(Calendar.SECOND, 0);
    			start.set(Calendar.MILLISECOND, 0);
    			result[0] = start.getTime();
    			GregorianCalendar end = new GregorianCalendar();
    			end.setTime(parseDate((String) criterion.getValue()));
    			end.set(Calendar.HOUR_OF_DAY, 23);
    			end.set(Calendar.MINUTE, 59);
    			end.set(Calendar.SECOND, 59);
    			end.set(Calendar.MILLISECOND, 999);
    			result[1] = end.getTime();
    		} else if (criterion.getOperator().equals("lessOrEqual")) {
    			GregorianCalendar end = new GregorianCalendar();
    			end.setTime(parseDate((String) criterion.getValue()));
    			end.set(Calendar.HOUR_OF_DAY, 23);
    			end.set(Calendar.MINUTE, 59);
    			end.set(Calendar.SECOND, 59);
    			end.set(Calendar.MILLISECOND, 999);
    			result[1] = end.getTime();
    		} else if (criterion.getOperator().equals("greaterOrEqual")) {
    			GregorianCalendar start = new GregorianCalendar();
    			start.setTime(parseDate((String) criterion.getValue()));
    			start.set(Calendar.HOUR_OF_DAY, 0);
    			start.set(Calendar.MINUTE, 0);
    			start.set(Calendar.SECOND, 0);
    			start.set(Calendar.MILLISECOND, 0);
    			result[0] = start.getTime();
    		} else if (criterion.getOperator().equals("lessThan")) {
    			GregorianCalendar end = new GregorianCalendar();
    			end.setTime(parseDate((String) criterion.getValue()));
    			end.set(Calendar.HOUR_OF_DAY, 23);
    			end.set(Calendar.MINUTE, 59);
    			end.set(Calendar.SECOND, 59);
    			end.set(Calendar.MILLISECOND, 999);
    			end.add(Calendar.DAY_OF_YEAR, 0);
    			result[1] = end.getTime();
    		} else if (criterion.getOperator().equals("greaterThan")) {
    			GregorianCalendar start = new GregorianCalendar();
    			start.setTime(parseDate((String) criterion.getValue()));
    			start.set(Calendar.HOUR_OF_DAY, 0);
    			start.set(Calendar.MINUTE, 0);
    			start.set(Calendar.SECOND, 0);
    			start.set(Calendar.MILLISECOND, 0);
    			start.add(Calendar.DAY_OF_YEAR, 0);
    			result[0] = start.getTime();
    		}
    	} else if ((criterion.getStart() != null && parseDate((String) criterion.getStart()) != null) || (criterion.getEnd() != null  && parseDate((String) criterion.getEnd()) != null)) {
    		if (criterion.getOperator().equals("betweenInclusive")) {
    			if (criterion.getStart() != null && parseDate((String) criterion.getStart()) != null) {
    				String[] startStr = ((String) criterion.getStart()).split("T");
    				GregorianCalendar start = new GregorianCalendar();
    				start.setTime(parseDate(startStr[0]));
    				start.set(Calendar.HOUR_OF_DAY, 0);
    				start.set(Calendar.MINUTE, 0);
    				start.set(Calendar.SECOND, 0);
    				start.set(Calendar.MILLISECOND, 0);
    				result[0] = start.getTime();
    			}
    			if (criterion.getEnd() != null  && parseDate((String) criterion.getEnd()) != null) {
    				String[] endStr = ((String) criterion.getEnd()).split("T");
    				GregorianCalendar end = new GregorianCalendar();
    				end.setTime(parseDate(endStr[0]));
    				end.set(Calendar.HOUR_OF_DAY, 23);
    				end.set(Calendar.MINUTE, 59);
    				end.set(Calendar.SECOND, 59);
    				end.set(Calendar.MILLISECOND, 999);
    				result[1] = end.getTime();
    			}
    		}
    	}
    	return result;
    }

    
}
