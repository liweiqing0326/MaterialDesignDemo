package lwq.com.materialdesigndemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lwq.com.materialdesigndemo.R;
import lwq.com.materialdesigndemo.entity.Item;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Item> itemList;

    private ItemClick itemClick;

    private Context context;

    public RecyclerAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        itemClick = (ItemClick) parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.text.setText(item.getText());
        holder.coloredView.setBackgroundColor(Color.parseColor(item.getColor()));
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onClick(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.colored_view)
        View coloredView;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.item_root)
        CardView itemRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClick {
        void onClick(View v, int position);
    }
}
