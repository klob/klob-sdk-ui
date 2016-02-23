package com.diandi.klob.sdk.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;



/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-09  .
 * *********    Time : 20:27 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class BaseFragmentParam<T extends Fragment> {
    public T from;
    public Class<?> cls;
    public Bundle data;
}
