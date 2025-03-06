package it.eng.suiteutility.mimedetector;

import java.util.Collection;
import java.util.HashSet;

import javax.activation.MimeType;

public class DetectorPrototype {
	private String name;
	private String description;
	private String clazz;
	private int role;
	private Collection<MimeType> mimes;
	
	public DetectorPrototype(String name, String description, String clazz, int role) {
		this.name = name;
		this.description = description;
		this.clazz = clazz;
		mimes = new HashSet<MimeType>();
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public void addMime(MimeType mimeType) {
		mimes.add(mimeType);
	}
	public Collection<MimeType> getMimes() {
		return mimes;
	}

	public boolean equals(DetectorPrototype detector) {
		return (detector!=null && detector.getClazz().equals(this.getClazz()));
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
  }

