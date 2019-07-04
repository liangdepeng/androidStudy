package ldp.example.com.android_demo.studydemo.sqllitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * created by ldp at 2018/11/2
 */
public class Sqlite1 extends SQLiteOpenHelper {

    /**
     *
     * @param context 上下文
     *
     *   第一个参数 上下文
     *   第二个参数 数据库名称
     *   第三个参数 null 默认的游标工厂
     *   第四个参数 数据库的版本号 数据库只能升级 不能降级 版本号只能增大 不能变小
     *
     */
    public Sqlite1(Context context) {
        super(context, "ldp", null, 1);
    }

    /**
     * 当数据库第一次被创建的时候调用，适合在这个方法中把数据库的表的结构定义出来
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("数据库被创建了");
        db.execSQL("CREATE TABLE student\n" +
                "(\n" +
                "id integer primary key autoincrement,\n" +
                "name char (20),\n" +
                "phone varchar (20)\n" +
                ")");
    }

    /**
     * 当更新数据库时用到的方法
     * @param db
     *          数据库
     * @param oldVersion
     *          旧版本
     * @param newVersion
     *          新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
