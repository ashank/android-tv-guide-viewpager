package tv.guide.pager.adapter;

import android.content.Context;

import java.util.List;

import tv.guide.pager.R;
import tv.guide.pager.model.DPGuideModel;
import tv.guide.pager.utils.ListUtils;


public class DPGuidePagerAdapter extends DPBasePageAdapter<DPGuideModel>{

    private boolean mIsInfiniteLoop;

    public DPGuidePagerAdapter(Context context, List<DPGuideModel> list, int layoutId) {
        super(context,list,layoutId);
    }

    @Override
    public void convert(DPAdapterViewHolder holder, DPGuideModel model) {
        holder.setImageResource(R.id.page_item_imageview,model.getImageResId());
    }


    @Override
    public int getCount() {
        return mIsInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getCount(mDataList);
    }

    private int getPosition(int position) {
        return mIsInfiniteLoop ? position % mDataList.size() : position;
    }

    public DPGuidePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		mIsInfiniteLoop = isInfiniteLoop;
		return this;
	}
   
}
