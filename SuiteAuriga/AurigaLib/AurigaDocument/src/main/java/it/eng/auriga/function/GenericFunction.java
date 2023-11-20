/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.function.bean.ArrayBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.generic.VersionHandler;
import it.eng.auriga.repository2.jaxws.webservices.common.ConnectionWrapper;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.spring.utility.SpringAppContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public abstract class GenericFunction {

	protected Connection mConnection;
	protected VersionHandler mVersionHandler;
	protected String token;
	protected String userIdLavoro;
	
	public GenericFunction() throws Exception{
		
	}
	
	protected void chechArray(Object lObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Field[] lFields = lObject.getClass().getDeclaredFields();
		for (Field lField : lFields){
			if (lField.getType().equals(String[].class)){
				String[] lString = BeanUtilsBean2.getInstance().getArrayProperty(lObject, lField.getName());
				if (lString == null){
					BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), new String[]{});
				}
			}
		}
	}
	
	protected void init(AurigaLoginBean lAurigaLoginBean) throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(lAurigaLoginBean.getSchema());
		subject.setUuidtransaction(lAurigaLoginBean.getUuid());
		SubjectUtil.subject.set(subject);   
		final ConnectionWrapper lConnectionWrapper = new ConnectionWrapper();
		Session session = HibernateUtil.begin();
		session.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				lConnectionWrapper.setConnection(paramConnection);
			}
		});
		mConnection = lConnectionWrapper.getConnection();
		if (StringUtils.isNotBlank(subject.getUuidtransaction())){
			HibernateUtil.addConnection(subject.getUuidtransaction(), mConnection);
		}
		mVersionHandler = (VersionHandler) SpringAppContext.getContext().getBean("VersionHandler");
		token = lAurigaLoginBean.getToken();
	}
	
	public GenericFunctionBean initWS(AurigaLoginBean lAurigaLoginBean) throws Exception {
		
		Session session = null;
		GenericFunctionBean gen = new GenericFunctionBean();
		try {
			final ConnectionWrapper lConnectionWrapper = new ConnectionWrapper();
			
			SubjectBean subject =  SubjectUtil.subject.get();
			subject.setIdDominio(lAurigaLoginBean.getSchema());
			subject.setUuidtransaction(lAurigaLoginBean.getUuid());
			SubjectUtil.subject.set(subject);   
			
			session = HibernateUtil.begin();
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					lConnectionWrapper.setConnection(paramConnection);
				}
			});
			gen.setmConnection(lConnectionWrapper.getConnection());
			if (StringUtils.isNotBlank(subject.getUuidtransaction())){
				HibernateUtil.addConnection(subject.getUuidtransaction(), gen.getmConnection());
			}
			gen.setmVersionHandler((VersionHandler) SpringAppContext.getContext().getBean("VersionHandler"));
			gen.setToken(lAurigaLoginBean.getToken());	
		}
		finally {
			try {
				if (session != null)
					HibernateUtil.release(session);
				
			} catch (Exception e) {
			}
		}		
		return gen;
	}
	
	protected void close() throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		if (StringUtils.isNotBlank(subject.getUuidtransaction())){
			HibernateUtil.removeConnection(subject.getUuidtransaction());
		}
		mConnection.close();		
	}
	
	protected Exception buildException(Exception e)throws Exception {
		SubjectBean subject =  SubjectUtil.subject.get();
		if (HibernateUtil.isForcedToCloseConnection(subject.getUuidtransaction())){
			return new Exception("CONNECTION_CLOSED");
		}
		return e;
	}
	
	public String[] transformResult(Object[] pObjects){
		String[] result = new String[pObjects.length];
		for (int i = 0; i < pObjects.length; i++){
			result[i] = (String)pObjects[i];
		}
		return result;
	}
	
	public static String[] getStringsFromArrayBean(ArrayBean<String> pBean){
		if (pBean == null) return null;
		if (pBean.getList() == null) return (new String[]{});
		return pBean.getList().toArray(new String[]{});
	}
	
	public static ArrayBean<Object> getObjectList(Object[] pObjects){
		if (pObjects==null) return null;
		ArrayList<Object> lArrayList = new ArrayList<Object>(Arrays.asList(pObjects));
		return new ArrayBean<Object>(lArrayList);
	}
	
}
