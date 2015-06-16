package tv.guide.pager;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import tv.guide.pager.adapter.GuidePagerAdapter;
import tv.guide.pager.model.ViewPagerState;
import tv.guide.pager.utils.ListUtils;
import tv.guide.pager.widget.GuideViewPage;

public class GuideViewPagerActivity extends Activity {
	protected GuideViewPage mViewPage;
	private int[] ICON_MAP_COMMON = { R.drawable.recommend_default_icon_1, R.drawable.recommend_default_icon_2,
			R.drawable.recommend_default_icon_3, R.drawable.recommend_default_icon_4,
			R.drawable.recommend_default_icon_5};
	private List<Integer> mImageIdList  = new ArrayList<Integer>();
	private GuidePagerAdapter mGuidePagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		initView();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!ListUtils.isEmpty(mImageIdList)) {
			mImageIdList.clear();
			mImageIdList = null;
		}
	}
	
	private void initView() {
		mViewPage = (GuideViewPage) findViewById(R.id.vp_activity);
		for (int i = 0; i < ICON_MAP_COMMON.length; i++) {
			mImageIdList.add(ICON_MAP_COMMON[i]);
		}
		mGuidePagerAdapter = new GuidePagerAdapter(getApplicationContext(), mImageIdList).setInfiniteLoop(false);
		mViewPage.setAdapter(mGuidePagerAdapter);
		mViewPage.startAutoScroll();//设置自动播放
		mViewPage.setScollTime(5);//设置滑动速度
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return dispatchKeyMultipleGuide(event);
	}
	
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

	private void startHomePage() {
		System.out.println("startHomePage");
	}
	
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
	
}