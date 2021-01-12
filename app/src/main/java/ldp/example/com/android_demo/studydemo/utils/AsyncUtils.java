package ldp.example.com.android_demo.studydemo.utils;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-11
 * <p>
 * Summary:
 */
public class AsyncUtils {

    private static HashMap<Object, MyAsyncTask> taskHashMap;

    public static void performTask(IAsyncTask iAsyncTask, Object taskTag) {
        MyAsyncTask asyncTask = new MyAsyncTask(iAsyncTask);
        if (taskHashMap == null) {
            taskHashMap = new HashMap<>();
        }
        taskHashMap.put(taskTag, asyncTask);
        asyncTask.execute();
    }

    public static void cancelTask(Object taskTag) {
        if (taskHashMap == null) return;
        MyAsyncTask myAsyncTask = taskHashMap.get(taskTag);
        if (myAsyncTask == null) return;
        myAsyncTask.cancel(true);
        taskHashMap.remove(taskTag);
    }

    public static void cancelAllTask() {
        if (taskHashMap == null) return;
        for (Map.Entry<Object, MyAsyncTask> entry : taskHashMap.entrySet()) {
            Object key = entry.getKey();
            MyAsyncTask task = entry.getValue();
            if (task != null) {
                task.cancel(true);
            }
        }
        taskHashMap.clear();
    }


    static class MyAsyncTask extends AsyncTask<Object, Integer, Object> {

        private IAsyncTask iAsyncTask;

        public MyAsyncTask(IAsyncTask iAsyncTask) {
            this.iAsyncTask = iAsyncTask;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            if (iAsyncTask != null) {
                return iAsyncTask.onAsyncTask();
            } else {
                cancel(true);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (iAsyncTask != null) {
                iAsyncTask.onTaskComplete(o);
            } else {
                cancel(true);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

    }

    public interface IAsyncTask {
        /**
         * WorkThread
         * <p>
         * 异步执行完任务 如果需要返回结果，则返回结果 没有则 null
         *
         * @return 结果
         */
        Object onAsyncTask();

        /**
         * MainThread
         * <p>
         * 任务执行结束 返回结果 没有则为 null
         *
         * @param o
         */
        void onTaskComplete(Object o);
    }
}
