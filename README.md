# Android-tv-guide-viewpager
  感谢[Trinea](https://github.com/Trinea/android-common)开源工具的帮助，了解了循环机制
  
### 自定义ViewPager，实现TV开机引导图,同时支持手机
1、Android原生的viewpager速度不能自己控制，所以我们需要自定义滑动速度，达到我们想要的效果
TV上不再是手势滑动，而是遥控按键，所以TV和手机上的ViewPager的实现效果和触发条件略有不同
而且TV上的ViewPage可能需要自动切换到下一页的需求<br/>

2、自定义viewpager,可以直接引用[ScrollViewPager.java](https://github.com/whiskeyfei/android-tv-guide-viewpager/blob/master/app/src/main/java/tv/guide/pager/widget/ScrollViewPager.java)<br/>

3、viewpager + fragment,详细使用可以查看[MainFragmentActivity.java](https://github.com/whiskeyfei/android-tv-guide-viewpager/blob/master/app/src/main/java/tv/guide/pager/MainFragmentActivity.java)<br/>

4、自定义viewpager,自动切换+循环播放[GuideViewPage](https://github.com/whiskeyfei/android-tv-guide-viewpager/blob/master/app/src/main/java/tv/guide/pager/widget/GuideViewPage.java),详细使用可以查看[GuideViewPagerActivity.java](https://github.com/whiskeyfei/android-tv-guide-viewpager/blob/master/app/src/main/java/tv/guide/pager/GuideViewPagerActivity.java)<br/>

### 效果图
  <img src="demo/demo.gif" width = "380" height = "676" alt="图片名称" align=center />

###7.18更新
1、编写通用DPBasePageAdapter，供扩展使用，DPAdapterViewHolder使用[DPBaseAdapter-Android](https://github.com/whiskeyfei/DPBaseAdapter-Android.git)中的viewholder<br/>
2、添加数据model和布局item<br/>
3、动态设置页面滑动速度<br/>
3、添加每一页监听，保存item当前位置position<br/>

### 7.19更新
1、添加viewPager循环机制<br/>
2、添加adapter多个构造函数，方便调用<br/>

### 7.20更新
1、添加viewPager当前position 回调<br/>
2、界面现实index/count 数比<br/>

### 7.21 更新
1、添加自定义viewpager，可以控制滑动速度<br/>
2、添加viewpager＋fragment方式<br/>

###下一步
1、添加viewPager循环机制 7.20已完成<br/>
2、支持new出来的view添加,不再只是布局(意义不大，最初GuidePagerAdapter使用new的方式，可参考)<br/>
3、抽象出BaseFragment<br/>

### 实例
目前需要配置Manifest文件<br/>
1、[GuideViewPagerActivity.java](https://github.com/whiskeyfei/android-tv-guide-viewpager/blob/master/app/src/main/java/tv/guide/pager/GuideViewPagerActivity.java)支持自动滑动，遥控按键及手势和循环播放<br/>
2、[MainFragmentActivity.java](https://github.com/whiskeyfei/android-tv-guide-viewpager/blob/master/app/src/main/java/tv/guide/pager/MainFragmentActivity.java)两种模式，原生viewpager＋imageview，自定义viewpager＋fragment<br/>


###使用方法
xml文件:
```
  <tv.guide.pager.widget.GuideViewPage
        android:id="@+id/vp_activity"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
  </tv.guide.pager.widget.GuideViewPage>
  
GuideViewPage mViewPage = (GuideViewPage) findViewById(R.id.vp_activity);
```

###代码实现

```java
  //三个参数
  mGuidePagerAdapter = new DPGuidePagerAdapter(mContext,mImageIdList,R.layout.page_item);
  mViewPage.setAdapter(mGuidePagerAdapter);
  mViewPage.startAutoScroll();//设置自动播放
  mViewPage.setScollTime(5);//设置滑动速度
  
  //选择不同方式调用
  mGuidePagerAdapter = new DPGuidePagerAdapter(mContext,mImageIdList,R.layout.page_item).setInfiniteLoop(true);
  mGuidePagerAdapter = new DPGuidePagerAdapter(mContext,mImageIdList,R.layout.page_item,true);
  //...
  mGuidePagerAdapter.setInfiniteLoop(true);
```

###页面处理
  目前逻辑是写在activity中的，以后打算抽到viewpager控件中，采用回调方式

```java
	private boolean dispatchKeyMultipleGuide(KeyEvent event) {
		int keyCode = event.getKeyCode();
		ViewPagerState state = mViewPage.getState();
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_MENU) {
				return true;
			}
			if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_BACK) {
				if (state == ViewPagerState.RIGHT_EDGE) {
					startHomePage();
					return true;
				}
				mViewPage.scrollOnce();
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

```
###循环机制
	增长数据重复，实际上真正view个数不变，通过去余来获得循环的position数据
```
   @Override
    public int getCount() {
        return mIsInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getCount(mDataList);
    }

  private int getPosition(int position) {
        return mIsInfiniteLoop ? position % mDataList.size() : position;
    }
    
  public DPGuidePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
	mIsInfiniteLoop = isInfiniteLoop;
	return this;
   }

```

### 回调接口

```
	public interface ViewPagerOnSelectedListener {
	    void onViewPageSelected(int position);
	}

```
### 接口使用
```
	@Override
	public void onViewPageSelected(int position) {
	//判断当前是否是循环，如果循环则对position取余
	if (mGuidePagerAdapter.getInfiniteLoop()){
		position = position % COUNT;
	}
		mIndexText.setText(new StringBuilder().append(position + 1).append("/").append(COUNT));
	}
```

###生命周期
  遵循Activity的生命周期
```java

  	@Override
	protected void onPause() {
		super.onPause();
		mViewPage.stopAutoScroll();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mViewPage.stopAutoScroll();
	}
	@Override
	protected void onResume() {
		super.onResume();
		mViewPage.startAutoScroll();
	}
```

### Manifest文件配置
	使用标签来制定主activity也就是main <intent-filter>
```
	 <activity
            android:name=".MainFragmentActivity"
            android:label="@string/title_activity_main_fragment" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
```

### 自定义viewpager
重写Scroller，更改平易时间

```
	class FixedSpeedScroller extends Scroller {

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy,int duration) {
			super.startScroll(startX, startY, dx, dy, mScrollDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			super.startScroll(startX, startY, dx, dy, mScrollDuration);
		}
	}
```


布局中使用
```
    <tv.guide.pager.widget.ScrollViewPager
        android:id="@+id/viewpager_f2"
        android:layout_width="match_parent"
        android:layout_height="320dp"
        android:layout_below="@+id/viewpager_f"
        android:background="#CC99FF">
    </tv.guide.pager.widget.ScrollViewPager>
```

