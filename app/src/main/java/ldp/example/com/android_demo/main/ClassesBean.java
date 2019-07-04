package ldp.example.com.android_demo.main;

/**
 * created by Da Peng at 2019/7/3
 */
public class ClassesBean {

    private String info;
    private Class clazz;

    public ClassesBean(String info, Class clazz) {
        this.info = info;
        this.clazz = clazz;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
