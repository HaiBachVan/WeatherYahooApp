package example.weatheryahooapp.Controller;


import example.weatheryahooapp.Model.WeatherPreferences;
import example.weatheryahooapp.Model.YahooWeatherAPIs;
import example.weatheryahooapp.Model.YahooWeatherDatabase;
import example.weatheryahooapp.Model.YahooWeatherInfo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetWeather extends AppWidgetProvider {
	/** For debug */
	private static final String TAG = "WidgetWeather";
	
	/** Update weather */
	public static final String UPDATE_WEATHER = "example.weatheryahooapp.UPDATE_WEATHER";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	private void registerPendindIntent(Context context, RemoteViews remoteViews){
		Intent settingIntent = new Intent(context, ActivityWeatherSetting.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, settingIntent, 0);

		/* Set pending intent */
		remoteViews.setOnClickPendingIntent(R.id.weather_widget, pendingIntent);		
	}


	@Override
	public void onReceive(Context context, Intent intent) {

		super.onReceive(context, intent);

		/* Verify input parameter */
		if ((context == null) || (intent == null)){
			Log.e(TAG, "Invalid input parameter");
			return;
		}
				
		/* Get intent data */
		String intentAction = intent.getAction();
		if (intentAction == null){
			Log.e(TAG,"No Action");
			return;
		}
		
		/* Handler special action */
		if (intentAction.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE) 
				|| intentAction.equals(UPDATE_WEATHER)){
			/* When receive request update view failed */
			updateWeatherView(context);
		}
	}


	private void updateWeatherView(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.weather_widget);
		
    	String strWOEID = WeatherPreferences.getInstance(context).getLocation();
    	if (strWOEID == null){
    		Log.e(TAG,"Can not get WOEID");
    		return;
    	}
    	
	    /* Get weather information */
	    YahooWeatherInfo weatherInfo = YahooWeatherDatabase.getInstance().getWeatherData(strWOEID);
	    if (weatherInfo == null){
	    	Log.w(TAG,"Can not get weather info");
	    	return;
	    }
	    
    	boolean bIsC = WeatherPreferences.getInstance(context).getTempFmt();
    	
    	String strFmt;
    	String strTemp = weatherInfo.getTemperature(YahooWeatherInfo.TEMPERATURE_C);
    	if (bIsC == true){
    		strFmt = context.getString(R.string.str_temperature_fmt); 
    	} else {
    		strFmt = context.getString(R.string.str_temperature_fmt_f);
    		strTemp = YahooWeatherDatabase.convertC2F(strTemp);
    	}

    	String strTemperature = String.format(strFmt, strTemp);	
    	remoteViews.setTextViewText(R.id.tempe, strTemperature);
    	
    	String strCode = weatherInfo.getCode();
    	int nCode = getImageByCode(strCode);    	
	        
		remoteViews.setImageViewResource(R.id.img_ic, nCode);
		
		/* Set pending intent */
		registerPendindIntent(context, remoteViews);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		ComponentName component = new ComponentName(context, WidgetWeather.class);
		int[] arrWidgetID = appWidgetManager.getAppWidgetIds(component);		
		appWidgetManager.updateAppWidget(arrWidgetID, remoteViews);
	}
	
    private int getImageByCode(String strCode){
    	int nImageCode = R.drawable.a0;
    	
    	if (strCode == null){
    		Log.e(TAG,"Code is null");
    		return nImageCode;
    	}
    	
    	int nCode = Integer.parseInt(strCode);
    	
    	int nNumber= YahooWeatherAPIs.arrImage.length;
    	for (int i=0; i < nNumber; i++){
    		if (nCode == YahooWeatherAPIs.arrImage[i][1]){
    			return YahooWeatherAPIs.arrImage[i][0];
    		}
    	}
    	return nImageCode;
    }	
}
