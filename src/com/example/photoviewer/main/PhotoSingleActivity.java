package com.example.photoviewer.main;

import com.example.photoviewer.R;
import com.example.photoviewer.lib.Info;
import com.example.photoviewer.lib.PhotoView;
import com.example.photoviewer.lib.PhotoViewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
public class PhotoSingleActivity extends Activity {

    int[] imgs = new int[]{R.drawable.aaa, R.drawable.bbb, R.drawable.ccc, R.drawable.ddd, R.drawable.ic_launcher, R.drawable.image003};

    GridView gv;
    PhotoViewer pvFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo_viewpager);

        pvFrame=(PhotoViewer)findViewById(R.id.acitivity_photo_frame);
        
        gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public Object getItem(int position) {
                return imgs[position];
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                PhotoView p = new PhotoView(PhotoSingleActivity.this);
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
            	//无效果显示
            	//pvFrame.setViewer(imgs[position]);
            	//动画效果显示
            	pvFrame.animateFrom((PhotoView)view,imgs[position]);
            }
        });
        
    }

    @Override
    public void onBackPressed() {
        if (pvFrame.isShow()==true) {
        	pvFrame.hide();
        } else {
            super.onBackPressed();
        }
    }
}
