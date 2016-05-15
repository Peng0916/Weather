package pengziyue.com.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Web 开发步骤：
 * 1.准备好网页（本地/云端网页）
 * 2.实例化WebView 控件
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.使用布局
//        setContentView(R.layout.activity_web_view);
//        webView = (WebView) findViewById(R.id.web_view);

        //2.不适用布局文件，直接实例化
        webView = new WebView(this);//实例化后要将控件放入进来 setContentView(webView);

        //设置允许执行js
        webView.getSettings().setJavaScriptEnabled(true);
        //网页放大缩小的控制  setDisplayZoomControls 放大缩小才显示  setBuiltInZoomControls显示
        webView.getSettings().setBuiltInZoomControls(true);
        //加载本地网页
        //webView.loadUrl("fil:///android_asset/index.html");

        //加载云端网页
        webView.loadUrl(VolleyUtils.BASE_URL + "index.jsp");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用当期那的WebView处理跳转
                view.loadUrl(url);
                //true表示此事件在此被处理，不需要再广播
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // error.getDescription() 版本原因无法使用
                Toast.makeText(WebViewActivity.this, "Oh no", Toast.LENGTH_SHORT).show();
            }
        });
        //使用对象实例化时要写上
        setContentView(webView);

    }

    /**
     * 默认点回退键，会退出活动，需要监听按键操作，使回退在WebView内发生
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断按键是回退键，切网页有历史记录
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            //在webview的历史记录中回退一次
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
