/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.database.store.exception.StoredProcException;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

public class HibernateStoreUtil {
	
	public void settingParameterOnStore(CallableStatement stmt, Object bean, String name, int position, int type, Connection con) throws Exception{
		settingParameterOnStore(stmt, bean, null, name, position, type, con);
	}

	public void settingParameterOnStore(CallableStatement stmt, Object bean, BeanWrapperImpl beanWrapper, String name, int position, int type, Connection con) throws StoredProcException {
		try {
			Object value = null;
			if (beanWrapper != null && BeanPropertyUtility.isSpringBeanWrapperEnabled()) {
				value = BeanPropertyUtility.getPropertyValue(bean, beanWrapper, name);
			} else {
				value = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, name);
			}
			if(value != null){
				if(type==Types.CLOB){
					ClobUtil util = new ClobUtil();
					CLOB clob = util.setString(value.toString(), con);
					stmt.setClob(position, clob);
				} else if(type==Types.BLOB){
					BlobUtil util = new BlobUtil();
					BLOB clob = util.setByte((byte[])value, con);
					stmt.setBlob(position, clob);
				} else if(type==Types.INTEGER){
					setINIntegerOrNull(stmt, position, value);
				}else if(type==Types.DECIMAL){
					setINDecimalOrNull(stmt, position, value);
				}else if(type==oracle.jdbc.internal.OracleTypes.ARRAY){
					setINArrayOrNull(stmt, position, con, value);
				}else{
					stmt.setString(position, value.toString());
				}
			}else{
				stmt.setNull(position, type);
			}
		} catch (Exception e) {
			throw new StoredProcException(e);
		}
	}

	private void setINArrayOrNull(CallableStatement stmt, int position, Connection con, Object value)
			throws SQLException {
		try{
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor("STRING_VARRAY", con);
			ARRAY varray = new ARRAY(desc, con, value);
			stmt.setArray(position, varray);
		}catch(Exception e){
			stmt.setNull(position, oracle.jdbc.internal.OracleTypes.ARRAY);
		}
	}

	private void setINDecimalOrNull(CallableStatement stmt, int position, Object value) throws SQLException {
		try{
			stmt.setDouble(position, new BigDecimal(value.toString()).doubleValue());
		}catch(Exception e){
			stmt.setNull(position, Types.DECIMAL);
		}
	}

	private void setINIntegerOrNull(CallableStatement stmt, int position, Object value) throws SQLException {
		try{
			stmt.setInt(position, new Integer(value.toString()));
		}catch(Exception e){
			stmt.setNull(position, Types.INTEGER);
		}
	}
	
	public void settinParameterOnBean(CallableStatement stmt, Object bean, String name, int position, int type) throws StoredProcException {
		settinParameterOnBean(stmt, bean, null, name, position, type);
	}
		
	public void settinParameterOnBean(CallableStatement stmt, Object bean, BeanWrapperImpl beanWrapper, String name, int position, int type) throws StoredProcException {
		try {
			PropertyUtilsBean utils = null;
			if (beanWrapper == null || !BeanPropertyUtility.isSpringBeanWrapperEnabled()) {
				utils = BeanUtilsBean2.getInstance().getPropertyUtils();
			}
			if(type==Types.VARCHAR){
				if (utils == null) {
					BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, stmt.getString(position));
				} else {
					utils.setProperty(bean, name, stmt.getString(position));
				}
			}else if(type==Types.CLOB){
				ClobUtil util = new ClobUtil();
				if (utils == null) {
					BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, util.getString(stmt.getClob(position)));
				} else {
					utils.setProperty(bean, name, util.getString(stmt.getClob(position)));
				}
			}else if(type==Types.NUMERIC){
				if (utils == null) {
					BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, stmt.getBigDecimal(position));
				} else {
					utils.setProperty(bean, name, stmt.getBigDecimal(position));
				}
			}else if(type==Types.INTEGER){
				if (utils == null) {
					BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, stmt.getInt(position));
				} else {
					utils.setProperty(bean, name, stmt.getInt(position));
				}
			}else if(type==Types.DECIMAL){
				setOUTDecimal(stmt, bean, beanWrapper, utils, name, position);
			}else if(type==Types.TIMESTAMP){
				setOUTTimestamp(stmt, bean, beanWrapper, utils, name, position);
			}else if(type==Types.DATE){
				if (utils == null) {
					BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, stmt.getDate(position));
				} else {
					utils.setProperty(bean, name, stmt.getDate(position));
				}
			}else if(type==oracle.jdbc.internal.OracleTypes.ARRAY){
				setOUTArray(stmt, bean, beanWrapper, utils, name, position);
			}else if(type==Types.BLOB){
				BlobUtil util = new BlobUtil();
				if (utils == null) {
					BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, util.getByte(stmt.getBlob(position)));
				} else {
					utils.setProperty(bean, name, util.getByte(stmt.getBlob(position)));
				}
			}
		} catch (Exception e) {
			throw new StoredProcException(e);
		}
	}

	private void setOUTArray(CallableStatement stmt, Object bean, BeanWrapperImpl beanWrapper, PropertyUtilsBean utils, String name, int position)
			throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Array array = stmt.getArray(position);
		String[] values = (String[]) array.getArray();
		if (utils == null) {
			BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, values);
		} else {
			utils.setProperty(bean, name, values);
		}
	}

	private void setOUTTimestamp(CallableStatement stmt, Object bean, BeanWrapperImpl beanWrapper, PropertyUtilsBean utils, String name, int position)
			throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Timestamp timestamp = stmt.getTimestamp(position);
		if (timestamp!=null){
			if (utils == null) {
				BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, new Date(timestamp.getTime()));
			} else {
				utils.setProperty(bean, name, new Date(timestamp.getTime()));
			}
		}
	}

	private void setOUTDecimal(CallableStatement stmt, Object bean, BeanWrapperImpl beanWrapper, PropertyUtilsBean utils, String name, int position)
			throws IllegalAccessException, InvocationTargetException, SQLException, NoSuchMethodException {
		if (name.equals("parametro_1")){
			Method[] allMethods = bean.getClass().getDeclaredMethods();
			for (Method lMethod : allMethods){
				if (lMethod.getName().equals("setParametro_1")){
					lMethod.invoke(bean,stmt.getBigDecimal(position));
				}
			}
		} else {
			if (utils == null) {
				BeanPropertyUtility.setPropertyValue(bean, beanWrapper, name, stmt.getBigDecimal(position));
			} else {
				utils.setProperty(bean, name, stmt.getBigDecimal(position));
			}
		}
	}
}