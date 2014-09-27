package com.example.conversion;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.MathContext;

public class Lengths {
	static ArrayList<String> list;

	public static String feetToMeter(String feet) {
		return new BigDecimal(".3048").multiply(new BigDecimal(feet),
				MathContext.DECIMAL64).toString();
	}

	public static String feetAndInchesToMeter(String feet, String inch) {
		if (feet.equals(""))
			feet = "0";
		else if (inch.equals(""))
			inch = "0";
		double f = Double.parseDouble(feet) + Double.parseDouble(inch) / 12;
		return "" + (f * .3048);
	}

	public static String mileToMeter(String mile) {
		return "" + 1609.34 * Double.parseDouble(mile);
	}

	public static String nauticalToMeter(String nautical) {
		return "" + 1852. * Double.parseDouble(nautical);
	}

	public static String centimeterToMeter(String cent) {
		return "" + .01 * Double.parseDouble(cent);
	}

	public static String milimeterToMeter(String mili) {
		return "" + .001 * Double.parseDouble(mili);
	}

	public static String kilometerToMeter(String kilo) {
		return "" + 1000. * Double.parseDouble(kilo);
	}

	public static String inchToMeter(String inch) {
		return "" + .0254 * Double.parseDouble(inch);
	}

	public static String yardToMeter(String yard) {
		return "" + .9144 * Double.parseDouble(yard);
	}

	public static String meterToFeet(String meter) {
		return "" + 3.28084 * Double.parseDouble(meter);
	}

	public static String meterToInch(String meter) {
		return "" + 39.3701 * Double.parseDouble(meter);
	}

	public static String meterToKilometer(String meter) {
		return "" + .001 * Double.parseDouble(meter);
	}

	public static String meterToMilimeter(String meter) {
		return "" + 1000. * Double.parseDouble(meter);
	}

	public static String meterToCentimeter(String meter) {
		return "" + 100. * Double.parseDouble(meter);
	}

	public static String meterToNauticalMile(String meter) {
		return "" + .000539957 * Double.parseDouble(meter);
	}

	public static String meterToYard(String meter) {
		return "" + 1.09361 * Double.parseDouble(meter);
	}

	public static ArrayList<String> meterToFeetAndInches(String meter) {
		ArrayList<String> list = new ArrayList<String>();
		String s = meterToFeet(meter);
		int pos = s.indexOf(".");
		String feet = s.substring(0, pos);
		String inch = "" + (Double.parseDouble(s.substring(pos)) * 12);
		list.add(feet);
		list.add(inch);
		return list;
	}

	public static String meterToMile(String meter) {
		return "" + 0.000621371 * Double.parseDouble(meter);
	}

	public static ArrayList<String> convert(String s, int i) {
		list = new ArrayList<String>();
		String meter = "";
		try {
			switch (i) {
			case 0:
				list.add(s);
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(meterToCentimeter(meter));
				list.add(meterToMilimeter(meter));
				list.add(meterToFeet(meter));
				list.add(meterToInch(meter));
				list.add(meterToYard(meter));
				list.add(meterToMile(meter));
				list.add(meterToNauticalMile(meter));
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 1:
				list.add(kilometerToMeter(s));
				meter = list.get(0);
				list.add(s);
				list.add(meterToCentimeter(meter));
				list.add(meterToMilimeter(meter));
				list.add(meterToFeet(meter));
				list.add(meterToInch(meter));
				list.add(meterToYard(meter));
				list.add(meterToMile(meter));
				list.add(meterToNauticalMile(meter));
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 2:
				list.add(centimeterToMeter(s));
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(s);
				list.add(meterToMilimeter(meter));
				list.add(meterToFeet(meter));
				list.add(meterToInch(meter));
				list.add(meterToYard(meter));
				list.add(meterToMile(meter));
				list.add(meterToNauticalMile(meter));
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 3:
				list.add(milimeterToMeter(s));
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(meterToCentimeter(meter));
				list.add(s);
				list.add(meterToFeet(meter));
				list.add(meterToInch(meter));
				list.add(meterToYard(meter));
				list.add(meterToMile(meter));
				list.add(meterToNauticalMile(meter));
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 4:
				list.add(feetToMeter(s));
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(meterToCentimeter(meter));
				list.add(meterToMilimeter(meter));
				list.add(s);
				list.add(meterToInch(meter));
				list.add(meterToYard(meter));
				list.add(meterToMile(meter));
				list.add(meterToNauticalMile(meter));
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 5:
				list.add(inchToMeter(s));
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(meterToCentimeter(meter));
				list.add(meterToMilimeter(meter));
				list.add(meterToFeet(meter));
				list.add(s);
				list.add(meterToYard(meter));
				list.add(meterToMile(meter));
				list.add(meterToNauticalMile(meter));
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 6:
				list.add(yardToMeter(s));
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(meterToCentimeter(meter));
				list.add(meterToMilimeter(meter));
				list.add(meterToFeet(meter));
				list.add(meterToInch(meter));
				list.add(s);
				list.add(meterToMile(meter));
				list.add(meterToNauticalMile(meter));
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 7:
				list.add(mileToMeter(s));
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(meterToCentimeter(meter));
				list.add(meterToMilimeter(meter));
				list.add(meterToFeet(meter));
				list.add(meterToInch(meter));
				list.add(meterToYard(meter));
				list.add(s);
				list.add(meterToNauticalMile(meter));
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 8:
				list.add(nauticalToMeter(s));
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(meterToCentimeter(meter));
				list.add(meterToMilimeter(meter));
				list.add(meterToFeet(meter));
				list.add(meterToInch(meter));
				list.add(meterToYard(meter));
				list.add(meterToMile(meter));
				list.add(s);
				list.addAll(meterToFeetAndInches(meter));
				break;
			case 9:
				String s1 = s.substring(0, s.indexOf("+"));
				String s2 = s.substring(s.indexOf("+") + 1);
				list.add(feetAndInchesToMeter(s1, s2));
				meter = list.get(0);
				list.add(meterToKilometer(meter));
				list.add(meterToCentimeter(meter));
				list.add(meterToMilimeter(meter));
				list.add(meterToFeet(meter));
				list.add(meterToInch(meter));
				list.add(meterToYard(meter));
				list.add(meterToMile(meter));
				list.add(meterToNauticalMile(meter));
				list.add(s1);
				list.add(s2);
				break;
			case 10:
				break;
			default:
				break;
			}
			// String meter = list.get(0);
			// list.add(meterToKilometer(meter));
			// list.add(meterToCentimeter(meter));
			// list.add(meterToMilimeter(meter));
			// list.add(meterToFeet(meter));
			// list.add(meterToInch(meter));
			// list.add(meterToYard(meter));
			// list.add(meterToMile(meter));
			// list.add(meterToNauticalMile(meter));
			// list.addAll(meterToFeetAndInches(meter));
			return list;
		} catch (Exception e) {
			return list;
		}

	}

	public void parse() {
		for (String s : list) {
			if (s.endsWith(".0"))
				s = s.substring(0, s.indexOf(".0"));
		}

	}
}
