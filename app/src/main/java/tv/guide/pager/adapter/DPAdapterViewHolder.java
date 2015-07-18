package tv.guide.pager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by whiskeyfei on 15-7-9.
 */
public class DPAdapterViewHolder {
	
	private final Context mContext;
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public DPAdapterViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
    	mContext = context;
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static DPAdapterViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new DPAdapterViewHolder(context, parent, layoutId, position);
        } else {
            DPAdapterViewHolder holder = (DPAdapterViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 通过viewId获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T extends View> T getViewByid(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition(){
        return mPosition;
    }
    

    public DPAdapterViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener){
    	View view = getViewByid(viewId);
    	view.setOnTouchListener(listener);
    	return this;
    }
    
    public DPAdapterViewHolder setVisible(int viewId,boolean visible){
    	View view = getViewByid(viewId);
    	view.setVisibility(visible? View.VISIBLE: View.INVISIBLE);
    	return this;
    }
    
    public DPAdapterViewHolder setText(int viewId,String text){
        TextView tv = getViewByid(viewId);
        tv.setText(text);
        return this;
    }

    public DPAdapterViewHolder setImageResource(int viewId,int resId){
        ImageView icon = getViewByid(viewId);
        icon.setImageResource(resId);
        return this;
    }

    public DPAdapterViewHolder setImageBitmap(int viewId,Bitmap bitmap){
        ImageView icon = getViewByid(viewId);
        icon.setImageBitmap(bitmap);
        return this;
    }
    
    public DPAdapterViewHolder setImageDrawable(int viewId,Drawable drawable){
        ImageView icon = getViewByid(viewId);
        icon.setImageDrawable(drawable);
        return this;
    }

}