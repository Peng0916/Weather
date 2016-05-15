package pengziyue.com.loginweb;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by PengYue on 2016/4/27.
 * 封装 doget /dopost 访问网络的操作
 */
public class NetService {

    public static final String BASE_URL
            = "http://192.168.43.245:8087/androidcloud/";


    /**
     * GET 方法
     * 用于检索信息（如文档、图表或数据库请求结果）
     * 请求的页面可以被设置为书签和使用电子邮件发送
     * 请求信息作为查询字符串发送;通过 URL 提交数据,数据在 URL 中可以看到;数据大小最多 1Kb;
     * 一般适用于小数据量的请求
     * index.html?username=tom&password=tom
     *
     * @param username
     * @param password
     * @return
     */

    public static String doGet(String username, String password) {
        //在浏览器中输入URL(访问云端的路径)   username password 要与服务器端的一致 1.在浏览器中输入URL(访问云端的路径)
        String urlPath = BASE_URL + "LoginServlet?username=" + username + "&password=" + password;
        //避免中文乱码 urlPath = Url.e
        try {
            // 2.创建 URL 对象
            URL url = new URL(urlPath);
            //打开访问网络的链接（浏览器回车）  ssl 是安全套接字   // 3.请求云端 (打开访问网络的连接 - 浏览器回车)
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);//超时设置
            conn.setRequestMethod("GET");//Service里的get方法  请求方式
            //响应来自云端服务器的状态码    // 4.获得云端服务器的响应状态码
            int code = conn.getResponseCode();
            //200 OK状态（正常响应）
            if (code == 200) {
                // 5.从网络连接中获得响应的输入流
                InputStream in = conn.getInputStream();
                // 6.通过流工具类把输入流数据转换为字符串
                return new String(StreamTool.read(in));
            }
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * POST 方法
     * 用于发送敏感信息（如信用卡号）或要保存到数据库中的信息
     * 请求的页面不能设置为书签或不能通过电子邮件发送发送的数据;
     * 数据放置在 html HEADER 内提交,无法在地址栏看到;数据大小没有限制(8 Kb);
     * 一般适用于大数据量的请求(如:附件)
     *
     * @param username
     * @param password
     * @return
     */
    public static String doPost(String username, String password) {
        try {
            //1在刘游览器中输入URL 访问云端的路径
            String urlPath = BASE_URL + "LoginServlet";
            String data = "username " + username + " & password = " + password;
            //2.创建URL对象
            URL url = new URL(urlPath);

            //3。请求云端  打开访问网络的链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);//设置链接时间
            conn.setRequestMethod("POST");//post请求方式

            //内容类型
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //浏览器把数据写给服务器   客户端到服务器流的使用是输出
            conn.setDoInput(true);
            //数据从内存中向服务器输出去，字节将用户名和密码传给服务器了
            OutputStream out = conn.getOutputStream();
            //将数据写给服务器了
            out.write(data.getBytes());


            //响应来自云端服务器的状态码    //获得云端服务器的响应状态码
            int code = conn.getResponseCode();
            //200 OK状态 （正常响应）
            if (code == 200) {
                // 5.从网络连接中获得响应的输入流     请求是输出，响应是输入
                InputStream in = conn.getInputStream();
                // 6.通过流工具类把输入流数据转换为字符串
                return new String(StreamTool.read(in));
            }


        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 处理云端服务器返回客户端的流的工具类
     */
    private static class StreamTool {
        /**
         * 从输入流中获取数据(文本/图片)
         *
         * @param in 输入流
         * @return
         * @throws Exception
         */
        public static byte[] read(InputStream in) throws Exception {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            byte[] data = out.toByteArray();

            in.close();
            out.close();

            return data;
        }
    }

    /**
     * 3.通过URL返回Bitmap对象
     * @param imageurl
     * @return
     */
    public static Bitmap load(String imageurl) {
        //通过URL返回Bitmap对象
        try {
            URL url = new URL(imageurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream in = conn.getInputStream();
                //将输入流通过位图工厂转化为位图
                return BitmapFactory.decodeStream(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
