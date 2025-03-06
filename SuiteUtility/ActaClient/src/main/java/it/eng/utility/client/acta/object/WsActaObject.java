package it.eng.utility.client.acta.object;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.doqui.acta.acaris.object.AcarisException;
import it.doqui.acta.acaris.object.EnumObjectType;
import it.doqui.acta.acaris.object.EnumPropertyFilter;
import it.doqui.acta.acaris.object.EnumQueryOperator;
import it.doqui.acta.acaris.object.NavigationConditionInfoType;
import it.doqui.acta.acaris.object.ObjectResponseType;
import it.doqui.acta.acaris.object.ObjectService;
import it.doqui.acta.acaris.object.ObjectServicePort;
import it.doqui.acta.acaris.object.QueryNameType;
import it.eng.utility.client.acta.WSActa;
import it.eng.utility.client.acta.bean.VoceTitolarioBean;

public class WsActaObject extends WSActa {

	private static final Logger logger = LoggerFactory.getLogger(WsActaObject.class);

	protected String endpointObject;

	public VoceTitolarioBean getVoceTitolario(String repoId, String principalId, String parentNodeId, String codiceVoceTitolario, String dbKeyTitolario) {
		
		StringTokenizer codiciVoci = new StringTokenizer(codiceVoceTitolario, ".");
		
		VoceTitolarioBean voceTitolario = null;
		if( codiciVoci!=null){
			String dbKeyPadre = null;
			while (codiciVoci.hasMoreElements()) {
				String codiceVoce = (String) codiciVoci.nextElement();
				logger.debug("codice voce titolario " + codiceVoce);
				voceTitolario = getVoceTitolario(repoId, principalId, parentNodeId, codiceVoce, dbKeyPadre, dbKeyTitolario);
				if( voceTitolario!=null ){
					dbKeyPadre = voceTitolario.getDbKeyVoce();
				}
			}
		}
		
		return voceTitolario;
	}
	
	public VoceTitolarioBean getVoceTitolario(String repoId, String principalId, String parentNodeId, String codiceVoce, String dbKeyPadre, String dbKeyTitolario) {
		VoceTitolarioBean voceTitolario = null;
		try {
			URL url = new URL(getEndpointObject());
			final ObjectService objectService = new ObjectService(url);
			ObjectServicePort objectServicePort = objectService.getObjectServicePort();
			((BindingProvider) objectServicePort).getBinding();
			setTimeout(objectServicePort);

			it.doqui.acta.acaris.object.ObjectIdType repo = new it.doqui.acta.acaris.object.ObjectIdType();
			repo.setValue(repoId);

			it.doqui.acta.acaris.object.PrincipalIdType principal = new it.doqui.acta.acaris.object.PrincipalIdType();
			principal.setValue(principalId);

			final it.doqui.acta.acaris.object.QueryableObjectType queryableObject = new it.doqui.acta.acaris.object.QueryableObjectType();
			logger.debug("Cerco l'oggetto " + EnumObjectType.VOCE_PROPERTIES_TYPE.value());
			queryableObject.setObject(EnumObjectType.VOCE_PROPERTIES_TYPE.value());

			final it.doqui.acta.acaris.object.PropertyFilterType propertyFilter = new it.doqui.acta.acaris.object.PropertyFilterType();
			propertyFilter.setFilterType(EnumPropertyFilter.LIST);
			QueryNameType propertyName = new QueryNameType();
			propertyName.setPropertyName("dbKeyPadre");
			propertyName.setClassName("VocePropertyType");
			propertyFilter.getPropertyList().add(propertyName);
			QueryNameType propertyName1 = new QueryNameType();
			propertyName1.setPropertyName("dbKey");
			propertyName1.setClassName("VocePropertyType");
			propertyFilter.getPropertyList().add(propertyName1);
			QueryNameType propertyName2 = new QueryNameType();
			propertyName2.setPropertyName("codice");
			propertyName2.setClassName("VocePropertyType");
			propertyFilter.getPropertyList().add(propertyName2);
			QueryNameType propertyName3 = new QueryNameType();
			propertyName3.setPropertyName("descrizione");
			propertyName3.setClassName("VocePropertyType");
			propertyFilter.getPropertyList().add(propertyName3);
			QueryNameType propertyName4 = new QueryNameType();
			propertyName4.setPropertyName("dbKeyTitolario");
			propertyName4.setClassName("VocePropertyType");
			propertyFilter.getPropertyList().add(propertyName4);

			NavigationConditionInfoType navigationConditionInfo = null;
			if (parentNodeId != null) {
				navigationConditionInfo = new NavigationConditionInfoType();
				it.doqui.acta.acaris.object.ObjectIdType parentNode = new it.doqui.acta.acaris.object.ObjectIdType();
				parentNode.setValue(parentNodeId);
				navigationConditionInfo.setParentNodeId(parentNode);
			}

			final List<it.doqui.acta.acaris.object.QueryConditionType> queryConditions = new ArrayList<it.doqui.acta.acaris.object.QueryConditionType>();
			if (codiceVoce != null) {
				it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("codice");
				logger.debug("Aggiungo la condizione codice=" + codiceVoce);
				queryCondition.setValue(codiceVoce);
				queryConditions.add(queryCondition);
			}
			if (dbKeyPadre != null) {
				it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("dbKeyPadre");
				logger.debug("Aggiungo la condizione dbKeyPadre=" + dbKeyPadre);
				queryCondition.setValue(dbKeyPadre);
				queryConditions.add(queryCondition);
			} else {
				it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("dbKeyPadre");
				logger.debug("Aggiungo la condizione dbKeyPadre=" + dbKeyPadre);
				queryCondition.setValue("");
				//queryConditions.add(queryCondition);
			}
			if (dbKeyTitolario != null) {
				it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("dbKeyTitolario");
				logger.debug("Aggiungo la condizione dbKeyTitolario=" + dbKeyTitolario);
				queryCondition.setValue(dbKeyTitolario);
				queryConditions.add(queryCondition);
			}

			it.doqui.acta.acaris.object.PagingResponseType pagingResponse = objectServicePort.query(repo, principal, queryableObject, propertyFilter,
					queryConditions, navigationConditionInfo, null, null);
			if (pagingResponse != null) {
				List<ObjectResponseType> objectsResponse = pagingResponse.getObjects();
				logger.debug("Numero risultati: " + objectsResponse.size());
				// logger.debug("\n");
				
				boolean voceRadiceTrovata = false;
				for (ObjectResponseType objectResponse : objectsResponse) {
					if( !voceRadiceTrovata ){
						// logger.debug("ObjectId: " + objectResponse.getObjectId().getValue());
						String idVoceTitolario = objectResponse.getObjectId().getValue();
						List<it.doqui.acta.acaris.object.PropertyType> properties = objectResponse.getProperties();
						voceTitolario = new VoceTitolarioBean();
						voceTitolario.setCodiceVoce(codiceVoce);
						voceTitolario.setIdVoce(idVoceTitolario);
						for (it.doqui.acta.acaris.object.PropertyType p : properties) {
							// logger.debug(p.getQueryName().getPropertyName() + " " + p.getValue().getContent());
							 if( p.getQueryName()!=null && p.getQueryName().getPropertyName()!=null && 
		            					p.getQueryName().getPropertyName().equalsIgnoreCase("codidbKeyceNodo")){
								 if( p.getValue().getContent()!=null && p.getValue().getContent().size()>0 )
									 voceTitolario.setDbKeyVoce( p.getValue().getContent().get(0) );
		            		}
							 if( p.getQueryName()!=null && p.getQueryName().getPropertyName()!=null && 
		            					p.getQueryName().getPropertyName().equalsIgnoreCase("dbKeyPadre")){
								 if( p.getValue().getContent()!=null && p.getValue().getContent().size()>0 )
									 voceTitolario.setDbKeyPadreVoce( p.getValue().getContent().get(0) );
		            		}
							 if( p.getQueryName()!=null && p.getQueryName().getPropertyName()!=null && 
		            					p.getQueryName().getPropertyName().equalsIgnoreCase("dbKey")){
								 if( p.getValue().getContent()!=null && p.getValue().getContent().size()>0 )
									 voceTitolario.setDbKeyVoce( p.getValue().getContent().get(0) );
		            		}
							 if( p.getQueryName()!=null && p.getQueryName().getPropertyName()!=null && 
		            					p.getQueryName().getPropertyName().equalsIgnoreCase("dbKeyTitolario")){
								 if( p.getValue().getContent()!=null && p.getValue().getContent().size()>0 )
									 voceTitolario.setDbKeyTitolario( p.getValue().getContent().get(0) );
		            		}
	            		
						}
						
						if( dbKeyPadre==null && voceTitolario.getDbKeyPadreVoce()==null )
							voceRadiceTrovata = true;
						// logger.debug("\n");
					}
				}
				logger.debug("Voce titolario trovata: " + voceTitolario );
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.object.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voceTitolario;
	}

	public String getFascicoloDossier(String repoId, String principalId, String parentNodeId, String dbKeyVoce,
			String codiceFascicoloDossier, String suffissoCodiceFascicoloDossier) {
		String idFascicoloDossier = null;
		try {
			URL url = new URL(getEndpointObject());
			final ObjectService objectService = new ObjectService(url);
			ObjectServicePort objectServicePort = objectService.getObjectServicePort();
			((BindingProvider) objectServicePort).getBinding();
			setTimeout(objectServicePort);

			it.doqui.acta.acaris.object.ObjectIdType repo = new it.doqui.acta.acaris.object.ObjectIdType();
			repo.setValue(repoId);

			it.doqui.acta.acaris.object.PrincipalIdType principal = new it.doqui.acta.acaris.object.PrincipalIdType();
			principal.setValue(principalId);

			final it.doqui.acta.acaris.object.PropertyFilterType propertyFilter = new it.doqui.acta.acaris.object.PropertyFilterType();
			propertyFilter.setFilterType(EnumPropertyFilter.LIST);
			QueryNameType propertyName = new QueryNameType();
			propertyName.setPropertyName("indiceClassificazioneEstesa");
			propertyName.setClassName("indiceClassificazioneEstesa");
			propertyFilter.getPropertyList().add(propertyName);
			QueryNameType propertyName2 = new QueryNameType();
			propertyName2.setPropertyName("idVoce");
			propertyName2.setClassName("idVoce");
			propertyFilter.getPropertyList().add(propertyName2);

			NavigationConditionInfoType navigationConditionInfo = null;
			if (parentNodeId != null) {
				navigationConditionInfo = new NavigationConditionInfoType();
				it.doqui.acta.acaris.object.ObjectIdType parentNode = new it.doqui.acta.acaris.object.ObjectIdType();
				logger.debug("Aggiungo la condizione sul nodo padre " + parentNodeId);
				parentNode.setValue(parentNodeId);
				navigationConditionInfo.setParentNodeId(parentNode);
				navigationConditionInfo.setLimitToChildren(false);
			}

			final List<it.doqui.acta.acaris.object.QueryConditionType> queryConditions = new ArrayList<it.doqui.acta.acaris.object.QueryConditionType>();
			if (codiceFascicoloDossier != null) {
				it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("codice");
				logger.debug("Aggiungo la condizione codice = " + codiceFascicoloDossier);
				queryCondition.setValue(codiceFascicoloDossier);
				queryConditions.add(queryCondition);
			}
			if (suffissoCodiceFascicoloDossier != null) {
				it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				logger.debug("Aggiungo la condizione suffissoCodice = " + suffissoCodiceFascicoloDossier);
				queryCondition.setPropertyName("suffissoCodice");
				queryCondition.setValue(suffissoCodiceFascicoloDossier);
				queryConditions.add(queryCondition);
			}

			it.doqui.acta.acaris.object.QueryableObjectType queryableObject = new it.doqui.acta.acaris.object.QueryableObjectType();
			logger.debug("Cerco l'oggetto " + EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE.value());
			queryableObject.setObject(EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE.value());// */"AggregazionePropertiesType");

			idFascicoloDossier = cercaFascicoloDossier(objectServicePort, repo, principal, queryableObject, propertyFilter, queryConditions,
					navigationConditionInfo, dbKeyVoce);

			if (idFascicoloDossier == null) {
				logger.debug("Cerco l'oggetto " + EnumObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE.value());
				queryableObject.setObject(EnumObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE.value());// */"AggregazionePropertiesType");
				idFascicoloDossier = cercaFascicoloDossier(objectServicePort, repo, principal, queryableObject, propertyFilter, queryConditions,
						navigationConditionInfo, dbKeyVoce);
			}

			if (idFascicoloDossier == null) {
				logger.debug("Cerco l'oggetto " + EnumObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE.value());
				queryableObject.setObject(EnumObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE.value());// */"AggregazionePropertiesType");
				idFascicoloDossier = cercaFascicoloDossier(objectServicePort, repo, principal, queryableObject, propertyFilter, queryConditions,
						navigationConditionInfo, dbKeyVoce);
			}

			/*
			 * if(idFascicoloDossier==null){ logger.debug("Cerco l'oggetto " + EnumObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE.value());
			 * queryableObject.setObject(EnumObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE.value()); idFascicoloDossier =
			 * cercaFascicoloDossier(objectServicePort, repo, principal, queryableObject, propertyFilter, queryConditions, navigationConditionInfo); }
			 */

			if (idFascicoloDossier == null) {
				logger.debug("Cerco l'oggetto " + EnumObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE.value());
				queryableObject.setObject(EnumObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE.value());// */"AggregazionePropertiesType");
				idFascicoloDossier = cercaFascicoloDossier(objectServicePort, repo, principal, queryableObject, propertyFilter, queryConditions,
						navigationConditionInfo, dbKeyVoce);
			}

			/*
			 * if(idFascicoloDossier==null){ logger.debug("Cerco l'oggetto " + EnumObjectType.FASCICOLO_STD_PROPERTIES_TYPE.value());
			 * queryableObject.setObject(EnumObjectType.FASCICOLO_STD_PROPERTIES_TYPE.value()); idFascicoloDossier = cercaFascicoloDossier(objectServicePort,
			 * repo, principal, queryableObject, propertyFilter, queryConditions, navigationConditionInfo); }
			 */

			/*
			 * if(idFascicoloDossier==null){ logger.debug("Cerco l'oggetto " + EnumObjectType.FASCICOLO_TEMPORANEO_PROPERTIES_TYPE.value());
			 * queryableObject.setObject(EnumObjectType.FASCICOLO_TEMPORANEO_PROPERTIES_TYPE.value()); idFascicoloDossier =
			 * cercaFascicoloDossier(objectServicePort, repo, principal, queryableObject, propertyFilter, queryConditions, navigationConditionInfo); }
			 */

			if (idFascicoloDossier == null) {
				logger.debug("Cerco l'oggetto " + EnumObjectType.DOSSIER_PROPERTIES_TYPE.value());
				queryableObject.setObject(EnumObjectType.DOSSIER_PROPERTIES_TYPE.value());// */"AggregazionePropertiesType");
				idFascicoloDossier = cercaFascicoloDossier(objectServicePort, repo, principal, queryableObject, propertyFilter, queryConditions,
						navigationConditionInfo, dbKeyVoce);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.object.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idFascicoloDossier;
	}
	
	public String getSottofascicolo(String repoId, String principalId, String parentNodeId, String dbKeyVoce,
			String numeroSottofascicolo) {
		String idSottofascicolo = null;
		try {
			URL url = new URL(getEndpointObject());
			final ObjectService objectService = new ObjectService(url);
			ObjectServicePort objectServicePort = objectService.getObjectServicePort();
			((BindingProvider) objectServicePort).getBinding();
			setTimeout(objectServicePort);

			it.doqui.acta.acaris.object.ObjectIdType repo = new it.doqui.acta.acaris.object.ObjectIdType();
			repo.setValue(repoId);

			it.doqui.acta.acaris.object.PrincipalIdType principal = new it.doqui.acta.acaris.object.PrincipalIdType();
			principal.setValue(principalId);

			final it.doqui.acta.acaris.object.PropertyFilterType propertyFilter = new it.doqui.acta.acaris.object.PropertyFilterType();
			propertyFilter.setFilterType(EnumPropertyFilter.LIST);
			QueryNameType propertyName = new QueryNameType();
			propertyName.setPropertyName("indiceClassificazioneEstesa");
			propertyName.setClassName("indiceClassificazioneEstesa");
			propertyFilter.getPropertyList().add(propertyName);
			QueryNameType propertyName2 = new QueryNameType();
			propertyName2.setPropertyName("idVoce");
			propertyName2.setClassName("idVoce");
			propertyFilter.getPropertyList().add(propertyName2);
			
			NavigationConditionInfoType navigationConditionInfo = null;
			if (parentNodeId != null) {
				navigationConditionInfo = new NavigationConditionInfoType();
				it.doqui.acta.acaris.object.ObjectIdType parentNode = new it.doqui.acta.acaris.object.ObjectIdType();
				logger.debug("Aggiungo la condizione sul nodo padre " + parentNodeId);
				parentNode.setValue(parentNodeId);
				navigationConditionInfo.setParentNodeId(parentNode);
				navigationConditionInfo.setLimitToChildren(false);
			}

			final List<it.doqui.acta.acaris.object.QueryConditionType> queryConditions = new ArrayList<it.doqui.acta.acaris.object.QueryConditionType>();
			if (numeroSottofascicolo != null) {
				it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("codice");
				logger.debug("Aggiungo la condizione numero = " + numeroSottofascicolo);
				queryCondition.setValue(numeroSottofascicolo);
				queryConditions.add(queryCondition);
			}
			
			it.doqui.acta.acaris.object.QueryableObjectType queryableObject = new it.doqui.acta.acaris.object.QueryableObjectType();
			logger.debug("Cerco l'oggetto " + EnumObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE.value());
			queryableObject.setObject(EnumObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE.value());// */"AggregazionePropertiesType");

			idSottofascicolo = cercaFascicoloDossier(objectServicePort, repo, principal, queryableObject, propertyFilter, queryConditions,
					navigationConditionInfo, dbKeyVoce);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.object.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idSottofascicolo;
	}
	
	private String cercaFascicoloDossier(ObjectServicePort objectServicePort, it.doqui.acta.acaris.object.ObjectIdType repo,
			it.doqui.acta.acaris.object.PrincipalIdType principal, it.doqui.acta.acaris.object.QueryableObjectType queryableObject,
			it.doqui.acta.acaris.object.PropertyFilterType propertyFilter, List<it.doqui.acta.acaris.object.QueryConditionType> queryConditions,
			NavigationConditionInfoType navigationConditionInfo, String idVoce) throws AcarisException {
		String idFascicoloDossier = null;
		it.doqui.acta.acaris.object.PagingResponseType pagingResponse = objectServicePort.query(repo, principal, queryableObject, propertyFilter,
				queryConditions, navigationConditionInfo, null, null);
		if (pagingResponse != null) {
			List<ObjectResponseType> objectsResponse = pagingResponse.getObjects();
			logger.debug("Numero totale di risultati: " + objectsResponse.size());
			for (ObjectResponseType objectResponse : objectsResponse) {
				// logger.debug("ObjectId: " + objectResponse.getObjectId().getValue());
				List<it.doqui.acta.acaris.object.PropertyType> properties = objectResponse.getProperties();
				for (it.doqui.acta.acaris.object.PropertyType p : properties) {
					//logger.debug(p.getQueryName().getPropertyName() + " " + p.getValue().getContent());
					if ("idVoce".equals(p.getQueryName().getPropertyName())) {
                        String valore = p.getValue().getContent().get(0);
                        logger.debug("valore " + valore);
                    	if (idVoce.equals(valore)) {
                        	logger.debug("Trovato match in voce " + idVoce);
                        	idFascicoloDossier = objectResponse.getObjectId().getValue();
            			}
                    }
				}
			}
		}
		return idFascicoloDossier;
	}

	public boolean verificaIndiceClassificazioneEsteso(String repoId, String principalId, String parentNodeId, String indiceClassificazioneEstesa) {
		//String idClassificazione = null;
		boolean esisteIndicaClassificazioneEsteso = false;
		try {
			URL url = new URL(getEndpointObject());
			final ObjectService objectService = new ObjectService(url);
			ObjectServicePort objectServicePort = objectService.getObjectServicePort();
			((BindingProvider) objectServicePort).getBinding();
			setTimeout(objectServicePort);

			it.doqui.acta.acaris.object.ObjectIdType repo = new it.doqui.acta.acaris.object.ObjectIdType();
			repo.setValue(repoId);

			it.doqui.acta.acaris.object.PrincipalIdType principal = new it.doqui.acta.acaris.object.PrincipalIdType();
			principal.setValue(principalId);

			final it.doqui.acta.acaris.object.QueryableObjectType queryableObject = new it.doqui.acta.acaris.object.QueryableObjectType();
			logger.debug("Cerco l'oggetto " + "AggregazioneICEView");
			queryableObject.setObject("AggregazioneICEView");

			final it.doqui.acta.acaris.object.PropertyFilterType propertyFilter = new it.doqui.acta.acaris.object.PropertyFilterType();
			propertyFilter.setFilterType(EnumPropertyFilter.NONE);

			it.doqui.acta.acaris.object.NavigationConditionInfoType navigationConditionInfo = null;
			if (parentNodeId != null) {
				navigationConditionInfo = new it.doqui.acta.acaris.object.NavigationConditionInfoType();
				it.doqui.acta.acaris.object.ObjectIdType parentNode = new it.doqui.acta.acaris.object.ObjectIdType();
				logger.debug("Aggiungo la condizione sul nodo padre " + parentNodeId);
				parentNode.setValue(parentNodeId);
				navigationConditionInfo.setParentNodeId(parentNode);
			}

			List<it.doqui.acta.acaris.object.QueryConditionType> queryConditions = new ArrayList<it.doqui.acta.acaris.object.QueryConditionType>();
			if (indiceClassificazioneEstesa != null) {
				it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("indiceClassificazioneEstesa");
				queryCondition.setValue(indiceClassificazioneEstesa);
				queryConditions.add(queryCondition);
			}

			it.doqui.acta.acaris.object.PagingResponseType pagingResponse = objectServicePort.query(repo, principal, queryableObject, propertyFilter,
					queryConditions, navigationConditionInfo, null, null);
			String ecmUuidNodo = null;
			if (pagingResponse != null) {
				List<ObjectResponseType> objectsResponse = pagingResponse.getObjects();
				logger.debug("Numero totale risultati: " + objectsResponse.size());
				for (ObjectResponseType objectResponse : objectsResponse) {
					//logger.debug("ObjectId " + objectResponse.getObjectId().getValue());
					//idClassificazione = objectResponse.getObjectId().getValue();
					List<it.doqui.acta.acaris.object.PropertyType> properties = objectResponse.getProperties();
					for (it.doqui.acta.acaris.object.PropertyType p : properties) {
						//logger.debug(p.getQueryName().getPropertyName() + " " + p.getValue().getContent());
						if (p.getQueryName() != null && p.getQueryName().getPropertyName() != null
								&& p.getQueryName().getPropertyName().equalsIgnoreCase("ecmUuidNodo")) {
							ecmUuidNodo = p.getValue().getContent().get(0);
						}
					}
				}
			}

			logger.debug("ecmUuidNodo dell'indice di classificazione " + ecmUuidNodo);
			if( ecmUuidNodo!=null)
				esisteIndicaClassificazioneEsteso = true;

			/*List<it.doqui.acta.acaris.object.QueryConditionType> queryConditions1 = new ArrayList<it.doqui.acta.acaris.object.QueryConditionType>();
			it.doqui.acta.acaris.object.QueryConditionType queryCondition = new it.doqui.acta.acaris.object.QueryConditionType();
			queryCondition.setOperator(EnumQueryOperator.EQUALS);
			queryCondition.setPropertyName("ecmUuidNodo");
			queryCondition.setValue(ecmUuidNodo.toString());
			queryConditions1.add(queryCondition);
			
			it.doqui.acta.acaris.object.PagingResponseType pagingResponse1 = objectServicePort.query(repo, principal, queryableObject, propertyFilter,
					queryConditions1, navigationConditionInfo, null, null);
			if (pagingResponse1 != null) {
				List<ObjectResponseType> objectsResponse = pagingResponse1.getObjects();
				logger.debug("Numero risultati:::" + objectsResponse.size());
				for (ObjectResponseType objectResponse : objectsResponse) {
					logger.debug("ObjectId " + objectResponse.getObjectId().getValue());
					esisteIndicaClassificazioneEsteso=true;
					List<it.doqui.acta.acaris.object.PropertyType> properties = objectResponse.getProperties();
					for (it.doqui.acta.acaris.object.PropertyType p : properties) {
						logger.debug(p.getQueryName().getPropertyName() + " " + p.getValue().getContent());

					}
					logger.debug("\n");
				}
			}*/
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.object.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esisteIndicaClassificazioneEsteso;
	}

	public String getEndpointObject() {
		return endpointObject;
	}

	public void setEndpointObject(String endpointObject) {
		this.endpointObject = endpointObject;
	}
}
