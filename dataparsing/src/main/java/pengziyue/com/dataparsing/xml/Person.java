package pengziyue.com.dataparsing.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by PengYue on 2016/4/26.
 * simple xml
 * Root      表示这个类的根节点
 * Attribute 表示这个类的属性
 * Element   表示这个类的节点
 */
@Root
public class Person {
    @Attribute
    private String id;
    @Element
    private String name;
    @Element
    private String age;


    public Person() {

    }

    public Person(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
