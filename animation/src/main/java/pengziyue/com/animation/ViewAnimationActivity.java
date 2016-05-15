package pengziyue.com.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ViewAnimationActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        imageView = (ImageView) findViewById(R.id.imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alpha:
                doAlpha();
                break;
            case R.id.action_scale:
                imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));
                break;
            case R.id.action_rotate:
                imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
                break;
            case R.id.action_translate:
                imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
                break;
            case R.id.action_set:
                imageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.set));
                break;

        }
        return true;
    }

    /**
     * 渐变动画    方案一 其它控件不可用
     private void doAlpha() {
     AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
     animation.setDuration(1000);
     animation.setRepeatMode(Animation.REVERSE);
     animation.setRepeatCount(Animation.INFINITE);
     imageView.startAnimation(animation);
     //监听事件
     animation.setAnimationListener(new Animation.AnimationListener() {
    @Override public void onAnimationStart(Animation animation) {

    }

    @Override public void onAnimationEnd(Animation animation) {

    }

    @Override public void onAnimationRepeat(Animation animation) {

    }
    });
     }
     */

    /**
     * 渐变动画   方案二  其它控件可用此方法
     */
    private void doAlpha() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        imageView.startAnimation(animation);

    }

}
