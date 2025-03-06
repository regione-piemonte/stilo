/**
 * ColLitostratigrafica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ColLitostratigrafica  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String carattLitologiche;

    private java.lang.Float coeffPermeabilita;

    private java.lang.Integer idStratoColonna;

    private java.lang.Integer numStrato;

    private java.lang.Float porosita;

    private java.lang.Float profonditaSubs;

    private java.lang.Float profonditaTetto;

    private java.lang.String strutturaStrato;

    public ColLitostratigrafica() {
    }

    public ColLitostratigrafica(
           java.lang.String carattLitologiche,
           java.lang.Float coeffPermeabilita,
           java.lang.Integer idStratoColonna,
           java.lang.Integer numStrato,
           java.lang.Float porosita,
           java.lang.Float profonditaSubs,
           java.lang.Float profonditaTetto,
           java.lang.String strutturaStrato) {
        this.carattLitologiche = carattLitologiche;
        this.coeffPermeabilita = coeffPermeabilita;
        this.idStratoColonna = idStratoColonna;
        this.numStrato = numStrato;
        this.porosita = porosita;
        this.profonditaSubs = profonditaSubs;
        this.profonditaTetto = profonditaTetto;
        this.strutturaStrato = strutturaStrato;
    }


    /**
     * Gets the carattLitologiche value for this ColLitostratigrafica.
     * 
     * @return carattLitologiche
     */
    public java.lang.String getCarattLitologiche() {
        return carattLitologiche;
    }


    /**
     * Sets the carattLitologiche value for this ColLitostratigrafica.
     * 
     * @param carattLitologiche
     */
    public void setCarattLitologiche(java.lang.String carattLitologiche) {
        this.carattLitologiche = carattLitologiche;
    }


    /**
     * Gets the coeffPermeabilita value for this ColLitostratigrafica.
     * 
     * @return coeffPermeabilita
     */
    public java.lang.Float getCoeffPermeabilita() {
        return coeffPermeabilita;
    }


    /**
     * Sets the coeffPermeabilita value for this ColLitostratigrafica.
     * 
     * @param coeffPermeabilita
     */
    public void setCoeffPermeabilita(java.lang.Float coeffPermeabilita) {
        this.coeffPermeabilita = coeffPermeabilita;
    }


    /**
     * Gets the idStratoColonna value for this ColLitostratigrafica.
     * 
     * @return idStratoColonna
     */
    public java.lang.Integer getIdStratoColonna() {
        return idStratoColonna;
    }


    /**
     * Sets the idStratoColonna value for this ColLitostratigrafica.
     * 
     * @param idStratoColonna
     */
    public void setIdStratoColonna(java.lang.Integer idStratoColonna) {
        this.idStratoColonna = idStratoColonna;
    }


    /**
     * Gets the numStrato value for this ColLitostratigrafica.
     * 
     * @return numStrato
     */
    public java.lang.Integer getNumStrato() {
        return numStrato;
    }


    /**
     * Sets the numStrato value for this ColLitostratigrafica.
     * 
     * @param numStrato
     */
    public void setNumStrato(java.lang.Integer numStrato) {
        this.numStrato = numStrato;
    }


    /**
     * Gets the porosita value for this ColLitostratigrafica.
     * 
     * @return porosita
     */
    public java.lang.Float getPorosita() {
        return porosita;
    }


    /**
     * Sets the porosita value for this ColLitostratigrafica.
     * 
     * @param porosita
     */
    public void setPorosita(java.lang.Float porosita) {
        this.porosita = porosita;
    }


    /**
     * Gets the profonditaSubs value for this ColLitostratigrafica.
     * 
     * @return profonditaSubs
     */
    public java.lang.Float getProfonditaSubs() {
        return profonditaSubs;
    }


    /**
     * Sets the profonditaSubs value for this ColLitostratigrafica.
     * 
     * @param profonditaSubs
     */
    public void setProfonditaSubs(java.lang.Float profonditaSubs) {
        this.profonditaSubs = profonditaSubs;
    }


    /**
     * Gets the profonditaTetto value for this ColLitostratigrafica.
     * 
     * @return profonditaTetto
     */
    public java.lang.Float getProfonditaTetto() {
        return profonditaTetto;
    }


    /**
     * Sets the profonditaTetto value for this ColLitostratigrafica.
     * 
     * @param profonditaTetto
     */
    public void setProfonditaTetto(java.lang.Float profonditaTetto) {
        this.profonditaTetto = profonditaTetto;
    }


    /**
     * Gets the strutturaStrato value for this ColLitostratigrafica.
     * 
     * @return strutturaStrato
     */
    public java.lang.String getStrutturaStrato() {
        return strutturaStrato;
    }


    /**
     * Sets the strutturaStrato value for this ColLitostratigrafica.
     * 
     * @param strutturaStrato
     */
    public void setStrutturaStrato(java.lang.String strutturaStrato) {
        this.strutturaStrato = strutturaStrato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ColLitostratigrafica)) return false;
        ColLitostratigrafica other = (ColLitostratigrafica) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.carattLitologiche==null && other.getCarattLitologiche()==null) || 
             (this.carattLitologiche!=null &&
              this.carattLitologiche.equals(other.getCarattLitologiche()))) &&
            ((this.coeffPermeabilita==null && other.getCoeffPermeabilita()==null) || 
             (this.coeffPermeabilita!=null &&
              this.coeffPermeabilita.equals(other.getCoeffPermeabilita()))) &&
            ((this.idStratoColonna==null && other.getIdStratoColonna()==null) || 
             (this.idStratoColonna!=null &&
              this.idStratoColonna.equals(other.getIdStratoColonna()))) &&
            ((this.numStrato==null && other.getNumStrato()==null) || 
             (this.numStrato!=null &&
              this.numStrato.equals(other.getNumStrato()))) &&
            ((this.porosita==null && other.getPorosita()==null) || 
             (this.porosita!=null &&
              this.porosita.equals(other.getPorosita()))) &&
            ((this.profonditaSubs==null && other.getProfonditaSubs()==null) || 
             (this.profonditaSubs!=null &&
              this.profonditaSubs.equals(other.getProfonditaSubs()))) &&
            ((this.profonditaTetto==null && other.getProfonditaTetto()==null) || 
             (this.profonditaTetto!=null &&
              this.profonditaTetto.equals(other.getProfonditaTetto()))) &&
            ((this.strutturaStrato==null && other.getStrutturaStrato()==null) || 
             (this.strutturaStrato!=null &&
              this.strutturaStrato.equals(other.getStrutturaStrato())));
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
        if (getCarattLitologiche() != null) {
            _hashCode += getCarattLitologiche().hashCode();
        }
        if (getCoeffPermeabilita() != null) {
            _hashCode += getCoeffPermeabilita().hashCode();
        }
        if (getIdStratoColonna() != null) {
            _hashCode += getIdStratoColonna().hashCode();
        }
        if (getNumStrato() != null) {
            _hashCode += getNumStrato().hashCode();
        }
        if (getPorosita() != null) {
            _hashCode += getPorosita().hashCode();
        }
        if (getProfonditaSubs() != null) {
            _hashCode += getProfonditaSubs().hashCode();
        }
        if (getProfonditaTetto() != null) {
            _hashCode += getProfonditaTetto().hashCode();
        }
        if (getStrutturaStrato() != null) {
            _hashCode += getStrutturaStrato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ColLitostratigrafica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "colLitostratigrafica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carattLitologiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "carattLitologiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coeffPermeabilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coeffPermeabilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idStratoColonna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idStratoColonna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numStrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numStrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("porosita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "porosita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profonditaSubs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profonditaSubs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profonditaTetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profonditaTetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strutturaStrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strutturaStrato"));
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
