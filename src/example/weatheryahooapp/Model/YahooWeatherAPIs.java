package example.weatheryahooapp.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import android.util.Log;

import example.weatheryahooapp.Controller.R;

public class YahooWeatherAPIs {
	// For debug
	private final static String TAG = "Yahoo Weather APIs";

	// Elements of yahoo
	private final static String ELEMENTS_YAHOO_LOCATION = "yweather:location";
	private final static String ELEMENTS_YAHOO_UNITS = "yweather:units";
	private final static String ELEMENTS_YAHOO_WIND = "yweather:wind";
	private final static String ELEMENTS_YAHOO_ATMOSPHERE = "yweather:atmosphere";
	private final static String ELEMENTS_YAHOO_ASTRONOMY = "yweather:astronomy";
	private final static String ELEMENTS_YAHOO_CONDITION = "yweather:condition";
	private final static String ELEMENTS_YAHOO_FORECAST = "yweather:forecast";

	// Attributes of elements "yweather:location"
	private final static String ATT_YAHOO_CITY = "city";
	private final static String ATT_YAHOO_COUNTRY = "country";

	// Attributes of elements "yweather:units"
	private final static String ATT_YAHOO_TEMP_UNITS = "temperature";

	// Attributes of elements "yweather:asmosphere"
	private final static String ATT_YAHOO_HUMIDITY = "humidity";
	private final static String ATT_YAHOO_VISIBILITY = "visibility";

	// Attributes of elements "yweather:condition"
	private final static String ATT_YAHOO_TEXT = "text";
	private final static String ATT_YAHOO_CODE = "code";
	private final static String ATT_YAHOO_TEMP = "temp";
	private final static String ATT_YAHOO_DATE = "date";

	// Attributes of elements "yweather:astronomy"
	private static final String ATT_YAHOO_SUNSET = "sunset";
	private static final String ATT_YAHOO_SUNRISE = "sunrise";

	// Attributes of elements "yweather:wind"
	private static final String ATT_YAHOO_WIND_SPEED = "speed";

	// Attributes of elements "yweather:forecast"
	private static String ATT_YAHOO_DATE_NEXTDAY = "day";
	private static String ATT_YAHOO_LOW_TEMP = "low";
	private static String ATT_YAHOO_MAX_TEMP = "high";

	// Image about info weather
	public static int[][] arrImage = { { R.drawable.a0, 0 },
			{ R.drawable.a1, 1 }, { R.drawable.a2, 2 }, { R.drawable.a3, 3 },
			{ R.drawable.a4, 4 }, { R.drawable.a5, 5 }, { R.drawable.a6, 6 },
			{ R.drawable.a7, 7 }, { R.drawable.a8, 8 }, { R.drawable.a9, 9 },
			{ R.drawable.a10, 10 }, { R.drawable.a11, 11 },
			{ R.drawable.a12, 12 }, { R.drawable.a13, 13 },
			{ R.drawable.a14, 14 }, { R.drawable.a15, 15 },
			{ R.drawable.a16, 16 }, { R.drawable.a17, 17 },
			{ R.drawable.a18, 18 }, { R.drawable.a19, 19 },
			{ R.drawable.a20, 20 }, { R.drawable.a21, 21 },
			{ R.drawable.a22, 22 }, { R.drawable.a23, 23 },
			{ R.drawable.a24, 24 }, { R.drawable.a25, 25 },
			{ R.drawable.a26, 26 }, { R.drawable.a27, 27 },
			{ R.drawable.a28, 28 }, { R.drawable.a29, 29 },
			{ R.drawable.a30, 30 }, { R.drawable.a31, 31 },
			{ R.drawable.a32, 32 }, { R.drawable.a33, 33 },
			{ R.drawable.a34, 34 }, { R.drawable.a35, 35 },
			{ R.drawable.a36, 36 }, { R.drawable.a37, 37 },
			{ R.drawable.a38, 38 }, { R.drawable.a39, 39 },
			{ R.drawable.a40, 40 }, { R.drawable.a41, 41 },
			{ R.drawable.a42, 42 }, { R.drawable.a43, 43 },
			{ R.drawable.a44, 44 }, { R.drawable.a45, 45 },
			{ R.drawable.a46, 46 }, { R.drawable.a47, 47 },
			{ R.drawable.a0, 3200 }, };

	//

	public static YahooWeatherInfo parserYahooWeatherInfo(Document docWeather) {
		if (docWeather == null) {
			Log.e(TAG, "Invalid doc weather");
			return null;
		}

		String strCity = null;
		String strCountry = null;
		String strTempUnit = null;
		String strTempValue = null;
		String strHumidity = null;
		String strText = null;
		String strCode = null;
		String strDate = null;
		String strVisibility = null;
		String strSunset = null;
		String strSunrise = null;
		String strWindSpeed = null;

		// Info weather of nextday
		String strDateNextDay = null;
		String strLowTemp = null;
		String strMaxTemp = null;
		String strCodeNextDay = null;

		try {
			Element root = docWeather.getDocumentElement();
			root.normalize();

			NamedNodeMap locationNode = root
					.getElementsByTagName(ELEMENTS_YAHOO_LOCATION).item(0)
					.getAttributes();

			if (locationNode != null) {
				strCity = locationNode.getNamedItem(ATT_YAHOO_CITY)
						.getNodeValue();
				strCountry = locationNode.getNamedItem(ATT_YAHOO_COUNTRY)
						.getNodeValue();
			}

			NamedNodeMap unitNode = root
					.getElementsByTagName(ELEMENTS_YAHOO_UNITS).item(0)
					.getAttributes();
			if (unitNode != null) {
				strTempUnit = unitNode.getNamedItem(ATT_YAHOO_TEMP_UNITS)
						.getNodeValue();
			}

			NamedNodeMap atmosNode = root
					.getElementsByTagName(ELEMENTS_YAHOO_ATMOSPHERE).item(0)
					.getAttributes();
			if (atmosNode != null) {
				strHumidity = atmosNode.getNamedItem(ATT_YAHOO_HUMIDITY)
						.getNodeValue();
				strVisibility = atmosNode.getNamedItem(ATT_YAHOO_VISIBILITY)
						.getNodeValue();
			}

			NamedNodeMap conditionNode = root
					.getElementsByTagName(ELEMENTS_YAHOO_CONDITION).item(0)
					.getAttributes();
			if (conditionNode != null) {
				strText = conditionNode.getNamedItem(ATT_YAHOO_TEXT)
						.getNodeValue();
				strCode = conditionNode.getNamedItem(ATT_YAHOO_CODE)
						.getNodeValue();
				strDate = conditionNode.getNamedItem(ATT_YAHOO_DATE)
						.getNodeValue();
				strTempValue = conditionNode.getNamedItem(ATT_YAHOO_TEMP)
						.getNodeValue();
			}

			NamedNodeMap astronomyNode = root
					.getElementsByTagName(ELEMENTS_YAHOO_ASTRONOMY).item(0)
					.getAttributes();
			strSunset = astronomyNode.getNamedItem(ATT_YAHOO_SUNSET)
					.getNodeValue();
			strSunrise = astronomyNode.getNamedItem(ATT_YAHOO_SUNRISE)
					.getNodeValue();

			// Info speed wind
			NamedNodeMap windNode = root
					.getElementsByTagName(ELEMENTS_YAHOO_WIND).item(0)
					.getAttributes();
			strWindSpeed = windNode.getNamedItem(ATT_YAHOO_WIND_SPEED)
					.getNodeValue();

			// Info Weather of nextday
			NamedNodeMap yahooForecast = root
					.getElementsByTagName(ELEMENTS_YAHOO_FORECAST).item(1)
					.getAttributes();
			strDateNextDay = yahooForecast.getNamedItem(ATT_YAHOO_DATE_NEXTDAY)
					.getNodeValue();
			strLowTemp = yahooForecast.getNamedItem(ATT_YAHOO_LOW_TEMP)
					.getNodeValue();
			strMaxTemp = yahooForecast.getNamedItem(ATT_YAHOO_MAX_TEMP)
					.getNodeValue();
			strCodeNextDay = yahooForecast.getNamedItem(ATT_YAHOO_CODE)
					.getNodeValue();
		} catch (Exception e) {
			Log.e(TAG, "Something wroing with parser data");
			return null;
		}

		// Info yahoo weather
		YahooWeatherInfo yahooWeatherInfo = new YahooWeatherInfo(strCity,
				strCountry, strTempValue, strHumidity, strWindSpeed, strText,
				strCode, strDate, strTempUnit, strVisibility, strSunset,
				strSunrise, strLowTemp, strMaxTemp, strDateNextDay,
				strCodeNextDay);

		return yahooWeatherInfo;

	}
}
