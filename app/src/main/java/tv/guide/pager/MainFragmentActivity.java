package tv.guide.pager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tv.guide.pager.fragment.FirstFragment;
import tv.guide.pager.widget.ScrollViewPager;


public class MainFragmentActivity extends ActionBarActivity {
    private int[] ICON_MAP_COMMON = { R.drawable.default_1, R.drawable.default_2,
            R.drawable.default_3, R.drawable.default_5};
    private ViewPager mViewPager;
    private ScrollViewPager mGuideViewPage;
    private final int COUNT = ICON_MAP_COMMON.length;
    public static final String KEY_IMAGE = "key_image";
    public static final String KEY_INDEX = "key_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        mViewPager = (ViewPager)findViewById(R.id.viewpager_f);
        mGuideViewPage = (ScrollViewPager)findViewById(R.id.viewpager_f2);

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        List<Fragment> fragmentList2 = new ArrayList<Fragment>();
        for (int i = 0; i < COUNT; i++) {
            FirstFragment firstFragment = new FirstFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_IMAGE, ICON_MAP_COMMON[i]);
            bundle.putString(KEY_INDEX, "index" + i);
            firstFragment.setArguments(bundle);
            fragmentList.add(firstFragment);
        }
        for (int i = 0; i < COUNT; i++) {
            FirstFragment firstFragment = new FirstFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_IMAGE, ICON_MAP_COMMON[i]);
            bundle.putString(KEY_INDEX, "index" + i);
            firstFragment.setArguments(bundle);
            fragmentList2.add(firstFragment);
        }
        mViewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragmentList));
        mGuideViewPage.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragmentList2));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_guide:
                startIntent(GuideViewPagerActivity.class);
                break;
            case R.id.action_fragment:
                startIntent(MainFragmentActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startIntent(Class<?> c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
        finish();
    }

    class myPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position+"";
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

}
