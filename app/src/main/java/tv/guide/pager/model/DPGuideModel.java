package tv.guide.pager.model;

import java.io.Serializable;

/**
 * Created by whiskeyfei on 15-7-18.
 */
public class DPGuideModel implements Serializable{
    private int mImageResId;
    private String mDes;

    public int getImageResId() {
        return mImageResId;
    }

    public void setImageResId(int mImageResId) {
        this.mImageResId = mImageResId;
    }

    public String getDes() {
        return mDes;
    }

    public void setDes(String mDes) {
        this.mDes = mDes;
    }
}
