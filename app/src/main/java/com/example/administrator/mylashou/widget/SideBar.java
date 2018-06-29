package com.example.administrator.mylashou.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.mylashou.R;

public class SideBar extends View {

    public static String[] b={"#","A","B","C","D","E","F","G","H","I","J","K",
                     "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    private int choose;
    private Paint paint = new Paint();
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;



    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int heigh = getHeight();
        int singleHeigh = heigh/b.length;


        paint.setColor(Color.rgb(154,154,154));
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(40);
        for(int i = 0;i<b.length;i++){
            if (i==choose){

                paint.setColor(Color.parseColor("#3d3d3d"));
                paint.setFakeBoldText(true);

            }

            float xPos = width/2-paint.measureText(b[i]);
            float yPos = singleHeigh*(i+1);
            canvas.drawText(b[i],xPos,yPos,paint);

        }
    }


    public interface OnTouchingLetterChangedListener{
        public void onTouchingLetterChanged(String s);

    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int)(y/getHeight()*b.length);
        switch (action){
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose=-1;
                invalidate();
                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if(oldChoose!=c){
                    if(c>=0&&c<b.length){
                        if(listener!=null){
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        choose = c;
                        invalidate();
                    }

                }
                break;
        }
        return true;
    }
}
