/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.doqui.acta.acaris.archive.AcarisRepositoryEntryType;
import it.doqui.acta.acaris.archive.EnumFolderObjectType;
import it.doqui.acta.acaris.archive.IdFormaDocumentariaType;
import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.doqui.acta.acaris.archive.VolumeSerieTipologicaDocumentiPropertiesType;
import it.doqui.acta.acaris.backoffice.PrincipalExtResponseType;
import it.doqui.acta.acaris.common.AcarisContentStreamType;
import it.doqui.acta.acaris.common.ChangeTokenType;
import it.doqui.acta.acaris.common.EnumMimeTypeType;
import it.doqui.acta.acaris.common.EnumObjectType;
import it.doqui.acta.acaris.common.EnumPropertyFilter;
import it.doqui.acta.acaris.common.EnumQueryOperator;
import it.doqui.acta.acaris.common.IdNodoType;
import it.doqui.acta.acaris.common.IdSmistamentoType;
import it.doqui.acta.acaris.common.IdUtenteType;
import it.doqui.acta.acaris.common.IdVitalRecordCodeType;
import it.doqui.acta.acaris.common.ItemType;
import it.doqui.acta.acaris.common.NavigationConditionInfoType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.ObjectResponseType;
import it.doqui.acta.acaris.common.PagingResponseType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.doqui.acta.acaris.common.PropertyFilterType;
import it.doqui.acta.acaris.common.PropertyType;
import it.doqui.acta.acaris.common.QueryConditionType;
import it.doqui.acta.acaris.common.QueryNameType;
import it.doqui.acta.acaris.common.QueryableObjectType;
import it.doqui.acta.acaris.common.SimpleResponseType;
import it.doqui.acta.acaris.common.ValueType;
import it.doqui.acta.acaris.common.VarargsType;
import it.doqui.acta.acaris.documentservice.EnumTipoOperazione;
import it.doqui.acta.acaris.documentservice.FailedStepsInfo;
import it.doqui.acta.acaris.documentservice.IdentificatoreDocumento;
import it.doqui.acta.acaris.documentservice.InfoRichiestaCreazione;
import it.doqui.acta.acaris.management.VitalRecordCodeType;
import it.doqui.acta.acaris.multifilingservice.AcarisException;
import it.doqui.acta.acaris.sms.DestinatarioType;
import it.doqui.acta.acaris.sms.IdTipoSmistamentoType;
import it.doqui.acta.acaris.sms.InfoCreazioneType;
import it.doqui.acta.acaris.sms.MittenteType;
import it.doqui.acta.acaris.sms.OggettoSmistamentoType;
import it.eng.acta.ws.BackofficeServiceClient;
import it.eng.acta.ws.DocumentServiceClient;
import it.eng.acta.ws.ManagementServiceClient;
import it.eng.acta.ws.MultifilingServiceClient;
import it.eng.acta.ws.ObjectServiceClient;
import it.eng.acta.ws.QueryableServiceClient;
import it.eng.acta.ws.RepositoryServiceClient;
import it.eng.acta.ws.SMSServiceClient;
import it.eng.acta.ws.handler.AbstractLogHandler;
import it.eng.acta.ws.handler.queue.LogHandler;
import it.eng.stilo.logic.OperationResult;
import it.eng.stilo.logic.exception.ActaException;
import it.eng.stilo.logic.exception.ClassificationException;
import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.exception.ObjectNotFoundException;
import it.eng.stilo.logic.exception.TooManyObjectsException;
import it.eng.stilo.logic.service.builder.DocumentRequestBean;
import it.eng.stilo.logic.service.builder.DocumentRequestBuilder;
import it.eng.stilo.logic.service.builder.RequestBuilderFactory;
import it.eng.stilo.logic.service.cache.ActaCache;
import it.eng.stilo.logic.service.cache.PrincipalCache;
import it.eng.stilo.logic.type.ActaEnte;
import it.eng.stilo.logic.type.EActaProperty;
import it.eng.stilo.logic.type.EAttributeType;
import it.eng.stilo.logic.type.EDocumentFormat;
import it.eng.stilo.logic.type.EDossierType;
import it.eng.stilo.logic.type.EEfficacy;
import it.eng.stilo.logic.type.EIntegrationResource;
import it.eng.stilo.logic.type.EStatusType;
import it.eng.stilo.logic.type.IntegrationResource;
import it.eng.stilo.logic.type.IntegrationResourceCMTO;
import it.eng.stilo.logic.type.IntegrationResourceCOTO;
import it.eng.stilo.logic.type.IntegrationResourceRP;
import it.eng.stilo.model.core.AdminOrganization;
import it.eng.stilo.model.core.AdminOrganizationKey;
import it.eng.stilo.model.core.AuthorityProperty;
import it.eng.stilo.model.core.DocumentType;
import it.eng.stilo.model.core.DocumentType_;
import it.eng.stilo.model.core.QueueDocAttribute;
import it.eng.stilo.model.core.QueueDocument;
import it.eng.stilo.model.util.EClassificationStatus;
import it.eng.stilo.model.util.EDocumentStatus;

@Stateless
public class ActaIntegrationEJB {

	private static final String NOT_AVAILABLE = "NA";
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject
	private ObjectServiceClient objectServiceClient;
	@Inject
	private RepositoryServiceClient repositoryServiceClient;
	@Inject
	private BackofficeServiceClient backofficeServiceClient;
	@Inject
	private DocumentServiceClient documentServiceClient;
	@Inject
	private ManagementServiceClient managementServiceClient;
	@Inject
	private MultifilingServiceClient multiFilingServiceClient;
	@Inject
	private SMSServiceClient smsServiceClient;
	@EJB
	private CacheEJB<ActaCache> cacheEJB;
	@EJB
	private StorageEJB storageEJB;
	@EJB(beanName = "genericDataAccessEJB")
	private GenericDataAccessEJB<AuthorityProperty> authorityPropertiesEJB;
	@EJB(beanName = "genericDataAccessEJB")
	private GenericDataAccessEJB<AuthorityProperty> attributePropertiesEJB;
	@EJB(beanName = "genericDataAccessEJB")
	private GenericDataAccessEJB<DocumentType> documentTypeEJB;
	@EJB(beanName = "genericDataAccessEJB")
	private GenericDataAccessEJB<DocumentType> genericDataAccessEJB;
	@EJB
	private AttributeDocEJB attributeDocEJB;

	private Map createParameterMap(final QueueDocument document) {
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(AbstractLogHandler.KEY_FLOW_ID, UUID.randomUUID());
		if (document != null) {
			paramMap.put(AbstractLogHandler.KEY_DOC_REF, document.getId());
		}
		
		logger.debug("Utilizzo la mappa parametri: " + paramMap );
		return paramMap;
	}

	/**
	 * Create document in remote target.
	 *
	 * @param document The document to send.
	 * @return The operation result.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public OperationResult createDocument(final QueueDocument document) {
		logger.info("Inzio metodo createDocument - Id: " + document.getId() + " Nome: " + document.getName());

		final Map<String, Object> paramMap = createParameterMap(document);

		// Get list of all authority properties
		logger.debug("Ricerca AuthorityProperty ");
		final List<AuthorityProperty> authorityProperties = authorityPropertiesEJB.loadList(AuthorityProperty.class);
		if( authorityProperties!=null ){
			for(AuthorityProperty authorityProperty: authorityProperties){
				logger.debug("ID: " + authorityProperty.getId() + " VALUE: "  + authorityProperty.getValue() );
			}
		}

		final OperationResult operationResult = new OperationResult(paramMap.get(AbstractLogHandler.KEY_FLOW_ID)
				.toString());
		
		final Map<String, String> dynamicAttributesMap = new HashMap<String, String>();
		try {
			logger.info("Cerco i metadati per il documento con idDoc " + document.getId() );
			List<QueueDocAttribute> attributesDoc = attributeDocEJB.find(document.getId() );
			for(QueueDocAttribute attributeDoc : attributesDoc){
				logger.info("Nome attributo: " + attributeDoc.getAttributeTag() + " Valore attributo: " + attributeDoc.getStrValue());
				dynamicAttributesMap.put(attributeDoc.getAttributeTag(), attributeDoc.getStrValue());
			}
		} catch (DatabaseException | JAXBException e) {
			// se non e' possibile individuare i metadati del documento non continuo
			logger.error("Errore nella creazione del documento con id ", document.getId() + ":" + e.getMessage(), e);
			operationResult.setResultType(OperationResult.ResultType.FAILED);
			document.setModified(LocalDateTime.now());
			document.setStatus( EDocumentStatus.ERROR );
			
			LogHandler.sendMessageError("Errore: " + e.getMessage(), 
					paramMap.get(AbstractLogHandler.KEY_DOC_REF), 
					paramMap.get(AbstractLogHandler.KEY_FLOW_ID), null);
			
			return operationResult;
		}
		
		// Work-around for reusing existing methods
		final AdminOrganization adminOrganization = getAdminOrganization( document );
		if( adminOrganization!=null && adminOrganization.getId()!=null){
			logger.debug("AdminOrganization AOO  " + adminOrganization.getId().getAooCode() );
			logger.debug("AdminOrganization NODE  " + adminOrganization.getNodeCode() );
			logger.debug("AdminOrganization STRUCTURE  " + adminOrganization.getStructureCode() );
		}
		
		try {
			// Get remote repository
			logger.debug("Cerco il repository ");
			final ObjectIdType repositoryId = findRepository(authorityProperties, paramMap);
			if( repositoryId!=null)
				logger.info("repositoryId: " + repositoryId.getValue() );

			// Get remote principal related to repository
			logger.debug("Cerco il principal ");
			final PrincipalIdType principalId = findPrincipal(repositoryId, adminOrganization, authorityProperties,
					paramMap);
			if( principalId!=null)
				logger.info("principalId: " + principalId.getValue() );
			
			ActaEnte actaEnte = new ActaEnte();
			String ente = actaEnte.getEnte();
			logger.debug("Ente configurato: " + ente);
			
			String dbKeyTitolario = actaEnte.getDbKeyTitolario();
			logger.debug("DbKeyTitolario configurato: " + dbKeyTitolario);
			
			// Get remote container / parent folder
			logger.debug("Cerco la serie ");
			final ObjectIdType serieId = findSerie(repositoryId, principalId, adminOrganization, paramMap, ente, dbKeyTitolario);

			boolean usaVolume = useVolume(adminOrganization.getDocumentType().getId() );
			
			ObjectIdType containerDocId = serieId;
			boolean possibileInserimentoInVolume = false;
			//cerco il volume all'interno del quale inserire i documenti
			if( usaVolume ){
			
				// Get remote container / volume folder where a new document could
				// be created
				ObjectIdType volumeId = null;
				try {
					logger.debug("Cerco il volume ");
					volumeId = findVolume(repositoryId, principalId, serieId, document.getDocumentType(),
						paramMap, dynamicAttributesMap, ente);
					if( volumeId!=null )
						possibileInserimentoInVolume = true;
					else 
						possibileInserimentoInVolume = false;
				} catch (ObjectNotFoundException e) {
					logger.error(String.format("[ErrorFindingVolume][%s][%s]-[%s]", adminOrganization.getId().getAooCode(),
							adminOrganization.getId().getDocumentTypeId(), e.getMessage()));
					
					logger.debug("Il volume non esiste, provo a crearlo");
					Date date = null;
					if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("dataCronica") && dynamicAttributesMap.get("dataCronica")!=null ){
						try {
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							date = format.parse( dynamicAttributesMap.get("dataCronica") );
						} catch (Exception e1) {
							logger.error("Errore nel parsing della data cronica");;
							date = new Date();
						}
			        }
					// Create folder properties object
					final VolumeSerieTipologicaDocumentiPropertiesType properties = createVolumeProperties(adminOrganization
							.getDocumentType(), date, dynamicAttributesMap);  
	
					// Create remote folder into parent folder
					try {
						volumeId = objectServiceClient.createFolder(repositoryId,
						EnumFolderObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE, 
						principalId, properties,
						serieId, paramMap);
						
						logger.info(String.format("CreatedVolume[%s][%s][%s]", adminOrganization.getId().getAooCode(), adminOrganization
								.getId().getDocumentTypeId(), (volumeId != null ? volumeId.getValue() : NOT_AVAILABLE)));
						if( volumeId!=null )
							possibileInserimentoInVolume = true;
						else 
							possibileInserimentoInVolume = false;
					} catch(Exception e1){
						logger.error("Errore nella creazione del volume");
						possibileInserimentoInVolume = false;
					}
				} catch ( TooManyObjectsException e) {
					logger.error(String.format("[ErrorFindingVolume][%s][%s]-[%s]", adminOrganization.getId().getAooCode(),
							adminOrganization.getId().getDocumentTypeId(), e.getMessage()));
					
					possibileInserimentoInVolume = false;
					
				} catch ( ActaException e) {
					logger.error(String.format("[ErrorFindingVolume][%s][%s]-[%s]", adminOrganization.getId().getAooCode(),
							adminOrganization.getId().getDocumentTypeId(), e.getMessage()));
					
					possibileInserimentoInVolume = false;
					
				}
				
				containerDocId = volumeId;
			}

			// Get efficacy type
			final IdStatoDiEfficaciaType efficacyTypeId = findEfficacy(repositoryId, principalId, document
					.getDocumentType(), paramMap, dynamicAttributesMap);
			
			final IdStatoDiEfficaciaType efficacyTypeNonFirmatoId = findEfficacyNonFirmato(repositoryId, principalId, paramMap, dynamicAttributesMap);

			// Get document format type
			final IdFormaDocumentariaType formatTypeId = findDocumentFormat(repositoryId, principalId, document
					.getDocumentType(), authorityProperties, paramMap, dynamicAttributesMap);

			// Get remote vital record registry
			final Map<String, IdVitalRecordCodeType> vitalRecordsMap = findVitalRecordCodes(repositoryId, paramMap);

			IdentificatoreDocumento documentId = null;
			if (document.getDocumentId() != null) {
				// Main document already exists - attachment management
				if( !usaVolume || (usaVolume && possibileInserimentoInVolume ) ) 
					documentId = create(document);
			} else {
				// -- Create MAIN DOCUMENT --
				if( !usaVolume || (usaVolume && possibileInserimentoInVolume ) ) {
					
					boolean esisteDocumento = false;
					String idActaDocumento = null;
					EDocumentStatus statoDocumento = document.getStatus();
					logger.debug("Stato Documento "+ statoDocumento);
					if( statoDocumento.equals( EDocumentStatus.READY2 ) || 
							statoDocumento.equals( EDocumentStatus.ERROR ) || 
							statoDocumento.equals( EDocumentStatus.ELABORATION )) {
						//effettuo la query su acta per verificare se esiste il documento
						String numeroRepertorio = null;
						String dataCronica = null;
						String parolaChiave = null;
						if( dynamicAttributesMap!=null && dynamicAttributesMap.get("parolaChiaveFiltro")!=null )
							parolaChiave = dynamicAttributesMap.get("parolaChiaveFiltro");
						if( dynamicAttributesMap!=null && dynamicAttributesMap.get("dataCronica")!=null )
							dataCronica = dynamicAttributesMap.get("dataCronica").substring(0,10);
						if( dynamicAttributesMap!=null && dynamicAttributesMap.get("numRepertorio")!=null )
							numeroRepertorio = dynamicAttributesMap.get("numRepertorio");
							
						idActaDocumento = verificaEsistenzaDoc(repositoryId, principalId, containerDocId, numeroRepertorio, dataCronica, parolaChiave, paramMap);
						if( idActaDocumento!=null ){
							logger.debug("Il documento esiste gia su acta");
							esisteDocumento = true;
						}
					} else {
						logger.debug("Lo stato del documento non richiede verifica esistenza documento su acta");
					}
				
					if( !esisteDocumento ){
						logger.debug("Avvio le operazioni necessarie al trasferimento del documento su acta");
						documentId = createDocument(document, repositoryId, principalId, containerDocId, vitalRecordsMap,
								efficacyTypeId, efficacyTypeNonFirmatoId, formatTypeId, null, paramMap, dynamicAttributesMap, true, ente);
						
						if( documentId!=null && documentId.getFailedStepsInfo()!=null ){
							List<FailedStepsInfo> steps = documentId.getFailedStepsInfo();
							for(FailedStepsInfo step : steps ){
								logger.info(":::: step " + step.getFileName() + " " + step.getFailedSteps() );
							}
						}
						
						logger.info(String.format("CreatedDocument[%s][%s]", document.getName(), (documentId != null
								&& documentId.getObjectIdDocumento() != null ? documentId.getObjectIdDocumento().getValue() :
								NOT_AVAILABLE)));
						if (documentId != null && documentId.getObjectIdDocumento() != null) {
							// Update MAIN DOCUMENT attributes
							document.setDocumentId(documentId.getObjectIdDocumento().getValue());
							document.setClassificationId(documentId.getObjectIdClassificazione().getValue());
							document.setStatus(EDocumentStatus.CONSUMED);
							document.setCommunicated(LocalDateTime.now());
						} else {
							document.setStatus(EDocumentStatus.ERROR);
						}
					} else {
						//il documento gia esiste, non lo ritrasferisco
						document.setStatus(EDocumentStatus.ERROR);
						LogHandler.sendMessageError("Documento gia inserito in acta ", 
								document.getId(), 
								paramMap.get(AbstractLogHandler.KEY_FLOW_ID), null);
					}
					
					document.setAttempts(document.getAttempts() + 1); // Updating even if creation has failed
					document.setModified(LocalDateTime.now());
				}
				if( usaVolume && !possibileInserimentoInVolume ){
					document.setStatus(EDocumentStatus.ERROR);
					document.setAttempts(document.getAttempts() + 1); // Updating even if creation has failed
					document.setModified(LocalDateTime.now());
				}
			}

			if (documentId != null && documentId.getObjectIdDocumento() != null) {
				operationResult.setResultType(OperationResult.ResultType.SUCCESS);

				if( document.getAttachments()!=null ){
					
					logger.debug("Numero allegati al documento: " + document.getAttachments().size() );	
				
					logger.debug("Verifico se la dd e' per pubblicazione ");
					if( document.getDocumentType().getId().equalsIgnoreCase("DDO")){
						DocumentoActa documentoActa = getClassificazioneProperties(repositoryId, principalId, null, document.getClassificationId(), paramMap);
						logger.debug("documentoActa " + documentoActa);
						
						if(documentoActa!=null && documentoActa.getDocConAllegati()!=null && documentoActa.getDocConAllegati().equalsIgnoreCase("N")){
							logger.debug("cambio la property per gli allegati ");
							modificaDocConAllegato(repositoryId, principalId, document.getClassificationId(), documentoActa.getChangeToken(), paramMap);
						}
					}
					
					boolean interrompiElaborazioneAllegati = false;
					// Attachments
					for (final QueueDocument attachment : document.getAttachments()) {
						if( !interrompiElaborazioneAllegati){
							logger.debug("Elaborazione allegato " + attachment.getName() + " "  + attachment.getInserted());
							try {
								if (!EDocumentStatus.CONSUMED.equals(attachment.getStatus())) {
									// -- Create ATTACHMENT DOCUMENT -- [supposing to apply classification also for attachment]
									
									final Map<String, String> dynamicAttributesAttachMap = new HashMap<String, String>();
									try {
										logger.info("effettuo ricerca:::::::::::::::::. idAttach " + attachment.getId() );
										List<QueueDocAttribute> attributesAttach = attributeDocEJB.find(attachment.getId() );
										logger.info("trovato:::::::::::::::::. attributesDoc " + attributesAttach );
										for(QueueDocAttribute attributeAttach : attributesAttach){
											logger.info("attributeAttach.getAttributeTag() " + attributeAttach.getAttributeTag());
											logger.info("attributeAttach.getStrValue() " + attributeAttach.getStrValue());
											dynamicAttributesAttachMap.put(attributeAttach.getAttributeTag(), attributeAttach.getStrValue());
										}
									} catch (DatabaseException | JAXBException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
									paramMap.put(AbstractLogHandler.KEY_DOCALL_REF, attachment.getId());
									
									boolean esisteAllegato = false;
									String idActaAllegato = null;
									String numeroRepertorio = null;
									String dataCronica = null;
									String parolaChiave = null;
									if( dynamicAttributesAttachMap!=null && dynamicAttributesAttachMap.get("parolaChiaveFiltro")!=null )
										parolaChiave = dynamicAttributesAttachMap.get("parolaChiaveFiltro");
									if( dynamicAttributesAttachMap!=null && dynamicAttributesAttachMap.get("dataCronica")!=null )
										dataCronica = dynamicAttributesAttachMap.get("dataCronica").substring(0,10);
									if( dynamicAttributesAttachMap!=null && dynamicAttributesAttachMap.get("numRepertorio")!=null )
										numeroRepertorio = dynamicAttributesAttachMap.get("numRepertorio");
									
									idActaAllegato = verificaEsistenzaDoc(repositoryId, principalId, containerDocId, 
											numeroRepertorio, dataCronica, parolaChiave, paramMap);
									if( idActaAllegato!=null ){
										logger.debug("L'allegato esiste gia su acta");
										esisteAllegato = true;
									}
									
									if( !esisteAllegato ){
										logger.debug("Avvio le operazioni necessarie al trasferimento del documento su acta");
										final IdentificatoreDocumento attDocumentId = createDocument(attachment, repositoryId,
											principalId, containerDocId, vitalRecordsMap, efficacyTypeId, efficacyTypeNonFirmatoId, formatTypeId, documentId
													.getObjectIdClassificazione(), paramMap, dynamicAttributesAttachMap, false, ente);
										logger.info(":::: attDocumentId " + attDocumentId );
										if( attDocumentId!=null && attDocumentId.getFailedStepsInfo()!=null ){
											List<FailedStepsInfo> steps = attDocumentId.getFailedStepsInfo();
											for(FailedStepsInfo step : steps ){
												logger.info(":::: step " + step.getFileName() + " " + step.getFailedSteps() );
											}
										}
										
										logger.info(String.format("CreatedAttachment[%s][%s]", attachment.getName(),
												(attDocumentId != null ? attDocumentId.getObjectIdDocumento().getValue() :
														NOT_AVAILABLE)));
			
										// Update ATTACHMENT DOCUMENT attributes
										if (attDocumentId != null && attDocumentId.getObjectIdDocumento() != null) {
											attachment.setDocumentId(attDocumentId.getObjectIdDocumento().getValue());
											attachment.setStatus(EDocumentStatus.CONSUMED);
											attachment.setCommunicated(LocalDateTime.now());
										} else {
											attachment.setStatus(EDocumentStatus.FIXABLE);
											interrompiElaborazioneAllegati = true;
										}
									} else {
										//il documento gia esiste, non lo ritrasferisco
										attachment.setStatus(EDocumentStatus.ERROR);
										LogHandler.sendMessageError("Allegato gia inserito in acta ", 
												attachment.getId(), 
												paramMap.get(AbstractLogHandler.KEY_FLOW_ID), null);
										interrompiElaborazioneAllegati = true;
									}
								} else {
									logger.debug("allegato gia trasferito");
								}
							} catch (Exception e) {
								logger.error(String.format("[ErrorProcessing-Attachment=%d]", attachment.getId()), e);
								operationResult.setResultType(OperationResult.ResultType.FAILED_ATTACHMENT);
								attachment.setStatus(EDocumentStatus.FIXABLE);
								interrompiElaborazioneAllegati = true;
								
								LogHandler.sendMessageError("Errore: " + e.getMessage(), 
										attachment.getId(), 
										paramMap.get(AbstractLogHandler.KEY_FLOW_ID), null);
							} catch (Throwable e) {
								logger.error(String.format("[ErrorProcessing-Attachment=%d]", attachment.getId()), e);
								operationResult.setResultType(OperationResult.ResultType.FAILED_ATTACHMENT);
								attachment.setStatus(EDocumentStatus.FIXABLE);
								interrompiElaborazioneAllegati = true;
								
								LogHandler.sendMessageError("Errore: " + e.getMessage(), 
										attachment.getId(), 
										paramMap.get(AbstractLogHandler.KEY_FLOW_ID), null);
							}
							attachment.setAttempts(attachment.getAttempts() + 1); // Updating even if creation has failed
							attachment.setModified(LocalDateTime.now());
						} else {
							logger.debug("elaborazione allegati successivi non consentita a causa di un precedente errore");
						}
					}
				}

				if (!operationResult.getResultType().equals(OperationResult.ResultType.FAILED_ATTACHMENT)) {
					// -- Check if CLASSIFICATION is requested --
					classify(repositoryId, principalId, authorityProperties, document, documentId, paramMap, dynamicAttributesMap);
				}
			} else {
				operationResult.setResultType(OperationResult.ResultType.FAILED);
			}

		} catch (JAXBException | DatabaseException e) {
			logger.error(String.format("[ErrorCreatingDocument][%s]-[%s]", document.getName(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED);
			document.setModified(LocalDateTime.now());
			document.setStatus( EDocumentStatus.ERROR );
			
			LogHandler.sendMessageError("Errore: " + e.getMessage(), 
					paramMap.get(AbstractLogHandler.KEY_DOC_REF), 
					paramMap.get(AbstractLogHandler.KEY_FLOW_ID), null);
			
		} catch ( ObjectNotFoundException e) {
			logger.error(String.format("[ErrorCreatingDocument][%s]-[%s]", document.getName(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED);
			document.setStatus( EDocumentStatus.ERROR );
			document.setModified(LocalDateTime.now());
		} catch ( TooManyObjectsException e) {
			logger.error(String.format("[ErrorCreatingDocument][%s]-[%s]", document.getName(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED);
			document.setStatus( EDocumentStatus.ERROR );
			document.setModified(LocalDateTime.now());
		} catch (ClassificationException e) {
			logger.error("",e);
			logger.error(String.format("[ErrorCreatingDocument][%s]-[%s]", document.getName(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED_CLASSIFICATION);
			document.setClassified(EClassificationStatus.FIXABLE);
			document.setModified(LocalDateTime.now());
		} catch (ActaException e) {
			logger.error(String.format("[ErrorCreatingDocument][%s]-[%s]", document.getName(), e.getMessage()), e);
			operationResult.setResultType(OperationResult.ResultType.FAILED);
			document.setModified(LocalDateTime.now());
			document.setStatus( EDocumentStatus.ERROR );
			
			LogHandler.sendMessageError("Errore: " + e.getMessage(), 
					paramMap.get(AbstractLogHandler.KEY_DOC_REF), 
					paramMap.get(AbstractLogHandler.KEY_FLOW_ID), null);
			
		} catch (Throwable e) {
			logger.error(String.format("[ErrorCreatingDocument][%s]-[%s]", document.getName(), e.getMessage()), e);
			operationResult.setResultType(OperationResult.ResultType.FAILED);
			document.setModified(LocalDateTime.now());
			document.setStatus( EDocumentStatus.ERROR );
			
			LogHandler.sendMessageError("Errore: " + e.getMessage(), 
					paramMap.get(AbstractLogHandler.KEY_DOC_REF), 
					paramMap.get(AbstractLogHandler.KEY_FLOW_ID), null);
			
		}

		return operationResult;
	}
	
	private String verificaEsistenzaDoc(ObjectIdType repoId, PrincipalIdType principalId, ObjectIdType parentNodeId, 
			String numeroRepertorio, String dataCronica, String parolaChiave, final Map<String, Object> paramMap) throws ActaException {
		String idActaDocumento = null;
		logger.debug("Verifico se il documento e stato gia' caricato su acta");
		
		final QueryableObjectType queryableObject = new QueryableObjectType();
		logger.debug("Cerco l'oggetto " + EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE.value());
        queryableObject.setObject(EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE.value());
        
        final PropertyFilterType propertyFilter = new PropertyFilterType();
		propertyFilter.setFilterType( EnumPropertyFilter.ALL );
		
		NavigationConditionInfoType navigationConditionInfo = null;
		if(parentNodeId!=null){
			navigationConditionInfo = new NavigationConditionInfoType();
			logger.debug("Aggiungo la condizione sul nodo padre " + parentNodeId.getValue() );
			navigationConditionInfo.setParentNodeId(parentNodeId);
			navigationConditionInfo.setLimitToChildren(false);
		}
		
		final List<QueryConditionType> queryConditions = new ArrayList<QueryConditionType>();
		if (numeroRepertorio != null) {
			QueryConditionType queryCondition = new QueryConditionType();
			queryCondition.setOperator(EnumQueryOperator.EQUALS);
			queryCondition.setPropertyName("numRepertorio");
			logger.debug("Aggiungo la condizione numRepertorio  = " + numeroRepertorio);
			queryCondition.setValue(numeroRepertorio);
			queryConditions.add(queryCondition);
		}
		if (dataCronica != null) {
			QueryConditionType queryCondition = new QueryConditionType();
			queryCondition.setOperator(EnumQueryOperator.EQUALS);
			logger.debug("Aggiungo la condizione dataDocCronica = " + dataCronica);
			queryCondition.setPropertyName("dataDocCronica");
			queryCondition.setValue(dataCronica);
			queryConditions.add(queryCondition);
		}
		if (parolaChiave != null) {
			QueryConditionType queryCondition = new QueryConditionType();
			//queryCondition.setOperator(EnumQueryOperator.EQUALS);
			queryCondition.setOperator(EnumQueryOperator.LIKE);
			logger.debug("Aggiungo la condizione paroleChiave like " + parolaChiave);
			queryCondition.setPropertyName("paroleChiave");
			//queryCondition.setValue(parolaChiave);
			queryCondition.setValue("*"+parolaChiave+"*");
			queryConditions.add(queryCondition);
		}
		
		PagingResponseType pagingResponse;
		try {
			pagingResponse = objectServiceClient.query(repoId, principalId, queryableObject, propertyFilter, 
					queryConditions, navigationConditionInfo, null, null, paramMap);
			
			if( pagingResponse!=null ){
	        	List<ObjectResponseType> objectsResponse = pagingResponse.getObjects();
	        	logger.debug("Numero risultati: " + objectsResponse.size());
	        	for(ObjectResponseType objectResponse : objectsResponse){
	        		logger.debug("ObjectId: " + objectResponse.getObjectId().getValue());
	        		idActaDocumento = objectResponse.getObjectId().getValue();
	        		List<PropertyType> properties = objectResponse.getProperties();
	        		for(PropertyType p : properties){
	        			//logger.debug(p.getQueryName().getPropertyName() + " " + p.getValue().getContent());
	        		}
	        		//System.out.println("\n");
	        	}
	        }
		} catch (Exception e) {
			throw new ActaException("Errore nella ricerca documenti");
		}
		
		return idActaDocumento;
	}
	
	private DocumentoActa getClassificazioneProperties(ObjectIdType repoId, PrincipalIdType principalId, ObjectIdType parentNodeId, 
			String idClassificazione,
			final Map<String, Object> paramMap) throws ActaException {
		DocumentoActa documentoActa = null;
		logger.debug("Verifico se il documento accetta allegati");
		
		final QueryableObjectType queryableObject = new QueryableObjectType();
		logger.debug("Cerco l'oggetto " + EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE.value());
        queryableObject.setObject(EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE.value());
        
        final PropertyFilterType propertyFilter = new PropertyFilterType();
		propertyFilter.setFilterType( EnumPropertyFilter.ALL );
		
		NavigationConditionInfoType navigationConditionInfo = null;
		if(parentNodeId!=null){
			navigationConditionInfo = new NavigationConditionInfoType();
			logger.debug("Aggiungo la condizione sul nodo padre " + parentNodeId.getValue() );
			navigationConditionInfo.setParentNodeId(parentNodeId);
			navigationConditionInfo.setLimitToChildren(false);
		}
		
		final List<QueryConditionType> queryConditions = new ArrayList<QueryConditionType>();
		
		if (idClassificazione != null) {
			QueryConditionType queryCondition = new QueryConditionType();
			queryCondition.setOperator(EnumQueryOperator.EQUALS);
			logger.debug("Aggiungo la condizione objectId = " + idClassificazione);
			queryCondition.setPropertyName("objectId");
			queryCondition.setValue(idClassificazione);
			queryConditions.add(queryCondition);
		}
		
		PagingResponseType pagingResponse;
		try {
			pagingResponse = objectServiceClient.query(repoId, principalId, queryableObject, propertyFilter, 
					queryConditions, navigationConditionInfo, null, null, paramMap);
			
			if( pagingResponse!=null ){
	        	List<ObjectResponseType> objectsResponse = pagingResponse.getObjects();
	        	logger.debug("Numero risultati: " + objectsResponse.size());
	        	for(ObjectResponseType objectResponse : objectsResponse){
	        		logger.debug("ObjectId: " + objectResponse.getObjectId().getValue());
	        		//idActaDocumento = objectResponse.getObjectId().getValue();
	        		List<PropertyType> properties = objectResponse.getProperties();
	        		documentoActa = new DocumentoActa();
        			for(PropertyType p : properties){
	        			logger.debug(p.getQueryName().getPropertyName() + " " + p.getValue().getContent());
	        			if( p.getQueryName().getPropertyName()!=null && p.getQueryName().getPropertyName().equalsIgnoreCase("changeToken")){
	        				documentoActa.setChangeToken(p.getValue().getContent().get(0));
	        			}
	        			if( p.getQueryName().getPropertyName()!=null && p.getQueryName().getPropertyName().equalsIgnoreCase("docConAllegati")){
	        				documentoActa.setDocConAllegati(p.getValue().getContent().get(0));
	        			}	
	        		}
	        		//System.out.println("\n");
	        	}
	        }
		} catch (Exception e) {
			throw new ActaException("Errore nella ricerca documenti");
		}
		
		return documentoActa;
	}
	
	private void modificaDocConAllegato(ObjectIdType repoId, PrincipalIdType principalId, 
			String idClassificazioneOggetto, String changeToken,
			final Map<String, Object> paramMap) throws ActaException {
		try {
			
			Map<QueryNameType, ValueType> propertiesMap = new HashMap<QueryNameType, ValueType>();
			
			QueryNameType q1 = new QueryNameType();
			q1.setPropertyName("docConAllegati");
			q1.setClassName("ClassificazionePropertiesType");
			ValueType v1 = new ValueType();
			v1.getContent().add("S");
			propertiesMap.put(q1, v1);
			
			QueryNameType q2 = new QueryNameType();
			q2.setPropertyName("numeroAllegati");
			q2.setClassName("ClassificazionePropertiesType");
			ValueType v2 = new ValueType();
			v2.getContent().add("1");
			propertiesMap.put(q2, v2);
			
			ObjectIdType objectId = new ObjectIdType();
			objectId.setValue(idClassificazioneOggetto);
			
			ChangeTokenType changeTokenType = new ChangeTokenType();
			changeTokenType.setValue(changeToken);
			
			logger.debug("richiamo updateProperties con changeToken " + changeToken);
			logger.debug("propertiesMap " + propertiesMap);
			SimpleResponseType response = objectServiceClient.updateProperties(repoId, objectId, principalId, propertiesMap, changeTokenType, paramMap);
			
			if( response!=null ){
	        	ObjectIdType objectResponse = response.getObjectId();
	        	if(objectResponse!=null){
	        		logger.debug("objectResponse: " + objectResponse.getValue());
	        	}
	        	ChangeTokenType changeTokenResponse = response.getChangeToken();
	        	if(changeTokenResponse!=null){
	        		logger.debug("changeTokenResponse: " + changeTokenResponse.getValue());
	        	}
	        }
		} catch (Exception e) {
			throw new ActaException("Errore nella modifica properties");
		}
		
		
	}
	

	private IdentificatoreDocumento create(final QueueDocument document) {
		final ObjectIdType docId = new ObjectIdType();
		docId.setValue(document.getDocumentId());
		final ObjectIdType classId = new ObjectIdType();
		classId.setValue(document.getClassificationId());

		final IdentificatoreDocumento documentId = new IdentificatoreDocumento();
		documentId.setObjectIdDocumento(docId);
		documentId.setObjectIdClassificazione(classId);

		return documentId;
	}

	private void classify(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final List<AuthorityProperty> authorityProperties, final QueueDocument document,
			final IdentificatoreDocumento documentId, final Map<String, Object> paramMap, Map<String, String> dynamicAttributesMap)
			throws ClassificationException {
		//if (document.getDossierCode() != null) {
		if (dynamicAttributesMap != null && dynamicAttributesMap.get("documentoDaClassificare")!=null && dynamicAttributesMap.get("documentoDaClassificare").equalsIgnoreCase("true")) {
			// -- Create classification for MAIN DOCUMENT and ATTACHMENTS --
			logger.info(String.format("DocumentClassificationRequired[%s]", documentId.getObjectIdDocumento()
					.getValue()));
			if (documentId.getObjectIdClassificazione() != null && 
					documentId.getObjectIdDocumento()!=null ) {
				final ObjectIdType classificationId = createClassification(document, documentId
						.getObjectIdClassificazione(), repositoryId, principalId, paramMap, dynamicAttributesMap);
				logger.info(String.format("ClassifiedDocument[%s][%s][%s][%s]", document.getName(), documentId
						.getObjectIdDocumento().getValue(), document.getDossierCode(), classificationId.getValue()));
				document.setClassified(EClassificationStatus.CLASSIFIED);
			} else {
				logger.warn("InvalidDocumentClassification");
			}
		} 
		if (dynamicAttributesMap != null && dynamicAttributesMap.get("nodoSmistamento")!=null ) {
		//else if (document.getSwStructureCode() != null) {
			// -- Create switching for MAIN DOCUMENT and ATTACHMENTS --
			logger.info(String.format("DocumentSwitchingRequired[%s][%s]",
					documentId.getObjectIdDocumento().getValue(), 
					dynamicAttributesMap.get("nodoSmistamento")
					//document.getSwStructureCode()
					));
			final IdSmistamentoType switchId = createSwitch(repositoryId, principalId, authorityProperties, document,
					paramMap, dynamicAttributesMap.get("nodoSmistamento"));
			logger.info(String.format("SwitchedDocument[%s][%s][%s][%s]", document.getName(), documentId
					.getObjectIdDocumento().getValue(), document.getSwStructureCode(), switchId.getValue()));
			document.setClassified(EClassificationStatus.CLASSIFIED);
		} else {
			logger.info("ClassificationNotRequired[Doc:" + documentId.getObjectIdDocumento().getValue() + "]");
		}
	}

	private IdSmistamentoType createSwitch(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final List<AuthorityProperty> authorityProperties, final QueueDocument document,
			final Map<String, Object> paramMap, String nodoSmistamento) throws ClassificationException {
		final List<DestinatarioType> receiverList = new ArrayList<>();
		final List<OggettoSmistamentoType> switchedList = new ArrayList<>();
		final MittenteType senderType = new MittenteType();
		final InfoCreazioneType infoCreationType = new InfoCreazioneType();
		try {
			// Set user sender
			senderType.setUtente(findUser(repositoryId, principalId, authorityProperties, paramMap));
			// Set node receiver
			//final List<IdNodoType> nodoTypes = findReceiver(repositoryId, principalId, document, paramMap, nodoSmistamento);
			final List<IdNodoType> nodoTypes = findReceiver(nodoSmistamento);
			nodoTypes.forEach(nodoType -> {
				final DestinatarioType receiverType = new DestinatarioType();
				receiverType.setNodoOrganizzativo(nodoType);
				receiverList.add(receiverType);
			});
			// Set switchingType info creation
			infoCreationType.setTipoSmistamento(findSwitchingType(repositoryId, principalId, paramMap));
		} catch (ObjectNotFoundException | TooManyObjectsException e) {
			throw new ClassificationException(e);
		} catch (ActaException e) {
			throw new ClassificationException(e);
		}
		final OggettoSmistamentoType oggettoSmistamentoType = new OggettoSmistamentoType();
		final ObjectIdType objectIdType = new ObjectIdType();
		objectIdType.setValue(document.getClassificationId());
		oggettoSmistamentoType.setClassificazione(objectIdType);
		switchedList.add(oggettoSmistamentoType);

		IdSmistamentoType resultSwitchingTypeId;
		try {
			resultSwitchingTypeId = smsServiceClient.createSwitching(repositoryId, principalId,
					senderType, receiverList, switchedList, infoCreationType, paramMap);
			if (resultSwitchingTypeId == null) {
				throw new ClassificationException(String.format("[ErrorCreatingClassification][%s][%s]", document
						.getClassificationId(), document.getSwStructureCode()));
			}
		} catch (Exception e) {
			throw new ClassificationException(String.format("[ErrorCreatingClassification][%s][%s]", document
					.getClassificationId(), document.getSwStructureCode()));
		}
		

		return resultSwitchingTypeId;
	}

	private IdTipoSmistamentoType findSwitchingType(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final Map<String, Object> paramMap) throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		// Search switchingType into cache
		final PrincipalCache cache = getFromCache(principalId);
		if (cache.getSwitchingTypeId() != null) {
			logger.debug("Resolved-[" + ActaCache.NAME + "][switchingType]");
			return cache.getSwitchingTypeId();
		}

		final Map<EProperty, Object> queryConditionMap = new HashMap<>();
		queryConditionMap.put(EProperty.DESCRIPTION, "competenza");

		Long switchingContainerId;
		IdTipoSmistamentoType switchingId = null;
		try {
			switchingContainerId = Long.valueOf(findObject(repositoryId, principalId,
					EObjectType.SWITCHING_TYPE_NAME.code, EnumPropertyFilter.ALL, EnumQueryOperator.EQUALS,
					queryConditionMap, null, smsServiceClient, paramMap, EProperty.DB_KEY));
			
			switchingId = new IdTipoSmistamentoType();
			switchingId.setValue(switchingContainerId);
			cache.setSwitchingTypeId(switchingId);

		} catch (NumberFormatException | ActaException e) {
			throw new ActaException("Errore nello smistamento");
		}

		return switchingId;
	}

	private List<IdNodoType> findReceiver(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final QueueDocument document, final Map<String, Object> paramMap, String nodoSmistamento)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		// Search receiverId into cache
		final PrincipalCache cache = getFromCache(principalId);
		if (cache.getReceiverMap().containsKey(document.getSwStructureCode())) {
			logger.debug("Resolved-[" + ActaCache.NAME + "][receiverId]");
			return cache.getReceiverMap().get(document.getSwStructureCode());
		}

		// Starting from a given Switching StructureCode into main document, we get the list of nodes identifier
		// belonging to such Structure by querying AOO_STRUCTURE_NODE_VIEW
		final Map<EProperty, Object> queryConditionMap = new HashMap<>();
		queryConditionMap.put(EProperty.STRUCTURE_CODE, nodoSmistamento);
		//queryConditionMap.put(EProperty.STRUCTURE_CODE, document.getSwStructureCode());
		List<String> nodes;
		final List<IdNodoType> nodoTypes = new ArrayList<>();
		try {
			nodes = findObjects(repositoryId, principalId, EObjectType.AOO_STRUCTURE_NODE_VIEW.code,
					EnumPropertyFilter.ALL, EnumQueryOperator.EQUALS, queryConditionMap, null, backofficeServiceClient,
					paramMap, EProperty.NODE_ID);
			
			
			nodes.forEach(node -> {
				IdNodoType receiverId = new IdNodoType();
				receiverId.setValue(Long.valueOf(node));
				nodoTypes.add(receiverId);
			});

			cache.getReceiverMap().put(document.getSwStructureCode(), nodoTypes);
		} catch (ActaException e) {
			throw e;
		}

		return nodoTypes;
	}
	
	private List<IdNodoType> findReceiver(String nodoSmistamento)
			throws ObjectNotFoundException, TooManyObjectsException {
		final List<IdNodoType> nodoTypes = new ArrayList<>();

		final IdNodoType receiverId = new IdNodoType();
		receiverId.setValue(Long.valueOf(nodoSmistamento));
		nodoTypes.add(receiverId);

		return nodoTypes;
	}

	private IdUtenteType findUser(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final List<AuthorityProperty> authorityProperties, final Map<String, Object> paramMap)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		// Search userId into cache
		final PrincipalCache cache = getFromCache(principalId);
		if (cache.getUserId() != null) {
			logger.debug("Resolved-[" + ActaCache.NAME + "][userId]");
			return cache.getUserId();
		}

		final Map<EProperty, Object> queryConditionMap = new HashMap<>();
		queryConditionMap.put(EProperty.FIS_CODE, authorityProperties.stream().filter(fs -> fs.getId().equals(
				EActaProperty.FISCAL_CODE.getCode())).findFirst().get().getValue());

		Long userContainerId;
		final IdUtenteType userId = new IdUtenteType();
		try {
			userContainerId = Long.valueOf(findObject(repositoryId, principalId,
					EnumObjectType.UTENTE_PROPERTIES_TYPE, EnumPropertyFilter.ALL, EnumQueryOperator.EQUALS,
					queryConditionMap, null, backofficeServiceClient, paramMap, EProperty.DB_KEY));
			userId.setValue(userContainerId);
			cache.setUserId(userId);
		} catch (NumberFormatException | ActaException e) {
			throw e;
		}

		

		return userId;
	}

	private IdentificatoreDocumento createDocument(final QueueDocument entity, final ObjectIdType repositoryId,
			final PrincipalIdType principalId, final ObjectIdType volumeId,
			final Map<String, IdVitalRecordCodeType> vitalRecordsMap, final IdStatoDiEfficaciaType efficacyTypeId, IdStatoDiEfficaciaType efficacyTypeNonFirmatoId,
			final IdFormaDocumentariaType formatTypeId, final ObjectIdType classificationId,
			final Map<String, Object> paramMap, Map<String, String> dynamicAttributesMap, boolean isPrimario, String ente) 
					throws DatabaseException, JAXBException, ActaException {
		
		logger.debug("Recupero dallo storage i bytes dal file dal path " + entity.getPath() );
		//final byte[] docBytes = storageEJB.find(entity.getPath() );// + "." + entity.getExtension());
		InputStream docBytes = storageEJB.getStream( entity.getPath() );// + "." + entity.getExtension());

		entity.setDocumentType(genericDataAccessEJB.findUnique(entity.getDocumentType(), DocumentType.class,
				DocumentType_.documentsAttribute));

		// Transform attributes list into map
		final Map<String, String> attributesMap = entity.getDocumentType().getDocumentsAttribute().stream().collect(
				Collectors.toMap(dt -> dt.getAttributeType().getId(), dt -> dt.getAttributeValue()));

		logger.debug("attributesMap " + attributesMap );	 
		
		// Create document request info object
		final InfoRichiestaCreazione requestInfo = createDocumentRequestInfo(entity, docBytes, volumeId, attributesMap, dynamicAttributesMap,
				vitalRecordsMap, efficacyTypeId, efficacyTypeNonFirmatoId, formatTypeId, classificationId, isPrimario);
		
		// Create remote document into parent folder
		IdentificatoreDocumento documentId;
		try {
			documentId = documentServiceClient.createDocument(repositoryId, principalId,
					EnumTipoOperazione.ELETTRONICO, requestInfo, paramMap);
		} catch (Exception e1) {
			throw new ActaException("Errore nella creazione documento");
		}

		 if( dynamicAttributesMap.containsKey("annotazioneVRCImpegno")  && dynamicAttributesMap.get("annotazioneVRCImpegno")!=null ) {
			 boolean annotazioneFormale = getValue(EAttributeType.ANNOTAZIONE_FORMALE, attributesMap);
			 logger.info("Aggiungo l'annotazione formale per il VCR impegno - annotazioneFormale " + annotazioneFormale );
			 try {
				managementServiceClient.addAnnotazioni(repositoryId, principalId, paramMap, dynamicAttributesMap.get("annotazioneVRCImpegno"), annotazioneFormale, documentId.getObjectIdDocumento());
			} catch (Exception e) {
				logger.info("Errore nella aggiunta dell'annotazione" );
			}
		 }
		 
		 //if( ente.equalsIgnoreCase("CMTO")){
		 if( dynamicAttributesMap.containsKey("annotazioneDD")  && dynamicAttributesMap.get("annotazioneDD")!=null && 
				 documentId!=null && documentId.getObjectIdDocumento()!=null) {
			 boolean annotazioneFormale = getValue(EAttributeType.ANNOTAZIONE_FORMALE, attributesMap);
			 logger.info("Aggiungo l'annotazione formale per la DD - annotazioneFormale " + annotazioneFormale );
			
			 String annotazioniDD = dynamicAttributesMap.get("annotazioneDD");
			 StringTokenizer st = new StringTokenizer(annotazioniDD, "##");
			 while (st.hasMoreElements()) {
				 String annotazioneDD = st.nextToken();
				 logger.info("Annotazione: " + annotazioneDD );
				 try {
					managementServiceClient.addAnnotazioni(repositoryId, principalId, paramMap, annotazioneDD, annotazioneFormale, documentId.getObjectIdDocumento());
				} catch (Exception e) {
					logger.info("Errore nella aggiunta dell'annotazione" );
				}
			 }
			 
		 }
		 
		 if( dynamicAttributesMap.containsKey("annotazioneLIQ")  && dynamicAttributesMap.get("annotazioneLIQ")!=null && 
				 documentId!=null && documentId.getObjectIdDocumento()!=null) {
			 boolean annotazioneFormale = getValue(EAttributeType.ANNOTAZIONE_FORMALE, attributesMap);
			 logger.info("Aggiungo l'annotazione formale per la LIQ - annotazioneFormale " + annotazioneFormale );
			
			 String annotazioniDD = dynamicAttributesMap.get("annotazioneLIQ");
			 StringTokenizer st = new StringTokenizer(annotazioniDD, "##");
			 while (st.hasMoreElements()) {
				 String annotazioneDD = st.nextToken();
				 logger.info("Annotazione: " + annotazioneDD );
				 try {
					managementServiceClient.addAnnotazioni(repositoryId, principalId, paramMap, annotazioneDD, annotazioneFormale, documentId.getObjectIdDocumento());
				} catch (Exception e) {
					logger.info("Errore nella aggiunta dell'annotazione" );
				}
			 }
			 
		 }
		 
		 if( dynamicAttributesMap.containsKey("annotazioneDCR")  && dynamicAttributesMap.get("annotazioneDCR")!=null && 
				 documentId!=null && documentId.getObjectIdDocumento()!=null) {
			 boolean annotazioneFormale = getValue(EAttributeType.ANNOTAZIONE_FORMALE, attributesMap);
			 logger.info("Aggiungo l'annotazione formale per DCR - annotazioneFormale " + annotazioneFormale );
			
			 String annotazioniDD = dynamicAttributesMap.get("annotazioneDCR");
			 StringTokenizer st = new StringTokenizer(annotazioniDD, "##");
			 while (st.hasMoreElements()) {
				 String annotazioneDD = st.nextToken();
				 logger.info("Annotazione: " + annotazioneDD );
				 try {
					managementServiceClient.addAnnotazioni(repositoryId, principalId, paramMap, annotazioneDD, annotazioneFormale, documentId.getObjectIdDocumento());
				} catch (Exception e) {
					logger.info("Errore nella aggiunta dell'annotazione" );
				}
			 }
			 
		 }
		 
		 if( dynamicAttributesMap.containsKey("annotazioneDCRC")  && dynamicAttributesMap.get("annotazioneDCRC")!=null && 
				 documentId!=null && documentId.getObjectIdDocumento()!=null) {
			 boolean annotazioneFormale = getValue(EAttributeType.ANNOTAZIONE_FORMALE, attributesMap);
			 logger.info("Aggiungo l'annotazione formale per DCRC - annotazioneFormale " + annotazioneFormale );
			
			 String annotazioniDD = dynamicAttributesMap.get("annotazioneDCRC");
			 StringTokenizer st = new StringTokenizer(annotazioniDD, "##");
			 while (st.hasMoreElements()) {
				 String annotazioneDD = st.nextToken();
				 logger.info("Annotazione: " + annotazioneDD );
				 try {
					managementServiceClient.addAnnotazioni(repositoryId, principalId, paramMap, annotazioneDD, annotazioneFormale, documentId.getObjectIdDocumento());
				} catch (Exception e) {
					logger.info("Errore nella aggiunta dell'annotazione" );
				}
			 }
		 }
		 
		 if( dynamicAttributesMap.containsKey("annotazioneDCRS")  && dynamicAttributesMap.get("annotazioneDCRS")!=null && 
				 documentId!=null && documentId.getObjectIdDocumento()!=null) {
			 boolean annotazioneFormale = getValue(EAttributeType.ANNOTAZIONE_FORMALE, attributesMap);
			 logger.info("Aggiungo l'annotazione formale per DCRS - annotazioneFormale " + annotazioneFormale );
			
			 String annotazioniDD = dynamicAttributesMap.get("annotazioneDCRS");
			 StringTokenizer st = new StringTokenizer(annotazioniDD, "##");
			 while (st.hasMoreElements()) {
				 String annotazioneDD = st.nextToken();
				 logger.info("Annotazione: " + annotazioneDD );
				 try {
					managementServiceClient.addAnnotazioni(repositoryId, principalId, paramMap, annotazioneDD, annotazioneFormale, documentId.getObjectIdDocumento());
				} catch (Exception e) {
					logger.info("Errore nella aggiunta dell'annotazione" );
				}
			 }
		 }
		 
		 if( dynamicAttributesMap.containsKey("annotazioneDEL_CONS")  && dynamicAttributesMap.get("annotazioneDEL_CONS")!=null && 
				 documentId!=null && documentId.getObjectIdDocumento()!=null) {
			 boolean annotazioneFormale = getValue(EAttributeType.ANNOTAZIONE_FORMALE, attributesMap);
			 logger.info("Aggiungo l'annotazione formale per DEL_CONS - annotazioneFormale " + annotazioneFormale );
			
			 String annotazioniDD = dynamicAttributesMap.get("annotazioneDEL_CONS");
			 StringTokenizer st = new StringTokenizer(annotazioniDD, "##");
			 while (st.hasMoreElements()) {
				 String annotazioneDD = st.nextToken();
				 logger.info("Annotazione: " + annotazioneDD );
				 try {
					managementServiceClient.addAnnotazioni(repositoryId, principalId, paramMap, annotazioneDD, annotazioneFormale, documentId.getObjectIdDocumento());
				} catch (Exception e) {
					logger.info("Errore nella aggiunta dell'annotazione" );
				}
			 }
			 
		 }
		// }
		 
		
		try {
			docBytes.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return documentId;
	}
	
	protected Boolean getValue(final EAttributeType attributeType, Map<String, String> attributesMap) {
        if( attributesMap.get(attributeType.getCode())!=null ){
        	Boolean value = Boolean.valueOf(attributesMap.get(attributeType.getCode()));
        	logger.debug("Attribute[" + attributeType.getCode() + "]-" + value);
        	return value;
        } else 
        	return false;        
    }
	
	@Deprecated
	private void updateAttachmentGroup(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final QueueDocument entity, final Map<String, Object> paramMap) throws ObjectNotFoundException, ActaException {
		// Parent document
		final ObjectIdType objectIdType = new ObjectIdType();
		objectIdType.setValue(entity.getMainDocument().getDocumentId());

		// Properties classified for EnumObjectType
		final Map<EnumObjectType, Map<EProperty, Object>> propertyMap = new HashMap<>();

		// Properties for specific EnumObjectType
		final Map<EProperty, Object> tmpPropertyMap = new HashMap<>();

		// Properties for EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE
		tmpPropertyMap.put(EProperty.DOC_ATTACHMENT, true);
		tmpPropertyMap.put(EProperty.NUM_ATTACHMENT, entity.getAttachments().size());
		propertyMap.put(EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE, new HashMap<>(tmpPropertyMap));

		try {
			if (objectServiceClient.updateProperties(repositoryId, objectIdType, principalId, setQueryNameValueType(
					propertyMap), paramMap) == null) {
				throw new ObjectNotFoundException("UpdateAttachmentGroupFailed-AttachmentId[" + entity.getId() + "]");
			}
		} catch (Exception e) {
			throw new ActaException("Errore nell'update delle properties");
		}
	}

	private Map<QueryNameType, ValueType> setQueryNameValueType(
			final Map<EnumObjectType, Map<EProperty, Object>> propertyMap) {
		final Map<QueryNameType, ValueType> properties = new HashMap<>();
		propertyMap.forEach((k, v) -> {
			final QueryNameType queryNameType = new QueryNameType();
			queryNameType.setClassName(k.value());
			v.forEach((kp, vp) -> {
				final ValueType valueType = new ValueType();
				queryNameType.setPropertyName(kp.code);
				valueType.getContent().add(String.valueOf(vp));
				properties.put(queryNameType, valueType);
			});
		});

		return properties;
	}

	private InfoRichiestaCreazione createDocumentRequestInfo(final QueueDocument entity, final byte[] docBytes,
			final ObjectIdType containerId, final Map<String, String> attributesMap, final Map<String, String> dynamicAttributesMap,
			final Map<String, IdVitalRecordCodeType> vitalRecordsMap, final IdStatoDiEfficaciaType efficacyTypeId, IdStatoDiEfficaciaType efficacyTypeNonFirmatoId,
			final IdFormaDocumentariaType formatTypeId, final ObjectIdType classificationId, boolean isPrimario) {

		// Create content stream as expected by Acaris
		final AcarisContentStreamType acarisContentStream = createContentStream(docBytes, entity.getName(), entity
				.getExtension(), EnumMimeTypeType.fromValue(entity.getMimeType()));

		// Get builder from DocumentType
		IntegrationResource intRes = new IntegrationResource();
		EIntegrationResource eIntRes = intRes.resolve( entity.getDocumentType().getId() , dynamicAttributesMap);
		
		final DocumentRequestBuilder builder = RequestBuilderFactory.getBuilder(entity.getDocumentType().getId(), dynamicAttributesMap);
		//		EIntegrationResource.resolve(entity.getDocumentType().getId()));
		logger.debug("builder::: " + builder);

		// Create returning object
		final DocumentRequestBean requestBean = new DocumentRequestBean();
		requestBean.setAttributesMap(attributesMap);
		requestBean.setDynamicAttributesMap(dynamicAttributesMap);
		requestBean.setContentStream(acarisContentStream);
		requestBean.setEfficacyTypeId(efficacyTypeId);
		requestBean.setEfficacyNonFirmatoTypeId(efficacyTypeNonFirmatoId);
		requestBean.setFormatTypeId(formatTypeId);
		requestBean.setVitalRecordsMap(vitalRecordsMap);
		requestBean.setClassificationId(classificationId);
		requestBean.setAooCode(entity.getAooCode());
		requestBean.setNomeFile( entity.getName());
		requestBean.setEstensioneFile( entity.getExtension());
		requestBean.setMimetype( entity.getMimeType());
		requestBean.setPrimario( isPrimario );
		final InfoRichiestaCreazione requestInfo = builder.build(containerId, requestBean, entity.getAttachments());

		return requestInfo;
	}

	private InfoRichiestaCreazione createDocumentRequestInfo(final QueueDocument entity, InputStream docBytes,
			final ObjectIdType containerId, final Map<String, String> attributesMap, final Map<String, String> dynamicAttributesMap,
			final Map<String, IdVitalRecordCodeType> vitalRecordsMap, final IdStatoDiEfficaciaType efficacyTypeId, IdStatoDiEfficaciaType efficacyTypeNonFirmatoId,
			final IdFormaDocumentariaType formatTypeId, final ObjectIdType classificationId, boolean isPrimario) {

		// Create content stream as expected by Acaris
		final AcarisContentStreamType acarisContentStream = createContentStream(docBytes, entity.getName(), entity
				.getExtension(), EnumMimeTypeType.fromValue(entity.getMimeType()));

		// Get builder from DocumentType
		//final DocumentRequestBuilder builder = RequestBuilderFactory.getBuilder(EIntegrationResource.resolve(entity
		//		.getDocumentType().getId()));
		final DocumentRequestBuilder builder = RequestBuilderFactory.getBuilder(entity.getDocumentType().getId(), dynamicAttributesMap);
		
		// Create returning object
		final DocumentRequestBean requestBean = new DocumentRequestBean();
		requestBean.setAttributesMap(attributesMap);
		requestBean.setDynamicAttributesMap(dynamicAttributesMap);
		requestBean.setContentStream(acarisContentStream);
		requestBean.setEfficacyTypeId(efficacyTypeId);
		requestBean.setEfficacyNonFirmatoTypeId(efficacyTypeNonFirmatoId);
		requestBean.setFormatTypeId(formatTypeId);
		requestBean.setVitalRecordsMap(vitalRecordsMap);
		requestBean.setClassificationId(classificationId);
		requestBean.setAooCode(entity.getAooCode());
		requestBean.setNomeFile( entity.getName());
		requestBean.setEstensioneFile( entity.getExtension());
		requestBean.setMimetype( entity.getMimeType());
		requestBean.setPrimario( isPrimario );
		final InfoRichiestaCreazione requestInfo = builder.build(containerId, requestBean, entity.getAttachments());

		
		return requestInfo;
	}
	/**
	 * Create classification for document in remote target.
	 *
	 * @param document The document to classify.
	 * @return The operation result.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public OperationResult createClassification(final QueueDocument document) {
		logger.info(String.format("createClassification[%s]", document.getName()));

		final Map<String, Object> paramMap = createParameterMap(document);

		// Get list of all authority properties
		final List<AuthorityProperty> authorityProperties = authorityPropertiesEJB.loadList(AuthorityProperty.class);

		// Work-around for reusing existing methods
		final AdminOrganization adminOrganization = getAdminOrganization(document);

		final OperationResult operationResult = new OperationResult(paramMap.get(AbstractLogHandler.KEY_FLOW_ID)
				.toString());
		try {
			// Get remote repository
			final ObjectIdType repositoryId = findRepository(authorityProperties, paramMap);
			if( repositoryId!=null)
				logger.info("repositoryId::: " + repositoryId.getValue() );

			// Get remote principal related to repository
			final PrincipalIdType principalId = findPrincipal(repositoryId, adminOrganization, authorityProperties,
					paramMap);
			if( principalId!=null)
				logger.info("principalId::: " + principalId.getValue() );

			final Map<String, String> dynamicAttributesMap = new HashMap<String, String>();
			try {
				logger.info("effettuo ricerca:::::::::::::::::. idDoc " + document.getId() );
				List<QueueDocAttribute> attributesDoc = attributeDocEJB.find(document.getId() );
				logger.info("trovato:::::::::::::::::. attributesDoc " + attributesDoc );
				for(QueueDocAttribute attributeDoc : attributesDoc){
					logger.info("attributeDoc.getAttributeTag() " + attributeDoc.getAttributeTag());
					logger.info("attributeDoc.getStrValue() " + attributeDoc.getStrValue());
					dynamicAttributesMap.put(attributeDoc.getAttributeTag(), attributeDoc.getStrValue());
				}
			} catch (DatabaseException | JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// -- Check if CLASSIFICATION is requested --
			classify(repositoryId, principalId, authorityProperties, document, create(document), paramMap, dynamicAttributesMap);
			operationResult.setResultType(OperationResult.ResultType.SUCCESS);

		} catch (ObjectNotFoundException e) {
			logger.error(String.format("[ErrorClassifyingDocument][%s]-[%s]", document.getName(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED);
		} catch (ClassificationException e) {
			logger.error(String.format("[ErrorClassifyingDocument][%s]-[%s]", document.getName(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED_CLASSIFICATION);
			document.setClassified(EClassificationStatus.FIXABLE);
		} catch (ActaException e) {
			logger.error(String.format("[ErrorClassifyingDocument][%s]-[%s]", document.getName(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED_CLASSIFICATION);
			document.setClassified(EClassificationStatus.FIXABLE);
		}

		document.setModified(LocalDateTime.now());

		return operationResult;
	}

	private AdminOrganization getAdminOrganization(final QueueDocument document) {
		
		final AdminOrganization adminOrganization = new AdminOrganization();
		adminOrganization.setDocumentType(document.getDocumentType());
		adminOrganization.setNodeCode(document.getNodeCode());
		adminOrganization.setStructureCode(document.getStructureCode());
		
		final AdminOrganizationKey adminOrganizationKey = new AdminOrganizationKey();
		adminOrganizationKey.setDocumentTypeId(document.getDocumentType().getId());
		adminOrganizationKey.setAooCode(document.getAooCode());
		adminOrganization.setId(adminOrganizationKey);

		return adminOrganization;
	}

	private ObjectIdType createClassification(final QueueDocument entity, final ObjectIdType classificationDocId,
			final ObjectIdType repositoryId, final PrincipalIdType principalId, final Map<String, Object> paramMap, 
			Map<String, String> dynamicAttributesMap)
			throws ClassificationException {
		logger.debug("Metodo createClassification");
		ObjectIdType dossierId = null;
		ObjectIdType aggregatoIndiceClassificazioneId = null;
		ObjectIdType classificationId = null;
		
		ActaEnte actaEnte = new ActaEnte();
		String ente = actaEnte.getEnte();
		logger.debug("ente " + ente);
		
		String dbKeyTitolario = actaEnte.getDbKeyTitolario();
		logger.debug("dbKeyTitolario" + dbKeyTitolario);
		try {
			//dossierId = findDossier(repositoryId, principalId, entity.getDossierCode(), paramMap);
			if (dynamicAttributesMap != null && dynamicAttributesMap.get("codiceDossierFascicolo")!=null ) {
				logger.debug("Inserisco nel dossier/fascicolo con id object " + dynamicAttributesMap.get("idDossierFascicolo"));
				dossierId = new ObjectIdType();
				dossierId.setValue( dynamicAttributesMap.get("idDossierFascicolo") );
				
				dossierId = findDossier(repositoryId, principalId, dynamicAttributesMap.get("CodAOO_Orig"), 
						dynamicAttributesMap.get("codiceDossierFascicolo") , 
						dynamicAttributesMap.get("suffissoCodiceDossierFascicolo"),
						dynamicAttributesMap.get("numeroSottoFascicolo"),
						dynamicAttributesMap.get("voceTitolarioDossierFascicolo"), dbKeyTitolario,
						paramMap);
				
				logger.debug("Richiamo createClassification con false"  );
				classificationId = createClassification(entity, classificationDocId, repositoryId, principalId, dossierId,
						false, paramMap);
				logger.debug("classificationId: " +classificationId );
				
				if( classificationId==null ){
					logger.debug("Richiamo createClassification con true"  );
					classificationId = createClassification(entity, classificationDocId, repositoryId, principalId,
							dossierId, true, paramMap);
					logger.debug("classificationId: " +classificationId );
				}
				
			}
			if (dynamicAttributesMap != null && dynamicAttributesMap.get("indiceClassificazioneEsteso")!=null ) {
				logger.debug("Inserisco nell'aggregato con indice di classificazione estesa " + dynamicAttributesMap.get("indiceClassificazioneEsteso"));
				aggregatoIndiceClassificazioneId = findAggregatoIndiceClassificazioneEsteso(repositoryId, principalId, dynamicAttributesMap.get("indiceClassificazioneEsteso"), paramMap);
				logger.debug("aggregatoIndiceClassificazioneId " + aggregatoIndiceClassificazioneId);
				classificationId = createClassification(entity, classificationDocId, repositoryId, principalId, aggregatoIndiceClassificazioneId,
						false, paramMap);
				
				if( classificationId==null ){
					logger.debug("Richiamo createClassification con true"  );
					createClassification(entity, classificationDocId, repositoryId, principalId, aggregatoIndiceClassificazioneId,
							true, paramMap);
					logger.debug("classificationId: " +classificationId );
				}
			}
				
			
		} catch (ObjectNotFoundException e) { // dossier not found
			logger.error("Errore ObjectNotFoundException ", e);
			
			throw new ClassificationException(e);
		} catch (AcarisException e) { // specific error creating classification => try with offline=true
			logger.error("Errore ", e);
			try {
				logger.debug("Richiamo createClassification con true"  );
				classificationId = createClassification(entity, classificationDocId, repositoryId, principalId,
						dossierId, true, paramMap);
				logger.debug("classificationId: " +classificationId );
			} catch (AcarisException ae) {
				throw new ClassificationException(ae);
			} catch (ActaException ae) {
				throw new ClassificationException(ae);
			}
		} catch (ActaException e) { 
			logger.error("Errore nell'invocazione dei servizio acta ", e);
			
			throw new ClassificationException(e);
		}

		if (classificationId == null) {
			//throw new ClassificationException(String.format("[ErrorCreatingClassification][%s][%s]",
			//		classificationDocId, dossierId));
		}

		return classificationId;
	}

	private ObjectIdType createClassification(final QueueDocument entity, final ObjectIdType classificationDocId,
			final ObjectIdType repositoryId, final PrincipalIdType principalId, final ObjectIdType dossierId,
			final boolean offline, final Map<String, Object> paramMap) throws AcarisException, ActaException {
		final VarargsType varargsType = new VarargsType();

		// Properties requested for classification
		final Map<EProperty, Object> properties = new HashMap<>();
		properties.put(EProperty.ADD_ATTACHMENT, entity.getAttachments().size() > 0);
		properties.put(EProperty.OFFLINE_REQUEST, offline);

		properties.forEach((k, v) -> {
			ItemType itemType = new ItemType();
			itemType.setKey(k.code);
			itemType.setValue(String.valueOf(v));
			varargsType.getItems().add(itemType);
		});
		ObjectIdType classificationId;
		try {
			classificationId = multiFilingServiceClient.addClassification(repositoryId, principalId,
					classificationDocId, dossierId, varargsType, paramMap);
		} catch (Exception e) {
			throw new ActaException("Errore nella classificazione");
		}

		return classificationId;
	}
	
	private boolean useVolume(String idDocumento){
		DocumentType tipoDocumento = null;
		final List<DocumentType> documentTypes = documentTypeEJB.loadList(DocumentType.class);
		for(DocumentType d : documentTypes){
			logger.debug("tipo documento " +  d.getId());
			if( d.getId().equalsIgnoreCase( idDocumento )){
				tipoDocumento = d;
			}
		}
		//logger.debug("tipoDocumento " + tipoDocumento );
		
		if( tipoDocumento!=null && 
				(tipoDocumento.getCreaVolume()==null || tipoDocumento.getCreaVolume().equalsIgnoreCase("true") )
			){
			logger.debug("tipo documento con volume");
			return true;
		} else {
			logger.debug("tipo documento senza volume");
			return false;
		}
	}

	/**
	 * Create folder (volume) in remote target for the given organization.
	 *
	 * @param organization The organization whose folder is requested.
	 * @return The operation result.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public OperationResult createFolder(final AdminOrganization organization, Map<String, String> dynamicAttributesMap) {
		logger.info(String.format("createFolder[%s][%s]", organization.getId().getAooCode(), organization.getId()
				.getDocumentTypeId()));

		final Map<String, Object> paramMap = createParameterMap(null);

		// Get list of all authority properties
		final List<AuthorityProperty> authorityProperties = authorityPropertiesEJB.loadList(AuthorityProperty.class);

		final OperationResult operationResult = new OperationResult(paramMap.get(AbstractLogHandler.KEY_FLOW_ID)
				.toString());
		try {
			// Get remote repository
			final ObjectIdType repositoryId = findRepository(authorityProperties, paramMap);

			// Get remote principal related to repository
			final PrincipalIdType principalId = findPrincipal(repositoryId, organization, authorityProperties,
					paramMap);

			ActaEnte actaEnte = new ActaEnte();
			String ente = actaEnte.getEnte();
			logger.debug("ente " + ente);
			
			String dbKeyTitolario = actaEnte.getDbKeyTitolario();
			logger.debug("dbKeyTitolario" + dbKeyTitolario);
			
			// Get remote container / parent folder
			final ObjectIdType serieId = findSerie(repositoryId, principalId, organization, paramMap, ente, dbKeyTitolario);

			boolean usaVolume = useVolume( organization.getDocumentType().getId() );
			
			if( usaVolume ){
				// Check if volume already exists
				try {
					final ObjectIdType volume = findVolume(repositoryId, principalId, serieId, organization
							.getDocumentType(), paramMap, null, ente);
					logger.info(String.format("VolumeAlreadyExists[%s][%s][%s]", organization.getId().getAooCode(),
							organization.getId().getDocumentTypeId(), volume.getValue()));
					operationResult.setResultType(OperationResult.ResultType.AUTO_SUCCESS);
					operationResult.getAttributes().put(OperationResult.AttributeType.OBJECT_ID_DOCUMENT, volume
							.getValue());
					return operationResult;
				} catch (ObjectNotFoundException e) {
					logger.warn(String.format("VolumeNotFound[%s][%s]-[%s]", organization.getId().getAooCode(),
							organization.getId().getDocumentTypeId(), e.getMessage()));
				} catch (TooManyObjectsException e) {
					logger.warn(String.format("VolumeNotFound[%s][%s]-[%s]", organization.getId().getAooCode(),
							organization.getId().getDocumentTypeId(), e.getMessage()));
					operationResult.setResultType(OperationResult.ResultType.FAILED);
					return operationResult;
				}
	
				// Create folder properties object
				final VolumeSerieTipologicaDocumentiPropertiesType properties = createVolumeProperties(organization
						.getDocumentType(), new Date(), dynamicAttributesMap);
	
				// Create remote folder into parent folder
				ObjectIdType volumeId;
				try {
					volumeId = objectServiceClient.createFolder(repositoryId,
							EnumFolderObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE, principalId, properties,
							serieId, paramMap);
				} catch (Exception e) {
					throw new ActaException("Errore nella creazione del volume");
				}
				logger.info(String.format("CreatedVolume[%s][%s][%s]", organization.getId().getAooCode(), organization
						.getId().getDocumentTypeId(), (volumeId != null ? volumeId.getValue() : NOT_AVAILABLE)));
	
				if (volumeId != null) {
					operationResult.setResultType(OperationResult.ResultType.SUCCESS);
					operationResult.getAttributes().put(OperationResult.AttributeType.OBJECT_ID_DOCUMENT, volumeId
							.getValue());
				}
			} else {
				//il volume non deve essere creato
				operationResult.setResultType(OperationResult.ResultType.SUCCESS);
			}
			return operationResult;
		} catch (ObjectNotFoundException | TooManyObjectsException | ActaException e) {
			logger.error(String.format("[ErrorCreatingFolder][%s][%s]-[%s]", organization.getId().getAooCode(),
					organization.getId().getDocumentTypeId(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED);
			return operationResult;
		}
	}

	private VolumeSerieTipologicaDocumentiPropertiesType createVolumeProperties(final DocumentType documentType, Date date,
			Map<String, String> dynamicAttributesMap) {
		final VolumeSerieTipologicaDocumentiPropertiesType properties =
				new VolumeSerieTipologicaDocumentiPropertiesType();
		//final EIntegrationResource integrationResource = EIntegrationResource.resolve(documentType.getId());
		IntegrationResource intRes = new IntegrationResource();
		EIntegrationResource eIntRes = intRes.resolve( documentType.getId() , dynamicAttributesMap);
		
//		properties.setConservazioneCorrente(integrationResource.getVolumeCurrRetention());
//		properties.setConservazioneGenerale(integrationResource.getVolumeGenRetention());
//		properties.setDescrizione(getVolumeDescription(integrationResource, 0, date));
		properties.setConservazioneCorrente(eIntRes.getVolumeCurrRetention(documentType.getId()));
		properties.setConservazioneGenerale(eIntRes.getVolumeGenRetention(documentType.getId()));
		properties.setDescrizione(getVolumeDescription(eIntRes, documentType.getId(), 0, date));

		
		return properties;
	}

	/**
	 * Close folder (volume) in remote target for the given organization.
	 *
	 * @param organization The organization whose folder is requested.
	 * @return The operation result.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public OperationResult closeFolder(final AdminOrganization organization, Map<String, String> dynamicAttributesMap) {
		logger.info(String.format("closeFolder[%s][%s]", organization.getId().getAooCode(), organization.getId()
				.getDocumentTypeId()));

		final Map<String, Object> paramMap = createParameterMap(null);

		// Get list of all authority properties
		final List<AuthorityProperty> authorityProperties = authorityPropertiesEJB.loadList(AuthorityProperty.class);

		final OperationResult operationResult = new OperationResult(paramMap.get(AbstractLogHandler.KEY_FLOW_ID)
				.toString());

		try {
			// Get remote repository
			final ObjectIdType repositoryId = findRepository(authorityProperties, paramMap);

			// Get remote principal related to repository
			final PrincipalIdType principalId = findPrincipal(repositoryId, organization, authorityProperties,
					paramMap);

			ActaEnte actaEnte = new ActaEnte();
			String ente = actaEnte.getEnte();
			logger.debug("ente " + ente);
			
			String dbKeyTitolario = actaEnte.getDbKeyTitolario();
			logger.debug("dbKeyTitolario" + dbKeyTitolario);
			
			// Get remote container / parent folder
			final ObjectIdType serieId = findSerie(repositoryId, principalId, organization, paramMap, ente, dbKeyTitolario);

			// Do closing operation
						
			int validityMonths = 0;
			if( ente!=null && ente.equalsIgnoreCase("RP")){
				IntegrationResourceRP intRP = new IntegrationResourceRP(dynamicAttributesMap);
				validityMonths = intRP.getValidityMonths(organization.getDocumentType().getId());
			}
			if( ente!=null && ente.equalsIgnoreCase("CMTO")){
				IntegrationResourceCMTO intCMTO = new IntegrationResourceCMTO();
				validityMonths = intCMTO.getValidityMonths(organization.getDocumentType().getId());
			}
			if( ente!=null && ente.equalsIgnoreCase("COTO")){
				IntegrationResourceCOTO intCOTO = new IntegrationResourceCOTO();
				validityMonths = intCOTO.getValidityMonths(organization.getDocumentType().getId());
			}
				
			operationResult.setResultType(closeVolume(repositoryId, principalId, serieId, organization,
					//EIntegrationResource.resolve(organization.getDocumentType().getId()).getValidityMonths(),
					validityMonths,
					paramMap, ente, dynamicAttributesMap));

		} catch (ObjectNotFoundException | TooManyObjectsException | ActaException e) {
			logger.error(String.format("[ErrorClosingFolder][%s][%s]-[%s]", organization.getId().getAooCode(),
					organization.getId().getDocumentTypeId(), e.getMessage()));
			operationResult.setResultType(OperationResult.ResultType.FAILED);
		}

		return operationResult;
	}

	private OperationResult.ResultType closeVolume(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final ObjectIdType serieId, final AdminOrganization organization, final int monthsAgo,
			final Map<String, Object> paramMap, String ente, Map<String, String> dynamicAttributesMap) {
		OperationResult.ResultType resultType = OperationResult.ResultType.FAILED;
		
		boolean usaVolume = useVolume( organization.getDocumentType().getId() );
		
		if( !usaVolume){
			resultType = OperationResult.ResultType.AUTO_SUCCESS;
			return resultType;
		}
		
		try {
			// Find opened volume
			final ObjectIdType volumeId = findVolume(repositoryId, principalId, serieId,
					organization.getDocumentType(), monthsAgo, EStatusType.OPEN, paramMap, null, ente);
			logger.info(String.format("OpenedVolumeFound[%s][%s][%s]", organization.getId().getAooCode(), organization
					.getId().getDocumentTypeId(), volumeId.getValue()));

			try {
				if (objectServiceClient.closeFolder(repositoryId, principalId, volumeId, paramMap) != null) {
					final PrincipalCache.VolumeKey volumeKey = getVolumeKey(serieId, organization.getDocumentType(),
							monthsAgo, EStatusType.OPEN, null, ente);

					// Remove volumeId from cache
					final PrincipalCache cache = getFromCache(principalId);
					if (cache.getVolumeMap().containsKey(volumeKey)) {
						cache.getVolumeMap().remove(volumeKey);
						logger.debug("Removed-[" + ActaCache.NAME + "][volumeId]");
					}

					resultType = OperationResult.ResultType.SUCCESS;
					logger.info(String.format("VolumeClosed[%s][%s][%s]", organization.getId().getAooCode(), organization
							.getId().getDocumentTypeId(), volumeId.getValue()));
				} else {
					logger.info(String.format("ErrorClosingVolume[%s][%s][%s]", organization.getId().getAooCode(),
							organization.getId().getDocumentTypeId(), volumeId.getValue()));
				}
			} catch (Exception e) {
				throw new ActaException("Errore nella chiusura del volume");
			}
		} catch (ObjectNotFoundException e) {
			logger.warn(String.format("OpenedVolumeNotFound[%s][%s]-[%s]", organization.getId().getAooCode(),
					organization.getId().getDocumentTypeId(), e.getMessage()));
			try {
				// Just for logging reason, find volume status
				final Map<EProperty, Object> conditionMap = new HashMap<>();
				
				IntegrationResource intRes = new IntegrationResource();
				EIntegrationResource eIntRes = intRes.resolve( organization.getDocumentType().getId(), dynamicAttributesMap );
				conditionMap.put(EProperty.DESCRIPTION, getVolumeDescription(
						//EIntegrationResource.resolve(organization.getDocumentType().getId())
						eIntRes, organization.getDocumentType().getId(), monthsAgo, new Date()));

				final Map<EProperty, Object> objectValues = findProperties(repositoryId, principalId,
						EnumObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE.value(),
						EnumPropertyFilter.NONE, EnumQueryOperator.EQUALS, conditionMap, serieId, objectServiceClient,
						paramMap, Arrays.asList(EProperty.OBJECT_ID, EProperty.STATUS));

				if (objectValues != null && !objectValues.isEmpty()) {
					resultType = OperationResult.ResultType.AUTO_SUCCESS;
					logger.info(String.format("ClosedVolumeFound[%s][%s][%s][%s]", organization.getId().getAooCode(),
							organization.getId().getDocumentTypeId(), objectValues.get(EProperty.OBJECT_ID),
							objectValues.get(EProperty.STATUS)));
				}
			} catch (ObjectNotFoundException | TooManyObjectsException | ActaException ie) {
				logger.warn(String.format("ClosedVolumeNotFound[%s][%s]-[%s]", organization.getId().getAooCode(),
						organization.getId().getDocumentTypeId(), ie.getMessage()));
			}
		} catch (TooManyObjectsException e) {
			logger.warn(String.format("OpenVolumeNotFound[%s][%s]-[%s]", organization.getId().getAooCode(),
					organization.getId().getDocumentTypeId(), e.getMessage()));
		} catch (ActaException e) {
			logger.warn(String.format("OpenVolumeNotFound[%s][%s]-[%s]", organization.getId().getAooCode(),
					organization.getId().getDocumentTypeId(), e.getMessage()));
		}

		return resultType;
	}

	/**
	 * Retrieve the volume identifier for the given organization.
	 *
	 * @param organization The organization whose folder is requested.
	 * @return The volume identifier.
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String findVolume(final AdminOrganization organization, String ente, String dbKeyTitolario) {
		logger.info(String.format("findVolume[%s][%s]", organization.getId().getAooCode(), organization.getId()
				.getDocumentTypeId()));

		ObjectIdType volumeId = null;

		final Map<String, Object> paramMap = createParameterMap(null);

		// Get list of all authority properties
		final List<AuthorityProperty> authorityProperties = authorityPropertiesEJB.loadList(AuthorityProperty.class);

		try {
			// Get remote repository
			final ObjectIdType repositoryId = findRepository(authorityProperties, paramMap);

			// Get remote principal related to repository
			final PrincipalIdType principalId = findPrincipal(repositoryId, organization, authorityProperties,
					paramMap);

			// Get remote container / parent folder
			final ObjectIdType serieId = findSerie(repositoryId, principalId, organization, paramMap, ente, dbKeyTitolario );

			volumeId = findVolume(repositoryId, principalId, serieId, organization.getDocumentType(), paramMap, null, ente);
			logger.info(String.format("FoundVolume[%s][%s][%s]", organization.getId().getAooCode(),
					organization.getId().getDocumentTypeId(), volumeId.getValue()));
		} catch (ObjectNotFoundException | TooManyObjectsException | ActaException e) {
			logger.error(String.format("[ErrorFindingVolume][%s][%s]-[%s]", organization.getId().getAooCode(),
					organization.getId().getDocumentTypeId(), e.getMessage()));
		}

		return volumeId != null ? volumeId.getValue() : null;
	}

	private ObjectIdType findSerie(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final AdminOrganization organization, final Map<String, Object> paramMap, String ente, String dbKeyTitolario) throws ObjectNotFoundException,
			TooManyObjectsException, ActaException {
		
		// Serie metadata
		
		int validityMonths = 0;
		String serieCode = "";
		String serieDescription = "";
		String voceTitolario = "";
		if( ente!=null && ente.equalsIgnoreCase("RP")){
			if(organization.getDocumentType().getId().equalsIgnoreCase("REG")){
				//gestisco in modo diverso solo il caso dei registri
				
			} else {
				serieCode = organization.getDocumentType().getName();
				serieDescription = organization.getId().getAooCode() + " " + organization.getDocumentType().getDescription();
				voceTitolario = organization.getDocumentType().getVoceTitolario();
			}
		} else if( ente!=null && ente.equalsIgnoreCase("CMTO")){
			serieCode = organization.getDocumentType().getName() ;//+ "/" + organization.getId().getAooCode() ;
			serieDescription =  organization.getDocumentType().getDescription();
			voceTitolario = organization.getDocumentType().getVoceTitolario();
		} else if( ente!=null && ente.equalsIgnoreCase("COTO")){
			serieCode = organization.getDocumentType().getName() ;//+ "/" + organization.getId().getAooCode() ;
			serieDescription =  organization.getDocumentType().getDescription();
			voceTitolario = organization.getDocumentType().getVoceTitolario();
		} 
		
		logger.debug("Codice  " + serieCode);
		logger.debug("Descrizione " + serieDescription);
		logger.debug("Voce Titolario " + voceTitolario);
		
		String voceTitolarioId = null;
		String dbKeyVoceTitolario = null;
		try {
			logger.info("Cerco la voce del titolario  " + voceTitolario);
			VoceTitolarioBean voceTitolarioBean = getVoceTitolario(repositoryId, principalId, null, voceTitolario, dbKeyTitolario, paramMap);
			if( voceTitolarioBean!=null ){
				voceTitolarioId = voceTitolarioBean.getIdVoce();
				dbKeyVoceTitolario = voceTitolarioBean.getDbKeyVoce();
			}
		} catch (Exception e) {
			logger.error(String.format("[ErrorFindingVoce Titolario][%s]-[%s]", voceTitolario, e.getMessage()));
		}
		
		ObjectIdType objVoceTitolario = null;
		if (voceTitolarioId != null) {
			logger.info(String.format("Individuata Voce Titolario [%s][%s]", voceTitolario, voceTitolarioId));
			
			objVoceTitolario = new ObjectIdType();
			objVoceTitolario.setValue(voceTitolarioId);
		} else {
			throw new ObjectNotFoundException("NotFound voce titolario");
		}
		
		final PrincipalCache.SerieKey serieKey = new PrincipalCache.SerieKey(serieCode, serieDescription);

		// Search serieId into cache
		final PrincipalCache cache = getFromCache(principalId);
		if (cache.getSerieMap().containsKey(serieKey)) {
			logger.debug("Individuata la serie nella cache-[" + ActaCache.NAME + "][serieId]");
			return cache.getSerieMap().get(serieKey);
		}

		// Get remote container / serie folder where a new folder could be created
		final Map<EProperty, Object> conditionMap = new HashMap<>();
		conditionMap.put(EProperty.CODE, serieCode);
		conditionMap.put(EProperty.DESCRIPTION, serieDescription);
		// alternative query condition
		// conditionMap.put("paroleChiave", documentType.getName() + "_" + organization.getCode());

		
		String serieContainerId;
		ObjectIdType serieId = null;
		try {
			serieContainerId = findObject(repositoryId, principalId,
					EnumObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE, EnumPropertyFilter.NONE,
					EnumQueryOperator.EQUALS, conditionMap, objVoceTitolario, objectServiceClient, paramMap, EProperty.OBJECT_ID);
			
			logger.debug("serieContainerId: " + serieContainerId);
			
			serieId = new ObjectIdType();
			serieId.setValue(serieContainerId);

			// Store serieId into cache
			cache.getSerieMap().put(serieKey, serieId);
		} catch (ActaException e) {
			throw new ActaException("Errore nel servizio di ricerca serie");
		}
		

		return serieId;
	}

	private ObjectIdType findVolume(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final ObjectIdType parentId, final DocumentType documentType, final Map<String, Object> paramMap, 
			Map<String, String> dynamicAttributesMap, String ente)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		return findVolume(repositoryId, principalId, parentId, documentType, 0, EStatusType.OPEN, paramMap, dynamicAttributesMap, ente);
	}

	private ObjectIdType findVolume(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final ObjectIdType parentId, final DocumentType documentType, final int monthsAgo,
			final EStatusType status,
			final Map<String, Object> paramMap, Map<String, String> dynamicAttributesMap, String ente) 
					throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		
		// Volume key
		final PrincipalCache.VolumeKey volumeKey = getVolumeKey(parentId, documentType, monthsAgo, status, dynamicAttributesMap, ente);

		// Search volumeId into cache
		final PrincipalCache cache = getFromCache(principalId);
		if (cache.getVolumeMap().containsKey(volumeKey)) {
			logger.debug("Individuato il volume nella cache-[" + ActaCache.NAME + "][volumeId]");
			return cache.getVolumeMap().get(volumeKey);
		}

		// Get remote container / volume folder where a new document could be created
		final Map<EProperty, Object> conditionMap = new HashMap<>();
		Date date = new Date();
		if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("dataCronica") && dynamicAttributesMap.get("dataCronica")!=null ){
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				date = format.parse( dynamicAttributesMap.get("dataCronica") );
			} catch (Exception e) {
				logger.error("Erore nel parsing della data cronica");
			}
        }
		logger.debug("Data di riferimento per la creazione del volume: " + date);
		
		IntegrationResource intRes = new IntegrationResource();
		EIntegrationResource eIntRes = intRes.resolve( documentType.getId(), dynamicAttributesMap );
		final String volumeDescription = getVolumeDescription(eIntRes,documentType.getId(), 
				//EIntegrationResource.resolve(documentType.getId()),
				monthsAgo, date);
		logger.debug("Descrizione volume " + volumeDescription);
		conditionMap.put(EProperty.DESCRIPTION,	volumeDescription);
		conditionMap.put(EProperty.STATUS, status.getCode());

		String volumeContainerId;
		ObjectIdType volumeId = null;
		try {
			volumeContainerId = findObject(repositoryId, principalId,
					EnumObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE, EnumPropertyFilter.NONE,
					EnumQueryOperator.EQUALS, conditionMap, parentId, objectServiceClient, paramMap, EProperty.OBJECT_ID);
			logger.debug("Indivuatuato il volume: " + volumeContainerId);
			
			volumeId = new ObjectIdType();
			volumeId.setValue(volumeContainerId);

			// Store volumeId into cache
			cache.getVolumeMap().put(volumeKey, volumeId);
		} catch (ActaException e) {
			throw new ActaException("Errore nella ricerca del volume");
		}

		return volumeId;
	}

	private PrincipalCache.VolumeKey getVolumeKey(final ObjectIdType parentId, final DocumentType documentType,
			final int monthsAgo, final EStatusType status, Map<String, String> dynamicAttributesMap, String ente) {
		// Volume metadata
		logger.debug("MEtodo getVolumeKey");
		Date date = new Date();
		if( dynamicAttributesMap!=null && dynamicAttributesMap.containsKey("dataCronica") && dynamicAttributesMap.get("dataCronica")!=null ){
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				date = format.parse( dynamicAttributesMap.get("dataCronica") );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("Data " + date);
		IntegrationResource intRes = new IntegrationResource();
		EIntegrationResource eIntRes = intRes.resolve( ente, dynamicAttributesMap );
		final String volumeDescription = getVolumeDescription(
				//EIntegrationResource.resolve(documentType.getId()),
				eIntRes, documentType.getId(),
				monthsAgo, date);
		final PrincipalCache.VolumeKey volumeKey = new PrincipalCache.VolumeKey(parentId.getValue(), volumeDescription,
				status.name());

		logger.debug("volumekey " + volumeKey);
		return volumeKey;
	}

	private String getVolumeDescription(final EIntegrationResource eDocumentType, String tipo, final int monthsAgo, Date data) {
		logger.debug("eDocumentType " + eDocumentType + " tipo " + tipo );
		String volumePattern = eDocumentType.getVolumeDescrPattern(tipo);
		logger.debug("volumePattern " + volumePattern);
		if( volumePattern!=null && volumePattern.equalsIgnoreCase("3MM - yyyy")){
			
			logger.debug("calcolo la descrizione volume per la data " + data);
			//return LocalDate.now().minusMonths(monthsAgo).format(volumeFormatter);
			LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			String anno = ""+localDate.getYear();
			int meseCorrente = localDate.getMonth().getValue();
			if( meseCorrente==1 || meseCorrente ==2 || meseCorrente==3 ){
				return "Gen-Mar - " + anno;
			}
			if( meseCorrente==4 || meseCorrente ==5 || meseCorrente==6 ){
				return "Apr-Giu - " + anno;
			}
			if( meseCorrente==7 || meseCorrente ==8 || meseCorrente==9 ){
				return "Lug-Set - " + anno;
			}
			if( meseCorrente==10 || meseCorrente ==11 || meseCorrente==12 ){
				return "Ott-Dic - " + anno;
			}
			return anno;
		} else {
			final DateTimeFormatter volumeFormatter = DateTimeFormatter.ofPattern(volumePattern);
			logger.debug("calcolo la descrizione volume per la data " + data);
			//return LocalDate.now().minusMonths(monthsAgo).format(volumeFormatter);
			LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			String dataFormattata = localDate.minusMonths(monthsAgo).format(volumeFormatter);
			if( dataFormattata!=null && dataFormattata.length()>=1){
				dataFormattata = dataFormattata.substring(0, 1).toUpperCase() + dataFormattata.substring(1);
			}
			return dataFormattata;
		}
	}
	
	private ObjectIdType findDossier(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final String dossierCode, final Map<String, Object> paramMap) throws ObjectNotFoundException {
		// Search dossierId into cache
		final PrincipalCache cache = getFromCache(principalId);
		ObjectIdType dossierId = cache.getDossierMap().get(dossierCode);
		if (dossierId != null) {
			logger.debug("Resolved-[" + ActaCache.NAME + "][dossierId]");
			return dossierId;
		}

		final Map<EProperty, Object> conditionMap = new HashMap<>();
		//conditionMap.put(EProperty.STATUS, 1);
		conditionMap.put(EProperty.CODE, dossierCode);
		conditionMap.put(EProperty.DESCRIPTION, "Fascicolo procedimento integrazione STILO");

		for (final EDossierType dossierType : EDossierType.values()) {
			try {
				//EDossierType dossierType = EDossierType.FASCICOLO_REALE_ANNUALE;
				logger.info("Cerco il dossier di tipo " + dossierType);
				final String dossierContainerId = findObject(repositoryId, principalId, dossierType.getCode(),
						EnumPropertyFilter.NONE, /*EnumQueryOperator.LIKE,*/EnumQueryOperator.EQUALS, conditionMap, null, objectServiceClient,
						paramMap, EProperty.OBJECT_ID);
				if (dossierContainerId != null) {
					logger.info(String.format("Found-Dossier[%s][%s][%s]", dossierType.getCode(), dossierCode,
							dossierContainerId));
					dossierId = new ObjectIdType();
					dossierId.setValue(dossierContainerId);
					break;
				}
			} catch (ObjectNotFoundException | TooManyObjectsException e) {
				logger.info(String.format("[ErrorFindingDossier][%s]-[%s]", dossierCode, e.getMessage()));
			} catch (ActaException e) {
				logger.info(String.format("[ErrorFindingDossier][%s]-[%s]", dossierCode, e.getMessage()));
			}
		}
		if (dossierId == null) {
			throw new ObjectNotFoundException("NotFoundDossier[" + dossierCode + "]");
		}

		cache.getDossierMap().put(dossierCode, dossierId);

		return dossierId;
	}
	
	private ObjectIdType findDossier(final ObjectIdType repositoryId, PrincipalIdType principalId, String aooOriginale,
			final String codiceFascicolo,
			final String suffissoCodiceFascicolo,
			final String numeroSottoFascicolo,
			final String voceTitolarioFascicolo, String dbKeyTitolario,
			final Map<String, Object> paramMap) throws ObjectNotFoundException {
		// Search dossierId into cache
		ObjectIdType dossierId = null;

		Map<EProperty, Object> conditionMap = new HashMap<>();
		//conditionMap.put(EProperty.STATUS, 1);
		//conditionMap.put(EProperty.DESCRIPTION, voceTitolarioFascicolo);

		String voceTitolarioId = null;
		String dbKeyVoceTitolario = null;
		try {
			logger.info("Cerco la voce del titolario  " + voceTitolarioFascicolo);
			//voceTitolarioId = findObject(repositoryId, principalId, EnumObjectType.VOCE_PROPERTIES_TYPE,
			//		EnumPropertyFilter.NONE, EnumQueryOperator.EQUALS, conditionMap, null, objectServiceClient,
			//		paramMap, EProperty.OBJECT_ID);
			
			if(aooOriginale!=null && !("".equalsIgnoreCase(aooOriginale))){
				final List<AuthorityProperty> authorityProperties = authorityPropertiesEJB.loadList(AuthorityProperty.class);
				principalId = findPrincipalOrig(repositoryId, aooOriginale, authorityProperties, paramMap);
			}
			
			VoceTitolarioBean voceTitolarioBean = getVoceTitolario(repositoryId, principalId, null, voceTitolarioFascicolo, dbKeyTitolario, paramMap);
			if( voceTitolarioBean!=null ){
				voceTitolarioId = voceTitolarioBean.getIdVoce();
				dbKeyVoceTitolario = voceTitolarioBean.getDbKeyVoce();
			}
		} catch (Exception e) {
			logger.info(String.format("[ErrorFindingDossier][%s]-[%s]", codiceFascicolo, e.getMessage()));
		}
		
		if (voceTitolarioId != null) {
			logger.info(String.format("Found-VoceTitolario [%s][%s]", voceTitolarioFascicolo, voceTitolarioId));
			
			ObjectIdType voceTitolario = new ObjectIdType();
			voceTitolario.setValue(voceTitolarioId);
			
			for (final EDossierType dossierType : EDossierType.values()) {
				try {
					conditionMap = new HashMap<>();
					conditionMap.put(EProperty.CODE, codiceFascicolo);
					conditionMap.put(EProperty.SUFFISSO_CODE, suffissoCodiceFascicolo);
					
					logger.debug("cerco " + dossierType.getCode().value());
					final String dossierContainerId = findObjectFascicolo(repositoryId, principalId, 
							dossierType.getCode().value(),//"AggregazionePropertiesType", 
							EnumPropertyFilter.LIST, EnumQueryOperator.EQUALS, conditionMap, voceTitolario, objectServiceClient,
							paramMap, EProperty.OBJECT_ID, dbKeyVoceTitolario);
					
					dossierId = new ObjectIdType();
					dossierId.setValue(dossierContainerId);
					break;
				
				} catch (ObjectNotFoundException | TooManyObjectsException | ActaException e ) {
					logger.info(String.format("[ErrorFindingDossier][%s]-[%s]", codiceFascicolo, e.getMessage()));
				}
			}
			
			if(dossierId!=null && dossierId.getValue()!=null && 
					numeroSottoFascicolo!=null && !numeroSottoFascicolo.equalsIgnoreCase("")){
				try {
					conditionMap = new HashMap<>();
					conditionMap.put(EProperty.CODE, numeroSottoFascicolo);
					final String sottoFascicoloId = findObjectFascicolo(repositoryId, principalId, 
							EnumObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE.value(),
							EnumPropertyFilter.LIST, EnumQueryOperator.EQUALS, conditionMap, dossierId, objectServiceClient,
							paramMap, EProperty.OBJECT_ID, dbKeyVoceTitolario);
					dossierId = new ObjectIdType();
					dossierId.setValue(sottoFascicoloId);
				} catch (ObjectNotFoundException | TooManyObjectsException | ActaException e ) {
					logger.info(String.format("[ErrorFindingSottofascicolo][%s]-[%s]", numeroSottoFascicolo, e.getMessage()));
				}
			}
		}
		if (dossierId == null) {
			throw new ObjectNotFoundException("NotFoundDossier[" + codiceFascicolo + "]");
		}

		
		return dossierId;
	}
	
	private VoceTitolarioBean getVoceTitolario(ObjectIdType repoId, PrincipalIdType principalId, String parentNodeId, 
			String codiceVoceTitolario, String dbKeyTitolario, final Map<String, Object> paramMap) throws Exception{
		
		StringTokenizer codiciVoci = new StringTokenizer(codiceVoceTitolario, ".");
		
		VoceTitolarioBean voceTitolario = null;
		if( codiciVoci!=null){
			String dbKeyPadre = null;
			while (codiciVoci.hasMoreElements()) {
				String codiceVoce = (String) codiciVoci.nextElement();
				logger.debug("codice voce titolario " + codiceVoce);
				voceTitolario = getVoceTitolario(repoId, principalId, parentNodeId, codiceVoce, dbKeyPadre, dbKeyTitolario, paramMap);
				if( voceTitolario!=null ){
					dbKeyPadre = voceTitolario.getDbKeyVoce();
				}
			}
		}
		
		return voceTitolario;
	}
	
	private VoceTitolarioBean getVoceTitolario(ObjectIdType repoId, PrincipalIdType principalId, String parentNodeId, 
			String codiceVoce, String dbKeyPadre, String dbKeyTitolario, final Map<String, Object> paramMap) throws Exception{
		VoceTitolarioBean voceTitolario = null;
		try {
			
			final QueryableObjectType queryableObject = new QueryableObjectType();
			logger.debug("Cerco l'oggetto " + EnumObjectType.VOCE_PROPERTIES_TYPE.value());
			queryableObject.setObject(EnumObjectType.VOCE_PROPERTIES_TYPE.value());

			final PropertyFilterType propertyFilter = new PropertyFilterType();
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

			NavigationConditionInfoType navigationConditionInfo = null;
			if (parentNodeId != null) {
				navigationConditionInfo = new NavigationConditionInfoType();
				ObjectIdType parentNode = new ObjectIdType();
				parentNode.setValue(parentNodeId);
				navigationConditionInfo.setParentNodeId(parentNode);
			}

			final List<QueryConditionType> queryConditions = new ArrayList<QueryConditionType>();
			if (codiceVoce != null) {
				QueryConditionType queryCondition = new QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("codice");
				logger.debug("Aggiungo la condizione codice=" + codiceVoce);
				queryCondition.setValue(codiceVoce);
				queryConditions.add(queryCondition);
			}
			if (dbKeyPadre != null) {
				QueryConditionType queryCondition = new QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("dbKeyPadre");
				logger.debug("Aggiungo la condizione dbKeyPadre=" + dbKeyPadre);
				queryCondition.setValue(dbKeyPadre);
				queryConditions.add(queryCondition);
			} else {
				QueryConditionType queryCondition = new QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("dbKeyPadre");
				logger.debug("Aggiungo la condizione dbKeyPadre=" + dbKeyPadre);
				queryCondition.setValue("");
				//queryConditions.add(queryCondition);
			}
			if (dbKeyTitolario != null) {
				QueryConditionType queryCondition = new QueryConditionType();
				queryCondition.setOperator(EnumQueryOperator.EQUALS);
				queryCondition.setPropertyName("dbKeyTitolario");
				logger.debug("Aggiungo la condizione dbKeyTitolario=" + dbKeyTitolario);
				queryCondition.setValue(dbKeyTitolario);
				queryConditions.add(queryCondition);
			}
			

			PagingResponseType pagingResponse = objectServiceClient.query(repoId, principalId, queryableObject, propertyFilter,
					queryConditions, navigationConditionInfo, null, null, paramMap);
			if (pagingResponse != null) {
				List<ObjectResponseType> objectsResponse = pagingResponse.getObjects();
				logger.debug("Numero risultati: " + objectsResponse.size());
				// logger.debug("\n");
				
				boolean voceRadiceTrovata = false;
				for (ObjectResponseType objectResponse : objectsResponse) {
					if( !voceRadiceTrovata ){
						// logger.debug("ObjectId: " + objectResponse.getObjectId().getValue());
						String idVoceTitolario = objectResponse.getObjectId().getValue();
						List<PropertyType> properties = objectResponse.getProperties();
						voceTitolario = new VoceTitolarioBean();
						voceTitolario.setCodiceVoce(codiceVoce);
						voceTitolario.setIdVoce(idVoceTitolario);
						for (PropertyType p : properties) {
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
	            		
						}
						
						if( dbKeyPadre==null && voceTitolario.getDbKeyPadreVoce()==null 
								//&& voceTitolario.getDescrizioneVoce()!=null && !voceTitolario.getDescrizioneVoce().equalsIgnoreCase("")
								)
							voceRadiceTrovata = true;
					}
				}
				logger.debug("Voce titolario trovata: " + voceTitolario );
			}
		} catch (Exception e) {
			throw e;
		}
		return voceTitolario;
	}
	
	private ObjectIdType findAggregatoIndiceClassificazioneEsteso(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final String indiceClassificazioneEsteso, final Map<String, Object> paramMap) throws ObjectNotFoundException {
		// Search dossierId into cache
		/*final PrincipalCache cache = getFromCache(principalId);
		ObjectIdType dossierId = cache.getDossierMap().get(dossierCode);
		if (dossierId != null) {
			logger.debug("Resolved-[" + ActaCache.NAME + "][dossierId]");
			return dossierId;
		}*/

		ObjectIdType aggregatoObj = null;
				
		Map<EProperty, Object> conditionMap = new HashMap<>();
		conditionMap.put(EProperty.INDICE_CLASSIFICAZIONE_ESTESO, indiceClassificazioneEsteso);
		
		String ecmUuidNodo = null;
		String idTipoAggregazione = null;
		String idObjAggregato = null;
		try {
			logger.info("Cerco l'indice di classificazione esteso " + indiceClassificazioneEsteso);
			List<PropertyType> properties = null;
			try {
				properties = findEcmUuidNodo(repositoryId, principalId, "AggregazioneICEView", EnumPropertyFilter.NONE, 
						EnumQueryOperator.EQUALS, conditionMap, null, 
						objectServiceClient, paramMap, EProperty.ECMUUIDNODO);
				logger.info("properties " + properties);
			} catch (ActaException e) {
				logger.error("Errore: " + e.getMessage());
			}
			if( properties!=null ){
				for(PropertyType p : properties){
        			if( p.getQueryName()!=null && p.getQueryName().getPropertyName()!=null && 
        					p.getQueryName().getPropertyName().equalsIgnoreCase("ecmUuidNodo")){
        				ecmUuidNodo = p.getValue().getContent().get(0);
        			}
        			if( p.getQueryName()!=null && p.getQueryName().getPropertyName()!=null && 
        					p.getQueryName().getPropertyName().equalsIgnoreCase("idTipoAggregazione")){
        				idTipoAggregazione = p.getValue().getContent().get(0);
        			}
        			
        		}
			}
				
			if(ecmUuidNodo!=null && idTipoAggregazione!=null){
				Map<String,EnumObjectType> tipoAggregatoMap = new HashMap<String, EnumObjectType>();
				tipoAggregatoMap.put("1", EnumObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE);
				tipoAggregatoMap.put("2", EnumObjectType.SERIE_FASCICOLI_PROPERTIES_TYPE);
				tipoAggregatoMap.put("3", EnumObjectType.SERIE_DOSSIER_PROPERTIES_TYPE);
				tipoAggregatoMap.put("4", EnumObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE);
				tipoAggregatoMap.put("5", EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE);
				tipoAggregatoMap.put("6", EnumObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE);
				tipoAggregatoMap.put("7", EnumObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE);
				tipoAggregatoMap.put("8", EnumObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE);
				tipoAggregatoMap.put("9", EnumObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE);
				tipoAggregatoMap.put("10", EnumObjectType.DOSSIER_PROPERTIES_TYPE);
				tipoAggregatoMap.put("11", EnumObjectType.VOLUME_SOTTOFASCICOLI_PROPERTIES_TYPE);
				tipoAggregatoMap.put("12", EnumObjectType.VOLUME_FASCICOLI_PROPERTIES_TYPE);
				tipoAggregatoMap.put("13", EnumObjectType.VOLUME_SERIE_FASCICOLI_PROPERTIES_TYPE);
				tipoAggregatoMap.put("14", EnumObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE);
				
				conditionMap = new HashMap<>();
				logger.info("Cerco l'uuidNodo " + ecmUuidNodo );
				conditionMap.put(EProperty.ECMUUIDNODO, ecmUuidNodo);
				
				logger.debug("Cerco l'oggetto " + tipoAggregatoMap.get(idTipoAggregazione).value() );
				try {
					idObjAggregato = findObject(repositoryId, principalId, tipoAggregatoMap.get(idTipoAggregazione), EnumPropertyFilter.NONE, 
							EnumQueryOperator.EQUALS, conditionMap, null, objectServiceClient, paramMap, EProperty.OBJECT_ID);
				} catch (ActaException e) {
					logger.error("Errore: " + e.getMessage());
				}
			}
				
			if (idObjAggregato != null) {
				logger.info(String.format("Found-Aggregato[%s]", idTipoAggregazione));
				aggregatoObj = new ObjectIdType();
				aggregatoObj.setValue(idObjAggregato);
			}
		} catch (ObjectNotFoundException | TooManyObjectsException e) {
			logger.info(String.format("[ErrorFindingAggregato][%s]-[%s]", indiceClassificazioneEsteso, e.getMessage()));
		}
		if (aggregatoObj == null) {
			throw new ObjectNotFoundException("NotFoundAggregato[" + indiceClassificazioneEsteso + "]");
		}

		//cache.getDossierMap().put(dossierCode, dossierId);

		return aggregatoObj;
	}

	private ObjectIdType findRepository(final List<AuthorityProperty> authorityProperties,
			final Map<String, Object> paramMap) throws ObjectNotFoundException, ActaException {
		// Search repositoryId into cache
		final ActaCache cache = cacheEJB.get(ActaCache.NAME);
		ObjectIdType repositoryId = cache.getRepository();
		if (repositoryId != null) {
			logger.debug("Ho recuperato il repository id dalla cache-[" + ActaCache.NAME + "][repositoryId]:" + repositoryId.getValue() );
			return repositoryId;
		}

		// Repository name
		final String repoName = authorityProperties.stream().filter(p -> p.getId().equals(EActaProperty.REPO_NAME
				.getCode())).findFirst().get().getValue();
		logger.debug("RepoName: " + repoName );
		
		List<AcarisRepositoryEntryType> repositories;
		try {
			repositories = repositoryServiceClient.getRepositories(paramMap);
			repositories.forEach(repo -> logger.debug("Individuato-Repo[" + repo.getRepositoryName() + "]"));
			final Optional<AcarisRepositoryEntryType> repository = repositories.stream().filter(repo -> repo
					.getRepositoryName().equals(repoName)).findAny();
			logger.debug("Filtro la response della lista repository per repoName: " + repoName);
			repositoryId = repository.isPresent() ? repository.get().getRepositoryId() : null;
			logger.debug("repositoryId: " + repositoryId);
			
			if (repositoryId == null) {
				throw new ObjectNotFoundException(AcarisRepositoryEntryType.class.getSimpleName() + "-NotFound");
			}
			cache.setRepository(repositoryId);
		} catch (Exception e) {
			throw new ActaException("Errore nel recupero del repository");
		}
		
		return repositoryId;
	}

	private PrincipalIdType findPrincipal(final ObjectIdType repositoryId, final AdminOrganization organization,
			final List<AuthorityProperty> authorityProperties, final Map<String, Object> paramMap)
			throws ObjectNotFoundException, ActaException {
		// Principal metadata
		final ActaCache.PrincipalKey principalKey = new ActaCache.PrincipalKey(organization.getId().getDocumentTypeId(),
						organization.getId().getAooCode());

		// Search principalId into cache
		final ActaCache cache = cacheEJB.get(ActaCache.NAME);
		PrincipalIdType principalId = cache.getPrincipalIdTypeMap().get(principalKey);
		if (principalId != null) {
			logger.debug("Ho recuperato il principal dalla cache-[" + ActaCache.NAME + "][principalId]: " + principalId.getValue());
			return principalId;
		}

		// Application key
		final String appKey = authorityProperties.stream().filter(p -> p.getId().equals(EActaProperty.APPLICATION_KEY
				.getCode())).findFirst().get().getValue();
		logger.debug( EActaProperty.APPLICATION_KEY.getCode() + ": " + appKey);
		// Fiscal code
		final String fiscalCode = authorityProperties.stream().filter(p -> p.getId().equals(EActaProperty.FISCAL_CODE
				.getCode())).findFirst().get().getValue();
		logger.debug(EActaProperty.FISCAL_CODE.getCode() + ": " + fiscalCode);
		
		List<PrincipalExtResponseType> principals;
		try {
			principals = backofficeServiceClient.getPrincipalsExt(repositoryId, fiscalCode, appKey, paramMap);
			
			principals.forEach(prin -> logger.debug("Individuato-PrincipalExt[" + prin.toString() + "]"));

			if (!principals.isEmpty()) {
				Optional<PrincipalExtResponseType> optPrincipal = principals.stream().
						filter(p -> p.getUtente().getAoo().getCodice().equals(organization.getId().getAooCode()))
						//.filter(p -> p.getUtente().getStruttura().getCodice().equals(organization.getStructureCode()))
						//.filter(p -> p.getUtente().getNodo().getCodice().equals(organization.getNodeCode()))
						.findFirst();
				
				principalId = optPrincipal.isPresent() ? optPrincipal.get().getPrincipalId() : null;
				logger.debug("principalId: " + principalId);
			}

			if (principalId == null) {
				throw new ObjectNotFoundException(PrincipalExtResponseType.class.getSimpleName() + "-NotFound");
			}
			cache.getPrincipalIdTypeMap().put(principalKey, principalId);
		} catch (Exception e) {
			throw new ActaException("Errore nel recupero del principal");
		}
		
		return principalId;
	}
	
	private PrincipalIdType findPrincipalOrig(final ObjectIdType repositoryId, String aooOriginale,
			final List<AuthorityProperty> authorityProperties, final Map<String, Object> paramMap)
			throws ObjectNotFoundException, ActaException {
		
		PrincipalIdType principalId = null;

		// Application key
		final String appKey = authorityProperties.stream().filter(p -> p.getId().equals(EActaProperty.APPLICATION_KEY
				.getCode())).findFirst().get().getValue();
		logger.debug( EActaProperty.APPLICATION_KEY.getCode() + ": " + appKey);
		// Fiscal code
		final String fiscalCode = authorityProperties.stream().filter(p -> p.getId().equals(EActaProperty.FISCAL_CODE
				.getCode())).findFirst().get().getValue();
		logger.debug(EActaProperty.FISCAL_CODE.getCode() + ": " + fiscalCode);
		
		List<PrincipalExtResponseType> principals;
		try {
			principals = backofficeServiceClient.getPrincipalsExt(repositoryId, fiscalCode, appKey, paramMap);
			
			principals.forEach(prin -> logger.debug("Individuato-PrincipalExt[" + prin.toString() + "]"));

			if (!principals.isEmpty()) {
				Optional<PrincipalExtResponseType> optPrincipal = principals.stream().
						filter(p -> p.getUtente().getAoo().getCodice().equals(aooOriginale))
						//.filter(p -> p.getUtente().getStruttura().getCodice().equals(organization.getStructureCode()))
						//.filter(p -> p.getUtente().getNodo().getCodice().equals(organization.getNodeCode()))
						.findFirst();
				
				principalId = optPrincipal.isPresent() ? optPrincipal.get().getPrincipalId() : null;
				logger.debug("principalId: " + principalId);
			}

			if (principalId == null) {
				throw new ObjectNotFoundException(PrincipalExtResponseType.class.getSimpleName() + "-NotFound");
			}
			
		} catch (Exception e) {
			throw new ActaException("Errore nel recupero del principal");
		}
		
		return principalId;
	}

	private PrincipalCache getFromCache(final PrincipalIdType principalId) {
		final Map<String, PrincipalCache> cacheMap = cacheEJB.get(ActaCache.NAME).getPrincipalCacheMap();
		if (!cacheMap.containsKey(principalId.getValue())) {
			cacheMap.put(principalId.getValue(), new PrincipalCache());
		}
		return cacheMap.get(principalId.getValue());
	}

	private Map<String, IdVitalRecordCodeType> findVitalRecordCodes(final ObjectIdType repositoryId,
			final Map<String, Object> paramMap) throws ActaException {
		// Search vitalRecords into cache
		logger.debug("Cerco i VitalRecordCodes ");
		final ActaCache cache = cacheEJB.get(ActaCache.NAME);
		final Map<String, IdVitalRecordCodeType> vitalRecordsMap = cache.getVitalRecordsMap();
		if (vitalRecordsMap.isEmpty()) {
			// Get remote vital record registry
			List<VitalRecordCodeType> vitalRecordCodes = new ArrayList<VitalRecordCodeType>();
			try {
				vitalRecordCodes = managementServiceClient.getVitalRecordCode(repositoryId,
						paramMap);
			} catch (Exception e) {
				throw new ActaException("Errore nel recupero dei vital record code");
			}
			if( vitalRecordCodes!=null ){
				for(VitalRecordCodeType vitalRecordCode : vitalRecordCodes){
					logger.debug("" + vitalRecordCode.getDescrizione()  + " " + vitalRecordCode.getTempoDiVitalita() + " " + vitalRecordCode.getIdVitalRecordCode().getValue());
				}
			}

			// Transform vital record registry into map
			vitalRecordsMap.putAll(vitalRecordCodes.stream().collect(Collectors.toMap(vr -> vr.getDescrizione(),
					vr -> vr.getIdVitalRecordCode())));
		} else {
			logger.debug("Individuati i vital record code nella cache-[" + ActaCache.NAME + "][vitalRecordCodes]");
		}

		return vitalRecordsMap;
	}

	private IdStatoDiEfficaciaType findEfficacy(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final DocumentType documentType, final Map<String, Object> paramMap, Map<String, String> dynamicAttributesMap) throws ObjectNotFoundException,
			TooManyObjectsException, ActaException {
		// Search efficacyId into cache
		logger.debug("Cerco il valore di efficacia per la tipologia di documento ");
		IntegrationResource intRes = new IntegrationResource();
		EIntegrationResource eIntRes = intRes.resolve( documentType.getId(), dynamicAttributesMap );
		
		final EEfficacy efficacy = eIntRes.getEfficacy(documentType.getId());
				//EIntegrationResource.resolve(documentType.getId()).getEfficacy();
		logger.debug("Codice efficacia  " + efficacy.getCode());
		final PrincipalCache cache = getFromCache(principalId);
		IdStatoDiEfficaciaType efficacyId = cache.getEfficacyMap().get(efficacy.getCode());
		if (efficacyId != null) {
			logger.debug("Individuato il valore dell'efficacia nella cache-[" + ActaCache.NAME + "][efficacyId]");
			return efficacyId;
		}

		final Map<EProperty, Object> conditionMap = new HashMap<>();
		conditionMap.put(EProperty.DESCRIPTION, efficacy.getCode());
		String resolvedEfficacy;
		try {
			resolvedEfficacy = findObject(repositoryId, principalId, EObjectType.EFFICACY_STATUS_NAME.code,
					EnumPropertyFilter.NONE, EnumQueryOperator.EQUALS, conditionMap, null, objectServiceClient, paramMap,
					EProperty.DB_KEY);
			
			efficacyId = new IdStatoDiEfficaciaType();
			logger.debug("Individuato il valore per l'efficacia: " + resolvedEfficacy);
			efficacyId.setValue(Long.valueOf(resolvedEfficacy));
			cache.getEfficacyMap().put(efficacy.getCode(), efficacyId);
		} catch (ActaException e) {
			throw e;
		}
		

		return efficacyId;
	}
	
	private IdStatoDiEfficaciaType findEfficacyNonFirmato(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final Map<String, Object> paramMap, Map<String, String> dynamicAttributesMap) throws ObjectNotFoundException,
			TooManyObjectsException, ActaException {
		// Search efficacyId into cache
		logger.debug("Cerco il valore di efficacia per la tipologia di documento non firmata ");
		
		final Map<EProperty, Object> conditionMap = new HashMap<>();
		conditionMap.put(EProperty.DESCRIPTION,  EEfficacy.PERFECT_EFFICACY_UNSIGNED.getCode());
		logger.debug("Codice efficacia  " + EEfficacy.PERFECT_EFFICACY_UNSIGNED.getCode());
		String resolvedEfficacy;
		IdStatoDiEfficaciaType efficacyId = null;
		try {
			resolvedEfficacy = findObject(repositoryId, principalId, EObjectType.EFFICACY_STATUS_NAME.code,
					EnumPropertyFilter.NONE, EnumQueryOperator.EQUALS, conditionMap, null, objectServiceClient, paramMap,
					EProperty.DB_KEY);
			
			efficacyId = new IdStatoDiEfficaciaType();
			logger.debug("Individuato il valore per l'efficacia non firmata: " + resolvedEfficacy);
			efficacyId.setValue(Long.valueOf(resolvedEfficacy));
		} catch (ActaException e) {
			throw e;
		}
		
		return efficacyId;
	}

	private IdFormaDocumentariaType findDocumentFormat(final ObjectIdType repositoryId,
			final PrincipalIdType principalId, final DocumentType documentType,
			final List<AuthorityProperty> authorityProperties, final Map<String, Object> paramMap, Map<String, String> dynamicAttributesMap)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		// Search formatId into cache
		logger.debug("Cerco il valore di DocumentFormat per la tipologia di documento ");
		
		IntegrationResource intRes = new IntegrationResource();
		EIntegrationResource eIntRes = intRes.resolve( documentType.getId(), dynamicAttributesMap );
		final EDocumentFormat format = eIntRes.getDocumentFormat( documentType.getId() );//EIntegrationResource.resolve(documentType.getId()).getDocumentFormat();
		logger.debug("Formato da cercare:  " + format.getCode());
		final PrincipalCache cache = getFromCache(principalId);
		IdFormaDocumentariaType formatId = cache.getFormatMap().get(format.getCode());
		if (formatId != null) {
			logger.debug("Individuato il formato nella cache-[" + ActaCache.NAME + "][formatId]");
			return formatId;
		}

		final Map<EProperty, Object> conditionMap = new HashMap<>();
		conditionMap.put(EProperty.DESCRIPTION, format.getCode());
		conditionMap.put(EProperty.DESCRIPTION_AUTHORITY, authorityProperties.stream().filter(p -> p.getId().equals(
				EActaProperty.REPO_CODE.getCode())).findFirst().get().getValue());

		String resolvedFormat;
		try {
			resolvedFormat = findObject(repositoryId, principalId, EObjectType.DOCUMENT_FORMAT_NAME.code,
					EnumPropertyFilter.NONE, EnumQueryOperator.EQUALS, conditionMap, null, objectServiceClient, paramMap,
					EProperty.DB_KEY);
			formatId = new IdFormaDocumentariaType();
			logger.debug("Individuato il formato: " + resolvedFormat);
			formatId.setValue(Long.valueOf(resolvedFormat));
			cache.getFormatMap().put(format.getCode(), formatId);
		} catch (ActaException e) {
			throw e;
		}
		

		return formatId;
	}

	private PagingResponseType find(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final String enumObject, final EnumPropertyFilter enumProperty, final EnumQueryOperator enumQuery,
			final Map<EProperty, Object> queryConditionMap, final ObjectIdType parentNodeId,
			final QueryableServiceClient client, final Map<String, Object> paramMap, boolean multipleResults)
					throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		final QueryableObjectType queryableObject = new QueryableObjectType();
		logger.debug("Setto queryableObject a " + enumObject );
		queryableObject.setObject(enumObject);
		final PropertyFilterType propertyFilter = new PropertyFilterType();
		logger.debug("Setto propertyFilter a " + enumProperty );
		propertyFilter.setFilterType(enumProperty);

		final List<QueryConditionType> queryConditions = new ArrayList<>();
		if (queryConditionMap != null) {
			queryConditionMap.forEach((k, v) -> {
				logger.info(String.format("QueryCondition-[%s][%s][%s]", enumQuery.value(), k.code, v.toString()));
				final QueryConditionType queryCondition = new QueryConditionType();
				queryCondition.setOperator(enumQuery);
				queryCondition.setPropertyName(k.code);
				queryCondition.setValue(v.toString());
				queryConditions.add(queryCondition);
			});
		}

		final NavigationConditionInfoType navigationConditionInfo = new NavigationConditionInfoType();
		if(parentNodeId!=null)
			logger.debug("Setto navigationConditionInfo ParentNodeId a " + parentNodeId.getValue() );
		else 
			logger.debug("Setto navigationConditionInfo ParentNodeId a null ");
		navigationConditionInfo.setParentNodeId(parentNodeId);
		
		PagingResponseType pagingResponse = null;
		try {
			pagingResponse = client.query(repositoryId, principalId, queryableObject,
					propertyFilter, queryConditions, parentNodeId != null ? navigationConditionInfo : null, paramMap);
		} catch (Exception e) {
			throw new ActaException("Errore chiamata Acta");
		}
		
		if( pagingResponse!=null && pagingResponse.getObjects()!=null ){
			logger.info("Numero risultati " + pagingResponse.getObjects().size());
		}
		
		if( pagingResponse!=null && pagingResponse.getObjects()!=null && pagingResponse.getObjects().size()>0){
			for(ObjectResponseType obj : pagingResponse.getObjects()){
				logger.debug("Obj value: "  + obj.getObjectId().getValue());
				List<PropertyType> properties = obj.getProperties();
				for(PropertyType property : properties ){
					logger.debug("property "  + property.getQueryName().getPropertyName() + " " + property.getValue().getContent() );

				}
				
			}
		}

		logger.warn("Response.size[" + (pagingResponse != null ? pagingResponse.getObjects().size() : NOT_AVAILABLE)
				+ "]");

		if (pagingResponse == null || pagingResponse.getObjects().isEmpty()) {
			throw new ObjectNotFoundException("NotFound[" + enumObject + "]");
		}

		if (!multipleResults && pagingResponse.getObjects().size() > 1) {
			throw new TooManyObjectsException("TooManyFound[" + enumObject + "][#" + pagingResponse.getObjects().size()
					+ "]");
		}

		return pagingResponse;
	}

	private List<String> filterObjects(final PagingResponseType pagingResponse, final EProperty eProperty) {
		final List<String> objectValues = new ArrayList<>();
		for (final ObjectResponseType objectResponse : pagingResponse.getObjects()) {
			final Optional<String> propertyValue = objectResponse.getProperties().stream().filter(p -> p.getQueryName()
					.getPropertyName().equals(eProperty.code)).map(p -> p.getValue().getContent().get(0)).findFirst();
			if (propertyValue.isPresent()) {
				objectValues.add(propertyValue.get());
			}
		}
		return objectValues;
	}
	
	private String findObjectFascicolo(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final String enumObject, final EnumPropertyFilter enumProperty, final EnumQueryOperator enumQuery,
			final Map<EProperty, Object> queryConditionMap, final ObjectIdType parentNodeId,
			final QueryableServiceClient client, final Map<String, Object> paramMap, final EProperty eProperty, String idVoce)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		final QueryableObjectType queryableObject = new QueryableObjectType();
		logger.debug("Setto queryableObject a " + enumObject );
		queryableObject.setObject(enumObject);
		final PropertyFilterType propertyFilter = new PropertyFilterType();
		logger.debug("Setto propertyFilter a " + enumProperty );
		QueryNameType propertyName = new QueryNameType();
		propertyName.setPropertyName("indiceClassificazioneEstesa");
		propertyName.setClassName("indiceClassificazioneEstesa");
		propertyFilter.getPropertyList().add(propertyName);
		QueryNameType propertyName2 = new QueryNameType();
		propertyName2.setPropertyName("idVoce");
		propertyName2.setClassName("idVoce");
		propertyFilter.getPropertyList().add(propertyName2);
		propertyFilter.setFilterType(enumProperty);

		final List<QueryConditionType> queryConditions = new ArrayList<>();
		if (queryConditionMap != null) {
			queryConditionMap.forEach((k, v) -> {
				logger.info(String.format("QueryCondition-[%s][%s][%s]", enumQuery.value(), k, v));
				final QueryConditionType queryCondition = new QueryConditionType();
				queryCondition.setOperator(enumQuery);
				queryCondition.setPropertyName(k.code);
				queryCondition.setValue(v.toString());
				queryConditions.add(queryCondition);
			});
		}

		final NavigationConditionInfoType navigationConditionInfo = new NavigationConditionInfoType();
		if(parentNodeId!=null)
			logger.debug("Setto navigationConditionInfo ParentNodeId a " + parentNodeId.getValue() );
		else 
			logger.debug("Setto navigationConditionInfo ParentNodeId a null ");
		navigationConditionInfo.setParentNodeId(parentNodeId);
		navigationConditionInfo.setLimitToChildren(false);

		PagingResponseType pagingResponse = null;
		try {
			pagingResponse = client.query(repositoryId, principalId, queryableObject,
					propertyFilter, queryConditions, parentNodeId != null ? navigationConditionInfo : null, paramMap);
		} catch (Exception e) {
			throw new ActaException("Errore nella ricerca della voce");
		}
		
		List<String> objectValues = new ArrayList<String>();
		if( pagingResponse!=null && pagingResponse.getObjects()!=null && pagingResponse.getObjects().size()>0){
			for(ObjectResponseType obj : pagingResponse.getObjects()){
				logger.debug("Obj value: "  + obj.getObjectId().getValue());
				List<PropertyType> properties = obj.getProperties();
				for(PropertyType property : properties ){
					logger.debug("property "  + property.getQueryName().getPropertyName() + " " + property.getValue().getContent() );
					if ("idVoce".equals(property.getQueryName().getPropertyName())) {
                        String valore = property.getValue().getContent().get(0);
                        if (idVoce.equals(valore)) {
                        	logger.debug("Trovato match in voce " + idVoce);
                            objectValues.add( obj.getObjectId().getValue());
                        }
                    }
				}
				
			}
		}

		logger.warn("Response.size[" + (pagingResponse != null ? pagingResponse.getObjects().size() : NOT_AVAILABLE)
				+ "]");

		if (pagingResponse == null || pagingResponse.getObjects().isEmpty()) {
			throw new ObjectNotFoundException("NotFound[" + enumObject + "]");
		}

		if ( pagingResponse.getObjects().size() > 1) {
			//throw new TooManyObjectsException("TooManyFound[" + enumObject + "][#" + pagingResponse.getObjects().size()
			//		+ "]");
		}
		
		
		//final List<String> objectValues = filterObjects(pagingResponse, eProperty);
		if( objectValues!=null ){
			String objectValue = null;
			for( String s : objectValues){
				//String objectValue = !objectValues.isEmpty() ? objectValues.get(0) : null;
				objectValue = s;
				
				if (objectValue != null) {
					logger.info("Found-ObjectType[" + enumObject + "]-ObjectValue[" + objectValue + "]");
					return objectValue;
				}
			}
			
		}

		throw new ObjectNotFoundException("NotFound[" + enumObject + "]InvalidProperty[" + eProperty.code + "]");
	}

	private String findObject(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final String enumObject, final EnumPropertyFilter enumProperty, final EnumQueryOperator enumQuery,
			final Map<EProperty, Object> queryConditionMap, final ObjectIdType parentNodeId,
			final QueryableServiceClient client, final Map<String, Object> paramMap, final EProperty eProperty)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		final PagingResponseType pagingResponse = find(repositoryId, principalId, enumObject, enumProperty, enumQuery,
				queryConditionMap, parentNodeId, client, paramMap, false);
		
		
		final List<String> objectValues = filterObjects(pagingResponse, eProperty);
		final String objectValue = !objectValues.isEmpty() ? objectValues.get(0) : null;
		if (objectValue != null) {
			logger.info("Found-ObjectType[" + enumObject + "]-ObjectValue[" + objectValue + "]");
			return objectValue;
		}

		throw new ObjectNotFoundException("NotFound[" + enumObject + "]InvalidProperty[" + eProperty.code + "]");
	}
	
	private List<PropertyType> findEcmUuidNodo(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final String enumObject, final EnumPropertyFilter enumProperty, final EnumQueryOperator enumQuery,
			final Map<EProperty, Object> queryConditionMap, final ObjectIdType parentNodeId,
			final QueryableServiceClient client, final Map<String, Object> paramMap, final EProperty eProperty)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		final PagingResponseType pagingResponse = find(repositoryId, principalId, enumObject, enumProperty, enumQuery,
				queryConditionMap, parentNodeId, client, paramMap, false);
		
		List<PropertyType> propertyValue = null;
		for (final ObjectResponseType objectResponse : pagingResponse.getObjects()) {
			propertyValue = objectResponse.getProperties();
		}		
		
		return propertyValue;
	}

	private List<String> findObjects(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final String enumObject, final EnumPropertyFilter enumProperty, final EnumQueryOperator enumQuery,
			final Map<EProperty, Object> queryConditionMap, final ObjectIdType parentNodeId,
			final QueryableServiceClient client, final Map<String, Object> paramMap, final EProperty eProperty)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		final PagingResponseType pagingResponse = find(repositoryId, principalId, enumObject, enumProperty, enumQuery,
				queryConditionMap, parentNodeId, client, paramMap, true);

		final List<String> objectValues = filterObjects(pagingResponse, eProperty);
		if (!objectValues.isEmpty()) {
			logger.info("Found-ObjectType[" + enumObject + "]-ObjectValues#[" + objectValues.size() + "]");
			return objectValues;
		}

		throw new ObjectNotFoundException("NotFound[" + enumObject + "]InvalidProperty[" + eProperty.code + "]");
	}

	private String findObject(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final EnumObjectType enumObject, final EnumPropertyFilter enumProperty, final EnumQueryOperator enumQuery,
			final Map<EProperty, Object> queryConditionMap, final ObjectIdType parentNodeId,
			final QueryableServiceClient client, final Map<String, Object> paramMap, final EProperty eProperty)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		return findObject(repositoryId, principalId, enumObject.value(), enumProperty, enumQuery, queryConditionMap,
				parentNodeId, client, paramMap, eProperty);
	}

	private Map<EProperty, Object> findProperties(final ObjectIdType repositoryId, final PrincipalIdType principalId,
			final String enumObject, final EnumPropertyFilter enumProperty, final EnumQueryOperator enumQuery,
			final Map<EProperty, Object> queryConditionMap, final ObjectIdType parentNodeId,
			final QueryableServiceClient client, final Map<String, Object> paramMap, final List<EProperty> eProperties)
			throws ObjectNotFoundException, TooManyObjectsException, ActaException {
		final PagingResponseType pagingResponse = find(repositoryId, principalId, enumObject, enumProperty, enumQuery,
				queryConditionMap, parentNodeId, client, paramMap, false);

		final Map<EProperty, Object> propertyMap = new HashMap<>();
		final List<PropertyType> properties = pagingResponse.getObjects().get(0).getProperties();
		for (final PropertyType property : properties) {
			final Optional<EProperty> matchingProperty = eProperties.stream().filter(p -> p.code.equals(property
					.getQueryName().getPropertyName())).findAny();
			if (matchingProperty.isPresent() && property.getValue() != null && property.getValue().getContent()
					.size() == 1) {
				propertyMap.put(matchingProperty.get(), property.getValue().getContent().get(0));
			}
		}

		return propertyMap;
	}

	private AcarisContentStreamType createContentStream(final byte[] fileStream, final String fileName,
			final String extension, final EnumMimeTypeType mimeType) {
		final String streamName = fileName + "." + extension;
		final AcarisContentStreamType contentStream = new AcarisContentStreamType();
		contentStream.setFilename(streamName);
		contentStream.setMimeType(mimeType);

		final InputStream inputStream = new ByteArrayInputStream(fileStream);
		final OutputStream outputStream = new ByteArrayOutputStream(fileStream.length);

		javax.activation.DataSource dataSource = new javax.activation.DataSource() {
			public OutputStream getOutputStream() {
				return outputStream;
			}

			public String getName() {
				return streamName;
			}

			public InputStream getInputStream() {
				return inputStream;
			}

			public String getContentType() {
				return extension;
			}
		};
		contentStream.setStreamMTOM(new DataHandler(dataSource));

		return contentStream;
	}
	
	private AcarisContentStreamType createContentStream(InputStream fileStream, final String fileName,
			final String extension, final EnumMimeTypeType mimeType) {
		final String streamName = fileName + "." + extension;
		final AcarisContentStreamType contentStream = new AcarisContentStreamType();
		contentStream.setFilename(streamName);
		contentStream.setMimeType(mimeType);

		try {
			final OutputStream outputStream = new FileOutputStream(File.createTempFile("tmp","file"));
			
			javax.activation.DataSource dataSource = new javax.activation.DataSource() {
				public OutputStream getOutputStream() {
					return outputStream;
				}

				public String getName() {
					return streamName;
				}

				public InputStream getInputStream() {
					return fileStream;
				}

				public String getContentType() {
					return extension;
				}
			};
			contentStream.setStreamMTOM(new DataHandler(dataSource));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return contentStream;
	}

	private enum EProperty {
		CODE("codice"),
		SUFFISSO_CODE("suffissoCodice"),
		STATUS("stato"),
		DESCRIPTION("descrizione"),
		DESCRIPTION_AUTHORITY("descEnte"),
		OBJECT_ID("objectId"),
		DB_KEY("dbKey"),
		ADD_ATTACHMENT("addConAllegati"),
		OFFLINE_REQUEST("offlineAddRequest"),
		DOC_ATTACHMENT("docConAllegati"),
		NUM_ATTACHMENT("numeroAllegati"),
		FIS_CODE("codiceFiscale"),
		STRUCTURE_CODE("codiceStruttura"),
		AOO_CODE("codiceAoo"),
		NODE_ID("idNodo"),
		ECMUUIDNODO("ecmUuidNodo"),
		INDICE_CLASSIFICAZIONE_ESTESO("indiceClassificazioneEstesa");

		private String code;

		EProperty(final String eCode) {
			this.code = eCode;
		}
	}

	private enum EObjectType {
		EFFICACY_STATUS_NAME("StatoDiEfficaciaDecodifica"),
		DOCUMENT_FORMAT_NAME("FormaDocumentariaDecodifica"),
		SWITCHING_TYPE_NAME("TipoSmistamentoDecodifica"),
		AOO_STRUCTURE_NODE_VIEW("AooStrNodoView");

		private String code;

		EObjectType(final String eCode) {
			this.code = eCode;
		}
	}
	
}
