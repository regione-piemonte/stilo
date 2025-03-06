package it.eng.hybrid.module.firmaCertificato.outputFileProvider;

import java.io.InputStream;
import java.security.cert.X509Certificate;

public interface FileOutputProvider {

	public FileOutputProviderOperationResultEnum saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, X509Certificate certificatoDiFirma, String... params) throws Exception;

	public void saveOutputParameter() throws Exception;

	public boolean getAutoClosePostSign();

	public String getCallBackAskForClose();

	public String getCallback();

	public String getCallbackResult();

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
