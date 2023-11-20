<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
	version="1.1" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:a="http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2"
	xmlns:d="http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.0">
	<xsl:output method="html" />
	
	<xsl:decimal-format name="euro" decimal-separator="," grouping-separator="."/>

	<xsl:template name="FormatDate">
		<xsl:param name="DateTime" />

		<xsl:variable name="year" select="substring($DateTime,1,4)" />
		<xsl:variable name="month" select="substring($DateTime,6,2)" />
		<xsl:variable name="day" select="substring($DateTime,9,2)" />

		<xsl:value-of select="' ('" />
		<xsl:value-of select="$day" />
		<xsl:value-of select="' '" />
		<xsl:choose>
			<xsl:when test="$month = '1' or $month = '01'">
				Gennaio
			</xsl:when>
			<xsl:when test="$month = '2' or $month = '02'">
				Febbraio
			</xsl:when>
			<xsl:when test="$month = '3' or $month = '03'">
				Marzo
			</xsl:when>
			<xsl:when test="$month = '4' or $month = '04'">
				Aprile
			</xsl:when>
			<xsl:when test="$month = '5' or $month = '05'">
				Maggio
			</xsl:when>
			<xsl:when test="$month = '6' or $month = '06'">
				Giugno
			</xsl:when>
			<xsl:when test="$month = '7' or $month = '07'">
				Luglio
			</xsl:when>
			<xsl:when test="$month = '8' or $month = '08'">
				Agosto
			</xsl:when>
			<xsl:when test="$month = '9' or $month = '09'">
				Settembre
			</xsl:when>
			<xsl:when test="$month = '10'">
				Ottobre
			</xsl:when>
			<xsl:when test="$month = '11'">
				Novembre
			</xsl:when>
			<xsl:when test="$month = '12'">
				Dicembre
			</xsl:when>
			<xsl:otherwise>
				Mese non riconosciuto
			</xsl:otherwise>
		</xsl:choose>
		<xsl:value-of select="' '" />
		<xsl:value-of select="$year" />

		<xsl:variable name="time" select="substring($DateTime,12)" />
		<xsl:if test="$time != ''">
			<xsl:variable name="hh" select="substring($time,1,2)" />
			<xsl:variable name="mm" select="substring($time,4,2)" />
			<xsl:variable name="ss" select="substring($time,7,2)" />

			<xsl:value-of select="' '" />
			<xsl:value-of select="$hh" />
			<xsl:value-of select="':'" />
			<xsl:value-of select="$mm" />
			<xsl:value-of select="':'" />
			<xsl:value-of select="$ss" />
		</xsl:if>
		<xsl:value-of select="')'" />
	</xsl:template>
	<xsl:template name="FormatDateShort">
		<xsl:param name="DateTime" />

		<xsl:variable name="year" select="substring($DateTime,1,4)" />
		<xsl:variable name="month" select="substring($DateTime,6,2)" />
		<xsl:variable name="day" select="substring($DateTime,9,2)" />

		<xsl:value-of select="$day" />
		<xsl:value-of select="'/'" />
		<xsl:value-of select="$month" />
		<xsl:value-of select="'/'" />
		<xsl:value-of select="$year" />
	</xsl:template>

	<xsl:template name="TipoDocumento">
		<xsl:param name="pTipoDocumento"/>
		<xsl:choose>
			<xsl:when test="$pTipoDocumento='TD01'">Fattura</xsl:when>
			<xsl:when test="$pTipoDocumento='TD02'">Acconto/anticipo su fattura</xsl:when>
			<xsl:when test="$pTipoDocumento='TD03'">Acconto/anticipo su parcella</xsl:when>
			<xsl:when test="$pTipoDocumento='TD04'">Nota di credito</xsl:when>
			<xsl:when test="$pTipoDocumento='TD05'">Nota di debito</xsl:when>
			<xsl:when test="$pTipoDocumento='TD06'">Parcella</xsl:when>
			<xsl:when test="$pTipoDocumento='TD07'">Fattura semplificata</xsl:when>
			<xsl:when test="$pTipoDocumento='TD08'">Nota di credito semplificata</xsl:when>
			<xsl:when test="$pTipoDocumento='TD09'">Nota di debito semplificata</xsl:when>
			<xsl:otherwise>TipoDocumento non riconosciuto</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="RegimeFiscale">
		<xsl:param name="pRegimeFiscale"/>
		<xsl:choose>
			<xsl:when test="$pRegimeFiscale='RF01'">Ordinario</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF02'">Contribuenti minimi</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF03'">Nuove iniziative produttive</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF04'">Agricoltura e attivit&#224; connesse e pesca</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF05'">Vendita sali e tabacchi</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF06'">Commercio fiammiferi</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF07'">Editoria</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF08'">Gestione servizi telefonia pubblica</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF09'">Rivendita documenti di trasporto pubblico e di sosta</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF10'">Intrattenimenti, giochi e altre attivit&#224; di cui alla tariffa allegata al DPR 640/72</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF11'">Agenzie viaggi e turismo</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF12'">Agriturismo</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF13'">Vendite a domicilio</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF14'">Rivendita beni usati, oggetti d'arte, d'antiquariato o da collezione</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF15'">Agenzie di vendite all'asta di oggetti d'arte, antiquariato o da collezione</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF16'">IVA per cassa P.A.</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF17'">IVA per cassa soggetti con vol. d'affari inferiore ad euro 200.000</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF18'">Altro</xsl:when>
			<xsl:when test="$pRegimeFiscale='RF19'">Regime forfettario (art.1, c.54-89, L. 190/2014)</xsl:when>
			<xsl:otherwise>Codice non riconosciuto</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="StatoLiquidazione">
		<xsl:param name="pStatoLiquidazione"/>
		<xsl:choose>
			<xsl:when test="$pStatoLiquidazione='LS'">In liquidazione</xsl:when>
			<xsl:when test="$pStatoLiquidazione='LN'">Non in liquidazione</xsl:when>
			<xsl:otherwise>Codice non riconosciuto</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="SocioUnico">
		<xsl:param name="pSocioUnico"/>
		<xsl:choose>
			<xsl:when test="$pSocioUnico='SU'">Socio unico</xsl:when>
			<xsl:when test="$pSocioUnico='SM'">Più soci</xsl:when>
			<xsl:otherwise>Codice non riconosciuto</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="ModalitaPagamento">
		<xsl:param name="pModalitaPagamento"/>
		<xsl:choose>
			<xsl:when test="$pModalitaPagamento='MP01'">Contanti</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP02'">Assegno</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP03'">Assegno circolare</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP04'">Contanti presso Tesoreria</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP05'">Bonifico</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP06'">Vaglia cambiario</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP07'">Bollettino bancario</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP08'">Carta di pagamento</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP09'">RID</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP10'">RID utenze</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP11'">RID veloce</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP12'">RIBA</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP13'">MAV</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP14'">Quietanza erario</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP15'">Giroconto su conti di contabilità speciale</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP16'">Domiciliazione bancaria</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP17'">Domiciliazione postale</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP18'">Bollettino di C/C postale</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP19'">SEPA Direct Debit</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP20'">SEPA Direct Debit CORE</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP21'">SEPA Direct Debit B2B</xsl:when>
			<xsl:when test="$pModalitaPagamento='MP22'">Trattenuta su somme già riscosse</xsl:when>
			<xsl:otherwise>Codice non riconosciuto</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/" mode="def">
		<html>
			<head>
				<meta http-equiv="X-UA-Compatible" content="IE=edge" />
				<style type="text/css">
					#fattura-container { width: 100%; position: relative; }

					#fattura-elettronica { font-family: sans-serif; font-size: 11px; margin-left: auto; margin-right: auto; max-width: 1280px; min-width: 930px; padding: 0; }
					#fattura-elettronica .versione { font-size: 11px; float:right; color: #777777; }
					#fattura-elettronica h1 { padding: 20px 0 0 0; margin: 0; font-size: 25px; color: #012f51; }
					#fattura-elettronica h2 { padding: 0 0 0 0; margin: 0; font-size: 12px; color: #012f51; font-weight: bold; }
					#fattura-elettronica h3 { padding: 20px 0 0 0; margin: 0; font-size: 11px; font-weight: bold; }
					#fattura-elettronica h4 { padding: 5 5 5 5; font-size: 15px; color: #012f51; font-style:italic; text-align: center; background-color: #e5eaed;}
					#fattura-elettronica h5 { padding: 15px 0 0 0; margin: 0; font-size: 17px; font-style: italic; }
					#fattura-elettronica h6 { padding: 0px; margin: 0; font-size: 11px; font-weight: bold; color: #012f51; }

					#fattura-elettronica .label { padding: 20px 0 0 0; margin: 0; font-size: 11px; font-weight: bold; }

					#fattura-elettronica hr { font-size: 1px; color: #012f51; }

					#cedente {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						font-size: 11px; 
						text-align: left;
						border-radius: 5px;
						width:300px;
					}

					#cessionario {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						font-size: 11px; 
						text-align: left;
						border-radius: 5px;
						width:300px;
					}

					#terzointermediario {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						font-size: 11px; 
						text-align: left;
						border-radius: 5px;
						width:300px;
					}

					.tabellaGenerica{
						font-size: 11px;
						width: 100%;
						border-collapse: collapse;
						page-break-inside: auto;
						empty-cells: show;
					}
					.tabellaGenerica td{
						border: 1px solid #012f51;
						padding: 2px 5px 2px 5px;
					}
					.rigaTitoli{
						border: 1px solid #012f51;
						padding: 0;
					}


					#fattura-elettronica div.page {
						background-color: #fff !important;
						position: relative;

						margin: 20px 0 50px 0;
						padding: 10px;

						border: 1px solid #012f51;
					}
				</style>
			</head>
			<body>
				<div id="fattura-container">
					<xsl:if test="a:FatturaElettronica">

						<!--INIZIO - Variabili Header-Globali -->
						<xsl:variable name="tipoDestinatario">
							<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/FormatoTrasmissione"/>
						</xsl:variable>
						<!--FINE - Variabili Header-Globali -->

						<div id="fattura-elettronica">

							<h1>Fattura Elettronica vs 
								<xsl:choose>
									<xsl:when test="$tipoDestinatario='FPA12'">Pubblica Amministrazione</xsl:when>
									<xsl:when test="$tipoDestinatario='FPR12'">Privati</xsl:when>
									<xsl:otherwise>
										<span/>
									</xsl:otherwise>
								</xsl:choose>
							</h1>
							<div class="versione">
								Versione <xsl:value-of select="a:FatturaElettronica/@versione"/>
							</div>

							<div class="page">
								<!--INIZIO DATI HEADER-->
								<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader">

									<table width="100%">
										<tr>
											<td align="left" valign="top">

												<!--INIZIO DATI CEDENTE PRESTATORE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore">
													<div id="cedente">

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">

																<h2>
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</h2>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Sede">
															<span class="label">Sede legale: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Sede">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat('(', Provincia, ') ')" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat('- ', Nazione, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/StabileOrganizzazione">
															<span class="label">Stabile Organizzazione: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/StabileOrganizzazione">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat('(', Provincia, ') ')" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat('- ', Nazione, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<span class="label">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
																<xsl:if test="CodiceFiscale">
																	<br/>
																	<span class="label">Codice fiscale: </span>
																	<xsl:value-of select="CodiceFiscale" />
																</xsl:if>
																<xsl:if test="Anagrafica/CodEORI">
																	<br/>
																	<span class="label">Codice EORI: </span>
																	<xsl:value-of select="Anagrafica/CodEORI" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<hr/>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/AlboProfessionale">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
																<span class="label">Albo Professionale: </span>
																<xsl:value-of select="AlboProfessionale"/>
																<xsl:if test="ProvinciaAlbo">
																	<xsl:value-of select="concat(' (Provincia ', ProvinciaAlbo, ')')"/>
																</xsl:if>
																<xsl:if test="NumeroIscrizioneAlbo">
																	<xsl:value-of select="concat(' n. ', NumeroIscrizioneAlbo)"/>
																</xsl:if>
																<xsl:if test="DataIscrizioneAlbo">
																	<xsl:value-of select="' del '"/>
																	<xsl:call-template name="FormatDateShort">
																		<xsl:with-param name="DateTime" select="DataIscrizioneAlbo" />
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
															<hr/>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
																<xsl:if test="RegimeFiscale">
																	<span class="label">Regime fiscale: </span>
																	<xsl:call-template name="RegimeFiscale">
																		<xsl:with-param name="pRegimeFiscale" select="RegimeFiscale"/>
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
																<xsl:if test="CapitaleSociale">
																	<br/>
																	<span class="label">Capitale sociale: </span>
																	<xsl:value-of select="CapitaleSociale" />
																</xsl:if>
																<xsl:if test="((Ufficio) or (NumeroREA))">
																	<br/>
																	<span class="label">REA: </span>
																	<xsl:value-of select="concat(Ufficio, ' ')" />
																	<xsl:value-of select="NumeroREA" />
																</xsl:if>
																<xsl:if test="SocioUnico">
																	<br/>
																	<span class="label">Numero soci: </span>
																	<xsl:call-template name="SocioUnico">
																		<xsl:with-param name="pSocioUnico" select="SocioUnico"/>
																	</xsl:call-template>
																</xsl:if>
																<xsl:if test="StatoLiquidazione">
																	<br/>
																	<span class="label">Stato Liquidazione: </span>
																	<xsl:call-template name="StatoLiquidazione">
																		<xsl:with-param name="pStatoLiquidazione" select="StatoLiquidazione"/>
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Contatti">
															<xsl:if test="Telefono or Fax or Email">
																<hr/>
																<xsl:if test="Telefono">
																	<span class="label">Telefono: </span>
																	<xsl:value-of select="Telefono" />
																	<br/>
																</xsl:if>
																<xsl:if test="Fax">
																	<span class="label">Fax: </span>
																	<xsl:value-of select="Fax" />
																	<br/>
																</xsl:if>
																<xsl:if test="Email">
																	<span class="label">E-mail: </span>
																	<xsl:value-of select="Email" />
																	<br/>
																</xsl:if>
															</xsl:if>
														</xsl:for-each>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/RiferimentoAmministrazione">
															<hr/>
															<span class="label">Riferimento amministrativo: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/RiferimentoAmministrazione" />
														</xsl:if>
													</div>
												</xsl:if>
												<!--FINE DATI CEDENTE PRESTATORE-->

												<!--INIZIO DATI RAPPRESENTANTE FISCALE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/RappresentanteFiscale">
													<div id="rappresentante-fiscale">
														<h3>Dati del rappresentante fiscale del cedente / prestatore</h3>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/RappresentanteFiscale/DatiAnagrafici">
															<h4>Dati anagrafici</h4>

															<ul>
																<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/RappresentanteFiscale/DatiAnagrafici">
																	<xsl:if test="IdFiscaleIVA">
																		<li>
																			Identificativo fiscale ai fini IVA:
																			<span>
																				<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																				<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="CodiceFiscale">
																		<li>
																			Codice fiscale:
																			<span>
																				<xsl:value-of select="CodiceFiscale" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Denominazione">
																		<li>
																			Denominazione:
																			<span>
																				<xsl:value-of select="Anagrafica/Denominazione" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<li>
																			Nome:
																			<span>
																				<xsl:value-of select="Anagrafica/Nome" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<li>
																			Cognome:
																			<span>
																				<xsl:value-of select="Anagrafica/Cognome" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<li>
																			Titolo onorifico:
																			<span>
																				<xsl:value-of select="Anagrafica/Titolo" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/CodEORI">
																		<li>
																			Codice EORI:
																			<span>
																				<xsl:value-of select="Anagrafica/CodEORI" />
																			</span>
																		</li>
																	</xsl:if>
																</xsl:for-each>
															</ul>
														</xsl:if>

													</div>
												</xsl:if>
												<!--FINE DATI RAPPRESENTANTE FISCALE-->

											</td>
											<td align="center" valign="top">
												<xsl:if test="((a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente)or(a:FatturaElettronica/FatturaElettronicaHeader/SoggettoEmittente))">
													<div id="terzointermediario">
														<h2>Emittente</h2>

														<!--INIZIO DATI TERZO INTERMEDIARIO SOGGETTO EMITTENTE-->
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
																<br/>
																<h2>
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</h2>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<span class="label">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
																<xsl:if test="CodiceFiscale">
																	<br/>
																	<span class="label">Codice fiscale: </span>
																	<xsl:value-of select="CodiceFiscale" />
																</xsl:if>
																<xsl:if test="Anagrafica/CodEORI">
																	<br/>
																	<span class="label">Codice EORI: </span>
																	<xsl:value-of select="Anagrafica/CodEORI" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<!--FINE DATI TERZO INTERMEDIARIO SOGGETTO EMITTENTE-->

														<!--INIZIO DATI SOGGETTO EMITTENTE-->
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/SoggettoEmittente">
															<div id="soggetto-emittente">
																<ul>
																	<li>
																		Soggetto emittente:
																		<span>
																			<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/SoggettoEmittente" />
																		</span>
																		<xsl:variable name="SC">
																			<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/SoggettoEmittente" />
																		</xsl:variable>
																		<xsl:choose>
																			<xsl:when test="$SC='CC'">
																				(cessionario/committente)
																			</xsl:when>
																			<xsl:when test="$SC='TZ'">
																				(terzo)
																			</xsl:when>
																			<xsl:when test="$SC=''">
																			</xsl:when>
																			<xsl:otherwise>
																				<span>(!!! codice non previsto !!!)</span>
																			</xsl:otherwise>
																		</xsl:choose>
																	</li>
																</ul>
															</div>
														</xsl:if>
														<!--FINE DATI SOGGETTO EMITTENTE-->
													</div>
												</xsl:if>
											</td>

											<td align="right" valign="top">

												<!--INIZIO DATI CESSIONARIO COMMITTENTE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente">
													<div id="cessionario">

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">

																<h2>
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</h2>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/Sede">
															<span class="label">Sede legale: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/Sede">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat('(', Provincia, ') ')" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat('- ', Nazione, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/StabileOrganizzazione">
															<span class="label">Stabile Organizzazione: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/StabileOrganizzazione">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat('(', Provincia, ') ')" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat('- ', Nazione, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<span class="label">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
																<xsl:if test="CodiceFiscale">
																	<br/>
																	<span class="label">Codice fiscale: </span>
																	<xsl:value-of select="CodiceFiscale" />
																</xsl:if>
																<xsl:if test="Anagrafica/CodEORI">
																	<br/>
																	<span class="label">Codice EORI: </span>
																	<xsl:value-of select="Anagrafica/CodEORI" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<hr/>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario">
															<span class="label">Codice Destinatario: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario" />
															<br/>
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario">
															<span class="label">Pec Destinatario: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario" />
															<br/>
														</xsl:if>


														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/RappresentanteFiscale">
															<div id="rappresentante-fiscale">
																<hr/>
																<h4>Dati del rappresentante fiscale del cessionario / committente</h4>

																<ul>
																	<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/RappresentanteFiscale">
																		<xsl:if test="IdFiscaleIVA">
																			<li>
																		Identificativo fiscale ai fini IVA:
																				<span>
																					<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																					<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Denominazione">
																			<li>
																		Denominazione:
																				<span>
																					<xsl:value-of select="Denominazione" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Nome">
																			<li>
																		Nome:
																				<span>
																					<xsl:value-of select="Nome" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Cognome">
																			<li>
																		Cognome:
																				<span>
																					<xsl:value-of select="Cognome" />
																				</span>
																			</li>
																		</xsl:if>
																	</xsl:for-each>
																</ul>
															</div>
														</xsl:if>

													</div>
												</xsl:if>
												<!--FINE DATI CESSIONARIO COMMITTENTE-->

											</td>
										</tr>
									</table>


								</xsl:if>
								<!--FINE DATI HEADER-->

								<!--INIZIO DATI BODY-->
								<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaBody">

									<xsl:variable name="valutaDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
									</xsl:variable>
									<xsl:variable name="tipoDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/TipoDocumento"/>
									</xsl:variable>

									<!--INIZIO DATI GENERALI DOCUMENTO-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento">

										<xsl:variable name="valuta">
											<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
										</xsl:variable>

										<div id="dati-generali-documento">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="2">
														<h4>Dati generali del Documento</h4>
													</td>
												</tr>
												<tr>
													<td width="300px" align="left">
														<span class="label">
															<xsl:call-template name="TipoDocumento">
																<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
															</xsl:call-template>
														</span>
														<span class="label">
													numero 
														</span>
														<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Numero" />
													</td>
													<td width="200px">
														<span class="label">Data documento: </span>
														<xsl:call-template name="FormatDateShort">
															<xsl:with-param name="DateTime" select="DatiGenerali/DatiGeneraliDocumento/Data" />
														</xsl:call-template>
													</td>
												</tr>

												<xsl:if test="((DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento) or (DatiGenerali/DatiGeneraliDocumento/Arrotondamento))">
													<tr>
														<td width="300px" align="left">
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento">
																<span class="label">
																	Importo documento: 
																</span>
																<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento" />
																<xsl:value-of select="concat(' ',$valuta)"/>
															</xsl:if>
														</td>
														<td width="200px">
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Arrotondamento">
																<span class="label">
																	Arrotondamento su Importo documento: 
																</span>
																<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Arrotondamento" />
																<xsl:value-of select="concat(' ',$valuta)"/>
															</xsl:if>
														</td>
													</tr>
												</xsl:if>

												<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiBollo">
													<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiBollo">
														<tr>
															<td width="300px" align="left">
																<span class="label">
																	Bollo virtuale: 
																	</span>
																<xsl:if test="BolloVirtuale">
																	<xsl:value-of select="BolloVirtuale" />
																</xsl:if>
																<xsl:if test="((BolloVirtuale)or(ImportoBollo))">
																	<xsl:value-of select="' - '"/>
																</xsl:if>
																<xsl:if test="BolloVirtuale">
																	<xsl:value-of select="ImportoBollo"/>
																	<xsl:value-of select="concat(' ',$valuta)"/>
																</xsl:if>
															</td>
															<td width="200px">
															</td>
														</tr>
													</xsl:for-each>
												</xsl:if>

											</table>
										</div>
										
										<!--Dati cassa-->
										<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiCassaPrevidenziale">
											<div id="dati-cassa-previdenziale">
												<br/>
												<table class="tabellaGenerica">
													<tr>
														<td colspan="8">
															<h4>Dati Cassa Previdenziale</h4>
														</td>
													</tr>
													<tr>
														<td width="100px">
															<span class="label">Tipo Cassa</span>
														</td>
														<td width="100px">
															<span class="label">Aliquota contributo cassa (%)</span>
														</td>
														<td width="100px">
															<span class="label">Importo contributo cassa</span>
														</td>
														<td width="100px">
															<span class="label">Imponibile previdenziale</span>
														</td>
														<td width="100px">
															<span class="label">Aliquota IVA applicata</span>
														</td>
														<td width="100px">
															<span class="label">Contributo cassa soggetto a ritenuta</span>
														</td>
														<td width="100px">
															<span class="label">Tipologia di non imponibilità del contributo</span>
														</td>
														<td width="100px">
															<span class="label">Riferimento amministrativo / contabile</span>
														</td>
													</tr>

													<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiCassaPrevidenziale">
														<tr>
															<td width="100px">
																<xsl:if test="TipoCassa">
																	<xsl:variable name="TC">
																		<xsl:value-of select="TipoCassa" />
																	</xsl:variable>
																	<xsl:choose>
																		<xsl:when test="$TC='TC01'">
																			Cassa Nazionale Previdenza e Assistenza Avvocati e Procuratori legali
																		</xsl:when>
																		<xsl:when test="$TC='TC02'">
																			Cassa Previdenza Dottori Commercialisti
																		</xsl:when>
																		<xsl:when test="$TC='TC03'">
																			Cassa Previdenza e Assistenza Geometri
																		</xsl:when>
																		<xsl:when test="$TC='TC04'">
																			Cassa Nazionale Previdenza e Assistenza Ingegneri e Architetti liberi profess.
																		</xsl:when>
																		<xsl:when test="$TC='TC05'">
																			Cassa Nazionale del Notariato
																		</xsl:when>
																		<xsl:when test="$TC='TC06'">
																			Cassa Nazionale Previdenza e Assistenza Ragionieri e Periti commerciali
																		</xsl:when>
																		<xsl:when test="$TC='TC07'">
																			Ente Nazionale Assistenza Agenti e Rappresentanti di Commercio-ENASARCO
																		</xsl:when>
																		<xsl:when test="$TC='TC08'">
																			Ente Nazionale Previdenza e Assistenza Consulenti del Lavoro-ENPACL
																		</xsl:when>
																		<xsl:when test="$TC='TC09'">
																			Ente Nazionale Previdenza e Assistenza Medici-ENPAM
																		</xsl:when>
																		<xsl:when test="$TC='TC10'">
																			Ente Nazionale Previdenza e Assistenza Farmacisti-ENPAF
																		</xsl:when>
																		<xsl:when test="$TC='TC11'">
																			Ente Nazionale Previdenza e Assistenza Veterinari-ENPAV
																		</xsl:when>
																		<xsl:when test="$TC='TC12'">
																			Ente Nazionale Previdenza e Assistenza Impiegati dell'Agricoltura-ENPAIA
																		</xsl:when>
																		<xsl:when test="$TC='TC13'">
																			Fondo Previdenza Impiegati Imprese di Spedizione e Agenzie Marittime
																		</xsl:when>
																		<xsl:when test="$TC='TC14'">
																			Istituto Nazionale Previdenza Giornalisti Italiani-INPGI
																		</xsl:when>
																		<xsl:when test="$TC='TC15'">
																			Opera Nazionale Assistenza Orfani Sanitari Italiani-ONAOSI
																		</xsl:when>
																		<xsl:when test="$TC='TC16'">
																			Cassa Autonoma Assistenza Integrativa Giornalisti Italiani-CASAGIT
																		</xsl:when>
																		<xsl:when test="$TC='TC17'">
																			Ente Previdenza Periti Industriali e Periti Industriali Laureati-EPPI
																		</xsl:when>
																		<xsl:when test="$TC='TC18'">
																			Ente Previdenza e Assistenza Pluricategoriale-EPAP
																		</xsl:when>
																		<xsl:when test="$TC='TC19'">
																			Ente Nazionale Previdenza e Assistenza Biologi-ENPAB
																		</xsl:when>
																		<xsl:when test="$TC='TC20'">
																			Ente Nazionale Previdenza e Assistenza Professione Infermieristica-ENPAPI
																		</xsl:when>
																		<xsl:when test="$TC='TC21'">
																			Ente Nazionale Previdenza e Assistenza Psicologi-ENPAP
																		</xsl:when>
																		<xsl:when test="$TC='TC22'">
																			INPS
																		</xsl:when>
																		<xsl:when test="$TC=''">
																		</xsl:when>
																		<xsl:otherwise>
																			<span>!!! Codice non previsto !!!</span>
																		</xsl:otherwise>
																	</xsl:choose>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="AlCassa">
																	<xsl:value-of select="AlCassa" /> %
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="ImportoContributoCassa">
																	<xsl:value-of select="ImportoContributoCassa" />
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="ImponibileCassa">
																	<xsl:value-of select="ImponibileCassa" />
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="AliquotaIVA">
																	<xsl:value-of select="AliquotaIVA" /> %
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="Ritenuta">
																	<xsl:value-of select="Ritenuta" />
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="Natura">
																	<xsl:variable name="NT">
																		<xsl:value-of select="Natura" />
																	</xsl:variable>
																	<span class="label">
																		<xsl:value-of select="Natura" />
																	</span>
																	 - 
																	<xsl:choose>
																		<xsl:when test="$NT='N1'">
																			escluse ex art. 15
																		</xsl:when>
																		<xsl:when test="$NT='N2'">
																			non soggette
																		</xsl:when>
																		<xsl:when test="$NT='N3'">
																			non imponibili
																		</xsl:when>
																		<xsl:when test="$NT='N4'">
																			esenti
																		</xsl:when>
																		<xsl:when test="$NT='N5'">
																			regime del margine / IVA non esposta in fattura
																		</xsl:when>
																		<xsl:when test="$NT='N6'">
																			inversione contabile
																		</xsl:when>
																		<xsl:when test="$NT='N7'">
																			IVA assolta in altro stato UE
																		</xsl:when>
																		<xsl:when test="$NT=''">
																		</xsl:when>
																		<xsl:otherwise>
																			<span>!!! codice non previsto !!!</span>
																		</xsl:otherwise>
																	</xsl:choose>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="RiferimentoAmministrazione">
																	<xsl:value-of select="RiferimentoAmministrazione" />
																</xsl:if>
															</td>
														</tr>
													</xsl:for-each>
												</table>

											</div>
										</xsl:if>

										<!--Dati ritenuta-->
										<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiRitenuta">
											<div id="dati-ritenuta">
												<br/>
												<table class="tabellaGenerica">
													<tr>
														<td colspan="4">
															<h4>Dati Ritenuta</h4>
														</td>
													</tr>
													<tr>
														<td width="100px">
															<span class="label">Tipo Ritenuta</span>
														</td>
														<td width="100px">
															<span class="label">Importo</span>
														</td>
														<td width="100px">
															<span class="label">Aliquota</span>
														</td>
														<td width="200px">
															<span class="label">Causale</span>
														</td>
													</tr>

													<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiRitenuta">
														<tr>
															<td width="100px">
																<xsl:if test="TipoRitenuta">
																	<xsl:variable name="TR">
																		<xsl:value-of select="TipoRitenuta" />
																	</xsl:variable>
																	<xsl:choose>
																		<xsl:when test="$TR='RT01'">
																			Ritenuta persone fisiche
																		</xsl:when>
																		<xsl:when test="$TR='RT02'">
																			Ritenuta persone giuridiche
																		</xsl:when>
																		<xsl:when test="$TR=''">
																		</xsl:when>
																		<xsl:otherwise>
																			<span>!!! Codice non previsto !!!</span>
																		</xsl:otherwise>
																	</xsl:choose>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="ImportoRitenuta">
																	<xsl:value-of select="ImportoRitenuta" />
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="AliquotaRitenuta">
																	<xsl:value-of select="AliquotaRitenuta" /> %
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="CausalePagamento">
																	<xsl:value-of select="CausalePagamento" />
																	<xsl:variable name="CP">
																		<xsl:value-of select="CausalePagamento" />
																	</xsl:variable>
																	<xsl:if test="$CP!=''">
																		(decodifica come da modello 770S)
																	</xsl:if>
																</xsl:if>
															</td>
														</tr>
													</xsl:for-each>
												</table>

											</div>
										</xsl:if>

										</xsl:if>
									<!--FINE DATI GENERALI DOCUMENTO-->


									<!--INIZIO DATI PAGAMENTO-->
									<xsl:if test="DatiPagamento">
										<div id="dati-pagamento-condizioni">
											<xsl:for-each select="DatiPagamento">
												<br/>
												<table class="tabellaGenerica">
													<tr>
														<td class="rigaTitoli" colspan="6">
															<h4>Dati relativi al pagamento</h4>
														</td>
													</tr>
													<tr>
														<td colspan="6">
															<xsl:if test="CondizioniPagamento">
																<span class="label">Condizioni di pagamento: </span>
																<xsl:variable name="CP">
																	<xsl:value-of select="CondizioniPagamento" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$CP='TP01'">
																		Pagamento a rate
																	</xsl:when>
																	<xsl:when test="$CP='TP02'">
																		Pagamento completo
																	</xsl:when>
																	<xsl:when test="$CP='TP03'">
																		Anticipo
																	</xsl:when>
																	<xsl:when test="$CP=''">
																	</xsl:when>
																	<xsl:otherwise>
																		Modalità di pagamento: codice non previsto !!!
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
															<br/>
															<br/>
														</td>
													</tr>
													<xsl:for-each select="DettaglioPagamento">
														<tr>
															<td width="100px">
																<h6>Importo da pagare</h6>
															</td>
															<td width="100px">
																<h6>Modalità di pagamento</h6>
															</td>
															<td width="200px">
																<h6>Beneficiario</h6>
															</td>
															<td width="60px">
																<h6>Scadenza pagamento</h6>
															</td>
															<td width="60px">
																<h6>Data Rif. Pagamento</h6>
															</td>
															<td width="60px">
																<h6>Giorni termine pagamento</h6>
															</td>
														</tr>
														<tr>
															<td width="200px">
																<xsl:value-of select="concat(ImportoPagamento, ' ', $valutaDocumento)"/>
															</td>
															<td width="150px">
																<xsl:if test="ModalitaPagamento">
																	<xsl:call-template name="ModalitaPagamento">
																		<xsl:with-param name="pModalitaPagamento" select="ModalitaPagamento"/>
																	</xsl:call-template>
																</xsl:if>
															</td>
															<td width="250px">
																<xsl:value-of select="Beneficiario"/>
															</td>
															<td width="100px">
																<xsl:if test="DataScadenzaPagamento">
																	<xsl:call-template name="FormatDateShort">
																		<xsl:with-param name="DateTime" select="DataScadenzaPagamento" />
																	</xsl:call-template>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="DataRiferimentoTerminiPagamento">
																	<xsl:call-template name="FormatDateShort">
																		<xsl:with-param name="DateTime" select="DataRiferimentoTerminiPagamento" />
																	</xsl:call-template>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="GiorniTerminiPagamento">
																	<xsl:value-of select="GiorniTerminiPagamento" />
																</xsl:if>
															</td>
														</tr>
														<tr>
															<td colspan="6" style="padding-left:40px; font-style:italic;">
																<xsl:if test="IstitutoFinanziario">
																	<span class="label">Pagabile presso: </span>
																	<xsl:value-of select="IstitutoFinanziario" />
																</xsl:if>
																<xsl:if test="IBAN">
																	<span class="label"> IBAN: </span>
																	<xsl:value-of select="IBAN" />
																</xsl:if>
																<xsl:if test="((ABI)or(CAB)or(BIC))"> (</xsl:if>
																<xsl:if test="ABI">
																	<span class="label"> ABI: </span>
																	<xsl:value-of select="ABI" />
																</xsl:if>
																<xsl:if test="CAB">
																	<span class="label"> CAB: </span>
																	<xsl:value-of select="CAB" />
																</xsl:if>
																<xsl:if test="BIC">
																	<span class="label"> BIC: </span>
																	<xsl:value-of select="BIC" />
																</xsl:if>
																<xsl:if test="((ABI)or(CAB)or(BIC))"> )</xsl:if>
															</td>
														</tr>
														<xsl:if test="CodUfficioPostale">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<span class="label">Codice Ufficio Postale: </span>
																	<xsl:value-of select="CodUfficioPostale" />
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="((TitoloQuietanzante)or(CognomeQuietanzante)or(NomeQuietanzante)or(CFQuietanzante))">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<span class="label"> Quietanzante: </span>
																	<xsl:value-of select="concat(TitoloQuietanzante, ' ', CognomeQuietanzante, ' ', NomeQuietanzante)" />
																	<span class="label"> CF: </span>
																	<xsl:value-of select="CFQuietanzante" />
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="((ScontoPagamentoAnticipato)or(DataLimitePagamentoAnticipato))">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<xsl:if test="ScontoPagamentoAnticipato">
																		<span class="label">Sconto per pagamento anticipato: </span>
																		<xsl:value-of select="format-number(ScontoPagamentoAnticipato,  '###.###.##0,00######', 'euro')" />
																	</xsl:if>
																	<xsl:if test="DataLimitePagamentoAnticipato">
																		<span class="label">Data limite per il pagamento anticipato: </span>
																		<xsl:call-template name="FormatDateShort">
																			<xsl:with-param name="DateTime" select="DataLimitePagamentoAnticipato" />
																		</xsl:call-template>
																	</xsl:if>
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="((PenalitaPagamentiRitardati)or(DataDecorrenzaPenale))">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<xsl:if test="PenalitaPagamentiRitardati">
																		<span class="label">Penale per ritardato pagamento: </span>
																		<xsl:value-of select="PenalitaPagamentiRitardati" />																			
																	</xsl:if>
																	<xsl:if test="DataDecorrenzaPenale">
																		<span class="label">Data di decorrenza della penale: </span>
																		<xsl:call-template name="FormatDateShort">
																			<xsl:with-param name="DateTime" select="DataDecorrenzaPenale" />
																		</xsl:call-template>
																	</xsl:if>
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="(CodicePagamento)">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<xsl:if test="CodicePagamento">
																		<span class="label">Codice pagamento: </span>
																		<xsl:value-of select="CodicePagamento" />
																	</xsl:if>
																</td>
															</tr>
														</xsl:if>
													</xsl:for-each>
												</table>
											</xsl:for-each>
										</div>
									</xsl:if>
									<!--FINE DATI PAGAMENTO-->

									<!--INIZIO CAUSALI-->
									<xsl:if test="((DatiGenerali/DatiGeneraliDocumento/Causale) or (DatiGenerali/DatiGeneraliDocumento/Art73))">
										<br/>
										<table class="tabellaGenerica">
											<tr>
												<td class="rigaTitoli" colspan="2">
													<h4>Causali</h4>
												</td>
											</tr>
											<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/Causale">
												<tr>
													<td align="left">
														<xsl:value-of select="current()" />
													</td>
												</tr>
											</xsl:for-each>
											<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Art73">
												<tr>
													<td align="left">
														<xsl:value-of select="concat('Articolo 73: ', DatiGenerali/DatiGeneraliDocumento/Art73)" />
													</td>
												</tr>
											</xsl:if>
											<xsl:for-each select="DatiGenerali/DatiSAL">
												<tr>
													<td align="left">
														<span class="label">
															Dati SAL: riferimento fase 
														</span>
														<xsl:value-of select="RiferimentoFase" />
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</xsl:if>
									<!--INIZIO CAUSALI-->

									<!--INIZIO DATI DI DETTAGLIO DELLE LINEE-->
									<xsl:if test="DatiBeniServizi/DettaglioLinee">
										<br/>
										<div id="righe">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="11">
														<h4>Dati Beni Servizi - Dettaglio Linee</h4>
													</td>
												</tr>
												<tr>
													<td width="30px">
														<h6>Linea</h6>
													</td>
													<td width="80px">
														<h6>Articolo</h6>
													</td>
													<td width="200px">
														<h6>Descrizione</h6>
													</td>
													<td width="80px">
														<h6>Periodo</h6>
													</td>
													<td width="50px">
														<h6>Quantità</h6>
													</td>
													<td width="50px">
														<h6>Unità di Misura</h6>
													</td>
													<td width="90px">
														<h6>Prezzo unitario</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Sconto o Maggiorazione</h6>
													</td>
													<td width="90px">
														<h6>Prezzo totale</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="50px">
														<h6>Aliquota</h6>
													</td>
													<td width="80px">
														<h6>Natura</h6>
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi/DettaglioLinee">
													<tr>
														<td align="right">
															<xsl:value-of select="NumeroLinea" />
														</td>
														<td>
															<xsl:if test="((CodiceArticolo/CodiceTipo)or(CodiceArticolo/CodiceValore))">
																<xsl:value-of select="CodiceArticolo/CodiceTipo" />
																<br/>
																<xsl:value-of select="CodiceArticolo/CodiceValore" />
															</xsl:if>
														</td>
														<td>
															<xsl:if test="TipoCessionePrestazione">
																<span class="label">
																	<xsl:value-of select="'Tipo Cessione Prestazione: '" />
																</span>
																<xsl:variable name="TCP">
																	<xsl:value-of select="TipoCessionePrestazione" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$TCP='SC'">
																		(sconto)
																	</xsl:when>
																	<xsl:when test="$TCP='PR'">
																		(premio)
																	</xsl:when>
																	<xsl:when test="$TCP='AB'">
																		(abbuono)
																	</xsl:when>
																	<xsl:when test="$TCP='AC'">
																		(spesa accessoria)
																	</xsl:when>
																	<xsl:otherwise>
																		<span>(!!! codice non previsto !!!)</span>
																	</xsl:otherwise>
																</xsl:choose>
																<br/>
															</xsl:if>
															<xsl:value-of select="Descrizione" />
														</td>
														<td>
															<xsl:if test="DataInizioPeriodo">
																da: 
																<xsl:call-template name="FormatDateShort">
																	<xsl:with-param name="DateTime" select="DataInizioPeriodo" />
																</xsl:call-template>
															</xsl:if>
															<xsl:if test="((DataInizioPeriodo) or (DataFinePeriodo))">
																<br/>
															</xsl:if>
															<xsl:if test="DataFinePeriodo">
																a: 
																<xsl:call-template name="FormatDateShort">
																	<xsl:with-param name="DateTime" select="DataFinePeriodo" />
																</xsl:call-template>
															</xsl:if>
														</td>
														<td align="right">
															<xsl:value-of select="Quantita" />
														</td>
														<td>
															<xsl:value-of select="UnitaMisura" />
														</td>
														<td align="right">
															<xsl:value-of select="PrezzoUnitario" />
														</td>
														<td>
															<xsl:for-each select="ScontoMaggiorazione">
																<xsl:variable name="TSCM">
																	<xsl:value-of select="Tipo" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$TSCM='SC'">
																		Sconto: 
																	</xsl:when>
																	<xsl:when test="$TSCM='MG'">
																		Magg.: 
																	</xsl:when>
																	<xsl:otherwise>
																		!!! Codice non previsto: 
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:if test="Percentuale">
																	<xsl:value-of select="Percentuale" /> %
																</xsl:if>
																<xsl:if test="Importo">
																	<xsl:value-of select="Importo" />
																	<xsl:value-of select="concat(' ',$valutaDocumento)" />
																</xsl:if>
															</xsl:for-each>
														</td>
														<td align="right">
															<xsl:value-of select="PrezzoTotale" />
														</td>
														<td align="right">
															<xsl:value-of select="AliquotaIVA" /> %</td>
														<td>
															<xsl:if test="Natura">
																<span class="label">
																	<xsl:value-of select="Natura" />
																</span>
																 - 
																<xsl:variable name="NAT">
																	<xsl:value-of select="Natura" />
																</xsl:variable>
																<br/>
																<xsl:choose>
																	<xsl:when test="$NAT='N1'">
																		esclusa ex art.15
																	</xsl:when>
																	<xsl:when test="$NAT='N2'">
																		non soggetta
																	</xsl:when>
																	<xsl:when test="$NAT='N3'">
																		non imponibile
																	</xsl:when>
																	<xsl:when test="$NAT='N4'">
																		esente
																	</xsl:when>
																	<xsl:when test="$NAT='N5'">
																		regime del margine / IVA non esposta in fattura
																	</xsl:when>
																	<xsl:when test="$NAT='N6'">
																		inversione contabile
																	</xsl:when>
																	<xsl:when test="$NAT='N7'">
																		IVA assolta in altro stato UE
																	</xsl:when>
																	<xsl:otherwise>
																		!!! codice non previsto !!!
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
													</tr>

													<xsl:if test="((Ritenuta)or(RiferimentoAmministrazione)or(AltriDatiGestionali))">
														<tr>
															<td style="border:0;"/>
															<td colspan="10">
																<table class="tabellaGenerica">
																	<xsl:if test="Ritenuta">
																		<tr>
																			<td width="200px" style="padding-left:30px; border:0; font-style:italic;">
																				<b>
																					<xsl:value-of select="'Soggetta a ritenuta:'"/>
																				</b>
																			</td>
																			<td style="padding-left:10px; border:0; font-style:italic;">
																				<xsl:value-of select="Ritenuta" />
																			</td>
																		</tr>
																	</xsl:if>
																	<xsl:if test="RiferimentoAmministrazione">
																		<tr>
																			<td width="200px" style="padding-left:30px; border:0; font-style:italic;">
																				<b>
																					<xsl:value-of select="'Riferimento amministrativo/contabile:'"/>
																				</b>
																			</td>
																			<td style="padding-left:10px; border:0; font-style:italic;">
																				<xsl:value-of select="RiferimentoAmministrazione" />
																			</td>
																		</tr>
																	</xsl:if>

																	<xsl:for-each select="AltriDatiGestionali">
																		<tr>
																			<td width="200px" style="padding-left:30px; border:0; font-style:italic;">
																				<b>
																					<xsl:value-of select="'Altri dati gestionali'"/>
																				</b>
																			</td>
																			<td style="padding-left:10px; border:0; font-style:italic;">
																				<b>
																					<xsl:value-of select="concat(TipoDato, ': ')"/>
																				</b>
																				<xsl:if test="RiferimentoTesto">
																					<xsl:value-of select="concat(RiferimentoTesto, ' ')"/>
																				</xsl:if>
																				<xsl:if test="RiferimentoNumero">
																					<xsl:value-of select="concat('numero ', RiferimentoNumero, ' ')"/>
																				</xsl:if>
																				<xsl:if test="RiferimentoData">
																					<xsl:value-of select="'del '"/>
																					<xsl:call-template name="FormatDateShort">
																						<xsl:with-param name="DateTime" select="RiferimentoData" />
																					</xsl:call-template>
																				</xsl:if>
																			</td>
																		</tr>
																	</xsl:for-each>

																</table>
															</td>
														</tr>
													</xsl:if>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI DI DETTAGLIO DELLE LINEE-->

									<!--INIZIO ALTRI DATI-->
									<xsl:if test="(
									(DatiGenerali/DatiOrdineAcquisto)or
									(DatiGenerali/DatiContratto)or
									(DatiGenerali/DatiConvenzione)or
									(DatiGenerali/DatiRicezione)or
									(DatiGenerali/DatiFattureCollegate))">
										<div id="altriDati">
											<br/>
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="8">
														<h4>Altri Dati Generali</h4>
													</td>
												</tr>
												<tr>
													<td width="100px">
														<h6>Tipo</h6>
													</td>
													<td width="50px">
														<h6>Linea</h6>
													</td>
													<td width="100px">
														<h6>id Documento</h6>
													</td>
													<td width="100px">
														<h6>Data</h6>
													</td>
													<td width="100px">
														<h6>CUP</h6>
													</td>
													<td width="100px">
														<h6>CIG</h6>
													</td>
													<td width="100px">
														<h6>Num. Item</h6>
													</td>
													<td width="100px">
														<h6>Codice Commessa Convenzione</h6>
													</td>
												</tr>
												<xsl:for-each select="DatiGenerali/DatiOrdineAcquisto">
													<tr>
														<td align="left">Ordine d'Acquisto</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiContratto">
													<tr>
														<td align="left">Contratto</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiConvenzione">
													<tr>
														<td align="left">Convenzione</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiRicezione">
													<tr>
														<td align="left">Ricezione</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiFattureCollegate">
													<tr>
														<td align="left">Fatture collegate</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE ALTRI DATI-->

									<!--INIZIO DATI DDT-->
									<xsl:if test="DatiGenerali/DatiDDT">
										<div id="datiDDT">
											<br/>
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="8">
														<h4>Dati DDT</h4>
													</td>
												</tr>
												<tr>
													<td width="50px">
														<h6>Linea</h6>
													</td>
													<td width="100px">
														<h6>Numero</h6>
													</td>
													<td width="100px">
														<h6>Data</h6>
													</td>
												</tr>
												<xsl:for-each select="DatiGenerali/DatiDDT">
													<tr>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumeroDDT"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="DataDDT" />
															</xsl:call-template>
														</td>
													</tr>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI DDT-->


									<!--INIZIO DATI DI RIEPILOGO ALIQUOTE E NATURE-->
									<xsl:if test="DatiBeniServizi/DatiRiepilogo">

										<br/>
										<div id="riepilogo-aliquote-nature">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="8">
														<h4>Dati Beni Servizi - Dati Riepilogo</h4>
													</td>
												</tr>
												<tr>
													<td width="60px">
														<h6>Aliquota</h6>
													</td>
													<td width="60px">
														<h6>Natura</h6>
													</td>
													<td width="100px">
														<h6>Imponibile</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Imposta</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Arrotondamento</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Spese Accessorie</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Esigibilità Iva</h6>
													</td>
													<td width="200px">
														<h6>Riferimento Normativo</h6>
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi/DatiRiepilogo">
													<tr>
														<td align="right">
															<xsl:value-of select="AliquotaIVA" /> %
														</td>
														<td>
															<xsl:if test="Natura">
																<xsl:variable name="NAT">
																	<xsl:value-of select="Natura" />
																</xsl:variable>
																<br/>
																<xsl:choose>
																	<xsl:when test="$NAT='N1'">
																		esclusa ex art.15
																	</xsl:when>
																	<xsl:when test="$NAT='N2'">
																		non soggetta
																	</xsl:when>
																	<xsl:when test="$NAT='N3'">
																		non imponibile
																	</xsl:when>
																	<xsl:when test="$NAT='N4'">
																		esente
																	</xsl:when>
																	<xsl:when test="$NAT='N5'">
																		regime del margine / IVA non esposta in fattura
																	</xsl:when>
																	<xsl:when test="$NAT='N6'">
																		inversione contabile
																	</xsl:when>
																	<xsl:when test="$NAT='N7'">
																		IVA assolta in altro stato UE
																	</xsl:when>
																	<xsl:otherwise>
																		!!! codice non previsto !!!
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
														<td align="right">
															<xsl:value-of select="ImponibileImporto" />
														</td>
														<td align="right">
															<xsl:value-of select="Imposta" />
														</td>
														<td align="right">
															<xsl:value-of select="Arrotondamento" />
														</td>
														<td align="right">
															<xsl:value-of select="SpeseAccessorie" />
														</td>
														<td>
															<xsl:if test="EsigibilitaIVA">
																<span class="label">
																	<xsl:value-of select="EsigibilitaIVA" />
																</span>
																<xsl:variable name="EI">
																	<xsl:value-of select="EsigibilitaIVA" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$EI='I'">
																	(esigibilità immediata)
																	</xsl:when>
																	<xsl:when test="$EI='D'">
																	(esigibilità differita)
																	</xsl:when>
																	<xsl:when test="$EI='S'">
																	(scissione dei pagamenti)
																	</xsl:when>
																	<xsl:otherwise>
																		<span>(!!! codice non previsto !!!)</span>
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
														<td>
															<xsl:value-of select="RiferimentoNormativo" />
														</td>
													</tr>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI RIEPILOGO ALIQUOTE E NATURE-->

								</xsl:for-each>
								<!--FINE DATI BODY-->
							</div>
						</div>
					</xsl:if>
				</div>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="/" mode="semplificata">
		<html>
			<head>
				<meta http-equiv="X-UA-Compatible" content="IE=edge" />
				<style type="text/css">

					body, div, table { font-size: 13px; }

					#fattura-container { width: 100%; position: relative;}

					#fattura-elettronica { font-size: 11px; font-family: sans-serif; margin-left: auto; margin-right: auto; max-width: 1280px; min-width: 930px; padding: 0; }
					#fattura-elettronica .versione { font-size: 11px; float:right; color: #777777; } <!-- Versione - in alto a dx -->

					.titoloPrincipale  { padding: 20px  0px  0px  0px; margin: 0; font-size: 25px; font-weight: bold; color: #012f51; } <!-- Titolo principale -->
					.denominazione     { padding:  0px  0px  0px  0px; margin: 0;                  font-weight: bold; color: #012f51; } <!-- Denominazione cessionario e cedente -->
					.titoloTabelle     { padding:  2px  2px  2px  2px;            font-size: 14px; font-weight: bold; color: #000000; } <!-- Titolo principale tabelle -->
					.rigaTitoliTabelle { padding:  0px  0px  0px  0px; margin: 0;                  font-weight: bold; color: #000000; } <!-- Riga titoli tabelle -->

					.label { padding: 15px 0 0 0; margin: 0; font-weight: bold; }
					td .labelAlign { padding: 0 0 0 0; margin: 0; font-weight: bold; }

					#cedente {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						text-align: left;
						border-radius: 5px;
						width:300px;
					}

					#cessionario {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						text-align: left;
						border-radius: 5px;
						width:300px;
					}

					#emittente {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						text-align: center;
						border-radius: 5px;
						width:200px;
					}


					.tabellaGenerica{
						width: 100%;
						border-collapse: collapse;
						page-break-inside: auto;
						empty-cells: show;
					}
					.tabellaGenerica td{
						border: 1px solid #012f51;
						padding: 2px 5px 2px 5px;
					}
					.rigaTitoli{
						border: 1px solid #012f51;
						padding: 0;
						background-color: #e5eaed;
					}

					#fattura-elettronica div.page {
						background-color: #fff !important;
						position: relative;

						margin: 20px 0 50px 0;
						padding: 10px;

						border: 1px solid #012f51;
					}

				</style>
			</head>
			<body>
			
				<div id="fattura-container">
					<xsl:if test="d:FatturaElettronicaSemplificata">

						<!--INIZIO - Variabili Header-Globali -->
						<xsl:variable name="tipoDestinatario">
							<xsl:value-of select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/DatiTrasmissione/FormatoTrasmissione"/>
						</xsl:variable>
						<!--FINE - Variabili Header-Globali -->

						<div id="fattura-elettronica">

							<span class="titoloPrincipale">Fattura Elettronica 
								<xsl:choose>
									<xsl:when test="$tipoDestinatario='FPA12'">vs Pubblica Amministrazione</xsl:when>
									<xsl:when test="$tipoDestinatario='FPR12'">vs Privati</xsl:when>
									<xsl:when test="$tipoDestinatario='FSM10'">Semplificata</xsl:when>
									<xsl:otherwise>
										<span/>
									</xsl:otherwise>
								</xsl:choose>
							</span>
							<br/>
							<div class="versione">
								Versione <xsl:value-of select="d:FatturaElettronicaSemplificata/@versione"/>
							</div>

							<div class="page">
								<!--INIZIO DATI HEADER-->
								<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader">

									<table width="100%">
										<tr>
											<td align="left" valign="top">

												<!--INIZIO DATI CEDENTE PRESTATORE-->
												<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore">
													<div id="cedente">

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore">
															<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore">

																<span class="denominazione">
																	<xsl:if test="Denominazione">
																		<xsl:value-of select="Denominazione" />
																	</xsl:if>
																	<br/>
																	<xsl:if test="((Denominazione) and ((Titolo) or (Nome) or (Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Titolo">
																		<xsl:value-of select="concat(Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Nome">
																		<xsl:value-of select="concat(Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Cognome">
																		<xsl:value-of select="Cognome" />
																	</xsl:if>
																</span>
															</xsl:for-each>
														</xsl:if>
														
														<br/>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/Sede">
															<table border="0">
																<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/Sede">
																	<tr>
																		<td class="labelAlign">Sede legale: </td>
																		<td>
																			<xsl:if test="Indirizzo">
																				<xsl:value-of select="Indirizzo" />
																			</xsl:if>
																			<xsl:if test="NumeroCivico">
																				<xsl:value-of select="concat(', ', NumeroCivico)" />
																			</xsl:if>
																		</td>
																	</tr>
																	<tr>
																		<td></td>
																		<td>
																			<xsl:if test="CAP">
																				<xsl:value-of select="concat(CAP, ' ')" />
																			</xsl:if>
																			<xsl:if test="Comune">
																				<xsl:value-of select="concat(Comune, ' ')" />
																			</xsl:if>
																			<xsl:if test="Provincia">
																				<xsl:value-of select="concat('(', Provincia, ') ')" />
																			</xsl:if>
																			<xsl:if test="Nazione">
																				<xsl:value-of select="concat('- ', Nazione, ' ')" />
																			</xsl:if>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</xsl:if>
														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/StabileOrganizzazione">
															<table border="0">
																<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/StabileOrganizzazione">
																	<tr>
																		<td class="labelAlign">Stabile Organizzazione: </td>
																		<td>
																			<xsl:if test="Indirizzo">
																				<xsl:value-of select="Indirizzo" />
																			</xsl:if>
																			<xsl:if test="NumeroCivico">
																				<xsl:value-of select="concat(', ', NumeroCivico)" />
																			</xsl:if>
																		</td>
																	</tr>
																	<tr>
																		<td></td>
																		<td>
																			<xsl:if test="CAP">
																				<xsl:value-of select="concat(CAP, ' ')" />
																			</xsl:if>
																			<xsl:if test="Comune">
																				<xsl:value-of select="concat(Comune, ' ')" />
																			</xsl:if>
																			<xsl:if test="Provincia">
																				<xsl:value-of select="concat('(', Provincia, ') ')" />
																			</xsl:if>
																			<xsl:if test="Nazione">
																				<xsl:value-of select="concat('- ', Nazione, ' ')" />
																			</xsl:if>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</xsl:if>

														<br/>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore">
															<table border="0">
																<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore">
																	<xsl:if test="IdFiscaleIVA">
																		<tr>
																			<td class="labelAlign">Partita Iva: </td>
																			<td>
																				<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																				<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																			</td>
																		</tr>
																	</xsl:if>
																	<xsl:if test="CodiceFiscale">
																		<tr>
																			<td class="labelAlign">Codice fiscale: </td>
																			<td>
																				<xsl:value-of select="CodiceFiscale" />
																			</td>
																		</tr>
																	</xsl:if>
																	<xsl:if test="CodEORI">
																		<tr>
																			<td class="label1">Codice EORI: </td>
																			<td>
																				<xsl:value-of select="CodEORI" />
																			</td>
																		</tr>
																	</xsl:if>
																</xsl:for-each>
															</table>
														</xsl:if>
														<hr/>
														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/AlboProfessionale">
															<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/AlboProfessionale">
																<span class="label">Albo Professionale: </span>
																<xsl:value-of select="AlboProfessionale"/>
																<xsl:if test="ProvinciaAlbo">
																	<xsl:value-of select="concat(' (Provincia ', ProvinciaAlbo, ')')"/>
																</xsl:if>
																<xsl:if test="NumeroIscrizioneAlbo">
																	<xsl:value-of select="concat(' n. ', NumeroIscrizioneAlbo)"/>
																</xsl:if>
																<xsl:if test="DataIscrizioneAlbo">
																	<xsl:value-of select="' del '"/>
																	<xsl:call-template name="FormatDateShort">
																		<xsl:with-param name="DateTime" select="DataIscrizioneAlbo" />
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
															<hr/>
														</xsl:if>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore">
															<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore">
																<xsl:if test="RegimeFiscale">
																	<span class="label">Regime fiscale: </span>
																	<xsl:call-template name="RegimeFiscale">
																		<xsl:with-param name="pRegimeFiscale" select="RegimeFiscale"/>
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
															<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
																<xsl:if test="CapitaleSociale">
																	<br/>
																	<span class="label">Capitale sociale: </span>
																	<xsl:value-of select="CapitaleSociale" />
																</xsl:if>
																<xsl:if test="((Ufficio) or (NumeroREA))">
																	<br/>
																	<span class="label">REA: </span>
																	<xsl:value-of select="concat(Ufficio, ' ')" />
																	<xsl:value-of select="NumeroREA" />
																</xsl:if>
																<xsl:if test="SocioUnico">
																	<br/>
																	<span class="label">Numero soci: </span>
																	<xsl:call-template name="SocioUnico">
																		<xsl:with-param name="pSocioUnico" select="SocioUnico"/>
																	</xsl:call-template>
																</xsl:if>
																<xsl:if test="StatoLiquidazione">
																	<br/>
																	<span class="label">Stato Liquidazione: </span>
																	<xsl:call-template name="StatoLiquidazione">
																		<xsl:with-param name="pStatoLiquidazione" select="StatoLiquidazione"/>
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/Contatti">
															<xsl:if test="Telefono or Fax or Email">
																<hr/>
																<xsl:if test="Telefono">
																	<span class="label">Telefono: </span>
																	<xsl:value-of select="Telefono" />
																	<br/>
																</xsl:if>
																<xsl:if test="Fax">
																	<span class="label">Fax: </span>
																	<xsl:value-of select="Fax" />
																	<br/>
																</xsl:if>
																<xsl:if test="Email">
																	<span class="label">E-mail: </span>
																	<xsl:value-of select="Email" />
																	<br/>
																</xsl:if>
															</xsl:if>
														</xsl:for-each>
													</div>
												</xsl:if>
												<!--FINE DATI CEDENTE PRESTATORE-->

												<!--INIZIO DATI RAPPRESENTANTE FISCALE-->
												<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/RappresentanteFiscale">
													<div id="rappresentante-fiscale">
														<h3>Dati del rappresentante fiscale del cedente / prestatore</h3>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/RappresentanteFiscale/DatiAnagrafici">
															<h4>Dati anagrafici</h4>

															<ul>
																<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/RappresentanteFiscale/DatiAnagrafici">
																	<xsl:if test="IdFiscaleIVA">
																		<li>
																			Identificativo fiscale ai fini IVA:
																			<span>
																				<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																				<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="CodiceFiscale">
																		<li>
																			Codice fiscale:
																			<span>
																				<xsl:value-of select="CodiceFiscale" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Denominazione">
																		<li>
																			Denominazione:
																			<span>
																				<xsl:value-of select="Anagrafica/Denominazione" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<li>
																			Nome:
																			<span>
																				<xsl:value-of select="Anagrafica/Nome" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<li>
																			Cognome:
																			<span>
																				<xsl:value-of select="Anagrafica/Cognome" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<li>
																			Titolo onorifico:
																			<span>
																				<xsl:value-of select="Anagrafica/Titolo" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/CodEORI">
																		<li>
																			Codice EORI:
																			<span>
																				<xsl:value-of select="Anagrafica/CodEORI" />
																			</span>
																		</li>
																	</xsl:if>
																</xsl:for-each>
															</ul>
														</xsl:if>

													</div>
												</xsl:if>
												<!--FINE DATI RAPPRESENTANTE FISCALE-->

											</td>
											<td align="center" valign="top">
												<xsl:if test="((d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente)or(d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/SoggettoEmittente))">
													<div id="emittente">
														<span class="denominazione">Emittente</span>

														<!--INIZIO DATI TERZO INTERMEDIARIO SOGGETTO EMITTENTE-->
														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
															<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
																<br/>
																<span class="denominazione">
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</span>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
															<br/><br/>
															<table border="0">
																<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
																	<xsl:if test="IdFiscaleIVA">
																		<tr>
																			<td class="labelAlign">Partita Iva: </td>
																			<td>
																				<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																				<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																			</td>
																		</tr>
																	</xsl:if>
																	<xsl:if test="CodiceFiscale">
																		<tr>
																			<td class="labelAlign">Codice fiscale: </td>
																			<td>
																				<xsl:value-of select="CodiceFiscale" />
																			</td>
																		</tr>
																	</xsl:if>
																	<xsl:if test="Anagrafica/CodEORI">
																		<tr>
																			<td class="label1">Codice EORI: </td>
																			<td>
																				<xsl:value-of select="Anagrafica/CodEORI" />
																			</td>
																		</tr>
																	</xsl:if>
																</xsl:for-each>
															</table>
														</xsl:if>
														<!--FINE DATI TERZO INTERMEDIARIO SOGGETTO EMITTENTE-->

														<!--INIZIO DATI SOGGETTO EMITTENTE-->
														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/SoggettoEmittente">
															<br/>
															<div id="soggetto-emittente">
																<span class="label">Soggetto emittente: </span>
																<span>
																	<xsl:value-of select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/SoggettoEmittente" />
																</span>
																<xsl:variable name="SC">
																	<xsl:value-of select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/SoggettoEmittente" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$SC='CC'">
																		(cessionario/committente)
																	</xsl:when>
																	<xsl:when test="$SC='TZ'">
																		(terzo)
																	</xsl:when>
																	<xsl:when test="$SC=''">
																	</xsl:when>
																	<xsl:otherwise>
																		<span>(!!! codice non previsto !!!)</span>
																	</xsl:otherwise>
																</xsl:choose>
															</div>
														</xsl:if>
														<!--FINE DATI SOGGETTO EMITTENTE-->
													</div>
												</xsl:if>
											</td>

											<td align="right" valign="top">

												<!--INIZIO DATI CESSIONARIO COMMITTENTE-->
												<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente">
													<div id="cessionario">

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/AltriDatiIdentificativi">
															<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/AltriDatiIdentificativi">

																<span class="denominazione">
																	<xsl:if test="Denominazione">
																		<xsl:value-of select="Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Denominazione) and ((Titolo) or (Nome) or (Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Titolo">
																		<xsl:value-of select="concat(Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Nome">
																		<xsl:value-of select="concat(Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Cognome">
																		<xsl:value-of select="Cognome" />
																	</xsl:if>
																</span>
																<br/>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/AltriDatiIdentificativi/Sede">
															<table>
																<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/AltriDatiIdentificativi/Sede">
																	<tr>
																		<td class="labelAlign">Sede legale: </td>
																		<td>
																			<xsl:if test="Indirizzo">
																				<xsl:value-of select="Indirizzo" />
																			</xsl:if>
																			<xsl:if test="NumeroCivico">
																				<xsl:value-of select="concat(', ', NumeroCivico)" />
																			</xsl:if>
																		</td>
																	</tr>
																	<tr>
																		<td></td>
																		<td>
																			<xsl:if test="CAP">
																				<xsl:value-of select="concat(CAP, ' ')" />
																			</xsl:if>
																			<xsl:if test="Comune">
																				<xsl:value-of select="concat(Comune, ' ')" />
																			</xsl:if>
																			<xsl:if test="Provincia">
																				<xsl:value-of select="concat('(', Provincia, ') ')" />
																			</xsl:if>
																			<xsl:if test="Nazione">
																				<xsl:value-of select="concat('- ', Nazione, ' ')" />
																			</xsl:if>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</xsl:if>
														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/StabileOrganizzazione">
															<table>
																<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/StabileOrganizzazione">
																	<tr>
																		<td class="labelAlign">Stabile Organizzazione: </td>
																		<td>
																			<xsl:if test="Indirizzo">
																				<xsl:value-of select="Indirizzo" />
																			</xsl:if>
																			<xsl:if test="NumeroCivico">
																				<xsl:value-of select="concat(', ', NumeroCivico)" />
																			</xsl:if>
																		</td>
																	</tr>
																	<tr>
																		<td></td>
																		<td>
																			<xsl:if test="CAP">
																				<xsl:value-of select="concat(CAP, ' ')" />
																			</xsl:if>
																			<xsl:if test="Comune">
																				<xsl:value-of select="concat(Comune, ' ')" />
																			</xsl:if>
																			<xsl:if test="Provincia">
																				<xsl:value-of select="concat('(', Provincia, ') ')" />
																			</xsl:if>
																			<xsl:if test="Nazione">
																				<xsl:value-of select="concat('- ', Nazione, ' ')" />
																			</xsl:if>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</xsl:if>
														
														<br/>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/IdentificativiFiscali">
															<table border="0">
																<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/IdentificativiFiscali">
																	<xsl:if test="IdFiscaleIVA">
																		<tr>
																			<td class="labelAlign">Partita Iva: </td>
																			<td>
																				<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																				<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																			</td>
																		</tr>
																	</xsl:if>
																	<xsl:if test="CodiceFiscale">
																		<tr>
																			<td class="labelAlign">Codice fiscale: </td>
																			<td>
																				<xsl:value-of select="CodiceFiscale" />
																			</td>
																		</tr>
																	</xsl:if>
																	<xsl:if test="Anagrafica/CodEORI">
																		<tr>
																			<td class="label1">Codice EORI: </td>
																			<td>
																				<xsl:value-of select="Anagrafica/CodEORI" />
																			</td>
																		</tr>
																	</xsl:if>
																</xsl:for-each>
															</table>
														</xsl:if>
														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/RiferimentoAmministrazione">
															<table>
																<tr>
																	<td class="labelAlign">Riferimento Amministrazione: </td>
																	<td>
																		<xsl:value-of select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CedentePrestatore/RiferimentoAmministrazione" />
																	</td>
																</tr>
															</table>
														</xsl:if>

														<hr/>
														
														<table>

														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario">
															<tr>
																<td class="labelAlign">Codice Destinatario: </td>
																<td>
																	<xsl:value-of select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario" />
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario">
															<tr>
																<td class="labelAlign">Pec Destinatario: </td>
																<td>
																	<xsl:value-of select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario" />
																</td>
															</tr>
														</xsl:if>
														</table>
														
														<xsl:if test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/RappresentanteFiscale">
															<div id="rappresentante-fiscale">
																<hr/>
																<h4>Dati del rappresentante fiscale del cessionario / committente</h4>

																<ul>
																	<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/CessionarioCommittente/RappresentanteFiscale">
																		<xsl:if test="IdFiscaleIVA">
																			<li>
																		Identificativo fiscale ai fini IVA:
																				<span>
																					<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																					<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Denominazione">
																			<li>
																		Denominazione:
																				<span>
																					<xsl:value-of select="Denominazione" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Nome">
																			<li>
																		Nome:
																				<span>
																					<xsl:value-of select="Nome" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Cognome">
																			<li>
																		Cognome:
																				<span>
																					<xsl:value-of select="Cognome" />
																				</span>
																			</li>
																		</xsl:if>
																	</xsl:for-each>
																</ul>
															</div>
														</xsl:if>

													</div>
												</xsl:if>
												<!--FINE DATI CESSIONARIO COMMITTENTE-->

											</td>
										</tr>
									</table>


								</xsl:if>
								<!--FINE DATI HEADER-->

								<!--INIZIO DATI BODY-->
								<xsl:for-each select="d:FatturaElettronicaSemplificata/FatturaElettronicaBody">

									<xsl:variable name="valutaDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
									</xsl:variable>
									<xsl:variable name="tipoDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/TipoDocumento"/>
									</xsl:variable>

									<!--INIZIO DATI GENERALI DOCUMENTO-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento">

										<xsl:variable name="valuta">
											<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
										</xsl:variable>

										<div id="dati-generali-documento">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="2">
														<span class="titoloTabelle">
															Dati 
															<xsl:call-template name="TipoDocumento">
																<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
															</xsl:call-template>
														</span>
													</td>
												</tr>
												<tr>
													<td width="50%" align="left">
														<span class="label">
															<xsl:call-template name="TipoDocumento">
																<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
															</xsl:call-template>
													numero 
														</span>
														<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Numero" />
													</td>
													<td width="50%">
														<span class="label">Data documento: </span>
														<xsl:call-template name="FormatDateShort">
															<xsl:with-param name="DateTime" select="DatiGenerali/DatiGeneraliDocumento/Data" />
														</xsl:call-template>
													</td>
												</tr>

												<xsl:if test="((DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento) or (DatiGenerali/DatiGeneraliDocumento/Arrotondamento))">
													<tr>
														<td width="50%" align="left">
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento">
																<span class="label">
																	Importo documento: 
																</span>
																<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento" />
																<xsl:value-of select="concat(' ',$valuta)"/>
															</xsl:if>
														</td>
														<td width="50%">
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Arrotondamento">
																<span class="label">
																	Arrotondamento su Importo documento: 
																</span>
																<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Arrotondamento" />
																<xsl:value-of select="concat(' ',$valuta)"/>
															</xsl:if>
														</td>
													</tr>
												</xsl:if>

												<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiBollo">
													<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiBollo">
														<tr>
															<td width="50%" align="left">
																<span class="label">
																	Bollo virtuale: 
																</span>
																<xsl:if test="BolloVirtuale">
																	<xsl:value-of select="BolloVirtuale" />
																</xsl:if>
																<xsl:if test="((BolloVirtuale)or(ImportoBollo))">
																	<xsl:value-of select="' - '"/>
																</xsl:if>
																<xsl:if test="BolloVirtuale">
																	<xsl:value-of select="ImportoBollo"/>
																	<xsl:value-of select="concat(' ',$valuta)"/>
																</xsl:if>
															</td>
															<td width="50%">
															</td>
														</tr>
													</xsl:for-each>
												</xsl:if>

											</table>
										</div>

									</xsl:if>
									<!--FINE DATI GENERALI DOCUMENTO-->

									<!--INIZIO DATI DatiFatturaRettificata-->
									<xsl:if test="DatiGenerali/DatiFatturaRettificata">
										<br/>
										<div id="dati-generali-documento-DatiFatturaRettificata">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="2">
														<span class="titoloTabelle">
															Dati Fattura Rettificata
														</span>
													</td>
												</tr>
												<tr>
													<td width="50%" align="left">
														<span class="label">
															Numero FR: 
														</span>
														<xsl:value-of select="DatiGenerali/DatiFatturaRettificata/NumeroFR" />
													</td>
													<td width="50%">
														<span class="label">Data FR: </span>
														<xsl:call-template name="FormatDateShort">
															<xsl:with-param name="DateTime" select="DatiGenerali/DatiFatturaRettificata/DataFR" />
														</xsl:call-template>
													</td>
												</tr>

												<tr>
													<td width="100%" align="left" colspan="2">
														<span class="label">Elementi rettificati: </span>
														<xsl:value-of select="DatiGenerali/DatiFatturaRettificata/ElementiRettificati" />
													</td>
												</tr>

											</table>
										</div>

									</xsl:if>
									<!--FINE DATI DatiFatturaRettificata-->

									<!--INIZIO CAUSALI-->
									<xsl:if test="((DatiGenerali/DatiGeneraliDocumento/Causale) or (DatiGenerali/DatiGeneraliDocumento/Art73))">
										<br/>
										<table class="tabellaGenerica">
											<tr>
												<td class="rigaTitoli" colspan="2">
													<span class="titoloTabelle">Causali</span>
												</td>
											</tr>
											<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/Causale">
												<tr>
													<td align="left">
														<xsl:value-of select="current()" />
													</td>
												</tr>
											</xsl:for-each>
											<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Art73">
												<tr>
													<td align="left">
														<xsl:value-of select="concat('Articolo 73: ', DatiGenerali/DatiGeneraliDocumento/Art73)" />
													</td>
												</tr>
											</xsl:if>
											<xsl:for-each select="DatiGenerali/DatiSAL">
												<tr>
													<td align="left">
														<span class="label">
															Dati SAL: riferimento fase 
														</span>
														<xsl:value-of select="RiferimentoFase" />
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</xsl:if>
									<!--INIZIO CAUSALI-->

									<!--INIZIO DATI DI DETTAGLIO DELLE LINEE-->
									<xsl:if test="DatiBeniServizi">
										<br/>
										<div id="righe">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="11">
														<span class="titoloTabelle">Dati Beni Servizi - Dettaglio Linee</span>
													</td>
												</tr>
												<tr>
													<td width="40%">
														<span class="rigaTitoliTabelle">Descrizione</span>
													</td>
													<td width="10%">
														<span class="rigaTitoliTabelle">Importo</span>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="5%">
														<span class="rigaTitoliTabelle">Aliquota</span>
													</td>
													<td width="10%">
														<span class="rigaTitoliTabelle">Imposta</span>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="10%">
														<span class="rigaTitoliTabelle">Natura</span>
													</td>
													<td width="25%">
														<span class="rigaTitoliTabelle">Riferimento Normativo</span>
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi">
													<tr>
														<td>
															<xsl:value-of select="Descrizione" />
														</td>
														<td align="right">
															<xsl:value-of select="Importo" />
														</td>
														<td align="right">
															<xsl:if test="DatiIVA/Aliquota">
																<xsl:value-of select="DatiIVA/Aliquota" /> %
															</xsl:if>
														</td>
														<td align="right">
															<xsl:value-of select="DatiIVA/Imposta" />
														</td>
														<td>
															<xsl:if test="Natura">
																<span class="label">
																	<xsl:value-of select="Natura" />
																</span>
																 - 
																<xsl:variable name="NAT">
																	<xsl:value-of select="Natura" />
																</xsl:variable>
																<br/>
																<xsl:choose>
																	<xsl:when test="$NAT='N1'">
																		esclusa ex art.15
																	</xsl:when>
																	<xsl:when test="$NAT='N2'">
																		non soggetta
																	</xsl:when>
																	<xsl:when test="$NAT='N3'">
																		non imponibile
																	</xsl:when>
																	<xsl:when test="$NAT='N4'">
																		esente
																	</xsl:when>
																	<xsl:when test="$NAT='N5'">
																		regime del margine / IVA non esposta in fattura
																	</xsl:when>
																	<xsl:when test="$NAT='N6'">
																		inversione contabile
																	</xsl:when>
																	<xsl:when test="$NAT='N7'">
																		IVA assolta in altro stato UE
																	</xsl:when>
																	<xsl:otherwise>
																		!!! codice non previsto !!!
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
														<td>
															<xsl:value-of select="RiferimentoNormativo" />
														</td>
													</tr>

												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI DI DETTAGLIO DELLE LINEE-->

								</xsl:for-each>
								<!--FINE DATI BODY-->
							</div>
						</div>
					</xsl:if>
				</div>
			</body>

		</html>
	</xsl:template>

	<xsl:template match="/" mode="tiscali">
		<html>
			<head>
				<meta http-equiv="X-UA-Compatible" content="IE=edge" />
				<style type="text/css">
					#fattura-container { width: 100%; position: relative; }

					#fattura-elettronica { font-family: sans-serif; font-size: 11px; margin-left: auto; margin-right: auto; max-width: 1280px; min-width: 930px; padding: 0; }
					#fattura-elettronica .versione { font-size: 11px; float:right; color: #777777; }
					#fattura-elettronica h1 { padding: 20px 0 0 0; margin: 0; font-size: 25px; color: #012f51; }
					#fattura-elettronica h2 { padding: 0 0 0 0; margin: 0; font-size: 12px; color: #012f51; font-weight: bold; }
					#fattura-elettronica h3 { padding: 20px 0 0 0; margin: 0; font-size: 11px; font-weight: bold; }
					#fattura-elettronica h4 { padding: 5 5 5 5; font-size: 15px; color: #012f51; font-style:italic; text-align: center; background-color: #e5eaed;}
					#fattura-elettronica h5 { padding: 15px 0 0 0; margin: 0; font-size: 17px; font-style: italic; }
					#fattura-elettronica h6 { padding: 0px; margin: 0; font-size: 11px; font-weight: bold; color: #012f51; }

					#fattura-elettronica .label { padding: 20px 0 0 0; margin: 0; font-size: 11px; font-weight: bold; }

					#fattura-elettronica hr { font-size: 1px; color: #012f51; }

					#cedente {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						font-size: 11px; 
						text-align: left;
						border-radius: 5px;
						width:300px;
					}

					#cessionario {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						font-size: 11px; 
						text-align: left;
						border-radius: 5px;
						width:300px;
					}

					#terzointermediario {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						font-size: 11px; 
						text-align: left;
						border-radius: 5px;
						width:300px;
					}

					.tabellaGenerica{
						font-size: 11px;
						width: 100%;
						border-collapse: collapse;
						page-break-inside: auto;
						empty-cells: show;
					}
					.tabellaGenerica td{
						border: 1px solid #012f51;
						padding: 2px 5px 2px 5px;
					}
					.rigaTitoli{
						border: 1px solid #012f51;
						padding: 0;
					}


					#fattura-elettronica div.page {
						background-color: #fff !important;
						position: relative;

						margin: 20px 0 50px 0;
						padding: 10px;

						border: 1px solid #012f51;
					}
				</style>
			</head>
			<body>
				<div id="fattura-container">
					<xsl:if test="a:FatturaElettronica">

						<!--INIZIO - Variabili Header-Globali -->
						<xsl:variable name="tipoDestinatario">
							<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/FormatoTrasmissione"/>
						</xsl:variable>
						<!--FINE - Variabili Header-Globali -->

						<div id="fattura-elettronica">

							<h1>Fattura Elettronica vs 
								<xsl:choose>
									<xsl:when test="$tipoDestinatario='FPA12'">Pubblica Amministrazione</xsl:when>
									<xsl:when test="$tipoDestinatario='FPR12'">Privati</xsl:when>
									<xsl:otherwise>
										<span/>
									</xsl:otherwise>
								</xsl:choose>
							</h1>
							<div class="versione">
								Versione <xsl:value-of select="a:FatturaElettronica/@versione"/>
							</div>

							<div class="page">
								<!--INIZIO DATI HEADER-->
								<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader">

									<table width="100%">
										<tr>
											<td align="left" valign="top">

												<!--INIZIO DATI CEDENTE PRESTATORE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore">
													<div id="cedente">

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">

																<h2>
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</h2>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Sede">
															<span class="label">Sede legale: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Sede">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat('(', Provincia, ') ')" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat('- ', Nazione, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/StabileOrganizzazione">
															<span class="label">Stabile Organizzazione: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/StabileOrganizzazione">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat('(', Provincia, ') ')" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat('- ', Nazione, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<span class="label">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
																<xsl:if test="CodiceFiscale">
																	<br/>
																	<span class="label">Codice fiscale: </span>
																	<xsl:value-of select="CodiceFiscale" />
																</xsl:if>
																<xsl:if test="Anagrafica/CodEORI">
																	<br/>
																	<span class="label">Codice EORI: </span>
																	<xsl:value-of select="Anagrafica/CodEORI" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<hr/>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/AlboProfessionale">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
																<span class="label">Albo Professionale: </span>
																<xsl:value-of select="AlboProfessionale"/>
																<xsl:if test="ProvinciaAlbo">
																	<xsl:value-of select="concat(' (Provincia ', ProvinciaAlbo, ')')"/>
																</xsl:if>
																<xsl:if test="NumeroIscrizioneAlbo">
																	<xsl:value-of select="concat(' n. ', NumeroIscrizioneAlbo)"/>
																</xsl:if>
																<xsl:if test="DataIscrizioneAlbo">
																	<xsl:value-of select="' del '"/>
																	<xsl:call-template name="FormatDateShort">
																		<xsl:with-param name="DateTime" select="DataIscrizioneAlbo" />
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
															<hr/>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
																<xsl:if test="RegimeFiscale">
																	<span class="label">Regime fiscale: </span>
																	<xsl:call-template name="RegimeFiscale">
																		<xsl:with-param name="pRegimeFiscale" select="RegimeFiscale"/>
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
																<xsl:if test="CapitaleSociale">
																	<br/>
																	<span class="label">Capitale sociale: </span>
																	<xsl:value-of select="format-number(CapitaleSociale,  '###.###.##0,00######', 'euro')" />
																</xsl:if>
																<xsl:if test="((Ufficio) or (NumeroREA))">
																	<br/>
																	<span class="label">REA: </span>
																	<xsl:value-of select="concat(Ufficio, ' ')" />
																	<xsl:value-of select="NumeroREA" />
																</xsl:if>
																<xsl:if test="SocioUnico">
																	<br/>
																	<span class="label">Numero soci: </span>
																	<xsl:call-template name="SocioUnico">
																		<xsl:with-param name="pSocioUnico" select="SocioUnico"/>
																	</xsl:call-template>
																</xsl:if>
																<xsl:if test="StatoLiquidazione">
																	<br/>
																	<span class="label">Stato Liquidazione: </span>
																	<xsl:call-template name="StatoLiquidazione">
																		<xsl:with-param name="pStatoLiquidazione" select="StatoLiquidazione"/>
																	</xsl:call-template>
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Contatti">
															<xsl:if test="Telefono or Fax or Email">
																<hr/>
																<xsl:if test="Telefono">
																	<span class="label">Telefono: </span>
																	<xsl:value-of select="Telefono" />
																	<br/>
																</xsl:if>
																<xsl:if test="Fax">
																	<span class="label">Fax: </span>
																	<xsl:value-of select="Fax" />
																	<br/>
																</xsl:if>
																<xsl:if test="Email">
																	<span class="label">E-mail: </span>
																	<xsl:value-of select="Email" />
																	<br/>
																</xsl:if>
															</xsl:if>
														</xsl:for-each>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/RiferimentoAmministrazione">
															<hr/>
															<span class="label">Riferimento amministrativo: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/RiferimentoAmministrazione" />
														</xsl:if>
													</div>
												</xsl:if>
												<!--FINE DATI CEDENTE PRESTATORE-->

												<!--INIZIO DATI RAPPRESENTANTE FISCALE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/RappresentanteFiscale">
													<div id="rappresentante-fiscale">
														<h3>Dati del rappresentante fiscale del cedente / prestatore</h3>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/RappresentanteFiscale/DatiAnagrafici">
															<h4>Dati anagrafici</h4>

															<ul>
																<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/RappresentanteFiscale/DatiAnagrafici">
																	<xsl:if test="IdFiscaleIVA">
																		<li>
																			Identificativo fiscale ai fini IVA:
																			<span>
																				<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																				<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="CodiceFiscale">
																		<li>
																			Codice fiscale:
																			<span>
																				<xsl:value-of select="CodiceFiscale" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Denominazione">
																		<li>
																			Denominazione:
																			<span>
																				<xsl:value-of select="Anagrafica/Denominazione" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<li>
																			Nome:
																			<span>
																				<xsl:value-of select="Anagrafica/Nome" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<li>
																			Cognome:
																			<span>
																				<xsl:value-of select="Anagrafica/Cognome" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<li>
																			Titolo onorifico:
																			<span>
																				<xsl:value-of select="Anagrafica/Titolo" />
																			</span>
																		</li>
																	</xsl:if>
																	<xsl:if test="Anagrafica/CodEORI">
																		<li>
																			Codice EORI:
																			<span>
																				<xsl:value-of select="Anagrafica/CodEORI" />
																			</span>
																		</li>
																	</xsl:if>
																</xsl:for-each>
															</ul>
														</xsl:if>

													</div>
												</xsl:if>
												<!--FINE DATI RAPPRESENTANTE FISCALE-->

											</td>
											<td align="center" valign="top">
												<xsl:if test="((a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente)or(a:FatturaElettronica/FatturaElettronicaHeader/SoggettoEmittente))">
													<div id="terzointermediario">
														<h2>Emittente</h2>

														<!--INIZIO DATI TERZO INTERMEDIARIO SOGGETTO EMITTENTE-->
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
																<br/>
																<h2>
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</h2>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/TerzoIntermediarioOSoggettoEmittente/DatiAnagrafici">
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<span class="label">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
																<xsl:if test="CodiceFiscale">
																	<br/>
																	<span class="label">Codice fiscale: </span>
																	<xsl:value-of select="CodiceFiscale" />
																</xsl:if>
																<xsl:if test="Anagrafica/CodEORI">
																	<br/>
																	<span class="label">Codice EORI: </span>
																	<xsl:value-of select="Anagrafica/CodEORI" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<!--FINE DATI TERZO INTERMEDIARIO SOGGETTO EMITTENTE-->

														<!--INIZIO DATI SOGGETTO EMITTENTE-->
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/SoggettoEmittente">
															<div id="soggetto-emittente">
																<ul>
																	<li>
																		Soggetto emittente:
																		<span>
																			<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/SoggettoEmittente" />
																		</span>
																		<xsl:variable name="SC">
																			<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/SoggettoEmittente" />
																		</xsl:variable>
																		<xsl:choose>
																			<xsl:when test="$SC='CC'">
																				(cessionario/committente)
																			</xsl:when>
																			<xsl:when test="$SC='TZ'">
																				(terzo)
																			</xsl:when>
																			<xsl:when test="$SC=''">
																			</xsl:when>
																			<xsl:otherwise>
																				<span>(!!! codice non previsto !!!)</span>
																			</xsl:otherwise>
																		</xsl:choose>
																	</li>
																</ul>
															</div>
														</xsl:if>
														<!--FINE DATI SOGGETTO EMITTENTE-->
													</div>
												</xsl:if>
											</td>

											<td align="right" valign="top">

												<!--INIZIO DATI CESSIONARIO COMMITTENTE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente">
													<div id="cessionario">

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">

																<h2>
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</h2>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/Sede">
															<span class="label">Sede legale: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/Sede">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat('(', Provincia, ') ')" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat('- ', Nazione, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/StabileOrganizzazione">
															<span class="label">Stabile Organizzazione: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/StabileOrganizzazione">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat('(', Provincia, ') ')" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat('- ', Nazione, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<span class="label">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
																<xsl:if test="CodiceFiscale">
																	<br/>
																	<span class="label">Codice fiscale: </span>
																	<xsl:value-of select="CodiceFiscale" />
																</xsl:if>
																<xsl:if test="Anagrafica/CodEORI">
																	<br/>
																	<span class="label">Codice EORI: </span>
																	<xsl:value-of select="Anagrafica/CodEORI" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<hr/>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario">
															<span class="label">Codice Destinatario: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario" />
															<br/>
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario">
															<span class="label">Pec Destinatario: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario" />
															<br/>
														</xsl:if>


														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/RappresentanteFiscale">
															<div id="rappresentante-fiscale">
																<hr/>
																<h4>Dati del rappresentante fiscale del cessionario / committente</h4>

																<ul>
																	<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/RappresentanteFiscale">
																		<xsl:if test="IdFiscaleIVA">
																			<li>
																		Identificativo fiscale ai fini IVA:
																				<span>
																					<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																					<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Denominazione">
																			<li>
																		Denominazione:
																				<span>
																					<xsl:value-of select="Denominazione" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Nome">
																			<li>
																		Nome:
																				<span>
																					<xsl:value-of select="Nome" />
																				</span>
																			</li>
																		</xsl:if>
																		<xsl:if test="Cognome">
																			<li>
																		Cognome:
																				<span>
																					<xsl:value-of select="Cognome" />
																				</span>
																			</li>
																		</xsl:if>
																	</xsl:for-each>
																</ul>
															</div>
														</xsl:if>

													</div>
												</xsl:if>
												<!--FINE DATI CESSIONARIO COMMITTENTE-->

											</td>
										</tr>
									</table>


								</xsl:if>
								<!--FINE DATI HEADER-->

								<!--INIZIO DATI BODY-->
								<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaBody">

									<xsl:variable name="valutaDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
									</xsl:variable>
									<xsl:variable name="tipoDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/TipoDocumento"/>
									</xsl:variable>

									<!--INIZIO DATI GENERALI DOCUMENTO-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento">

										<xsl:variable name="valuta">
											<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
										</xsl:variable>

										<div id="dati-generali-documento">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="2">
														<h4>Dati generali del Documento</h4>
													</td>
												</tr>
												<tr>
													<td width="300px" align="left">
														<span class="label">
															<xsl:call-template name="TipoDocumento">
																<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
															</xsl:call-template>
														</span>
														<span class="label">
													numero 
														</span>
														<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Numero" />
													</td>
													<td width="200px">
														<span class="label">Data documento: </span>
														<xsl:call-template name="FormatDateShort">
															<xsl:with-param name="DateTime" select="DatiGenerali/DatiGeneraliDocumento/Data" />
														</xsl:call-template>
													</td>
												</tr>

												<xsl:if test="((DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento) or (DatiGenerali/DatiGeneraliDocumento/Arrotondamento))">
													<tr>
														<td width="300px" align="left">
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento">
																<span class="label">
																	Importo documento: 
																</span>
																<xsl:value-of select="format-number(DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento,  '###.###.##0,00######', 'euro')" />
																<xsl:value-of select="concat(' ',$valuta)"/>
															</xsl:if>
														</td>
														<td width="200px">
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Arrotondamento">
																<span class="label">
																	Arrotondamento su Importo documento: 
																</span>
																<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Arrotondamento" />
																<xsl:value-of select="concat(' ',$valuta)"/>
															</xsl:if>
														</td>
													</tr>
												</xsl:if>

												<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiBollo">
													<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiBollo">
														<tr>
															<td width="300px" align="left">
																<span class="label">
																	Bollo virtuale: 
																	</span>
																<xsl:if test="BolloVirtuale">
																	<xsl:value-of select="BolloVirtuale" />
																</xsl:if>
																<xsl:if test="((BolloVirtuale)or(ImportoBollo))">
																	<xsl:value-of select="' - '"/>
																</xsl:if>
																<xsl:if test="BolloVirtuale">
																	<xsl:value-of select="ImportoBollo"/>
																	<xsl:value-of select="concat(' ',$valuta)"/>
																</xsl:if>
															</td>
															<td width="200px">
															</td>
														</tr>
													</xsl:for-each>
												</xsl:if>

											</table>
										</div>
										
										<!--Dati cassa-->
										<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiCassaPrevidenziale">
											<div id="dati-cassa-previdenziale">
												<br/>
												<table class="tabellaGenerica">
													<tr>
														<td colspan="8">
															<h4>Dati Cassa Previdenziale</h4>
														</td>
													</tr>
													<tr>
														<td width="100px">
															<span class="label">Tipo Cassa</span>
														</td>
														<td width="100px">
															<span class="label">Aliquota contributo cassa (%)</span>
														</td>
														<td width="100px">
															<span class="label">Importo contributo cassa</span>
														</td>
														<td width="100px">
															<span class="label">Imponibile previdenziale</span>
														</td>
														<td width="100px">
															<span class="label">Aliquota IVA applicata</span>
														</td>
														<td width="100px">
															<span class="label">Contributo cassa soggetto a ritenuta</span>
														</td>
														<td width="100px">
															<span class="label">Tipologia di non imponibilità del contributo</span>
														</td>
														<td width="100px">
															<span class="label">Riferimento amministrativo / contabile</span>
														</td>
													</tr>

													<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiCassaPrevidenziale">
														<tr>
															<td width="100px">
																<xsl:if test="TipoCassa">
																	<xsl:variable name="TC">
																		<xsl:value-of select="TipoCassa" />
																	</xsl:variable>
																	<xsl:choose>
																		<xsl:when test="$TC='TC01'">
																			Cassa Nazionale Previdenza e Assistenza Avvocati e Procuratori legali
																		</xsl:when>
																		<xsl:when test="$TC='TC02'">
																			Cassa Previdenza Dottori Commercialisti
																		</xsl:when>
																		<xsl:when test="$TC='TC03'">
																			Cassa Previdenza e Assistenza Geometri
																		</xsl:when>
																		<xsl:when test="$TC='TC04'">
																			Cassa Nazionale Previdenza e Assistenza Ingegneri e Architetti liberi profess.
																		</xsl:when>
																		<xsl:when test="$TC='TC05'">
																			Cassa Nazionale del Notariato
																		</xsl:when>
																		<xsl:when test="$TC='TC06'">
																			Cassa Nazionale Previdenza e Assistenza Ragionieri e Periti commerciali
																		</xsl:when>
																		<xsl:when test="$TC='TC07'">
																			Ente Nazionale Assistenza Agenti e Rappresentanti di Commercio-ENASARCO
																		</xsl:when>
																		<xsl:when test="$TC='TC08'">
																			Ente Nazionale Previdenza e Assistenza Consulenti del Lavoro-ENPACL
																		</xsl:when>
																		<xsl:when test="$TC='TC09'">
																			Ente Nazionale Previdenza e Assistenza Medici-ENPAM
																		</xsl:when>
																		<xsl:when test="$TC='TC10'">
																			Ente Nazionale Previdenza e Assistenza Farmacisti-ENPAF
																		</xsl:when>
																		<xsl:when test="$TC='TC11'">
																			Ente Nazionale Previdenza e Assistenza Veterinari-ENPAV
																		</xsl:when>
																		<xsl:when test="$TC='TC12'">
																			Ente Nazionale Previdenza e Assistenza Impiegati dell'Agricoltura-ENPAIA
																		</xsl:when>
																		<xsl:when test="$TC='TC13'">
																			Fondo Previdenza Impiegati Imprese di Spedizione e Agenzie Marittime
																		</xsl:when>
																		<xsl:when test="$TC='TC14'">
																			Istituto Nazionale Previdenza Giornalisti Italiani-INPGI
																		</xsl:when>
																		<xsl:when test="$TC='TC15'">
																			Opera Nazionale Assistenza Orfani Sanitari Italiani-ONAOSI
																		</xsl:when>
																		<xsl:when test="$TC='TC16'">
																			Cassa Autonoma Assistenza Integrativa Giornalisti Italiani-CASAGIT
																		</xsl:when>
																		<xsl:when test="$TC='TC17'">
																			Ente Previdenza Periti Industriali e Periti Industriali Laureati-EPPI
																		</xsl:when>
																		<xsl:when test="$TC='TC18'">
																			Ente Previdenza e Assistenza Pluricategoriale-EPAP
																		</xsl:when>
																		<xsl:when test="$TC='TC19'">
																			Ente Nazionale Previdenza e Assistenza Biologi-ENPAB
																		</xsl:when>
																		<xsl:when test="$TC='TC20'">
																			Ente Nazionale Previdenza e Assistenza Professione Infermieristica-ENPAPI
																		</xsl:when>
																		<xsl:when test="$TC='TC21'">
																			Ente Nazionale Previdenza e Assistenza Psicologi-ENPAP
																		</xsl:when>
																		<xsl:when test="$TC='TC22'">
																			INPS
																		</xsl:when>
																		<xsl:when test="$TC=''">
																		</xsl:when>
																		<xsl:otherwise>
																			<span>!!! Codice non previsto !!!</span>
																		</xsl:otherwise>
																	</xsl:choose>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="AlCassa">
																	<xsl:value-of select="AlCassa" /> %
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="ImportoContributoCassa">
																	<xsl:value-of select="format-number(ImportoContributoCassa,  '###.###.##0,00######', 'euro')" />
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="ImponibileCassa">
																	<xsl:value-of select="format-number(ImponibileCassa,  '###.###.##0,00######', 'euro')" />
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="AliquotaIVA">
																	<xsl:value-of select="AliquotaIVA" /> %
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="Ritenuta">
																	<xsl:value-of select="format-number(Ritenuta,  '###.###.##0,00######', 'euro')" />
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="Natura">
																	<xsl:variable name="NT">
																		<xsl:value-of select="Natura" />
																	</xsl:variable>
																	<span class="label">
																		<xsl:value-of select="Natura" />
																	</span>
																	 - 
																	<xsl:choose>
																		<xsl:when test="$NT='N1'">
																			escluse ex art. 15
																		</xsl:when>
																		<xsl:when test="$NT='N2'">
																			non soggette
																		</xsl:when>
																		<xsl:when test="$NT='N3'">
																			non imponibili
																		</xsl:when>
																		<xsl:when test="$NT='N4'">
																			esenti
																		</xsl:when>
																		<xsl:when test="$NT='N5'">
																			regime del margine / IVA non esposta in fattura
																		</xsl:when>
																		<xsl:when test="$NT='N6'">
																			inversione contabile
																		</xsl:when>
																		<xsl:when test="$NT='N7'">
																			IVA assolta in altro stato UE
																		</xsl:when>
																		<xsl:when test="$NT=''">
																		</xsl:when>
																		<xsl:otherwise>
																			<span>!!! codice non previsto !!!</span>
																		</xsl:otherwise>
																	</xsl:choose>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="RiferimentoAmministrazione">
																	<xsl:value-of select="RiferimentoAmministrazione" />
																</xsl:if>
															</td>
														</tr>
													</xsl:for-each>
												</table>

											</div>
										</xsl:if>

										<!--Dati ritenuta-->
										<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiRitenuta">
											<div id="dati-ritenuta">
												<br/>
												<table class="tabellaGenerica">
													<tr>
														<td colspan="4">
															<h4>Dati Ritenuta</h4>
														</td>
													</tr>
													<tr>
														<td width="100px">
															<span class="label">Tipo Ritenuta</span>
														</td>
														<td width="100px">
															<span class="label">Importo</span>
														</td>
														<td width="100px">
															<span class="label">Aliquota</span>
														</td>
														<td width="200px">
															<span class="label">Causale</span>
														</td>
													</tr>

													<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiRitenuta">
														<tr>
															<td width="100px">
																<xsl:if test="TipoRitenuta">
																	<xsl:variable name="TR">
																		<xsl:value-of select="TipoRitenuta" />
																	</xsl:variable>
																	<xsl:choose>
																		<xsl:when test="$TR='RT01'">
																			Ritenuta persone fisiche
																		</xsl:when>
																		<xsl:when test="$TR='RT02'">
																			Ritenuta persone giuridiche
																		</xsl:when>
																		<xsl:when test="$TR=''">
																		</xsl:when>
																		<xsl:otherwise>
																			<span>!!! Codice non previsto !!!</span>
																		</xsl:otherwise>
																	</xsl:choose>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="ImportoRitenuta">
																	<xsl:value-of select="format-number(ImportoRitenuta,  '###.###.##0,00######', 'euro')" />
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="AliquotaRitenuta">
																	<xsl:value-of select="AliquotaRitenuta" /> %
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="CausalePagamento">
																	<xsl:value-of select="CausalePagamento" />
																	<xsl:variable name="CP">
																		<xsl:value-of select="CausalePagamento" />
																	</xsl:variable>
																	<xsl:if test="$CP!=''">
																		(decodifica come da modello 770S)
																	</xsl:if>
																</xsl:if>
															</td>
														</tr>
													</xsl:for-each>
												</table>

											</div>
										</xsl:if>

										</xsl:if>
									<!--FINE DATI GENERALI DOCUMENTO-->


									<!--INIZIO DATI PAGAMENTO-->
									<xsl:if test="DatiPagamento">
										<div id="dati-pagamento-condizioni">
											<xsl:for-each select="DatiPagamento">
												<br/>
												<table class="tabellaGenerica">
													<tr>
														<td class="rigaTitoli" colspan="6">
															<h4>Dati relativi al pagamento</h4>
														</td>
													</tr>
													<tr>
														<td colspan="6">
															<xsl:if test="CondizioniPagamento">
																<span class="label">Condizioni di pagamento: </span>
																<xsl:variable name="CP">
																	<xsl:value-of select="CondizioniPagamento" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$CP='TP01'">
																		Pagamento a rate
																	</xsl:when>
																	<xsl:when test="$CP='TP02'">
																		Pagamento completo
																	</xsl:when>
																	<xsl:when test="$CP='TP03'">
																		Anticipo
																	</xsl:when>
																	<xsl:when test="$CP=''">
																	</xsl:when>
																	<xsl:otherwise>
																		Modalità di pagamento: codice non previsto !!!
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
															<br/>
															<br/>
														</td>
													</tr>
													<xsl:for-each select="DettaglioPagamento">
														<tr>
															<td width="100px">
																<h6>Importo da pagare</h6>
															</td>
															<td width="100px">
																<h6>Modalità di pagamento</h6>
															</td>
															<td width="200px">
																<h6>Beneficiario</h6>
															</td>
															<td width="60px">
																<h6>Scadenza pagamento</h6>
															</td>
															<td width="60px">
																<h6>Data Rif. Pagamento</h6>
															</td>
															<td width="60px">
																<h6>Giorni termine pagamento</h6>
															</td>
														</tr>
														<tr>
															<td width="200px">
																<xsl:value-of select="concat(format-number(ImportoPagamento,  '###.###.##0,00######', 'euro'), ' ', $valutaDocumento)"/>
															</td>
															<td width="150px">
																<xsl:if test="ModalitaPagamento">
																	<xsl:call-template name="ModalitaPagamento">
																		<xsl:with-param name="pModalitaPagamento" select="ModalitaPagamento"/>
																	</xsl:call-template>
																</xsl:if>
															</td>
															<td width="250px">
																<xsl:value-of select="Beneficiario"/>
															</td>
															<td width="100px">
																<xsl:if test="DataScadenzaPagamento">
																	<xsl:call-template name="FormatDateShort">
																		<xsl:with-param name="DateTime" select="DataScadenzaPagamento" />
																	</xsl:call-template>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="DataRiferimentoTerminiPagamento">
																	<xsl:call-template name="FormatDateShort">
																		<xsl:with-param name="DateTime" select="DataRiferimentoTerminiPagamento" />
																	</xsl:call-template>
																</xsl:if>
															</td>
															<td width="100px">
																<xsl:if test="GiorniTerminiPagamento">
																	<xsl:value-of select="GiorniTerminiPagamento" />
																</xsl:if>
															</td>
														</tr>
														<tr>
															<td colspan="6" style="padding-left:40px; font-style:italic;">
																<xsl:if test="IstitutoFinanziario">
																	<span class="label">Pagabile presso: </span>
																	<xsl:value-of select="IstitutoFinanziario" />
																</xsl:if>
																<xsl:if test="IBAN">
																	<span class="label"> IBAN: </span>
																	<xsl:value-of select="IBAN" />
																</xsl:if>
																<xsl:if test="((ABI)or(CAB)or(BIC))"> (</xsl:if>
																<xsl:if test="ABI">
																	<span class="label"> ABI: </span>
																	<xsl:value-of select="ABI" />
																</xsl:if>
																<xsl:if test="CAB">
																	<span class="label"> CAB: </span>
																	<xsl:value-of select="CAB" />
																</xsl:if>
																<xsl:if test="BIC">
																	<span class="label"> BIC: </span>
																	<xsl:value-of select="BIC" />
																</xsl:if>
																<xsl:if test="((ABI)or(CAB)or(BIC))"> )</xsl:if>
															</td>
														</tr>
														<xsl:if test="CodUfficioPostale">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<span class="label">Codice Ufficio Postale: </span>
																	<xsl:value-of select="CodUfficioPostale" />
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="((TitoloQuietanzante)or(CognomeQuietanzante)or(NomeQuietanzante)or(CFQuietanzante))">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<span class="label"> Quietanzante: </span>
																	<xsl:value-of select="concat(TitoloQuietanzante, ' ', CognomeQuietanzante, ' ', NomeQuietanzante)" />
																	<span class="label"> CF: </span>
																	<xsl:value-of select="CFQuietanzante" />
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="((ScontoPagamentoAnticipato)or(DataLimitePagamentoAnticipato))">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<xsl:if test="ScontoPagamentoAnticipato">
																		<span class="label">Sconto per pagamento anticipato: </span>
																		<xsl:value-of select="format-number(ScontoPagamentoAnticipato,  '###.###.##0,00######', 'euro')" />
																	</xsl:if>
																	<xsl:if test="DataLimitePagamentoAnticipato">
																		<span class="label">Data limite per il pagamento anticipato: </span>
																		<xsl:call-template name="FormatDateShort">
																			<xsl:with-param name="DateTime" select="DataLimitePagamentoAnticipato" />
																		</xsl:call-template>
																	</xsl:if>
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="((PenalitaPagamentiRitardati)or(DataDecorrenzaPenale))">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<xsl:if test="PenalitaPagamentiRitardati">
																		<span class="label">Penale per ritardato pagamento: </span>
																		<xsl:value-of select="PenalitaPagamentiRitardati" />																			
																	</xsl:if>
																	<xsl:if test="DataDecorrenzaPenale">
																		<span class="label">Data di decorrenza della penale: </span>
																		<xsl:call-template name="FormatDateShort">
																			<xsl:with-param name="DateTime" select="DataDecorrenzaPenale" />
																		</xsl:call-template>
																	</xsl:if>
																</td>
															</tr>
														</xsl:if>
														<xsl:if test="(CodicePagamento)">
															<tr>
																<td colspan="6" style="padding-left:40px; border:0; font-style:italic;">
																	<xsl:if test="CodicePagamento">
																		<span class="label">Codice pagamento: </span>
																		<xsl:value-of select="CodicePagamento" />
																	</xsl:if>
																</td>
															</tr>
														</xsl:if>
													</xsl:for-each>
												</table>
											</xsl:for-each>
										</div>
									</xsl:if>
									<!--FINE DATI PAGAMENTO-->

									<!--INIZIO CAUSALI-->
									<xsl:if test="((DatiGenerali/DatiGeneraliDocumento/Causale) or (DatiGenerali/DatiGeneraliDocumento/Art73))">
										<br/>
										<table class="tabellaGenerica">
											<tr>
												<td class="rigaTitoli" colspan="2">
													<h4>Causali</h4>
												</td>
											</tr>
											<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/Causale">
												<tr>
													<td align="left">
														<xsl:value-of select="current()" />
													</td>
												</tr>
											</xsl:for-each>
											<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Art73">
												<tr>
													<td align="left">
														<xsl:value-of select="concat('Articolo 73: ', DatiGenerali/DatiGeneraliDocumento/Art73)" />
													</td>
												</tr>
											</xsl:if>
											<xsl:for-each select="DatiGenerali/DatiSAL">
												<tr>
													<td align="left">
														<span class="label">
															Dati SAL: riferimento fase 
														</span>
														<xsl:value-of select="RiferimentoFase" />
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</xsl:if>
									<!--INIZIO CAUSALI-->

									<!--INIZIO DATI DI DETTAGLIO DELLE LINEE-->
									<xsl:if test="DatiBeniServizi/DettaglioLinee">
										<br/>
										<div id="righe">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="11">
														<h4>Dati Beni Servizi - Dettaglio Linee</h4>
													</td>
												</tr>
												<tr>
													<td width="30px">
														<h6>Linea</h6>
													</td>
													<td width="80px">
														<h6>Articolo</h6>
													</td>
													<td width="200px">
														<h6>Descrizione</h6>
													</td>
													<td width="80px">
														<h6>Periodo</h6>
													</td>
													<td width="50px">
														<h6>Quantità</h6>
													</td>
													<td width="50px">
														<h6>Unità di Misura</h6>
													</td>
													<td width="90px">
														<h6>Prezzo unitario</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Sconto o Maggiorazione</h6>
													</td>
													<td width="90px">
														<h6>Prezzo totale</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="50px">
														<h6>Aliquota</h6>
													</td>
													<td width="80px">
														<h6>Natura</h6>
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi/DettaglioLinee">
													<tr>
														<td align="right">
															<xsl:value-of select="NumeroLinea" />
														</td>
														<td>
															<xsl:if test="((CodiceArticolo/CodiceTipo)or(CodiceArticolo/CodiceValore))">
																<xsl:value-of select="CodiceArticolo/CodiceTipo" />
																<br/>
																<xsl:value-of select="CodiceArticolo/CodiceValore" />
															</xsl:if>
														</td>
														<td>
															<xsl:if test="TipoCessionePrestazione">
																<span class="label">
																	<xsl:value-of select="'Tipo Cessione Prestazione: '" />
																</span>
																<xsl:variable name="TCP">
																	<xsl:value-of select="TipoCessionePrestazione" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$TCP='SC'">
																		(sconto)
																	</xsl:when>
																	<xsl:when test="$TCP='PR'">
																		(premio)
																	</xsl:when>
																	<xsl:when test="$TCP='AB'">
																		(abbuono)
																	</xsl:when>
																	<xsl:when test="$TCP='AC'">
																		(spesa accessoria)
																	</xsl:when>
																	<xsl:otherwise>
																		<span>(!!! codice non previsto !!!)</span>
																	</xsl:otherwise>
																</xsl:choose>
																<br/>
															</xsl:if>
															<xsl:value-of select="Descrizione" />
														</td>
														<td>
															<xsl:if test="DataInizioPeriodo">
																da: 
																<xsl:call-template name="FormatDateShort">
																	<xsl:with-param name="DateTime" select="DataInizioPeriodo" />
																</xsl:call-template>
															</xsl:if>
															<xsl:if test="((DataInizioPeriodo) or (DataFinePeriodo))">
																<br/>
															</xsl:if>
															<xsl:if test="DataFinePeriodo">
																a: 
																<xsl:call-template name="FormatDateShort">
																	<xsl:with-param name="DateTime" select="DataFinePeriodo" />
																</xsl:call-template>
															</xsl:if>
														</td>
														<td align="right">
															<xsl:value-of select="Quantita" />
														</td>
														<td>
															<xsl:value-of select="UnitaMisura" />
														</td>
														<td align="right">
															<xsl:value-of select="format-number(PrezzoUnitario,  '###.###.##0,00######', 'euro')" />
														</td>
														<td>
															<xsl:for-each select="ScontoMaggiorazione">
																<xsl:variable name="TSCM">
																	<xsl:value-of select="Tipo" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$TSCM='SC'">
																		Sconto: 
																	</xsl:when>
																	<xsl:when test="$TSCM='MG'">
																		Magg.: 
																	</xsl:when>
																	<xsl:otherwise>
																		!!! Codice non previsto: 
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:if test="Percentuale">
																	<xsl:value-of select="Percentuale" /> %
																</xsl:if>
																<xsl:if test="Importo">
																	<xsl:value-of select="format-number(Importo,  '###.###.##0,00######', 'euro')"/>
																	<xsl:value-of select="concat(' ',$valutaDocumento)" />
																</xsl:if>
															</xsl:for-each>
														</td>
														<td align="right">
															<xsl:value-of select="format-number(PrezzoTotale,  '###.###.##0,00######', 'euro')" />
														</td>
														<td align="right">
															<xsl:value-of select="AliquotaIVA" /> %</td>
														<td>
															<xsl:if test="Natura">
																<span class="label">
																	<xsl:value-of select="Natura" />
																</span>
																 - 
																<xsl:variable name="NAT">
																	<xsl:value-of select="Natura" />
																</xsl:variable>
																<br/>
																<xsl:choose>
																	<xsl:when test="$NAT='N1'">
																		esclusa ex art.15
																	</xsl:when>
																	<xsl:when test="$NAT='N2'">
																		non soggetta
																	</xsl:when>
																	<xsl:when test="$NAT='N3'">
																		non imponibile
																	</xsl:when>
																	<xsl:when test="$NAT='N4'">
																		esente
																	</xsl:when>
																	<xsl:when test="$NAT='N5'">
																		regime del margine / IVA non esposta in fattura
																	</xsl:when>
																	<xsl:when test="$NAT='N6'">
																		inversione contabile
																	</xsl:when>
																	<xsl:when test="$NAT='N7'">
																		IVA assolta in altro stato UE
																	</xsl:when>
																	<xsl:otherwise>
																		!!! codice non previsto !!!
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
													</tr>

													<xsl:if test="((Ritenuta)or(RiferimentoAmministrazione)or(AltriDatiGestionali))">
														<tr>
															<td style="border:0;"/>
															<td colspan="10">
																<table class="tabellaGenerica">
																	<xsl:if test="Ritenuta">
																		<tr>
																			<td width="200px" style="padding-left:30px; border:0; font-style:italic;">
																				<b>
																					<xsl:value-of select="'Soggetta a ritenuta:'"/>
																				</b>
																			</td>
																			<td style="padding-left:10px; border:0; font-style:italic;">
																				<xsl:value-of select="Ritenuta" />
																			</td>
																		</tr>
																	</xsl:if>
																	<xsl:if test="RiferimentoAmministrazione">
																		<tr>
																			<td width="200px" style="padding-left:30px; border:0; font-style:italic;">
																				<b>
																					<xsl:value-of select="'Riferimento amministrativo/contabile:'"/>
																				</b>
																			</td>
																			<td style="padding-left:10px; border:0; font-style:italic;">
																				<xsl:value-of select="RiferimentoAmministrazione" />
																			</td>
																		</tr>
																	</xsl:if>

																	<xsl:for-each select="AltriDatiGestionali">
																		<tr>
																			<td width="200px" style="padding-left:30px; border:0; font-style:italic;">
																				<b>
																					<xsl:value-of select="'Altri dati gestionali'"/>
																				</b>
																			</td>
																			<td style="padding-left:10px; border:0; font-style:italic;">
																				<b>
																					<xsl:value-of select="concat(TipoDato, ': ')"/>
																				</b>
																				<xsl:if test="RiferimentoTesto">
																					<xsl:value-of select="concat(RiferimentoTesto, ' ')"/>
																				</xsl:if>
																				<xsl:if test="RiferimentoNumero">
																					<xsl:value-of select="concat('numero ', RiferimentoNumero, ' ')"/>
																				</xsl:if>
																				<xsl:if test="RiferimentoData">
																					<xsl:value-of select="'del '"/>
																					<xsl:call-template name="FormatDateShort">
																						<xsl:with-param name="DateTime" select="RiferimentoData" />
																					</xsl:call-template>
																				</xsl:if>
																			</td>
																		</tr>
																	</xsl:for-each>

																</table>
															</td>
														</tr>
													</xsl:if>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI DI DETTAGLIO DELLE LINEE-->

									<!--INIZIO ALTRI DATI-->
									<xsl:if test="(
									(DatiGenerali/DatiOrdineAcquisto)or
									(DatiGenerali/DatiContratto)or
									(DatiGenerali/DatiConvenzione)or
									(DatiGenerali/DatiRicezione)or
									(DatiGenerali/DatiFattureCollegate))">
										<div id="altriDati">
											<br/>
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="8">
														<h4>Altri Dati Generali</h4>
													</td>
												</tr>
												<tr>
													<td width="100px">
														<h6>Tipo</h6>
													</td>
													<td width="50px">
														<h6>Linea</h6>
													</td>
													<td width="100px">
														<h6>id Documento</h6>
													</td>
													<td width="100px">
														<h6>Data</h6>
													</td>
													<td width="100px">
														<h6>CUP</h6>
													</td>
													<td width="100px">
														<h6>CIG</h6>
													</td>
													<td width="100px">
														<h6>Num. Item</h6>
													</td>
													<td width="100px">
														<h6>Codice Commessa Convenzione</h6>
													</td>
												</tr>
												<xsl:for-each select="DatiGenerali/DatiOrdineAcquisto">
													<tr>
														<td align="left">Ordine d'Acquisto</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiContratto">
													<tr>
														<td align="left">Contratto</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiConvenzione">
													<tr>
														<td align="left">Convenzione</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiRicezione">
													<tr>
														<td align="left">Ricezione</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiFattureCollegate">
													<tr>
														<td align="left">Fatture collegate</td>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="Data" />
															</xsl:call-template>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumItem"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCommessaConvenzione"/>
														</td>
													</tr>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE ALTRI DATI-->

									<!--INIZIO DATI DDT-->
									<xsl:if test="DatiGenerali/DatiDDT">
										<div id="datiDDT">
											<br/>
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="8">
														<h4>Dati DDT</h4>
													</td>
												</tr>
												<tr>
													<td width="50px">
														<h6>Linea</h6>
													</td>
													<td width="100px">
														<h6>Numero</h6>
													</td>
													<td width="100px">
														<h6>Data</h6>
													</td>
												</tr>
												<xsl:for-each select="DatiGenerali/DatiDDT">
													<tr>
														<td align="left">
															<xsl:value-of select="RiferimentoNumeroLinea"/>
														</td>
														<td align="left">
															<xsl:value-of select="NumeroDDT"/>
														</td>
														<td align="left">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="DataDDT" />
															</xsl:call-template>
														</td>
													</tr>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI DDT-->


									<!--INIZIO DATI DI RIEPILOGO ALIQUOTE E NATURE-->
									<xsl:if test="DatiBeniServizi/DatiRiepilogo">

										<br/>
										<div id="riepilogo-aliquote-nature">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="8">
														<h4>Dati Beni Servizi - Dati Riepilogo</h4>
													</td>
												</tr>
												<tr>
													<td width="60px">
														<h6>Aliquota</h6>
													</td>
													<td width="60px">
														<h6>Natura</h6>
													</td>
													<td width="100px">
														<h6>Imponibile</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Imposta</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Arrotondamento</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Spese Accessorie</h6>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px">
														<h6>Esigibilità Iva</h6>
													</td>
													<td width="200px">
														<h6>Riferimento Normativo</h6>
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi/DatiRiepilogo">
													<tr>
														<td align="right">
															<xsl:value-of select="AliquotaIVA" /> %
														</td>
														<td>
															<xsl:if test="Natura">
																<xsl:variable name="NAT">
																	<xsl:value-of select="Natura" />
																</xsl:variable>
																<br/>
																<xsl:choose>
																	<xsl:when test="$NAT='N1'">
																		esclusa ex art.15
																	</xsl:when>
																	<xsl:when test="$NAT='N2'">
																		non soggetta
																	</xsl:when>
																	<xsl:when test="$NAT='N3'">
																		non imponibile
																	</xsl:when>
																	<xsl:when test="$NAT='N4'">
																		esente
																	</xsl:when>
																	<xsl:when test="$NAT='N5'">
																		regime del margine / IVA non esposta in fattura
																	</xsl:when>
																	<xsl:when test="$NAT='N6'">
																		inversione contabile
																	</xsl:when>
																	<xsl:when test="$NAT='N7'">
																		IVA assolta in altro stato UE
																	</xsl:when>
																	<xsl:otherwise>
																		!!! codice non previsto !!!
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
														<td align="right">
															<xsl:value-of select="format-number(ImponibileImporto,  '###.###.##0,00######', 'euro')" />
														</td>
														<td align="right">
															<xsl:value-of select="format-number(Imposta,  '###.###.##0,00######', 'euro')" />
														</td>
														<td align="right">
															<xsl:value-of select="Arrotondamento" />
														</td>
														<td align="right">
															<xsl:value-of select="format-number(SpeseAccessorie,  '###.###.##0,00######', 'euro')" />
														</td>
														<td>
															<xsl:if test="EsigibilitaIVA">
																<span class="label">
																	<xsl:value-of select="EsigibilitaIVA" />
																</span>
																<xsl:variable name="EI">
																	<xsl:value-of select="EsigibilitaIVA" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$EI='I'">
																	(esigibilità immediata)
																	</xsl:when>
																	<xsl:when test="$EI='D'">
																	(esigibilità differita)
																	</xsl:when>
																	<xsl:when test="$EI='S'">
																	(scissione dei pagamenti)
																	</xsl:when>
																	<xsl:otherwise>
																		<span>(!!! codice non previsto !!!)</span>
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
														<td>
															<xsl:value-of select="RiferimentoNormativo" />
														</td>
													</tr>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI RIEPILOGO ALIQUOTE E NATURE-->

								</xsl:for-each>
								<!--FINE DATI BODY-->
							</div>
						</div>
					</xsl:if>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/" mode="costamanuale">
		<html>
			<head>
				<meta http-equiv="X-UA-Compatible" content="IE=edge" />
				<style type="text/css">
				
					body, div, table { font-size: 13px;}
					
					#fattura-container { width: 100%; position: relative;}
					
					#fattura-elettronica { font-size: 11px; font-family: sans-serif; margin-left: auto; margin-right: auto; max-width: 1280px; min-width: 930px; padding: 0; }
					#fattura-elettronica .versione { font-size: 11px; float:right; color: #777777; } <!-- Versione - in alto a dx -->
					
					.titoloPrincipale  { padding: 20px  0px  0px  0px; margin: 0; font-size: 25px; font-weight: bold;                     color: #012f51; } <!-- Titolo principale -->
					.denominazione     { padding:  0px  0px  0px  0px; margin: 0;                  font-weight: bold;                     color: #012f51; } <!-- Denominazione cessionario e cedente -->
					.titoloTabelle     { padding:  2px  2px  2px  2px;            font-size: 14px; font-weight: bold; font-style: italic;                 } <!-- Titolo principale tabelle -->
					.rigaTitoliTabelle { padding:  0px  0px  0px  0px; margin: 0;                  font-weight: bold;                     color: #012f51; } <!-- Riga titoli tabelle -->

					.label { padding: 15px 0 0 0; margin: 0; font-weight: bold; }

					#cedente {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						text-align: left;
						border-radius: 5px;
						width:400px;
					}

					#cessionario {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						text-align: left;
						border-radius: 5px;
						width:400px;
					}

					.tabellaGenerica{
						width: 100%;
						border-collapse: collapse;
						page-break-inside: auto;
						empty-cells: show;
					}
					.tabellaGenerica td{
						border: 1px solid #012f51;
						padding: 2px 5px 2px 5px;
					}
					.rigaTitoli{
						border: 1px solid #012f51;
						padding: 0;
					}

					#fattura-elettronica div.page {
						background-color: #fff !important;
						position: relative;

						margin: 20px 0 50px 0;
						padding: 10px;

						border: 1px solid #012f51;
					}
					
				</style>
			</head>
			<body>
				<div id="fattura-container">
					<xsl:if test="a:FatturaElettronica">

						<!--INIZIO - Variabili Header-Globali -->
						<xsl:variable name="tipoDestinatario">
							<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/FormatoTrasmissione"/>
						</xsl:variable>
						<!--FINE - Variabili Header-Globali -->

						<div id="fattura-elettronica">

							<span class="titoloPrincipale">Fattura Elettronica vs 
								<xsl:choose>
									<xsl:when test="$tipoDestinatario='FPA12'">Pubblica Amministrazione</xsl:when>
									<xsl:when test="$tipoDestinatario='FPR12'">Privati</xsl:when>
									<xsl:otherwise>
										<span/>
									</xsl:otherwise>
								</xsl:choose>
							</span>
							<br/>
							<div class="versione">
								Versione <xsl:value-of select="a:FatturaElettronica/@versione"/>
							</div>

							<div class="page">
								<!--INIZIO DATI HEADER-->
								<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader">

									<table width="100%">
										<tr>
											<td align="left" valign="top">

												<!--INIZIO DATI CEDENTE PRESTATORE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore">
													<div id="cedente">

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">

																<span class="denominazione">
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																</span>
																<br/>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Sede">
															<span class="">Sede legale: </span>
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Sede">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(' ', CAP)" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(' ', Comune)" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat(' ', Nazione)" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														
														<br/>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<span class="">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																	<br/>
																	<span class="">Codice fiscale: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														
														<br/>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
																<xsl:if test="CapitaleSociale">
																	<br/>
																	<span class="">Capitale sociale: </span>
																	<xsl:value-of select="CapitaleSociale" />
																</xsl:if>
																<br/>
																<span class="">Reg,. Imp. Ge n. 02545900108: </span>
																<xsl:if test="((Ufficio) or (NumeroREA))">
																	<br/>
																	<span class="">REA: </span>
																	<xsl:value-of select="concat(Ufficio, ' - ', NumeroREA)" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Contatti">
															<xsl:if test="Telefono or Fax or Email">
																<xsl:if test="Telefono">
																	<br/>
																	<span class="">Tel.: </span>
																	<xsl:value-of select="Telefono" />
																</xsl:if>
															</xsl:if>
														</xsl:for-each>
														<br/>
														<xsl:value-of select="'www.costacrociere.it'" />
													</div>
												</xsl:if>
												<!--FINE DATI CEDENTE PRESTATORE-->

											</td>

											<td align="right" valign="top">

												<!--INIZIO DATI CESSIONARIO COMMITTENTE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente">
													<div id="cessionario">

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">

																<span class="denominazione">
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</span>
																<br/>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/Sede">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/Sede">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(' ', CAP)" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(' ', Comune)" />
																</xsl:if>
																<xsl:if test="Nazione">
																	<xsl:value-of select="concat(' ', Nazione)" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<br/>
														
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/CodiceFiscale">
															<br/>
															<span class="">Codice fiscale: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/CodiceFiscale" />
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA">
															<br/>
															<span class="">Partita Iva: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA/IdPaese" />
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA/IdCodice" />
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario">
															<br/>
															<span class="">Codice Destinatario: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario" />
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario">
															<br/>
															<span class="">PEC: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario" />
														</xsl:if>
														
													</div>
												</xsl:if>
												<!--FINE DATI CESSIONARIO COMMITTENTE-->

											</td>
										</tr>
									</table>


								</xsl:if>
								<!--FINE DATI HEADER-->

								<!--INIZIO DATI BODY-->
								<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaBody">

									<xsl:variable name="valutaDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
									</xsl:variable>
									<xsl:variable name="tipoDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/TipoDocumento"/>
									</xsl:variable>

									<!--INIZIO DATI GENERALI DOCUMENTO-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento">

										<xsl:variable name="valuta">
											<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
										</xsl:variable>

										<div id="dati-generali-documento">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="2">
														<span class="titoloTabelle">
															Dati 
															<xsl:call-template name="TipoDocumento">
																<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
															</xsl:call-template>
														</span>
													</td>
												</tr>
												<tr>
													<td width="300px" align="left">
														<span class="label">
															<xsl:call-template name="TipoDocumento">
																<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
															</xsl:call-template>
														</span>
														numero: 
														<span class="label">
														<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Numero" />
														</span>
													</td>
													<td width="200px">
														Data documento: 
														<span class="label">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="DatiGenerali/DatiGeneraliDocumento/Data" />
															</xsl:call-template>
														</span>
													</td>
												</tr>

												<xsl:if test="((DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento) or (DatiGenerali/DatiGeneraliDocumento/Arrotondamento))">
													<tr>
														<td width="300px" align="left">
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento">
																Importo 
																<xsl:call-template name="TipoDocumento">
																	<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
																</xsl:call-template>: 
																<span class="label">
																	<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento" />
																</span>
															</xsl:if>
														</td>
														<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/Causale">
															<xsl:if test="starts-with(current(), 'Codice Cliente: ')">
																<td align="left">
																	<xsl:value-of select="substring (current(), 1, 16) " />
																	<span class="label">
																		<xsl:value-of select="substring (current(), 16, 100) " />
																	</span>
																</td>
															</xsl:if>
														</xsl:for-each>
														
														
													</tr>
												</xsl:if>
												
												<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiRitenuta">
													<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiRitenuta">
														<tr>
															<td width="100px">
																<xsl:if test="ImportoRitenuta">
																	Importo Ritenuta: 
																	<span class="label">
																		<xsl:value-of select="ImportoRitenuta" />
																	</span>
																</xsl:if>
																                                
																<xsl:if test="AliquotaRitenuta">
																	Aliquota Ritenuta: 
																	<span class="label">
																		<xsl:value-of select="AliquotaRitenuta" />
																	</span>
																</xsl:if>
															</td><td></td>
														</tr>
													</xsl:for-each>
												</xsl:if>
												
												<tr>
													<td width="100px">
														Divisa: 
														<span class="label">
															<xsl:value-of select="$valutaDocumento" />
														</span>
													</td><td></td>
												</tr>

											</table>
										</div>
										
										</xsl:if>
									<!--FINE DATI GENERALI DOCUMENTO-->

									<!--INIZIO DATI DI DETTAGLIO DELLE LINEE-->
									<xsl:if test="DatiBeniServizi/DettaglioLinee">
										<br/>
										<div id="righe">
											<table class="tabellaGenerica">
												<tr>
													<td width="100px">
														<span class="rigaTitoliTabelle">Linea</span>
													</td>
													<td width="200px">
														<span class="rigaTitoliTabelle">Descrizione</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">Importo</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">Aliquota</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">Natura</span>
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi/DettaglioLinee">
													<tr>
														<td align="center">
															<xsl:value-of select="NumeroLinea" />
														</td>
														<td>
															<xsl:value-of select="Descrizione" />
														</td>
														<td align="center">
															<xsl:value-of select="PrezzoUnitario" />
														</td>
														<td align="center">
															<xsl:value-of select="AliquotaIVA" />
														</td>
														<td align="center">
															<xsl:if test="Natura">
																<span class="label">
																	<xsl:value-of select="Natura" />
																</span>
															</xsl:if>
														</td>
													</tr>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI DI DETTAGLIO DELLE LINEE-->

									<!--INIZIO ALTRI DATI-->
									<xsl:if test="(
									(DatiGenerali/DatiOrdineAcquisto)or
									(DatiGenerali/DatiContratto)or
									(DatiGenerali/DatiConvenzione)or
									(DatiGenerali/DatiRicezione)or
									(DatiGenerali/DatiFattureCollegate))">
										<div id="altriDati">
											<br/>
											<table class="tabellaGenerica" style="width:400px">
												<tr>
													<td width="200px">
														<span class="rigaTitoliTabelle">id Documento</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">CUP</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">CIG</span>
													</td>
												</tr>
												<xsl:for-each select="DatiGenerali/DatiOrdineAcquisto">
													<tr>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiContratto">
													<tr>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiConvenzione">
													<tr>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiRicezione">
													<tr>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
													</tr>
												</xsl:for-each>
												<xsl:for-each select="DatiGenerali/DatiFattureCollegate">
													<tr>
														<td align="left">
															<xsl:value-of select="IdDocumento"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCUP"/>
														</td>
														<td align="left">
															<xsl:value-of select="CodiceCIG"/>
														</td>
													</tr>
												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE ALTRI DATI-->

									<!--INIZIO DATI DI RIEPILOGO ALIQUOTE E NATURE-->
									<xsl:if test="DatiBeniServizi/DatiRiepilogo">
										<br/>
										<div id="riepilogo-aliquote-nature">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="8" style="border:0" align="center">
														<span class="titoloTabelle">Dati di riepilogo</span>
														<br/><br/>
													</td>
												</tr>
												<tr>
													<td width="300px">
														<span class="rigaTitoliTabelle">Riferimento Normativo</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">Natura</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">Aliquota</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">Esigibilità Iva</span>
													</td>
													<td width="200px">
														<span class="rigaTitoliTabelle">Imponibile</span>
													</td>
													<td width="200px">
														<span class="rigaTitoliTabelle">Imposta</span>
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi/DatiRiepilogo">
													<tr>
														<td>
															<xsl:value-of select="RiferimentoNormativo" />
														</td>
														<td>
															<xsl:if test="Natura">
																<xsl:variable name="NAT">
																	<xsl:value-of select="Natura" />
																</xsl:variable>
															</xsl:if>
														</td>
														<td align="center">
															<xsl:value-of select="AliquotaIVA" />
														</td>
														<td align="center">
															<xsl:if test="EsigibilitaIVA">
																<span class="label">
																	<xsl:value-of select="EsigibilitaIVA" />
																</span>
																<xsl:variable name="EI">
																	<xsl:value-of select="EsigibilitaIVA" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$EI='I'">
																	(esigibilità immediata)
																	</xsl:when>
																	<xsl:when test="$EI='D'">
																	(esigibilità differita)
																	</xsl:when>
																	<xsl:when test="$EI='S'">
																	(scissione dei pagamenti)
																	</xsl:when>
																	<xsl:otherwise>
																		<span>(!!! codice non previsto !!!)</span>
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
														<td align="center">
															<xsl:value-of select="ImponibileImporto" />
														</td>
														<td align="center">
															<xsl:value-of select="Imposta" />
														</td>
													</tr>
												</xsl:for-each>
												<tr><td style="border:0"> </td></tr>
												<tr>
													<td style="border:0"></td>
													<td style="border:0"></td>
													<td style="border:0"></td>
													<td style="border:0"></td>
													<td align="center">
														Totale 
														<xsl:call-template name="TipoDocumento">
															<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
														</xsl:call-template>
													</td>
													<td align="center">
														<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento" />
													</td>
												</tr>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI RIEPILOGO ALIQUOTE E NATURE-->

									<!--INIZIO DATI PAGAMENTO-->
									<xsl:if test="DatiPagamento">
										<div id="dati-pagamento-condizioni">
											<xsl:for-each select="DatiPagamento">
												<br/>
												<table class="tabellaGenerica">
													<tr>
														<td class="rigaTitoli" colspan="2">
															<span class="titoloTabelle">Dati Bancari</span>
														</td>
													</tr>
													<xsl:for-each select="DettaglioPagamento">
														<tr>
															<td width="200px">
																<span class="">Nome Banca</span>
															</td>
															<td width="200px">
																<xsl:value-of select="IstitutoFinanziario" />
															</td>
															<td style="border:0">
															</td>
														</tr>
														<tr>
															<td width="200px">
																<span class="">Appoggio Bancario</span>
															</td>
															<td width="200px">
																<xsl:value-of select="IBAN" />
															</td>
														</tr>
														<tr>
															<td width="200px">
																<span class="">Swift</span>
															</td>
															<td width="200px">
																<xsl:value-of select="BIC" />
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</xsl:for-each>
										</div>
									</xsl:if>
									<!--FINE DATI PAGAMENTO-->
									<table class="tabellaGenerica">
										<tr>
											<td style="border:0">
												<br/>
												<span class="label">
													Imposta di bollo assolta ai sensi del DM 17/06/2014.<br/>
Il presente documento è una copia analogica della fattura elettronica emessa secondo normativa vigente e trasmessa all'Agenzia delle Entrate che, su richiesta, mette a disposizione.
												</span>
											</td>
										</tr>
									</table>
								</xsl:for-each>
								<!--FINE DATI BODY-->
							</div>
						</div>
					</xsl:if>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/" mode="costattg">
		<html>
			<head>
				<meta http-equiv="X-UA-Compatible" content="IE=edge" />
				<style type="text/css">
				
					body, div, table { font-size: 13px; }
					
					#fattura-container { width: 100%; position: relative;}
					
					#fattura-elettronica { font-size: 11px; font-family: sans-serif; margin-left: auto; margin-right: auto; max-width: 1280px; min-width: 930px; padding: 0; }
					#fattura-elettronica .versione { font-size: 11px; float:right; color: #777777; } <!-- Versione - in alto a dx -->
					
					.titoloPrincipale  { padding: 20px  0px  0px  0px; margin: 0; font-size: 25px; font-weight: bold;                     color: #012f51; } <!-- Titolo principale -->
					.denominazione     { padding:  0px  0px  0px  0px; margin: 0;                  font-weight: bold;                     color: #012f51; } <!-- Denominazione cessionario e cedente -->
					.titoloTabelle     { padding:  2px  2px  2px  2px;            font-size: 14px; font-weight: bold; font-style: italic;                 } <!-- Titolo principale tabelle -->
					.rigaTitoliTabelle { padding:  0px  0px  0px  0px; margin: 0;                  font-weight: bold;                     color: #012f51; } <!-- Riga titoli tabelle -->

					.label { padding: 15px 0 0 0; margin: 0; font-weight: bold; }

					#cedente {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						text-align: left;
						border-radius: 5px;
						width:400px;
					}

					#cessionario {
						margin: 10;
						border: 1px solid #012f51;
						padding: 10px 10px 5px 10px;
						text-align: left;
						border-radius: 5px;
						width:400px;
					}

					.tabellaGenerica{
						width: 100%;
						border-collapse: collapse;
						page-break-inside: auto;
						empty-cells: show;
					}
					.tabellaGenerica td{
						border: 1px solid #012f51;
						padding: 2px 5px 2px 5px;
					}
					.rigaTitoli{
						border: 1px solid #012f51;
						padding: 0;
					}

					#fattura-elettronica div.page {
						background-color: #fff !important;
						position: relative;

						margin: 20px 0 50px 0;
						padding: 10px;

						border: 1px solid #012f51;
					}

				</style>
			</head>
			<body>
				<div id="fattura-container">
					<xsl:if test="a:FatturaElettronica">

						<!--INIZIO - Variabili Header-Globali -->
						<xsl:variable name="tipoDestinatario">
							<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/FormatoTrasmissione"/>
						</xsl:variable>
						<!--FINE - Variabili Header-Globali -->

						<div id="fattura-elettronica">

							<span class="titoloPrincipale">Fattura Elettronica vs 
								<xsl:choose>
									<xsl:when test="$tipoDestinatario='FPA12'">Pubblica Amministrazione</xsl:when>
									<xsl:when test="$tipoDestinatario='FPR12'">Privati</xsl:when>
									<xsl:otherwise>
										<span/>
									</xsl:otherwise>
								</xsl:choose>
							</span>
							<br/>
							<div class="versione">
								Versione <xsl:value-of select="a:FatturaElettronica/@versione"/>
							</div>

							<div class="page">
								<!--INIZIO DATI HEADER-->
								<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader">

									<table width="100%">
										<tr>
											<td align="left" valign="top">

												<!--INIZIO DATI CEDENTE PRESTATORE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore">
													<div id="cedente">

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">

																<span class="denominazione">
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</span>
															</xsl:for-each>
														</xsl:if>
														
														<br/>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Sede">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/Sede">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																 - 
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici">
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<br/>
																	<span class="label">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
																<xsl:if test="CodiceFiscale">
																	<br/>
																	<span class="label">Codice fiscale: </span>
																	<xsl:value-of select="CodiceFiscale" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<br/>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/IscrizioneREA">
																<xsl:if test="CapitaleSociale">
																	<br/>
																	Capitale sociale: 
																	<xsl:value-of select="CapitaleSociale" />
																</xsl:if>

																<xsl:if test="((Ufficio) or (NumeroREA))">
																	<br/>
																	Reg,. Imp. Ge n. 02545900108
																	<br/>
																	REA: 
																	<xsl:value-of select="NumeroREA" />
																	<br/>
																	Tel. +39 01054831
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
													</div>
												</xsl:if>
												<!--FINE DATI CEDENTE PRESTATORE-->

											</td>

											<td align="right" valign="top">

												<!--INIZIO DATI CESSIONARIO COMMITTENTE-->
												<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente">
													<div id="cessionario">

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">

																<span class="denominazione">
																	<xsl:if test="Anagrafica/Denominazione">
																		<xsl:value-of select="Anagrafica/Denominazione" />
																	</xsl:if>
																	<xsl:if test="((Anagrafica/Denominazione) and ((Anagrafica/Titolo) or (Anagrafica/Nome) or (Anagrafica/Cognome)))">
																		<br/>
																	</xsl:if>
																	<xsl:if test="Anagrafica/Titolo">
																		<xsl:value-of select="concat(Anagrafica/Titolo, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Nome">
																		<xsl:value-of select="concat(Anagrafica/Nome, ' ')" />
																	</xsl:if>
																	<xsl:if test="Anagrafica/Cognome">
																		<xsl:value-of select="Anagrafica/Cognome" />
																	</xsl:if>
																</span>
															</xsl:for-each>
														</xsl:if>
														
														<br/>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/Sede">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/Sede">
																<xsl:if test="Indirizzo">
																	<xsl:value-of select="Indirizzo" />
																</xsl:if>
																<xsl:if test="NumeroCivico">
																	<xsl:value-of select="concat(', ', NumeroCivico)" />
																</xsl:if>
																<br/>
																<xsl:if test="CAP">
																	<xsl:value-of select="concat(CAP, ' ')" />
																</xsl:if>
																<xsl:if test="Comune">
																	<xsl:value-of select="concat(Comune, ' ')" />
																</xsl:if>
																<xsl:if test="Provincia">
																	<xsl:value-of select="concat(Provincia, ' ')" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
															<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici">
																<xsl:if test="CodiceFiscale">
																	<br/>
																	<span class="">Codice fiscale: </span>
																	<xsl:value-of select="CodiceFiscale" />
																</xsl:if>
																<xsl:if test="IdFiscaleIVA">
																	<br/>
																	<span class="">Partita Iva: </span>
																	<xsl:value-of select="IdFiscaleIVA/IdPaese" />
																	<xsl:value-of select="IdFiscaleIVA/IdCodice" />
																</xsl:if>
															</xsl:for-each>
														</xsl:if>

														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario">
															<br/>
															<span class="">Codice Destinatario: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/CodiceDestinatario" />
														</xsl:if>
														<xsl:if test="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario">
															<br/>
															<span class="">Pec: </span>
															<xsl:value-of select="a:FatturaElettronica/FatturaElettronicaHeader/DatiTrasmissione/PECDestinatario" />
														</xsl:if>



													</div>
												</xsl:if>
												<!--FINE DATI CESSIONARIO COMMITTENTE-->

											</td>
										</tr>
									</table>


								</xsl:if>
								<!--FINE DATI HEADER-->

								<!--INIZIO DATI BODY-->
								<xsl:for-each select="a:FatturaElettronica/FatturaElettronicaBody">

									<xsl:variable name="valutaDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
									</xsl:variable>
									<xsl:variable name="tipoDocumento">
										<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/TipoDocumento"/>
									</xsl:variable>

									<!--INIZIO DATI GENERALI DOCUMENTO-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento">

										<xsl:variable name="valuta">
											<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa"/>
										</xsl:variable>

										<div id="dati-generali-documento">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="2">
														<span class="titoloTabelle">Dati Fattura</span>
													</td>
												</tr>
												<tr>
													<td width="300px" align="left">
															<xsl:call-template name="TipoDocumento">
																<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
															</xsl:call-template>
													numero:  
														<span class="label">
														<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Numero" />
														</span>
													</td>
													<td width="200px">
														Data documento: 
														<span class="label">
															<xsl:call-template name="FormatDateShort">
																<xsl:with-param name="DateTime" select="DatiGenerali/DatiGeneraliDocumento/Data" />
															</xsl:call-template>
															</span>
													</td>
												</tr>

												<xsl:if test="((DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento) or (DatiGenerali/DatiGeneraliDocumento/Arrotondamento))">
													<tr>
														<td width="300px" align="left">
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento">
																
																	Importo <xsl:call-template name="TipoDocumento">
																<xsl:with-param name="pTipoDocumento" select="$tipoDocumento"/>
															</xsl:call-template>: 
																
																<span class="label">
																<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento" />
																<xsl:value-of select="concat(' ',$valuta)"/>
																</span>
															</xsl:if>
														</td>
														<td> </td>
													</tr>
												</xsl:if>
												<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/Causale">
															<xsl:if test="starts-with(current(), 'Codice Agente')">
																<tr>
																	<td align="left">
																<xsl:value-of select="current()" />
																	</td><td></td>
																</tr>
															</xsl:if>
															<xsl:if test="starts-with(current(), 'Gruppo') and current()!='Gruppo: '">
																<tr>
																	<td align="left">
																<xsl:value-of select="current()" />
																	</td><td></td>
																</tr>
															</xsl:if>
															<xsl:if test="starts-with(current(), 'Rif. Pratica') and current()!='Rif. Pratica: '">
																<tr>
																	<td align="left">
																<xsl:value-of select="current()" />
																	</td><td></td>
																</tr>
															</xsl:if>
												</xsl:for-each>

											</table>
										</div>

										</xsl:if>
									<!--FINE DATI GENERALI DOCUMENTO-->

									<!--INIZIO DATI DI DETTAGLIO DELLE LINEE-->
									<xsl:if test="DatiBeniServizi/DettaglioLinee">
										<br/>
										<div id="righe">
											<table class="tabellaGenerica">
												<tr>
													<td width="200px" align="center">
														<span class="rigaTitoliTabelle">Linea</span>
													</td>
													<td width="200px" align="center">
														<span class="rigaTitoliTabelle">Passeggero</span>
													</td>
													<td width="200px" align="center">
														<span class="rigaTitoliTabelle">Descrizione</span>
													</td>
													<td width="80px" colspan="2" align="center">
														<span class="rigaTitoliTabelle">Natura</span>
													</td>
													<td width="90px" align="center">
														<span class="rigaTitoliTabelle">Importo</span>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="50px" align="center">
														<span class="rigaTitoliTabelle">Aliquota</span>
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi/DettaglioLinee">
													<tr>
														<td align="center">
															<xsl:value-of select="NumeroLinea" />
														</td>
														<td align="center">
															<xsl:for-each select="AltriDatiGestionali">
																<xsl:if test="TipoDato='PASSEGGERO'">
																	<xsl:value-of select="concat(RiferimentoTesto, ' ')"/>
																</xsl:if>
															</xsl:for-each>
														</td>
														<td align="center">
															<xsl:value-of select="Descrizione" />
														</td>
														<td align="center">
															<xsl:if test="Natura">
																<span class="">
																	<xsl:value-of select="Natura" />
																</span>
															</xsl:if>
														</td>
														<td align="center">
															<xsl:if test="Natura">
																<xsl:variable name="NAT">
																	<xsl:value-of select="Natura" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$NAT='N1'">
																		esclusa ex art.15
																	</xsl:when>
																	<xsl:when test="$NAT='N2'">
																		non soggetta
																	</xsl:when>
																	<xsl:when test="$NAT='N3'">
																		non imponibile
																	</xsl:when>
																	<xsl:when test="$NAT='N4'">
																		esente
																	</xsl:when>
																	<xsl:when test="$NAT='N5'">
																		regime del margine / IVA non esposta in fattura
																	</xsl:when>
																	<xsl:when test="$NAT='N6'">
																		inversione contabile
																	</xsl:when>
																	<xsl:when test="$NAT='N7'">
																		IVA assolta in altro stato UE
																	</xsl:when>
																	<xsl:otherwise>
																		!!! codice non previsto !!!
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
														<td align="center">
															<xsl:value-of select="PrezzoUnitario" />
														</td>
														<td align="center">
															<xsl:value-of select="AliquotaIVA" /></td>
													</tr>

												</xsl:for-each>
											</table>
										</div>
									</xsl:if>
									<!--FINE DATI DI DETTAGLIO DELLE LINEE-->


									<!--INIZIO DATI DI RIEPILOGO ALIQUOTE E NATURE-->
									<xsl:if test="DatiBeniServizi/DatiRiepilogo">

										<br/>
										<div id="riepilogo-aliquote-nature">
											<table class="tabellaGenerica">
												<tr>
													<td class="rigaTitoli" colspan="8">
														<span class="titoloTabelle">Dati Beni Servizi - Dati Riepilogo</span>
													</td>
												</tr>
												<tr>
													<td width="200px">
														<span class="rigaTitoliTabelle">Riferimento Normativo</span>
													</td>
													<td width="100px">
														<span class="rigaTitoliTabelle">Natura</span>
													</td>
													<td width="100px" align="center">
														<span class="rigaTitoliTabelle">Aliquota</span>
													</td>
													<td width="100px" align="center">
														<span class="rigaTitoliTabelle">Esigibilità Iva</span>
													</td>
													<td width="100px" align="center">
														<span class="rigaTitoliTabelle">Imponibile</span>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
													<td width="100px" align="center">
														<span class="rigaTitoliTabelle">Imposta</span>
														<xsl:value-of select="concat(' [',$valutaDocumento,']')" />
													</td>
												</tr>
												<xsl:for-each select="DatiBeniServizi/DatiRiepilogo">
													<tr>
														<td>
															<xsl:value-of select="RiferimentoNormativo" />
														</td>
														<td>
															<xsl:if test="Natura">
																<xsl:value-of select="Natura" />
															</xsl:if>
														</td>
														<td align="center">
															<xsl:value-of select="AliquotaIVA" /> %
														</td>
														<td align="center">
															<xsl:if test="EsigibilitaIVA">
																<span class="label">
																	<xsl:value-of select="EsigibilitaIVA" />
																</span>
																<xsl:variable name="EI">
																	<xsl:value-of select="EsigibilitaIVA" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$EI='I'">
																	(esigibilità immediata)
																	</xsl:when>
																	<xsl:when test="$EI='D'">
																	(esigibilità differita)
																	</xsl:when>
																	<xsl:when test="$EI='S'">
																	(scissione dei pagamenti)
																	</xsl:when>
																	<xsl:otherwise>
																		<span>(!!! codice non previsto !!!)</span>
																	</xsl:otherwise>
																</xsl:choose>
															</xsl:if>
														</td>
														<td align="center">
															<xsl:value-of select="ImponibileImporto" />
														</td>
														<td align="center">
															<xsl:value-of select="Imposta" />
														</td>
													</tr>
												</xsl:for-each>
												
											</table>
										</div>
									</xsl:if>
									<br/>
									<table class="tabellaGenerica">
										<tr>
											<td style="border:0"></td>
											<td width="100px" align="center">
												Totale fattura
											</td>
											<td width="100px" align="center">
												<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento" />
														<xsl:value-of select="concat(' ',$valutaDocumento,'')" />

											</td>
										</tr>
										<tr><td style="border:0"> </td></tr>
										<tr>
										<td colspan="3" style="border:0">
										Imposta di bollo assolta ai sensi del DM 17/06/2014.<br/>
Il presente documento è una copia analogica della fattura elettronica emessa secondo normativa vigente e trasmessa all'Agenzia delle Entrate che, su richiesta, mette a disposizione.
</td></tr>
									</table>
									<!--FINE DATI RIEPILOGO ALIQUOTE E NATURE-->

								</xsl:for-each>
								<!--FINE DATI BODY-->
							</div>
						</div>
					</xsl:if>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/" >
		<xsl:choose>
			<xsl:when test="d:FatturaElettronicaSemplificata/FatturaElettronicaHeader/DatiTrasmissione/FormatoTrasmissione='FSM10'">
				<xsl:apply-templates select="/" mode="semplificata" />
			</xsl:when>
			<xsl:when test="starts-with(a:FatturaElettronica/FatturaElettronicaBody/DatiGenerali/DatiGeneraliDocumento/Numero,'COS')">
				<xsl:apply-templates select="/" mode="costamanuale" />
			</xsl:when>
			<xsl:when test="a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/IdFiscaleIVA/IdCodice='02545900108'">
				<xsl:apply-templates select="/" mode="costattg" />
			</xsl:when>
			<xsl:when test="
			(a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/IdFiscaleIVA/IdCodice='02838760540') or
			(a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/IdFiscaleIVA/IdCodice='02508100928') or
			(a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/IdFiscaleIVA/IdCodice='02375280928') or
			(a:FatturaElettronica/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/IdFiscaleIVA/IdCodice='03430060925') or
			(a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA/IdCodice='02838760540') or
			(a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA/IdCodice='02508100928') or
			(a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA/IdCodice='02375280928') or
			(a:FatturaElettronica/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA/IdCodice='03430060925')
			">
				<xsl:apply-templates select="/" mode="tiscali" />
			</xsl:when>	
			<xsl:otherwise>
				<xsl:apply-templates select="/" mode="def" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>