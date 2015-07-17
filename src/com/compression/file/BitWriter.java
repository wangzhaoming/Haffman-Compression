package com.compression.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitWriter {
	private BufferedOutputStream bos;
	private byte[] buf;
	private int lastBits;
	private final int bit1;
	private final int bit0;
	private int i;

	public BitWriter(FileOutputStream fos) {
		bos = new BufferedOutputStream(fos);
		buf = new byte[1024];
		i = 0;// buf的下标
		lastBits = 8;
		bit1 = 0x80;// 按位置1
		bit0 = 0xffffff7f;// 置0
	}

	public void write(String str) throws IOException {// 写入01字符串
		char[] ch = str.toCharArray();
		for (char c : ch) {
			if (c == '1') {
				buf[i] = (byte) (buf[i] | (bit1 >>> (8 - lastBits)));
			} else {// =='0'
				buf[i] = (byte) (buf[i] & (bit0 >>> (8 - lastBits)));
			}
			if (--lastBits == 0) {
				lastBits = 8;
				if (++i == 1024) {
					bos.write(buf);
					i = 0;
				}
			}
		}
	}

	public void close() throws IOException {
		bos.write(buf, 0, i+1);
		if (lastBits<8) {
			bos.write(lastBits);
		}else{
			bos.write(0);
		}
		bos.flush();
		bos.close();
	}
}
