package ldp.example.com.android_demo.studydemo.kotlin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Da Peng at 2019/3/27
 */
public class TestBean implements Serializable {


    private ArrayList<TrailersBean> trailers;

    public ArrayList<TrailersBean> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<TrailersBean> trailers) {
        this.trailers = trailers;
    }

    public static class TrailersBean implements Serializable{
        /**
         * id : 74113
         * movieName : 昆汀《好莱坞往事》预告“李小龙”现身
         * coverImg : http://img5.mtime.cn/mg/2019/03/21/142620.19535364_120X90X4.jpg
         * movieId : 252117
         * url : http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4
         * hightUrl : http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4
         * videoTitle : 好莱坞往事 中字先导预告片
         * videoLength : 100
         * rating : -1
         * type : ["喜剧","剧情"]
         * summary :
         */

        private int id;
        private String movieName;
        private String coverImg;
        private int movieId;
        private String url;
        private String hightUrl;
        private String videoTitle;
        private int videoLength;
        private double rating;
        private String summary;
        private List<String> type;

        @Override
        public String toString() {
            return "TrailersBean{" +
                    "id=" + id +
                    ", movieName='" + movieName + '\'' +
                    ", coverImg='" + coverImg + '\'' +
                    ", movieId=" + movieId +
                    ", url='" + url + '\'' +
                    ", hightUrl='" + hightUrl + '\'' +
                    ", videoTitle='" + videoTitle + '\'' +
                    ", videoLength=" + videoLength +
                    ", rating=" + rating +
                    ", summary='" + summary + '\'' +
                    ", type=" + type +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public int getVideoLength() {
            return videoLength;
        }

        public void setVideoLength(int videoLength) {
            this.videoLength = videoLength;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }
}
