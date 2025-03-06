package it.eng.auriga.opentext.service.cs;

import com.opentext.livelink.service.memberservice.Member;
import com.opentext.livelink.service.memberservice.User;

import it.eng.auriga.opentext.exception.ContentServerException;

public interface CSMemberService {
	
	public User retrieveLoggedUser(String otToken) throws ContentServerException;
	
	public Member retrieveMemberById(String otToken, Long idUser) throws ContentServerException ;
	
}
