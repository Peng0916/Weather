package pengziyue.com.dataparsing;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

import pengziyue.com.dataparsing.gson.Book;
import pengziyue.com.dataparsing.gson.GsonService;
import pengziyue.com.dataparsing.xml.Person;
import pengziyue.com.dataparsing.xml.PersonList;

/**
 * 开发步骤 XML 解析方式
 * 1.XML pull * 简单应用，效率高
 * 2.simple xml 零配置,简单易用
 * 3.dom    适合解析小XML文件，缺点 耗内存
 * 4.sax    事件驱动， 解析大的XML文件，占内存小
 * json解析方式
 * 1，json  2,gson
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream in=getAssets().open("person.xml");
            //    simpleXml(in);
            //   simpleXmlList(in);
            // 关闭上面的输入流流,否则 end of document 异常
            Log.v("MainActivity", "DOM 解析 *********");
            //for (Person p : DomPersonService.getPersons(in)) {
            //    Log.v("MainActivity", p.getId() + " " + p.getName() + " " + p.getAge());
            //}

            Log.v("MainActivity", "Sax 解析 *********");
            //SaxPersonService service = new SaxPersonService();
            //for (Person p : service.getPersons(in)) {
            //    Log.v("MainActivity", p.getId() + " " + p.getName() + " " + p.getAge());
            //}

            Log.v("MainActivity", "GSON 解析 *********");
            String json = GsonService.object2gson();
            // 通过 JSONArray / JSONObject 获得数据
            Log.v("MainActivity", "集合->JSON:" + json);

            Log.v("MainActivity", "JSON->Book:");
            Book book = GsonService.gson2object();
            Log.v("MainActivity", book.getId() + " " + book.getTitle() + " " + book.getPrice());

            Log.v("MainActivity", "JSON->Book List:");
            for (Book b : GsonService.gson2objectList()) {
                Log.v("MainActivity", b.getId() + " " + b.getTitle() + " " + b.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Simple xml 读取xml文件(多个 Person)
     *
     * @param in * @throws Exception
     */
    private void simpleXmlList(InputStream in) throws Exception {
        Serializer serializer = new Persister();
        PersonList personList = serializer.read(PersonList.class, in);
        for (Person p : personList.getPersons()) {
            Log.v("MainActivity", p.getId() + " " + p.getName() + " " + p.getAge());
        }
    }


    private void readSimpleXml(InputStream in) throws Exception {
        //实例化Simple xml 持久化对象
        Serializer serializer = new Persister();
        //读取输入流中数据转化为对象
        Person p = serializer.read(Person.class, in);
        Log.v("MainActivity", p.getId() + " " + p.getName() + " " + p.getAge());

        //***************写入XML文件，上面是读取
        StringWriter writer = new StringWriter();
        p = new Person();
        p.setId("1234");
        p.setName("米粒");
        p.setAge("24");
        //把java对象写入XML文件
        serializer.write(p, writer);
        //写入XML文件
        Log.v("MainActivity", writer.toString());

        //把java对象写入XMLsd卡  授权
        File dir = Environment.getExternalStorageDirectory();
        File file = new File("newer.xml");
        serializer.write(p, file);
    }

    /**
     * Simple xml 读取xml文件(一个 Person)
     *
     * @param in
     * @throws Exception
     */
    private void simpleXml(InputStream in) throws Exception {
        // 实例化 simple xml持久化对象
        Serializer serializer = new Persister();
        // 读取输入流中数据转换为对象
        Person p = serializer.read(Person.class, in);
        Log.v("MainActivity", p.getId() + " " + p.getName() + " " + p.getAge());

        // **写 xml 文件 ***********************
        StringWriter writer = new StringWriter();
        p = new Person();
        p.setId("999");
        p.setName("乔峰");
        p.setAge("32");

        // 把Java对象写入xml文件
        serializer.write(p, writer);
        Log.v("MainActivity", writer.toString());

        // 把Java对象写入xml文件(SDCard) - 授权
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, "newer.xml");
        serializer.write(p, file);
    }
}