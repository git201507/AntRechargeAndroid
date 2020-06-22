package com.ant.recharge.common.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by kwc on 2016/10/18.
 */
public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
        private boolean mOnce = false;//是否执行了一次

        /**
       * 初始缩放的比例
       */
        private float initScale;
        /**
       * 缩放比例
       */
        private float midScale;
    /**
     * 可放大的最大比例
     */
    private float maxScale;
    /**
     * 缩放矩阵
     */
    private Matrix scaleMatrix;

    /**
     * 缩放的手势监控类
     */
    private ScaleGestureDetector mScaleGestureDetector;

    public ZoomImageView(Context context)
    {
        this(context,null);
    }
    public ZoomImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }
    public ZoomImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        scaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);

        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        //触摸回调
        setOnTouchListener(this);
    }

    /**
     * 该方法在view与window绑定时被调用，且只会被调用一次，其在view的onDraw方法之前调用
     */
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        //注册监听器
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 该方法在view被销毁时被调用
     */
    @SuppressLint("NewApi") protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        //取消监听器
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 当一个view的布局加载完成或者布局发生改变时，OnGlobalLayoutListener会监听到，调用该方法
     * 因此该方法可能会被多次调用，需要在合适的地方注册和取消监听器
     */
    public void onGlobalLayout()
    {
        if(!mOnce)
        {
            //获得当前view的Drawable
            Drawable d = getDrawable();
            if(d == null)
            {
                return;
            }
            //获得Drawable的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            //获取当前view的宽和高
            int width = getWidth();
            int height = getHeight();
            //缩放的比例,scale可能是缩小的比例也可能是放大的比例，看它的值是大于1还是小于1
            float scale = 1.0f;
            //如果仅仅是图片宽度比view宽度大，则应该将图片按宽度缩小
            if(dw>width&&dh<height)
            {
                scale = width*1.0f/dw;
        }
        //如果图片和高度都比view的大，则应该按最小的比例缩小图片
        if(dw>width&&dh>height)
        {
            scale = Math.min(width*1.0f/dw, height*1.0f/dh);
        }
        //如果图片宽度和高度都比view的要小，则应该按最小的比例放大图片
        if(dw<width&&dh<height)
        {
            scale = Math.min(width*1.0f/dw, height*1.0f/dh);
        }
        //如果仅仅是高度比view的大，则按照高度缩小图片即可
        if(dw<width&&dh>height)
        {
            scale = height*1.0f/dh;
        }

        //初始化缩放的比例
        initScale = scale;
        midScale = initScale*2;
        maxScale = initScale*4;

                //移动图片到达view的中心
                int dx = width/2 - dw/2;
                int dy = height/2 - dh/2;
                scaleMatrix.postTranslate(dx, dy);

                //缩放图片
                scaleMatrix.postScale(initScale, initScale, width/2, height/2);

                setImageMatrix(scaleMatrix);
                mOnce = true;
            }

        }
    /**
     * 获取当前已经缩放的比例
     * @return  因为x方向和y方向比例相同，所以只返回x方向的缩放比例即可
     */
    private float getDrawableScale()
    {
        float[] values = new float[9];
        scaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }
    /**
     * 缩放手势进行时调用该方法
     *
     * 缩放范围：initScale~maxScale
     */
    public boolean onScale(ScaleGestureDetector detector)
    {
        if(getDrawable() == null)
        {
            return true;//如果没有图片，下面的代码没有必要运行
        }

        float scale = getDrawableScale();
        //获取当前缩放因子
        float scaleFactor = detector.getScaleFactor();

        if((scale<maxScale&&scaleFactor>1.0f)||(scale>initScale&&scaleFactor<1.0f))
        {
            //如果缩小的范围比允许的最小范围还要小，就重置缩放因子为当前的状态的因子
            if(scale*scaleFactor<initScale&&scaleFactor<1.0f)
            {
                scaleFactor = initScale/scale;
            }
            //如果缩小的范围比允许的最小范围还要小，就重置缩放因子为当前的状态的因子
            if(scale*scaleFactor>maxScale&&scaleFactor>1.0f)
            {
                scaleFactor = maxScale/scale;
            }
         //            scaleMatrix.postScale(scaleFactor, scaleFactor, getWidth()/2, getHeight()/2);
            scaleMatrix.postScale(scaleFactor, scaleFactor,detector.getFocusX(),
                    detector.getFocusY());
            setImageMatrix(scaleMatrix);//千万不要忘记设置这个，我总是忘记
        }
        return true;
    }
    /**
     * 缩放手势开始时调用该方法
     */
    public boolean onScaleBegin(ScaleGestureDetector detector)
    {
        //返回为true，则缩放手势事件往下进行，否则到此为止，即不会执行onScale和onScaleEnd方法
        return true;
    }
    /**
     * 缩放手势完成后调用该方法
     */
    public void onScaleEnd(ScaleGestureDetector detector)
    {
    }
    /**
     * 监听触摸事件
     */
    public boolean onTouch(View v, MotionEvent event)
         {
             if(mScaleGestureDetector != null)
             {
                 //将触摸事件传递给手势缩放这个类
                 mScaleGestureDetector.onTouchEvent(event);
             }
             return true;
         }

}
