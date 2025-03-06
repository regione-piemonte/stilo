package it.eng.core.service.bean;

import it.eng.core.business.KeyGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * marker interface per i bean usati per descrivere attachment
 * Quando occorre remotizzare un metodo che accetta o ritorna  un file , o una collection di file,o un bean con 
 * una prop di tipo file,  i file sono inviati come attach.
 * i parametri che devono essere serializzati come attach sono serializzati mediante una classe che estende 
 * {@link AttachDescription}.<br>
 * Un {@link AttachDescription} contiene sempre una lista di {@link FileIdAssociation}, ad ogni file da inviare 
 * occorre associare un id che consente di recuperarlo in fase di ricostruzione (deserializzazione).
 * Se il parametro da serializzare è un file la lista conterrà un solo elemento se è una collection o un bean 
 * possono esserci più file associati al parametro da serializzare.
 * In fase di serializzazione vengono inviati solo gli id, mentre i files saranno inviati come attach.
 * Riepilogando  un Parametro di tipo "Attach" (file,collection di files bean con file), viene serializzato
 * con un oggetto di tipo {@link AttachDescription} che serializzato invia gli id dei files asssociati al parametro.
 * le classi che estendono AttachDescription oltre alla lista degli id possono inviare altri dati che consentono di 
 * riassociare i parametri di tipo file al parametro originario, ad esempio nel caso di bean con parametri di tipo file
 * viene inviato il nome della prop. associata al file vedi {@link BeanParamsAttachDescription}.
 * 
 * 
 * 
 * @author Russo
 *
 */
public abstract class AttachDescription {
	
	//file associati al parametro, possono essere più di uno perchè puoi
	//avere un paramentro che è una collection di file
	private List<FileIdAssociation> contents= new ArrayList<AttachDescription.FileIdAssociation>();
	
	/**
	 * indica la posizione del paramentro nel metodo (0 primo paraemtro ,1 secondo etc) 
	 * -1 nel caso sia il parametro di ritorno dal metodo
	 */
	private Integer parPosition;// posizione parametro -1 for return
	
	public Integer getParPosition() {
		return parPosition;
	}

	public void setParPosition(Integer parPosition) {
		this.parPosition = parPosition;
	}
	
	public List<FileIdAssociation> getContents() {
		return contents;
	}

	public void setContents(List<FileIdAssociation> contents) {
		this.contents = contents;
	}

	public void addFile(File file){
		addFile(KeyGenerator.gen(),file);
	}
	public void addFile(String id,File file){
		String idAttach=id;
		if(id==null){
			idAttach=KeyGenerator.gen();
		}
		contents.add(new FileIdAssociation(idAttach,file));
	}

	@XmlRootElement
	public static class FileIdAssociation{
		public FileIdAssociation(){
			
		}
		public FileIdAssociation(String id,File file){
			this.content=file;
			this.id=id;
		}
		@XmlTransient
		private  File content;
		
		//identificativo univoco dell'attach ; consente di associare la attachDescription con l'attach inviato
		//questo id deve essere usato come nome dell'attach o nel bodypart per il rest in modo 
		//da associarlo alla AttachDescription ovvero al parametro a cui si riferisce 
		//l'id viene usato per riassociare il giusto file al parametro
		private String id;
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		 
		@XmlTransient
		public File getContent() {
			return content;
		}

		public void setContent(File content) {
			this.content = content;
		}

		
	}
}
