package com.populi.testapp.testapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.populi.testapp.testapplication.internal.DataManager;
import com.populi.testapp.testapplication.internal.network.Country;
import com.populi.testapp.testapplication.internal.network.RestAdapter;

import java.util.List;

/**
 * Created by Alexander Gavrikov.
 */

public class TaskUpdateTours extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private OnToursReadyListener listener;
    private static TaskUpdateTours instance;
    private volatile boolean isInProgress;

    public interface OnToursReadyListener{
        void onToursUpdate();
    }

    public static TaskUpdateTours getInstance(Context context) {
        if (instance == null){
            synchronized (TaskUpdateTours.class) {
                if (instance == null) {
                    instance = new TaskUpdateTours(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private TaskUpdateTours(Context context) {
        this.context = context;
    }

    public synchronized void updateTours(OnToursReadyListener listener) {
        this.listener = listener;
        if (!isInProgress){

            //TODO: sync issues. Cannot execute task: the task has already been executed (a task can be executed only once)
            execute();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        synchronized (this) {
            isInProgress = true;
        }

        List<Country> result = RestAdapter.getInstance().getTours();
        if (result != null) {
            DataManager.getInstance().updateTour(result);
            return true;
        }
        return false;
    }

    @Override
    protected synchronized void onPostExecute(Boolean result) {
        isInProgress = false;

        if (result){
            // Push to gui
            if (listener != null) {
                listener.onToursUpdate();
            }
        }
        else{
            // TODO: show error message
            Toast.makeText(context, R.string.tours_error, Toast.LENGTH_SHORT).show();
        }
    }
}
