package it.eng.spring.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import it.eng.spring.utility.SpringAppContext;

public class SpringInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(SpringInitServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);				

		//Setto il context di spring sulla variabile statica della masteraction
		try{
			String name = config.getInitParameter("springcontextxml"); 
			if(!name.endsWith("xml")){
				name += ".xml";
			}
			AbstractXmlApplicationContext context=null;
			String configPath = obtainConfigPath(config.getServletContext());
			if (configPath!=null && !configPath.trim().equals("") && new File(configPath).exists()) {
				try {
					String locationFile = "file:" + configPath + File.separator + name;
					logger.info("FILE DI CONFIGURAZIONE ESTERNO: "  + locationFile);
					context = new FileSystemXmlApplicationContext(locationFile);
				}
				catch (Exception e) {
					logger.fatal("Errore caricamento Spring Context da file di configurazione esterno", e);
					context = new ClassPathXmlApplicationContext(name);
				}
			}
			else {
				context = new ClassPathXmlApplicationContext(name);
			}
			SpringAppContext.setContext(context);
		}catch(Exception e){
			logger.fatal("Errore caricamento Spring Context da classpath", e);
			throw new ServletException(e);
		}
	}

	private String obtainConfigPath(ServletContext servletContext) {
		if (servletContext==null)
			return null;
		
		String pathToCheck = servletContext.getInitParameter("CONFIG_PATH");
		String envVarToCheck = servletContext.getInitParameter("CONFIG_ENV_VAR");
		String subDir = servletContext.getInitParameter("CONFIG_SUBDIR");

		logger.info("CONFIG_PATH = " + pathToCheck);
		logger.info("CONFIG_ENV_VAR = " + envVarToCheck);
		logger.info("CONFIG_SUBDIR = " + subDir);
		
		String rootConfDir = null;
		if (envVarToCheck!=null) {
			rootConfDir =  System.getProperty(envVarToCheck);
			logger.info("rootConfDir come System Property = " + rootConfDir);
			
			if (rootConfDir==null) {
				rootConfDir =  System.getenv(envVarToCheck);
				logger.info("rootConfDir come Env var = " + rootConfDir);
			}
		}
		else if (pathToCheck!=null) {
			rootConfDir =  pathToCheck;
			logger.info("rootConfDir come path = " + rootConfDir);
		}
		else 
			return null;
		
		if (rootConfDir==null)
			rootConfDir="";
		if (!"".equals(rootConfDir) && !rootConfDir.endsWith(File.separator))
			rootConfDir+=File.separator;
		if (subDir!=null)
			rootConfDir+=subDir;
		
		return rootConfDir;
	}
}
