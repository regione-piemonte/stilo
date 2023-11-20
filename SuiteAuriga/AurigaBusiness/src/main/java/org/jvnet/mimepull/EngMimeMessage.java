/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.nio.ByteBuffer;

public class EngMimeMessage extends MIMEMessage {
	
	
	public EngMimeMessage(InputStream in, String boundary) {
		super(in, boundary);
	}
	
	public EngMimeMessage(InputStream in, String boundary, MIMEConfig config) {
		super(in,boundary,config);
	}
	
	
	@Override
	public synchronized boolean makeProgress() {
		if (!it.hasNext()) {
			return false;
		}

		MIMEEvent event = it.next();

		switch (event.getEventType()) {
		case START_MESSAGE:
			LOGGER.fine("MIMEEvent=" + MIMEEvent.EVENT_TYPE.START_MESSAGE);
			break;

		case START_PART:
			LOGGER.fine("MIMEEvent=" + MIMEEvent.EVENT_TYPE.START_PART);
			break;

		case HEADERS:
			LOGGER.fine("MIMEEvent=" + MIMEEvent.EVENT_TYPE.HEADERS);
			MIMEEvent.Headers headers = (MIMEEvent.Headers) event;
			InternetHeaders ih = headers.getHeaders();
			List<String> cids = ih.getHeader("content-id");
			String cid = null;
			if(cids != null) {
				if(currentIndex > 0) {
					// Vuol dire che abbiamo superato il root header, quindi possiamo modificare gli header.
					ih.changeHeaderLine("content-id", currentIndex);
				}
				cid = ih.getHeader("content-id").get(0);
			} else {
				cid = currentIndex + "";
			}
			
			if (cid.length() > 2 && cid.charAt(0) == '<') {
				cid = cid.substring(1, cid.length() - 1);
			}
			MIMEPart listPart = (currentIndex < partsList.size()) ? partsList.get(currentIndex) : null;
			MIMEPart mapPart = getDecodedCidPart(cid);
			if (listPart == null && mapPart == null) {
				currentPart = getPart(cid);
				partsList.add(currentIndex, currentPart);
			} else if (listPart == null) {
				currentPart = mapPart;
				partsList.add(currentIndex, mapPart);
			} else if (mapPart == null) {
				currentPart = listPart;
				currentPart.setContentId(cid);
				partsMap.put(cid, currentPart);
			} else if (listPart != mapPart) {
				throw new MIMEParsingException("Created two different attachments using Content-ID and index");
			}
			currentPart.setHeaders(ih);
			break;

		case CONTENT:
			LOGGER.finer("MIMEEvent=" + MIMEEvent.EVENT_TYPE.CONTENT);
			MIMEEvent.Content content = (MIMEEvent.Content) event;
			ByteBuffer buf = content.getData();
			currentPart.addBody(buf);
			break;

		case END_PART:
			LOGGER.fine("MIMEEvent=" + MIMEEvent.EVENT_TYPE.END_PART);
			currentPart.doneParsing();
			++currentIndex;
			break;

		case END_MESSAGE:
			LOGGER.fine("MIMEEvent=" + MIMEEvent.EVENT_TYPE.END_MESSAGE);
			parsed = true;
			try {
				in.close();
			} catch (IOException ioe) {
				throw new MIMEParsingException(ioe);
			}
			break;

		default:
			throw new MIMEParsingException("Unknown Parser state = " + event.getEventType());
		}
		return true;
	}
	
}
