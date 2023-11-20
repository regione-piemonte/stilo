/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.util.FileOpConfigHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * cancella i file più vecchi di una certa data
 * @author Russo
 *
 */

public class DeleteOldFileJob extends QuartzJobBean {
	
	private Integer daysBack=1;
	private Integer hoursback=24;
	private Integer minutesback=10;

	private static final Logger log = LogManager.getLogger(DeleteOldFileJob.class);

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		deleteFilesOlderThanNdays();
	}
	
	public  void deleteFilesOlderThanNdays() {  
//		if(daysBack==null || daysBack<=0){
//			log.warn("imposto cancellazione a 1 giorno");
//			daysBack=1;
//		}
		
		//ApplicationContext context=FileoperationContextProvider.getApplicationContext();
		//FileOperationController foc=context.getBean("FileOperationController",FileOperationController.class);
		//File directory = foc.getTemporarydirectroy();  
		File directory = FileOpConfigHelper.getTempDir();
		log.info("Dir temporanea " + directory);
		
		if(directory.exists()){  
			TimeZone timeZone = TimeZone.getTimeZone("Europe/Rome");
			Calendar cal = Calendar.getInstance(timeZone); 
			if(minutesback!=null && minutesback>0){
				cal.add(Calendar.MINUTE, minutesback *-1);
			}
			if(hoursback!=null && hoursback>0){
				cal.add(Calendar.HOUR, hoursback *-1);
			} //else {
			if(daysBack!=null && daysBack>0){
				cal.add(Calendar.DAY_OF_MONTH, daysBack * -1); 
			}
			log.debug("cancello i file con data modifica <"+cal.getTime());
			long purgeTime = cal.getTimeInMillis();   
			// long purgeTime = System.currentTimeMillis() - (daysBack * 24 * 60 * 60 * 1000);  
			
			File[] filesInDir = directory.listFiles();   
			if( filesInDir!=null ){
				for(File fileInDir : filesInDir) {
					if(fileInDir.isDirectory() ){
						//log.debug("fileInDir  " + fileInDir + " data modifica "+new Date(fileInDir.lastModified()));
						Calendar calFile = Calendar.getInstance(timeZone); 
						calFile.setTimeInMillis( fileInDir.lastModified() );
						calFile.setTimeZone(timeZone);
						//log.debug("fileInDir  " + fileInDir + " data modifica "+calFile.getTime());
						
						LocalDateTime localdateFile = LocalDateTime.ofInstant(
					            Instant.ofEpochMilli(fileInDir.lastModified()), ZoneId.systemDefault()
					    );
						Date dateFile = Date.from(localdateFile.atZone(ZoneId.systemDefault()).toInstant());
						//if(fileInDir.lastModified() < purgeTime) {  
						if(dateFile.before( cal.getTime())) {  
							deleteFiles( fileInDir, purgeTime );
							
						}  
					} else if(fileInDir.isFile()){
						//log.debug("fileInDir  " + fileInDir + " data modifica "+new Date(fileInDir.lastModified()));
						Calendar calFile = Calendar.getInstance(timeZone); 
						calFile.setTimeInMillis( fileInDir.lastModified() );
						calFile.setTimeZone(timeZone);
						//log.debug("fileInDir  " + fileInDir + " data modifica "+calFile.getTime());
						
						LocalDateTime localdateFile = LocalDateTime.ofInstant(
					            Instant.ofEpochMilli(fileInDir.lastModified()), ZoneId.systemDefault()
					    );
						Date dateFile = Date.from(localdateFile.atZone(ZoneId.systemDefault()).toInstant());
						//if(fileInDir.lastModified() < purgeTime) {  
						if(dateFile.before( cal.getTime())) {  
								FileUtils.deleteQuietly( fileInDir );
						}
					}
				}  
			}
		} else {  
			log.warn("Files were not deleted, directory " + directory + " does'nt exist!");  
		}  
	}  
	
	public static void deleteFiles(File fileToDelete, long purgeTime){
		//log.info("Provo a cancellare " + fileToDelete);
		if( fileToDelete.isFile() && fileToDelete.lastModified() < purgeTime){
			log.debug("Cancello " + fileToDelete );
			FileUtils.deleteQuietly(fileToDelete);
		} else if( fileToDelete.isDirectory() ){
			File[] filesInDir = fileToDelete.listFiles();
			if( filesInDir!=null ){
				for( File fileInDir : filesInDir ){
					//log.debug("Cancello " + fileInDir );
					deleteFiles(fileInDir, purgeTime);
				}
			}
			if(fileToDelete.lastModified() < purgeTime) {  
				//log.debug("Cancello " + fileToDelete );
				FileUtils.deleteQuietly(fileToDelete);
			}
		}
	}
	
	public static void main(String[] args) {
//		File file = new File("D:/apache-tomcat-7.0.62_FILEOP/temp/");
//		for(File listFile : file.listFiles() ) {
//			if(listFile.isDirectory()){
//				//deleteFiles(listFile);
//			}
//		}
		TimeZone timeZone = TimeZone.getTimeZone("Europe/Rome");
		Calendar cal = Calendar.getInstance(timeZone);  
		System.out.println("cancello i file con data modifica <"+cal.getTime());
		Date date = cal.getTime();
		
		File file = new File("C:/sviluppo/ws-fileop/TestFileop/src/test/resources/timbro/output/");
		File[] filesInDir = file.listFiles();  
		System.out.println(filesInDir);
		if( filesInDir!=null ){
			for(File fileInDir : filesInDir) {
				Calendar calFile = Calendar.getInstance(timeZone); 
				calFile.setTimeInMillis( fileInDir.lastModified() );
				calFile.setTimeZone(timeZone);
				System.out.println(fileInDir);
				System.out.println(calFile.getTime());
				System.out.println(new Date(fileInDir.lastModified())+"\n");
			}
		}
		
	}

	public Integer getDaysBack() {
		return daysBack;
	}
	public void setDaysBack(Integer daysBack) {
		this.daysBack = daysBack;
	}

	public Integer getHoursback() {
		return hoursback;
	}
	public void setHoursback(Integer hoursback) {
		this.hoursback = hoursback;
	}

	public Integer getMinutesback() {
		return minutesback;
	}

	public void setMinutesback(Integer minutesback) {
		this.minutesback = minutesback;
	}

	

}
