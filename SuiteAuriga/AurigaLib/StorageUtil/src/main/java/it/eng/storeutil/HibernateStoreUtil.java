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
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

public class HibernateStoreUtil {
	
	public void settingParameterOnStore(CallableStatement stmt, Object bean, String name, int position, int type, Connection con) throws Exception{
		settingParameterOnStore(stmt, bean, null, name, position, type, con);
	}

	// Il parametro beanWrapper non viene utilizzato, su AurigaBusinessModule e AurigaBusiness la classe è sovrascitta e il paranetro utilizzato.
	// Ho messo questa firma solamente per mantenere la copatibilità
	public void settingParameterOnStore(CallableStatement stmt, Object bean, BeanWrapperImpl beanWrapper, String name, int position, int type, Connection con) throws StoredProcException {
		try {
			Object value = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, name);
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
	
	// Il parametro beanWrapper non viene utilizzato, su AurigaBusinessModule e AurigaBusiness la classe è sovrascitta e il paranetro utilizzato.
	// Ho messo questa firma solamente per mantenere la copatibilità
	public void settinParameterOnBean(CallableStatement stmt, Object bean, String name, int position, int type) throws StoredProcException {
		settinParameterOnBean(stmt, bean, null, name, position, type);
	}
		
	public void settinParameterOnBean(CallableStatement stmt, Object bean, BeanWrapperImpl beanWrapper, String name, int position, int type) throws StoredProcException {
		try {
			PropertyUtilsBean utils = BeanUtilsBean2.getInstance().getPropertyUtils();
			if(type==Types.VARCHAR){
				utils.setProperty(bean, name, stmt.getString(position));
			}else if(type==Types.CLOB){
				ClobUtil util = new ClobUtil();
				utils.setProperty(bean, name, util.getString(stmt.getClob(position)));
			}else if(type==Types.NUMERIC){
				utils.setProperty(bean, name, stmt.getBigDecimal(position));
			}else if(type==Types.INTEGER){
				utils.setProperty(bean, name, stmt.getInt(position));
			}else if(type==Types.DECIMAL){
				setOUTDecimal(stmt, bean, name, position, utils);
			}else if(type==Types.TIMESTAMP){
				setOUTTimestamp(stmt, bean, name, position, utils);
			}else if(type==Types.DATE){
				utils.setProperty(bean, name, stmt.getDate(position));
			}else if(type==oracle.jdbc.internal.OracleTypes.ARRAY){
				setOUTArray(stmt, bean, name, position, utils);
			}else if(type==Types.BLOB){
				BlobUtil util = new BlobUtil();
				utils.setProperty(bean, name, util.getByte(stmt.getBlob(position)));
			}
		} catch (Exception e) {
			throw new StoredProcException(e);
		}
	}

	private void setOUTArray(CallableStatement stmt, Object bean, String name, int position, PropertyUtilsBean utils)
			throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Array array = stmt.getArray(position);
		String[] values = (String[]) array.getArray();
		utils.setProperty(bean, name, values);
	}

	private void setOUTTimestamp(CallableStatement stmt, Object bean, String name, int position, PropertyUtilsBean utils)
			throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Timestamp timestamp = stmt.getTimestamp(position);
		if (timestamp!=null){
			utils.setProperty(bean, name, new Date(timestamp.getTime()));
		}
	}

	private void setOUTDecimal(CallableStatement stmt, Object bean, String name, int position, PropertyUtilsBean utils)
			throws IllegalAccessException, InvocationTargetException, SQLException, NoSuchMethodException {
		if (name.equals("parametro_1")){
			Method[] allMethods = bean.getClass().getDeclaredMethods();
			for (Method lMethod : allMethods){
				if (lMethod.getName().equals("setParametro_1")){
					lMethod.invoke(bean,stmt.getBigDecimal(position));
				}
			}
		} else utils.setProperty(bean, name, stmt.getBigDecimal(position));
	}
}