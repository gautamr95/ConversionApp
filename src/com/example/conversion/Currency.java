package com.example.conversion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class Currency extends AsyncTask<SharedPreferences, Void, String> {

	private static String[] rates = { "USD", "GBP", "EUR", "AUD", "BTC", "BRL",
			"CAD", "CNY", "INR", "JPY", "MXN", "CHF" };
	private static ArrayList<String> list;
	private static SharedPreferences cPref;

	public static ArrayList<String> convert(String key, String s, int id,
			SharedPreferences currencyPref) {
		list = new ArrayList<String>();
		if (cPref == null)
			cPref = currencyPref;
		BigDecimal usd = new BigDecimal(1).divide(
				new BigDecimal(cPref.getString(key, "0")),
				MathContext.DECIMAL32).multiply(new BigDecimal(s),
				MathContext.DECIMAL32);
		for (String k : rates) {
			BigDecimal quote = usd.multiply(
					new BigDecimal(cPref.getString(k, "0")),
					MathContext.DECIMAL32);
			Log.v("Currency", k);
			list.add(quote.stripTrailingZeros().toString());
		}
		list.remove(id);
		list.add(id, s);
		return list;
	}

	private String getReadableDateString(long time) {
		// Because the API returns a unix timestamp (measured in seconds),
		// it must be converted to milliseconds in order to be converted to
		// valid date.
		Date date = new Date(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSSZ");
		return format.format(date).toString();
	}

	private void getCurrencyDataFromJson(String currencyJsonStr)
			throws JSONException {
		// Base units are USD
		final String TIME = "timestamp";
		final String RATES = "rates";

		JSONObject currencyJson = new JSONObject(currencyJsonStr);
		JSONObject rates = currencyJson.getJSONObject(RATES);
		JSONArray names = rates.names();
		Editor editor = cPref.edit();
		for (int i = 0; i < rates.names().length(); i++) {
			editor.putString(names.getString(i),
					(new BigDecimal(rates.getString(names.getString(i))))
							.toString());
		}

		long time = currencyJson.getLong(TIME);
		editor.putLong(TIME, time * 1000);
		editor.commit();
		Log.v("stupid time", time + " " + cPref.getLong(TIME, 1234));
		String timeRefreshed = getReadableDateString(time);
		Log.v("Currency", "Refreshed at: " + time + " " + timeRefreshed);
	}

	@Override
	protected String doInBackground(SharedPreferences... params) {
		final String APP_ID = "58f2b70b535247c99515f85c7c52feb8";
		final String CURRENCY_BASE_URL = "http://openexchangerates.org/api/latest.json";
		final String ID_PARAM = "app_id";
		final String LOG_TAG = MainActivity.class.getSimpleName();
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		String currencyJsonStr = null;
		cPref = params[0];
		try {
			Uri builtUri = Uri.parse(CURRENCY_BASE_URL).buildUpon()
					.appendQueryParameter(ID_PARAM, APP_ID).build();
			URL url = new URL(builtUri.toString());
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			InputStream inputStream = urlConnection.getInputStream();
			StringBuffer buffer = new StringBuffer();
			if (inputStream == null) {
				return null;
			}
			reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}
			if (buffer.length() == 0) {
				return null;
			}
			currencyJsonStr = buffer.toString();
			getCurrencyDataFromJson(currencyJsonStr);
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error no internet?");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return currencyJsonStr;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

}
