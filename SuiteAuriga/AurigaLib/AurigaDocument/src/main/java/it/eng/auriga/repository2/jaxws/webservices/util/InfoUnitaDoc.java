/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class InfoUnitaDoc {

    public InfoUnitaDoc() {
    }
    private String idUD;
    private String categoriaReg;
    private String siglaReg;
	private String annoReg;
	private String numReg;

    /**
     * Ritorna il valore di nroAttachmentAssociato.
     * @return il valore di nroAttachmentAssociato.
     */
    public String getIdUD()
    {
        return idUD;
    }

    /**
     * Imposta il valore di nroAttachmentAssociato.
     * @param v  Valore da assegnare a nroAttachmentAssociato.
     */
    public void setIdUD(String  v)
    {
        this.idUD = v;
    }

	/**
     * Ritorna il valore di nomeFile.
     * @return il valore di nomeFile.
     */
    public String getCategoriaReg()
    {
        return categoriaReg;
    }

    /**
     * Imposta il valore di nomeFile.
     * @param v  Valore da assegnare a nomeFile.
     */
    public void setCategoriaReg(String  v)
    {
        this.categoriaReg = v;
    }

	/**
     * Ritorna il valore di attivaVerificaFirma.
     * @return il valore di attivaVerificaFirma.
     */
    public String getSiglaReg()
    {
        return siglaReg;
    }

    /**
     * Imposta il valore di attivaVerificaFirma.
     * @param v  Valore da assegnare a attivaVerificaFirma.
     */
    public void setSiglaReg(String  v)
    {
        this.siglaReg = v;
    }

	/**
     * Ritorna il valore di nomeFile.
     * @return il valore di nomeFile.
     */
    public String getAnnoReg()
    {
        return annoReg;
    }

    /**
     * Imposta il valore di nomeFile.
     * @param v  Valore da assegnare a nomeFile.
     */
    public void setAnnoReg(String  v)
    {
        this.annoReg = v;
    }

	/**
     * Ritorna il valore di attivaVerificaFirma.
     * @return il valore di attivaVerificaFirma.
     */
    public String getNumReg()
    {
        return numReg;
    }

    /**
     * Imposta il valore di attivaVerificaFirma.
     * @param v  Valore da assegnare a attivaVerificaFirma.
     */
    public void setNumReg(String  v)
    {
        this.numReg = v;
    }
}
