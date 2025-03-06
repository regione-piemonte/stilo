package it.eng.utility.jobmanager.test;

import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.jobmanager.quartz.config.JobManager;

import java.io.File;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {

		// Recupero i nome delle entity da configurare sul package
		File directoryjob = null;
		String packageJob = "it.eng.utility.jobmanager.test";
		HashMap<String, Class<?>> jobclass = new HashMap<String, Class<?>>();
		try {
			String realPath = "target\\test-classes\\";
			directoryjob = new File(realPath + File.separator + packageJob.replace('.', File.separatorChar));
			String[] files = directoryjob.list();
			for (String filename : files) {
				if (filename.endsWith(".class")) {
					// Controllo se ha l'annotation job
					Class<?> classe = Class.forName(packageJob + '.' + filename.substring(0, filename.length() - 6));
					if (classe.isAnnotationPresent(Job.class)) {
						String jobtype = ((Job) classe.getAnnotation(Job.class)).type();
						jobclass.put(jobtype, classe);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ApplicationContext context = new ClassPathXmlApplicationContext("job.xml");

		// Recupero il jobmanager e setto i job
		JobManager manager = (JobManager) context.getBean("jobmanager");
		manager.initialize(jobclass);

	}

}
