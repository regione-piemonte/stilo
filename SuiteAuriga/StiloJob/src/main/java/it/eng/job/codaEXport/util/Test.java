/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TCodaXExportUtils tst = new TCodaXExportUtils();
		
		String appClob = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"            <DatiAtto xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http:/www.csi.it/atm2bu/stilo2bdc/xml/atti\"><atto>\r\n" + 
				"<numeroProvvisorio>\r\n" + 
				"<anno>2022</anno>\r\n" + 
				"<numero>29611</numero>\r\n" + 
				"<tipo>DGC</tipo>\r\n" + 
				"</numeroProvvisorio>\r\n" + 
				"<numeroDefinitivo>\r\n" + 
				"<anno>2022</anno><numero>843</numero><tipo>DGC</tipo><numeroRegistro>11</numeroRegistro>\r\n" + 
				"</numeroDefinitivo>\r\n" + 
				"<meccanografico></meccanografico>\r\n" + 
				"<settoreProponente></settoreProponente>\r\n" + 
				"<cronDet></cronDet>\r\n" + 
				"<oggetto>RICONOSCIMENTO AI SENSI DELL&apos;ARTICOLO 194 COMMA 1 LETTERA A) DEL D.LGS. 267/2000  DELLA LEGITTIMITA&apos; DI DEBITI FUORI BILANCIO DERIVANTI DAL PAGAMENTO DELLE SPESE LEGALI PER N. 4 PROVVEDIMENTI GIURISDIZIONALI ESECUTIVI SFAVOREVOLI AL COMUNE DI TORINO IN CAUSE SEGUITE DALL&apos;AVVOCATURA COMUNALE PER UN TOTALE COMPLESSIVO DI EURO 15.460,25.</oggetto>\r\n" + 
				"<statoIter>FINE</statoIter>\r\n" + 
				"<note></note>\r\n" + 
				"<dataCreazione>2022-11-22</dataCreazione>\r\n" + 
				"<dataAssegnazione>2022-11-28</dataAssegnazione>\r\n" + 
				"<dataAmmissibilita></dataAmmissibilita>\r\n" + 
				"<dataGiunta>2022-11-22</dataGiunta>\r\n" + 
				"<dataConsiglio>2022-12-12</dataConsiglio>\r\n" + 
				"<dataPresentazione>2022-11-22</dataPresentazione>\r\n" + 
				"<dataInizioPubblicazioneConsiglio></dataInizioPubblicazioneConsiglio>\r\n" + 
				"<dataFinePubblicazioneConsiglio></dataFinePubblicazioneConsiglio>\r\n" + 
				"<dataEsecutivitaConsiglio></dataEsecutivitaConsiglio>\r\n" + 
				"<esitoConsiglio>1</esitoConsiglio>\r\n" + 
				"<ie>Y</ie>\r\n" + 
				"<privacy>1</privacy>\r\n" + 
				"<filename>ATTO_DGC_2022_2961.pdf</filename>\r\n" + 
				"<uri>2139031</uri>\r\n" + 
				"<allegati>\r\n" + 
				"<allegato>\r\n" + 
				"<filename>DEL-843-2022-All_1-DEL-843&-2022-All_1-Allegato_1.pdf</filename>\r\n" + 
				"<uri>3047223</uri>\r\n" + 
				"</allegato>\r\n" + 
				"<allegato>\r\n" + 
				"<filename>DEL-843-2022-All_7-DEL-843-2022-All_7-PROP-29611-2022-All_7-Allegato_7_verbale_n(1)&_61_del_17_11.22_debiti_fuori_bilancio.pdf</filename>\r\n" + 
				"<uri>3047221</uri>\r\n" + 
				"</allegato>\r\n" + 
				"</allegati>\r\n" + 
				"<emendamenti>\r\n" + 
				"</emendamenti>\r\n" + 
				"<organi>\r\n" + 
				"</organi>\r\n" + 
				"<soggetti>\r\n" + 
				"<soggetto>\r\n" + 
				"<tipoSoggetto>P</tipoSoggetto>\r\n" + 
				"<numeroOrdine>1</numeroOrdine>\r\n" + 
				"<codiceDipendente>2551422</codiceDipendente>\r\n" + 
				"<codiceFiscale>LRSSFN75R15L219D</codiceFiscale>\r\n" + 
				"</soggetto>\r\n" + 
				"<soggetto>\r\n" + 
				"<tipoSoggetto>I</tipoSoggetto>\r\n" + 
				"<numeroOrdine>2</numeroOrdine>\r\n" + 
				"<codiceDipendente>2152738</codiceDipendente>\r\n" + 
				"<codiceFiscale>SPNDTL56C59L219H</codiceFiscale>\r\n" + 
				"</soggetto>\r\n" + 
				"</soggetti>\r\n" + 
				"<parereCircoscrizioni>N</parereCircoscrizioni>\r\n" + 
				"<circoscrizioni></circoscrizioni></atto></DatiAtto>";
		System.out.println("appClob"+appClob);
		String appClobRe = appClob.replaceAll("&", "e");
		String appClobRe1 = appClobRe.replaceAll("eapos;", "&apos;");
		System.out.println("appClob1"+appClobRe1);
		
		try {
			tst.renameMd5Decreti("C:\\Downloads\\EXPORT_CODA\\", "decreto.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
