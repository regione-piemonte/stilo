/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class DocumentoXmlAlgOutBean extends DocumentoXmlOutBean {

	private static final long serialVersionUID = -483078932590126871L;

	// ****************************************************************
	// COMUNI IN TUTTE LE SEZIONI
	// ****************************************************************
	
	// Codice societa
	@XmlVariabile(nome="COD_SOCIETA", tipo=TipoVariabile.SEMPLICE)
	private String codiceSocieta;
	
	// Data e ora sottoscrizione contratto
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome="DATA_SOTTOSCRIZIONE_CONTRATTO", tipo=TipoVariabile.SEMPLICE)
	private Date dataStipulaContratto;

	// Luogo sottoscrizione contratto
	@XmlVariabile(nome="LUOGO_SOTTOSCRIZIONE_CONTRATTO", tipo=TipoVariabile.SEMPLICE)
	private String comunePuntoVenditaSottoscrizioneContratto;	
	
	// Nome venditore
	@XmlVariabile(nome="NOME_COGNOME_VENDITORE", tipo=TipoVariabile.SEMPLICE)
	private String nomeCognomeVenditore;	
	
	@XmlVariabile(nome="CODICE_VENDITORE_NETA", tipo=TipoVariabile.SEMPLICE)
	private String codiceVenditoreNETA;
	
	@XmlVariabile(nome="CODICE_PROFILO_VENDITORE", tipo=TipoVariabile.SEMPLICE)
	private String codiceProfiloVenditore;
	
	@XmlVariabile(nome="CODICE_PROVENIENZA_PV", tipo=TipoVariabile.SEMPLICE)
	private String codProvenienzaPV;
	
	@XmlVariabile(nome="TICKET_REGISTRAZIONE_NETA", tipo=TipoVariabile.SEMPLICE)
	private String ticketNETA;	
	
	@XmlVariabile(nome="COD_PROCESSO", tipo=TipoVariabile.SEMPLICE)
	private String codiceProcesso;	
	
	@XmlVariabile(nome="COD_TIPO_DOC", tipo=TipoVariabile.SEMPLICE)
	private String codiceTipoDoc;	
	

	// ****************************************************************
	// PUNTO VENDITA DI LAVORO
	// ****************************************************************
	
	@XmlVariabile(nome="ID_PV_SELEZIONATO", tipo=TipoVariabile.SEMPLICE)
	private String idPuntoVenditaSelezionato;
	
	@XmlVariabile(nome="NOME_PV_SELEZIONATO", tipo=TipoVariabile.SEMPLICE)
	private String nomePuntoVenditaSelezionato;
	
	@XmlVariabile(nome="NOME_COMUNE_PV_SELEZIONATO", tipo=TipoVariabile.SEMPLICE)
	private String comunePuntoVenditaSelezionato;

	@XmlVariabile(nome="COD_ISTAT_COMUNE_PV_SELEZIONATO", tipo=TipoVariabile.SEMPLICE)
	private String codIstatComunePuntoVenditaSelezionato;
	
	@XmlVariabile(nome = "CATEGORIA_SOCIO_PV_SELEZIONATO", tipo=TipoVariabile.SEMPLICE)
	private String categoriaSocioPuntoVenditaSelezionato;


	// ****************************************************************
	// TIPOLOGIA DI FORNITURA
	// ****************************************************************
	
	@XmlVariabile(nome="COD_TIPOLOGIA_FORNITURA", tipo=TipoVariabile.SEMPLICE)
	private String tipologiaFornitura;	
	
	@XmlVariabile(nome="TIPOLOGIA_FORNITURA", tipo=TipoVariabile.SEMPLICE)	
	private String nomeTipologiaFornitura;
	
	// ****************************************************************
	// TIPOLOGIA DI OFFERTA E DATI CONTRATTUALI
	// ****************************************************************
	
	@XmlVariabile(nome="TIPOLOGIA_OFFERTA", tipo=TipoVariabile.SEMPLICE)
	private String nomeTipologiaOfferta;
	
	@XmlVariabile(nome="COD_TIPOLOGIA_OFFERTA", tipo=TipoVariabile.SEMPLICE)
	private String tipologiaOfferta;
	
	@XmlVariabile(nome="TIPOLOGIA_PROMOZIONE", tipo=TipoVariabile.SEMPLICE)
	private String nomeTipologiaPromozione;
	
	@XmlVariabile(nome="COD_TIPOLOGIA_PROMOZIONE", tipo=TipoVariabile.SEMPLICE)
	private String tipologiaPromozione;

	@XmlVariabile(nome="MESE_RIF_TIPOLOGIA_OFFERTA", tipo=TipoVariabile.SEMPLICE)
	private String meseRifTipologiaOffertaForm;
	
	@XmlVariabile(nome="OFFERTA_MONO_BIORARIA", tipo=TipoVariabile.SEMPLICE)
	private String nomeTipologiaOffertaBiOrario;

	@XmlVariabile(nome="FLG_ENERGIA_VERDE", tipo=TipoVariabile.SEMPLICE)
	private String flgAdesioneEnergiaVerde;

	
	@XmlVariabile(nome="COD_CRITTOGRAFATO_PROMO_AMICO", tipo=TipoVariabile.SEMPLICE)
	private String codicePromoAmico;
	
	
	// ****************************************************************
	// DATI CLIENTE
	// ****************************************************************

	@XmlVariabile(nome="NRO_CARTA_SOCIO_COOP_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String numeroCartaSocioCoopDatiClienteForm;
		
	@XmlVariabile(nome="CF_PIVA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String codiceFiscaleCliente;
	
	@XmlVariabile(nome="COD_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String codiceCliente;
	
	@XmlVariabile(nome="NOME_CLIENTE", tipo = TipoVariabile.SEMPLICE)
	private String nomeCliente;
	
	@XmlVariabile(nome="COGNOME_CLIENTE", tipo = TipoVariabile.SEMPLICE)
	private String cognomeCliente;

	@XmlVariabile(nome="DES_CLIENTE", tipo = TipoVariabile.SEMPLICE)
	private String desCliente;

	
	@XmlVariabile(nome="LABEL_SOCIO_COOP_CLIENTE", tipo = TipoVariabile.SEMPLICE)
	private String labelSocioCoopDatiClienteForm;
	
	@XmlVariabile(nome="SOCIO_COOP_CLIENTE", tipo = TipoVariabile.SEMPLICE)
	private String flgSocioCoopCliente;

	@XmlVariabile(nome="STATO_NASCITA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String nomeStatoNascitaCliente;
	
	@XmlVariabile(nome="COD_STATO_NASCITA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String statoNascitaCliente;
	
	@XmlVariabile(nome="COMUNE_NASCITA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String nomeComuneNascitaCliente;

	@XmlVariabile(nome="COD_COMUNE_NASCITA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String comuneNascitaCliente;

	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="DT_NASCITA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private Date dataNascitaCliente;
	
	@XmlVariabile(nome="SESSO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String sessoCliente;
	
	@XmlVariabile(nome="EMAIL_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String emailCliente;

	@XmlVariabile(nome="CELL_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String cellulareCliente;

	@XmlVariabile(nome="TIPO_DOCUMENTO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String nomeTipoDocumentoCliente;
		
	@XmlVariabile(nome="COD_TIPO_DOCUMENTO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String tipoDocumentoCliente;

	@XmlVariabile(nome="NRO_DOCUMENTO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String numeroDocumentoCliente;
		
	@XmlVariabile(nome="ENTE_RILASCIO_DOCUMENTO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String enteRilascioDocumentoCliente;

	// Scadenza Documento	
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="DATA_SCADENZA_DOCUMENTO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private Date dataScadenzaDocumentoCliente;

     // Residenza
	@XmlVariabile(nome="COD_STATO_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String statoResidenzaCliente;	

	@XmlVariabile(nome="NOME_STATO_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String nomeStatoResidenzaCliente;	

	@XmlVariabile(nome="COD_ISTAT_COMUNE_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String comuneResidenzaCliente;
		
	@XmlVariabile(nome="NOME_COMUNE_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String nomeComuneResidenzaCliente;

	@XmlVariabile(nome="COD_ISTAT_REGIONE_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String codIstatRegioneResidenzaCliente;

	@XmlVariabile(nome="DESC_REGIONE_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String descRegioneResidenzaCliente;

	@XmlVariabile(nome="DESC_PROVINCIA_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String descProvinciaResidenzaCliente;

	
	@XmlVariabile(nome="DESC_REGIONE_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String descRegioneDomicilioCliente;

	@XmlVariabile(nome="DESC_PROVINCIA_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String descProvinciaDomicilioCliente;

	
	@XmlVariabile(nome="COD_COMUNE_ALG_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String codComuneAlgResidenzaCliente;	
	
	@XmlVariabile(nome="INDIRIZZO_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)	
	private String indirizzoResidenzaCliente;

	@XmlVariabile(nome="CIVICO_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)	
	private String civicoResidenzaCliente;
	
	@XmlVariabile(nome="INTERNO_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String internoResidenzaCliente;
	
	@XmlVariabile(nome="APPENDICI_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String appendiciResidenzaCliente;

	@XmlVariabile(nome="CAP_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String capResidenzaCliente;

	@XmlVariabile(nome="CITTA_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String cittaResidenzaCliente;

	@XmlVariabile(nome="PROVINCIA_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String provinciaResidenzaCliente;

	@XmlVariabile(nome="FLG_DOMICILIO_UGUALE_RESIDENZA_CLIENTE", tipo=TipoVariabile.SEMPLICE)	
	private String flgDomicilioUgualeResidenzaDatiClienteForm;
	
	 // Domicilio
	@XmlVariabile(nome="COD_STATO_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String statoDomicilioCliente;	

	@XmlVariabile(nome="NOME_STATO_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String nomeStatoDomicilioCliente;	

	@XmlVariabile(nome="COD_ISTAT_COMUNE_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String comuneDomicilioCliente;
	
	@XmlVariabile(nome="NOME_COMUNE_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String nomeComuneDomicilioCliente;

	@XmlVariabile(nome="COD_ISTAT_REGIONE_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
    private String codIstatRegioneDomicilioCliente;

	@XmlVariabile(nome="COD_COMUNE_ALG_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String codComuneAlgDomicilioCliente;
	
	@XmlVariabile(nome="INDIRIZZO_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)	
	private String indirizzoDomicilioCliente;

	@XmlVariabile(nome="CIVICO_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)	
	private String civicoDomicilioCliente;
	
	@XmlVariabile(nome="INTERNO_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String internoDomicilioCliente;
	
	@XmlVariabile(nome="APPENDICI_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String appendiciDomicilioCliente;
	
	@XmlVariabile(nome="CAP_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String capDomicilioCliente;
	
	@XmlVariabile(nome="CITTA_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String cittaDomicilioCliente;
	
	@XmlVariabile(nome="PROVINCIA_DOMICILIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String provinciaDomicilioCliente;

	// ****************************************************************
	// DATI TECNICI DI FORNITURA
	// ****************************************************************

	@XmlVariabile(nome="POD_EE", tipo=TipoVariabile.SEMPLICE)
	private String podEE;

	@XmlVariabile(nome="FLG_DOMESTICO_RESIDENTE_EE", tipo=TipoVariabile.SEMPLICE)
	private String flgDomensticoNonResidenteEE;

	@XmlVariabile(nome="POTENZA_DISPONIBILE_EE", tipo=TipoVariabile.SEMPLICE)
	private String potenzaDisponibileEE;
	
	@XmlVariabile(nome="VALORE_POTENZA_IMPEGNATA_EE", tipo=TipoVariabile.SEMPLICE)
	private String valorePotenzaImpegnataEE;
	
	@XmlVariabile(nome="POTENZA_IMPEGNATA_EE", tipo=TipoVariabile.SEMPLICE)
	private String potenzaImpegnataEE;

	@XmlVariabile(nome="CONSUMO_ANNUO_EE", tipo=TipoVariabile.SEMPLICE)
	private String consumoAnnuoEE;
	
	@XmlVariabile(nome="FLG_MERCATO_LIBERO_EE", tipo=TipoVariabile.SEMPLICE)
	private String flgMercatoLiberoEE;

	@XmlVariabile(nome="FORNITORE_PRECEDENTE_EE", tipo=TipoVariabile.SEMPLICE)
	private String nomeFornitorePrecedenteEE;

	@XmlVariabile(nome="COD_FORNITORE_PRECEDENTE_EE", tipo=TipoVariabile.SEMPLICE)
	private String fornitorePrecedenteEE;

	@XmlVariabile(nome="FLG_SERVIZIO_TUTELA_EE", tipo=TipoVariabile.SEMPLICE)
	private String flgServizioTutelaEE;

	@XmlVariabile(nome="PDR_GAS", tipo=TipoVariabile.SEMPLICE)
	private String pdrGAS;

	@XmlVariabile(nome="CONSUMO_ANNUO_GAS", tipo=TipoVariabile.SEMPLICE)
	private String consumoAnnuoGAS;

	@XmlVariabile(nome="FLG_MERCATO_LIBERO_GAS", tipo=TipoVariabile.SEMPLICE)
	private String flgMercatoLiberoGAS;

	@XmlVariabile(nome="FORNITORE_PRECEDENTE_GAS", tipo=TipoVariabile.SEMPLICE)
	private String nomeFornitorePrecedenteGAS;

	@XmlVariabile(nome="COD_FORNITORE_PRECEDENTE_GAS", tipo=TipoVariabile.SEMPLICE)
	private String fornitorePrecedenteGAS;

	@XmlVariabile(nome="FLG_SERVIZIO_TUTELA_GAS", tipo=TipoVariabile.SEMPLICE)
	private String flgServizioTutelaGAS;

	// ****************************************************************
	// MODALITA' DI RICEZIONE FATTURE E DOCUMENTI
	// ****************************************************************

	@XmlVariabile(nome="FLG_MODALITA_RICEZIONE_DOCUMENTI", tipo=TipoVariabile.SEMPLICE)
	private String tipologiaModalitaRicezione;
	
	@XmlVariabile(nome="INDIRIZZO_EMAIL_RICEZIONE_DOCUMENTI", tipo=TipoVariabile.SEMPLICE)
	private String indirizzoEmailModalitaRicezione;
	
	@XmlVariabile(nome="COD_STATO_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String statoRicezionePosta;	

	@XmlVariabile(nome="NOME_STATO_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String nomeStatoRicezionePosta;	

	@XmlVariabile(nome="COD_ISTAT_COMUNE_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String comuneRicezionePosta;
	
	@XmlVariabile(nome="NOME_COMUNE_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String nomeComuneRicezionePosta;
	
	@XmlVariabile(nome="COD_ISTAT_REGIONE_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String codIstatRegioneRicezionePosta;

	@XmlVariabile(nome="DESC_REGIONE_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String descRegioneRicezionePosta;

	@XmlVariabile(nome="DESC_PROVINCIA_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String descProvinciaRicezionePosta;

	@XmlVariabile(nome="INDIRIZZO_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)	
	private String indirizzoRicezionePosta;

	@XmlVariabile(nome="CIVICO_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)	
	private String civicoRicezionePosta;
	
	@XmlVariabile(nome="INTERNO_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String internoRicezionePosta;
	
	@XmlVariabile(nome="APPENDICI_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String appendiciRicezionePosta;
	
	@XmlVariabile(nome="CAP_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String capRicezionePosta;
	
	@XmlVariabile(nome="CITTA_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String cittaRicezionePosta;

	@XmlVariabile(nome="PROVINCIA_RICEZIONE_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String provinciaRicezionePosta;
	
	// ****************************************************************
	// MODALITA' DI PAGAMENTO DELLE FATTURE
	// ****************************************************************
	
	@XmlVariabile(nome="MODALITA_PAGAMENTO", tipo=TipoVariabile.SEMPLICE)	
	private String nomeModalitaPagamento;
	
	@XmlVariabile(nome="COD_MODALITA_PAGAMENTO", tipo=TipoVariabile.SEMPLICE)	
	private String modalitaPagamento;
	
	// Modalita' LIBRETTO SOCIO COOP
	@XmlVariabile(nome="NRO_LIBRETTO_SOCIO_COOP", tipo=TipoVariabile.SEMPLICE)		
	private String numeroLibrettoSocioCoop;
	
	@XmlVariabile(nome="INTESTATARIO_LIBRETTO_SOCIO_COOP", tipo=TipoVariabile.SEMPLICE)			
	private String intestatarioLibrettoSocioCoop;
	
	// Modalita' CONTO CORRENTE	
	@XmlVariabile(nome="CODICE_IBA_CONTO_CORRENTE", tipo=TipoVariabile.SEMPLICE)
	private String codiceIBANContoCorrente;
	
	@XmlVariabile(nome="INTESTATARIO_CONTO_CORRENTE", tipo=TipoVariabile.SEMPLICE)
	private String intestatarioContoCorrente;
	
	@XmlVariabile(nome="CF_INTESTATARIO_CONTO_CORRENTE", tipo=TipoVariabile.SEMPLICE)
	private String codiceFiscaleIntestatarioContoCorrente;
	
	// ****************************************************************
	// INFORMATIVA PRIVACY
	// ****************************************************************
	
	@XmlVariabile(nome="FLG_NOTA1_INFORMATIVA_PRIVACY", tipo=TipoVariabile.SEMPLICE)
	private String flgNota1InformativaPrivacyForm;
	
	@XmlVariabile(nome="FLG_NOTA2_INFORMATIVA_PRIVACY", tipo=TipoVariabile.SEMPLICE)
	private String flgNota2InformativaPrivacyForm;

	@XmlVariabile(nome="FLG_NOTA3_INFORMATIVA_PRIVACY", tipo=TipoVariabile.SEMPLICE)
	private String flgNota3InformativaPrivacyForm;

	@XmlVariabile(nome="FLG_NOTA4_INFORMATIVA_PRIVACY", tipo=TipoVariabile.SEMPLICE)
	private String flgNota4InformativaPrivacyForm;

	@XmlVariabile(nome="FLG_NOTA5_INFORMATIVA_PRIVACY", tipo=TipoVariabile.SEMPLICE)
	private String flgNota5InformativaPrivacyForm;

	@XmlVariabile(nome="FLG_NOTA6_INFORMATIVA_PRIVACY", tipo=TipoVariabile.SEMPLICE)
	private String flgNota6InformativaPrivacyForm;

	@XmlVariabile(nome="FLG_NOTA7_INFORMATIVA_PRIVACY", tipo=TipoVariabile.SEMPLICE)
	private String flgNota7InformativaPrivacyForm;

	
	
	// ****************************************************************	
	// * DATI CATASTALI
	// ****************************************************************
	
	@XmlVariabile(nome="FLG_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String flgDatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="COGN_NOME_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String nominativoClienteDatiCatastaliForm;
	
	@XmlVariabile(nome="COD_FISCALE_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String codiceFiscaleClienteDatiCatastaliForm;
	
	@XmlVariabile(nome="COD_ISTAT_COMUNE_NASCITA_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String comuneNascitaClienteDatiCatastaliForm;

	@XmlVariabile(nome="NOME_COMUNE_NASCITA_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String nomeComuneNascitaClienteDatiCatastaliForm;

	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="DATA_NASCITA_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private Date dataNascitaClienteDatiCatastaliForm;
	
	@XmlVariabile(nome="COD_ISTAT_COMUNE_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String comuneResidenzaClienteDatiCatastaliForm;
	
	@XmlVariabile(nome="NOME_COMUNE_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String nomeComuneResidenzaClienteDatiCatastaliForm;
	
	@XmlVariabile(nome="INDIRIZZO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String viaResidenzaClienteDatiCatastaliForm;
	
	@XmlVariabile(nome="NRO_CIVICO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String civicoResidenzaClienteDatiCatastaliForm;
	
	// check 1
	@XmlVariabile(nome="FLG_PROP_IMMOBILE_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String flg1DatiCatastaliImmobileForm;
	
	// check 2
	@XmlVariabile(nome="FLG_INTESTATARIO_LOC_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String flg2DatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="COD_ISTAT_COMUNE_REG_CONTRATTO_LOC_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String agenziaEntrateFlg2DatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="NOME_COMUNE_REG_CONTRATTO_LOC_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String nomeAgenziaEntrateFlg2DatiCatastaliImmobileForm;
	
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="DT_REG_CONTRATTO_LOC_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private Date dataRegistrazioneFlg2DatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="NRO_REG_CONTRATTO_LOC_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String numeroRegistrazioneFlg2DatiCatastaliImmobileForm;
		
	// check 3
	@XmlVariabile(nome="FLG_INTESTATARIO_LOC_ERP_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String flg3DatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="NRO_CONTRATTO_ERP_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String numeroContrattoConsegnaAbitazioneDatiCatastaliImmobileForm;
		
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="DT_CONTRATTO_ERP_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private Date dataContrattoConsegnaAbitazioneDatiCatastaliImmobileForm;
	
	// check 4
	@XmlVariabile(nome="FLG_COMODATARIO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String flg4DatiCatastaliImmobileForm;

	@XmlVariabile(nome="COD_ISTAT_COMUNE_REG_CONTRATTO_COM_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String agenziaEntrateFlg4DatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="NOME_COMUNE_REG_CONTRATTO_COM_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String nomeAgenziaEntrateFlg4DatiCatastaliImmobileForm;
	
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="DT_REG_CONTRATTO_COM_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private Date dataRegistrazioneFlg4DatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="NRO_REG_CONTRATTO_COM_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String numeroRegistrazioneFlg4DatiCatastaliImmobileForm;
	 
	// check 5
	@XmlVariabile(nome="FLG_LEGITTIMO_TITOLO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)
	private String flg5DatiCatastaliImmobileForm;

	@XmlVariabile(nome="LEGITTIMO_TITOLO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String tipoTitoloDetenzioneDatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="N_ATTO_LEGITTIMO_TITOLO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String attoAcquisizioneDatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="COD_ISTAT_COMUNE_REG_ATTO_LEG_TIT_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String agenziaEntrateFlg5DatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="NOME_COMUNE_REG_ATTO_LEG_TIT_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String nomeAgenziaEntrateFlg5DatiCatastaliImmobileForm;
	
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="DT_REG_ATTO_LEG_TIT_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private Date dataRegistrazioneFlg5DatiCatastaliImmobileForm;
	
	@XmlVariabile(nome="N_REG_ATTO_LEG_TIT_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String numeroRegistrazioneFlg5DatiCatastaliImmobileForm;
	
	// Codice comune catastale ( metto la seconda parte di NOME_COMUNE_DATI_CATASTALI ) 
	@XmlVariabile(nome="COD_COMUNE_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE) 
	private String codComuneCatastoClienteDatiCatastaliForm;

	// Tipo catasto
	@XmlVariabile(nome="TIPO_CATASTO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String tipoCatastoClienteDatiCatastaliForm;

	@XmlVariabile(nome="NOME_TIPO_CATASTO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String nomeTipoCatastoClienteDatiCatastaliForm;

	// Sezione catasto
	@XmlVariabile(nome="SEZIONE_CATASTO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String sezioneCatastoClienteDatiCatastaliForm;
		
	// Foglio catasto
	@XmlVariabile(nome="FOGLIO_CATASTO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String foglioCatastoClienteDatiCatastaliForm;
		
	// Numero sub catasto
	@XmlVariabile(nome="SUB_CATASTO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String subAlternoCatastoClienteDatiCatastaliForm;
		
	// Numero particella
	@XmlVariabile(nome="NRO_PART_CATASTO_DATI_CATASTALI", tipo=TipoVariabile.SEMPLICE)	
	private String numeroParticellaCatastoClienteDatiCatastaliForm;
	
	// ****************************************************************
	// ALLEGATO C - NOTA INFORMATIVA PER IL CLIENTE FINALE
	// ****************************************************************

	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="DATA_FIRMA_ALLEGATOC", tipo=TipoVariabile.SEMPLICE)
	private Date dataConsegnaContrattoAllegatoC;
	
	@XmlVariabile(nome="ORA_FIRMA_ALLEGATOC", tipo=TipoVariabile.SEMPLICE)
	private String oraConsegnaContrattoAllegatoC;
	
	@XmlVariabile(nome="NOME_COGNOME_VENDITORE_ALLEGATOC", tipo=TipoVariabile.SEMPLICE)
	private String nomeCognomeVenditoreAllegatoC;	
	
	@XmlVariabile(nome="FLG_CLIENTE_CONTATTATO_ALLEGATOC", tipo=TipoVariabile.SEMPLICE)
	private String flgClienteContattatoDalPersonaleAllegatoC;

	
	// ****************************************************************
	// ABILITAZIONI BOTTONI GUI
	// ****************************************************************
	
	@XmlVariabile(nome="AbilitaSalvaContrattoInBozza", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilitaSalvaContrattoInBozza;
	
	@XmlVariabile(nome="AbilitaArchiviaContratto", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilitaArchiviaContratto;
	
	@XmlVariabile(nome="AbilitaStampaPDFContratto", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilitaStampaPDFContratto;
	
	@XmlVariabile(nome="AbilitaModificaContratto", tipo=TipoVariabile.SEMPLICE)	
	private Boolean abilitaModificaContratto;     
	
	@XmlVariabile(nome="AbilitaUploadContratto", tipo=TipoVariabile.SEMPLICE)	
	private Boolean abilitaUploadContratto;
	
	@XmlVariabile(nome="AbilitaChiusuraLavorazione", tipo=TipoVariabile.SEMPLICE)	
	private Boolean abilitaChiusuraLavorazione;

	
	
	// ****************************************************************
	// GETTER AND SETTER
	// ****************************************************************

	public String getCodiceSocieta() {
		return codiceSocieta;
	}

	public void setCodiceSocieta(String codiceSocieta) {
		this.codiceSocieta = codiceSocieta;
	}

	public Date getDataStipulaContratto() {
		return dataStipulaContratto;
	}

	public void setDataStipulaContratto(Date dataStipulaContratto) {
		this.dataStipulaContratto = dataStipulaContratto;
	}

	public String getComunePuntoVenditaSottoscrizioneContratto() {
		return comunePuntoVenditaSottoscrizioneContratto;
	}

	public void setComunePuntoVenditaSottoscrizioneContratto(String comunePuntoVenditaSottoscrizioneContratto) {
		this.comunePuntoVenditaSottoscrizioneContratto = comunePuntoVenditaSottoscrizioneContratto;
	}

	public String getNomeCognomeVenditore() {
		return nomeCognomeVenditore;
	}

	public void setNomeCognomeVenditore(String nomeCognomeVenditore) {
		this.nomeCognomeVenditore = nomeCognomeVenditore;
	}

	public String getCodiceVenditoreNETA() {
		return codiceVenditoreNETA;
	}

	public void setCodiceVenditoreNETA(String codiceVenditoreNETA) {
		this.codiceVenditoreNETA = codiceVenditoreNETA;
	}

	public String getCodiceProfiloVenditore() {
		return codiceProfiloVenditore;
	}

	public void setCodiceProfiloVenditore(String codiceProfiloVenditore) {
		this.codiceProfiloVenditore = codiceProfiloVenditore;
	}

	public String getCodProvenienzaPV() {
		return codProvenienzaPV;
	}

	public void setCodProvenienzaPV(String codProvenienzaPV) {
		this.codProvenienzaPV = codProvenienzaPV;
	}

	public String getIdPuntoVenditaSelezionato() {
		return idPuntoVenditaSelezionato;
	}

	public void setIdPuntoVenditaSelezionato(String idPuntoVenditaSelezionato) {
		this.idPuntoVenditaSelezionato = idPuntoVenditaSelezionato;
	}

	public String getNomePuntoVenditaSelezionato() {
		return nomePuntoVenditaSelezionato;
	}

	public void setNomePuntoVenditaSelezionato(String nomePuntoVenditaSelezionato) {
		this.nomePuntoVenditaSelezionato = nomePuntoVenditaSelezionato;
	}

	public String getComunePuntoVenditaSelezionato() {
		return comunePuntoVenditaSelezionato;
	}

	public void setComunePuntoVenditaSelezionato(String comunePuntoVenditaSelezionato) {
		this.comunePuntoVenditaSelezionato = comunePuntoVenditaSelezionato;
	}

	public String getCodIstatComunePuntoVenditaSelezionato() {
		return codIstatComunePuntoVenditaSelezionato;
	}

	public void setCodIstatComunePuntoVenditaSelezionato(String codIstatComunePuntoVenditaSelezionato) {
		this.codIstatComunePuntoVenditaSelezionato = codIstatComunePuntoVenditaSelezionato;
	}

	public String getTipologiaFornitura() {
		return tipologiaFornitura;
	}

	public void setTipologiaFornitura(String tipologiaFornitura) {
		this.tipologiaFornitura = tipologiaFornitura;
	}

	public String getNomeTipologiaFornitura() {
		return nomeTipologiaFornitura;
	}

	public void setNomeTipologiaFornitura(String nomeTipologiaFornitura) {
		this.nomeTipologiaFornitura = nomeTipologiaFornitura;
	}

	public String getNomeTipologiaOfferta() {
		return nomeTipologiaOfferta;
	}

	public void setNomeTipologiaOfferta(String nomeTipologiaOfferta) {
		this.nomeTipologiaOfferta = nomeTipologiaOfferta;
	}

	public String getTipologiaOfferta() {
		return tipologiaOfferta;
	}

	public void setTipologiaOfferta(String tipologiaOfferta) {
		this.tipologiaOfferta = tipologiaOfferta;
	}

	public String getNomeTipologiaPromozione() {
		return nomeTipologiaPromozione;
	}

	public void setNomeTipologiaPromozione(String nomeTipologiaPromozione) {
		this.nomeTipologiaPromozione = nomeTipologiaPromozione;
	}

	public String getTipologiaPromozione() {
		return tipologiaPromozione;
	}

	public void setTipologiaPromozione(String tipologiaPromozione) {
		this.tipologiaPromozione = tipologiaPromozione;
	}

	public String getMeseRifTipologiaOffertaForm() {
		return meseRifTipologiaOffertaForm;
	}

	public void setMeseRifTipologiaOffertaForm(String meseRifTipologiaOffertaForm) {
		this.meseRifTipologiaOffertaForm = meseRifTipologiaOffertaForm;
	}

	public String getNomeTipologiaOffertaBiOrario() {
		return nomeTipologiaOffertaBiOrario;
	}

	public void setNomeTipologiaOffertaBiOrario(String nomeTipologiaOffertaBiOrario) {
		this.nomeTipologiaOffertaBiOrario = nomeTipologiaOffertaBiOrario;
	}

	public String getFlgAdesioneEnergiaVerde() {
		return flgAdesioneEnergiaVerde;
	}

	public void setFlgAdesioneEnergiaVerde(String flgAdesioneEnergiaVerde) {
		this.flgAdesioneEnergiaVerde = flgAdesioneEnergiaVerde;
	}

	public String getNumeroCartaSocioCoopDatiClienteForm() {
		return numeroCartaSocioCoopDatiClienteForm;
	}

	public void setNumeroCartaSocioCoopDatiClienteForm(String numeroCartaSocioCoopDatiClienteForm) {
		this.numeroCartaSocioCoopDatiClienteForm = numeroCartaSocioCoopDatiClienteForm;
	}

	public String getCodiceFiscaleCliente() {
		return codiceFiscaleCliente;
	}

	public void setCodiceFiscaleCliente(String codiceFiscaleCliente) {
		this.codiceFiscaleCliente = codiceFiscaleCliente;
	}

	public String getCodiceCliente() {
		return codiceCliente;
	}

	public void setCodiceCliente(String codiceCliente) {
		this.codiceCliente = codiceCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCognomeCliente() {
		return cognomeCliente;
	}

	public void setCognomeCliente(String cognomeCliente) {
		this.cognomeCliente = cognomeCliente;
	}

	public String getFlgSocioCoopCliente() {
		return flgSocioCoopCliente;
	}

	public void setFlgSocioCoopCliente(String flgSocioCoopCliente) {
		this.flgSocioCoopCliente = flgSocioCoopCliente;
	}

	public String getNomeStatoNascitaCliente() {
		return nomeStatoNascitaCliente;
	}

	public void setNomeStatoNascitaCliente(String nomeStatoNascitaCliente) {
		this.nomeStatoNascitaCliente = nomeStatoNascitaCliente;
	}

	public String getStatoNascitaCliente() {
		return statoNascitaCliente;
	}

	public void setStatoNascitaCliente(String statoNascitaCliente) {
		this.statoNascitaCliente = statoNascitaCliente;
	}

	public String getNomeComuneNascitaCliente() {
		return nomeComuneNascitaCliente;
	}

	public void setNomeComuneNascitaCliente(String nomeComuneNascitaCliente) {
		this.nomeComuneNascitaCliente = nomeComuneNascitaCliente;
	}

	public String getComuneNascitaCliente() {
		return comuneNascitaCliente;
	}

	public void setComuneNascitaCliente(String comuneNascitaCliente) {
		this.comuneNascitaCliente = comuneNascitaCliente;
	}

	public Date getDataNascitaCliente() {
		return dataNascitaCliente;
	}

	public void setDataNascitaCliente(Date dataNascitaCliente) {
		this.dataNascitaCliente = dataNascitaCliente;
	}

	public String getSessoCliente() {
		return sessoCliente;
	}

	public void setSessoCliente(String sessoCliente) {
		this.sessoCliente = sessoCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getCellulareCliente() {
		return cellulareCliente;
	}

	public void setCellulareCliente(String cellulareCliente) {
		this.cellulareCliente = cellulareCliente;
	}

	public String getNomeTipoDocumentoCliente() {
		return nomeTipoDocumentoCliente;
	}

	public void setNomeTipoDocumentoCliente(String nomeTipoDocumentoCliente) {
		this.nomeTipoDocumentoCliente = nomeTipoDocumentoCliente;
	}

	public String getTipoDocumentoCliente() {
		return tipoDocumentoCliente;
	}

	public void setTipoDocumentoCliente(String tipoDocumentoCliente) {
		this.tipoDocumentoCliente = tipoDocumentoCliente;
	}

	public String getNumeroDocumentoCliente() {
		return numeroDocumentoCliente;
	}

	public void setNumeroDocumentoCliente(String numeroDocumentoCliente) {
		this.numeroDocumentoCliente = numeroDocumentoCliente;
	}

	public String getEnteRilascioDocumentoCliente() {
		return enteRilascioDocumentoCliente;
	}

	public void setEnteRilascioDocumentoCliente(String enteRilascioDocumentoCliente) {
		this.enteRilascioDocumentoCliente = enteRilascioDocumentoCliente;
	}

	public Date getDataScadenzaDocumentoCliente() {
		return dataScadenzaDocumentoCliente;
	}

	public void setDataScadenzaDocumentoCliente(Date dataScadenzaDocumentoCliente) {
		this.dataScadenzaDocumentoCliente = dataScadenzaDocumentoCliente;
	}

	public String getStatoResidenzaCliente() {
		return statoResidenzaCliente;
	}

	public void setStatoResidenzaCliente(String statoResidenzaCliente) {
		this.statoResidenzaCliente = statoResidenzaCliente;
	}

	public String getNomeStatoResidenzaCliente() {
		return nomeStatoResidenzaCliente;
	}

	public void setNomeStatoResidenzaCliente(String nomeStatoResidenzaCliente) {
		this.nomeStatoResidenzaCliente = nomeStatoResidenzaCliente;
	}

	public String getComuneResidenzaCliente() {
		return comuneResidenzaCliente;
	}

	public void setComuneResidenzaCliente(String comuneResidenzaCliente) {
		this.comuneResidenzaCliente = comuneResidenzaCliente;
	}

	public String getNomeComuneResidenzaCliente() {
		return nomeComuneResidenzaCliente;
	}

	public void setNomeComuneResidenzaCliente(String nomeComuneResidenzaCliente) {
		this.nomeComuneResidenzaCliente = nomeComuneResidenzaCliente;
	}

	public String getIndirizzoResidenzaCliente() {
		return indirizzoResidenzaCliente;
	}

	public void setIndirizzoResidenzaCliente(String indirizzoResidenzaCliente) {
		this.indirizzoResidenzaCliente = indirizzoResidenzaCliente;
	}

	public String getCivicoResidenzaCliente() {
		return civicoResidenzaCliente;
	}

	public void setCivicoResidenzaCliente(String civicoResidenzaCliente) {
		this.civicoResidenzaCliente = civicoResidenzaCliente;
	}

	public String getInternoResidenzaCliente() {
		return internoResidenzaCliente;
	}

	public void setInternoResidenzaCliente(String internoResidenzaCliente) {
		this.internoResidenzaCliente = internoResidenzaCliente;
	}

	public String getAppendiciResidenzaCliente() {
		return appendiciResidenzaCliente;
	}

	public void setAppendiciResidenzaCliente(String appendiciResidenzaCliente) {
		this.appendiciResidenzaCliente = appendiciResidenzaCliente;
	}

	public String getCapResidenzaCliente() {
		return capResidenzaCliente;
	}

	public void setCapResidenzaCliente(String capResidenzaCliente) {
		this.capResidenzaCliente = capResidenzaCliente;
	}

	public String getCittaResidenzaCliente() {
		return cittaResidenzaCliente;
	}

	public void setCittaResidenzaCliente(String cittaResidenzaCliente) {
		this.cittaResidenzaCliente = cittaResidenzaCliente;
	}

	public String getProvinciaResidenzaCliente() {
		return provinciaResidenzaCliente;
	}

	public void setProvinciaResidenzaCliente(String provinciaResidenzaCliente) {
		this.provinciaResidenzaCliente = provinciaResidenzaCliente;
	}

	public String getFlgDomicilioUgualeResidenzaDatiClienteForm() {
		return flgDomicilioUgualeResidenzaDatiClienteForm;
	}

	public void setFlgDomicilioUgualeResidenzaDatiClienteForm(String flgDomicilioUgualeResidenzaDatiClienteForm) {
		this.flgDomicilioUgualeResidenzaDatiClienteForm = flgDomicilioUgualeResidenzaDatiClienteForm;
	}

	public String getStatoDomicilioCliente() {
		return statoDomicilioCliente;
	}

	public void setStatoDomicilioCliente(String statoDomicilioCliente) {
		this.statoDomicilioCliente = statoDomicilioCliente;
	}

	public String getNomeStatoDomicilioCliente() {
		return nomeStatoDomicilioCliente;
	}

	public void setNomeStatoDomicilioCliente(String nomeStatoDomicilioCliente) {
		this.nomeStatoDomicilioCliente = nomeStatoDomicilioCliente;
	}

	public String getComuneDomicilioCliente() {
		return comuneDomicilioCliente;
	}

	public void setComuneDomicilioCliente(String comuneDomicilioCliente) {
		this.comuneDomicilioCliente = comuneDomicilioCliente;
	}

	public String getNomeComuneDomicilioCliente() {
		return nomeComuneDomicilioCliente;
	}

	public void setNomeComuneDomicilioCliente(String nomeComuneDomicilioCliente) {
		this.nomeComuneDomicilioCliente = nomeComuneDomicilioCliente;
	}

	public String getIndirizzoDomicilioCliente() {
		return indirizzoDomicilioCliente;
	}

	public void setIndirizzoDomicilioCliente(String indirizzoDomicilioCliente) {
		this.indirizzoDomicilioCliente = indirizzoDomicilioCliente;
	}

	public String getCivicoDomicilioCliente() {
		return civicoDomicilioCliente;
	}

	public void setCivicoDomicilioCliente(String civicoDomicilioCliente) {
		this.civicoDomicilioCliente = civicoDomicilioCliente;
	}

	public String getInternoDomicilioCliente() {
		return internoDomicilioCliente;
	}

	public void setInternoDomicilioCliente(String internoDomicilioCliente) {
		this.internoDomicilioCliente = internoDomicilioCliente;
	}

	public String getAppendiciDomicilioCliente() {
		return appendiciDomicilioCliente;
	}

	public void setAppendiciDomicilioCliente(String appendiciDomicilioCliente) {
		this.appendiciDomicilioCliente = appendiciDomicilioCliente;
	}

	public String getCapDomicilioCliente() {
		return capDomicilioCliente;
	}

	public void setCapDomicilioCliente(String capDomicilioCliente) {
		this.capDomicilioCliente = capDomicilioCliente;
	}

	public String getCittaDomicilioCliente() {
		return cittaDomicilioCliente;
	}

	public void setCittaDomicilioCliente(String cittaDomicilioCliente) {
		this.cittaDomicilioCliente = cittaDomicilioCliente;
	}

	public String getProvinciaDomicilioCliente() {
		return provinciaDomicilioCliente;
	}

	public void setProvinciaDomicilioCliente(String provinciaDomicilioCliente) {
		this.provinciaDomicilioCliente = provinciaDomicilioCliente;
	}

	public String getPodEE() {
		return podEE;
	}

	public void setPodEE(String podEE) {
		this.podEE = podEE;
	}

	public String getFlgDomensticoNonResidenteEE() {
		return flgDomensticoNonResidenteEE;
	}

	public void setFlgDomensticoNonResidenteEE(String flgDomensticoNonResidenteEE) {
		this.flgDomensticoNonResidenteEE = flgDomensticoNonResidenteEE;
	}

	public String getPotenzaDisponibileEE() {
		return potenzaDisponibileEE;
	}

	public void setPotenzaDisponibileEE(String potenzaDisponibileEE) {
		this.potenzaDisponibileEE = potenzaDisponibileEE;
	}

	public String getValorePotenzaImpegnataEE() {
		return valorePotenzaImpegnataEE;
	}

	public void setValorePotenzaImpegnataEE(String valorePotenzaImpegnataEE) {
		this.valorePotenzaImpegnataEE = valorePotenzaImpegnataEE;
	}

	public String getPotenzaImpegnataEE() {
		return potenzaImpegnataEE;
	}

	public void setPotenzaImpegnataEE(String potenzaImpegnataEE) {
		this.potenzaImpegnataEE = potenzaImpegnataEE;
	}

	public String getConsumoAnnuoEE() {
		return consumoAnnuoEE;
	}

	public void setConsumoAnnuoEE(String consumoAnnuoEE) {
		this.consumoAnnuoEE = consumoAnnuoEE;
	}

	public String getFlgMercatoLiberoEE() {
		return flgMercatoLiberoEE;
	}

	public void setFlgMercatoLiberoEE(String flgMercatoLiberoEE) {
		this.flgMercatoLiberoEE = flgMercatoLiberoEE;
	}

	public String getNomeFornitorePrecedenteEE() {
		return nomeFornitorePrecedenteEE;
	}

	public void setNomeFornitorePrecedenteEE(String nomeFornitorePrecedenteEE) {
		this.nomeFornitorePrecedenteEE = nomeFornitorePrecedenteEE;
	}

	public String getFornitorePrecedenteEE() {
		return fornitorePrecedenteEE;
	}

	public void setFornitorePrecedenteEE(String fornitorePrecedenteEE) {
		this.fornitorePrecedenteEE = fornitorePrecedenteEE;
	}

	public String getFlgServizioTutelaEE() {
		return flgServizioTutelaEE;
	}

	public void setFlgServizioTutelaEE(String flgServizioTutelaEE) {
		this.flgServizioTutelaEE = flgServizioTutelaEE;
	}

	public String getPdrGAS() {
		return pdrGAS;
	}

	public void setPdrGAS(String pdrGAS) {
		this.pdrGAS = pdrGAS;
	}

	public String getConsumoAnnuoGAS() {
		return consumoAnnuoGAS;
	}

	public void setConsumoAnnuoGAS(String consumoAnnuoGAS) {
		this.consumoAnnuoGAS = consumoAnnuoGAS;
	}

	public String getFlgMercatoLiberoGAS() {
		return flgMercatoLiberoGAS;
	}

	public void setFlgMercatoLiberoGAS(String flgMercatoLiberoGAS) {
		this.flgMercatoLiberoGAS = flgMercatoLiberoGAS;
	}

	public String getNomeFornitorePrecedenteGAS() {
		return nomeFornitorePrecedenteGAS;
	}

	public void setNomeFornitorePrecedenteGAS(String nomeFornitorePrecedenteGAS) {
		this.nomeFornitorePrecedenteGAS = nomeFornitorePrecedenteGAS;
	}

	public String getFornitorePrecedenteGAS() {
		return fornitorePrecedenteGAS;
	}

	public void setFornitorePrecedenteGAS(String fornitorePrecedenteGAS) {
		this.fornitorePrecedenteGAS = fornitorePrecedenteGAS;
	}

	public String getFlgServizioTutelaGAS() {
		return flgServizioTutelaGAS;
	}

	public void setFlgServizioTutelaGAS(String flgServizioTutelaGAS) {
		this.flgServizioTutelaGAS = flgServizioTutelaGAS;
	}

	public String getTipologiaModalitaRicezione() {
		return tipologiaModalitaRicezione;
	}

	public void setTipologiaModalitaRicezione(String tipologiaModalitaRicezione) {
		this.tipologiaModalitaRicezione = tipologiaModalitaRicezione;
	}

	public String getIndirizzoEmailModalitaRicezione() {
		return indirizzoEmailModalitaRicezione;
	}

	public void setIndirizzoEmailModalitaRicezione(String indirizzoEmailModalitaRicezione) {
		this.indirizzoEmailModalitaRicezione = indirizzoEmailModalitaRicezione;
	}

	public String getStatoRicezionePosta() {
		return statoRicezionePosta;
	}

	public void setStatoRicezionePosta(String statoRicezionePosta) {
		this.statoRicezionePosta = statoRicezionePosta;
	}

	public String getNomeStatoRicezionePosta() {
		return nomeStatoRicezionePosta;
	}

	public void setNomeStatoRicezionePosta(String nomeStatoRicezionePosta) {
		this.nomeStatoRicezionePosta = nomeStatoRicezionePosta;
	}

	public String getComuneRicezionePosta() {
		return comuneRicezionePosta;
	}

	public void setComuneRicezionePosta(String comuneRicezionePosta) {
		this.comuneRicezionePosta = comuneRicezionePosta;
	}

	public String getNomeComuneRicezionePosta() {
		return nomeComuneRicezionePosta;
	}

	public void setNomeComuneRicezionePosta(String nomeComuneRicezionePosta) {
		this.nomeComuneRicezionePosta = nomeComuneRicezionePosta;
	}

	public String getIndirizzoRicezionePosta() {
		return indirizzoRicezionePosta;
	}

	public void setIndirizzoRicezionePosta(String indirizzoRicezionePosta) {
		this.indirizzoRicezionePosta = indirizzoRicezionePosta;
	}

	public String getCivicoRicezionePosta() {
		return civicoRicezionePosta;
	}

	public void setCivicoRicezionePosta(String civicoRicezionePosta) {
		this.civicoRicezionePosta = civicoRicezionePosta;
	}

	public String getInternoRicezionePosta() {
		return internoRicezionePosta;
	}

	public void setInternoRicezionePosta(String internoRicezionePosta) {
		this.internoRicezionePosta = internoRicezionePosta;
	}

	public String getAppendiciRicezionePosta() {
		return appendiciRicezionePosta;
	}

	public void setAppendiciRicezionePosta(String appendiciRicezionePosta) {
		this.appendiciRicezionePosta = appendiciRicezionePosta;
	}

	public String getCapRicezionePosta() {
		return capRicezionePosta;
	}

	public void setCapRicezionePosta(String capRicezionePosta) {
		this.capRicezionePosta = capRicezionePosta;
	}

	public String getCittaRicezionePosta() {
		return cittaRicezionePosta;
	}

	public void setCittaRicezionePosta(String cittaRicezionePosta) {
		this.cittaRicezionePosta = cittaRicezionePosta;
	}

	public String getProvinciaRicezionePosta() {
		return provinciaRicezionePosta;
	}

	public void setProvinciaRicezionePosta(String provinciaRicezionePosta) {
		this.provinciaRicezionePosta = provinciaRicezionePosta;
	}

	public String getNomeModalitaPagamento() {
		return nomeModalitaPagamento;
	}

	public void setNomeModalitaPagamento(String nomeModalitaPagamento) {
		this.nomeModalitaPagamento = nomeModalitaPagamento;
	}

	public String getModalitaPagamento() {
		return modalitaPagamento;
	}

	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}

	public String getNumeroLibrettoSocioCoop() {
		return numeroLibrettoSocioCoop;
	}

	public void setNumeroLibrettoSocioCoop(String numeroLibrettoSocioCoop) {
		this.numeroLibrettoSocioCoop = numeroLibrettoSocioCoop;
	}

	public String getIntestatarioLibrettoSocioCoop() {
		return intestatarioLibrettoSocioCoop;
	}

	public void setIntestatarioLibrettoSocioCoop(String intestatarioLibrettoSocioCoop) {
		this.intestatarioLibrettoSocioCoop = intestatarioLibrettoSocioCoop;
	}

	public String getCodiceIBANContoCorrente() {
		return codiceIBANContoCorrente;
	}

	public void setCodiceIBANContoCorrente(String codiceIBANContoCorrente) {
		this.codiceIBANContoCorrente = codiceIBANContoCorrente;
	}

	public String getIntestatarioContoCorrente() {
		return intestatarioContoCorrente;
	}

	public void setIntestatarioContoCorrente(String intestatarioContoCorrente) {
		this.intestatarioContoCorrente = intestatarioContoCorrente;
	}

	public String getCodiceFiscaleIntestatarioContoCorrente() {
		return codiceFiscaleIntestatarioContoCorrente;
	}

	public void setCodiceFiscaleIntestatarioContoCorrente(String codiceFiscaleIntestatarioContoCorrente) {
		this.codiceFiscaleIntestatarioContoCorrente = codiceFiscaleIntestatarioContoCorrente;
	}

	public String getFlgNota1InformativaPrivacyForm() {
		return flgNota1InformativaPrivacyForm;
	}

	public void setFlgNota1InformativaPrivacyForm(String flgNota1InformativaPrivacyForm) {
		this.flgNota1InformativaPrivacyForm = flgNota1InformativaPrivacyForm;
	}

	public String getFlgNota2InformativaPrivacyForm() {
		return flgNota2InformativaPrivacyForm;
	}

	public void setFlgNota2InformativaPrivacyForm(String flgNota2InformativaPrivacyForm) {
		this.flgNota2InformativaPrivacyForm = flgNota2InformativaPrivacyForm;
	}

	public String getFlgDatiCatastaliImmobileForm() {
		return flgDatiCatastaliImmobileForm;
	}

	public void setFlgDatiCatastaliImmobileForm(String flgDatiCatastaliImmobileForm) {
		this.flgDatiCatastaliImmobileForm = flgDatiCatastaliImmobileForm;
	}

	public String getNominativoClienteDatiCatastaliForm() {
		return nominativoClienteDatiCatastaliForm;
	}

	public void setNominativoClienteDatiCatastaliForm(String nominativoClienteDatiCatastaliForm) {
		this.nominativoClienteDatiCatastaliForm = nominativoClienteDatiCatastaliForm;
	}

	public String getCodiceFiscaleClienteDatiCatastaliForm() {
		return codiceFiscaleClienteDatiCatastaliForm;
	}

	public void setCodiceFiscaleClienteDatiCatastaliForm(String codiceFiscaleClienteDatiCatastaliForm) {
		this.codiceFiscaleClienteDatiCatastaliForm = codiceFiscaleClienteDatiCatastaliForm;
	}

	public String getComuneNascitaClienteDatiCatastaliForm() {
		return comuneNascitaClienteDatiCatastaliForm;
	}

	public void setComuneNascitaClienteDatiCatastaliForm(String comuneNascitaClienteDatiCatastaliForm) {
		this.comuneNascitaClienteDatiCatastaliForm = comuneNascitaClienteDatiCatastaliForm;
	}

	public String getNomeComuneNascitaClienteDatiCatastaliForm() {
		return nomeComuneNascitaClienteDatiCatastaliForm;
	}

	public void setNomeComuneNascitaClienteDatiCatastaliForm(String nomeComuneNascitaClienteDatiCatastaliForm) {
		this.nomeComuneNascitaClienteDatiCatastaliForm = nomeComuneNascitaClienteDatiCatastaliForm;
	}

	public Date getDataNascitaClienteDatiCatastaliForm() {
		return dataNascitaClienteDatiCatastaliForm;
	}

	public void setDataNascitaClienteDatiCatastaliForm(Date dataNascitaClienteDatiCatastaliForm) {
		this.dataNascitaClienteDatiCatastaliForm = dataNascitaClienteDatiCatastaliForm;
	}

	public String getComuneResidenzaClienteDatiCatastaliForm() {
		return comuneResidenzaClienteDatiCatastaliForm;
	}

	public void setComuneResidenzaClienteDatiCatastaliForm(String comuneResidenzaClienteDatiCatastaliForm) {
		this.comuneResidenzaClienteDatiCatastaliForm = comuneResidenzaClienteDatiCatastaliForm;
	}

	public String getNomeComuneResidenzaClienteDatiCatastaliForm() {
		return nomeComuneResidenzaClienteDatiCatastaliForm;
	}

	public void setNomeComuneResidenzaClienteDatiCatastaliForm(String nomeComuneResidenzaClienteDatiCatastaliForm) {
		this.nomeComuneResidenzaClienteDatiCatastaliForm = nomeComuneResidenzaClienteDatiCatastaliForm;
	}

	public String getViaResidenzaClienteDatiCatastaliForm() {
		return viaResidenzaClienteDatiCatastaliForm;
	}

	public void setViaResidenzaClienteDatiCatastaliForm(String viaResidenzaClienteDatiCatastaliForm) {
		this.viaResidenzaClienteDatiCatastaliForm = viaResidenzaClienteDatiCatastaliForm;
	}

	public String getCivicoResidenzaClienteDatiCatastaliForm() {
		return civicoResidenzaClienteDatiCatastaliForm;
	}

	public void setCivicoResidenzaClienteDatiCatastaliForm(String civicoResidenzaClienteDatiCatastaliForm) {
		this.civicoResidenzaClienteDatiCatastaliForm = civicoResidenzaClienteDatiCatastaliForm;
	}

	public String getFlg1DatiCatastaliImmobileForm() {
		return flg1DatiCatastaliImmobileForm;
	}

	public void setFlg1DatiCatastaliImmobileForm(String flg1DatiCatastaliImmobileForm) {
		this.flg1DatiCatastaliImmobileForm = flg1DatiCatastaliImmobileForm;
	}

	public String getFlg2DatiCatastaliImmobileForm() {
		return flg2DatiCatastaliImmobileForm;
	}

	public void setFlg2DatiCatastaliImmobileForm(String flg2DatiCatastaliImmobileForm) {
		this.flg2DatiCatastaliImmobileForm = flg2DatiCatastaliImmobileForm;
	}

	public String getAgenziaEntrateFlg2DatiCatastaliImmobileForm() {
		return agenziaEntrateFlg2DatiCatastaliImmobileForm;
	}

	public void setAgenziaEntrateFlg2DatiCatastaliImmobileForm(String agenziaEntrateFlg2DatiCatastaliImmobileForm) {
		this.agenziaEntrateFlg2DatiCatastaliImmobileForm = agenziaEntrateFlg2DatiCatastaliImmobileForm;
	}

	public String getNomeAgenziaEntrateFlg2DatiCatastaliImmobileForm() {
		return nomeAgenziaEntrateFlg2DatiCatastaliImmobileForm;
	}

	public void setNomeAgenziaEntrateFlg2DatiCatastaliImmobileForm(String nomeAgenziaEntrateFlg2DatiCatastaliImmobileForm) {
		this.nomeAgenziaEntrateFlg2DatiCatastaliImmobileForm = nomeAgenziaEntrateFlg2DatiCatastaliImmobileForm;
	}

	public Date getDataRegistrazioneFlg2DatiCatastaliImmobileForm() {
		return dataRegistrazioneFlg2DatiCatastaliImmobileForm;
	}

	public void setDataRegistrazioneFlg2DatiCatastaliImmobileForm(Date dataRegistrazioneFlg2DatiCatastaliImmobileForm) {
		this.dataRegistrazioneFlg2DatiCatastaliImmobileForm = dataRegistrazioneFlg2DatiCatastaliImmobileForm;
	}

	public String getNumeroRegistrazioneFlg2DatiCatastaliImmobileForm() {
		return numeroRegistrazioneFlg2DatiCatastaliImmobileForm;
	}

	public void setNumeroRegistrazioneFlg2DatiCatastaliImmobileForm(
			String numeroRegistrazioneFlg2DatiCatastaliImmobileForm) {
		this.numeroRegistrazioneFlg2DatiCatastaliImmobileForm = numeroRegistrazioneFlg2DatiCatastaliImmobileForm;
	}

	public String getFlg3DatiCatastaliImmobileForm() {
		return flg3DatiCatastaliImmobileForm;
	}

	public void setFlg3DatiCatastaliImmobileForm(String flg3DatiCatastaliImmobileForm) {
		this.flg3DatiCatastaliImmobileForm = flg3DatiCatastaliImmobileForm;
	}

	public String getNumeroContrattoConsegnaAbitazioneDatiCatastaliImmobileForm() {
		return numeroContrattoConsegnaAbitazioneDatiCatastaliImmobileForm;
	}

	public void setNumeroContrattoConsegnaAbitazioneDatiCatastaliImmobileForm(
			String numeroContrattoConsegnaAbitazioneDatiCatastaliImmobileForm) {
		this.numeroContrattoConsegnaAbitazioneDatiCatastaliImmobileForm = numeroContrattoConsegnaAbitazioneDatiCatastaliImmobileForm;
	}

	public Date getDataContrattoConsegnaAbitazioneDatiCatastaliImmobileForm() {
		return dataContrattoConsegnaAbitazioneDatiCatastaliImmobileForm;
	}

	public void setDataContrattoConsegnaAbitazioneDatiCatastaliImmobileForm(
			Date dataContrattoConsegnaAbitazioneDatiCatastaliImmobileForm) {
		this.dataContrattoConsegnaAbitazioneDatiCatastaliImmobileForm = dataContrattoConsegnaAbitazioneDatiCatastaliImmobileForm;
	}

	public String getFlg4DatiCatastaliImmobileForm() {
		return flg4DatiCatastaliImmobileForm;
	}

	public void setFlg4DatiCatastaliImmobileForm(String flg4DatiCatastaliImmobileForm) {
		this.flg4DatiCatastaliImmobileForm = flg4DatiCatastaliImmobileForm;
	}

	public String getAgenziaEntrateFlg4DatiCatastaliImmobileForm() {
		return agenziaEntrateFlg4DatiCatastaliImmobileForm;
	}

	public void setAgenziaEntrateFlg4DatiCatastaliImmobileForm(String agenziaEntrateFlg4DatiCatastaliImmobileForm) {
		this.agenziaEntrateFlg4DatiCatastaliImmobileForm = agenziaEntrateFlg4DatiCatastaliImmobileForm;
	}

	public String getNomeAgenziaEntrateFlg4DatiCatastaliImmobileForm() {
		return nomeAgenziaEntrateFlg4DatiCatastaliImmobileForm;
	}

	public void setNomeAgenziaEntrateFlg4DatiCatastaliImmobileForm(String nomeAgenziaEntrateFlg4DatiCatastaliImmobileForm) {
		this.nomeAgenziaEntrateFlg4DatiCatastaliImmobileForm = nomeAgenziaEntrateFlg4DatiCatastaliImmobileForm;
	}

	public Date getDataRegistrazioneFlg4DatiCatastaliImmobileForm() {
		return dataRegistrazioneFlg4DatiCatastaliImmobileForm;
	}

	public void setDataRegistrazioneFlg4DatiCatastaliImmobileForm(Date dataRegistrazioneFlg4DatiCatastaliImmobileForm) {
		this.dataRegistrazioneFlg4DatiCatastaliImmobileForm = dataRegistrazioneFlg4DatiCatastaliImmobileForm;
	}

	public String getNumeroRegistrazioneFlg4DatiCatastaliImmobileForm() {
		return numeroRegistrazioneFlg4DatiCatastaliImmobileForm;
	}

	public void setNumeroRegistrazioneFlg4DatiCatastaliImmobileForm(
			String numeroRegistrazioneFlg4DatiCatastaliImmobileForm) {
		this.numeroRegistrazioneFlg4DatiCatastaliImmobileForm = numeroRegistrazioneFlg4DatiCatastaliImmobileForm;
	}

	public String getFlg5DatiCatastaliImmobileForm() {
		return flg5DatiCatastaliImmobileForm;
	}

	public void setFlg5DatiCatastaliImmobileForm(String flg5DatiCatastaliImmobileForm) {
		this.flg5DatiCatastaliImmobileForm = flg5DatiCatastaliImmobileForm;
	}

	public String getTipoTitoloDetenzioneDatiCatastaliImmobileForm() {
		return tipoTitoloDetenzioneDatiCatastaliImmobileForm;
	}

	public void setTipoTitoloDetenzioneDatiCatastaliImmobileForm(String tipoTitoloDetenzioneDatiCatastaliImmobileForm) {
		this.tipoTitoloDetenzioneDatiCatastaliImmobileForm = tipoTitoloDetenzioneDatiCatastaliImmobileForm;
	}

	public String getAttoAcquisizioneDatiCatastaliImmobileForm() {
		return attoAcquisizioneDatiCatastaliImmobileForm;
	}

	public void setAttoAcquisizioneDatiCatastaliImmobileForm(String attoAcquisizioneDatiCatastaliImmobileForm) {
		this.attoAcquisizioneDatiCatastaliImmobileForm = attoAcquisizioneDatiCatastaliImmobileForm;
	}

	public String getAgenziaEntrateFlg5DatiCatastaliImmobileForm() {
		return agenziaEntrateFlg5DatiCatastaliImmobileForm;
	}

	public void setAgenziaEntrateFlg5DatiCatastaliImmobileForm(String agenziaEntrateFlg5DatiCatastaliImmobileForm) {
		this.agenziaEntrateFlg5DatiCatastaliImmobileForm = agenziaEntrateFlg5DatiCatastaliImmobileForm;
	}

	public String getNomeAgenziaEntrateFlg5DatiCatastaliImmobileForm() {
		return nomeAgenziaEntrateFlg5DatiCatastaliImmobileForm;
	}

	public void setNomeAgenziaEntrateFlg5DatiCatastaliImmobileForm(String nomeAgenziaEntrateFlg5DatiCatastaliImmobileForm) {
		this.nomeAgenziaEntrateFlg5DatiCatastaliImmobileForm = nomeAgenziaEntrateFlg5DatiCatastaliImmobileForm;
	}

	public Date getDataRegistrazioneFlg5DatiCatastaliImmobileForm() {
		return dataRegistrazioneFlg5DatiCatastaliImmobileForm;
	}

	public void setDataRegistrazioneFlg5DatiCatastaliImmobileForm(Date dataRegistrazioneFlg5DatiCatastaliImmobileForm) {
		this.dataRegistrazioneFlg5DatiCatastaliImmobileForm = dataRegistrazioneFlg5DatiCatastaliImmobileForm;
	}

	public String getNumeroRegistrazioneFlg5DatiCatastaliImmobileForm() {
		return numeroRegistrazioneFlg5DatiCatastaliImmobileForm;
	}

	public void setNumeroRegistrazioneFlg5DatiCatastaliImmobileForm(
			String numeroRegistrazioneFlg5DatiCatastaliImmobileForm) {
		this.numeroRegistrazioneFlg5DatiCatastaliImmobileForm = numeroRegistrazioneFlg5DatiCatastaliImmobileForm;
	}

	public String getCodComuneCatastoClienteDatiCatastaliForm() {
		return codComuneCatastoClienteDatiCatastaliForm;
	}

	public void setCodComuneCatastoClienteDatiCatastaliForm(String codComuneCatastoClienteDatiCatastaliForm) {
		this.codComuneCatastoClienteDatiCatastaliForm = codComuneCatastoClienteDatiCatastaliForm;
	}

	public String getTipoCatastoClienteDatiCatastaliForm() {
		return tipoCatastoClienteDatiCatastaliForm;
	}

	public void setTipoCatastoClienteDatiCatastaliForm(String tipoCatastoClienteDatiCatastaliForm) {
		this.tipoCatastoClienteDatiCatastaliForm = tipoCatastoClienteDatiCatastaliForm;
	}

	public String getNomeTipoCatastoClienteDatiCatastaliForm() {
		return nomeTipoCatastoClienteDatiCatastaliForm;
	}

	public void setNomeTipoCatastoClienteDatiCatastaliForm(String nomeTipoCatastoClienteDatiCatastaliForm) {
		this.nomeTipoCatastoClienteDatiCatastaliForm = nomeTipoCatastoClienteDatiCatastaliForm;
	}

	public String getSezioneCatastoClienteDatiCatastaliForm() {
		return sezioneCatastoClienteDatiCatastaliForm;
	}

	public void setSezioneCatastoClienteDatiCatastaliForm(String sezioneCatastoClienteDatiCatastaliForm) {
		this.sezioneCatastoClienteDatiCatastaliForm = sezioneCatastoClienteDatiCatastaliForm;
	}

	public String getFoglioCatastoClienteDatiCatastaliForm() {
		return foglioCatastoClienteDatiCatastaliForm;
	}

	public void setFoglioCatastoClienteDatiCatastaliForm(String foglioCatastoClienteDatiCatastaliForm) {
		this.foglioCatastoClienteDatiCatastaliForm = foglioCatastoClienteDatiCatastaliForm;
	}

	public String getSubAlternoCatastoClienteDatiCatastaliForm() {
		return subAlternoCatastoClienteDatiCatastaliForm;
	}

	public void setSubAlternoCatastoClienteDatiCatastaliForm(String subAlternoCatastoClienteDatiCatastaliForm) {
		this.subAlternoCatastoClienteDatiCatastaliForm = subAlternoCatastoClienteDatiCatastaliForm;
	}

	public String getNumeroParticellaCatastoClienteDatiCatastaliForm() {
		return numeroParticellaCatastoClienteDatiCatastaliForm;
	}

	public void setNumeroParticellaCatastoClienteDatiCatastaliForm(String numeroParticellaCatastoClienteDatiCatastaliForm) {
		this.numeroParticellaCatastoClienteDatiCatastaliForm = numeroParticellaCatastoClienteDatiCatastaliForm;
	}

	public Date getDataConsegnaContrattoAllegatoC() {
		return dataConsegnaContrattoAllegatoC;
	}

	public void setDataConsegnaContrattoAllegatoC(Date dataConsegnaContrattoAllegatoC) {
		this.dataConsegnaContrattoAllegatoC = dataConsegnaContrattoAllegatoC;
	}

	public String getOraConsegnaContrattoAllegatoC() {
		return oraConsegnaContrattoAllegatoC;
	}

	public void setOraConsegnaContrattoAllegatoC(String oraConsegnaContrattoAllegatoC) {
		this.oraConsegnaContrattoAllegatoC = oraConsegnaContrattoAllegatoC;
	}

	public String getNomeCognomeVenditoreAllegatoC() {
		return nomeCognomeVenditoreAllegatoC;
	}

	public void setNomeCognomeVenditoreAllegatoC(String nomeCognomeVenditoreAllegatoC) {
		this.nomeCognomeVenditoreAllegatoC = nomeCognomeVenditoreAllegatoC;
	}

	public String getFlgClienteContattatoDalPersonaleAllegatoC() {
		return flgClienteContattatoDalPersonaleAllegatoC;
	}

	public void setFlgClienteContattatoDalPersonaleAllegatoC(String flgClienteContattatoDalPersonaleAllegatoC) {
		this.flgClienteContattatoDalPersonaleAllegatoC = flgClienteContattatoDalPersonaleAllegatoC;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTicketNETA() {
		return ticketNETA;
	}

	public void setTicketNETA(String ticketNETA) {
		this.ticketNETA = ticketNETA;
	}

	public Boolean getAbilitaSalvaContrattoInBozza() {
		return abilitaSalvaContrattoInBozza;
	}

	public void setAbilitaSalvaContrattoInBozza(Boolean abilitaSalvaContrattoInBozza) {
		this.abilitaSalvaContrattoInBozza = abilitaSalvaContrattoInBozza;
	}

	public Boolean getAbilitaArchiviaContratto() {
		return abilitaArchiviaContratto;
	}

	public void setAbilitaArchiviaContratto(Boolean abilitaArchiviaContratto) {
		this.abilitaArchiviaContratto = abilitaArchiviaContratto;
	}

	public Boolean getAbilitaStampaPDFContratto() {
		return abilitaStampaPDFContratto;
	}

	public void setAbilitaStampaPDFContratto(Boolean abilitaStampaPDFContratto) {
		this.abilitaStampaPDFContratto = abilitaStampaPDFContratto;
	}

	public Boolean getAbilitaModificaContratto() {
		return abilitaModificaContratto;
	}

	public void setAbilitaModificaContratto(Boolean abilitaModificaContratto) {
		this.abilitaModificaContratto = abilitaModificaContratto;
	}

	public Boolean getAbilitaUploadContratto() {
		return abilitaUploadContratto;
	}

	public void setAbilitaUploadContratto(Boolean abilitaUploadContratto) {
		this.abilitaUploadContratto = abilitaUploadContratto;
	}

	public Boolean getAbilitaChiusuraLavorazione() {
		return abilitaChiusuraLavorazione;
	}

	public void setAbilitaChiusuraLavorazione(Boolean abilitaChiusuraLavorazione) {
		this.abilitaChiusuraLavorazione = abilitaChiusuraLavorazione;
	}

	public String getCodComuneAlgResidenzaCliente() {
		return codComuneAlgResidenzaCliente;
	}

	public void setCodComuneAlgResidenzaCliente(String codComuneAlgResidenzaCliente) {
		this.codComuneAlgResidenzaCliente = codComuneAlgResidenzaCliente;
	}

	public String getCodComuneAlgDomicilioCliente() {
		return codComuneAlgDomicilioCliente;
	}

	public void setCodComuneAlgDomicilioCliente(String codComuneAlgDomicilioCliente) {
		this.codComuneAlgDomicilioCliente = codComuneAlgDomicilioCliente;
	}

	public String getCodIstatRegioneResidenzaCliente() {
		return codIstatRegioneResidenzaCliente;
	}

	public void setCodIstatRegioneResidenzaCliente(
			String codIstatRegioneResidenzaCliente) {
		this.codIstatRegioneResidenzaCliente = codIstatRegioneResidenzaCliente;
	}

	public String getCodIstatRegioneDomicilioCliente() {
		return codIstatRegioneDomicilioCliente;
	}

	public void setCodIstatRegioneDomicilioCliente(
			String codIstatRegioneDomicilioCliente) {
		this.codIstatRegioneDomicilioCliente = codIstatRegioneDomicilioCliente;
	}

	public String getDescRegioneResidenzaCliente() {
		return descRegioneResidenzaCliente;
	}

	public void setDescRegioneResidenzaCliente(String descRegioneResidenzaCliente) {
		this.descRegioneResidenzaCliente = descRegioneResidenzaCliente;
	}

	public String getDescProvinciaResidenzaCliente() {
		return descProvinciaResidenzaCliente;
	}

	public void setDescProvinciaResidenzaCliente(
			String descProvinciaResidenzaCliente) {
		this.descProvinciaResidenzaCliente = descProvinciaResidenzaCliente;
	}

	public String getDescRegioneDomicilioCliente() {
		return descRegioneDomicilioCliente;
	}

	public void setDescRegioneDomicilioCliente(String descRegioneDomicilioCliente) {
		this.descRegioneDomicilioCliente = descRegioneDomicilioCliente;
	}

	public String getDescProvinciaDomicilioCliente() {
		return descProvinciaDomicilioCliente;
	}

	public void setDescProvinciaDomicilioCliente(
			String descProvinciaDomicilioCliente) {
		this.descProvinciaDomicilioCliente = descProvinciaDomicilioCliente;
	}

	public String getCodIstatRegioneRicezionePosta() {
		return codIstatRegioneRicezionePosta;
	}

	public void setCodIstatRegioneRicezionePosta(
			String codIstatRegioneRicezionePosta) {
		this.codIstatRegioneRicezionePosta = codIstatRegioneRicezionePosta;
	}

	public String getDescRegioneRicezionePosta() {
		return descRegioneRicezionePosta;
	}

	public void setDescRegioneRicezionePosta(String descRegioneRicezionePosta) {
		this.descRegioneRicezionePosta = descRegioneRicezionePosta;
	}

	public String getDescProvinciaRicezionePosta() {
		return descProvinciaRicezionePosta;
	}

	public void setDescProvinciaRicezionePosta(String descProvinciaRicezionePosta) {
		this.descProvinciaRicezionePosta = descProvinciaRicezionePosta;
	}

	public String getLabelSocioCoopDatiClienteForm() {
		return labelSocioCoopDatiClienteForm;
	}

	public void setLabelSocioCoopDatiClienteForm(
			String labelSocioCoopDatiClienteForm) {
		this.labelSocioCoopDatiClienteForm = labelSocioCoopDatiClienteForm;
	}

	public String getDesCliente() {
		return desCliente;
	}

	public void setDesCliente(String desCliente) {
		this.desCliente = desCliente;
	}

	public String getCodiceTipoDoc() {
		return codiceTipoDoc;
	}

	public void setCodiceTipoDoc(String codiceTipoDoc) {
		this.codiceTipoDoc = codiceTipoDoc;
	}

	public String getCodiceProcesso() {
		return codiceProcesso;
	}

	public void setCodiceProcesso(String codiceProcesso) {
		this.codiceProcesso = codiceProcesso;
	}

	public String getCategoriaSocioPuntoVenditaSelezionato() {
		return categoriaSocioPuntoVenditaSelezionato;
	}

	public void setCategoriaSocioPuntoVenditaSelezionato(
			String categoriaSocioPuntoVenditaSelezionato) {
		this.categoriaSocioPuntoVenditaSelezionato = categoriaSocioPuntoVenditaSelezionato;
	}

	public String getCodicePromoAmico() {
		return codicePromoAmico;
	}

	public void setCodicePromoAmico(String codicePromoAmico) {
		this.codicePromoAmico = codicePromoAmico;
	}

	public String getFlgNota3InformativaPrivacyForm() {
		return flgNota3InformativaPrivacyForm;
	}

	public void setFlgNota3InformativaPrivacyForm(
			String flgNota3InformativaPrivacyForm) {
		this.flgNota3InformativaPrivacyForm = flgNota3InformativaPrivacyForm;
	}

	public String getFlgNota4InformativaPrivacyForm() {
		return flgNota4InformativaPrivacyForm;
	}

	public void setFlgNota4InformativaPrivacyForm(
			String flgNota4InformativaPrivacyForm) {
		this.flgNota4InformativaPrivacyForm = flgNota4InformativaPrivacyForm;
	}

	public String getFlgNota5InformativaPrivacyForm() {
		return flgNota5InformativaPrivacyForm;
	}

	public void setFlgNota5InformativaPrivacyForm(
			String flgNota5InformativaPrivacyForm) {
		this.flgNota5InformativaPrivacyForm = flgNota5InformativaPrivacyForm;
	}

	public String getFlgNota6InformativaPrivacyForm() {
		return flgNota6InformativaPrivacyForm;
	}

	public void setFlgNota6InformativaPrivacyForm(
			String flgNota6InformativaPrivacyForm) {
		this.flgNota6InformativaPrivacyForm = flgNota6InformativaPrivacyForm;
	}

	public String getFlgNota7InformativaPrivacyForm() {
		return flgNota7InformativaPrivacyForm;
	}

	public void setFlgNota7InformativaPrivacyForm(
			String flgNota7InformativaPrivacyForm) {
		this.flgNota7InformativaPrivacyForm = flgNota7InformativaPrivacyForm;
	}	
}
