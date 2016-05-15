package pengziyue.com.volley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pengziyue.com.volley.upload.UploadActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_download)
    public void btnDownloadClick() {
        // 从云端服务器下载数据
        startActivity(new Intent(this, DownloadActivity.class));
    }

    @OnClick(R.id.btn_upload)
    public void btnUploadClick() {
        // 从 Android 客户端提交数据至云端服务器
        startActivity(new Intent(this, UploadActivity.class));
    }

    @OnClick(R.id.btn_web_view)
    public void btnWebViewClick() {
        startActivity(new Intent(this, WebViewActivity.class));
    }
}
