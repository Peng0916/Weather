package pengziyue.com.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class DrawableAnimationActivity extends AppCompatActivity {
    private ImageView imageView;
    //帧动画
    private AnimationDrawable animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_animation);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.drawable_animation);
        //将图片变为帧转换成动画
        animation = (AnimationDrawable) imageView.getDrawable();


    }

    public void onClick(View view) {
        if (animation.isRunning()) {
            animation.stop();
        } else {
           // Animation anim = AnimationUtils.loadAnimation(this, R.anim);
           // imageView.startAnimation(anim);
            animation.start();
        }
    }
}
