/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovarichinvioemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.inviodoc.bean.InvioDocViaMailBean;
import it.eng.auriga.ui.module.layout.server.inviodoc.bean.InvioDocViaMailFilterSezioneCache;
import it.eng.client.DmpkIntMgoEmailTrovarichinvioemail;
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
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "RichiesteDocViaPecDataSource")
public class RichiesteDocViaPecDataSource extends AbstractFetchDataSource<InvioDocViaMailBean> {

	private static final Logger log = Logger.getLogger(RichiesteDocViaPecDataSource.class);

	@Override
	public String getNomeEntita() {
		return "invio_documentazione_via_pec";
	};

	@Override
	public InvioDocViaMailBean get(InvioDocViaMailBean bean) throws Exception {
		return null;
	}

	/**
	 * La maschera è in sola visualizzazione
	 */
	@Override
	public InvioDocViaMailBean add(InvioDocViaMailBean bean) throws Exception {
		return null;
	}

	/**
	 * La maschera è in sola visualizzazione
	 */
	@Override
	public InvioDocViaMailBean remove(InvioDocViaMailBean bean) throws Exception {
		return null;
	}

	/**
	 * La maschera è in sola visualizzazione
	 */
	@Override
	public InvioDocViaMailBean update(InvioDocViaMailBean bean, InvioDocViaMailBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<InvioDocViaMailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		List<InvioDocViaMailBean> data = new ArrayList<InvioDocViaMailBean>();

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;

		DmpkIntMgoEmailTrovarichinvioemailBean input = new DmpkIntMgoEmailTrovarichinvioemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		InvioDocViaMailFilterSezioneCache lInvioDocViaMailFilterSezioneCache = new InvioDocViaMailFilterSezioneCache();

		String includiSottoCartelle = "0";
		Integer searchAllTerms = null;
		String filtroFullText = null;
		String[] checkAttributes = null;
		Date dtSottRichDa = null;
		Date dtSottRichA = null;
		Date dtInvioEmailDa = null;
		Date dtInvioEmailA = null;

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("applicazioneRichiedente".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (StringUtils.isNotBlank(value)) {
						lInvioDocViaMailFilterSezioneCache.setCodApplRichiedenti(value);
					}
				} else if ("idRichiesta".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (StringUtils.isNotBlank(value)) {
						lInvioDocViaMailFilterSezioneCache.setIdProvRichiesta(value);
					}
				} else if ("statoRichiesta".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (StringUtils.isNotBlank(value)) {
						lInvioDocViaMailFilterSezioneCache.setStatiRichesta(value);
					}
				} else if ("statoEmail".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (StringUtils.isNotBlank(value)) {
						lInvioDocViaMailFilterSezioneCache.setStatiEmail(value);
					}				
				}
				else if ("xmlRichiesta".equals(crit.getFieldName())) {
						String value = getTextFilterValue(crit);
						if (StringUtils.isNotBlank(value)) {
							lInvioDocViaMailFilterSezioneCache.setXmlRichiestaSezCache(value);
						}						
				} 
				/*
				else if ("searchFulltext".equals(crit.getFieldName())) {
					// se sono entrato qui sono in modalità di ricerca con i filtri quindi imposto il valore di default a 1
					includiSottoCartelle = "1";
					if (crit.getValue() != null) {					
						Map map = (Map) crit.getValue();
						filtroFullText = (String) map.get("parole");
						ArrayList<String> lArrayList = (ArrayList<String>) map.get("attributi");
						checkAttributes = lArrayList != null ? lArrayList.toArray(new String[] {}) : null;
						Boolean flgRicorsiva = (Boolean) map.get("flgRicorsiva");
						if (flgRicorsiva != null) {
							includiSottoCartelle = flgRicorsiva ? "1" : "0";
						}
						String operator = crit.getOperator();
						if (StringUtils.isNotBlank(operator)) {
							if ("allTheWords".equals(operator)) {
								searchAllTerms = new Integer("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = new Integer("0");
							}
						}
						lInvioDocViaMailFilterSezioneCache.setXmlRichiestaSezCache(filtroFullText);
					}
				}
				*/
				// DATA SOTTOMISSIONE RICHIESTA
				else if ("dtSottRich".equals(crit.getFieldName())) {
					Date[] estremiSottRich = getDateConOraFilterValue(crit);
					if (estremiSottRich[0] != null) {
						lInvioDocViaMailFilterSezioneCache.setTsRichiestaDa(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(estremiSottRich[0]));
					}
					if (estremiSottRich[1] != null) {
						lInvioDocViaMailFilterSezioneCache.setTsRichiestaA(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(estremiSottRich[1]));
					}
				}
				// DATA INVIO EMAIL
				else if ("dtInvioEmail".equals(crit.getFieldName())) {
					Date[] estremiInvioEmail = getDateConOraFilterValue(crit);
					if (estremiInvioEmail[0] != null) {
						lInvioDocViaMailFilterSezioneCache.setTsInvioEmailDa(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(estremiInvioEmail[0]));
					}
					if (estremiInvioEmail[1] != null) {
						lInvioDocViaMailFilterSezioneCache.setTsInvioEmailA(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(estremiInvioEmail[1]));
					}
				}
			}
		}

		String lStrXml = lXmlUtilitySerializer.bindXml(lInvioDocViaMailFilterSezioneCache);
		input.setFiltriio(lStrXml);
		input.setFlgsenzapaginazionein(1);

		DmpkIntMgoEmailTrovarichinvioemail dmpkIntMgoEmailTrovarichinvioemail = new DmpkIntMgoEmailTrovarichinvioemail();
		StoreResultBean<DmpkIntMgoEmailTrovarichinvioemailBean> output = dmpkIntMgoEmailTrovarichinvioemail.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
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

					InvioDocViaMailBean lInvioDocViaMailBean = new InvioDocViaMailBean();

					lInvioDocViaMailBean.setId(v.get(0));
					lInvioDocViaMailBean.setIdRichiesta(v.get(1));
					lInvioDocViaMailBean.setCodApplRich(v.get(2));
					lInvioDocViaMailBean.setDescApplRich(v.get(3));
					lInvioDocViaMailBean.setTipoDocInv(v.get(4));
					lInvioDocViaMailBean.setDecodificaTipoDocInv(v.get(5));
					lInvioDocViaMailBean.setXmlRichiesta(v.get(6));
					lInvioDocViaMailBean.setStatoRichiesta(v.get(7));
					lInvioDocViaMailBean.setErrorMessage(v.get(8));
					lInvioDocViaMailBean.setDtSottRich(v.get(9) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(9)) : null);
					lInvioDocViaMailBean.setDtInvioEmail(v.get(10) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(10)) : null);
					lInvioDocViaMailBean.setIdEmailAss(v.get(11));
					lInvioDocViaMailBean.setStatoInvioMail(v.get(12));
					lInvioDocViaMailBean.setStatoAccettazioneMail(v.get(13));
					lInvioDocViaMailBean.setStatoConsegnaMail(v.get(14));
					lInvioDocViaMailBean.setDtUltimoAgg(v.get(15) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(15)) : null);

					data.add(lInvioDocViaMailBean);
				}
			}
		}

		PaginatorBean<InvioDocViaMailBean> lPaginatorBean = new PaginatorBean<InvioDocViaMailBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}
}
