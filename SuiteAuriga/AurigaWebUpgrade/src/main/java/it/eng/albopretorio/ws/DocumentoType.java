/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * DocumentoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.albopretorio.ws;

import java.util.List;

import it.eng.albopretorio.bean.AlboPretorioAttachBean;

public class DocumentoType  implements java.io.Serializable {
	
    private java.lang.String protocollo;

    private java.lang.String numeroDocumento;

    private java.lang.Integer annoDocumento;

    private java.util.Date dataDocumento;

    private java.lang.String oggetto;

    private java.lang.String settore;

    private java.util.Date dataInizioEsposizione;

    private java.util.Date dataFineEsposizione;

    private java.lang.String nomeFile;

    private java.lang.String username;

    private int tipoDocumento;

    private java.lang.String enteProvenienza;

    private java.lang.String note;
    
    private List<AlboPretorioAttachBean> allegati;

    public DocumentoType() {}

    public DocumentoType(
           java.lang.String protocollo,
           java.lang.String numeroDocumento,
           java.lang.Integer annoDocumento,
           java.util.Date dataDocumento,
           java.lang.String oggetto,
           java.lang.String settore,
           java.util.Date dataInizioEsposizione,
           java.util.Date dataFineEsposizione,
           java.lang.String nomeFile,
           java.lang.String username,
           int tipoDocumento,
           java.lang.String enteProvenienza,
           java.lang.String note,
           List<AlboPretorioAttachBean> allegati) {
           this.protocollo = protocollo;
           this.numeroDocumento = numeroDocumento;
           this.annoDocumento = annoDocumento;
           this.dataDocumento = dataDocumento;
           this.oggetto = oggetto;
           this.settore = settore;
           this.dataInizioEsposizione = dataInizioEsposizione;
           this.dataFineEsposizione = dataFineEsposizione;
           this.nomeFile = nomeFile;
           this.username = username;
           this.tipoDocumento = tipoDocumento;
           this.enteProvenienza = enteProvenienza;
           this.note = note;
           this.allegati = allegati;
    }


    /**
     * Gets the protocollo value for this DocumentoType.
     * 
     * @return protocollo
     */
    public java.lang.String getProtocollo() {
        return protocollo;
    }


    /**
     * Sets the protocollo value for this DocumentoType.
     * 
     * @param protocollo
     */
    public void setProtocollo(java.lang.String protocollo) {
        this.protocollo = protocollo;
    }


    /**
     * Gets the numeroDocumento value for this DocumentoType.
     * 
     * @return numeroDocumento
     */
    public java.lang.String getNumeroDocumento() {
        return numeroDocumento;
    }


    /**
     * Sets the numeroDocumento value for this DocumentoType.
     * 
     * @param numeroDocumento
     */
    public void setNumeroDocumento(java.lang.String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }


    /**
     * Gets the annoDocumento value for this DocumentoType.
     * 
     * @return annoDocumento
     */
    public java.lang.Integer getAnnoDocumento() {
        return annoDocumento;
    }


    /**
     * Sets the annoDocumento value for this DocumentoType.
     * 
     * @param annoDocumento
     */
    public void setAnnoDocumento(java.lang.Integer annoDocumento) {
        this.annoDocumento = annoDocumento;
    }


    /**
     * Gets the dataDocumento value for this DocumentoType.
     * 
     * @return dataDocumento
     */
    public java.util.Date getDataDocumento() {
        return dataDocumento;
    }


    /**
     * Sets the dataDocumento value for this DocumentoType.
     * 
     * @param dataDocumento
     */
    public void setDataDocumento(java.util.Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }


    /**
     * Gets the oggetto value for this DocumentoType.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this DocumentoType.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the settore value for this DocumentoType.
     * 
     * @return settore
     */
    public java.lang.String getSettore() {
        return settore;
    }


    /**
     * Sets the settore value for this DocumentoType.
     * 
     * @param settore
     */
    public void setSettore(java.lang.String settore) {
        this.settore = settore;
    }


    /**
     * Gets the dataInizioEsposizione value for this DocumentoType.
     * 
     * @return dataInizioEsposizione
     */
    public java.util.Date getDataInizioEsposizione() {
        return dataInizioEsposizione;
    }


    /**
     * Sets the dataInizioEsposizione value for this DocumentoType.
     * 
     * @param dataInizioEsposizione
     */
    public void setDataInizioEsposizione(java.util.Date dataInizioEsposizione) {
        this.dataInizioEsposizione = dataInizioEsposizione;
    }


    /**
     * Gets the dataFineEsposizione value for this DocumentoType.
     * 
     * @return dataFineEsposizione
     */
    public java.util.Date getDataFineEsposizione() {
        return dataFineEsposizione;
    }


    /**
     * Sets the dataFineEsposizione value for this DocumentoType.
     * 
     * @param dataFineEsposizione
     */
    public void setDataFineEsposizione(java.util.Date dataFineEsposizione) {
        this.dataFineEsposizione = dataFineEsposizione;
    }


    /**
     * Gets the nomeFile value for this DocumentoType.
     * 
     * @return nomeFile
     */
    public java.lang.String getNomeFile() {
        return nomeFile;
    }


    /**
     * Sets the nomeFile value for this DocumentoType.
     * 
     * @param nomeFile
     */
    public void setNomeFile(java.lang.String nomeFile) {
        this.nomeFile = nomeFile;
    }


    /**
     * Gets the username value for this DocumentoType.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this DocumentoType.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the tipoDocumento value for this DocumentoType.
     * 
     * @return tipoDocumento
     */
    public int getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this DocumentoType.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(int tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the enteProvenienza value for this DocumentoType.
     * 
     * @return enteProvenienza
     */
    public java.lang.String getEnteProvenienza() {
        return enteProvenienza;
    }


    /**
     * Sets the enteProvenienza value for this DocumentoType.
     * 
     * @param enteProvenienza
     */
    public void setEnteProvenienza(java.lang.String enteProvenienza) {
        this.enteProvenienza = enteProvenienza;
    }


    /**
     * Gets the note value for this DocumentoType.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this DocumentoType.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }

    public List<AlboPretorioAttachBean> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AlboPretorioAttachBean> allegati) {
		this.allegati = allegati;
	}

	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoType)) return false;
        DocumentoType other = (DocumentoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.protocollo==null && other.getProtocollo()==null) || 
             (this.protocollo!=null &&
              this.protocollo.equals(other.getProtocollo()))) &&
            ((this.numeroDocumento==null && other.getNumeroDocumento()==null) || 
             (this.numeroDocumento!=null &&
              this.numeroDocumento.equals(other.getNumeroDocumento()))) &&
            ((this.annoDocumento==null && other.getAnnoDocumento()==null) || 
             (this.annoDocumento!=null &&
              this.annoDocumento.equals(other.getAnnoDocumento()))) &&
            ((this.dataDocumento==null && other.getDataDocumento()==null) || 
             (this.dataDocumento!=null &&
              this.dataDocumento.equals(other.getDataDocumento()))) &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.settore==null && other.getSettore()==null) || 
             (this.settore!=null &&
              this.settore.equals(other.getSettore()))) &&
            ((this.dataInizioEsposizione==null && other.getDataInizioEsposizione()==null) || 
             (this.dataInizioEsposizione!=null &&
              this.dataInizioEsposizione.equals(other.getDataInizioEsposizione()))) &&
            ((this.dataFineEsposizione==null && other.getDataFineEsposizione()==null) || 
             (this.dataFineEsposizione!=null &&
              this.dataFineEsposizione.equals(other.getDataFineEsposizione()))) &&
            ((this.nomeFile==null && other.getNomeFile()==null) || 
             (this.nomeFile!=null &&
              this.nomeFile.equals(other.getNomeFile()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            this.tipoDocumento == other.getTipoDocumento() &&
            ((this.enteProvenienza==null && other.getEnteProvenienza()==null) || 
             (this.enteProvenienza!=null &&
              this.enteProvenienza.equals(other.getEnteProvenienza()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote())));
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
        if (getProtocollo() != null) {
            _hashCode += getProtocollo().hashCode();
        }
        if (getNumeroDocumento() != null) {
            _hashCode += getNumeroDocumento().hashCode();
        }
        if (getAnnoDocumento() != null) {
            _hashCode += getAnnoDocumento().hashCode();
        }
        if (getDataDocumento() != null) {
            _hashCode += getDataDocumento().hashCode();
        }
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getSettore() != null) {
            _hashCode += getSettore().hashCode();
        }
        if (getDataInizioEsposizione() != null) {
            _hashCode += getDataInizioEsposizione().hashCode();
        }
        if (getDataFineEsposizione() != null) {
            _hashCode += getDataFineEsposizione().hashCode();
        }
        if (getNomeFile() != null) {
            _hashCode += getNomeFile().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        _hashCode += getTipoDocumento();
        if (getEnteProvenienza() != null) {
            _hashCode += getEnteProvenienza().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "DocumentoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "Protocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "NumeroDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "AnnoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "DataDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "Oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "Settore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioEsposizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "DataInizioEsposizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineEsposizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "DataFineEsposizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeFile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "NomeFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "TipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enteProvenienza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "EnteProvenienza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.intersail.it/AlboPretorio/Protocollo", "Note"));
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
