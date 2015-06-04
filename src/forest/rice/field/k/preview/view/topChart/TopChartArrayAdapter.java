
package forest.rice.field.k.preview.view.topChart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.volley.VolleyManager;

public class TopChartArrayAdapter extends ArrayAdapter<Track> {
    private LayoutInflater layoutInflater_;

    private Tracks tracks = null;

    public TopChartArrayAdapter(Context context, Tracks tracks) {
        super(context, 0, tracks);

        layoutInflater_ = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.tracks = tracks;
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

        Track track = tracks.get(position);

        holder.name.setText(track.get(Track.trackName));
        holder.artist.setText(track.get(Track.artistName));

        VolleyManager manager = VolleyManager.getInstance(getContext());
        manager.imageGet(track.getLargestArtwork(), holder.image, android.R.drawable.ic_media_play,
                android.R.drawable.ic_media_play);

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView artist;
        ImageView image;
    }

}
