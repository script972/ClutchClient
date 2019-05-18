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

import com.script972.clutchclient.R
import com.script972.clutchclient.helpers.DataTransferHelper
import com.script972.clutchclient.helpers.ImageHelper
import com.script972.clutchclient.ui.model.CardItem
import com.squareup.picasso.Picasso

import java.io.FileDescriptor
import java.io.IOException
import java.net.URI

class InfoFragment : Fragment() {


    //outlets
    lateinit var front: ImageView
    lateinit var back: ImageView
    lateinit var rvAccessList: RecyclerView

    companion object {
        fun instance(): InfoFragment {
            return InfoFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.info_card_fragment, container, false)
        front = view.findViewById(R.id.photo_front)
        back = view.findViewById(R.id.photo_back)
        rvAccessList = view.findViewById(R.id.rvAccessList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
    }


    private fun fillData() {
        val bundle = this.arguments ?: return
        if (bundle.containsKey("cardItem")) {
            val cardItem = DataTransferHelper.convertFromJson(CardItem::class.java,
                    bundle.getString("cardItem")) as CardItem
            setBind(cardItem)
        }
    }

    fun setBind(cardItem: CardItem) {
        val backphoto = cardItem.photoBack
        val facephoto = cardItem.photoFront

        if (backphoto != null) {
            Picasso.get()
                    .load(Uri.parse(backphoto).getPath())
                    .placeholder(R.drawable.cardtemplate)
                    .into(back);
            back.setImageURI(Uri.parse(backphoto))
            //Может не работать
            // back.setImageURI(Uri.parse(backphoto).getPath());

        }

        if (facephoto != null && front != null) {
            /*Picasso.get()
                    .load(Uri.parse(facephoto))
                    .placeholder(R.drawable.cardtemplate)
                    .into(front);
            front!!.setImageURI(Uri.parse(facephoto))
        }
        */
        }
    }


}
