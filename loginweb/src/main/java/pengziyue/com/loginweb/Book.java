package pengziyue.com.loginweb;

import java.io.Serializable;

/**
 * Created by PengYue on 2016/4/28.
 */
public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private String images;

    public Book() {
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
