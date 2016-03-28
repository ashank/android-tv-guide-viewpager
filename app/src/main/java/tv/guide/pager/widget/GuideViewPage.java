package tv.guide.pager.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Interpolator;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import tv.guide.pager.inter.INextEvent;
import tv.guide.pager.inter.ViewPagerOnSelectedListener;
import tv.guide.pager.model.ViewPagerState;

public class GuideViewPage extends ViewPager {

	private static final String TAG = "GuideViewPage";
	private int mDuration = 2000;
	private long mInterval = 2000;
	private int mScollTime = 2;
	private boolean mIsAutoScroll = false;
	private static final int SCROLL_WHAT = 0;
	private static final int SCROLL_WHAT2 = 1;

	private static final int FIRST_ITEM_INDEX = 0;
	
	private GuideScroller mSelfScroller;
	private GuideHandler mHandler;
	private ViewPagerState mState = ViewPagerState.LEFT_EDGE;
	private ViewPagerOnSelectedListener mViewPagerOnSelectedListener;
	public INextEvent mNextEvent;

	public void setViewPagerOnSelectedListener(ViewPagerOnSelectedListener mViewPagerOnSelectedListener) {
		this.mViewPagerOnSelectedListener = mViewPagerOnSelectedListener;
	}

	public GuideViewPage(Context context) {
		super(context);
		init();
	}

	public GuideViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		mHandler = new GuideHandler(this);
		setViewPagerScroller();
		setOnPageChangeListener(mOnPageChangeListener);
	}
	
	public ViewPagerState getState() {
		return mState;
	}

	public int getDuration() {
		return mDuration;
	}
	
	public void setDuration(int duration) {
		mDuration = duration;
	}
	
	public boolean isIsAutoScroll() {
		return mIsAutoScroll;
	}

	public void setIsAutoScroll(boolean isAutoScroll) {
		mIsAutoScroll = isAutoScroll;
	}
	
	public long getInterval() {
		return mInterval;
	}

	public void setInterval(long interval) {
		mInterval = interval;
	}
	
	public void setScollTime(int scollTime) {
		mScollTime = scollTime;
		mSelfScroller.setScrollDuration(mScollTime);
	}

	public void startAutoScroll() {
        mIsAutoScroll = true;
        sendScrollMessage(mInterval + mSelfScroller.getDuration());
    }
	
	public void stopAutoScroll() {
		mIsAutoScroll = false;
		mHandler.removeMessages(SCROLL_WHAT);
	}
	
	public void sendScrollMessage(long delayTimeInMills) {
		mHandler.removeMessages(SCROLL_WHAT);
		if (mState == ViewPagerState.RIGHT_EDGE){
			mHandler.sendEmptyMessage(SCROLL_WHAT2);
		}else{
			mHandler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
		}
	}

	private void setViewPagerScroller() {
		try {
            Field filed = ViewPager.class.getDeclaredField("mScroller");
            filed.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            mSelfScroller = new GuideScroller(getContext(), (Interpolator)interpolatorField.get(null));
            filed.set(this, mSelfScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void scrollOnce() {
		PagerAdapter pagerAdapter = getAdapter();
		int currentItem = getCurrentItem();
		int totalCount = pagerAdapter.getCount();
		if (pagerAdapter == null || totalCount <= 1) {
			return;
		}

		int nextItem = ++currentItem;
		if (nextItem != totalCount) {
			setCurrentItem(nextItem, true);
		}
	}

	private boolean hasViewPager(){
		return mViewPagerOnSelectedListener != null;
	}
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
	    @Override
	    public void onPageSelected(int position) {
			if(hasViewPager()) mViewPagerOnSelectedListener.onViewPageSelected(position);
	    	stopAutoScroll();
	    	initEgdeState();
			sendScrollMessage(getInterval() + mSelfScroller.getDuration());
	    }

	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
			if(hasViewPager()) mViewPagerOnSelectedListener.onPageScrolled(arg0,arg1,arg2);
	    }

	    @Override
	    public void onPageScrollStateChanged(int state) {
			if(hasViewPager()) mViewPagerOnSelectedListener.onPageScrollStateChanged(state);
	    }
	};
	
	private void initEgdeState() {
		if (getCurrentItem() == FIRST_ITEM_INDEX) {
			mState = ViewPagerState.LEFT_EDGE;
			Log.e(TAG,"left");
		} else if (getCurrentItem() == getAdapter().getCount() - 1) {
			mState = ViewPagerState.RIGHT_EDGE;
			Log.e(TAG,"right");
		} else {
			mState = ViewPagerState.MIDDLE;
			Log.e(TAG,"middle");
		}
	}

	public boolean dispatchKeyGuidePage(KeyEvent event) {
		int keyCode = event.getKeyCode();
		ViewPagerState state = getState();
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_MENU) {
				return super.dispatchKeyEvent(event);
			}
			if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_BACK) {
				if (state == ViewPagerState.RIGHT_EDGE) {
					if (mNextEvent != null){
						mNextEvent.start();
					}
					return true;
				}
				scrollOnce();
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	public void setStartNextEvent(INextEvent startNextEvent) {
		this.mNextEvent = startNextEvent;
	}

	private static class GuideHandler extends Handler {

		private static final String TAG = "GuideHandler";
		private final WeakReference<GuideViewPage> guideViewPageWeakReference;

		public GuideHandler(GuideViewPage autoScrollViewPager) {
			this.guideViewPageWeakReference = new WeakReference<>(autoScrollViewPager);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			GuideViewPage pager = this.guideViewPageWeakReference.get();
			if (pager == null) {
				return;
			}
			switch (msg.what) {
				case SCROLL_WHAT2:
					if (pager.mNextEvent != null) {
						pager.mNextEvent.start();
					}
					break;
				case SCROLL_WHAT:
					Log.e(TAG, "pager != null");
					pager.scrollOnce();
					pager.sendScrollMessage(pager.getInterval() + pager.mSelfScroller.getDuration());
					break;
			}
		}
	}
	
}