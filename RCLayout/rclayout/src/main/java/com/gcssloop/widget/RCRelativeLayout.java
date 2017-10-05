/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-09-11 15:29:05
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.gcssloop.rclayout.R;


/**
 * 作用：圆角相对布局
 * 作者：GcsSloop
 */
public class RCRelativeLayout extends RelativeLayout {
    private float[] radii = new float[8];   // top-left, top-right, bottom-right, bottom-left
    private Path mClipPath;                 // 剪裁区域路径
    private Path mStrokePath;               // 描边区域路径
    private Paint mPaint;                   // 画笔
    private boolean mRoundAsCircle = false; // 圆形
    private int mStrokeColor;               // 描边颜色
    private int mStrokeWidth;               // 描边半径
    private Region mAreaRegion;             // 内容区域

    public RCRelativeLayout(Context context) {
        this(context, null);
    }

    public RCRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RCRelativeLayout);
        mRoundAsCircle = ta.getBoolean(R.styleable.RCRelativeLayout_round_as_circle, false);
        mStrokeColor = ta.getColor(R.styleable.RCRelativeLayout_stroke_color, Color.WHITE);
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.RCRelativeLayout_stroke_width, 0);
        int roundCorner = ta.getDimensionPixelSize(R.styleable.RCRelativeLayout_round_corner, 0);
        int roundCornerTopLeft = ta.getDimensionPixelSize(
                R.styleable.RCRelativeLayout_round_corner_top_left, roundCorner);
        int roundCornerTopRight = ta.getDimensionPixelSize(
                R.styleable.RCRelativeLayout_round_corner_top_right, roundCorner);
        int roundCornerBottomLeft = ta.getDimensionPixelSize(
                R.styleable.RCRelativeLayout_round_corner_bottom_left, roundCorner);
        int roundCornerBottomRight = ta.getDimensionPixelSize(
                R.styleable.RCRelativeLayout_round_corner_bottom_right, roundCorner);

        radii[0] = roundCornerTopLeft;
        radii[1] = roundCornerTopLeft;

        radii[2] = roundCornerTopRight;
        radii[3] = roundCornerTopRight;

        radii[4] = roundCornerBottomRight;
        radii[5] = roundCornerBottomRight;

        radii[6] = roundCornerBottomLeft;
        radii[7] = roundCornerBottomLeft;

        mClipPath = new Path();
        mStrokePath = new Path();
        mAreaRegion = new Region();
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        RectF areas = new RectF();
        areas.left = getPaddingLeft();
        areas.top = getPaddingTop();
        areas.right = w - getPaddingRight();
        areas.bottom = h - getPaddingBottom();
        mClipPath.reset();
        if (mRoundAsCircle) {
            float d = areas.width() >= areas.height() ? areas.height() : areas.width();
            float r = d / 2;
            PointF center = new PointF(w / 2, h / 2);
            mClipPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
            mClipPath.addRect(areas, Path.Direction.CW);
            mClipPath.addCircle(center.x, center.y, r, Path.Direction.CW);
            mStrokePath.addCircle(center.x, center.y, r, Path.Direction.CW);
        } else {
            mClipPath.setFillType(Path.FillType.EVEN_ODD);
            mClipPath.addRoundRect(areas, radii, Path.Direction.CW);
            mStrokePath.addRoundRect(areas, radii, Path.Direction.CW);
        }
        Region clip = new Region((int) areas.left, (int) areas.top,
                                 (int) areas.right, (int) areas.bottom);
        mAreaRegion.setPath(mStrokePath, clip);
    }

    @Override protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null, Canvas
                .ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        if (mStrokeWidth > 0) {
            mPaint.setXfermode(null);
            mPaint.setStrokeWidth(mStrokeWidth * 2);
            mPaint.setColor(mStrokeColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mStrokePath, mPaint);

        }
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mClipPath, mPaint);
        canvas.restore();
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mAreaRegion.contains((int) ev.getX(), (int) ev.getY())) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }
}
