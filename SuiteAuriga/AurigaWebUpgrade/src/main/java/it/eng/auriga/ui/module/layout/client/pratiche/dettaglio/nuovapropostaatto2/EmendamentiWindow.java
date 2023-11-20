/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class EmendamentiWindow extends ModalWindow {
		
	protected EmendamentiWindow _instance = this;
	protected NuovaPropostaAtto2CompletaDetail _detail;
	
	protected Record recordEmendamenti;
	protected int posEmendamentoCorrente;
	
	protected VLayout portletLayout;
	protected VLayout mainLayout;
	protected ToolStrip detailToolStrip;
		
	protected DynamicForm mDynamicForm;
	protected DynamicForm mButtonForm;
	 
	protected HiddenItem uriFileItem;
	protected HiddenItem nomeFileItem;
	protected TextItem estremiItem;
	protected CKEditorItem testoHtmlItem;
	protected ImgButtonItem fileItem;
		
	protected ImgButtonItem emendamentoPrecButton;
	protected ImgButtonItem emendamentoSuccButton;
	protected ImgButtonItem ritornaListaButton;
	protected ImgButtonItem upDownLevel;
		
	public EmendamentiWindow(String title, String nomeEntita, Canvas parentCanvas, NuovaPropostaAtto2CompletaDetail detail){
		
		super(nomeEntita, false, false);
		
		_detail = detail;
				
		setParentCanvas(parentCanvas);  
		setWidth(600);
		setHeight(500);  
		setTop(50);  
		setLeft(parentCanvas.getVisibleWidth() - 950);  
		setBackgroundColor("#FFFFFF");  
		setVisibility(Visibility.HIDDEN);  
		setAlign(Alignment.CENTER);  
		setAnimateTime(1200); // milliseconds
		setCanDragReposition(true);
		setCanDragResize(true);
		setKeepInParentRect(true);
		setIsModal(false);
		setAutoCenter(false);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		settingsMenu.removeItem(setHomepageMenuItem);
		setTitle(title);
		setIcon("buttons/altreOp.png");
		
		createMainLayout();
		
		portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		portletLayout.addMember(mainLayout);		
		addMember(portletLayout);
	}
		
	protected void createMainLayout() {
		
		mainLayout = new VLayout();  
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		mainLayout.setOverflow(Overflow.VISIBLE);
		
		mButtonForm = new DynamicForm();													
		mButtonForm.setKeepInParentRect(true);
		mButtonForm.setWidth100();
		mButtonForm.setHeight(10);
		mButtonForm.setNumCols(5);
		mButtonForm.setColWidths("1","1","1","1","*");		
		mButtonForm.setCellPadding(5);		
		mButtonForm.setWrapItemTitles(false);
		mButtonForm.setBorder("1px");
		
		emendamentoPrecButton = new ImgButtonItem("emendamentoPrecButton", "buttons/back.png", "Emendamento precedente");
		emendamentoPrecButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				setEmendamentoPrec();				
			}
		});
		
		emendamentoSuccButton = new ImgButtonItem("emendamentoSuccButton", "buttons/forward.png", "Emendamento successivo");
		emendamentoSuccButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				setEmendamentoSucc();				
			}
		});
	
		estremiItem = new TextItem();
		estremiItem.setWidth(259);
		estremiItem.setShowTitle(false);
		estremiItem.setCanEdit(false);
	
		ritornaListaButton = new ImgButtonItem("ritornaListaButton", "visualizzaDati.png", "Lista emendamenti");
		ritornaListaButton.setHeight(10);
		ritornaListaButton.setEndRow(true);
		ritornaListaButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				_instance.hide();
				_detail.apriListaEmendamenti();
			}
		});
		
		upDownLevel = new ImgButtonItem("upDownLevel", "", "");
		upDownLevel.setHeight(10);
		upDownLevel.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				if (recordEmendamenti != null && recordEmendamenti.getAttribute("listaEmendamenti") != null) {
					RecordList listaEmendamenti = recordEmendamenti.getAttributeAsRecordList("listaEmendamenti");
					if (posEmendamentoCorrente < listaEmendamenti.getLength()) {
						Record emendamento = listaEmendamenti.get(posEmendamentoCorrente);
						Record lRecordEmendamenti = new Record();
						//TODO è corretto che se è valorizzato nroSubEmendamento si chiami Lista emendamenti?
						if (emendamento.getAttribute("nroSubEmendamento") != null && !"".equals(emendamento.getAttributeAsString("nroSubEmendamento"))) {
							String nroEmendamento = emendamento.getAttributeAsString("nroEmendamento");
							if (recordEmendamenti.getAttribute("parentListaEmendamenti") != null) {
								lRecordEmendamenti.setAttribute("listaEmendamenti", new RecordList(recordEmendamenti.getAttributeAsRecordArray("parentListaEmendamenti")));
							} else {
								lRecordEmendamenti.setAttribute("listaEmendamenti", listaEmendamenti);
							}
							lRecordEmendamenti.setAttribute("posEmendamento", Integer.parseInt(nroEmendamento) - 1);							
							_instance.setTitle("Lista emendamenti");
						} else {
							RecordList listaSubEmendamenti = new RecordList(emendamento.getAttributeAsRecordArray("listaSubEmendamenti"));
							lRecordEmendamenti.setAttribute("listaEmendamenti", listaSubEmendamenti);
							lRecordEmendamenti.setAttribute("parentListaEmendamenti", listaEmendamenti);
							lRecordEmendamenti.setAttribute("posEmendamento", 0);
							_instance.setTitle("Lista sub-emendamenti");
						}						
						initContent(lRecordEmendamenti);
					}
				}
			}
		});
		
		mButtonForm.setItems(emendamentoPrecButton, emendamentoSuccButton, estremiItem, upDownLevel, ritornaListaButton);
		
		mDynamicForm = new DynamicForm();													
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths("*","1","1","1","1");		
		mDynamicForm.setCellPadding(5);		
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setBorder("1px");
		
		nomeFileItem = new HiddenItem("nomeFile");
		uriFileItem = new HiddenItem("uriFile");
		
//		testoEmendamentoItem = new TextAreaItem();
//		testoEmendamentoItem.setWidth("100%");
//		testoEmendamentoItem.setHeight("*");
//		testoEmendamentoItem.setShowTitle(false);
//		testoEmendamentoItem.setStartRow(true);
//		testoEmendamentoItem.setCanEdit(false);
//		testoEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				return !emendamentoSuFile();
//			}
//		});
		
		testoHtmlItem = new CKEditorItem("testoHtml", -1, "STANDARD", "96%", -1, "", false, true);
		testoHtmlItem.setWidth("100%");
		testoHtmlItem.setHeight("100%");
		testoHtmlItem.setShowTitle(false);
		testoHtmlItem.setStartRow(true);
		testoHtmlItem.setCanEdit(false);

		fileItem = new ImgButtonItem("", "pratiche/task/buttons/scaricaPdf.png", "Scarica emendamento");
		fileItem.setStartRow(true);
		fileItem.setEndRow(true);
		fileItem.setHeight(10);
		fileItem.setVisible(uriFileItem.getValue() != null && !"".equals(uriFileItem.getValue()));
		
		fileItem.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record lRecord = new Record();
				lRecord.setAttribute("displayFilename",  mDynamicForm.getValueAsString("nomeFile"));
				lRecord.setAttribute("uri",  mDynamicForm.getValueAsString("uriFile"));
				lRecord.setAttribute("sbustato", "false");
				lRecord.setAttribute("remoteUri", true);
				DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
			}
		});
		
		mDynamicForm.setItems(nomeFileItem, uriFileItem, testoHtmlItem, fileItem);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.addMember(mButtonForm);
		layout.addMember(mDynamicForm);
		mainLayout.addMember(layout);	
		
	}
	
	public void initContent(Record recordEmendamenti) {
		Integer posEmendamento = recordEmendamenti.getAttributeAsInt("posEmendamento") != null ? recordEmendamenti.getAttributeAsInt("posEmendamento") : 0;
		this.recordEmendamenti = recordEmendamenti;		

		setPosEmendamentoToShow(posEmendamento);
		
	}
	
	public void setPosEmendamentoToShow(int posEmendamento) {
		if (recordEmendamenti != null && recordEmendamenti.getAttribute("listaEmendamenti") != null) {
			RecordList listaEmendamenti = recordEmendamenti.getAttributeAsRecordList("listaEmendamenti");
			if (posEmendamento < listaEmendamenti.getLength()) {
				Record emendamento = listaEmendamenti.get(posEmendamento);
				
				if (emendamento.getAttribute("nroSubEmendamento") != null && !"".equals(emendamento.getAttribute("nroSubEmendamento"))) {
					estremiItem.setValue("Emendamento n° " + emendamento.getAttribute("nroEmendamento") + " / " + emendamento.getAttribute("nroSubEmendamento"));
					if (recordEmendamenti.getAttribute("parentListaEmendamenti") != null) {
						upDownLevel.setIcon("buttons/up.png");
						upDownLevel.setPrompt("Emendamenti");
						upDownLevel.redraw();
						upDownLevel.show();
					} else {
						upDownLevel.hide();
					}
				} else {
					estremiItem.setValue("Emendamento n° " + emendamento.getAttribute("nroEmendamento"));
					if (emendamento.getAttribute("listaSubEmendamenti") != null && emendamento.getAttributeAsRecordArray("listaSubEmendamenti").length > 0) {
						upDownLevel.setIcon("buttons/down.png");
						upDownLevel.setPrompt("Sub-emendamenti");
						upDownLevel.redraw();
						upDownLevel.show();
					} else {
						upDownLevel.hide();
					}
				}
				
				String testoHtmlDaSettare = emendamento.getAttribute("testoHtml");
				if (testoHtmlDaSettare == null || "".equalsIgnoreCase(testoHtmlDaSettare)) {
					testoHtmlDaSettare = "Testo non disponibile";
				}
				testoHtmlItem.setValue(testoHtmlDaSettare);
				nomeFileItem.setValue(emendamento.getAttribute("nomeFile"));
				uriFileItem.setValue(emendamento.getAttribute("uriFile"));
				posEmendamentoCorrente = posEmendamento;
				// testoHtmlItem.setVisible(emendamento.getAttribute("testoHtml") != null && !"".equalsIgnoreCase(emendamento.getAttribute("testoHtml")));
				// fileItem.setVisible(emendamento.getAttribute("uriFileEmendamento") != null && !"".equalsIgnoreCase(emendamento.getAttribute("uriFileEmendamento")));
				mDynamicForm.markForRedraw();
			}
		}
		
	}
	
	private void setEmendamentoSucc() {
		if (recordEmendamenti.getAttribute("listaEmendamenti") != null) {
			RecordList listaEmendamenti = recordEmendamenti.getAttributeAsRecordList("listaEmendamenti");
			if (posEmendamentoCorrente < listaEmendamenti.getLength() - 1) {
				setPosEmendamentoToShow(posEmendamentoCorrente + 1);	
			} else {
				setPosEmendamentoToShow(0);	
			}
		}
	}
	
	private void setEmendamentoPrec() {
		if (recordEmendamenti.getAttribute("listaEmendamenti") != null) {
			RecordList listaEmendamenti = recordEmendamenti.getAttributeAsRecordList("listaEmendamenti");
			if (posEmendamentoCorrente > 0) {
				setPosEmendamentoToShow(posEmendamentoCorrente - 1);	
			} else {
				setPosEmendamentoToShow(listaEmendamenti.getLength() - 1);	
			}
		}
	}
	
	private boolean emendamentoSuFile() {
		String uriFileEmendamento = mDynamicForm.getValueAsString("uriFileEmendamento");
		return (uriFileEmendamento != null && !"".equalsIgnoreCase(uriFileEmendamento));
	}
	
	@Override
	public void manageOnCloseClick() {
		_instance.hide(); 
	}
	
}