
package it.doqui.acta.acaris.sms;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.doqui.acta.acaris.sms package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AcarisFault_QNAME = new QName("common.acaris.acta.doqui.it", "acarisFault");
    private final static QName _TitolarioProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "titolarioProperties");
    private final static QName _ClassificazioneProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "classificazioneProperties");
    private final static QName _VoceProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "voceProperties");
    private final static QName _SottofascicoloProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "sottofascicoloProperties");
    private final static QName _DossierProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "dossierProperties");
    private final static QName _VolumeSerieFascicoliProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "volumeSerieFascicoliProperties");
    private final static QName _VolumeSerieTipologicaDocumentiProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "volumeSerieTipologicaDocumentiProperties");
    private final static QName _VolumeFascicoliProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "volumeFascicoliProperties");
    private final static QName _VolumeSottofascicoliProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "volumeSottofascicoliProperties");
    private final static QName _SerieFascicoliProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "serieFascicoliProperties");
    private final static QName _SerieTipologicaDocumentiProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "serieTipologicaDocumentiProperties");
    private final static QName _SerieDossierProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "serieDossierProperties");
    private final static QName _FascicoloRealeLiberoProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "fascicoloRealeLiberoProperties");
    private final static QName _FascicoloRealeAnnualeProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "fascicoloRealeAnnualeProperties");
    private final static QName _FascicoloRealeLegislaturaProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "fascicoloRealeLegislaturaProperties");
    private final static QName _FascicoloRealeEreditatoProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "fascicoloRealeEreditatoProperties");
    private final static QName _FascicoloRealeContinuoProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "fascicoloRealeContinuoProperties");
    private final static QName _FascicoloTemporaneoProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "fascicoloTemporaneoProperties");
    private final static QName _DocumentoFisicoProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "documentoFisicoProperties");
    private final static QName _GruppoAllegatiProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "gruppoAllegatiProperties");
    private final static QName _ClipsMetallicaProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "clipsMetallicaProperties");
    private final static QName _DocumentoSempliceProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "documentoSempliceProperties");
    private final static QName _DocumentoRegistroProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "documentoRegistroProperties");
    private final static QName _DocumentoDBProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "documentoDBProperties");
    private final static QName _ContenutoFisicoProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "contenutoFisicoProperties");
    private final static QName _DocumentAssociationProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "documentAssociationProperties");
    private final static QName _HistoryModificheTecnicheProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "historyModificheTecnicheProperties");
    private final static QName _DocumentCompositionProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "documentCompositionProperties");
    private final static QName _HistoryVecchieVersioniProperties_QNAME = new QName("archive.acaris.acta.doqui.it", "historyVecchieVersioniProperties");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.doqui.acta.acaris.sms
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AcarisFaultType }
     * 
     */
    public AcarisFaultType createAcarisFaultType() {
        return new AcarisFaultType();
    }

    /**
     * Create an instance of {@link Query }
     * 
     */
    public Query createQuery() {
        return new Query();
    }

    /**
     * Create an instance of {@link ObjectIdType }
     * 
     */
    public ObjectIdType createObjectIdType() {
        return new ObjectIdType();
    }

    /**
     * Create an instance of {@link PrincipalIdType }
     * 
     */
    public PrincipalIdType createPrincipalIdType() {
        return new PrincipalIdType();
    }

    /**
     * Create an instance of {@link QueryableObjectType }
     * 
     */
    public QueryableObjectType createQueryableObjectType() {
        return new QueryableObjectType();
    }

    /**
     * Create an instance of {@link PropertyFilterType }
     * 
     */
    public PropertyFilterType createPropertyFilterType() {
        return new PropertyFilterType();
    }

    /**
     * Create an instance of {@link QueryConditionType }
     * 
     */
    public QueryConditionType createQueryConditionType() {
        return new QueryConditionType();
    }

    /**
     * Create an instance of {@link NavigationConditionInfoType }
     * 
     */
    public NavigationConditionInfoType createNavigationConditionInfoType() {
        return new NavigationConditionInfoType();
    }

    /**
     * Create an instance of {@link QueryResponse }
     * 
     */
    public QueryResponse createQueryResponse() {
        return new QueryResponse();
    }

    /**
     * Create an instance of {@link PagingResponseType }
     * 
     */
    public PagingResponseType createPagingResponseType() {
        return new PagingResponseType();
    }

    /**
     * Create an instance of {@link GetPropertiesMassive }
     * 
     */
    public GetPropertiesMassive createGetPropertiesMassive() {
        return new GetPropertiesMassive();
    }

    /**
     * Create an instance of {@link GetPropertiesMassiveResponse }
     * 
     */
    public GetPropertiesMassiveResponse createGetPropertiesMassiveResponse() {
        return new GetPropertiesMassiveResponse();
    }

    /**
     * Create an instance of {@link ObjectResponseType }
     * 
     */
    public ObjectResponseType createObjectResponseType() {
        return new ObjectResponseType();
    }

    /**
     * Create an instance of {@link DecodificaType }
     * 
     */
    public DecodificaType createDecodificaType() {
        return new DecodificaType();
    }

    /**
     * Create an instance of {@link DecodificaExtType }
     * 
     */
    public DecodificaExtType createDecodificaExtType() {
        return new DecodificaExtType();
    }

    /**
     * Create an instance of {@link ChangeTokenType }
     * 
     */
    public ChangeTokenType createChangeTokenType() {
        return new ChangeTokenType();
    }

    /**
     * Create an instance of {@link IdAnnotazioniType }
     * 
     */
    public IdAnnotazioniType createIdAnnotazioniType() {
        return new IdAnnotazioniType();
    }

    /**
     * Create an instance of {@link IdAOOType }
     * 
     */
    public IdAOOType createIdAOOType() {
        return new IdAOOType();
    }

    /**
     * Create an instance of {@link IdStrutturaType }
     * 
     */
    public IdStrutturaType createIdStrutturaType() {
        return new IdStrutturaType();
    }

    /**
     * Create an instance of {@link IdNodoType }
     * 
     */
    public IdNodoType createIdNodoType() {
        return new IdNodoType();
    }

    /**
     * Create an instance of {@link IdVitalRecordCodeType }
     * 
     */
    public IdVitalRecordCodeType createIdVitalRecordCodeType() {
        return new IdVitalRecordCodeType();
    }

    /**
     * Create an instance of {@link IdMovimentazioneType }
     * 
     */
    public IdMovimentazioneType createIdMovimentazioneType() {
        return new IdMovimentazioneType();
    }

    /**
     * Create an instance of {@link IdProtocolloType }
     * 
     */
    public IdProtocolloType createIdProtocolloType() {
        return new IdProtocolloType();
    }

    /**
     * Create an instance of {@link IdProvvedimentoAutorizzatType }
     * 
     */
    public IdProvvedimentoAutorizzatType createIdProvvedimentoAutorizzatType() {
        return new IdProvvedimentoAutorizzatType();
    }

    /**
     * Create an instance of {@link IdProfiloType }
     * 
     */
    public IdProfiloType createIdProfiloType() {
        return new IdProfiloType();
    }

    /**
     * Create an instance of {@link CodiceFiscaleType }
     * 
     */
    public CodiceFiscaleType createCodiceFiscaleType() {
        return new CodiceFiscaleType();
    }

    /**
     * Create an instance of {@link IdUtenteType }
     * 
     */
    public IdUtenteType createIdUtenteType() {
        return new IdUtenteType();
    }

    /**
     * Create an instance of {@link IdRegistrazioneType }
     * 
     */
    public IdRegistrazioneType createIdRegistrazioneType() {
        return new IdRegistrazioneType();
    }

    /**
     * Create an instance of {@link IdSmistamentoType }
     * 
     */
    public IdSmistamentoType createIdSmistamentoType() {
        return new IdSmistamentoType();
    }

    /**
     * Create an instance of {@link ValueType }
     * 
     */
    public ValueType createValueType() {
        return new ValueType();
    }

    /**
     * Create an instance of {@link SimpleResponseType }
     * 
     */
    public SimpleResponseType createSimpleResponseType() {
        return new SimpleResponseType();
    }

    /**
     * Create an instance of {@link QueryNameType }
     * 
     */
    public QueryNameType createQueryNameType() {
        return new QueryNameType();
    }

    /**
     * Create an instance of {@link PropertyType }
     * 
     */
    public PropertyType createPropertyType() {
        return new PropertyType();
    }

    /**
     * Create an instance of {@link ComplexPropertyType }
     * 
     */
    public ComplexPropertyType createComplexPropertyType() {
        return new ComplexPropertyType();
    }

    /**
     * Create an instance of {@link AcarisContentStreamType }
     * 
     */
    public AcarisContentStreamType createAcarisContentStreamType() {
        return new AcarisContentStreamType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link VarargsType }
     * 
     */
    public VarargsType createVarargsType() {
        return new VarargsType();
    }

    /**
     * Create an instance of {@link AllowedChildObjectTypeIdsType }
     * 
     */
    public AllowedChildObjectTypeIdsType createAllowedChildObjectTypeIdsType() {
        return new AllowedChildObjectTypeIdsType();
    }

    /**
     * Create an instance of {@link ObjectMetadataType }
     * 
     */
    public ObjectMetadataType createObjectMetadataType() {
        return new ObjectMetadataType();
    }

    /**
     * Create an instance of {@link PropertyFilterConfigurationInfoType }
     * 
     */
    public PropertyFilterConfigurationInfoType createPropertyFilterConfigurationInfoType() {
        return new PropertyFilterConfigurationInfoType();
    }

    /**
     * Create an instance of {@link AnnotazioniPropertiesType }
     * 
     */
    public AnnotazioniPropertiesType createAnnotazioniPropertiesType() {
        return new AnnotazioniPropertiesType();
    }

    /**
     * Create an instance of {@link ProtocolloPropertiesType }
     * 
     */
    public ProtocolloPropertiesType createProtocolloPropertiesType() {
        return new ProtocolloPropertiesType();
    }

    /**
     * Create an instance of {@link CreaSmistamento }
     * 
     */
    public CreaSmistamento createCreaSmistamento() {
        return new CreaSmistamento();
    }

    /**
     * Create an instance of {@link MittenteType }
     * 
     */
    public MittenteType createMittenteType() {
        return new MittenteType();
    }

    /**
     * Create an instance of {@link DestinatarioType }
     * 
     */
    public DestinatarioType createDestinatarioType() {
        return new DestinatarioType();
    }

    /**
     * Create an instance of {@link OggettoSmistamentoType }
     * 
     */
    public OggettoSmistamentoType createOggettoSmistamentoType() {
        return new OggettoSmistamentoType();
    }

    /**
     * Create an instance of {@link InfoCreazioneType }
     * 
     */
    public InfoCreazioneType createInfoCreazioneType() {
        return new InfoCreazioneType();
    }

    /**
     * Create an instance of {@link CreaSmistamentoResponse }
     * 
     */
    public CreaSmistamentoResponse createCreaSmistamentoResponse() {
        return new CreaSmistamentoResponse();
    }

    /**
     * Create an instance of {@link CompletaSmistamentoFirmaDwd }
     * 
     */
    public CompletaSmistamentoFirmaDwd createCompletaSmistamentoFirmaDwd() {
        return new CompletaSmistamentoFirmaDwd();
    }

    /**
     * Create an instance of {@link DocumentoFisicoIRC }
     * 
     */
    public DocumentoFisicoIRC createDocumentoFisicoIRC() {
        return new DocumentoFisicoIRC();
    }

    /**
     * Create an instance of {@link DestinatarioConNoteType }
     * 
     */
    public DestinatarioConNoteType createDestinatarioConNoteType() {
        return new DestinatarioConNoteType();
    }

    /**
     * Create an instance of {@link CompletaSmistamentoFirmaDwdResponse }
     * 
     */
    public CompletaSmistamentoFirmaDwdResponse createCompletaSmistamentoFirmaDwdResponse() {
        return new CompletaSmistamentoFirmaDwdResponse();
    }

    /**
     * Create an instance of {@link AnnullaSmistamentoFirmaDwd }
     * 
     */
    public AnnullaSmistamentoFirmaDwd createAnnullaSmistamentoFirmaDwd() {
        return new AnnullaSmistamentoFirmaDwd();
    }

    /**
     * Create an instance of {@link AnnullaSmistamentoFirmaDwdResponse }
     * 
     */
    public AnnullaSmistamentoFirmaDwdResponse createAnnullaSmistamentoFirmaDwdResponse() {
        return new AnnullaSmistamentoFirmaDwdResponse();
    }

    /**
     * Create an instance of {@link IdNodoSmistamentoType }
     * 
     */
    public IdNodoSmistamentoType createIdNodoSmistamentoType() {
        return new IdNodoSmistamentoType();
    }

    /**
     * Create an instance of {@link IdTipoSmistamentoType }
     * 
     */
    public IdTipoSmistamentoType createIdTipoSmistamentoType() {
        return new IdTipoSmistamentoType();
    }

    /**
     * Create an instance of {@link IdentificatoreDocumento }
     * 
     */
    public IdentificatoreDocumento createIdentificatoreDocumento() {
        return new IdentificatoreDocumento();
    }

    /**
     * Create an instance of {@link IdentificazioneTrasformazione }
     * 
     */
    public IdentificazioneTrasformazione createIdentificazioneTrasformazione() {
        return new IdentificazioneTrasformazione();
    }

    /**
     * Create an instance of {@link InfoRichiestaTrasformazione }
     * 
     */
    public InfoRichiestaTrasformazione createInfoRichiestaTrasformazione() {
        return new InfoRichiestaTrasformazione();
    }

    /**
     * Create an instance of {@link FailedStepsInfo }
     * 
     */
    public FailedStepsInfo createFailedStepsInfo() {
        return new FailedStepsInfo();
    }

    /**
     * Create an instance of {@link DocumentoArchivisticoIRC }
     * 
     */
    public DocumentoArchivisticoIRC createDocumentoArchivisticoIRC() {
        return new DocumentoArchivisticoIRC();
    }

    /**
     * Create an instance of {@link ContenutoFisicoIRC }
     * 
     */
    public ContenutoFisicoIRC createContenutoFisicoIRC() {
        return new ContenutoFisicoIRC();
    }

    /**
     * Create an instance of {@link StepErrorAction }
     * 
     */
    public StepErrorAction createStepErrorAction() {
        return new StepErrorAction();
    }

    /**
     * Create an instance of {@link CollocazioneDocumento }
     * 
     */
    public CollocazioneDocumento createCollocazioneDocumento() {
        return new CollocazioneDocumento();
    }

    /**
     * Create an instance of {@link DocumentoArchivisticoIdMap }
     * 
     */
    public DocumentoArchivisticoIdMap createDocumentoArchivisticoIdMap() {
        return new DocumentoArchivisticoIdMap();
    }

    /**
     * Create an instance of {@link DocumentoFisicoIdMap }
     * 
     */
    public DocumentoFisicoIdMap createDocumentoFisicoIdMap() {
        return new DocumentoFisicoIdMap();
    }

    /**
     * Create an instance of {@link ContenutoFisicoIdMap }
     * 
     */
    public ContenutoFisicoIdMap createContenutoFisicoIdMap() {
        return new ContenutoFisicoIdMap();
    }

    /**
     * Create an instance of {@link TitolarioPropertiesType }
     * 
     */
    public TitolarioPropertiesType createTitolarioPropertiesType() {
        return new TitolarioPropertiesType();
    }

    /**
     * Create an instance of {@link ClassificazionePropertiesType }
     * 
     */
    public ClassificazionePropertiesType createClassificazionePropertiesType() {
        return new ClassificazionePropertiesType();
    }

    /**
     * Create an instance of {@link VocePropertiesType }
     * 
     */
    public VocePropertiesType createVocePropertiesType() {
        return new VocePropertiesType();
    }

    /**
     * Create an instance of {@link SottofascicoloPropertiesType }
     * 
     */
    public SottofascicoloPropertiesType createSottofascicoloPropertiesType() {
        return new SottofascicoloPropertiesType();
    }

    /**
     * Create an instance of {@link DossierPropertiesType }
     * 
     */
    public DossierPropertiesType createDossierPropertiesType() {
        return new DossierPropertiesType();
    }

    /**
     * Create an instance of {@link VolumeSerieFascicoliPropertiesType }
     * 
     */
    public VolumeSerieFascicoliPropertiesType createVolumeSerieFascicoliPropertiesType() {
        return new VolumeSerieFascicoliPropertiesType();
    }

    /**
     * Create an instance of {@link VolumeSerieTipologicaDocumentiPropertiesType }
     * 
     */
    public VolumeSerieTipologicaDocumentiPropertiesType createVolumeSerieTipologicaDocumentiPropertiesType() {
        return new VolumeSerieTipologicaDocumentiPropertiesType();
    }

    /**
     * Create an instance of {@link VolumeFascicoliPropertiesType }
     * 
     */
    public VolumeFascicoliPropertiesType createVolumeFascicoliPropertiesType() {
        return new VolumeFascicoliPropertiesType();
    }

    /**
     * Create an instance of {@link VolumeSottofascicoliPropertiesType }
     * 
     */
    public VolumeSottofascicoliPropertiesType createVolumeSottofascicoliPropertiesType() {
        return new VolumeSottofascicoliPropertiesType();
    }

    /**
     * Create an instance of {@link SerieFascicoliPropertiesType }
     * 
     */
    public SerieFascicoliPropertiesType createSerieFascicoliPropertiesType() {
        return new SerieFascicoliPropertiesType();
    }

    /**
     * Create an instance of {@link SerieTipologicaDocumentiPropertiesType }
     * 
     */
    public SerieTipologicaDocumentiPropertiesType createSerieTipologicaDocumentiPropertiesType() {
        return new SerieTipologicaDocumentiPropertiesType();
    }

    /**
     * Create an instance of {@link SerieDossierPropertiesType }
     * 
     */
    public SerieDossierPropertiesType createSerieDossierPropertiesType() {
        return new SerieDossierPropertiesType();
    }

    /**
     * Create an instance of {@link FascicoloRealeLiberoPropertiesType }
     * 
     */
    public FascicoloRealeLiberoPropertiesType createFascicoloRealeLiberoPropertiesType() {
        return new FascicoloRealeLiberoPropertiesType();
    }

    /**
     * Create an instance of {@link FascicoloRealeAnnualePropertiesType }
     * 
     */
    public FascicoloRealeAnnualePropertiesType createFascicoloRealeAnnualePropertiesType() {
        return new FascicoloRealeAnnualePropertiesType();
    }

    /**
     * Create an instance of {@link FascicoloRealeLegislaturaPropertiesType }
     * 
     */
    public FascicoloRealeLegislaturaPropertiesType createFascicoloRealeLegislaturaPropertiesType() {
        return new FascicoloRealeLegislaturaPropertiesType();
    }

    /**
     * Create an instance of {@link FascicoloRealeEreditatoPropertiesType }
     * 
     */
    public FascicoloRealeEreditatoPropertiesType createFascicoloRealeEreditatoPropertiesType() {
        return new FascicoloRealeEreditatoPropertiesType();
    }

    /**
     * Create an instance of {@link FascicoloRealeContinuoPropertiesType }
     * 
     */
    public FascicoloRealeContinuoPropertiesType createFascicoloRealeContinuoPropertiesType() {
        return new FascicoloRealeContinuoPropertiesType();
    }

    /**
     * Create an instance of {@link FascicoloTemporaneoPropertiesType }
     * 
     */
    public FascicoloTemporaneoPropertiesType createFascicoloTemporaneoPropertiesType() {
        return new FascicoloTemporaneoPropertiesType();
    }

    /**
     * Create an instance of {@link DocumentoFisicoPropertiesType }
     * 
     */
    public DocumentoFisicoPropertiesType createDocumentoFisicoPropertiesType() {
        return new DocumentoFisicoPropertiesType();
    }

    /**
     * Create an instance of {@link GruppoAllegatiPropertiesType }
     * 
     */
    public GruppoAllegatiPropertiesType createGruppoAllegatiPropertiesType() {
        return new GruppoAllegatiPropertiesType();
    }

    /**
     * Create an instance of {@link ClipsMetallicaPropertiesType }
     * 
     */
    public ClipsMetallicaPropertiesType createClipsMetallicaPropertiesType() {
        return new ClipsMetallicaPropertiesType();
    }

    /**
     * Create an instance of {@link DocumentoSemplicePropertiesType }
     * 
     */
    public DocumentoSemplicePropertiesType createDocumentoSemplicePropertiesType() {
        return new DocumentoSemplicePropertiesType();
    }

    /**
     * Create an instance of {@link DocumentoRegistroPropertiesType }
     * 
     */
    public DocumentoRegistroPropertiesType createDocumentoRegistroPropertiesType() {
        return new DocumentoRegistroPropertiesType();
    }

    /**
     * Create an instance of {@link DocumentoDBPropertiesType }
     * 
     */
    public DocumentoDBPropertiesType createDocumentoDBPropertiesType() {
        return new DocumentoDBPropertiesType();
    }

    /**
     * Create an instance of {@link ContenutoFisicoPropertiesType }
     * 
     */
    public ContenutoFisicoPropertiesType createContenutoFisicoPropertiesType() {
        return new ContenutoFisicoPropertiesType();
    }

    /**
     * Create an instance of {@link DocumentAssociationPropertiesType }
     * 
     */
    public DocumentAssociationPropertiesType createDocumentAssociationPropertiesType() {
        return new DocumentAssociationPropertiesType();
    }

    /**
     * Create an instance of {@link HistoryModificheTecnichePropertiesType }
     * 
     */
    public HistoryModificheTecnichePropertiesType createHistoryModificheTecnichePropertiesType() {
        return new HistoryModificheTecnichePropertiesType();
    }

    /**
     * Create an instance of {@link DocumentCompositionPropertiesType }
     * 
     */
    public DocumentCompositionPropertiesType createDocumentCompositionPropertiesType() {
        return new DocumentCompositionPropertiesType();
    }

    /**
     * Create an instance of {@link HistoryVecchieVersioniPropertiesType }
     * 
     */
    public HistoryVecchieVersioniPropertiesType createHistoryVecchieVersioniPropertiesType() {
        return new HistoryVecchieVersioniPropertiesType();
    }

    /**
     * Create an instance of {@link IdFascicoloStandardType }
     * 
     */
    public IdFascicoloStandardType createIdFascicoloStandardType() {
        return new IdFascicoloStandardType();
    }

    /**
     * Create an instance of {@link IdFormaDocumentariaType }
     * 
     */
    public IdFormaDocumentariaType createIdFormaDocumentariaType() {
        return new IdFormaDocumentariaType();
    }

    /**
     * Create an instance of {@link IdStatoDiEfficaciaType }
     * 
     */
    public IdStatoDiEfficaciaType createIdStatoDiEfficaciaType() {
        return new IdStatoDiEfficaciaType();
    }

    /**
     * Create an instance of {@link FascicoloStandardType }
     * 
     */
    public FascicoloStandardType createFascicoloStandardType() {
        return new FascicoloStandardType();
    }

    /**
     * Create an instance of {@link FormaDocumentariaType }
     * 
     */
    public FormaDocumentariaType createFormaDocumentariaType() {
        return new FormaDocumentariaType();
    }

    /**
     * Create an instance of {@link StatoDiEfficaciaType }
     * 
     */
    public StatoDiEfficaciaType createStatoDiEfficaciaType() {
        return new StatoDiEfficaciaType();
    }

    /**
     * Create an instance of {@link DatiCertificatoType }
     * 
     */
    public DatiCertificatoType createDatiCertificatoType() {
        return new DatiCertificatoType();
    }

    /**
     * Create an instance of {@link RelationshipPropertiesType }
     * 
     */
    public RelationshipPropertiesType createRelationshipPropertiesType() {
        return new RelationshipPropertiesType();
    }

    /**
     * Create an instance of {@link ActaACEPropertiesType }
     * 
     */
    public ActaACEPropertiesType createActaACEPropertiesType() {
        return new ActaACEPropertiesType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcarisFaultType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AcarisFaultType }{@code >}
     */
    @XmlElementDecl(namespace = "common.acaris.acta.doqui.it", name = "acarisFault")
    public JAXBElement<AcarisFaultType> createAcarisFault(AcarisFaultType value) {
        return new JAXBElement<AcarisFaultType>(_AcarisFault_QNAME, AcarisFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TitolarioPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TitolarioPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "titolarioProperties")
    public JAXBElement<TitolarioPropertiesType> createTitolarioProperties(TitolarioPropertiesType value) {
        return new JAXBElement<TitolarioPropertiesType>(_TitolarioProperties_QNAME, TitolarioPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClassificazionePropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClassificazionePropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "classificazioneProperties")
    public JAXBElement<ClassificazionePropertiesType> createClassificazioneProperties(ClassificazionePropertiesType value) {
        return new JAXBElement<ClassificazionePropertiesType>(_ClassificazioneProperties_QNAME, ClassificazionePropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VocePropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VocePropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "voceProperties")
    public JAXBElement<VocePropertiesType> createVoceProperties(VocePropertiesType value) {
        return new JAXBElement<VocePropertiesType>(_VoceProperties_QNAME, VocePropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SottofascicoloPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SottofascicoloPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "sottofascicoloProperties")
    public JAXBElement<SottofascicoloPropertiesType> createSottofascicoloProperties(SottofascicoloPropertiesType value) {
        return new JAXBElement<SottofascicoloPropertiesType>(_SottofascicoloProperties_QNAME, SottofascicoloPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DossierPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DossierPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "dossierProperties")
    public JAXBElement<DossierPropertiesType> createDossierProperties(DossierPropertiesType value) {
        return new JAXBElement<DossierPropertiesType>(_DossierProperties_QNAME, DossierPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VolumeSerieFascicoliPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VolumeSerieFascicoliPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "volumeSerieFascicoliProperties")
    public JAXBElement<VolumeSerieFascicoliPropertiesType> createVolumeSerieFascicoliProperties(VolumeSerieFascicoliPropertiesType value) {
        return new JAXBElement<VolumeSerieFascicoliPropertiesType>(_VolumeSerieFascicoliProperties_QNAME, VolumeSerieFascicoliPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VolumeSerieTipologicaDocumentiPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VolumeSerieTipologicaDocumentiPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "volumeSerieTipologicaDocumentiProperties")
    public JAXBElement<VolumeSerieTipologicaDocumentiPropertiesType> createVolumeSerieTipologicaDocumentiProperties(VolumeSerieTipologicaDocumentiPropertiesType value) {
        return new JAXBElement<VolumeSerieTipologicaDocumentiPropertiesType>(_VolumeSerieTipologicaDocumentiProperties_QNAME, VolumeSerieTipologicaDocumentiPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VolumeFascicoliPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VolumeFascicoliPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "volumeFascicoliProperties")
    public JAXBElement<VolumeFascicoliPropertiesType> createVolumeFascicoliProperties(VolumeFascicoliPropertiesType value) {
        return new JAXBElement<VolumeFascicoliPropertiesType>(_VolumeFascicoliProperties_QNAME, VolumeFascicoliPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VolumeSottofascicoliPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VolumeSottofascicoliPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "volumeSottofascicoliProperties")
    public JAXBElement<VolumeSottofascicoliPropertiesType> createVolumeSottofascicoliProperties(VolumeSottofascicoliPropertiesType value) {
        return new JAXBElement<VolumeSottofascicoliPropertiesType>(_VolumeSottofascicoliProperties_QNAME, VolumeSottofascicoliPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SerieFascicoliPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SerieFascicoliPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "serieFascicoliProperties")
    public JAXBElement<SerieFascicoliPropertiesType> createSerieFascicoliProperties(SerieFascicoliPropertiesType value) {
        return new JAXBElement<SerieFascicoliPropertiesType>(_SerieFascicoliProperties_QNAME, SerieFascicoliPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SerieTipologicaDocumentiPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SerieTipologicaDocumentiPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "serieTipologicaDocumentiProperties")
    public JAXBElement<SerieTipologicaDocumentiPropertiesType> createSerieTipologicaDocumentiProperties(SerieTipologicaDocumentiPropertiesType value) {
        return new JAXBElement<SerieTipologicaDocumentiPropertiesType>(_SerieTipologicaDocumentiProperties_QNAME, SerieTipologicaDocumentiPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SerieDossierPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SerieDossierPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "serieDossierProperties")
    public JAXBElement<SerieDossierPropertiesType> createSerieDossierProperties(SerieDossierPropertiesType value) {
        return new JAXBElement<SerieDossierPropertiesType>(_SerieDossierProperties_QNAME, SerieDossierPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FascicoloRealeLiberoPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FascicoloRealeLiberoPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "fascicoloRealeLiberoProperties")
    public JAXBElement<FascicoloRealeLiberoPropertiesType> createFascicoloRealeLiberoProperties(FascicoloRealeLiberoPropertiesType value) {
        return new JAXBElement<FascicoloRealeLiberoPropertiesType>(_FascicoloRealeLiberoProperties_QNAME, FascicoloRealeLiberoPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FascicoloRealeAnnualePropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FascicoloRealeAnnualePropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "fascicoloRealeAnnualeProperties")
    public JAXBElement<FascicoloRealeAnnualePropertiesType> createFascicoloRealeAnnualeProperties(FascicoloRealeAnnualePropertiesType value) {
        return new JAXBElement<FascicoloRealeAnnualePropertiesType>(_FascicoloRealeAnnualeProperties_QNAME, FascicoloRealeAnnualePropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FascicoloRealeLegislaturaPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FascicoloRealeLegislaturaPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "fascicoloRealeLegislaturaProperties")
    public JAXBElement<FascicoloRealeLegislaturaPropertiesType> createFascicoloRealeLegislaturaProperties(FascicoloRealeLegislaturaPropertiesType value) {
        return new JAXBElement<FascicoloRealeLegislaturaPropertiesType>(_FascicoloRealeLegislaturaProperties_QNAME, FascicoloRealeLegislaturaPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FascicoloRealeEreditatoPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FascicoloRealeEreditatoPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "fascicoloRealeEreditatoProperties")
    public JAXBElement<FascicoloRealeEreditatoPropertiesType> createFascicoloRealeEreditatoProperties(FascicoloRealeEreditatoPropertiesType value) {
        return new JAXBElement<FascicoloRealeEreditatoPropertiesType>(_FascicoloRealeEreditatoProperties_QNAME, FascicoloRealeEreditatoPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FascicoloRealeContinuoPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FascicoloRealeContinuoPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "fascicoloRealeContinuoProperties")
    public JAXBElement<FascicoloRealeContinuoPropertiesType> createFascicoloRealeContinuoProperties(FascicoloRealeContinuoPropertiesType value) {
        return new JAXBElement<FascicoloRealeContinuoPropertiesType>(_FascicoloRealeContinuoProperties_QNAME, FascicoloRealeContinuoPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FascicoloTemporaneoPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FascicoloTemporaneoPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "fascicoloTemporaneoProperties")
    public JAXBElement<FascicoloTemporaneoPropertiesType> createFascicoloTemporaneoProperties(FascicoloTemporaneoPropertiesType value) {
        return new JAXBElement<FascicoloTemporaneoPropertiesType>(_FascicoloTemporaneoProperties_QNAME, FascicoloTemporaneoPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentoFisicoPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentoFisicoPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "documentoFisicoProperties")
    public JAXBElement<DocumentoFisicoPropertiesType> createDocumentoFisicoProperties(DocumentoFisicoPropertiesType value) {
        return new JAXBElement<DocumentoFisicoPropertiesType>(_DocumentoFisicoProperties_QNAME, DocumentoFisicoPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GruppoAllegatiPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GruppoAllegatiPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "gruppoAllegatiProperties")
    public JAXBElement<GruppoAllegatiPropertiesType> createGruppoAllegatiProperties(GruppoAllegatiPropertiesType value) {
        return new JAXBElement<GruppoAllegatiPropertiesType>(_GruppoAllegatiProperties_QNAME, GruppoAllegatiPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClipsMetallicaPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClipsMetallicaPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "clipsMetallicaProperties")
    public JAXBElement<ClipsMetallicaPropertiesType> createClipsMetallicaProperties(ClipsMetallicaPropertiesType value) {
        return new JAXBElement<ClipsMetallicaPropertiesType>(_ClipsMetallicaProperties_QNAME, ClipsMetallicaPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentoSemplicePropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentoSemplicePropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "documentoSempliceProperties")
    public JAXBElement<DocumentoSemplicePropertiesType> createDocumentoSempliceProperties(DocumentoSemplicePropertiesType value) {
        return new JAXBElement<DocumentoSemplicePropertiesType>(_DocumentoSempliceProperties_QNAME, DocumentoSemplicePropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentoRegistroPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentoRegistroPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "documentoRegistroProperties")
    public JAXBElement<DocumentoRegistroPropertiesType> createDocumentoRegistroProperties(DocumentoRegistroPropertiesType value) {
        return new JAXBElement<DocumentoRegistroPropertiesType>(_DocumentoRegistroProperties_QNAME, DocumentoRegistroPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentoDBPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentoDBPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "documentoDBProperties")
    public JAXBElement<DocumentoDBPropertiesType> createDocumentoDBProperties(DocumentoDBPropertiesType value) {
        return new JAXBElement<DocumentoDBPropertiesType>(_DocumentoDBProperties_QNAME, DocumentoDBPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContenutoFisicoPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ContenutoFisicoPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "contenutoFisicoProperties")
    public JAXBElement<ContenutoFisicoPropertiesType> createContenutoFisicoProperties(ContenutoFisicoPropertiesType value) {
        return new JAXBElement<ContenutoFisicoPropertiesType>(_ContenutoFisicoProperties_QNAME, ContenutoFisicoPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentAssociationPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentAssociationPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "documentAssociationProperties")
    public JAXBElement<DocumentAssociationPropertiesType> createDocumentAssociationProperties(DocumentAssociationPropertiesType value) {
        return new JAXBElement<DocumentAssociationPropertiesType>(_DocumentAssociationProperties_QNAME, DocumentAssociationPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HistoryModificheTecnichePropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HistoryModificheTecnichePropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "historyModificheTecnicheProperties")
    public JAXBElement<HistoryModificheTecnichePropertiesType> createHistoryModificheTecnicheProperties(HistoryModificheTecnichePropertiesType value) {
        return new JAXBElement<HistoryModificheTecnichePropertiesType>(_HistoryModificheTecnicheProperties_QNAME, HistoryModificheTecnichePropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentCompositionPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentCompositionPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "documentCompositionProperties")
    public JAXBElement<DocumentCompositionPropertiesType> createDocumentCompositionProperties(DocumentCompositionPropertiesType value) {
        return new JAXBElement<DocumentCompositionPropertiesType>(_DocumentCompositionProperties_QNAME, DocumentCompositionPropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HistoryVecchieVersioniPropertiesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HistoryVecchieVersioniPropertiesType }{@code >}
     */
    @XmlElementDecl(namespace = "archive.acaris.acta.doqui.it", name = "historyVecchieVersioniProperties")
    public JAXBElement<HistoryVecchieVersioniPropertiesType> createHistoryVecchieVersioniProperties(HistoryVecchieVersioniPropertiesType value) {
        return new JAXBElement<HistoryVecchieVersioniPropertiesType>(_HistoryVecchieVersioniProperties_QNAME, HistoryVecchieVersioniPropertiesType.class, null, value);
    }

}
