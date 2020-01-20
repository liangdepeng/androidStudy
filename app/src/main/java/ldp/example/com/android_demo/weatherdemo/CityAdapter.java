package ldp.example.com.android_demo.weatherdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.RecyclerView;

import java.util.ArrayList;

import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.weatherdemo.bean.ResultBean;

/**
 * created by Da Peng at 2019/4/2
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private Context content;
    private OnItemClickListener onItemClickListener;
    private ArrayList<ResultBean> list = new ArrayList<>();

    public CityAdapter(Context context, ArrayList<ResultBean> list) {
        this.content = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(content).inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mAddress_tv.setText(list.get(position).getCity());

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position,list.get(position).getCity());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAddress_tv;

        ViewHolder(View itemView) {
            super(itemView);
            mAddress_tv = (TextView) itemView.findViewById(R.id.address_tv);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position,String str);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
