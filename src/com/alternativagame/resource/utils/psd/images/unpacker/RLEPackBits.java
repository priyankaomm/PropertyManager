/**
 * 
 */
package com.alternativagame.resource.utils.psd.images.unpacker;


public class RLEPackBits {

    private final byte[] data;

    private final int size;

    public RLEPackBits(byte[] data, int size) {
	this.data = data;
	this.size = size;
    }

    public byte[] unpack() {
	byte[] result = new byte[size];

	
	int writePos = 0;
	
	int readPos = 0;
	
	while (readPos < data.length) {
	    
	    int n = data[readPos++];

	    if (n > 0) {
		
		int count = n + 1;
		for (int j = 0; j < count; j++)
		    result[writePos++] = data[readPos++];
	    } else {
		
		byte b = data[readPos++];
		int count = -n + 1;
		for (int j = 0; j < count; j++)
		    result[writePos++] = b;
	    }
	}

	return result;
    }

}
