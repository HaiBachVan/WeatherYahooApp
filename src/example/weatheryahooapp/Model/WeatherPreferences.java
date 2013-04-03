package example.weatheryahooapp.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class WeatherPreferences {
	// For debugging
	private static final String TAG = "WeatherPreferences";
	// Preferences name
	private static final String WEATHER_PREFERENCE = "weather_preference";

	// Key user agreement
	private static final String KEY_AGREEMENT = "weather_agreement";

	/** Key for location */
	private static final String KEY_LOCATION = "weather_location";

	/** Key for format temp */
	private static final String KEY_TIME_UPDATE = "time_update";

	private static final String KEY_TEMP_FMT = "temp_fmt";

	/** Default location */
	private static final String DEFAULT_LOCATION = "1236594";

	/** Instance */
	private static WeatherPreferences m_Instance;

	/** Context application */
	private Context m_Context;

	private WeatherPreferences(Context context) {
		this.m_Context = context;
	}

	public synchronized static WeatherPreferences getInstance(Context context) {
		if (m_Instance == null) {
			m_Instance = new WeatherPreferences(context);
		}

		return m_Instance;
	}

	public void setAccpetAgreement(boolean bIsAccept) {
		SharedPreferences preferences = m_Context.getSharedPreferences(
				WEATHER_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor settingEditor = preferences.edit();
		settingEditor.putBoolean(KEY_AGREEMENT, bIsAccept);
		settingEditor.commit();
	}

	public boolean getAcceptAgreement() {
		/* Get value of setting from preference */
		SharedPreferences preferences = m_Context.getSharedPreferences(
				WEATHER_PREFERENCE, Context.MODE_PRIVATE);

		return preferences.getBoolean(KEY_AGREEMENT, false);
	}

	public void setTimeUpdate(int nTime) {
		SharedPreferences preferences = m_Context.getSharedPreferences(
				WEATHER_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor settingEditor = preferences.edit();
		settingEditor.putInt(KEY_TIME_UPDATE, nTime);
		settingEditor.commit();
	}

	public int getTimeUpdate() {
		/* Get value of setting from preference */
		SharedPreferences preferences = m_Context.getSharedPreferences(
				WEATHER_PREFERENCE, Context.MODE_PRIVATE);

		return preferences.getInt(KEY_TIME_UPDATE, 5);
	}

	public void setTempFmt(boolean bIsAccept) {
		SharedPreferences preferences = m_Context.getSharedPreferences(
				WEATHER_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor settingEditor = preferences.edit();
		settingEditor.putBoolean(KEY_TEMP_FMT, bIsAccept);
		settingEditor.commit();
	}

	public boolean getTempFmt() {
		/* Get value of setting from preference */
		SharedPreferences preferences = m_Context.getSharedPreferences(
				WEATHER_PREFERENCE, Context.MODE_PRIVATE);

		return preferences.getBoolean(KEY_TEMP_FMT, true);
	}

	public String getLocation() {
		return _getStringPreferences(KEY_LOCATION, DEFAULT_LOCATION);
	}

	public boolean setLocation(String strLocation) {
		return _setStringPreferences(KEY_LOCATION, strLocation);
	}

	private String _getStringPreferences(String strKey, String strDefaultValue) {
		/* Verify input */
		if (strKey == null) {
			Log.e(TAG, "Invalid input parameter");
			return strDefaultValue;
		}

		/* Get value of setting from preference */
		SharedPreferences preferences = m_Context.getSharedPreferences(
				WEATHER_PREFERENCE, Context.MODE_PRIVATE);

		return preferences.getString(strKey, strDefaultValue);
	}

	private boolean _setStringPreferences(String strKey, String strValue) {
		/* Verify input parameter */
		if ((strKey == null) || (strValue == null)) {
			Log.e(TAG, "Invalid input parameter");
			return false;
		}

		SharedPreferences preferences = m_Context.getSharedPreferences(
				WEATHER_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor settingEditor = preferences.edit();
		settingEditor.putString(strKey, strValue);
		boolean bResult = settingEditor.commit();

		return bResult;
	}
}
