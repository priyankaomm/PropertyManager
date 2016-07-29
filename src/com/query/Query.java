/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.query;

import com.Connection.MyConnection;
import com.alternativagame.resource.utils.psd.PSDParser;
import com.alternativagame.resource.utils.psd.layer.PSDLayerPixelData;
import com.alternativagame.resource.utils.psd.layer.PSDLayerStructure;
import com.alternativagame.resource.utils.psd.section.PSDLayerAndMask;
import com.aspose.imaging.imageoptions.PngOptions;
import com.bean.HissaBean;
import com.bean.HistoryBean;
import com.bean.OwnerBean;
import com.bean.ProjectBean;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Query {
    Connection con=null;
    public boolean addProject(ProjectBean pbean) throws FileNotFoundException, SQLException, ClassNotFoundException
{
    boolean flag=false;
      
                    File imgfile = new File(pbean.getLogo_path());
                    //File mapfile = new File(pbean.getMap_path());
                    FileInputStream fin = new FileInputStream(imgfile); 
		  con = MyConnection.createConnection();
		  PreparedStatement pre =
		  con.prepareStatement("insert into project(project_name,project_logo,city,district,location,total_area,ownership,Id,map,survey_no,unit_id,builder_id) "
                          + " values(?,?,?,?,?,?,?,?,?,?,?,?)");
         	  pre.setString(1,pbean.getProject_name());
                  pre.setBinaryStream(2,(InputStream)fin,(int)imgfile.length());
                  pre.setString(3,pbean.getCity());
                  pre.setString(4,pbean.getDistrict());
                  pre.setString(5,pbean.getLocation());
                  pre.setString(6,pbean.getTotal_area());
                  pre.setString(7,pbean.getOwner());
                  pre.setInt(8,pbean.getProj_id());
                  pre.setString(9,pbean.getMap_path());
                  pre.setString(10, pbean.getSurvey_no());
                  pre.setInt(11,pbean.getUnitId());
                  pre.setInt(12,pbean.getBuilder_id());
		  pre.executeUpdate();
                  pre =
		  con.prepareStatement("update plot set plot_survey_no=concat(?,concat('/',plot)),survey_no=?,unit_id=? where project_id=?");
         	  pre.setString(1, pbean.getSurvey_no());
                  pre.setString(2, pbean.getSurvey_no());
                  pre.setInt(3, pbean.getUnitId());
                  pre.setInt(4,pbean.getProj_id());
                            
		  pre.executeUpdate();
                  flag=true;
		  
		
      return flag;
}
    public File ExportPsdLayersToImages(File sPath,String dPath,int pid) throws SQLException
    {
        System.out.println("source="+sPath);
        String d[]=sPath.getName().split(".p");
        String dp=dPath+"/"+d[0]+".png";
        System.out.println("dest="+dp);
        com.aspose.imaging.Image image = com.aspose.imaging.Image.load(sPath.getPath());
        
        PngOptions pngOptions = new PngOptions();  
        
        image.save(dp,pngOptions);
        File fp=new File(dp);
        //String cropFile=dPath+"/"+d[0]+"_crop.png";
        File retFile=cropImage(fp,dp);
        getPlots(sPath,dPath);
        //File dir=new File(dPath+"/Layers");
          //dir.mkdir();
        com.aspose.imaging.fileformats.psd.PsdImage psdImage=
                (com.aspose.imaging.fileformats.psd.PsdImage)image;
        com.aspose.imaging.fileformats.psd.layers.Layer[] allLayers = psdImage.getLayers();
        
        for (int i = 0; i < allLayers.length; i++)
        {   
        // convert and save the layer to PNG file format.
         //allLayers[i].save(dir.getPath()+"/layer" + (i + 1) + ".png" , pngOptions);
         saveCoordinates(allLayers[i],pid,(i+1));
        }
        return retFile;
    }
    //To get coordibnates of layer and save in database
    private void saveCoordinates(com.aspose.imaging.fileformats.psd.layers.Layer img_layer,int pid,int i) throws SQLException 
    {
        try{
             con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("insert into layer_info (project_id,layer,`left`,top) values (?,?,?,?) ");
            pmt.setInt(1, pid);
            pmt.setInt(2, i);
            pmt.setInt(3, img_layer.getLeft());
            pmt.setInt(4, img_layer.getTop());
            pmt.executeUpdate();
            pmt=con.prepareStatement("insert into plot (project_id,plot) values (?,?) ");
            pmt.setInt(1, pid);
            pmt.setInt(2, i);
            //pmt.setInt(3, img_layer.getLeft());
            //pmt.setInt(4, img_layer.getTop());
            pmt.executeUpdate();
            //System.out.println("Layer info saved!!");
            
        }catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        
        
    }
    
    public ResultSet getPlotDetails(int i,int pid) throws SQLException
    {
        ResultSet rs=null;
        try{
            Connection con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select plot,survey_no,plot_survey_no,total_area,sold_area,nonsold_area,unit_name,short_name from plot p inner join unit u on p.unit_id=u.Id where project_id=? and plot=?");
            pmt.setInt(1, pid);
            pmt.setInt(2, i);
            rs=pmt.executeQuery();
           
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
    public ResultSet getPlotDetails(String psurvey) throws SQLException
    {
        ResultSet rs=null;
        try{
            Connection con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select plot,survey_no,plot_survey_no,total_area,sold_area,nonsold_area,unit_name,short_name from plot p inner join unit u on p.unit_id=u.Id where plot_survey_no=?");
            pmt.setString(1, psurvey);
            //pmt.setInt(2, i);
            rs=pmt.executeQuery();
            
           
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
    public ResultSet getPlotTitleOwner(String psurvey) throws SQLException
    {
        ResultSet rs=null;
        try{
            Connection con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select total_area,area_purchased,title_owner,hissa_survey,unit_id,unit_name from plot_hissa_details p,unit u where hissa_survey=? and p.unit_id=u.Id");
            pmt.setString(1, psurvey);
            //pmt.setInt(2, i);
            rs=pmt.executeQuery();
            
           
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
    public ResultSet getPlotDetails(int pid) throws SQLException
    {
        ResultSet rs=null;
        try{
            Connection con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select plot,survey_no,plot_survey_no,total_area,sold_area,nonsold_area,unit_name,short_name from plot p inner join unit u on p.unit_id=u.Id where project_id=?");
            pmt.setInt(1, pid);
            //pmt.setInt(2, i);
            rs=pmt.executeQuery();
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
    //To CROP IMAGE
    public File cropImage(File file,String cropFile) 
    { 
        File outputfile = null;
        //File file = new File();
        try{
        Rectangle r = new Rectangle(50, 80, 800, 550);
        BufferedImage bufferedImage = ImageIO.read(file);
        BufferedImage cropped=cropImage(bufferedImage,r);
        outputfile=new File(cropFile);
        ImageIO.write(cropped, "png", outputfile);
        
        }catch(Exception e)
        {}
        return outputfile;
        
    }
    private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
      BufferedImage dest = src.getSubimage(50,80, rect.width, rect.height);
      return dest; 
   }
    public void getPlots(File source,String dpath) {
        try{
          File dir=new File(dpath+"/Layers");
          dir.mkdir();
    	PSDParser parser = new PSDParser(new FileInputStream(source.getPath()));
	PSDLayerAndMask layerAndMask = parser.getLayerAndMask();

	List<PSDLayerStructure> layers = layerAndMask.getLayers();
	List<PSDLayerPixelData> images = layerAndMask.getImageLayers();
        
	int i = 0;
        //DefaultComboBoxModel dm = (DefaultComboBoxModel)plots.getModel();
        //dm.addElement("--Select--");
	for (PSDLayerStructure layer : layers) {
	    PSDLayerPixelData pixelData = images.get(i);
	    BufferedImage image = pixelData.getImage();
            
	    if (image != null)
		ImageIO.write(image, "png", new File(dir.getPath()+"/layer"+(i+1) + ".png"));
            
            //dm.addElement(layer.getName());
	    i++;
	}
        }catch(Exception e)
        {
            
        }
}
    public boolean addUnit(String uname,String sname) throws SQLException
    {
        boolean flag=false;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("insert into unit(unit_name,short_name) values (?,?) ");
            pmt.setString(1,uname);
            pmt.setString(2, sname);
            pmt.executeUpdate();
            flag=true;
           
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return flag;
    }
    public boolean savePlotArea(String plotarea,String plotunit,String plotSurvey) throws SQLException
    {
        boolean flag=false;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("update plot set total_area=?,nonsold_area=?,unit_id=? where plot_survey_no=? ");
            pmt.setString(1,plotarea);
            pmt.setString(2,plotarea);   
            pmt.setInt(3,getUnitId(plotunit));
            pmt.setString(4,plotSurvey);
            pmt.executeUpdate();
            flag=true;
            //System.out.println("Layer info saved!!");
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return flag;
    }
    public boolean editPlotArea(String plotarea,String plotSurvey) throws SQLException
    {
        boolean flag=false;
        try{
            Double nonsold=0.00,total=0.00;
            ResultSet rs=getPlotDetails(plotSurvey);
            while(rs.next())
            {
                nonsold=rs.getDouble("nonsold_area");
                total=rs.getDouble("total_area");
            }
            total=total-Double.parseDouble(plotarea);
            nonsold=nonsold-total;
            if(nonsold>0)
            {
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("update plot set total_area=?,nonsold_area=? where plot_survey_no=? ");
            pmt.setString(1,plotarea);
            pmt.setDouble(2,nonsold);   
            //pmt.setInt(3,getUnitId(plotunit));
            pmt.setString(3,plotSurvey);
            pmt.executeUpdate();
            flag=true;
            }
            else
                flag=false;
            //System.out.println("Layer info saved!!");
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return flag;
    }
     public ResultSet getUnitList() throws SQLException
    {
        ResultSet rs=null;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select * from unit");
           
                      
            rs=pmt.executeQuery();
           
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return  rs;
    }
     public ResultSet getHissaList(int pid,int plot) throws SQLException
    {
        ResultSet rs=null;
        try{
             con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select * from plot_hissa_details where project_id=? and plot_id=?");
            pmt.setInt(1, pid);
            pmt.setInt(2, plot);
            rs=pmt.executeQuery();
            
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return  rs;
    }
     public ResultSet getHissaListBySurveyNo(String sno,String hs) throws SQLException
    {
        ResultSet rs=null;
        try{
             con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select * from plot_hissa_details where survey_no=? and hissa_survey not in (?)");
            pmt.setString(1, sno);
            pmt.setString(2, hs);
            rs=pmt.executeQuery();
            
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return  rs;
    }
     public int getHissaNo(String sno) throws SQLException
    {
        int i=0;
        ResultSet rs=null;
        try{
             con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select max(hissa_no) as 'Hissa_no' from plot_hissa_details where survey_no=?");
            pmt.setString(1, sno);
                      
            rs=pmt.executeQuery();
            while(rs.next())
            {
                i=rs.getInt("Hissa_no");
            }
            i++;
            
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return  i;
    }
     public int getHissaId(String sno) throws SQLException
    {
        int i=0;
        ResultSet rs=null;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select Id from plot_hissa_details where hissa_survey=?");
            pmt.setString(1, sno);
                      
            rs=pmt.executeQuery();
            while(rs.next())
            {
                i=rs.getInt("Id");
            }
            
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        return  i;
    }
     public int getUnitId(String uname) throws SQLException
    {
        ResultSet rs=null;
        int uid=0;   
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select * from unit where unit_name=?");
            pmt.setString(1, uname);
                
            rs=pmt.executeQuery();
            while (rs.next()) {
               uid=rs.getInt("Id");
                
            }
            
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return  uid;
    }
     public void deleteLastPath() throws SQLException
    {
        ResultSet rs=null;
        int uid=0;   
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select max(project_id) as 'pid' from plot");
            rs=pmt.executeQuery();
            while(rs.next())
            {
                uid=rs.getInt("pid");
            }
            pmt=con.prepareStatement("delete from plot where project_id=?  ");
            pmt.setInt(1, uid);
            pmt.executeUpdate();
            pmt=con.prepareStatement("delete from layer_info where project_id=?");
            pmt.setInt(1, uid);
            pmt.executeUpdate();
           
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
    }
     public int getPlotId(String psurvey) throws SQLException
    {
        ResultSet rs=null;
        int plotid=0;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select * from plot where plot_survey_no=? ");
            pmt.setString(1, psurvey);
            //pmt.setString(2, pno);
            rs=pmt.executeQuery();
            while(rs.next())
            {
                plotid=rs.getInt("Id");
            }
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return plotid;
    }
     public int getProjectId(String psurvey) throws SQLException
    {
        ResultSet rs=null;
        int plotid=0;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select * from plot where plot_survey_no=?");
            pmt.setString(1, psurvey);
            //pmt.setString(2, pno);
            rs=pmt.executeQuery();
            while(rs.next())
            {
                plotid=rs.getInt("project_id");
            }
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return plotid;
    }
     public ResultSet getTotalPlotArea(int pid) throws SQLException
    {
        ResultSet rs=null;
        //Double t=0.0;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select sum(total_area) as 'total',sum(sold_area) as 'Sold',"
                    + "sum(nonsold_area) as 'Nonsold' from plot where project_id=?");
            pmt.setInt(1, pid);
            
            rs=pmt.executeQuery();
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
     public ResultSet getProjectDetails(int pid) throws SQLException
    {
        ResultSet rs=null;
        int plotid=0;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select total_area,unit_id,survey_no,unit_name,short_name from project p "
                    + "inner join unit u on p.unit_id=u.Id where p.Id=? ");
            pmt.setInt(1, pid);
           
            rs=pmt.executeQuery();
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
     public ResultSet getProjectInfo(int pid) throws SQLException
    {
        ResultSet rs=null;
        int plotid=0;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select project_name,project_logo,city,district,location,total_area,ownership,map,survey_no,unit_name from "
                    + "project p,unit u where p.Id=? and p.unit_id=u.Id");
            pmt.setInt(1, pid);
           
            rs=pmt.executeQuery();
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
      public boolean addHissa(HissaBean hbean) throws SQLException
    {
        boolean flag=false;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("insert into plot_hissa_details(project_id,plot_id,survey_no,hissa_no,total_area,area_purchased,title_owner,hissa_survey,unit_id) values (?,?,?,?,?,?,?,?,?) ");
            pmt.setInt(1,hbean.getProject_id());
            pmt.setInt(2,hbean.getPlot_id());
            pmt.setString(3, hbean.getSurvey_no());
            pmt.setString(4, hbean.getHissa_no());
            pmt.setString(5, hbean.getTotal_area());
            pmt.setString(6, hbean.getArea_purchased());
            pmt.setString(7, hbean.getTitle_owner());
            pmt.setString(8, hbean.getHissa_survey());
            pmt.setInt(9, hbean.getUnit_id());
            pmt.executeUpdate();
            pmt=con.prepareStatement("insert into owner(hissa_id,hissa_survey,owner_name) values (?,?,?) ");
            pmt.setInt(1,getHissaId(hbean.getHissa_survey()));
            pmt.setString(2, hbean.getHissa_survey());
            pmt.setString(3, hbean.getTitle_owner());
            pmt.executeUpdate();
            
            pmt=con.prepareStatement("insert into history(hissa_id,hissa_survey,owner_id,child_id,isTitleOwner,isLast) values (?,?,?,?,?,?) ");
            int oid=getMaxOwnerId();
            oid--;
            pmt.setInt(1,getHissaId(hbean.getHissa_survey()));
            pmt.setString(2, hbean.getHissa_survey());
            pmt.setInt(3, oid);
            pmt.setInt(4, oid);
            pmt.setInt(5, 1);
            pmt.setInt(6, 0);
            pmt.executeUpdate();
            flag=true;
            updateArea(hbean);
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return flag;
    }
      public boolean editHissa(HissaBean hbean) throws SQLException
    {
        boolean flag=false;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("update plot_hissa_details set hissa_no=?,area_purchased=?,title_owner=?,hissa_survey=?,unit_id=? where hi");
            pmt.setInt(1,hbean.getProject_id());
            pmt.setInt(2,hbean.getPlot_id());
            pmt.setString(3, hbean.getSurvey_no());
            pmt.setString(4, hbean.getHissa_no());
            pmt.setString(5, hbean.getTotal_area());
            pmt.setString(6, hbean.getArea_purchased());
            pmt.setString(7, hbean.getTitle_owner());
            pmt.setString(8, hbean.getHissa_survey());
            pmt.setInt(9, hbean.getUnit_id());
            pmt.executeUpdate();
            pmt=con.prepareStatement("insert into owner(hissa_id,hissa_survey,owner_name) values (?,?,?) ");
            pmt.setInt(1,getHissaId(hbean.getHissa_survey()));
            pmt.setString(2, hbean.getHissa_survey());
            pmt.setString(3, hbean.getTitle_owner());
            pmt.executeUpdate();
            
            
            updateArea(hbean);
            //System.out.println("Layer info saved!!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return flag;
    }
      public boolean updateArea(HissaBean hbean) throws SQLException
    {
        boolean flag=false;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("update plot set sold_area=sold_area+? where plot_survey_no=? ");
            pmt.setString(1,hbean.getArea_purchased());
            //pmt.setString(2,hbean.getArea_purchased());
            pmt.setString(2, hbean.getSurvey_no());
            pmt.executeUpdate();
            pmt=con.prepareStatement("update plot set nonsold_area=total_area-sold_area where plot_survey_no=? ");
            //pmt.setString(1,hbean.getArea_purchased());
            //pmt.setString(2,hbean.getArea_purchased());
            pmt.setString(1, hbean.getSurvey_no());
            pmt.executeUpdate();
            flag=true;
            //System.out.println("Layer info saved!!");
           
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return flag;
    }
      public ResultSet getOwner(String hs) throws SQLException
    {
        ResultSet rs=null;
        System.out.println("Hissa id in fun="+hs);
        //Double t=0.0;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select * from owner where hissa_id=?");
            pmt.setString(1, hs);
            
            rs=pmt.executeQuery();
           
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
      public ResultSet getParentList(String hs) throws SQLException
    {
        ResultSet rs=null;
        //System.out.println("Hissa id in fun="+hs);
        //Double t=0.0;
        try{
            con=MyConnection.createConnection();
            PreparedStatement pmt=con.prepareStatement("select * from same_history where hissa_id=?");
            pmt.setString(1, hs);
            
            rs=pmt.executeQuery();
          
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rs;
    }
     
      public void uploadTemp(String table,String filePath,boolean flag,int hissa_id,int owner_id) throws SQLException
      {
          int len;
          try {
              /*if(!flag)
              {
                  emptyTemp(table);
              }*/
              //int oid=0;
              //oid=getOwnerId(String.valueOf(hissa_id));
              con=MyConnection.createConnection();
              File file = new File(filePath);
              FileInputStream fis = new FileInputStream(file);
              len = (int)file.length();
              String query = ("insert into "+table+" (doc,owner_id,path) VALUES(?,?,?)");
              PreparedStatement pstmt = con.prepareStatement(query);
              //pstmt.setInt(1, getMaxDocId(table)); 
              pstmt.setBinaryStream(1, fis, len); 
              pstmt.setInt(2,owner_id);
              pstmt.setString(3, file.getPath());
              pstmt.executeUpdate();
             
        } catch (Exception e) {
            e.printStackTrace();
        }
          
      }
      public int getOwnerId(String owner_name)
      {
          int owner_id=0;
          try{
          ResultSet rs=getOwner(owner_name);
              
              while(rs.next())
              {
                  owner_id=rs.getInt("Id");
              }
             
          }catch(Exception e)
          {
              
          }
          return owner_id;
      }
      public int getMaxDocId(String table) throws SQLException
      {
          int id=0;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("select max(Id) as 'Id' from "+table);
              ResultSet rs=pmt.executeQuery();
              
              while(rs.next())
              {
                  id=rs.getInt("Id");
              }
              id++;
              
          }catch(Exception e)
          {
              
          }
         
          return id;
      }
      public int getMaxOwnerId() throws SQLException
      {
          int id=0;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("select max(Id) as 'Id' from owner");
              ResultSet rs=pmt.executeQuery();
              
              while(rs.next())
              {
                  id=rs.getInt("Id");
              }
              id++;
              
          }catch(Exception e)
          {
              
          }
          
          return id;
      }
      public int getOwnerIdByHissa(int hid) throws SQLException
      {
          int id=0;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("select max(Id) as 'Id' from owner where hissa_id=?");
              pmt.setInt(1,hid);
              ResultSet rs=pmt.executeQuery();
              
              while(rs.next())
              {
                  id=rs.getInt("Id");
              }
             
          }catch(Exception e)
          {
              
          }
          
          return id;
      }
      public ResultSet getOwnerListByHissa(int hid) throws SQLException
      {
          //int id=0;
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("select * from owner where hissa_id=?");
              pmt.setInt(1,hid);
              rs=pmt.executeQuery();
              
            
              
          }catch(Exception e)
          {
              
          }
          
          return rs;
      }
      public ResultSet getHistoryListByHissa(int hid) throws SQLException
      {
          //int id=0;
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("select * from same_history where hissa_id=?");
              pmt.setInt(1,hid);
              rs=pmt.executeQuery();
              
              
          }catch(Exception e)
          {
              
          }
          
          return rs;
      }
      public int getOwnerIdByName(String owner) throws SQLException
      {
          int id=0;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("select Id from owner where owner_name=?");
              pmt.setString(1,owner);
              ResultSet rs=pmt.executeQuery();
              
              while(rs.next())
              {
                  id=rs.getInt("Id");
              }
              
          }catch(Exception e)
          {
              
          }
          
          return id;
      }
      public int getDocId(int owner_id,String table) throws SQLException
      {
          int id=0;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("select doc_id as 'Id' from "+table+" where owner_id=?");
              pmt.setInt(1, owner_id);
              ResultSet rs=pmt.executeQuery();
              
              if(rs.next())
              {
                  id=rs.getInt("Id");
              }
              
              //id++;
          }catch(Exception e)
          {
              e.printStackTrace();
          }
          
          return id;
      }
      
      public void emptyTemp(String table) throws SQLException
      {
          try {
              con=MyConnection.createConnection();
              String query = ("delete from "+table);
              PreparedStatement pstmt = con.prepareStatement(query);
              pstmt.executeUpdate();
             
        } catch (Exception e) {
            e.printStackTrace();
        }
          
      }
      public InputStream getPDFData(String table) throws SQLException {
         
        byte[] fileBytes;
        InputStream ins=null;
        String query;
        try {
            con=MyConnection.createConnection();
            query = 
              "select doc from "+table;
            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery(query);
            while (rs.next()) {
                //fileBytes = rs.getBytes(1);
                
                 ins=rs.getBinaryStream("document");
                
                //targetFile.write(fileBytes);
                //targetFile.close();
            }
            //pmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ins;
    }
      public void saveDocument(String stable,String dtable) throws SQLException
      {
          try
          {
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("insert into "+dtable+" (document,owner_id,doc_id,path) "
                      + " select doc,owner_id,Id,path from "+stable);
              pmt.executeUpdate();
              emptyTemp(stable);
              
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          
      }
      public boolean saveOwnerDetails(OwnerBean obean) throws SQLException
      {
          boolean flag=false;
          String mtable="mutation";
          String stable="sale_deed";
          saveDocument("mutation_temp", mtable);
          saveDocument("saledeed_temp", stable);
          try{
              //int owner_id=getOwnerIdByName(obean.getOwner_name());
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("update owner set from_date=?,to_date=?,mutation_id=?,saledeed_id=?,"
                      + "mutation_reg_no=?,saledeed_reg_no=?,owner_name=? where hissa_id=? and Id=?");
              pmt.setDate(1, obean.getFrom_date());
              pmt.setDate(2, obean.getTo_date());
              pmt.setInt(3, getDocId(obean.getOwner_id(),mtable));
              pmt.setInt(4, getDocId(obean.getOwner_id(),stable));
              pmt.setString(5, obean.getMutation_no());
              pmt.setString(6, obean.getSaledeed_no());
              pmt.setString(7, obean.getOwner_name());
              pmt.setInt(8, obean.getHissa_id());
              pmt.setInt(9, obean.getOwner_id());
              pmt.executeUpdate();
              insertHistory(obean);
              flag=true;
              
          }
          catch(Exception e)
          {
              
          }
          
          return flag;
      }
      public void insertHistory(OwnerBean obean) throws SQLException
      {
          try{
              /*int owner_id=getOwnerIdByName(obean.getOwner_name());
              int pid=getOwnerIdByName(obean.getPurchaser_name());
              int islast=0,isOwner=0;
              if(owner_id==pid)
              {
                  islast=1;
                  
              }
              else{
                  islast=0;
              }
              */
              con=MyConnection.createConnection();
              
              PreparedStatement pmt=con.prepareStatement("insert into history (hissa_id,hissa_survey,owner_id,child_id,isLast) "
                      + " select hissa_id,hissa_survey,owner_id,child_id,isLast from temp_history");
              //pmt.setInt(1, obean.getOwner_id());
              pmt.executeUpdate();
              emptyHistory();
              
          }
          catch(Exception e)
          {
              e.printStackTrace();
              emptyHistory();
          }
          
      }
      public void emptyHistory() throws SQLException
        {
            try{
                 con=MyConnection.createConnection();
                 PreparedStatement pmt=con.prepareStatement("delete from temp_history");
                 pmt.executeUpdate();
            }catch(Exception e)
            {
                
            }
            
        }
      public boolean saveOwner(OwnerBean obean) throws SQLException
      {
          boolean flag=false;
          String mtable="mutation";
          String stable="sale_deed";
          saveDocument("mutation_temp", mtable);
          saveDocument("saledeed_temp", stable);
          try{
              con=MyConnection.createConnection();
              PreparedStatement pmt=con.prepareStatement("insert into owner (hissa_id,hissa_survey,owner_name,from_date,to_date,mutation_id,saledeed_id,mutation_reg_no,saledeed_reg_no) "
                      + " values(?,?,?,?,?,?,?,?,?)");
              pmt.setInt(1, obean.getHissa_id());
              pmt.setString(2, obean.getHissa_survey());
              pmt.setString(3, obean.getOwner_name());
              
              pmt.setDate(4, obean.getFrom_date());
              pmt.setDate(5, obean.getTo_date());
              pmt.setInt(6, getDocId(getMaxOwnerId(),mtable));
              pmt.setInt(7, getDocId(getMaxOwnerId(),stable));
              pmt.setString(8, obean.getMutation_no());
              pmt.setString(9, obean.getSaledeed_no());
              //pmt.setInt(7, obean.getHissa_id());
              pmt.executeUpdate();
              insertHistory(obean);
              flag=true;
              
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          
          return flag;
      }
      public boolean deleteDocument(String table,String oid,String did) throws SQLException
      {
          boolean flag=false;
          try{
          con=MyConnection.createConnection();
          PreparedStatement pst=con.prepareStatement("delete from "+table+" where owner_id=? and doc_id=?");
          pst.setString(1, oid);
          pst.setString(2, did);
          pst.executeUpdate();
          flag=true;
         
            
          }catch(Exception e)
          {
             e.printStackTrace();
          }
          
          return flag;
      }
      public boolean deleteDocument(String table,String did) throws SQLException
      {
          boolean flag=false;
          try{
          con=MyConnection.createConnection();
          PreparedStatement pst=con.prepareStatement("delete from "+table+" where Id=?");
          //pst.setString(1, oid);
          pst.setString(1, did);
          pst.executeUpdate();
          flag=true;
         
          }catch(Exception e)
          {
              e.printStackTrace();
          }
          
          return flag;
      }
      public boolean isTempEmpty(String table,int oid) throws SQLException
      {
          boolean flag=true;
          try{
          con=MyConnection.createConnection();
          PreparedStatement pst=con.prepareStatement("select * from "+table+" where owner_id=?");
          //pst.setString(1, oid);
          pst.setInt(1, oid);
          ResultSet rs=pst.executeQuery();
          if(rs.next())
          {
            flag=false;  
          }
          else{
            flag=true;
          }
         
          }catch(Exception e)
          {
              e.printStackTrace();
          }
          
          return flag;
      }
      public String getParentNameById(int oid) throws SQLException
      {
          String parent="";
          try{
          con=MyConnection.createConnection();
          PreparedStatement pst=con.prepareStatement("select o.owner_name as 'Parent' from history h,owner o where o.Id=h.child_id and h.owner_id=?");
          pst.setInt(1, oid);
          //pst.setString(1, did);
          ResultSet rs=pst.executeQuery();
          while(rs.next())
          {
              parent=rs.getString("Parent");
          }
          
          }catch(Exception e)
          {
              e.printStackTrace();
          }
          
          return parent;
      }
      public int getMaxProjectId() 
{
    Connection con=null;
    int pid=0;
    //boolean flag=false;
      
                  try{  
		  con = MyConnection.createConnection();
		  PreparedStatement pre =
		  con.prepareStatement("select max(Id) as 'Id' from project");
                  ResultSet rs=pre.executeQuery();
                  
		  if(rs.next())
                  {
                      pid=rs.getInt("Id");
                  }
                  pid++;
                  //project_id1.setText(""+pid);
                  //System.out.println("pid-"+pid);
		  pre.close();
		  con.close(); 
                  }catch(Exception e){}
      return pid;

    
    }
      public void saveSameHistory(int oid,OwnerBean obean,int hid,String hs) throws SQLException
      {
          boolean flag=true;
          try{
              
              //int oid=getOwnerIdByName(obean.getOwner_name());
          con=MyConnection.createConnection();
          PreparedStatement pst=con.prepareStatement("select owner_id from owner o,history h where o.Id=h.child_id and h.child_id=?");
          pst.setInt(1, oid);
          //pst.setString(1, did);
          ResultSet rs=pst.executeQuery();
          List<Integer> owner_id=new ArrayList<Integer>();
          List<String> hissa_id=new ArrayList<String>();
          List<String> hissa_survey=new ArrayList<String>();
          List<String> owner_name=new ArrayList<String>();
          List<String> from=new ArrayList<String>();
          List<String> to=new ArrayList<String>();
          List<String> mid=new ArrayList<String>();
          List<String> sid=new ArrayList<String>();
          List<String> mno=new ArrayList<String>();
          List<String> sno=new ArrayList<String>();
          int j=0;
          while(rs.next())
          {
              j++;
              
              owner_id.add(rs.getInt("owner_id"));
              
          }
          
               for(int i=0;i<owner_id.size();i++){
              pst=con.prepareStatement("select Id,hissa_id,hissa_survey,owner_name,from_date,to_date,mutation_id,saledeed_id,mutation_reg_no,saledeed_reg_no from owner where Id=?");
              pst.setInt(1, owner_id.get(i));
              rs=pst.executeQuery();
              while(rs.next())
              {
                  
                  //hissa_id.add(rs.getString("hissa_id"));
                  //hissa_survey.add(rs.getString("hissa_survey"));
                   owner_name.add(rs.getString("owner_name"));
                   from.add(rs.getString("from_date"));
                   to.add(rs.getString("to_date"));
                   mid.add(rs.getString("mutation_id"));
                   sid.add(rs.getString("saledeed_id"));
                   mno.add(rs.getString("mutation_reg_no"));
                   sno.add(rs.getString("saledeed_reg_no"));
              }
              
          }
              
          for(int i=0;i<j;i++)
          {
              
             PreparedStatement psmt=con.prepareStatement("insert into same_history(hissa_id,hissa_survey,owner_name,from_date,to_date,mutation_id,saledeed_id,mutation_reg_no,saledeed_reg_no,owner_id) "
                     + " values (?,?,?,?,?,?,?,?,?,?)");
             psmt.setInt(1, hid);
             psmt.setString(2, hs);
             psmt.setString(3, owner_name.get(i));
             psmt.setString(4, from.get(i));
             psmt.setString(5, to.get(i));
             psmt.setString(6, mid.get(i));
             psmt.setString(7, sid.get(i));
             psmt.setString(8, mno.get(i));
             psmt.setString(9, sno.get(i));
             psmt.setInt(10,owner_id.get(i));
             psmt.executeUpdate();
             
             
          }
          
             int k=0;
             try{
             oid=owner_id.get(k);
                 saveSameHistory(oid, obean, hid, hs);
             flag=true;
             }catch(Exception e){
                 e.printStackTrace();
             flag=false;}
             k++;
              //}
              PreparedStatement psmt=con.prepareStatement("insert into history (hissa_id,hissa_survey,owner_id,child_id,isTitleOwner,isLast) values "
                     + " (?,?,?,?,?,?)");
             psmt.setInt(1, hid);
             psmt.setString(2,hs);
             psmt.setInt(4,getOwnerIdByHissa(hid));
             psmt.setInt(3,getOwnerIdByName(obean.getOwner_name()));
             psmt.setInt(5,0);
             psmt.setInt(6,0);
             psmt.executeUpdate();
             
          }catch(Exception e)
          {
              e.printStackTrace();
          }
          
      }
      public void saveFromOwner(int oid,OwnerBean obean,int hid,String hs) throws SQLException
      {
          try{
              List<Integer> owner_id=new ArrayList<Integer>();
              List<String> hissa_id=new ArrayList<String>();
              List<String> hissa_survey=new ArrayList<String>();
              List<String> owner_name=new ArrayList<String>();
              List<String> from=new ArrayList<String>();
              List<String> to=new ArrayList<String>();
              List<String> mid=new ArrayList<String>();
              List<String> sid=new ArrayList<String>();
              List<String> mno=new ArrayList<String>();
              List<String> sno=new ArrayList<String>();
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select Id,hissa_id,hissa_survey,owner_name,from_date,to_date,mutation_id,saledeed_id,mutation_reg_no,saledeed_reg_no from owner where Id=?");
              pst.setInt(1, oid);
              ResultSet rs=pst.executeQuery();
              int j=0;
              while(rs.next())
              {
                  j++;
                  System.out.println("in save from owner:"+rs.getString("owner_name"));
                  //hissa_id.add(rs.getString("hissa_id"));
                  //hissa_survey.add(rs.getString("hissa_survey"));
                   owner_name.add(rs.getString("owner_name"));
                   from.add(rs.getString("from_date"));
                   to.add(rs.getString("to_date"));
                   mid.add(rs.getString("mutation_id"));
                   sid.add(rs.getString("saledeed_id"));
                   mno.add(rs.getString("mutation_reg_no"));
                   sno.add(rs.getString("saledeed_reg_no"));
              
              }
          
              
          for(int i=0;i<j;i++)
          {
              
             PreparedStatement psmt=con.prepareStatement("insert into same_history(hissa_id,hissa_survey,owner_name,from_date,to_date,mutation_id,saledeed_id,mutation_reg_no,saledeed_reg_no,owner_id) "
                     + " values (?,?,?,?,?,?,?,?,?,?)");
             psmt.setInt(1, hid);
             psmt.setString(2, hs);
             psmt.setString(3, owner_name.get(i));
             psmt.setString(4, from.get(i));
             psmt.setString(5, to.get(i));
             psmt.setString(6, mid.get(i));
             psmt.setString(7, sid.get(i));
             psmt.setString(8, mno.get(i));
             psmt.setString(9, sno.get(i));
             psmt.setInt(10,oid);
             psmt.executeUpdate();
             
          } 
             //psmt.close();
             
          }catch(Exception e)
          {
              
          }
          
      }
      public ResultSet getTitleOwner(int hid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select o.Id,h.hissa_id,o.hissa_survey,owner_name,from_date,to_date,mutation_id,saledeed_id,mutation_reg_no,saledeed_reg_no " +
                   " from  history h,owner o where  h.isTitleOwner=1 and h.owner_id=o.Id and h.hissa_id=?");
              pst.setInt(1, hid);
              rs=pst.executeQuery();
              
          }catch(Exception e)
          {
              
          }
          
         return rs; 
      }
      public ResultSet getOwnerById(int oid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select owner_id from owner o,history h where o.Id=h.child_id and h.child_id=? and isTitleOwner=0");
              pst.setInt(1, oid);
              rs=pst.executeQuery();
              
          }catch(Exception e)
          {
              
          }
         
         return rs; 
      }
      public ResultSet getOwnerInformation(int oid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select hissa_id,hissa_survey,owner_name,from_date,to_date,mutation_id,saledeed_id,mutation_reg_no,saledeed_reg_no from owner where Id=?");
              pst.setInt(1, oid);
              rs=pst.executeQuery();
            
          }catch(Exception e)
          {
              
          }
         
         return rs; 
      }
      public ResultSet getDocumentPath(String table,int oid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select * from "+table+" where owner_id=?");
              pst.setInt(1, oid);
               rs=pst.executeQuery();
               
            
          }catch(Exception e)
          {
              
          }
          
          return rs;
      }
      public ResultSet getSearchHistory(String stext,String hid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select * from owner where "
                      + "(owner_name like '"+stext+"%' or year(from_date) =? or year(to_date) =? "
                      +" or mutation_reg_no like '"+stext+"%' or saledeed_reg_no like '"+stext+"%') and hissa_id=?");
               pst.setString(1,stext);
               pst.setString(2, stext);
               pst.setString(3, hid);
               rs=pst.executeQuery();
               
          }catch(Exception e)
          {
             e.printStackTrace();
          }
         
          return rs;
      }
      public ResultSet getPlotIdByNo(String pid,String plotno) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select * from plot where project_id=? and plot=?");
               pst.setString(1,pid);
               pst.setString(2, plotno);
              
               rs=pst.executeQuery();
              
          }catch(Exception e)
          {
             e.printStackTrace();
          }
         
          return rs;
      }
      public ResultSet getProjectIdByName(String pname) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select * from project where project_name=?");
               pst.setString(1,pname);
               
               rs=pst.executeQuery();
              
          }catch(Exception e)
          {
             e.printStackTrace();
          }
          
          return rs;
      }
      public ResultSet getHissaIdByNo(String pid,String plotid,String hno) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select * from plot_hissa_details where project_id=? and plot_id=? and hissa_no=?");
               pst.setString(1,pid);
               pst.setString(2,plotid);
               pst.setString(3,hno);
               rs=pst.executeQuery();
              
          }catch(Exception e)
          {
             e.printStackTrace();
          }
          
          return rs;
      }
      public ResultSet getOwnerNames(int hid,String oid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select o.owner_name from history h,owner o where h.child_id=o.Id and "
                      + " h.hissa_id=? and h.owner_id=? ");
               pst.setInt(1,hid);
               ///System.out.println("hid= "+hid+"oid= "+oid);
               pst.setString(2,oid);
               
               rs=pst.executeQuery();
              
          }catch(Exception e)
          {
             e.printStackTrace();
          }
          
          return rs;
          
          
      }
      public ResultSet getPlot(String hs,String hid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select * from plot_hissa_details where Id=? and hissa_survey=?");
               pst.setString(1,hid);
               pst.setString(2,hs);
               
               rs=pst.executeQuery();
              
          }catch(Exception e)
          {
             e.printStackTrace();
          }
          
          return rs;
          
          
      }
      public boolean savePurchaser(HistoryBean hsbean,String[] list) throws SQLException
      {
         boolean flag=false;
          try{
              con=MyConnection.createConnection();
              for(int i=0;i<list.length;i++)
              {
              PreparedStatement pst=con.prepareStatement("insert into temp_history(hissa_id,hissa_survey,owner_id,child_id,isTitleOwner,isLast) values"
                      + "(?,?,?,?,?,?)");
               pst.setInt(1,hsbean.getHissa_id());
               pst.setString(2,hsbean.getHissa_survey());
               pst.setString(3,hsbean.getOwner_id());
               pst.setString(4,list[i]);
               pst.setInt(5, 0);
               pst.setInt(6,0);
               pst.executeUpdate();
               flag=true;
              }
              //pst.close();
            
          }catch(Exception e)
          {
             e.printStackTrace();
          }
         
          return flag; 
      }
      public ResultSet getHissaSurveyById(int hid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select * from plot_hissa_details where Id=?");
               pst.setInt(1,hid);
               //pst.setString(2,oid);
               
               rs=pst.executeQuery();
              
          }catch(Exception e)
          {
             e.printStackTrace();
          }
          
          return rs;
          
          
      }
      public ResultSet getChild(String oid) throws SQLException
      {
          ResultSet rs=null;
          try{
              con=MyConnection.createConnection();
              PreparedStatement pst=con.prepareStatement("select * from history where child_id=? and isTitleOwner=0");
               pst.setString(1,oid);
               //pst.setString(2,oid);
               
               rs=pst.executeQuery();
              
          }catch(Exception e)
          {
             e.printStackTrace();
          }
          
          return rs;
          
          
      }
      public boolean deleteOwner(String oid) throws SQLException
      {
          boolean flag=false;
          try{
          con=MyConnection.createConnection();
          PreparedStatement pst=con.prepareStatement("delete from history where owner_id=?");
          pst.setString(1, oid);
          pst.executeUpdate();
          pst=con.prepareStatement("delete from owner where Id=?");
          pst.setString(1, oid);
          pst.executeUpdate();
          pst=con.prepareStatement("delete from mutation where owner_id=?");
          pst.setString(1, oid);
          pst.executeUpdate();
          pst=con.prepareStatement("delete from sale_deed where owner_id=?");
          pst.setString(1, oid);
          pst.executeUpdate();
          pst=con.prepareStatement("delete from mutation_temp where owner_id=?");
          pst.setString(1, oid);
          pst.executeUpdate();
          pst=con.prepareStatement("delete from saledeed_temp where owner_id=?");
          pst.setString(1, oid);
          pst.executeUpdate();
          pst=con.prepareStatement("delete from same_history where owner_id=?");
          pst.setString(1, oid);
          pst.executeUpdate();
          
          flag=true;
         
            
          }catch(Exception e)
          {
             e.printStackTrace();
          }
          
          return flag;
      }
      public boolean updateProjectName(String field,String name,String pid)
      {
          boolean flag=false;
         try{
          con=MyConnection.createConnection();
             //System.out.println("field="+field+" name="+name);
          PreparedStatement pst=con.prepareStatement("update project set "+field+" =? where Id=?");
         // pst.setString(1, field);
          pst.setString(1, name);
          pst.setString(2, pid);
          pst.executeUpdate();
          flag=true;
          
          }catch(Exception e)
          {
             e.printStackTrace();
          } 
         return flag;
      }
      public boolean updateLogo(String field,String path,String pid)
      {
          boolean flag=false;
         try{
             File imgfile = new File(path);
             FileInputStream fin = new FileInputStream(imgfile); 
             con=MyConnection.createConnection();
             //System.out.println("field="+field+" name="+name);
          PreparedStatement pst=con.prepareStatement("update project set "+field+" =? where Id=?");
         // pst.setString(1, field);
          pst.setBinaryStream(1, (InputStream)fin, imgfile.length());
          pst.setString(2, pid);
          pst.executeUpdate();
          flag=true;
          
          }catch(Exception e)
          {
             e.printStackTrace();
          } 
         return flag;
      }
      public boolean updateOwner(String field,String name,String pid)
      {
          boolean flag=false;
         try{
             
             String owner="";
             ResultSet rs=getProjectInfo(Integer.parseInt(pid));
              while(rs.next())
              {
                  owner=rs.getString("ownership");
              }
              name=owner+","+name;
          con=MyConnection.createConnection();
             //System.out.println("field="+field+" name="+name);
          PreparedStatement pst=con.prepareStatement("update project set ownership=? where Id=?");
         // pst.setString(1, field);
          pst.setString(1, name);
          pst.setString(2, pid);
          pst.executeUpdate();
          flag=true;
          
          }catch(Exception e)
          {
             e.printStackTrace();
          } 
         return flag;
      }
      public boolean deleteOwner(String field,String name,String pid)
      {
          boolean flag=false;
         try{
             name=name.concat(",");
             String owner="";
             ResultSet rs=getProjectInfo(Integer.parseInt(pid));
              while(rs.next())
              {
                  owner=rs.getString("ownership");
              }
              owner=owner.replace(name,"");
          con=MyConnection.createConnection();
             //System.out.println("field="+field+" name="+name);
          PreparedStatement pst=con.prepareStatement("update project set ownership=? where Id=?");
         // pst.setString(1, field);
          pst.setString(1, owner);
          pst.setString(2, pid);
          pst.executeUpdate();
          flag=true;
          
          }catch(Exception e)
          {
             e.printStackTrace();
          } 
         return flag;
      }
}
