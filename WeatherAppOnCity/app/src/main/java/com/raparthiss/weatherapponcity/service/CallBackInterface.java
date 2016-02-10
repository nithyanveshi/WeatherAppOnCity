package com.raparthiss.weatherapponcity.service;

import com.raparthiss.weatherapponcity.data.Channel;

/**
 * Created by rapar on 2/3/2016.
 */
public interface CallBackInterface {
    void success(Channel channel);
    void failure(Exception exp);
}
