/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class MyConnection {
    public static Connection createConnection()throws SQLException,ClassNotFoundException
    {
                Connection con=null;
                Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/property","root","vertrigo");
                return con;
    }
    public static void main(String[] args) {
		MyConnection obj=new MyConnection();
		try {
			Connection con=obj.createConnection();
			//System.out.println("Connected:"+con);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
