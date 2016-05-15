package pengziyue.com.themestyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_shape:
                intent.setClass(this, ShapeActivity.class);
                break;
            case R.id.btn_state:
                intent.setClass(this, StateActivity.class);
                break;
            case R.id.btn_style:
                intent.setClass(this, StyleActivity.class);
                break;
        }
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 计算屏幕大小，勾股定律计算
         */
        //获取屏幕规格
        DisplayMetrics dm = getResources().getDisplayMetrics();
        //屏幕宽度
        int screenWidth = dm.widthPixels;
        //屏幕高度
        int screeHeight = dm.heightPixels;

        String text = String.format("%d -%d", screenWidth, screeHeight);
        Log.v("MainActivity", text);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
