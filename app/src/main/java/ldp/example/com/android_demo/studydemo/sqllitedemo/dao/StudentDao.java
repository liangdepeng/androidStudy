package ldp.example.com.android_demo.studydemo.sqllitedemo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ldp.example.com.android_demo.studydemo.sqllitedemo.Sqlite1;

/**
 * created by ldp at 2018/11/2
 */
public class StudentDao {

    private final Sqlite1 mSqlite1;

    public StudentDao(Context context) {
        mSqlite1 = new Sqlite1(context);
    }

    /**
     * 添加一条记录
     *
     * @param name 姓名
     * @param id   id
     */
    public void SQLite_add(String name, String id) {
        SQLiteDatabase db = mSqlite1.getWritableDatabase();
        db.execSQL("insert into student (name ,id)values(?,?);", new Object[]{name, id});
        db.close();
    }

    /**
     * 删除一条记录
     *
     * @param name 姓名
     */
    public void SQLite_delete(String name) {
        SQLiteDatabase db = mSqlite1.getWritableDatabase();
        db.execSQL("delete from student where name = ?", new Object[]{name});
        db.close();
    }

    /**
     * 修改一条记录
     *
     * @param name 姓名
     * @param id   id
     */
    public void SQLite_update(String name, String id) {
        SQLiteDatabase db = mSqlite1.getWritableDatabase();
        db.execSQL("update student set id = ? where name = ?", new Object[]{id, name});
        db.close();
    }

    /**
     * 查询一条记录
     *
     * @param name 姓名
     * @return
     */
    public String SQLite_query(String name) {
        SQLiteDatabase db = mSqlite1.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id from student where name = ?;", new String[]{name});
        String id = null;
        if (cursor.moveToNext()) {
            id = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return id;
    }
}
