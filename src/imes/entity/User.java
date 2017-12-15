package imes.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String userid;
    private String name;
    private String password;
    private String email;
    private String salt;
    private String manager;
    private String status;
    private String admin;
    private String crtdt;
    private String chgdt;

    
    public static List<String> getAllUserIds(Connection conn){
    	
    	List<String> list = new ArrayList<String>();
    	
    	String sql = "SELECT USERID FROM USERS WHERE STATUS = 'A' ";
    	
    	try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			ResultSet rst = pstm.executeQuery();
			
			while (rst.next()){
				
				list.add(rst.getString("USERID"));
			}
			
			rst.close();
			
			pstm.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return list;
    }
    
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCrtdt() {
        return crtdt;
    }

    public void setCrtdt(String crtdt) {
        this.crtdt = crtdt;
    }

    public String getStatus() {
        return status;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChgdt() {
        return chgdt;
    }

    public void setChgdt(String chgdt) {
        this.chgdt = chgdt;
    }
}
