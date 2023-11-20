/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import it.eng.bean.ExecutionResultBean;
import it.eng.utility.filemanager.AbstractFileManager;
import it.eng.utility.filemanager.FileBean;
import it.eng.utility.filemanager.FileManagerConfig;
import it.eng.utility.filemanager.FileManagerException;

public class FtpFileManager extends AbstractFileManager {

	// private FTPUtil ftpUtil;
	// private SFTPUtil sftpUtil;
	// private SshClient sshClient;
	// private SftpClient sftpClient;
	// private FTPClient ftpClient;

	private boolean secure = false;

	public FtpFileManager(FileManagerConfig config) {
		super(config);
	}

	private void apriSessione() throws Exception {
		// logger.debug("APRO sessione: " + (secure ? "SFTP" : "FTP") + "; IP: " + ip + "; PORTA: " + porta + "; UTENTE: " + utente);
		// if (secure) {
		// sshClient = sftpUtil.connectSFTP(ip, porta, utente, password, tentativiConnessione);
		// sftpClient = sshClient.openSftpClient();
		// } else {
		// ftpClient = ftpUtil.connectFTP(ip, porta, utente, password, tentativiConnessione);
		// }
		// logger.debug("APERTA sessione");
	}

	private void chiudiSessione() throws Exception {
		// logger.debug("CHIUDO sessione: " + (secure ? "SFTP" : "FTP") + "; IP: " + ip + "; PORTA: " + porta + "; UTENTE: " + utente);
		// if (secure) {
		// sshClient.disconnect();
		// } else {
		// ftpClient.disconnect(true);
		// }
		// logger.debug("CHIUSA sessione");
	}

	@Override
	public boolean fileExists(FileBean fileBean) throws FileManagerException {
		// File file = FileUtils.getFile(fileBean.getAbsoluteParentPath(), fileBean.getName());
		// return file != null ? file.exists() : false;
		return false;
	}

	@Override
	public File getFile(FileBean fileBean, String destDir) throws FileManagerException {
		// File inFile = FileManagerUtil.getFileFromBean(fileBean);
		// File outFile = FileUtils.getFile(StringUtils.defaultString(destDir, "."), fileBean.getName());
		// try {
		// FileUtils.copyFile(inFile, outFile);
		// } catch (IOException e) {
		// throw new FileManagerException(String.format("Errore nella copia del file da %s a %s", inFile, outFile), e);
		// }
		// return outFile;
		return null;
	}

	@Override
	public ExecutionResultBean<Map<String, Boolean>> deleteFiles(List<FileBean> listaFiles) throws FileManagerException {
		ExecutionResultBean<Map<String, Boolean>> result = new ExecutionResultBean<>();
		// Map<String, Boolean> deleteResultList = new HashMap<>();
		// result.setSuccessful(true);
		// if (listaFiles != null) {
		// for (FileBean fileBean : listaFiles) {
		// File fod = FileManagerUtil.getFileFromBean(fileBean);
		// boolean deleted = FileUtils.deleteQuietly(fod);
		// deleteResultList.put(fod.getAbsolutePath(), deleted);
		// if (!deleted)
		// result.setSuccessful(false);
		// result.setMessage("Uno o più file o directory non sono stati cancellati");
		// }
		// } else {
		// result.setMessage("Nessun file o directory da cancellare");
		// }

		return result;
	}

	@Override
	public boolean deleteFile(FileBean fileBean) throws FileManagerException {
		// if (fileBean != null) {
		// return FileUtils.deleteQuietly(FileManagerUtil.getFileFromBean(fileBean));
		// } else {
		// return true;
		// }
		return false;
	}

	@Override
	protected void refreshConfig() throws FileManagerException {
		return; // niente da aggiornare
	}

	@Override
	public boolean canReadFile(FileBean fileBean) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canWriteFile(FileBean fileBean) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDirectory(FileBean fileBean) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDirectory(FileBean fileBean) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean copyFile(FileBean fileBean, FileBean destDir) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveFile(FileBean fileBean, FileBean destDir) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean renameFile(FileBean fileBean, FileBean newFileBean) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createDirectory(Path path) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createDirectory(FileBean fileBean) throws FileManagerException {
		// TODO Auto-generated method stub
		return false;
	}

}
