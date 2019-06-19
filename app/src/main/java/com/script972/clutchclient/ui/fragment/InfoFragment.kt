package com.script972.clutchclient.ui.fragment

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.util.ArrayUtils.removeAll

import com.script972.clutchclient.R
import com.script972.clutchclient.helpers.DataTransferHelper
import com.script972.clutchclient.helpers.ImageHelper
import com.script972.clutchclient.ui.adapters.AccessListAdapter
import com.script972.clutchclient.ui.model.AccessPersonItem
import com.script972.clutchclient.ui.model.CardItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_card_fragment.*
import kotlinx.android.synthetic.main.info_card_fragment.view.*

import java.io.FileDescriptor
import java.io.IOException
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class InfoFragment : Fragment() {

    /* companion object {
         fun instance(): InfoFragment {
             return InfoFragment()
         }
     }*/

    private lateinit var accessListAdapter: AccessListAdapter
    private val dataAccess: ArrayList<AccessPersonItem> = ArrayList();


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.info_card_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
      //  fillData()
    }


    private fun initView() {
        accessListAdapter = AccessListAdapter(dataAccess)
        rvAccessList.layoutManager = LinearLayoutManager(context)
        rvAccessList.itemAnimator = DefaultItemAnimator()
        rvAccessList.adapter = accessListAdapter
    }


  /*  private fun fillData() {
        val bundle = this.arguments ?: return
        if (bundle.containsKey("cardItem")) {
            val cardItem = DataTransferHelper.convertFromJson(CardItem::class.java,
                    bundle.getString("cardItem")) as CardItem
            setBind(cardItem)

        }
    }*/

    private fun mockDataCardItem(cardItem: CardItem): MutableList<AccessPersonItem> {
        val accessList: MutableList<AccessPersonItem> = ArrayList();
        val itemPerson = AccessPersonItem();
        itemPerson.name = "Name 123456789101112131415"
        accessList.add(itemPerson)
        return accessList;
    }

    fun setBind(cardItem: CardItem) {
        txtComment.text = cardItem.comment

        if (cardItem.photoBack != null) {
            Picasso.get()
                    .load(Uri.parse(cardItem.photoBack).path)
                    .placeholder(R.drawable.cardtemplate)
                    .into(imgPhotoBack)
        }
        // imgPhotoBack.setImageURI(Uri.parse(cardItem.photoBack))
        //Может не работать

        /* Picasso.get()
                 .load(Uri.parse(facephoto))
                 .placeholder(R.drawable.cardtemplate)
                 .into(photo_front);*/
        if (cardItem.photoFront != null) {
            imgPhotoFront.setImageURI(Uri.parse(cardItem.photoFront))
        }

        cardItem.listAccessUser?.clear()
        cardItem.listAccessUser?.addAll(mockDataCardItem(cardItem))
        dataAccess.clear();
        cardItem.listAccessUser?.let { dataAccess.addAll(it) }
        accessListAdapter.notifyDataSetChanged()

    }

}
