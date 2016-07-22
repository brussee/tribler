package org.tribler.android;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter channel name, channel description, torrent name, torrent category
 */
public class TriblerViewAdapterFilter extends Filter {

    private FilterableRecyclerViewAdapter mAdapter;
    private List<Object> mDataList;

    public TriblerViewAdapterFilter(FilterableRecyclerViewAdapter adapter, List<Object> list) {
        super();
        mAdapter = adapter;
        mDataList = list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectListFilterResults performFiltering(CharSequence query) {
        List<Object> filteredList = new ArrayList<>();
        String constraint = query.toString().trim().toLowerCase();
        if (constraint.isEmpty()) {
            filteredList.addAll(mDataList);
        } else {
            for (Object item : mDataList) {
                if (item instanceof TriblerChannel) {
                    TriblerChannel channel = (TriblerChannel) item;
                    if (channel.getName().toLowerCase().contains(constraint)
                            || channel.getDescription().toLowerCase().contains(constraint)) {
                        filteredList.add(channel);
                    }
                } else if (item instanceof TriblerTorrent) {
                    TriblerTorrent torrent = (TriblerTorrent) item;
                    if (torrent.getName().toLowerCase().contains(constraint)
                            || torrent.getCategory().toLowerCase().contains(constraint)) {
                        filteredList.add(torrent);
                    }
                }
            }
        }
        ObjectListFilterResults results = new ObjectListFilterResults();
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void publishResults(CharSequence query, FilterResults results) {
        if (results instanceof ObjectListFilterResults) {
            List<Object> list = ((ObjectListFilterResults) results).values;
            // Check in case of concurrent access while iterating over mDataList during filtering
            if (list != null) {
                mAdapter.setList(list);
            }
        }
    }

    protected static class ObjectListFilterResults extends FilterResults {

        public int count;
        public List<Object> values;
    }
}
