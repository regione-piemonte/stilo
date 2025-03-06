package it.eng.client.applet.thread;

import it.eng.common.LogWriter;
import it.lisit.siss.http.HTTPUtil;
import it.lisit.siss.http.client.HTTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FirmaSISS {

	private int numeroFiles = 0;
	
	public FirmaSISS(int numeroFiles) {
		super();
		this.numeroFiles = numeroFiles;
	}

	public static String chiama(String xmlRequest) throws Exception {
	       
		HTTPClient httpClient = new HTTPClient(
				"http://127.0.0.1:8000", // url di accesso ai servizi della PDL SISS
				xmlRequest, // messaggio xml di input
				null, // host del proxy da cui passare
				null, // porta del proxy da cui passare
				null, // 'trustStore'
				null, // timeout
				HTTPUtil.ContentType.TEXT_XML, // contentType per la chiamata
				HTTPUtil.RequestMethod.POST // requestMethod per la chiamata
		); 
		String ris = httpClient.executeCall();
		return ris;
    }
	
	public boolean richiediFirmaFile(File fileDaFirmare, String idFile, boolean primoFile, boolean ultimoFile, String tipoFile ) throws Exception{
		byte[] fileContent = IOUtils.toByteArray( new FileInputStream( fileDaFirmare) );
		String fileContentString = Base64.encodeBase64String( fileContent ) ;
		//System.out.println("fileContentString " + fileContentString);
		
		int flagPrimoFile = 0;
		if( primoFile )
			flagPrimoFile = 1;
		int flagUltimoFile = 0;
		if( ultimoFile )
			flagUltimoFile = 1;
		
		String xmlRichiediFirmaDocumento = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"+
				"<SOAP-ENV:Envelope "+
				"xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" "+
				"xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" "+
				"xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\" "+
				"xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" "+
				"SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"+
				"<SOAP-ENV:Body>"+
				"<m:SA.richiediFirmaDocumento xmlns:m=\"http://www.crs.lombardia.it/schemas/CRS-SISS/SA/2009-01/richiediFirmaDocumento/\" " +
					 "dataSetVersion=\"2.0\">" +
					 "<param>" +
					 "<dati>" +
					 "<inizioLotto>" + flagPrimoFile + "</inizioLotto>" +
					 "<fineLotto>" + flagUltimoFile + "</fineLotto>" +
					 "<documento>"+
					 "<identificativoDocumento>"+idFile+"</identificativoDocumento>"+
					 "<tipoDocumento>" + tipoFile + "</tipoDocumento>"+
					 "<tipoFirma>LEGALE_OPERATORE</tipoFirma>"+
					 "<contenuto>"+ fileContentString + "</contenuto>"+
					 //"<policy>...</policy>"+
					 "</documento>"+
					 "</dati>"+
					 "</param>" +
					 "</m:SA.richiediFirmaDocumento>"+
					 "</SOAP-ENV:Body>"+
					 "</SOAP-ENV:Envelope>";
		System.out.println("xmlRichiediFirmaDocumento " + xmlRichiediFirmaDocumento);
		
		String responseRichiediFirmaDocumento = chiama(xmlRichiediFirmaDocumento);
//		String responseRichiediFirmaDocumento = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
//				"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
//				"xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" " +
//				"xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\" " +
//				"xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" " +
//				"SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
//				"<SOAP-ENV:Body>" +
//				"<m:SA.richiediFirmaDocumentoResponse xmlns:m=\"http://www.crs.lombardia.it/schemas/CRS-SISS/SA/2009-01/richiediFirmaDocumento/\">" +
//				"<param>" +
//				"<datiRisposta>" +
//				"<identificativoDocumento>001</identificativoDocumento>" +
//				"</datiRisposta>" +
//				"</param>" +
//				"</m:SA.richiediFirmaDocumentoResponse>" +
//				"</SOAP-ENV:Body>" +
//				"</SOAP-ENV:Envelope>";

		//System.out.println(responseRichiediFirmaDocumento);
		
		Document responseDoc = getDocument(responseRichiediFirmaDocumento);
		NodeList documentoList = responseDoc.getElementsByTagName("Fault");
		if( documentoList==null || documentoList.getLength()>0)
			return false;
		return true;
	}
	
	public String ottieniFileFirmato(String idFile, boolean primoFile, boolean ultimoFile, File outputFile ) throws Exception{
		
		int flagPrimoFile = 0;
		if( primoFile )
			flagPrimoFile = 1;
		int flagUltimoFile = 0;
		if( ultimoFile )
			flagUltimoFile = 1;
		
		String xmlOttieniDocumentoFirmato = 
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"+
				"<SOAP-ENV:Envelope "+
				"xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" "+
				"xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" "+
				"xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\" "+
				"xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" "+
				"SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"+
				"<SOAP-ENV:Body>"+
				"<m:SA.ottieniDocumentoFirmato xmlns:m=\"http://www.crs.lombardia.it/schemas/CRS-SISS/SA/2009-01/ottieniDocumentoFirmato/\" " +
				 "dataSetVersion=\"2.0\">" +
				 "<param>" +
				 "<dati>" +
				 "<inizioLotto>" + flagPrimoFile + "</inizioLotto>" +
				 "<fineLotto>" + flagUltimoFile + "</fineLotto>" +
				 "<documento>"+
				 "<identificativoDocumento>"+idFile+"</identificativoDocumento>"+
				 "</documento>"+
				 "</dati>"+
				 "</param>" +
				 "</m:SA.ottieniDocumentoFirmato>"+
				 "</SOAP-ENV:Body>"+
				 "</SOAP-ENV:Envelope>";
		String responseOttieniDocumentoFirmato = chiama(xmlOttieniDocumentoFirmato);
//		String responseOttieniDocumentoFirmato = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
//				"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
//				" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" " +
//				"xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\" " +
//				"xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" " +
//				"SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
//				"<SOAP-ENV:Body>" +
//				"<m:SA.ottieniDocumentoFirmatoResponse xmlns:m=\"http://www.crs.lombardia.it/schemas/CRS-SISS/SA/2009-01/ottieniDocumentoFirmato/\">" +
//				"<param>" +
//				"<datiRisposta>" +
//				"<esitoOperazione>FIRMA_OK</esitoOperazione>" +
//				"<identificativoDocumento>002</identificativoDocumento>" +
//				"<documentoFirmato>" +
//				"<tipoDocumento>PDF</tipoDocumento>" +
//				"<tipoFirma>LEGALE_OPERATORE</tipoFirma>" +
//				"<contenutoFirmato>MIAGCSqGSIb3DQEHAqCAMIACAQExDzANBglghkgBZQMEAgEFADCABgkqhkiG9w0BBwGggCSABII6PiVQREY"+
//				"tMS40CiXDpMO8w7bDnwoyIDAgb2JqCjw8L0xlbmd0aCAzIDAgUi9GaWx0ZXIvRmxhdGVEZWNvZGU+Pgp"+
//"zdHJlYW0KeJwlirEKAjEQRPv9iq2FxNmYzeUgLHighd1BwELs1OsEr/H33UMGhpl5gyj8pQ+D4UlHjYl"+
//"rllh5fdJ1x+8/c60LTZ20OBqG7Of+4P1ZWBL3161BrNSGZNJwsJAasrkpigWfhq1UjBa04bidFJPd+4V"+
//"OnWaa+QfJlh0zCmVuZHN0cmVhbQplbmRvYmoKCjMgMCBvYmoKMTIzCmVuZG9iagoKNSAwIG9iago8PC9"+
//"MZW5ndGggNiAwIFIvRmlsdGVyL0ZsYXRlRGVjb2RlL0xlbmd0aDEgMjQ5NjA+PgpzdHJlYW0KeJztfHt"+
//"8G8W18Mzu6i1ZsvyS/NLq5Zfktx1Hjojl+JE4tmOTOImdYmzZkm0ltqVIcoIpIaa8GgeIb6FASiGhNEA"+
//"b2igOUCeUJvDRB4U2oVBaelsSbtMWWtKmbaC3F2J9Z2bXjh1Sbnu/74/v9/uy6zNz5syZmTPnnDkz65U"+
//"UDY/5kRpNIBZ5+ke8IYsmVY8QehUhrO/fFuWf+Gk7lPEZhGQvDYQGR+7+yo5fIqT4KkKSyODw+ICr9Pf"+
//"XIKQ1IJT0xyG/18dXebQIWeXQx5IhIKydHZdB2QNl29BI9IYESUY+lH1QLhkO9nvfyH4tE8r3QTl9xHt"+
//"D6DFdiQTKMSjzo94R/2d+qH0DyqcQKhoLBSNRH7LFEVq7itSHwv7Q33IHPoDykCADwnCTSw2olJQZlpN"+
//"IZXKFUoX+v7wk9wC0IBNAJnsfykAo/g7AWYB3Z1fHP5ZsQdbZzfEzbBIwf0MEhOzofrQP2dB5XIpeRCf"+
//"QavQ4qkXt6D60Ep1Eh1ACGsevIA5ZUT16EtmxCTGoEaVhCdqL3kLXoTD6DTqD8lAzehvroZ8GFEKpyBV"+
//"/D9Jm9Pn4UeBSojr0TXQMD+N1qBjwVYwTO2DkPfETKA3lxX8U/zmUHka/wbb4YbQKsN+iRJSLdqJ/Q3q"+
//"0Gf0w/jFIakN96Al8E34PmVEv2s1VcJPxLWgZegb9FDcD1orGJT9XPIOGodVjOA2fiJ+O/w59h8PIDz1"+
//"9Dn0eJJ5GJ5gitk6yH/EoB12D1iAv1H4WvYWTcCnriefGV8T3AvUJ9BfGwXyPlYEcDtSEetDd6FHQxpv"+
//"oLPoAq3AlfhgfhPs1/EfJz0G2ZjSGboS19TBo7wn0FDqKS3Epk8akgbbSUD5aD3V70AEY/wg6hZtxFz6"+
//"BX2APSEpma+LJ8ZT47+JxVIA6QcJ96AUY4wIuAR4YgbWwUS6bi0rKLt4CM/ShL6NT6DWQ423Q+wfoP3E"+
//"B3O8wNzM74xvjT8Z/A7LIkQktRdeiTSiItqHt6Ctg1RfRS+jP+CNGAZwnue9KbpScj38BdJuDVoDsbcC"+
//"9DvreDVaaRjNwvwmzTMQ8zGIpXoPX4kG8B9+PZ/Bb+C1GypiZrczv2Rj7CvtLbolEEq+GnlJRNoxrRRv"+
//"REFjgZtD2F2C+T6LvopdxCs7BhTCjN6H9h8wyph7ux5iTzNvs7ewe7mPJHbNnZv8w+1F8EsnAy1aCHsb"+
//"Q10ELf8KpIEM+3owj+Ncg+RTzNJvA6lgrW8nWsh1sF/t59j72B+yPuTB3kPuFpEnilRyUeWdHZ1+LN8d"+
//"vQyRKSEGuXOREFagK/GcAvGkLyBeCO4xuQregSXQP+MsX0H50EOZ9HL2Mfop+hd4HCyBsBpkDMPoIeN3"+
//"t+B649+Kn8Av4u/hl/A7+kNyMBe48ZglTw9Qxjcwgczvc9zGnmDeZd9lMtp/dyU7A/Qj7LPsWhziOi0v"+
//"K4F4l2S15QvqKLE+2StYnf/XjcxcLLnZdfHsWzabPfmb2/tkXZn8X3xAfB/ntqBAVgaR3gpR7wQcPwP1"+
//"18MRn0fcgdv+MyvoXzGAJeLwBW8EbnGC1GrwSN8Hdiq+Fez3cG/EmuL24Dw/BvRNP4M/hW/Ft+G78RXo"+
//"/CHM7gL+Gn4X7W/gY3D/Fp/Fv8e/xXxhwYoYFb7YzuUwx44KZ1jErmTZmLdyDTBDuEBNmtoGFnmCOMEe"+
//"ZN9kk1s4Wsl52K7uX/Sb7IvsG+3eO4ZxcMefmNnCD3K3cSe417ufcRxKTpEEyJHlE8qI0Q1ohXS/dLH1"+
//"Qekj6rvRjmVTWLuuT3SR7QxaX2yFafR/m/cyikFcsPYkjkmTuBuY0rAsDG5LcideDxqRMBzvM3sP+RDK"+
//"Az7M8/gWeZAPslvhjbCPzn2wQb2COYwtrklSzA+guFMcHmXeYC8zvuBTcwbyH87h/w99igmwdI6Vx9XU"+
//"uhbtV8i5CzM9QNbMDn2C+y97K3hp/HlVLHsGnJY8wryGeO8MkodOwqu9kHoBGP2YCzG7UyVVIPkIB0Pv"+
//"XJDeAvpczn8cF7BvcI+g3rJX5Kz6P74eo8SO8mrMx1zMufBAi7kWcjc7hrSiEv4g8+Dn8KzyDMH6SfQK"+
//"3MGqwVozR4CrY+n7EmvEbrBJ1ERlxDpOC25nzzHr229JTbCXGECV+gm7ELC4B35m7ZtEorID7mFyIaQ0"+
//"QTV7HZciAHoB4f2H22yRiS34u2Q1+9ijrRGtRCepmXkHVsDZ+A3cnugOVoWPgg59HJcyD6Kb4BPZB3G+"+
//"F+MmgGbwZFWMVRMs0kG0n7BepjAViYQ+M+p8Q/38IUb8Z/xFtxzysrBMojyM1d3ENEJl6If7uhtuHuqH"+
//"0ZfQF6TOS11EbTkOI42cfAS//Jboe9pxfw/jpyA3ybUKPck6QmofIvBVafHl2FfLAfQd6BTNoB8i8HNZ"+
//"5O7cKIu/98c0wwwDsUS2wJ76MAvEHUB3Ybm381vhu1BN/NH4dGkTr4k9C/N0Wn0ZL0J2SLmaDxMFVQIx"+
//"9Gb8E+9G/490Qt1ehX0A8smMD+j3c3wSJlkueQ5PczyB21sTviv8UpYA+LKChPthFz6IR9EfQ2yr2BCq"+
//"fXcMcjjeyIdihTqNr40/ETViJhuLDEHm/jQ7IJBB7JlC25IDH46lZfo17WbVradWSyorystKS4qJCp6M"+
//"gPy83x26zWsy8KTsrMyPdaEhLTU7SJ+q0CRq1SqmQy6QSjmUwcjZYG3v5WE5vjMuxrlpVSMpWLxC8Cwi"+
//"9MR5IjYt5YnwvZeMXc3qAc+AyTo/A6ZnnxDrejdyFTr7Bysd+VG/lZ/CmazsBv7ve2sXHzlG8leJTFNc"+
//"AbjZDA77BMFTPx3Av3xBr3DY02dBbD90dVinrrHV+ZaETHVaqAFUBFkuzhg7jtOWYIkxaQ/VhBsk1IFQ"+
//"s3VrfEDNa64kEMdbe4PXF2q/tbKjPMJu7Cp0xXNdv7Ysh64qY1kFZUB0dJiati8noMHyAzAbt5g87T0z"+
//"eNaNDfb0Otc/q817XGWO9XWSMRAeMWx9Lu/Gs4VIROtfXdd65sDaDnWwwBHhSnJy8k4/tv7ZzYa2ZpF1"+
//"d0EeMsTf2TjbCwHeBCpvX8TAWc3tXZwzfDgPyZB5kTsLs/NYGQundzMcU1hXWocnNvWCY9MkYWjtunk5"+
//"P9xyNn0HpDfxkR6fVHKvJsHZ56zMPJ6PJteNHjB7euLim0HlYlyio9XCCVkTUmoWIf76OYpSdYM1r5/W"+
//"KiUTWJnCHGN/PgySdVpjTUpL4l6LJ/qXABlcXhlYxH9gjEFPU9U7qqoGuI+1jErvOyk9+gMD+1nPvL6Z"+
//"4RYrUrvsAEZR4ybyjQf0cHnM4YgUFxEFkdWBRkHE5LVcWOrfNMDFrSMdDBupD7aBbb1d1MSjfbCbm3T3"+
//"jQX1QiE1c2ymUedSXMY08xY6uGNNLak7M1aSsJzUTczXzzXut4MdP06eRlJg8Z/5Pq0tNahiqjuHUT6n"+
//"2C/XN66zN127q5Bsme0XdNncsKgn1S+frRAwLFaDwGGcHTTVZwfXWbuokBPiT2ButDYHeVbDUQMZYUl0"+
//"nm8F0CRiTwdKuwH+vm++ZFDrVpC/OLqX+75uRycGBKQXzjTFd7yoh7VKazf9ko5n4edKKZpeaiXOKVTs"+
//"Wl5ctKi8STz3JgsBcDtPcsWlyUrmorhGC1eRko5VvnOyd9M7EJ/qsvM46eZTtZDsnQw29c+afiR/bnRF"+
//"rvKsLJjGEqwthWye2kcANT8Yy1HqYwc8x34Fzo4w5Po0k3AzznadZpJQR5BmMjHKp5DjUM4jF+UiBt+D"+
//"rkcGh+9B90b1Gd8HdetGNagDXfQxJaYk50ZxohwTDjvgxz5742CNBH8Fp4QQ4xHLY32LkiQ297KmGwxm"+
//"zKWtT9ha8hdmStSVbXmyuMbeZH5Q8kPGk5PEMGYOzslNNGTqzRWHK0JqtMoMVmRidVm6eYU54khTwPOV"+
//"JS6jRa6G7djgecmiGyfuWXGFJSzU5smfiJzwJpBpl67J7svdnc9nHmDzYo08c4X3dRPwL3XWdR1F2/MS"+
//"0qpKwT6u0FQ5Hl+OsDuZBKj0K5FFVAszRfwvzJDN1OLDuZd3LpSW4G3XjJGtObk6O1ZqYnJZaXp5iriw"+
//"vg50s0WqRSaUyaxL3qDZHlWQa7DiekdNWfPGFkg221Md68ipWy3J0kpbZFzts1VUfXdhhKrDbK/gwp05"+
//"IGr4Og6IY1BI/y65jYygZZTF1HqO+x9Bt7EW9yW+yEiOf6UoDSPVkukxkqsq61RVyU52mewkpHsnLq6D"+
//"kzxQUVWRIjYrOpOtTe9I2GT6TLsOsQipTyNWSlCbpLuYu6Z3qSd3tWY8xBw3PJL3BvKX9he4C81c2SQ+"+
//"OIdfJdPJeWa88JJuQ7VK8IPuB9rxMzWGZ5jaGVRyD8CuNn/HULVE0MisVbaYOpkPRB6fiXUm7jHuTvqr"+
//"4qnJG/owipvw+8zvmjPqCMll+SoaR7JSM4WVTsv2ymIyT7eCSUUlqCpE1Se/S96TsTNmXcjqFS0nJeJ3"+
//"D3Ez81HSyC7J3p5NI9nPPKr2LK1WprsvAGfZEmexVeWpehkubioOpO1P3pLKpF5KTJ+S4RD4lZ0rke+S"+
//"n5axO7pHDFOQx+Rm5VP71hBQO7SLnN9bp0ZckeBLaE1iUoEvgE9jzCTiBSKIAZSbUZdc1Gxzg4I6t4XD"+
//"rxa1u3cXurd2Qneve6tCdQzXnwjXnHA5HONFVDM4yHUzB3V0Osg4udG8Nu6hzLF2Ktnbjus6npQgzzNa"+
//"urWGHcKEwBJCjSAaDqawutafQpQGQEy/Mc8mETEqyDKGUIdSJJaVQUgolBS15EhSuFJ3RZeQTXRoABE7"+
//"qQI4FV1eSFBy0bElVmlRqtTCVFfrystQUu5l6r0X6C+zz3bnp9kJTyg8fPPCHPz/7pe9dvBM/KdEZ+5e"+
//"su5VZ9mo02n9D8q53MH7rD1j2yterO21LPbfAmvbFzzI/ZQ+hUlbhyVc4jU5Gry/yqFzOPJXLkNyl3pT"+
//"zkO4+m0QpU+Yp83vLQ+UT5VJt+QzmPXeCt76ieSXhJdtL9p9Z37S95fwt91vrb23vOVX6Gme3c7Rwh3M"+
//"P3sPsYSdSJtInMiYydxXuKdJosZZRsgq1NFPp/IHlZas8k01N1memZhnzM5x7FXuVD/H3Wu+1qfQOTZ5"+
//"ztbOtvKf8hvwbnHckPGk9VP4u+9tMdb68NBs9z2RjEy6G58gZ7JhGzxfN4HRPYoEh2/h8Rna6KR3r0vl"+
//"0Jp1UGp9PJZUWvd5m1ag4bS7NJNn4+6iouKAUQqm9QJZ+s9FomGEbPcmpxdl2vYp5VY+x/qT5tPlPZtY"+
//"8wyZ7VCEt7tWGtFNaVjuDl3iMuenGIpMcy537cnFvbih3Ipflc0tymdxj8MxQhvnDggOCO7WeC1+gDni"+
//"RuFrcDK7mKgYXmo5jQIkznoX6czXn3Dq37qzuXKI+zYVJAlFMabNabRpVskajujOhyJGwQ/dSlwHp3r9"+
//"wrjuMdecunBNwipaW1I17MvPyTbwuUSozJZozsTRfnol4XXYmkuVJMjH1K8ctcGEaIj+Sfaj7MPGjPK6"+
//"7C4fh0YQQjfvwPmYfu0/1Jc1UylT6VMZU5l7LA9Z9herurm4HPOnBMgA2VbG12Lbb+ZDtIaekuwtm40n"+
//"M440uRZ7RhT1KFwOQIfh5OnFwo9JVBCQnBYVLrcvW1yTwJIFtFpYGzYwumxAsrEKmhuzZJJfTkCT0pRf"+
//"60uphCD0MoXc5eT1pc96j1QKb1sXqNDCOhnRw3qPXwDga4AEwJFJYtKY+cYFuumBPSLTSNZUCW0JaWmp"+
//"KMiy4XCDIrInlqWQNVlbk2HJzKiuWlJcJa5KZMudsv65xA2/q+cIrz491DJtT0jRmc+YjfQ0bvbNvFxY"+
//"+9NklreWJOr2aPTT7g3s3ry5cmpdftLL/Kzv2ZivT8cq77rnW1XD9VLVr49YH07QJBvJ/oOT4nxk39wI"+
//"8A755FGni73pq1a4e3MMwNVl7E/caj6ccT50xvmuU7cvCu9Jxm7pN06Pu0XxgkEgNKYZcA5uaYjCms5g"+
//"kyRn7MZtSws3gDE8mZksYBkvVlXKnVpV6EsL1n1LYFH9yxqtINYPf9zh5NVYXFWfFspgseJDnOIktuT0"+
//"JTyRhlKRLiiWdSDqVdCZJmtSbeXAX2YjBv0nYJHf3he5zunPg8nCkuHi2Blxadw6qzuLENBcC0ENcJSF"+
//"1a7gbnAgnlqfAtksVWE6CWk4O7LuVS5ZULanCq998szzPvDwx1zpRX9RZ8G9VkcK0fO6F2dcbL36za3l"+
//"+Xl9/eU8/M2RODazK8YO2zHA2If9JLsTocF7xDM72VNl9SxScQhkrZh90HHN8z/EW+7rjPe495UfcR0p"+
//"FSBKS7pTtlE9IJqR7ZHvkcplSUcDIzGr1DM7xaOQZsixTRprZIjUzDKHkSzKkCaaMVLM125SRY7Y6nHl"+
//"KuZqTgCqtao0mrRBZc1CeLo/Jm2Fe99hzc3OY1DR5riPvKZSPUX5Jvic/lM/lT0mlJhluk+HjMiybwc9"+
//"4lCjBkp31SJG3nwYLchwjEcNN9HrxLD2x6P7Yrbvo1ruKQc2Jehf8QWxwkVPMufeR7uJcLqgWdjfQK9F"+
//"oorWIEY41iw42FbmwY1itleYkUo8f+9v6No3djnMb6v+mUfLOktKLx0o6cgwapclRWsL+WWNNb/BvljA"+
//"X/9AcnK1sW22f3TBoNuoNdnspfyM7LOCzb/Z05ZEzjxsSmeQepEIW9O8e45QN99pCtinbftt5m4S3tds"+
//"YD0ls5JBTVlZB86XVQl5YIuRWO809Rcb0CkN+dtJqiyY/W7/aas411vLZ5nq1UZ00JcVSF0IWtSxJr5x"+
//"SYIWLJeGhrpKl4aGmkt2iVmuMGpvB43AZCC19SXXFlAG3G3CvIWSYMuw3nDdIDNPW6cdEH3Y4aPS9AHm"+
//"Yqh1OCKBm3QfnPsYfOEAXWIgQcIrAYVwmRAUSA5LAhVOJfsuWCOolkSLXjvMLli0rKHAvu9lYWjtbV1e"+
//"UoZBlp2fmJeBkyT2kwl1QsGzWfJHf4Mq02dLd67H3i07eqLWFwJs1oMdvwa6ch77qCfIMry1jyrQexqP"+
//"9HCfzFOCeAmzKzzbkWhLzs9PusObm8rU52bn1SKkqSEzmdZgzTBCd6GAhd7HwjGBIU/ZIsQeUVmQqwAU"+
//"o0WYymXg8wU/xDGwOfIw/wZ/iJXxv/uOjc3uWuxWcThc+u1XQhu5c+Fx3It2WXC60QCth2DRwypLyOY2"+
//"Qg7OMeJ2gE1jLRCtzSmmJjFetqrBZN6boUwpLkjQrls86Gi1GpQT8zJSrxCnsoR//uM6Zu6QhOf/62aa"+
//"W3AybzZaqsya24/7912QK2klESFoC2tmI6z2ZFbJTXX9KZSe6cGJXLxxJHBo81YV5OQ8KmmE+ftpSlZ9"+
//"dCohHZWnJz165mupshk142urIzy6ZYTVPW2vzsxsB8Sy3rs9tre3IXl8vz69q9bjy8+RIZl+5YaPM7ZT"+
//"YnWqlSiblJLKVjaUloNGutLR0XaLNXMLjEGiQ4WdwpUdblV/ksC0tqcKhqlgVU0Voqa0ba20tLabW9lZ"+
//"monWqlUGtulamFbzy2eTUitbezq4ZZtMR8+M7DTPYd7vDseaCY17/F0hwPStk7jUN/nryEEOuGvrXCgG"+
//"2xi0eF1xItAq56FEg2WJTazV2a45NDUeBBK0lwZ6JIV7AIxCcAWDDJ9t5NwYTEfulCWlqyrz1yufMR3Y"+
//"/qUyWdsnV58nU2ldaAOW43acvHCrfcFPK4D3NTVvNqRrlkmtm3UnLzGlKLiN3Q+WWFoZJqW6cLW1xqSR"+
//"mZ9uSynWFxtLm2WU1Zel0seRqcbKDed+nzSnw9dzQ3Ly++qbZbRv4VJPNlkZ9YjJU5KlcpXLMNl9fBES"+
//"bLXEt0Eo9Wc6q2ZRNS8B5Mpatx9c/4DTThQURqil+jt0FvlOGrmHWe4KqzMlyRr9uCdbzJtdEzZOKZ5W"+
//"s3qHfgXaU34F2q3ZXSrP0qdW6mokaTpHZImmRNvANlpZqT82uLLkyQcYjSxNuVjapmiqbq+qqm67ZqBp"+
//"U3a64TXmbStuRemsqY6rpqWF65eWowl2UX1jxHM5AaqQGy8MJCE7Yahqaqit16nY144GkV83yNNum5tR"+
//"uA3lkyle52gw9hqCBLTbsNDCGm006rLNny0rcHjfjdnKhwolCprAy31lCjq+JnKroRCEu7LWjco1aXVF"+
//"R/hweRDZkh4GSE1zIbrJP2KfsnMd+3s5M2LH9OaYOnhVTIHqaXCkzeNCTnVHsKpV5Ely8rB0eHFmdDJ+"+
//"X4XbYr+qW182Hh7CjFQ6cDnKoDTvm9n+ya8HWf+Hi2W7dua0QQKHWkeii0dVRfE7YvFzUNVdWLsu0SpK"+
//"qli5ZykgVcqWckZotvIWRVqpcPErMSspE+iStSZOJLdZlElcmWiqv4HFlhUqfqQNXtkBSLXVnkuMcjAs"+
//"HN0iIXxcUFJDDbRiOqVvhnEEOtNM1ejhl427hse3pUpha0Uz8zLSOZs8muKp4mCw5cKpJdsajgkcfXgV"+
//"P6CpXJjlGpqtcSjBWVR7JlZArIVdArvjEcRJOj3apTNgZYFVVwUmRLJGUtLndgpwY6RIjB0lYXSmEngu"+
//"ndSkcN4HErLzbtuSans9m57/y/sZ1NfYcpjjHXhzbd+OaZZl6ZZpWp05xhwZKq/EDzrb6DUtbbhtJNH5"+
//"uc11p/Q0bbLsGLBZndVFZReGGqXzTCsftsy/fuixZpnEvvb/+XtztNjp7Xat6yNnSiRB7UDKEsnGj53M"+
//"yA8zVkHlNhcEDiZEk2uzU1HyZW9Yk+5pM6uE/w22SfyZtk2GLPJoY1X9Z9XDC3sSnVE8lvCx5Oe0Hhrf"+
//"S3jKc4f/O/T0tJQVncUZJRoox1ZiWZZAp0lQGVVaFcaVxV9oeXmYwMkxaulFtlGpYIwPnVHK2liVxmhk"+
//"85FEoPMnqGrJvzbDlHrVOkr7HiPcZDxkZ4zG2HLH47iOYUWfP4Ls9GiT9j7aknqRg0s4kLmkGyzxJHph"+
//"UOuI9/ATP9vL7IRobn8N/RyzSYI8nuYcJMjuZPcxx5iRzmvkTI2eMpmP4HoyR4M6tZ93n1ui6t37Y3Up"+
//"PsBBjz4HrumsubnWcJdFVPHO5GF2C+06dZMdLCS/B1keOsd3kWIAcmDVXIkStLZVZlwjbIYRGOFFCVKx"+
//"iD/Z8fAZ7Mf/IqG9fjt148qEDvypZ/fjfl+O+4Y2N6Vgy+5Edr8APfu2Wx8e2Hv3eG1ODg195Zvb8Ul1"+
//"pIX0nDJHrtfcffe+VIz1a9wdyo5y+HPvKr7NevPRqMX5WWkI+UYEU4udMaDuZebYBbZxnwmjxpZa6cCb"+
//"3a7ScvRu1sFnIx7hQMgMVXASZAXczX0caOGklQn0Th4jfoGXoJ4yd/Qu3i9slaZGch1P0wwpe8TexdzX"+
//"qRAztm0E6VIxq4fH6R1oDWIJQV7KbEPmvpfBuD4k4Rtm0xNJWCThTxFkUhsOKgHPgq18WcQky4GMiLkU"+
//"W/BMRl6Gf4wsiLkc5zKsirkB3MH8RcaVkA3uDiKtQWP5jEVejAYVHxDXSpxWPi3gCuk63aV5vO3XPijh"+
//"G2sRKEWeQLLFexFnkSmwWcQ54bhNxCVIn3iviUpSYuE/EZWg4MSbicpSkzxRxBarTF4u4kjmoD4u4Crl"+
//"SsuY/SVSeskHENeymlF0inoCKDL8GSTBHtK42JlJcQixizKK4lNILKS6jdBfF5RRvoriC2MjYJeJgo/S"+
//"NIg42Sh8TcbBR+q0iDjZK/0DEwUYZSSIONspwiDjYKKNVxMFGmXYRBxtlNos42CjzNREHG1lyRRxsZNk"+
//"r4mAjS1zEwUb5RyiuJPMq0FJcReZSkEFxNaULMiRQvIriOjKXgjqKJwGuL7iW4smUp5/iKbSfIMVTKX0"+
//"nxY207W6KZ1AeQbYsyvM1ipso/gzFbZT/BYoXUPwkxQsp/iuCywX5/0BxYay/EVxN6Q6W4nQuDjpHLfE"+
//"f5MhAHWgchZAfDSAv6oecR18D6EBDFG9FQTQKEBW5eFQHpTDgJPUCPUA5eKAMQ/siwOop3ft/2FPxvGQ"+
//"8Wgc1w2hsnidC34aPiuOVIhfcJahQxMootRZaDEO+FtoMggxR2mot9BcBCKNtkPpgjAAaoTQerYF8O+U"+
//"JAs0L/RPuQRh3GErhT8yg+r9pzV/WvhptoCNH5mdKJF0KKU8/WxCA+YShJgIwAKPk/zf9/6PeLrUS2lx"+
//"q0Q6abIX6T+/3m9RqxCY+qBuhsm8BGpHqf25PHqhEGwEYNUolJ/rnoUx4omKv60FCHuQk7cmn1sh4rZC"+
//"2wdgD1K5EQtLOD71GqOxDYm9FV5BJ8KEgjEtkCgHv+D/k8lPfJXzbqVSD8+MGxJVRSH0xSmUYBsq4qIc"+
//"wnRXp1QmUDZQ/Suk8aqH6I5ocpXMiPlpOrTREWwl6mdOyF/XRnvl56S6tSyJHmGqPp3Mhtd7L9DjX+1x"+
//"5zloLLS7YsYXK6xNtNEo1GYE+vbTfMJ3JgDiH7VTWfkhJv1FK8dK+fLRPssJGqRzEQmRtEp4hkScCK6C"+
//"P2morYIIehqnu+qDUT/3OT+UaFfOBBR6xncowDH2Tvkbo+oiKvfZTzUTgHqCrjF9g036qGe+CmCHINqc"+
//"RwWqDVE9e2ta3yPYROrbgWTy1j49iY1RrfqqXT/eFXFFDAdpH/4IV0Ue5P91PhBXwSfst1LCgo1FR0tF"+
//"5GokiYzTq8WIk8qMb6KobpdbaRvsMiOtQ0JFAC9G2c1oVvGgbjb7b5tcE0XVYHDs8b6Et8z53+foS9PD"+
//"PrTFhdiuo5wh+HZyXX/BLQQ+jYjxfrHHB53zU+oJ3j1ENCz2N0bkLY7bTvkiPUaB7F8SVdhqtR6lOhPU"+
//"cWOTNQowcp5IN0xYROtNh0euGqB294rhhMd6R2UWo5ccWrR8iLVlxczISb+CpVwr2IPPup7FueN7Cw2I"+
//"c7QMYptKNizMeo7FW6Gk7rRmivQXhFmJmv2ibEWgj6Hoj8PnoCOOijhbGkz7adosoq6AhooFBgBspD/G"+
//"UhbGC+LqwB0TFmuCiGOqj/jW2yIpzPXtpTA8u6M1H9ReiNhlfxOmjGgpT3c7ZtYju81Hgr4bzQzHogNx"+
//"FNGos9MgiMeoUU/4R6L0Y0iiNBEQuUoqgHtq3sOqE+Bie3yOL5lv+3x1xO7XEXEy8NMoaWCUdsOobAer"+
//"gbEPwNqCS1dNIowehNwBlHaTk9LMSdvQG+slHQu1AGqSkcGnf+eQOM0cfWhALQqKWx+cj8z+3y16yVUC"+
//"0suBbc9FvnPrr3Jj99PPbl04FC6PsnDzCehpZsId56WoQPGtU7N1LpfDTPVXwMOLnXeJoZHVuE+N/H43"+
//"eAXHnEsb5R5qZO5NtF3dcspYCC2LgwigvrKQB0VuupK+gOC+iMf+iSDq3Zj85nk+MJGG68sfmI0afaJm"+
//"Fe+eVI/BiTQl7ySe94pMjB8Q1yoPmvPQcfumU4qX7hJ/GpSuPTbS/XtwjhT1l/BO2EOy0+EwoREIvlSh"+
//"ENRsQo8g/Y3Ne9MW5OD64YFwSO3xU08J+LOz+4QXPCc557vACv710Lvl0TQ3TqBG4LKZf6m9uv4xQ/7t"+
//"0KpiLeZc4g8ArnKDHqMZJ/0Pz8xHkWujdI2KUFPQvrKqQ6B+XouliH/q0GV3yjyY6909abm4vFE52kQW"+
//"zEXaafmrV0ctsEL5M35d6JvML0rOcT9xLyLlDeEKZiwP/jPXn+hPWpF/cTxfvi3P9fdKOgraEGUTFvfx"+
//"K63jOYt7LdD3wL0l7ScufHKFfPL/1iaWFEvnFnTAKe89cD+T5iXxWnDyp5MHTIPkmSD7gVfBksBSoJUA"+
//"pgZv812Q9ahY5S6C2FGoqRLwKniGqaKslqBKeKAiQ3v+1ve5/vjPO1RVfpr35/bBjPOQf8Pb7+a/xHUN"+
//"+vjU4GowCia8LhkPBsDcaCI7yoeH+Ir7eG/X+N0zFpDN+XXB4jFAifNMotCt1uUoKISkr4muHh/m1gcG"+
//"haIRf64/4w9v8vo7AiD/Cr/Fv59cGR7yja/2DY8Pe8NwA1ZdV82J99QZ/OEIGLStaWsbntQb6w8FIcCC"+
//"afxn/QjZaBTW0on1da8dlvE/yHWGvzz/iDW/hgwOfOk8+7B8MRKL+sN/HB0b5KLCuX8e3e6N8Dt/Ryrc"+
//"NDBTx3lEf7x+O+LcPAVvRfE+goeBg2BsaGl9I8vP1Ye/2wOggaRsAYxTy66Le0WH/OMgQDkSCo05+Q6A"+
//"/GgzzLd6wzz8aBbWWl3UMBSIgCxHZ2zfs56NzthwIhCNR3hsK+b2ijISd5GRawsRhji3BUR/MaNS/PRL"+
//"yhvxhJz8AI2wfCvQP8YEov90b4X3+SGBw1O8r4vmmKD8ElMhYX8S/dQxkGB7n+/z9wRE/Hxz1k/6IIrY"+
//"Hw8O+CD8SBAEiY/39/khkYGyYisb3h/1UhxHojQgCUxsMjHqHeZ8w+wi/HZTFj4AZ+LFRnz98uRZyQaB"+
//"A2N9PDdE3frlOwADz8xMEBolGodNRgoWDY4NDYBfef0PUPxoJbPPDJP3EqoCFwkEiKqhoW3B4G7HEwFg"+
//"YWofJhLYQzc3ZC2S4gsVguBXeCOg6SPoHXYIMo+DnouCgOR/fD+oe648C01iEtGz3h0P+6JiX+kr7sHc"+
//"0GgA7BwQ1g0eO88FhHx+JjoNp+4e8YS+0hd6igf4I3zcm2Mfr84ZIj9EgP0jm4b+h3z88TCY8DD7aFxg"+
//"ORMdh4LHQMDBtD0SH+MFgEDwTZAmOjIPUGwM+PxhyLCL4SV8wuCVCBRrxDnpvDIz6I4JXhP2wAqJQCAo"+
//"e6gv2jwlTJMze4UiQsvkCkdCwd1wg+rb5w9EAmWvRUDQaqi4u3r59e9GIqMgicJ3ioejIcPFIlHyTt3g"+
//"k0hMlpgN/DJMVWUQq/8mG2/3DxBNpkzVtHU2NTXW1HU1ta/i2Rr6lqa5hzboGvnbl2oaG1oY1HRqlRkn"+
//"XzvyCIfgQ9QIwHWgMnPkKS5bOKgBTBm0R9xsPjpGW/cFtNBQILkv6ATuN0BXm5YdBWaPA7h0M+/1EYUV"+
//"8FzQb8oKxgn1RL2gYrLdIGBLJtsPC5f0B6oGCy4ORBkAtl+QCbUeDg37BSYll59uBEaLhALgIdA1iiqt"+
//"zgQOLQsEqmVfFfGPAvfw27/AYDSneSMQfXdi6iF8PKxJWyvjcLGBOYiQEJ/TykZC/PwAu8smZ86BF4uO"+
//"DtK3X5wuQdQzLP0z3BCchh6luaSy5TKjhwEhA9HTKR9ZlJCrEZOJ5lBjcDgF6rG84EBki40BfgrpHwCV"+
//"BfjBVaJwX3FTU0OKBqD6aBi5NjqxCCHYROgwsmn5/eFScQViUmzJHhoJjsFjD/m0B2FCID3xy+oQPLOm"+
//"HdSquRcI3P0cQCwaIwiq/ZGMyMa8o9cCVu6Uizzfoh/jW55/rCMbxRqsJw/p1tbCp5C2tqMrnq0qXFpZ"+
//"UlJQoFOubgVhSWlpRAWlVeRVftaTSVenSKP/BqvvUxUhKxaJ4dB3Cw3KQPmaSxwLykDiONXD02AxHkPf"+
//"owWWubu6ffz7hH3fsl9jD7PPscYCj7DH2qasvVq6+WLn6YuXqixV09cXK1RcrV1+sXH2xcvXFytUXK1d"+
//"frFx9sXL1xcrVFytXX6xcfbHy/+SLlUX//biEeyn/lereuayNf9H/RYST95X7HKYevqDMZXOlXDO3krs"+
//"GUteiEUgM/ke9rKFrhsQeYfZDOIYfZRFdF7XAFaZ7HpHpH/dwZXz+8+YobkY+dIXraPwE+86RhoYyzwz"+
//"kjiKaT+fll9GK6fTMsufZd5inYJ8wAeH0dGoGrXl7esUKEVmyVECOFBSWna5Vsm+jPwEw7NvsafAz2up"+
//"IXlHZ+VoNEDB7M9JijExoP/srFANgkIf9xRFbTtm+4+yrUP9D9mWQlDR7eVqTWAYdfp/9FtIjE/ss+4x"+
//"Y88yRhMQyVBth70YYnYD0FMAZgPMAHAqyT6CdAHsADgFwSAupCaAYoI1Q2IPsQZDzAPkoO6TFAEGAPQA"+
//"c6mC/DvQtJGWfZDcjC7S9i70PpUC+m72X5l+FPB3yrwA9G/JHoUzyfWL5IchJ/ZdE+l4op0L+oJg/APQ"+
//"MyO+nP/5oYr8olrexY7RdVMz3s5HpbJOuNhvqeYASABaw+wC7D1R3HzEwpJi9lR2mIx2GvAzyESEHde2"+
//"YNlupjXYcSTOW7QeV7gDV7wDN7QDN7UAcVN00x3OTwFPI3gQ8NwHPTcBzE2ilhI3AeBHyVQZIdQA8AAt"+
//"6j4DeCT0G6QmAU5R+G6RTAPtJid0OeswHqXaxm6fzTOBkg0dcnrKa59gBULWHHThizCrbc6mkUBJHhDx"+
//"BzLWE109r/UcUakL1H0nPEnLg2lKbwPajzwIwKBlSG0AFQD0Ax/ZP24pNx9g1aESOPAmmncxOdie3U8K"+
//"V1GP9cbYMtcsRuKSeLURuYMg39bhxVa8ipJhQsDoFryhReBTtCkmQ3cnuYVkTW8zWsG1sDysh37uVVZe"+
//"TL7KtlFaXT6n2q2KqE6pTKklMekJ6SnpGel4q4aUlUo+0XdorDUknpFPS/VLFlHRKxvSqQqoJFatT8ao"+
//"SlUfVrpKYZHh/7e1sH/kqA6Q6gBDAFAAHOu4BOs9eD9AD1ugBVVwPdAQpgpIO4BTgZyCXQEkLfFrg0wJ"+
//"VC1Qt/c0cLa1pB+gFCIm10vmauTaE/zypAciF2gSgki8PnIH0PMEAVkNJAyUNlDTAdYr5GCTUQcoDtAO"+
//"wlHYGALwG0rm6ErG+F0BK689Tnrk6D2nLfOzx5p7Ix7F8vD8fT+Vjj7umtsxjgUSv1/dYe+w9eT0HuKA"+
//"1aA/mBQ9wbdY2e1te2wGuxlpjr8mrOcAVW4vtxXnFBziT1WQ35ZkOcHtaDrUcbznZwvW0BFt2trBV5Ev"+
//"X046SMppb7CR/ZtqYXlalrb2GOQTT6YF0H8BpABZpITUBFAPUAAQBJMwhSv0GUL8B1G+gNoAeAAm0+gY"+
//"JMZCaxDpC30frCEbqmUX1LEz+qenq8rbaFgi7PQD7AFjo+ymof4pyC9ghSo9BeobS20T+/ZROuEwAc+1"+
//"IENxEw90mWIabUA1AD0AIQIJOshvRaQDoHVITQAjgEADHboJ7I7uR+QbcTzFPsU6PpjTFhFJTYfvQJ8p"+
//"1tTpGDb6gwU/S9EGa7qJpDU1tnoTVmg9Xa76zWnPHak0uIEwebGwafB9NzR5VrebpWk1brSa/VgO9pSE"+
//"z0jApNJWSFP+Bpmto6vQkmzV/N2v+atb82ax52KzZatZcYybtMmENa5hkmqpIiu+n6Wqa5nhUJs33TJq"+
//"NJk2VSVOrwY9gGB2toGk2TTNIiv/ytLZeixTP4b+geugJT7vzTTMMohmOT7trIZuddq+E7OK0+xHI/mv"+
//"afa/p2/jvmG5t+MNp21lTbQq+gJs4Uv6rmP8ZN6GDkJ+HfBDyx5Eb2yH/6rT7FsL/GLT/EpS/gixywv8"+
//"oaqft9uEmSn9YbPflaWcfjPrQtHMcRv0SctJRH5h2ngXqvdPOXZB9Ydo5DNmeaTsRcPO0u8BUm0i+DMs"+
//"Q3n5kZ4gkLeKIq6DnYchXCo0bpp2kVT0ZYAbXTVtLIcslUn4bW1E7Hc40baWTzEJW2kUmslKhM5Cd5gl"+
//"YS4XXIAvN5dPWW6AX6dP2s6a/uZ8jE0cfYO30I6ZffxvmtwGK/4Gbpg+aXjtK1DVtOumcwfZnTT+2Pmf"+
//"6rm0Gb5g2nXDOyKHiuHOGwc+YDoOSY8DL4GdNh5yDpm9Yae0BK9SCqfe5C00PWTeZ9tqhPG26xfltIgY"+
//"agRlvgOou53JTi/ugqdE+g6Ha44bBPEpTtTVscgF56QxuOnLQVGqbIaKUQB8HnzUVwIg5VirK+qpjTCW"+
//"S4TGPUxaV9ck2yK6VLZOVywplvCxLlilLluvlOnmCXC1XyuVyqZyTM3IkTyZfsHXQ3zeR6ujP3XIk5Si"+
//"uY0jKCF8lZLCcgbUTS2KbmeZ1K3BM34yaO1bEqhzNM7L42thSR3NM3v6ZzsMY39MFpRjz+RmMOjrBQQn"+
//"p9gzyQ4dHEcbFt9+dQfKbbr+7qws3x070o+Y+PvbhOpiH8tpNMYl1hQGlbqsx1OiXJ7oa66+Q9Irpgm/"+
//"6GhZ979eQFbu/eV1n7OtZXbEygsSzuppjK8lPJB5ltjLBhvqjTIhkXZ1H8Y3M1oa1hI5vrO+aZ0MWJgR"+
//"syE0ywnYEWQgbsuAjlK2FsoGbWhrqD1ssAtOLuIkwgfu8SJkGhb5sMAT01U4yYGOykY32ZWOyCRv4g9C"+
//"ZdmFnaoS1tDOtGtHOMgnTYbsdWJx2wnK4yg4Mh+1VtPrgpWqrXRCnC9npOHbcRcfB+BJPnsADXiDyMHL"+
//"g+dTf4/lXL/+Kf4EZH/H+0tdPfqiy19rgB+iN7d42ZIhN9PH8Yd8vxV+wzOnt6x8iudcf+6XVXx/zWev"+
//"5w97+K1T3k2qvtf4w6m/o6Dzc7/HXT3s93gart77ryOM765oXjbVrfqy6nVfobCfprI6M9XjzFaqbSfX"+
//"jZKxmMlYzGetxz+N0rOa1K3Bze+dhOVpBfpeJ5kcYlRLWQ2+GuWtFqi60nC6OZWbDzRnHOATblsrRFVN"+
//"bV8Q0AKSqsLawllTB6iRVCeSnSMUqw83LzBnH8JNilQ7IidYVyIEMDYH6+b9IJBIlMDbmgDQ6ZqC0KCx"+
//"a87rmWCP54UR3zN0Q8/TWd9GfVhkTr7pOj+64+6SbCbp3uve497kPuSVjY11A1h+3nLQwPZagZadlj2W"+
//"f5ZBFSiqu63zW495n+ZOFHQNvwlG4GurpmGOQwx8pRsci5EIwQARAGM4x5qjrrLWgfjj1YjihF6IkACt"+
//"AOcA6AAn6X5C+DvBrgL8CcOhWSO8FeAzgCKGwhWxhgyFQT0bscpCgY2DLjpRUli2dgdw7IOTrNgl5wxo"+
//"hd9eWGSCfrilX1mrhAI7RMUh/CPALgN8D/BeAhC1jy2jnY4LXdkVQxIFBfPLTA1GSRBxR+ltxmKg7GnE"+
//"4EAHi4GAB8pNWeLHfIxwZQ6AKMAhkwESpEdJsjORz1/8G+UkgzgplbmRzdHJlYW0KZW5kb2JqCgo2IDA"+
//"gb2JqCjEyNzAzCmVuZG9iagoKNyAwIG9iago8PC9UeXBlL0ZvbnREZXNjcmlwdG9yL0ZvbnROYW1lL0J"+
//"BQUFBQStUaW1lc05ld1JvbWFuUFNNVAovRmxhZ3MgNAovRm9udEJCb3hbLTU2OCAtMzA2IDIwMjcgMTA"+
//"wNl0vSXRhbGljQW5nbGUgMAovQXNjZW50IDg5MQovRGVzY2VudCAtMjE2Ci9DYXBIZWlnaHQgMTAwNgo"+
//"vU3RlbVYgODAKL0ZvbnRGaWxlMiA1IDAgUgo+PgplbmRvYmoKCjggMCBvYmoKPDwvTGVuZ3RoIDI3NC9"+
//"GaWx0ZXIvRmxhdGVEZWNvZGU+PgpzdHJlYW0KeJxdkctuwyAQRfd8Bct0EeFn0kiWpTRRJC/6UN18AIa"+
//"xi1RjhPHCf18Y3FbqAnSGuRddBnZpro1Wjr3ZSbTgaK+0tDBPixVAOxiUJmlGpRJuq3AXIzeEeW+7zg7"+
//"GRvdTVRH27nuzsyvdneXUwQNhr1aCVXqgu/ul9XW7GPMFI2hHE1LXVELv73nm5oWPwNC1b6RvK7fuveV"+
//"P8LEaoBnWaYwiJgmz4QIs1wOQKklqWt1uNQEt//XSzdL14pNbL029NEnKovacIR/KwDnyMQ9cREZNiZw"+
//"lgQ/IxSHwMXpPgR+jPgt8iufXwOfIaeAn5DzDkFuaEDfM82cMVCzW+hHg0PHt4dVKw++/mMkEF65vLfS"+
//"E0gplbmRzdHJlYW0KZW5kb2JqCgo5IDAgb2JqCjw8L1R5cGUvRm9udC9TdWJ0eXBlL1RydWVUeXBlL0J"+
//"hc2VGb250L0JBQUFBQStUaW1lc05ld1JvbWFuUFNNVAovRmlyc3RDaGFyIDAKL0xhc3RDaGFyIDExCi9"+
//"XaWR0aHNbNzc3IDYxMCA0NDMgMzg5IDI3NyAyNTAgNTU2IDI3NyAzMzMgNzc3IDQ0MyA1MDAgXQovRm9"+
//"udERlc2NyaXB0b3IgNyAwIFIKL1RvVW5pY29kZSA4IDAgUgo+PgplbmRvYmoKCjEwIDAgb2JqCjw8L0Y"+
//"xIDkgMCBSCj4+CmVuZG9iagoKMTEgMCBvYmoKPDwvRm9udCAxMCAwIFIKL1Byb2NTZXRbL1BERi9UZXh"+
//"0XQo+PgplbmRvYmoKCjEgMCBvYmoKPDwvVHlwZS9QYWdlL1BhcmVudCA0IDAgUi9SZXNvdXJjZXMgMTE"+
//"gMCBSL01lZGlhQm94WzAgMCA1OTUgODQyXS9Hcm91cDw8L1MvVHJhbnNwYXJlbmN5L0NTL0RldmljZVJ"+
//"HQi9JIHRydWU+Pi9Db250ZW50cyAyIDAgUj4+CmVuZG9iagoKNCAwIG9iago8PC9UeXBlL1BhZ2VzCi9"+
//"SZXNvdXJjZXMgMTEgMCBSCi9NZWRpYUJveFsgMCAwIDU5NSA4NDIgXQovS2lkc1sgMSAwIFIgXQovQ29"+
//"1bnQgMT4+CmVuZG9iagoKMTIgMCBvYmoKPDwvVHlwZS9DYXRhbG9nL1BhZ2VzIDQgMCBSCi9PcGVuQWN"+
//"0aW9uWzEgMCBSIC9YWVogbnVsbCBudWxsIDBdCi9MYW5nKGl0LUlUKQo+PgplbmRvYmoKCjEzIDAgb2J"+
//"qCjw8L0F1dGhvcjxGRUZGMDA3MjAwNkYwMDZGMDA3NDAwMjA+Ci9DcmVhdG9yPEZFRkYwMDU3MDA3MjA"+
//"wNjkwMDc0MDA2NTAwNzI+Ci9Qcm9kdWNlcjxGRUZGMDA0QzAwNjkwMDYyMDA3MjAwNjUwMDRGMDA2NjA"+
//"wNjYwMDY5MDA2MzAwNjUwMDIwMDAzMzAwMkUwMDM1PgovQ3JlYXRpb25EYXRlKEQ6MjAxNTAxMjIxMjE"+
//"3MzUrMDEnMDAnKT4+CmVuZG9iagoKeHJlZgowIDE0CjAwMDAwMDAwMDAgNjU1MzUgZiAKMDAwMDAxMzg"+
//"3OCAwMDAwMCBuIAowMDAwMDAwMDE5IDAwMDAwIG4gCjAwMDAwMDAyMTMgMDAwMDAgbiAKMDAwMDAxNDA"+
//"yMSAwMDAwMCBuIAowMDAwMDAwMjMzIDAwMDAwIG4gCjAwMDAwMTMwMjEgMDAwMDAgbiAKMDAwMDAxMzA"+
//"0MyAwMDAwMCBuIAowMDAwMDEzMjQyIDAwMDAwIG4gCjAwMDAwMTM1ODUgMDAwMDAgbiAKMDAwMDAxMzc"+
//"5MSAwMDAwMCBuIAowMDAwMDEzODIzIDAwMDAwIG4gCjAwMDAwMTQxMjAgMDAwMDAgbiAKMDAwMDAxNDI"+
//"xNyAwMDAwMCBuIAp0cmFpbGVyCjw8L1NpemUgMTQvUm9vdCAxMiAwIFIKL0luZm8gMTMgMCBSCi9JRCB"+
//"bIDwxMUUwNzJBQ0I2MjU2REQ0OTk5NEU4M0I2NjBBRDFFQz4KPDExRTA3MkFDQjYyNTZERDQ5OTk0RTg"+
//"zQjY2MEFEMUVDPiBdCi9Eb2NDaGVja3N1bSAvQjQ2MTRGNzVDNzI2RjhGMTBFNzlDMzJFQkYxRUZFRTY"+
//"KPj4Kc3RhcnR4cmVmCjE0NDI2CiUlRU9GCgAAAAAAAKCCBMwwggTIMIIDsKADAgECAgMBNvswDQYJKoZ"+
//"IhvcNAQELBQAwaDELMAkGA1UEBhMCSVQxFTATBgNVBAoTDExJU0lUIFMucC5BLjEjMCEGA1UECxMaU2V"+
//"ydml6aW8gZGkgY2VydGlmaWNhemlvbmUxHTAbBgNVBAMTFExJU0lUIENBIGRpIFNlcnZpemlvMB4XDTE"+
//"xMDUxMzEwMjQwMloXDTE2MDkwMjE0NTgwNFowgeExCzAJBgNVBAYTAklUMS8wLQYDVQQKDCZDUlMgU0l"+
//"TUyBDZXJ0aWZpY2F0aSBkaSBmaXJtYSBkaWdpdGFsZTEnMCUGA1UECwweQ2VydGlmaWNhdGkgT3BlcmF"+
//"0b3JpIFZpcnR1YWxpMRAwDgYDVQQEDAdWVkFSRVNFMRAwDgYDVQQqDAdWTUVESUNJMRgwFgYDVQQDDA9"+
//"WTUVESUNJIFZWQVJFU0UxHDAaBgNVBAUTE0lUOlZWUlZEQzYxQTAxTDY4MlYxCzAJBgNVBAwMAjEyMQ8"+
//"wDQYDVQQuEwYyODM5NTMwgaAwDQYJKoZIhvcNAQEBBQADgY4AMIGKAoGBAI1fFhTChAAmhs/oLYmLn/0"+
//"5ndU3M8Sx2MDYPDHEPYYGZ45ggc1f9B4OvNAUmzuG5Epr2uETJ5ZFl33MM5wxjZ9GMVFV9c+yrh7nAK+"+
//"U8bq+Ypd77sSq8bwtm/wbIG4CQMGXu7t84wmGSrdyecK3nyreDg449FKfAwV11JakqV4dAgQAwVzTo4I"+
//"BgjCCAX4wSAYDVR0gBEEwPzA9BgorBgEEAbxuEwJjMC8wLQYIKwYBBQUHAgEWIWh0dHA6Ly93d3cubGl"+
//"zaXQuaXQvZmlybWFkaWdpdGFsZTAvBggrBgEFBQcBAwQjMCEwCAYGBACORgEBMAsGBgQAjkYBAwIBFDA"+
//"IBgYEAI5GAQQwDgYDVR0PAQH/BAQDAgZAMB8GA1UdIwQYMBaAFITNsS8cq9CLz3r+wbxXsIodDoULMIG"+
//"wBgNVHR8EgagwgaUwgaKggZ+ggZyGgZlsZGFwOi8vbGRhcC5jcnMubG9tYmFyZGlhLml0L2NuJTNkQ0R"+
//"QMSxvdSUzZENSTCUyMENBJTIwU2Vydml6aW8sbyUzZExJU0lUJTIwUy5wLkEuLGMlM2RJVD9jZXJ0aWZ"+
//"pY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Q2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwHQY"+
//"DVR0OBBYEFFyfyGhqHcpZZqq1hiQ+9HehjVKIMA0GCSqGSIb3DQEBCwUAA4IBAQBKce/912ZgglLIKYe"+
//"0ncKGmCWFqyNkZEKmp4K/rx4MLDTQ2NqBk8ogZmnWc8h4Tb0SzKtjVLLisn7Z7t2VFkx00m21EkWxStv"+
//"RBAxXJg/GQgm3mjsVWbvj58XuBk+3JsJaxmNDHdKtPUj3CfcSQ3xgeQ8YxtU11BVPaJf5OK8FxKkwdTW"+
//"0G2g9gZqtbHKcczzfxfXl/Qk1cthyJl/ftPv6ubpfD3zlXZOL4dnHHms+oUqVgkbawFwWr06m4RP63ru"+
//"8JTcHj+fKKb2ho7DIXxYiJwcaYhJueqbvJ7cQ4BBh4Ce4jAMgAoFeYx4dUzWoBQkAehprG+6O7/IMwkz"+
//"RacZ4MYICOTCCAjUCAQEwbzBoMQswCQYDVQQGEwJJVDEVMBMGA1UEChMMTElTSVQgUy5wLkEuMSMwIQY"+
//"DVQQLExpTZXJ2aXppbyBkaSBjZXJ0aWZpY2F6aW9uZTEdMBsGA1UEAxMUTElTSVQgQ0EgZGkgU2Vydml"+
//"6aW8CAwE2+zANBglghkgBZQMEAgEFAKCCARwwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhki"+
//"G9w0BCQUxDxcNMTUwMTI2MTI1MTIzWjAvBgkqhkiG9w0BCQQxIgQg7uUl+VdA7ate7LFIT4p8cb1bJ2t"+
//"t51jlFK05gEOAoSYwgbAGCyqGSIb3DQEJEAIvMYGgMIGdMIGaMIGXBCCMPBVPLQBSW0xK5truJjoV2Rx"+
//"55QRw6u9Chsvc4XVPBzBzMGykajBoMQswCQYDVQQGEwJJVDEVMBMGA1UEChMMTElTSVQgUy5wLkEuMSM"+
//"wIQYDVQQLExpTZXJ2aXppbyBkaSBjZXJ0aWZpY2F6aW9uZTEdMBsGA1UEAxMUTElTSVQgQ0EgZGkgU2V"+
//"ydml6aW8CAwE2+zANBgkqhkiG9w0BAQEFAASBgIf3/vxsG7YHawH2j0g00KKVbMD+tBxM0bZCiinxk0t"+
//"rJFbIyYhmiL2pXCQZ7s0LYAEWtyv8cr/iNTo6hyOM/0dnsCBs4z7CgEKtf82lbJeLjTsxu00KnA23pEZ"+
//"Iav7APBigP8LYLjVE0WeEqfIgaZbyjqW1FFmoXjdtUp0h4v6lAAAAAAAA</contenutoFirmato>" +
//"</documentoFirmato>" +
//"</datiRisposta>" +
//"</param>" +
//"</m:SA.ottieniDocumentoFirmatoResponse>" +
//"</SOAP-ENV:Body></SOAP-ENV:Envelope>";
		//System.out.println(responseOttieniDocumentoFirmato);

		Document responseDoc = getDocument(responseOttieniDocumentoFirmato);
		
		if( isFault(responseDoc) ){
			LogWriter.writeLog("ERRORE nella risposta del servizio ");
			return null;
		}
		
		String contenutoFirmato = getContenutoFirmato(responseDoc);
		
		FileOutputStream outputStream = FileUtils.openOutputStream( outputFile );
		byte[] fileContentByteArray = Base64.decodeBase64( contenutoFirmato ) ;
		if( fileContentByteArray!=null ){
			try {
				FileUtils.writeByteArrayToFile(outputFile, fileContentByteArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return contenutoFirmato;
	}
	
	private static boolean isFault(Document responseDoc ){
		NodeList documentoList = responseDoc.getElementsByTagName("Fault");
		if( documentoList.getLength()>0)
			return true;
		return false;
	}
	
	private static String getContenutoFirmato(Document responseDoc ){
		NodeList documentoList = responseDoc.getElementsByTagName("contenutoFirmato");
		if( documentoList==null || documentoList.getLength()==0)
			return null;
		
		String contenutoFirmato = null;
		for (int temp = 0; temp < documentoList.getLength(); temp++) {
			Node nNode = documentoList.item(temp);
	 		
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 			Element eElement = (Element) nNode;
	 			contenutoFirmato = eElement.getTextContent();
			}
		}
		
		return contenutoFirmato;
	}
	
	public static void main(String[] args) {
		String response = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
				"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
				" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" " +
				"xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\" " +
				"xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" " +
				"SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
				"<SOAP-ENV:Body>" +
				"<m:SA.ottieniDocumentoFirmatoResponse xmlns:m=\"http://www.crs.lombardia.it/schemas/CRS-SISS/SA/2009-01/ottieniDocumentoFirmato/\">" +
				"<param>" +
				"<datiRisposta>" +
				"<esitoOperazione>FIRMA_OK</esitoOperazione>" +
				"<identificativoDocumento>002</identificativoDocumento>" +
				"<documentoFirmato>" +
				"<tipoDocumento>PDF</tipoDocumento>" +
				"<tipoFirma>LEGALE_OPERATORE</tipoFirma>" +
				"<contenutoFirmato>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"+
"5w97+K1T3k2qvtf4w6m/o6Dzc7/HXT3s93gart77ryOM765oXjbVrfqy6nVfobCfprI6M9XjzFaqbSfX"+
"jZKxmMlYzGetxz+N0rOa1K3Bze+dhOVpBfpeJ5kcYlRLWQ2+GuWtFqi60nC6OZWbDzRnHOATblsrRFVN"+
"bV8Q0AKSqsLawllTB6iRVCeSnSMUqw83LzBnH8JNilQ7IidYVyIEMDYH6+b9IJBIlMDbmgDQ6ZqC0KCx"+
"a87rmWCP54UR3zN0Q8/TWd9GfVhkTr7pOj+64+6SbCbp3uve497kPuSVjY11A1h+3nLQwPZagZadlj2W"+
"f5ZBFSiqu63zW495n+ZOFHQNvwlG4GurpmGOQwx8pRsci5EIwQARAGM4x5qjrrLWgfjj1YjihF6IkACt"+
"AOcA6AAn6X5C+DvBrgL8CcOhWSO8FeAzgCKGwhWxhgyFQT0bscpCgY2DLjpRUli2dgdw7IOTrNgl5wxo"+
"hd9eWGSCfrilX1mrhAI7RMUh/CPALgN8D/BeAhC1jy2jnY4LXdkVQxIFBfPLTA1GSRBxR+ltxmKg7GnE"+
"4EAHi4GAB8pNWeLHfIxwZQ6AKMAhkwESpEdJsjORz1/8G+UkgzgplbmRzdHJlYW0KZW5kb2JqCgo2IDA"+
"gb2JqCjEyNzAzCmVuZG9iagoKNyAwIG9iago8PC9UeXBlL0ZvbnREZXNjcmlwdG9yL0ZvbnROYW1lL0J"+
"BQUFBQStUaW1lc05ld1JvbWFuUFNNVAovRmxhZ3MgNAovRm9udEJCb3hbLTU2OCAtMzA2IDIwMjcgMTA"+
"wNl0vSXRhbGljQW5nbGUgMAovQXNjZW50IDg5MQovRGVzY2VudCAtMjE2Ci9DYXBIZWlnaHQgMTAwNgo"+
"vU3RlbVYgODAKL0ZvbnRGaWxlMiA1IDAgUgo+PgplbmRvYmoKCjggMCBvYmoKPDwvTGVuZ3RoIDI3NC9"+
"GaWx0ZXIvRmxhdGVEZWNvZGU+PgpzdHJlYW0KeJxdkctuwyAQRfd8Bct0EeFn0kiWpTRRJC/6UN18AIa"+
"xi1RjhPHCf18Y3FbqAnSGuRddBnZpro1Wjr3ZSbTgaK+0tDBPixVAOxiUJmlGpRJuq3AXIzeEeW+7zg7"+
"GRvdTVRH27nuzsyvdneXUwQNhr1aCVXqgu/ul9XW7GPMFI2hHE1LXVELv73nm5oWPwNC1b6RvK7fuveV"+
"P8LEaoBnWaYwiJgmz4QIs1wOQKklqWt1uNQEt//XSzdL14pNbL029NEnKovacIR/KwDnyMQ9cREZNiZw"+
"lgQ/IxSHwMXpPgR+jPgt8iufXwOfIaeAn5DzDkFuaEDfM82cMVCzW+hHg0PHt4dVKw++/mMkEF65vLfS"+
"E0gplbmRzdHJlYW0KZW5kb2JqCgo5IDAgb2JqCjw8L1R5cGUvRm9udC9TdWJ0eXBlL1RydWVUeXBlL0J"+
"hc2VGb250L0JBQUFBQStUaW1lc05ld1JvbWFuUFNNVAovRmlyc3RDaGFyIDAKL0xhc3RDaGFyIDExCi9"+
"XaWR0aHNbNzc3IDYxMCA0NDMgMzg5IDI3NyAyNTAgNTU2IDI3NyAzMzMgNzc3IDQ0MyA1MDAgXQovRm9"+
"udERlc2NyaXB0b3IgNyAwIFIKL1RvVW5pY29kZSA4IDAgUgo+PgplbmRvYmoKCjEwIDAgb2JqCjw8L0Y"+
"xIDkgMCBSCj4+CmVuZG9iagoKMTEgMCBvYmoKPDwvRm9udCAxMCAwIFIKL1Byb2NTZXRbL1BERi9UZXh"+
"0XQo+PgplbmRvYmoKCjEgMCBvYmoKPDwvVHlwZS9QYWdlL1BhcmVudCA0IDAgUi9SZXNvdXJjZXMgMTE"+
"gMCBSL01lZGlhQm94WzAgMCA1OTUgODQyXS9Hcm91cDw8L1MvVHJhbnNwYXJlbmN5L0NTL0RldmljZVJ"+
"HQi9JIHRydWU+Pi9Db250ZW50cyAyIDAgUj4+CmVuZG9iagoKNCAwIG9iago8PC9UeXBlL1BhZ2VzCi9"+
"SZXNvdXJjZXMgMTEgMCBSCi9NZWRpYUJveFsgMCAwIDU5NSA4NDIgXQovS2lkc1sgMSAwIFIgXQovQ29"+
"1bnQgMT4+CmVuZG9iagoKMTIgMCBvYmoKPDwvVHlwZS9DYXRhbG9nL1BhZ2VzIDQgMCBSCi9PcGVuQWN"+
"0aW9uWzEgMCBSIC9YWVogbnVsbCBudWxsIDBdCi9MYW5nKGl0LUlUKQo+PgplbmRvYmoKCjEzIDAgb2J"+
"qCjw8L0F1dGhvcjxGRUZGMDA3MjAwNkYwMDZGMDA3NDAwMjA+Ci9DcmVhdG9yPEZFRkYwMDU3MDA3MjA"+
"wNjkwMDc0MDA2NTAwNzI+Ci9Qcm9kdWNlcjxGRUZGMDA0QzAwNjkwMDYyMDA3MjAwNjUwMDRGMDA2NjA"+
"wNjYwMDY5MDA2MzAwNjUwMDIwMDAzMzAwMkUwMDM1PgovQ3JlYXRpb25EYXRlKEQ6MjAxNTAxMjIxMjE"+
"3MzUrMDEnMDAnKT4+CmVuZG9iagoKeHJlZgowIDE0CjAwMDAwMDAwMDAgNjU1MzUgZiAKMDAwMDAxMzg"+
"3OCAwMDAwMCBuIAowMDAwMDAwMDE5IDAwMDAwIG4gCjAwMDAwMDAyMTMgMDAwMDAgbiAKMDAwMDAxNDA"+
"yMSAwMDAwMCBuIAowMDAwMDAwMjMzIDAwMDAwIG4gCjAwMDAwMTMwMjEgMDAwMDAgbiAKMDAwMDAxMzA"+
"0MyAwMDAwMCBuIAowMDAwMDEzMjQyIDAwMDAwIG4gCjAwMDAwMTM1ODUgMDAwMDAgbiAKMDAwMDAxMzc"+
"5MSAwMDAwMCBuIAowMDAwMDEzODIzIDAwMDAwIG4gCjAwMDAwMTQxMjAgMDAwMDAgbiAKMDAwMDAxNDI"+
"xNyAwMDAwMCBuIAp0cmFpbGVyCjw8L1NpemUgMTQvUm9vdCAxMiAwIFIKL0luZm8gMTMgMCBSCi9JRCB"+
"bIDwxMUUwNzJBQ0I2MjU2REQ0OTk5NEU4M0I2NjBBRDFFQz4KPDExRTA3MkFDQjYyNTZERDQ5OTk0RTg"+
"zQjY2MEFEMUVDPiBdCi9Eb2NDaGVja3N1bSAvQjQ2MTRGNzVDNzI2RjhGMTBFNzlDMzJFQkYxRUZFRTY"+
"KPj4Kc3RhcnR4cmVmCjE0NDI2CiUlRU9GCgAAAAAAAKCCBMwwggTIMIIDsKADAgECAgMBNvswDQYJKoZ"+
"IhvcNAQELBQAwaDELMAkGA1UEBhMCSVQxFTATBgNVBAoTDExJU0lUIFMucC5BLjEjMCEGA1UECxMaU2V"+
"ydml6aW8gZGkgY2VydGlmaWNhemlvbmUxHTAbBgNVBAMTFExJU0lUIENBIGRpIFNlcnZpemlvMB4XDTE"+
"xMDUxMzEwMjQwMloXDTE2MDkwMjE0NTgwNFowgeExCzAJBgNVBAYTAklUMS8wLQYDVQQKDCZDUlMgU0l"+
"TUyBDZXJ0aWZpY2F0aSBkaSBmaXJtYSBkaWdpdGFsZTEnMCUGA1UECwweQ2VydGlmaWNhdGkgT3BlcmF"+
"0b3JpIFZpcnR1YWxpMRAwDgYDVQQEDAdWVkFSRVNFMRAwDgYDVQQqDAdWTUVESUNJMRgwFgYDVQQDDA9"+
"WTUVESUNJIFZWQVJFU0UxHDAaBgNVBAUTE0lUOlZWUlZEQzYxQTAxTDY4MlYxCzAJBgNVBAwMAjEyMQ8"+
"wDQYDVQQuEwYyODM5NTMwgaAwDQYJKoZIhvcNAQEBBQADgY4AMIGKAoGBAI1fFhTChAAmhs/oLYmLn/0"+
"5ndU3M8Sx2MDYPDHEPYYGZ45ggc1f9B4OvNAUmzuG5Epr2uETJ5ZFl33MM5wxjZ9GMVFV9c+yrh7nAK+"+
"U8bq+Ypd77sSq8bwtm/wbIG4CQMGXu7t84wmGSrdyecK3nyreDg449FKfAwV11JakqV4dAgQAwVzTo4I"+
"BgjCCAX4wSAYDVR0gBEEwPzA9BgorBgEEAbxuEwJjMC8wLQYIKwYBBQUHAgEWIWh0dHA6Ly93d3cubGl"+
"zaXQuaXQvZmlybWFkaWdpdGFsZTAvBggrBgEFBQcBAwQjMCEwCAYGBACORgEBMAsGBgQAjkYBAwIBFDA"+
"IBgYEAI5GAQQwDgYDVR0PAQH/BAQDAgZAMB8GA1UdIwQYMBaAFITNsS8cq9CLz3r+wbxXsIodDoULMIG"+
"wBgNVHR8EgagwgaUwgaKggZ+ggZyGgZlsZGFwOi8vbGRhcC5jcnMubG9tYmFyZGlhLml0L2NuJTNkQ0R"+
"QMSxvdSUzZENSTCUyMENBJTIwU2Vydml6aW8sbyUzZExJU0lUJTIwUy5wLkEuLGMlM2RJVD9jZXJ0aWZ"+
"pY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Q2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwHQY"+
"DVR0OBBYEFFyfyGhqHcpZZqq1hiQ+9HehjVKIMA0GCSqGSIb3DQEBCwUAA4IBAQBKce/912ZgglLIKYe"+
"0ncKGmCWFqyNkZEKmp4K/rx4MLDTQ2NqBk8ogZmnWc8h4Tb0SzKtjVLLisn7Z7t2VFkx00m21EkWxStv"+
"RBAxXJg/GQgm3mjsVWbvj58XuBk+3JsJaxmNDHdKtPUj3CfcSQ3xgeQ8YxtU11BVPaJf5OK8FxKkwdTW"+
"0G2g9gZqtbHKcczzfxfXl/Qk1cthyJl/ftPv6ubpfD3zlXZOL4dnHHms+oUqVgkbawFwWr06m4RP63ru"+
"8JTcHj+fKKb2ho7DIXxYiJwcaYhJueqbvJ7cQ4BBh4Ce4jAMgAoFeYx4dUzWoBQkAehprG+6O7/IMwkz"+
"RacZ4MYICOTCCAjUCAQEwbzBoMQswCQYDVQQGEwJJVDEVMBMGA1UEChMMTElTSVQgUy5wLkEuMSMwIQY"+
"DVQQLExpTZXJ2aXppbyBkaSBjZXJ0aWZpY2F6aW9uZTEdMBsGA1UEAxMUTElTSVQgQ0EgZGkgU2Vydml"+
"6aW8CAwE2+zANBglghkgBZQMEAgEFAKCCARwwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhki"+
"G9w0BCQUxDxcNMTUwMTI2MTI1MTIzWjAvBgkqhkiG9w0BCQQxIgQg7uUl+VdA7ate7LFIT4p8cb1bJ2t"+
"t51jlFK05gEOAoSYwgbAGCyqGSIb3DQEJEAIvMYGgMIGdMIGaMIGXBCCMPBVPLQBSW0xK5truJjoV2Rx"+
"55QRw6u9Chsvc4XVPBzBzMGykajBoMQswCQYDVQQGEwJJVDEVMBMGA1UEChMMTElTSVQgUy5wLkEuMSM"+
"wIQYDVQQLExpTZXJ2aXppbyBkaSBjZXJ0aWZpY2F6aW9uZTEdMBsGA1UEAxMUTElTSVQgQ0EgZGkgU2V"+
"ydml6aW8CAwE2+zANBgkqhkiG9w0BAQEFAASBgIf3/vxsG7YHawH2j0g00KKVbMD+tBxM0bZCiinxk0t"+
"rJFbIyYhmiL2pXCQZ7s0LYAEWtyv8cr/iNTo6hyOM/0dnsCBs4z7CgEKtf82lbJeLjTsxu00KnA23pEZ"+
"Iav7APBigP8LYLjVE0WeEqfIgaZbyjqW1FFmoXjdtUp0h4v6lAAAAAAAA</contenutoFirmato>" +
"</documentoFirmato>" +
"</datiRisposta>" +
"</param>" +
"</m:SA.ottieniDocumentoFirmatoResponse>" +
"</SOAP-ENV:Body></SOAP-ENV:Envelope>";
		
		Document responseDoc = getDocument(response);
		
		if( isFault(responseDoc) )
			System.out.println("errore nella risposta");
		
		String contenutoFirmato = getContenutoFirmato(responseDoc);
		System.out.println("contenutoFirmato " + contenutoFirmato);
//		if( contenutoFirmato!=null ){
//		byte[] fileContent1String = Base64.decodeBase64( contenutoFirmato ) ;
//			try {
//				FileUtils.writeByteArrayToFile(new File("/root/Scrivania/testAnna.p7m"), fileContent1String);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	} 
	
	public static Document getDocument(String xmlString) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource( new StringReader( xmlString ) ));
			doc.getDocumentElement().normalize();
			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
