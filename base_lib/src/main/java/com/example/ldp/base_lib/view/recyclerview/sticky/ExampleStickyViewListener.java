package com.example.ldp.base_lib.view.recyclerview.sticky;

import android.view.View;

/**
 * Created by cpf on 2018/1/16.
 */

public class ExampleStickyViewListener implements StickyViewListener {

    @Override
    public boolean isStickyView(View view) {
        return (Boolean) view.getTag();
    }

    @Override
    public int getStickViewType() {
        return 11;
    }
}
