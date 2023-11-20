/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.DaoBmtJobs;
import it.eng.auriga.module.business.dao.beans.JobBean;
//import it.eng.bean.SecurityWSBean;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.DefaultConfigBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.crypto.CryptoUtility;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

@Singleton
@Api
@Path("/job")
public class AurigaJobService {
	
	protected static final String JOBSTATUS_IN_ELABORAZIONE = "E";
	protected static final String JOBSTATUS_IN_INIZIALE = "I";
	protected static final String JOBSTATUS_IN_ERRORE = "X";
	protected static final String JOBSTATUS_COMPLETATO = "R";
	protected static final String JOBSTATUS_DA_RITENTARE = "XT";
	
	protected static final String PASSWORD_TO_ACCESS_DEFAULT = "PaSSw0rd123";
	
	DaoBmtJobs daoBmtJob = new DaoBmtJobs();

	@Context
	ServletContext context;
	
	@Context
	HttpServletRequest request;
	
	protected static final Logger logger = Logger.getLogger(AurigaJobService.class);
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/riprocessaJob")
	public Response riprocessaJob(@Context HttpServletRequest request) throws Exception {

		String idJobParam = request.getParameter("idJob");
		
		logger.info("RIPROCESSAJOB SERVICE - START");
		
		// recupero la password di autenticazione dall header
//		String password = request.getHeader("password");
//		if(!checkPassword(password)) {
//			return Response.status(Response.Status.UNAUTHORIZED)
//					.entity("Accesso Negato").type(MediaType.TEXT_PLAIN).build();
//		}
		
		BigDecimal idJob;
		if(StringUtils.isNotBlank(idJobParam)) {
			idJob = new BigDecimal(idJobParam);
			logger.info("idJob: " + idJobParam);
		}else {
			logger.error("idJob non specificato");
			logger.info("RIATTIVAJOB RIPROCESSAJOB - END");
			return Response.status(Response.Status.BAD_REQUEST).entity("idJob non specificato").type(MediaType.TEXT_PLAIN).build();
		}
		
		try {
			DefaultConfigBean defaultConfigBean = (DefaultConfigBean) SpringAppContext.getContext()
					.getBean("defaultConfigBean");

			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setToken(defaultConfigBean.getCodIdConnectionToken());
			loginBean.setSchema(defaultConfigBean.getSchema());

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(loginBean.getSchema());
			SubjectUtil.subject.set(subject);

			aggiornaStatoJob(idJob, JOBSTATUS_IN_INIZIALE, "");
		} catch (Exception e) {
			String errorMessage = "Errore durante l'aggiornamento di stato per il job: " + idJobParam + " - errore: " + e.getMessage();
			logger.error(errorMessage);
			logger.info("RIPROCESSAJOB SERVICE - END");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).type(MediaType.TEXT_PLAIN).build();
		}
		
		logger.info("RIPROCESSAJOB SERVICE - END");
		return Response.status(Response.Status.OK).entity("Il job " + idJobParam + " e' stato messo nello stato I correttamente, verra riprocessato").type(MediaType.TEXT_PLAIN).build();
		
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/downloadResource")
	public Response downloadResource(@Context HttpServletRequest request) throws Exception {

		String pathLog = request.getParameter("path");
		
//		String decodePath = URLDecoder.decode(pathLog, "UTF-8");
		
		pathLog = CryptoUtility.decrypt64FromString(pathLog);

		// recupero la password di autenticazione dall header
//		String password = request.getHeader("password");
//
//		if(!checkPassword(password)) {
//			return Response.status(Response.Status.UNAUTHORIZED)
//					.entity("Accesso Negato").type(MediaType.TEXT_PLAIN).build();
//		}

		File logFile = new File(pathLog);
		if (!logFile.exists()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Non e' stato trovato il file: " + pathLog)
					.type(MediaType.TEXT_PLAIN).build();
		}

		StreamingOutput stream = null;
		try {
			final InputStream in = new FileInputStream(logFile);
			stream = new StreamingOutput() {
				public void write(OutputStream out) throws IOException, WebApplicationException {
					try {
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = in.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new WebApplicationException(e);
					} finally {
						if (in != null) {
							in.close();
						}
					}
				}
			};
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(stream).header("content-disposition", "attachment; filename = " + logFile.getName())
				.build();
	}
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/marcaJobComeGestito")
	public Response marcaJobComeGestito(@Context HttpServletRequest request) throws Exception {
		
		String pathReportFile = request.getParameter("pathReportFile");
		String idJob = request.getParameter("idJob");
		
		pathReportFile = CryptoUtility.decrypt64FromString(pathReportFile);
		
		File reportFile = new File(pathReportFile);
		if (!reportFile.exists()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Non e' stato trovato il file: " + pathReportFile)
					.type(MediaType.TEXT_PLAIN).build();
		}
		
		try {			
			cancellaRiga(pathReportFile, idJob);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity("Il job " + idJob + " e' stato cancellato dal file di report").type(MediaType.TEXT_PLAIN).build();

	}
	

	private void cancellaRiga(String pathReportFile, String idJob) throws Exception {
	    XSSFWorkbook workbook = null;
	    XSSFSheet sheet = null;

	    try {
	        FileInputStream file = new FileInputStream(new File(pathReportFile));
	        workbook = new XSSFWorkbook(file);
	        sheet = workbook.getSheet("Error Report");
	        if (sheet == null) {
	            throw new Exception("Pagina non trovata");
	        }
	        //Call getRowList method to get the rows containing the keyword
	        List<Integer> list = getRowList(sheet, idJob);

	        //Loop through the list
	        for (int i = 0; i < list.size(); i++) {
	            //Delete the specified row
	        	int rowIndex = list.get(i);
	            sheet.removeRow(sheet.getRow(list.get(i)));
	            if(rowIndex < sheet.getLastRowNum()) {
	                sheet.shiftRows(rowIndex + 1, sheet.getLastRowNum(), -1);
	            }
	        }
	    
	        file.close();
	        FileOutputStream outFile = new FileOutputStream(new File(pathReportFile));
	        workbook.write(outFile);
	        outFile.close();

	    } catch(Exception e) {
	        throw e;
	    } finally {
	        if(workbook != null)
	            workbook.close();
	    }
	}	
	
	private List<Integer> getRowList(XSSFSheet sheet, String keyword) {
		int rowNum = 0;
		int colNum = 0;
		List<Integer> rowList = new ArrayList<>();
		for (int i = sheet.getLastRowNum(); i >= 2; i--) {
			rowNum = i;
			Row row = sheet.getRow(rowNum);
			int maxCell = row.getLastCellNum();
			for (int j = maxCell; j >= 1; j--) {
				colNum = j;
				if (row.getCell(colNum - 1).getStringCellValue().contains(keyword)) {
					rowList.add(rowNum);
					break;
				}
			}
		}
		return rowList;
	}	

	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/downloadFile")
	public Response downloadFile(@Context HttpServletRequest request) throws Exception {

		String uriFile = request.getParameter("uriFile");

		// recupero la password di autenticazione dall header
//		String password = request.getHeader("password");
//
//		if(!checkPassword(password)) {
//			return Response.status(Response.Status.UNAUTHORIZED)
//					.entity("Accesso Negato").type(MediaType.TEXT_PLAIN).build();
//		}

		File fileExtract;
		MimeTypeFirmaBean infoFile;

		if (uriFile != null && !"".equals(uriFile)) {
			try {
				
				fileExtract = DocumentStorage.extract(uriFile, null);				
				
				infoFile = new InfoFileUtility().getInfoFromFile(fileExtract.toURI().toString(), fileExtract.getName(),
						false, null);
			} catch (Exception ex) {
				logger.error("ERRORE SERVIZIO DI SCARICO FILE: " + ex.getMessage(), ex);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Errore durante il recupero del File").type(MediaType.TEXT_PLAIN).build();
			}

			StreamingOutput stream = null;
			try {
				final InputStream in = new FileInputStream(fileExtract);
				stream = new StreamingOutput() {
					public void write(OutputStream out) throws IOException, WebApplicationException {
						try {
							int read = 0;
							byte[] bytes = new byte[1024];

							while ((read = in.read(bytes)) != -1) {
								out.write(bytes, 0, read);
							}
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							throw new WebApplicationException(e);
						}finally {
							if( in != null) {
								in.close();
							}
						}
					}
				};
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			return Response.ok(stream)
					.header("content-disposition", "attachment; filename = " + infoFile.getCorrectFileName()).build();
		}

		return Response.status(Response.Status.BAD_REQUEST).entity("uri mancante").type(MediaType.TEXT_PLAIN).build();
	}
	
//	@POST
//	@Produces({ MediaType.TEXT_PLAIN })
//	@Consumes({ MediaType.APPLICATION_XML })
//	@Path("/riattivaJob")
//	public Response riattivaJob(JobRequest requestBean,
//								@Context HttpServletRequest request) throws Exception {
//		String message = "";
//		BigDecimal idJob;
//		
//		logger.info("RIATTIVAJOB SERVICE - START");
//		
//		// recupero la password di autenticazione dall header
//		String password = request.getHeader("password");
//		if(!checkPassword(password)) {
//			return Response.status(Response.Status.UNAUTHORIZED)
//					.entity("Accesso Negato").type(MediaType.TEXT_PLAIN).build();
//		}
//		
//		if(StringUtils.isNotBlank(requestBean.getIdJob())) {
//			idJob = new BigDecimal(requestBean.getIdJob());
//			logger.info("idJob: " + idJob);
//		}else {
//			logger.error("idJob non specificato");
//			logger.info("RIATTIVAJOB SERVICE - END");
//			return Response.status(Response.Status.BAD_REQUEST).entity("idJob non specificato").type(MediaType.TEXT_PLAIN).build();
//		}
//		
//		try {
//			aggiornaStatoJob(idJob, JOBSTATUS_IN_INIZIALE, "");
//		} catch (Exception e) {
//			logger.error("Errore durante l'aggiornamento di stato per il job: " + idJob + " - errore: " + e.getMessage(), e);
//			logger.info("RIATTIVAJOB SERVICE - END");
//			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore durante l'esecuzione: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
//		}
//		
//		logger.info("RIATTIVAJOB SERVICE - END");
//		return Response.status(Response.Status.OK).entity(message).type(MediaType.TEXT_PLAIN).build();
//		
//	}
	
	protected void aggiornaStatoJob(BigDecimal idJob, String stato, String message) throws Exception {
		JobBean jobBean = getInstanceJobBean(idJob);

		jobBean.setStatus(stato);
		jobBean.setMessage(message);

		switch (stato) {
		case JOBSTATUS_IN_ELABORAZIONE:
			jobBean.setStartTime(new Date());
			break;

		case JOBSTATUS_COMPLETATO:
			jobBean.setEndTime(new Date());
			break;

		case JOBSTATUS_IN_ERRORE:
			jobBean.setEndTime(new Date());
			break;

		case JOBSTATUS_IN_INIZIALE:
			jobBean.setStartTime(null);
			jobBean.setEndTime(null);
			break;

		case JOBSTATUS_DA_RITENTARE:
			jobBean.setEndTime(new Date());
			break;

		default:
			break;
		}

		daoBmtJob.update(jobBean);

	}

	protected JobBean getInstanceJobBean(BigDecimal idJob) throws Exception {
		JobBean jobBean = null;
		
		JobBean filterBean = new JobBean();
		filterBean.setIdJob(idJob);

		TFilterFetch<JobBean> filter = new TFilterFetch<JobBean>();
		filter.setFilter(filterBean);
		
		TPagingList<JobBean> params = daoBmtJob.search(filter);
		
		if(params.getData()!=null && !params.getData().isEmpty()) {
			jobBean = params.getData().get(0);
		}
		
		return jobBean;
	}
	
	private boolean checkPassword(String password) {
//		SecurityWSBean securityWSBean = (SecurityWSBean) SpringAppContext.getContext().getBean("securityWSBean");
		String passwordToAcces = PASSWORD_TO_ACCESS_DEFAULT;

		/*Controllo se nelle config è specificata la password da usare, altrimenti utilizzio quella di default*/
//		if (securityWSBean != null && StringUtils.isNotBlank(securityWSBean.getPassword())) {
//			passwordToAcces = securityWSBean.getPassword();
//		}
		
		if (password != null && !"".equals(password) && password.equals(passwordToAcces)) {
			return true;
		}

		return false;
	}
	
}