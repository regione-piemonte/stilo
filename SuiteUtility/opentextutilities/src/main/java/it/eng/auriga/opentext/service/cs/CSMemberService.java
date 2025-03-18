/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.opentext.livelink.service.memberservice.Member;
import com.opentext.livelink.service.memberservice.User;

import it.eng.auriga.opentext.exception.ContentServerException;

public interface CSMemberService {
	
	public User retrieveLoggedUser(String otToken) throws ContentServerException;
	
	public Member retrieveMemberById(String otToken, Long idUser) throws ContentServerException ;
	
}
