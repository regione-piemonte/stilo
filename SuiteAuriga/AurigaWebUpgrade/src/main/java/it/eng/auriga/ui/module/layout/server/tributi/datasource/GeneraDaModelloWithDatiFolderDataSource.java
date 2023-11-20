/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocLoaddettmodelloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.GeneraDaModelloBean;
import it.eng.auriga.ui.module.layout.shared.util.TipoModelloDoc;
import it.eng.client.DmpkCoreExtractverdoc;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkModelliDocLoaddettmodello;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "GeneraDaModelloWithDatiFolderDataSource")
public class GeneraDaModelloWithDatiFolderDataSource extends AbstractDataSource<GeneraDaModelloBean, GeneraDaModelloBean> {

	private static Logger mLogger = Logger.getLogger(GeneraDaModelloWithDatiFolderDataSource.class);

	public GeneraDaModelloBean caricaModello(GeneraDaModelloBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		boolean flgConvertiInPdf = getExtraparams().get("flgConvertiInPdf") != null && "1".equals(getExtraparams().get("flgConvertiInPdf"));

		DmpkModelliDocLoaddettmodelloBean input = new DmpkModelliDocLoaddettmodelloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdmodelloio(new BigDecimal(bean.getIdModello()));

		DmpkModelliDocLoaddettmodello dmpkModelliDocLoaddettmodello = new DmpkModelliDocLoaddettmodello();
		StoreResultBean<DmpkModelliDocLoaddettmodelloBean> output = dmpkModelliDocLoaddettmodello.execute(getLocale(), loginBean, input);

		GeneraDaModelloBean result = new GeneraDaModelloBean();
		result.setIdModello(output.getResultBean().getIdmodelloio() != null ? output.getResultBean().getIdmodelloio().toString() : null);

		result.setIdDocPrimario(output.getResultBean().getIddocout() != null ? output.getResultBean().getIddocout() : null);

		String nomeFilePrimario = output.getResultBean().getNomeout() + "."
				+ FilenameUtils.getExtension(output.getResultBean().getDisplayfilenamevereldocout());
		result.setNomeFilePrimario(normalizeFilename(nomeFilePrimario));

		if (output.getResultBean().getIddocout() != null) {

			SezioneCache lSezioneCache = null;
			if (bean.getValori() != null && bean.getValori().size() > 0 && bean.getTipiValori() != null && bean.getTipiValori().size() > 0) {
				lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, bean.getValori(), bean.getTipiValori(), getSession());
			} else {
				lSezioneCache = new SezioneCache();
			}

			String xmlSezioneCacheAttributiAdd = null;
			if (lSezioneCache != null) {
				StringWriter lStringWriter = new StringWriter();
				Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
				lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				lMarshaller.marshal(lSezioneCache, lStringWriter);
				xmlSezioneCacheAttributiAdd = lStringWriter.toString();
			}

			DmpkCoreExtractverdocBean inputDoc = new DmpkCoreExtractverdocBean();
			inputDoc.setIddocin(output.getResultBean().getIddocout());
			inputDoc.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

			DmpkCoreExtractverdoc dmpkCoreExtractverdoc = new DmpkCoreExtractverdoc();
			StoreResultBean<DmpkCoreExtractverdocBean> outputDoc = dmpkCoreExtractverdoc.execute(getLocale(), loginBean, inputDoc);

			DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
			DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
			lGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			if(StringUtils.isNotBlank(bean.getIdUd())) {
				lGetdatixgendamodelloInput.setIdobjrifin(bean.getIdFolder() + "+" + bean.getIdUd());
				lGetdatixgendamodelloInput.setFlgtpobjrifin("F+U");
			} else {
				lGetdatixgendamodelloInput.setIdobjrifin(bean.getIdFolder());
				lGetdatixgendamodelloInput.setFlgtpobjrifin("F");
			}
			lGetdatixgendamodelloInput.setNomemodelloin(output.getResultBean().getNomeout());
			lGetdatixgendamodelloInput.setAttributiaddin(xmlSezioneCacheAttributiAdd);

			StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello.execute(getLocale(), loginBean,
					lGetdatixgendamodelloInput);
			
			if (lGetdatixgendamodelloOutput.isInError()) {
				throw new StoreException(lGetdatixgendamodelloOutput);
			}
			
			String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();

			mLogger.debug("Dati folder da iniettare nel modello: " + templateValues);

			if (flgConvertiInPdf) {

				mLogger.debug("Modello da convertire in pdf");

				FileDaFirmareBean fillTemplateBean = ModelliUtil.fillTemplate(bean.getProcessId(), bean.getIdModello(), templateValues, true, getSession());

				result.setUriFilePrimario(fillTemplateBean.getUri());
				result.setNomeFilePrimario(FilenameUtils.getBaseName(result.getNomeFilePrimario()) + ".pdf");

				MimeTypeFirmaBean infoFile = fillTemplateBean.getInfoFile();
				infoFile.setDaScansione(false);
				infoFile.setFirmato(false);
				infoFile.setFirmaValida(false);
				infoFile.setCorrectFileName(result.getNomeFilePrimario());
				result.setInfoFile(infoFile);

			} else {

				FileDaFirmareBean fillTemplateBean = ModelliUtil.fillTemplate(bean.getProcessId(), bean.getIdModello(), templateValues, false, getSession());

				// Se il modello è un odt freemarker ricevo un doc, ma il modello di partenza è un odt
				if (TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equalsIgnoreCase(output.getResultBean().getTipomodelloout())) {
					result.setNomeFilePrimario(FilenameUtils.getBaseName(result.getNomeFilePrimario()) + ".doc");	
				}

				result.setUriFilePrimario(fillTemplateBean.getUri());

				MimeTypeFirmaBean lMimeTypeFirmaBean = fillTemplateBean.getInfoFile();
				lMimeTypeFirmaBean.setCorrectFileName(result.getNomeFilePrimario());
				result.setInfoFile(lMimeTypeFirmaBean);
			}
		}

		return result;

	}

	public String normalizeFilename(String fileName) {
		String newFileName = fileName;
		newFileName = newFileName.replace(" ", "_");
		newFileName = newFileName.replace("\\", "_");
		newFileName = newFileName.replace("/", "_");
		newFileName = newFileName.replace(":", "_");
		newFileName = newFileName.replace("*", "_");
		newFileName = newFileName.replace("?", "_");
		newFileName = newFileName.replace("<", "_");
		newFileName = newFileName.replace(">", "_");
		newFileName = newFileName.replace("|", "_");
		newFileName = newFileName.replace("\"", "_");
		return newFileName;
	}

	@Override
	public GeneraDaModelloBean get(GeneraDaModelloBean bean) throws Exception {

		return null;
	}

	@Override
	public GeneraDaModelloBean add(GeneraDaModelloBean bean) throws Exception {

		return null;
	}

	@Override
	public GeneraDaModelloBean remove(GeneraDaModelloBean bean) throws Exception {

		return null;
	}

	@Override
	public GeneraDaModelloBean update(GeneraDaModelloBean bean, GeneraDaModelloBean oldvalue) throws Exception {

		return null;
	}

	@Override
	public PaginatorBean<GeneraDaModelloBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(GeneraDaModelloBean bean) throws Exception {

		return null;
	}

}
