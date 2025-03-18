/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.semanticdesktop.aperture.mime.identifier.magic.MagicMimeTypeIdentifier;

public class MagicMimeTypeIdentifierExtension extends MagicMimeTypeIdentifier {

	private static final String MIME_TYPES_RESOURCE = "it/eng/sga/mimedetector/implementations/aperture/config/mimetypes.xml";
	
	public MagicMimeTypeIdentifierExtension() {
		super(MIME_TYPES_RESOURCE);
	}
}
