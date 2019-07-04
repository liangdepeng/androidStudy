package com.example.ldp.base_lib.bean;

import java.util.List;

/**
 * created by Da Peng at 2019/7/4
 */
public class HtmlInfoBean {

    /**
     * status : 0
     * msg : ok
     * result : {"channel":"头条","num":2,"list":[{"title":"9名男子为涨粉直播绑架殴打 警方：全部被传唤","time":"2019-07-04 16:38:05","src":"中国新闻网","category":"news","pic":"https://cms-bucket.ws.126.net/2019/07/04/687789382dd84aaba50f328962b88a64.png","content":"","url":"http://3g.163.com/news/19/0704/16/EJ8MGOKE0001875P.html","weburl":"http://news.163.com/19/0704/16/EJ8MGOKE0001875P.html"},{"title":"乐山大佛修缮成\"小鲜肉\"?景区:网传图片或被\"美颜\"","time":"2019-07-04 16:32:08","src":"新京报","category":"news","pic":"https://cms-bucket.ws.126.net/2019/07/04/6aab5b578ff046a78f9912dfa718ffb6.png","content":"","url":"http://3g.163.com/news/19/0704/16/EJ8M5SE10001899N.html","weburl":"http://news.163.com/19/0704/16/EJ8M5SE10001899N.html"}]}
     */

    private int status;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HtmlInfoBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", result=" + result.toString() +
                '}';
    }

    public static class ResultBean {
        /**
         * channel : 头条
         * num : 2
         * list : [{"title":"9名男子为涨粉直播绑架殴打 警方：全部被传唤","time":"2019-07-04 16:38:05","src":"中国新闻网","category":"news","pic":"https://cms-bucket.ws.126.net/2019/07/04/687789382dd84aaba50f328962b88a64.png","content":"","url":"http://3g.163.com/news/19/0704/16/EJ8MGOKE0001875P.html","weburl":"http://news.163.com/19/0704/16/EJ8MGOKE0001875P.html"},{"title":"乐山大佛修缮成\"小鲜肉\"?景区:网传图片或被\"美颜\"","time":"2019-07-04 16:32:08","src":"新京报","category":"news","pic":"https://cms-bucket.ws.126.net/2019/07/04/6aab5b578ff046a78f9912dfa718ffb6.png","content":"","url":"http://3g.163.com/news/19/0704/16/EJ8M5SE10001899N.html","weburl":"http://news.163.com/19/0704/16/EJ8M5SE10001899N.html"}]
         */

        private String channel;
        private int num;
        private List<ListBean> list;

        public String getDatas() {
            StringBuilder builder = new StringBuilder();
            for (ListBean bean : list) {
                builder.append("\n");
                builder.append("\n");
                builder.append(bean.toString());
                builder.append("\n");
                builder.append("\n");
            }
            return builder.toString();
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "channel='" + channel + '\'' +
                    ", num=" + num +
                    ", list=" + getDatas() +
                    '}';
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * title : 9名男子为涨粉直播绑架殴打 警方：全部被传唤
             * time : 2019-07-04 16:38:05
             * src : 中国新闻网
             * category : news
             * pic : https://cms-bucket.ws.126.net/2019/07/04/687789382dd84aaba50f328962b88a64.png
             * content :
             * url : http://3g.163.com/news/19/0704/16/EJ8MGOKE0001875P.html
             * weburl : http://news.163.com/19/0704/16/EJ8MGOKE0001875P.html
             */

            private String title;
            private String time;
            private String src;
            private String category;
            private String pic;
            private Object content;
            private String url;
            private String weburl;

            @Override
            public String toString() {
                return "ListBean{" +
                        "title='" + title + '\'' +
                        ", time='" + time + '\'' +
                        ", src='" + src + '\'' +
                        ", category='" + category + '\'' +
                        ", pic='" + pic + '\'' +
                        ", content=" + content +
                        ", url='" + url + '\'' +
                        ", weburl='" + weburl + '\'' +
                        '}';
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWeburl() {
                return weburl;
            }

            public void setWeburl(String weburl) {
                this.weburl = weburl;
            }
        }
    }
}
