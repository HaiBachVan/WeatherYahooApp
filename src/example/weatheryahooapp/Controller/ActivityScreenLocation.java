package example.weatheryahooapp.Controller;

import example.weatheryahooapp.Model.WeatherPreferences;
import example.weatheryahooapp.Model.YahooWeatherDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityScreenLocation extends Activity implements OnClickListener {
	/** For debugging */
	private static final String TAG = "ActivityScreenLocation";

	/** Querry to get location */
	private static final String GET_LOCATION_WOEID = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.places%20where%20text%3D%22";	
	
	/** Location error*/
	private static final int LOCATION_ERROR = -1;
	
	/** Locattion OK */
	private static final int LOCATION_OK = 0;
	
	/** NO WOEID */
	private static final int LOCATION_NOWOEID = 1;
	
	/** Get data failed */
	private static final int LOCATION_GET_FAILED = 2;
	
	/** Request get location */
	private static final int REG_GET_LOCATION_SATRT = 100;
	
	/** Request get location finish */
	private static final int REG_GET_LOCATION_FINISH = 101;
	
	/** Request searching */
	private static final int REG_GET_LOCATION_SEARCHING = 102;
	
	/** Search button */
	private Button m_btnSearch;
	
	/** Input country */
	private EditText m_Country;
	
	/** Input city */
	//private EditText m_City;
	
	/** Data model */
	private YahooWeatherDatabase m_DataModel;
	
	/** Preference */
	private WeatherPreferences m_Preference;
	
	/** Dialog */
	ProgressDialog m_Dialog;
	
	
	/** Handle request */
	Handler m_HandleRequest;
	
	/** Request result */
	private int m_RequestResult = LOCATION_ERROR;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_location);

		boolean bResult = false;
		bResult = initializeData();
		if (bResult == true) {
			initializeView();
		}

		if (bResult == false) {
			Log.e(TAG,"onCreate Error");
		}
		
		/* Draw screen */
		drawScreen();
	}


	private boolean initializeView() {
		boolean bResult = true;

		m_btnSearch = (Button) findViewById(R.id.btn_search);
		m_Country = (EditText) findViewById(R.id.input_country);
		//m_City = (EditText) findViewById(R.id.inputCity);

		if ( (m_btnSearch == null) || (m_Country == null)){
			Log.e(TAG,"initialize view error");
			bResult = false;
		}
		
		/* Regist handle click */
		m_btnSearch.setOnClickListener(this);

		/* Regist handle */
		initializeHandleRequest();
		
		return bResult;
	}
	
	
	private void initializeHandleRequest(){
	    /* Setting up handler for ProgressBar */
		m_HandleRequest = new Handler(){
			@Override
			public void handleMessage(Message message) {
				int nRequest = message.what;
				
				switch(nRequest){
				case REG_GET_LOCATION_SATRT:
					String strMsg = getString(R.string.strOnSearching);	
					m_Dialog = ProgressDialog.show(ActivityScreenLocation.this, "", strMsg, true);
					
					Message msgRegSearch = new Message();
					msgRegSearch.what = REG_GET_LOCATION_SEARCHING;
					sendMessage(msgRegSearch);
					break;
					
				case REG_GET_LOCATION_SEARCHING:
					m_RequestResult = getDataByLocatition();
					Message msgFinish = new Message();
					msgFinish.what = REG_GET_LOCATION_FINISH;
					sendMessage(msgFinish);					
					break;
					
				case REG_GET_LOCATION_FINISH:
					m_Dialog.dismiss();
					getLocationFinish(m_RequestResult);
					 break;
					 
					 default:
						 Log.e(TAG,"Can not handle this message");
						 break;
				}
			}
        };		
	}


	private boolean initializeData() {
		boolean bResult = true;
		
		m_DataModel = YahooWeatherDatabase.getInstance();
		if (m_DataModel == null){
			Log.e(TAG,"Init data failed");
			return false;
		}
		
		m_Preference = WeatherPreferences.getInstance(getApplicationContext());
		if (m_Preference == null){
			Log.e(TAG,"Can not get preference");
			return false;
		}

		return bResult;
	}


	private void drawScreen() {
		drawTitle();

	}


	private void drawTitle() {
	    setTitle(R.string.app_name);
	}
	

	private int getDataByLocatition(){
		String strLocationQuerry = createQueryByCityCountry();
		if (strLocationQuerry == null){
			Log.e(TAG,"Querry invalid");
			return LOCATION_ERROR;
		}
		
		String strWOEID = m_DataModel.getWOEIDByLocation(strLocationQuerry);
		if (strWOEID != null){
			m_Preference.setLocation(strWOEID);
			return LOCATION_OK;
		}
		
		return LOCATION_ERROR;
	}
	
	
	private String createQueryByCityCountry(){
		StringBuffer strQuerryBuf = new StringBuffer();
		
		strQuerryBuf.append(m_Country.getText().toString().trim());

		String strQuerryWOEID =  strQuerryBuf.toString().trim();
		if (strQuerryWOEID != null){
			strQuerryWOEID = strQuerryWOEID.replace(" ", "%20");
		}
		
		return strQuerryWOEID;
	}
	
	
	public static String createQuerryGetWoeid(String strQuerry){
		if (strQuerry == null){
			return null;
		}
		
		StringBuffer strQuerryBuf = new StringBuffer(GET_LOCATION_WOEID);
		strQuerryBuf.append(strQuerry);
		strQuerryBuf.append("%22&format=xml");

		return strQuerryBuf.toString();				
	}



	public void onClick(View arg0) {
		/* Get woeid */
		getWoeIDByLocation();
	}
	

	private void getWoeIDByLocation(){
		Message regSearchLocation = new Message();
		regSearchLocation.what = REG_GET_LOCATION_SATRT;
		m_HandleRequest.sendMessage(regSearchLocation);
	}
	

	private void getLocationFinish(int nGetResult){
		Intent changeResult = new Intent();
		switch (nGetResult){
		case LOCATION_OK:
			setResult(RESULT_OK, changeResult);
			break;
			
		case LOCATION_ERROR:
		case LOCATION_GET_FAILED:
		case LOCATION_NOWOEID:
			setResult(RESULT_CANCELED, changeResult);
			default:
		}

		finish();		
	}
}
