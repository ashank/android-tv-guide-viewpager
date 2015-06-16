package tv.guide.pager.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class GuideScroller extends Scroller {
	 private double mScrollFactor = 2;
	
	public GuideScroller(Context context) {
		super(context);
	}

	public GuideScroller(Context context, Interpolator interpolator) {
		super(context, interpolator);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy) {
		super.startScroll(startX, startY, dx, dy);
	}
	
	public void setScrollDuration(double scrollFactor) {
		mScrollFactor = scrollFactor;
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		super.startScroll(startX, startY, dx, dy, (int)(duration * mScrollFactor));
	}
}
