package com.was.ios.common.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@Component
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class DBHelper {

	private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);

	@Autowired
	private DataSource ds;
	
	private Connection conn = null;


	/*********************************************************************************************************/
	/********************************************* public method *********************************************/
	/*********************************************************************************************************/
	
	/********************************************* select method *********************************************/
	/* 
	 *  SELECT결과물을 단일로 return
	 */
	public JSONObject getObject(String procName, Object[] params) {
	    JSONObject jsonObject = new JSONObject();
	    
		try {		
			ResultSet rs = this.excuteQuery(procName, params);
			if(rs == null) return null;
			
			while (rs.next()) {
			    int columns = rs.getMetaData().getColumnCount();
			    for (int i = 0; i < columns; i++) {
			    	jsonObject.put(rs.getMetaData().getColumnLabel(i + 1), rs.getObject(i + 1));
			    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}

	/* 
	 *  SELECT 결과물을 Array로 return
	 */
	public JSONArray getArray(String procName, Object[] params) {		
		JSONArray jsonArray = new JSONArray();
		
		try {
			ResultSet rs = this.excuteQuery(procName, params);
			if(rs == null) return null;
			
			while (rs.next()) {			    
			    JSONObject obj = new JSONObject();

			    int columns = rs.getMetaData().getColumnCount();
			    for (int i = 0; i < columns; i++) {
			    	obj.put(rs.getMetaData().getColumnLabel(i + 1), rs.getObject(i + 1));
			    }
			    
			    jsonArray.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}

	/********************************************* save method *********************************************/

	// Insert, Delete, Update
	public boolean save(String procName, Object[] params) {
		boolean result = true;
		
		try {
			String parameter = this.SetParamCreate(params);
			String sql = this.setSql(procName, parameter);
			
			// 1. AutoCommit 해제
			conn.setAutoCommit(false);

			CallableStatement cstmt = this.conn.prepareCall(sql);
			
			for(int i = 1; i <= params.length; i++) {
				cstmt.setString(i, params[i-1].toString());	
			}
			result = cstmt.executeUpdate() == 1 ? true : false;

			// Commit data here
			conn.commit();
		} catch(SQLException se) {
			se.printStackTrace();
			// RollBack
			try {
				if(conn != null) {
					conn.rollback();
				}
			} catch(SQLException se2) {
				se2.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**********************************************************************************************************/
	/********************************************* private method *********************************************/
	/**********************************************************************************************************/
	
	private ResultSet excuteQuery(String procName, Object[] params) {
		ResultSet rs = null;
		try {
			String parameter = this.SetParamCreate(params);
			String sql = this.setSql(procName, parameter);
			
			int rsCount = 0;
			
			CallableStatement cstmt = this.conn.prepareCall(sql);
			
			for(int i = 1; i <= params.length; i++) {
				cstmt.setString(i, params[i-1].toString());	
			}
			rs = cstmt.executeQuery();	
		}
		catch(Exception ex){
			System.out.println("Exception : " + ex);
		} 
		
		return rs;
	}

	private String SetParamCreate(Object[] param) {
		String parameter = "";

		if(param.length > 0)
		{
			for(int i=0; i<param.length; i++) {
				parameter = parameter + "?,";			
			}
			parameter = parameter.substring(0, parameter.length()-1);
		}

		return parameter;
	}
	private String setSql(String procName, String param) {
		return "{call " + procName + "(" + param + ")}";
	}
	
	
	
	/******************************************************************************************************/
	/********************************************* connection *********************************************/
	/******************************************************************************************************/
	public void getConnection() {
		if (ds == null) {
			logger.error("DataSource NullPointException");
		} else {
			try {
				if(this.conn == null) this.conn = ds.getConnection();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void terminate() {
		if(ds == null) {
			logger.error("DataSource NullPointException");
		} else {
			try {
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
