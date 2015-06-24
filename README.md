# android-tv-guide-viewpager
#自定义ViewPager，实现TV开机引导图
TV上不再是手势滑动，而是遥控按键，所以TV和手机上的ViewPager略有不同

#使用方法
xml文件:
```
  <tv.guide.pager.widget.GuideViewPage
        android:id="@+id/vp_activity"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
  </tv.guide.pager.widget.GuideViewPage>
  
GuideViewPage mViewPage = (GuideViewPage) findViewById(R.id.vp_activity);
```
代码实现:
```
GuideViewPage mViewPage = new GuideViewPage(this);
...
setContentView(mViewPage);
```
