package agrawal.snehal.allwidgetexample.tabhost;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;

import java.util.HashMap;

import agrawal.snehal.allwidgetexample.R;


public class TabHostFragmentActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    private TabHost mTabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<>();
    private TabInfo mLastTab = null;

    private class TabInfo {
        private String tag;
        private Class clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }

    private class TabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost_with_fragment);
        initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState);
    }

    private void initialiseTabHost(Bundle args) {
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo;
        TabHostFragmentActivity.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab1").setIndicator("Tab 1"), (tabInfo = new TabInfo("Tab1", Tab1Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        TabHostFragmentActivity.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab2").setIndicator("Tab 2"), (tabInfo = new TabInfo("Tab2", Tab2Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        TabHostFragmentActivity.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab3").setIndicator("Tab 3"), (tabInfo = new TabInfo("Tab3", Tab3Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        this.onTabChanged("Tab1");
        mTabHost.setOnTabChangedListener(this);
    }


    private static void addTab(TabHostFragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        tabSpec.setContent(activity.new TabFactory(activity));
        String tag = tabSpec.getTag();
        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }

        tabHost.addTab(tabSpec);
    }

    public void onTabChanged(String tag) {
        TabInfo newTab = this.mapTabInfo.get(tag);
        if (mLastTab != newTab) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(this,
                            newTab.clss.getName(), newTab.args);
                    ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }

            mLastTab = newTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
        }
    }
}
