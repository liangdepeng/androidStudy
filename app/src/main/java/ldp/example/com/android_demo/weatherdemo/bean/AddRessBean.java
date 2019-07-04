package ldp.example.com.android_demo.weatherdemo.bean;

import java.util.ArrayList;

/**
 * created by Da Peng at 2019/6/21
 */
public class AddRessBean {

    /**
     * status : 0
     * msg : ok
     * result : [{"cityid":1,"parentid":0,"citycode":"101010100","city":"北京"},{"cityid":2,"parentid":0,"citycode":null,"city":"安徽"},{"cityid":3,"parentid":0,"citycode":null,"city":"福建"},{"cityid":4,"parentid":0,"citycode":null,"city":"甘肃"},{"cityid":5,"parentid":0,"citycode":null,"city":"广东"},{"cityid":6,"parentid":0,"citycode":null,"city":"广西"}......
     */

    private int status;
    private String msg;
    private ArrayList<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<ResultBean> getResult() {
        return result;
    }

    public void setResult(ArrayList<ResultBean> result) {
        this.result = result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append('\n');
        builder.append('\n');

        for (ResultBean resultBean : result) {
            builder.append(resultBean.toString());
            builder.append('\n');
            builder.append('\n');
        }

        return "AddRessBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", result=" + builder.toString() +
                '}';
    }

}


