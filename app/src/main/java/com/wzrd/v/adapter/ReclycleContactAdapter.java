package com.wzrd.v.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.db.manger.ContactsManager;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.inteface.AdapterClickPosition;
import com.wzrd.v.view.GlideCircleTransform;
import com.wzrd.v.view.NumImageView;
import com.wzrd.v.view.SwipeMenuLayout;

import java.util.List;

/**
 * Created by lk on 2018/1/5.
 */

public class ReclycleContactAdapter extends RecyclerView.Adapter<ReclycleContactAdapter.ViewHolder> {
    private Context mContext;
    private List<TSYSCONTANTS> tsyscontantsList;
    private AdapterClickPosition.position adapterClickPosition;
    private boolean isDeleteAble = true;

    public ReclycleContactAdapter(Context mContext, List<TSYSCONTANTS> tsyscontantsList, AdapterClickPosition.position adapterClickPosition) {
        this.mContext = mContext;
        this.tsyscontantsList = tsyscontantsList;
        this.adapterClickPosition = adapterClickPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        View view = holder.itemView;
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Utils.ToastShort(mContext,"posiotion--"+position);
//                holder.cl_all.setBackgroundColor(mContext.getResources().getColor(R.color.white_b9));
                adapterClickPosition.adapterposition(position);
            }
        });
//        holder.cl_all.setBackgroundColor(0);
        holder.cl_all.setBackgroundColor(Color.rgb(26, 32, 39));
        holder.ll_item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
              int  x = (int) motionEvent.getX();
              int  y = (int) motionEvent.getY();
                Log.e("motionEvent.getAction()","motionEvent.getAction()--"+motionEvent.getAction());
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        holder.cl_all.setBackgroundColor(mContext.getResources().getColor(R.color.white_b9));
                        break;
                    case MotionEvent.ACTION_UP:
                        holder.cl_all.setBackgroundColor(Color.rgb(26, 32, 39));
//                        holder.cl_all.setBackgroundColor(0);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int  mx = (int) motionEvent.getX();
                        int  my = (int) motionEvent.getY();
                        if(Math.abs(mx-x)>5||Math.abs(my-y)>5){
                            holder.cl_all.setBackgroundColor(Color.rgb(26, 32, 39));
                        }

//                        holder.cl_all.setBackgroundColor(0);
                        break;


                    default:
//                        Utils.ToastShort(mContext,"1234");
                        holder.cl_all.setBackgroundColor(Color.rgb(26, 32, 39));
//                        holder.cl_all.setBackgroundColor(0);
                        break;
                }


                return false;
            }

        });


        final TSYSCONTANTS modlie = tsyscontantsList.get(position);
        holder.tv_contact_name.setText(modlie.getT_sys_contacts_name());

        Glide.with(view.getContext())
                .load(modlie.getT_sys_usericonpath())
                .placeholder(R.mipmap.icon_signin_default)
                .bitmapTransform(new GlideCircleTransform(view.getContext()))
                .error(R.mipmap.icon_signin_default)
                .into(holder.iv_contact_iocn);
        holder.btn_delcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                if (null != viewCache) {
                    viewCache.smoothClose();
                }
                if (isDeleteAble) {
                    isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.3秒钟再让他为
                    ContactsManager manager = new ContactsManager(mContext);
                    manager.deleteUser(modlie);
                    //true,起到让数据源刷新完成的作用
                    tsyscontantsList.remove(position);//删除数据源
                    notifyItemRemoved(position);//刷新被删除的地方
                    notifyItemRangeChanged(position, getItemCount()); //刷新被删除数据，以及其后面的数据
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(300);
                                isDeleteAble = true;//可点击按钮
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tsyscontantsList == null ? 0 : tsyscontantsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_contact_name;
        NumImageView iv_contact_iocn;
        Button btn_delcontact;
        LinearLayout ll_item;
        ConstraintLayout cl_all;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_contact_name = (TextView) itemView.findViewById(R.id.tv_contact_name);
            iv_contact_iocn = (NumImageView) itemView.findViewById(R.id.iv_contact_iocn);
            btn_delcontact = (Button) itemView.findViewById(R.id.btn_delcontact);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            cl_all = (ConstraintLayout) itemView.findViewById(R.id.cl_all);

        }
    }
}
