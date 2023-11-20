/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.datasource.GetClobFromTabColDataSource;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.DownloadFileBean;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author DANCRIST
 *
 */
@Controller
@RequestMapping("/downloadClob")
public class DownloadClobServlet {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(DownloadServlet.class);

	@RequestMapping(value="", method=RequestMethod.GET)
	@ResponseBody
	public HttpEntity download(HttpServletRequest req, HttpServletResponse resp,HttpSession session)
	throws ServletException, IOException {

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("octet", "stream"));
		byte[] documentBody = null;

		AurigaLoginBean lLoginBean = AurigaUserUtil.getLoginInfo(session);	
		DownloadFileBean inputDownloadFileBean = null;		
		try {
			String values = req.getParameter("record");
			GsonBuilder builder = GsonBuilderFactory.getIstance();
		    Gson lGsonInput = builder.create();
			inputDownloadFileBean = lGsonInput.fromJson(values, DownloadFileBean.class);
			GetClobFromTabColDataSource lGetClobFromTabColDataSource = new GetClobFromTabColDataSource();
			DownloadFileBean resultDownloadFileBean = lGetClobFromTabColDataSource.downloadBlob(inputDownloadFileBean,lLoginBean);		
			//documentBody = IOUtils.toByteArray(resultDownloadFileBean.getMessage());
			String filename = resultDownloadFileBean.getNomeFile();
			String extFile =  resultDownloadFileBean.getExtFile();
			String message =  resultDownloadFileBean.getMessage();
			if(message != null && !"".equals(message)) {
				try {
					File fileTemp = File.createTempFile("downloadClob", extFile);
					FileUtils.writeStringToFile(fileTemp, message, "UTF-8");	
					documentBody = IOUtils.toByteArray(FileUtils.openInputStream(fileTemp));
					deleteLocalCopy(fileTemp);
				} catch (Exception e) {
					logger.error("Errore download file: "+ e.getMessage()+ " - "+ e.getCause());
					throw new ServletException(e);
				}
			} 
			header.set("Content-Disposition",
					"attachment; filename=\"" + filename+"\"");
			header.setContentLength(documentBody.length);
			return new HttpEntity<byte[]>(documentBody, header);
			
		} catch (Exception e) {
			
			logger.error("Errore download " + e.getMessage(), e);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.TEXT_HTML);
			responseHeaders.setCacheControl("private, no-store, no-cache, must-revalidate");
			responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
			responseHeaders.add("Cache-Control", "private, no-store, no-cache, must-revalidate");
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("<html>");
			lStringBuffer.append("<head>");
			lStringBuffer.append("<body>");
			lStringBuffer.append("<script>");
			lStringBuffer.append("try {window.top.errorCallback('Impossibile effettuare il download del file'); } " +
			"catch(err) {for (var p in err) alert(err[p])}");
			lStringBuffer.append("</script>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		}
	}
	
	protected void deleteLocalCopy(File localCopy) {
		try {
			FileDeleteStrategy.FORCE.delete(localCopy);
		} catch (Exception e) {
			logger.error(String.format("Non è stato possibile cancellare il file %1$s, si è verificata la seguente eccezione", localCopy.getAbsoluteFile()), e);
		}
	}


}