package com.news.simple_news.util

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.news.simple_news.R
import com.news.simple_news.util.GlideRoundTransform

fun AppCompatImageView.setImage(url: String) {
    Glide.with(this.context).load(url)
        .into(this)
}

fun AppCompatImageView.setImageWithNull() {
    Glide.with(this.context).load(R.drawable.nopic)
        .into(this)
}

fun AppCompatImageView.setBannerImage(url: String) {
    Glide.with(this.context).load(url)
        .placeholder(R.drawable.placeholder_banner)
        .transition(DrawableTransitionOptions().crossFade())
        .into(this)
}

fun AppCompatImageView.setAvatarImage(url: String) {
    Glide.with(this.context).load(url)
        .placeholder(R.drawable.default_avatar).circleCrop()
        .transition(DrawableTransitionOptions().crossFade())
        .into(this)
}
fun AppCompatImageView.setAvatarImage(imageResource:Int) {
    Glide.with(this.context).load(imageResource)
        .placeholder(R.drawable.default_avatar).circleCrop()
        .transition(DrawableTransitionOptions().crossFade())
        .into(this)
}

fun AppCompatImageView.setRoundImage(url:String){
    Glide.with(this.context)
        .load(url)
        .optionalTransform(GlideRoundTransform())
        .placeholder(R.drawable.placeholder_banner)
        .into(this)
}

fun AppCompatImageView.setBackground(url: String){
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .format(DecodeFormat.PREFER_ARGB_8888)
        .transition(DrawableTransitionOptions().crossFade())
        .into(this)
}

fun AppCompatImageView.setBackImage(url: String){
    Glide.with(this.context)
        .load(url)
        .transition(DrawableTransitionOptions().crossFade())
        .dontAnimate()
        .into(this)
}