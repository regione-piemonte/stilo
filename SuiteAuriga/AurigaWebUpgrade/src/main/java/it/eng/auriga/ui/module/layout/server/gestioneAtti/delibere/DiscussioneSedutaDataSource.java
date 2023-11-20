/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollAnnarchiviasedutaBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollGetdiscussionesedutaBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollGetpresenzevotisucontodgBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollSavediscussionesedutaBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollSavepresenzevotisucontodgBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollSimulanumerazioneattisedutaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.firmamultipla.bean.FirmaMassivaFilesBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ArgomentiOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.CommissioniXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.DiscussioneSedutaBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.EsitiXTipoArgomentoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.FirmatarioXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.InfoConvocazioneXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.OdgInfoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.OperazioneMassivaArgomentiOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.PresenzeDichiarazioniVotiOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.PresenzeOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.PresenzeVotiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.VerbaleInfoXmlBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.CallExecAttDatasource;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ImpostazioniUnioneFileBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.UnioneFileAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.NuovaPropostaAtto2CompletaDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkSeduteOrgCollAnnarchiviaseduta;
import it.eng.client.DmpkSeduteOrgCollGetdiscussioneseduta;
import it.eng.client.DmpkSeduteOrgCollGetpresenzevotisucontodg;
import it.eng.client.DmpkSeduteOrgCollSavediscussioneseduta;
import it.eng.client.DmpkSeduteOrgCollSavepresenzevotisucontodg;
import it.eng.client.DmpkSeduteOrgCollSimulanumerazioneattiseduta;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoDocumenti;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FirmaUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="DiscussioneSedutaDataSource")
public class DiscussioneSedutaDataSource extends AbstractServiceDataSource<DiscussioneSedutaBean,DiscussioneSedutaBean> {

	private static Logger mLogger = Logger.getLogger(DiscussioneSedutaDataSource.class);
	
	@Override
	public DiscussioneSedutaBean call(DiscussioneSedutaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkSeduteOrgCollGetdiscussionesedutaBean input = new DmpkSeduteOrgCollGetdiscussionesedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setOrganocollegialein(pInBean.getOrganoCollegiale());
		input.setIdsedutaio(pInBean.getIdSeduta() != null ? pInBean.getIdSeduta() : null);
		/**
		 * (obblig. OrganoCollegialeIn = commessione) Lista con gli ID delle commissioni in seduta
		 */
		input.setListacommissioniin(pInBean.getListaCommissioni());
		
		DmpkSeduteOrgCollGetdiscussioneseduta dmpkSeduteOrgCollGetdiscussioneseduta = new DmpkSeduteOrgCollGetdiscussioneseduta();
		StoreResultBean<DmpkSeduteOrgCollGetdiscussionesedutaBean> output = dmpkSeduteOrgCollGetdiscussioneseduta.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		DiscussioneSedutaBean lDiscussioneSedutaBean = new DiscussioneSedutaBean();
		lDiscussioneSedutaBean.setIdSeduta(output.getResultBean().getIdsedutaio());
		lDiscussioneSedutaBean.setNumero(output.getResultBean().getNrosedutaout() != null ? String.valueOf(output.getResultBean().getNrosedutaout()) : null);
		lDiscussioneSedutaBean.setDtPrimaConvocazione(output.getResultBean().getDataora1aconvocazioneout() != null ? 
				new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getDataora1aconvocazioneout()) : null);
		lDiscussioneSedutaBean.setLuogoPrimaConvocazione(output.getResultBean().getLuogo1aconvocazioneout());	
		lDiscussioneSedutaBean.setDtSecondaConvocazione(output.getResultBean().getDataora2aconvocazioneout() != null ? 
				new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getDataora2aconvocazioneout()) : null);
		lDiscussioneSedutaBean.setLuogoSecondaConvocazione(output.getResultBean().getLuogo2aconvocazioneout());	
		
		lDiscussioneSedutaBean.setStato(output.getResultBean().getStatosedutaout());
		String statoSedutaOut = output.getResultBean().getStatosedutaout();
		if(StringUtils.isNotBlank(statoSedutaOut)) {
			statoSedutaOut = statoSedutaOut.replaceAll("_", " ");
			lDiscussioneSedutaBean.setDesStato(statoSedutaOut);
		}
		
		lDiscussioneSedutaBean.setTipoSessione(output.getResultBean().getTiposessioneout());
		
		if(StringUtils.isNotBlank(output.getResultBean().getOdginfoout())) {
			OdgInfoXmlBean lOdgInfoBean = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getOdginfoout(), OdgInfoXmlBean.class);
			lDiscussioneSedutaBean.setDisattivatoRiordinoAutomatico(lOdgInfoBean.getDisattivatoRiordinoAutomatico() != null ?
					lOdgInfoBean.getDisattivatoRiordinoAutomatico() : null);
			lDiscussioneSedutaBean.setOdgInfo(lOdgInfoBean);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getArgomentiodgout())) {
			List<ArgomentiOdgXmlBean> listaArgomentiOdg = XmlListaUtility.recuperaLista(output.getResultBean().getArgomentiodgout(), ArgomentiOdgXmlBean.class);
			lDiscussioneSedutaBean.setListaArgomentiOdg(listaArgomentiOdg);
		}
		
		// Appiattisco dati prima convocazione
		if(StringUtils.isNotBlank(output.getResultBean().getInfo1aconvocazioneout())) {
			InfoConvocazioneXmlBean infoPrimaConvocazioneBean = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getInfo1aconvocazioneout(), InfoConvocazioneXmlBean.class);
			
			lDiscussioneSedutaBean.setVerbaleInfoPrimaConvocazione(createVerbaleInfoXmlBeanFromInfoConvocazioneXmlBean(null, infoPrimaConvocazioneBean));
			
			lDiscussioneSedutaBean.setDatiDiscPrimaConvDtInizioLavori(infoPrimaConvocazioneBean.getDtInizioLavori());
			lDiscussioneSedutaBean.setDatiDiscPrimaConvDtFineLavori(infoPrimaConvocazioneBean.getDtFineLavori());
			lDiscussioneSedutaBean.setListaDatiDiscPrimaConvPresenzeAppelloIniziale(infoPrimaConvocazioneBean.getPresenzeAppelloIniziale());
			if(lDiscussioneSedutaBean.getListaDatiDiscPrimaConvPresenzeAppelloIniziale() != null) {
				for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : lDiscussioneSedutaBean.getListaDatiDiscPrimaConvPresenzeAppelloIniziale()) {
					if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getIdDelegato())) {
						lPresenzeOdgXmlBean.setDelegato(lPresenzeOdgXmlBean.getIdDelegato() + "|*|" + lPresenzeOdgXmlBean.getDecodificaDelegato());
					}
				}
			}
			
			lDiscussioneSedutaBean.setDatiDiscPrimaConvEditorVerbale(infoPrimaConvocazioneBean.getVerbaleTestoHtml());
			
			// TODEL Verificare cosa mettere
//			lDiscussioneSedutaBean.setDatiDiscPrimaConvEditorVerbale()
			
//			FileVerbaleSedutaBean datiFileVerbalePrimaConvocazione = createFileVerbaleSedutaBean(infoPrimaConvocazioneBean);			
//			lDiscussioneSedutaBean.setFileVerbalePrimaConvocazione(datiFileVerbalePrimaConvocazione);			
		}
		
		// Appiattisco dati seconda convocazione
		if(StringUtils.isNotBlank(output.getResultBean().getInfo2aconvocazioneout())) {
			InfoConvocazioneXmlBean infoSecondaConvocazioneBean = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getInfo2aconvocazioneout(), InfoConvocazioneXmlBean.class);
			
			lDiscussioneSedutaBean.setVerbaleInfoSecondaConvocazione(createVerbaleInfoXmlBeanFromInfoConvocazioneXmlBean(null, infoSecondaConvocazioneBean));
			
			lDiscussioneSedutaBean.setDatiDiscSecondaConvDtInizioLavori(infoSecondaConvocazioneBean.getDtInizioLavori());
			lDiscussioneSedutaBean.setDatiDiscSecondaConvDtFineLavori(infoSecondaConvocazioneBean.getDtFineLavori());
			lDiscussioneSedutaBean.setListaDatiDiscSecondaConvPresenzeAppelloIniziale(infoSecondaConvocazioneBean.getPresenzeAppelloIniziale());
			if(lDiscussioneSedutaBean.getListaDatiDiscSecondaConvPresenzeAppelloIniziale() != null) {
				for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : lDiscussioneSedutaBean.getListaDatiDiscSecondaConvPresenzeAppelloIniziale()) {
					if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getIdDelegato())) {
						lPresenzeOdgXmlBean.setDelegato(lPresenzeOdgXmlBean.getIdDelegato() + "|*|" + lPresenzeOdgXmlBean.getDecodificaDelegato());
					}
				}
			}
			
			lDiscussioneSedutaBean.setDatiDiscSecondaConvEditorVerbale(infoSecondaConvocazioneBean.getVerbaleTestoHtml());
			
			// TODEL Verificare cosa mettere
//			lDiscussioneSedutaBean.setDatiDiscPrimaConvEditorVerbale()
			
//			FileVerbaleSedutaBean datiFileVerbaleSecondaConvocazione = createFileVerbaleSedutaBean(infoSecondaConvocazioneBean);			
//			lDiscussioneSedutaBean.setFileVerbaleSecondaConvocazione(datiFileVerbaleSecondaConvocazione);			
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getEsitixtipoargomentoout())) {
			List<EsitiXTipoArgomentoXmlBean> lEsitiXTipoArgomentoBean = XmlListaUtility.recuperaLista(output.getResultBean().getEsitixtipoargomentoout(), EsitiXTipoArgomentoXmlBean.class);
			lDiscussioneSedutaBean.setEsitiXtipoArgomento(lEsitiXTipoArgomentoBean);
		}
		
		// Estraggo la lista delle commissioni convocate
		if (lDiscussioneSedutaBean.getOdgInfo()!=null){
			if (lDiscussioneSedutaBean.getOdgInfo().getListaCommissioniConvocate()!=null && !lDiscussioneSedutaBean.getOdgInfo().getListaCommissioniConvocate().isEmpty()){
				String listaCommissioniConvocate = "";
				String listaCommissioniConvocateId = "";
				for(CommissioniXmlBean item : lDiscussioneSedutaBean.getOdgInfo().getListaCommissioniConvocate()) {
					if(item != null && item.getNomeCommissione() != null && !"".equals(item.getNomeCommissione())) {
						listaCommissioniConvocate   += item.getNomeCommissione() + "; \r\n";
						listaCommissioniConvocateId += item.getIdCommissione() + "; ";
					}
				}
				// Lista dei nomi delle commissioni convocate
				lDiscussioneSedutaBean.setListaNomiCommissioniConvocate(listaCommissioniConvocate);
				
				// Lista dei ID delle commissioni convocate
				lDiscussioneSedutaBean.setListaIdCommissioniConvocate(listaCommissioniConvocateId);
			}
		}
				
		return lDiscussioneSedutaBean;
	}
	
	public DiscussioneSedutaBean annullaSeduta(DiscussioneSedutaBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkSeduteOrgCollAnnarchiviasedutaBean input = new  DmpkSeduteOrgCollAnnarchiviasedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(bean.getIdSeduta());
		input.setAzionein("ANN");
		input.setMotiviin(bean.getMotivoAnnullamento());
		
		DmpkSeduteOrgCollAnnarchiviaseduta dmpkSeduteOrgCollAnnarchiviaseduta = new DmpkSeduteOrgCollAnnarchiviaseduta();
		StoreResultBean<DmpkSeduteOrgCollAnnarchiviasedutaBean> output = dmpkSeduteOrgCollAnnarchiviaseduta.execute(getLocale(), loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		return bean;
	}
	
	public DiscussioneSedutaBean archiviaSeduta(DiscussioneSedutaBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkSeduteOrgCollAnnarchiviasedutaBean input = new  DmpkSeduteOrgCollAnnarchiviasedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(bean.getIdSeduta());
		input.setAzionein("ARCH");
		
		DmpkSeduteOrgCollAnnarchiviaseduta dmpkSeduteOrgCollAnnarchiviaseduta = new DmpkSeduteOrgCollAnnarchiviaseduta();
		StoreResultBean<DmpkSeduteOrgCollAnnarchiviasedutaBean> output = dmpkSeduteOrgCollAnnarchiviaseduta.execute(getLocale(), loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		return bean;
	}
	
	public FirmaMassivaFilesBean getListaPerFirmaAttiAdottati(OperazioneMassivaArgomentiOdgXmlBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> lListaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			String idUd = bean.getListaRecord().get(i).getIdUd();
			// Recupero il file primario e gli allegati dell'unità documentale
			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = new RecuperoDocumenti().loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
			if(lRecuperaDocumentoOutBean.isInError()) {
				throw new StoreException(lRecuperaDocumentoOutBean);
			}
			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			ProtocollazioneBean lProtocollazioneBean = new ProtocolloUtility(getSession()).getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());
			// File primario
			if (StringUtils.isNotBlank(lProtocollazioneBean.getUriFilePrimario())) {
				FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
				lFileDaFirmareBean.setIdFile(lProtocollazioneBean.getIdDocPrimario() != null ? String.valueOf(lProtocollazioneBean.getIdDocPrimario()) : null);
				lFileDaFirmareBean.setNomeFile(lProtocollazioneBean.getNomeFilePrimario());
				lFileDaFirmareBean.setUri(lProtocollazioneBean.getUriFilePrimario());
				lFileDaFirmareBean.setInfoFile(lProtocollazioneBean.getInfoFile());
				lFileDaFirmareBean.setIdUd(idUd);
				lFileDaFirmareBean.setCodiceTipoRelazione("P");
				lFileDaFirmareBean.setIsFilePrincipaleAtto(true);
				lListaFileDaFirmare.add(lFileDaFirmareBean);
			}	
			// File primario omissis
			if(lProtocollazioneBean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(lProtocollazioneBean.getFilePrimarioOmissis().getUriFile())) {
				FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
				lFileDaFirmareBeanOmissis.setIdFile(lProtocollazioneBean.getFilePrimarioOmissis().getIdDoc() != null ? String.valueOf(lProtocollazioneBean.getFilePrimarioOmissis().getIdDoc()) : null);
				lFileDaFirmareBeanOmissis.setNomeFile(lProtocollazioneBean.getFilePrimarioOmissis().getNomeFile());
				lFileDaFirmareBeanOmissis.setUri(lProtocollazioneBean.getFilePrimarioOmissis().getUriFile());
				lFileDaFirmareBeanOmissis.setInfoFile(lProtocollazioneBean.getFilePrimarioOmissis().getInfoFile());
				lFileDaFirmareBeanOmissis.setIdUd(idUd);
				lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("P");
				lFileDaFirmareBeanOmissis.setIsFilePrincipaleAtto(true);
				lListaFileDaFirmare.add(lFileDaFirmareBeanOmissis);
			}
			// File allegati parte integrante			
			if (lProtocollazioneBean.getListaAllegati() != null) {
				for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()) {
					if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {						
						if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
							if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())){
								FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
								lFileDaFirmareBean.setIdFile(lAllegatoProtocolloBean.getIdDocAllegato() != null ? String.valueOf(lAllegatoProtocolloBean.getIdDocAllegato()) : null);
								lFileDaFirmareBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
								lFileDaFirmareBean.setUri(lAllegatoProtocolloBean.getUriFileAllegato());
								lFileDaFirmareBean.setInfoFile(lAllegatoProtocolloBean.getInfoFile());
								lFileDaFirmareBean.setIdUd(idUd);
								lFileDaFirmareBean.setCodiceTipoRelazione("ALL");
								lListaFileDaFirmare.add(lFileDaFirmareBean);
							}
						}
						if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())) {
							if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())) {								
								FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
								lFileDaFirmareBeanOmissis.setIdFile(lAllegatoProtocolloBean.getIdDocOmissis() != null ? String.valueOf(lAllegatoProtocolloBean.getIdDocOmissis()) : null);
								lFileDaFirmareBeanOmissis.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
								lFileDaFirmareBeanOmissis.setUri(lAllegatoProtocolloBean.getUriFileOmissis());
								lFileDaFirmareBeanOmissis.setInfoFile(lAllegatoProtocolloBean.getInfoFileOmissis());
								lFileDaFirmareBeanOmissis.setIdUd(idUd);
								lFileDaFirmareBeanOmissis.setCodiceTipoRelazione("ALL");
								lListaFileDaFirmare.add(lFileDaFirmareBeanOmissis);
							}
						}
					}
				}
			}
			
		}		
		lFirmaMassivaFilesBean.setFiles(lListaFileDaFirmare);		
		GenericConfigBean config = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		String prefTipoFirma = getExtraparams().get("prefTipoFirma");
		if(prefTipoFirma != null && !"".equals(prefTipoFirma)) {
			if("C".equalsIgnoreCase(prefTipoFirma)) {
				if(config.getMaxNroDocFirmaMassivaClient() >= 1) {
					if( lListaFileDaFirmare.size() > config.getMaxNroDocFirmaMassivaClient()){
						lListaFileDaFirmare.clear();
						addMessage("Selezionato per firma un n.ro di documenti superiore al massimo consentito di "
								+ config.getMaxNroDocFirmaMassivaClient(), "", MessageType.ERROR);
					}
				}
			} else if("R".equalsIgnoreCase(prefTipoFirma)) {
				if(config.getMaxNroDocFirmaMassivaRemotaNonAuto() >= 1) {
					if(lListaFileDaFirmare.size() > config.getMaxNroDocFirmaMassivaRemotaNonAuto()){
						lListaFileDaFirmare.clear();
						addMessage("Selezionato per firma un n.ro di documenti superiore al massimo consentito di "
								+ config.getMaxNroDocFirmaMassivaRemotaNonAuto(), "", MessageType.ERROR);
					}
				}
			} 
//			else  if("A".equalsIgnoreCase(prefTipoFirma)) {
//				
//			}
		}		
		return lFirmaMassivaFilesBean;
	}
	
	private boolean skipFirmaAllegatiFirmati(String uriFile, MimeTypeFirmaBean lInfoFile) throws Exception {		
		// Se ESCLUDI_FIRMA_ALLEGATI_FIRMATI_PI_ATTO non è presente o è false firmo sempre
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_ALLEGATI_FIRMATI_PI_ATTO")) {
			// Se il file non è firmato lo firmo sempre
			if (lInfoFile != null && lInfoFile.isFirmato()) {
				boolean presentiFirmeExtraAuriga = lInfoFile.getInfoFirmaMarca().isFirmeExtraAuriga();
				if (presentiFirmeExtraAuriga) {
					// Se il file è firmato extra auriga lo rifirmo solamente se ha firma non valida
					boolean firmaValida = lInfoFile.isFirmaValida();
					if (firmaValida) {
						// La firma è valida e non sono presenti firme extra Auriga
						return true;
					}
				}
			}
		}	
		
		return false;
	}
	
	public FirmaMassivaFilesBean aggiornaDocumentoFirmato(FirmaMassivaFilesBean pFirmaMassivaFilesBean) throws Exception {
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> filesInError = new ArrayList<FileDaFirmareBean>();
		HashMap<String, String> errors = new HashMap<String, String>();
		for (FileDaFirmareBean lFileDaFirmareBean : pFirmaMassivaFilesBean.getFiles()) {
			try {
				String modalitaFirma = ParametriDBUtil.getParametroDB(getSession(), "MODALITA_FIRMA");
				String appletFirmaSpec = ParametriDBUtil.getParametroDB(getSession(), "APPLET_FIRMA_SPEC");
				if ((StringUtils.isBlank(modalitaFirma) || modalitaFirma.equalsIgnoreCase("APPLET")) && StringUtils.isBlank(appletFirmaSpec)) {
					// aggiungo l'estensione p7m al nome del file firmato perchè l'applet di firma multipla non lo fa
					boolean isCAdES = lFileDaFirmareBean.getInfoFile() != null && lFileDaFirmareBean.getInfoFile().isFirmato() && lFileDaFirmareBean.getInfoFile().getTipoFirma().startsWith("CAdES");
					if (isCAdES && !lFileDaFirmareBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
						lFileDaFirmareBean.setNomeFile(lFileDaFirmareBean.getNomeFile() + ".p7m");
					}
				}
				boolean firmaEseguita = lFileDaFirmareBean.getFirmaEseguita() != null && lFileDaFirmareBean.getFirmaEseguita();
				if(firmaEseguita) {
					versionaDocumento(lFileDaFirmareBean);
				} else {
					throw new Exception("Firma non presente o non valida");
				}
			} catch (Exception e) {
				errors.put(lFileDaFirmareBean.getIdFile(), e.getMessage());
				filesInError.add(lFileDaFirmareBean);
			}
		}
		if(filesInError != null && filesInError.size() > 0) {
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("DISCUSSIONE SEDUTA - Il versionamento di " + filesInError.size() + " file firmati su " + pFirmaMassivaFilesBean.getFiles().size() + " è andato in errore.");
			for(int i = 0; i < filesInError.size(); i++) {
				lStringBuffer.append("\nALL N° " + filesInError.get(i).getNroProgAllegato() + " - " + filesInError.get(i).getNomeFile() + ": " + errors.get(filesInError.get(i).getIdFile()));
			}
			mLogger.error(lStringBuffer.toString());			
		} else {
			mLogger.error("DISCUSSIONE SEDUTA - Il versionamento di tutti i " + pFirmaMassivaFilesBean.getFiles().size() + " file firmati è stato eseguito con successo.");
		}
		lFirmaMassivaFilesBean.setFiles(filesInError);
		return lFirmaMassivaFilesBean;
	}
	
	public void versionaDocumento(IdFileBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdFile()));
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(
				StorageImplementation.getStorage().store(StorageImplementation.getStorage().extractFile(bean.getUri()))));
		
		MimeTypeFirmaBean lMimeTypeFirmaBean;
		if(bean.getInfoFile() == null) {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		} else {
			lMimeTypeFirmaBean = bean.getInfoFile();
		}
			
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(bean.getNomeFile());
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		if (lMimeTypeFirmaBean.isDaScansione()) {
			lGenericFile.setDaScansione(Flag.SETTED);
			lGenericFile.setDataScansione(new Date());
			lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
		}
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lRebuildedFile.setInfo(lFileInfoBean);

		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);
		if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
			mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutBean);
		}
	}
	
	public PresenzeVotiBean getPresenzeVotiSuContOdG(PresenzeVotiBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkSeduteOrgCollGetpresenzevotisucontodgBean input = new  DmpkSeduteOrgCollGetpresenzevotisucontodgBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(pInBean.getIdSeduta());
		input.setIdudargomentoinodgin(pInBean.getIdUdArgomentoOdG() != null ? new BigDecimal(pInBean.getIdUdArgomentoOdG()) : null );
		input.setFlgpresenzevotiin(pInBean.getFlgPresenzeVotiIn() != null ? pInBean.getFlgPresenzeVotiIn() : null);
		
		DmpkSeduteOrgCollGetpresenzevotisucontodg dmpkSeduteOrgCollGetpresenzevotisucontodg = new DmpkSeduteOrgCollGetpresenzevotisucontodg();
		StoreResultBean<DmpkSeduteOrgCollGetpresenzevotisucontodgBean> output = dmpkSeduteOrgCollGetpresenzevotisucontodg.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		PresenzeVotiBean lPresenzeVotiBean = new PresenzeVotiBean();
		
		lPresenzeVotiBean.setIdSeduta(pInBean.getIdSeduta());
		lPresenzeVotiBean.setIdUdArgomentoOdG(pInBean.getIdUdArgomentoOdG());
		
		if (pInBean.getFlgPresenzeVotiIn() != null) {
			if(StringUtils.isNotBlank(output.getResultBean().getPresenzevotiout())) {
				List<PresenzeDichiarazioniVotiOdgXmlBean> listaPresenzeDichiarazioniVoti = XmlListaUtility.recuperaLista(output.getResultBean().getPresenzevotiout(), PresenzeDichiarazioniVotiOdgXmlBean.class);
				if(listaPresenzeDichiarazioniVoti != null) {
					for(PresenzeDichiarazioniVotiOdgXmlBean lPresenzeDichiarazioniVotiOdgXmlBean : listaPresenzeDichiarazioniVoti) {
						if(StringUtils.isNotBlank(lPresenzeDichiarazioniVotiOdgXmlBean.getIdDelegato())) {
							lPresenzeDichiarazioniVotiOdgXmlBean.setDelegato(lPresenzeDichiarazioniVotiOdgXmlBean.getIdDelegato() + "|*|" + lPresenzeDichiarazioniVotiOdgXmlBean.getDecodificaDelegato());
						}
					}
				}
				lPresenzeVotiBean.setListaPresenzeDichiarazioniVoti(listaPresenzeDichiarazioniVoti);
			}
		} else {
			if(StringUtils.isNotBlank(output.getResultBean().getPresenzevotiout())) {
				List<PresenzeOdgXmlBean> listaPresenzeVoti = XmlListaUtility.recuperaLista(output.getResultBean().getPresenzevotiout(), PresenzeOdgXmlBean.class);
				lPresenzeVotiBean.setListaPresenzeVoti(listaPresenzeVoti);
			}
		}
		
		lPresenzeVotiBean.setTotaliAstenutiVoto(output.getResultBean().getTotaleastenutivotoout());
		lPresenzeVotiBean.setTotaleVotiEspressi(output.getResultBean().getTotalevotiespressiout());
		lPresenzeVotiBean.setTotaleVotiFavorevoli(output.getResultBean().getTotalevotifavorevoliout());
		lPresenzeVotiBean.setTotaleVotiContrari(output.getResultBean().getTotalevoticontrariout());
		
		if(StringUtils.isNotBlank(output.getResultBean().getAltripresentiout())) {
			List<PresenzeOdgXmlBean> altriPresenti = XmlListaUtility.recuperaLista(output.getResultBean().getAltripresentiout(), PresenzeOdgXmlBean.class);
			lPresenzeVotiBean.setListaAltriPresenti(altriPresenti);
		}
				
		return lPresenzeVotiBean;
	}
	
	public PresenzeVotiBean savePresenzeVotiSuContOdG(PresenzeVotiBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		DmpkSeduteOrgCollSavepresenzevotisucontodgBean input = new DmpkSeduteOrgCollSavepresenzevotisucontodgBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(pInBean.getIdSeduta());
		input.setIdudargomentoinodgin(pInBean.getIdUdArgomentoOdG() != null ? new BigDecimal(pInBean.getIdUdArgomentoOdG()) : null);	
		if(StringUtils.isNotBlank(pInBean.getFlgPresenzeVotiIn())){
			input.setFlgpresenzevotiin(pInBean.getFlgPresenzeVotiIn() != null ? pInBean.getFlgPresenzeVotiIn() : null);
			if(pInBean.getListaPresenzeDichiarazioniVoti() != null) {
				for(PresenzeDichiarazioniVotiOdgXmlBean lPresenzeDichiarazioniVotiOdgXmlBean : pInBean.getListaPresenzeDichiarazioniVoti()) {
					if(StringUtils.isNotBlank(lPresenzeDichiarazioniVotiOdgXmlBean.getDelegato())) {
						lPresenzeDichiarazioniVotiOdgXmlBean.setIdDelegato(getIdDelegato(lPresenzeDichiarazioniVotiOdgXmlBean.getDelegato()));
					} else {
						lPresenzeDichiarazioniVotiOdgXmlBean.setIdDelegato(null);
					}
				}
				input.setPresenzevotiin(lXmlUtilitySerializer.bindXmlList(pInBean.getListaPresenzeDichiarazioniVoti()));
			}
		} else if(pInBean.getListaPresenzeVoti() != null) {
			input.setPresenzevotiin(lXmlUtilitySerializer.bindXmlList(pInBean.getListaPresenzeVoti()));
		}
		input.setTotaleastenutivotoin(pInBean.getTotaliAstenutiVoto());
		input.setTotalevotiespressiin(pInBean.getTotaleVotiEspressi());
		input.setTotalevotifavorevoliin(pInBean.getTotaleVotiFavorevoli());
		input.setTotalevoticontrariin(pInBean.getTotaleVotiContrari());
		if(pInBean.getListaAltriPresenti() != null && !pInBean.getListaAltriPresenti().isEmpty()) {
			input.setAltripresentiin(lXmlUtilitySerializer.bindXmlList(pInBean.getListaAltriPresenti()));
		}
		
		DmpkSeduteOrgCollSavepresenzevotisucontodg  dmpkSeduteOrgCollSavepresenzevotisucontodg = new DmpkSeduteOrgCollSavepresenzevotisucontodg();
		StoreResultBean<DmpkSeduteOrgCollSavepresenzevotisucontodgBean> output = dmpkSeduteOrgCollSavepresenzevotisucontodg.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		return pInBean;
	}
	
	public DiscussioneSedutaBean saveDiscussioneSeduta(DiscussioneSedutaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		DmpkSeduteOrgCollSavediscussionesedutaBean input = new DmpkSeduteOrgCollSavediscussionesedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(pInBean.getIdSeduta());
		/**
		 *  Stato della seduta. In input se NULL significa che lo stato resta invariato o si aggiorna in automatico in base agli altri dati in input. Valori possibili:
			-- inviata_convocazione
			--	1a_convocazione
			--	1a_convocazione_deserta
			--	2a_convocazione
			--	lavori_conclusi
			--	firmati_atti_adottati
		 */
		input.setStatosedutaio(pInBean.getStato());
		
		List<ArgomentiOdgXmlBean> listaArgomentiOdgCompleta = new ArrayList<ArgomentiOdgXmlBean>();
		if (pInBean.getListaArgomentiOdgEliminati() != null ) {
			for(ArgomentiOdgXmlBean itemEliminato : pInBean.getListaArgomentiOdgEliminati()) {
				listaArgomentiOdgCompleta.add(itemEliminato);
			}
		}
		if (pInBean.getListaArgomentiOdg() != null ) {
			for(ArgomentiOdgXmlBean itemCorrente : pInBean.getListaArgomentiOdg()) {
				listaArgomentiOdgCompleta.add(itemCorrente);
			}
		}
		input.setArgomentiodgio(lXmlUtilitySerializer.bindXmlList(listaArgomentiOdgCompleta));
		
//		if(pInBean.getListaArgomentiOdg() != null) {
//			input.setArgomentiodgio(lXmlUtilitySerializer.bindXmlList(pInBean.getListaArgomentiOdg()));
//		}
		
//		if(pInBean.getFileVerbalePrimaConvocazione() != null ) {
			input.setInfo1aconvocazioneio(lXmlUtilitySerializer.bindXml(createInfoPrimaConvocazioneXmlBean(pInBean)));
//		}
//		if(pInBean.getFileVerbaleSecondaConvocazione() != null ) {
			input.setInfo2aconvocazioneio(lXmlUtilitySerializer.bindXml(createInfoSecondaConvocazioneXmlBean(pInBean)));
//		}
		
		DmpkSeduteOrgCollSavediscussioneseduta dmpkSeduteOrgCollSavediscussioneseduta = new DmpkSeduteOrgCollSavediscussioneseduta();
		StoreResultBean<DmpkSeduteOrgCollSavediscussionesedutaBean> output = dmpkSeduteOrgCollSavediscussioneseduta.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			mLogger.error("DISCUSSIONE SEDUTA - Errore store di salvataggio discussione seduta: " + output.getDefaultMessage());
			throw new StoreException(output);						
		}
		
		DiscussioneSedutaBean lDiscussioneSedutaBean = new DiscussioneSedutaBean();
		lDiscussioneSedutaBean.setIdSeduta(pInBean.getIdSeduta());
		lDiscussioneSedutaBean.setStato(output.getResultBean().getStatosedutaio());
		
		if(StringUtils.isNotBlank(output.getResultBean().getArgomentiodgio())) {
			List<ArgomentiOdgXmlBean> listaArgomentiOdg = XmlListaUtility.recuperaLista(output.getResultBean().getArgomentiodgio(), ArgomentiOdgXmlBean.class);
			lDiscussioneSedutaBean.setListaArgomentiOdg(listaArgomentiOdg);
		}
		
		// Appiattisco dati prima convocazione
		if(StringUtils.isNotBlank(output.getResultBean().getInfo1aconvocazioneio())) {
			InfoConvocazioneXmlBean infoPrimaConvocazioneBean = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getInfo1aconvocazioneio(), InfoConvocazioneXmlBean.class);
					
			lDiscussioneSedutaBean.setVerbaleInfoPrimaConvocazione(createVerbaleInfoXmlBeanFromInfoConvocazioneXmlBean(null, infoPrimaConvocazioneBean));
			
			lDiscussioneSedutaBean.setDatiDiscPrimaConvDtInizioLavori(infoPrimaConvocazioneBean.getDtInizioLavori());
			lDiscussioneSedutaBean.setDatiDiscPrimaConvDtFineLavori(infoPrimaConvocazioneBean.getDtFineLavori());
			lDiscussioneSedutaBean.setListaDatiDiscPrimaConvPresenzeAppelloIniziale(infoPrimaConvocazioneBean.getPresenzeAppelloIniziale());
			if(lDiscussioneSedutaBean.getListaDatiDiscPrimaConvPresenzeAppelloIniziale() != null) {
				for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : lDiscussioneSedutaBean.getListaDatiDiscPrimaConvPresenzeAppelloIniziale()) {
					if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getIdDelegato())) {
						lPresenzeOdgXmlBean.setDelegato(lPresenzeOdgXmlBean.getIdDelegato() + "|*|" + lPresenzeOdgXmlBean.getDecodificaDelegato());
					}
				}
			}
			
			lDiscussioneSedutaBean.setDatiDiscPrimaConvEditorVerbale(infoPrimaConvocazioneBean.getVerbaleTestoHtml());
			
			// TODEL Verificare cosa mettere
//			lDiscussioneSedutaBean.setDatiDiscPrimaConvEditorVerbale()
			
//			FileVerbaleSedutaBean datiFileVerbalePrimaConvocazione = createFileVerbaleSedutaBean(infoPrimaConvocazioneBean);
//			lDiscussioneSedutaBean.setFileVerbalePrimaConvocazione(datiFileVerbalePrimaConvocazione);			
		}
		
		// Appiattisco dati seconda convocazione
		if(StringUtils.isNotBlank(output.getResultBean().getInfo2aconvocazioneio())) {
			InfoConvocazioneXmlBean infoSecondaConvocazioneBean = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getInfo2aconvocazioneio(), InfoConvocazioneXmlBean.class);
			
			lDiscussioneSedutaBean.setVerbaleInfoSecondaConvocazione(createVerbaleInfoXmlBeanFromInfoConvocazioneXmlBean(null, infoSecondaConvocazioneBean));
			
			lDiscussioneSedutaBean.setDatiDiscSecondaConvDtInizioLavori(infoSecondaConvocazioneBean.getDtInizioLavori());
			lDiscussioneSedutaBean.setDatiDiscSecondaConvDtFineLavori(infoSecondaConvocazioneBean.getDtFineLavori());
			lDiscussioneSedutaBean.setListaDatiDiscSecondaConvPresenzeAppelloIniziale(infoSecondaConvocazioneBean.getPresenzeAppelloIniziale());
			if(lDiscussioneSedutaBean.getListaDatiDiscSecondaConvPresenzeAppelloIniziale() != null) {
				for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : lDiscussioneSedutaBean.getListaDatiDiscSecondaConvPresenzeAppelloIniziale()) {
					if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getIdDelegato())) {
						lPresenzeOdgXmlBean.setDelegato(lPresenzeOdgXmlBean.getIdDelegato() + "|*|" + lPresenzeOdgXmlBean.getDecodificaDelegato());
					}
				}
			}
			
			lDiscussioneSedutaBean.setDatiDiscSecondaConvEditorVerbale(infoSecondaConvocazioneBean.getVerbaleTestoHtml());
			
			// TODEL Verificare cosa mettere
//			lDiscussioneSedutaBean.setDatiDiscSecondaConvEditorVerbale()
			
//			FileVerbaleSedutaBean datiFileVerbaleSecondaConvocazione = createFileVerbaleSedutaBean(infoSecondaConvocazioneBean);			
//			lDiscussioneSedutaBean.setFileVerbaleSecondaConvocazione(datiFileVerbaleSecondaConvocazione);			
		}
		
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		boolean isAttivoContabilia = lSistAMC != null && ("CONTABILIA".equalsIgnoreCase(lSistAMC) || "CONTABILIA2".equalsIgnoreCase(lSistAMC));
		boolean isChiusuraLavori = getExtraparams().get("isChiusuraLavori") != null && "true".equalsIgnoreCase(getExtraparams().get("isChiusuraLavori"));		
		if(isChiusuraLavori && isAttivoContabilia) {
			StringBuffer lAttiInErroreContabilia = null;
			if(lDiscussioneSedutaBean.getListaArgomentiOdg() != null) {
				boolean firstError = true;
				for(int i = 0; i < lDiscussioneSedutaBean.getListaArgomentiOdg().size(); i++) {
					ArgomentiOdgXmlBean lArgomentiOdgXmlBean = lDiscussioneSedutaBean.getListaArgomentiOdg().get(i);
					//TODO in lDiscussioneSedutaBean.getListaArgomentiOdg() non ho i dati che ho in pInBean.getListaArgomentiOdg(), tra cui numeroFinaleAtto
					if(StringUtils.isNotBlank(lArgomentiOdgXmlBean.getNumeroFinaleAtto()) && StringUtils.isNotBlank(lArgomentiOdgXmlBean.getEventoContabiliaInChiusuraSeduta())) {						
						try {
							NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean();
							lNuovaPropostaAtto2CompletaBean.setIdUd(lArgomentiOdgXmlBean.getIdUd());
							lNuovaPropostaAtto2CompletaBean = getNuovaPropostaAtto2CompletaDataSource().get(lNuovaPropostaAtto2CompletaBean);
							if(getNuovaPropostaAtto2CompletaDataSource().isAttivoContabilia(lNuovaPropostaAtto2CompletaBean)) {								
								if(StringUtils.isNotBlank(lArgomentiOdgXmlBean.getEventoContabiliaInChiusuraSeduta())) {
									StringSplitterServer st = new StringSplitterServer(lArgomentiOdgXmlBean.getEventoContabiliaInChiusuraSeduta(), ";");
									while(st.hasMoreTokens()) {
										getNuovaPropostaAtto2CompletaDataSource().eventoContabilia(st.nextToken(), lNuovaPropostaAtto2CompletaBean);
						    		}		    		
								}								
							}
						} catch(Exception e) {
							if(lAttiInErroreContabilia == null) {
								lAttiInErroreContabilia = new StringBuffer();
							}
							if(firstError) {
								firstError = false;
								lAttiInErroreContabilia.append(lArgomentiOdgXmlBean.getNumeroFinaleAtto());
							} else {								
								lAttiInErroreContabilia.append(", " + lArgomentiOdgXmlBean.getNumeroFinaleAtto());							
							}
							mLogger.warn("Chiamata a Contabilia fallita su atto " + lArgomentiOdgXmlBean.getNumeroFinaleAtto(), e);							
						}
					}
				}				
				if(lAttiInErroreContabilia != null) {
					addMessage("Chiamata a Contabilia fallita sui seguenti atti: " + lAttiInErroreContabilia.toString(), "", MessageType.WARNING);
				}
			}
		}
		
		return lDiscussioneSedutaBean;
	}
	
	private VerbaleInfoXmlBean createVerbaleInfoXmlBeanFromInfoConvocazioneXmlBean(VerbaleInfoXmlBean pVerbaleInfoXmlBean, InfoConvocazioneXmlBean pInfoConvocazioneXmlBean) {
	
		if(pVerbaleInfoXmlBean == null) {
			pVerbaleInfoXmlBean = new VerbaleInfoXmlBean();
		}
		
		if(pInfoConvocazioneXmlBean != null) {
			pVerbaleInfoXmlBean.setIdDoc(pInfoConvocazioneXmlBean.getVerbaleIdDoc());
			pVerbaleInfoXmlBean.setUri(pInfoConvocazioneXmlBean.getVerbaleURI());
			pVerbaleInfoXmlBean.setFlgDaFirmare(pInfoConvocazioneXmlBean.getVerbaleFlgDaFirmare() != null ? pInfoConvocazioneXmlBean.getVerbaleFlgDaFirmare().intValue() == 1 : null);
			pVerbaleInfoXmlBean.setDisplayFilename(pInfoConvocazioneXmlBean.getVerbaleDisplayFilename());
			
			pVerbaleInfoXmlBean.setNomeModello(pInfoConvocazioneXmlBean.getVerbaleNomeModello());
			pVerbaleInfoXmlBean.setIdModello(pInfoConvocazioneXmlBean.getVerbaleIdModello());
			pVerbaleInfoXmlBean.setUriModello(pInfoConvocazioneXmlBean.getVerbaleUriModello());
			pVerbaleInfoXmlBean.setTipoModello(pInfoConvocazioneXmlBean.getVerbaleTipoModello());
						
			if (pInfoConvocazioneXmlBean.getListaVerbaleFirmatari() != null) {
				List<FirmatarioXmlBean> listaVerbaleFirmatari = new ArrayList<>();
				for (FirmatarioXmlBean firmatario: pInfoConvocazioneXmlBean.getListaVerbaleFirmatari()) {
					listaVerbaleFirmatari.add(firmatario);
				}
				pVerbaleInfoXmlBean.setListaFirmatari(listaVerbaleFirmatari);
			}
			pVerbaleInfoXmlBean.setMimetype(pInfoConvocazioneXmlBean.getMimetype());
			pVerbaleInfoXmlBean.setAlgoritmo(pInfoConvocazioneXmlBean.getVerbaleAlgoritmoImpronta());
			pVerbaleInfoXmlBean.setDimensione(pInfoConvocazioneXmlBean.getVerbaleDimensione());
			pVerbaleInfoXmlBean.setEncoding(pInfoConvocazioneXmlBean.getVerbaleEncodingImpronta());
			pVerbaleInfoXmlBean.setFlgFirmato(pInfoConvocazioneXmlBean.getVerbaleFlgFirmato() != null ? pInfoConvocazioneXmlBean.getVerbaleFlgFirmato().intValue() == 1 : null);
			pVerbaleInfoXmlBean.setFlgPdfizzabile(pInfoConvocazioneXmlBean.getVerbaleFlgPdfizzabile() != null ? pInfoConvocazioneXmlBean.getVerbaleFlgPdfizzabile().intValue() == 1 : null);
			pVerbaleInfoXmlBean.setImpronta(pInfoConvocazioneXmlBean.getVerbaleImpronta());
			
			pVerbaleInfoXmlBean.setTestoHtml(pInfoConvocazioneXmlBean.getVerbaleTestoHtml());
		}
		
		return pVerbaleInfoXmlBean;
	}
	
	private InfoConvocazioneXmlBean createInfoConvocazioneXmlBeanFromVerbaleInfoXmlBean(InfoConvocazioneXmlBean pInfoConvocazioneXmlBean, VerbaleInfoXmlBean pVerbaleInfoXmlBean) {
		
		if(pInfoConvocazioneXmlBean == null) {
			pInfoConvocazioneXmlBean = new InfoConvocazioneXmlBean();
		}
		
		if(pVerbaleInfoXmlBean != null) {
			pInfoConvocazioneXmlBean.setVerbaleIdDoc(pVerbaleInfoXmlBean.getIdDoc());
			pInfoConvocazioneXmlBean.setVerbaleURI(pVerbaleInfoXmlBean.getUri());
			pInfoConvocazioneXmlBean.setVerbaleFlgDaFirmare(pVerbaleInfoXmlBean.getFlgDaFirmare() != null && pVerbaleInfoXmlBean.getFlgDaFirmare() ? 1 : 0);
			pInfoConvocazioneXmlBean.setVerbaleDisplayFilename(pVerbaleInfoXmlBean.getDisplayFilename());
			
			pInfoConvocazioneXmlBean.setVerbaleNomeModello(pVerbaleInfoXmlBean.getNomeModello());
			pInfoConvocazioneXmlBean.setVerbaleIdModello(pVerbaleInfoXmlBean.getIdModello());
			pInfoConvocazioneXmlBean.setVerbaleUriModello(pVerbaleInfoXmlBean.getUriModello());
			pInfoConvocazioneXmlBean.setVerbaleTipoModello(pVerbaleInfoXmlBean.getTipoModello());
			
			if (pVerbaleInfoXmlBean.getListaFirmatari() != null) {
				List<FirmatarioXmlBean> listaVerbaleFirmatari = new ArrayList<>();
				for (FirmatarioXmlBean firmatario: pVerbaleInfoXmlBean.getListaFirmatari()) {
					listaVerbaleFirmatari.add(firmatario);
				}
				pInfoConvocazioneXmlBean.setListaVerbaleFirmatari(listaVerbaleFirmatari);
			}
			pInfoConvocazioneXmlBean.setMimetype(pVerbaleInfoXmlBean.getMimetype());
			pInfoConvocazioneXmlBean.setVerbaleAlgoritmoImpronta(pVerbaleInfoXmlBean.getAlgoritmo());
			pInfoConvocazioneXmlBean.setVerbaleDimensione(pVerbaleInfoXmlBean.getDimensione() != null ? pVerbaleInfoXmlBean.getDimensione().longValue() : null);
			pInfoConvocazioneXmlBean.setVerbaleEncodingImpronta(pVerbaleInfoXmlBean.getEncoding());
			pInfoConvocazioneXmlBean.setVerbaleFlgFirmato(pVerbaleInfoXmlBean.getFlgFirmato() != null && pVerbaleInfoXmlBean.getFlgFirmato() ? 1: 0);
			pInfoConvocazioneXmlBean.setVerbaleFlgPdfizzabile(pVerbaleInfoXmlBean.getFlgPdfizzabile() != null && pVerbaleInfoXmlBean.getFlgPdfizzabile() ? 1 : 0);
			pInfoConvocazioneXmlBean.setVerbaleImpronta(pVerbaleInfoXmlBean.getImpronta());
		}
		
		return pInfoConvocazioneXmlBean;
	}
	
	/*
	private FileVerbaleSedutaBean createFileVerbaleSedutaBean(InfoConvocazioneXmlBean infoPrimaConvocazioneBean) {
		
		FileVerbaleSedutaBean datiFileVerbalePrimaConvocazione = new FileVerbaleSedutaBean();
		datiFileVerbalePrimaConvocazione.setIdDoc(infoPrimaConvocazioneBean.getVerbaleIdDoc());
		datiFileVerbalePrimaConvocazione.setUri(infoPrimaConvocazioneBean.getVerbaleURI());
		datiFileVerbalePrimaConvocazione.setFlgDaFirmare(infoPrimaConvocazioneBean.getVerbaleFlgDaFirmare() != null && infoPrimaConvocazioneBean.getVerbaleFlgDaFirmare().intValue() == 1 ? true : false);
		datiFileVerbalePrimaConvocazione.setDisplayFilename(infoPrimaConvocazioneBean.getVerbaleDisplayFilename());
		
		datiFileVerbalePrimaConvocazione.setIdModello(infoPrimaConvocazioneBean.getVerbaleIdModello());
		datiFileVerbalePrimaConvocazione.setNomeModello(infoPrimaConvocazioneBean.getVerbaleIdModello());
		datiFileVerbalePrimaConvocazione.setTipoModello(infoPrimaConvocazioneBean.getVerbaleTipoModello());
		datiFileVerbalePrimaConvocazione.setUriModello(infoPrimaConvocazioneBean.getVerbaleUriModello());
		
		MimeTypeFirmaBean infoFileVerbalePrimaConvocazione = new MimeTypeFirmaBean();
		infoFileVerbalePrimaConvocazione.setMimetype(infoPrimaConvocazioneBean.getMimetype());
		infoFileVerbalePrimaConvocazione.setBytes(infoPrimaConvocazioneBean.getVerbaleDimensione());
		infoFileVerbalePrimaConvocazione.setFirmato(infoPrimaConvocazioneBean.getVerbaleFlgFirmato() != null && infoPrimaConvocazioneBean.getVerbaleFlgFirmato().intValue() == 1 ? true : false );
		if (infoPrimaConvocazioneBean.getListaVerbaleFirmatari() != null) {
			List<String> listaVerbaleFirmatari = new ArrayList<>();
			for (FirmatarioXmlBean  firmatario: infoPrimaConvocazioneBean.getListaVerbaleFirmatari()) {
				listaVerbaleFirmatari.add(firmatario.getFirmatario());
			}
			 String[] itemsArray = new String[listaVerbaleFirmatari.size()];
			infoFileVerbalePrimaConvocazione.setFirmatari(listaVerbaleFirmatari.toArray(itemsArray));
		}
		infoFileVerbalePrimaConvocazione.setImpronta(infoPrimaConvocazioneBean.getVerbaleImpronta());
		infoFileVerbalePrimaConvocazione.setEncoding(infoPrimaConvocazioneBean.getVerbaleEncodingImpronta());
		infoFileVerbalePrimaConvocazione.setAlgoritmo(infoPrimaConvocazioneBean.getVerbaleAlgoritmoImpronta());
		
		datiFileVerbalePrimaConvocazione.setInfoFileVerbale(infoFileVerbalePrimaConvocazione);
		return datiFileVerbalePrimaConvocazione;
	}
	*/
	
	/**
	 * 
	 * @param pInBean
	 * @return metodo che genera un InfoPrimaConvocazioneXmlBean a partire dalle informazioni di DiscussioneSedutaBean
	 */
	private InfoConvocazioneXmlBean createInfoPrimaConvocazioneXmlBean(DiscussioneSedutaBean pInBean) {
		
		InfoConvocazioneXmlBean lInfoConvocazioneXmlBean = new InfoConvocazioneXmlBean();
		lInfoConvocazioneXmlBean.setDtInizioLavori(pInBean.getDatiDiscPrimaConvDtInizioLavori());
		lInfoConvocazioneXmlBean.setDtFineLavori(pInBean.getDatiDiscPrimaConvDtFineLavori());
		lInfoConvocazioneXmlBean.setDisattivatoRiordinoAutomatico(pInBean.getDisattivatoRiordinoAutomatico());
		
		for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : pInBean.getListaDatiDiscPrimaConvPresenzeAppelloIniziale()) {
			if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getDelegato())) {
				lPresenzeOdgXmlBean.setIdDelegato(getIdDelegato(lPresenzeOdgXmlBean.getDelegato()));
			} else {
				lPresenzeOdgXmlBean.setIdDelegato(null);
			}
		}
		lInfoConvocazioneXmlBean.setPresenzeAppelloIniziale(pInBean.getListaDatiDiscPrimaConvPresenzeAppelloIniziale());
		lInfoConvocazioneXmlBean.setVerbaleTestoHtml(pInBean.getDatiDiscPrimaConvEditorVerbale());
		
		/*
		FileVerbaleSedutaBean fileVerbaleSedutaPrimaConvocazioneBean = pInBean.getFileVerbalePrimaConvocazione();

		lInfoConvocazioneXmlBean.setVerbaleIdDoc(fileVerbaleSedutaPrimaConvocazioneBean.getIdDoc());
		lInfoConvocazioneXmlBean.setVerbaleURI(fileVerbaleSedutaPrimaConvocazioneBean.getUri());
		lInfoConvocazioneXmlBean.setVerbaleFlgDaFirmare(fileVerbaleSedutaPrimaConvocazioneBean.isFlgDaFirmare()? 1 : 0);
		lInfoConvocazioneXmlBean.setVerbaleDisplayFilename(fileVerbaleSedutaPrimaConvocazioneBean.getDisplayFilename());

		lInfoConvocazioneXmlBean.setVerbaleNomeModello(fileVerbaleSedutaPrimaConvocazioneBean.getNomeModello());
		lInfoConvocazioneXmlBean.setVerbaleIdModello(fileVerbaleSedutaPrimaConvocazioneBean.getIdModello());
		lInfoConvocazioneXmlBean.setVerbaleUriModello(fileVerbaleSedutaPrimaConvocazioneBean.getUriModello());
		lInfoConvocazioneXmlBean.setVerbaleTipoModello(fileVerbaleSedutaPrimaConvocazioneBean.getTipoModello());
		
		MimeTypeFirmaBean mimeTypeVerbalePrimaConvocazioneBean = fileVerbaleSedutaPrimaConvocazioneBean.getInfoFileVerbale();		
		
		if (mimeTypeVerbalePrimaConvocazioneBean.getFirmatari() != null) {
			List<FirmatarioXmlBean> listaVerbaleFirmatari = new ArrayList<>();
			for (String  firmatario: mimeTypeVerbalePrimaConvocazioneBean.getFirmatari()) {
				FirmatarioXmlBean bean = new FirmatarioXmlBean();
				bean.setFirmatario(firmatario);
				listaVerbaleFirmatari.add(bean);
			}
			lInfoConvocazioneXmlBean.setListaVerbaleFirmatari(listaVerbaleFirmatari);
		}
		lInfoConvocazioneXmlBean.setMimetype(mimeTypeVerbalePrimaConvocazioneBean.getMimetype());
		lInfoConvocazioneXmlBean.setVerbaleAlgoritmoImpronta(mimeTypeVerbalePrimaConvocazioneBean.getAlgoritmo());
		lInfoConvocazioneXmlBean.setVerbaleDimensione(mimeTypeVerbalePrimaConvocazioneBean.getBytes());
		lInfoConvocazioneXmlBean.setVerbaleEncodingImpronta(mimeTypeVerbalePrimaConvocazioneBean.getEncoding());
		lInfoConvocazioneXmlBean.setVerbaleFlgFirmato(mimeTypeVerbalePrimaConvocazioneBean.isFirmato()? 1: 0);
		lInfoConvocazioneXmlBean.setVerbaleFlgPdfizzabile(mimeTypeVerbalePrimaConvocazioneBean.isConvertibile()? 1 : 0);
		lInfoConvocazioneXmlBean.setVerbaleImpronta(mimeTypeVerbalePrimaConvocazioneBean.getImpronta());
		*/
		
		return createInfoConvocazioneXmlBeanFromVerbaleInfoXmlBean(lInfoConvocazioneXmlBean, pInBean.getVerbaleInfoPrimaConvocazione());	
	}
	
	/**
	 * 
	 * @param pInBean
	 * @return metodo che genera un InfoSecondaConvocazioneXmlBean a partire dalle informazioni di DiscussioneSedutaBean
	 */
	private InfoConvocazioneXmlBean createInfoSecondaConvocazioneXmlBean(DiscussioneSedutaBean pInBean) {
		
		InfoConvocazioneXmlBean lInfoConvocazioneXmlBean = new InfoConvocazioneXmlBean();
		lInfoConvocazioneXmlBean.setDtInizioLavori(pInBean.getDatiDiscSecondaConvDtInizioLavori());
		lInfoConvocazioneXmlBean.setDtFineLavori(pInBean.getDatiDiscSecondaConvDtFineLavori());
		
		for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : pInBean.getListaDatiDiscSecondaConvPresenzeAppelloIniziale()) {
			if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getDelegato())) {
				lPresenzeOdgXmlBean.setIdDelegato(getIdDelegato(lPresenzeOdgXmlBean.getDelegato()));
			} else {
				lPresenzeOdgXmlBean.setIdDelegato(null);
			}
		}
		lInfoConvocazioneXmlBean.setPresenzeAppelloIniziale(pInBean.getListaDatiDiscSecondaConvPresenzeAppelloIniziale());
		lInfoConvocazioneXmlBean.setVerbaleTestoHtml(pInBean.getDatiDiscSecondaConvEditorVerbale());
		/*
		FileVerbaleSedutaBean fileVerbaleSedutaSecondaConvocazioneBean = pInBean.getFileVerbaleSecondaConvocazione();

		lInfoConvocazioneXmlBean.setVerbaleIdDoc(fileVerbaleSedutaSecondaConvocazioneBean.getIdDoc());
		lInfoConvocazioneXmlBean.setVerbaleURI(fileVerbaleSedutaSecondaConvocazioneBean.getUri());
		lInfoConvocazioneXmlBean.setVerbaleFlgDaFirmare(fileVerbaleSedutaSecondaConvocazioneBean.isFlgDaFirmare()? 1 : 0);
		lInfoConvocazioneXmlBean.setVerbaleDisplayFilename(fileVerbaleSedutaSecondaConvocazioneBean.getDisplayFilename());

		lInfoConvocazioneXmlBean.setVerbaleNomeModello(fileVerbaleSedutaSecondaConvocazioneBean.getNomeModello());
		lInfoConvocazioneXmlBean.setVerbaleIdModello(fileVerbaleSedutaSecondaConvocazioneBean.getIdModello());
		lInfoConvocazioneXmlBean.setVerbaleUriModello(fileVerbaleSedutaSecondaConvocazioneBean.getUriModello());
		lInfoConvocazioneXmlBean.setVerbaleTipoModello(fileVerbaleSedutaSecondaConvocazioneBean.getTipoModello());
		
		MimeTypeFirmaBean mimeTypeVerbaleSecondaConvocazioneBean = fileVerbaleSedutaSecondaConvocazioneBean.getInfoFileVerbale();		
		
		if (mimeTypeVerbaleSecondaConvocazioneBean.getFirmatari() != null) {
			List<FirmatarioXmlBean> listaVerbaleFirmatari = new ArrayList<>();
			for (String  firmatario: mimeTypeVerbaleSecondaConvocazioneBean.getFirmatari()) {
				FirmatarioXmlBean bean = new FirmatarioXmlBean();
				bean.setFirmatario(firmatario);
				listaVerbaleFirmatari.add(bean);
			}
			lInfoConvocazioneXmlBean.setListaVerbaleFirmatari(listaVerbaleFirmatari);
		}
		lInfoConvocazioneXmlBean.setMimetype(mimeTypeVerbaleSecondaConvocazioneBean.getMimetype());
		lInfoConvocazioneXmlBean.setVerbaleAlgoritmoImpronta(mimeTypeVerbaleSecondaConvocazioneBean.getAlgoritmo());
		lInfoConvocazioneXmlBean.setVerbaleDimensione(mimeTypeVerbaleSecondaConvocazioneBean.getBytes());
		lInfoConvocazioneXmlBean.setVerbaleEncodingImpronta(mimeTypeVerbaleSecondaConvocazioneBean.getEncoding());
		lInfoConvocazioneXmlBean.setVerbaleFlgFirmato(mimeTypeVerbaleSecondaConvocazioneBean.isFirmato()? 1: 0);
		lInfoConvocazioneXmlBean.setVerbaleFlgPdfizzabile(mimeTypeVerbaleSecondaConvocazioneBean.isConvertibile()? 1 : 0);
		lInfoConvocazioneXmlBean.setVerbaleImpronta(mimeTypeVerbaleSecondaConvocazioneBean.getImpronta());
		*/
		
		return createInfoConvocazioneXmlBeanFromVerbaleInfoXmlBean(lInfoConvocazioneXmlBean, pInBean.getVerbaleInfoSecondaConvocazione());		
	}
	
	public DiscussioneSedutaBean simulaNumerazioneAttiSeduta(DiscussioneSedutaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		DmpkSeduteOrgCollSimulanumerazioneattisedutaBean input = new DmpkSeduteOrgCollSimulanumerazioneattisedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(pInBean.getIdSeduta());
		if(pInBean.getListaArgomentiOdg() != null) {
			input.setArgomentiodgio(lXmlUtilitySerializer.bindXmlList(pInBean.getListaArgomentiOdg()));
		}
		
		DmpkSeduteOrgCollSimulanumerazioneattiseduta dmpkSeduteOrgCollSimulanumerazioneattiseduta = new DmpkSeduteOrgCollSimulanumerazioneattiseduta();
		StoreResultBean<DmpkSeduteOrgCollSimulanumerazioneattisedutaBean> output = dmpkSeduteOrgCollSimulanumerazioneattiseduta.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		DiscussioneSedutaBean lDiscussioneSedutaBean = new DiscussioneSedutaBean();
		if(StringUtils.isNotBlank(output.getResultBean().getArgomentiodgio())) {
			List<ArgomentiOdgXmlBean> listaArgomentiOdg = XmlListaUtility.recuperaLista(output.getResultBean().getArgomentiodgio(), ArgomentiOdgXmlBean.class);
			lDiscussioneSedutaBean.setListaArgomentiOdg(listaArgomentiOdg);
		}
		
		return lDiscussioneSedutaBean;	
	}
	
	public CallExecAttDatasource getCallExecAttDatasource() {
		CallExecAttDatasource lCallExecAttDatasource = new CallExecAttDatasource();
		lCallExecAttDatasource.setSession(getSession());
		lCallExecAttDatasource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lCallExecAttDatasource.setMessages(getMessages()); 		
		return lCallExecAttDatasource;
	}	
	
	public NuovaPropostaAtto2CompletaDataSource getNuovaPropostaAtto2CompletaDataSource() {
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = new NuovaPropostaAtto2CompletaDataSource();
		lNuovaPropostaAtto2CompletaDataSource.setSession(getSession());
		lNuovaPropostaAtto2CompletaDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lNuovaPropostaAtto2CompletaDataSource.setMessages(getMessages()); 		
		return lNuovaPropostaAtto2CompletaDataSource;
	}	
	
	public OperazioneMassivaArgomentiOdgXmlBean cambiaStatoAtti(OperazioneMassivaArgomentiOdgXmlBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		String codStato = getExtraparams().get("stato");
		String flgGeneraFileUnione = getExtraparams().get("flgGeneraFileUnione");
		
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			
			ArgomentiOdgXmlBean lArgomentiOdgXmlBean = (ArgomentiOdgXmlBean) bean.getListaRecord().get(i);
			
			if(flgGeneraFileUnione != null && "1".equals(flgGeneraFileUnione)) {
				
				if(lArgomentiOdgXmlBean.getStatoRevisioneTesto() != null && lArgomentiOdgXmlBean.getStatoRevisioneTesto().equalsIgnoreCase("testo_in_lavorazione")) {
					
					try {
						
						AttProcBean lAttProcBean = new AttProcBean();
						lAttProcBean.setIdUd(lArgomentiOdgXmlBean.getIdUd());
						lAttProcBean.setIdProcess(lArgomentiOdgXmlBean.getIdProcessoAuriga());
						lAttProcBean.setActivityName("#POST_DISCUSSIONE_AULA_" + bean.getOrganoCollegiale());
						lAttProcBean.setIdDefProcFlow(lArgomentiOdgXmlBean.getIdTipoFlussoActiviti());
						lAttProcBean.setIdInstProcFlow(lArgomentiOdgXmlBean.getIdIstanzaFlussoActiviti());			
						lAttProcBean = getCallExecAttDatasource().call(lAttProcBean);
						
						String nomeFileUnione = lAttProcBean.getUnioneFileNomeFile();
						String nomeFileUnioneOmissis = lAttProcBean.getUnioneFileNomeFileOmissis();
						ImpostazioniUnioneFileBean lImpostazioniUnioneFileBean = lAttProcBean.getImpostazioniUnioneFile();
			
						NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = getNuovaPropostaAtto2CompletaDataSource();
						lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("flgPostDiscussione", "true");
						lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("codStato", codStato);
						
						NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean();
						lNuovaPropostaAtto2CompletaBean.setIdUd(lArgomentiOdgXmlBean.getIdUd());
						lNuovaPropostaAtto2CompletaBean = lNuovaPropostaAtto2CompletaDataSource.get(lNuovaPropostaAtto2CompletaBean);
												
						lNuovaPropostaAtto2CompletaBean.setIdProcess(lAttProcBean.getIdProcess());
						lNuovaPropostaAtto2CompletaBean.setIdProcess(lAttProcBean.getIdProcess());
						lNuovaPropostaAtto2CompletaBean.setIdModCopertina(lAttProcBean.getIdModCopertina());
						lNuovaPropostaAtto2CompletaBean.setNomeModCopertina(lAttProcBean.getNomeModCopertina());						
						lNuovaPropostaAtto2CompletaBean.setIdModCopertinaFinale(lAttProcBean.getIdModCopertinaFinale());
						lNuovaPropostaAtto2CompletaBean.setNomeModCopertinaFinale(lAttProcBean.getNomeModCopertinaFinale());						
						lNuovaPropostaAtto2CompletaBean.setIdModAllegatiParteIntSeparati(lAttProcBean.getIdModAllegatiParteIntSeparati());
						lNuovaPropostaAtto2CompletaBean.setNomeModAllegatiParteIntSeparati(lAttProcBean.getNomeModAllegatiParteIntSeparati());
						lNuovaPropostaAtto2CompletaBean.setUriModAllegatiParteIntSeparati(lAttProcBean.getUriModAllegatiParteIntSeparati());
						lNuovaPropostaAtto2CompletaBean.setTipoModAllegatiParteIntSeparati(lAttProcBean.getTipoModAllegatiParteIntSeparati());						
						lNuovaPropostaAtto2CompletaBean.setIdModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getIdModAllegatiParteIntSeparatiXPubbl());
						lNuovaPropostaAtto2CompletaBean.setNomeModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getNomeModAllegatiParteIntSeparatiXPubbl());
						lNuovaPropostaAtto2CompletaBean.setUriModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getUriModAllegatiParteIntSeparatiXPubbl());
						lNuovaPropostaAtto2CompletaBean.setTipoModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getTipoModAllegatiParteIntSeparatiXPubbl());						
						lNuovaPropostaAtto2CompletaBean.setFlgAppendiceDaUnire(lAttProcBean.getFlgAppendiceDaUnire());		
						lNuovaPropostaAtto2CompletaBean.setIdModAppendice(lAttProcBean.getIdModAppendice());
						lNuovaPropostaAtto2CompletaBean.setNomeModAppendice(lAttProcBean.getNomeModAppendice());						
						lNuovaPropostaAtto2CompletaBean.setIdModello(lAttProcBean.getIdModAssDocTask());
						lNuovaPropostaAtto2CompletaBean.setNomeModello(lAttProcBean.getNomeModAssDocTask());
						lNuovaPropostaAtto2CompletaBean.setDisplayFilenameModello(lAttProcBean.getDisplayFilenameModAssDocTask());
						lNuovaPropostaAtto2CompletaBean.setIdUoDirAdottanteSIB(lAttProcBean.getIdUoDirAdottanteSIB());
						lNuovaPropostaAtto2CompletaBean.setCodUoDirAdottanteSIB(lAttProcBean.getCodUoDirAdottanteSIB());
						lNuovaPropostaAtto2CompletaBean.setDesUoDirAdottanteSIB(lAttProcBean.getDesUoDirAdottanteSIB());						
						lNuovaPropostaAtto2CompletaBean.setImpostazioniUnioneFile(lImpostazioniUnioneFileBean);						
						
						lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("nomeFileUnione", nomeFileUnione);
						lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("nomeFileUnioneOmissis", nomeFileUnioneOmissis);
					
						UnioneFileAttoBean lUnioneFileAttoBean = lNuovaPropostaAtto2CompletaDataSource.unioneFile(lNuovaPropostaAtto2CompletaBean);
						
						if(lUnioneFileAttoBean != null) {
							lNuovaPropostaAtto2CompletaBean.setUriFilePrimario(lUnioneFileAttoBean.getUriVersIntegrale());
							lNuovaPropostaAtto2CompletaBean.setNomeFilePrimario(lUnioneFileAttoBean.getNomeFileVersIntegrale());
							lNuovaPropostaAtto2CompletaBean.setInfoFilePrimario(lUnioneFileAttoBean.getInfoFileVersIntegrale());
							lNuovaPropostaAtto2CompletaBean.setUriFilePrimarioOmissis(lUnioneFileAttoBean.getUri());
							lNuovaPropostaAtto2CompletaBean.setNomeFilePrimarioOmissis(lUnioneFileAttoBean.getNomeFile());
							lNuovaPropostaAtto2CompletaBean.setInfoFilePrimarioOmissis(lUnioneFileAttoBean.getInfoFile());
							lNuovaPropostaAtto2CompletaBean.setUriDocGeneratoFormatoOdt(lUnioneFileAttoBean.getUriFileOdtGenerato());
						}
						
						lNuovaPropostaAtto2CompletaBean = lNuovaPropostaAtto2CompletaDataSource.aggiornaStato(lNuovaPropostaAtto2CompletaBean);
						
					} catch(Exception e) {
						errorMessages.put(lArgomentiOdgXmlBean.getIdUd(), e.getMessage());					
					}
				}
				
			} else {
			
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("U");
				input.setIduddocin(new BigDecimal(lArgomentiOdgXmlBean.getIdUd()));
				
				CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
				creaModDocumentoInBean.setCodStatoDett(codStato);				
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if (output.isInError()) {					
					errorMessages.put(lArgomentiOdgXmlBean.getIdUd(), output.getDefaultMessage());
				}
			
			}
		}
		
		if(errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		return bean;
	}
	
	public OperazioneMassivaArgomentiOdgXmlBean vistoAttiAdottati(OperazioneMassivaArgomentiOdgXmlBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String idUser = loginBean.getIdUser() != null && !"".equals(loginBean.getIdUser().toString()) ? loginBean.getIdUser().toString() : "";
		String ID_SEGRETARIO_VISTO_POST_DISCUSSIONE = idUserLavoro != null ? idUserLavoro : idUser;
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			
			ArgomentiOdgXmlBean lArgomentiOdgXmlBean = (ArgomentiOdgXmlBean) bean.getListaRecord().get(i);
			
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgtipotargetin("U");
			input.setIduddocin(new BigDecimal(lArgomentiOdgXmlBean.getIdUd()));
			
			CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("ID_SEGRETARIO_VISTO_POST_DISCUSSIONE_Ud", ID_SEGRETARIO_VISTO_POST_DISCUSSIONE));
			creaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
			input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
			
			DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
			if (output.isInError()) {					
				errorMessages.put(lArgomentiOdgXmlBean.getIdUd(), output.getDefaultMessage());
			}			
		}
		
		if(errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		return bean;
	}
	
	public FileDaFirmareBean generaModelloVerbalePrimaConvocazione(DiscussioneSedutaBean pInBean) throws Exception {
		// Ricavo i dati da iniettare nel modello
		String nomeModello = pInBean.getVerbaleInfoPrimaConvocazione() != null ? pInBean.getVerbaleInfoPrimaConvocazione().getNomeModello() : ""; 
		String templateValues = getDatiXGenDaModello(pInBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, pInBean.getVerbaleInfoPrimaConvocazione().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());
		// FileDaFirmareBean templateWithValues = ModelliUtil.fillTemplate(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), templateValues, true, getSession());
		return templateWithValues;
	}
	
	public FileDaFirmareBean generaModelloVerbaleSecondaConvocazione(DiscussioneSedutaBean pInBean) throws Exception {
		// Ricavo i dati da iniettare nel modello
		String nomeModello = pInBean.getVerbaleInfoSecondaConvocazione() != null ? pInBean.getVerbaleInfoSecondaConvocazione().getNomeModello() : ""; 
		String templateValues = getDatiXGenDaModello(pInBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, pInBean.getVerbaleInfoSecondaConvocazione().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());
		// FileDaFirmareBean templateWithValues = ModelliUtil.fillTemplate(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), templateValues, true, getSession());
		return templateWithValues;
	}
	
	/*
	public DiscussioneSedutaBean generaModelloPerSalvaChiudiVerbale(DiscussioneSedutaBean pInBean) throws Exception {
		//TODO come mai legge i dati del modello da OdgInfo?
		// Ricavo i dati da iniettare nel modello
		String nomeModello = pInBean.getOdgInfo() != null ? pInBean.getOdgInfo().getNomeModello() : "";
		String templateValues = getDatiXGenDaModello(pInBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, pInBean.getOdgInfo().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());
		// FileDaFirmareBean templateWithValues = ModelliUtil.fillTemplate(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), templateValues, true, getSession());
		pInBean.setFileGenerato(templateWithValues);
		return pInBean;
	}
	*/
	
	public DiscussioneSedutaBean generaModelloPerSalvaChiudiLavori(DiscussioneSedutaBean pInBean) throws Exception {
		// Ricavo i dati da iniettare nel modello
		String nomeModello = pInBean.getOdgInfo() != null ? pInBean.getOdgInfo().getNomeModello() : ""; 
		String templateValues = getDatiXGenDaModello(pInBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, pInBean.getOdgInfo().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());
		// FileDaFirmareBean templateWithValues = ModelliUtil.fillTemplate(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), templateValues, true, getSession());
		pInBean.setFileGenerato(templateWithValues);
		return pInBean;
	}
	
	public FileDaFirmareBean generaAnteprimaOdg(DiscussioneSedutaBean pInBean) throws Exception {
		// Ricavo i dati da iniettare nel modello
		String nomeModello = pInBean.getOdgInfo() != null ? pInBean.getOdgInfo().getNomeModello() : ""; 
		String templateValues = getDatiXGenDaModello(pInBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, pInBean.getOdgInfo().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());
		// FileDaFirmareBean templateWithValues = ModelliUtil.fillTemplate(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), templateValues, true, getSession());
		return templateWithValues;
	}
		
	private String getDatiXGenDaModello(DiscussioneSedutaBean bean, String nomeModello) throws Exception {
		// Creo la sezione cache
		SezioneCache variabiliSezioneCache = creaSezioneCachePerModello(bean);
		
		// chiamo la get dati per modello
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdSeduta());
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("S");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		// Creo gli attributi addizionali
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(variabiliSezioneCache);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		return lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
	}
	
	private SezioneCache creaSezioneCachePerModello(DiscussioneSedutaBean lConvocazioneSedutaBean) throws Exception {
		// Creo la sezione chache coi dati a maschera
		SezioneCache variabiliPerModello = new SezioneCache();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(FMT_STD_DATA);
		
		// Metto tutte le variabile che mi servono		
		if (lConvocazioneSedutaBean.getDatiDiscPrimaConvEditorVerbale() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "discPrimaConvEditorVerbale", lConvocazioneSedutaBean.getDatiDiscPrimaConvEditorVerbale());
		}
		
		if (lConvocazioneSedutaBean.getDatiDiscSecondaConvEditorVerbale() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "discSecondaConvEditorVerbale", lConvocazioneSedutaBean.getDatiDiscSecondaConvEditorVerbale());
		}
		
		if (lConvocazioneSedutaBean.getDtPrimaConvocazione() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "DataOra1aConvocazione", dateFormatter.format(lConvocazioneSedutaBean.getDtPrimaConvocazione()));
		}
		
		if (lConvocazioneSedutaBean.getDtSecondaConvocazione() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "DataOra2aConvocazione", dateFormatter.format(lConvocazioneSedutaBean.getDtSecondaConvocazione()));
		}
		
		if (lConvocazioneSedutaBean.getTipoSessione() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "TipoSessione", lConvocazioneSedutaBean.getTipoSessione());
		}
		
		if (lConvocazioneSedutaBean.getListaArgomentiOdg() != null) {
			putVariabileListaSezioneCache(variabiliPerModello, "ListaAttilista", new XmlUtilitySerializer().createVariabileLista(lConvocazioneSedutaBean.getListaArgomentiOdg()));
		}
		
		if (lConvocazioneSedutaBean.getListaDatiDiscPrimaConvPresenzeAppelloIniziale() != null) {
			putVariabileListaSezioneCache(variabiliPerModello, "Presenzelista", new XmlUtilitySerializer().createVariabileLista(lConvocazioneSedutaBean.getListaDatiDiscPrimaConvPresenzeAppelloIniziale()));
		}
		
		return variabiliPerModello;	
		
	}
	
	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile, String valoreSemplice) {
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}
	
	private void putVariabileListaSezioneCache(SezioneCache sezioneCache, String nomeVariabile, Lista lista) {
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setLista(lista);	
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileLista(nomeVariabile, lista));
		}
	}
	
	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {	
		if(sezioneCache != null && sezioneCache.getVariabile() != null) {
			for(int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if(var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	protected String getIdDelegato(String value) {
		String ret = "";
		if (value != null && !value.equalsIgnoreCase("")){
			String[] listaValoriString = value.split("\\|\\*\\|");
			List<String> listaValoriList  = Arrays.asList(listaValoriString);
			String idDelegato = null;
			if (listaValoriList.size() > 0 ){
				idDelegato = listaValoriList.get(0).trim();
			}
			ret = idDelegato;
		}
		return ret;
	}
	
}