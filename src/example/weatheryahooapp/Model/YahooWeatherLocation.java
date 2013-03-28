package example.weatheryahooapp.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.util.Log;

public class YahooWeatherLocation {
	// WOIED of city
	private static final String PARAM_WOEID = "woeid";

	// Currently not support this country
	public static final int WOEID_NOTSUPPORT = -1;

	// TAG for debugging
	private static final String TAG = "YahooWeatherLocation";

	// Instance of yahoo location support
	private static YahooWeatherLocation m_Instance;

	private YahooWeatherLocation() {
		// Do Something
	}

	public synchronized YahooWeatherLocation getInstance() {
		if (m_Instance == null) {
			m_Instance = new YahooWeatherLocation();
		}

		return m_Instance;
	}

	
	// Get WOIED of locations
	public int getWOEIDByLocation(String strLocation) {
		if (strLocation == null) {
			Log.e(TAG, "Invalid input parameter");
			return WOEID_NOTSUPPORT;
		}

		int nWOEID = WOEID_NOTSUPPORT;

		return nWOEID;
	}

	// Get data about WOIED of locations
	public static String parserWOEIDData(Document docWeather) {
		if (docWeather == null) {
			Log.e(TAG, "Invalid doc weatehr");
			return null;
		}

		String strWOEID = null;
		try {
			Element root = docWeather.getDocumentElement();
			root.normalize();

			strWOEID = root.getElementsByTagName(PARAM_WOEID).item(0)
					.getFirstChild().getNodeValue();

		} catch (Exception e) {
			Log.e(TAG, "Something wroing with parser data");
			return null;
		}

		return strWOEID;
	}
}
