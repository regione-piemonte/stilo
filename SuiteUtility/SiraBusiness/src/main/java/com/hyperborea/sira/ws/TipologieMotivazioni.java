/**
 * TipologieMotivazioni.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipologieMotivazioni  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer attivo;

    private java.lang.Integer blocco;

    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives;

    private com.hyperborea.sira.ws.RaggruppamentiAta raggruppamentiAta;

    private com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi;

    private com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni;

    public TipologieMotivazioni() {
    }

    public TipologieMotivazioni(
           java.lang.Integer attivo,
           java.lang.Integer blocco,
           java.lang.Integer codice,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives,
           com.hyperborea.sira.ws.RaggruppamentiAta raggruppamentiAta,
           com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi,
           com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni) {
        this.attivo = attivo;
        this.blocco = blocco;
        this.codice = codice;
        this.descrizione = descrizione;
        this.praticheAmministratives = praticheAmministratives;
        this.raggruppamentiAta = raggruppamentiAta;
        this.riferimentiNormativi = riferimentiNormativi;
        this.tipologieFunzioni = tipologieFunzioni;
    }


    /**
     * Gets the attivo value for this TipologieMotivazioni.
     * 
     * @return attivo
     */
    public java.lang.Integer getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this TipologieMotivazioni.
     * 
     * @param attivo
     */
    public void setAttivo(java.lang.Integer attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the blocco value for this TipologieMotivazioni.
     * 
     * @return blocco
     */
    public java.lang.Integer getBlocco() {
        return blocco;
    }


    /**
     * Sets the blocco value for this TipologieMotivazioni.
     * 
     * @param blocco
     */
    public void setBlocco(java.lang.Integer blocco) {
        this.blocco = blocco;
    }


    /**
     * Gets the codice value for this TipologieMotivazioni.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this TipologieMotivazioni.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this TipologieMotivazioni.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this TipologieMotivazioni.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the praticheAmministratives value for this TipologieMotivazioni.
     * 
     * @return praticheAmministratives
     */
    public com.hyperborea.sira.ws.PraticheAmministrative[] getPraticheAmministratives() {
        return praticheAmministratives;
    }


    /**
     * Sets the praticheAmministratives value for this TipologieMotivazioni.
     * 
     * @param praticheAmministratives
     */
    public void setPraticheAmministratives(com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives) {
        this.praticheAmministratives = praticheAmministratives;
    }

    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministratives(int i) {
        return this.praticheAmministratives[i];
    }

    public void setPraticheAmministratives(int i, com.hyperborea.sira.ws.PraticheAmministrative _value) {
        this.praticheAmministratives[i] = _value;
    }


    /**
     * Gets the raggruppamentiAta value for this TipologieMotivazioni.
     * 
     * @return raggruppamentiAta
     */
    public com.hyperborea.sira.ws.RaggruppamentiAta getRaggruppamentiAta() {
        return raggruppamentiAta;
    }


    /**
     * Sets the raggruppamentiAta value for this TipologieMotivazioni.
     * 
     * @param raggruppamentiAta
     */
    public void setRaggruppamentiAta(com.hyperborea.sira.ws.RaggruppamentiAta raggruppamentiAta) {
        this.raggruppamentiAta = raggruppamentiAta;
    }


    /**
     * Gets the riferimentiNormativi value for this TipologieMotivazioni.
     * 
     * @return riferimentiNormativi
     */
    public com.hyperborea.sira.ws.RiferimentiNormativi getRiferimentiNormativi() {
        return riferimentiNormativi;
    }


    /**
     * Sets the riferimentiNormativi value for this TipologieMotivazioni.
     * 
     * @param riferimentiNormativi
     */
    public void setRiferimentiNormativi(com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi) {
        this.riferimentiNormativi = riferimentiNormativi;
    }


    /**
     * Gets the tipologieFunzioni value for this TipologieMotivazioni.
     * 
     * @return tipologieFunzioni
     */
    public com.hyperborea.sira.ws.TipologieFunzioni getTipologieFunzioni() {
        return tipologieFunzioni;
    }


    /**
     * Sets the tipologieFunzioni value for this TipologieMotivazioni.
     * 
     * @param tipologieFunzioni
     */
    public void setTipologieFunzioni(com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni) {
        this.tipologieFunzioni = tipologieFunzioni;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipologieMotivazioni)) return false;
        TipologieMotivazioni other = (TipologieMotivazioni) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attivo==null && other.getAttivo()==null) || 
             (this.attivo!=null &&
              this.attivo.equals(other.getAttivo()))) &&
            ((this.blocco==null && other.getBlocco()==null) || 
             (this.blocco!=null &&
              this.blocco.equals(other.getBlocco()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.praticheAmministratives==null && other.getPraticheAmministratives()==null) || 
             (this.praticheAmministratives!=null &&
              java.util.Arrays.equals(this.praticheAmministratives, other.getPraticheAmministratives()))) &&
            ((this.raggruppamentiAta==null && other.getRaggruppamentiAta()==null) || 
             (this.raggruppamentiAta!=null &&
              this.raggruppamentiAta.equals(other.getRaggruppamentiAta()))) &&
            ((this.riferimentiNormativi==null && other.getRiferimentiNormativi()==null) || 
             (this.riferimentiNormativi!=null &&
              this.riferimentiNormativi.equals(other.getRiferimentiNormativi()))) &&
            ((this.tipologieFunzioni==null && other.getTipologieFunzioni()==null) || 
             (this.tipologieFunzioni!=null &&
              this.tipologieFunzioni.equals(other.getTipologieFunzioni())));
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
        if (getAttivo() != null) {
            _hashCode += getAttivo().hashCode();
        }
        if (getBlocco() != null) {
            _hashCode += getBlocco().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getPraticheAmministratives() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPraticheAmministratives());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPraticheAmministratives(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRaggruppamentiAta() != null) {
            _hashCode += getRaggruppamentiAta().hashCode();
        }
        if (getRiferimentiNormativi() != null) {
            _hashCode += getRiferimentiNormativi().hashCode();
        }
        if (getTipologieFunzioni() != null) {
            _hashCode += getTipologieFunzioni().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipologieMotivazioni.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieMotivazioni"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blocco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "blocco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("praticheAmministratives");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministratives"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("raggruppamentiAta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "raggruppamentiAta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "raggruppamentiAta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentiNormativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentiNormativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "riferimentiNormativi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieFunzioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieFunzioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieFunzioni"));
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
