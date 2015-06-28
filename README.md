# android-tv-guide-viewpager
  感谢[Trinea](https://github.com/Trinea/android-common)开源工具的帮助
#自定义ViewPager，实现TV开机引导图
Android原生的viewpager速度不能自己控制，所以我们需要自定义滑动速度，达到我们想要的效果
TV上不再是手势滑动，而是遥控按键，所以TV和手机上的ViewPager的实现效果和触发条件略有不同
而且TV上的ViewPage可能需要自动切换到下一页的需求

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
