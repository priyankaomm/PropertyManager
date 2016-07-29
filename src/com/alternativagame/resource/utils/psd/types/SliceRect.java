/**
 * 
 */
package com.alternativagame.resource.utils.psd.types;

import java.io.DataInputStream;
import java.io.IOException;


public class SliceRect extends Rect {

    /**
     * @param in
     * @throws IOException
     */
    public SliceRect(DataInputStream in) throws IOException {

	setLeft(in.readInt());
	setTop(in.readInt());
	setRight(in.readInt());
	setBottom(in.readInt());
    }

}
