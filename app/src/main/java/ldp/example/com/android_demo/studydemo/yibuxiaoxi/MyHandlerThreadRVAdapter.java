package ldp.example.com.android_demo.studydemo.yibuxiaoxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ldp.example.com.android_demo.R;

public class MyHandlerThreadRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> list;
    private Context context;

    public MyHandlerThreadRVAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case R.layout.item_handler_thread_one:
                view = LayoutInflater.from(context).inflate(R.layout.item_handler_thread_one, parent,false);
                return new ViewHolderOne(view);
            case R.layout.item_handler_thread_two:
                view = LayoutInflater.from(context).inflate(R.layout.item_handler_thread_two, parent,false);
                return new ViewHolderTwo(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOne) {
            ((ViewHolderOne) holder).textView1.setText(list.get(position));
        } else if (holder instanceof ViewHolderTwo) {
            ((ViewHolderTwo) holder).textView2.setText(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return R.layout.item_handler_thread_one;
        } else {
            return R.layout.item_handler_thread_two;
        }
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {

        private TextView textView1;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.text1);
        }

    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder {

        private TextView textView2;

        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.text2);
        }

    }
}
