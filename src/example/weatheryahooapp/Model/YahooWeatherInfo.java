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

	// Wind Speed
	private String sWindSpeed;

	// Temperature nextday
	private String sLowTemp;
	private String sMaxTemp;

	// Code nextday
	private String sCodeDay;

	// Day
	private String sNextDay;

	public YahooWeatherInfo() {
		this(DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA,
				DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA,
				DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA,
				DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA);
	}

	public YahooWeatherInfo(String strCity, String strCountry, String strTemp,
			String strHum, String strWindSpeed, String strText, String strCode,
			String strDate, String strTempUnit, String strVisi,
			String strSunset, String strSunrise, String strLowTemp, String strMaxTemp,
			String strDay, String strCodeDay) {
		// TODO Auto-generated constructor stub
		sCity = strCity;
		sCountry = strCountry;
		sTemperature = strTemp;
		sHumidity = strHum;
		sWindSpeed = strWindSpeed;
		sText = strText;
		sCode = strCode;
		sDate = strDate;
		sTemperatureUnits = strTempUnit;
		sVisibility = strVisi;
		sSunset = strSunset;
		sSunrise = strSunrise;
		
		sLowTemp = strLowTemp;
		sMaxTemp = strMaxTemp;
		sNextDay = strDay;
		sCodeDay = strCodeDay;
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

	// ================== 12 =================
	public void setWindSpeed(String s_WindSpeed) {
		sWindSpeed = s_WindSpeed;
	}

	public String getWindSpeed() {
		return sWindSpeed;
	}

	// ================== 13 =================
	public void setNextDay(String s_NextDay) {
		sNextDay = s_NextDay;
	}

	public String getNextDay() {
		return sNextDay;
	}

	// ================== 14 =================
	public void setLowTemp(String s_LowTemp) {
		sLowTemp = s_LowTemp;
	}

	public String getLowTemp() {
		return sLowTemp;
	}

	// ================== 15 =================
	public void setMaxTemp(String s_MaxTemp) {
		sMaxTemp = s_MaxTemp;
	}

	public String getMaxTemp() {
		return sMaxTemp;
	}

	// ================== 16 =================
	public void setCodeDaY(String s_CodeDay) {
		sCodeDay = s_CodeDay;
	}

	public String getCodeDay() {
		return sCodeDay;
	}
}
