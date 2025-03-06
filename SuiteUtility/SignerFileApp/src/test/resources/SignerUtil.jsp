<%@ taglib prefix="signer" uri="http://it.eng.signer" %>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/SignerProxy.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/SignerUtil.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/script/modal/prototype.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/script/modal/window.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/script/modal/window_effects.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/script/modal/debug.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/script/modal/effects.js"> </script>
<link href="<%=request.getContextPath()%>/includes/script/modal/themes/default.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/includes/script/modal/themes/alphacube.css" rel="stylesheet" type="text/css" />
 
<signer:init callback="signerEnd"></signer:init>

<script type="text/javascript">
  var master = null;
  var info = null;
  
  function loadSigner(){
  	//document.getElementById('glass').style.display = 'block';
  	master = new Window("master", {className: "alphacube", resizable:false, minimizable:false,  maximizable:false,draggable: false, width:305, height:205,title:"Firma"});
    master.setHTMLContent('<APPLET id="SignerApplet" CODE="it.eng.client.applet.SmartCard" NAME="SignerApplet" HEIGHT=200 WIDTH=300  mayscript="mayscript" archive="http://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/jsp/protocollo/firmaNew/SmartCardApplet.jar"></APPLET>');
    master.setZIndex(110);
    master.showCenter();
    master.setCloseCallback(function(){master.destroy();window.open(document.formFirma.url.value + '/jsp/common/forzaChiusura.html','_self');});
  }

  function signerEnd(){
	  SignerUtil.saveSignerFile(document.formFirma.tipoFile.value, document.formFirma.url.value, document.formFirma.idDoc.value, signerSaveEnd);
  }

  function signerSaveEnd(data){
	 //Chiudo l'applet
	 master.destroy();
	 if(callbakFunctionReturn!=null){
		 var call =callbakFunctionReturn+'("'+data+'");';
		 eval(call);		 
	 }
	 eval(data);
	 window.open(document.formFirma.url.value + '/jsp/common/forzaChiusura.html','_self');
  }  

  function firmafile(displayfilename, realfilename, urlTmp, idDoc, tipoFile){
	  info = new Window("info", {className: "alphacube", closable:false,resizable:false, minimizable:false,  maximizable:false,draggable: false, width:305, height:205,title:"Firma"});
	  info.setHTMLContent('<div style="text-align:center;width:100%">Attendere recupero file da firmare in corso!</br><img src="<%=request.getContextPath()%>/includes/gifs/ajax-loader.gif"></div>');
	  info.setZIndex(110);
	  info.showCenter();
	  //Recupero il file
	  SignerUtil.retrieveFile(displayfilename, realfilename, urlTmp, idDoc, tipoFile, endFinishRetriveFile);
  }

  function endFinishRetriveFile(data){
 	  if(info!=null){
		  info.destroy();
	  } 
 	  avvioFirma();
  }

  var callbakFunctionReturn=null;

</script>
 
