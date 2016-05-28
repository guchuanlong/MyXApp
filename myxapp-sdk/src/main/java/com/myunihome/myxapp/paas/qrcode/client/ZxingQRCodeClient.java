package com.myunihome.myxapp.paas.qrcode.client;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class ZxingQRCodeClient extends AbstractQRCodeClient {

	@Override
	public void writeQRCode(Path path, String text, int width, int height, int margin, String encode, String format)
			throws Exception {
		Map<EncodeHintType, Object> hints= new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, encode);
		hints.put(EncodeHintType.MARGIN, margin);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);
	}

	@Override
	public void writeQRCode(OutputStream os, String text, int width, int height, int margin, String encode,
			String format) throws Exception {
		Map<EncodeHintType, Object> hints= new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, encode);
		hints.put(EncodeHintType.MARGIN, margin);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
		MatrixToImageWriter.writeToStream(bitMatrix, format, os);
	}

}
