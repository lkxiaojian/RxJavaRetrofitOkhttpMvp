package com.wzrd.v.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wzrd.R;
import com.wzrd.m.utils.MoviceDateUtils;

import java.math.BigDecimal;

import static com.makeramen.roundedimageview.RoundedDrawable.drawableToBitmap;

/**
 * 双向滑块的进度条（区域选择）
 */
public class SeekRangeBar extends View {
    private Context _context;
    private static final int CLICK_ON_LOW = 1;        //手指在前滑块上滑动
    private static final int CLICK_ON_HIGH = 2;       //手指在后滑块上滑动
    private static final int CLICK_IN_LOW_AREA = 3;   //手指点击离前滑块近
    private static final int CLICK_IN_HIGH_AREA = 4;  //手指点击离后滑块近
    private static final int CLICK_OUT_AREA = 5;      //手指点击在view外
    private static final int CLICK_INVAILD = 0;
    private static final int[] STATE_NORMAL = {};
    private static final int[] STATE_PRESSED = {android.R.attr.state_pressed, android.R.attr.state_window_focused,};
    private static int mThumbMarginTop = 0;   //滑动块顶部离view顶部的距离
    private static int mTextViewMarginTop = 0;   //当前滑块文字距离view顶部距离
    private Drawable hasScrollBarBg;        //滑动条滑动后背景图
    private Drawable notScrollBarBg;        //滑动条未滑动背景图
    private Drawable mThumbLow;         //前滑块
    private Drawable mThumbHigh;        //后滑块
    private int mScollBarWidth;     //控件宽度 = 滑动条宽度 + 滑动块宽度
    private int mScollBarHeight;    //滑动条高度
    private int mThumbWidth;        //滑动块直径
    private double mOffsetLow = 0;     //前滑块中心坐标
    private double mOffsetHigh = 0;    //后滑块中心坐标
    private int mDistance = 0;      //总刻度是固定距离 两边各去掉半个滑块距离
    private int mFlag = CLICK_INVAILD;   //手指按下的类型
    private double defaultScreenLow = 0;    //默认前滑块位置百分比
    private double defaultScreenHigh = 100;  //默认后滑块位置百分比
    private OnSeekBarChangeListener mBarChangeListener;
    private boolean editable = false;//是否处于可编辑状态
    private int miniGap = 5;//AB的最小间隔
    private double progressLow;//起点(百分比)
    private double progressHigh;//终点
    private int total = 100;
    private Bitmap mthumhighbitma;
    private int fontsizea = 20;//字体的大小
    private int fontsizeb = 20;//字体的大小
    private int coclora = Color.BLACK;//字体的颜色
    private int coclorb = Color.BLACK;//字体的颜色
    private int hight = 20;
    private Paint painta;
    private Paint paintb;
    private int isNumText = 1;//默认选中的第二个 0 表示第一个 1表示第二个
    private String selectedColor = "#FF007AFF";
    private String unSelectColor = "#FFFFFFFF";

    public int getIsNumText() {
        return isNumText;
    }

    public void setIsNumText(int isNumText) {
        this.isNumText = isNumText;
    }

    public SeekRangeBar(Context context) {
        this(context, null);
    }

    public SeekRangeBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekRangeBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        _context = context;
        notScrollBarBg = ContextCompat.getDrawable(_context, R.drawable.select_line_bg);
        hasScrollBarBg = ContextCompat.getDrawable(_context, R.drawable.selected_line_bg);
        mThumbLow = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_start);//clip_time_shape_start
        mThumbHigh = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_end);//clip_time_shape_end
//        mThumbLow=zoomDrawable(mThumbLow,300,300);

        mThumbLow.setState(STATE_NORMAL);
        mThumbHigh.setState(STATE_NORMAL);
        //设置滑动条高度
        mScollBarHeight = notScrollBarBg.getIntrinsicHeight();
        //设置滑动块直径
        mThumbWidth = mThumbHigh.getIntrinsicWidth() + 10;


    }

    /**
     * 测量view尺寸（在onDraw()之前）
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        mScollBarWidth = width;
        if (mDistance == 0) {//只是初始化的时候测量
            mOffsetLow = mThumbWidth / 2;
            mOffsetHigh = width - mThumbWidth / 2;
        }
        mDistance = width - mThumbWidth;
        if (defaultScreenLow != 0) {
            mOffsetLow = formatInt(defaultScreenLow / total * (mDistance)) + mThumbWidth / 2;
        }
        if (defaultScreenHigh != total) {
            mOffsetHigh = formatInt(defaultScreenHigh / total * (mDistance)) + mThumbWidth / 2;
        }
        setMeasuredDimension(width, mThumbHigh.getIntrinsicHeight() + mThumbMarginTop+2+10);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int top = mThumbMarginTop + mThumbHigh.getIntrinsicHeight() / 2 - mScollBarHeight / 2 + hight;
        int bottom = top + mScollBarHeight;

        if (editable) {//仅可编辑状态下显示进度条
            //白色滑动条，两个滑块各两边部分
            notScrollBarBg.setBounds(mThumbWidth / 2, top, mScollBarWidth - mThumbWidth / 2, bottom);
            notScrollBarBg.draw(canvas);

            //红色滑动条，两个滑块中间部分
            hasScrollBarBg.setBounds((int) mOffsetLow, top, (int) mOffsetHigh, bottom);
            hasScrollBarBg.draw(canvas);
        }

        //前滑块

        mThumbLow.setBounds((int) (mOffsetLow - mThumbWidth / 2), mThumbMarginTop + hight, (int) (mOffsetLow + mThumbWidth / 2), mThumbWidth + mThumbMarginTop + hight);
        mThumbLow.draw(canvas);

        //后滑块
        mThumbHigh.setBounds((int) (mOffsetHigh - mThumbWidth / 2), mThumbMarginTop + hight, (int) (mOffsetHigh + mThumbWidth / 2), mThumbWidth + mThumbMarginTop + hight);
        mThumbHigh.draw(canvas);

        //当前滑块刻度
        progressLow = formatInt((mOffsetLow - mThumbWidth / 2) * total / mDistance);
        progressHigh = formatInt((mOffsetHigh - mThumbWidth / 2) * total / mDistance);
        painta = new Paint();
        //写文字
        painta.setColor(coclora);
        painta.setTextAlign(Paint.Align.CENTER);
        painta.setTextSize(fontsizea); //以px为单位
        paintb = new Paint();
        paintb.setColor(coclorb);
        paintb.setTextAlign(Paint.Align.CENTER);
        paintb.setTextSize(fontsizeb); //以px为单位
        MoviceDateUtils utils = new MoviceDateUtils();
        canvas.drawText(utils.stringForTime((int) progressLow) + "", (int) mOffsetLow, 20, painta);
        canvas.drawText(utils.stringForTime((int) progressHigh) + "", (int) mOffsetHigh, 20, paintb);

        if (mBarChangeListener != null) {
            mBarChangeListener.onProgressChanged(this, progressLow, progressHigh);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (!editable) {
            return false;
        }
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            mFlag = getAreaFlag(e);
            if (mFlag == CLICK_ON_LOW) {
                isNumText = 0;//第一个亮
                coclora = Color.parseColor(selectedColor);
                coclorb = Color.parseColor(unSelectColor);
                mThumbLow = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_end);
                mThumbHigh = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_start);
                mThumbLow.setState(STATE_PRESSED);
            } else if (mFlag == CLICK_ON_HIGH) {
                isNumText = 1;//第二个亮
                coclora = Color.parseColor(unSelectColor);
                coclorb = Color.parseColor(selectedColor);
                mThumbLow = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_start);
                mThumbHigh = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_end);
                mThumbHigh.setState(STATE_PRESSED);
            } else if (mFlag == CLICK_IN_LOW_AREA) {
                isNumText = 0;//第一个亮
                coclora = Color.parseColor(selectedColor);
                coclorb = Color.parseColor(unSelectColor);
                mThumbLow = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_end);
                mThumbHigh = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_start);
                mThumbLow.setState(STATE_PRESSED);
                mThumbHigh.setState(STATE_NORMAL);
                //如果点击0-mThumbWidth/2坐标
                if (e.getX() < 0 || e.getX() <= mThumbWidth / 2) {
                    mOffsetLow = mThumbWidth / 2;
                } else if (e.getX() > mScollBarWidth - mThumbWidth / 2) {
                    mOffsetLow = mThumbWidth / 2 + mDistance;
                } else {
                    mOffsetLow = formatInt(e.getX());

                }
            } else if (mFlag == CLICK_IN_HIGH_AREA) {
                isNumText = 1;//第二个亮
                coclora = Color.parseColor(unSelectColor);
                coclorb = Color.parseColor(selectedColor);
                mThumbLow = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_start);
                mThumbHigh = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_end);
                mThumbHigh.setState(STATE_PRESSED);
                mThumbLow.setState(STATE_NORMAL);
                if (e.getX() >= mScollBarWidth - mThumbWidth / 2) {
                    mOffsetHigh = mDistance + mThumbWidth / 2;
                } else {
                    mOffsetHigh = formatInt(e.getX());
                }
            }
            //更新滑块
            Log.d("LOGCAT", "refresh down");
            if (cha(mOffsetHigh, mOffsetLow)) {
                //更新滑块
                invalidate();

            }
        } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
            if (mFlag == CLICK_ON_LOW) {
                isNumText = 0;//第一个亮
                coclora = Color.parseColor(selectedColor);
                coclorb = Color.parseColor(unSelectColor);
                mThumbLow = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_end);
                mThumbHigh = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_start);
                if (e.getX() < 0 || e.getX() <= mThumbWidth / 2) {
                    mOffsetLow = mThumbWidth / 2;
                } else if (e.getX() >= mScollBarWidth - mThumbWidth / 2) {
                    mOffsetLow = mThumbWidth / 2 + mDistance;
                    mOffsetHigh = mOffsetLow;
                } else {
                    mOffsetLow = formatInt(e.getX());
                    if (mOffsetHigh - mOffsetLow <= 0) {
                        mOffsetHigh = (mOffsetLow <= mDistance + mThumbWidth / 2) ? (mOffsetLow) : (mDistance + mThumbWidth / 2);
                    }
                }
            } else if (mFlag == CLICK_ON_HIGH) {
                isNumText = 1;//第二个亮
                coclora = Color.parseColor(unSelectColor);
                coclorb = Color.parseColor(selectedColor);
                mThumbLow = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_start);
                mThumbHigh = ContextCompat.getDrawable(_context, R.drawable.clip_time_shape_end);
                if (e.getX() < mThumbWidth / 2) {
                    if (cha(mThumbWidth / 2, mThumbWidth / 2)) {

                    }
                    mOffsetHigh = mThumbWidth / 2;
                    mOffsetLow = mThumbWidth / 2;
                } else if (e.getX() > mScollBarWidth - mThumbWidth / 2) {
                    mOffsetHigh = mThumbWidth / 2 + mDistance;
                } else {
                    mOffsetHigh = formatInt(e.getX());
                    if (mOffsetHigh - mOffsetLow <= 0) {
                        mOffsetLow = (mOffsetHigh >= mThumbWidth / 2) ? (mOffsetHigh) : mThumbWidth / 2;
                    }
                }
            }
            if (cha(mOffsetHigh, mOffsetLow)) {
                //更新滑块
                invalidate();
            }

        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            Log.d("LOGCAT", "ACTION UP:" + progressHigh + "-" + progressLow);
            mThumbLow.setState(STATE_NORMAL);
            mThumbHigh.setState(STATE_NORMAL);
            if (cha(progressHigh, progressLow) && progressHigh < progressLow + miniGap) {
                progressHigh = progressLow + miniGap;
                this.defaultScreenHigh = progressHigh;
                mOffsetHigh = formatInt(progressHigh / total * (mDistance)) + mThumbWidth / 2;

                if (cha(mOffsetHigh, mOffsetLow)) {
                    //更新滑块
                    invalidate();
                }
            }
        }
        return true;
    }

    private boolean cha(double progressHigh, double progressLow) {
        double less = Math.abs(progressHigh) - Math.abs(progressLow);
        Log.e("less", "less--->" + less);
        return less > 100;
    }

    /**
     * 设置是否可编辑状态
     *
     * @param _b
     */
    public void setEditable(boolean _b) {
        editable = _b;
        invalidate();
        Log.d("LOGCAT", "editable:" + editable);
    }

    /**
     * 设置a左边字体的大小
     *
     * @param size
     */
    public void setFontSizea(int size) {
        this.fontsizea = size;
        invalidate();
    }

    /**
     * 设置b右边字体的大小
     *
     * @param size
     */
    public void setFontSizeb(int size) {
        this.fontsizeb = size;
        invalidate();
    }

    /**
     * 设置corlor a 的颜色
     *
     * @param colora
     */
    public void setColora(int colora) {
        this.coclora = colora;
        invalidate();
    }

    /**
     * 设置corlor b 的颜色
     *
     * @param colorb
     */
    public void setColorb(int colorb) {
        this.coclorb = colorb;
        invalidate();
    }

    /**
     * 获取当前手指位置
     *
     * @param e
     * @return
     */
    public int getAreaFlag(MotionEvent e) {
        int top = mThumbMarginTop + 40;
        int bottom = mThumbWidth + mThumbMarginTop + 40;
        if (e.getY() >= top && e.getY() <= bottom && e.getX() >= (mOffsetLow - mThumbWidth / 2) && e.getX() <= mOffsetLow + mThumbWidth / 2) {
            return CLICK_ON_LOW;
        } else if (e.getY() >= top && e.getY() <= bottom && e.getX() >= (mOffsetHigh - mThumbWidth / 2) && e.getX() <= (mOffsetHigh + mThumbWidth / 2)) {
            return CLICK_ON_HIGH;
        } else if (e.getY() >= top
                && e.getY() <= bottom
                && ((e.getX() >= 0 && e.getX() < (mOffsetLow - mThumbWidth / 2)) || ((e.getX() > (mOffsetLow + mThumbWidth / 2))
                && e.getX() <= ((double) mOffsetHigh + mOffsetLow) / 2))) {
            return CLICK_IN_LOW_AREA;
        } else if (e.getY() >= top && e.getY() <= bottom && (((e.getX() > ((double) mOffsetHigh + mOffsetLow) / 2) && e.getX() < (mOffsetHigh - mThumbWidth / 2)) || (e
                .getX() > (mOffsetHigh + mThumbWidth / 2) && e.getX() <= mScollBarWidth))) {
            return CLICK_IN_HIGH_AREA;
        } else if (!(e.getX() >= 0 && e.getX() <= mScollBarWidth && e.getY() >= top && e.getY() <= bottom)) {
            return CLICK_OUT_AREA;
        } else {
            return CLICK_INVAILD;
        }
    }

    /**
     * 设置前滑块的值
     *
     * @param progressLow
     */
    public boolean setProgressLow(double progressLow) {

         mOffsetLow = formatInt(progressLow / total * (mDistance)) + mThumbWidth / 2;
        if (cha(mOffsetHigh, mOffsetLow)) {
//            this.defaultScreenLow = progressLow;
            invalidate();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置后滑块的值
     *
     * @param progressHigh
     */
    public boolean setProgressHigh(double progressHigh) {
         mOffsetHigh = formatInt(progressHigh / total * (mDistance)) + mThumbWidth / 2;
        if (cha(mOffsetHigh, mOffsetLow)) {
//            this.defaultScreenHigh = progressHigh;
            invalidate();
            return true;
        }
        return false;
    }


    /**
     * 设置后滑块位置百分比
     * @param progressHigh
     */
    public  void setdefaultScreenHigh(double progressHigh){
        this.defaultScreenHigh=progressHigh;
        invalidate();

    }

    /**
     * 设置前滑块位置百分比
     * @param progressLow
     */
    public  void setddefaultScreenLow(double progressLow){
        this.defaultScreenLow=progressLow;
        invalidate();

    }

    /**
     * 设置滑动监听
     *
     * @param mListener
     */
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener mListener) {
        this.mBarChangeListener = mListener;
    }

    /**
     * 滑动监听，改变输入框的值
     */
    public interface OnSeekBarChangeListener {
        //滑动时
        public void onProgressChanged(SeekRangeBar seekBar, double progressLow, double progressHigh);
    }

    /**
     * 设置滑动结果为整数
     *
     * @param value
     * @return
     */
    private int formatInt(double value) {
        BigDecimal bd = new BigDecimal(value);
        BigDecimal bd1 = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
        return bd1.intValue();
    }

    /**
     * 设置总的progess值
     *
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }


    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
        float scaleWidth = ((float) w / width);   // 计算缩放比例
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return new BitmapDrawable(newbmp);       // 把 bitmap 转换成 drawable 并返回
    }
}