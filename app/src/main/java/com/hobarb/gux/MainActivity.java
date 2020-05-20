package com.hobarb.gux;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout linearLayout;
    SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager_main);
        linearLayout = findViewById(R.id.ll_main);
       sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

    }
}
