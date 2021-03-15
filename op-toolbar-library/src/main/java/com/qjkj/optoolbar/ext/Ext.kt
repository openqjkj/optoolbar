package com.qjkj.optoolbar.ext

import android.content.res.Resources
import android.util.TypedValue

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 21-3-15
 * Version: 1.0.4
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
inline var Int.dp:Int
    get() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                Resources.getSystem().displayMetrics
        ).toInt()
    }
    set(value) {}


inline var Float.dp:Int
    get() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this,
                Resources.getSystem().displayMetrics
        ).toInt()
    }
    set(value) {}

inline var Int.sp:Int
    get() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                this.toFloat(),
                Resources.getSystem().displayMetrics
        ).toInt()
    }
    set(value) {}


inline var Float.sp:Int
    get() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                this,
                Resources.getSystem().displayMetrics
        ).toInt()
    }
    set(value) {}
