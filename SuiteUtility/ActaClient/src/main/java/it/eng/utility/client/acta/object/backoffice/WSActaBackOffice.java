package it.eng.utility.client.acta.object.backoffice;

// it.eng.utility.client.acta
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.doqui.acta.acaris.backoffice.BackOfficeService;
import it.doqui.acta.acaris.backoffice.BackOfficeServicePort;
import it.doqui.acta.acaris.backoffice.ClientApplicationInfo;
import it.doqui.acta.acaris.backoffice.CodiceFiscaleType;
import it.doqui.acta.acaris.backoffice.ObjectIdType;
import it.doqui.acta.acaris.backoffice.PagingResponseType;
import it.doqui.acta.acaris.backoffice.PrincipalExtResponseType;
import it.eng.utility.client.acta.WSActa;
import it.eng.utility.client.acta.bean.NodoSmistamento;

public class WSActaBackOffice extends WSActa {

	private static final Logger logger = LoggerFactory.getLogger(WSActaBackOffice.class);

	protected String endpointBackOffice;

	public List<String> getPrincipalsExt(String repoId, String aooCode/*, String structurCode*/) {
		List<String> principalIdList = new ArrayList<String>();
		try {
			URL url = new URL(getEndpointBackOffice());
			final BackOfficeService backOfficeService = new BackOfficeService(url);
			BackOfficeServicePort backOfficeServicePort = backOfficeService.getBackOfficeServicePort();
			((BindingProvider) backOfficeServicePort).getBinding();
            setTimeout(backOfficeServicePort);
            
			final CodiceFiscaleType codiceFiscale = new CodiceFiscaleType();
			codiceFiscale.setValue(getFiscalCodeUtente());
			final ClientApplicationInfo clientApplicationInfo = new ClientApplicationInfo();
			clientApplicationInfo.setAppKey(getAppKey());
			ObjectIdType repo = new ObjectIdType();
			repo.setValue(repoId);
			List<PrincipalExtResponseType> principalExts = backOfficeServicePort.getPrincipalExt(repo, codiceFiscale, null, null, null, clientApplicationInfo);

			if (principalExts != null) {
				logger.debug("Numero totale di principal trovati: " + principalExts.size());
				logger.debug("Filtro per codice Aoo " + aooCode /*+ " e struttura " + structurCode*/);
				for (PrincipalExtResponseType principalExt : principalExts) {
					// logger.debug(principalExt.getPrincipalId().getValue() );
					// logger.debug("AOOO" + principalExt.getUtente().getAoo().getCodice() + " " + principalExt.getUtente().getAoo().getDescrizione());
					// logger.debug(principalExt.getUtente().getStruttura().getCodice() + " " + principalExt.getUtente().getStruttura().getDescrizione()
					// );
					// logger.debug(principalExt.getUtente().getNodo().getCodice() + " " + principalExt.getUtente().getNodo().getDescrizione() );
					// logger.debug("\n");
					if (principalExt.getUtente().getAoo().getCodice().equalsIgnoreCase(aooCode) 
							/*&& principalExt.getUtente().getStruttura().getCodice().equalsIgnoreCase(structurCode) 
							* && principalExt.getUtente().getNodo().getCodice().equalsIgnoreCase( nodeCode )
							*/) {
						principalIdList.add(principalExt.getPrincipalId().getValue());
						// logger.debug("PrincipalId: " + principalId);
					}
				}

			}

			// logger.debug("PrincipalId: " + principalId);
			return principalIdList;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.backoffice.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getPrincipalsExt(String repoId, String aooCode, String structurCode, String nodeCode) {
		List<String> principalIdList = new ArrayList<String>();

		try {
			URL url = new URL(getEndpointBackOffice());
			final BackOfficeService backOfficeService = new BackOfficeService(url);
			BackOfficeServicePort backOfficeServicePort = backOfficeService.getBackOfficeServicePort();
			((BindingProvider) backOfficeServicePort).getBinding();
			setTimeout(backOfficeServicePort);

			final CodiceFiscaleType codiceFiscale = new CodiceFiscaleType();
			codiceFiscale.setValue(getFiscalCodeUtente());
			final ClientApplicationInfo clientApplicationInfo = new ClientApplicationInfo();
			clientApplicationInfo.setAppKey(getAppKey());
			ObjectIdType repo = new ObjectIdType();
			repo.setValue(repoId);
			List<PrincipalExtResponseType> principalExts = backOfficeServicePort.getPrincipalExt(repo, codiceFiscale, null, null, null, clientApplicationInfo);

			if (principalExts != null) {
				logger.debug("Numero totale di principal trovati: " + principalExts.size());
				logger.debug("Filtro per codice Aoo " + aooCode + " e struttura " + structurCode);
				for (PrincipalExtResponseType principalExt : principalExts) {
					// logger.debug(principalExt.getPrincipalId().getValue() );
					// logger.debug(principalExt.getUtente().getAoo().getCodice() + " " + principalExt.getUtente().getAoo().getDescrizione());
					// logger.debug(principalExt.getUtente().getStruttura().getCodice() + " " + principalExt.getUtente().getStruttura().getDescrizione()
					// );
					// logger.debug(principalExt.getUtente().getNodo().getCodice() + " " + principalExt.getUtente().getNodo().getDescrizione() );
					// logger.debug("\n");
					if (principalExt.getUtente().getAoo().getCodice().equalsIgnoreCase(aooCode)
							&& principalExt.getUtente().getStruttura().getCodice().equalsIgnoreCase(structurCode)
							&& principalExt.getUtente().getNodo().getCodice().equalsIgnoreCase(nodeCode)) {
						principalIdList.add(principalExt.getPrincipalId().getValue());
						// logger.debug("PrincipalId: " + principalId);
					}
				}

			}

			// logger.debug("PrincipalId: " + principalId);
			return principalIdList;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.backoffice.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<NodoSmistamento> getNodiDestinatarioSmistamento(String repoId, String principalId, String parentNodeId, String codiceStrutturaSmistamento) {
		URL url;
		List<NodoSmistamento> nodi = new ArrayList<NodoSmistamento>();
		try {
			url = new URL(getEndpointBackOffice());
			final BackOfficeService backOfficeService = new BackOfficeService(url);
			BackOfficeServicePort backOfficeServicePort = backOfficeService.getBackOfficeServicePort();
			((BindingProvider) backOfficeServicePort).getBinding();
			setTimeout(backOfficeServicePort);

			it.doqui.acta.acaris.backoffice.ObjectIdType repo = new it.doqui.acta.acaris.backoffice.ObjectIdType();
			repo.setValue(repoId);

			it.doqui.acta.acaris.backoffice.PrincipalIdType principal = new it.doqui.acta.acaris.backoffice.PrincipalIdType();
			principal.setValue(principalId);

			final it.doqui.acta.acaris.backoffice.QueryableObjectType queryableObject = new it.doqui.acta.acaris.backoffice.QueryableObjectType();
			logger.debug("Cerco l'oggetto " + "AooStrNodoView");
			queryableObject.setObject("AooStrNodoView");

			final it.doqui.acta.acaris.backoffice.PropertyFilterType propertyFilter = new it.doqui.acta.acaris.backoffice.PropertyFilterType();
			propertyFilter.setFilterType(it.doqui.acta.acaris.backoffice.EnumPropertyFilter.ALL);

			it.doqui.acta.acaris.backoffice.NavigationConditionInfoType navigationConditionInfo = null;
			if (parentNodeId != null) {
				navigationConditionInfo = new it.doqui.acta.acaris.backoffice.NavigationConditionInfoType();
				it.doqui.acta.acaris.backoffice.ObjectIdType parentNode = new it.doqui.acta.acaris.backoffice.ObjectIdType();
				logger.debug("Aggiungo la condizione sul nodo padre " + parentNodeId);
				parentNode.setValue(parentNodeId);
				navigationConditionInfo.setParentNodeId(parentNode);
			}

			final List<it.doqui.acta.acaris.backoffice.QueryConditionType> queryConditions = new ArrayList<it.doqui.acta.acaris.backoffice.QueryConditionType>();
			if (codiceStrutturaSmistamento != null) {
				it.doqui.acta.acaris.backoffice.QueryConditionType queryCondition = new it.doqui.acta.acaris.backoffice.QueryConditionType();
				queryCondition.setOperator(it.doqui.acta.acaris.backoffice.EnumQueryOperator.EQUALS);
				logger.debug("Aggiungo la condizione codiceStruttura = " + codiceStrutturaSmistamento);
				queryCondition.setPropertyName("codiceStruttura");
				queryCondition.setValue(codiceStrutturaSmistamento);
				queryConditions.add(queryCondition);
			}

			it.doqui.acta.acaris.backoffice.PagingResponseType pagingResponse = backOfficeServicePort.query(repo, principal, queryableObject, propertyFilter,
					queryConditions, navigationConditionInfo, null, null);
			if (pagingResponse != null) {
				List<it.doqui.acta.acaris.backoffice.ObjectResponseType> objectsResponse = pagingResponse.getObjects();
				logger.debug("Numero totale risultati: " + objectsResponse.size());
				for (it.doqui.acta.acaris.backoffice.ObjectResponseType objectResponse : objectsResponse) {
					// logger.debug("ObjectId: " + objectResponse.getObjectId().getValue());
					NodoSmistamento nodo = new NodoSmistamento();
					List<it.doqui.acta.acaris.backoffice.PropertyType> properties = objectResponse.getProperties();
					for (it.doqui.acta.acaris.backoffice.PropertyType p : properties) {
						// logger.debug(p.getQueryName().getPropertyName() + " " + p.getValue().getContent());
						if (p.getQueryName() != null && p.getQueryName().getPropertyName() != null
								&& p.getQueryName().getPropertyName().equalsIgnoreCase("idNodo")) {
							nodo.setIdNodo(p.getValue().getContent().get(0));
							logger.debug("Id Nodo smistamento: " + nodo.getIdNodo() );
						}
						if (p.getQueryName() != null && p.getQueryName().getPropertyName() != null
								&& p.getQueryName().getPropertyName().equalsIgnoreCase("codiceNodo")) {
							nodo.setCodiceNodo(p.getValue().getContent().get(0));
							logger.debug("Codice Nodo smistamento: " + nodo.getCodiceNodo() );
						}
					}
					String descrizioneNodo = getDescrizioneNodo(repoId, principalId, nodo.getCodiceNodo() );
					logger.debug("Descrizione Nodo smistamento: " + descrizioneNodo );
					if( descrizioneNodo!=null ){
						nodo.setCodiceNodo( nodo.getCodiceNodo() + "- " + descrizioneNodo);
					}
					nodi.add(nodo);
					// logger.debug("\n");
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.backoffice.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodi;
	}
	
	public String getDescrizioneNodo(String repoId, String principalId, String codiceNodo ){
		String descrizioneNodo = null;
		URL url;
		try {
			url = new URL(getEndpointBackOffice());
			final BackOfficeService backOfficeService = new BackOfficeService(url);
	        BackOfficeServicePort backOfficeServicePort = backOfficeService.getBackOfficeServicePort();
	        ((BindingProvider) backOfficeServicePort).getBinding();
	       
	        ObjectIdType repo = new ObjectIdType();
			repo.setValue(repoId);
			
			it.doqui.acta.acaris.backoffice.PrincipalIdType principal = new it.doqui.acta.acaris.backoffice.PrincipalIdType();
			principal.setValue( principalId );
			
        	it.doqui.acta.acaris.backoffice.QueryableObjectType queryableObject = new it.doqui.acta.acaris.backoffice.QueryableObjectType();
	        logger.debug("Cerco l'oggetto " + it.doqui.acta.acaris.backoffice.EnumObjectType.NODO_PROPERTIES_TYPE.value());
	        queryableObject.setObject(it.doqui.acta.acaris.backoffice.EnumObjectType.NODO_PROPERTIES_TYPE.value());
		        
	        final it.doqui.acta.acaris.backoffice.PropertyFilterType propertyFilter = new it.doqui.acta.acaris.backoffice.PropertyFilterType();
			propertyFilter.setFilterType( it.doqui.acta.acaris.backoffice.EnumPropertyFilter.ALL );
	        	
			List<it.doqui.acta.acaris.backoffice.QueryConditionType> queryConditions = new ArrayList<it.doqui.acta.acaris.backoffice.QueryConditionType>();
			it.doqui.acta.acaris.backoffice.QueryConditionType queryCondition = new it.doqui.acta.acaris.backoffice.QueryConditionType();
			queryCondition.setOperator(it.doqui.acta.acaris.backoffice.EnumQueryOperator.EQUALS);
			queryCondition.setPropertyName("codice");
			logger.debug("Aggiungo la condizione codice = " + codiceNodo);
			queryCondition.setValue(codiceNodo);
			queryConditions.add(queryCondition);
			
			it.doqui.acta.acaris.backoffice.NavigationConditionInfoType navigationConditionInfo = new it.doqui.acta.acaris.backoffice.NavigationConditionInfoType();
				
	        PagingResponseType responseQueryNodo = backOfficeServicePort.query(repo, principal, queryableObject, propertyFilter, queryConditions, navigationConditionInfo, null, null);
	        String objNodo = null;
        	if( responseQueryNodo!=null ){
            	List<it.doqui.acta.acaris.backoffice.ObjectResponseType> objectsResponse = responseQueryNodo.getObjects();
            	logger.debug("Numero risultati: " + objectsResponse.size());
            	for(it.doqui.acta.acaris.backoffice.ObjectResponseType objectResponse : objectsResponse){
            		//logger.debug("ObjectId: " + objectResponse.getObjectId().getValue());
            		objNodo = objectResponse.getObjectId().getValue();
            		List<it.doqui.acta.acaris.backoffice.PropertyType> properties = objectResponse.getProperties();
            		for(it.doqui.acta.acaris.backoffice.PropertyType p : properties){
            			//logger.debug(p.getQueryName().getPropertyName() + " " + p.getValue().getContent());
            			if( p.getQueryName()!=null && p.getQueryName().getPropertyName()!=null && 
                			p.getQueryName().getPropertyName().equalsIgnoreCase("descNodo")){
            				descrizioneNodo = p.getValue().getContent().get(0);	
                		}
            		}
            		//logger.debug("\n");
            	}
            }
	        	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (it.doqui.acta.acaris.backoffice.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return descrizioneNodo;
	}

	public String getEndpointBackOffice() {
		return endpointBackOffice;
	}

	public void setEndpointBackOffice(String endpointBackOffice) {
		this.endpointBackOffice = endpointBackOffice;
	}

}
