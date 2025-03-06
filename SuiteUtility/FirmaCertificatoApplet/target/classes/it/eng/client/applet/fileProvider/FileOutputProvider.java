package it.eng.client.applet.fileProvider;

import java.io.InputStream;
import java.security.cert.X509Certificate;

import javax.swing.JApplet;

public interface FileOutputProvider {

	public FileOutputProviderOperationResultEnum saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, X509Certificate certificatoDiFirma, String... params) throws Exception;

	public void saveOutputParameter(JApplet applet) throws Exception;

	public boolean getAutoClosePostSign();

	public String getCallBackAskForClose();

	enum FileOutputProviderOperationResultEnum {

		OK(0), FILE_UPLOAD_ERROR(1), VERIFICA_COERENZA_CERTIFICATO_ERROR(2);

		private int operationResultCode;

		FileOutputProviderOperationResultEnum(int operationResultCode) {
			this.operationResultCode = operationResultCode;
		}

		public int getOperationResultCode() {
			return operationResultCode;
		}

		public void setOperationResultCode(int operationResultCode) {
			this.operationResultCode = operationResultCode;
		}

	}

}
