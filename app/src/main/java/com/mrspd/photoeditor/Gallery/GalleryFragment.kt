package com.mrspd.photoeditor.Gallery

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrspd.photoeditor.R


class GalleryFragment : Fragment(), OnPhoneImagesObtained {

    lateinit var mContext : Context
    companion object {
        fun newInstance() = GalleryFragment()
    }

    private lateinit var mAdapter: AlbumAdapter
    private lateinit var mRecyclerView: RecyclerView
    lateinit var title : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = container!!.context
        var rootLayout = inflater.inflate(R.layout.activity_gallery_albums,container,false)
        mRecyclerView = rootLayout.findViewById(R.id.list_albums)

        mRecyclerView.addItemDecoration(SpacesItemDecoration(4))
        mRecyclerView.layoutManager = GridLayoutManager(container!!.context,2)
        mAdapter = AlbumAdapter(container!!.context,this,activity as Activity)
        mRecyclerView.adapter = mAdapter
        var deviceImageManager = DeviceImageManager()
        deviceImageManager.getPhoneAlbums(container!!.context,this)
        return rootLayout
    }

    override fun onComplete(albums: Vector<PhoneAlbum>) {
        mAdapter.setAlbumList(albums)
    }

    override fun onError() {
        Log.v("GALLERY","Couldn't get albums")
    }



    fun startPhotoFragment() {
        var mAdapter = PhotoAdapter(mContext,activity as Activity)
        mRecyclerView.adapter = mAdapter
//        val intent = Intent(mContext, MainActivity::class.java)
        var bundle : Bundle? = requireActivity().intent.extras
        title = requireActivity().intent.getStringExtra("album").toString()
        var photoList = bundle?.getStringArrayList("photos") as List<String>
        mAdapter.setPhotoList(photoList)
       // (activity as MainActivity).setActionBarTitle(title)
    }



}