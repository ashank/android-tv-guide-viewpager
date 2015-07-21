package tv.guide.pager.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 *  自定义viewpager 调速
 */
public class ScrollViewPager extends ViewPager{
	
    private int mScrollDuration = 400;
    private Interpolator mInterpolator;
    protected Context mContext;
	
    public ScrollViewPager(Context context) {
		super(context);
		init(context);
	}
    
    public ScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
    
    private void init(Context context) {
		mContext = context;
		setScrollerAttrs();
	}
    
    public int getScrollDuration() {
		return mScrollDuration;
	}
    
    public void setScrollDuration(int scrollDuration) {
		this.mScrollDuration = scrollDuration;
		setScrollerAttrs();
	}
    
    public void setInterpolator(Interpolator interpolator){
    	this.mInterpolator = interpolator;
    	setScrollerAttrs();
    }
    
    public Interpolator getInterpolator() {
    	return mInterpolator;
	}
    
	protected void setScrollerAttrs() {
		try {
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			if(mInterpolator == null){
				mInterpolator = new AccelerateDecelerateInterpolator();
			}
			FixedSpeedScroller scroller = new FixedSpeedScroller(mContext, mInterpolator);
			mField.set(this, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
}