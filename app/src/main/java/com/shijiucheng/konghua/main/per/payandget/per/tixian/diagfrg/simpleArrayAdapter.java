package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class simpleArrayAdapter<T> extends ArrayAdapter {
    public simpleArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
