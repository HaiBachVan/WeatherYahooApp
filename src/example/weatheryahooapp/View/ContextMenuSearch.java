package example.weatheryahooapp.View;

import java.util.List;

import example.weatheryahooapp.Controller.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ContextMenuSearch extends ArrayAdapter<ContextMenuItem> {
	/** For debugging */
	private static final String TAG = "ContextMenuAdapter";

	/** LayoutInflater to apply view for List */
	private final LayoutInflater inflater;

	public ContextMenuSearch(Context context, int MessagewResourceId,
			List<ContextMenuItem> objects) {
		super(context, MessagewResourceId, objects);

		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		view = inflater.inflate(R.layout.activity_search_location, null);
		if (view == null) {
			Log.e(TAG, "WTF, why view is null?");
		}

		return view;
	}
}