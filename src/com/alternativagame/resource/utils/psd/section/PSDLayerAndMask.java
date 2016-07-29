/**
 * 
 */
package com.alternativagame.resource.utils.psd.section;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alternativagame.resource.utils.psd.layer.PSDLayerPixelData;
import com.alternativagame.resource.utils.psd.layer.PSDLayerStructure;


public class PSDLayerAndMask {

    
    private final List<PSDLayerStructure> layers = new ArrayList<PSDLayerStructure>();

   
    private final List<PSDLayerPixelData> imageLayers = new ArrayList<PSDLayerPixelData>();

    public PSDLayerAndMask(DataInputStream in) throws IOException {
	int size = in.readInt();
	parseLayerInfo(in);
	parseLayerMask(in);
	in.skipBytes(size);
    }

    /**
     * @param in
     */
    private void parseLayerMask(DataInputStream in) {

    }

    private void parseLayerInfo(DataInputStream in) throws IOException {
	
	int size = in.readInt();

	int countLayer = Math.abs(in.readShort());
	// Layer Structure
	for (int i = 0; i < countLayer; i++) {
	    PSDLayerStructure layer = new PSDLayerStructure(in);
	    this.layers.add(layer);

	}
	// Layer Pixel Data
	for (int i = 0; i < countLayer; i++) {
	    PSDLayerPixelData pixels = new PSDLayerPixelData(this.layers.get(i), in);
	    this.imageLayers.add(pixels);
	}

    }

   
    public List<PSDLayerStructure> getLayers() {
	return layers;
    }

    
    public List<PSDLayerPixelData> getImageLayers() {
	return imageLayers;
    }

}
