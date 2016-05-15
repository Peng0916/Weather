package pengziyue.com.volley;

import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 使用 Volley 从云端服务器下载数据 开发步骤:
 * 1.导入 jar 包 (volley / butterknife)
 * build.gradle:compile 'com.mcxiaoke.volley:library:1.0.+'
 * 2.menu - menu_download.xml.xml
 * 3.getDataFromServer():加载来自云端的数据
 * 3.1 VolleyUtils:处理 URL 的工具类
 * 3.2 Volley
 * 4.加载数据中的图片处理
 */
public class DownloadActivity extends AppCompatActivity {
    private ArrayList<Book> list;
    private BookFragment bookFragment;
    private ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        if (savedInstanceState == null) {
            list = new ArrayList<>();
            bookFragment = new BookFragment();

            getFragmentManager().beginTransaction().add(R.id.container, bookFragment)
                    .commit();
        }

        // 获得连接管理器对象
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    /**
     * step 2:加载选项菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
    }

    /**
     * step 2:菜单项事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                getDataFromServer();
                // 获得当前网络详情 (默认数据网络)
                //NetworkInfo info = cm.getActiveNetworkInfo();
                //if (info != null && info.isConnectedOrConnecting()) {
                // 访问网络
                //} else {
                //Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                //}
                break;
        }
        return true;
    }

    /**
     * 3.加载来自云端的数据
     */
    private void getDataFromServer() {
        // 3.1 volley:创建一个StringRequest/JsonObjectRequest/JsonArrayRequest/XxxRequest对象
        // 默认的请求方式:get
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                // 参数 1:请求服务器的绝对路径
                VolleyUtils.getAbsoluteUrl("BookJsonServlet"),
                // 3.4.1 volley: 参数 2:响应成功的监听方法
                new Response.Listener<JSONArray>() {
                    // 返回的数据是 JSON 数组
                    @Override
                    public void onResponse(JSONArray ja) {
                        try {
                            list.clear(); // 清空已有数据
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                String isbn = jo.getString("isbn");
                                String title = jo.getString("title");
                                String author = jo.getString("author");
                                String barUrl = VolleyUtils.BASE_URL.substring(0, VolleyUtils.BASE_URL.length() - 1);
                                String image = barUrl + jo.getString("image");
                                Log.v("MainActivity", isbn + " " + title + " " + author + " " + image);

                                Book book = new Book(isbn, title, author, image);
                                list.add(book);
                            }
                            // 通知 Fragment 加载新数据
                            bookFragment.loadData(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                // 3.4.2 volley:参数 3:响应失败(404)的监听方法
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 输出响应错误 error.getMessage()
                    }
                }
        );

        // 3.2 volley:创建一个RequestQueue对象
        // RequestQueue是一个请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求

        // 3.3 volley:在请求对象中设置标记
        // 3.4 volley:将jsonArrayRequest对象添加到RequestQueue里面
        // 响应处理在request对象的回调(onResponse/onErrorResponse)方法中执行
        VolleyUtils.getInstance(this).addToRequestQueue(jsonArrayRequest, "json_array_obj");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 3.5 volley:移除所有请求中持有此标记的对象
        VolleyUtils.getInstance(this).cancelRequests("json_array_obj");
    }
}
