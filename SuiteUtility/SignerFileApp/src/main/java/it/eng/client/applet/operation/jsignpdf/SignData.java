package it.eng.client.applet.operation.jsignpdf;

import java.io.File;

import com.itextpdf.text.Image;

/**
 * Models all the data you need in order to sign a Pdf document.
 */
public class SignData {

	private boolean signMode, canEncrypt, flatten, isVisibleSignature;
	private boolean crlEnabled, ocspEnabled, timestamp, caEnabled;
	private String tsaUrl;
	private String ocspUrl;
	private boolean tsaServerAuthn;
	private String tsaUser;
	private String tsaPasswd;
	private String tsaCertFileType;
	private String tsaCertFile;
	private String tsaCertFilePwd;
	private String tsaPolicy;
	private String outputPath, keyFilePath, keyStorePath, alias, reason, location, contact;
	private char[] keyFilePassword, keyStorePassword, aliasPassword, encryptUserPassword, encryptOwnerPassword;
	private int encryptPermissions;
	private CertificationLevel certifyMode;
	float x1, y1, x2, y2;
	private File outputFile, keyFile;
 
	private Image signImage;
	private Image imageBg;
	
	private float bgImgScale;
	private String l2Text;
	private float l2TextFontSize;
	private String l4Text;
	private boolean acro6Layers;
	private RenderMode renderMode;
	private char[] pin;
	private int slot;
	private HashAlgorithm hashAlgorithm;

	//Fields for use with checking validity of data.
	private boolean valid = false;
	private String invalidMessage;
	private int signaturePage;
	
	private String proxyHost;
	private String proxyPort;
	private String proxyUser;
	private String proxyPassword;
	
	private boolean appendMode;
	 
	/**
	 * @return True if using a keystore file to sign.
	 */
	public boolean isKeystoreSign()
	{
		return signMode;
	}
	
	/**
	 * @param b True if using a keystore file to sign document
	 */
	public void setSignMode(boolean b)
	{
		signMode = b;
	}
	
	/**
	 * @param path Absolute path of the destination of the signed document
	 */
	public void setOutputFilePath(String path)
	{
		outputPath = path;
	}
	
	public String getOutputFilePath()
	{
		return outputPath;
	}
	
	public File getOutput()
	{
		return outputFile;
	}
	
	/**
	 * @param path Absolute path of .pfx file.
	 */
	public void setKeyFilePath(String path)
	{
		keyFilePath = path;
	}
	
	public String getKeyFilePath()
	{
		return keyFilePath;
	}
	
	public File getKeyFile()
	{
		return keyFile;
	}

	public void setKeyStorePath(String path)
	{
		keyStorePath = path;
	}
	
	public String getKeyStorePath()
	{
		return keyStorePath;
	}

	public char[] getKeystorePassword()
	{
		return keyStorePassword;
	}
	
	public void setKeystorePassword(char[] password)
	{
		keyStorePassword = password;
	}

	public String getAlias()
	{
		return alias;
	}
	
	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public char[] getAliasPassword()
	{
		return aliasPassword;
	}
	
	public void setAliasPassword(char[] password)
	{
		aliasPassword = password;
	}

	public void setKeyFilePassword(char[] password)
	{
		keyFilePassword = password;
	}
	
	public char[] getKeyFilePassword()
	{
		return keyFilePassword;
	}

	public boolean canEncrypt()
	{
		return canEncrypt;
	}
	
	public void setEncrypt(boolean b)
	{
		canEncrypt = b;
	}
	
	public String getReason()
	{
		return reason;
	}
	
	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * @param certifyMode Certify mode in accordance with PdfSignatureAppearance constants.
	 */
	public void setCertifyMode(CertificationLevel certifyMode)
	{
		this.certifyMode = certifyMode;
	}
	
	public CertificationLevel getCertifyMode()
	{
		return certifyMode;
	}

	public void setFlatten(boolean selected)
	{
		flatten = selected;
	}
	
	public boolean canFlatten()
	{
		return flatten;
	}

	public void setEncryptUserPass(char[] password)
	{
		encryptUserPassword = password;
	}
	
	public char[] getEncryptUserPass()
	{
		return encryptUserPassword;
	}

	public void setEncryptOwnerPass(char[] password)
	{
		encryptOwnerPassword = password;
	}
	
	public char[] getEncryptOwnerPass()
	{
		return encryptOwnerPassword;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public String getLocation()
	{
		return location;
	}

	/**
	 * @param permissions In accordance with PdfWriter constants
	 */
	public void setEncryptPermissions(int permissions)
	{
		encryptPermissions = permissions;
	}
	
	public int getEncryptPermissions()
	{
		return encryptPermissions;
	}
	
	/**
	 * This method is overidden to display messages about its this objects state.
	 * Used after calling validate.
	 */
	public String toString()
	{
		String result;
		
		if(valid) {
			result =  "Output File: " + outputFile.getAbsolutePath() + '\n';
		}
		else {
			return invalidMessage;
		}
		
		result += "Reason: \"" + reason + "\"\n"
		        + "Location: " + location + '\n'
		        + "Contact: " + contact + '\n';
		
		if(canEncrypt()) {
			result += "Encrypt PDF" + '\n';
		}
		if(canFlatten()) {
			result += "Flatten PDF" + '\n';
		}

		return result;
	}

	/**
	 * Initialises and checks validity of files.  This objects toString() method changes to
	 * reflect failures in validation in order for the user to be informed.
	 * 
	 * @return True if the files are valid.
	 */
	public boolean validate() { 
		//#TODO Validate whether an author or encryption signature is possible. 
		System.out.println("outputPath " + outputPath);
		outputFile = new File(outputPath);
		
//		if(outputFile.exists() || outputFile.isDirectory()) {   		
//			invalidMessage = "Output file already exists."; //TODO Signer: Internalisation of messages
//			return valid = false;
//		}    	
//		if(!signMode) {
//			keyFile = new File(keyFilePath);
//			if(!keyFile.exists() || keyFile.isDirectory()) {   		
//				invalidMessage = "Key file not found."; //TODO Signer: Internalisation of messages
//				return valid = false;
//			}
//		}
		return valid = true;
	}

	public boolean isVisibleSignature()
	{
		return isVisibleSignature;
	}
	
	public void setVisibleSignature(boolean b)
	{
		isVisibleSignature = b;
	}

    public void setRectangle(float x1, float y1, float x2, float y2)
    {
    	if(x1<x2) {
    		this.x1 = x1;
    		this.x2 = x2;
    	}
    	else {
    		this.x2 = x1;
    		this.x1 = x2;
    	}
    	
    	if(y1<y2) {
    		this.y1 = y1;
    		this.y2 = y2;
    	}
    	else {
    		this.y2 = y1;
    		this.y1 = y2;
    	}
    }

	/**
	 * @return Four coordinates representing the visible signature area.
	 */
	public float[] getRectangle()
	{
		float result[] = {x1, y1, x2, y2};
        return result;
	}
	
	/**
	 * @return The page to sign
	 */
	public int getSignPage()
	{
		return signaturePage;
	}
	
	public void setSignPage(int page)
	{
		signaturePage = page;
	}
	
	public void setAppend(boolean b)
	{
		appendMode = b;
	}

	public boolean isAppendMode() {
		return appendMode;
	}

	public boolean isCrlEnabled() {
		return crlEnabled;
	}

	public void setCrlEnabled(boolean crlEnabled) {
		this.crlEnabled = crlEnabled;
	}
	
	public boolean isOcspEnabled() {
		return ocspEnabled;
	}

	public void setOcspEnabled(boolean ocspEnabled) {
		this.ocspEnabled = ocspEnabled;
	}
	
	public boolean isTimestamp() {
		return timestamp;
	}

	public void setTimestamp(boolean timestamp) {
		this.timestamp = timestamp;
	}
	
//	public String getImgPath() {
//		return imgPath;
//	}
//
//	public void setImgPath(String imgPath) {
//		this.imgPath = imgPath;
//	}
//
//	public String getBgImgPath() {
//		return bgImgPath;
//	}
//
//	public void setBgImgPath(String bgImgPath) {
//		this.bgImgPath = bgImgPath;
//	}
	
	public float getBgImgScale() {
		return bgImgScale;
	}

	public void setBgImgScale(float bgImgScale) {
		this.bgImgScale = bgImgScale;
	}

	public String getL2Text() {
		return l2Text;
	}

	public void setL2Text(String l2Text) {
		this.l2Text = l2Text;
	}

	public float getL2TextFontSize() {
		return l2TextFontSize;
	}

	public void setL2TextFontSize(float l2TextFontSize) {
		this.l2TextFontSize = l2TextFontSize;
	}

	public String getL4Text() {
		return l4Text;
	}

	public void setL4Text(String l4Text) {
		this.l4Text = l4Text;
	}

	public boolean isAcro6Layers() {
		return acro6Layers;
	}

	public void setAcro6Layers(boolean acro6Layers) {
		this.acro6Layers = acro6Layers;
	}

	public RenderMode getRenderMode() {
		return renderMode;
	}

	public void setRenderMode(RenderMode renderMode) {
		this.renderMode = renderMode;
	}
	
	public char[] getPin() {
		return pin;
	}

	public void setPin(char[] pin) {
		this.pin = pin;
	}
	
	public HashAlgorithm getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(HashAlgorithm hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTsaUrl() {
		return tsaUrl;
	}

	public void setTsaUrl(String tsaUrl) {
		this.tsaUrl = tsaUrl;
	}

	

	public String getTsaUser() {
		return tsaUser;
	}

	public void setTsaUser(String tsaUser) {
		this.tsaUser = tsaUser;
	}

	public String getTsaPasswd() {
		return tsaPasswd;
	}

	public void setTsaPasswd(String tsaPasswd) {
		this.tsaPasswd = tsaPasswd;
	}

	public String getTsaCertFileType() {
		return tsaCertFileType;
	}

	public void setTsaCertFileType(String tsaCertFileType) {
		this.tsaCertFileType = tsaCertFileType;
	}

	public String getTsaCertFile() {
		return tsaCertFile;
	}

	public void setTsaCertFile(String tsaCertFile) {
		this.tsaCertFile = tsaCertFile;
	}

	public String getTsaCertFilePwd() {
		return tsaCertFilePwd;
	}

	public void setTsaCertFilePwd(String tsaCertFilePwd) {
		this.tsaCertFilePwd = tsaCertFilePwd;
	}

	public String getTsaPolicy() {
		return tsaPolicy;
	}

	public void setTsaPolicy(String tsaPolicy) {
		this.tsaPolicy = tsaPolicy;
	}

	public boolean isCaEnabled() {
		return caEnabled;
	}

	public void setCaEnabled(boolean caEnabled) {
		this.caEnabled = caEnabled;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public String getOcspUrl() {
		return ocspUrl;
	}

	public void setOcspUrl(String ocspUrl) {
		this.ocspUrl = ocspUrl;
	}

	public Image getSignImage() {
		return signImage;
	}

	public void setSignImage(Image signImage) {
		this.signImage = signImage;
	}

	public Image getImageBg() {
		return imageBg;
	}

	public void setImageBg(Image imageBg) {
		this.imageBg = imageBg;
	}

	public boolean isTsaServerAuthn() {
		return tsaServerAuthn;
	}

	public void setTsaServerAuthn(boolean tsaServerAuthn) {
		this.tsaServerAuthn = tsaServerAuthn;
	}

}
