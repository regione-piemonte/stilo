/**
 * Allegati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Allegati  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AllegatiCcost[] allegatiCcosts;

    private com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm;

    private java.lang.String contentType;

    private java.util.Calendar dataAllegato;

    private com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali;

    private com.hyperborea.sira.ws.FormNatura2000 formNatura2000;

    private java.lang.Integer idAllegato;

    private java.lang.String nomeFile;

    private java.lang.String oggetto;

    private com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative;

    private java.lang.String tipoDocumento;

    public Allegati() {
    }

    public Allegati(
           com.hyperborea.sira.ws.AllegatiCcost[] allegatiCcosts,
           com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm,
           java.lang.String contentType,
           java.util.Calendar dataAllegato,
           com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali,
           com.hyperborea.sira.ws.FormNatura2000 formNatura2000,
           java.lang.Integer idAllegato,
           java.lang.String nomeFile,
           java.lang.String oggetto,
           com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative,
           java.lang.String tipoDocumento) {
        this.allegatiCcosts = allegatiCcosts;
        this.attiDisposizioniAmm = attiDisposizioniAmm;
        this.contentType = contentType;
        this.dataAllegato = dataAllegato;
        this.dichiarazioniAmbientali = dichiarazioniAmbientali;
        this.formNatura2000 = formNatura2000;
        this.idAllegato = idAllegato;
        this.nomeFile = nomeFile;
        this.oggetto = oggetto;
        this.praticheAmministrative = praticheAmministrative;
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the allegatiCcosts value for this Allegati.
     * 
     * @return allegatiCcosts
     */
    public com.hyperborea.sira.ws.AllegatiCcost[] getAllegatiCcosts() {
        return allegatiCcosts;
    }


    /**
     * Sets the allegatiCcosts value for this Allegati.
     * 
     * @param allegatiCcosts
     */
    public void setAllegatiCcosts(com.hyperborea.sira.ws.AllegatiCcost[] allegatiCcosts) {
        this.allegatiCcosts = allegatiCcosts;
    }

    public com.hyperborea.sira.ws.AllegatiCcost getAllegatiCcosts(int i) {
        return this.allegatiCcosts[i];
    }

    public void setAllegatiCcosts(int i, com.hyperborea.sira.ws.AllegatiCcost _value) {
        this.allegatiCcosts[i] = _value;
    }


    /**
     * Gets the attiDisposizioniAmm value for this Allegati.
     * 
     * @return attiDisposizioniAmm
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm getAttiDisposizioniAmm() {
        return attiDisposizioniAmm;
    }


    /**
     * Sets the attiDisposizioniAmm value for this Allegati.
     * 
     * @param attiDisposizioniAmm
     */
    public void setAttiDisposizioniAmm(com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm) {
        this.attiDisposizioniAmm = attiDisposizioniAmm;
    }


    /**
     * Gets the contentType value for this Allegati.
     * 
     * @return contentType
     */
    public java.lang.String getContentType() {
        return contentType;
    }


    /**
     * Sets the contentType value for this Allegati.
     * 
     * @param contentType
     */
    public void setContentType(java.lang.String contentType) {
        this.contentType = contentType;
    }


    /**
     * Gets the dataAllegato value for this Allegati.
     * 
     * @return dataAllegato
     */
    public java.util.Calendar getDataAllegato() {
        return dataAllegato;
    }


    /**
     * Sets the dataAllegato value for this Allegati.
     * 
     * @param dataAllegato
     */
    public void setDataAllegato(java.util.Calendar dataAllegato) {
        this.dataAllegato = dataAllegato;
    }


    /**
     * Gets the dichiarazioniAmbientali value for this Allegati.
     * 
     * @return dichiarazioniAmbientali
     */
    public com.hyperborea.sira.ws.DichiarazioniAmbientali getDichiarazioniAmbientali() {
        return dichiarazioniAmbientali;
    }


    /**
     * Sets the dichiarazioniAmbientali value for this Allegati.
     * 
     * @param dichiarazioniAmbientali
     */
    public void setDichiarazioniAmbientali(com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali) {
        this.dichiarazioniAmbientali = dichiarazioniAmbientali;
    }


    /**
     * Gets the formNatura2000 value for this Allegati.
     * 
     * @return formNatura2000
     */
    public com.hyperborea.sira.ws.FormNatura2000 getFormNatura2000() {
        return formNatura2000;
    }


    /**
     * Sets the formNatura2000 value for this Allegati.
     * 
     * @param formNatura2000
     */
    public void setFormNatura2000(com.hyperborea.sira.ws.FormNatura2000 formNatura2000) {
        this.formNatura2000 = formNatura2000;
    }


    /**
     * Gets the idAllegato value for this Allegati.
     * 
     * @return idAllegato
     */
    public java.lang.Integer getIdAllegato() {
        return idAllegato;
    }


    /**
     * Sets the idAllegato value for this Allegati.
     * 
     * @param idAllegato
     */
    public void setIdAllegato(java.lang.Integer idAllegato) {
        this.idAllegato = idAllegato;
    }


    /**
     * Gets the nomeFile value for this Allegati.
     * 
     * @return nomeFile
     */
    public java.lang.String getNomeFile() {
        return nomeFile;
    }


    /**
     * Sets the nomeFile value for this Allegati.
     * 
     * @param nomeFile
     */
    public void setNomeFile(java.lang.String nomeFile) {
        this.nomeFile = nomeFile;
    }


    /**
     * Gets the oggetto value for this Allegati.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this Allegati.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the praticheAmministrative value for this Allegati.
     * 
     * @return praticheAmministrative
     */
    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministrative() {
        return praticheAmministrative;
    }


    /**
     * Sets the praticheAmministrative value for this Allegati.
     * 
     * @param praticheAmministrative
     */
    public void setPraticheAmministrative(com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative) {
        this.praticheAmministrative = praticheAmministrative;
    }


    /**
     * Gets the tipoDocumento value for this Allegati.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this Allegati.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Allegati)) return false;
        Allegati other = (Allegati) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.allegatiCcosts==null && other.getAllegatiCcosts()==null) || 
             (this.allegatiCcosts!=null &&
              java.util.Arrays.equals(this.allegatiCcosts, other.getAllegatiCcosts()))) &&
            ((this.attiDisposizioniAmm==null && other.getAttiDisposizioniAmm()==null) || 
             (this.attiDisposizioniAmm!=null &&
              this.attiDisposizioniAmm.equals(other.getAttiDisposizioniAmm()))) &&
            ((this.contentType==null && other.getContentType()==null) || 
             (this.contentType!=null &&
              this.contentType.equals(other.getContentType()))) &&
            ((this.dataAllegato==null && other.getDataAllegato()==null) || 
             (this.dataAllegato!=null &&
              this.dataAllegato.equals(other.getDataAllegato()))) &&
            ((this.dichiarazioniAmbientali==null && other.getDichiarazioniAmbientali()==null) || 
             (this.dichiarazioniAmbientali!=null &&
              this.dichiarazioniAmbientali.equals(other.getDichiarazioniAmbientali()))) &&
            ((this.formNatura2000==null && other.getFormNatura2000()==null) || 
             (this.formNatura2000!=null &&
              this.formNatura2000.equals(other.getFormNatura2000()))) &&
            ((this.idAllegato==null && other.getIdAllegato()==null) || 
             (this.idAllegato!=null &&
              this.idAllegato.equals(other.getIdAllegato()))) &&
            ((this.nomeFile==null && other.getNomeFile()==null) || 
             (this.nomeFile!=null &&
              this.nomeFile.equals(other.getNomeFile()))) &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.praticheAmministrative==null && other.getPraticheAmministrative()==null) || 
             (this.praticheAmministrative!=null &&
              this.praticheAmministrative.equals(other.getPraticheAmministrative()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento())));
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
        if (getAllegatiCcosts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllegatiCcosts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllegatiCcosts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAttiDisposizioniAmm() != null) {
            _hashCode += getAttiDisposizioniAmm().hashCode();
        }
        if (getContentType() != null) {
            _hashCode += getContentType().hashCode();
        }
        if (getDataAllegato() != null) {
            _hashCode += getDataAllegato().hashCode();
        }
        if (getDichiarazioniAmbientali() != null) {
            _hashCode += getDichiarazioniAmbientali().hashCode();
        }
        if (getFormNatura2000() != null) {
            _hashCode += getFormNatura2000().hashCode();
        }
        if (getIdAllegato() != null) {
            _hashCode += getIdAllegato().hashCode();
        }
        if (getNomeFile() != null) {
            _hashCode += getNomeFile().hashCode();
        }
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getPraticheAmministrative() != null) {
            _hashCode += getPraticheAmministrative().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Allegati.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "allegati"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegatiCcosts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegatiCcosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "allegatiCcost"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attiDisposizioniAmm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attiDisposizioniAmm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataAllegato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataAllegato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dichiarazioniAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dichiarazioniAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dichiarazioniAmbientali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formNatura2000");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formNatura2000"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "formNatura2000"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAllegato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAllegato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeFile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("praticheAmministrative");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministrative"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
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
