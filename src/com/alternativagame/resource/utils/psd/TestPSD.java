
package com.alternativagame.resource.utils.psd;

//import com.alternativagame.resource.utils.psd.PSDParser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.alternativagame.resource.utils.psd.layer.PSDLayerPixelData;
import com.alternativagame.resource.utils.psd.layer.PSDLayerStructure;
import com.alternativagame.resource.utils.psd.section.PSDLayerAndMask;


public class TestPSD {

    public static void main(String[] args) throws IOException {

	PSDParser parser = new PSDParser(new FileInputStream("SunshineLand.psd"));
	PSDLayerAndMask layerAndMask = parser.getLayerAndMask();

	List<PSDLayerStructure> layers = layerAndMask.getLayers();
	List<PSDLayerPixelData> images = layerAndMask.getImageLayers();
	int i = 0;
	for (PSDLayerStructure layer : layers) {
	    PSDLayerPixelData pixelData = images.get(i);
	    BufferedImage image = pixelData.getImage();
	    if (image != null)
		ImageIO.write(image, "png", new File(layer.getName() + ".png"));
            

	    i++;
	}

    }
}
