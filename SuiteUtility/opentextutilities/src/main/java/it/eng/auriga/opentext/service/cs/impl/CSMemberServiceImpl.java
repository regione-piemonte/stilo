package it.eng.auriga.opentext.service.cs.impl;

import org.springframework.stereotype.Service;

import com.opentext.livelink.service.memberservice.Member;
import com.opentext.livelink.service.memberservice.MemberService;
import com.opentext.livelink.service.memberservice.User;
import com.sun.xml.ws.developer.WSBindingProvider;

import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.CSMemberService;

@Service("memberCSService")
public class CSMemberServiceImpl extends AbstractManageCSService implements CSMemberService{
	
	
	
	
	public User retrieveLoggedUser(String otToken) throws ContentServerException {
		MemberService memberService = this.getMemberServiceClient(otToken);
		return memberService.getAuthenticatedUser();
	}

	
	private MemberService getMemberServiceClient(String otToken) throws ContentServerException {
		MemberService memberService = managementCSClient.getMemberServiceClient();
		managementCSClient.updateClientWithAuthorization(otToken, (WSBindingProvider) memberService);
		
		
		return memberService;
	}
	
	
	//////da aggiungere alle api del Content
	
	public Member retrieveMemberById(String otToken, Long idUser) throws ContentServerException {
		MemberService memberService = this.getMemberServiceClient(otToken);
		
		return memberService.getMemberById(idUser);
	}


}
