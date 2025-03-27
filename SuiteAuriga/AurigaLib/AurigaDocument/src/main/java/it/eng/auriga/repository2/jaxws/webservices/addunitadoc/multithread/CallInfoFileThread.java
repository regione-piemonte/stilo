/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.addunitadoc.multithread;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.Callable;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure.AddUdUtils;
import it.eng.document.function.bean.AttachWSBean;

public class CallInfoFileThread implements Callable<AttachWSBean> {
	
	private boolean flgImpresaInUnGiorno; 
	private String xml; 
	private String flgFilePrimario; 
	private int indiceFile; 
	private String displayFileName;
	private File fileAttach;
	private AurigaLoginBean pAurigaLoginBean;
	
    public CallInfoFileThread(boolean flgImpresaInUnGiorno, String xml, int indiceFile, File fileAttach, AurigaLoginBean pAurigaLoginBean, 
    		String displayFileName, String flgFilePrimario) {
		super();
		this.flgImpresaInUnGiorno = flgImpresaInUnGiorno;
		this.xml = xml;
		this.indiceFile = indiceFile;
		this.fileAttach = fileAttach;
		this.displayFileName = displayFileName;
		this.pAurigaLoginBean = pAurigaLoginBean;
		this.flgFilePrimario = flgFilePrimario;
	}

	@Override
    public AttachWSBean call() throws Exception {
        try {
        	AttachWSBean attachWSBean =  AddUdUtils.buildAttachWSBean(fileAttach, xml, indiceFile, flgImpresaInUnGiorno, pAurigaLoginBean);  
        	
        	boolean isValid = AddUdUtils.checkRequiredAttribute(fileAttach.getName(), attachWSBean);
			
			if(!isValid) {
				AddUdUtils.retryCallFileOp(fileAttach, xml, indiceFile, flgImpresaInUnGiorno, pAurigaLoginBean);
			}
			
			attachWSBean.setNumeroAttach(String.valueOf(indiceFile));
			attachWSBean.setDisplayFilename(displayFileName);
			attachWSBean.setFlgFilePrimario(flgFilePrimario);
			
			return attachWSBean;
        } catch (Exception e) {
        	 throw new Exception(e.getMessage(), e);
        }
    }
}
