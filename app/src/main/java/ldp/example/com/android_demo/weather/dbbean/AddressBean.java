package ldp.example.com.android_demo.weather.dbbean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Da Peng at 2019/4/2
 * @author mini
 */
public class AddressBean extends DataSupport {

    /**
     * status : 0
     * msg : ok
     * result : [{"cityid":"1","parentid":"0","citycode":"101010100","city":"北京"},{"cityid":"2","parentid":"0","citycode":"","city":"安徽"}]
     */

    private String status;
    private String msg;
    private ArrayList<AddressDetailsBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<AddressDetailsBean> getResult() {
        return result;
    }

    public void setResult(ArrayList<AddressDetailsBean> result) {
        this.result = result;
    }

    public static class AddressDetailsBean extends DataSupport {
        /**
         * cityid : 1
         * parentid : 0
         * citycode : 101010100
         * city : 北京
         */

        private String cityid;
        private String parentid;
        private String citycode;
        private String city;

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
