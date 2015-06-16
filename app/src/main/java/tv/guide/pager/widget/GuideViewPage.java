package tv.guide.pager.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import tv.guide.pager.model.ViewPagerState;

public class GuideViewPage extends ViewPager {

	private int mDuration = 2000;
	private long mInterval = 2000;
	private int mScollTime = 2;
	private boolean mIsAutoScroll = false;
	private static final int SCROLL_WHAT = 0;
	private static final int FIRST_ITEM_INDEX = 0;
	
	private GuideScroller mSelfScroller;
	private GuideHandler mHandler;
	private ViewPagerState mState = ViewPagerState.LEFT_EDGE;
	
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
		mHandler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
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
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
	    @Override
	    public void onPageSelected(int position) {
	    	stopAutoScroll();
	    	initEgdeState();
	    	sendScrollMessage(getInterval() + mSelfScroller.getDuration());
	    }

	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
	    }

	    @Override
	    public void onPageScrollStateChanged(int state) {
	    }
	};
	
	private void initEgdeState() {
		if (getCurrentItem() == FIRST_ITEM_INDEX) {
			mState = ViewPagerState.LEFT_EDGE;
			System.out.println("left");
		} else if (getCurrentItem() == getAdapter().getCount() - 1) {
			mState = ViewPagerState.RIGHT_EDGE;
			System.out.println("right");
		} else {
			mState = ViewPagerState.MIDDLE;
			System.out.println("middle");
		}
	}
	
	private static class GuideHandler extends Handler {

		private final WeakReference<GuideViewPage> p;

		public GuideHandler(GuideViewPage autoScrollViewPager) {
			this.p = new WeakReference<GuideViewPage>(autoScrollViewPager);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCROLL_WHAT:
				 GuideViewPage pager = this.p.get();
                 if (pager != null) {
                     pager.scrollOnce();
                     pager.sendScrollMessage(pager.getInterval() + pager.mSelfScroller.getDuration());
                 }
				break;
			}
		}
	}
	
}