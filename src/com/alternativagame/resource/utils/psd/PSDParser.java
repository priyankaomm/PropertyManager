
package com.alternativagame.resource.utils.psd;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.alternativagame.resource.utils.psd.section.PSDColorMode;
import com.alternativagame.resource.utils.psd.section.PSDHeader;
import com.alternativagame.resource.utils.psd.section.PSDImageResources;
import com.alternativagame.resource.utils.psd.section.PSDLayerAndMask;

public class PSDParser {

    private final PSDHeader header;

    private final PSDColorMode colorMode;

    private final PSDImageResources imageResources;

    private final PSDLayerAndMask layerAndMask;

    public PSDParser(InputStream input) throws IOException {
	DataInputStream in = new DataInputStream(input);
	// file header
	header = new PSDHeader(in);
	// color mode data
	colorMode = new PSDColorMode(in);
	// image resources
	imageResources = new PSDImageResources(in);
	// layer and mask
	layerAndMask = new PSDLayerAndMask(in);
	// image
    }

    
    public PSDHeader getHeader() {
	// TODO Auto-generated method stub
	return header;
    }

    public PSDColorMode getColorMode() {
	return colorMode;
    }

    public PSDImageResources getImageResources() {
	return imageResources;
    }

    public PSDLayerAndMask getLayerAndMask() {
	return layerAndMask;
    }

}
