package it.eng.server.taglib;

import it.eng.server.SignerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

public class SignerInit extends TagSupport{

	private static final long serialVersionUID = 1L;
	private String callback;


	@Override
	public int doStartTag() throws JspException {
		try{
			JspWriter out = pageContext.getOut();
				
			SignerUtil util = SignerUtil.getSignerUtil(((HttpServletRequest)pageContext.getRequest()).getSession());
			
			out.println("<script>");
			out.println("var hashObject;");
			out.println("var digest;");
			out.println("var signerType;");
			out.println("var tabConfig;");
			out.println("var signerTypeList;");
			out.println("var signerObject = new Array();");

			out.println("function getObjectWithNumber(index){");
			out.println("	return hashObject[index];");
			out.println("}");
			
			out.println("function getTabConfig(){");
			out.println("	return tabConfig;");
			out.println("}");
			
			out.println("function getDigest(){");
			out.println("	return digest;");
			out.println("}");

			out.println("function getSignerType(){");
			out.println("	return signerType;");
			out.println("}");

			out.println("function getSignerTypeList(){");
			out.println("	return signerTypeList;");
			out.println("}");
			
			out.println("function getNumberHashToSigner(){");
			out.println("	return hashObject.size();");
			out.println("}");

			out.println("function addObjectSigner(str){");
			out.println("	signerObject.push(str);");
			out.println("}");

			out.println("function errorObjectSigner(){");
//			out.println("try{");
//			out.println("	signer.destroy();");
//			out.println("}catch(e){}");
			out.println("}");

			out.println("function endObjectSigner(){");
			out.println("	SignerProxy.signerFile(signerObject,callbackEndObjectSigner);");
			out.println("}");
			
			out.println("function getDebug(){");
			out.println("	return '"+util.getDebug()+"'");
			out.println("}");

			out.println("function callbackEndObjectSigner(data){");
			out.println("	if(data.length!=0){");
			out.println("		alert(data);");
			out.println("	}");
			out.println("	document.SignerApplet.close();");
			
			if(!StringUtils.contains(callback, "()")){
				callback += "()";
			}		
			out.println(callback+";");	
			out.println("}");
		
			out.println("function avvioFirma(){");
			out.println("	signerObject = new Array();");
			out.println("	SignerProxy.getHashFileSigner(callback);");	
			out.println("}");

			out.println("function callback(data){");
			out.println("	hashObject = data.lista;");
			out.println("	digest = data.digest;");
			out.println("	signerType = data.signerType;");
			out.println("	signerTypeList = data.signerTypeList;");
			out.println("	tabConfig = data.tabConfig;");
			out.println("	loadSigner();");
			out.println("}");
			out.println("</script>");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
		
}
