/**
 * MaterialiConduttori.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MaterialiConduttori  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private int attivo;

    private int blocco;

    private com.hyperborea.sira.ws.CcostTratteEd[] ccostTratteEds;

    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private java.lang.Float diametro;

    private java.lang.String sezione;

    public MaterialiConduttori() {
    }

    public MaterialiConduttori(
           int attivo,
           int blocco,
           com.hyperborea.sira.ws.CcostTratteEd[] ccostTratteEds,
           java.lang.Integer codice,
           java.lang.String descrizione,
           java.lang.Float diametro,
           java.lang.String sezione) {
        this.attivo = attivo;
        this.blocco = blocco;
        this.ccostTratteEds = ccostTratteEds;
        this.codice = codice;
        this.descrizione = descrizione;
        this.diametro = diametro;
        this.sezione = sezione;
    }


    /**
     * Gets the attivo value for this MaterialiConduttori.
     * 
     * @return attivo
     */
    public int getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this MaterialiConduttori.
     * 
     * @param attivo
     */
    public void setAttivo(int attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the blocco value for this MaterialiConduttori.
     * 
     * @return blocco
     */
    public int getBlocco() {
        return blocco;
    }


    /**
     * Sets the blocco value for this MaterialiConduttori.
     * 
     * @param blocco
     */
    public void setBlocco(int blocco) {
        this.blocco = blocco;
    }


    /**
     * Gets the ccostTratteEds value for this MaterialiConduttori.
     * 
     * @return ccostTratteEds
     */
    public com.hyperborea.sira.ws.CcostTratteEd[] getCcostTratteEds() {
        return ccostTratteEds;
    }


    /**
     * Sets the ccostTratteEds value for this MaterialiConduttori.
     * 
     * @param ccostTratteEds
     */
    public void setCcostTratteEds(com.hyperborea.sira.ws.CcostTratteEd[] ccostTratteEds) {
        this.ccostTratteEds = ccostTratteEds;
    }

    public com.hyperborea.sira.ws.CcostTratteEd getCcostTratteEds(int i) {
        return this.ccostTratteEds[i];
    }

    public void setCcostTratteEds(int i, com.hyperborea.sira.ws.CcostTratteEd _value) {
        this.ccostTratteEds[i] = _value;
    }


    /**
     * Gets the codice value for this MaterialiConduttori.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this MaterialiConduttori.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this MaterialiConduttori.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this MaterialiConduttori.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the diametro value for this MaterialiConduttori.
     * 
     * @return diametro
     */
    public java.lang.Float getDiametro() {
        return diametro;
    }


    /**
     * Sets the diametro value for this MaterialiConduttori.
     * 
     * @param diametro
     */
    public void setDiametro(java.lang.Float diametro) {
        this.diametro = diametro;
    }


    /**
     * Gets the sezione value for this MaterialiConduttori.
     * 
     * @return sezione
     */
    public java.lang.String getSezione() {
        return sezione;
    }


    /**
     * Sets the sezione value for this MaterialiConduttori.
     * 
     * @param sezione
     */
    public void setSezione(java.lang.String sezione) {
        this.sezione = sezione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MaterialiConduttori)) return false;
        MaterialiConduttori other = (MaterialiConduttori) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.attivo == other.getAttivo() &&
            this.blocco == other.getBlocco() &&
            ((this.ccostTratteEds==null && other.getCcostTratteEds()==null) || 
             (this.ccostTratteEds!=null &&
              java.util.Arrays.equals(this.ccostTratteEds, other.getCcostTratteEds()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.diametro==null && other.getDiametro()==null) || 
             (this.diametro!=null &&
              this.diametro.equals(other.getDiametro()))) &&
            ((this.sezione==null && other.getSezione()==null) || 
             (this.sezione!=null &&
              this.sezione.equals(other.getSezione())));
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
        _hashCode += getAttivo();
        _hashCode += getBlocco();
        if (getCcostTratteEds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCcostTratteEds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCcostTratteEds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDiametro() != null) {
            _hashCode += getDiametro().hashCode();
        }
        if (getSezione() != null) {
            _hashCode += getSezione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MaterialiConduttori.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialiConduttori"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blocco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "blocco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostTratteEds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostTratteEds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostTratteEd"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diametro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diametro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sezione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sezione"));
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
