/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetlistdocversionBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Stato;
import it.eng.auriga.ui.module.layout.server.registroDocumenti.bean.RegistroDocumentiBean;
import it.eng.auriga.ui.module.layout.server.registroDocumenti.bean.VersioneDocBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkCoreGetlistdocversion;
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
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="RegistroDocumentiDataSource")
public class RegistroDocumentiDataSource extends AbstractFetchDataSource<RegistroDocumentiBean>{

	@Override
	public PaginatorBean<RegistroDocumentiBean> fetch(AdvancedCriteria criteria, Integer startRow,
			Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String filtroFullText = null;
		String[] checkAttributes = null;	
		String formatoEstremiReg = null;
		Integer searchAllTerms = null;
		Long idFolder = null;
		String advancedFilters = null;
		String customFilters = null;
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1);
		Integer numPagina = null;
		Integer numRighePagina = null;
		Integer online = null;
		String percorsoRicerca = null;
		String flgTipoRicerca = null;		
		String finalita = null;
		
		//Criteri icerca
		String stato = null;
		String oggetto = null;
		
		String colsToReturn = "3;18;33;53;93;219;COD_SOCIETA;COD_CLIENTE;CF_PIVA_CLIENTE;DES_CLIENTE;COD_TIPO_DOC;" +
				"COD_PROCESSO;SEZIONALE;DATA_DOCUMENTO;NRO_DOCUMENTO;TIPO_FATTURA;" +
				"COD_CONTRATTO;COD_FORNITURA;TIPO_SERVIZIO;COD_PDR_POD";
		
		List<RegistroDocumentiBean> data = new ArrayList<RegistroDocumentiBean>();
		List<CriteriPersonalizzati> listCustomFilters = new ArrayList<CriteriPersonalizzati>(); 

		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){				
				if("stato".equals(crit.getFieldName()))
					stato = getTextFilterValue(crit);
				else if("oggetto".equals(crit.getFieldName()))
					oggetto = getTextFilterValue(crit);
				else if("codiceSocieta".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_SOCIETA", crit));
				}
				else if("codiceCliente".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_CLIENTE", crit));
				}  
				else if("codFiscPIVA".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("CF_PIVA_CLIENTE", crit));
				}
				else if("descriioneCliente".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("DES_CLIENTE", crit));
				}
				else if("codiceTipoDocumento".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_TIPO_DOC", crit));
				} 
				else if("codiceProcesso".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_PROCESSO", crit));
				} 
				else if("tipoServizio".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("TIPO_SERVIZIO", crit));
				} 
				else if("dataDocumento".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("DATA_DOCUMENTO", crit));
				} 
				else if("numeroDocumento".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("NRO_DOCUMENTO", crit));
				} 
				else if("tipoFattura".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("TIPO_FATTURA", crit));
				} 
				else if("codiceContratto".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_CONTRATTO", crit));
				} 
				else if("codiceFornitura".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_FORNITURA", crit));
				} 
				else if("sezionale".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("SEZIONALE", crit));
				}
				else if("codicePRD_POD".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("COD_PDR_POD", crit));
				}
			
			}			
		}

		CriteriRicerca criteriRicerca = new CriteriRicerca();
		
		if(StringUtils.isNotBlank(oggetto)) {
			criteriRicerca.setOggettoUD(oggetto);
	    }
		
		if(StringUtils.isNotBlank(stato)) {
			List<Stato> statiDettDoc = new ArrayList<Stato>();
			StringSplitterServer st = new StringSplitterServer(stato, ";");
			while(st.hasMoreTokens()) {
				Stato statoDettDoc = new Stato();
				statoDettDoc.setCodice(st.nextToken());
				statiDettDoc.add(statoDettDoc);
			}	    	
			criteriRicerca.setStatiDettDoc(statiDettDoc);
		}
		
//		criteriRicerca.setIdDocType("9");
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		advancedFilters = lXmlUtilitySerializer.bindXml(criteriRicerca);  
		
		customFilters = lXmlUtilitySerializer.bindXmlList(listCustomFilters);
		
		if(StringUtils.isNotBlank(filtroFullText) && (checkAttributes == null || checkAttributes.length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);			
		} else {

			FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();	
			lFindRepositoryObjectBean.setFiltroFullText(filtroFullText);
			lFindRepositoryObjectBean.setCheckAttributes(checkAttributes);
			lFindRepositoryObjectBean.setFormatoEstremiReg(formatoEstremiReg);
			lFindRepositoryObjectBean.setSearchAllTerms(searchAllTerms);
			lFindRepositoryObjectBean.setFlgUdFolder("U");
			lFindRepositoryObjectBean.setIdFolderSearchIn(idFolder);		
			lFindRepositoryObjectBean.setFlgSubfoderSearchIn(null);
			lFindRepositoryObjectBean.setAdvancedFilters(advancedFilters);
			lFindRepositoryObjectBean.setCustomFilters(customFilters);
			lFindRepositoryObjectBean.setColsOrderBy(colsOrderBy);
			lFindRepositoryObjectBean.setFlgDescOrderBy(flgDescOrderBy);
			lFindRepositoryObjectBean.setFlgSenzaPaginazione(flgSenzaPaginazione);
			lFindRepositoryObjectBean.setNumPagina(numPagina);
			lFindRepositoryObjectBean.setNumRighePagina(numRighePagina);
			lFindRepositoryObjectBean.setOnline(online);
			lFindRepositoryObjectBean.setColsToReturn(colsToReturn);
			lFindRepositoryObjectBean.setPercorsoRicerca(percorsoRicerca);
			lFindRepositoryObjectBean.setFlagTipoRicerca(flgTipoRicerca);
			lFindRepositoryObjectBean.setFinalita(finalita);
			lFindRepositoryObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());

			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findrepositoryobject(
						getLocale(), 
						loginBean, 
						lFindRepositoryObjectBean
				).getList();
			} catch(Exception vhe) {
				throw new StoreException(vhe.getMessage());
			}

			String xmlResultSetOut = (String) resFinder.get(0);

			if (xmlResultSetOut != null){
				StringReader sr = new StringReader(xmlResultSetOut);
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
				if(lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) 
					{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
						RegistroDocumentiBean node = new RegistroDocumentiBean();	        		
						node.setIdUd(v.get(1) != null ? v.get(1) : "");    
						node.setNome(v.get(2));
						node.setOggetto(v.get(17));
						node.setIdDoc(v.get(32));
						node.setStato(v.get(52));   
						node.setNroDocConFile(v.get(92));		        		
						node.setCodiceSocieta(v.get(100));
						node.setCodiceCliente(v.get(101));   
						node.setCodFiscPIVA(v.get(102));
						node.setDescrizioneCliente(v.get(103));
						node.setCodiceTipoDocumento(v.get(104));
						node.setCodiceProcesso(v.get(105));
						node.setSezionale(v.get(106));
						node.setDataDocumento(v.get(107));
						node.setNumeroDocumento(v.get(108));
						node.setTipoFattura(v.get(109));
						node.setCodiceContratto(v.get(110));
						node.setCodiceFornitura(v.get(111));
						node.setTipoServizio(v.get(112));
						node.setCodicePRD_POD(v.get(113));
						node.setStatoConservazione(v.get(218));	
						data.add(node);
					}
				}							
			}			
		}			
		PaginatorBean<RegistroDocumentiBean> lPaginatorBean = new PaginatorBean<RegistroDocumentiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}
	
	
	private String getDecodeCritOperator(String operator){

		String out="";

		if (operator == null)
			return out;

		if(operator.equalsIgnoreCase("iContains")){
			out = "simile a (case-insensitive)";
		}
		else if (operator.equalsIgnoreCase("iEquals")){
			out = "simile a (case-insensitive)";
		}
		else if (operator.equalsIgnoreCase("iStartsWith")){
			out = "simile a (case-insensitive)";
		}
		else if (operator.equalsIgnoreCase("iEndsWith")){
			out = "simile a (case-insensitive)";
		}

		else if (operator.equalsIgnoreCase("equals")){
			out = "uguale";
		}
		else if (operator.equalsIgnoreCase("greaterThan")){
			out = "maggiore";
		}
		else if (operator.equalsIgnoreCase("greaterOrEqual")){
			out = "maggiore o uguale";
		}
		else if (operator.equalsIgnoreCase("lessThan")){
			out = "minore";
		}
		else if (operator.equalsIgnoreCase("lessOrEqual")){
			out = "minore o uguale";
		}
		else if (operator.equalsIgnoreCase("betweenInclusive")){
			out = "tra";
		}

		return out;
	}
	
	@Override
	public RegistroDocumentiBean get(RegistroDocumentiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		DmpkCoreGetlistdocversionBean input = new DmpkCoreGetlistdocversionBean();
		input.setIddocin(StringUtils.isNotBlank(bean.getIdDoc()) ? new BigDecimal(bean.getIdDoc()) : null);
		input.setFlgignoredellogin(new Integer(1));		
		
		DmpkCoreGetlistdocversion store = new DmpkCoreGetlistdocversion();
	
		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(loginBean.getSchema());
		StoreResultBean<DmpkCoreGetlistdocversionBean> output = store.execute(getLocale(), schemaBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getVersionlistxmlout())) {
			List<VersioneDocBean> listaVersioni = XmlListaUtility.recuperaLista(output.getResultBean().getVersionlistxmlout(), VersioneDocBean.class); 			
			if(listaVersioni != null) {
				for(int i = 0; i < listaVersioni.size(); i++) {
					VersioneDocBean lVersioneDocBean = listaVersioni.get(i);
					lVersioneDocBean.setRemoteUri(true);
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					lMimeTypeFirmaBean.setImpronta(lVersioneDocBean.getImpronta());					
					lMimeTypeFirmaBean.setCorrectFileName(lVersioneDocBean.getNomeFile());
					lMimeTypeFirmaBean.setFirmato(lVersioneDocBean.getFlgFirmato() != null && "1".equals(lVersioneDocBean.getFlgFirmato()));
					lMimeTypeFirmaBean.setFirmaValida(lVersioneDocBean.getFlgFirmato() != null && "1".equals(lVersioneDocBean.getFlgFirmato()));		
					lMimeTypeFirmaBean.setConvertibile(true);
					lMimeTypeFirmaBean.setDaScansione(false);
					lMimeTypeFirmaBean.setMimetype(lVersioneDocBean.getMimetype());
					lMimeTypeFirmaBean.setBytes(StringUtils.isNotBlank(lVersioneDocBean.getDimensione()) ? Long.parseLong(lVersioneDocBean.getDimensione()) : 0);
					if (lMimeTypeFirmaBean.isFirmato()){
						lMimeTypeFirmaBean.setTipoFirma(lVersioneDocBean.getNomeFile().toUpperCase().endsWith("P7M")||lVersioneDocBean.getNomeFile().toUpperCase().endsWith("TSD")?"CAdES_BES":"PDF");
						lMimeTypeFirmaBean.setFirmatari(null);
					}		
					lVersioneDocBean.setInfoFile(lMimeTypeFirmaBean);
				}
			}			
			bean.setListaVersioni(listaVersioni);
		}
		
//		RecuperaDocumentoInBean recuperaDocumentoInBean = new RecuperaDocumentoInBean();
//		recuperaDocumentoInBean.setIdUd(new BigDecimal(bean.getIdUd()));
//
//		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
//		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, recuperaDocumentoInBean);
//		if(lRecuperaDocumentoOutBean.isInError()) {
//			throw new StoreException(lRecuperaDocumentoOutBean.getDefaultMessage(), lRecuperaDocumentoOutBean.getErrorCode(), lRecuperaDocumentoOutBean.getErrorContext());
//		}
//		
//		DocumentoXmlOutBean documentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
//		
//		ProtocolloUtility protocolloUtility = new ProtocolloUtility(getSession());
//		ProtocollazioneBean protocollazioneBean = new ProtocollazioneBean();
//		protocolloUtility.recuperaFilesFromDoc(documentoXmlOutBean, protocollazioneBean, null);
//		
//		bean.setUrifile(documentoXmlOutBean.getFilePrimario().getUri());
//		bean.setNomeFile(documentoXmlOutBean.getFilePrimario().getDisplayFilename());
//		bean.setRemoteUri(true);	
//		bean.setInfoFile(protocollazioneBean.getInfoFile());	

		return bean;		
	}
	
}