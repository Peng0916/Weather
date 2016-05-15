package pengziyue.com.tmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pengziyue.com.tmall.R;
import pengziyue.com.tmall.pojo.Product;
import pengziyue.com.tmall.utils.VolleyUtil;

/**
 * Created by PengYue on 2016/5/9.
 */
public class ProductListRecyclerViewAdapter extends
        RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;

    public ProductListRecyclerViewAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;

    }
//    public ProductListRecyclerViewAdapter( List<Product> context,FragmentActivity products) {
//        this.products = products;
//        this.context = context;
//
//        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        mBackground = mTypedValue.resourceId;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(String.format("%0.2f", product.getPrice()));
        ImageLoader imageLoader = VolleyUtil.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.
                getImageListener(holder.imageProductImgPath,
                        R.drawable.me, R.mipmap.ic_launcher);
        imageLoader.get(product.getImgPath(), listener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_product_img_path)
        ImageView imageProductImgPath;
        @Bind(R.id.tv_product_name)
        TextView tvProductName;
        @Bind(R.id.tv_product_price)
        TextView tvProductPrice;
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
