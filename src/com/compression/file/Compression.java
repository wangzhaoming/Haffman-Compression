package com.compression.file;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Compression extends JFrame {
	private Tree tree;
	private JButton btnCompress;
	private JButton btnDecompress;

	public Compression() {
		this.setLayout(new FlowLayout());
		btnCompress = new JButton("Compress");
		btnDecompress = new JButton("Decompress");
		add(btnCompress);
		add(btnDecompress);
		this.setSize(200, 120);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		btnCompress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					doCompress();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnDecompress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					doDecompress();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void doCompress() throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();// ��ȡ·��
			String dPath = path.substring(0, path.lastIndexOf('.')) + ".huf";// Ŀ��·��
			char[] ch = path.substring(path.lastIndexOf('.')+1).toCharArray();// ��ȡ��չ��

			SrcReader reader = new SrcReader();
			tree = new Tree(reader.read(path));// ���ɹ�������
			HashMap<Integer, String> map = tree.getHashMap();// ��ȡ���

			BitWriter bw = new BitWriter(new FileOutputStream(dPath));// ��λд�����ݵ���

			bw.write(Tools.toBinaryString8(ch.length));// д����չ������
			for (char c : ch) {
				bw.write(Tools.toBinaryString8(c));// д����չ��
			}

			bw.write(Tools.toBinaryString16(Tools.getStoreSize(map)));// д�������

			Iterator<Integer> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {// ����д�����
				int key = iterator.next();
				String val = map.get(key);
				int length = val.length();
				bw.write(Tools.toBinaryString8(key));
				bw.write(Tools.toBinaryString8(length));
				bw.write(val);
			}

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(path));// ��ȡ�ļ�
			int b;
			while ((b = bis.read()) != -1) {// ���ֽڶ�ȡ��ת�롢д��Դ�ļ�
				String str = map.get(b);
				bw.write(str);
			}
			bis.close();
			bw.close();
			JOptionPane.showMessageDialog(this, "Compress Completed");
		}
	}

	private void doDecompress() throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			
			BitReader br=new BitReader(new FileInputStream(path));
			br.deCompress(path);
			
			JOptionPane.showMessageDialog(this, "Decompress Completed");
		}
	}

	public static void main(String[] args) {
		new Compression();
	}
}
