/**
 * FasiProcedimenti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class FasiProcedimenti  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer attivo;

    private java.lang.Integer blocco;

    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.NormeLeggi normeLeggi;

    private java.lang.Integer progressivo;

    private com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi;

    public FasiProcedimenti() {
    }

    public FasiProcedimenti(
           java.lang.Integer attivo,
           java.lang.Integer blocco,
           java.lang.Integer codice,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.NormeLeggi normeLeggi,
           java.lang.Integer progressivo,
           com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi) {
        this.attivo = attivo;
        this.blocco = blocco;
        this.codice = codice;
        this.descrizione = descrizione;
        this.normeLeggi = normeLeggi;
        this.progressivo = progressivo;
        this.riferimentiNormativi = riferimentiNormativi;
    }


    /**
     * Gets the attivo value for this FasiProcedimenti.
     * 
     * @return attivo
     */
    public java.lang.Integer getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this FasiProcedimenti.
     * 
     * @param attivo
     */
    public void setAttivo(java.lang.Integer attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the blocco value for this FasiProcedimenti.
     * 
     * @return blocco
     */
    public java.lang.Integer getBlocco() {
        return blocco;
    }


    /**
     * Sets the blocco value for this FasiProcedimenti.
     * 
     * @param blocco
     */
    public void setBlocco(java.lang.Integer blocco) {
        this.blocco = blocco;
    }


    /**
     * Gets the codice value for this FasiProcedimenti.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this FasiProcedimenti.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this FasiProcedimenti.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this FasiProcedimenti.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the normeLeggi value for this FasiProcedimenti.
     * 
     * @return normeLeggi
     */
    public com.hyperborea.sira.ws.NormeLeggi getNormeLeggi() {
        return normeLeggi;
    }


    /**
     * Sets the normeLeggi value for this FasiProcedimenti.
     * 
     * @param normeLeggi
     */
    public void setNormeLeggi(com.hyperborea.sira.ws.NormeLeggi normeLeggi) {
        this.normeLeggi = normeLeggi;
    }


    /**
     * Gets the progressivo value for this FasiProcedimenti.
     * 
     * @return progressivo
     */
    public java.lang.Integer getProgressivo() {
        return progressivo;
    }


    /**
     * Sets the progressivo value for this FasiProcedimenti.
     * 
     * @param progressivo
     */
    public void setProgressivo(java.lang.Integer progressivo) {
        this.progressivo = progressivo;
    }


    /**
     * Gets the riferimentiNormativi value for this FasiProcedimenti.
     * 
     * @return riferimentiNormativi
     */
    public com.hyperborea.sira.ws.RiferimentiNormativi getRiferimentiNormativi() {
        return riferimentiNormativi;
    }


    /**
     * Sets the riferimentiNormativi value for this FasiProcedimenti.
     * 
     * @param riferimentiNormativi
     */
    public void setRiferimentiNormativi(com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi) {
        this.riferimentiNormativi = riferimentiNormativi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FasiProcedimenti)) return false;
        FasiProcedimenti other = (FasiProcedimenti) obj;
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
            ((this.normeLeggi==null && other.getNormeLeggi()==null) || 
             (this.normeLeggi!=null &&
              this.normeLeggi.equals(other.getNormeLeggi()))) &&
            ((this.progressivo==null && other.getProgressivo()==null) || 
             (this.progressivo!=null &&
              this.progressivo.equals(other.getProgressivo()))) &&
            ((this.riferimentiNormativi==null && other.getRiferimentiNormativi()==null) || 
             (this.riferimentiNormativi!=null &&
              this.riferimentiNormativi.equals(other.getRiferimentiNormativi())));
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
        if (getNormeLeggi() != null) {
            _hashCode += getNormeLeggi().hashCode();
        }
        if (getProgressivo() != null) {
            _hashCode += getProgressivo().hashCode();
        }
        if (getRiferimentiNormativi() != null) {
            _hashCode += getRiferimentiNormativi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FasiProcedimenti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasiProcedimenti"));
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
        elemField.setFieldName("normeLeggi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "normeLeggi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "normeLeggi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("progressivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "progressivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
