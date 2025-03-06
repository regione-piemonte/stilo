// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import richieste.pora.types.prontoweb.eng.it.FiltriProdotto;
import autolettura.pora.types.prontoweb.eng.it.PERIODO;
import richieste.pora.types.prontoweb.eng.it.DATIINSERIMENTORICHIESTA;
import registrazione.pora.types.prontoweb.eng.it.DATISOGGETTOEXT;
import filtri.types.pora.types.prontoweb.eng.it.FiltroRichiesta;
import registrazione.pora.types.prontoweb.eng.it.ArrayOfCONSENSO;
import registrazione.pora.types.prontoweb.eng.it.DATISOGGETTO;
import richieste.pora.types.prontoweb.eng.it.IndRecType;
import richieste.pora.types.prontoweb.eng.it.IndUbiType;
import pora.types.prontoweb.eng.it.ArrayOfSTATOFORNITURA;
import allegati.pora.types.prontoweb.eng.it.ALLEGATORICHIESTA;
import lettureconsumi.pora.types.prontoweb.eng.it.TIPOFILE;
import pora.types.prontoweb.eng.it.ArrayOfFORNITURACP;
import autolettura.pora.types.prontoweb.eng.it.TIPOAUTOLETTURA;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import pora.types.prontoweb.eng.it.ArrayOfRUOLO;
import domiciliazione.pora.types.prontoweb.eng.it.DOMICILIAZIONE;
import javax.xml.ws.ResponseWrapper;
import javax.jws.WebMethod;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.Action;
import javax.jws.WebResult;
import pora.types.prontoweb.eng.it.RESULTMSG;
import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import javax.jws.WebParam;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import pora.types.prontoweb.eng.it.ObjectFactory;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.jws.WebService;

@WebService(targetNamespace = "http://poraservice/", name = "PORAPWEBService")
@XmlSeeAlso({ ObjectFactory.class, poraservice.ObjectFactory.class, lettureconsumi.pora.types.prontoweb.eng.it.ObjectFactory.class, prospect.pora.types.prontoweb.eng.it.ObjectFactory.class, alert.pora.types.prontoweb.eng.it.ObjectFactory.class, dettaglioservizio.pora.types.prontoweb.eng.it.ObjectFactory.class, autolettura.pora.types.prontoweb.eng.it.ObjectFactory.class, estrattoconto.pora.types.prontoweb.eng.it.ObjectFactory.class, richieste.types.pora.types.prontoweb.eng.it.ObjectFactory.class, richieste.pora.types.prontoweb.eng.it.ObjectFactory.class, stradario.pora.types.prontoweb.eng.it.ObjectFactory.class, allegati.pora.types.prontoweb.eng.it.ObjectFactory.class, com.microsoft.schemas._2003._10.serialization.arrays.ObjectFactory.class, domiciliazione.pora.types.prontoweb.eng.it.ObjectFactory.class, filtri.types.pora.types.prontoweb.eng.it.ObjectFactory.class, multicompany.pora.types.prontoweb.eng.it.ObjectFactory.class, org.datacontract.schemas._2004._07.pweb_be_pora.ObjectFactory.class, com.microsoft.schemas._2003._10.serialization.ObjectFactory.class, profilocliente.pora.types.prontoweb.eng.it.ObjectFactory.class, registrazione.pora.types.prontoweb.eng.it.ObjectFactory.class, log.pora.types.prontoweb.eng.it.ObjectFactory.class, org.datacontract.schemas._2004._07.pweb_be_pora_types.ObjectFactory.class })
public interface PORAPWEBService
{
    @WebResult(name = "GetDocumentiPagareResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetDocumentiPagare", output = "http://poraservice/PORAPWEBService/GetDocumentiPagareResponse")
    @RequestWrapper(localName = "GetDocumentiPagare", targetNamespace = "http://poraservice/", className = "poraservice.GetDocumentiPagare")
    @WebMethod(operationName = "GetDocumentiPagare", action = "http://poraservice/PORAPWEBService/GetDocumentiPagare")
    @ResponseWrapper(localName = "GetDocumentiPagareResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetDocumentiPagareResponse")
    RESULTMSG getDocumentiPagare(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filterTbl", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p3);
    
    @WebResult(name = "AttivaDomiciliazioneResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/AttivaDomiciliazione", output = "http://poraservice/PORAPWEBService/AttivaDomiciliazioneResponse")
    @RequestWrapper(localName = "AttivaDomiciliazione", targetNamespace = "http://poraservice/", className = "poraservice.AttivaDomiciliazione")
    @WebMethod(operationName = "AttivaDomiciliazione", action = "http://poraservice/PORAPWEBService/AttivaDomiciliazione")
    @ResponseWrapper(localName = "AttivaDomiciliazioneResponse", targetNamespace = "http://poraservice/", className = "poraservice.AttivaDomiciliazioneResponse")
    RESULTMSG attivaDomiciliazione(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "forntituraCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "domiciliazione", targetNamespace = "http://poraservice/") final DOMICILIAZIONE p3);
    
    @WebResult(name = "AutenticaTOKENResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/AutenticaTOKEN", output = "http://poraservice/PORAPWEBService/AutenticaTOKENResponse")
    @RequestWrapper(localName = "AutenticaTOKEN", targetNamespace = "http://poraservice/", className = "poraservice.AutenticaTOKEN")
    @WebMethod(operationName = "AutenticaTOKEN", action = "http://poraservice/PORAPWEBService/AutenticaTOKEN")
    @ResponseWrapper(localName = "AutenticaTOKENResponse", targetNamespace = "http://poraservice/", className = "poraservice.AutenticaTOKENResponse")
    RESULTMSG autenticaTOKEN(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "token", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruoli", targetNamespace = "http://poraservice/") final ArrayOfRUOLO p2);
    
    @WebResult(name = "SetAutoletturaFlussoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/SetAutoletturaFlusso", output = "http://poraservice/PORAPWEBService/SetAutoletturaFlussoResponse")
    @RequestWrapper(localName = "SetAutoletturaFlusso", targetNamespace = "http://poraservice/", className = "poraservice.SetAutoletturaFlusso")
    @WebMethod(operationName = "SetAutoletturaFlusso", action = "http://poraservice/PORAPWEBService/SetAutoletturaFlusso")
    @ResponseWrapper(localName = "SetAutoletturaFlussoResponse", targetNamespace = "http://poraservice/", className = "poraservice.SetAutoletturaFlussoResponse")
    RESULTMSG setAutoletturaFlusso(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "matricolaMis", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "valoreLetturaMis", targetNamespace = "http://poraservice/") final BigDecimal p3, @WebParam(name = "matricolaCorr", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "valoreLetturaCorr", targetNamespace = "http://poraservice/") final BigDecimal p5, @WebParam(name = "dataLettura", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p6, @WebParam(name = "tipoAutoLettura", targetNamespace = "http://poraservice/") final TIPOAUTOLETTURA p7);
    
    @WebResult(name = "DownloadCurvePrelievoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DownloadCurvePrelievo", output = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoResponse")
    @RequestWrapper(localName = "DownloadCurvePrelievo", targetNamespace = "http://poraservice/", className = "poraservice.DownloadCurvePrelievo")
    @WebMethod(operationName = "DownloadCurvePrelievo", action = "http://poraservice/PORAPWEBService/DownloadCurvePrelievo")
    @ResponseWrapper(localName = "DownloadCurvePrelievoResponse", targetNamespace = "http://poraservice/", className = "poraservice.DownloadCurvePrelievoResponse")
    RESULTMSG downloadCurvePrelievo(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "elencoForniture", targetNamespace = "http://poraservice/") final ArrayOfFORNITURACP p1, @WebParam(name = "periodoIni", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p2, @WebParam(name = "periodoFin", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p3, @WebParam(name = "tipoFile", targetNamespace = "http://poraservice/") final TIPOFILE p4);
    
    @WebResult(name = "GetTipiServizioResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetTipiServizio", output = "http://poraservice/PORAPWEBService/GetTipiServizioResponse")
    @RequestWrapper(localName = "GetTipiServizio", targetNamespace = "http://poraservice/", className = "poraservice.GetTipiServizio")
    @WebMethod(operationName = "GetTipiServizio", action = "http://poraservice/PORAPWEBService/GetTipiServizio")
    @ResponseWrapper(localName = "GetTipiServizioResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetTipiServizioResponse")
    RESULTMSG getTipiServizio(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "GetTipiRichiestaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetTipiRichiesta", output = "http://poraservice/PORAPWEBService/GetTipiRichiestaResponse")
    @RequestWrapper(localName = "GetTipiRichiesta", targetNamespace = "http://poraservice/", className = "poraservice.GetTipiRichiesta")
    @WebMethod(operationName = "GetTipiRichiesta", action = "http://poraservice/PORAPWEBService/GetTipiRichiesta")
    @ResponseWrapper(localName = "GetTipiRichiestaResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetTipiRichiestaResponse")
    RESULTMSG getTipiRichiesta(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "DisattivaPaperlessResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DisattivaPaperless", output = "http://poraservice/PORAPWEBService/DisattivaPaperlessResponse")
    @RequestWrapper(localName = "DisattivaPaperless", targetNamespace = "http://poraservice/", className = "poraservice.DisattivaPaperless")
    @WebMethod(operationName = "DisattivaPaperless", action = "http://poraservice/PORAPWEBService/DisattivaPaperless")
    @ResponseWrapper(localName = "DisattivaPaperlessResponse", targetNamespace = "http://poraservice/", className = "poraservice.DisattivaPaperlessResponse")
    RESULTMSG disattivaPaperless(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "codFornitura", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "UploadAllegatoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/UploadAllegato", output = "http://poraservice/PORAPWEBService/UploadAllegatoResponse")
    @RequestWrapper(localName = "UploadAllegato", targetNamespace = "http://poraservice/", className = "poraservice.UploadAllegato")
    @WebMethod(operationName = "UploadAllegato", action = "http://poraservice/PORAPWEBService/UploadAllegato")
    @ResponseWrapper(localName = "UploadAllegatoResponse", targetNamespace = "http://poraservice/", className = "poraservice.UploadAllegatoResponse")
    RESULTMSG uploadAllegato(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filtro", targetNamespace = "http://poraservice/") final ALLEGATORICHIESTA p1);
    
    @WebResult(name = "GetServiziLightResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetServiziLight", output = "http://poraservice/PORAPWEBService/GetServiziLightResponse")
    @RequestWrapper(localName = "GetServiziLight", targetNamespace = "http://poraservice/", className = "poraservice.GetServiziLight")
    @WebMethod(operationName = "GetServiziLight", action = "http://poraservice/PORAPWEBService/GetServiziLight")
    @ResponseWrapper(localName = "GetServiziLightResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetServiziLightResponse")
    RESULTMSG getServiziLight(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruolosoggCOD", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "soggCodAmm", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "codicePDRPOR", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p12, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p13);
    
    @WebResult(name = "NormalizzaIndirizzoUbiGeoCallResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/NormalizzaIndirizzoUbiGeoCall", output = "http://poraservice/PORAPWEBService/NormalizzaIndirizzoUbiGeoCallResponse")
    @RequestWrapper(localName = "NormalizzaIndirizzoUbiGeoCall", targetNamespace = "http://poraservice/", className = "poraservice.NormalizzaIndirizzoUbiGeoCall")
    @WebMethod(operationName = "NormalizzaIndirizzoUbiGeoCall", action = "http://poraservice/PORAPWEBService/NormalizzaIndirizzoUbiGeoCall")
    @ResponseWrapper(localName = "NormalizzaIndirizzoUbiGeoCallResponse", targetNamespace = "http://poraservice/", className = "poraservice.NormalizzaIndirizzoUbiGeoCallResponse")
    RESULTMSG normalizzaIndirizzoUbiGeoCall(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "indirizzo", targetNamespace = "http://poraservice/") final IndUbiType p1);
    
    @WebResult(name = "GetPDF_NETAResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetPDF_NETA", output = "http://poraservice/PORAPWEBService/GetPDF_NETAResponse")
    @RequestWrapper(localName = "GetPDF_NETA", targetNamespace = "http://poraservice/", className = "poraservice.GetPDFNETA")
    @WebMethod(operationName = "GetPDF_NETA", action = "http://poraservice/PORAPWEBService/GetPDF_NETA")
    @ResponseWrapper(localName = "GetPDF_NETAResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetPDFNETAResponse")
    RESULTMSG getPDFNETA(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "documentoID", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "LoginResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/Login", output = "http://poraservice/PORAPWEBService/LoginResponse")
    @RequestWrapper(localName = "Login", targetNamespace = "http://poraservice/", className = "poraservice.Login")
    @WebMethod(operationName = "Login", action = "http://poraservice/PORAPWEBService/Login")
    @ResponseWrapper(localName = "LoginResponse", targetNamespace = "http://poraservice/", className = "poraservice.LoginResponse")
    RESULTMSG login(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "account", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "password", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "ruoli", targetNamespace = "http://poraservice/") final ArrayOfRUOLO p3);
    
    @WebResult(name = "GetCurrentVersionResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetCurrentVersion", output = "http://poraservice/PORAPWEBService/GetCurrentVersionResponse")
    @RequestWrapper(localName = "GetCurrentVersion", targetNamespace = "http://poraservice/", className = "poraservice.GetCurrentVersion")
    @WebMethod(operationName = "GetCurrentVersion", action = "http://poraservice/PORAPWEBService/GetCurrentVersion")
    @ResponseWrapper(localName = "GetCurrentVersionResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetCurrentVersionResponse")
    RESULTMSG getCurrentVersion();
    
    @WebResult(name = "SbloccaAccountResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/SbloccaAccount", output = "http://poraservice/PORAPWEBService/SbloccaAccountResponse")
    @RequestWrapper(localName = "SbloccaAccount", targetNamespace = "http://poraservice/", className = "poraservice.SbloccaAccount")
    @WebMethod(operationName = "SbloccaAccount", action = "http://poraservice/PORAPWEBService/SbloccaAccount")
    @ResponseWrapper(localName = "SbloccaAccountResponse", targetNamespace = "http://poraservice/", className = "poraservice.SbloccaAccountResponse")
    RESULTMSG sbloccaAccount(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "token", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruoli", targetNamespace = "http://poraservice/") final ArrayOfRUOLO p2);
    
    @WebResult(name = "DownloadEstrattoContoGeneraleResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DownloadEstrattoContoGenerale", output = "http://poraservice/PORAPWEBService/DownloadEstrattoContoGeneraleResponse")
    @RequestWrapper(localName = "DownloadEstrattoContoGenerale", targetNamespace = "http://poraservice/", className = "poraservice.DownloadEstrattoContoGenerale")
    @WebMethod(operationName = "DownloadEstrattoContoGenerale", action = "http://poraservice/PORAPWEBService/DownloadEstrattoContoGenerale")
    @ResponseWrapper(localName = "DownloadEstrattoContoGeneraleResponse", targetNamespace = "http://poraservice/", className = "poraservice.DownloadEstrattoContoGeneraleResponse")
    RESULTMSG downloadEstrattoContoGenerale(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruoloSoggettoCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p8, @WebParam(name = "soggettoCodAmm", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "codicePODPDR", targetNamespace = "http://poraservice/") final String p12);
    
    @WebResult(name = "CambioPasswordResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/CambioPassword", output = "http://poraservice/PORAPWEBService/CambioPasswordResponse")
    @RequestWrapper(localName = "CambioPassword", targetNamespace = "http://poraservice/", className = "poraservice.CambioPassword")
    @WebMethod(operationName = "CambioPassword", action = "http://poraservice/PORAPWEBService/CambioPassword")
    @ResponseWrapper(localName = "CambioPasswordResponse", targetNamespace = "http://poraservice/", className = "poraservice.CambioPasswordResponse")
    RESULTMSG cambioPassword(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "account", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "password", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nuovoAccount", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "nuovaPassword", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "token", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "ruoli", targetNamespace = "http://poraservice/") final ArrayOfRUOLO p6);
    
    @WebResult(name = "GetTipoViaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetTipoVia", output = "http://poraservice/PORAPWEBService/GetTipoViaResponse")
    @RequestWrapper(localName = "GetTipoVia", targetNamespace = "http://poraservice/", className = "poraservice.GetTipoVia")
    @WebMethod(operationName = "GetTipoVia", action = "http://poraservice/PORAPWEBService/GetTipoVia")
    @ResponseWrapper(localName = "GetTipoViaResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetTipoViaResponse")
    RESULTMSG getTipoVia(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "criterioRicerca", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "NormalizzaIndirizzoRecGeoCallResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/NormalizzaIndirizzoRecGeoCall", output = "http://poraservice/PORAPWEBService/NormalizzaIndirizzoRecGeoCallResponse")
    @RequestWrapper(localName = "NormalizzaIndirizzoRecGeoCall", targetNamespace = "http://poraservice/", className = "poraservice.NormalizzaIndirizzoRecGeoCall")
    @WebMethod(operationName = "NormalizzaIndirizzoRecGeoCall", action = "http://poraservice/PORAPWEBService/NormalizzaIndirizzoRecGeoCall")
    @ResponseWrapper(localName = "NormalizzaIndirizzoRecGeoCallResponse", targetNamespace = "http://poraservice/", className = "poraservice.NormalizzaIndirizzoRecGeoCallResponse")
    RESULTMSG normalizzaIndirizzoRecGeoCall(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "indirizzo", targetNamespace = "http://poraservice/") final IndRecType p1);
    
    @WebResult(name = "RecuperaPasswordResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/RecuperaPassword", output = "http://poraservice/PORAPWEBService/RecuperaPasswordResponse")
    @RequestWrapper(localName = "RecuperaPassword", targetNamespace = "http://poraservice/", className = "poraservice.RecuperaPassword")
    @WebMethod(operationName = "RecuperaPassword", action = "http://poraservice/PORAPWEBService/RecuperaPassword")
    @ResponseWrapper(localName = "RecuperaPasswordResponse", targetNamespace = "http://poraservice/", className = "poraservice.RecuperaPasswordResponse")
    RESULTMSG recuperaPassword(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggetto", targetNamespace = "http://poraservice/") final DATISOGGETTO p1);
    
    @WebResult(name = "SetRegistrazioneResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/SetRegistrazione", output = "http://poraservice/PORAPWEBService/SetRegistrazioneResponse")
    @RequestWrapper(localName = "SetRegistrazione", targetNamespace = "http://poraservice/", className = "poraservice.SetRegistrazione")
    @WebMethod(operationName = "SetRegistrazione", action = "http://poraservice/PORAPWEBService/SetRegistrazione")
    @ResponseWrapper(localName = "SetRegistrazioneResponse", targetNamespace = "http://poraservice/", className = "poraservice.SetRegistrazioneResponse")
    RESULTMSG setRegistrazione(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "username", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "password", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "soggetto", targetNamespace = "http://poraservice/") final DATISOGGETTO p3, @WebParam(name = "consensi", targetNamespace = "http://poraservice/") final ArrayOfCONSENSO p4, @WebParam(name = "ruoli", targetNamespace = "http://poraservice/") final ArrayOfRUOLO p5);
    
    @WebResult(name = "GetConsensiSoggettoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetConsensiSoggetto", output = "http://poraservice/PORAPWEBService/GetConsensiSoggettoResponse")
    @RequestWrapper(localName = "GetConsensiSoggetto", targetNamespace = "http://poraservice/", className = "poraservice.GetConsensiSoggetto")
    @WebMethod(operationName = "GetConsensiSoggetto", action = "http://poraservice/PORAPWEBService/GetConsensiSoggetto")
    @ResponseWrapper(localName = "GetConsensiSoggettoResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetConsensiSoggettoResponse")
    RESULTMSG getConsensiSoggetto(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "GetLineeProdottoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetLineeProdotto", output = "http://poraservice/PORAPWEBService/GetLineeProdottoResponse")
    @RequestWrapper(localName = "GetLineeProdotto", targetNamespace = "http://poraservice/", className = "poraservice.GetLineeProdotto")
    @WebMethod(operationName = "GetLineeProdotto", action = "http://poraservice/PORAPWEBService/GetLineeProdotto")
    @ResponseWrapper(localName = "GetLineeProdottoResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetLineeProdottoResponse")
    RESULTMSG getLineeProdotto(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "GetSituazioneDebitoriaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetSituazioneDebitoria", output = "http://poraservice/PORAPWEBService/GetSituazioneDebitoriaResponse")
    @RequestWrapper(localName = "GetSituazioneDebitoria", targetNamespace = "http://poraservice/", className = "poraservice.GetSituazioneDebitoria")
    @WebMethod(operationName = "GetSituazioneDebitoria", action = "http://poraservice/PORAPWEBService/GetSituazioneDebitoria")
    @ResponseWrapper(localName = "GetSituazioneDebitoriaResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetSituazioneDebitoriaResponse")
    RESULTMSG getSituazioneDebitoria(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filterTbl", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "fornituraCOD", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "ruoloSoggettoCod", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "podpdrCod", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "dataIniEmis", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p7, @WebParam(name = "nonPagati", targetNamespace = "http://poraservice/") final Boolean p8, @WebParam(name = "ultimaFatt", targetNamespace = "http://poraservice/") final Boolean p9);
    
    @WebResult(name = "GetSoggettiResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetSoggetti", output = "http://poraservice/PORAPWEBService/GetSoggettiResponse")
    @RequestWrapper(localName = "GetSoggetti", targetNamespace = "http://poraservice/", className = "poraservice.GetSoggetti")
    @WebMethod(operationName = "GetSoggetti", action = "http://poraservice/PORAPWEBService/GetSoggetti")
    @ResponseWrapper(localName = "GetSoggettiResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetSoggettiResponse")
    RESULTMSG getSoggetti(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruolosoggCOD", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "soggCodAmm", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "codicePDRPOR", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p10, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p11);
    
    @WebResult(name = "GetServiziResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetServizi", output = "http://poraservice/PORAPWEBService/GetServiziResponse")
    @RequestWrapper(localName = "GetServizi", targetNamespace = "http://poraservice/", className = "poraservice.GetServizi")
    @WebMethod(operationName = "GetServizi", action = "http://poraservice/PORAPWEBService/GetServizi")
    @ResponseWrapper(localName = "GetServiziResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetServiziResponse")
    RESULTMSG getServizi(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruolosoggCOD", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "soggCodAmm", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "codicePDRPOR", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p12, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p13);
    
    @WebResult(name = "UpdateSoggettoProspectResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/UpdateSoggettoProspect", output = "http://poraservice/PORAPWEBService/UpdateSoggettoProspectResponse")
    @RequestWrapper(localName = "UpdateSoggettoProspect", targetNamespace = "http://poraservice/", className = "poraservice.UpdateSoggettoProspect")
    @WebMethod(operationName = "UpdateSoggettoProspect", action = "http://poraservice/PORAPWEBService/UpdateSoggettoProspect")
    @ResponseWrapper(localName = "UpdateSoggettoProspectResponse", targetNamespace = "http://poraservice/", className = "poraservice.UpdateSoggettoProspectResponse")
    RESULTMSG updateSoggettoProspect(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoProspect", targetNamespace = "http://poraservice/") final DATISOGGETTO p1, @WebParam(name = "consensi", targetNamespace = "http://poraservice/") final ArrayOfCONSENSO p2);
    
    @WebResult(name = "GetSituazioneDebitoriaExtResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetSituazioneDebitoriaExt", output = "http://poraservice/PORAPWEBService/GetSituazioneDebitoriaExtResponse")
    @RequestWrapper(localName = "GetSituazioneDebitoriaExt", targetNamespace = "http://poraservice/", className = "poraservice.GetSituazioneDebitoriaExt")
    @WebMethod(operationName = "GetSituazioneDebitoriaExt", action = "http://poraservice/PORAPWEBService/GetSituazioneDebitoriaExt")
    @ResponseWrapper(localName = "GetSituazioneDebitoriaExtResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetSituazioneDebitoriaExtResponse")
    RESULTMSG getSituazioneDebitoriaExt(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filterTbl", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "fornituraCOD", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "ruoloSoggettoCod", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "podpdrCod", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "dataIniEmis", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p7, @WebParam(name = "nonPagati", targetNamespace = "http://poraservice/") final Boolean p8, @WebParam(name = "ultimaFatt", targetNamespace = "http://poraservice/") final Boolean p9, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "dataScadenzaIni", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p12, @WebParam(name = "dataScadenzaFin", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p13);
    
    @WebResult(name = "SetAutoletturaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/SetAutolettura", output = "http://poraservice/PORAPWEBService/SetAutoletturaResponse")
    @RequestWrapper(localName = "SetAutolettura", targetNamespace = "http://poraservice/", className = "poraservice.SetAutolettura")
    @WebMethod(operationName = "SetAutolettura", action = "http://poraservice/PORAPWEBService/SetAutolettura")
    @ResponseWrapper(localName = "SetAutoletturaResponse", targetNamespace = "http://poraservice/", className = "poraservice.SetAutoletturaResponse")
    RESULTMSG setAutolettura(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "dataLettura", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p2, @WebParam(name = "valore", targetNamespace = "http://poraservice/") final BigDecimal p3, @WebParam(name = "forzaPeriodo", targetNamespace = "http://poraservice/") final Boolean p4, @WebParam(name = "forzaConsumo", targetNamespace = "http://poraservice/") final Boolean p5);
    
    @WebResult(name = "GetConsensiSocietaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetConsensiSocieta", output = "http://poraservice/PORAPWEBService/GetConsensiSocietaResponse")
    @RequestWrapper(localName = "GetConsensiSocieta", targetNamespace = "http://poraservice/", className = "poraservice.GetConsensiSocieta")
    @WebMethod(operationName = "GetConsensiSocieta", action = "http://poraservice/PORAPWEBService/GetConsensiSocieta")
    @ResponseWrapper(localName = "GetConsensiSocietaResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetConsensiSocietaResponse")
    RESULTMSG getConsensiSocieta(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "GetLetturaPrecedenteExtResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetLetturaPrecedenteExt", output = "http://poraservice/PORAPWEBService/GetLetturaPrecedenteExtResponse")
    @RequestWrapper(localName = "GetLetturaPrecedenteExt", targetNamespace = "http://poraservice/", className = "poraservice.GetLetturaPrecedenteExt")
    @WebMethod(operationName = "GetLetturaPrecedenteExt", action = "http://poraservice/PORAPWEBService/GetLetturaPrecedenteExt")
    @ResponseWrapper(localName = "GetLetturaPrecedenteExtResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetLetturaPrecedenteExtResponse")
    RESULTMSG getLetturaPrecedenteExt(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codicePDRPOR", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "fornituracod", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p8, @WebParam(name = "dataRiferimento", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p9);
    
    @WebResult(name = "GetEstrattoContoServizioResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetEstrattoContoServizio", output = "http://poraservice/PORAPWEBService/GetEstrattoContoServizioResponse")
    @RequestWrapper(localName = "GetEstrattoContoServizio", targetNamespace = "http://poraservice/", className = "poraservice.GetEstrattoContoServizio")
    @WebMethod(operationName = "GetEstrattoContoServizio", action = "http://poraservice/PORAPWEBService/GetEstrattoContoServizio")
    @ResponseWrapper(localName = "GetEstrattoContoServizioResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetEstrattoContoServizioResponse")
    RESULTMSG getEstrattoContoServizio(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filterTbl", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "ruoloSoggettoCod", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p9, @WebParam(name = "soggettoCodAmm", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p12, @WebParam(name = "codicePODPDR", targetNamespace = "http://poraservice/") final String p13);
    
    @WebResult(name = "GetUtentiNewsletterResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetUtentiNewsletter", output = "http://poraservice/PORAPWEBService/GetUtentiNewsletterResponse")
    @RequestWrapper(localName = "GetUtentiNewsletter", targetNamespace = "http://poraservice/", className = "poraservice.GetUtentiNewsletter")
    @WebMethod(operationName = "GetUtentiNewsletter", action = "http://poraservice/PORAPWEBService/GetUtentiNewsletter")
    @ResponseWrapper(localName = "GetUtentiNewsletterResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetUtentiNewsletterResponse")
    RESULTMSG getUtentiNewsletter(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1);
    
    @WebResult(name = "GetSottotipiResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetSottotipi", output = "http://poraservice/PORAPWEBService/GetSottotipiResponse")
    @RequestWrapper(localName = "GetSottotipi", targetNamespace = "http://poraservice/", className = "poraservice.GetSottotipi")
    @WebMethod(operationName = "GetSottotipi", action = "http://poraservice/PORAPWEBService/GetSottotipi")
    @ResponseWrapper(localName = "GetSottotipiResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetSottotipiResponse")
    RESULTMSG getSottotipi(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "tipoRichiesta", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "codLineaProdotto", targetNamespace = "http://poraservice/") final String p2);
    
    @WebResult(name = "GetComuniCatastoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetComuniCatasto", output = "http://poraservice/PORAPWEBService/GetComuniCatastoResponse")
    @RequestWrapper(localName = "GetComuniCatasto", targetNamespace = "http://poraservice/", className = "poraservice.GetComuniCatasto")
    @WebMethod(operationName = "GetComuniCatasto", action = "http://poraservice/PORAPWEBService/GetComuniCatasto")
    @ResponseWrapper(localName = "GetComuniCatastoResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetComuniCatastoResponse")
    RESULTMSG getComuniCatasto(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "criterioRicerca", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "DownloadSituazioneDebitoriaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DownloadSituazioneDebitoria", output = "http://poraservice/PORAPWEBService/DownloadSituazioneDebitoriaResponse")
    @RequestWrapper(localName = "DownloadSituazioneDebitoria", targetNamespace = "http://poraservice/", className = "poraservice.DownloadSituazioneDebitoria")
    @WebMethod(operationName = "DownloadSituazioneDebitoria", action = "http://poraservice/PORAPWEBService/DownloadSituazioneDebitoria")
    @ResponseWrapper(localName = "DownloadSituazioneDebitoriaResponse", targetNamespace = "http://poraservice/", className = "poraservice.DownloadSituazioneDebitoriaResponse")
    RESULTMSG downloadSituazioneDebitoria(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "fornituraCOD", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "ruoloSoggettoCod", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "podpdrCod", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "dataIniEmis", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p6, @WebParam(name = "nonPagati", targetNamespace = "http://poraservice/") final Boolean p7, @WebParam(name = "ultimaFatt", targetNamespace = "http://poraservice/") final Boolean p8, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "dataScadenzaIni", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p11, @WebParam(name = "dataScadenzaFin", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p12);
    
    @WebResult(name = "GetTOKENResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetTOKEN", output = "http://poraservice/PORAPWEBService/GetTOKENResponse")
    @RequestWrapper(localName = "GetTOKEN", targetNamespace = "http://poraservice/", className = "poraservice.GetTOKEN")
    @WebMethod(operationName = "GetTOKEN", action = "http://poraservice/PORAPWEBService/GetTOKEN")
    @ResponseWrapper(localName = "GetTOKENResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetTOKENResponse")
    RESULTMSG getTOKEN(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "GetComuniLocalitaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetComuniLocalita", output = "http://poraservice/PORAPWEBService/GetComuniLocalitaResponse")
    @RequestWrapper(localName = "GetComuniLocalita", targetNamespace = "http://poraservice/", className = "poraservice.GetComuniLocalita")
    @WebMethod(operationName = "GetComuniLocalita", action = "http://poraservice/PORAPWEBService/GetComuniLocalita")
    @ResponseWrapper(localName = "GetComuniLocalitaResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetComuniLocalitaResponse")
    RESULTMSG getComuniLocalita(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "criterioRicerca", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "DownloadCurvePrelievoOrarieEEExtResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoOrarieEEExt", output = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoOrarieEEExtResponse")
    @RequestWrapper(localName = "DownloadCurvePrelievoOrarieEEExt", targetNamespace = "http://poraservice/", className = "poraservice.DownloadCurvePrelievoOrarieEEExt")
    @WebMethod(operationName = "DownloadCurvePrelievoOrarieEEExt", action = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoOrarieEEExt")
    @ResponseWrapper(localName = "DownloadCurvePrelievoOrarieEEExtResponse", targetNamespace = "http://poraservice/", className = "poraservice.DownloadCurvePrelievoOrarieEEExtResponse")
    RESULTMSG downloadCurvePrelievoOrarieEEExt(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruolosoggCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "partitaIva", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "soggCodAmm", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "codicePDRPOD", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p12, @WebParam(name = "periodoIni", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p13, @WebParam(name = "periodoFin", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p14, @WebParam(name = "tipoFile", targetNamespace = "http://poraservice/") final TIPOFILE p15);
    
    @WebResult(name = "GetProvinceResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetProvince", output = "http://poraservice/PORAPWEBService/GetProvinceResponse")
    @RequestWrapper(localName = "GetProvince", targetNamespace = "http://poraservice/", className = "poraservice.GetProvince")
    @WebMethod(operationName = "GetProvince", action = "http://poraservice/PORAPWEBService/GetProvince")
    @ResponseWrapper(localName = "GetProvinceResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetProvinceResponse")
    RESULTMSG getProvince(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "nazioneCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "regioneCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "criterioRicerca", targetNamespace = "http://poraservice/") final String p3);
    
    @WebResult(name = "VerificaAttivazionePaperlessResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/VerificaAttivazionePaperless", output = "http://poraservice/PORAPWEBService/VerificaAttivazionePaperlessResponse")
    @RequestWrapper(localName = "VerificaAttivazionePaperless", targetNamespace = "http://poraservice/", className = "poraservice.VerificaAttivazionePaperless")
    @WebMethod(operationName = "VerificaAttivazionePaperless", action = "http://poraservice/PORAPWEBService/VerificaAttivazionePaperless")
    @ResponseWrapper(localName = "VerificaAttivazionePaperlessResponse", targetNamespace = "http://poraservice/", className = "poraservice.VerificaAttivazionePaperlessResponse")
    RESULTMSG verificaAttivazionePaperless(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "codFornitura", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "ElencoRichiesteExtResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/ElencoRichiesteExt", output = "http://poraservice/PORAPWEBService/ElencoRichiesteExtResponse")
    @RequestWrapper(localName = "ElencoRichiesteExt", targetNamespace = "http://poraservice/", className = "poraservice.ElencoRichiesteExt")
    @WebMethod(operationName = "ElencoRichiesteExt", action = "http://poraservice/PORAPWEBService/ElencoRichiesteExt")
    @ResponseWrapper(localName = "ElencoRichiesteExtResponse", targetNamespace = "http://poraservice/", className = "poraservice.ElencoRichiesteExtResponse")
    RESULTMSG elencoRichiesteExt(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p2, @WebParam(name = "filtroRichiesta", targetNamespace = "http://poraservice/") final FiltroRichiesta p3);
    
    @WebResult(name = "GetTotaliConsumoFatturatoPagatoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetTotaliConsumoFatturatoPagato", output = "http://poraservice/PORAPWEBService/GetTotaliConsumoFatturatoPagatoResponse")
    @RequestWrapper(localName = "GetTotaliConsumoFatturatoPagato", targetNamespace = "http://poraservice/", className = "poraservice.GetTotaliConsumoFatturatoPagato")
    @WebMethod(operationName = "GetTotaliConsumoFatturatoPagato", action = "http://poraservice/PORAPWEBService/GetTotaliConsumoFatturatoPagato")
    @ResponseWrapper(localName = "GetTotaliConsumoFatturatoPagatoResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetTotaliConsumoFatturatoPagatoResponse")
    RESULTMSG getTotaliConsumoFatturatoPagato(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filterTbl", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "annoRiferimento", targetNamespace = "http://poraservice/") final Integer p2, @WebParam(name = "soggettoCodAmm", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "ruoloSoggettoCod", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "codicePODPDR", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p9);
    
    @WebResult(name = "GetPlayerResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetPlayer", output = "http://poraservice/PORAPWEBService/GetPlayerResponse")
    @RequestWrapper(localName = "GetPlayer", targetNamespace = "http://poraservice/", className = "poraservice.GetPlayer")
    @WebMethod(operationName = "GetPlayer", action = "http://poraservice/PORAPWEBService/GetPlayer")
    @ResponseWrapper(localName = "GetPlayerResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetPlayerResponse")
    RESULTMSG getPlayer(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "funzioneCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "lineaProdottoCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p3);
    
    @WebResult(name = "ElencoRichiesteResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/ElencoRichieste", output = "http://poraservice/PORAPWEBService/ElencoRichiesteResponse")
    @RequestWrapper(localName = "ElencoRichieste", targetNamespace = "http://poraservice/", className = "poraservice.ElencoRichieste")
    @WebMethod(operationName = "ElencoRichieste", action = "http://poraservice/PORAPWEBService/ElencoRichieste")
    @ResponseWrapper(localName = "ElencoRichiesteResponse", targetNamespace = "http://poraservice/", className = "poraservice.ElencoRichiesteResponse")
    RESULTMSG elencoRichieste(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p2);
    
    @WebResult(name = "GetEmailProspectResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetEmailProspect", output = "http://poraservice/PORAPWEBService/GetEmailProspectResponse")
    @RequestWrapper(localName = "GetEmailProspect", targetNamespace = "http://poraservice/", className = "poraservice.GetEmailProspect")
    @WebMethod(operationName = "GetEmailProspect", action = "http://poraservice/PORAPWEBService/GetEmailProspect")
    @ResponseWrapper(localName = "GetEmailProspectResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetEmailProspectResponse")
    RESULTMSG getEmailProspect(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "GetEstrattoContoGeneraleResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetEstrattoContoGenerale", output = "http://poraservice/PORAPWEBService/GetEstrattoContoGeneraleResponse")
    @RequestWrapper(localName = "GetEstrattoContoGenerale", targetNamespace = "http://poraservice/", className = "poraservice.GetEstrattoContoGenerale")
    @WebMethod(operationName = "GetEstrattoContoGenerale", action = "http://poraservice/PORAPWEBService/GetEstrattoContoGenerale")
    @ResponseWrapper(localName = "GetEstrattoContoGeneraleResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetEstrattoContoGeneraleResponse")
    RESULTMSG getEstrattoContoGenerale(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filterTbl", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "ruoloSoggettoCod", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p9, @WebParam(name = "soggettoCodAmm", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p12, @WebParam(name = "codicePODPDR", targetNamespace = "http://poraservice/") final String p13);
    
    @WebResult(name = "GetLetturaPrecedenteResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetLetturaPrecedente", output = "http://poraservice/PORAPWEBService/GetLetturaPrecedenteResponse")
    @RequestWrapper(localName = "GetLetturaPrecedente", targetNamespace = "http://poraservice/", className = "poraservice.GetLetturaPrecedente")
    @WebMethod(operationName = "GetLetturaPrecedente", action = "http://poraservice/PORAPWEBService/GetLetturaPrecedente")
    @ResponseWrapper(localName = "GetLetturaPrecedenteResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetLetturaPrecedenteResponse")
    RESULTMSG getLetturaPrecedente(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "fornituracod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "dataRiferimento", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p3);
    
    @WebResult(name = "GetListaAlertResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetListaAlert", output = "http://poraservice/PORAPWEBService/GetListaAlertResponse")
    @RequestWrapper(localName = "GetListaAlert", targetNamespace = "http://poraservice/", className = "poraservice.GetListaAlert")
    @WebMethod(operationName = "GetListaAlert", action = "http://poraservice/PORAPWEBService/GetListaAlert")
    @ResponseWrapper(localName = "GetListaAlertResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetListaAlertResponse")
    RESULTMSG getListaAlert(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filterTbldateProssBoll", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "filterTblConsStim", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p2, @WebParam(name = "filterTbldocPagare", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p3, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p5);
    
    @WebResult(name = "GetConsumoStimatoProssimaBollettaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetConsumoStimatoProssimaBolletta", output = "http://poraservice/PORAPWEBService/GetConsumoStimatoProssimaBollettaResponse")
    @RequestWrapper(localName = "GetConsumoStimatoProssimaBolletta", targetNamespace = "http://poraservice/", className = "poraservice.GetConsumoStimatoProssimaBolletta")
    @WebMethod(operationName = "GetConsumoStimatoProssimaBolletta", action = "http://poraservice/PORAPWEBService/GetConsumoStimatoProssimaBolletta")
    @ResponseWrapper(localName = "GetConsumoStimatoProssimaBollettaResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetConsumoStimatoProssimaBollettaResponse")
    RESULTMSG getConsumoStimatoProssimaBolletta(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p3);
    
    @WebResult(name = "GetInfoMulticompanyResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetInfoMulticompany", output = "http://poraservice/PORAPWEBService/GetInfoMulticompanyResponse")
    @RequestWrapper(localName = "GetInfoMulticompany", targetNamespace = "http://poraservice/", className = "poraservice.GetInfoMulticompany")
    @WebMethod(operationName = "GetInfoMulticompany", action = "http://poraservice/PORAPWEBService/GetInfoMulticompany")
    @ResponseWrapper(localName = "GetInfoMulticompanyResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetInfoMulticompanyResponse")
    RESULTMSG getInfoMulticompany(@WebParam(name = "mcomp", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "SetNewsletterResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/SetNewsletter", output = "http://poraservice/PORAPWEBService/SetNewsletterResponse")
    @RequestWrapper(localName = "SetNewsletter", targetNamespace = "http://poraservice/", className = "poraservice.SetNewsletter")
    @WebMethod(operationName = "SetNewsletter", action = "http://poraservice/PORAPWEBService/SetNewsletter")
    @ResponseWrapper(localName = "SetNewsletterResponse", targetNamespace = "http://poraservice/", className = "poraservice.SetNewsletterResponse")
    RESULTMSG setNewsletter(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "adesioneNewsLetter", targetNamespace = "http://poraservice/") final Boolean p2);
    
    @WebResult(name = "AttivaPaperlessResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/AttivaPaperless", output = "http://poraservice/PORAPWEBService/AttivaPaperlessResponse")
    @RequestWrapper(localName = "AttivaPaperless", targetNamespace = "http://poraservice/", className = "poraservice.AttivaPaperless")
    @WebMethod(operationName = "AttivaPaperless", action = "http://poraservice/PORAPWEBService/AttivaPaperless")
    @ResponseWrapper(localName = "AttivaPaperlessResponse", targetNamespace = "http://poraservice/", className = "poraservice.AttivaPaperlessResponse")
    RESULTMSG attivaPaperless(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "codFornitura", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "email", targetNamespace = "http://poraservice/") final String p2);
    
    @WebResult(name = "GetElencoLingueResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetElencoLingue", output = "http://poraservice/PORAPWEBService/GetElencoLingueResponse")
    @RequestWrapper(localName = "GetElencoLingue", targetNamespace = "http://poraservice/", className = "poraservice.GetElencoLingue")
    @WebMethod(operationName = "GetElencoLingue", action = "http://poraservice/PORAPWEBService/GetElencoLingue")
    @ResponseWrapper(localName = "GetElencoLingueResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetElencoLingueResponse")
    RESULTMSG getElencoLingue(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "GetDettaglioServizioResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetDettaglioServizio", output = "http://poraservice/PORAPWEBService/GetDettaglioServizioResponse")
    @RequestWrapper(localName = "GetDettaglioServizio", targetNamespace = "http://poraservice/", className = "poraservice.GetDettaglioServizio")
    @WebMethod(operationName = "GetDettaglioServizio", action = "http://poraservice/PORAPWEBService/GetDettaglioServizio")
    @ResponseWrapper(localName = "GetDettaglioServizioResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetDettaglioServizioResponse")
    RESULTMSG getDettaglioServizio(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "fornituraCOD", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "GetServiziRaggrResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetServiziRaggr", output = "http://poraservice/PORAPWEBService/GetServiziRaggrResponse")
    @RequestWrapper(localName = "GetServiziRaggr", targetNamespace = "http://poraservice/", className = "poraservice.GetServiziRaggr")
    @WebMethod(operationName = "GetServiziRaggr", action = "http://poraservice/PORAPWEBService/GetServiziRaggr")
    @ResponseWrapper(localName = "GetServiziRaggrResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetServiziRaggrResponse")
    RESULTMSG getServiziRaggr(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filterTbl", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "filterStatiForn", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p2, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p3);
    
    @WebResult(name = "DownloadCurvePrelievoExtResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoExt", output = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoExtResponse")
    @RequestWrapper(localName = "DownloadCurvePrelievoExt", targetNamespace = "http://poraservice/", className = "poraservice.DownloadCurvePrelievoExt")
    @WebMethod(operationName = "DownloadCurvePrelievoExt", action = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoExt")
    @ResponseWrapper(localName = "DownloadCurvePrelievoExtResponse", targetNamespace = "http://poraservice/", className = "poraservice.DownloadCurvePrelievoExtResponse")
    RESULTMSG downloadCurvePrelievoExt(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruolosoggCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "partitaIva", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "soggCodAmm", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "codicePDRPOD", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p12, @WebParam(name = "periodoIni", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p13, @WebParam(name = "periodoFin", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p14, @WebParam(name = "tipoFile", targetNamespace = "http://poraservice/") final TIPOFILE p15);
    
    @WebResult(name = "GetVieResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetVie", output = "http://poraservice/PORAPWEBService/GetVieResponse")
    @RequestWrapper(localName = "GetVie", targetNamespace = "http://poraservice/", className = "poraservice.GetVie")
    @WebMethod(operationName = "GetVie", action = "http://poraservice/PORAPWEBService/GetVie")
    @ResponseWrapper(localName = "GetVieResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetVieResponse")
    RESULTMSG getVie(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "provinciaCOD", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "comuneCOD", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "localitaCOD", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "criterioRicerca", targetNamespace = "http://poraservice/") final String p4);
    
    @WebResult(name = "GetCurvePrelievoExtResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetCurvePrelievoExt", output = "http://poraservice/PORAPWEBService/GetCurvePrelievoExtResponse")
    @RequestWrapper(localName = "GetCurvePrelievoExt", targetNamespace = "http://poraservice/", className = "poraservice.GetCurvePrelievoExt")
    @WebMethod(operationName = "GetCurvePrelievoExt", action = "http://poraservice/PORAPWEBService/GetCurvePrelievoExt")
    @ResponseWrapper(localName = "GetCurvePrelievoExtResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetCurvePrelievoExtResponse")
    RESULTMSG getCurvePrelievoExt(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruolosoggCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "partitaIva", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "soggCodAmm", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "codicePDRPOD", targetNamespace = "http://poraservice/") final String p8, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p12, @WebParam(name = "periodoIni", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p13, @WebParam(name = "periodoFin", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p14, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p15);
    
    @WebResult(name = "SetAutoletturaEleResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/SetAutoletturaEle", output = "http://poraservice/PORAPWEBService/SetAutoletturaEleResponse")
    @RequestWrapper(localName = "SetAutoletturaEle", targetNamespace = "http://poraservice/", className = "poraservice.SetAutoletturaEle")
    @WebMethod(operationName = "SetAutoletturaEle", action = "http://poraservice/PORAPWEBService/SetAutoletturaEle")
    @ResponseWrapper(localName = "SetAutoletturaEleResponse", targetNamespace = "http://poraservice/", className = "poraservice.SetAutoletturaEleResponse")
    RESULTMSG setAutoletturaEle(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "dataLettura", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p2, @WebParam(name = "valFascia1", targetNamespace = "http://poraservice/") final BigDecimal p3, @WebParam(name = "valFascia2", targetNamespace = "http://poraservice/") final BigDecimal p4, @WebParam(name = "valFascia3", targetNamespace = "http://poraservice/") final BigDecimal p5, @WebParam(name = "forzaPeriodo", targetNamespace = "http://poraservice/") final Boolean p6, @WebParam(name = "forzaConsumo", targetNamespace = "http://poraservice/") final Boolean p7);
    
    @WebResult(name = "GetTipiCiviciResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetTipiCivici", output = "http://poraservice/PORAPWEBService/GetTipiCiviciResponse")
    @RequestWrapper(localName = "GetTipiCivici", targetNamespace = "http://poraservice/", className = "poraservice.GetTipiCivici")
    @WebMethod(operationName = "GetTipiCivici", action = "http://poraservice/PORAPWEBService/GetTipiCivici")
    @ResponseWrapper(localName = "GetTipiCiviciResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetTipiCiviciResponse")
    RESULTMSG getTipiCivici(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "criterioRicerca", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "GetStatiFornResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetStatiForn", output = "http://poraservice/PORAPWEBService/GetStatiFornResponse")
    @RequestWrapper(localName = "GetStatiForn", targetNamespace = "http://poraservice/", className = "poraservice.GetStatiForn")
    @WebMethod(operationName = "GetStatiForn", action = "http://poraservice/PORAPWEBService/GetStatiForn")
    @ResponseWrapper(localName = "GetStatiFornResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetStatiFornResponse")
    RESULTMSG getStatiForn(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCOD", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruolosoggCOD", targetNamespace = "http://poraservice/") final String p2);
    
    @WebResult(name = "GestioneSoggettoProspectResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GestioneSoggettoProspect", output = "http://poraservice/PORAPWEBService/GestioneSoggettoProspectResponse")
    @RequestWrapper(localName = "GestioneSoggettoProspect", targetNamespace = "http://poraservice/", className = "poraservice.GestioneSoggettoProspect")
    @WebMethod(operationName = "GestioneSoggettoProspect", action = "http://poraservice/PORAPWEBService/GestioneSoggettoProspect")
    @ResponseWrapper(localName = "GestioneSoggettoProspectResponse", targetNamespace = "http://poraservice/", className = "poraservice.GestioneSoggettoProspectResponse")
    RESULTMSG gestioneSoggettoProspect(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoProspect", targetNamespace = "http://poraservice/") final DATISOGGETTOEXT p1, @WebParam(name = "consensi", targetNamespace = "http://poraservice/") final ArrayOfCONSENSO p2);
    
    @WebResult(name = "GetPDFResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetPDF", output = "http://poraservice/PORAPWEBService/GetPDFResponse")
    @RequestWrapper(localName = "GetPDF", targetNamespace = "http://poraservice/", className = "poraservice.GetPDF")
    @WebMethod(operationName = "GetPDF", action = "http://poraservice/PORAPWEBService/GetPDF")
    @ResponseWrapper(localName = "GetPDFResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetPDFResponse")
    RESULTMSG getPDF(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "documentoID", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "GetDataProssimaBollettaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetDataProssimaBolletta", output = "http://poraservice/PORAPWEBService/GetDataProssimaBollettaResponse")
    @RequestWrapper(localName = "GetDataProssimaBolletta", targetNamespace = "http://poraservice/", className = "poraservice.GetDataProssimaBolletta")
    @WebMethod(operationName = "GetDataProssimaBolletta", action = "http://poraservice/PORAPWEBService/GetDataProssimaBolletta")
    @ResponseWrapper(localName = "GetDataProssimaBollettaResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetDataProssimaBollettaResponse")
    RESULTMSG getDataProssimaBolletta(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p3);
    
    @WebResult(name = "SetConsensiSoggettoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/SetConsensiSoggetto", output = "http://poraservice/PORAPWEBService/SetConsensiSoggettoResponse")
    @RequestWrapper(localName = "SetConsensiSoggetto", targetNamespace = "http://poraservice/", className = "poraservice.SetConsensiSoggetto")
    @WebMethod(operationName = "SetConsensiSoggetto", action = "http://poraservice/PORAPWEBService/SetConsensiSoggetto")
    @ResponseWrapper(localName = "SetConsensiSoggettoResponse", targetNamespace = "http://poraservice/", className = "poraservice.SetConsensiSoggettoResponse")
    RESULTMSG setConsensiSoggetto(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "consensi", targetNamespace = "http://poraservice/") final ArrayOfCONSENSO p2);
    
    @WebResult(name = "InserimentoRichiestaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/InserimentoRichiesta", output = "http://poraservice/PORAPWEBService/InserimentoRichiestaResponse")
    @RequestWrapper(localName = "InserimentoRichiesta", targetNamespace = "http://poraservice/", className = "poraservice.InserimentoRichiesta")
    @WebMethod(operationName = "InserimentoRichiesta", action = "http://poraservice/PORAPWEBService/InserimentoRichiesta")
    @ResponseWrapper(localName = "InserimentoRichiestaResponse", targetNamespace = "http://poraservice/", className = "poraservice.InserimentoRichiestaResponse")
    RESULTMSG inserimentoRichiesta(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "datiRichiesta", targetNamespace = "http://poraservice/") final DATIINSERIMENTORICHIESTA p1);
    
    @WebResult(name = "DownloadCurvePrelievoOrarieEEResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoOrarieEE", output = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoOrarieEEResponse")
    @RequestWrapper(localName = "DownloadCurvePrelievoOrarieEE", targetNamespace = "http://poraservice/", className = "poraservice.DownloadCurvePrelievoOrarieEE")
    @WebMethod(operationName = "DownloadCurvePrelievoOrarieEE", action = "http://poraservice/PORAPWEBService/DownloadCurvePrelievoOrarieEE")
    @ResponseWrapper(localName = "DownloadCurvePrelievoOrarieEEResponse", targetNamespace = "http://poraservice/", className = "poraservice.DownloadCurvePrelievoOrarieEEResponse")
    RESULTMSG downloadCurvePrelievoOrarieEE(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "elencoForniture", targetNamespace = "http://poraservice/") final ArrayOfFORNITURACP p1, @WebParam(name = "periodoIni", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p2, @WebParam(name = "periodoFin", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p3, @WebParam(name = "tipoFile", targetNamespace = "http://poraservice/") final TIPOFILE p4);
    
    @WebResult(name = "GetLettureConsumiResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetLettureConsumi", output = "http://poraservice/PORAPWEBService/GetLettureConsumiResponse")
    @RequestWrapper(localName = "GetLettureConsumi", targetNamespace = "http://poraservice/", className = "poraservice.GetLettureConsumi")
    @WebMethod(operationName = "GetLettureConsumi", action = "http://poraservice/PORAPWEBService/GetLettureConsumi")
    @ResponseWrapper(localName = "GetLettureConsumiResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetLettureConsumiResponse")
    RESULTMSG getLettureConsumi(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "periodo", targetNamespace = "http://poraservice/") final PERIODO p3);
    
    @WebResult(name = "EliminaAccountResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/EliminaAccount", output = "http://poraservice/PORAPWEBService/EliminaAccountResponse")
    @RequestWrapper(localName = "EliminaAccount", targetNamespace = "http://poraservice/", className = "poraservice.EliminaAccount")
    @WebMethod(operationName = "EliminaAccount", action = "http://poraservice/PORAPWEBService/EliminaAccount")
    @ResponseWrapper(localName = "EliminaAccountResponse", targetNamespace = "http://poraservice/", className = "poraservice.EliminaAccountResponse")
    RESULTMSG eliminaAccount(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "account", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "password", targetNamespace = "http://poraservice/") final String p2);
    
    @WebResult(name = "GetNazioniResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetNazioni", output = "http://poraservice/PORAPWEBService/GetNazioniResponse")
    @RequestWrapper(localName = "GetNazioni", targetNamespace = "http://poraservice/", className = "poraservice.GetNazioni")
    @WebMethod(operationName = "GetNazioni", action = "http://poraservice/PORAPWEBService/GetNazioni")
    @ResponseWrapper(localName = "GetNazioniResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetNazioniResponse")
    RESULTMSG getNazioni(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "criterioRicerca", targetNamespace = "http://poraservice/") final String p1);
    
    @WebResult(name = "GetProdottiResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetProdotti", output = "http://poraservice/PORAPWEBService/GetProdottiResponse")
    @RequestWrapper(localName = "GetProdotti", targetNamespace = "http://poraservice/", className = "poraservice.GetProdotti")
    @WebMethod(operationName = "GetProdotti", action = "http://poraservice/PORAPWEBService/GetProdotti")
    @ResponseWrapper(localName = "GetProdottiResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetProdottiResponse")
    RESULTMSG getProdotti(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filtriProdotto", targetNamespace = "http://poraservice/") final FiltriProdotto p1);
    
    @WebResult(name = "InserimentoRichiestaDDResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/InserimentoRichiestaDD", output = "http://poraservice/PORAPWEBService/InserimentoRichiestaDDResponse")
    @RequestWrapper(localName = "InserimentoRichiestaDD", targetNamespace = "http://poraservice/", className = "poraservice.InserimentoRichiestaDD")
    @WebMethod(operationName = "InserimentoRichiestaDD", action = "http://poraservice/PORAPWEBService/InserimentoRichiestaDD")
    @ResponseWrapper(localName = "InserimentoRichiestaDDResponse", targetNamespace = "http://poraservice/", className = "poraservice.InserimentoRichiestaDDResponse")
    RESULTMSG inserimentoRichiestaDD(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "datiRichiesta", targetNamespace = "http://poraservice/") final DATIINSERIMENTORICHIESTA p1);
    
    @WebResult(name = "GetPeriodoInserimentoAutoletturaFlussoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetPeriodoInserimentoAutoletturaFlusso", output = "http://poraservice/PORAPWEBService/GetPeriodoInserimentoAutoletturaFlussoResponse")
    @RequestWrapper(localName = "GetPeriodoInserimentoAutoletturaFlusso", targetNamespace = "http://poraservice/", className = "poraservice.GetPeriodoInserimentoAutoletturaFlusso")
    @WebMethod(operationName = "GetPeriodoInserimentoAutoletturaFlusso", action = "http://poraservice/PORAPWEBService/GetPeriodoInserimentoAutoletturaFlusso")
    @ResponseWrapper(localName = "GetPeriodoInserimentoAutoletturaFlussoResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetPeriodoInserimentoAutoletturaFlussoResponse")
    RESULTMSG getPeriodoInserimentoAutoletturaFlusso(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "dataLettura", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p2, @WebParam(name = "tipoAutoLettura", targetNamespace = "http://poraservice/") final TIPOAUTOLETTURA p3);
    
    @WebResult(name = "RecuperaCredenzialiAccountResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/RecuperaCredenzialiAccount", output = "http://poraservice/PORAPWEBService/RecuperaCredenzialiAccountResponse")
    @RequestWrapper(localName = "RecuperaCredenzialiAccount", targetNamespace = "http://poraservice/", className = "poraservice.RecuperaCredenzialiAccount")
    @WebMethod(operationName = "RecuperaCredenzialiAccount", action = "http://poraservice/PORAPWEBService/RecuperaCredenzialiAccount")
    @ResponseWrapper(localName = "RecuperaCredenzialiAccountResponse", targetNamespace = "http://poraservice/", className = "poraservice.RecuperaCredenzialiAccountResponse")
    RESULTMSG recuperaCredenzialiAccount(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "rec_password", targetNamespace = "http://poraservice/") final Boolean p1, @WebParam(name = "soggetto", targetNamespace = "http://poraservice/") final DATISOGGETTO p2);
    
    @WebResult(name = "ElencoAllegatiResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/ElencoAllegati", output = "http://poraservice/PORAPWEBService/ElencoAllegatiResponse")
    @RequestWrapper(localName = "ElencoAllegati", targetNamespace = "http://poraservice/", className = "poraservice.ElencoAllegati")
    @WebMethod(operationName = "ElencoAllegati", action = "http://poraservice/PORAPWEBService/ElencoAllegati")
    @ResponseWrapper(localName = "ElencoAllegatiResponse", targetNamespace = "http://poraservice/", className = "poraservice.ElencoAllegatiResponse")
    RESULTMSG elencoAllegati(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filtro", targetNamespace = "http://poraservice/") final ALLEGATORICHIESTA p1);
    
    @WebResult(name = "GetPeriodoInserimentoAutoletturaResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetPeriodoInserimentoAutolettura", output = "http://poraservice/PORAPWEBService/GetPeriodoInserimentoAutoletturaResponse")
    @RequestWrapper(localName = "GetPeriodoInserimentoAutolettura", targetNamespace = "http://poraservice/", className = "poraservice.GetPeriodoInserimentoAutolettura")
    @WebMethod(operationName = "GetPeriodoInserimentoAutolettura", action = "http://poraservice/PORAPWEBService/GetPeriodoInserimentoAutolettura")
    @ResponseWrapper(localName = "GetPeriodoInserimentoAutoletturaResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetPeriodoInserimentoAutoletturaResponse")
    RESULTMSG getPeriodoInserimentoAutolettura(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "dataLettura", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p2);
    
    @WebResult(name = "DownloadEstrattoContoServizioResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DownloadEstrattoContoServizio", output = "http://poraservice/PORAPWEBService/DownloadEstrattoContoServizioResponse")
    @RequestWrapper(localName = "DownloadEstrattoContoServizio", targetNamespace = "http://poraservice/", className = "poraservice.DownloadEstrattoContoServizio")
    @WebMethod(operationName = "DownloadEstrattoContoServizio", action = "http://poraservice/PORAPWEBService/DownloadEstrattoContoServizio")
    @ResponseWrapper(localName = "DownloadEstrattoContoServizioResponse", targetNamespace = "http://poraservice/", className = "poraservice.DownloadEstrattoContoServizioResponse")
    RESULTMSG downloadEstrattoContoServizio(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruoloSoggettoCod", targetNamespace = "http://poraservice/") final String p2, @WebParam(name = "nominativo", targetNamespace = "http://poraservice/") final String p3, @WebParam(name = "codiceFiscale", targetNamespace = "http://poraservice/") final String p4, @WebParam(name = "fornituraCod", targetNamespace = "http://poraservice/") final String p5, @WebParam(name = "contrattoNum", targetNamespace = "http://poraservice/") final String p6, @WebParam(name = "prodottoCod", targetNamespace = "http://poraservice/") final String p7, @WebParam(name = "elencoStati", targetNamespace = "http://poraservice/") final ArrayOfSTATOFORNITURA p8, @WebParam(name = "soggettoCodAmm", targetNamespace = "http://poraservice/") final String p9, @WebParam(name = "partitaIVA", targetNamespace = "http://poraservice/") final String p10, @WebParam(name = "tipoServizioCod", targetNamespace = "http://poraservice/") final String p11, @WebParam(name = "codicePODPDR", targetNamespace = "http://poraservice/") final String p12);
    
    @WebResult(name = "GetListaAttributiDynResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetListaAttributiDyn", output = "http://poraservice/PORAPWEBService/GetListaAttributiDynResponse")
    @RequestWrapper(localName = "GetListaAttributiDyn", targetNamespace = "http://poraservice/", className = "poraservice.GetListaAttributiDyn")
    @WebMethod(operationName = "GetListaAttributiDyn", action = "http://poraservice/PORAPWEBService/GetListaAttributiDyn")
    @ResponseWrapper(localName = "GetListaAttributiDynResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetListaAttributiDynResponse")
    RESULTMSG getListaAttributiDyn(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "GetCAPDaComuneResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetCAPDaComune", output = "http://poraservice/PORAPWEBService/GetCAPDaComuneResponse")
    @RequestWrapper(localName = "GetCAPDaComune", targetNamespace = "http://poraservice/", className = "poraservice.GetCAPDaComune")
    @WebMethod(operationName = "GetCAPDaComune", action = "http://poraservice/PORAPWEBService/GetCAPDaComune")
    @ResponseWrapper(localName = "GetCAPDaComuneResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetCAPDaComuneResponse")
    RESULTMSG getCAPDaComune(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "comuneCOD", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "localitaCOD", targetNamespace = "http://poraservice/") final String p2);
    
    @WebResult(name = "GetCurvePrelievoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetCurvePrelievo", output = "http://poraservice/PORAPWEBService/GetCurvePrelievoResponse")
    @RequestWrapper(localName = "GetCurvePrelievo", targetNamespace = "http://poraservice/", className = "poraservice.GetCurvePrelievo")
    @WebMethod(operationName = "GetCurvePrelievo", action = "http://poraservice/PORAPWEBService/GetCurvePrelievo")
    @ResponseWrapper(localName = "GetCurvePrelievoResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetCurvePrelievoResponse")
    RESULTMSG getCurvePrelievo(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "elencoForniture", targetNamespace = "http://poraservice/") final ArrayOfFORNITURACP p1, @WebParam(name = "periodoIni", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p2, @WebParam(name = "periodoFin", targetNamespace = "http://poraservice/") final XMLGregorianCalendar p3, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p4);
    
    @WebResult(name = "DownloadAllegatoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/DownloadAllegato", output = "http://poraservice/PORAPWEBService/DownloadAllegatoResponse")
    @RequestWrapper(localName = "DownloadAllegato", targetNamespace = "http://poraservice/", className = "poraservice.DownloadAllegato")
    @WebMethod(operationName = "DownloadAllegato", action = "http://poraservice/PORAPWEBService/DownloadAllegato")
    @ResponseWrapper(localName = "DownloadAllegatoResponse", targetNamespace = "http://poraservice/", className = "poraservice.DownloadAllegatoResponse")
    RESULTMSG downloadAllegato(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filtro", targetNamespace = "http://poraservice/") final ALLEGATORICHIESTA p1);
    
    @WebResult(name = "InserimentoSoggettoProspectResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/InserimentoSoggettoProspect", output = "http://poraservice/PORAPWEBService/InserimentoSoggettoProspectResponse")
    @RequestWrapper(localName = "InserimentoSoggettoProspect", targetNamespace = "http://poraservice/", className = "poraservice.InserimentoSoggettoProspect")
    @WebMethod(operationName = "InserimentoSoggettoProspect", action = "http://poraservice/PORAPWEBService/InserimentoSoggettoProspect")
    @ResponseWrapper(localName = "InserimentoSoggettoProspectResponse", targetNamespace = "http://poraservice/", className = "poraservice.InserimentoSoggettoProspectResponse")
    RESULTMSG inserimentoSoggettoProspect(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoProspect", targetNamespace = "http://poraservice/") final DATISOGGETTO p1, @WebParam(name = "consensi", targetNamespace = "http://poraservice/") final ArrayOfCONSENSO p2);
    
    @WebResult(name = "GetUtentiNewsletterExtResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetUtentiNewsletterExt", output = "http://poraservice/PORAPWEBService/GetUtentiNewsletterExtResponse")
    @RequestWrapper(localName = "GetUtentiNewsletterExt", targetNamespace = "http://poraservice/", className = "poraservice.GetUtentiNewsletterExt")
    @WebMethod(operationName = "GetUtentiNewsletterExt", action = "http://poraservice/PORAPWEBService/GetUtentiNewsletterExt")
    @ResponseWrapper(localName = "GetUtentiNewsletterExtResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetUtentiNewsletterExtResponse")
    RESULTMSG getUtentiNewsletterExt(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "filter", targetNamespace = "http://poraservice/") final PWEBTABLEFILTER p1, @WebParam(name = "filtroAttrDyn", targetNamespace = "http://poraservice/") final String p2);
    
    @WebResult(name = "GetRuoliResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetRuoli", output = "http://poraservice/PORAPWEBService/GetRuoliResponse")
    @RequestWrapper(localName = "GetRuoli", targetNamespace = "http://poraservice/", className = "poraservice.GetRuoli")
    @WebMethod(operationName = "GetRuoli", action = "http://poraservice/PORAPWEBService/GetRuoli")
    @ResponseWrapper(localName = "GetRuoliResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetRuoliResponse")
    RESULTMSG getRuoli(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0);
    
    @WebResult(name = "GetGerarchiaSoggettoResult", targetNamespace = "http://poraservice/")
    @Action(input = "http://poraservice/PORAPWEBService/GetGerarchiaSoggetto", output = "http://poraservice/PORAPWEBService/GetGerarchiaSoggettoResponse")
    @RequestWrapper(localName = "GetGerarchiaSoggetto", targetNamespace = "http://poraservice/", className = "poraservice.GetGerarchiaSoggetto")
    @WebMethod(operationName = "GetGerarchiaSoggetto", action = "http://poraservice/PORAPWEBService/GetGerarchiaSoggetto")
    @ResponseWrapper(localName = "GetGerarchiaSoggettoResponse", targetNamespace = "http://poraservice/", className = "poraservice.GetGerarchiaSoggettoResponse")
    RESULTMSG getGerarchiaSoggetto(@WebParam(name = "multicompany", targetNamespace = "http://poraservice/") final INFOMULTICOMPANY p0, @WebParam(name = "soggettoCod", targetNamespace = "http://poraservice/") final String p1, @WebParam(name = "ruolo", targetNamespace = "http://poraservice/") final String p2);
}
