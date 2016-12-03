package org.lema.notasapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.lema.notasapp.R;

import java.util.HashMap;

/**
 * Created by Cadu on 05/11/2016.
 */

public class DashboardActivity extends AppCompatActivity {
    private SliderLayout mSlider;
    private LinearLayout mLayoutNotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        mSlider = (SliderLayout)findViewById(R.id.slider);

        preencheReferencias();

        configuraSlider();

        ativaBotoesDash();
    }

    private void preencheReferencias() {
        mSlider = (SliderLayout)findViewById(R.id.slider);
        mLayoutNotas = (LinearLayout) findViewById(R.id.ll_dashboard_button_grades);
    }

    private void configuraSlider() {
        HashMap<Integer, Integer> file_maps = new HashMap<Integer, Integer>();
        file_maps.put(R.string.dashboard_slider_news_title_1, R.drawable.temp1);

        for(Integer index : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(getResources().getString(index))
                    .image(file_maps.get(index))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", getResources().getString(index));

            mSlider.addSlider(textSliderView);
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setCustomAnimation(new DescriptionAnimation());
            mSlider.setDuration(4000);
        }
    }

    private void ativaBotoesDash() {
        mLayoutNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, BoletimActivity.class);
                startActivity(intent);
            }
        });

    /*    mLayoutNotas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(DashboardActivity.this, BoletimActivity.class);
                startActivity(intent);
                return true;
            }
        });*/
    }


    @Override
    protected void onStop(){
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();
        super.onStop();
    }

    /*
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    */
}
