/**
 * 
 */
package com.alternativagame.resource.utils.psd.layer;

import java.io.DataInputStream;
import java.io.IOException;


public class ChannelLengthInfo {

    private final short id;

    private final int length;

    /**
     * @param in
     * @throws IOException
     */
    public ChannelLengthInfo(DataInputStream in) throws IOException {
	this.id = in.readShort();
	this.length = in.readInt();
    }

    public short getId() {
	return id;
    }

    public int getLength() {
	return length;
    }

}
