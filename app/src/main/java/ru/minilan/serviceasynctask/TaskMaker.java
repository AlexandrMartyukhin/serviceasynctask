package ru.minilan.serviceasynctask;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

public class TaskMaker {

    private OnTaskListener onTaskListener;

    public TaskMaker(OnTaskListener onTaskListener) {
        this.onTaskListener = onTaskListener;
    }

    public interface OnTaskListener {
        void onStartup();

        void onStatusProgress();

        void onComplete();
    }

    public void createTask(Integer i, Integer j) {
        MyTask myTask = new MyTask(onTaskListener);
        myTask.execute(i,j);
    }


    private static class MyTask extends AsyncTask<Integer, String, String> {

        private OnTaskListener onTaskListener;

        public MyTask(OnTaskListener onTaskListener) {
            this.onTaskListener = onTaskListener;
        }

        @Override
        protected void onPreExecute() {
            onTaskListener.onStartup();
        }

        @Override
        protected String doInBackground(Integer... integers) {

            try {

                TimeUnit.SECONDS.sleep(integers[0]);
                publishProgress();
                TimeUnit.SECONDS.sleep(integers[1]);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Background process done";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            onTaskListener.onStatusProgress();
        }

        @Override
        protected void onPostExecute(String s) {
            onTaskListener.onComplete();
        }


    }
}
