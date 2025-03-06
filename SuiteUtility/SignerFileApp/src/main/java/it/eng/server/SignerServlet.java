package it.eng.server;

import it.eng.common.constants.Constants;
import it.eng.server.util.CryptoConfiguration;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * manca inizializazione del remote proxy per al classe SignerProxy, l'iniizalizzaizone e' presente
 * nella stessa classe nel progetto SmartCart
 * @author Russo
 *
 */
public class SignerServlet extends HttpServlet {

	public static CryptoConfiguration configuration = new CryptoConfiguration();
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String operationtype = req.getParameter(Constants.OPERATION_TYPE_PARAMETER);
		String hashbean = req.getParameter(Constants.HASHBEAN_PARAMETER);
		SignerUtil util = SignerUtil.getSignerUtil(req.getSession());
		
		if(operationtype == null){
			operationtype = "";
		}
		
		if(operationtype.equals(Constants.OPERATION_TYPE_UPLOAD)){
			try{
				boolean isMultipart = ServletFileUpload.isMultipartContent(req);
				if(isMultipart){
							
					// Create a factory for disk-based file items
					FileItemFactory factory = new DiskFileItemFactory();
		
					// Create a new file upload handler
					ServletFileUpload upload = new ServletFileUpload(factory);
		
					// Parse the request
					List items = upload.parseRequest(req);
					
					// Process the uploaded items
					Iterator iter = items.iterator();
					while (iter.hasNext()) {
					    FileItem item = (FileItem)iter.next();
					    if (!item.isFormField()) {
					    	//Associo il file in sessione
					    	
					        util.addSignerFile(item.getInputStream(), hashbean);
					     }
					}
				}
			}catch(Exception e){
				throw new ServletException(e);
			}
		}else if(operationtype.equals(Constants.OPERATION_TYPE_DOWNLOAD)){
			try{
				File                f        = util.getUnsignerFile(hashbean);
			    int                 length   = 0;
			    ServletOutputStream op       = resp.getOutputStream();
			    ServletContext      context  = getServletConfig().getServletContext();
			    String              mimetype = context.getMimeType(f.getName());
		        resp.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
		        resp.setContentLength( (int)f.length() );
		        resp.setHeader( "Content-Disposition", "attachment; filename=\"" + f.getName() + "\"" );
		        byte[] bbuf = new byte[1024];
		        DataInputStream in = new DataInputStream(new FileInputStream(f));
		        while ((in != null) && ((length = in.read(bbuf)) != -1)){
		        	op.write(bbuf,0,length);
		        }
		        in.close();
			    op.flush();
			    op.close();
			}catch(Exception e){
				throw new ServletException(e);
			}
		}else{
			throw new ServletException("Operazione ["+operationtype+"] non supportata");
		}
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		this.doPost(req, resp);
		
	}
	
	
}
