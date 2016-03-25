package com.example.tainttip;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    boolean decisionSent = false;

    private void sendResponse(byte[] response)
    {
        Intent intent = getIntent();
        int uid = intent.getIntExtra("uid", 0);
        if (uid == 0)
        {
            // No valid UID, do nothing
            Log.d("TaintTip", "No UID received");
            return;
        }

        LocalSocket socket = new LocalSocket();
        try
        {
            socket.connect(new LocalSocketAddress("com.example.tainttip.tipsocket" + uid));
            OutputStream socketOutputStream = socket.getOutputStream();
            socketOutputStream.write(response);
            socketOutputStream.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
    }

    private void sendAllow()
    {
        sendResponse("ALLOW".getBytes());
        System.exit(0);
        Runtime.getRuntime().halt(0);
    }

    private void sendDeny()
    {
        sendResponse("DENY".getBytes());
        System.exit(0);
        Runtime.getRuntime().halt(0);
    }

    public void denyButtonHandler(View view)
    {
        sendDeny();
    }

    public void allowButtonHandler(View view)
    {
        sendAllow();
    }

    @Override
    protected void onPause()
    {
        // User cancelled the dialog, send "ALLOW" to API
        sendAllow();
    }

    private String uidToAppName(int uid)
    {
        PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo pi : packages)
        {
            // Skip system apps
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ||
                (pi.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)
            {
                continue;
            }

            //
            // If UID matches, return the application name
            if (pi.applicationInfo.uid == uid)
            {
                return pi.applicationInfo.loadLabel(pm).toString();
            }
        }

        return null;
    }

    private void setHeadText(String text)
    {
        TextView headTextView = (TextView)findViewById(R.id.headText);
        headTextView.setText(text);
    }

    private void refreshTaintList(int taint)
    {
        //
        // Convert taints to taint info list
        TaintInfo[] taintInfoList = TaintParser.taintToTips(taint);
        if (taintInfoList == null || taintInfoList.length == 0)
        {
            return;
        }

        //
        // Convert taint info to list items
        ArrayList<HashMap<String, String>> taintListItems = new ArrayList<HashMap<String, String>>(taintInfoList.length);
        for (TaintInfo taintInfo : taintInfoList)
        {
            // Construct list item
            HashMap<String, String> taintListItem = new HashMap<String, String>();
            taintListItem.put("taintName", taintInfo.name);
            taintListItem.put("taintDescription", taintInfo.description);
            taintListItems.add(taintListItem);
        }

        //
        // Refresh list view
        ListView taintListView = (ListView)findViewById(R.id.permissionList);
        SimpleAdapter taintListAdapter = new SimpleAdapter(
                this,
                taintListItems,
                R.layout.listitem_taint,
                new String[] { "taintName", "taintDescription" },
                new int[] { R.id.permissionName, R.id.permissionDescription }
        );
        taintListView.setAdapter(taintListAdapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //
        // Parse intent
        Intent intent = getIntent();
        if (intent == null)
        {
            setHeadText("Intent is NULL");
            return;
        }

        Bundle intentBundle = intent.getExtras();
        if (intentBundle == null)
        {
            setHeadText("Intent without Key Value Pairs");
            return;
        }

        //
        // Display application info (UID)
        int uid = intentBundle.getInt("uid", 0);
        String appName = uidToAppName(uid);
        setHeadText(appName);

        //
        // Refresh taint list
        int taint = intentBundle.getInt("taint", 0);
        refreshTaintList(taint);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
