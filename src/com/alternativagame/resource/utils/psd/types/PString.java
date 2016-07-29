/**
 * 
 */
package com.alternativagame.resource.utils.psd.types;

import java.io.DataInputStream;
import java.io.IOException;


public class PString {

    private final String value;

    /**
     * @param in
     * @throws IOException
     */
    public PString(DataInputStream in) throws IOException {
	byte length = in.readByte();
	byte[] data = new byte[length];
	in.read(data);
	value = new String(data);
    }

    public String getValue() {
	return value;
    }

   
    @Override
    public String toString() {
	return value;
    }

}
