package com.chaiy.memento.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chaiy.memento.model.MementoModel;
import com.chaiy.memento.view.framents.HorizontalPagerFragment;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends FragmentStatePagerAdapter {

    private MementoModel mementoModel;

    public HorizontalPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setData(MementoModel mementoModel) {
        this.mementoModel = mementoModel;
    }

    @Override
    public int getCount() {
        if (mementoModel == null) {
            return 0;
        }
        return mementoModel.getSectionOrder().size();
    }

    @Override
    public Fragment getItem(int position) {
        HorizontalPagerFragment horizontalPagerFragment = HorizontalPagerFragment.newInstance(mementoModel.getSections()
                .get(mementoModel.getSectionOrder().get(position)));

        return horizontalPagerFragment;
    }

  /*  @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.horizontal_fragment, container, false);

        final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
        verticalInfiniteCycleViewPager.setAdapter(
                new MoviesVerticalPagerAdapter(mContext, SECTIONS[position])
        );
        //verticalInfiniteCycleViewPager.setCurrentItem(position);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }*/
}
