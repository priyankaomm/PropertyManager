/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.query;

import java.net.HttpURLConnection;
import java.net.URL;
import javafx.embed.swing.JFXPanel;

/**
 *
 * @author comp14
 */
public class Mapconnection 
{
    public static  boolean checkConnection()
            {
                boolean flag=false;
		try {
			try{
				URL url = new URL("http://www.google.com");
				System.out.println(url.getHost());
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.connect();
				if (con.getResponseCode() == 200){
                                    flag=true;
					System.out.println("Connection established!!");
				}
                                else{
                                    flag=false;
                                 
                                }
			} catch (Exception exception) {
				System.out.println("No Connection");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
                return flag;
	}
            
}
