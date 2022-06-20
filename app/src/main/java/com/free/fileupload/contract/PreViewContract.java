package com.free.fileupload.contract;

import android.content.Context;
import android.widget.ImageView;

public interface PreViewContract {


    interface View {
        void showMsg();
    }

    interface Presenter {
        void loadPic(Context context, String pic_url, ImageView imageView);
    }

    interface Model {
        void loadPic(Context context, String pic_url, ImageView imageView,OnLoadingListener loadingListener);
    }

    interface OnLoadingListener {
        void loading();
    }
}
