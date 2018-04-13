package com.gcssloop.roundcornerlayouttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.gcssloop.widget.RCRelativeLayout;

public class ExampleActivity extends AppCompatActivity {
    RCRelativeLayout layout;
    CheckBox cb_circle;
    SeekBar seekbar_stroke_width;
    SeekBar seekbar_radius_top_left;
    SeekBar seekbar_radius_top_right;
    SeekBar seekbar_radius_bottom_left;
    SeekBar seekbar_radius_bottom_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        layout = (RCRelativeLayout) findViewById(R.id.rc_layout);
        cb_circle = (CheckBox) findViewById(R.id.cb_circle);
        seekbar_stroke_width = (SeekBar) findViewById(R.id.seekbar_stroke_width);
        seekbar_radius_top_left = (SeekBar) findViewById(R.id.seekbar_radius_top_left);
        seekbar_radius_top_right = (SeekBar) findViewById(R.id.seekbar_radius_top_right);
        seekbar_radius_bottom_left = (SeekBar) findViewById(R.id.seekbar_radius_bottom_left);
        seekbar_radius_bottom_right = (SeekBar) findViewById(R.id.seekbar_radius_bottom_right);

        //圆形
        cb_circle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layout.setRoundAsCircle(isChecked);
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

    }

    private int getProgressRadius(int progress) {
        int size = getResources().getDimensionPixelOffset(R.dimen.size_example_image);
        return (int) ((float) progress / 100 * size) / 2;
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
