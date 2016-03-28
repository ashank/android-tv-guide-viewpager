package tv.guide.pager.adapter;

import android.content.Context;

import java.util.List;

import tv.guide.pager.R;
import tv.guide.pager.model.DPGuideModel;


public class DPGuidePagerAdapter extends DPBasePageAdapter<DPGuideModel>{

    public DPGuidePagerAdapter(Context context, List<DPGuideModel> list, int layoutId) {
        super(context,list,layoutId);
    }

    public DPGuidePagerAdapter(Context context, List<DPGuideModel> list, int layoutId,boolean isInfiniteLoop) {
        super(context,list,layoutId,isInfiniteLoop);
    }

    @Override
    public void convert(DPAdapterViewHolder holder, DPGuideModel model) {
        holder.setImageResource(R.id.page_item_imageview, model.getImageResId());
    }


    public DPGuidePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		mIsInfiniteLoop = isInfiniteLoop;
		return this;
	}

    public boolean getInfiniteLoop(){
        return mIsInfiniteLoop;
    }

}
