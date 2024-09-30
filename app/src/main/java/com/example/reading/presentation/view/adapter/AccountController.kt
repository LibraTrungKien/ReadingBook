package com.example.reading.presentation.view.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.Glide
import com.example.reading.R
import com.example.reading.databinding.ItemAccountBinding
import com.example.reading.domain.model.Account
import com.example.reading.presentation.view.base.ViewBindingEpoxyModelWithHolder

class AccountController(private val onMenuClick: OnMenuClick) :
    TypedEpoxyController<List<Account>>() {
    override fun buildModels(data: List<Account>?) {
        data ?: return
        data.forEach {
            Holder(onMenuClick, it).id(it.hashCode()).addTo(this)
        }
    }

    class Holder(private val onMenuClick: OnMenuClick, private val account: Account) :
        ViewBindingEpoxyModelWithHolder<ItemAccountBinding>() {
        override fun getDefaultLayout() = R.layout.item_account

        override fun initializeView() {
            binding.btnRemoveAccount.setOnClickListener { onMenuClick.onMenuClicked(account) }
        }

        override fun bindView() {
            binding.txtUserName.text = account.username
            binding.txtPhoneNumber.text = account.email
            Glide.with(binding.imgUser).load(account.avatar).into(binding.imgUser)
        }
    }
}

interface OnMenuClick {
    fun onMenuClicked(account: Account)
}
