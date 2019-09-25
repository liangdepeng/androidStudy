package com.example.ldp.base_lib.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ldp.base_lib.base.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by Da Peng at 2019/8/23
 */

public class EditTextLimitTextWatcher implements TextWatcher {

    private boolean isShowTips = true;
    private EditText editText;
    private int maxLength;
    private CharSequence tip;

    public EditTextLimitTextWatcher(int maxLength, EditText editText, boolean isShowTips) {
        this.maxLength = maxLength;
        this.editText = editText;
        this.isShowTips = isShowTips;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        Editable editable = editText.getText();
        int len = editable.length();
        //大于最大长度
        if (len > maxLength) {
            int selEndIndex = Selection.getSelectionEnd(editable);
            String str = editable.toString();
            //截取新字符串
            String newStr = str.substring(0, maxLength);
            editText.setText(newStr);
            editable = editText.getText();
            //新字符串长度
            int newLen = editable.length();
            //旧光标位置超过字符串长度
            if (selEndIndex > newLen) {
                selEndIndex = editable.length();
            }
            //设置新的光标所在位置
            Selection.setSelection(editable, selEndIndex);

            if (isShowTips) {
                if (!TextUtils.isEmpty(tip)) {
                    showTips(tip);
                } else {
                    showTips("最多可输入" + maxLength + "字符");
                }

            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public EditTextLimitTextWatcher setTip(CharSequence tip) {
        this.tip = tip;
        return this;
    }

    // 方案宣言 过滤 特殊字符
    public EditTextLimitTextWatcher setFilter(EditText editText) {
        InputFilter inputFilter = new InputFilter() {
            // String str = "[-`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？《》\n]";
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                //String str = "[-`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？《》\n]";
                String str = "[-`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？《》\n]";
                Pattern pattern = Pattern.compile(str);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{inputFilter});
        return this;
    }

    public EditTextLimitTextWatcher setFilters(InputFilter inputFilter) {
        editText.setFilters(new InputFilter[]{inputFilter});
        return this;
    }

    private void showTips(CharSequence tips) {
        Toast.makeText(BaseApplication.getAppContent(), tips, Toast.LENGTH_SHORT).show();
    }
}
