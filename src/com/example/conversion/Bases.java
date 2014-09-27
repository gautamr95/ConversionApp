package com.example.conversion;

import java.util.ArrayList;

public class Bases {

	public static String decToBin(String sDec) {
		int dec = Integer.parseInt(sDec);
		return Integer.toBinaryString(dec);
	}

	public static String binToDec(String sBin) {
		return "" + Integer.parseInt(sBin, 2);
	}

	public static String decToHex(String sDec) {
		int hex = Integer.parseInt(sDec);
		return Integer.toHexString(hex);
	}

	public static String hexToDec(String sHex) {
		return "" + Integer.parseInt(sHex, 16);
	}

	public static String binToHex(String sBin) {
		return decToHex(binToDec(sBin));
	}

	public static String hexToBin(String sHex) {
		return decToBin(hexToDec(sHex));
	}

	public static ArrayList<String> convert(String s, int i) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			switch (i) {
			case 0:
				list.add(s);
				break;
			case 1:
				list.add(binToDec(s));
				break;
			case 2:
				list.add(hexToDec(s));
				break;
			default:
				break;
			}
			list.add(decToBin(list.get(0)));
			list.add(decToHex(list.get(0)));
			return list;

		} catch (Exception e) {
			return list;
		}

	}

}
