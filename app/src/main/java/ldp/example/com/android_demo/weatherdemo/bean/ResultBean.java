package ldp.example.com.android_demo.weatherdemo.bean;

import org.litepal.crud.DataSupport;

/**
 * created by Da Peng at 2019/6/25
 */
public class ResultBean extends DataSupport {
        /**
         * cityid : 1
         * parentid : 0
         * citycode : 101010100
         * city : 北京
         */

        private int cityid;
        private int parentid;
        private String citycode;
        private String city;

        public int getCityid() {
            return cityid;
        }

        public void setCityid(int cityid) {
            this.cityid = cityid;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
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

        @Override
        public String toString() {
            return "ResultBean{" +
                    "cityid=" + cityid +
                    ", parentid=" + parentid +
                    ", citycode='" + citycode + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
}
