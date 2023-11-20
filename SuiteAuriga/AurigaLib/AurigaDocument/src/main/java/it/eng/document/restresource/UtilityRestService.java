/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import it.eng.utility.crypto.CryptoUtility;

@Singleton
@Api
@Path("/utilityWS")
public class UtilityRestService {

	@Context
	ServletContext context;

	private static final Logger logger = Logger.getLogger(UtilityRestService.class);

	
	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_XML }) 
	@Path("/calculateHash")
	public String calculate(String xmlInput) throws Exception {
		//porto l'xml su unica riga elimanando gli spazi tra i tag
		String xml = xmlInput.replaceAll(">\\s+<", "><");
	
		java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA");
		md.update(xml.getBytes());
		
		// calcolo lo sha-1
		byte[] digest = md.digest();
		
		// lo codifico base64
		it.eng.auriga.repository2.util.Base64 encoder = new it.eng.auriga.repository2.util.Base64();
		String digest_str = encoder.encode(digest);

		return digest_str;
		
	}	
	
	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.TEXT_PLAIN }) 
	@Path("/encryptText")
	public String encryptText(String text) throws Exception {
		String encryptText = CryptoUtility.encrypt64FromString(text);

		return encryptText;	
	}
	
	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.TEXT_PLAIN }) 
	@Path("/decryptText")
	public String decryptText(String text) throws Exception {
		String decryptText = CryptoUtility.decrypt64FromString(text);
		
		return decryptText;	
	}
	
}