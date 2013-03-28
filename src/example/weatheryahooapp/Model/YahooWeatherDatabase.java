package example.weatheryahooapp.Model;

import org.w3c.dom.Document;

import example.weatheryahooapp.Controller.ActivityScreenLocation;

import android.util.Log;

public class YahooWeatherDatabase {
	/** For debugging */
	private static final String TAG = "Yahoo Weather Database";

	/** URL for Yahoo API */
	private static final String URL_YAHOO_API_WEATHER = "http://weather.yahooapis.com/forecastrss?w=%s&u=c";

	/** Request type */
	private static final int REG_GET_WEATHER = 1;

	/** Connect helper for connection */
	private HttpConnectData m_ConnectHelper = null;

	/** Data model instance */
	private static YahooWeatherDatabase m_Instance = null;

	private YahooWeatherDatabase() {
		/* Create for connect helper */
		m_ConnectHelper = new HttpConnectData();
	}

	public static synchronized YahooWeatherDatabase getInstance() {
		if (m_Instance == null) {
			m_Instance = new YahooWeatherDatabase();
		}

		return m_Instance;
	}

	public YahooWeatherInfo getWeatherData(String strQuerry) {
		/* Verify input parameter */
		if (strQuerry == null) {
			Log.e(TAG, "Input is invalid");
			return null;
		}

		/* Currently use Yahoo API */
		YahooWeatherInfo weatherInfo = getYahooWeatherInfo(strQuerry);
		return weatherInfo;
	}

	private YahooWeatherInfo getYahooWeatherInfo(String strWOEID) {
		if (strWOEID == null) {
			Log.e(TAG, "Invalid location");
			return null;
		}

		/* Create request URL */
		String strRegURL = createURL(REG_GET_WEATHER, strWOEID);
		if (strRegURL == null) {
			Log.e(TAG, "Reg URL error");
			return null;
		}

		YahooWeatherInfo yahooWeather = null;
		try {
			Document doc = m_ConnectHelper.getDocumentFromURL(strRegURL);
			if (doc != null) {
				yahooWeather = YahooWeatherAPIs.parserYahooWeatherInfo(doc);
			}

		} catch (Exception e) {
			Log.e(TAG, "XML Pasing error:" + e);
		}

		return yahooWeather;
	}

	public String getWOEIDByLocation(String strLocation) {
		String strWOEID = null;
		String strQuerryWOEID = ActivityScreenLocation.createQuerryGetWoeid(strLocation);
		if (strQuerryWOEID == null) {
			Log.e(TAG, "Can not create WOEID");
			return null;
		}

		try {
			Document doc = m_ConnectHelper.getDocumentFromURL(strQuerryWOEID);
			if (doc != null) {
				strWOEID = YahooWeatherLocation.parserWOEIDData(doc);
			}

		} catch (Exception e) {
			Log.e(TAG, "XML Pasing error:" + e);
			return null;
		}

		return strWOEID;
	}

	private String createURL(int nRequestType, String strData) {
		if (strData == null) {
			Log.e(TAG, "Invalid input data");
			return null;
		}

		String strRegURL = null;
		switch (nRequestType) {
		case REG_GET_WEATHER:
			strRegURL = String.format(URL_YAHOO_API_WEATHER, strData);
			break;

		default:
			Log.e(TAG, "Not support this request:" + nRequestType);
			return null;
		}

		return strRegURL;
	}

	public static String convertC2F(String strC) {
		if (strC == null) {
			return "";
		}

		int nC = Integer.parseInt(strC);
		int nF = (nC * 9) / 5 + 32;
		return Integer.toString(nF);
	}
}
