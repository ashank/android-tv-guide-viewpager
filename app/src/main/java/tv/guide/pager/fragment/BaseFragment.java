package tv.guide.pager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by whiskeyfei on 15-7-20.
 */
public abstract class BaseFragment extends Fragment {
    private static final String CONTENT_RESID = "layoutResID";
    private View mCurrentView;
    private int mContentResID;

    public BaseFragment(){
    }

    public BaseFragment(int layoutId){
        super();
        mContentResID = layoutId;
        Bundle arguments = new Bundle();
        arguments.putInt(CONTENT_RESID, layoutId);
        setArguments(arguments);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mContentResID = arguments.getInt(CONTENT_RESID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCurrentView = inflater.inflate(mContentResID,null);
        initView();
        return mCurrentView;
    }
    public abstract void initView();

}
