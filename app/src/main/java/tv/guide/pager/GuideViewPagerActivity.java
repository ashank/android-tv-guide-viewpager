package tv.guide.pager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tv.guide.pager.adapter.DPGuidePagerAdapter;
import tv.guide.pager.inter.INextEvent;
import tv.guide.pager.inter.ViewPagerOnSelectedListener;
import tv.guide.pager.model.DPGuideModel;
import tv.guide.pager.utils.ListUtils;
import tv.guide.pager.widget.GuideViewPage;

public class GuideViewPagerActivity extends Activity implements ViewPagerOnSelectedListener {

	private static final String TAG = "GuideViewPagerActivity";
	private GuideViewPage mGuideViewPage;
	private Context mContext;

	private int[] ICON_MAP_COMMON = { R.drawable.default_1, R.drawable.default_2,
			R.drawable.default_3, R.drawable.default_5};

	private  final int COUNT = ICON_MAP_COMMON.length;
	private List<DPGuideModel> mImageIdList  = new ArrayList<>();
	private DPGuidePagerAdapter mGuidePagerAdapter;
	private TextView mIndexText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		mContext = this;
		initView();
	}
	
	private void initView() {
		mGuideViewPage = (GuideViewPage) findViewById(R.id.vp_activity);
		mIndexText = (TextView) findViewById(R.id.vp_index);
		mIndexText.setText(new StringBuilder().append("1/").append(COUNT));
		mGuidePagerAdapter = new DPGuidePagerAdapter(mContext,getImageIdList(),R.layout.page_item);
		mGuidePagerAdapter.setInfiniteLoop(true);
		mGuideViewPage.setViewPagerOnSelectedListener(this);
		mGuideViewPage.setAdapter(mGuidePagerAdapter);
		mGuideViewPage.startAutoScroll();//设置自动播放
		mGuideViewPage.setScollTime(5);//设置滑动速度
		mGuideViewPage.setStartNextEvent(mInexNextEvent);
	}

	private INextEvent mInexNextEvent = new INextEvent() {
		@Override
		public void start() {
			startHomePage();
		}
	};
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return mGuideViewPage.dispatchKeyGuidePage(event);
	}

	private List<DPGuideModel> getImageIdList() {
		if (ListUtils.isEmpty(mImageIdList)) {
			int len = ICON_MAP_COMMON.length;
			for (int i = 0; i < len; i++) {
				DPGuideModel model = new DPGuideModel();
				model.setImageResId(ICON_MAP_COMMON[i]);
				model.setDes(i + "des");
				mImageIdList.add(model);
			}
		}
		return mImageIdList;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!ListUtils.isEmpty(mImageIdList)) {
			mImageIdList.clear();
			mImageIdList = null;
		}
	}
	
	private void startHomePage() {
		Toast.makeText(this,"startHomePage",Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mGuideViewPage.stopAutoScroll();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mGuideViewPage.stopAutoScroll();
	}
	@Override
	protected void onResume() {
		super.onResume();
		mGuideViewPage.startAutoScroll();
	}

	@Override
	public void onViewPageSelected(int position) {
		Log.e(TAG,"onViewPageSelected position :" + position);
		if (mGuidePagerAdapter.getInfiniteLoop()) {
			position = position % COUNT;
		}
		mIndexText.setText(new StringBuilder().append(position + 1).append("/").append(COUNT));
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		Log.e(TAG,"onPageScrolled arg0 :" + arg0);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
//		Log.e(TAG,"onPageScrollStateChanged state :" + state);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_guide:
				startIntent(GuideViewPagerActivity.class);
				break;
			case R.id.action_fragment:
				startIntent(MainFragmentActivity.class);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void startIntent(Class<?> c){
		Intent intent = new Intent(this,c);
		startActivity(intent);
		finish();
	}

}