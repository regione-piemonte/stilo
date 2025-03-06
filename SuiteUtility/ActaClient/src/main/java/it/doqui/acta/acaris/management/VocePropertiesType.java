
package it.doqui.acta.acaris.management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per VocePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VocePropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}FolderPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codice" type="{archive.acaris.acta.doqui.it}CodiceVoceType"/&gt;
 *         &lt;element name="descrizione" type="{archive.acaris.acta.doqui.it}DescrizioneType"/&gt;
 *         &lt;element name="stato" type="{archive.acaris.acta.doqui.it}enumVoceStatoType"/&gt;
 *         &lt;element name="indiceDiClassificazioneEsteso" type="{archive.acaris.acta.doqui.it}IndiceClassificazioneEstesaXMLType"/&gt;
 *         &lt;element name="descrBreve" type="{archive.acaris.acta.doqui.it}DescrBreveType"/&gt;
 *         &lt;element name="dataCreazione" type="{archive.acaris.acta.doqui.it}DataCreazioneType"/&gt;
 *         &lt;element name="dataInizio" type="{archive.acaris.acta.doqui.it}DataInizioType"/&gt;
 *         &lt;element name="dataFine" type="{archive.acaris.acta.doqui.it}DataFineType"/&gt;
 *         &lt;element name="presenzaFascStand" type="{archive.acaris.acta.doqui.it}PresenzaFascStandType"/&gt;
 *         &lt;element name="presenzaFascRealeAnnualeNV" type="{archive.acaris.acta.doqui.it}PresenzaFascRealeAnnualeNVType"/&gt;
 *         &lt;element name="presenzaFascRealeContinuoNV" type="{archive.acaris.acta.doqui.it}PresenzaFascRealeContinuoNVType"/&gt;
 *         &lt;element name="presenzaFascRealeLegislaturaNV" type="{archive.acaris.acta.doqui.it}PresenzaFascRealeLegislaturaNVType"/&gt;
 *         &lt;element name="presenzaFascRealeEreditatoNV" type="{archive.acaris.acta.doqui.it}PresenzaFascRealeEreditatoNVType"/&gt;
 *         &lt;element name="presenzaFascRealeLiberoNV" type="{archive.acaris.acta.doqui.it}PresenzaFascRealeLiberoNVType"/&gt;
 *         &lt;element name="presenzaFascTemp" type="{archive.acaris.acta.doqui.it}PresenzaFascTempType"/&gt;
 *         &lt;element name="presenzaSerieDoc" type="{archive.acaris.acta.doqui.it}PresenzaSerieDocType"/&gt;
 *         &lt;element name="presenzaSerieFasc" type="{archive.acaris.acta.doqui.it}PresenzaSerieFascType"/&gt;
 *         &lt;element name="presenzaSerieDoss" type="{archive.acaris.acta.doqui.it}PresenzaSerieDossType"/&gt;
 *         &lt;element name="insertSottoVociGestCont" type="{archive.acaris.acta.doqui.it}InsertSottoVociGestContType"/&gt;
 *         &lt;element name="creataGestCont" type="{archive.acaris.acta.doqui.it}CreataGestContType"/&gt;
 *         &lt;element name="paroleChiave" type="{archive.acaris.acta.doqui.it}ParoleChiaveType"/&gt;
 *         &lt;element name="dataUltimaModifica" type="{archive.acaris.acta.doqui.it}DataUltimaModificaType"/&gt;
 *         &lt;element name="conservazioneCorrente" type="{archive.acaris.acta.doqui.it}ConservazioneCorrenteType"/&gt;
 *         &lt;element name="conservazioneGenerale" type="{archive.acaris.acta.doqui.it}ConservazioneGeneraleType"/&gt;
 *         &lt;element name="dataBloccoPassaggioInDeposito" type="{archive.acaris.acta.doqui.it}DataBloccoPassaggioInDepositoType"/&gt;
 *         &lt;element name="dataSbloccoPassaggioInDeposito" type="{archive.acaris.acta.doqui.it}DataSbloccoPassaggioInDepositoType"/&gt;
 *         &lt;element name="dataCancellazione" type="{archive.acaris.acta.doqui.it}DataCancellazioneType"/&gt;
 *         &lt;element name="dataEsportazione" type="{archive.acaris.acta.doqui.it}DataEsportazioneType"/&gt;
 *         &lt;element name="idFascicoloStandardList" type="{archive.acaris.acta.doqui.it}IdFascicoloStandardType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="idProvvedimentoAutorizzatList" type="{common.acaris.acta.doqui.it}IdProvvedimentoAutorizzatType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="idAnnotazioniList" type="{common.acaris.acta.doqui.it}IdAnnotazioniType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="idTipoClasse" type="{archive.acaris.acta.doqui.it}IdTipoClasseType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VocePropertiesType", propOrder = {
    "codice",
    "descrizione",
    "stato",
    "indiceDiClassificazioneEsteso",
    "descrBreve",
    "dataCreazione",
    "dataInizio",
    "dataFine",
    "presenzaFascStand",
    "presenzaFascRealeAnnualeNV",
    "presenzaFascRealeContinuoNV",
    "presenzaFascRealeLegislaturaNV",
    "presenzaFascRealeEreditatoNV",
    "presenzaFascRealeLiberoNV",
    "presenzaFascTemp",
    "presenzaSerieDoc",
    "presenzaSerieFasc",
    "presenzaSerieDoss",
    "insertSottoVociGestCont",
    "creataGestCont",
    "paroleChiave",
    "dataUltimaModifica",
    "conservazioneCorrente",
    "conservazioneGenerale",
    "dataBloccoPassaggioInDeposito",
    "dataSbloccoPassaggioInDeposito",
    "dataCancellazione",
    "dataEsportazione",
    "idFascicoloStandardList",
    "idProvvedimentoAutorizzatList",
    "idAnnotazioniList",
    "idTipoClasse"
})
public class VocePropertiesType
    extends FolderPropertiesType
{

    protected int codice;
    @XmlElement(required = true)
    protected String descrizione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumVoceStatoType stato;
    @XmlElement(required = true)
    protected String indiceDiClassificazioneEsteso;
    @XmlElement(required = true)
    protected String descrBreve;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataCreazione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizio;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFine;
    protected boolean presenzaFascStand;
    protected boolean presenzaFascRealeAnnualeNV;
    protected boolean presenzaFascRealeContinuoNV;
    protected boolean presenzaFascRealeLegislaturaNV;
    protected boolean presenzaFascRealeEreditatoNV;
    protected boolean presenzaFascRealeLiberoNV;
    protected boolean presenzaFascTemp;
    protected boolean presenzaSerieDoc;
    protected boolean presenzaSerieFasc;
    protected boolean presenzaSerieDoss;
    protected boolean insertSottoVociGestCont;
    protected boolean creataGestCont;
    @XmlElement(required = true)
    protected String paroleChiave;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataUltimaModifica;
    protected int conservazioneCorrente;
    protected int conservazioneGenerale;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataBloccoPassaggioInDeposito;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataSbloccoPassaggioInDeposito;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataCancellazione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEsportazione;
    protected List<IdFascicoloStandardType> idFascicoloStandardList;
    protected List<IdProvvedimentoAutorizzatType> idProvvedimentoAutorizzatList;
    protected List<IdAnnotazioniType> idAnnotazioniList;
    protected int idTipoClasse;

    /**
     * Recupera il valore della propriet� codice.
     * 
     */
    public int getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della propriet� codice.
     * 
     */
    public void setCodice(int value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della propriet� descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della propriet� descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della propriet� stato.
     * 
     * @return
     *     possible object is
     *     {@link EnumVoceStatoType }
     *     
     */
    public EnumVoceStatoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della propriet� stato.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumVoceStatoType }
     *     
     */
    public void setStato(EnumVoceStatoType value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della propriet� indiceDiClassificazioneEsteso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndiceDiClassificazioneEsteso() {
        return indiceDiClassificazioneEsteso;
    }

    /**
     * Imposta il valore della propriet� indiceDiClassificazioneEsteso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndiceDiClassificazioneEsteso(String value) {
        this.indiceDiClassificazioneEsteso = value;
    }

    /**
     * Recupera il valore della propriet� descrBreve.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrBreve() {
        return descrBreve;
    }

    /**
     * Imposta il valore della propriet� descrBreve.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrBreve(String value) {
        this.descrBreve = value;
    }

    /**
     * Recupera il valore della propriet� dataCreazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCreazione() {
        return dataCreazione;
    }

    /**
     * Imposta il valore della propriet� dataCreazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCreazione(XMLGregorianCalendar value) {
        this.dataCreazione = value;
    }

    /**
     * Recupera il valore della propriet� dataInizio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta il valore della propriet� dataInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizio(XMLGregorianCalendar value) {
        this.dataInizio = value;
    }

    /**
     * Recupera il valore della propriet� dataFine.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Imposta il valore della propriet� dataFine.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Recupera il valore della propriet� presenzaFascStand.
     * 
     */
    public boolean isPresenzaFascStand() {
        return presenzaFascStand;
    }

    /**
     * Imposta il valore della propriet� presenzaFascStand.
     * 
     */
    public void setPresenzaFascStand(boolean value) {
        this.presenzaFascStand = value;
    }

    /**
     * Recupera il valore della propriet� presenzaFascRealeAnnualeNV.
     * 
     */
    public boolean isPresenzaFascRealeAnnualeNV() {
        return presenzaFascRealeAnnualeNV;
    }

    /**
     * Imposta il valore della propriet� presenzaFascRealeAnnualeNV.
     * 
     */
    public void setPresenzaFascRealeAnnualeNV(boolean value) {
        this.presenzaFascRealeAnnualeNV = value;
    }

    /**
     * Recupera il valore della propriet� presenzaFascRealeContinuoNV.
     * 
     */
    public boolean isPresenzaFascRealeContinuoNV() {
        return presenzaFascRealeContinuoNV;
    }

    /**
     * Imposta il valore della propriet� presenzaFascRealeContinuoNV.
     * 
     */
    public void setPresenzaFascRealeContinuoNV(boolean value) {
        this.presenzaFascRealeContinuoNV = value;
    }

    /**
     * Recupera il valore della propriet� presenzaFascRealeLegislaturaNV.
     * 
     */
    public boolean isPresenzaFascRealeLegislaturaNV() {
        return presenzaFascRealeLegislaturaNV;
    }

    /**
     * Imposta il valore della propriet� presenzaFascRealeLegislaturaNV.
     * 
     */
    public void setPresenzaFascRealeLegislaturaNV(boolean value) {
        this.presenzaFascRealeLegislaturaNV = value;
    }

    /**
     * Recupera il valore della propriet� presenzaFascRealeEreditatoNV.
     * 
     */
    public boolean isPresenzaFascRealeEreditatoNV() {
        return presenzaFascRealeEreditatoNV;
    }

    /**
     * Imposta il valore della propriet� presenzaFascRealeEreditatoNV.
     * 
     */
    public void setPresenzaFascRealeEreditatoNV(boolean value) {
        this.presenzaFascRealeEreditatoNV = value;
    }

    /**
     * Recupera il valore della propriet� presenzaFascRealeLiberoNV.
     * 
     */
    public boolean isPresenzaFascRealeLiberoNV() {
        return presenzaFascRealeLiberoNV;
    }

    /**
     * Imposta il valore della propriet� presenzaFascRealeLiberoNV.
     * 
     */
    public void setPresenzaFascRealeLiberoNV(boolean value) {
        this.presenzaFascRealeLiberoNV = value;
    }

    /**
     * Recupera il valore della propriet� presenzaFascTemp.
     * 
     */
    public boolean isPresenzaFascTemp() {
        return presenzaFascTemp;
    }

    /**
     * Imposta il valore della propriet� presenzaFascTemp.
     * 
     */
    public void setPresenzaFascTemp(boolean value) {
        this.presenzaFascTemp = value;
    }

    /**
     * Recupera il valore della propriet� presenzaSerieDoc.
     * 
     */
    public boolean isPresenzaSerieDoc() {
        return presenzaSerieDoc;
    }

    /**
     * Imposta il valore della propriet� presenzaSerieDoc.
     * 
     */
    public void setPresenzaSerieDoc(boolean value) {
        this.presenzaSerieDoc = value;
    }

    /**
     * Recupera il valore della propriet� presenzaSerieFasc.
     * 
     */
    public boolean isPresenzaSerieFasc() {
        return presenzaSerieFasc;
    }

    /**
     * Imposta il valore della propriet� presenzaSerieFasc.
     * 
     */
    public void setPresenzaSerieFasc(boolean value) {
        this.presenzaSerieFasc = value;
    }

    /**
     * Recupera il valore della propriet� presenzaSerieDoss.
     * 
     */
    public boolean isPresenzaSerieDoss() {
        return presenzaSerieDoss;
    }

    /**
     * Imposta il valore della propriet� presenzaSerieDoss.
     * 
     */
    public void setPresenzaSerieDoss(boolean value) {
        this.presenzaSerieDoss = value;
    }

    /**
     * Recupera il valore della propriet� insertSottoVociGestCont.
     * 
     */
    public boolean isInsertSottoVociGestCont() {
        return insertSottoVociGestCont;
    }

    /**
     * Imposta il valore della propriet� insertSottoVociGestCont.
     * 
     */
    public void setInsertSottoVociGestCont(boolean value) {
        this.insertSottoVociGestCont = value;
    }

    /**
     * Recupera il valore della propriet� creataGestCont.
     * 
     */
    public boolean isCreataGestCont() {
        return creataGestCont;
    }

    /**
     * Imposta il valore della propriet� creataGestCont.
     * 
     */
    public void setCreataGestCont(boolean value) {
        this.creataGestCont = value;
    }

    /**
     * Recupera il valore della propriet� paroleChiave.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParoleChiave() {
        return paroleChiave;
    }

    /**
     * Imposta il valore della propriet� paroleChiave.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParoleChiave(String value) {
        this.paroleChiave = value;
    }

    /**
     * Recupera il valore della propriet� dataUltimaModifica.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataUltimaModifica() {
        return dataUltimaModifica;
    }

    /**
     * Imposta il valore della propriet� dataUltimaModifica.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataUltimaModifica(XMLGregorianCalendar value) {
        this.dataUltimaModifica = value;
    }

    /**
     * Recupera il valore della propriet� conservazioneCorrente.
     * 
     */
    public int getConservazioneCorrente() {
        return conservazioneCorrente;
    }

    /**
     * Imposta il valore della propriet� conservazioneCorrente.
     * 
     */
    public void setConservazioneCorrente(int value) {
        this.conservazioneCorrente = value;
    }

    /**
     * Recupera il valore della propriet� conservazioneGenerale.
     * 
     */
    public int getConservazioneGenerale() {
        return conservazioneGenerale;
    }

    /**
     * Imposta il valore della propriet� conservazioneGenerale.
     * 
     */
    public void setConservazioneGenerale(int value) {
        this.conservazioneGenerale = value;
    }

    /**
     * Recupera il valore della propriet� dataBloccoPassaggioInDeposito.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataBloccoPassaggioInDeposito() {
        return dataBloccoPassaggioInDeposito;
    }

    /**
     * Imposta il valore della propriet� dataBloccoPassaggioInDeposito.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataBloccoPassaggioInDeposito(XMLGregorianCalendar value) {
        this.dataBloccoPassaggioInDeposito = value;
    }

    /**
     * Recupera il valore della propriet� dataSbloccoPassaggioInDeposito.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataSbloccoPassaggioInDeposito() {
        return dataSbloccoPassaggioInDeposito;
    }

    /**
     * Imposta il valore della propriet� dataSbloccoPassaggioInDeposito.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataSbloccoPassaggioInDeposito(XMLGregorianCalendar value) {
        this.dataSbloccoPassaggioInDeposito = value;
    }

    /**
     * Recupera il valore della propriet� dataCancellazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCancellazione() {
        return dataCancellazione;
    }

    /**
     * Imposta il valore della propriet� dataCancellazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCancellazione(XMLGregorianCalendar value) {
        this.dataCancellazione = value;
    }

    /**
     * Recupera il valore della propriet� dataEsportazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsportazione() {
        return dataEsportazione;
    }

    /**
     * Imposta il valore della propriet� dataEsportazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsportazione(XMLGregorianCalendar value) {
        this.dataEsportazione = value;
    }

    /**
     * Gets the value of the idFascicoloStandardList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idFascicoloStandardList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdFascicoloStandardList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdFascicoloStandardType }
     * 
     * 
     */
    public List<IdFascicoloStandardType> getIdFascicoloStandardList() {
        if (idFascicoloStandardList == null) {
            idFascicoloStandardList = new ArrayList<IdFascicoloStandardType>();
        }
        return this.idFascicoloStandardList;
    }

    /**
     * Gets the value of the idProvvedimentoAutorizzatList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idProvvedimentoAutorizzatList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdProvvedimentoAutorizzatList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdProvvedimentoAutorizzatType }
     * 
     * 
     */
    public List<IdProvvedimentoAutorizzatType> getIdProvvedimentoAutorizzatList() {
        if (idProvvedimentoAutorizzatList == null) {
            idProvvedimentoAutorizzatList = new ArrayList<IdProvvedimentoAutorizzatType>();
        }
        return this.idProvvedimentoAutorizzatList;
    }

    /**
     * Gets the value of the idAnnotazioniList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idAnnotazioniList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdAnnotazioniList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdAnnotazioniType }
     * 
     * 
     */
    public List<IdAnnotazioniType> getIdAnnotazioniList() {
        if (idAnnotazioniList == null) {
            idAnnotazioniList = new ArrayList<IdAnnotazioniType>();
        }
        return this.idAnnotazioniList;
    }

    /**
     * Recupera il valore della propriet� idTipoClasse.
     * 
     */
    public int getIdTipoClasse() {
        return idTipoClasse;
    }

    /**
     * Imposta il valore della propriet� idTipoClasse.
     * 
     */
    public void setIdTipoClasse(int value) {
        this.idTipoClasse = value;
    }

}
