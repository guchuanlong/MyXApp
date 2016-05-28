package com.myunihome.myxapp.paas.qrcode.factory;

import com.myunihome.myxapp.paas.qrcode.client.AbstractQRCodeClient;
import com.myunihome.myxapp.paas.qrcode.client.ZxingQRCodeClient;

public class QRCodeFactory {
public static AbstractQRCodeClient getZxingQRCodeClient(){
	return new ZxingQRCodeClient();
}
}
