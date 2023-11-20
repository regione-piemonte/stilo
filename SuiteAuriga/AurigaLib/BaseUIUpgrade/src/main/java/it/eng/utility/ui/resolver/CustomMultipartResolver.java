/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.listener.UploadProgressListener;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Component
public class CustomMultipartResolver extends CommonsMultipartResolver {

	private static ThreadLocal<UploadProgressListener> progressListener = new ThreadLocal<UploadProgressListener>();
	private static Logger mLogger = Logger.getLogger(CustomMultipartResolver.class);

	@Override
	public void cleanupMultipart(MultipartHttpServletRequest request) {
		super.cleanupMultipart(request);
	}



	@Override
	protected DiskFileItemFactory newFileItemFactory() {
		
		return super.newFileItemFactory();
	}

	@Override
	public void setUploadTempDir(Resource uploadTempDir) throws IOException {
		
		super.setUploadTempDir(uploadTempDir);
	}

	@Override
	protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		DiskFileItemFactory lFileItemFactory = new DiskFileItemFactory();
		try {
			CustomMultipartProperty lCustomMultipartProperty = SpringAppContext.getContext().getBean(CustomMultipartProperty.class);
			if (lCustomMultipartProperty.getTempRepository()==null
					|| lCustomMultipartProperty.getTempRepository().trim().equals("")){
				return super.newFileUpload(fileItemFactory);
			}
			if (lCustomMultipartProperty.getThreshold()!=null)
				lFileItemFactory.setSizeThreshold(lCustomMultipartProperty.getThreshold().intValue());
			lFileItemFactory.setRepository(new File(lCustomMultipartProperty.getTempRepository()));
			FileUpload fileUpload =  new ServletFileUpload(lFileItemFactory);
			fileUpload.setProgressListener(progressListener.get());
			return fileUpload;
		} catch (Exception e) {
			mLogger.error("Contesto Spring ancora non inizalizzato");
			return super.newFileUpload(fileItemFactory);
			
		}
	}

	@Override
	public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
		progressListener.set(new UploadProgressListener(request));
		return super.resolveMultipart(request);
	}

}