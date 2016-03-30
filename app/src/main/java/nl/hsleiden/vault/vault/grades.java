package nl.hsleiden.vault.vault;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import org.jsoup.nodes.Document;

public class grades extends AppCompatActivity{
    Document goods = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);
        tabhost.setup();

        TabHost.TabSpec tabSpec1 = tabhost.newTabSpec("per1");
        tabSpec1.setContent(R.id.per1);
        tabSpec1.setIndicator("Per. 1");
        tabhost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabhost.newTabSpec("per2");
        tabSpec2.setContent(R.id.per2);
        tabSpec2.setIndicator("Per. 2");
        tabhost.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 = tabhost.newTabSpec("per3");
        tabSpec3.setContent(R.id.per3);
        tabSpec3.setIndicator("Per. 3");
        tabhost.addTab(tabSpec3);

        TabHost.TabSpec tabSpec4 = tabhost.newTabSpec("per4");
        tabSpec4.setContent(R.id.per4);
        tabSpec4.setIndicator("Per. 4");
        tabhost.addTab(tabSpec4);
    }
}
