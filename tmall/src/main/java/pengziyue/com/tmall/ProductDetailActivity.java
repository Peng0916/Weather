package pengziyue.com.tmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import pengziyue.com.tmall.pojo.Product;
import pengziyue.com.tmall.utils.VolleyUtil;

public class ProductDetailActivity extends AppCompatActivity {
    @Bind(R.id.productImgPath)
    ImageView productImgPath;
    @Bind(R.id.productId)
    TextView productId;
    @Bind(R.id.productName)
    TextView productName;
    @Bind(R.id.productPrice)
    TextView productPrice;
    @Bind(R.id.productRemark)
    TextView productRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Product product = (Product) intent.getSerializableExtra("product");
        // TmallUtil.showToast(this,product.getName());

        // 设置应用栏
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 设置应用栏中的标题(宝贝名)
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(product.getName());

        // 填充数据到指定控件
        productId.setText(String.format("%d", product.getId()));
        productName.setText(product.getName());
        productPrice.setText(String.format("%.2f", product.getPrice()));
        productRemark.setText(product.getRemark());

        ImageLoader imageLoader =
                VolleyUtil.getInstance(this).getImageLoader();
        ImageLoader.ImageListener listener =
                ImageLoader.getImageListener(productImgPath,
                        R.drawable.me, R.mipmap.ic_launcher);
        imageLoader.get(product.getImgPath(), listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();  // 返回退出当前 Activity
                break;
        }
        return true;
    }
}
