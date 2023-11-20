/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;

public abstract class RenderingPdfDetail extends CustomDetail implements BackDetailInterface, DownloadInterface {
	
	public enum TIPO_QUADRO {
		QUADRO_AMMINISTRATIVO, QUADRO_PROGRAMMATICO;
	}
	
	public abstract void afterShow(final Record object);
	
	private HTMLFlow htmlFlow;
	private TIPO_QUADRO tipoQuadro;
	private String idProcess;
	private String uriFile;
	private String nomeFile;
	private String mimetype;
	
	public RenderingPdfDetail(String nomeEntita, TIPO_QUADRO tipoQuadro, String idProcess){
		
		super(nomeEntita);
		
		this.tipoQuadro = tipoQuadro;
		this.idProcess = idProcess;
		setHeight100();
		setWidth100();
		htmlFlow = new HTMLFlow();
		htmlFlow.setHeight100();
		htmlFlow.setWidth100();		
		htmlFlow.setContentsType(ContentsType.PAGE);
		addMember(htmlFlow);
		
		loadTipoQuadro();
	}
	
	public RenderingPdfDetail(String nomeEntita, String uriFile, String nomeFile, String idProcess){
		this(nomeEntita, null, uriFile, nomeFile, idProcess, null);
	}
	
	public RenderingPdfDetail(String nomeEntita, String uriFile, String nomeFile, String idProcess, Record record){
		this(nomeEntita, null, uriFile, nomeFile, idProcess, record);
	}
	
	public RenderingPdfDetail(String nomeEntita, String titolo, String uriFile, String nomeFile, String idProcess, Record record){
		
		super(nomeEntita);
		
		this.uriFile = uriFile;
		this.nomeFile = nomeFile;
		this.idProcess =  idProcess;
		
		if (record!=null && record.getAttributeAsString("mimetypeDocTipAssTask")!=null && !record.getAttributeAsString("mimetypeDocTipAssTask").equals("")){
			mimetype = record.getAttributeAsString("mimetypeDocTipAssTask");
		}
		
		setHeight100();
		setWidth100();
		
		if(titolo != null && !"".equals(titolo)) {
			String contents = "<div style='font-weight:bold;font-size:15px;font-style:normal;text-decoration:underline;color:black;padding-top:5;padding-left:5;'>" + titolo + "</div>";						
			Label labelTitolo = new Label(contents);  
			labelTitolo.setAlign(Alignment.LEFT);  
			labelTitolo.setBaseStyle(it.eng.utility.Styles.formTitle);
			labelTitolo.setHeight(2);
			labelTitolo.setPadding(5);
			addMember(labelTitolo);
		}
		
		htmlFlow = new HTMLFlow();
		htmlFlow.setHeight100();
		htmlFlow.setWidth100();		
		htmlFlow.setContentsType(ContentsType.PAGE);
		addMember(htmlFlow);
		
		loadPdfFromUri();
	}
	
	private void loadPdfFromUri()
	{
		Record lRecord = new Record();
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("uriFile", uriFile);
		lRecord.setAttribute("nomeFile", nomeFile);
		lRecord.setAttribute("mimetype",mimetype);
		new GWTRestService<Record, Record>("FillPdfService").call(lRecord, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				previewFile(object);
			}
		});
	}
	
	private void loadTipoQuadro() {
		Record lRecord = new Record();
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("tipoPdf", tipoQuadro.name());
		lRecord.setAttribute("mimetype", mimetype);
		new GWTRestService<Record, Record>("FillPdfService").call(lRecord, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				previewFile(object);
			}
		});
	}

	protected void previewFile(Record object) {
		Record lRecord = new Record();
		lRecord.setAttribute("sbustato", false);
		lRecord.setAttribute("remoteUri", false);
		lRecord.setAttribute("displayFilename", object.getAttribute("nomeFilePdf"));
		lRecord.setAttribute("uri", object.getAttribute("uriFilePdf"));
		String url = GWT.getHostPageBaseURL() + "springdispatcher/preview?fromRecord=true&mimetype=application/pdf&recordType="+DownloadFile.encodeURL("FileToExtractBean")+"&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
		htmlFlow.setContentsURL(url);
		afterShow(object);
	}

	
}
