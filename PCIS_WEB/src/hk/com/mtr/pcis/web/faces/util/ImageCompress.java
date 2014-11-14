package hk.com.mtr.pcis.web.faces.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.ImageIcon;
import com.sun.image.codec.jpeg.JPEGCodec;

import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
;

public class ImageCompress {
	
	private final static float quality = 0.1f;
	/**
	 * Compress image by DPI
	 * 
	 * @param originalFile
	 * @param resizedFile
	 * @param newWidth
	 * @param quality
	 * @throws IOException
	 */

	public static void resize(final File originalFile, final ByteArrayOutputStream byteOutputStream, final int originDPI, final int newDPI) throws IOException {
		ImageIcon icon = new ImageIcon(originalFile.getCanonicalPath());
		Image img = icon.getImage();
		Image resizedImage = null;
		int iWidth = img.getWidth(null);
		int iHeight = img.getHeight(null);		

		
		if (newDPI > originDPI) {
			throw new IllegalArgumentException("dpi cannot be max then original File");
		}
		Double douWidth = new Double(iWidth) * ((new Double(newDPI) / new Double(originDPI)));
		int newWidth = douWidth.intValue();
		if (iWidth > iHeight) {
			resizedImage = img.getScaledInstance(newWidth, (newWidth * iHeight)
					/ iWidth, Image.SCALE_SMOOTH);
		} else {
			resizedImage = img.getScaledInstance((newWidth * iWidth) / iHeight,
					newWidth, Image.SCALE_SMOOTH);
		}
		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();
		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();
		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor,
				1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);
		// Encodes image as a JPEG data stream
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(byteOutputStream);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
		param.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
		param.setXDensity(newDPI);
		param.setYDensity(newDPI);
		param.setQuality(quality, true);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(bufferedImage);
	}
	
	public static void main(String[] args){
		
		int iWidth = 2480;
		Double newWidth = new Double(iWidth) * ((new Double(200) / new Double(300)));
		
		System.out.println("width:" + newWidth.intValue());
	}

}
