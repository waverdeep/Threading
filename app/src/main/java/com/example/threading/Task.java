package com.example.threading;

import android.app.Activity;
import android.content.Context;

public abstract class Task<Params, Progress, Result> implements Runnable{
    Params argument;
    Result threadResult;
    Activity activityContext;

    public Task(Activity activityContext){
        this.activityContext = activityContext;
    }
    public abstract void onPreExecute();
    public abstract Result doInBackground(Params param);
    public abstract void onPostExecute(Result result);
    public abstract void onProgressUpdate(Progress progress);

    final public void execute(final Params param){
        argument = param;

        onPreExecute();

        Thread thread = new Thread(this);
        thread.start();

        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
            onPostExecute(null);
            return;
        }

        onPostExecute(threadResult);
    }

    @Override
    public void run() {
        threadResult = doInBackground(argument);
    }

    final public void publishProgress(Progress data){
        activityContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(data);
            }
        });
    }


}
