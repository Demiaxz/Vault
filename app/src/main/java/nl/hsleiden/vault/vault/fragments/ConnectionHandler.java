package nl.hsleiden.vault.vault.fragments;

import android.content.Context;

import org.jsoup.nodes.Document;

import nl.hsleiden.vault.vault.Database.DatabaseHelper;
import nl.hsleiden.vault.vault.fetcher.DataFetch;
import nl.hsleiden.vault.vault.resultaatActivity;
import nl.hsleiden.vault.vault.stashGoods;


/**
 * Created by Perseus on 06-04-16.
 */
public class ConnectionHandler implements Runnable{
    private int interval = 60; //Cycle timeout
    private boolean syncEnabled = true;
    private DatabaseHelper database = null;
    private Context context = null;
    private String username;
    private String password;

    //TODO: XXXX data = null;

    public ConnectionHandler(int intervalDurationInMinutes, Context context, String password, String username) throws InterruptedException {
        setPassword(password);
        setUsername(username);
        setInterval(intervalDurationInMinutes);
        setContext(context);
        new resultaatActivity();
        //setDatabase(new DatabaseHelper(getContext()).getHelper(getContext()));
        //setData(getInterval());
    }

    public int getInterval() {
        return interval;
    }

    public DatabaseHelper getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseHelper database) {
        this.database = database;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isSyncEnabled() {
        return syncEnabled;
    }

    public void setSyncEnabled(boolean syncEnabled) {
        this.syncEnabled = syncEnabled;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        Document kip = new DataFetch(getUsername(), getPassword(), getContext()).runAuth();
        new stashGoods(kip);
    }

    private void setData(int intervalDurationInMinutes) throws InterruptedException {
        while (isSyncEnabled()){
            //do some shit
            run();
            //loop the sync
            long sleeptime = (intervalDurationInMinutes * 60) * 1000;
            Thread.sleep(sleeptime);
        }
    }
}
