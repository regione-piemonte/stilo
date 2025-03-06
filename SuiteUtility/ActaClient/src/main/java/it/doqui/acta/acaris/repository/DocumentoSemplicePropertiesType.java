
package it.doqui.acta.acaris.repository;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentoSemplicePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentoSemplicePropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}DocumentoPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codBarre" type="{archive.acaris.acta.doqui.it}CodBarreType"/&gt;
 *         &lt;element name="multiplo" type="{archive.acaris.acta.doqui.it}MultiploType"/&gt;
 *         &lt;element name="tipoDocFisico" type="{archive.acaris.acta.doqui.it}enumTipoDocumentoType"/&gt;
 *         &lt;element name="composizione" type="{archive.acaris.acta.doqui.it}enumDocPrimarioType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoSemplicePropertiesType", propOrder = {
    "codBarre",
    "multiplo",
    "tipoDocFisico",
    "composizione"
})
public class DocumentoSemplicePropertiesType
    extends DocumentoPropertiesType
{

    @XmlElement(required = true)
    protected String codBarre;
    protected boolean multiplo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipoDocumentoType tipoDocFisico;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumDocPrimarioType composizione;

    /**
     * Recupera il valore della propriet� codBarre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodBarre() {
        return codBarre;
    }

    /**
     * Imposta il valore della propriet� codBarre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodBarre(String value) {
        this.codBarre = value;
    }

    /**
     * Recupera il valore della propriet� multiplo.
     * 
     */
    public boolean isMultiplo() {
        return multiplo;
    }

    /**
     * Imposta il valore della propriet� multiplo.
     * 
     */
    public void setMultiplo(boolean value) {
        this.multiplo = value;
    }

    /**
     * Recupera il valore della propriet� tipoDocFisico.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipoDocumentoType }
     *     
     */
    public EnumTipoDocumentoType getTipoDocFisico() {
        return tipoDocFisico;
    }

    /**
     * Imposta il valore della propriet� tipoDocFisico.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipoDocumentoType }
     *     
     */
    public void setTipoDocFisico(EnumTipoDocumentoType value) {
        this.tipoDocFisico = value;
    }

    /**
     * Recupera il valore della propriet� composizione.
     * 
     * @return
     *     possible object is
     *     {@link EnumDocPrimarioType }
     *     
     */
    public EnumDocPrimarioType getComposizione() {
        return composizione;
    }

    /**
     * Imposta il valore della propriet� composizione.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumDocPrimarioType }
     *     
     */
    public void setComposizione(EnumDocPrimarioType value) {
        this.composizione = value;
    }

}
