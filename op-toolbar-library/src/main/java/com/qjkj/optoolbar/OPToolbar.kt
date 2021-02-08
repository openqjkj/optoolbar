package com.qjkj.optoolbar

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar

/**
 * Copyright (C), 2020-2020, openqjkj
 * Author: pix
 * Date: 8/6/20
 * Version: 1.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
</desc></version></time></author> */
class OPToolbar : Toolbar {
    var tvTitle: TextView? = null
    var mImageButtonLeft: ImageButton? = null
    var mImageButtonRight: ImageButton? = null
    var mRightTextView: TextView? = null
    var leftButtonClickListener:((View)->Unit)? = null
    var rightButtonClickListener:((View)->Unit)? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setTitle(@StringRes resId: Int) {
        if (null == tvTitle) {
            tvTitle = addMiddleTitle(context, this)
        }
        tvTitle?.setText(resId)
    }

    override fun setTitle(title: CharSequence) {
        if (TextUtils.isEmpty(title)) {
            return
        }
        if (null == tvTitle) {
            tvTitle = addMiddleTitle(context, this)
        }
        tvTitle?.text = title
    }

    /**
     * 设置标题粗体
     *
     * @param isBold
     */
    fun setTitleBlod(isBold: Boolean) {
        if (null == tvTitle) {
            tvTitle = addMiddleTitle(context, this)
        }
        val paint = tvTitle?.paint
        if (isBold) {
            paint?.isFakeBoldText = true
        } else {
            paint?.isFakeBoldText = false
        }
    }

    private fun addMiddleTitle(context: Context, toolbar: Toolbar): TextView {
        val textView = TextView(context)
        textView.textSize = 17f
        textView.maxWidth = dp2px(250f).toInt()
        textView.ellipsize = TextUtils.TruncateAt.END
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER_HORIZONTAL
        toolbar.addView(textView, params)
        val paint = textView.paint
        paint.isFakeBoldText = true
        textView.setTextColor(resources.getColor(R.color.color_23204C))
        return textView
    }

    private fun addRightTextBtn(): TextView {
        val textView = TextView(context)
        textView.textSize = 16f
        textView.ellipsize = TextUtils.TruncateAt.END
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
        val pd = dp2px(10f).toInt()
        textView.setPadding(10, pd, dp2px(20f).toInt(), pd)
        addView(textView, params)
        textView.setOnClickListener { v ->
            rightButtonClickListener?.also { it(v) }
        }
        return textView
    }

    private fun addLeftImgBtn(): ImageButton {
        val ib = ImageButton(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        val pd = dp2px(10f).toInt()
        ib.setPadding(0, pd, dp2px(25f).toInt(), pd)
        addView(ib, params)
        ib.setOnClickListener { v ->
            leftButtonClickListener?.let { it(v) }
        }
        return ib
    }

    private fun addRightImgBtn(): ImageButton {
        val ib = ImageButton(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        val pd = dp2px(10f).toInt()
        ib.setPadding(dp2px(10f).toInt(), pd, dp2px(25f).toInt(), pd)
        ib.layoutParams = params
        addView(ib)
        ib.setOnClickListener { v ->
            rightButtonClickListener?.let { it(v) }
        }
        return ib
    }

    fun setLeftBtnDrawable(@DrawableRes leftImgRes: Int): ImageButton? {
        if (null == mImageButtonLeft) {
            mImageButtonLeft = addLeftImgBtn()
        }
        //        mImageButtonLeft.setBackgroundResource(leftImgRes);
        mImageButtonLeft?.setBackgroundColor(resources.getColor(R.color.translate))
        mImageButtonLeft?.setImageResource(leftImgRes)
        return mImageButtonLeft
    }

    fun setRightBtnDrawable(@DrawableRes rightImgRes: Int): ImageButton? {
        if (null == mImageButtonRight) {
            mImageButtonRight = addRightImgBtn()
        }
        mImageButtonRight?.setBackgroundColor(resources.getColor(R.color.translate))
        mImageButtonRight?.setImageResource(rightImgRes)
        return mImageButtonRight
    }

    fun setRightTextBtnString(text: CharSequence?): TextView? {
        if (null == mRightTextView) {
            mRightTextView = addRightTextBtn()
        }
        mRightTextView?.text = text
        return mRightTextView
    }

    fun setLeftBtnVisibility(visibility: Int) {
        if (null != mImageButtonLeft) {
            mImageButtonLeft?.visibility = visibility
        }
    }

    fun setRightBtnVisibility(visibility: Int) {
        if (null != mImageButtonRight) {
            mImageButtonRight?.visibility = visibility
        }
    }

    private fun dp2px(dp: Float): Float {
        return dp * context.resources.displayMetrics.density + 0.5f
    }
}