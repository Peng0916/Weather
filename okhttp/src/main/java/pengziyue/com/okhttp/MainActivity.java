package pengziyue.com.okhttp;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public final static String BASE_URL = "http://192.168.43.156:8086/androidcloud/";

    private final static String TAG = "MainActivity";

    private OkHttpClient client;

    /**
     * OKHTTP  同步GET请求方式
     */
    @OnClick(R.id.btn_do_get)
    public void doGet() {
        //1耗时的操作需在子线程中完成 例：访问网络
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //访问网络
                    // 2 实例化OkHttp 客户端
                    client = new OkHttpClient();
                    //3 实例化请求对象
                    Request request = new Request.Builder().url
                            (BASE_URL + "LoginServlet?username=tom&password=admin").build();
                    //4 响应对象
                    Response response = client.newCall(request).execute();
                    //云端服务器返回的字符串
                    final String result = response.body().string();
                    //子线程不能更新UI，必须用runOnUiThread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //更新UI
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                            Log.v(TAG, result);
                        }
                    });
                } catch (Exception e) {
                    Log.v(TAG, "访问网络出错！");
                }

            }
        }).start();
    }

    /**
     * OKHTTP  异步GET请求方式
     */
    @OnClick(R.id.btn_do_get_enqueue)
    public void doGetEnqueue() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                client = new OkHttpClient();
                Request request = new Request.Builder().
                        url(BASE_URL + "LoginServlet?username=tom&password=admin").build();
                //异步获得响应对象
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.v(TAG, "访问网络出错！");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                                Log.v(TAG, result);
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * 异步POST请求方式
     */
    @OnClick(R.id.btn_do_get_post)
    public void GetPost() {
        PostTask task = new PostTask();
        //参数来自输入的用户名或密码
        task.execute("tom", "admin");

    }

    private class PostTask extends AsyncTask<String, Integer, String> {
        String result = "";

        @Override
        protected String doInBackground(String... params) {
            client = new OkHttpClient();

            //封装表单数据
            RequestBody body = new FormBody.Builder().add("username", params[0])
                    .add("password", params[1]).build();

            Request request = new Request.Builder().url(BASE_URL + "LoginServlet").post(body).build();

         /*        client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        result = response.body().string();
                    }

                });
                */
            Response response = null;
            try {
                response = client.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v(TAG, s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_do_get_cache)
    public void doCache() {
        // 获得当前 APP 的缓存目录
        File cacheDirectory = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheDirectory, cacheSize);
        // 实例化带缓存的 OkHttp 对象
        client = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(BASE_URL + "BookJsonServlet")
                            .build();
                    Response response = client.newCall(request).execute();
                    String json = response.body().string();

                    Log.v(TAG, json);
                    // 通过 runOnUiThread(action) 更新 UI,在界面显示来自云端的数据

                    // 测试缓存 ********************************
                    Response response1 = client.newCall(request).execute();
                    if (!response1.isSuccessful())
                        throw new IOException("Unexpected code " + response1);

                    String response1Body = response1.body().string();
                    Log.v(TAG, "Response 1 response:          " + response1);
                    Log.v(TAG, "Response 1 cache response:    " + response1.cacheResponse());
                    Log.v(TAG, "Response 1 network response:  " + response1.networkResponse());

                    Response response2 = client.newCall(request).execute();
                    if (!response2.isSuccessful())
                        throw new IOException("Unexpected code " + response2);

                    String response2Body = response2.body().string();
                    Log.v(TAG, "Response 2 response:          " + response2);
                    Log.v(TAG, "Response 2 cache response:    " + response2.cacheResponse());
                    Log.v(TAG, "Response 2 network response:  " + response2.networkResponse());

                    Log.v(TAG, "Response 2 equals Response 1? " + response1Body.equals(response2Body));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 上传文件 *************************************
     */
    private static final int TAKE_PHOTO = 0;
    private static final int CHOOSE_PHONE = 1;
    private static final int SCALE = 5; // 照片缩小比例
    private String uploadFile; // 上传文件

    @OnClick(R.id.btn_do_get_upload)
    public void upload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("图片来源:");
        builder.setNegativeButton("取消", null);
        builder.setItems(
                new CharSequence[]{"相机", "相册"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case TAKE_PHOTO:
                                // 打开 相机 App
                                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                Uri imageUri = Uri.fromFile(
                                        new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(openCameraIntent, TAKE_PHOTO);
                                break;
                            case CHOOSE_PHONE:
                                // 从 相册 中选择图片
                                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                startActivityForResult(openAlbumIntent, CHOOSE_PHONE);
                                break;
                        }
                    }
                });

        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    // 将保存在本地的图片取出并缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile(
                            Environment.getExternalStorageDirectory() + "/image.jpg");
                    // 缩小相机的图片
                    Bitmap newBitmap = ImageTools.zoomBitmap(
                            bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                    // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    bitmap.recycle();

                    // 同时复制至存储相片的目录
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String filename = sdf.format(new Date());
                    // 文件名
                    filename = "IMG_" + filename;
                    // 图片目录
                    String dir = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            .getAbsolutePath()
                            + "/Camera";
                    ImageTools.savePhotoToSDCard(newBitmap, dir, filename);
                    // 上传文件名
                    uploadFile = dir + "/" + filename + ".png";
                    break;
                case CHOOSE_PHONE:
                    ContentResolver resolver = this.getContentResolver();
                    // 照片的原始资源地址
                    Uri originalUri = data.getData();
                    try {
                        // 通过内容访问者和图片url获得bitmap的图片(相册中的原图)
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(
                                    photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                            // 释放原始图片占用的内存，防止out of memory异常发生
                            photo.recycle();

                            // 上传文件名
                            uploadFile = ImageUri.getImageAbsolutePath(this, originalUri);
                            Log.v("MainActivity", originalUri.toString());
                        }
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(this, "读取文件,出错啦", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            Log.v("MainActivity", uploadFile);

            // 上传文件
            final String IMGUR_CLIENT_ID = "9199fdef135c122";
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");

            client = new OkHttpClient();
            final File file = new File(uploadFile);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("title", "Square Logo")
                                .addFormDataPart("image", file.getName(),
                                        RequestBody.create(MEDIA_TYPE_PNG, file))
                                .build();

                        Request request = new Request.Builder()
                                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                                .url(BASE_URL + "UploadServlet")
                                .post(requestBody)
                                .build();

                        Response response = client.newCall(request).execute();
                        if (!response.isSuccessful())
                            throw new IOException("Unexpected code " + response);

                        Log.v(TAG, response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }
}
