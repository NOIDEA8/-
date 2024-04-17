package com.example.myapplication.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.ActivityCollector.BaseActivity;
import com.example.myapplication.VPAdaptor.MyFragmentStateVPAdaptor;
import com.example.myapplication.Fragment.MainFragment;
import com.example.myapplication.Fragment.MineFragment;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Mainpage extends BaseActivity implements View.OnClickListener {
    private ViewPager mViewPager;//300659，退出程序就退出应用的特性要改912325
    private MyFragmentStateVPAdaptor mStateVPAdaptor;
    private List<Fragment> mFragmentList;
    private TextView tMain,tMine;
    private ImageView iMain,iMine;
    private LinearLayout lMain,lMine;
    private EditText show_search;
    private EditText real_search;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        //实例对应


        initView();
        initData();

        //创建一个fragment的adaptor，然后设置为ViewPager的adaptor，这样就可把viewpager里放入碎片（主要贪图数据源）
        mStateVPAdaptor = new MyFragmentStateVPAdaptor(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mStateVPAdaptor);
        //页面滑动的监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //获取当前页位置
            @Override
            public void onPageSelected(int position) {
                onViewPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setClickListener();
        //默认首页
        onViewPagerSelected(0);
    }

    private void setClickListener() {
        lMine.setOnClickListener(this);
        lMain.setOnClickListener(this);
        tMine.setOnClickListener(this);
        tMain.setOnClickListener(this);
        iMine.setOnClickListener(this);
        iMain.setOnClickListener(this);

    }

    //自定义方法
    private void onViewPagerSelected(int position) {
        primaryButtonState();
        switch (position){
            case 0:
                //Color.parseColor,将括号内的颜色转为int以适应函数
                lMain.setBackgroundColor(Color.parseColor("#03A9F4"));
                break;
            case 1:
                lMine.setBackgroundColor(Color.parseColor("#03A9F4"));
                break;
            default:
                break;
        }
    }

    private void primaryButtonState() {
        lMain.setBackgroundColor(Color.parseColor("#FFFFFF"));
        lMine.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp);
        tMain=findViewById(R.id.main_text);
        tMine=findViewById(R.id.mine_text);
        iMain=findViewById(R.id.main_icon);
        iMine=findViewById(R.id.mine_icon);
        lMain=findViewById(R.id.main_page);
        lMine=findViewById(R.id.mine_page);

    }
    
    private void initData() {
        mFragmentList = new ArrayList<>();
        Intent intent = getIntent();
        MineFragment mineFragment = MineFragment.newInstance(intent.getStringExtra("name"),
                intent.getStringExtra("account"));
        mineFragment.onAttach(Mainpage.this);
        MainFragment mainFragment=MainFragment.newInstance(null,null);
        mFragmentList.add(mainFragment);
        mFragmentList.add(mineFragment);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.main_text:
            case R.id.main_page:
            case R.id.main_icon:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.mine_text:
            case R.id.mine_icon:
            case R.id.mine_page:
                mViewPager.setCurrentItem(1);
                break;
            default:
                break;

        }
    }
}