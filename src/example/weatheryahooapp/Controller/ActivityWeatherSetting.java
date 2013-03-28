package example.weatheryahooapp.Controller;

import java.util.ArrayList;
import java.util.List;

import example.weatheryahooapp.Model.WeatherPreferences;
import example.weatheryahooapp.Model.YahooWeatherAPIs;
import example.weatheryahooapp.Model.YahooWeatherDatabase;
import example.weatheryahooapp.Model.YahooWeatherInfo;
import example.weatheryahooapp.View.ContextMenuAdapter;
import example.weatheryahooapp.View.ContextMenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityWeatherSetting extends Activity {

	/** TAG, for debugging */
	private static final String TAG = "Activity Weather Setting";

	/** Dialog type */
	private static final int DIALOG_TYPE_USER_AGREEMENT = 1;

	/** Change location */
	private static final int REG_CHANGELOCATION = 1;

	/** Request get location */
	private static final int REG_GET_WEATHER_START = 100;

	/** Request get location finish */
	private static final int REG_GET_WEATHER_FINISH = 101;

	/** Frequency update */
	private static final int ONE_MINUTE = 60 * 1000;

	/** Context menu */
	private static final int MENU_CONTEXT_0 = 0;

	/** Context menu */
	private static final int MENU_CONTEXT_1 = 1;

	/** Context menu */
	private static final int MENU_CONTEXT_2 = 2;

	/** Item 1 */
	private static final int SELECT_ITEM_1 = 0;

	/** Item 2 */
	private static final int SELECT_ITEM_2 = 1;

	/** Item 3 */
	private static final int SELECT_ITEM_3 = 2;

	/** Weather infomation */
	private YahooWeatherInfo m_WeatherInfo;

	/** Weather setting */
	private WeatherPreferences m_Preferneces;

	/** Model data */
	private YahooWeatherDatabase m_DataModel;

	/** Location */
	private TextView m_TextLocation;

	/** Temperature */
	private TextView m_Temperature;

	/** Humimidy */
	private TextView m_Humimidy;

	/** State */
	private TextView m_Visibility;

	/** Sunset */
	private TextView m_Sunset;

	/** Sunrise */
	private TextView m_Sunrise;

	/** Time */
	private TextView m_Date;

	/** Icon */
	private ImageView m_WeatherIcon;

	/** Handle request */
	Handler m_HandleRequest;

	/** Dialog */
	// ProgressDialog m_ProgressDialog;

	/** Dialog */
	AlertDialog m_Dialog;

	/** Runable */
	Runnable m_Runnable;

	/** For adapter of dialog */
	private ContextMenuAdapter m_contextAdapter;

	/** Dialog */
	AlertDialog m_Alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_setting);

		boolean bResult = initializeData();

		if (bResult != false) {
			/* Initialize view */
			bResult = initializeView();
		}

		if (bResult == false) {
			Log.e(TAG, "Init data failed");
			/* Add notify here and quit app */
			finish();
			return;
		}

		if (m_Preferneces.getAcceptAgreement() == false) {
			showDialog(DIALOG_TYPE_USER_AGREEMENT);
		} else {
			/* Draw screen */
			drawWeatherScreen();
			selectWeatherSetting();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REG_CHANGELOCATION:
			updateDataOfCurrentLocation();
			break;

		default:
			Log.w(TAG, "Not handle request code:" + requestCode);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void updateDataOfCurrentLocation() {
		requestUpdateWeather();
	}

	private void requestUpdateWeather() {
		Message msgFetchData = new Message();
		msgFetchData.what = REG_GET_WEATHER_START;
		m_HandleRequest.sendMessage(msgFetchData);
	}

	private void initializeHandleRequest() {
		m_Runnable = new Runnable() {

			@Override
			public void run() {
				requestUpdateWeather();
			}
		};

		/* Setting up handler for ProgressBar */
		m_HandleRequest = new Handler() {
			@Override
			public void handleMessage(Message message) {
				int nRequest = message.what;

				switch (nRequest) {
				case REG_GET_WEATHER_START:
					String strWOEID = m_Preferneces.getLocation();
					if (strWOEID == null) {
						Log.e(TAG, "Can not get WOEID");
						// m_ProgressDialog.dismiss();
						displayNotifyCation(R.string.strFetchFailed);
						return;
					} else {
						/* Get weather information */
						m_WeatherInfo = m_DataModel.getWeatherData(strWOEID);
					}

					Message msgRegSearch = new Message();
					msgRegSearch.what = REG_GET_WEATHER_FINISH;
					sendMessage(msgRegSearch);
					break;

				case REG_GET_WEATHER_FINISH:
					if (m_WeatherInfo != null) {
						updateWeatherInfo(m_WeatherInfo);
						notifyUpdateTime();
					}
					// m_ProgressDialog.dismiss();
					m_HandleRequest.postDelayed(m_Runnable,
							(ONE_MINUTE * m_Preferneces.getTimeUpdate()));
					break;

				default:
					Log.e(TAG, "Can not handle this message");
					break;
				}
			}
		};
	}

	private void notifyUpdateTime() {
		Intent intentSettingUpdate = new Intent(WidgetWeather.UPDATE_WEATHER);
		this.sendBroadcast(intentSettingUpdate);
	}

	private void displayNotifyCation(int nResID) {
		Toast.makeText(getApplicationContext(), getString(nResID),
				Toast.LENGTH_LONG).show();
	}

	private void selectSetting() {
		Intent intent = new Intent(ActivityWeatherSetting.this,
				ActivityScreenLocation.class);
		startActivityForResult(intent, REG_CHANGELOCATION);
	}

	private void selectTimeIntervalUpdating() {
		final CharSequence[] items = { "30 minutes", "3 hours", "12 hours" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.selectTimeUpdate);
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						/* Check to update setting */
						int nTime = 30;
						switch (item) {
						case SELECT_ITEM_1:
							nTime = 30;
							break;
						case SELECT_ITEM_2:
							nTime = 180;
						case SELECT_ITEM_3:
							nTime = 720;
							break;

						default:
							break;
						}

						m_Preferneces.setTimeUpdate(nTime);
						m_Alert.dismiss();
						updateWeatherInfo(m_WeatherInfo);
					}
				});

		m_Alert = builder.create();
		m_Alert.show();
	}

	private void selectTempFormat() {
		final CharSequence[] items = { "Celsius", "Fahrenheit" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.selectTemperatureUnit);
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						/* Check to update setting */
						boolean bIsC = true;
						switch (item) {
						case SELECT_ITEM_1:
							bIsC = true;
							break;
						case SELECT_ITEM_2:
							bIsC = false;

						default:
							break;
						}

						m_Preferneces.setTempFmt(bIsC);
						m_Alert.dismiss();
						notifyUpdateTime();
						updateWeatherInfo(m_WeatherInfo);
					}
				});
		m_Alert = builder.create();
		m_Alert.show();
	}

	private boolean initializeView() {
		m_TextLocation = (TextView) findViewById(R.id.location);
		m_Temperature = (TextView) findViewById(R.id.temperature);
		m_Humimidy = (TextView) findViewById(R.id.humidityValue);
		m_Visibility = (TextView) findViewById(R.id.visiValue);
		m_WeatherIcon = (ImageView) findViewById(R.id.weather_icon);
		m_Date = (TextView) findViewById(R.id.dateTime);
		m_Sunset = (TextView) findViewById(R.id.tv_sunset);
		m_Sunrise = (TextView) findViewById(R.id.tv_sunrise);

		if ((m_TextLocation == null) || (m_Temperature == null)
				|| (m_Humimidy == null) || (m_WeatherIcon == null)
				|| (m_Visibility == null) || (m_Date == null)
				|| (m_Sunset == null) || (m_Sunrise == null)) {
			Log.e(TAG, "View init failed");
			return false;
		}

		return true;
	}

	private boolean initializeData() {
		/* Get application context */
		Context appContext = this.getApplicationContext();

		/* Get preference instance */
		m_Preferneces = WeatherPreferences.getInstance(appContext);
		if (m_Preferneces == null) {
			Log.e(TAG, "Get preference instance failed, check please");
			return false;
		}

		/* Get instance of data model */
		m_DataModel = YahooWeatherDatabase.getInstance();
		if (m_DataModel == null) {
			Log.e(TAG, "Can not get data model");
			return false;
		}

		initializeHandleRequest();

		return true;
	}

	private void drawWeatherScreen() {
		drawTitle();

		/* Request update */
		updateDataOfCurrentLocation();
	}

	private void updateWeatherInfo(YahooWeatherInfo weatherInfo) {
		if (weatherInfo == null) {
			Log.e(TAG, "Weather is null");
			return;
		}

		String strCode = weatherInfo.getCode();
		int nCode = getImageByCode(strCode);
		m_WeatherIcon.setImageResource(nCode);

		boolean bIsC = m_Preferneces.getTempFmt();

		String strFmt;
		String strTemp = weatherInfo
				.getTemperature(YahooWeatherInfo.TEMPERATURE_C);
		if (bIsC == true) {
			strFmt = getString(R.string.str_temperature_fmt);
		} else {
			strFmt = getString(R.string.str_temperature_fmt_f);
			strTemp = YahooWeatherDatabase.convertC2F(strTemp);
		}

		String strTemperature = String.format(strFmt, strTemp);

		m_TextLocation.setText(weatherInfo.getCity());
		m_Temperature.setText(strTemperature);
		m_Date.setText(weatherInfo.getDate());

		// Get info about atmosphere
		strFmt = getString(R.string.str_humidity_fmt);
		String strHumidity = String.format(strFmt, weatherInfo.getHumidity());
		m_Humimidy.setText(strHumidity);
		strFmt = getString(R.string.str_visi_fmt);
		String strVisi = String.format(strFmt, weatherInfo.getVisibility());
		m_Visibility.setText(strVisi);
		
		// Get info about astronomy
		m_Sunset.setText(weatherInfo.getSunset());
		m_Sunrise.setText(weatherInfo.getSunrise());
	}

	private int getImageByCode(String strCode) {
		int nImageCode = R.drawable.a0;

		if (strCode == null) {
			Log.e(TAG, "Code is null");
			return nImageCode;
		}

		int nCode = Integer.parseInt(strCode);

		int nNumber = YahooWeatherAPIs.arrImage.length;
		for (int i = 0; i < nNumber; i++) {
			if (nCode == YahooWeatherAPIs.arrImage[i][1]) {
				return YahooWeatherAPIs.arrImage[i][0];
			}
		}
		return nImageCode;
	}

	private void drawTitle() {
		setTitle(R.string.strSettingTitle);
	}

	private AlertDialog createContextMenuSetting(Context context) {
		/* Crate menu list */
		AlertDialog dialogMenu = null;
		List<ContextMenuItem> arrMenuItem = null;
		AlertDialog.Builder contextMenu = new AlertDialog.Builder(context);

		/* Create menu item of context menu */
		arrMenuItem = _createContextMenuList();
		if (arrMenuItem == null) {
			Log.e(TAG, "Can note create dialog item");
			return null;
		}

		this.m_contextAdapter = new ContextMenuAdapter(context, 0, arrMenuItem);
		contextMenu.setAdapter(m_contextAdapter, new HandleSelectContextMenu());
		contextMenu.setInverseBackgroundForced(true);
		contextMenu.setTitle(R.string.title_context_menu_setting);
		contextMenu.setIcon(R.drawable.ic_context_menu);

		dialogMenu = contextMenu.create();
		dialogMenu.setCanceledOnTouchOutside(true);

		return dialogMenu;
	}

	private List<ContextMenuItem> _createContextMenuList() {
		ArrayList<ContextMenuItem> arrMenuItem = new ArrayList<ContextMenuItem>();

		/* Create first menu item base on menu state */
		ContextMenuItem itemContext1 = new ContextMenuItem(MENU_CONTEXT_0,
				R.string.context_menu_changeLocation, R.drawable.location_ic);

		ContextMenuItem itemContext2 = new ContextMenuItem(MENU_CONTEXT_1,
				R.string.context_menu_update_time, R.drawable.update_time);

		ContextMenuItem itemContext3 = new ContextMenuItem(MENU_CONTEXT_2,
				R.string.temperature_unit, R.drawable.temperature_ic);

		/* Add context item to list */
		arrMenuItem.add(itemContext1);
		arrMenuItem.add(itemContext2);
		arrMenuItem.add(itemContext3);
		return arrMenuItem;
	}

	private class HandleSelectContextMenu implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			switch (which) {
			case MENU_CONTEXT_0:
				selectSetting();
				break;

			case MENU_CONTEXT_1:
				selectTimeIntervalUpdating();
				break;

			case MENU_CONTEXT_2:
				selectTempFormat();
				break;

			default:
				Log.e(TAG, "Invalid context menu");
				break;
			}
		}
	}

	private void selectWeatherSetting() {
		m_Dialog = createContextMenuSetting(this);
		if (m_Dialog != null) {
			m_Dialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.activity_menu_setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.about_menu:
			Intent about = new Intent("example.weatheryahooapp.ABOUT");
			startActivity(about);
			break;
		case R.id.setting_menu:
			selectWeatherSetting();
			break;
		default:
			Log.e(TAG, "Why you don't handle this case");
			break;
		}
		return false;
	}

}
