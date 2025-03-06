// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _ArrayOfTIPOSERVIZIORESULT_QNAME;
    private static final QName _TIPOSERVIZIORESULT_QNAME;
    private static final QName _Richiesta_QNAME;
    private static final QName _ArrayOfFornitura_QNAME;
    private static final QName _Fornitura_QNAME;
    private static final QName _IndRecType_QNAME;
    private static final QName _IndUbiType_QNAME;
    private static final QName _Agente_QNAME;
    private static final QName _DATIINSERIMENTORICHIESTA_QNAME;
    private static final QName _TIPORICHIESTA_QNAME;
    private static final QName _ESITORICHIESTA_QNAME;
    private static final QName _ELENCORICHIESTE_QNAME;
    private static final QName _RICHIESTAINFO_QNAME;
    private static final QName _SOTTOTIPIRESULT_QNAME;
    private static final QName _ArrayOfSOTTOTIPIRESULT_QNAME;
    private static final QName _ArrayOfIndRecType_QNAME;
    private static final QName _ArrayOfIndUbiType_QNAME;
    private static final QName _PRODOTTORESULT_QNAME;
    private static final QName _ArrayOfPRODOTTORESULT_QNAME;
    private static final QName _ESITONORMALIZZAZIONEGEOCALL_QNAME;
    private static final QName _ArrayOfTipoFornitura_QNAME;
    private static final QName _TipoFornitura_QNAME;
    private static final QName _FiltriProdotto_QNAME;
    
    static {
        _ArrayOfTIPOSERVIZIORESULT_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ArrayOfTIPOSERVIZIORESULT");
        _TIPOSERVIZIORESULT_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "TIPOSERVIZIORESULT");
        _Richiesta_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "Richiesta");
        _ArrayOfFornitura_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ArrayOfFornitura");
        _Fornitura_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "Fornitura");
        _IndRecType_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "IndRecType");
        _IndUbiType_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "IndUbiType");
        _Agente_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "Agente");
        _DATIINSERIMENTORICHIESTA_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "DATIINSERIMENTORICHIESTA");
        _TIPORICHIESTA_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "TIPORICHIESTA");
        _ESITORICHIESTA_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ESITORICHIESTA");
        _ELENCORICHIESTE_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ELENCORICHIESTE");
        _RICHIESTAINFO_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "RICHIESTAINFO");
        _SOTTOTIPIRESULT_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "SOTTOTIPIRESULT");
        _ArrayOfSOTTOTIPIRESULT_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ArrayOfSOTTOTIPIRESULT");
        _ArrayOfIndRecType_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ArrayOfIndRecType");
        _ArrayOfIndUbiType_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ArrayOfIndUbiType");
        _PRODOTTORESULT_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "PRODOTTORESULT");
        _ArrayOfPRODOTTORESULT_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ArrayOfPRODOTTORESULT");
        _ESITONORMALIZZAZIONEGEOCALL_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ESITONORMALIZZAZIONEGEOCALL");
        _ArrayOfTipoFornitura_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "ArrayOfTipoFornitura");
        _TipoFornitura_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "TipoFornitura");
        _FiltriProdotto_QNAME = new QName("it.eng.prontoweb.types.pora.richieste", "FiltriProdotto");
    }
    
    public ArrayOfTIPOSERVIZIORESULT createArrayOfTIPOSERVIZIORESULT() {
        return new ArrayOfTIPOSERVIZIORESULT();
    }
    
    public TIPOSERVIZIORESULT createTIPOSERVIZIORESULT() {
        return new TIPOSERVIZIORESULT();
    }
    
    public Richiesta createRichiesta() {
        return new Richiesta();
    }
    
    public ArrayOfFornitura createArrayOfFornitura() {
        return new ArrayOfFornitura();
    }
    
    public Fornitura createFornitura() {
        return new Fornitura();
    }
    
    public IndRecType createIndRecType() {
        return new IndRecType();
    }
    
    public IndUbiType createIndUbiType() {
        return new IndUbiType();
    }
    
    public Agente createAgente() {
        return new Agente();
    }
    
    public DATIINSERIMENTORICHIESTA createDATIINSERIMENTORICHIESTA() {
        return new DATIINSERIMENTORICHIESTA();
    }
    
    public ESITORICHIESTA createESITORICHIESTA() {
        return new ESITORICHIESTA();
    }
    
    public ELENCORICHIESTE createELENCORICHIESTE() {
        return new ELENCORICHIESTE();
    }
    
    public RICHIESTAINFO createRICHIESTAINFO() {
        return new RICHIESTAINFO();
    }
    
    public SOTTOTIPIRESULT createSOTTOTIPIRESULT() {
        return new SOTTOTIPIRESULT();
    }
    
    public ArrayOfSOTTOTIPIRESULT createArrayOfSOTTOTIPIRESULT() {
        return new ArrayOfSOTTOTIPIRESULT();
    }
    
    public ArrayOfIndRecType createArrayOfIndRecType() {
        return new ArrayOfIndRecType();
    }
    
    public ArrayOfIndUbiType createArrayOfIndUbiType() {
        return new ArrayOfIndUbiType();
    }
    
    public PRODOTTORESULT createPRODOTTORESULT() {
        return new PRODOTTORESULT();
    }
    
    public ArrayOfPRODOTTORESULT createArrayOfPRODOTTORESULT() {
        return new ArrayOfPRODOTTORESULT();
    }
    
    public ESITONORMALIZZAZIONEGEOCALL createESITONORMALIZZAZIONEGEOCALL() {
        return new ESITONORMALIZZAZIONEGEOCALL();
    }
    
    public ArrayOfTipoFornitura createArrayOfTipoFornitura() {
        return new ArrayOfTipoFornitura();
    }
    
    public TipoFornitura createTipoFornitura() {
        return new TipoFornitura();
    }
    
    public FiltriProdotto createFiltriProdotto() {
        return new FiltriProdotto();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ArrayOfTIPOSERVIZIORESULT")
    public JAXBElement<ArrayOfTIPOSERVIZIORESULT> createArrayOfTIPOSERVIZIORESULT(final ArrayOfTIPOSERVIZIORESULT value) {
        return (JAXBElement<ArrayOfTIPOSERVIZIORESULT>)new JAXBElement(ObjectFactory._ArrayOfTIPOSERVIZIORESULT_QNAME, (Class)ArrayOfTIPOSERVIZIORESULT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "TIPOSERVIZIORESULT")
    public JAXBElement<TIPOSERVIZIORESULT> createTIPOSERVIZIORESULT(final TIPOSERVIZIORESULT value) {
        return (JAXBElement<TIPOSERVIZIORESULT>)new JAXBElement(ObjectFactory._TIPOSERVIZIORESULT_QNAME, (Class)TIPOSERVIZIORESULT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "Richiesta")
    public JAXBElement<Richiesta> createRichiesta(final Richiesta value) {
        return (JAXBElement<Richiesta>)new JAXBElement(ObjectFactory._Richiesta_QNAME, (Class)Richiesta.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ArrayOfFornitura")
    public JAXBElement<ArrayOfFornitura> createArrayOfFornitura(final ArrayOfFornitura value) {
        return (JAXBElement<ArrayOfFornitura>)new JAXBElement(ObjectFactory._ArrayOfFornitura_QNAME, (Class)ArrayOfFornitura.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "Fornitura")
    public JAXBElement<Fornitura> createFornitura(final Fornitura value) {
        return (JAXBElement<Fornitura>)new JAXBElement(ObjectFactory._Fornitura_QNAME, (Class)Fornitura.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "IndRecType")
    public JAXBElement<IndRecType> createIndRecType(final IndRecType value) {
        return (JAXBElement<IndRecType>)new JAXBElement(ObjectFactory._IndRecType_QNAME, (Class)IndRecType.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "IndUbiType")
    public JAXBElement<IndUbiType> createIndUbiType(final IndUbiType value) {
        return (JAXBElement<IndUbiType>)new JAXBElement(ObjectFactory._IndUbiType_QNAME, (Class)IndUbiType.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "Agente")
    public JAXBElement<Agente> createAgente(final Agente value) {
        return (JAXBElement<Agente>)new JAXBElement(ObjectFactory._Agente_QNAME, (Class)Agente.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "DATIINSERIMENTORICHIESTA")
    public JAXBElement<DATIINSERIMENTORICHIESTA> createDATIINSERIMENTORICHIESTA(final DATIINSERIMENTORICHIESTA value) {
        return (JAXBElement<DATIINSERIMENTORICHIESTA>)new JAXBElement(ObjectFactory._DATIINSERIMENTORICHIESTA_QNAME, (Class)DATIINSERIMENTORICHIESTA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "TIPORICHIESTA")
    public JAXBElement<TIPORICHIESTA> createTIPORICHIESTA(final TIPORICHIESTA value) {
        return (JAXBElement<TIPORICHIESTA>)new JAXBElement(ObjectFactory._TIPORICHIESTA_QNAME, (Class)TIPORICHIESTA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ESITORICHIESTA")
    public JAXBElement<ESITORICHIESTA> createESITORICHIESTA(final ESITORICHIESTA value) {
        return (JAXBElement<ESITORICHIESTA>)new JAXBElement(ObjectFactory._ESITORICHIESTA_QNAME, (Class)ESITORICHIESTA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ELENCORICHIESTE")
    public JAXBElement<ELENCORICHIESTE> createELENCORICHIESTE(final ELENCORICHIESTE value) {
        return (JAXBElement<ELENCORICHIESTE>)new JAXBElement(ObjectFactory._ELENCORICHIESTE_QNAME, (Class)ELENCORICHIESTE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "RICHIESTAINFO")
    public JAXBElement<RICHIESTAINFO> createRICHIESTAINFO(final RICHIESTAINFO value) {
        return (JAXBElement<RICHIESTAINFO>)new JAXBElement(ObjectFactory._RICHIESTAINFO_QNAME, (Class)RICHIESTAINFO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "SOTTOTIPIRESULT")
    public JAXBElement<SOTTOTIPIRESULT> createSOTTOTIPIRESULT(final SOTTOTIPIRESULT value) {
        return (JAXBElement<SOTTOTIPIRESULT>)new JAXBElement(ObjectFactory._SOTTOTIPIRESULT_QNAME, (Class)SOTTOTIPIRESULT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ArrayOfSOTTOTIPIRESULT")
    public JAXBElement<ArrayOfSOTTOTIPIRESULT> createArrayOfSOTTOTIPIRESULT(final ArrayOfSOTTOTIPIRESULT value) {
        return (JAXBElement<ArrayOfSOTTOTIPIRESULT>)new JAXBElement(ObjectFactory._ArrayOfSOTTOTIPIRESULT_QNAME, (Class)ArrayOfSOTTOTIPIRESULT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ArrayOfIndRecType")
    public JAXBElement<ArrayOfIndRecType> createArrayOfIndRecType(final ArrayOfIndRecType value) {
        return (JAXBElement<ArrayOfIndRecType>)new JAXBElement(ObjectFactory._ArrayOfIndRecType_QNAME, (Class)ArrayOfIndRecType.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ArrayOfIndUbiType")
    public JAXBElement<ArrayOfIndUbiType> createArrayOfIndUbiType(final ArrayOfIndUbiType value) {
        return (JAXBElement<ArrayOfIndUbiType>)new JAXBElement(ObjectFactory._ArrayOfIndUbiType_QNAME, (Class)ArrayOfIndUbiType.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "PRODOTTORESULT")
    public JAXBElement<PRODOTTORESULT> createPRODOTTORESULT(final PRODOTTORESULT value) {
        return (JAXBElement<PRODOTTORESULT>)new JAXBElement(ObjectFactory._PRODOTTORESULT_QNAME, (Class)PRODOTTORESULT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ArrayOfPRODOTTORESULT")
    public JAXBElement<ArrayOfPRODOTTORESULT> createArrayOfPRODOTTORESULT(final ArrayOfPRODOTTORESULT value) {
        return (JAXBElement<ArrayOfPRODOTTORESULT>)new JAXBElement(ObjectFactory._ArrayOfPRODOTTORESULT_QNAME, (Class)ArrayOfPRODOTTORESULT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ESITONORMALIZZAZIONEGEOCALL")
    public JAXBElement<ESITONORMALIZZAZIONEGEOCALL> createESITONORMALIZZAZIONEGEOCALL(final ESITONORMALIZZAZIONEGEOCALL value) {
        return (JAXBElement<ESITONORMALIZZAZIONEGEOCALL>)new JAXBElement(ObjectFactory._ESITONORMALIZZAZIONEGEOCALL_QNAME, (Class)ESITONORMALIZZAZIONEGEOCALL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "ArrayOfTipoFornitura")
    public JAXBElement<ArrayOfTipoFornitura> createArrayOfTipoFornitura(final ArrayOfTipoFornitura value) {
        return (JAXBElement<ArrayOfTipoFornitura>)new JAXBElement(ObjectFactory._ArrayOfTipoFornitura_QNAME, (Class)ArrayOfTipoFornitura.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "TipoFornitura")
    public JAXBElement<TipoFornitura> createTipoFornitura(final TipoFornitura value) {
        return (JAXBElement<TipoFornitura>)new JAXBElement(ObjectFactory._TipoFornitura_QNAME, (Class)TipoFornitura.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.richieste", name = "FiltriProdotto")
    public JAXBElement<FiltriProdotto> createFiltriProdotto(final FiltriProdotto value) {
        return (JAXBElement<FiltriProdotto>)new JAXBElement(ObjectFactory._FiltriProdotto_QNAME, (Class)FiltriProdotto.class, (Class)null, (Object)value);
    }
}
