package it.eng.hsm.client;

import java.io.File;
import java.util.Date;
import java.util.List;

import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.marca.MarcaRequestBean;
import it.eng.hsm.client.bean.marca.MarcaResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.CertificateVerifyResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.bean.sign.SignVerifyResponseBean;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.exception.HsmClientSignatureVerifyException;
import it.eng.hsm.client.option.SignOption;

public interface Hsm {

	public SignResponseBean firmaCades(List<byte[]>  bytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException;

	public SignResponseBean firmaCadesParallela(List<byte[]> bytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException;

	public SignResponseBean firmaPades(List<byte[]>  bytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException;

	public SignResponseBean firmaXades(List<byte[]> bytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException;

	public SessionResponseBean apriSessioneFirmaMultipla() throws HsmClientConfigException, UnsupportedOperationException;

	public SignResponseBean firmaMultiplaHash(List<HashRequestBean> listaHashDaFirmare)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException;

	public SignResponseBean firmaMultiplaHashInSession(List<HashRequestBean> listaHashDaFirmare, String sessionId)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException;

	public MessageBean chiudiSessioneFirmaMultipla(String sessionId) throws HsmClientConfigException, UnsupportedOperationException;

	public OtpResponseBean richiediOTP() throws HsmClientConfigException, UnsupportedOperationException;

	public CertResponseBean getUserCertificateList() throws HsmClientConfigException, UnsupportedOperationException;

	public CertResponseBean getUserCertificateById() throws HsmClientConfigException, UnsupportedOperationException;
	
	public MarcaResponseBean aggiungiMarca(File fileDaMarcare, MarcaRequestBean marcaRequestBean) throws HsmClientConfigException, UnsupportedOperationException;
	public MarcaResponseBean aggiungiMarca(File fileDaMarcare) throws HsmClientConfigException, UnsupportedOperationException;

	public HsmConfig getHsmConfig();
	
	public SignVerifyResponseBean verificaFirmeCades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException;
	
	public SignVerifyResponseBean verificaFirmePades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException;
	
	public SignVerifyResponseBean verificaFirmeXades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException;
	
	public CertificateVerifyResponseBean verificaCertificato(List<byte[]> listaCertificati)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException;
	
	public CertificateVerifyResponseBean verificaCertificatoPerData(byte[] bytesFileDaVerificare, Date date)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException;
}