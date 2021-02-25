package com.mlink.bluetooth.gateway.view;

import android.view.View;
import android.widget.TextView;

import com.mlink.bluetooth.gateway.R;

public
class ViewHolder {

    public TextView mTextView;
    public ViewHolder(View view) {
        mTextView = view.findViewById(R.id.textView);
    }
}
