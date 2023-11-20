/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class VersioneElettronica {

    public VersioneElettronica() {
    }
    private String nroAttachmentAssociato;
    private String nomeFile;
    private String attivaVerificaFirma;
	private boolean flgDocPrimario;
	private boolean flgAddDoc;
	private String numAttach;

    /**
     * Ritorna il valore di nroAttachmentAssociato.
     * @return il valore di nroAttachmentAssociato.
     */
    public String getNroAttachmentAssociato()
    {
        return nroAttachmentAssociato;
    }

    /**
     * Imposta il valore di nroAttachmentAssociato.
     * @param v  Valore da assegnare a nroAttachmentAssociato.
     */
    public void setNroAttachmentAssociato(String  v)
    {
        this.nroAttachmentAssociato = v;
    }

	/**
     * Ritorna il valore di nomeFile.
     * @return il valore di nomeFile.
     */
    public String getNomeFile()
    {
        return nomeFile;
    }

    /**
     * Imposta il valore di nomeFile.
     * @param v  Valore da assegnare a nomeFile.
     */
    public void setNomeFile(String  v)
    {
        this.nomeFile = v;
    }

	/**
     * Ritorna il valore di attivaVerificaFirma.
     * @return il valore di attivaVerificaFirma.
     */
    public String getAttivaVerificaFirma()
    {
        return attivaVerificaFirma;
    }

    /**
     * Imposta il valore di attivaVerificaFirma.
     * @param v  Valore da assegnare a attivaVerificaFirma.
     */
    public void setAttivaVerificaFirma(String  v)
    {
        this.attivaVerificaFirma = v;
    }

	/**
     * Ritorna il valore di isDocPrimario.
     * @return il valore di isDocPrimario.
     */
    public boolean isDocPrimario()
    {
        return flgDocPrimario;
    }

    /**
     * Imposta il valore di attivaVerificaFirma.
     * @param v  Valore da assegnare a attivaVerificaFirma.
     */
    public void setFlgDocPrimario(boolean  v)
    {
        this.flgDocPrimario = v;
    }

	/**
     * Ritorna il valore di isDocPrimario.
     * @return il valore di isDocPrimario.
     */
    public boolean isAddDoc()
    {
        return flgAddDoc;
    }

    /**
     * Imposta il valore di attivaVerificaFirma.
     * @param v  Valore da assegnare a attivaVerificaFirma.
     */
    public void setFlgAddDoc(boolean  v)
    {
        this.flgAddDoc = v;
    }

	/**
     * Ritorna il valore di nroAttachmentAssociato.
     * @return il valore di nroAttachmentAssociato.
     */
    public String getNumAttach()
    {
        return numAttach;
    }

    /**
     * Imposta il valore di nroAttachmentAssociato.
     * @param v  Valore da assegnare a nroAttachmentAssociato.
     */
    public void setNumAttach(String   v)
    {
        this.numAttach = v;
    }

}
