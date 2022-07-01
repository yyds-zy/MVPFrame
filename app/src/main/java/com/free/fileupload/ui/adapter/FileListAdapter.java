package com.free.fileupload.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.fileupload.R;
import com.free.fileupload.contract.UrlDef;
import com.free.fileupload.model.bean.FileBean;
import com.free.fileupload.util.MyBitmapUtils;

import java.util.List;

public class FileListAdapter extends BaseAdapter {
    private List<FileBean.DataBean> mFileDataList;   //创建一个StudentData 类的对象 集合
    private LayoutInflater inflater;
    private Context mContext;

    public FileListAdapter(List<FileBean.DataBean> fileBeans, Context context) {
        mContext = context;
        this.mFileDataList = fileBeans;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return  mFileDataList == null ? 0 : mFileDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mFileDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.file_layout, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv_img);
            holder.textView_title = convertView.findViewById(R.id.tv_title);
            holder.textView_context =  convertView.findViewById(R.id.tv_context);
            holder.textView_time = convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyBitmapUtils.getInstance().setContext(mContext).disPlay(holder.imageView, UrlDef.BASE_DEBUG_URL+"showFile?uin="+mFileDataList.get(position).getUin());
        holder.textView_time.setText("发布时间："+mFileDataList.get(position).getFileUpTime());
        holder.textView_context.setText(mFileDataList.get(position).getFilePath());
        holder.textView_title.setText(mFileDataList.get(position).getFileExtension());
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView_context,textView_time,textView_title;
    }
}
