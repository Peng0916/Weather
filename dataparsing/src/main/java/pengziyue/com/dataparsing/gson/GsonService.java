package pengziyue.com.dataparsing.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Gson 是 Google 提供的用来在 Java 对象和 JSON 数据之间进行映射的 Java 类库
 * 可以将一个 JSON 字符串转成一个 Java 对象，或者反过来
 */

public class GsonService {
    // [{id:100,title:'abc',price:99},{},{}]
    public void object2gson() {
        //实例化GSON对象
        Gson gson = new Gson();
        //指定转换类型    TypeToken 放入要转换的类型
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        //把java对象（数据）转换为json格式的字符串
        String json = gson.toJson(getBooks(), type);

    }
    public static Book gson2object() {
        Gson gson = new Gson();
        String json = "{id:100,title:'abc',price:99}";
        // 把json格式的字符串转换为Java对象
        Book book = gson.fromJson(json, Book.class);
        return book;
    }

    public static ArrayList<Book> gson2objectList() {
        String json = "[{'id':100,'price':99.0,'title':'完美世界'},{'id':100,'price':99.0,'title':'择天记'}]";
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        // 把json格式的字符串转换为集合对象
        return gson.fromJson(json,type);
    }

    public ArrayList<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(100, "天龙八部", 99d));
        books.add(new Book(101, "简爱", 79d));
        books.add(new Book(102, "择天记", 89d));
        books.add(new Book(103, "赵云记", 102d));
        books.add(new Book(104, "三国", 188d));
        return books;
    }
}
