/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
import it.eng.utility.ui.user.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/UploadServletNoFileOp")
public class UploadServletNoFileOp {

	private static Logger logger = Logger.getLogger(UploadServletNoFileOp.class);
	private StorageService storageService;
	private Thread lThread;
	private TimeAdvance lTimeAdvance;
	public static Long maxSize;

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleIOException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
		StringBuffer lStringBuffer = new StringBuffer();
		lStringBuffer.append("<html>");					
		lStringBuffer.append("<body>");
		lStringBuffer.append("<script type=\"text/javascript\">");	
		lStringBuffer.append("</script>");
		lStringBuffer.append("</body>");
		lStringBuffer.append("</html>");
		return lStringBuffer.toString();
	}


	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("smartId") String smartId,
			@RequestParam("isExternalPortlet") boolean isExternalPortlet,
			@RequestParam("fileUploadAttr") MultipartFile fileUploadAttr, final HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		StringBuffer lStringBuffer = new StringBuffer();
		try {
			it.eng.utility.ui.module.layout.shared.bean.LoginBean loginInfo = UserUtil.getLoginInfo(session);
			if(loginInfo == null) {
				throw new Exception("Sessione utente scaduta");
			}
			session.setAttribute("semaforo", true); 
			session.setAttribute("finito", false);
			if (maxSize!=null && fileUploadAttr.getSize()>maxSize){
				throw new Exception("Il file risulta eccedere la massima dimensione di " + maxSize + " bytes");
			}
			initStorageService();
			logger.debug(session.getAttribute("caricato"));
			String filename = fileUploadAttr.getOriginalFilename().replaceAll("#", "");
			String lStrPath = storageService.storeStream(fileUploadAttr.getInputStream());	
			lTimeAdvance = new TimeAdvance(session);
			lThread = new Thread(lTimeAdvance);
			lThread.start();
			lTimeAdvance.setEnd();
			lStringBuffer.append("<html>");					
			lStringBuffer.append("<body>");
			lStringBuffer.append("<script type=\"text/javascript\">");
			lStringBuffer.append("try {" + (isExternalPortlet?"parent.document":"window.top") +  ".changeNameFile_" + smartId + "('" + StringEscapeUtils.escapeJavaScript(filename+"#"+lStrPath) + "'); } " +
			"catch(err) {alert('changeNameFile ' + err)}");
			lStringBuffer.append("</script>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");	
			session.removeAttribute("finito");
			session.removeAttribute("caricato");
			session.removeAttribute("numero");
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);							           
		} catch (Exception e) {
			if (lThread!=null && lTimeAdvance!=null){
				lTimeAdvance.setEnd();
			}
			lStringBuffer.append("<html>");					
			lStringBuffer.append("<body>");
			lStringBuffer.append("<script type=\"text/javascript\">");	
			lStringBuffer.append("try {" + (isExternalPortlet?"parent.document":"window.top") +  ".manageError_" + smartId + "('" + StringEscapeUtils.escapeJava(e.getMessage()) + "'); } " +
			"catch(err) {for (var p in err) alert(err[p])}");
			lStringBuffer.append("</script>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");	
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);		       	
		}
	}

	private void initStorageService() {
		storageService = StorageServiceImpl.newInstance(
				new GenericStorageInfo() {			

					public String getUtilizzatoreStorageId() {
						ModuleConfig mc = (ModuleConfig)SpringAppContext.getContext().getBean("moduleConfig");
						logger.debug("Recuperato module config");
						if(mc!=null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
							logger.debug("Id Storage vale " + mc.getIdDbStorage());
							return mc.getIdDbStorage();
						}
						else {
							logger.error("L'identificativo del DB di storage non è correttamente configurato, " +
							"controllare il file di configurazione del modulo.");
							return null;
						}
					}
				}
		);
	}

	private class TimeAdvance implements Runnable{
		private boolean end = false;
		private HttpSession session;
		public TimeAdvance(HttpSession session){
			this.session = session;
		}
		@Override
		public void run() {
			int value = 50;
			while (!end){
				if (value<=95){ 
					value++;
					session.setAttribute("caricato", value);
				}
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					logger.warn(e);
				}
			}
			session.setAttribute("caricato", 95);
			session.setAttribute("finito", true);

		}
		public void setEnd(){
			end = true;
		}

	}

}
