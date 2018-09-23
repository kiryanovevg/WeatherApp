package com.kiryanov.weatherapp.utils;

import android.support.annotation.IntDef;
import android.support.design.widget.BottomSheetBehavior;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({BottomSheetBehavior.STATE_HIDDEN,
        BottomSheetBehavior.STATE_EXPANDED,
        BottomSheetBehavior.STATE_COLLAPSED})
@Retention(RetentionPolicy.SOURCE)
public @interface BottomSheetStates {
}
