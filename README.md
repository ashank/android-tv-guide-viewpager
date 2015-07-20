# Android-tv-guide-viewpager
  感谢[Trinea](https://github.com/Trinea/android-common)开源工具的帮助，了解了循环机制
  
### 自定义ViewPager，实现TV开机引导图,同时支持手机
Android原生的viewpager速度不能自己控制，所以我们需要自定义滑动速度，达到我们想要的效果
TV上不再是手势滑动，而是遥控按键，所以TV和手机上的ViewPager的实现效果和触发条件略有不同
而且TV上的ViewPage可能需要自动切换到下一页的需求

### 效果图
  <img src="demo/demo.gif" width = "380" height = "676" alt="图片名称" align=center />

###7.18更新
1、编写通用DPBasePageAdapter，供扩展使用，DPAdapterViewHolder使用[DPBaseAdapter-Android](https://github.com/whiskeyfei/DPBaseAdapter-Android.git)中的viewholder<br/>
2、添加数据model和布局item<br/>
3、动态设置页面滑动速度<br/>
3、添加每一页监听，保存item当前位置position<br/>

###下一步
1、添加viewPager循环机制<br/>
2、支持new出来的view添加，不再只是布局<br/>

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

