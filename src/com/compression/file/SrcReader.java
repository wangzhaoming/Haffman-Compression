package com.compression.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class SrcReader {
	private int[] num;
	private double[] value;
	
	public SrcReader() {
		num = new int[256];
		value=new double[256];
	}

	public double[][] read(String path) throws Exception {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				path));
		int byteRead;
		byte[] buf = new byte[1024];

		while ((byteRead = in.read(buf)) != -1) {
			count(buf, byteRead);
		}
		countValue(path);
		in.close();
		return changeArray();
	}
	
	private void count(byte[] buf,int byteRead){
		for (int i = 0; i < byteRead; i++) {
			int n=buf[i]&0x000000ff;
			num[n]++;
		}
	}
	
	private void countValue(String path){
		File file=new File(path);
		long totalByte=file.length();
		for (int i = 0; i < 256; i++) {
			value[i]=num[i]*1.0/totalByte;
		}
	}
	
	private double[][] changeArray(){
		double[][] d;
		int count=0;
		for (int i=0;i<256;i++) {
			if (value[i]!=0.0) {
				count++;
			}
		}
		d=new double[count][2];
		int j=0;
		for (int i=0;i<256;i++) {
			if (value[i]!=0.0) {
				d[j][0]=i;
				d[j][1]=value[i];
				j++;
			}
		}
		return d;
	}
}
