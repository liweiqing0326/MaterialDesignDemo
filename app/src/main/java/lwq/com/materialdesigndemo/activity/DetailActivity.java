package lwq.com.materialdesigndemo.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import lwq.com.materialdesigndemo.Fragment.TabFragment;
import lwq.com.materialdesigndemo.R;
import lwq.com.materialdesigndemo.adapter.ViewPagerAdapter;
import lwq.com.materialdesigndemo.entity.Item;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        //获取Intent数据
        item = getIntent().getParcelableExtra("item");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(darker(Color.parseColor(item.getColor()), 0.9f));
        }

        //设置TabLayout
        toolbar.setTitle(item.getText());
        setSupportActionBar(toolbar);
        //改变颜色
        changeColor();
        //设置ViewPager
        setupViewPager(viewpager);
        //配置ViewPager
        tabs.setupWithViewPager(viewpager);
        //设置ActionBar
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(item.getColor())));
        }
        //设置监听
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFragment(), "OVERVIEW");
        adapter.addFragment(new TabFragment(), "USAGE");
        adapter.addFragment(new TabFragment(), "STYLE");
        viewpager.setAdapter(adapter);
        Animation bottomUp = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.bottom_up);
        viewpager.startAnimation(bottomUp);
    }

    public int darker(int color, float factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(a, Math.max((int) (r * factor), 0), Math.max((int) (g * factor), 0), Math.max((int) (b * factor), 0));
    }

    private void changeColor() {
        appBarLayout.setBackgroundColor(Color.parseColor(item.getColor()));
        toolbar.setBackgroundColor(Color.parseColor(item.getColor()));
        tabs.setBackgroundColor(Color.parseColor(item.getColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_simple, menu);
        return true;
    }
}
