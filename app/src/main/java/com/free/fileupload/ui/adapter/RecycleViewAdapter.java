package com.free.fileupload.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.free.fileupload.R;
import com.free.fileupload.contract.UrlDef;
import com.free.fileupload.model.bean.FileBean;
import com.free.fileupload.util.MyBitmapUtils;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

    private List<FileBean.DataBean> mFileDataList;

    public RecycleViewAdapter(List<FileBean.DataBean> fileDataList){
        mFileDataList = fileDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileBean.DataBean dataBean = mFileDataList.get(position);
        MyBitmapUtils.getInstance().setContext(holder.itemView.getContext()).disPlay(holder.mImageViewPre, UrlDef.BASE_DEBUG_URL+"showFile?uin="+dataBean.getUin());
        holder.mUpTime.setText("发布时间："+dataBean.getFileUpTime());
        holder.mContextTv.setText(dataBean.getFilePath());
        holder.mTitle.setText(dataBean.getFileExtension());
    }

    @Override
    public int getItemCount() {
        if (mFileDataList == null) return 0;
        return mFileDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewPre;
        TextView mTitle, mContextTv, mUpTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageViewPre = itemView.findViewById(R.id.iv_img);
            mTitle = itemView.findViewById(R.id.tv_title);
            mContextTv = itemView.findViewById(R.id.tv_context);
            mUpTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
