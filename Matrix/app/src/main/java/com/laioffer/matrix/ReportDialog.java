package com.laioffer.matrix;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReportDialog extends Dialog {

    private int cx;
    private int cy;
    private RecyclerView mRecyclerView;
    private ReportRecyclerViewAdapter mRecyclerViewAdapter;
    private ViewSwitcher mViewSwitcher;
    private String mEventype;

    private ImageView mImageCamera;
    private Button mBackButton;
    private Button mSendButton;
    private EditText mCommentEditText;
    private ImageView mEventTypeImg;
    private TextView mTypeTextView;
    private DialogCallBack mDialogCallBack;

    interface DialogCallBack {
        void onSubmit(String editString, String event_type);
        void startCamera();
    }


    private void setUpEventSpecs(final View dialogView) {
        mImageCamera = (ImageView) dialogView.findViewById(R.id.event_camera_img);
        mBackButton = (Button) dialogView.findViewById(R.id.event_back_button);
        mSendButton = (Button) dialogView.findViewById(R.id.event_send_button);
        mCommentEditText = (EditText) dialogView.findViewById(R.id.event_comment);
        mEventTypeImg = (ImageView) dialogView.findViewById(R.id.event_type_img);
        mTypeTextView = (TextView) dialogView.findViewById(R.id.event_type);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogCallBack.onSubmit(mCommentEditText.getText().toString(), mEventype);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewSwitcher.showPrevious();
            }
        });

        mImageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogCallBack.startCamera();
            }
        });

    }



    public ReportDialog(@NonNull Context context) {
        this(context, R.style.MyAlertDialogStyle);
    }

    public ReportDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public void setDialogCallBack(DialogCallBack dialogCallBack) {
        mDialogCallBack = dialogCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View dialogView = View.inflate(getContext(), R.layout.dialog, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        setupRecyclerView(dialogView);
        mViewSwitcher = (ViewSwitcher) dialogView.findViewById(R.id.viewSwitcher);
        setUpEventSpecs(dialogView);


    }


    private void setupRecyclerView(View dialogView) {
        mRecyclerView = dialogView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerViewAdapter = new ReportRecyclerViewAdapter(getContext(), Config.listItems);
        mRecyclerViewAdapter.setClickListener(new ReportRecyclerViewAdapter
                .OnClickListener() {
            @Override
            public void setItem(String item) {
                // for switch item
                showNextViewSwitcher(item);
            }
        });

        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }


    private void showNextViewSwitcher(String item) {
        mEventype = item;
        if (mViewSwitcher != null) {
            mViewSwitcher.showNext();
            mTypeTextView.setText(mEventype);
            mEventTypeImg.setImageDrawable(ContextCompat.getDrawable(getContext(),Config.trafficMap.get(mEventype)));
        }
    }

    public void updateImage(Bitmap bitmap) {
        mImageCamera.setImageBitmap(bitmap);
    }


}
