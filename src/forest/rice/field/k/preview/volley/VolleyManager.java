package forest.rice.field.k.preview.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

public class VolleyManager {

	private static VolleyManager manager;
	
	private Context context;
	private ImageLoader mImageLoader;
	private RequestQueue mQueue;
	
	private VolleyManager(Context context){
		this.context = context;
		mQueue = Volley.newRequestQueue(this.context);
		mImageLoader = new ImageLoader(mQueue, new VolleyLruCache());
	}
	
	public static VolleyManager getInstance(Context context) {
		if(manager == null) {
			manager = new VolleyManager(context);
		}		 
		return manager;
	}
	
	public ImageContainer imageGet(String requestUrl, ImageView view, int defaultImageResId, int errorImageResId) {
		ImageListener listener = ImageLoader
				.getImageListener(view,
						defaultImageResId /* 表示待ち時の画像 */,
						errorImageResId /* エラー時の画像 */);
		ImageContainer container = mImageLoader.get(requestUrl, listener); /* URLから画像を取得する */
		
		ImageContainer container2 = (ImageContainer)view.getTag();
		if(container2 != null) {
			container2.cancelRequest();
		}
		view.setTag(container);
		
		return container;
	}
	
	public class VolleyLruCache implements ImageCache {

		private LruCache<String, Bitmap> mMemoryCache;

		public VolleyLruCache() {
			int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
			int cacheSize = maxMemory / 4; // 最大メモリに依存

			mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					// 使用キャッシュサイズ(KB単位)
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
			};
		}

		// ImageCacheのインターフェイス実装
		@Override
		public Bitmap getBitmap(String url) {
			return mMemoryCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			mMemoryCache.put(url, bitmap);
		}
	}
}
