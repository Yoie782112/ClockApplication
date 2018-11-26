package com.yoie.com.clockapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.yoie.com.clockapp.Model.Clock;
import com.yoie.com.clockapp.Services.NotificationService;
import com.yoie.com.clockapp.SqlDB.ClockAppSQLiteDbHelper;
import com.yoie.com.clockapp.SqlDB.ClockDaoImp;
import com.yoie.com.clockapp.ViewModel.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity{

    private final static String TAG = "";
    private Context mContext = null;
    private ClockAppSQLiteDbHelper mSQLHelper = null;
    private Toolbar mToolBar = null;
    private ClockDaoImp mClockDataBaseProxy = null;
    private List<Clock> mClockList = new ArrayList<Clock>();
    private ListViewAdapter mListViewAdapter;
    private ListView mListView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        startNotifiction();

        ActivityLifeCycleHelper
                .init(this.getApplication())
                .setOnEnterForegroundListener(new ActivityLifeCycleHelper.OnEnterForegroundListener() {
                    @Override
                    public void onEnterForeground() {
                        Log.d("LifeCycle","onEnterForeground");

                    }

                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch ( id ){
            case R.id.action_delete:
                ClockDaoImp.sDeleteTableSQL();
                break;
            case R.id.action_hints:
                break;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initData(){
        mContext = this.getBaseContext();
        mSQLHelper = new ClockAppSQLiteDbHelper(mContext);
        mSQLHelper.onCreate(ClockAppSQLiteDbHelper.getDatabase(mContext));
        mClockDataBaseProxy = new ClockDaoImp(mContext);
        refresh();
    }

    private void initView(){
        setContentView(R.layout.activity_main);
        mListView = ((ListView) findViewById(R.id.lv));
        initMenu();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                if(mSQLHelper != null)
                {
                    mClockDataBaseProxy = new ClockDaoImp(mContext);
                    mClockDataBaseProxy.test();
                    refresh();
                }

            }
        });
    }


    private void initMenu(){
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(mToolBar);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                switch ( id ){
                    case R.id.action_delete:
                        mClockDataBaseProxy.deleteAll();
                        refresh();
                        break;
                    case R.id.action_hints:
                        break;
                    default:
                        break;

                }
                return true;
            }


        });
        //setSupportActionBar(mToolBar);  寫在這裡事件會跑到onOptionsItemSelected(item)
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    public void refresh(){
        Observable<List<Clock>> observable = Observable.create(new ObservableOnSubscribe<List<Clock>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Clock>> e) throws Exception {
                //Use onNext to emit each item in the stream//
                e.onNext(mClockDataBaseProxy.getAll());
                e.onComplete();
            }
        });

        Observer<List<Clock>> observer = new Observer<List<Clock>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(List<Clock> value) {
                mClockList = value;
                Log.e(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                if(mClockList.size() <=0)
                    Log.e(TAG, "no Clocks");
                else {
                    for(int i = 0 ; i <mClockList.size(); i++)
                    {
                        Clock clock = mClockList.get(i);
                        clock.setNumber(String.valueOf(i));
                        mClockList.set(i, clock);
                    }
                }
                mListViewAdapter = new ListViewAdapter(mContext, R.layout.clock_item, mClockList, com.yoie.com.clockapp.BR.clock);
                mListView.setAdapter(mListViewAdapter);
            }
        };
        observable.subscribe(observer);

    }


    private void startNotifiction(){
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }

    private void stopNotifiction(){
        Intent intent = new Intent(this, NotificationService.class);
        stopService(intent);
    }


}
