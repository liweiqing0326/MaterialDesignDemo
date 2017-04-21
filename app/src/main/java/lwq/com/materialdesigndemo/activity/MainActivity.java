package lwq.com.materialdesigndemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lwq.com.materialdesigndemo.Event.ScrollEvent;
import lwq.com.materialdesigndemo.R;
import lwq.com.materialdesigndemo.adapter.RecyclerAdapter;
import lwq.com.materialdesigndemo.entity.Item;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClick {

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.header)
    LinearLayout header;
    private ArrayList<Item> items;
    private RecyclerAdapter adapter;
    private boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置RecyclerView;
        setRecyclerView();
        //设置监听
        setListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    header.post(new Runnable() {
                        @Override
                        public void run() {
                            openEffect(header);
                        }
                    });
                } else {
                    header.post(new Runnable() {
                        @Override
                        public void run() {
                            closeEffect(header);
                        }
                    });
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    EventBus.getDefault().post(new ScrollEvent(0));
                } else {
                    EventBus.getDefault().post(new ScrollEvent(1));
                }
            }
        });
    }

    /**
     * 打开View
     *
     * @param header
     */
    private void closeEffect(final LinearLayout header) {
        if (!isOpen) {
            return;
        }
        Animation bottomUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shring_to_center);
        bottomUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                header.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        header.startAnimation(bottomUp);
        header.setVisibility(View.INVISIBLE);
        isOpen = false;
    }

    /**
     * 关闭View
     *
     * @param header
     */
    private void openEffect(LinearLayout header) {
        if (isOpen) {
            return;
        }
        Animation bottomUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.grow_from_center);
        header.startAnimation(bottomUp);
        header.setVisibility(View.VISIBLE);
        isOpen = true;
    }

    /**
     * 设置RecyclerView
     */
    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置adapter
        adapter = new RecyclerAdapter(getItems(), this);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<Item> getItems() {
        items = new ArrayList<>();
        items.add(new Item("Bottom Navigation", "#F1D05D"));
        items.add(new Item("Buttons", "#F1D05D"));
        items.add(new Item("Cards", "#00bfff"));
        items.add(new Item("Chips", "#00bfff"));
        items.add(new Item("Data Tables", "#ff33cc"));
        items.add(new Item("Dialogs", "#ff33cc"));
        items.add(new Item("Dividers", "#59D5B8"));
        items.add(new Item("Expansion Panels", "#59D5B8"));
        items.add(new Item("Grid Lists", "#F1D05D"));
        items.add(new Item("Lists", "#F1D05D"));
        items.add(new Item("Cards", "#00bfff"));
        items.add(new Item("Chips", "#00bfff"));
        items.add(new Item("Bottom Navigation", "#F1D05D"));
        items.add(new Item("Buttons", "#F1D05D"));
        items.add(new Item("Data Tables", "#ff33cc"));
        items.add(new Item("Dialogs", "#ff33cc"));
        items.add(new Item("Dividers", "#59D5B8"));
        items.add(new Item("Expansion Panels", "#59D5B8"));
        items.add(new Item("Grid Lists", "#F1D05D"));
        items.add(new Item("Lists", "#F1D05D"));
        return items;
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("item", items.get(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, "transition item");
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
