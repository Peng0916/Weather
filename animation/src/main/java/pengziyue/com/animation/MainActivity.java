package pengziyue.com.animation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.btn_View:
                intent.setClass(this, ViewAnimationActivity.class);
                break;
            case R.id.btn_Drawable:
                intent.setClass(this, DrawableAnimationActivity.class);
                break;
            case R.id.btn_Property:
                intent.setClass(this, PropertyAnimationActivity.class);
                break;

        }
        startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
    }
}
