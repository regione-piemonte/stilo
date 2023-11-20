/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

public class SingletonDataSource {

	Logger logger = Logger.getLogger(SingletonDataSource.class);

	private static SingletonDataSource instance = new SingletonDataSource();
	
	private SingletonDataSource(){}
	
	public static SingletonDataSource getInstance(){
		return instance;
	}
	
	public void initialize(){
		Reflections reflections = new Reflections("it.eng");
		Set<Class<?>> datasourcesclasses = reflections.getTypesAnnotatedWith(Datasource.class);
		Iterator<Class<?>> iteratore = datasourcesclasses.iterator();
		logger.debug("SingletonDataSource initialize");
		while(iteratore.hasNext()){
			Class classe = iteratore.next();
			datasources.put(((Datasource)classe.getAnnotation(Datasource.class)).id(),classe.getName());
			logger.debug(((Datasource)classe.getAnnotation(Datasource.class)).id() + ": " + classe.getName());
		}
	}
	
	
	private Map<String,String> datasources = new HashMap<String,String>();

	public Map<String, String> getDatasources() {
		
		//Se è vuoto lo inizializzo
		return datasources;
	}

	public void setDatasources(Map<String, String> datasources) {
		this.datasources = datasources;
	}	
}