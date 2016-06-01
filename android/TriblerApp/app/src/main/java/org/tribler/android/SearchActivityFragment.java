package org.tribler.android;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

import static org.tribler.android.Triblerd.restApi;

public class SearchActivityFragment extends Fragment implements TriblerViewAdapter.OnClickListener {

    private TriblerViewAdapter mAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TriblerViewAdapter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.search_results_list);
        recyclerView.setAdapter(mAdapter);

        // Swipe list item
        ItemTouchHelper.SimpleCallback onSwipe = new TriblerViewAdapterSwipeListener(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(onSwipe);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(TriblerChannel channel) {
        //TODO: open channel
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(TriblerTorrent torrent) {
        //TODO: play video
    }

    public void doMySearch(String query) {
        mAdapter.clear();
        //TODO
        Handler handler = new Handler() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void handleMessage(Message message) {
                if (MY_MESSAGE == message.what) {
                    onMy((TriblerChannel) message.obj);
                } else {
                    super.handleMessage(message);
                }
            }

            private void onMy(TriblerChannel channel) {
                mAdapter.addItem(channel);
            }
        };
        JsonStreamAsyncHttpResponseHandler<Handler> responseHandler = new JsonStreamAsyncHttpResponseHandler<Handler>(handler) {

            protected static final int MY_MESSAGE = -1;

            /**
             * {@inheritDoc}
             */
            @Override
            void readJsonStream(JsonReader reader) throws IOException {
                reader.beginObject();
                reader.nextName(); // "channels":
                reader.beginArray();
                while (reader.hasNext()) {
                    TriblerChannel channel = gson.fromJson(reader, TriblerChannel.class);

                    Message msg = handler.obtainMessage(MY_MESSAGE, channel);
                    handler.sendMessage(msg);
                }
                reader.endArray();
                reader.endObject();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, Handler handler) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Handler handler) {

            }
        };
        restApi.get(getActivity(), Triblerd.BASE_URL + "/channels/discovered", responseHandler);
    }

}
