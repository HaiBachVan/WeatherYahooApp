package example.weatheryahooapp.Model;

public class YahooWeatherInfo {
	// Initialize description
	private final static String DEFAULT_DATA = "NO DATA";

	// C Temperature
	public final static int TEMPERATURE_C = 1;

	// F Temprature
	public final static int TEMPERATURE_F = 2;

	// City
	private String sCity;

	// Country
	private String sCountry;

	// Temperature
	private String sTemperature;

	// Humidity
	private String sHumidity;

	// Date
	private String sDate;

	// Text
	private String sText;

	// Code
	private String sCode;

	// Visibility
	private String sVisibility;

	// Temperature Units
	private String sTemperatureUnits;
	
	// Sunset
	private String sSunset;
	
	// Sunrise
	private String sSunrise;

	public YahooWeatherInfo() {
		this(DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA,
				DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA,
				DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA);
	}

	public YahooWeatherInfo(String strCity, String strCountry, String strTemp,
			String strHum, String strText, String strCode, String strDate,
			String strTempUnit, String strVisi, String strSunset, String strSunrise) {
		// TODO Auto-generated constructor stub
		sCity = strCity;
		sCountry = strCountry;
		sTemperature = strTemp;
		sHumidity = strHum;
		sText = strText;
		sCode = strCode;
		sDate = strDate;
		sTemperatureUnits = strTempUnit;
		sVisibility = strVisi;
		sSunset = strSunset;
		sSunrise = strSunrise;
	}

	// ================ 1 ====================

	public void setTemperature(String nTemp) {
		sTemperature = nTemp;
	}

	public String getTemperature(int nTempF) {
		if (nTempF == TEMPERATURE_C) {
			return sTemperature;
		}

		return sTemperature;
	}

	// ================== 2 =====================
	public void setTempUnit(String nTempUnits) {
		sTemperatureUnits = nTempUnits;
	}

	public String getTempUnit() {
		return sTemperatureUnits;
	}

	// ================= 3 =====================
	public void setCity(String s_City) {
		sCity = s_City;
	}

	public String getCity() {
		return sCity;
	}

	// ================== 4 =====================
	public void setCountry(String s_Country) {
		sCountry = s_Country;
	}

	public String getCountry() {
		return sCountry;
	}

	// =================== 5 ====================
	public void setDate(String s_Date) {
		sDate = s_Date;
	}

	public String getDate() {
		return sDate;
	}

	// ================= 6 ===================
	public void setCode(String s_Code) {
		sCode = s_Code;
	}

	public String getCode() {
		return sCode;
	}

	// ================= 7 ===================
	public void setText(String s_Text) {
		sText = s_Text;
	}

	public String getText() {
		return sText;
	}

	// ================= 8 ===================
	public void setHumidity(String s_Humidity) {
		sHumidity = s_Humidity;
	} // Set humidity

	public String getHumidity() {
		return sHumidity;
	} // Get humidity

	// ================= 9 ===================
	public void setVisibility(String s_Visibility) {
		sVisibility = s_Visibility;
	} // Set visibility

	public String getVisibility() {
		return sVisibility;
	} // Get visibility

	// ================= 10 ==================
	public void setSunset(String s_Sunset) {
		sSunset = s_Sunset;
	}
	
	public String getSunset() {
		return sSunset;
	}
	
	// ================== 11 =================
	public void setSunrise(String s_Sunrise) {
		sSunrise = s_Sunrise;
	}
	
	public String getSunrise() {
		return sSunrise;
	}
}
