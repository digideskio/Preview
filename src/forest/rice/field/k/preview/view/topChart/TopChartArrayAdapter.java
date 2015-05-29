package forest.rice.field.k.preview.view.topChart;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Item;
import forest.rice.field.k.preview.manager.MediaPlayerManager;
import forest.rice.field.k.preview.volley.VolleyManager;

public class TopChartArrayAdapter extends ArrayAdapter<Item> {
	private LayoutInflater layoutInflater_;

	List<Item> itemList = null;

	public TopChartArrayAdapter(Context context, List<Item> objects) {
		super(context, 0, objects);

		layoutInflater_ = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		itemList = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			convertView = layoutInflater_.inflate(
					R.layout.fragment_topchart_list, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.topchart_name);
			holder.artist = (TextView) convertView
					.findViewById(R.id.topchart_artist);
			holder.image = (ImageView) convertView
					.findViewById(R.id.topchart_image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Item item = itemList.get(position);

		holder.name.setText(item.name);
		holder.artist.setText(item.artist);
		
		VolleyManager manager = VolleyManager.getInstance(getContext());
		manager.imageGet(item.image, holder.image, R.drawable.ic_launcher, R.drawable.ic_launcher);
		
		
		holder.image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MediaPlayerManager manager = MediaPlayerManager.getInstance();
					try {
						manager.setDataSourceAndPlay(getContext(), item.previewUrl);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
		
		return convertView;
	}

	private class ViewHolder {
		TextView name;
		TextView artist;
		ImageView image;
	}

}
