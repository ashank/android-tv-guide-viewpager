package tv.guide.pager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tv.guide.pager.MainFragmentActivity;
import tv.guide.pager.R;

/**
 * Created by whiskeyfei on 15-7-21.
 */
public class FirstFragment extends Fragment{

    private int mImageResId;
    private String mIndex;

    public FirstFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mImageResId = arguments.getInt(MainFragmentActivity.KEY_IMAGE);
            mIndex = arguments.getString(MainFragmentActivity.KEY_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.fragment_image);
        TextView textView = (TextView)view.findViewById(R.id.fragment_index);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),mIndex,Toast.LENGTH_SHORT).show();
            }
        });
        textView.setText(mIndex);
        imageView.setImageResource(mImageResId);
        return view;
    }
}
