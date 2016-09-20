package com.atto.developers.atto.asymmetricgridview;

import android.widget.ListAdapter;

import java.util.List;

/**
 * Created by Tacademy on 2016-08-23.
 */
public interface DemoAdapter extends ListAdapter {

    void appendItems(List<DemoItem> newItems);

    void setItems(List<DemoItem> moreItems);

}