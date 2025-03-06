package it.eng.module.archiveUtility;

public enum ArchiveType {

	ZIP("zip","application/x-compressed"),
	ZIP2("zip","application/x-zip-compressed"),
	RAR("rar","application/x-rar-compressed"),
	_7Z("7z","application/x-7z"),
	TAR("tar","application/x-tar");
	
	private String type;
	private String mime;
	
	private ArchiveType(String type, String mime) {
        this.type = type;
        this.mime = mime;
    }

	public String getType() {
		return type;
	}
	
	public String getMime() {
		return mime;
	}
	
}
