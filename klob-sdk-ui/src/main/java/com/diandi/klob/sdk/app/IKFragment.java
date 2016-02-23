package com.diandi.klob.sdk.app;

import android.os.Bundle;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-09  .
 * *********    Time : 20:27 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public interface IKFragment {
    void onEnter(Bundle data);

    void onLeave();

    void onShown(boolean isShown);

}
