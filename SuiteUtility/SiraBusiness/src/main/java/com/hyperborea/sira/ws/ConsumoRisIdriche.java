/**
 * ConsumoRisIdriche.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ConsumoRisIdriche  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.String approvvigionamento;

    private com.hyperborea.sira.ws.CcostOperaCaptIdr ccostOperaCaptIdr;

    private java.lang.Float consumoGiornaliero;

    private java.lang.Float consumoGiornalieroAnnoRif;

    private org.apache.axis.types.UnsignedShort contatore;

    private java.lang.String descrizione;

    private java.lang.String giorniPunta;

    private java.lang.Integer idRisIdrica;

    private java.lang.String mesiPunta;

    private java.lang.String orePunta;

    private java.lang.Float portataOrariaPunta;

    private java.lang.Float portataOrariaPuntaAnnoRif;

    private java.lang.String specificaAltro;

    private org.apache.axis.types.UnsignedShort utilizzoAltro;

    private org.apache.axis.types.UnsignedShort utilizzoIgienicoSan;

    private org.apache.axis.types.UnsignedShort utilizzoProcesso;

    private org.apache.axis.types.UnsignedShort utilizzoRaffreddamento;

    private java.lang.Float volTotAnnuo;

    private java.lang.Float volTotAnnuoAnnoRif;

    private java.lang.Integer wsCcostRef;

    private java.lang.Integer wsInternalRef;

    public ConsumoRisIdriche() {
    }

    public ConsumoRisIdriche(
           java.lang.Integer anno,
           java.lang.String approvvigionamento,
           com.hyperborea.sira.ws.CcostOperaCaptIdr ccostOperaCaptIdr,
           java.lang.Float consumoGiornaliero,
           java.lang.Float consumoGiornalieroAnnoRif,
           org.apache.axis.types.UnsignedShort contatore,
           java.lang.String descrizione,
           java.lang.String giorniPunta,
           java.lang.Integer idRisIdrica,
           java.lang.String mesiPunta,
           java.lang.String orePunta,
           java.lang.Float portataOrariaPunta,
           java.lang.Float portataOrariaPuntaAnnoRif,
           java.lang.String specificaAltro,
           org.apache.axis.types.UnsignedShort utilizzoAltro,
           org.apache.axis.types.UnsignedShort utilizzoIgienicoSan,
           org.apache.axis.types.UnsignedShort utilizzoProcesso,
           org.apache.axis.types.UnsignedShort utilizzoRaffreddamento,
           java.lang.Float volTotAnnuo,
           java.lang.Float volTotAnnuoAnnoRif,
           java.lang.Integer wsCcostRef,
           java.lang.Integer wsInternalRef) {
        this.anno = anno;
        this.approvvigionamento = approvvigionamento;
        this.ccostOperaCaptIdr = ccostOperaCaptIdr;
        this.consumoGiornaliero = consumoGiornaliero;
        this.consumoGiornalieroAnnoRif = consumoGiornalieroAnnoRif;
        this.contatore = contatore;
        this.descrizione = descrizione;
        this.giorniPunta = giorniPunta;
        this.idRisIdrica = idRisIdrica;
        this.mesiPunta = mesiPunta;
        this.orePunta = orePunta;
        this.portataOrariaPunta = portataOrariaPunta;
        this.portataOrariaPuntaAnnoRif = portataOrariaPuntaAnnoRif;
        this.specificaAltro = specificaAltro;
        this.utilizzoAltro = utilizzoAltro;
        this.utilizzoIgienicoSan = utilizzoIgienicoSan;
        this.utilizzoProcesso = utilizzoProcesso;
        this.utilizzoRaffreddamento = utilizzoRaffreddamento;
        this.volTotAnnuo = volTotAnnuo;
        this.volTotAnnuoAnnoRif = volTotAnnuoAnnoRif;
        this.wsCcostRef = wsCcostRef;
        this.wsInternalRef = wsInternalRef;
    }


    /**
     * Gets the anno value for this ConsumoRisIdriche.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this ConsumoRisIdriche.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the approvvigionamento value for this ConsumoRisIdriche.
     * 
     * @return approvvigionamento
     */
    public java.lang.String getApprovvigionamento() {
        return approvvigionamento;
    }


    /**
     * Sets the approvvigionamento value for this ConsumoRisIdriche.
     * 
     * @param approvvigionamento
     */
    public void setApprovvigionamento(java.lang.String approvvigionamento) {
        this.approvvigionamento = approvvigionamento;
    }


    /**
     * Gets the ccostOperaCaptIdr value for this ConsumoRisIdriche.
     * 
     * @return ccostOperaCaptIdr
     */
    public com.hyperborea.sira.ws.CcostOperaCaptIdr getCcostOperaCaptIdr() {
        return ccostOperaCaptIdr;
    }


    /**
     * Sets the ccostOperaCaptIdr value for this ConsumoRisIdriche.
     * 
     * @param ccostOperaCaptIdr
     */
    public void setCcostOperaCaptIdr(com.hyperborea.sira.ws.CcostOperaCaptIdr ccostOperaCaptIdr) {
        this.ccostOperaCaptIdr = ccostOperaCaptIdr;
    }


    /**
     * Gets the consumoGiornaliero value for this ConsumoRisIdriche.
     * 
     * @return consumoGiornaliero
     */
    public java.lang.Float getConsumoGiornaliero() {
        return consumoGiornaliero;
    }


    /**
     * Sets the consumoGiornaliero value for this ConsumoRisIdriche.
     * 
     * @param consumoGiornaliero
     */
    public void setConsumoGiornaliero(java.lang.Float consumoGiornaliero) {
        this.consumoGiornaliero = consumoGiornaliero;
    }


    /**
     * Gets the consumoGiornalieroAnnoRif value for this ConsumoRisIdriche.
     * 
     * @return consumoGiornalieroAnnoRif
     */
    public java.lang.Float getConsumoGiornalieroAnnoRif() {
        return consumoGiornalieroAnnoRif;
    }


    /**
     * Sets the consumoGiornalieroAnnoRif value for this ConsumoRisIdriche.
     * 
     * @param consumoGiornalieroAnnoRif
     */
    public void setConsumoGiornalieroAnnoRif(java.lang.Float consumoGiornalieroAnnoRif) {
        this.consumoGiornalieroAnnoRif = consumoGiornalieroAnnoRif;
    }


    /**
     * Gets the contatore value for this ConsumoRisIdriche.
     * 
     * @return contatore
     */
    public org.apache.axis.types.UnsignedShort getContatore() {
        return contatore;
    }


    /**
     * Sets the contatore value for this ConsumoRisIdriche.
     * 
     * @param contatore
     */
    public void setContatore(org.apache.axis.types.UnsignedShort contatore) {
        this.contatore = contatore;
    }


    /**
     * Gets the descrizione value for this ConsumoRisIdriche.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ConsumoRisIdriche.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the giorniPunta value for this ConsumoRisIdriche.
     * 
     * @return giorniPunta
     */
    public java.lang.String getGiorniPunta() {
        return giorniPunta;
    }


    /**
     * Sets the giorniPunta value for this ConsumoRisIdriche.
     * 
     * @param giorniPunta
     */
    public void setGiorniPunta(java.lang.String giorniPunta) {
        this.giorniPunta = giorniPunta;
    }


    /**
     * Gets the idRisIdrica value for this ConsumoRisIdriche.
     * 
     * @return idRisIdrica
     */
    public java.lang.Integer getIdRisIdrica() {
        return idRisIdrica;
    }


    /**
     * Sets the idRisIdrica value for this ConsumoRisIdriche.
     * 
     * @param idRisIdrica
     */
    public void setIdRisIdrica(java.lang.Integer idRisIdrica) {
        this.idRisIdrica = idRisIdrica;
    }


    /**
     * Gets the mesiPunta value for this ConsumoRisIdriche.
     * 
     * @return mesiPunta
     */
    public java.lang.String getMesiPunta() {
        return mesiPunta;
    }


    /**
     * Sets the mesiPunta value for this ConsumoRisIdriche.
     * 
     * @param mesiPunta
     */
    public void setMesiPunta(java.lang.String mesiPunta) {
        this.mesiPunta = mesiPunta;
    }


    /**
     * Gets the orePunta value for this ConsumoRisIdriche.
     * 
     * @return orePunta
     */
    public java.lang.String getOrePunta() {
        return orePunta;
    }


    /**
     * Sets the orePunta value for this ConsumoRisIdriche.
     * 
     * @param orePunta
     */
    public void setOrePunta(java.lang.String orePunta) {
        this.orePunta = orePunta;
    }


    /**
     * Gets the portataOrariaPunta value for this ConsumoRisIdriche.
     * 
     * @return portataOrariaPunta
     */
    public java.lang.Float getPortataOrariaPunta() {
        return portataOrariaPunta;
    }


    /**
     * Sets the portataOrariaPunta value for this ConsumoRisIdriche.
     * 
     * @param portataOrariaPunta
     */
    public void setPortataOrariaPunta(java.lang.Float portataOrariaPunta) {
        this.portataOrariaPunta = portataOrariaPunta;
    }


    /**
     * Gets the portataOrariaPuntaAnnoRif value for this ConsumoRisIdriche.
     * 
     * @return portataOrariaPuntaAnnoRif
     */
    public java.lang.Float getPortataOrariaPuntaAnnoRif() {
        return portataOrariaPuntaAnnoRif;
    }


    /**
     * Sets the portataOrariaPuntaAnnoRif value for this ConsumoRisIdriche.
     * 
     * @param portataOrariaPuntaAnnoRif
     */
    public void setPortataOrariaPuntaAnnoRif(java.lang.Float portataOrariaPuntaAnnoRif) {
        this.portataOrariaPuntaAnnoRif = portataOrariaPuntaAnnoRif;
    }


    /**
     * Gets the specificaAltro value for this ConsumoRisIdriche.
     * 
     * @return specificaAltro
     */
    public java.lang.String getSpecificaAltro() {
        return specificaAltro;
    }


    /**
     * Sets the specificaAltro value for this ConsumoRisIdriche.
     * 
     * @param specificaAltro
     */
    public void setSpecificaAltro(java.lang.String specificaAltro) {
        this.specificaAltro = specificaAltro;
    }


    /**
     * Gets the utilizzoAltro value for this ConsumoRisIdriche.
     * 
     * @return utilizzoAltro
     */
    public org.apache.axis.types.UnsignedShort getUtilizzoAltro() {
        return utilizzoAltro;
    }


    /**
     * Sets the utilizzoAltro value for this ConsumoRisIdriche.
     * 
     * @param utilizzoAltro
     */
    public void setUtilizzoAltro(org.apache.axis.types.UnsignedShort utilizzoAltro) {
        this.utilizzoAltro = utilizzoAltro;
    }


    /**
     * Gets the utilizzoIgienicoSan value for this ConsumoRisIdriche.
     * 
     * @return utilizzoIgienicoSan
     */
    public org.apache.axis.types.UnsignedShort getUtilizzoIgienicoSan() {
        return utilizzoIgienicoSan;
    }


    /**
     * Sets the utilizzoIgienicoSan value for this ConsumoRisIdriche.
     * 
     * @param utilizzoIgienicoSan
     */
    public void setUtilizzoIgienicoSan(org.apache.axis.types.UnsignedShort utilizzoIgienicoSan) {
        this.utilizzoIgienicoSan = utilizzoIgienicoSan;
    }


    /**
     * Gets the utilizzoProcesso value for this ConsumoRisIdriche.
     * 
     * @return utilizzoProcesso
     */
    public org.apache.axis.types.UnsignedShort getUtilizzoProcesso() {
        return utilizzoProcesso;
    }


    /**
     * Sets the utilizzoProcesso value for this ConsumoRisIdriche.
     * 
     * @param utilizzoProcesso
     */
    public void setUtilizzoProcesso(org.apache.axis.types.UnsignedShort utilizzoProcesso) {
        this.utilizzoProcesso = utilizzoProcesso;
    }


    /**
     * Gets the utilizzoRaffreddamento value for this ConsumoRisIdriche.
     * 
     * @return utilizzoRaffreddamento
     */
    public org.apache.axis.types.UnsignedShort getUtilizzoRaffreddamento() {
        return utilizzoRaffreddamento;
    }


    /**
     * Sets the utilizzoRaffreddamento value for this ConsumoRisIdriche.
     * 
     * @param utilizzoRaffreddamento
     */
    public void setUtilizzoRaffreddamento(org.apache.axis.types.UnsignedShort utilizzoRaffreddamento) {
        this.utilizzoRaffreddamento = utilizzoRaffreddamento;
    }


    /**
     * Gets the volTotAnnuo value for this ConsumoRisIdriche.
     * 
     * @return volTotAnnuo
     */
    public java.lang.Float getVolTotAnnuo() {
        return volTotAnnuo;
    }


    /**
     * Sets the volTotAnnuo value for this ConsumoRisIdriche.
     * 
     * @param volTotAnnuo
     */
    public void setVolTotAnnuo(java.lang.Float volTotAnnuo) {
        this.volTotAnnuo = volTotAnnuo;
    }


    /**
     * Gets the volTotAnnuoAnnoRif value for this ConsumoRisIdriche.
     * 
     * @return volTotAnnuoAnnoRif
     */
    public java.lang.Float getVolTotAnnuoAnnoRif() {
        return volTotAnnuoAnnoRif;
    }


    /**
     * Sets the volTotAnnuoAnnoRif value for this ConsumoRisIdriche.
     * 
     * @param volTotAnnuoAnnoRif
     */
    public void setVolTotAnnuoAnnoRif(java.lang.Float volTotAnnuoAnnoRif) {
        this.volTotAnnuoAnnoRif = volTotAnnuoAnnoRif;
    }


    /**
     * Gets the wsCcostRef value for this ConsumoRisIdriche.
     * 
     * @return wsCcostRef
     */
    public java.lang.Integer getWsCcostRef() {
        return wsCcostRef;
    }


    /**
     * Sets the wsCcostRef value for this ConsumoRisIdriche.
     * 
     * @param wsCcostRef
     */
    public void setWsCcostRef(java.lang.Integer wsCcostRef) {
        this.wsCcostRef = wsCcostRef;
    }


    /**
     * Gets the wsInternalRef value for this ConsumoRisIdriche.
     * 
     * @return wsInternalRef
     */
    public java.lang.Integer getWsInternalRef() {
        return wsInternalRef;
    }


    /**
     * Sets the wsInternalRef value for this ConsumoRisIdriche.
     * 
     * @param wsInternalRef
     */
    public void setWsInternalRef(java.lang.Integer wsInternalRef) {
        this.wsInternalRef = wsInternalRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsumoRisIdriche)) return false;
        ConsumoRisIdriche other = (ConsumoRisIdriche) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            ((this.approvvigionamento==null && other.getApprovvigionamento()==null) || 
             (this.approvvigionamento!=null &&
              this.approvvigionamento.equals(other.getApprovvigionamento()))) &&
            ((this.ccostOperaCaptIdr==null && other.getCcostOperaCaptIdr()==null) || 
             (this.ccostOperaCaptIdr!=null &&
              this.ccostOperaCaptIdr.equals(other.getCcostOperaCaptIdr()))) &&
            ((this.consumoGiornaliero==null && other.getConsumoGiornaliero()==null) || 
             (this.consumoGiornaliero!=null &&
              this.consumoGiornaliero.equals(other.getConsumoGiornaliero()))) &&
            ((this.consumoGiornalieroAnnoRif==null && other.getConsumoGiornalieroAnnoRif()==null) || 
             (this.consumoGiornalieroAnnoRif!=null &&
              this.consumoGiornalieroAnnoRif.equals(other.getConsumoGiornalieroAnnoRif()))) &&
            ((this.contatore==null && other.getContatore()==null) || 
             (this.contatore!=null &&
              this.contatore.equals(other.getContatore()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.giorniPunta==null && other.getGiorniPunta()==null) || 
             (this.giorniPunta!=null &&
              this.giorniPunta.equals(other.getGiorniPunta()))) &&
            ((this.idRisIdrica==null && other.getIdRisIdrica()==null) || 
             (this.idRisIdrica!=null &&
              this.idRisIdrica.equals(other.getIdRisIdrica()))) &&
            ((this.mesiPunta==null && other.getMesiPunta()==null) || 
             (this.mesiPunta!=null &&
              this.mesiPunta.equals(other.getMesiPunta()))) &&
            ((this.orePunta==null && other.getOrePunta()==null) || 
             (this.orePunta!=null &&
              this.orePunta.equals(other.getOrePunta()))) &&
            ((this.portataOrariaPunta==null && other.getPortataOrariaPunta()==null) || 
             (this.portataOrariaPunta!=null &&
              this.portataOrariaPunta.equals(other.getPortataOrariaPunta()))) &&
            ((this.portataOrariaPuntaAnnoRif==null && other.getPortataOrariaPuntaAnnoRif()==null) || 
             (this.portataOrariaPuntaAnnoRif!=null &&
              this.portataOrariaPuntaAnnoRif.equals(other.getPortataOrariaPuntaAnnoRif()))) &&
            ((this.specificaAltro==null && other.getSpecificaAltro()==null) || 
             (this.specificaAltro!=null &&
              this.specificaAltro.equals(other.getSpecificaAltro()))) &&
            ((this.utilizzoAltro==null && other.getUtilizzoAltro()==null) || 
             (this.utilizzoAltro!=null &&
              this.utilizzoAltro.equals(other.getUtilizzoAltro()))) &&
            ((this.utilizzoIgienicoSan==null && other.getUtilizzoIgienicoSan()==null) || 
             (this.utilizzoIgienicoSan!=null &&
              this.utilizzoIgienicoSan.equals(other.getUtilizzoIgienicoSan()))) &&
            ((this.utilizzoProcesso==null && other.getUtilizzoProcesso()==null) || 
             (this.utilizzoProcesso!=null &&
              this.utilizzoProcesso.equals(other.getUtilizzoProcesso()))) &&
            ((this.utilizzoRaffreddamento==null && other.getUtilizzoRaffreddamento()==null) || 
             (this.utilizzoRaffreddamento!=null &&
              this.utilizzoRaffreddamento.equals(other.getUtilizzoRaffreddamento()))) &&
            ((this.volTotAnnuo==null && other.getVolTotAnnuo()==null) || 
             (this.volTotAnnuo!=null &&
              this.volTotAnnuo.equals(other.getVolTotAnnuo()))) &&
            ((this.volTotAnnuoAnnoRif==null && other.getVolTotAnnuoAnnoRif()==null) || 
             (this.volTotAnnuoAnnoRif!=null &&
              this.volTotAnnuoAnnoRif.equals(other.getVolTotAnnuoAnnoRif()))) &&
            ((this.wsCcostRef==null && other.getWsCcostRef()==null) || 
             (this.wsCcostRef!=null &&
              this.wsCcostRef.equals(other.getWsCcostRef()))) &&
            ((this.wsInternalRef==null && other.getWsInternalRef()==null) || 
             (this.wsInternalRef!=null &&
              this.wsInternalRef.equals(other.getWsInternalRef())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        if (getApprovvigionamento() != null) {
            _hashCode += getApprovvigionamento().hashCode();
        }
        if (getCcostOperaCaptIdr() != null) {
            _hashCode += getCcostOperaCaptIdr().hashCode();
        }
        if (getConsumoGiornaliero() != null) {
            _hashCode += getConsumoGiornaliero().hashCode();
        }
        if (getConsumoGiornalieroAnnoRif() != null) {
            _hashCode += getConsumoGiornalieroAnnoRif().hashCode();
        }
        if (getContatore() != null) {
            _hashCode += getContatore().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getGiorniPunta() != null) {
            _hashCode += getGiorniPunta().hashCode();
        }
        if (getIdRisIdrica() != null) {
            _hashCode += getIdRisIdrica().hashCode();
        }
        if (getMesiPunta() != null) {
            _hashCode += getMesiPunta().hashCode();
        }
        if (getOrePunta() != null) {
            _hashCode += getOrePunta().hashCode();
        }
        if (getPortataOrariaPunta() != null) {
            _hashCode += getPortataOrariaPunta().hashCode();
        }
        if (getPortataOrariaPuntaAnnoRif() != null) {
            _hashCode += getPortataOrariaPuntaAnnoRif().hashCode();
        }
        if (getSpecificaAltro() != null) {
            _hashCode += getSpecificaAltro().hashCode();
        }
        if (getUtilizzoAltro() != null) {
            _hashCode += getUtilizzoAltro().hashCode();
        }
        if (getUtilizzoIgienicoSan() != null) {
            _hashCode += getUtilizzoIgienicoSan().hashCode();
        }
        if (getUtilizzoProcesso() != null) {
            _hashCode += getUtilizzoProcesso().hashCode();
        }
        if (getUtilizzoRaffreddamento() != null) {
            _hashCode += getUtilizzoRaffreddamento().hashCode();
        }
        if (getVolTotAnnuo() != null) {
            _hashCode += getVolTotAnnuo().hashCode();
        }
        if (getVolTotAnnuoAnnoRif() != null) {
            _hashCode += getVolTotAnnuoAnnoRif().hashCode();
        }
        if (getWsCcostRef() != null) {
            _hashCode += getWsCcostRef().hashCode();
        }
        if (getWsInternalRef() != null) {
            _hashCode += getWsInternalRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsumoRisIdriche.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoRisIdriche"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvvigionamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approvvigionamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostOperaCaptIdr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostOperaCaptIdr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostOperaCaptIdr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoGiornaliero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoGiornaliero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoGiornalieroAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoGiornalieroAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contatore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giorniPunta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giorniPunta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRisIdrica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRisIdrica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mesiPunta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mesiPunta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orePunta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orePunta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataOrariaPunta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataOrariaPunta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataOrariaPuntaAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataOrariaPuntaAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specificaAltro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specificaAltro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilizzoAltro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilizzoAltro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilizzoIgienicoSan");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilizzoIgienicoSan"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilizzoProcesso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilizzoProcesso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilizzoRaffreddamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilizzoRaffreddamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volTotAnnuo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volTotAnnuo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volTotAnnuoAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volTotAnnuoAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCcostRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCcostRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsInternalRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsInternalRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
