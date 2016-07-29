
package com.alternativagame.resource.utils.psd.images;

import java.io.DataInputStream;
import java.io.IOException;


public class RAWParser {

    byte[] result = null;

    
    public RAWParser(int width, int heigth, DataInputStream in) throws IOException {
	result = new byte[width * heigth];
	in.read(result);
    }

    
    public byte[] getData() {
	return result;
    }
}
