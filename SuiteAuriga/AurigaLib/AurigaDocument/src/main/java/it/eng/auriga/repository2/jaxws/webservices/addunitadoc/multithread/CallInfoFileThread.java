/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.concurrent.Callable;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.AttachWSBean;
import it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure.AddUdUtils;

public class CallInfoFileThread implements Callable<AttachWSBean> {
	
	private boolean flgImpresaInUnGiorno; 
	private String xml; 
	private int indiceFile; 
	private File fileAttach;
	private AurigaLoginBean pAurigaLoginBean;
	
    public CallInfoFileThread(boolean flgImpresaInUnGiorno, String xml, int indiceFile, File fileAttach, AurigaLoginBean pAurigaLoginBean) {
		super();
		this.flgImpresaInUnGiorno = flgImpresaInUnGiorno;
		this.xml = xml;
		this.indiceFile = indiceFile;
		this.fileAttach = fileAttach;
		this.pAurigaLoginBean = pAurigaLoginBean;
	}

	@Override
    public AttachWSBean call() throws Exception {
        try {
        	AttachWSBean attachWSBean =  AddUdUtils.buildAttachWSBean(fileAttach, xml, indiceFile, flgImpresaInUnGiorno, pAurigaLoginBean);  
        	
        	boolean isValid = AddUdUtils.checkRequiredAttribute(fileAttach.getName(), attachWSBean);
			
			if(!isValid) {
				AddUdUtils.retryCallFileOp(fileAttach, xml, indiceFile, flgImpresaInUnGiorno, pAurigaLoginBean);
			}
			
			return attachWSBean;
        } catch (Exception e) {
        	 throw new Exception(e.getMessage(), e);
        }
    }
}
