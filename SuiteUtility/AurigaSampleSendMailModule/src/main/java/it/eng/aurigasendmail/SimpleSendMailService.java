package it.eng.aurigasendmail;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;

import it.eng.aurigasendmail.bean.AurigaAttachmentMailToSendBean;
import it.eng.aurigasendmail.bean.AurigaDummyMailToSendBean;
import it.eng.aurigasendmail.bean.AurigaMailToSendBean;
import it.eng.aurigasendmail.bean.AurigaResultSendMail;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.simplesendermail.service.SimpleSenderMail;
import it.eng.simplesendermail.service.bean.AttachmentMailToSendBean;
import it.eng.simplesendermail.service.bean.DummyMailToSendBean;
import it.eng.simplesendermail.service.bean.MailToSendBean;
import it.eng.simplesendermail.service.bean.ResultSendMail;

@Service(name="SimpleSendMailService")
public class SimpleSendMailService {

	@Operation(name = "simpleSendMail")
	public AurigaResultSendMail simpleSendMail(AurigaMailToSendBean pMailToSendBean) throws IllegalAccessException, InvocationTargetException{
		MailToSendBean lMailToSendBean = buildMailToSendBean(pMailToSendBean);
		ResultSendMail lResultSendMail = new SimpleSenderMail().simpleSendMail(lMailToSendBean);
		AurigaResultSendMail lAurigaResultSendMail = new AurigaResultSendMail();
		BeanUtilsBean2.getInstance().copyProperties(lAurigaResultSendMail, lResultSendMail);
		return lAurigaResultSendMail;
	}
	@Operation(name = "asyncSimpleSendMail")
	public AurigaResultSendMail asyncSimpleSendMail(AurigaMailToSendBean pMailToSendBean) throws IllegalAccessException, InvocationTargetException{
		MailToSendBean lMailToSendBean = buildMailToSendBean(pMailToSendBean);
		ResultSendMail lResultSendMail = new SimpleSenderMail().asyncSimpleSendMail(lMailToSendBean);
		AurigaResultSendMail lAurigaResultSendMail = new AurigaResultSendMail();
		BeanUtilsBean2.getInstance().copyProperties(lAurigaResultSendMail, lResultSendMail);
		return lAurigaResultSendMail;
	}
	protected MailToSendBean buildMailToSendBean(
			AurigaMailToSendBean pMailToSendBean)
			throws IllegalAccessException, InvocationTargetException {
		MailToSendBean lMailToSendBean = new MailToSendBean();
		BeanUtilsBean2.getInstance().copyProperties(lMailToSendBean, pMailToSendBean);
		if (pMailToSendBean.getAttachmentMailToSendBeans()!=null){
			List<AttachmentMailToSendBean> lListAttach = new ArrayList<AttachmentMailToSendBean>(pMailToSendBean.getAttachmentMailToSendBeans().size());
			for (AurigaAttachmentMailToSendBean lAurigaAttachmentMailToSendBean : pMailToSendBean.getAttachmentMailToSendBeans()){
				AttachmentMailToSendBean lAttachmentMailToSendBean = new AttachmentMailToSendBean();
				BeanUtilsBean2.getInstance().copyProperties(lAttachmentMailToSendBean, lAurigaAttachmentMailToSendBean);
				lListAttach.add(lAttachmentMailToSendBean);
			}
			lMailToSendBean.setAttachmentMailToSendBeans(lListAttach);
		}
		return lMailToSendBean;
	}
	@Operation(name = "sendFromConfigured")
	public AurigaResultSendMail sendFromConfigured(AurigaDummyMailToSendBean pAurigaDummyMailToSendBean, String pStringStrConfiguredAccount) throws IllegalAccessException, InvocationTargetException{
		DummyMailToSendBean lDummyMailToSendBean = buildDummyMailToSendBean(pAurigaDummyMailToSendBean);
		ResultSendMail lResultSendMail = new SimpleSenderMail().sendFromConfigured(lDummyMailToSendBean, pStringStrConfiguredAccount);
		AurigaResultSendMail lAurigaResultSendMail = new AurigaResultSendMail();
		BeanUtilsBean2.getInstance().copyProperties(lAurigaResultSendMail, lResultSendMail);
		return lAurigaResultSendMail;
	}
	@Operation(name = "asyncSendFromConfigured")
	public AurigaResultSendMail asyncSendFromConfigured(AurigaDummyMailToSendBean pAurigaDummyMailToSendBean, String pStringStrConfiguredAccount) throws IllegalAccessException, InvocationTargetException{
		DummyMailToSendBean lDummyMailToSendBean = buildDummyMailToSendBean(pAurigaDummyMailToSendBean);
		ResultSendMail lResultSendMail = new SimpleSenderMail().asyncSendFromConfigured(lDummyMailToSendBean, pStringStrConfiguredAccount);
		AurigaResultSendMail lAurigaResultSendMail = new AurigaResultSendMail();
		BeanUtilsBean2.getInstance().copyProperties(lAurigaResultSendMail, lResultSendMail);
		return lAurigaResultSendMail;
	}
	protected DummyMailToSendBean buildDummyMailToSendBean(
			AurigaDummyMailToSendBean pAurigaDummyMailToSendBean)
			throws IllegalAccessException, InvocationTargetException {
		DummyMailToSendBean lDummyMailToSendBean = new DummyMailToSendBean();
		BeanUtilsBean2.getInstance().copyProperties(lDummyMailToSendBean, pAurigaDummyMailToSendBean);
		if (pAurigaDummyMailToSendBean.getAttachmentMailToSendBeans()!=null){
			List<AttachmentMailToSendBean> lListAttach = new ArrayList<AttachmentMailToSendBean>(pAurigaDummyMailToSendBean.getAttachmentMailToSendBeans().size());
			for (AurigaAttachmentMailToSendBean lAurigaAttachmentMailToSendBean : pAurigaDummyMailToSendBean.getAttachmentMailToSendBeans()){
				AttachmentMailToSendBean lAttachmentMailToSendBean = new AttachmentMailToSendBean();
				BeanUtilsBean2.getInstance().copyProperties(lAttachmentMailToSendBean, lAurigaAttachmentMailToSendBean);
				lListAttach.add(lAttachmentMailToSendBean);
			}
			lDummyMailToSendBean.setAttachmentMailToSendBeans(lListAttach);
		}
		return lDummyMailToSendBean;
	}
}
