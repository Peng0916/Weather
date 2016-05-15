package pengziyue.com.volley;

import java.io.Serializable;

/**
 * Created by SONG on 2016/4/29.
 */
public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private String image;

    public Book() {
    }

    public Book(String isbn, String title, String author, String image) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
