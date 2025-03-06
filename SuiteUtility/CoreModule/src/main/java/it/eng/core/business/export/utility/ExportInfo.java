package it.eng.core.business.export.utility;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
/**
 * info per eseguire l'export
 * @author Russo
 *
 */
public class ExportInfo {
	//cartella dove fare l'export
	private String baseFolder;
	//nome del file di export
	private String fileName;
	//filtro sql da eseguire per esportare risultati parziali
	private String sqlFilter;
	//formati di export da generare
	private Set<ExportFormat> exportFormat;
	//TODO add column title mapping per far scrivere un titolo diverso dal nome della colonna
	private LinkedHashMap<String, String> columMapping = new LinkedHashMap<String, String>();
 
	 
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBaseFolder() {
		return baseFolder;
	}
	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}
	public String getSqlFilter() {
		return sqlFilter;
	}
	public void setSqlFilter(String sqlFilter) {
		this.sqlFilter = sqlFilter;
	}
	 
	
	public Set<ExportFormat> getExportFormat() {
		return exportFormat;
	}
	public void setExportFormat(Set<ExportFormat> exportFormat) {
		this.exportFormat = exportFormat;
	}
	/**
	 * crea le info di export 
	 * @param baseFolder
	 * @param baseName
	 * @param sqlFilter
	 * @param format
	 * @return
	 */
	public static ExportInfo createInfo(String baseFolder,String fileName,String sqlFilter, ExportFormat... format){
		ExportInfo info=new ExportInfo();
		info.setBaseFolder(baseFolder);
		info.setFileName(fileName);
		info.setSqlFilter(sqlFilter);
		info.setExportFormat(new HashSet<ExportFormat>());
		for (ExportFormat exportFormat : format) {
			info.addFormat(exportFormat);
		}
		return info;
	}
	
//	public static ExportInfo createInfo(String baseFolder,String baseName,Criteria criteria, ExportFormat... format){
//		ExportInfo info=new ExportInfo();
//		info.setBaseFolder(baseFolder);
//		info.setBaseName(baseName);
//		info.setSqlFilter(CriteriaUtil.toSql(criteria));
//		info.setExportFormat(new HashSet<ExportFormat>());
//		for (ExportFormat exportFormat : format) {
//			info.addFormat(exportFormat);
//		}
//		return info;
//	}
	
	public void addFormat(ExportFormat format){
		getExportFormat().add(format);
	}
	
	public void addCoulumn(String columnName,String title){
		columMapping.put(columnName, title);
	}
	public LinkedHashMap<String, String> getColumMapping() {
		return columMapping;
	}
	public void setColumMapping(LinkedHashMap<String, String> columMapping) {
		this.columMapping = columMapping;
	}
	
	
}
