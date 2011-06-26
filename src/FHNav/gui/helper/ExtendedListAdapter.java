package FHNav.gui.helper;

/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Demonstrates how to write an efficient list adapter. The adapter used in this
 * example binds to an ImageView and to a TextView for each row in the list.
 * 
 * To work efficiently the adapter implemented here uses two techniques: - It
 * reuses the convertView passed to getView() to avoid inflating View when it is
 * not necessary - It uses the ViewHolder pattern to avoid calling
 * findViewById() when it is not necessary
 * 
 * The ViewHolder pattern consists in storing a data structure in the tag of the
 * view returned by getView(). This data structures contains references to the
 * views we want to bind data to, thus avoiding calls to findViewById() every
 * time getView() is invoked.
 */

public class ExtendedListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<Veranstaltung> items;
	ArrayList<Boolean> checked;
	String pattern = "HH:mm";
	SimpleDateFormat sdf = new SimpleDateFormat();
	ArrayList<Veranstaltung> itemsToManipulate = new ArrayList<Veranstaltung>();


	public ExtendedListAdapter(Context context, ArrayList<Veranstaltung> items) {

		mInflater = LayoutInflater.from(context);
		this.items = items;
		sdf.applyPattern(pattern);

	}

	@SuppressWarnings("unused")
	public void deselectAll() {
		checked.clear();
		for (Veranstaltung v : items) {
			checked.add(false);
		}

		notifyDataSetChanged();
	}

	@SuppressWarnings("unused")
	public void selectAll() {
		checked.clear();
		for (Veranstaltung v : items) {
			checked.add(true);
		}

		notifyDataSetChanged();
	}

	/**
	 * Make a view to hold each row.
	 * 
	 * @see android.widget.ListAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		// When convertView is not null, we can reuse it directly, there is no
		// need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.editagendarow, null);

			holder = new ViewHolder();
			holder.bottomtext = (TextView) convertView.findViewById(R.id.extenden_row_bottomtext);
			holder.toptext = (TextView) convertView.findViewById(R.id.extenden_row_toptext);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.extenden_row_checkbox);
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

		final int pos = position;
		Veranstaltung ve = items.get(position);
		// Bind the data efficiently with the holder.
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
		holder.checkbox.setChecked(checked.get(position));
		// Für Click auf Box
		holder.checkbox.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Log.e("", "klickBox");
				boolean tmp = checked.get(pos);
				checked.set(pos, !tmp);
				holder.checkbox.setChecked(!tmp);
			}
		});
		// Für Click in Zeile
		convertView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Log.e("", "klick");
				boolean tmp = checked.get(pos);
				checked.set(pos, !tmp);
				holder.checkbox.setChecked(!tmp);
			}
		});

		return convertView;
	}

	/**
	 * The number of items in the list is determined by the number of speeches
	 * in our array.
	 * 
	 * @see android.widget.ListAdapter#getCount()
	 */
	public int getCount() {
		return items.size();
	}

	/**
	 * Since the data comes from an array, just returning the index is sufficent
	 * to get at the data. If we were using a more complex data structure, we
	 * would return whatever object represents one row in the list.
	 * 
	 * @see android.widget.ListAdapter#getItem(int)
	 */
	public Object getItem(int position) {
		return position;
	}

	/**
	 * Use the array index as a unique id.
	 * 
	 * @see android.widget.ListAdapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}

	public ArrayList<Veranstaltung> getItems() {
		return items;
	}

	public void setItems(ArrayList<Veranstaltung> items) {
		this.items = items;
	}

	public ArrayList<Boolean> getChecked() {
		return checked;
	}

	public void setChecked(ArrayList<Boolean> checked) {
		this.checked = checked;
	}

	static class ViewHolder {
		TextView bottomtext;
		TextView toptext;
		CheckBox checkbox;
	}

	@SuppressWarnings("unused")
	public void refresh() {
		checked = new ArrayList<Boolean>();

		for (Veranstaltung v : items) {
			checked.add(false);
		}
	}
}
