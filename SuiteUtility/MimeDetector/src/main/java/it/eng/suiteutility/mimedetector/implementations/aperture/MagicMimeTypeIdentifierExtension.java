package it.eng.suiteutility.mimedetector.implementations.aperture;

import org.semanticdesktop.aperture.mime.identifier.magic.MagicMimeTypeIdentifier;

public class MagicMimeTypeIdentifierExtension extends MagicMimeTypeIdentifier {

	private static final String MIME_TYPES_RESOURCE = "it/eng/sga/mimedetector/implementations/aperture/config/mimetypes.xml";
	
	public MagicMimeTypeIdentifierExtension() {
		super(MIME_TYPES_RESOURCE);
	}
}
