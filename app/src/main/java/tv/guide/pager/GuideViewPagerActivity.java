package tv.guide.pager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tv.guide.pager.adapter.DPGuidePagerAdapter;
import tv.guide.pager.inter.ViewPagerOnSelectedListener;
import tv.guide.pager.model.DPGuideModel;
import tv.guide.pager.model.ViewPagerState;
import tv.guide.pager.utils.ListUtils;
import tv.guide.pager.widget.GuideViewPage;

public class GuideViewPagerActivity extends Activity implements ViewPagerOnSelectedListener {
	protected GuideViewPage mViewPage;
	private Context mContext;
	private int[] ICON_MAP_COMMON = { R.drawable.recommend_default_icon_1, R.drawable.recommend_default_icon_2,
			R.drawable.recommend_default_icon_3, R.drawable.recommend_default_icon_5};
	private  final int COUNT = ICON_MAP_COMMON.length;
	private List<DPGuideModel> mImageIdList  = new ArrayList<DPGuideModel>();
	private DPGuidePagerAdapter mGuidePagerAdapter;
	private TextView mIndexText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		mContext = this;
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
		mIndexText = (TextView) findViewById(R.id.vp_index);
		mIndexText.setText(new StringBuilder().append("1/").append(COUNT));
		for (int i = 0; i < ICON_MAP_COMMON.length; i++) {
			DPGuideModel model = new DPGuideModel();
			model.setImageResId(ICON_MAP_COMMON[i]);
			model.setDes(i + "des");
			mImageIdList.add(model);
		}
//		mGuidePagerAdapter = new DPGuidePagerAdapter(mContext,mImageIdList,R.layout.page_item).setInfiniteLoop(true);
		mGuidePagerAdapter = new DPGuidePagerAdapter(mContext,mImageIdList,R.layout.page_item,true);
//		mGuidePagerAdapter.setInfiniteLoop(true);
		mViewPage.setViewPagerOnSelectedListener(this);
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
		Toast.makeText(this,"startHomePage",Toast.LENGTH_LONG).show();
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

	@Override
	public void onViewPageSelected(int position) {
	if (mGuidePagerAdapter.getInfiniteLoop()){
		position = position % COUNT;
	}
		mIndexText.setText(new StringBuilder().append(position + 1).append("/").append(COUNT));
	}
}