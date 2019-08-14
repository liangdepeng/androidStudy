package com.example.ldp.base_lib.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;


import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.ldp.base_lib.R;
import com.example.ldp.base_lib.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * created by Da Peng at 2019/8/13
 */
public class BottomSingleWheelDialog<T> extends BaseDialog {

    private List<T> bankList;
    private List<String> datas = new ArrayList<>();
    private OnMyItemSelectedListener<T> onItemSelectedListener;
    private WheelView wheelView;
    private int selectPos;

    public BottomSingleWheelDialog(@NonNull Context context, List<T> bankList, OptionParser<T> optionParser) {
        super(context);
        this.bankList = bankList;
        setShowBottom(true);
        setAnimStyle(R.style.dialog_anim_bottom);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        for (T bean : bankList) {
            datas.add(optionParser.parseTitle(bean));
        }
    }


    @Override
    public int intLayoutId() {
        return R.layout.dialog_bank_dialog_layout;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        wheelView = holder.getView(R.id.bank_dialog_wheel_view);
        wheelView.setCyclic(false);
        wheelView.setAdapter(new ArrayWheelAdapter<String>(datas));

        holder.setOnClickListener(R.id.ok_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) {
                    dismiss();
                    onItemSelectedListener.onSelectItem(bankList.get(selectPos));
                }
            }
        });

        holder.setOnClickListener(R.id.cancel_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectPos = index;
            }
        });

    }

    public BottomSingleWheelDialog<T> setOnItemSelectedListener(OnMyItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
        return this;
    }

    public BottomSingleWheelDialog<T> setData(List<T> bankList) {
        this.bankList = bankList;

        return this;
    }


    public interface OnMyItemSelectedListener<T> {
        void onSelectItem(T selectItem);
    }

    public interface OptionParser<T> {
        String parseTitle(T item);
    }

    @Override
    public void show() {
        super.show();
    }
}
