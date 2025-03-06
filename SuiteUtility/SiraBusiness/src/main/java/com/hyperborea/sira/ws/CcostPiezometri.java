/**
 * CcostPiezometri.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostPiezometri  implements java.io.Serializable {
    private java.lang.Integer annoRealizzazione;

    private java.lang.String codGeniocivile;

    private com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas;

    private org.apache.axis.types.UnsignedShort elettControllo;

    private java.lang.Integer idCcost;

    private java.lang.Float livPiezometro;

    private java.lang.Float profondita;

    private java.lang.Float soggiacenza;

    private org.apache.axis.types.UnsignedShort stratigrafia;

    private org.apache.axis.types.UnsignedShort strumMisura;

    private java.lang.String tipoSoggiacenza;

    public CcostPiezometri() {
    }

    public CcostPiezometri(
           java.lang.Integer annoRealizzazione,
           java.lang.String codGeniocivile,
           com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas,
           org.apache.axis.types.UnsignedShort elettControllo,
           java.lang.Integer idCcost,
           java.lang.Float livPiezometro,
           java.lang.Float profondita,
           java.lang.Float soggiacenza,
           org.apache.axis.types.UnsignedShort stratigrafia,
           org.apache.axis.types.UnsignedShort strumMisura,
           java.lang.String tipoSoggiacenza) {
           this.annoRealizzazione = annoRealizzazione;
           this.codGeniocivile = codGeniocivile;
           this.colLitostratigraficas = colLitostratigraficas;
           this.elettControllo = elettControllo;
           this.idCcost = idCcost;
           this.livPiezometro = livPiezometro;
           this.profondita = profondita;
           this.soggiacenza = soggiacenza;
           this.stratigrafia = stratigrafia;
           this.strumMisura = strumMisura;
           this.tipoSoggiacenza = tipoSoggiacenza;
    }


    /**
     * Gets the annoRealizzazione value for this CcostPiezometri.
     * 
     * @return annoRealizzazione
     */
    public java.lang.Integer getAnnoRealizzazione() {
        return annoRealizzazione;
    }


    /**
     * Sets the annoRealizzazione value for this CcostPiezometri.
     * 
     * @param annoRealizzazione
     */
    public void setAnnoRealizzazione(java.lang.Integer annoRealizzazione) {
        this.annoRealizzazione = annoRealizzazione;
    }


    /**
     * Gets the codGeniocivile value for this CcostPiezometri.
     * 
     * @return codGeniocivile
     */
    public java.lang.String getCodGeniocivile() {
        return codGeniocivile;
    }


    /**
     * Sets the codGeniocivile value for this CcostPiezometri.
     * 
     * @param codGeniocivile
     */
    public void setCodGeniocivile(java.lang.String codGeniocivile) {
        this.codGeniocivile = codGeniocivile;
    }


    /**
     * Gets the colLitostratigraficas value for this CcostPiezometri.
     * 
     * @return colLitostratigraficas
     */
    public com.hyperborea.sira.ws.ColLitostratigrafica[] getColLitostratigraficas() {
        return colLitostratigraficas;
    }


    /**
     * Sets the colLitostratigraficas value for this CcostPiezometri.
     * 
     * @param colLitostratigraficas
     */
    public void setColLitostratigraficas(com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas) {
        this.colLitostratigraficas = colLitostratigraficas;
    }

    public com.hyperborea.sira.ws.ColLitostratigrafica getColLitostratigraficas(int i) {
        return this.colLitostratigraficas[i];
    }

    public void setColLitostratigraficas(int i, com.hyperborea.sira.ws.ColLitostratigrafica _value) {
        this.colLitostratigraficas[i] = _value;
    }


    /**
     * Gets the elettControllo value for this CcostPiezometri.
     * 
     * @return elettControllo
     */
    public org.apache.axis.types.UnsignedShort getElettControllo() {
        return elettControllo;
    }


    /**
     * Sets the elettControllo value for this CcostPiezometri.
     * 
     * @param elettControllo
     */
    public void setElettControllo(org.apache.axis.types.UnsignedShort elettControllo) {
        this.elettControllo = elettControllo;
    }


    /**
     * Gets the idCcost value for this CcostPiezometri.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostPiezometri.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the livPiezometro value for this CcostPiezometri.
     * 
     * @return livPiezometro
     */
    public java.lang.Float getLivPiezometro() {
        return livPiezometro;
    }


    /**
     * Sets the livPiezometro value for this CcostPiezometri.
     * 
     * @param livPiezometro
     */
    public void setLivPiezometro(java.lang.Float livPiezometro) {
        this.livPiezometro = livPiezometro;
    }


    /**
     * Gets the profondita value for this CcostPiezometri.
     * 
     * @return profondita
     */
    public java.lang.Float getProfondita() {
        return profondita;
    }


    /**
     * Sets the profondita value for this CcostPiezometri.
     * 
     * @param profondita
     */
    public void setProfondita(java.lang.Float profondita) {
        this.profondita = profondita;
    }


    /**
     * Gets the soggiacenza value for this CcostPiezometri.
     * 
     * @return soggiacenza
     */
    public java.lang.Float getSoggiacenza() {
        return soggiacenza;
    }


    /**
     * Sets the soggiacenza value for this CcostPiezometri.
     * 
     * @param soggiacenza
     */
    public void setSoggiacenza(java.lang.Float soggiacenza) {
        this.soggiacenza = soggiacenza;
    }


    /**
     * Gets the stratigrafia value for this CcostPiezometri.
     * 
     * @return stratigrafia
     */
    public org.apache.axis.types.UnsignedShort getStratigrafia() {
        return stratigrafia;
    }


    /**
     * Sets the stratigrafia value for this CcostPiezometri.
     * 
     * @param stratigrafia
     */
    public void setStratigrafia(org.apache.axis.types.UnsignedShort stratigrafia) {
        this.stratigrafia = stratigrafia;
    }


    /**
     * Gets the strumMisura value for this CcostPiezometri.
     * 
     * @return strumMisura
     */
    public org.apache.axis.types.UnsignedShort getStrumMisura() {
        return strumMisura;
    }


    /**
     * Sets the strumMisura value for this CcostPiezometri.
     * 
     * @param strumMisura
     */
    public void setStrumMisura(org.apache.axis.types.UnsignedShort strumMisura) {
        this.strumMisura = strumMisura;
    }


    /**
     * Gets the tipoSoggiacenza value for this CcostPiezometri.
     * 
     * @return tipoSoggiacenza
     */
    public java.lang.String getTipoSoggiacenza() {
        return tipoSoggiacenza;
    }


    /**
     * Sets the tipoSoggiacenza value for this CcostPiezometri.
     * 
     * @param tipoSoggiacenza
     */
    public void setTipoSoggiacenza(java.lang.String tipoSoggiacenza) {
        this.tipoSoggiacenza = tipoSoggiacenza;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostPiezometri)) return false;
        CcostPiezometri other = (CcostPiezometri) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoRealizzazione==null && other.getAnnoRealizzazione()==null) || 
             (this.annoRealizzazione!=null &&
              this.annoRealizzazione.equals(other.getAnnoRealizzazione()))) &&
            ((this.codGeniocivile==null && other.getCodGeniocivile()==null) || 
             (this.codGeniocivile!=null &&
              this.codGeniocivile.equals(other.getCodGeniocivile()))) &&
            ((this.colLitostratigraficas==null && other.getColLitostratigraficas()==null) || 
             (this.colLitostratigraficas!=null &&
              java.util.Arrays.equals(this.colLitostratigraficas, other.getColLitostratigraficas()))) &&
            ((this.elettControllo==null && other.getElettControllo()==null) || 
             (this.elettControllo!=null &&
              this.elettControllo.equals(other.getElettControllo()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.livPiezometro==null && other.getLivPiezometro()==null) || 
             (this.livPiezometro!=null &&
              this.livPiezometro.equals(other.getLivPiezometro()))) &&
            ((this.profondita==null && other.getProfondita()==null) || 
             (this.profondita!=null &&
              this.profondita.equals(other.getProfondita()))) &&
            ((this.soggiacenza==null && other.getSoggiacenza()==null) || 
             (this.soggiacenza!=null &&
              this.soggiacenza.equals(other.getSoggiacenza()))) &&
            ((this.stratigrafia==null && other.getStratigrafia()==null) || 
             (this.stratigrafia!=null &&
              this.stratigrafia.equals(other.getStratigrafia()))) &&
            ((this.strumMisura==null && other.getStrumMisura()==null) || 
             (this.strumMisura!=null &&
              this.strumMisura.equals(other.getStrumMisura()))) &&
            ((this.tipoSoggiacenza==null && other.getTipoSoggiacenza()==null) || 
             (this.tipoSoggiacenza!=null &&
              this.tipoSoggiacenza.equals(other.getTipoSoggiacenza())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAnnoRealizzazione() != null) {
            _hashCode += getAnnoRealizzazione().hashCode();
        }
        if (getCodGeniocivile() != null) {
            _hashCode += getCodGeniocivile().hashCode();
        }
        if (getColLitostratigraficas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getColLitostratigraficas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getColLitostratigraficas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getElettControllo() != null) {
            _hashCode += getElettControllo().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLivPiezometro() != null) {
            _hashCode += getLivPiezometro().hashCode();
        }
        if (getProfondita() != null) {
            _hashCode += getProfondita().hashCode();
        }
        if (getSoggiacenza() != null) {
            _hashCode += getSoggiacenza().hashCode();
        }
        if (getStratigrafia() != null) {
            _hashCode += getStratigrafia().hashCode();
        }
        if (getStrumMisura() != null) {
            _hashCode += getStrumMisura().hashCode();
        }
        if (getTipoSoggiacenza() != null) {
            _hashCode += getTipoSoggiacenza().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostPiezometri.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPiezometri"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoRealizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoRealizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codGeniocivile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codGeniocivile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colLitostratigraficas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "colLitostratigraficas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "colLitostratigrafica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elettControllo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elettControllo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("livPiezometro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "livPiezometro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profondita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profondita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggiacenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggiacenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stratigrafia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stratigrafia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strumMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strumMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoSoggiacenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSoggiacenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
