package pengziyue.com.volley.upload;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pengziyue.com.volley.R;
import pengziyue.com.volley.VolleyUtils;

public class UploadActivity extends AppCompatActivity {
    @Bind(R.id.txt_isbn)
    EditText txtIsbn;
    @Bind(R.id.txt_title)
    EditText txtTitle;
    @Bind(R.id.txt_author)
    EditText txtAuthor;
    @Bind(R.id.image_view)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
    }

    // *** 提交表单数据 **************************
    @OnClick(R.id.btn_submit)
    public void btnSubmitClick() {
        //  1.验证表单数据
        if (TextUtils.isEmpty(txtTitle.getText().toString())) {
            Toast.makeText(this, "书名不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        //写完下面uploadFile的方法再写的此类  然后进行上传图片
        if (TextUtils.isEmpty(uploadFile)) {
            Toast.makeText(this, "请选择封面!", Toast.LENGTH_SHORT).show();
            return;
        }
        // 2.上传图片
        String requestURL = VolleyUtils.BASE_URL + "UploadServlet";
        File file = new File(uploadFile);
        LoadDialog dialog = new LoadDialog(this);

        dialog.execute(new LoadDialog.Callback() {
            @Override
            public void getResult(Object obj) {
                System.out.println(obj + "");
            }
        }, UploadService.class, "postUseUrlConnection", requestURL, file);

        // 3.提交表单数据
        // (请求方式POST|Get,url,listener, errorListener)
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                VolleyUtils.getAbsoluteUrl("BookServlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UploadActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", error.getMessage(), error);
                    }
                }
        ) {
            // 设置POST参数(在StringRequest的匿名类中重写getParams()方法)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("isbn", txtIsbn.getText().toString());
                params.put("title", txtTitle.getText().toString());
                params.put("author", txtAuthor.getText().toString());
                // 设置默认的书籍封面 (木有图片路径)

                //params.put("image", "/images/qq.jpg");
                params.put("image", "/images" + uploadFile.substring
                        (uploadFile.lastIndexOf("/"), uploadFile.length()));
                return params;
            }
        };
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyUtils.getInstance(this).cancelRequests(VolleyUtils.TAG);
    }

    // *** 上传图片 *****************************
    private static final int TAKE_PHOTO = 0;
    private static final int CHOOSE_PHOTO = 1;

    private static final int SCALE = 5;//图片缩小比例
    private String uploadFile;//上传文件

    @OnClick(R.id.btn_choose_photo)
    public void btnChoosePhotoClick() {
        //选择相片的操作
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("图片来源");
        //  builder.setNegativeButton("取消",);
        builder.setItems(new CharSequence[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case TAKE_PHOTO:
                        //打开相机 app  打开相机需要添加权限，对内存卡进行操作
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//有牌照和录像
                        //getDownloadCacheDirectory  SD卡根目录 后面填写文件名作为临时文件
                        Uri imageUri = Uri.fromFile(new File(Environment.getDownloadCacheDirectory(), "image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        //带个照片的请求码，进行区分
                        startActivityForResult(openCameraIntent, TAKE_PHOTO);
                        break;
                    case CHOOSE_PHOTO:
                        //从相册中选择图片
                        //ACTION_GET_CONTENT  获得图片内容
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        //获得图片类型
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PHOTO);
                        break;
                }
            }
        });
        builder.create().show();
    }

    /**
     * 处理相机或者相册返回的操作
     * 先看结果码再看请求码  无论是相册还是照片都是成功后才请求
     *
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (resultCode) {
                //无论哪种返回都要成功返回，看是哪个回来。
                case TAKE_PHOTO:
                    //将保存在本地的图片缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile(
                            Environment.getExternalStorageDirectory() + "/image.jpg");
                    //位图不会自动缩小，要新建一个，调用zoomBitmap设置图片的位置高宽
                    Bitmap newbitmap = ImageTools.zoomBitmap(bitmap,
                            bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                    //回收机制
                    bitmap.recycle();
                    //显示相机的图片
                    imageView.setImageBitmap(newbitmap);
                    //同时复制到存储相片的目录
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
                    //文件名
                    String filename = sdf.format(new Date());
                    //图片目录
                    filename = "IMG_" + filename + ".png";
                    String dir = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DCIM).getAbsolutePath() + "Camera";

                    uploadFile = dir + "/" + filename + ".png";
//                    uploadFile = Environment.getExternalStoragePublicDirectory
//                            (Environment.DIRECTORY_DCIM).getAbsolutePath() +
//                            "/Camra/" + "IMG" + filename + ".png";
                    //上传文件名
                    ImageTools.savePhotoToSDCard(newbitmap, dir, filename);
                    break;
                case CHOOSE_PHOTO:
                    ContentResolver resolver = this.getContentResolver();
                    //获得照片的原始路径
                    Uri originalUri = data.getData();
                    try {
                        //通过内容访问者和图片URl返回，获取位图的图片  (相册中的原图)
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            Bitmap smallBitmap = ImageTools.zoomBitmap
                                    (photo, photo.getHeight() / SCALE, photo.getWidth() / SCALE);
                            //释放原图
                            photo.recycle();
                            //显示选中的图片
                            imageView.setImageBitmap(smallBitmap);

                            uploadFile = ImageUri.getImageAbsolutePath(this, originalUri);
                            Log.v("MainActivity", originalUri.toString());
                        }

                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(this, "读取文件，出错了", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
        Log.v("MainActivity", uploadFile);

    }
}
