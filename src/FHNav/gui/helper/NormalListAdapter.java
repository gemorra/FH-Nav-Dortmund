package FHNav.gui.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import FHNav.controller.MainApplicationManager;
import FHNav.controller.SettingsManager;
import FHNav.gui.R;
import FHNav.model.Veranstaltung;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NormalListAdapter extends BaseAdapter {

	static class ViewHolder {
		TextView bottomtext;
		TextView toptext;
	}

	private LayoutInflater mInflater;
	private ArrayList<Veranstaltung> items;
	String pattern = "HH:mm";
	SimpleDateFormat sdf = new SimpleDateFormat();
	Context ctx;

	float res = MainApplicationManager.getDensity();
	private final static int small = 10;
	private final static int normal = 15;

	private final static int big = 20;

	public NormalListAdapter(Context ctx, ArrayList<Veranstaltung> items) {
		mInflater = LayoutInflater.from(ctx);
		this.ctx = ctx;
		this.items = items;
		sdf.applyPattern(pattern);
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public ArrayList<Veranstaltung> getItems() {
		return items;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row, null);

			holder = new ViewHolder();
			holder.bottomtext = (TextView) convertView
					.findViewById(R.id.normal_row_bottomtext);

			holder.toptext = (TextView) convertView
					.findViewById(R.id.normal_row_toptext);

			if (SettingsManager.getText_size(ctx) == 1) {
				holder.bottomtext.setTextSize(normal);
				holder.toptext.setTextSize(normal);

				// paramsBottom.height = 25;
				// paramsTop.height = 40;
			} else if (SettingsManager.getText_size(ctx) == 2) {
				holder.bottomtext.setTextSize(big);
				holder.toptext.setTextSize(big);
				// paramsBottom.height = 30;
				// paramsTop.height = 50;

			} else if (SettingsManager.getText_size(ctx) == 0) {
				holder.bottomtext.setTextSize(small);
				holder.toptext.setTextSize(small);
				// paramsBottom.height = 20;
				// paramsTop.height = 30;

			}
			// holder.toptext.setHeight(800);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Veranstaltung ve = items.get(position);

		String topText = ve.getName();
		if (SettingsManager.isLecture_details_type(ctx)) {
			if (ve.getType().length() > 0) {
				topText += " [" + ve.getType() + "]";
			}
		}

		String bottomText = sdf.format(ve.getStartTime()) + "-"
				+ sdf.format(ve.getEndTime()) + " " + ve.getRaum();

		if (SettingsManager.isLecture_details_groupletter(ctx)) {
			bottomText += " (" + ve.getStudentSet() + ")";
		}
		if (SettingsManager.isLecture_details_lecturer(ctx)) {
			bottomText += " " + ve.getDozent();
		}
		holder.bottomtext.setText(bottomText);
		holder.toptext.setText(topText);
		// int tb = holder.toptext.getMeasuredHeight();

		// int i = tb;
		// holder.bottomtext.invalidate();
		// int linecount = holder.toptext.getLineCount();
		// int height_in_pixel =
		// (int)(holder.toptext.getLineCount()*holder.toptext.getLineHeight()*MainApplicationManager.getDensity());
		//
		// // holder.bottomtext.setHeight(LayoutParams.WRAP_CONTENT);
		LayoutParams paramsTop = holder.toptext.getLayoutParams();
		paramsTop.height = 0;
		LayoutParams paramsBottom = holder.bottomtext.getLayoutParams();
		paramsBottom.height = 0;

		holder.toptext.setLayoutParams(paramsTop);
		holder.bottomtext.setLayoutParams(paramsBottom);
		return convertView;
	}

	public void setItems(ArrayList<Veranstaltung> items) {
		this.items = items;
	}
}
