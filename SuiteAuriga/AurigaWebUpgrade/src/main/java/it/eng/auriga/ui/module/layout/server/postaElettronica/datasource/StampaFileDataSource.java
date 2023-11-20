/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.StampaFileBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

@Datasource(id = "StampaFileDataSource")
public class StampaFileDataSource extends AbstractServiceDataSource<StampaFileBean, StampaFileBean> {

	private static Logger mLogger = Logger.getLogger(StampaFileDataSource.class);

	@Override
	public StampaFileBean call(StampaFileBean pInBean) throws Exception {

		return new StampaFileBean();
	}

	public StampaFileBean trasformaFile(StampaFileBean input) {

		InfoFileUtility lInfoFileUtility = new InfoFileUtility();

		List<StampaFileBean> listStampaFileBean = new ArrayList<StampaFileBean>();
		StampaFileBean output = new StampaFileBean();

		// Se è pdf non faccio modifiche, lo porto fuori cosi com'è
		if (input != null && input.getListaAllegati() != null && input.getListaAllegati().size() > 0 && !input.getListaAllegati().isEmpty()) {
			
			List<StampaFileBean> listaAllegati = input.getListaAllegati();
			try {
				for (StampaFileBean allegato : listaAllegati) {
					StampaFileBean lStampaFileBeanItem = new StampaFileBean();
					lStampaFileBeanItem.setNomeFile(allegato.getNomeFile());
					lStampaFileBeanItem.setUri(allegato.getUri());
					lStampaFileBeanItem.setInfoFile(allegato.getInfoFile());
					lStampaFileBeanItem.setRemoteUri(allegato.getRemoteUri());
					if (allegato != null && allegato.getInfoFile() != null && allegato.getInfoFile().isConvertibile()) {
						// Mimetype PDF
						if ("application/pdf".equals(allegato.getInfoFile().getMimetype())) {
							if (allegato.getNomeFile() != null && allegato.getNomeFile().toLowerCase().endsWith(".p7m")) {
								sbustaFilePDf(input, lInfoFileUtility, allegato, lStampaFileBeanItem);
							}
						} else {
							populateUriPdf(input, lInfoFileUtility, allegato, lStampaFileBeanItem);
						}
					}
					listStampaFileBean.add(lStampaFileBeanItem);
				}
				output.setListaAllegati(listStampaFileBean);
			} catch (StorageException e) {
				mLogger.warn(e);
			}
		}

		return output;
	}

	private void sbustaFilePDf(StampaFileBean input, InfoFileUtility lInfoFileUtility, StampaFileBean allegato, StampaFileBean lStampaFileBeanItem)
			throws StorageException {
		File fileTmp = null;
		if (allegato != null && allegato.getInfoFile() != null && allegato.getInfoFile().isConvertibile()) {
			if (allegato.getRemoteUri() != null && !allegato.getRemoteUri()) {
				fileTmp = StorageImplementation.getStorage().extractFile(allegato.getUri());
			} else {
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(allegato.getUri());
				FileExtractedOut out;

				out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);

				fileTmp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
			}

			try {
				InputStream lInputStream = null;
				String displayFilename = allegato.getNomeFile();
				if (displayFilename.toUpperCase().endsWith("P7M") || displayFilename.toUpperCase().endsWith("TSD")) {
					displayFilename = displayFilename.substring(0, displayFilename.length() - 4);
				}
				lInputStream = lInfoFileUtility.sbusta(fileTmp.toURI().toString(), allegato.getNomeFile());

				lStampaFileBeanItem.setUri(StorageImplementation.getStorage().storeStream(lInputStream));
				lStampaFileBeanItem.setNomeFile(displayFilename);

				String improntaPdf = calculateHash(lStampaFileBeanItem.getUri());
				MimeTypeFirmaBean infoFilePdf = new MimeTypeFirmaBean();
				infoFilePdf.setMimetype("application/pdf");
				infoFilePdf.setImpronta(improntaPdf);
				infoFilePdf.setCorrectFileName(lStampaFileBeanItem.getNomeFile());
				lStampaFileBeanItem.setInfoFile(infoFilePdf);
				lStampaFileBeanItem.setRemoteUri(false);

			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
			}

		}
	}

	private void populateUriPdf(StampaFileBean input, InfoFileUtility lInfoFileUtility, StampaFileBean allegato, StampaFileBean lStampaFileBeanItem)
			throws StorageException {
		String fileUrl = null;
		if (allegato != null && allegato.getInfoFile() != null && allegato.getInfoFile().isConvertibile()) {
			if (allegato.getRemoteUri() != null && !allegato.getRemoteUri()) {
				fileUrl = StorageImplementation.getStorage().getRealFile(allegato.getUri()).toURI().toString();
			} else {
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(allegato.getUri());
				FileExtractedOut out;

				out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);

				fileUrl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted())).toURI().toString();
			}

			try {
				String nomeFile = allegato.getInfoFile().getCorrectFileName() != null ? allegato.getInfoFile().getCorrectFileName() : "";
				String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, nomeFile));
				String improntaPdf = calculateHash(uriPdf);
				lStampaFileBeanItem.setNomeFile(FilenameUtils.getBaseName(nomeFile) + ".pdf");
				lStampaFileBeanItem.setUri(uriPdf);
				MimeTypeFirmaBean infoFilePdf = new MimeTypeFirmaBean();
				infoFilePdf.setMimetype("application/pdf");
				infoFilePdf.setImpronta(improntaPdf);
				infoFilePdf.setCorrectFileName(lStampaFileBeanItem.getNomeFile());
				lStampaFileBeanItem.setInfoFile(infoFilePdf);
				lStampaFileBeanItem.setRemoteUri(false);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
			}

		}
	}

	public String calculateHash(String uri) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
			return lInfoFileUtility.calcolaImpronta(fileUrl, "");
		} catch (Exception e) {
			mLogger.error("Errore durante il calcolo dell'impronta: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile calcolare l'impronta del file");
		}
	}

}
