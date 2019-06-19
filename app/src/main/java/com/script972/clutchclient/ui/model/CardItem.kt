package com.script972.clutchclient.ui.model

data class CardItem(

        var id: Int = 0,

        var title: String? = null,

        var cardNumber: String? = null,

        var comment: String? = null,

        var photoFront: String? = null,

        var photoBack: String? = null,

        var updateMode: Boolean = false,

        var listAccessUser: MutableList<AccessPersonItem>? = ArrayList()

)
