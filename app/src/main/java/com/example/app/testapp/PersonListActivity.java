package com.example.app.testapp;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.app.testapp.httpCon.PersonFetchr;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PersonListActivity extends SingleFragmentActivity{

    final String LOG_TAG = "myLogs";
    private List<Person> mItems;

    /*public class FetchItemTask extends AsyncTask<Void, Void, List<Person>> {
        @Override
        protected List<Person> doInBackground(Void... params) {
            mItems = new PersonFetchr().fetchItems();
            Log.d(LOG_TAG, "FetchItem finish, start addPerson");
            PersonBank.addPerson(mItems);
            return mItems;
        }

        @Override
        protected void onPostExecute(List<Person> people) {
            super.onPostExecute(people);
        }
    }*/

    @Override
    protected Fragment createFragment() {
        /*Log.d(LOG_TAG, "Start FetchItemTask");
        try {
            new FetchItemTask().execute().get(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/
        return new PersonListFragment();
    }
}
