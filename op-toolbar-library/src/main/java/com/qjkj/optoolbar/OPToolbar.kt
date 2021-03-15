package com.qjkj.optoolbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.qjkj.optoolbar.ext.sp

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
    val TAG = OPToolbar::class.java.simpleName
    var tvTitle: TextView? = null
    var mImageButtonLeft: ImageButton? = null
    var mImageButtonRight: ImageButton? = null
    var mRightTextView: TextView? = null
    var leftButtonClickListener:((View)->Unit)? = null
    var rightButtonClickListener:((View)->Unit)? = null

    constructor(context: Context) : super(context) {}
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.OPToolbar)
        var titleText = typedArray.getString(R.styleable.OPToolbar_title_text)
        var titleTextColor = typedArray.getColor(R.styleable.OPToolbar_title_text_color, Color.BLACK)
        var titleTextSize = typedArray.getDimensionPixelSize(R.styleable.OPToolbar_title_text_size, 12.sp)
        var titleBackground = typedArray.getDrawable(R.styleable.OPToolbar_title_background)
        var titleTextBold = typedArray.getBoolean(R.styleable.OPToolbar_title_text_bold, false)
        typedArray.recycle(); //注意回收
//        Log.d(TAG, "constructor(2), titleText: $titleText")
//        Log.d(TAG, "constructor(2), titleTextColor: $titleTextColor")
//        Log.d(TAG, "constructor(2), titleTextSize: $titleTextSize")
//        Log.d(TAG, "constructor(2), titleBackground: $titleBackground")
        initTitle(titleText, titleTextSize, titleTextColor, titleBackground, titleTextBold)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initTitle(title: String?, textSize: Int, textColor: Int, background: Drawable?, bold: Boolean) {
        if(TextUtils.isEmpty(title)) {
            return
        }
        if (null == tvTitle) {
            tvTitle = addMiddleTitle(context, this)
        }
        tvTitle?.text = title
        tvTitle?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        tvTitle?.setTextColor(textColor)
        tvTitle?.background = background
        if(bold) {
            tvTitle?.typeface = Typeface.defaultFromStyle(Typeface.BOLD);
            tvTitle?.paint?.isFakeBoldText = true
        }
    }

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