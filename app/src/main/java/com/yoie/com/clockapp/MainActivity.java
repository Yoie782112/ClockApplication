package com.yoie.com.clockapp;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yoie.com.clockapp.ObjDataStructure.Clock;
import com.yoie.com.clockapp.SqlDB.ClockAppSQLiteDbHelper;
import com.yoie.com.clockapp.SqlDB.ClockDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext = null;
    private ClockAppSQLiteDbHelper mSQLHelper = null;
    private Toolbar mToolBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
        setUI();

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
        switch ( id ){
            case R.id.action_delete:
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

    private void setData(){
        mContext = this.getBaseContext();
        mSQLHelper = new ClockAppSQLiteDbHelper(mContext);
        mSQLHelper.onCreate(ClockAppSQLiteDbHelper.getDatabase(mContext));
    }

    private void setUI(){
        setContentView(R.layout.activity_main);

        setMenu();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                if(mSQLHelper != null)
                {
                    ClockDao dao = new ClockDao(mContext);
                    dao.sample();
                    List<Clock> clockList = dao.getAll();
                    int size = clockList.size();
                }




            }
        });
    }


    private void setMenu(){
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
                        break;
                    case R.id.action_hints:
                        break;
                    default:
                        break;

                }
                String title = item.toString();
                return true;
            }


        });
        //setSupportActionBar(mToolBar);  寫在這裡事件會跑到onOptionsItemSelected(item)

        getSupportActionBar().setHomeButtonEnabled(true);
    }



}
