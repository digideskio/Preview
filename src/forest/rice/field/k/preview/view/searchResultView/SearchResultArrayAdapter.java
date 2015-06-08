
package forest.rice.field.k.preview.view.searchResultView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.volley.VolleyManager;

public class SearchResultArrayAdapter extends ArrayAdapter<Track> {
    private LayoutInflater layoutInflater_;

    private Tracks item;
    private Context context;

    public SearchResultArrayAdapter(Context context, int resource,
            Tracks searchResultItem) {
        super(context, 0, searchResultItem);

        this.context = context;

        layoutInflater_ = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        item = searchResultItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater_.inflate(
                    R.layout.fragment_searchresult_list, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView
                    .findViewById(R.id.searchresult_name);
            holder.artist = (TextView) convertView
                    .findViewById(R.id.searchresult_artist);
            holder.image = (ImageView) convertView
                    .findViewById(R.id.searchresult_image);
            holder.link = (ImageButton) convertView.findViewById(R.id.searchresult_link);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Track track = item.get(position);

        holder.name.setText(track.get("trackName"));
        holder.artist.setText(track.get("artistName"));

        holder.link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(track
                        .get(Track.trackViewUrl));
                if (uri != null && uri.getHost() != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            }

        });

        VolleyManager manager = VolleyManager.getInstance(getContext());
        manager.imageGet(track.getLargestArtwork(), holder.image,
                android.R.drawable.ic_media_play,
                android.R.drawable.ic_media_play);

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView artist;
        ImageView image;
        ImageButton link;
    }
}
