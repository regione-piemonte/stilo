/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfGetlistaattflussoprocBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DatiProcXAtt;
import it.eng.client.DmpkWfGetlistaattflussoproc;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "GetListaAttFlussoProcDatasource")
public class GetListaAttFlussoProcDatasource extends AbstractDataSource<AttProcBean, AttProcBean> {

	private static Logger mLogger = Logger.getLogger(GetListaAttFlussoProcDatasource.class);

	@Override
	public PaginatorBean<AttProcBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String idProcess = (String) getExtraparams().get("idProcess");
		String idTipoProc = (String) getExtraparams().get("idTipoProc");
		String codGruppoAtt = (String) getExtraparams().get("codGruppoAtt");
		String idTipoEventoApp = (String) getExtraparams().get("idTipoEventoApp");

		DmpkWfGetlistaattflussoprocBean dmpkWfGetlistaattflussoprocInput = new DmpkWfGetlistaattflussoprocBean();
		dmpkWfGetlistaattflussoprocInput.setCodidconnectiontokenin(token);
		dmpkWfGetlistaattflussoprocInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		dmpkWfGetlistaattflussoprocInput.setIdprocessin(StringUtils.isNotBlank(idProcess) ? new BigDecimal(idProcess) : null);
		dmpkWfGetlistaattflussoprocInput.setCidgruppoattin(codGruppoAtt);
		dmpkWfGetlistaattflussoprocInput.setIdtipoeventoappin(StringUtils.isNotBlank(idTipoEventoApp) ? new BigDecimal(idTipoEventoApp) : null);
		dmpkWfGetlistaattflussoprocInput.setIdprocesstypein(StringUtils.isNotBlank(idTipoProc) ? new BigDecimal(idTipoProc) : null);

		DmpkWfGetlistaattflussoproc dmpkWfGetlistaattflussoproc = new DmpkWfGetlistaattflussoproc();
		StoreResultBean<DmpkWfGetlistaattflussoprocBean> dmpkWfGetlistaattflussoprocOutput = dmpkWfGetlistaattflussoproc.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), dmpkWfGetlistaattflussoprocInput);
		if (dmpkWfGetlistaattflussoprocOutput.isInError()) {
			throw new StoreException(dmpkWfGetlistaattflussoprocOutput);
		}

		DatiProcXAtt scDatiProcXAtt = null;
		if (StringUtils.isNotBlank(dmpkWfGetlistaattflussoprocOutput.getResultBean().getDatiprocxattout())) {
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			scDatiProcXAtt = lXmlUtilityDeserializer.unbindXml(dmpkWfGetlistaattflussoprocOutput.getResultBean().getDatiprocxattout(), DatiProcXAtt.class);
		}

		List<AttProcBean> listaAttivita = XmlListaUtility.recuperaLista(dmpkWfGetlistaattflussoprocOutput.getResultBean().getListaxmlout(), AttProcBean.class);
		boolean isFirstAS = true;
		if (listaAttivita != null) {
			for (AttProcBean attProc : listaAttivita) {
				attProc.setIdProcess(idProcess);
				if (StringUtils.isBlank(idProcess) && StringUtils.isNotBlank(idTipoProc)) {
					if (attProc.getTipoAttivita() != null && attProc.getTipoAttivita().equals("AS") && isFirstAS) {
						isFirstAS = false;
						if(attProc.getNome() != null && attProc.getNome().toLowerCase().startsWith("avvio")) {
							attProc.setFlgDatiVisibili("1");
							attProc.setFlgEseguibile("1");
							attProc.setFlgFatta("-1");
							attProc.setFlgEsito("");
							attProc.setIdGUIEvento("AVVIO_PROPOSTA_ATTO");
						}
					}
				}
				if (StringUtils.isNotBlank(attProc.getActivityName())) {
					attProc.setNome(attProc.getActivityName() + "|*|" + attProc.getNome());
				}
				if (scDatiProcXAtt != null) {
					attProc.setIdUnitaLocale(scDatiProcXAtt.getIdUnitaLocale());
					attProc.setNomeUnitaLocale(scDatiProcXAtt.getNomeUnitaLocale());
					attProc.setIdUbicazioneAreaIntervento(scDatiProcXAtt.getIdUbicazioneAreaIntervento());
					attProc.setIdUdIstanza(scDatiProcXAtt.getIdUdIstanza());
					attProc.setUriRicAvvio(scDatiProcXAtt.getUriRicAvvio());
					attProc.setEmailProponente(scDatiProcXAtt.getEmailProponente());
					attProc.setIdDefProcFlow(scDatiProcXAtt.getIdDefProcFlow());
					attProc.setIdInstProcFlow(scDatiProcXAtt.getIdInstProcFlow());
					attProc.setDesUoProponente(scDatiProcXAtt.getDesUoProponente());
					attProc.setNomeResponsabileUoProponente(scDatiProcXAtt.getNomeResponsabileUoProponente());
					attProc.setRifAttoInOrganigramma(scDatiProcXAtt.getRifAttoInOrganigramma());
					attProc.setListaNextTask(scDatiProcXAtt.getListaNextTask());	
					attProc.setAbilitaCallApplTitoliEdilizi(scDatiProcXAtt.getAbilitaCallApplTitoliEdilizi());
					attProc.setAnnoProtocolloIstanza(scDatiProcXAtt.getAnnoProtocolloIstanza());
					attProc.setNroProtocolloIstanza(scDatiProcXAtt.getNroProtocolloIstanza());
					attProc.setIdDocIstanza(scDatiProcXAtt.getIdDocIstanza());
				}
			}
		}

		return new PaginatorBean<AttProcBean>(listaAttivita);
	}

	@Override
	public AttProcBean get(AttProcBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AttProcBean add(AttProcBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AttProcBean remove(AttProcBean bean) throws Exception {
		
		return null;
	}

	@Override
	public AttProcBean update(AttProcBean bean, AttProcBean oldvalue) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AttProcBean bean) throws Exception {
		
		return null;
	}

}
