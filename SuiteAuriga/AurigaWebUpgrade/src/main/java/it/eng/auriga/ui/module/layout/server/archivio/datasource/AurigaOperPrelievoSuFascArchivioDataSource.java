/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreOperprelievosufascarchivioBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.OperPrelievoSuFascArchivioBean;
import it.eng.client.DmpkCoreOperprelievosufascarchivio;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "AurigaOperPrelievoSuFascArchivioDataSource")
public class AurigaOperPrelievoSuFascArchivioDataSource extends AbstractDataSource<OperPrelievoSuFascArchivioBean, OperPrelievoSuFascArchivioBean>{
	
	@Override
	public OperPrelievoSuFascArchivioBean add(OperPrelievoSuFascArchivioBean bean) throws Exception {		
			
		boolean operazioneValida = false;
		if("REGISTRA_PRELIEVO".equalsIgnoreCase(bean.getCodOperPrelievoSuFascArchivio())){
			operazioneValida = true;
		}else if("MODIFICA_PRELIEVO".equalsIgnoreCase(bean.getCodOperPrelievoSuFascArchivio())){
			operazioneValida = true;
		}else if("ELIMINA_PRELIEVO".equalsIgnoreCase(bean.getCodOperPrelievoSuFascArchivio())){
			operazioneValida = true;
		}else if("REGISTRA_RESTITUZIONE".equalsIgnoreCase(bean.getCodOperPrelievoSuFascArchivio())){
			operazioneValida = true;
		}
		
		if (operazioneValida) {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();
			
			String xmlIn = creaSezioneCache(bean);
			
			DmpkCoreOperprelievosufascarchivioBean input = new DmpkCoreOperprelievosufascarchivioBean(); 
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdfolderin(StringUtils.isNotBlank(bean.getIdUdFolder()) ? new BigDecimal(bean.getIdUdFolder()) : null);
			input.setCodoperazionein(bean.getCodOperPrelievoSuFascArchivio());
			input.setMotiviin(bean.getMotiviPrelievoSuFascArchivio());
			input.setDatioperazionein(xmlIn);
			
			DmpkCoreOperprelievosufascarchivio dmpkCoreOperprelievosufascarchivio = new DmpkCoreOperprelievosufascarchivio();
			StoreResultBean<DmpkCoreOperprelievosufascarchivioBean> output = dmpkCoreOperprelievosufascarchivio.execute(getLocale(),loginBean, input); 
			
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					throw new StoreException(output);
				} else {
					bean.setSuccess(false);
					bean.setErrorMessage(output.getDefaultMessage());
				}
			}else {
				bean.setSuccess(true);
			}
		}else {
			bean.setSuccess(false);
			bean.setErrorMessage("Operazione non valida");
		}
		return bean;
		
	}	

	@Override
	public OperPrelievoSuFascArchivioBean get(OperPrelievoSuFascArchivioBean bean) throws Exception {
		return null;
	}

	@Override
	public OperPrelievoSuFascArchivioBean remove(OperPrelievoSuFascArchivioBean bean) throws Exception {
		return null;
	}

	@Override
	public OperPrelievoSuFascArchivioBean update(OperPrelievoSuFascArchivioBean bean, OperPrelievoSuFascArchivioBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<OperPrelievoSuFascArchivioBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(OperPrelievoSuFascArchivioBean bean) throws Exception {
		return null;
	}
	
	private String creaSezioneCache(OperPrelievoSuFascArchivioBean bean) throws Exception{
		SezioneCache lSezioneCache = new SezioneCache();

		if (bean.getDatiPrelievoDaArchivioDataPrelievo() != null) {
			Variabile var = new Variabile();
			var.setNome("#DataPrelievo");
			var.setValoreSemplice(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDatiPrelievoDaArchivioDataPrelievo()));
			lSezioneCache.getVariabile().add(var);
		}
		
		if (bean.getDatiPrelievoDaArchivioDataRestituzionePrelievo() != null) {
			Variabile var = new Variabile();
			var.setNome("#DataRestituzionePrelievo");
			var.setValoreSemplice(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDatiPrelievoDaArchivioDataRestituzionePrelievo()));
			lSezioneCache.getVariabile().add(var);
		}
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioCodUO())) {
			Variabile var = new Variabile();
			var.setNome("#CodUOPrelievo");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioCodUO());
			lSezioneCache.getVariabile().add(var);
		}
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioDesUO())) {
			Variabile var = new Variabile();
			var.setNome("#DesUOPrelievo");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioDesUO());
			lSezioneCache.getVariabile().add(var);
		}
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioIdUO())) {
			Variabile var = new Variabile();
			var.setNome("#IdUOPrelievo");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioIdUO());
			lSezioneCache.getVariabile().add(var);
		}
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioCognomeResponsabile())) {
			Variabile var = new Variabile();
			var.setNome("#CognomeRespPrelievo");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioCognomeResponsabile());
			lSezioneCache.getVariabile().add(var);
		}
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioNomeResponsabile())) {
			Variabile var = new Variabile();
			var.setNome("#NomeRespPrelievo");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioNomeResponsabile());
			lSezioneCache.getVariabile().add(var);
		}
		
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioIdUserResponsabile())) {
			Variabile var = new Variabile();
			var.setNome("#IdUserRespPrelievo");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioIdUserResponsabile());
			lSezioneCache.getVariabile().add(var);
		}
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioUsernameResponsabile())) {
			Variabile var = new Variabile();
			var.setNome("#UsernameRespPrelievo ");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioUsernameResponsabile());
			lSezioneCache.getVariabile().add(var);
		}
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioNoteRichiedente())) {
			Variabile var = new Variabile();
			var.setNome("#NoteRichiedente");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioNoteRichiedente());
			lSezioneCache.getVariabile().add(var);
		}
		
		if (StringUtils.isNotBlank(bean.getDatiPrelievoDaArchivioNoteArchivio())) {
			Variabile var = new Variabile();
			var.setNome("#NoteArchivio");
			var.setValoreSemplice(bean.getDatiPrelievoDaArchivioNoteArchivio());
			lSezioneCache.getVariabile().add(var);
		}

		String xmlSezioneCacheAttributiAdd = null;
		if (lSezioneCache != null) {
			StringWriter lStringWriter = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			lMarshaller.marshal(lSezioneCache, lStringWriter);
			xmlSezioneCacheAttributiAdd = lStringWriter.toString();
		}

		return xmlSezioneCacheAttributiAdd;
	}
	
	
}
