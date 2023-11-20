<!DOCTYPE html>
<%@page import="com.smartgwt.client.util.JSONEncoder"%>
<%@page
	import="it.eng.utility.ui.module.layout.server.common.ServiceRestGenericPropertyConfig"%>
<%@page
	import="it.eng.auriga.ui.module.layout.server.common.SchemaSelectionDataSource"%>
<%@page
	import="it.eng.auriga.ui.module.layout.shared.bean.SchemaSelector"%>
<%@page import="java.util.Date"%>

<html lang="it">
<%
	String lApplicationName = "AurigaWeb";
	String lConfigApplicationName = ServiceRestGenericPropertyConfig.getApplicationName();
	if (lConfigApplicationName != null && !"".equals(lConfigApplicationName)) {
		lApplicationName = lConfigApplicationName;
	}

	String displayApplicationName = lApplicationName;
	if (ServiceRestGenericPropertyConfig.getDisplayApplicationName() != null
			&& !ServiceRestGenericPropertyConfig.getDisplayApplicationName().equals("")) {
		displayApplicationName = ServiceRestGenericPropertyConfig.getDisplayApplicationName();
	}

	//String userid = request.getParameter("userid") != null ? (String) request.getParameter("userid") : "";
	String dominioSelezionato = request.getParameter("dominioSelezionato") != null
			? (String) request.getParameter("dominioSelezionato")
			: "";
	String schemaSelezionato = request.getParameter("schemaSelezionato") != null
			? (String) request.getParameter("schemaSelezionato")
			: "";
			
	String defaultLocale = "it_IT";
	if (ServiceRestGenericPropertyConfig.getDefaultLocale() != null
			&& !ServiceRestGenericPropertyConfig.getDefaultLocale().equals("")) {
		defaultLocale = ServiceRestGenericPropertyConfig.getDefaultLocale();
	}
	String locale = ((request.getParameter("locale") != null)
			&& (!request.getParameter("locale").equalsIgnoreCase("")))
			? (String) request.getParameter("locale")
			: defaultLocale;
					
	boolean skipLogin = Boolean
			.valueOf(request.getParameter("skipLogin") != null ? request.getParameter("skipLogin") : "false");

	session.setAttribute("APPLICATION_NAME", lApplicationName);

	String lshowResetPasswordLogin = "false";
	String lConfigShowResetPasswordLogin = ServiceRestGenericPropertyConfig.getShowResetPasswordLogin();

	if (lConfigShowResetPasswordLogin != null && !"".equals(lConfigShowResetPasswordLogin)) {
		lshowResetPasswordLogin = lConfigShowResetPasswordLogin;
	}

	String modalitaCompatibilitaIE = request.getParameter("IE") != null
			? (String) request.getParameter("IE")
			: "edge";
			
	String skin = request.getParameter("skin") != null
			? (String) request.getParameter("skin")
			: "AurigaTahoeMilano";

	String flagCifratura = "false";
	String flagCifraturaConfig = ServiceRestGenericPropertyConfig.getFlagCifratura();
	if ("true".equalsIgnoreCase(flagCifraturaConfig)) {
		flagCifratura = "true";
	}
	
	String flagAttivaRequestValidator = "false";
	String flagAttivaRequestValidatorConfig = ServiceRestGenericPropertyConfig.getFlagAttivaRequestValidator();
	if ("true".equalsIgnoreCase(flagAttivaRequestValidatorConfig)) {
		flagAttivaRequestValidator = "true";
	}
	
	String flagRimuoviScript = "false";
	String flagRimuoviScriptConfig = ServiceRestGenericPropertyConfig.getFlagRimuoviScript();
	if ("true".equalsIgnoreCase(flagRimuoviScriptConfig)) {
		flagRimuoviScript = "true";
	}
	
	String flagEscapeHtml = "false";
	String flagEscapeHtmlConfig = ServiceRestGenericPropertyConfig.getFlagEscapeHtml();
	if ("true".equalsIgnoreCase(flagEscapeHtmlConfig)) {
		flagEscapeHtml = "true";
	}
	
%>
<head>
<style>
.progress-wrapper {
    width:100%;
}
.progress-wrapper .progress {
    background-color:green;
    width:0%;
    padding:5px 0px 5px 0px;
}
</style>

<!--                                                               -->
<!-- Consider inlining CSS to reduce the number of requested files -->
<!--                                                               -->

<!--                                           -->
<!-- Any title is fine                         -->
<!--                                           -->
<title><%=displayApplicationName%></title>

<!-- <meta http-equiv="X-UA-Compatible" content="IE=<%=modalitaCompatibilitaIE%>" /> -->

<meta name="gwt:property" content="locale=<%=locale%>">

<!-- font Raleway -->
<!-- <link href="https://fonts.googleapis.com/css?family=Raleway:400,400i,500,500i,600,600i,700,700i" rel="stylesheet"> -->
<link rel="stylesheet" type="text/css" href="css/fontRaleway.css">

<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->

<!-- <link rel="stylesheet" type="text/css" href="index.css"> -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="text/javascript">
	var userAgent = navigator.userAgent.toLowerCase();
	<!-- To fix the CSS visibility bug (hidden elements remain visible) with new Chrome version (62) -->
	if (userAgent.indexOf('chrome/62') > -1) {
		<!-- Introduce un bug sulle liste: quando hai scrollato e clicchi il check di selezione fa un flash -->
		<!-- document.write('<style type="text/css">*{transition: visibility 0.01s;}</style>'); -->
	}
</script>

<script src="script/ckeditor_4.15.0_20230323/ckeditor.js"></script>
<script src="script/pdfViewer_20220316/build/pdf.worker.js"></script>
<script src="script/pdfViewer_20220316/build/pdf.js"></script>
<script src="script/pdfViewer_20220316/web/viewer.js"></script>
</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!-- 
                                              -->
<body id="body">

	<iframe height="0" width="0" frameborder="0" name="uploadTarget"></iframe>
	<iframe height="0" width="0" frameborder="0"
		name="uploadMultipleTarget"></iframe>
	<iframe height="0" width="0" frameborder="0" name="downloadTarget"></iframe>

	<script language="JavaScript" src="script/domManipulate.js">
	</script>
	<script type="text/javascript" src="script/sjcl.js"></script>

	<script type="text/javascript" src="script/scopeleaks/scopeleaks.js"></script>

	<!-- OPTIONAL: include this if you want history support -->
	<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
		style="position: absolute; width: 0; height: 0; border: 0"></iframe>


	<div id="loadingWrapper">
		<div id="loading">
			<div class="loadingIndicator">
				<img src="images/loading.gif" width="16" height="16"
					style="margin-right: 8px; float: left; vertical-align: top;" /><%=displayApplicationName%><br />
				<span id="loadingMsg">Loading...</span>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var isomorphicDir = "layout/sc/";
		var zIndexTile = ""
	</script>

	<script type="text/javascript">
	var observer = new MutationObserver(function(mutations) {
		mutations.forEach(function(mutation) {
			if(mutation.type == 'childList') {
				var nodes = Array.prototype.slice.call(mutation.addedNodes);
				nodes.forEach(function(node) {
					if(node.id) {
						/*
						if(node.id.startsWith('isc_') && node.getAttribute('eventproxy') != null && node.getAttribute('eventproxy').startsWith('isc_Menu_')) {
							console.log(node);
						}
						*/
						if(node.id.startsWith('isc_OID_')) {
							node.remove();	
						}									
					}
				});
			}
	    });
	});
	observer.observe(document.body, {
		childList: true,
		subtree: true,
		attributes: false,
	    characterData: false,
	});
	</script>
	
	<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = 'Loading Core API...';
	</script>

	<!--include the SC Core API-->
	<script src=layout/sc/modules/ISC_Core.js></script>

	<!--include SmartClient -->
	<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = 'Loading UI Components...';
	</script>
	<script src='layout/sc/modules/ISC_Foundation.js'></script>
	<script src='layout/sc/modules/ISC_Containers.js'></script>
	<script src='layout/sc/modules/ISC_Grids.js'></script>
	<script src='layout/sc/modules/ISC_Forms.js'></script>
	<script src='layout/sc/modules/ISC_RichTextEditor.js'></script>
	<script src='layout/sc/modules/ISC_Calendar.js'></script>
	<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = 'Loading Data API...';
	</script>
	<script src='layout/sc/modules/ISC_DataBinding.js'></script>
	<script src='layout/sc/modules/ISC_Drawing.js'></script>
	<script src='layout/sc/modules/ISC_PluginBridges.js'></script>

	<!-- <script src='layout/sc/skins/auriga/load_skin.js'></script> -->
	<script src='layout/sc/skins/<%=skin%>/load_skin.js'></script>
	
	<script src='si.files.js'></script>

	<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = 'Loading application...';
	</script>

	<script type="text/javascript" language="javascript"
		src="layout/layout.nocache.js?nocache=<%=new Date().getTime()%>"></script>

	<link rel="stylesheet" type="text/css" href="hybrid/hybrid-light.css">
	<script src="hybrid/hybrid.js"></script>

	<script language="JavaScript">
	<%
	if (!skipLogin) {
		session.invalidate();
	}
	String lSchemaSelector = SchemaSelectionDataSource.getSchemas(schemaSelezionato);
	%>
	var params = {
		skipLogin:'<%=skipLogin%>',
		dominioSelezionato:'<%=dominioSelezionato%>',
		schemaSelezionato:'<%=schemaSelezionato%>',
		schema:'<%=lSchemaSelector%>',
		applicationName:'<%=lApplicationName%>',
		showResetPasswordLogin:'<%=lshowResetPasswordLogin%>',
		flagCifratura:'<%=flagCifratura%>',
		flagAttivaRequestValidator:'<%=flagAttivaRequestValidator%>',
		flagRimuoviScript:'<%=flagRimuoviScript%>',
		flagEscapeHtml:'<%=flagEscapeHtml%>',
		skin:'<%=skin%>'
	};

	var counterSignerApplet = 0;
	function doSignerAppletMultiHash(lMapParams) {
		Hybrid.require('SignerMultiHybridBundle', function(cryptoLight) {
			cryptoLight.sign(lMapParams,
			function(result) {
				if(counterSignerApplet <5){
					if(result.sParcheck!=false){
						counterSignerApplet = 0;
						// Eseguo la callback di upload
						var callBackFunction = result.callBackFunction;
						var callBackArgs = result.callBackArgs;
						var fn1 = window[callBackFunction];
						if (typeof fn1 === "function"){
							fn1(callBackArgs);
						}
						
						// Eseguo la callback di chiusura
						var callBackClose = result.callBackClose;
						var fn = window[callBackClose];
						if (typeof fn === "function"){
							fn();
						}
					}else{
						counterSignerApplet++;
						doSignerAppletMultiHash(lMapParams);
						}
				}else{
					counterSignerApplet = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
	
	
	/**
		Il bundle dev'essere caricato in LoadHybridModuleServlet per essere reso disponibile
	*/
	var counterSelectCertificate = 0;
	
	function doSelectCertificate(lMapParams) {
		/*
			Il selectCertificati all'interno di function e subito dopo corrisponde al package
			nel modulo di firma (dove si trova il metodo processHttpRequest)
			Quello invece che corrisponde al metodo a cui viene passato lMapParams e la
			callback ? il metodo definito all'interno di processHttpRequest
		*/
		Hybrid.require('SelectCertificatoHybridBundle', function(selectCertificati) {
			selectCertificati.selectCertificati(lMapParams,
			function(result) {
				if(counterSelectCertificate <5){
					if(result.sParcheck!=false){
						counterSelectCertificate = 0;
						// Eseguo la callback di upload
						var callBackFunction = result.callBackFunction;
						var callBackArgs = result.callBackArgs;
						var fn1 = window[callBackFunction];
						if (typeof fn1 === "function"){
							fn1(callBackArgs);
						}
						
						// Eseguo la callback di chiusura
						var callBackClose = result.callBackClose;
						var fn = window[callBackClose];
						if (typeof fn === "function"){
							fn(callBackArgs);
						}
					}else{
						counterSelectCertificate++;
						doSelectCertificate(lMapParams);
						}
				}else{
					counterSelectCertificate = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
	
	/**
		Il bundle dev'essere caricato in LoadHybridModuleServlet per essere reso disponibile
	*/
	var counterFirmaCertificati = 0;
	
	function doFirmaCertificatiHybrid(lMapParams) {
		Hybrid.require('FirmaCertificatoHybridBundle', function(firmaCertificato) {
			firmaCertificato.firmaCertificato(lMapParams,
			function(result) {
				if(counterFirmaCertificati <5){
					if(result.sParcheck!=false){
						counterFirmaCertificati = 0;
						// Eseguo la callback di upload
						var callBackFunction = result.callBackFunction;
						var callBackArgs = result.callBackArgs;
						var fn1 = window[callBackFunction];
						if (typeof fn1 === "function"){
							fn1(callBackArgs);
						}
						
						// Eseguo la callback di chiusura
						var callBackClose = result.callBackClose;
						var fn = window[callBackClose];
						if (typeof fn === "function"){
							fn();
						}
					}else{
						counterFirmaCertificati++;
						doFirmaCertificatiHybrid(lMapParams);
						}
				}else{
					counterFirmaCertificati = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
	
	/**
		Il bundle dev'essere caricato in LoadHybridModuleServlet per essere reso disponibile
	*/
	var counterPrinterFiles = 0;
	
	function doPrinterFiles(lMapParams) {
		/*
			Il stampaFiles all'interno di function e subito dopo corrisponde al package
			nel modulo di firma (dove si trova il metodo processHttpRequest)
			Quello invece che corrisponde al metodo a cui viene passato lMapParams e la
			callback ? il metodo definito all'interno di processHttpRequest
		*/
		Hybrid.require('StampaFilesHybridBundle', function(stampaFiles) {
			stampaFiles.stampaFiles(lMapParams,
			function(result) {
				if(counterPrinterFiles <5){
					if(result.sParcheck!=false){
						counterPrinterFiles = 0;
						// Eseguo la callback di upload
						var callBackFunction = result.callBackFunction;
						var callBackArgs = result.callBackArgs;
						var fn1 = window[callBackFunction];
						if (typeof fn1 === "function"){
							fn1(callBackArgs);
						}
						
						// Eseguo la callback di chiusura
						var callBackClose = result.callBackClose;
						var fn = window[callBackClose];
						if (typeof fn === "function"){
							fn(callBackArgs);
						}
					}else{
						counterPrinterFiles++;
						doPrinterFiles(lMapParams);
						}
				}else{
					counterPrinterFiles = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
		
	var counterWordOpener = 0;
	
	function doWordOpener(lMapParams) {
		Hybrid.require('WordOpenerHybridBundle', function(wordOpener) {
			wordOpener.wordOpener(lMapParams,
			function(result) {
				if(counterWordOpener <5){
					if(result.sParcheck!=false){
						counterWordOpener = 0;
						var commonNameFunction = result.resultFunction;
						var commonNameArg = result.resultArg;
						var fn1 = window[commonNameFunction];
						if (typeof fn1 === "function") {
							fn1(commonNameArg);
						}
					}else{
						counterWordOpener++;
						doWordOpener(lMapParams);
						}
				}else{
					counterWordOpener = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
	
	var counterStampaEtichette = 0;
	
	function doStampaEtichette(lMapParams) {
		Hybrid.require('StampaEtichetteHybridBundle', function(stampaEtichette) {
			stampaEtichette.stampaEtichette(lMapParams,
			function(result) {
				if(counterStampaEtichette <5){
					if(result.sParcheck!=false){
						 counterStampaEtichette = 0;
						var jsontext   = result.signResult;
						var fn = window[jsontext];
						if (typeof fn === "function") fn();
					}else{
						counterStampaEtichette++;
						doStampaEtichette(lMapParams);
						}
				}else{
					 counterStampaEtichette = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
	
	var counterPrinterScanner = 0;
	
	function doPrinterScanner(lMapParams) {
		Hybrid.require('PrinterScannerHybridBundle', function(printerScanner) {
			printerScanner.printerScanner(lMapParams,
		  	function(result) {
				if(counterPrinterScanner <5){
					if(result.sParcheck!=false){
						counterPrinterScanner = 0;
						var callBackFunction = result.callBackFunction;
						var callBackArgs = result.callBackArgs;
						var fn1 = window[callBackFunction];
						if (typeof fn1 === "function"){
							fn1(callBackArgs);
						}
					}else{
						counterPrinterScanner++;
						doPrinterScanner(lMapParams);
					}
				}else{
					counterPrinterScanner = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
	
	var counterPortScanner = 0;
	
	function doPortScanner(lMapParams) {
		Hybrid.require('PortScannerHybridBundle', function(portScanner) {
			portScanner.portScanner(lMapParams,
		  	function(result) {
				if(counterPortScanner <5){
					if(result.sParcheck!=false){
						counterPortScanner = 0;
						var callBackFunction = result.callBackFunction;
						var callBackArgs = result.callBackArgs;
						var fn1 = window[callBackFunction];
						if (typeof fn1 === "function"){
							fn1(callBackArgs);
						}
					}else{
						counterPortScanner++;
						doPortScanner(lMapParams);
					}
				}else{
					counterPortScanner = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
	
	var counterScan = 0;
	
	function doScan(lMapParams) {
		Hybrid.require('ScanHybridBundle', function(scan) {
			scan.scan(lMapParams,
			function(result) {
				if(counterScan <5){
					if(result.sParcheck!=false){
						counterScan = 0;
						var jsontext   = result.callBackClose;
						 var callBackCloseArg = result.callBackCloseArg;
						 var fn = window[jsontext];
						 if (typeof fn === "function") fn(callBackCloseArg);
					}else{
						counterScan++;
						doScan(lMapParams);
						}
				}else{
					counterScan = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}
			});
		});
	}
	
	var counterPieChart = 0;

	function doPieChart(lMapParams) {
		Hybrid.require('PieChartHybridBundle', function(pieChart) {
			pieChart.pieChart(lMapParams,
			function(result) {
				if(counterPieChart <5){
					if(result.sParcheck == false){
						counterPieChart++;
						doPieChart(lMapParams);
					}else{
						counterPieChart = 0;
					}
				}else{
					counterPieChart = 0;
					alert("Non ? stato possibile caricare il modulo richiesto per problemi di comunicazione di rete");
				}	
			});
		});
	}
	
	function funzioneClose() {
		alert('Firma eseguita!!!!');
	}

	function funzioneCommonName(name) {
		alert('funzioneCommonName ' + name);
	}
	
	function _(el) {
	  return document.getElementById(el);
	}
	
	var uploadXMLHttpRequest = null;
	var idFileGlobal = null;
	var smartIdGlobal = null;
	var isMultipleUpload = false;
	
	function uploadFile(idFile, smartId, isExternalPortlet) {

	  idFileGlobal = idFile;
	  var file = _(""+idFile).files[0];
	  _("progressBar").value = 0;
	  // alert(file.name+" | "+file.size+" | "+file.type);
	  var formdata = new FormData();
	  smartIdGlobal = smartId;
	  formdata.append("smartId", "#SKIP_PROCESS");
	  formdata.append("isExternalPortlet", isExternalPortlet);
	  formdata.append("fileUploadAttr", file);
	  uploadXMLHttpRequest = new XMLHttpRequest();
	  uploadXMLHttpRequest.upload.addEventListener("progress", progressHandler, false);
	  uploadXMLHttpRequest.addEventListener("load", completeHandler, false);
	  uploadXMLHttpRequest.addEventListener("error", errorHandler, false);
	  uploadXMLHttpRequest.addEventListener("abort", abortHandler, false);
	  //ajax.open("POST", "springdispatcher/UploadServlet/"); 
	  //ajax.responseType = "document";
	  //ajax.onreadystatechange = () => {
		//if (ajax.readyState === XMLHttpRequest.DONE) {
		 // eval(ajax.responseText);
		  //document.write(ajax.responseText);
		//}
	 // }
	  //ajax.open("POST", "springdispatcher/UploadServlet/"); 
	  uploadXMLHttpRequest.open("POST", "springdispatcher/UploadServlet/");
	  //use file_upload_parser.php from above url
	  uploadXMLHttpRequest.send(formdata);
	}
		
	function uploadFileMultiplo(idFile, smartId, isExternalPortlet) {
	  idFileGlobal = idFile;
	  isMultipleUpload = true;
	  var files = _(""+idFile).files;
	  _("progressBar").value = 0;
	  // alert(file.name+" | "+file.size+" | "+file.type);
	  var formdata = new FormData();
	  smartIdGlobal = smartId;
	  formdata.append("smartId", "#SKIP_PROCESS");
	  formdata.append("isExternalPortlet", isExternalPortlet);
	  //formdata.append("fileUploadAttr", files);
	  var arrayFiles = [];
	  for ( i = 0; i < files.length; i++) {
		//arrayFiles.push(files[i]);
		formdata.append("fileUploadAttr", files[i]);
	  }
	  //formdata.append("fileUploadAttr", arrayFiles);
	  uploadXMLHttpRequest = new XMLHttpRequest();
	  uploadXMLHttpRequest.upload.addEventListener("progress", progressHandler, false);
	  uploadXMLHttpRequest.addEventListener("load", completeHandler, false);
	  uploadXMLHttpRequest.addEventListener("error", errorHandler, false);
	  uploadXMLHttpRequest.addEventListener("abort", abortHandler, false);
	  //ajax.onreadystatechange = () => {
		//if (ajax.readyState === XMLHttpRequest.DONE) {
		  //eval(ajax.responseText);
		  //document.write(ajax.responseText);
		//}
	  //}
	  uploadXMLHttpRequest.open("POST", "springdispatcher/UploadMultiploServlet/"); // http://www.developphp.com/video/JavaScript/File-Upload-Progress-Bar-Meter-Tutorial-Ajax-PHP

	  //use file_upload_parser.php from above url
	  uploadXMLHttpRequest.send(formdata);
	}
	
	function progressHandler(event) {
	  var percent = (event.loaded / event.total) * 100;
	  _("loaded_n_total").innerHTML = "Caricati " + formatBytes(event.loaded, 1) + " di " + formatBytes(event.total, 1);
	  _("progressBar").value = Math.round(percent);
	  if ( percent === 100 ) { 
		_("buttonSetElaborateProgressBarModal").click();
		if (isMultipleUpload) {
			_("formMultipleUpload"+smartIdGlobal).submit();
		} else {
			_("formSingleUpload"+smartIdGlobal).submit();
		}
	  }
	}
	
	function completeHandler(event) {
	  //_("progressBar").value = 100; 
	  //wil clear progress bar after successful upload  
	  uploadXMLHttpRequest = null;
	  smartIdGlobal = null;
	  _(""+idFileGlobal).value = null;
	  idFileGlobal = null;
	  isMultipleUpload = false;
	  //_("form1").submit();
	}
	
	function errorHandler(event) {
		uploadXMLHttpRequest = null;
		_(""+idFileGlobal).value = null;
		idFileGlobal = null;
		smartIdGlobal = null;
		isMultipleUpload = false;
	  //_("status").innerHTML = "Upload Fallito";
	}

	function abortHandler(event) {
		uploadXMLHttpRequest = null;
		_(""+idFileGlobal).value = null;
		idFileGlobal = null;
		smartIdGlobal = null;
		isMultipleUpload = false;
	  //_("status").innerHTML = "Upload Cancellato";
	}
	
	function abortUpload() {
		if (uploadXMLHttpRequest !== null) {
			uploadXMLHttpRequest.abort();	
		}
	}
		
	function formatBytes(bytes, decimals = 2) {
		if (!+bytes) return '0 Bytes'
		const k = 1024
		const dm = decimals < 0 ? 0 : decimals
		const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
		const i = Math.floor(Math.log(bytes) / Math.log(k))
		return `${parseFloat((bytes / Math.pow(k, i)).toFixed(dm))} ${sizes[i]}`
	}
	
	function settaValoreProgressBar(testo, percent) {
	  	_("loaded_n_total").innerHTML = testo;
	  	_("progressBar").value = Math.round(percent);
	}
	
	</script>
</body>
</html>
