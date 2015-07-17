package com.compression.file;

import java.util.HashMap;
import java.util.Iterator;

public class Tools {
	public static String toBinaryString8(int n) {
		String str = Integer.toBinaryString(n);
		int l = str.length();
		if (l > 8) {
			str = str.substring(l - 8);
		} else {
			for (int i = 0; i < 8 - l; i++) {
				str = "0" + str;
			}
		}
		return str;
	}

	public static String toBinaryString16(int n) {
		String str = Integer.toBinaryString(n);
		int l = str.length();
		if (l > 16) {
			str = str.substring(l - 16);
		} else {
			for (int i = 0; i < 16 - l; i++) {
				str = "0" + str;
			}
		}
		return str;
	}

	public static int getStoreSize(HashMap<Integer, String> map) {
		Iterator<Integer> iterator = map.keySet().iterator();
		int size = 0;
		while (iterator.hasNext()) {
			int key = iterator.next();
			String val = map.get(key);
			int length = val.length();
			size = size + 16 + length;
		}
//		if (size % 8 == 0) {
//			size = size / 8;
//		} else {
//			size = size / 8 + 1;
//		}
		return size;
	}
}
