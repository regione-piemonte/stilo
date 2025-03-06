package it.eng.core.service.server;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

import it.eng.core.service.bean.rest.RestServiceBean;
import it.eng.core.service.serialization.IServiceSerialize;

/**
 * test servizi upload rest
 * 
 * @author Russo
 *
 */
@Path("/UploadService")
public class UploadRest {

	private static Logger log = Logger.getLogger(ServiceRestStore.class);

	@POST
	@Path("/uploadS")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadStream(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {

		// Given that I have ‘uploadedInputStream’ can I just pass this
		// directly into the second call, below?
		return Response.ok().build();

	}

	@POST
	@Path("testStream")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_XML)
	public String invoke(@Context HttpServletRequest request, @FormDataParam("restservicebean") RestServiceBean bean,
			@FormDataParam("stream") InputStream files) {
		IServiceSerialize serializeutil = null;
		Locale localeFromClient = request.getLocale();
		log.debug("Locale:" + request.getLocale());

		return "ok";
	}

	@POST
	@Path("bodypart")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_XML)
	public String invoke(@Context HttpServletRequest request, @FormDataParam("restservicebean") RestServiceBean bean,
			@FormDataParam("files") List<FormDataBodyPart> files) {
		IServiceSerialize serializeutil = null;
		Locale localeFromClient = request.getLocale();
		log.debug("Locale:" + request.getLocale());
		return "ok";
	}

	/**
	 * upload semplice
	 * 
	 * @param request
	 * @param aFile
	 * @return
	 */
	@POST
	@Path("invoke")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_XML)
	public String invoke(@Context HttpServletRequest request, @FormDataParam("restservicebean") RestServiceBean bean, @FormDataParam("file") File aFile) {
		IServiceSerialize serializeutil = null;
		Locale localeFromClient = request.getLocale();
		log.debug("Locale:" + request.getLocale());
		log.debug("file:" + aFile);
		return "ok";
	}

	// @Path("download")
	// @GET
	// public Response download() throws Exception{
	// return Response.ok(new StreamingOutput() {
	//
	// @Override
	// public void write(OutputStream os) throws IOException,
	// WebApplicationException {
	//
	// InputStream is = new BufferedInputStream(
	// new FileInputStream("c:/test.txt"));
	// byte[] buffer = new byte[4096]; // tweaking this number may increase performance
	// int len;
	// while ((len = is.read(buffer)) != -1)
	// {
	// os.write(buffer, 0, len);
	// }
	// }
	// }
	//
	// , MediaType.APPLICATION_OCTET_STREAM)
	// .header("content-disposition","attachment; filename = file")
	// .build();
	//
	// }

	// @Path("download")
	// @GET
	// public Response getFile() throws Exception {
	// String filepath="C:/VIDEO_E_FOTO/Film/Rapunzel.L.Intreccio.Della.Torre.CD1.avi";
	// return Response.ok( new BufferedInputStream(
	// new FileInputStream(filepath)),MediaType.APPLICATION_OCTET_STREAM)
	// .header("content-disposition","attachment; filename = file")
	// .build();
	// }

	@Path("download")
	@GET
	public Response getFile() throws Exception {
		String filepath = "C:/VIDEO_E_FOTO/Film/Rapunzel.L.Intreccio.Della.Torre.CD1.avi";
		return Response.ok(new File(filepath), MediaType.APPLICATION_OCTET_STREAM).header("content-disposition", "attachment; filename = file").build();
	}
	/*
	 * @Path("multiupload")
	 * 
	 * @POST
	 * 
	 * @Consumes(MediaType.MULTIPART_FORM_DATA)
	 * 
	 * @Produces("text/plain") //non funziona se aggiungi un mapping jersey puoi mettere solo al request //,@FormDataParam("restservicebean") RestServiceBean
	 * rsb, public String registerWebService(@Context HttpServletRequest request) { String responseStatus = "OK"; String candidateName = null;
	 * 
	 * //checks whether there is a file upload request or not if (ServletFileUpload.isMultipartContent(request)) { final FileItemFactory factory = new
	 * DiskFileItemFactory(1000000,new File("c:/temp")); final ServletFileUpload fileUpload = new ServletFileUpload(factory); //final ServletFileUpload
	 * fileUpload = new ServletFileUpload(); try { FileItemIterator iter = fileUpload.getItemIterator(request); while (iter.hasNext()) { FileItemStream item =
	 * iter.next(); String name = item.getFieldName(); InputStream stream = item.openStream(); if (item.isFormField()) { System.out.println("Form field " + name
	 * + " with value " + Streams.asString(stream) + " detected."); } else { System.out.println("File field " + name + " with file name " + item.getName() +
	 * " detected."); // Process the input stream
	 * 
	 * } }
	 * 
	 * 
	 * final List items = fileUpload.parseRequest(request);
	 * 
	 * if (items != null) { final Iterator iter2 = items.iterator(); while (iter2.hasNext()) { final FileItem item = (FileItem) iter2.next(); final String
	 * itemName = item.getName(); final String fieldName = item.getFieldName(); final String fieldValue = item.getString();
	 * 
	 * if (item.isFormField()) { candidateName = fieldValue; System.out.println("Field Name: " + fieldName + ", Field Value: " + fieldValue);
	 * System.out.println("Candidate Name: " + candidateName); } else { // final File savedFile = new File(FILE_UPLOAD_PATH + File.separator // + itemName); //
	 * System.out.println("Saving the file: " + savedFile.getName()); // item.write(savedFile); System.out.println("item:"+itemName); }
	 * 
	 * } } } catch (FileUploadException fue) {
	 * 
	 * fue.printStackTrace(); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } }
	 * 
	 * System.out.println("Returned Response Status: " + responseStatus);
	 * 
	 * return responseStatus; }
	 */

}
