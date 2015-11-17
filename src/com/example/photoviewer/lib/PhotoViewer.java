package com.example.photoviewer.lib;

import com.example.photoviewer.main.PhotoSingleActivity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

/***
 * photoViewer 浏览控件
 * @author panhuachao
 * 具体见：https://github.com/panhuachao/PhotoViewer
 */
public class PhotoViewer extends FrameLayout {
	
	private boolean isShowAnimation=false;
	private ImageView ivBg=null;  //效果背景imageview
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);
    FrameLayout.LayoutParams match_parent = new FrameLayout.LayoutParams(     
			FrameLayout.LayoutParams.MATCH_PARENT,     
			FrameLayout.LayoutParams.MATCH_PARENT     
    );
    
	private Context mContext=null;
	private PhotoViewer pvthis;
	
	/**单张图片显示相关参数**/
	private int imgId;
	private PhotoView photoviewSingle;
	private Info animateInfo;
	/**Viewpager相关参数*/
	private int[] imgIds;
	private ViewPager viewPagerContainer;
	private int bgColor=Color.parseColor("#000000");
	
	public PhotoViewer(Context context) {
		super(context);
		init();
		mContext=context;
		pvthis=this;
		// TODO Auto-generated constructor stub
	}
	public PhotoViewer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		mContext=context;
		pvthis=this;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化设置framelayout
	 */
	private void init()
	{
		//设置Framelayout属性
		this.setBackgroundColor(bgColor);
		this.setVisibility(View.GONE);
	}
	
	public void show()
	{
		this.setVisibility(View.VISIBLE);
	}
	/**
	 * 显示控件
	 */
	public void show(int _position)
	{
		if(isShowAnimation==true)
    	{
    		//ivBg.startAnimation(in);
    	}
		viewPagerContainer.setCurrentItem(_position,false);
		this.setVisibility(View.VISIBLE);
	}
	
	/***
	 * 隐藏控件
	 */
	public void hide()
	{
		if(isShowAnimation==true)
    	{
    		ivBg.startAnimation(out);
    	}
        this.setVisibility(View.GONE);
	}
	
	/**
	 * 获取显示与否状态（true:正在显示）
	 * @return
	 */
	public boolean isShow()
	{
		if (this.getVisibility() == View.VISIBLE) {
	        return true;
	     } else {
	        return false;
	    }
	}
	
	/**
	 * 单张图片点击效果
	 * @param _imgid
	 */
	public void setViewer(int _imgid)
	{
		this.imgId=_imgid;
		if(photoviewSingle==null)
		{
			photoviewSingle=new PhotoView(mContext);
			photoviewSingle.enable();
			this.addView(photoviewSingle,match_parent);
		}
		photoviewSingle.setImageResource(_imgid);
		photoviewSingle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//消失
				pvthis.animateTo();
			}
		});
		this.show();
	}
	
	/***
	 * 单张图片出现效果
	 * @param frompv
	 */
	public void animateFrom(PhotoView frompv,int imgid)
	{
		this.show();
		animateInfo = frompv.getInfo();
         //让img2从img1的位置变换到他本身的位置
		if(photoviewSingle==null)
		{
			photoviewSingle=new PhotoView(mContext);
			photoviewSingle.enable();
			this.addView(photoviewSingle,match_parent);
		}
		photoviewSingle.setImageResource(imgid);
		photoviewSingle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//消失
				pvthis.animateTo();
			}
		});
		photoviewSingle.animaFrom(animateInfo);
		photoviewSingle.setVisibility(View.VISIBLE);
	}
	
	/***
	 * 单张图片消失效果
	 */
	public void animateTo()
	{
		if(animateInfo!=null)
		{
			photoviewSingle.animaTo(animateInfo, new Runnable() {
	            @Override
	            public void run() {
	            	pvthis.hide();
	            }
	        });
		}else
		{
			this.hide();
		}
	}
	/***
	 * 设置viewpager
	 * @param _imgids
	 */
	public void setViewer(int[] _imgids)
	{
		this.imgIds=_imgids;
		viewPagerContainer=new ViewPager(mContext);
		
		//vpContainer.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
		viewPagerContainer.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(mContext);
                view.enable();
                view.setImageResource(imgIds[position]);
                container.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	pvthis.hide();
                    }
                });
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
		this.addView(viewPagerContainer,match_parent);
	}
	
	/***
	 * 设置ViewPager背景色
	 */
	public void setBgColor(int _color)
	{
		bgColor=_color;
		this.setBackgroundColor(_color);
	}
	
	/***
	 * 设置动画显示隐藏效果
	 * @param _show
	 */
	public void setAnimation(boolean _show)
	{
		isShowAnimation=_show;
		if(_show==true)
		{
			in.setDuration(300);
		    out.setDuration(300);
			ivBg=new ImageView(mContext);
			//ivBg.setBackgroundColor(Color.parseColor("#ff000000"));
			this.addView(ivBg,match_parent);
		}
	}
	
	
}
