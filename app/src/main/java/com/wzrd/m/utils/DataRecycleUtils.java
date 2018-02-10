package com.wzrd.m.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.been.InboxContactMessage;
import com.wzrd.v.adapter.InboxAdapter;
import com.wzrd.v.adapter.OutboxAdapter;
import com.wzrd.v.view.SwipeMenuLayout;

import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class DataRecycleUtils {
    @BindingAdapter("outboxadapter")
    public static void setoutboxadapter(RecyclerView recyclerView, List<ContactMessage> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new OutboxAdapter(recyclerView.getContext(), data));
    }


    @BindingAdapter("inboxadapter")
    public static void setinboxadapter(RecyclerView recyclerView, List<ContactMessage> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new InboxAdapter(recyclerView.getContext(), data));
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                    if (null != viewCache) {
                        viewCache.smoothClose();
                    }
                }
                return false;
            }
        });
    }
}
