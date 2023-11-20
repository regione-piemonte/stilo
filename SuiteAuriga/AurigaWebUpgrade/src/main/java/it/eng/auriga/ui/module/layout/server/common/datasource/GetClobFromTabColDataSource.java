/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetclobfromtabcolBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.BlobBean;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.DownloadFileBean;
import it.eng.client.DmpkUtilityGetclobfromtabcol;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="GetClobFromTabColDataSource")
public class GetClobFromTabColDataSource extends AbstractFetchDataSource<BlobBean>{

	private static final Logger log = Logger.getLogger(GetClobFromTabColDataSource.class);

	public BlobBean getBlob(DownloadFileBean downloadFileBeanIn) throws Exception {
		
		String nomeTabella = downloadFileBeanIn.getNomeTabella();
		String nomeColonna = downloadFileBeanIn.getNomeColonna();
		String rowIdRecord = downloadFileBeanIn.getRowIdRecord();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo input
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());
		
		DmpkUtilityGetclobfromtabcolBean input = new DmpkUtilityGetclobfromtabcolBean();
		input.setNometabellain(nomeTabella);
		input.setNomecolonnain(nomeColonna);
		input.setRowidrecin(rowIdRecord);
		
		// Eseguo il servizio
		DmpkUtilityGetclobfromtabcol lservice = new DmpkUtilityGetclobfromtabcol();
		StoreResultBean<DmpkUtilityGetclobfromtabcolBean> lStoreResultBean = lservice.execute(getLocale(), lSchemaBean, input);
		
		if (lStoreResultBean.isInError()) {
			throw new StoreException(lStoreResultBean);
		} 

		// leggo output
		BlobBean retValue = new BlobBean();
		
		if(StringUtils.isNotBlank(lStoreResultBean.getResultBean().getClobout())) {			
			retValue.setBlobValue(lStoreResultBean.getResultBean().getClobout());
		}
		
		return retValue;
	}
	
	public BlobBean getClobFromTabCol(DownloadFileBean downloadFileBeanIn,AurigaLoginBean lLoginBean) throws Exception {
		
		String nomeTabella = downloadFileBeanIn.getNomeTabella();
		String nomeColonna = downloadFileBeanIn.getNomeColonna();
		String rowIdRecord = downloadFileBeanIn.getRowIdRecord();
		
		// Inizializzo input
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(lLoginBean.getSchema());
		
		DmpkUtilityGetclobfromtabcolBean input = new DmpkUtilityGetclobfromtabcolBean();
		input.setNometabellain(nomeTabella);
		input.setNomecolonnain(nomeColonna);
		input.setRowidrecin(rowIdRecord);
		
		// Eseguo il servizio
		DmpkUtilityGetclobfromtabcol lservice = new DmpkUtilityGetclobfromtabcol();
		StoreResultBean<DmpkUtilityGetclobfromtabcolBean> lStoreResultBean = lservice.execute(getLocale(), lSchemaBean, input);
		
		if (lStoreResultBean.isInError()) {
			throw new StoreException(lStoreResultBean);
		} 

		// leggo output
		BlobBean retValue = new BlobBean();
		
		if(StringUtils.isNotBlank(lStoreResultBean.getResultBean().getClobout())) {			
			retValue.setBlobValue(lStoreResultBean.getResultBean().getClobout());
		}
		
		return retValue;
	}
	
	public DownloadFileBean downloadBlob(DownloadFileBean downloadFileBeanIn) throws Exception {
		
		String nomeFile    = downloadFileBeanIn.getNomeFile();
		String extFile     = downloadFileBeanIn.getExtFile();
			
		BlobBean blobOut = new BlobBean();
		
		// legge il blob
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		blobOut = getClobFromTabCol(downloadFileBeanIn,loginBean);
		
		DownloadFileBean resultDownloadFileBean = new DownloadFileBean();
		
		//Salvo il blob in un file temporaneo
		if (blobOut.getBlobValue() != null && !blobOut.getBlobValue().equalsIgnoreCase("")){
			String uriTemp = StorageImplementation.getStorage().storeStream(IOUtils.toInputStream(blobOut.getBlobValue().toString()));
			resultDownloadFileBean.setUriFile(uriTemp);
			resultDownloadFileBean.setNomeFile(nomeFile+extFile);
			resultDownloadFileBean.setExtFile(extFile);
		}
		else{
			resultDownloadFileBean.setMessage("Il file e' vuoto");
		}
			
		return resultDownloadFileBean;
	}
	
	public DownloadFileBean downloadBlob(DownloadFileBean downloadFileBeanIn,AurigaLoginBean lLoginBean) throws Exception {
		
		String nomeFile    = downloadFileBeanIn.getNomeFile();
		String extFile     = downloadFileBeanIn.getExtFile();
			
		BlobBean blobOut = new BlobBean();
		
		// legge il blob
		blobOut = getClobFromTabCol(downloadFileBeanIn,lLoginBean);
		
		DownloadFileBean resultDownloadFileBean = new DownloadFileBean();
		
		//Salvo il blob in un file temporaneo
		if (blobOut.getBlobValue() != null && !blobOut.getBlobValue().equalsIgnoreCase("")){
			resultDownloadFileBean.setMessage(blobOut.getBlobValue().toString());
			resultDownloadFileBean.setNomeFile(nomeFile+extFile);
			resultDownloadFileBean.setExtFile(extFile);
		}
		else{
			resultDownloadFileBean.setMessage("Il file e' vuoto");
		}
			
		return resultDownloadFileBean;
	}
	
	protected void deleteLocalCopy(File localCopy) {
		try {
			FileDeleteStrategy.FORCE.delete(localCopy);
		} catch (Exception e) {
			log.error(String.format("Non è stato possibile cancellare il file %1$s, si è verificata la seguente eccezione", localCopy.getAbsoluteFile()), e);
		}
	}

	@Override
	public PaginatorBean<BlobBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}
	
}