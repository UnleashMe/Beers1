package com.example.neverpidor.ui.fragments.itemlist

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.example.neverpidor.R
import com.example.neverpidor.databinding.ModelSnackBinding
import com.example.neverpidor.databinding.ModelSnackTypeBinding
import com.example.neverpidor.model.Beverage
import com.example.neverpidor.model.Data
import com.example.neverpidor.model.Snack
import com.example.neverpidor.model.SnackList
import com.example.neverpidor.ui.epoxy.ViewBindingKotlinModel

class MenuItemListEpoxyController(val id: Int) : EpoxyController() {

    var snacks = SnackList()
        set(value) {
            field = value
            requestModelBuild()
        }
    var drinks = listOf<Beverage>()
    var isShown = ""
    set(value) {
        if (value == field) {
            field = ""
            requestModelBuild()
        } else {
            field = value
            requestModelBuild()
        }
    }


    override fun buildModels() {

        when (id) {
            1 -> {

                snacks.data.groupBy { it.type }.forEach { map ->
                    SnackTypeEpoxyModel(map.key) { string ->
                        isShown = string

                    }.id(map.key.hashCode()).addTo(this)

                    map.value.filter { it.type == isShown }.forEach {
                        SnackEpoxyModel(it).id(it.UID).addTo(this)
                    }

                }
            }
        }
    }

    data class SnackTypeEpoxyModel(val type: String, val onTypeClick: (String) -> Unit) :
        ViewBindingKotlinModel<ModelSnackTypeBinding>(R.layout.model_snack_type) {
        override fun ModelSnackTypeBinding.bind() {
            typeText.text = type
            root.setOnClickListener {
                onTypeClick(type)
            }
        }

    }

    data class SnackEpoxyModel(val data: Data) :
        ViewBindingKotlinModel<ModelSnackBinding>(R.layout.model_snack) {
        override fun ModelSnackBinding.bind() {
            nameText.text = data.name

            price.text = "${data.price} P."
            var closed: Boolean = true
            showDescImage.setOnClickListener {

                if (closed) {
                    this.description.isVisible = true
                    this.description.text = data.description
                } else {
                    this.description.isGone = true
                }
                closed = !closed

            }

        }

    }


}