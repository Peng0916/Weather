package pengziyue.com.dataparsing.xml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by PengYue on 2016/4/26.
 */
@Root(name ="persons")
public class PersonList {
    //inline 在一行
    @ElementList(inline = true)
    private ArrayList<Person> persons;


    public PersonList(){
        persons = new ArrayList<>();

    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

}
