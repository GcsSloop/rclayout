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
 * Last modified 2018-04-16 13:23:16
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.roundcornerlayouttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.gcssloop.widget.RCRelativeLayout;
import com.gcssloop.widget.helper.RCHelper;

public class ExampleActivity extends AppCompatActivity {
    RCRelativeLayout layout;
    CheckBox cb_clip_background;
    CheckBox cb_circle;
    CheckBox cb_background;
    SeekBar seekbar_stroke_width;
    SeekBar seekbar_radius_top_left;
    SeekBar seekbar_radius_top_right;
    SeekBar seekbar_radius_bottom_left;
    SeekBar seekbar_radius_bottom_right;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        layout = (RCRelativeLayout) findViewById(R.id.rc_layout);
        cb_circle = (CheckBox) findViewById(R.id.cb_circle);
        cb_background = (CheckBox) findViewById(R.id.cb_background);
        cb_clip_background = (CheckBox) findViewById(R.id.cb_clip_background);
        seekbar_stroke_width = (SeekBar) findViewById(R.id.seekbar_stroke_width);
        seekbar_radius_top_left = (SeekBar) findViewById(R.id.seekbar_radius_top_left);
        seekbar_radius_top_right = (SeekBar) findViewById(R.id.seekbar_radius_top_right);
        seekbar_radius_bottom_left = (SeekBar) findViewById(R.id.seekbar_radius_bottom_left);
        seekbar_radius_bottom_right = (SeekBar) findViewById(R.id.seekbar_radius_bottom_right);

        //checked状态
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        layout.setOnCheckedChangeListener(new RCHelper.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View v, boolean isChecked) {
                toast.setText("checked = " + isChecked);
                toast.show();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.toggle();
            }
        });
        //背景色
        cb_background.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout.setBackgroundResource(R.color.colorBackground);
                } else {
                    layout.setBackground(null);
                }
                cb_clip_background.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        //剪裁背景
        cb_clip_background.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layout.setClipBackground(isChecked);
            }
        });
        //圆形
        cb_circle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layout.setRoundAsCircle(isChecked);

                findViewById(R.id.layout_radius).setVisibility(isChecked ? View.INVISIBLE : View.VISIBLE);
            }
        });
        //边框粗细
        seekbar_stroke_width.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                layout.setStrokeWidth(progress);
            }
        });
        //左上角半径
        seekbar_radius_top_left.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                layout.setTopLeftRadius(getProgressRadius(progress));
            }
        });
        //右上角半径
        seekbar_radius_top_right.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                layout.setTopRightRadius(getProgressRadius(progress));
            }
        });
        //左下角半径
        seekbar_radius_bottom_left.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                layout.setBottomLeftRadius(getProgressRadius(progress));
            }
        });
        //右下角半径
        seekbar_radius_bottom_right.setOnSeekBarChangeListener(new SimpleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                layout.setBottomRightRadius(getProgressRadius(progress));
            }
        });
        seekbar_stroke_width.setProgress(getResources().getDimensionPixelSize(R.dimen.default_stroke_width));

        cb_circle.setChecked(true);
    }

    private int getProgressRadius(int progress) {
        int size = getResources().getDimensionPixelOffset(R.dimen.size_example_image);
        return (int) ((float) progress / 100 * size) / 2;
    }

    public void clickAntiAlias(View view) {
        startActivity(new Intent(this,AntiAliasActivity.class));
    }


    public static abstract class SimpleSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
