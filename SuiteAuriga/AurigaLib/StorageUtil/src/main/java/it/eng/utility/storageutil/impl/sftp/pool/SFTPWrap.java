/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPWrap {

	private Logger logger = Logger.getLogger(SFTPWrap.class);
	private JSch jsch = null;
	private Session session = null;
	ChannelSftp channelSftp = null;

	private String sftpUsername = null;
	private String sftpPassword = null;
	private String sftpHost = null;
	private int sftpPort = 22;
	private int timeout = 60000;

	public SFTPWrap(String sftpUsername, String sftpPassword, String sftpHost, int sftpPort, int timeout) throws Exception {
		this.sftpUsername = sftpUsername;
		this.sftpPassword = sftpPassword;
		this.sftpHost = sftpHost;
		this.sftpPort = sftpPort;
		this.timeout = timeout;

		// this.init();
	}

	protected void init() throws Exception {

		Channel channel = null;

		if (session == null || !session.isConnected()) {
			this.jsch = new JSch();
			session = jsch.getSession(this.sftpUsername, this.sftpHost, this.sftpPort);
			session.setPassword(this.sftpPassword);
			java.util.Properties _config = new java.util.Properties();
			_config.put("StrictHostKeyChecking", "no");
			session.setConfig(_config);
			session.connect();
		}

		channel = session.openChannel("sftp");
		channel.connect(timeout);
		logger.info("sftp channel opened and connected.");
		channelSftp = (ChannelSftp) channel;

	}

	protected void checkChannelSftp() throws Exception {
		if (channelSftp == null)
			throw new Exception("Canale null");
		if (channelSftp.isClosed())
			throw new Exception("Canale chiuso");
	}

	public void deleteFile(String file) throws Exception {
		checkChannelSftp();
		channelSftp.rm(file);
	}

	public void deleteDirectory(String remoteDir) throws Exception {
		checkChannelSftp();
		if (isDir(channelSftp, remoteDir)) {
			channelSftp.cd(remoteDir);
			Vector<LsEntry> entries = channelSftp.ls(".");
			for (LsEntry entry : entries) {
				if(!(entry.getFilename().equals(".") || entry.getFilename().equals(".."))){
					deleteDirectory(entry.getFilename());
				}
			}
			channelSftp.cd("..");
			int iPos = remoteDir.lastIndexOf("/"); 
			if ( iPos !=0 ){
				remoteDir = remoteDir.substring(iPos+1);
			}
			channelSftp.rmdir(remoteDir);

		} else {
			channelSftp.rm(remoteDir);
		}
	}

	public void downloadDirectory(String sourcePath, String destPath) throws Exception { // With subfolders and all files.
		// Create local folders if absent.
		try {
			new File(destPath).mkdirs();
		} catch (Exception e) {
			logger.error("Error at : " + destPath);
		}
		channelSftp.lcd(destPath);

		// Copy remote folders one by one.
		lsFolderCopy(sourcePath, destPath); // Separated because loops itself inside for subfolders.
	}

	private void lsFolderCopy(String sourcePath, String destPath) throws Exception { // List source (remote, sftp) directory and create a local copy of it
																						// - method for every single directory.
		Vector<ChannelSftp.LsEntry> list = channelSftp.ls(sourcePath); // List source directory structure.
		for (ChannelSftp.LsEntry oListItem : list) { // Iterate objects in the list to get file/folder names.
			System.out.println(">"+oListItem.getFilename());
			if (!oListItem.getAttrs().isDir()) { // If it is a file (not a directory).
				if (!(new File(destPath + "/" + oListItem.getFilename())).exists()
						|| (oListItem.getAttrs().getMTime() > Long.valueOf(new File(destPath + "/" + oListItem.getFilename()).lastModified() / (long) 1000)
								.intValue())) { // Download only if changed later.
					new File(destPath + "/" + oListItem.getFilename());
					channelSftp.get(sourcePath + "/" + oListItem.getFilename(), destPath + "/" + oListItem.getFilename()); // Grab file from source ([source
																															// filename], [destination
																															// filename]).
				}
			} else if (!".".equals(oListItem.getFilename()) && !"..".equals(oListItem.getFilename())) {
				new File(destPath + "/" + oListItem.getFilename()).mkdirs(); // Empty folder copy.
				lsFolderCopy(sourcePath + "/" + oListItem.getFilename(), destPath + "/" + oListItem.getFilename()); // Enter found folder on server to read
																													// its contents and create locally.
			}
		}
	}

	private boolean isDir(ChannelSftp sftp, String entry) throws Exception {
		return sftp.stat(entry).isDir();
	}

	protected boolean check() throws Exception {
		boolean ret = false;
		ret = this.session.isConnected();
		return ret;
	}

	public void close() {
		if (channelSftp != null)
			channelSftp.exit();
		session.disconnect();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public ChannelSftp getChannelSftp() {
		return channelSftp;
	}

	public void setChannelSftp(ChannelSftp channelSftp) {
		this.channelSftp = channelSftp;
	}

	public String getSftpUsername() {
		return sftpUsername;
	}

	public void setSftpUsername(String sftpUsername) {
		this.sftpUsername = sftpUsername;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public String getSftpHost() {
		return sftpHost;
	}

	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(int sftpPort) {
		this.sftpPort = sftpPort;
	}

}
