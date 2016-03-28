package tv.guide.pager.inter;

/**
 * Created by whiskeyfei on 15-7-21.
 */
public interface ViewPagerOnSelectedListener {
    void onViewPageSelected(int position);

    void onPageScrollStateChanged(int state);

    void onPageScrolled(int arg0, float arg1, int arg2);
}
