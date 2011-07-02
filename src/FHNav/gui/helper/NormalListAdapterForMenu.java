package FHNav.gui.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import FHNav.controller.SettingsManager;
import FHNav.gui.R;
import FHNav.model.CanteenMenu;
import FHNav.model.Veranstaltung;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NormalListAdapterForMenu extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<CanteenMenu> items;
	String pattern = "HH:mm";
	SimpleDateFormat sdf = new SimpleDateFormat();
	
	
	public NormalListAdapterForMenu(Context ctx,ArrayList<CanteenMenu> items)
	{
		mInflater = LayoutInflater.from(ctx);
		this.items = items;
		sdf.applyPattern(pattern);
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row2, null);

			holder = new ViewHolder();
//			holder.bottomtext = (TextView) convertView
//					.findViewById(R.id.normal_row_bottomtext);
			
			
			holder.toptext = (TextView) convertView.findViewById(R.id.normal_row_toptext);
			
			LayoutParams paramsTop = holder.toptext.getLayoutParams();
//			LayoutParams paramsBottom = holder.bottomtext.getLayoutParams();
			
			if(SettingsManager.getText_size()==1)
			{
//				holder.bottomtext.setTextSize(0);
				holder.toptext.setTextSize(15);
//				paramsBottom.height = 0;
				paramsTop.height = 65;
			}
			else if(SettingsManager.getText_size()==2)
			{
//				holder.bottomtext.setTextSize(0);
				holder.toptext.setTextSize(20);
//				paramsBottom.height = 0;
				paramsTop.height = 80;
			}
			else if(SettingsManager.getText_size()==0)
			{
//				holder.bottomtext.setTextSize(0);
				holder.toptext.setTextSize(10);
//				paramsBottom.height = 0;
				paramsTop.height = 50;
			}
			holder.toptext.setLayoutParams(paramsTop);
//			holder.bottomtext.setLayoutParams(paramsBottom);
			
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		CanteenMenu cm = items.get(position);
		
		String topText = cm.getDesc();

		
		String bottomText = cm.getTitle();
		
//		holder.bottomtext.setText(bottomText);
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
	
	public ArrayList<CanteenMenu> getItems() {
		return items;
	}


	public void setItems(ArrayList<CanteenMenu> items) {
		this.items = items;
	}

	static class ViewHolder {
//		TextView bottomtext;
		TextView toptext;
	}
}
