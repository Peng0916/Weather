package pengziyue.com.loginweb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.txt_username)
    EditText txtUsername;
    @Bind(R.id.txt_password)
    EditText txtPassword;
    @Bind(R.id.txt_image_url)
    EditText txtImageUrl;
    @Bind(R.id.image_view)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    // **** doGet() *************************************
    @OnClick(R.id.btn_login_get)
    public void btnLoginGetClick() {
        // doGet step 1:获得用户名和密码
        final String username = txtUsername.getText().toString();
        final String password = txtPassword.getText().toString();
        //子线程访问网络 耗时操作的2种方法 异步任务和线程  通过匿名的子线程访问网络

        new Thread(new Runnable() {
            @Override
            public void run() {
                //执行(写入)doget()方法,并且返回服务器的数据
                // doGet step 3: 执行 doGet(),并且返回服务器的数据
                // 登录成功......
                final String result = NetService.doGet(username, password).trim();
                //子线程中运行UI线程
                // doGet step 4:子线程中运行 UI 线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI 处理来自服务器的数据   doGet step 5:更新 UI (处理来自服务器的数据   )
                        //result.length() > 0
                        if (!TextUtils.isEmpty(result)) {
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Oh no!访问网络出错!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.btn_login_list)
    public void btnLoginPostClick() {
        // dopost step 1:获得用户名和密码
        final String username = txtUsername.getText().toString();
        final String password = txtPassword.getText().toString();
        //异步任务访问网络
        //2.实例化异步任务，并且执行
        PostTask task = new PostTask();
        task.execute(username, password);
    }

    private class PostTask extends AsyncTask<String, Integer, String> {
        //3.后台线程访问网络
        @Override
        protected String doInBackground(String... params) {
            //4.执行dopost 并且返回服务器的数据
            String result = NetService.doPost(params[0], params[1]);
            return result;
        }

        //5.接收后台线程返回的数据
        @Override
        protected void onPostExecute(String result) {
            //6.更新UI，处理来自服务器的数据
            if (!TextUtils.isEmpty(result.trim())) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "访问网络程序出错", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick(R.id.btn_image_url)
    public void btnImageUrlClick() {
        //1.获得云端图片路径
        String url = NetService.BASE_URL + "image/" + txtImageUrl.getText().toString();
        //2.执行异步任务
        LoadImageTask task = new LoadImageTask();
        task.execute(url);

    }

    private class LoadImageTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            //通过URL路径获得返回Bitmap对象

            return NetService.load(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //4.更新UI。
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(MainActivity.this, "加载图片失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //*****************************************************************json
    @OnClick(R.id.btn_json)
    public void btnJsonClick() {
//        Intent intent = new Intent(MainActivity.this,BookActivity.class);
//        startActivity(intent);
        startActivity(new Intent(this, BookActivity.class));

    }

}
