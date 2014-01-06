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
import android.widget.CheckBox;
import android.widget.TextView;

public class ExtendedListAdapter extends BaseAdapter {
	static class ViewHolder {
		TextView bottomtext;
		TextView toptext;
		CheckBox checkbox;
	}

	private LayoutInflater mInflater;
	ArrayList<Veranstaltung> items;
	ArrayList<Boolean> checked;
	String pattern = "HH:mm";
	SimpleDateFormat sdf = new SimpleDateFormat();
	ArrayList<Veranstaltung> itemsToManipulate = new ArrayList<Veranstaltung>();

	Context ctx;

	public ExtendedListAdapter(Context context, ArrayList<Veranstaltung> items) {
		mInflater = LayoutInflater.from(context);
		ctx = context;
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

	public ArrayList<Boolean> getChecked() {
		return checked;
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public ArrayList<Veranstaltung> getItems() {
		return items;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.editagendarow, null);

			holder = new ViewHolder();
			holder.bottomtext = (TextView) convertView
					.findViewById(R.id.extenden_row_bottomtext);
			holder.toptext = (TextView) convertView
					.findViewById(R.id.extenden_row_toptext);
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.extenden_row_checkbox);

			if (SettingsManager.getText_size(ctx) == 1) {
				Log.e("asd", "1");
				holder.bottomtext.setTextSize(15);
				holder.toptext.setTextSize(15);
				// paramsBottom.height = LayoutParams.WRAP_CONTENT;
				// paramsTop.height = 40;
			} else if (SettingsManager.getText_size(ctx) == 2) {
				Log.e("asd", "2");
				holder.bottomtext.setTextSize(20);
				holder.toptext.setTextSize(20);
				// paramsBottom.height = 30;
				// paramsTop.height = 50;
			} else if (SettingsManager.getText_size(ctx) == 0) {
				holder.bottomtext.setTextSize(10);
				holder.toptext.setTextSize(10);
				// paramsBottom.height = 20;
				// paramsTop.height = 30;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final int pos = position;
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
		LayoutParams paramsTop = holder.toptext.getLayoutParams();
		paramsTop.height = 0;
		LayoutParams paramsBottom = holder.bottomtext.getLayoutParams();
		paramsBottom.height = 0;

		holder.toptext.setLayoutParams(paramsTop);
		holder.bottomtext.setLayoutParams(paramsBottom);

		LayoutParams paramsCheckbox = holder.checkbox.getLayoutParams();
		paramsCheckbox.height = LayoutParams.FILL_PARENT;
		holder.checkbox.setLayoutParams(paramsCheckbox);
		return convertView;
	}

	@SuppressWarnings("unused")
	public void refresh() {
		checked = new ArrayList<Boolean>();

		for (Veranstaltung v : items) {
			checked.add(false);
		}
	}

	@SuppressWarnings("unused")
	public void selectAll() {
		checked.clear();
		for (Veranstaltung v : items) {
			checked.add(true);
		}

		notifyDataSetChanged();
	}

	public void setChecked(ArrayList<Boolean> checked) {
		this.checked = checked;
	}

	public void setItems(ArrayList<Veranstaltung> items) {
		this.items = items;
	}
}
