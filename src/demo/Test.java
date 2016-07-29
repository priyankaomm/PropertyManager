/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Test {

    private JFrame frame;
    private JLabel[] labels;
    private static String imagePath = "D:/search.png";
    private final int rows = 3; //You should decide the values for rows and cols variables
    private final int cols = 3;
    private final int chunks = rows * cols;
    private final int SPACING = 10;//spacing between split images

    

    public void createAndShowUI(String image) {
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents(image);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(200, 200);
        
    }

    private void initComponents(String image) {

        BufferedImage[] imgs = getImages(image);

        //set contentpane layout for grid
        frame.getContentPane().setLayout(new GridLayout(rows, cols, SPACING, SPACING));

        labels = new JLabel[imgs.length];

        //create JLabels with split images and add to frame contentPane
        for (int i = 0; i < imgs.length; i++) {
            labels[i] = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(imgs[i].getSource())));
            frame.getContentPane().add(labels[i]);
        }
    }

    private BufferedImage[] getImages(String image) {
        File file = new File(image); // I have bear.jpg in my working directory
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage image1 = null;
        try {
            image1 = ImageIO.read(fis); //reading the image file
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        int chunkWidth = image1.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = image1.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image1.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image1, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    }
}