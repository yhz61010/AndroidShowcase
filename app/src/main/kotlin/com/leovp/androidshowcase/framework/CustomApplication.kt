package com.leovp.androidshowcase.framework

import android.util.Log
import androidx.multidex.MultiDexApplication
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.leovp.androidshowcase.util.UnsplashSizingInterceptor

/**
 * Author: Michael Leo
 * Date: 2023/7/6 15:52
 */
class CustomApplication : MultiDexApplication(), ImageLoaderFactory {
    companion object {
        private const val TAG = "CA"
    }

    override fun onCreate() {
        super.onCreate()
        InitManager.init(this)
    }

    /**
     * Create the singleton [ImageLoader].
     * This is used by [AsyncImage] to load images in the app.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            // Disable `Cache-Control` header support in order to disable disk caching.
            .respectCacheHeaders(false)
            .components {
                add(UnsplashSizingInterceptor)
            }
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }

    // override fun attachBaseContext(base: Context) {
    //     // super.attachBaseContext(LangUtil.getInstance(base).setAppLanguage(base))
    //     super.attachBaseContext(this)
    //     // Reflection.unseal(base)
    //     // MultiDex.install(this) // already called in super
    // }

    // override fun onConfigurationChanged(newConfig: Configuration) {
    //     super.onConfigurationChanged(newConfig)
    //     LangUtil.getInstance(this).setAppLanguage(this)
    // }

    override fun onLowMemory() {
        Log.w(TAG, "=====> onLowMemory() <=====")
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        Log.w(TAG, "=====> onTrimMemory($level) <=====")
        super.onTrimMemory(level)
    }
}
