package com.example.conversion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UnitFragment extends Fragment implements TextWatcher {
	String[] units;
	ArrayList<EditText> etList = new ArrayList<EditText>();
	ArrayList<TextView> tvList = new ArrayList<TextView>();
	int pos = 0;
	final static String TAG = "Main Activity";
	private static Hashtable<Integer, String> currencies;
	private final static String[] rates = { "USD", "GBP", "EUR", "AUD", "BTC",
			"BRL", "CAD", "CNY", "INR", "JPY", "MXN", "CHF" };
	private final String TIME = "timestamp";

	public UnitFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_unit, container,
				false);
		LinearLayout linearLayout = (LinearLayout) rootView
				.findViewById(R.id.linLayout);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		setUnits(pos);
		for (int i = 0; i < units.length; i++) {
			createEditTexts(i, params);
			createTextViews(i, params);
			linearLayout.addView(tvList.get(i));
			linearLayout.addView(etList.get(i));
			if (i != units.length - 1) {
				View ruler = new View(getActivity());
				ruler.setBackgroundColor(0xFF33B5E5);
				linearLayout.addView(ruler, new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 2));
			}
		}
		if (pos == 0) {
			makeFootInch(linearLayout, params);
		}
		addWatchers();
		Log.i(TAG, "Made views");
		return rootView;
	}

	public void setUnits(int i) {
		Log.i(getTag(), "setting units");
		switch (pos) {
		case 0:
			units = getResources().getStringArray(R.array.length_units);
			break;
		case 1:
			units = getResources().getStringArray(R.array.weight_units);
			break;
		case 2:
			units = getResources().getStringArray(R.array.numerical_units);
			break;
		case 3:
			units = getResources().getStringArray(R.array.currency_units);
			makeHashtable();
			Date d = new Date();
			if (d.getTime() > (getCurrencyPref().getLong(TIME, 0) + (3600000 * 2))) {
				Currency c = new Currency();
				c.execute(getCurrencyPref());
				Log.v("currency", "time: " + d.getTime() + "; old: "
						+ getCurrencyPref().getLong(TIME, 0));
			}
			break;
		default:
			units = getResources().getStringArray(R.array.length_units);
			break;
		}
	}

	public void createEditTexts(int i, LayoutParams params) {
		EditText et = new EditText(getActivity());
		et.setHint(units[i]);
		et.setId(i);
		et.setLayoutParams(params);
		if (pos != 2)
			et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL
					| InputType.TYPE_CLASS_NUMBER);
		else if (pos == 2 && i != 2)
			et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL
					| InputType.TYPE_CLASS_NUMBER);
		etList.add(et);
	}

	public void createTextViews(int i, LayoutParams params) {
		TextView tv = new TextView(getActivity());
		tv.setText(units[i]);
		tv.setId(i);
		tv.setLayoutParams(params);
		tvList.add(tv);
	}

	public void setPos(int i) {
		pos = i;
	}

	public void clear() {
		for (EditText et : etList) {
			et.getText().clear();
		}
		enableEditText();
	}

	private void makeFootInch(LinearLayout linearLayout, LayoutParams params) {
		View ruler = new View(getActivity());
		ruler.setBackgroundColor(0xFF33B5E5);
		linearLayout.addView(ruler, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 2));
		EditText footEt = new EditText(getActivity());
		EditText inchEt = new EditText(getActivity());
		TextView footTv = new TextView(getActivity());
		footEt.setHint(units[4]);
		inchEt.setHint(units[5]);
		footTv.setText(units[4] + " and " + units[5]);
		footEt.setLayoutParams(params);
		inchEt.setLayoutParams(params);
		footEt.setId(9);
		inchEt.setId(10);
		footTv.setLayoutParams(params);
		inchEt.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL
				| InputType.TYPE_CLASS_NUMBER);
		footEt.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL
				| InputType.TYPE_CLASS_NUMBER);
		// footEt.addTextChangedListener(this);
		// inchEt.addTextChangedListener(this);
		etList.add(footEt);
		etList.add(inchEt);
		linearLayout.addView(footTv);
		linearLayout.addView(footEt);
		linearLayout.addView(inchEt);

	}

	public void convert(Editable s, int id) {
		ArrayList<String> values = new ArrayList<String>();
		String s1 = "";
		String s2 = "";
		String string = "";
		switch (pos) {
		case 0:
			if (id == 9) {
				s1 = s.toString();
				for (EditText et : etList) {
					if (et.getId() == 10)
						s2 = et.getText().toString();
				}
				string = s1 + "+" + s2;
				values = Lengths.convert(string, id);
			} else if (id == 10) {
				s2 = s.toString();
				for (EditText et : etList) {
					if (et.getId() == 9)
						s1 = et.getText().toString();
				}
				string = s1 + "+" + s2;
				values = Lengths.convert(string, id);
			} else
				values = Lengths.convert(s.toString(), id);
			Log.i(TAG, s.toString());
			break;
		case 2:
			values = Bases.convert(s.toString(), id);
			break;
		case 3:
			values = Currency.convert(currencies.get(id), s.toString(), id,
					getCurrencyPref());
			Log.v("something", "converting");
		default:
			break;
		}
		if (values.size() == 0)
			Toast.makeText(getActivity(), "Invalid number", Toast.LENGTH_SHORT)
					.show();
		for (int j = 0; j < values.size(); j++) {
			EditText et = etList.get(j);
			et.setText(values.get(j));
			et.setSelection(et.getText().length());

		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		Log.i(TAG, getActivity().getCurrentFocus().getId() + "");
		int id = getActivity().getCurrentFocus().getId();
		if (pos == 0) {
			if (id == 9)
				s.append("+" + etList.get(10).getText());
			else if (id == 10) {
				s = etList.get(9).getText().append(s);

			}
		}

		if (s.toString().equals("")) {
			clear();
			return;
		}
		removeWatchers();
		convert(s, id);
		disableEditText(id);
		addWatchers();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		enableEditText();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		enableEditText();

	}

	public void addWatchers() {
		for (EditText et : etList)
			et.addTextChangedListener(this);
	}

	public void removeWatchers() {
		for (EditText et : etList) {
			et.removeTextChangedListener(this);
		}
	}

	public void enableEditText() {

		for (EditText et : etList) {
			et.setSelectAllOnFocus(false);
		}

	}

	public void disableEditText(int id) {
		for (EditText et : etList) {
			et.setSelectAllOnFocus(true);
		}
	}

	private void makeHashtable() {
		currencies = new Hashtable<Integer, String>();
		for (int i = 0; i < rates.length; i++) {
			currencies.put(Integer.valueOf(i), rates[i]);
		}

	}

	private void makeCurrencyPrefs(Hashtable<String, BigDecimal> currencyTable) {
		Context context = getActivity();
		SharedPreferences currencyPref = context.getSharedPreferences(
				getString(R.string.CURRENCY_PREF), Context.MODE_PRIVATE);
		Editor editor = currencyPref.edit();
		for (String key : rates) {
			editor.putString(key, currencyTable.get(key).toString());
		}
		editor.commit();
	}

	private SharedPreferences getCurrencyPref() {
		Context context = getActivity();
		SharedPreferences currencyPref = context.getSharedPreferences(
				getString(R.string.CURRENCY_PREF), Context.MODE_PRIVATE);

		return currencyPref;
	}
}
