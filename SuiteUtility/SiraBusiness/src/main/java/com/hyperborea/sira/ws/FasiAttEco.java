/**
 * FasiAttEco.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class FasiAttEco  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CmpFat[] cmpFats;

    private com.hyperborea.sira.ws.CoenFat[] coenFats;

    private com.hyperborea.sira.ws.CridFat[] cridFats;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.EmissioniNonconv[] emissioniNonconvs;

    private java.lang.Integer idFaseAttEco;

    private com.hyperborea.sira.ws.PemFat[] pemFats;

    private com.hyperborea.sira.ws.ProduzioneEnergia[] produzioneEnergias;

    private com.hyperborea.sira.ws.ProduzioneRifiuti[] produzioneRifiutis;

    private java.lang.String riferimento;

    private java.lang.String rilevante;

    private com.hyperborea.sira.ws.ScarichiParziali[] scarichiParzialis;

    public FasiAttEco() {
    }

    public FasiAttEco(
           com.hyperborea.sira.ws.CmpFat[] cmpFats,
           com.hyperborea.sira.ws.CoenFat[] coenFats,
           com.hyperborea.sira.ws.CridFat[] cridFats,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.EmissioniNonconv[] emissioniNonconvs,
           java.lang.Integer idFaseAttEco,
           com.hyperborea.sira.ws.PemFat[] pemFats,
           com.hyperborea.sira.ws.ProduzioneEnergia[] produzioneEnergias,
           com.hyperborea.sira.ws.ProduzioneRifiuti[] produzioneRifiutis,
           java.lang.String riferimento,
           java.lang.String rilevante,
           com.hyperborea.sira.ws.ScarichiParziali[] scarichiParzialis) {
        this.cmpFats = cmpFats;
        this.coenFats = coenFats;
        this.cridFats = cridFats;
        this.descrizione = descrizione;
        this.emissioniNonconvs = emissioniNonconvs;
        this.idFaseAttEco = idFaseAttEco;
        this.pemFats = pemFats;
        this.produzioneEnergias = produzioneEnergias;
        this.produzioneRifiutis = produzioneRifiutis;
        this.riferimento = riferimento;
        this.rilevante = rilevante;
        this.scarichiParzialis = scarichiParzialis;
    }


    /**
     * Gets the cmpFats value for this FasiAttEco.
     * 
     * @return cmpFats
     */
    public com.hyperborea.sira.ws.CmpFat[] getCmpFats() {
        return cmpFats;
    }


    /**
     * Sets the cmpFats value for this FasiAttEco.
     * 
     * @param cmpFats
     */
    public void setCmpFats(com.hyperborea.sira.ws.CmpFat[] cmpFats) {
        this.cmpFats = cmpFats;
    }

    public com.hyperborea.sira.ws.CmpFat getCmpFats(int i) {
        return this.cmpFats[i];
    }

    public void setCmpFats(int i, com.hyperborea.sira.ws.CmpFat _value) {
        this.cmpFats[i] = _value;
    }


    /**
     * Gets the coenFats value for this FasiAttEco.
     * 
     * @return coenFats
     */
    public com.hyperborea.sira.ws.CoenFat[] getCoenFats() {
        return coenFats;
    }


    /**
     * Sets the coenFats value for this FasiAttEco.
     * 
     * @param coenFats
     */
    public void setCoenFats(com.hyperborea.sira.ws.CoenFat[] coenFats) {
        this.coenFats = coenFats;
    }

    public com.hyperborea.sira.ws.CoenFat getCoenFats(int i) {
        return this.coenFats[i];
    }

    public void setCoenFats(int i, com.hyperborea.sira.ws.CoenFat _value) {
        this.coenFats[i] = _value;
    }


    /**
     * Gets the cridFats value for this FasiAttEco.
     * 
     * @return cridFats
     */
    public com.hyperborea.sira.ws.CridFat[] getCridFats() {
        return cridFats;
    }


    /**
     * Sets the cridFats value for this FasiAttEco.
     * 
     * @param cridFats
     */
    public void setCridFats(com.hyperborea.sira.ws.CridFat[] cridFats) {
        this.cridFats = cridFats;
    }

    public com.hyperborea.sira.ws.CridFat getCridFats(int i) {
        return this.cridFats[i];
    }

    public void setCridFats(int i, com.hyperborea.sira.ws.CridFat _value) {
        this.cridFats[i] = _value;
    }


    /**
     * Gets the descrizione value for this FasiAttEco.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this FasiAttEco.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the emissioniNonconvs value for this FasiAttEco.
     * 
     * @return emissioniNonconvs
     */
    public com.hyperborea.sira.ws.EmissioniNonconv[] getEmissioniNonconvs() {
        return emissioniNonconvs;
    }


    /**
     * Sets the emissioniNonconvs value for this FasiAttEco.
     * 
     * @param emissioniNonconvs
     */
    public void setEmissioniNonconvs(com.hyperborea.sira.ws.EmissioniNonconv[] emissioniNonconvs) {
        this.emissioniNonconvs = emissioniNonconvs;
    }

    public com.hyperborea.sira.ws.EmissioniNonconv getEmissioniNonconvs(int i) {
        return this.emissioniNonconvs[i];
    }

    public void setEmissioniNonconvs(int i, com.hyperborea.sira.ws.EmissioniNonconv _value) {
        this.emissioniNonconvs[i] = _value;
    }


    /**
     * Gets the idFaseAttEco value for this FasiAttEco.
     * 
     * @return idFaseAttEco
     */
    public java.lang.Integer getIdFaseAttEco() {
        return idFaseAttEco;
    }


    /**
     * Sets the idFaseAttEco value for this FasiAttEco.
     * 
     * @param idFaseAttEco
     */
    public void setIdFaseAttEco(java.lang.Integer idFaseAttEco) {
        this.idFaseAttEco = idFaseAttEco;
    }


    /**
     * Gets the pemFats value for this FasiAttEco.
     * 
     * @return pemFats
     */
    public com.hyperborea.sira.ws.PemFat[] getPemFats() {
        return pemFats;
    }


    /**
     * Sets the pemFats value for this FasiAttEco.
     * 
     * @param pemFats
     */
    public void setPemFats(com.hyperborea.sira.ws.PemFat[] pemFats) {
        this.pemFats = pemFats;
    }

    public com.hyperborea.sira.ws.PemFat getPemFats(int i) {
        return this.pemFats[i];
    }

    public void setPemFats(int i, com.hyperborea.sira.ws.PemFat _value) {
        this.pemFats[i] = _value;
    }


    /**
     * Gets the produzioneEnergias value for this FasiAttEco.
     * 
     * @return produzioneEnergias
     */
    public com.hyperborea.sira.ws.ProduzioneEnergia[] getProduzioneEnergias() {
        return produzioneEnergias;
    }


    /**
     * Sets the produzioneEnergias value for this FasiAttEco.
     * 
     * @param produzioneEnergias
     */
    public void setProduzioneEnergias(com.hyperborea.sira.ws.ProduzioneEnergia[] produzioneEnergias) {
        this.produzioneEnergias = produzioneEnergias;
    }

    public com.hyperborea.sira.ws.ProduzioneEnergia getProduzioneEnergias(int i) {
        return this.produzioneEnergias[i];
    }

    public void setProduzioneEnergias(int i, com.hyperborea.sira.ws.ProduzioneEnergia _value) {
        this.produzioneEnergias[i] = _value;
    }


    /**
     * Gets the produzioneRifiutis value for this FasiAttEco.
     * 
     * @return produzioneRifiutis
     */
    public com.hyperborea.sira.ws.ProduzioneRifiuti[] getProduzioneRifiutis() {
        return produzioneRifiutis;
    }


    /**
     * Sets the produzioneRifiutis value for this FasiAttEco.
     * 
     * @param produzioneRifiutis
     */
    public void setProduzioneRifiutis(com.hyperborea.sira.ws.ProduzioneRifiuti[] produzioneRifiutis) {
        this.produzioneRifiutis = produzioneRifiutis;
    }

    public com.hyperborea.sira.ws.ProduzioneRifiuti getProduzioneRifiutis(int i) {
        return this.produzioneRifiutis[i];
    }

    public void setProduzioneRifiutis(int i, com.hyperborea.sira.ws.ProduzioneRifiuti _value) {
        this.produzioneRifiutis[i] = _value;
    }


    /**
     * Gets the riferimento value for this FasiAttEco.
     * 
     * @return riferimento
     */
    public java.lang.String getRiferimento() {
        return riferimento;
    }


    /**
     * Sets the riferimento value for this FasiAttEco.
     * 
     * @param riferimento
     */
    public void setRiferimento(java.lang.String riferimento) {
        this.riferimento = riferimento;
    }


    /**
     * Gets the rilevante value for this FasiAttEco.
     * 
     * @return rilevante
     */
    public java.lang.String getRilevante() {
        return rilevante;
    }


    /**
     * Sets the rilevante value for this FasiAttEco.
     * 
     * @param rilevante
     */
    public void setRilevante(java.lang.String rilevante) {
        this.rilevante = rilevante;
    }


    /**
     * Gets the scarichiParzialis value for this FasiAttEco.
     * 
     * @return scarichiParzialis
     */
    public com.hyperborea.sira.ws.ScarichiParziali[] getScarichiParzialis() {
        return scarichiParzialis;
    }


    /**
     * Sets the scarichiParzialis value for this FasiAttEco.
     * 
     * @param scarichiParzialis
     */
    public void setScarichiParzialis(com.hyperborea.sira.ws.ScarichiParziali[] scarichiParzialis) {
        this.scarichiParzialis = scarichiParzialis;
    }

    public com.hyperborea.sira.ws.ScarichiParziali getScarichiParzialis(int i) {
        return this.scarichiParzialis[i];
    }

    public void setScarichiParzialis(int i, com.hyperborea.sira.ws.ScarichiParziali _value) {
        this.scarichiParzialis[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FasiAttEco)) return false;
        FasiAttEco other = (FasiAttEco) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cmpFats==null && other.getCmpFats()==null) || 
             (this.cmpFats!=null &&
              java.util.Arrays.equals(this.cmpFats, other.getCmpFats()))) &&
            ((this.coenFats==null && other.getCoenFats()==null) || 
             (this.coenFats!=null &&
              java.util.Arrays.equals(this.coenFats, other.getCoenFats()))) &&
            ((this.cridFats==null && other.getCridFats()==null) || 
             (this.cridFats!=null &&
              java.util.Arrays.equals(this.cridFats, other.getCridFats()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.emissioniNonconvs==null && other.getEmissioniNonconvs()==null) || 
             (this.emissioniNonconvs!=null &&
              java.util.Arrays.equals(this.emissioniNonconvs, other.getEmissioniNonconvs()))) &&
            ((this.idFaseAttEco==null && other.getIdFaseAttEco()==null) || 
             (this.idFaseAttEco!=null &&
              this.idFaseAttEco.equals(other.getIdFaseAttEco()))) &&
            ((this.pemFats==null && other.getPemFats()==null) || 
             (this.pemFats!=null &&
              java.util.Arrays.equals(this.pemFats, other.getPemFats()))) &&
            ((this.produzioneEnergias==null && other.getProduzioneEnergias()==null) || 
             (this.produzioneEnergias!=null &&
              java.util.Arrays.equals(this.produzioneEnergias, other.getProduzioneEnergias()))) &&
            ((this.produzioneRifiutis==null && other.getProduzioneRifiutis()==null) || 
             (this.produzioneRifiutis!=null &&
              java.util.Arrays.equals(this.produzioneRifiutis, other.getProduzioneRifiutis()))) &&
            ((this.riferimento==null && other.getRiferimento()==null) || 
             (this.riferimento!=null &&
              this.riferimento.equals(other.getRiferimento()))) &&
            ((this.rilevante==null && other.getRilevante()==null) || 
             (this.rilevante!=null &&
              this.rilevante.equals(other.getRilevante()))) &&
            ((this.scarichiParzialis==null && other.getScarichiParzialis()==null) || 
             (this.scarichiParzialis!=null &&
              java.util.Arrays.equals(this.scarichiParzialis, other.getScarichiParzialis())));
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
        if (getCmpFats() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCmpFats());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCmpFats(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCoenFats() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCoenFats());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCoenFats(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCridFats() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCridFats());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCridFats(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getEmissioniNonconvs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEmissioniNonconvs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEmissioniNonconvs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdFaseAttEco() != null) {
            _hashCode += getIdFaseAttEco().hashCode();
        }
        if (getPemFats() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPemFats());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPemFats(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProduzioneEnergias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProduzioneEnergias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProduzioneEnergias(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProduzioneRifiutis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProduzioneRifiutis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProduzioneRifiutis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRiferimento() != null) {
            _hashCode += getRiferimento().hashCode();
        }
        if (getRilevante() != null) {
            _hashCode += getRilevante().hashCode();
        }
        if (getScarichiParzialis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getScarichiParzialis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getScarichiParzialis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FasiAttEco.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasiAttEco"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cmpFats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cmpFats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cmpFat"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coenFats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coenFats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "coenFat"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cridFats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cridFats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cridFat"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emissioniNonconvs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emissioniNonconvs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniNonconv"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFaseAttEco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFaseAttEco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pemFats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pemFats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pemFat"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produzioneEnergias");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzioneEnergias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioneEnergia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produzioneRifiutis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzioneRifiutis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioneRifiuti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rilevante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rilevante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scarichiParzialis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scarichiParzialis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scarichiParziali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
