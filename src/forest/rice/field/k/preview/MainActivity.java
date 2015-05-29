package forest.rice.field.k.preview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import forest.rice.field.k.preview.view.searchResultView.SearchResultFragment;
import forest.rice.field.k.preview.view.topChart.TopChartAsyncTask;
import forest.rice.field.k.preview.view.topChart.TopChartListFragment;

public class MainActivity extends Activity implements OnQueryTextListener {

	private SearchView searchView = null;
	private MenuItem searchMenu = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, TopChartListFragment.newInstance())
					.commit();
		}

		TopChartAsyncTask task = new TopChartAsyncTask();
		task.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_view, menu);
		searchMenu = menu.findItem(R.id.searchView);
		searchView = (SearchView) searchMenu.getActionView();
		searchView.setOnQueryTextListener(this);

		MenuItem actionItem = menu.add("Source");
		actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onQueryTextChange(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {

		SearchResultFragment fragment = SearchResultFragment.newInstance(query);
		
		searchView.clearFocus();
		searchMenu.collapseActionView();
		getActionBar().setTitle(query);
		
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).addToBackStack(null)
				.commit();
		return true;
	}

}
