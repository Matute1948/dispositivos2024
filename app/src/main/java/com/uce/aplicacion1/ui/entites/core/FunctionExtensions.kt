package com.uce.aplicacion1.ui.entites.core

import com.uce.aplicacion1.data.network.entities.clash.cards.Item
import com.uce.aplicacion1.data.network.entities.topNews.Data
import com.uce.aplicacion1.ui.entites.NewsDataUI

class FunctionExtensions

    fun Data.toNewsDataUI() = NewsDataUI(
        this.uuid,
        this.url,
        this.title,
        this.image_url,
        this.description,
        this.language
    )


