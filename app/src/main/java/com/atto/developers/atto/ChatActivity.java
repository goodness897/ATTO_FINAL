package com.atto.developers.atto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.atto.developers.atto.manager.DBManager;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.chatdata.ChatContract;
import com.atto.developers.atto.networkdata.chatdata.ChatListItemData;
import com.atto.developers.atto.networkdata.userdata.User;
import com.atto.developers.atto.request.AddChatRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView listView;

    ChatAdapter mAdapter;

    @BindView(R.id.edit_input)
    EditText inputView;

    public static final String EXTRA_USER = "user";
    User user;

    LocalBroadcastManager mLBM;
    int tradeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        tradeid = getIntent().getIntExtra("tid", -1);

        mAdapter = new ChatAdapter();
        Cursor c = DBManager.getInstance().getChatUser();
        mAdapter.changeCursor(c);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        mLBM = LocalBroadcastManager.getInstance(this);


    }

    @OnClick(R.id.btn_send)
    public void onSend(View view) {
        final String message = inputView.getText().toString();

        AddChatRequest request = new AddChatRequest(this, String.valueOf(tradeid), user, message, null);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ChatListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<ChatListItemData> request, ChatListItemData result) {
                if (result.getCode() == 1) {
                    DBManager.getInstance().addMessage(user, ChatContract.ChatMessage.TYPE_SEND, message);
                    updateMessage();
                    Log.d("chat", result.getMessage());

                } else {
                    Toast.makeText(ChatActivity.this, "전송실패", Toast.LENGTH_SHORT).show();
                    Log.d("chat", result.getMessage());
                }
            }

            @Override
            public void onFail(NetworkRequest<ChatListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(ChatActivity.this, "fail", Toast.LENGTH_SHORT).show();
                Log.d("chat", errorCode + "");
            }
        });

//        int type = ChatContract.ChatMessage.TYPE_SEND;
//        switch (typeView.getCheckedRadioButtonId()) {
//            case R.id.radio_send :
//                type = ChatContract.ChatMessage.TYPE_SEND;
//                break;
//            case R.id.radio_receive :
//                type = ChatContract.ChatMessage.TYPE_RECEIVE;
//                break;
//        }
//        DBManager.getInstance().addMessage(user, type, message);
//
//        updateMessage();
    }

    private void updateMessage() {
        Cursor c = DBManager.getInstance().getChatMessage(user);
        mAdapter.changeCursor(c);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMessage();
//        mLBM.registerReceiver(mReceiver, new IntentFilter(MyGcmListenerService.ACTION_CHAT));
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            User u = (User) intent.getSerializableExtra(MyGcmListenerService.EXTRA_CHAT_USER);
//            if (u.getId() == user.getId()) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        updateMessage();
//                    }
//                });
//                intent.putExtra(MyGcmListenerService.EXTRA_RESULT, true);
//            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.changeCursor(null);
        mLBM.unregisterReceiver(mReceiver);
    }
}
