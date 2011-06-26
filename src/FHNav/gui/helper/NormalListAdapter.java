package FHNav.gui.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import FHNav.controller.SettingsManager;
import FHNav.gui.R;
import FHNav.model.Veranstaltung;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NormalListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Veranstaltung> items;
	String pattern = "HH:mm";
	SimpleDateFormat sdf = new SimpleDateFormat();
	
	
	public NormalListAdapter(Context ctx,ArrayList<Veranstaltung> items)
	{
		mInflater = LayoutInflater.from(ctx);
		this.items = items;
		sdf.applyPattern(pattern);
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row, null);

			holder = new ViewHolder();
			holder.bottomtext = (TextView) convertView
					.findViewById(R.id.normal_row_bottomtext);
			
			
			holder.toptext = (TextView) convertView.findViewById(R.id.normal_row_toptext);
			
			LayoutParams paramsTop = holder.toptext.getLayoutParams();
			LayoutParams paramsBottom = holder.bottomtext.getLayoutParams();
			
			if(SettingsManager.getText_size()==1)
			{
				Log.e("asd", "1");
				holder.bottomtext.setTextSize(15);
				holder.toptext.setTextSize(15);
				paramsBottom.height = 25;
				paramsTop.height = 40;
			}
			else if(SettingsManager.getText_size()==2)
			{
				Log.e("asd", "2");
				holder.bottomtext.setTextSize(20);
				holder.toptext.setTextSize(20);
				paramsBottom.height = 30;
				paramsTop.height = 50;
			}
			else if(SettingsManager.getText_size()==0)
			{
				holder.bottomtext.setTextSize(10);
				holder.toptext.setTextSize(10);
				paramsBottom.height = 20;
				paramsTop.height = 30;
			}
			holder.toptext.setLayoutParams(paramsTop);
			holder.bottomtext.setLayoutParams(paramsBottom);
			
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Veranstaltung ve = items.get(position);
		
		String topText = ve.getName();
		if(SettingsManager.isLecture_details_type())
		if(ve.getType().length()>0)
			topText += " ["+ve.getType()+"]";
		
		String bottomText = sdf.format(ve.getStartTime()) + "-"+ sdf.format(ve.getEndTime()) + " "+ ve.getRaum();
		
		if(SettingsManager.isLecture_details_groupletter())
		bottomText += " (" + ve.getStudentSet() + ")";
		if(SettingsManager.isLecture_details_lecturer())
			bottomText += " " + ve.getDozent();
		holder.bottomtext.setText(bottomText);
		holder.toptext.setText(topText);

		return convertView;
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


	public void setItems(ArrayList<Veranstaltung> items) {
		this.items = items;
	}

	static class ViewHolder {
		TextView bottomtext;
		TextView toptext;
	}
}
