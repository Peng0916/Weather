package pengziyue.com.volley;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Book> list;
    private LayoutInflater inflater;

    public BookAdapter(Context context, ArrayList<Book> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Book getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_item, null);
            // 通过黄油刀实例化控件
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book book = list.get(position);

        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.isbn.setText(book.getIsbn());

        // ImageLoader也可以用于加载网络上的图片，
        // 并且它的内部也是使用ImageRequest来实现的，
        // 不过ImageLoader明显要比ImageRequest更加高效，
        // 因为它不仅可以帮我们对图片进行缓存，还可以过滤掉重复的链接，
        // 避免重复发送请求
        ImageLoader imageLoader =
                VolleyUtils.getInstance(context).getImageLoader();

        // getImageListener(显示图片的ImageView控件,
        // 加载图片的过程中显示的图片,加载图片失败的情况下显示的图片)
        ImageLoader.ImageListener listener =
                ImageLoader.getImageListener(holder.image,
                        R.drawable.empty_photo, R.mipmap.ic_launcher);

        // get(图片URL,listener)
        imageLoader.get(book.getImage(), listener);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.isbn)
        TextView isbn;
        @Bind(R.id.image)
        ImageView image;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
