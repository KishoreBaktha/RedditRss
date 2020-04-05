package com.example.redditrss

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener

class CustomListAdapter(context: Context, resource: Int, objects: ArrayList<Post?>) :
    ArrayAdapter<Post?>(context, resource, objects) {
    private val mContext: Context
    private val mResource: Int

    /**
     * Holds variables in a View
     */
    private class ViewHolder {
        var title: TextView? = null
        var image: ImageView? = null
        var author: TextView? = null
        var updated: TextView? = null
        var dialog: ProgressBar? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv: View? = convertView
        val title = getItem(position)?.title
        val author = getItem(position)?.author
        val updated = getItem(position)?.date_updated
        val thumbNailUrl = getItem(position)?.thrumbNailUrl
        return try {
            //ViewHolder object
            val holder: ViewHolder
            if (cv == null) {
                val inflater = LayoutInflater.from(mContext)
                cv = inflater.inflate(mResource, parent, false)
                holder = ViewHolder()
                holder.title = cv.findViewById(R.id.cardTitle)
                holder.image = cv.findViewById(R.id.cardImage) as ImageView
                holder.dialog = cv.findViewById(R.id.cardProgressDialog) as ProgressBar
                holder.author = cv.findViewById(R.id.cardAuthor)
                holder.updated = cv.findViewById(R.id.cardUpdated)
                cv.tag = holder
            } else {
                holder = cv.tag as ViewHolder
            }
            holder.title?.text = title
            holder.author?.text = author
            holder.updated?.text = updated
            //create the imageloader object
            val imageLoader: ImageLoader = ImageLoader.getInstance()
            val defaultImage: Int = mContext.getResources().getIdentifier("@drawable/ic_launcher_background", null, mContext.getPackageName())
            //create display options
            val options = DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build()

            //download and display image from url
            imageLoader.displayImage(thumbNailUrl, holder.image, options, object : ImageLoadingListener {
                override fun onLoadingStarted(imageUri: String?, view: View?) {
                    holder.dialog?.visibility = View.VISIBLE
                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                    holder.dialog?.visibility = View.GONE
                }

                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    holder.dialog?.visibility = View.GONE
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {}
            }
            )
            cv as View
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "getView: IllegalArgumentException: " + e.message)
            cv as View
        }
    }

    /**
     * Required for setting up the Universal Image loader Library
     */
    private fun setupImageLoader() { // UNIVERSAL IMAGE LOADER SETUP
        val defaultOptions = DisplayImageOptions.Builder()
            .cacheOnDisc(true).cacheInMemory(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(FadeInBitmapDisplayer(300)).build()
        val config = ImageLoaderConfiguration.Builder(mContext)
            .defaultDisplayImageOptions(defaultOptions)
            .memoryCache(WeakMemoryCache())
            .discCacheSize(100 * 1024 * 1024).build()
        ImageLoader.getInstance().init(config)
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

    companion object {
        private const val TAG = "CustomListAdapter"
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    init {
        mContext = context
        mResource = resource
        //sets up the image loader library
        setupImageLoader()
    }
}