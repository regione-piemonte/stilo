/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.Layout;

public class CKEditor extends Canvas {
	
	private boolean initialized = false;
	private boolean initReadOnly = false;
	private boolean initVisible = true;	
	private String initValue = "";
	private String fontfamily;
	private String fontsize;
	private boolean showSorgente = false;
	private boolean showIndentButtons = false;
	private String id;
	private int numMaxCaratteri;
	private String tipoEditor;
	private String altezza;
	private String larghezza;
	private String defaultValue;
	private boolean upperCase;
	private boolean hideBorder;
	
	public CKEditor(String id, final int numMaxCaratteri, final String tipoEditor, final String altezzaInPixel, final String larghezza, final String defaultValue) {
		this(id, numMaxCaratteri, tipoEditor, altezzaInPixel, larghezza, defaultValue, false, false);
	}
	
	public CKEditor(String id, final int numMaxCaratteri, final String tipoEditor, final String altezza, final String larghezza, final String defaultValue, final boolean upperCase, final boolean hideBorder) {
		super(id);
		this.id = id;
		this.numMaxCaratteri = numMaxCaratteri;
		this.tipoEditor = tipoEditor;
		this.altezza = altezza;
		this.larghezza = larghezza;
		this.defaultValue = defaultValue;
		this.upperCase = upperCase;
		this.hideBorder = hideBorder;
		
		fontfamily = AurigaLayout.getParametroDB("CKEDITOR_FONT_TYPE");
		fontsize = AurigaLayout.getParametroDB("CKEDITOR_FONT_SIZE");

		setOverflow(Overflow.VISIBLE);
		setCanDragResize(false);
		setRedrawOnResize(false);
		setZIndex(0);
//		setAutoDraw(true);
		showSorgente = Layout.isPrivilegioAttivo("ASW");
//		showSorgente = true;
		showIndentButtons = AurigaLayout.getParametroDBAsBoolean("ATTIVA_INDENTAZIONE_CKEDITOR");
		addDrawHandler(new DrawHandler() {
			
			@Override
			public void onDraw(DrawEvent event) {
				init(numMaxCaratteri, tipoEditor, altezza, larghezza, defaultValue, showSorgente, showIndentButtons, fontfamily, fontsize, upperCase, hideBorder);				
			}
		});
	}
	
	protected void close() {
		close(getID()+"_ta");
	}
	
	protected native void close(String ckEditorId)/*-{
		if($wnd.CKEDITOR.instances[ckEditorId]) {
			//$wnd.CKEDITOR.instances[ckEditorId].removeAllListeners();
			$wnd.CKEDITOR.instances[ckEditorId].destroy();
			//$wnd.CKEDITOR.instances[ckEditorId] = null;
		} 
	}-*/;
	
	@Override
	public String getInnerHTML() {
		return "<div id='" + this.getID() + "_ta' style='width:100%;height:100%;margin-bottom:15px;'></div>";
	}

	// nelle configurazioni iniziali bisogna usare le "virgolette" e non il singolo 'apice'
	protected native void init(int numMaxCaratteri, String tipoEditor, String altezza, String larghezza, String defaultValue, boolean showSorgente, boolean showIndentButtons,
			String fontfamily, String fontsize, boolean upperCase, boolean hideBorder)/*-{
		if(!this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::initialized) {
			var toolbar;
			var extraPlugins;
			var removeButtons;
			
			var showCharCount = false;
			if (numMaxCaratteri > -1){
				showCharCount = true;
			}	
			
			var editorId = this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::getID()() + "_ta";
			switch (tipoEditor.toUpperCase()) {			
				case "TRASPARENZA":
				toolbar = [
						{ name: "document", items: [ "Source", "-", "Undo", "Redo" ] },
						{ name: "clipboard", items: [ "Cut", "Copy", "-", "Table" ] },
						{ name: 'links', items: [ 'Link', 'Unlink'] },
						{ name: "basicstyles", items: [ "Bold", "Italic", "Underline", "Strike", "FontSize", "-", "Subscript", "Superscript" ] },
						{ name: "paragraph", items: [ "NumberedList", "BulletedList", "-", "JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyBlock", "-", "Outdent", "Indent", "-", "PageBreak" ] },				
					];
				extraPlugins = "table,tabletools,tableselection,tableresize,liststyle,wordcount,notification,font,pagebreak";
				break;
				case "RESTRICTED":
				toolbar = [
						{ name: "basicstyles", items: [ "Strike", "-", "Subscript", "Superscript"] },
						{ name: "paragraph", items: [ "JustifyLeft", "JustifyBlock" ] }				
						];
		    	extraPlugins = "wordcount,notification";
			    break;
				case "EXTENDED":
				toolbar = [
						{ name: "document", items: [ "Source", "-", "Undo", "Redo" ] },
						//{ name: "clipboard", items: [ "Cut", "Copy", "Paste", "PasteText", "PasteFromWord", "-", "Table" ] },
						{ name: "clipboard", items: [ "Cut", "Copy", "-", "Table" ] },
						{ name: "basicstyles", items: [ "Bold", "Italic", "Underline", "Strike", "FontSize", "-", "Subscript", "Superscript" ] },
						{ name: "paragraph", items: [ "NumberedList", "BulletedList", "-", "JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyBlock", "-", "Outdent", "Indent", "-", "PageBreak" ] },				
						{ name: "colors", items: [ "TextColor", "BGColor" ] },
					];
				extraPlugins = "table,tabletools,tableselection,tableresize,liststyle,wordcount,notification,colorbutton,font,pagebreak";
			    break;
				case "STANDARD":
				toolbar = [
						{ name: "document", items: [ "Source", "-", "Undo", "Redo" ] },
						//{ name: "clipboard", items: [ "Cut", "Copy", "Paste", "PasteText", "PasteFromWord", "-", "Table" ] },
						{ name: "clipboard", items: [ "Cut", "Copy", "-", "Table" ] },
						{ name: "basicstyles", items: [ "Bold", "Italic", "Underline", "Strike", "FontSize", "-", "Subscript", "Superscript" ] },
						{ name: "paragraph", items: [ "NumberedList", "BulletedList", "-", "JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyBlock", "-", "Outdent", "Indent", "-", "PageBreak" ] },				
					];
				extraPlugins = "table,tabletools,tableselection,tableresize,liststyle,wordcount,notification,font,pagebreak";
				break;
				default:
				toolbar = [
						{ name: "document", items: [ "Source", "-", "Undo", "Redo" ] },
						//{ name: "clipboard", items: [ "Cut", "Copy", "Paste", "PasteText", "PasteFromWord", "-", "Table" ] },
						{ name: "clipboard", items: [ "Cut", "Copy", "-", "Table" ] },
						{ name: "basicstyles", items: [ "Bold", "Italic", "Underline", "Strike", "FontSize", "-", "Subscript", "Superscript" ] },
						{ name: "paragraph", items: [ "NumberedList", "BulletedList", "-", "JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyBlock", "-", "Outdent", "Indent", "-", "PageBreak" ] },				
					];
				extraPlugins = "table,tabletools,tableselection,tableresize,liststyle,wordcount,notification,font,pagebreak";
				break;
				
			}
			
			if (showSorgente) {
				removeButtons = "RemoveFormat,Anchor,Styles,Specialchar"
			} else {
				removeButtons = "Source,RemoveFormat,Anchor,Styles,Specialchar"
			}	
			
			if (!showIndentButtons) {
				removeButtons = removeButtons + ",Outdent,Indent"
			}
			
			$wnd.CKEDITOR.replace(editorId, {
				height: altezza,
				width: larghezza,
	            resize_enabled: false,
	            // Use a base zIndex of 300,000 for the dialogs such as the Insert Special Character dialog.
	            baseFloatZIndex: 1000000,
	            // Disable these plugins because they cause the CKEditor widget to grow outside of the canvas' bounds.
	            removePlugins: "autogrow,resize,maximize,div,elementspath",
	            extraPlugins: extraPlugins,
				// Toolbar adjustments to simplify the editor.
				toolbar: toolbar,
				// Remove the redundant buttons from toolbar groups defined above.
				removeButtons: removeButtons,
				
				//funzionalità che permette di mantenere la size del testo quando si fa copia/incolla da file
				pasteFromWordRemoveStyles: false, 
				//pasteFromWordRemoveFontStyles: false, 
				
				wordcount : {
					showParagraphs: false,
	    			showWordCount: false,
				    showCharCount: showCharCount,
				    maxCharCount: numMaxCaratteri,
				    countSpacesAsChars: true
				}

	        });
	        
	        // INIZIO CORREZIONE BUG MODAL WINDOW
	        // Quando CKEditor è dentro ad una modal window GWT blocca l'iterazione con tutte le pop up del CKEditor 
	        // (ad esempio le proprietà delle tabelle o la creazione di link)
	        var me = this;
	    	$wnd.CKEDITOR.on('dialogDefinition', function (evt) {
	        	var dialog = evt.data.definition.dialog;
	        	dialog.on('show', function () {
	        		
	         		me.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::onDialogShow()();
	               	var element = this.getElement();
	               	var labelledby = element.getAttribute('aria-labelledby');
	               	var nativeElement = $wnd.document.querySelector("[aria-labelledby='" + labelledby + "']");
	               	nativeElement.onclick = function (evt) {
	                	if ((evt.target.tagName == "INPUT" || evt.target.tagName == "SELECT" || evt.target.tagName == "TEXTAREA") && -1 != evt.target.className.indexOf("cke_dialog_ui_input")) {
	                       evt.target.focus();
	                  	};
	               	}
	         	});
	     	});
	     	// FINE CORREZIONE BUG MODAL WINDOW
			
			var currentInstance = $wnd.CKEDITOR.instances[editorId];
//			// addContentsCss all'istanza altrimenti modifica il css globale del ckeditor        
			
			if (fontfamily != null ) {
	        	$wnd.CKEDITOR.addCss('.cke_editable{font-family:'+ fontfamily +';}');
	        }
	        
	        if (fontsize != null) {
	        	$wnd.CKEDITOR.addCss('.cke_editable{font-size:' + fontsize + ';}');
	        }
	        var self = this;	  

	        currentInstance.on("instanceReady", function(event) {
				self.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::onInstanceReady()();
				if(currentInstance){
					if (altezza != null && altezza.indexOf('%') != -1) {
						currentInstance.element.$.parentElement.style.height = altezza;
						if (currentInstance.element.$.nextSibling) {
							currentInstance.element.$.nextSibling.style.height = altezza;
							if (hideBorder) {
								currentInstance.element.$.nextSibling.style.border = 'none';
							}
							if (currentInstance.element.$.nextSibling.children[1]) {
								currentInstance.element.$.nextSibling.children[1].style.height = '100%';
								if (hideBorder) {
									currentInstance.element.$.nextSibling.children[1].children[0].style.border = '1px solid #d1d1d1';
								}
							}
						}
					}
				}
			});
			
			currentInstance.on("blur", function(event) {
				self.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::onBlur()();
			});
			
			currentInstance.on("change", function(event) {
				self.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::onChange()();
			});
			
			currentInstance.on("key", function(event) {
				console.log(event.data.keyCode);
				
				if ($wnd.Browser.isFirefox) {
					console.log(String.fromCharCode(event.data.keyCode));
				}
				
				if (upperCase && event.data.domEvent.$.type ==  "keydown") {
					
						var textIn;
						var keyCode1, keyCode2, keyCode3;
						if ($wnd.Browser.isFirefox && $wnd.Browser.version<50) {
							textIn = String.fromCharCode(event.data.keyCode);
						} else if ($wnd.Browser.isFirefox && $wnd.Browser.version>50) {
							textIn = event.data.domEvent.$.key;
							keyCode1 = 59;
							keyCode2 = 160;
							keyCode3 = 2228283;
						} else {
							textIn = event.data.domEvent.$.key;
							keyCode1 = 186;
							keyCode2 = 221;
							keyCode3 = 2228410;
						}
						
						if ((event.data.keyCode < 91 && event.data.keyCode > 64) || (event.data.keyCode <=2228314 && event.data.keyCode >=2228289)) {
							event.cancel();
							var textInUpper = textIn.toUpperCase();
							currentInstance.insertText(textInUpper);
						} else if ((event.data.keyCode === 222 || event.data.keyCode === keyCode1 || event.data.keyCode === 192 || 
							event.data.keyCode === keyCode2 || event.data.keyCode === 191 || event.data.keyCode === keyCode3)) {
							event.cancel();
							var textConvertedCharacters = convertCharacters(textIn);
							currentInstance.insertText(textConvertedCharacters.toUpperCase());
						}
					
				}

			});
			
			currentInstance.on("paste", function(event) {
				console.log(event.data.dataValue);
				event.cancel();
				var textIn =  event.data.dataValue;
				
				textIn = @it.eng.auriga.ui.module.layout.client.editor.CKEditor::correggiElenchiPuntati(Ljava/lang/String;)(textIn);
				textIn = @it.eng.auriga.ui.module.layout.client.editor.CKEditor::correggiStileElenchiPuntati(Ljava/lang/String;)(textIn);
				
				if (upperCase && event.data.dataValue != null && event.data.dataValue != "") {
					var textOut = textIn.toUpperCase();
					var textOutConvertedCharacters = convertCharacters(textOut);
					textOutConvertedCharacters = textOutConvertedCharacters.replace(/&nbsp;/gi, "&nbsp;");
					textOutConvertedCharacters = textOutConvertedCharacters.replace(/&ensp;/gi, "&ensp;");
					// Usare insertHtml mi evita di dover convertire le entity html
					currentInstance.insertHtml(textOutConvertedCharacters);
				}else{
					currentInstance.insertHtml(textIn);
				}
			});
						
			function convertCharacters(string) {
				conv_map = {
					'À': 'A\'', 'à': 'a\'', 'Á': 'A\'', 'á': 'a\'',
					'È': 'E\'', 'è': 'e\'', 'É': 'E\'', 'é': 'e\'',
					'Ì': 'I\'', 'ì': 'i\'', 'Í': 'I\'', 'í': 'i\'',
					'Ò': 'O\'', 'ò': 'o\'', 'Ó': 'O\'', 'ó': 'o\'',
					'Ù': 'U\'', 'ù': 'u\'', 'Ú': 'U\'', 'ú': 'u\''
				};
			 
				for (var i in conv_map) {
					string = string.replace(new RegExp(i, "g"), conv_map[i]);
				}
				return string;
			}
			
		}
	}-*/;
	
	protected native void onInstanceReady()/*-{
		this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::initialized = true;
		var initReadOnly = this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::initReadOnly;
	    this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::setReadOnly(Z)(initReadOnly);
	    var initValue = this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::initValue;
	   	this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::setValue(Ljava/lang/String;)(initValue);
	    var initVisible = this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::initVisible;
	   	this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::setVisible(Z)(initVisible);
//	   	var ckEditorId = this.@it.eng.auriga.ui.module.layout.client.editor.CKEditor::getID()() + "_ta";
//	   	$wnd.CKEDITOR.instances[ckEditorId].config.wordcount = "{showWordCount: true, showCharCount: false, maxWordCount: 4, maxCharCount: 10};"
	   	
	}-*/;
	
	protected void onBlur() {
		// getValue() ritorna "" se non presente testo
//		if (!AurigaLayout.getParametroDBAsBoolean("ATTIVA_SOST_BR_HTML_MODELLI") && htmlContainsBrTag(this.getValue())) {
//			String newValue = this.getValue().replaceAll("<br />", " ");
//			newValue = newValue.replaceAll("<br/>", " ");
//			newValue = newValue.replaceAll("<br>", " ");
//			this.setValue(newValue);
//			SC.warn(I18NUtil.getMessages().ckEditorHtmlCorrection_message());
//		}
	}
	
	private void onChange() {
		// TODO Auto-generated method stub
		fireEvent(new ChangedEvent(this.getJsObj()));
	}
		
	public boolean htmlContainsBrTag(String value) {
		return value.contains("<br />") || value.contains("<br/>") || value.contains("<br>");
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setReadOnly(boolean readOnly) {
		if(isInitialized()) {
			initReadOnly = false;
			if(isCKEditorReady(getID()+"_ta")) {
				setCKEditorReadOnly(getID()+"_ta", readOnly);
			} else {
				setCKEditorReadOnlyOnStartup(getID()+"_ta", readOnly);
			}
		} else {
			initReadOnly = readOnly;
		}
	}
	
	private native boolean isCKEditorReady(String ckEditorId)/*-{
		if($wnd.CKEDITOR.instances[ckEditorId]) {
			return $wnd.CKEDITOR.instances[ckEditorId].status == "ready";
		} else {
			return false;
		}
	}-*/;

	private native void setCKEditorReadOnly(String ckEditorId, boolean readOnly)/*-{
		if($wnd.CKEDITOR.instances[ckEditorId]) {
			$wnd.CKEDITOR.instances[ckEditorId].setReadOnly(readOnly);
		}
	}-*/;
	
	private native void setCKEditorReadOnlyOnStartup(String ckEditorId, boolean readOnly)/*-{
		if($wnd.CKEDITOR.instances[ckEditorId]) {
			$wnd.CKEDITOR.instances[ckEditorId].config.readOnly = readOnly;
		}
	}-*/;

	public String getValue() {
		String value = "";
		if(isInitialized()) {
			value = getCKEditorValue(getID()+"_ta");				
		} else {
			value = initValue;
		}
		return value != null ? value : "";
	}
	
	private native String getCKEditorValue(String ckEditorId)/*-{
		if($wnd.CKEDITOR.instances[ckEditorId]) {
			return $wnd.CKEDITOR.instances[ckEditorId].getData();
		}
		return null;
	}-*/;
	
	/* 
	 * Il metodo setData() del CKEditor è asinconro. Se faccio quindi due setData() in sequenza ravvicinata, viene eseguito solo il primo mentre 
	 * il secondo viene ignorato. Dato che l'unico caso in cui vengono fatti due setData() ravvicinati è quello in cui sbianchiamo i dati a maschera 
	 * con editNewRecord() prima di caricare i valori da dettaglio (o dal modello), potremmo fare così:
	 * - quando il valore è una stringa vuota o null, quindi sto sbiancando il campo, viene chiamato il metodo clearCKEditorValue(): qui il setData() 
	 *   viene fatto in un livello, senza callback;
	 * - se la stringa invece non è vuota viene chiamato il metodo setCKEditorValue(value): qui il setData() viene richiamato nella callback di se 
	 *   stesso, in questo modo verrà sempre eseguito in uno step temporale successivo al clearCKEditorValue().
	 * In questo modo se io faccio un clearCKEditorValue() e subito dopo un setCKEditorValue(value), verrà comunque eseguito il secondo e il valore 
	 * caricato correttamente nell'editor.
	 */
	
	public void setValue(String value) {
		if(isInitialized()) {
			initValue = "";
			if(value != null && !"".equals(value)) {
				setCKEditorValue(getID()+"_ta", value);
			} else {
				clearCKEditorValue(getID()+"_ta");
			}
		} else {
			initValue = value != null ? value : "";
		}		
	}
	
	private native void clearCKEditorValue(String ckEditorId)/*-{
		if($wnd.CKEDITOR.instances[ckEditorId]) {	
			$wnd.CKEDITOR.instances[ckEditorId].setData("");
		}
	}-*/;

	private native void setCKEditorValue(String ckEditorId, String value)/*-{
		if($wnd.CKEDITOR.instances[ckEditorId]) {	
			$wnd.CKEDITOR.instances[ckEditorId].setData(value, {
            	callback: function() {
            		$wnd.CKEDITOR.instances[ckEditorId].setData(value);
					//this.checkDirty(); //true
               		//$wnd.alert(ckEditorId + ': ' + value);
            	}
            });
		}
	}-*/;
	
	public void setVisible(boolean visible) {
		setVisible(visible, false);
	}
	
	public void setVisible(boolean visible, boolean forceInit) {
		// Passare forceInit a true quando si lavora in una maschera in cui il ckeditor può essere mostrato o meno in base a delle condizioni dinamiche - 
		// ES: Vedere funzione Vocabolario (VocabolarioDetail.java)
		if (!visible && forceInit) {
			initialized = false;
			init(numMaxCaratteri, tipoEditor, altezza, larghezza, defaultValue, showSorgente, showIndentButtons, fontfamily, fontsize, upperCase, hideBorder);
		}
		if(isInitialized() && isCKEditorReady(getID()+"_ta")) {
			initVisible = false;
			setCKEditorVisible(visible, getID()+"_ta");			
		} else {
			initVisible = visible;
		}
	}
	
	public static String correggiElenchiPuntati(String inputString) {
		String string = inputString;

		try {
			if (string != null && string != "") {							
				// Correggo gli stili dei paragrafi degli elenchi puntati
				int indiceIniziale = 0;
				int indiceFinale = 0;
				while (indiceIniziale >= 0) {
					indiceIniziale = string.indexOf("<li><p", indiceIniziale);
					if (indiceIniziale == -1) {
						return inputString;
					}
					indiceFinale = string.indexOf(">", indiceIniziale + 4);
					if (indiceFinale == -1) {
						return inputString;
					}
					if (string.length() >= indiceIniziale + 6 && string.length() >= indiceFinale) {
						String stileP = string.substring(indiceIniziale + 6, indiceFinale);
						String newTag = "<li " + stileP + ">";
						string = string.substring(0, indiceIniziale) + newTag + string.substring(indiceFinale + 1, string.length());
						indiceIniziale = string.indexOf("<li><p", indiceIniziale + 1);
					} else {
						return inputString;
					}
				}
				string = string.replaceAll("</p></li>", "</li>");
			}
		} catch (Exception e) {
			return inputString;
		}

		return string;
	}
	
	public static String correggiStileElenchiPuntati(String inputString) {
		String string = inputString;

		try {
			if (string != null && string != "") {						
				// Elimino gli stili none negli elenchi
				int indiceInizioLi = string.indexOf("<li", 0);
				int indiceFineLi = 0;
				while (indiceInizioLi >= 0) {
					indiceFineLi = string.indexOf(">", indiceInizioLi + 1);
					if (indiceFineLi == -1) {
						return inputString;
					} 
					String tagLi = string.substring(indiceInizioLi, indiceFineLi + 1);
					tagLi = tagLi.replaceAll("list-style-type(\\s*):(\\s*)none", "");
					String beforeLiTag = string.substring(0, indiceInizioLi);
					String afterLiTag = "";
					if (indiceFineLi < string.length() - 2) {
						afterLiTag = string.substring(indiceFineLi + 1, string.length());
					}
					string = beforeLiTag + tagLi + afterLiTag;
					indiceInizioLi = string.indexOf("<li", indiceInizioLi + 1);
				}
			}
		} catch (Exception e) {
			return inputString;
		}

		return string;
	}

	private native void setCKEditorVisible(boolean visible, String editorId) /*-{
		var currentInstance = $wnd.CKEDITOR.instances[editorId];
		if(currentInstance){
			if (currentInstance.element.$.nextSibling) {
				if (visible) {
					currentInstance.element.$.nextSibling.style.display = "block";
				} else {
					currentInstance.element.$.nextSibling.style.display = "none";
				}
			}
		}
	}-*/;
	
	// Risolve i problemi legati al focus delle pop up (impostazioni tabelle, link,..) quando ckeditor è in una modal window
	public void onDialogShow() {
        final NodeList<Element> allWindowsWithModalMask = findAllWindowsWithModalMask();
        if(allWindowsWithModalMask != null ) {
            for(int i =0; i<allWindowsWithModalMask.getLength(); i++) {
                Element el = allWindowsWithModalMask.getItem(i);
                String id = el.getAttribute("eventproxy");
                if(Canvas.getById(id) != null) {
                    hideClickMask(Canvas.getById(id).getOrCreateJsObj());
                }
            }
        }
    }
	
	protected native NodeList<Element> findAllWindowsWithModalMask() /*-{
    	 return $wnd.document.querySelectorAll("[class='windowBackground']");
	}-*/;

	protected native void hideClickMask(JavaScriptObject windowCanvas) /*-{
		windowCanvas.hideClickMask();
	}-*/;
				
}