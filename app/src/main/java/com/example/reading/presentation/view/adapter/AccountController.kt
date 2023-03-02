package com.example.reading.presentation.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.example.reading.R
import com.example.reading.databinding.ItemAccountBinding
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder

class AccountController : TypedEpoxyController<List<Account>>() {
    override fun buildModels(data: List<Account>?) {
        data ?: return
        data.forEach {
            Holder(it).id(it.hashCode()).addTo(this)
        }
    }

    class Holder(private val account: Account) :
        ViewBindingEpoxyModelWithHolder<ItemAccountBinding>() {
        override fun getDefaultLayout() = R.layout.item_account

        override fun bindView() {
            binding.txtUserName.text = account.username
            binding.txtPhoneNumber.text = account.phone
        }
    }
}