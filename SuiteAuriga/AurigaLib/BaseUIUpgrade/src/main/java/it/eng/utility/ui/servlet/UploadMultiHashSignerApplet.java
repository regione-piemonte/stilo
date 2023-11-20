/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSSignedData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.common.bean.SignerObjectBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.sign.SignerHashUtil;

@Controller
@RequestMapping("/UploadMultiHashSignerApplet")
public class UploadMultiHashSignerApplet {

	private static Logger logger = Logger.getLogger(UploadMultiHashSignerApplet.class);

	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("idDoc") String idDoc,
			@RequestParam("signedBean") String signedBean,
			@RequestParam("firmatario") String String, 
			@RequestParam(value = "pathFileTemp", required = false) String pathFileTemp, 
			@RequestParam("versioneDoc") String versioneDoc, 
			final HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		CMSSignedData data;
		try {
			SignerObjectBean bean = (SignerObjectBean)SerializationUtils.deserialize(Base64.decodeBase64( signedBean ) );
			logger.debug("Recupero il file originale");
			String[] infoFile = idDoc.split("#");
			String fileNameOriginale = infoFile[2];			
			if(!fileNameOriginale.toLowerCase().endsWith(".p7m")){
				fileNameOriginale = fileNameOriginale + ".p7m";
			}
			String uriFile = URLDecoder.decode(infoFile[1], "UTF-8");
			String idDocExtracted = infoFile[0];
			InputStream lInputStream = StorageImplementation.getStorage().extract(uriFile);
			byte[] originalData = IOUtils.toByteArray( lInputStream ) ;
			data = SignerHashUtil.signerP7M(bean, originalData);
			File lFileFirmato = File.createTempFile("sign", "");
			FileOutputStream outputStream = FileUtils.openOutputStream(lFileFirmato);
			ByteArrayInputStream bis= new ByteArrayInputStream(data.getEncoded());
			SignerHashUtil.attachP7M(originalData, bis, outputStream);
			outputStream.flush();
			outputStream.close();
			lInputStream.close();
			String uriFileFirmato = StorageImplementation.getStorage().store(lFileFirmato, new String[]{});
			String fileSavedPDF = StringEscapeUtils.escapeJava(fileNameOriginale+"#"+uriFileFirmato+"#"+idDocExtracted);
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(fileSavedPDF);
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			return new ResponseEntity<String>("OPERATION_ERROR", responseHeaders, HttpStatus.CREATED);    
		}
	}
}
