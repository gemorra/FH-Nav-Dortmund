package FHNav.gui.helper;

import java.util.ArrayList;

import FHNav.gui.R;
import FHNav.model.Veranstaltung;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NormalListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Veranstaltung> items;
	
	public NormalListAdapter(Context ctx,ArrayList<Veranstaltung> items)
	{
		mInflater = LayoutInflater.from(ctx);
		this.items = items;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row, null);

			holder = new ViewHolder();
			holder.bottomtext = (TextView) convertView
					.findViewById(R.id.normal_row_bottomtext);
			holder.toptext = (TextView) convertView.findViewById(R.id.normal_row_toptext);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Veranstaltung ve = items.get(position);
		holder.bottomtext.setText(ve.getRaum());
		holder.toptext.setText(ve.getName());

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
