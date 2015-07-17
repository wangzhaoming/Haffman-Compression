package com.compression.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class BitReader {
	private BufferedInputStream bis;
	private byte[] buf;
	private int i;
	private StringBuffer strBuf;
	private final int bit1;
	private final int bit0;
	private HashMap<String, Integer> map;
	private int lastbits;

	public BitReader(FileInputStream fis) throws Exception {
		bis = new BufferedInputStream(fis);
		buf = new byte[1024];
		strBuf = new StringBuffer();
		i = 0;// 作为buf的下标
		bit1 = 0x80;// 按位置1
		bit0 = 0xffffff7f;// 置0
		map = new HashMap<String, Integer>();
		lastbits=-1;
	}

	public void deCompress(String path) throws Exception {
		String dPath = path.substring(0, path.lastIndexOf('.'))
				+'.'+ getExtendName();// 目标路径
		getHashMap();

		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(dPath));
		byte[] bosBuf=new byte[102400];//缓冲大小*************
		int bosIndex=0;
		
		
		
		String key="";
		int value=0;
		while (lastbits>0||lastbits==-1) {
			key+=getString(1);
			if (map.get(key)!=null) {
				value=map.get(key);
				bosBuf[bosIndex]=(byte) value;
				bosIndex++;
				if (bosIndex==102400) {//缓冲大小***********
					bos.write(bosBuf);
					bosIndex=0;
				}
				key="";
			}
		}
		bos.write(bosBuf, 0, bosIndex+1);
		bos.flush();
		bos.close();
		bis.close();
	}

	private void getHashMap() throws Exception {
		int size = bis.read();
		size = size << 8;
		size = size | bis.read();// 获取码表的长度

		i = bis.read(buf);
		byteArrayToString();
		if (i<1024) {//如果读到文件末尾
			lastbits=strBuf.length()-8-str8ToInt(strBuf.substring(strBuf.length()-8));
		}
		i = 0;

		while (size > 0) {
			int val = str8ToInt(getString(8));
			int l = str8ToInt(getString(8));
			String key = getString(l);

			map.put(key, val);

			size = size - 16 - l;
		}
	}

	private int str8ToInt(String str) {
		char[] ch = str.toCharArray();
		int val = 0;
		for (int k = 7; k >= 0; k--) {
			if (ch[7 - k] == '1') {
				val = (val | (bit1 >>> (7 - k)));
			} else {// =='0'
				val = (val & (bit0 >>> (7 - k)));
			}
		}
		return val;
	}

	private void byteArrayToString() {
		for (int j = 0; j < i; j++) {
			for (int k = 7; k >= 0; k--) {
				int temp = (buf[j] >>> k) & 0x00000001;
				strBuf.append(temp);
			}
		}
	}

	private String getString(int n) throws IOException {
		if (strBuf.length() < n) {
			i = bis.read(buf);
			byteArrayToString();
			if (i<1024) {
				lastbits=strBuf.length()-8-str8ToInt(strBuf.substring(strBuf.length()-8));
			}
			i = 0;
		}

		String str = strBuf.substring(0, n);
		strBuf.delete(0, n);
		if (lastbits!=-1) {
			lastbits-=n;
		}
		return str;
	}

	private String getExtendName() throws Exception {
		int n = bis.read();
		byte[] b = new byte[n];
		bis.read(b);

		String ext = "";
		for (byte c : b) {
			char ch = (char) c;
			ext += ch;
		}
		return ext;
	}
}
