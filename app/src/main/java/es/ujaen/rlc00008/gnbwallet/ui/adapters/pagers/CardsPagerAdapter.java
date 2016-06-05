package es.ujaen.rlc00008.gnbwallet.ui.adapters.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.CardPageFragment;

public class CardsPagerAdapter extends FragmentStatePagerAdapter {

	private List<Card> data;

	public CardsPagerAdapter(List<Card> data, FragmentManager fm) {
		super(fm);
		if (data == null) {
			throw new RuntimeException("data is null!");
		}
		this.data = data;
	}

	@Override
	public int getCount() {
		return (data != null ? data.size() : 0);
	}

	public void refreshList(List<Card> newData) {
		if (newData == null) {
			throw new RuntimeException("data is null!");
		}
		this.data = newData;
		notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position) {
		return CardPageFragment.newInstance(data.get(position));
	}
}
