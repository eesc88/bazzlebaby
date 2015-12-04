package com.houkew.bazzlebaby.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;


/**
 * Volley管理请求对列与图片加载器的工具类
 * 
 * @author apple
 *
 */
public class VolleyUtils {

	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	// 获取请求对象
	public static RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
		}

		return mRequestQueue;
	}

	// 返回ImageLoader对象
	public static ImageLoader getImageLoader(Context context) {
		getRequestQueue(context);
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(mRequestQueue, new ImageCache() {

				LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
						10 * 1024 * 1024) {
					@Override
					protected int sizeOf(String key, Bitmap value) {

						return value.getRowBytes() * value.getHeight(); // 单位是Byte字节
					}
				};

				@Override
				public void putBitmap(String url, Bitmap bitmap) {
					lruCache.put(url, bitmap);
				}

				@Override
				public Bitmap getBitmap(String url) {

					return lruCache.get(url);
				}
			});
		}

		return mImageLoader;
	}

}
