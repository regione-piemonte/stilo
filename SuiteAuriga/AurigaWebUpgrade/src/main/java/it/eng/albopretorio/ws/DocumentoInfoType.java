/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * DocumentoInfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.albopretorio.ws;

public class DocumentoInfoType  implements java.io.Serializable {
    private int annullamentoUserId;

    private java.util.Calendar annullamentoData;

    private java.lang.String annullamentoMotivo;

    private boolean annullamentoEsposizione;

    private boolean annullato;

    private java.lang.String username;

    private it.eng.albopretorio.ws.StatoDocumentoType statoDocumento;

    private int settoreId;

    private java.lang.String attachConformitaId;

    private java.lang.String attachInizioEsposizioneId;

    private java.lang.String attachFineEsposizioneId;

    private java.lang.String attachEsecutivitaId;

    private java.lang.String settore;

    private java.lang.String settoreb;

    private java.lang.String attachmentId;

    private int tipoDocumentoId;

    private int anno;

    private java.util.Calendar dataFineEsposizione;

    private java.util.Calendar dataInizioEsposizione;

    private java.util.Calendar dataDocumento;

    private java.lang.String numero;

    private java.lang.String descrizione;

    private int id;

    private java.lang.String protocollo;

    private java.lang.String esibente;

    private boolean validData;

    public DocumentoInfoType() {
    }

    public DocumentoInfoType(
           int annullamentoUserId,
           java.util.Calendar annullamentoData,
           java.lang.String annullamentoMotivo,
           boolean annullamentoEsposizione,
           boolean annullato,
           java.lang.String username,
           it.eng.albopretorio.ws.StatoDocumentoType statoDocumento,
           int settoreId,
           java.lang.String attachConformitaId,
           java.lang.String attachInizioEsposizioneId,
           java.lang.String attachFineEsposizioneId,
           java.lang.String attachEsecutivitaId,
           java.lang.String settore,
           java.lang.String settoreb,
           java.lang.String attachmentId,
           int tipoDocumentoId,
           int anno,
           java.util.Calendar dataFineEsposizione,
           java.util.Calendar dataInizioEsposizione,
           java.util.Calendar dataDocumento,
           java.lang.String numero,
           java.lang.String descrizione,
           int id,
           java.lang.String protocollo,
           java.lang.String esibente,
           boolean validData) {
           this.annullamentoUserId = annullamentoUserId;
           this.annullamentoData = annullamentoData;
           this.annullamentoMotivo = annullamentoMotivo;
           this.annullamentoEsposizione = annullamentoEsposizione;
           this.annullato = annullato;
           this.username = username;
           this.statoDocumento = statoDocumento;
           this.settoreId = settoreId;
           this.attachConformitaId = attachConformitaId;
           this.attachInizioEsposizioneId = attachInizioEsposizioneId;
           this.attachFineEsposizioneId = attachFineEsposizioneId;
           this.attachEsecutivitaId = attachEsecutivitaId;
           this.settore = settore;
           this.settoreb = settoreb;
           this.attachmentId = attachmentId;
           this.tipoDocumentoId = tipoDocumentoId;
           this.anno = anno;
           this.dataFineEsposizione = dataFineEsposizione;
           this.dataInizioEsposizione = dataInizioEsposizione;
           this.dataDocumento = dataDocumento;
           this.numero = numero;
           this.descrizione = descrizione;
           this.id = id;
           this.protocollo = protocollo;
           this.esibente = esibente;
           this.validData = validData;
    }


    /**
     * Gets the annullamentoUserId value for this DocumentoInfoType.
     * 
     * @return annullamentoUserId
     */
    public int getAnnullamentoUserId() {
        return annullamentoUserId;
    }


    /**
     * Sets the annullamentoUserId value for this DocumentoInfoType.
     * 
     * @param annullamentoUserId
     */
    public void setAnnullamentoUserId(int annullamentoUserId) {
        this.annullamentoUserId = annullamentoUserId;
    }


    /**
     * Gets the annullamentoData value for this DocumentoInfoType.
     * 
     * @return annullamentoData
     */
    public java.util.Calendar getAnnullamentoData() {
        return annullamentoData;
    }


    /**
     * Sets the annullamentoData value for this DocumentoInfoType.
     * 
     * @param annullamentoData
     */
    public void setAnnullamentoData(java.util.Calendar annullamentoData) {
        this.annullamentoData = annullamentoData;
    }


    /**
     * Gets the annullamentoMotivo value for this DocumentoInfoType.
     * 
     * @return annullamentoMotivo
     */
    public java.lang.String getAnnullamentoMotivo() {
        return annullamentoMotivo;
    }


    /**
     * Sets the annullamentoMotivo value for this DocumentoInfoType.
     * 
     * @param annullamentoMotivo
     */
    public void setAnnullamentoMotivo(java.lang.String annullamentoMotivo) {
        this.annullamentoMotivo = annullamentoMotivo;
    }


    /**
     * Gets the annullamentoEsposizione value for this DocumentoInfoType.
     * 
     * @return annullamentoEsposizione
     */
    public boolean isAnnullamentoEsposizione() {
        return annullamentoEsposizione;
    }


    /**
     * Sets the annullamentoEsposizione value for this DocumentoInfoType.
     * 
     * @param annullamentoEsposizione
     */
    public void setAnnullamentoEsposizione(boolean annullamentoEsposizione) {
        this.annullamentoEsposizione = annullamentoEsposizione;
    }


    /**
     * Gets the annullato value for this DocumentoInfoType.
     * 
     * @return annullato
     */
    public boolean isAnnullato() {
        return annullato;
    }


    /**
     * Sets the annullato value for this DocumentoInfoType.
     * 
     * @param annullato
     */
    public void setAnnullato(boolean annullato) {
        this.annullato = annullato;
    }


    /**
     * Gets the username value for this DocumentoInfoType.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this DocumentoInfoType.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the statoDocumento value for this DocumentoInfoType.
     * 
     * @return statoDocumento
     */
    public it.eng.albopretorio.ws.StatoDocumentoType getStatoDocumento() {
        return statoDocumento;
    }


    /**
     * Sets the statoDocumento value for this DocumentoInfoType.
     * 
     * @param statoDocumento
     */
    public void setStatoDocumento(it.eng.albopretorio.ws.StatoDocumentoType statoDocumento) {
        this.statoDocumento = statoDocumento;
    }


    /**
     * Gets the settoreId value for this DocumentoInfoType.
     * 
     * @return settoreId
     */
    public int getSettoreId() {
        return settoreId;
    }


    /**
     * Sets the settoreId value for this DocumentoInfoType.
     * 
     * @param settoreId
     */
    public void setSettoreId(int settoreId) {
        this.settoreId = settoreId;
    }


    /**
     * Gets the attachConformitaId value for this DocumentoInfoType.
     * 
     * @return attachConformitaId
     */
    public java.lang.String getAttachConformitaId() {
        return attachConformitaId;
    }


    /**
     * Sets the attachConformitaId value for this DocumentoInfoType.
     * 
     * @param attachConformitaId
     */
    public void setAttachConformitaId(java.lang.String attachConformitaId) {
        this.attachConformitaId = attachConformitaId;
    }


    /**
     * Gets the attachInizioEsposizioneId value for this DocumentoInfoType.
     * 
     * @return attachInizioEsposizioneId
     */
    public java.lang.String getAttachInizioEsposizioneId() {
        return attachInizioEsposizioneId;
    }


    /**
     * Sets the attachInizioEsposizioneId value for this DocumentoInfoType.
     * 
     * @param attachInizioEsposizioneId
     */
    public void setAttachInizioEsposizioneId(java.lang.String attachInizioEsposizioneId) {
        this.attachInizioEsposizioneId = attachInizioEsposizioneId;
    }


    /**
     * Gets the attachFineEsposizioneId value for this DocumentoInfoType.
     * 
     * @return attachFineEsposizioneId
     */
    public java.lang.String getAttachFineEsposizioneId() {
        return attachFineEsposizioneId;
    }


    /**
     * Sets the attachFineEsposizioneId value for this DocumentoInfoType.
     * 
     * @param attachFineEsposizioneId
     */
    public void setAttachFineEsposizioneId(java.lang.String attachFineEsposizioneId) {
        this.attachFineEsposizioneId = attachFineEsposizioneId;
    }


    /**
     * Gets the attachEsecutivitaId value for this DocumentoInfoType.
     * 
     * @return attachEsecutivitaId
     */
    public java.lang.String getAttachEsecutivitaId() {
        return attachEsecutivitaId;
    }


    /**
     * Sets the attachEsecutivitaId value for this DocumentoInfoType.
     * 
     * @param attachEsecutivitaId
     */
    public void setAttachEsecutivitaId(java.lang.String attachEsecutivitaId) {
        this.attachEsecutivitaId = attachEsecutivitaId;
    }


    /**
     * Gets the settore value for this DocumentoInfoType.
     * 
     * @return settore
     */
    public java.lang.String getSettore() {
        return settore;
    }


    /**
     * Sets the settore value for this DocumentoInfoType.
     * 
     * @param settore
     */
    public void setSettore(java.lang.String settore) {
        this.settore = settore;
    }


    /**
     * Gets the settoreb value for this DocumentoInfoType.
     * 
     * @return settoreb
     */
    public java.lang.String getSettoreb() {
        return settoreb;
    }


    /**
     * Sets the settoreb value for this DocumentoInfoType.
     * 
     * @param settoreb
     */
    public void setSettoreb(java.lang.String settoreb) {
        this.settoreb = settoreb;
    }


    /**
     * Gets the attachmentId value for this DocumentoInfoType.
     * 
     * @return attachmentId
     */
    public java.lang.String getAttachmentId() {
        return attachmentId;
    }


    /**
     * Sets the attachmentId value for this DocumentoInfoType.
     * 
     * @param attachmentId
     */
    public void setAttachmentId(java.lang.String attachmentId) {
        this.attachmentId = attachmentId;
    }


    /**
     * Gets the tipoDocumentoId value for this DocumentoInfoType.
     * 
     * @return tipoDocumentoId
     */
    public int getTipoDocumentoId() {
        return tipoDocumentoId;
    }


    /**
     * Sets the tipoDocumentoId value for this DocumentoInfoType.
     * 
     * @param tipoDocumentoId
     */
    public void setTipoDocumentoId(int tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }


    /**
     * Gets the anno value for this DocumentoInfoType.
     * 
     * @return anno
     */
    public int getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this DocumentoInfoType.
     * 
     * @param anno
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }


    /**
     * Gets the dataFineEsposizione value for this DocumentoInfoType.
     * 
     * @return dataFineEsposizione
     */
    public java.util.Calendar getDataFineEsposizione() {
        return dataFineEsposizione;
    }


    /**
     * Sets the dataFineEsposizione value for this DocumentoInfoType.
     * 
     * @param dataFineEsposizione
     */
    public void setDataFineEsposizione(java.util.Calendar dataFineEsposizione) {
        this.dataFineEsposizione = dataFineEsposizione;
    }


    /**
     * Gets the dataInizioEsposizione value for this DocumentoInfoType.
     * 
     * @return dataInizioEsposizione
     */
    public java.util.Calendar getDataInizioEsposizione() {
        return dataInizioEsposizione;
    }


    /**
     * Sets the dataInizioEsposizione value for this DocumentoInfoType.
     * 
     * @param dataInizioEsposizione
     */
    public void setDataInizioEsposizione(java.util.Calendar dataInizioEsposizione) {
        this.dataInizioEsposizione = dataInizioEsposizione;
    }


    /**
     * Gets the dataDocumento value for this DocumentoInfoType.
     * 
     * @return dataDocumento
     */
    public java.util.Calendar getDataDocumento() {
        return dataDocumento;
    }


    /**
     * Sets the dataDocumento value for this DocumentoInfoType.
     * 
     * @param dataDocumento
     */
    public void setDataDocumento(java.util.Calendar dataDocumento) {
        this.dataDocumento = dataDocumento;
    }


    /**
     * Gets the numero value for this DocumentoInfoType.
     * 
     * @return numero
     */
    public java.lang.String getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this DocumentoInfoType.
     * 
     * @param numero
     */
    public void setNumero(java.lang.String numero) {
        this.numero = numero;
    }


    /**
     * Gets the descrizione value for this DocumentoInfoType.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this DocumentoInfoType.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the id value for this DocumentoInfoType.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this DocumentoInfoType.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the protocollo value for this DocumentoInfoType.
     * 
     * @return protocollo
     */
    public java.lang.String getProtocollo() {
        return protocollo;
    }


    /**
     * Sets the protocollo value for this DocumentoInfoType.
     * 
     * @param protocollo
     */
    public void setProtocollo(java.lang.String protocollo) {
        this.protocollo = protocollo;
    }


    /**
     * Gets the esibente value for this DocumentoInfoType.
     * 
     * @return esibente
     */
    public java.lang.String getEsibente() {
        return esibente;
    }


    /**
     * Sets the esibente value for this DocumentoInfoType.
     * 
     * @param esibente
     */
    public void setEsibente(java.lang.String esibente) {
        this.esibente = esibente;
    }


    /**
     * Gets the validData value for this DocumentoInfoType.
     * 
     * @return validData
     */
    public boolean isValidData() {
        return validData;
    }


    /**
     * Sets the validData value for this DocumentoInfoType.
     * 
     * @param validData
     */
    public void setValidData(boolean validData) {
        this.validData = validData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoInfoType)) return false;
        DocumentoInfoType other = (DocumentoInfoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.annullamentoUserId == other.getAnnullamentoUserId() &&
            ((this.annullamentoData==null && other.getAnnullamentoData()==null) || 
             (this.annullamentoData!=null &&
              this.annullamentoData.equals(other.getAnnullamentoData()))) &&
            ((this.annullamentoMotivo==null && other.getAnnullamentoMotivo()==null) || 
             (this.annullamentoMotivo!=null &&
              this.annullamentoMotivo.equals(other.getAnnullamentoMotivo()))) &&
            this.annullamentoEsposizione == other.isAnnullamentoEsposizione() &&
            this.annullato == other.isAnnullato() &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.statoDocumento==null && other.getStatoDocumento()==null) || 
             (this.statoDocumento!=null &&
              this.statoDocumento.equals(other.getStatoDocumento()))) &&
            this.settoreId == other.getSettoreId() &&
            ((this.attachConformitaId==null && other.getAttachConformitaId()==null) || 
             (this.attachConformitaId!=null &&
              this.attachConformitaId.equals(other.getAttachConformitaId()))) &&
            ((this.attachInizioEsposizioneId==null && other.getAttachInizioEsposizioneId()==null) || 
             (this.attachInizioEsposizioneId!=null &&
              this.attachInizioEsposizioneId.equals(other.getAttachInizioEsposizioneId()))) &&
            ((this.attachFineEsposizioneId==null && other.getAttachFineEsposizioneId()==null) || 
             (this.attachFineEsposizioneId!=null &&
              this.attachFineEsposizioneId.equals(other.getAttachFineEsposizioneId()))) &&
            ((this.attachEsecutivitaId==null && other.getAttachEsecutivitaId()==null) || 
             (this.attachEsecutivitaId!=null &&
              this.attachEsecutivitaId.equals(other.getAttachEsecutivitaId()))) &&
            ((this.settore==null && other.getSettore()==null) || 
             (this.settore!=null &&
              this.settore.equals(other.getSettore()))) &&
            ((this.settoreb==null && other.getSettoreb()==null) || 
             (this.settoreb!=null &&
              this.settoreb.equals(other.getSettoreb()))) &&
            ((this.attachmentId==null && other.getAttachmentId()==null) || 
             (this.attachmentId!=null &&
              this.attachmentId.equals(other.getAttachmentId()))) &&
            this.tipoDocumentoId == other.getTipoDocumentoId() &&
            this.anno == other.getAnno() &&
            ((this.dataFineEsposizione==null && other.getDataFineEsposizione()==null) || 
             (this.dataFineEsposizione!=null &&
              this.dataFineEsposizione.equals(other.getDataFineEsposizione()))) &&
            ((this.dataInizioEsposizione==null && other.getDataInizioEsposizione()==null) || 
             (this.dataInizioEsposizione!=null &&
              this.dataInizioEsposizione.equals(other.getDataInizioEsposizione()))) &&
            ((this.dataDocumento==null && other.getDataDocumento()==null) || 
             (this.dataDocumento!=null &&
              this.dataDocumento.equals(other.getDataDocumento()))) &&
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            this.id == other.getId() &&
            ((this.protocollo==null && other.getProtocollo()==null) || 
             (this.protocollo!=null &&
              this.protocollo.equals(other.getProtocollo()))) &&
            ((this.esibente==null && other.getEsibente()==null) || 
             (this.esibente!=null &&
              this.esibente.equals(other.getEsibente()))) &&
            this.validData == other.isValidData();
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
        _hashCode += getAnnullamentoUserId();
        if (getAnnullamentoData() != null) {
            _hashCode += getAnnullamentoData().hashCode();
        }
        if (getAnnullamentoMotivo() != null) {
            _hashCode += getAnnullamentoMotivo().hashCode();
        }
        _hashCode += (isAnnullamentoEsposizione() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isAnnullato() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getStatoDocumento() != null) {
            _hashCode += getStatoDocumento().hashCode();
        }
        _hashCode += getSettoreId();
        if (getAttachConformitaId() != null) {
            _hashCode += getAttachConformitaId().hashCode();
        }
        if (getAttachInizioEsposizioneId() != null) {
            _hashCode += getAttachInizioEsposizioneId().hashCode();
        }
        if (getAttachFineEsposizioneId() != null) {
            _hashCode += getAttachFineEsposizioneId().hashCode();
        }
        if (getAttachEsecutivitaId() != null) {
            _hashCode += getAttachEsecutivitaId().hashCode();
        }
        if (getSettore() != null) {
            _hashCode += getSettore().hashCode();
        }
        if (getSettoreb() != null) {
            _hashCode += getSettoreb().hashCode();
        }
        if (getAttachmentId() != null) {
            _hashCode += getAttachmentId().hashCode();
        }
        _hashCode += getTipoDocumentoId();
        _hashCode += getAnno();
        if (getDataFineEsposizione() != null) {
            _hashCode += getDataFineEsposizione().hashCode();
        }
        if (getDataInizioEsposizione() != null) {
            _hashCode += getDataInizioEsposizione().hashCode();
        }
        if (getDataDocumento() != null) {
            _hashCode += getDataDocumento().hashCode();
        }
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        _hashCode += getId();
        if (getProtocollo() != null) {
            _hashCode += getProtocollo().hashCode();
        }
        if (getEsibente() != null) {
            _hashCode += getEsibente().hashCode();
        }
        _hashCode += (isValidData() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentoInfoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://it.intersail/wse", "DocumentoInfoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annullamentoUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AnnullamentoUserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annullamentoData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AnnullamentoData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annullamentoMotivo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AnnullamentoMotivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annullamentoEsposizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AnnullamentoEsposizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annullato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Annullato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "StatoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://it.intersail/wse", "StatoDocumentoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settoreId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "SettoreId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachConformitaId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AttachConformitaId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachInizioEsposizioneId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AttachInizioEsposizioneId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachFineEsposizioneId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AttachFineEsposizioneId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachEsecutivitaId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AttachEsecutivitaId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Settore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settoreb");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Settoreb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachmentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "AttachmentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumentoId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "TipoDocumentoId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineEsposizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "DataFineEsposizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioEsposizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "DataInizioEsposizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "DataDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Protocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esibente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "Esibente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://it.intersail/wse", "ValidData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
