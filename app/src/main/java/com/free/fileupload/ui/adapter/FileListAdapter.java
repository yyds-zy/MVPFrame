package com.free.fileupload.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.free.fileupload.R;
import com.free.fileupload.model.bean.FileBean;

import java.util.List;

public class FileListAdapter extends BaseAdapter {
    private List<FileBean.DataBean> mFileDataList;   //创建一个StudentData 类的对象 集合
    private LayoutInflater inflater;

    public FileListAdapter(List<FileBean.DataBean> fileBeans, Context context) {
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
            holder.textView =  convertView.findViewById(R.id.tv_fileName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mFileDataList.get(position).getFileName());
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
