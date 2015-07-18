package tv.guide.pager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by whiskeyfei on 15-7-9.
 */
public abstract class DPBasePageAdapter<T> extends PagerAdapter {

    protected List<T> mDataList;
    protected Context mContext;
    protected int mLayoutId;

    public DPBasePageAdapter(Context context, List<T> list, int layoutId) {
        mContext = context;
        mDataList = list;
        mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDataList.size();
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
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final DPAdapterViewHolder holder = DPAdapterViewHolder.get(mContext, convertView, parent, mLayoutId, position);
        convert(holder,getItem(position));
        return holder.getConvertView();
    }

	public abstract void convert(DPAdapterViewHolder holder,T t);
}
