
package forest.rice.field.k.preview.view.topChart;

import android.os.Bundle;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.view.base.BaseListFragment;
import forest.rice.field.k.preview.view.topChart.TopChartAsyncTask.TopChartAsyncTaskCallback;

public class TopChartListFragment extends BaseListFragment implements TopChartAsyncTaskCallback
{

    public static TopChartListFragment newInstance() {
        TopChartListFragment fragment = new TopChartListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TopChartAsyncTask task = new TopChartAsyncTask();
        task.callback = this;
        task.execute();
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().getActionBar().setTitle(getString(R.string.app_name));
    };

    @Override
    public void callback(Tracks tracks) {
        this.tracks = tracks;
        TopChartArrayAdapter adapter = new TopChartArrayAdapter(getActivity(), tracks);
        setListAdapter(adapter);
    }
}
