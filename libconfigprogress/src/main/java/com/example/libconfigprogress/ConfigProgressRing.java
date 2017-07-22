package com.example.libconfigprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by USER on 2017/7/14.
 */

public class ConfigProgressRing extends ConfigProgress {
    /**
     * 主题颜色
     */
    public final static int DefaultColor = 0xFF6E9FFF;
    public final static double Pi = 3.1415926;
    public final static int DefaultSpeed = 2000;
    /**
     * 动画总时长
     */
    public long duratime = 100 * 1000L;
    private Context context;
    /**
     * 圆环颜色
     */
    private int ring_color = DefaultColor;
    /**
     * 旋转圆颜色
     */
    private int circle_color = DefaultColor;
    /**
     * 文本颜色
     */
    private int text_color = DefaultColor;
    /**
     * 文本大小
     */
    private int text_size = 12;
    /**
     * 圆环大小（根据控件宽与旋转圆大小计算，设置无效）
     */
    private int ring_r = 0;
    /**
     * 圆环线大小
     */
    private int ring_w = 5;
    /**
     * 旋转圆半径
     */
    private int cicle_r = 5;
    /**
     * 旋转圆旋转速度
     */
    private float speed=1.0f;
    private int mCenterx = 0;
    private int mCentery = 0;

    private Paint RingPaint;
    private Paint CirclePaint;
    private Paint TextPaint;

    private float progress;
    private float TextProgress;
    private ProgressAnimation animal;
    private CountDownTimer TextTimer;
    private int mark;
    private RectF mRectF = new RectF();
    private SweepGradient ring;
    public static final int PROGRESSTYPE_AUTO=0;
    public static final int PROGRESSTYPE_MULTI=1;
    private int ProgrssType=PROGRESSTYPE_AUTO;

    //圆环颜色
    private static int[] doughnutColors = new int[]{
            DefaultColor,
            Color.WHITE,
            DefaultColor,
            Color.WHITE};
    private static float[] doughnutPosition = new float[]{
            0,
            0.5f,
            0.5f,
            1};

    public ConfigProgressRing(Context context) {
        this(context, null);
    }

    public ConfigProgressRing(Context context,int w,int h) {
        this(context, null);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(w,h);
        setLayoutParams(params);
    }


    public ConfigProgressRing(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ConfigProgressRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        try {
            TypedArray t = context.obtainStyledAttributes(attrs,
                    R.styleable.configprogress, 0, 0);
            ring_color = t.getColor(R.styleable.configprogress_configprogress_ring_color, DefaultColor);
            circle_color = t.getColor(R.styleable.configprogress_configprogress_circle_color, DefaultColor);
            ring_w = t.getDimensionPixelSize(R.styleable.configprogress_configprogress_ring_w, ring_w);
            cicle_r = t.getDimensionPixelSize(R.styleable.configprogress_configprogress_circle_R, cicle_r);
            text_color = t.getColor(R.styleable.configprogress_configprogress_text_color, DefaultColor);
            text_size = t.getDimensionPixelSize(R.styleable.configprogress_configprogress_text_size, text_size);
            speed=t.getInt(R.styleable.configprogress_configprogress_circle_speed,1);
            ProgrssType=t.getInt(R.styleable.configprogress_configprogress_type,0);
            t.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        text_size = Utils.sp2px(context, text_size);
        ring_w=Utils.dip2px(context,ring_w);
        cicle_r=Utils.dip2px(context,cicle_r);
        setBackgroundColor(Color.TRANSPARENT);
        setProgressListner(listner);
    }

    private void initPaint() {
        RingPaint = new Paint();
        RingPaint.setAntiAlias(true);
        RingPaint.setStyle(Paint.Style.STROKE);
        RingPaint.setStrokeWidth(ring_w);
        RingPaint.setStrokeCap(Paint.Cap.ROUND);
        RingPaint.setShader(ring);
//        RingPaint.setColor(ring_color);

        CirclePaint = new Paint();
        CirclePaint.setAntiAlias(true);
        CirclePaint.setColor(circle_color);

        TextPaint = new Paint();
        TextPaint.setAntiAlias(true);
        TextPaint.setColor(text_color);
        TextPaint.setTextSize(text_size);
    }

    private void initAnimal() {
        animal = new ProgressAnimation();
        animal.setRepeatCount(-1);
        animal.setRepeatMode(Animation.RESTART);
        animal.setInterpolator(new LinearInterpolator());
        animal.setAnimationProgressListner(circleListner);
        animal.setDuration((long) (DefaultSpeed*speed));
        TextTimer = new CountDownTimer(duratime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextProgress = (1 - ((float) millisUntilFinished) / duratime);
            }

            @Override
            public void onFinish() {
                Log.e("dxsTest","onFinish"+TextProgress);
                TextProgress = 1;
                if (listner != null) {
                    listner.onFinish(ConfigProgressRing.this,mark);
                }
                listner=null;
            }
        };
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (listner != null) {
            listner.onStart(this);
        }
    }

    /**
     * 开始执行动画
     */
    public void start() {
        initPaint();
        if (animal == null || TextTimer == null) {
            initAnimal();
        }
        if(ProgrssType==PROGRESSTYPE_AUTO){
            TextTimer.start();
        }else{
            //Empty
        }
        this.startAnimation(animal);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (listner != null) {
            listner.onFinish(this,mark);
        }
        listner=null;
    }

    /**
     * 回收动画
     */
    public void clear() {
        if (TextTimer != null) {
            TextTimer.onFinish();
            TextTimer.cancel();
        }
        this.clearAnimation();
    }

    /**
     * 暂停动画（暂未实现）
     */
    public void pause() {
        //empty
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int temp = Utils.dip2px(context, ring_r) / 2;
        mRectF.set(temp, temp, w - temp, h - temp);
        ring=new SweepGradient(getWidth()/2,getHeight()/2,doughnutColors,doughnutPosition);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mCenterx = widthSize / 2;
        mCentery = heightSize / 2;
        ring_r = getRingDrawR();
        int temp = Utils.dip2px(context, ring_r) / 2;
        mRectF.set(temp, temp, widthSize - temp, heightSize - temp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(-progress*360,mCenterx,mCentery);
        drawRing(canvas);
        drawCircle(canvas,0,ring_w/2);
        drawCircle(canvas,0.5f,ring_w/2);
        canvas.restore();
        DrawText(canvas, getPrecent());
    }

    /**
     * 绘制圆环
     *
     * @param canvas
     */
    private void drawRing(Canvas canvas) {
        canvas.drawCircle(mCenterx, mCentery, getRingDrawR(), RingPaint);
    }

    private void drawCircle(Canvas canvas, float progress) {
        int[] center = getCirclrR(progress * 360);
        canvas.drawCircle(center[0], center[1], cicle_r, CirclePaint);
    }

    private void drawCircle(Canvas canvas,float progress,int r_w){
        int[] center = getCirclrR(progress*360);
        canvas.drawCircle(center[0], center[1], r_w, CirclePaint);
    }

    private void DrawText(Canvas canvas, String text) {
        Paint.FontMetricsInt fontMetrics = TextPaint.getFontMetricsInt();
        int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetrics.ascent + fontMetrics.descent) / 2 - fontMetrics.bottom);
        TextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, mRectF.centerX(), baseline, TextPaint);
    }


    public int getRing_color() {
        return ring_color;
    }

    public void setRing_color(int ring_color) {
        this.ring_color = ring_color;
    }

    public int getCircle_color() {
        return circle_color;
    }

    public void setCircle_color(int circle_color) {
        this.circle_color = circle_color;
    }

    public int getText_color() {
        return text_color;
    }

    public void setText_color(int text_color) {
        this.text_color = text_color;
    }

    public int getText_size() {
        return text_size;
    }

    public void setText_size(int text_size) {
        this.text_size = text_size;
    }

    public int getCicle_r() {
        return cicle_r;
    }

    public void setCicle_r(int cicle_r) {
        this.cicle_r = cicle_r;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public long getDuratime() {
        return duratime;
    }

    public void setDuratime(long duratime) {
        this.duratime = duratime;
    }

    public int getProgrssType() {
        return ProgrssType;
    }

    public void setProgrssType(int progrssType) {
        ProgrssType = progrssType;
    }

    //获取显示文本
    private String getPrecent() {
        return "" + getProgress() + "%";
    }
    //获取旋转圆的坐标
    private int[] getCirclrR(double arc) {
        int x = (int) (mCenterx + ring_r * Math.cos(arc * Pi / 180));
        int y = (int) (mCentery + ring_r * Math.sin(arc * Pi / 180));
        return new int[]{x, y};
    }
    //获取环宽
    private int getRingDrawR() {
        return (getWidth() - ring_w) / 2 - cicle_r;
    }

    /**
     * 获取进度
     * @return 进度百分比
     */
    public int getProgress() {
        return Math.round(TextProgress * 100);
    }

    /**
     * 设置进度
     * @param progress
     * @param mark
     */
    public void setProgress(int progress,int mark){
        TextProgress=(float)progress/100;
        this.mark=mark;
        if(progress>=100&&TextTimer!=null){
            TextTimer.onFinish();
        }
    }

    private ProgressAnimation.AnimationProgressListner circleListner = new ProgressAnimation.AnimationProgressListner() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            progress = interpolatedTime;
            postInvalidate();
        }
    };

    private ProgressListner listner=new ProgressListner() {
        @Override
        public void onFinish(ConfigProgress configProgress, int mark) {
            //Empty
        }
    };

    /**
     * 设置状态监听
     * @param mPercentListener
     */
    public void setProgressListner(ProgressListner mPercentListener) {
        this.listner = mPercentListener;
    }
}
