package tv.guide.pager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import tv.guide.pager.utils.ListUtils;


public class GuidePagerAdapter extends PagerAdapter{
    private List<Integer> mImageIdList;
    private Context mContext;
    private boolean mIsInfiniteLoop;
    private int mSize;

    public GuidePagerAdapter(Context context, List<Integer> list) {
    	mContext = context;
        mImageIdList = list;
        mIsInfiniteLoop = false;
        mSize = ListUtils.getCount(list);
    }

    @Override
    public int getCount() {
    	return mIsInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getCount(mImageIdList);
    }

    private int getPosition(int position) {
		return mIsInfiniteLoop ? position % mSize : position;
	}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
		View view = null;
		view = getView(position, view, container);
		container.addView(view);
		return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	View view = (View) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    private static class ViewHolder {
		ImageView imageView;
	}
    
   public View getView(int position, View view, ViewGroup container){
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = holder.imageView = new ImageView(mContext);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.imageView.setImageResource(mImageIdList.get(getPosition(position)));
		return view;
   }

   public GuidePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		mIsInfiniteLoop = isInfiniteLoop;
		return this;
	}
   
}
