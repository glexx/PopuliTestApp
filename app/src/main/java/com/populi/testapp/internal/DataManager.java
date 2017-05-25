package com.populi.testapp.internal;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.populi.testapp.internal.model.City;
import com.populi.testapp.internal.model.Country;
import com.populi.testapp.internal.model.Tour;
import com.populi.testapp.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander Gavrikov.
 */

public class DataManager {

    private static final String TAG = "DataManager";
    private static final String TOURS_FILE_NAME = "tours.dat";

    private static DataManager instance = new DataManager();

    private HashMap<String, Tour> tours = new HashMap<>();
    private List<Country> dataTree;
    private Context context;
    private static TaskUpdateTours taskUpdateTours;
    private OnToursReadyListener taskUpdateToursListener;

    public interface OnToursReadyListener{
        void onToursUpdate(boolean result);
    }


    public static DataManager getInstance() {
        return instance;
    }

    public void initialize(Context context){
        this.context = context.getApplicationContext();

        String dataJson = readFromFile();
        dataTree = getDataTreeFromJson(dataJson);
        prepareRuntimeData();
    }

    public synchronized Tour getTour(String id){
        return tours.get(id);
    }

    public synchronized List<Tour> getAllTours(){
        return new ArrayList<>(tours.values());
    }

    public synchronized void updateTours(OnToursReadyListener listener){
        this.taskUpdateToursListener = listener;
        if (taskUpdateTours == null){
            taskUpdateTours = new TaskUpdateTours();
            taskUpdateTours.execute();
        }
    }

    public synchronized List<Country> getDataTree(){
        return new ArrayList<>(dataTree);
    }

    synchronized void updateDataTree(String updatedDataJson) {

        List<Country> newDataTree = getDataTreeFromJson(updatedDataJson);
        if (newDataTree != null){
            dataTree = newDataTree;
            prepareRuntimeData();
            writeToFile(updatedDataJson);
        }
    }

    private List<Country> getDataTreeFromJson(String dataJson){
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Country>>(){}.getType();
            return gson.fromJson(dataJson, listType);
        }
        catch (Exception e){
            Log.e(TAG, "Issues with reading the model after update", e);
        }
        return null;
    }

    private void prepareRuntimeData(){
        if (dataTree == null){
            return;
        }

        tours.clear();
        for (Country country : dataTree) {

            for (City city : country.getCities()) {
                city.setCountry(country);

                for (Tour tour : city.getTours()) {
                    tour.setCity(city);
                    tours.put(tour.getUid(), tour);
                }
            }
        }
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    context.openFileOutput(TOURS_FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {
        String ret = "";

        try {
            InputStream inputStream = getDataFileInputStream();
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }

    protected InputStream getDataFileInputStream(){
        try {
            return context.openFileInput(TOURS_FILE_NAME);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        }
        return null;
    }

    private class TaskUpdateTours extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            String result = (new RestAdapter()).getTours();
            if (result != null) {
                DataManager.getInstance().updateDataTree(result);
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            synchronized (DataManager.this) {
                if (taskUpdateToursListener != null) {
                    taskUpdateToursListener.onToursUpdate(result);
                }
                taskUpdateTours = null;
            }
        }
    }

}
