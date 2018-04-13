/*
 * Copyright 2018 GcsSloop
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
 * Last modified 2018-03-20 17:09:03
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.widget.helper;


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
import android.view.View;

import com.gcssloop.rclayout.R;

public class RCHelper {
    public float[] radii = new float[8];   // top-left, top-right, bottom-right, bottom-left
    public Path mClipPath;                 // 剪裁区域路径
    public Paint mPaint;                   // 画笔
    public boolean mRoundAsCircle = false; // 圆形
    public int mStrokeColor;               // 描边颜色
    public int mStrokeWidth;               // 描边半径
    public boolean mClipBackground;        // 是否剪裁背景
    public Region mAreaRegion;             // 内容区域
    public int mEdgeFix = 10;              // 边缘修复
    public RectF mLayer;                   // 画布图层大小

    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RCAttrs);
        mRoundAsCircle = ta.getBoolean(R.styleable.RCAttrs_round_as_circle, false);
        mStrokeColor = ta.getColor(R.styleable.RCAttrs_stroke_color, Color.WHITE);
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.RCAttrs_stroke_width, 0);
        mClipBackground = ta.getBoolean(R.styleable.RCAttrs_clip_background, false);
        int roundCorner = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner, 0);
        int roundCornerTopLeft = ta.getDimensionPixelSize(
                R.styleable.RCAttrs_round_corner_top_left, roundCorner);
        int roundCornerTopRight = ta.getDimensionPixelSize(
                R.styleable.RCAttrs_round_corner_top_right, roundCorner);
        int roundCornerBottomLeft = ta.getDimensionPixelSize(
                R.styleable.RCAttrs_round_corner_bottom_left, roundCorner);
        int roundCornerBottomRight = ta.getDimensionPixelSize(
                R.styleable.RCAttrs_round_corner_bottom_right, roundCorner);
        ta.recycle();

        radii[0] = roundCornerTopLeft;
        radii[1] = roundCornerTopLeft;

        radii[2] = roundCornerTopRight;
        radii[3] = roundCornerTopRight;

        radii[4] = roundCornerBottomRight;
        radii[5] = roundCornerBottomRight;

        radii[6] = roundCornerBottomLeft;
        radii[7] = roundCornerBottomLeft;

        mClipPath = new Path();
        mAreaRegion = new Region();
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
    }

    public void onSizeChanged(View view, int w, int h) {
        mLayer = new RectF(0, 0, w, h);
        refreshRegion(view);
    }

    public void refreshRegion(View view) {
        int w = (int) mLayer.width();
        int h = (int) mLayer.height();
        RectF areas = new RectF();
        areas.left = view.getPaddingLeft();
        areas.top = view.getPaddingTop();
        areas.right = w - view.getPaddingRight();
        areas.bottom = h - view.getPaddingBottom();
        mClipPath.reset();
        if (mRoundAsCircle) {
            float d = areas.width() >= areas.height() ? areas.height() : areas.width();
            float r = d / 2;
            PointF center = new PointF(w / 2, h / 2);
            mClipPath.addCircle(center.x, center.y, r, Path.Direction.CW);
        } else {
            mClipPath.addRoundRect(areas, radii, Path.Direction.CW);
        }
        mClipPath.moveTo(-mEdgeFix, -mEdgeFix);  // 通过空操作让Path区域占满画布
        mClipPath.moveTo(w + mEdgeFix, h + mEdgeFix);
        Region clip = new Region((int) areas.left, (int) areas.top,
                (int) areas.right, (int) areas.bottom);
        mAreaRegion.setPath(mClipPath, clip);
    }

    public void onClipDraw(Canvas canvas) {
        if (mStrokeWidth > 0) {
            // 支持半透明描边，将与描边区域重叠的内容裁剪掉
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeWidth(mStrokeWidth * 2);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);
            // 绘制描边
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setColor(mStrokeColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);
        }
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mClipPath, mPaint);
    }
}
