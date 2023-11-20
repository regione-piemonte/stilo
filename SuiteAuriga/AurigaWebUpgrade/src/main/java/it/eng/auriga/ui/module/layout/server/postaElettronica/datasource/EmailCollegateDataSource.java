/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailDestinatarioBean;
import it.eng.aurigamailbusiness.bean.Dizionario;
import it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean;
import it.eng.aurigamailbusiness.bean.InterrogazioneRelazioneEmailBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TipoRelazione;
import it.eng.client.AurigaMailService;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "EmailCollegateDataSource")
public class EmailCollegateDataSource extends AbstractFetchDataSource<DettaglioEmailBean> {

	@Override
	public String getNomeEntita() {
		return "emailcollegate";
	}

	@Override
	public PaginatorBean<DettaglioEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		// leggo il paramatro di input
		String idEmail = StringUtils.isNotBlank(getExtraparams().get("idEmail")) ? getExtraparams().get("idEmail") : null;
		String tipoRel = StringUtils.isNotBlank(getExtraparams().get("tipoRel")) ? getExtraparams().get("tipoRel") : null;

		List<DettaglioEmailBean> data = new ArrayList<DettaglioEmailBean>();

		if ("risposta".equals(tipoRel)) {
			data = listaEmailCollegateFromDizionario(idEmail, tipoRel, Dizionario.RISPOSTA);
		} else if ("inoltro".equals(tipoRel)) {
			data = listaEmailCollegateFromDizionario(idEmail, tipoRel, Dizionario.INOLTRO);
		} else if ("notifica".equals(tipoRel)) {
			data.addAll(listaEmailCollegateFromDizionario(idEmail, tipoRel, Dizionario.NOTIFICA_INTEROPERABILE));
			data.addAll(listaEmailCollegateFromDizionario(idEmail, tipoRel, Dizionario.NOTIFICA_CONFERMA));
			data.addAll(listaEmailCollegateFromDizionario(idEmail, tipoRel, Dizionario.NOTIFICA_ECCEZIONE));
		}

		PaginatorBean<DettaglioEmailBean> lPaginatorBean = new PaginatorBean<DettaglioEmailBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());

		return lPaginatorBean;
	}

	public List<DettaglioEmailBean> listaEmailCollegateFromDizionario(String idEmail, String tipoRel, Dizionario dizionario) throws Exception {
		// Inizializzo l'INPUT
		List<DettaglioEmailBean> res = new ArrayList<DettaglioEmailBean>();

		InterrogazioneRelazioneEmailBean lInterrogazioneRelazioneEmailBean = new InterrogazioneRelazioneEmailBean();
		lInterrogazioneRelazioneEmailBean.setIdEmail(idEmail);
		TipoRelazione tipoRelazione = TipoRelazione.DIRETTA;
		lInterrogazioneRelazioneEmailBean.setTipoRelazione(tipoRelazione);
		lInterrogazioneRelazioneEmailBean.setDizionario(dizionario);
		EmailInfoRelazioniBean lEmailInfoRelazioniBean = AurigaMailService.getMailProcessorService().getrelazionimail(getLocale(),
				lInterrogazioneRelazioneEmailBean);

		if (lEmailInfoRelazioniBean.getRelazioni() != null && lEmailInfoRelazioniBean.getRelazioni().size() > 0) {
			for (TRelEmailMgoBean listTRelEmailMgoBean : lEmailInfoRelazioniBean.getRelazioni()) {
				String idEmail1 = listTRelEmailMgoBean.getIdEmail1();

				DettaglioEmailBean node = new DettaglioEmailBean();

				node.setIdEmail(idEmail1);
				node.setTipoRel(tipoRel);

				TFilterFetch<TDestinatariEmailMgoBean> filter = new TFilterFetch<TDestinatariEmailMgoBean>();
				TDestinatariEmailMgoBean lTDestinatariEmailMgoBean = new TDestinatariEmailMgoBean();
				lTDestinatariEmailMgoBean.setIdEmail(idEmail1);
				filter.setFilter(lTDestinatariEmailMgoBean);
				List<DettaglioEmailDestinatarioBean> lList = new ArrayList<DettaglioEmailDestinatarioBean>();
				List<DettaglioEmailDestinatarioBean> lListCC = new ArrayList<DettaglioEmailDestinatarioBean>();
				List<DettaglioEmailDestinatarioBean> lListCCn = new ArrayList<DettaglioEmailDestinatarioBean>();
				TPagingList<TDestinatariEmailMgoBean> result = AurigaMailService.getDaoTDestinatariEmailMgo().search(getLocale(), filter);
				if (result.getData() != null && result.getData().size() > 0) {
					for (TDestinatariEmailMgoBean lTDestinatariEmailMgoBeanRes : result.getData()) {
						DettaglioEmailDestinatarioBean lDestinatarioBean = new DettaglioEmailDestinatarioBean();
						lDestinatarioBean.setIdDestinatario(lTDestinatariEmailMgoBeanRes.getIdDestinatarioEmail());
						lDestinatarioBean.setStatoConsolidamento(lTDestinatariEmailMgoBeanRes.getStatoConsolidamento());
						lDestinatarioBean.setAccount(lTDestinatariEmailMgoBeanRes.getAccountDestinatario());
						if (lTDestinatariEmailMgoBeanRes.getFlgTipoDestinatario().equals("P")) {
							lList.add(lDestinatarioBean);
						} else if (lTDestinatariEmailMgoBeanRes.getFlgTipoDestinatario().equals("CC")) {
							lListCC.add(lDestinatarioBean);
						} else if (lTDestinatariEmailMgoBeanRes.getFlgTipoDestinatario().equals("CCN")) {
							lListCCn.add(lDestinatarioBean);
						}
					}
				}
				if (lListCCn.size() > 0) {
					node.setDestinatariCCn(lListCCn);
				}
				if (lListCC.size() > 0) {
					node.setDestinatariCC(lListCC);
				}
				node.setDestinatariPrincipali(lList);

				TEmailMgoBean lTEmailMgoBean = AurigaMailService.getDaoTEmailMgo().get(getLocale(), idEmail1);

				node.setFlgIo(lTEmailMgoBean.getFlgIo());
				node.setOggetto(lTEmailMgoBean.getOggetto());
				node.setAccountMittente(lTEmailMgoBean.getAccountMittente());
				node.setUriEmail(lTEmailMgoBean.getUriEmail());

				node.setTsInvio(lTEmailMgoBean.getTsInvio());
				node.setTsRicezione(lTEmailMgoBean.getTsRicezione());

				res.add(node);
			}
		}
		return res;
	}
}
