package com.example.photoviewer.main;

import com.example.photoviewer.R;
import com.example.photoviewer.lib.Info;
import com.example.photoviewer.lib.PhotoView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


/**
 * Created by liuheng on 2015/8/19.
 */
public class PhotoBrowse extends Activity {

    int[] imgs = new int[]{R.drawable.aaa, R.drawable.bbb, R.drawable.ccc, R.drawable.ddd, R.drawable.ic_launcher, R.drawable.image003};

    GridView gv;

    View mParent;
    View mBg;
    PhotoView mPhotoView;
    Info mInfo;
    
    View mViewpagerFrame;
    private ViewPager mViewPager;
    View mViewBg;
    
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo_browse);

        in.setDuration(300);
        out.setDuration(300);
        
        mViewpagerFrame=findViewById(R.id.acitivity_photo_frame);
        mViewPager = (ViewPager) findViewById(R.id.acitivity_photo_viewpager);
        mViewBg = findViewById(R.id.acitivity_photo_bg);
        
        mParent = findViewById(R.id.parent);
        mBg = findViewById(R.id.bg);
        mPhotoView = (PhotoView) findViewById(R.id.img);
        gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                PhotoView p = new PhotoView(PhotoBrowse.this);
                p.setLayoutParams(new AbsListView.LayoutParams((int) (getResources().getDisplayMetrics().density * 100), (int) (getResources().getDisplayMetrics().density * 100)));
                p.setScaleType(ImageView.ScaleType.CENTER_CROP);
                p.setImageResource(imgs[position]);
                // 把PhotoView当普通的控件把触摸功能关掉
                p.disenable();
                return p;
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mPhotoView.setImageResource(imgs[position]);
                mViewBg.startAnimation(in);
//                //mParent.setVisibility(View.VISIBLE);
                //mPhotoView.animaFrom(mInfo);
            	mViewpagerFrame.setVisibility(View.VISIBLE);
            	mViewPager.setCurrentItem(position);
            }
        });

        
        mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(PhotoBrowse.this);
                view.enable();
                view.setImageResource(imgs[position]);
                container.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewBg.startAnimation(out);
                        mViewpagerFrame.setVisibility(View.GONE);
                    }
                });
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        
    }

    @Override
    public void onBackPressed() {
        if (mViewpagerFrame.getVisibility() == View.VISIBLE) {
        	mViewBg.startAnimation(out);
        	mViewpagerFrame.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
