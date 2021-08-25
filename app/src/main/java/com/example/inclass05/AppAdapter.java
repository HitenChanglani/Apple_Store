package com.example.inclass05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class AppAdapter extends ArrayAdapter<DataServices.App> {
    public AppAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_list_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.appNameLayout = convertView.findViewById(R.id.appNameLayout);
            viewHolder.artistNameLayout = convertView.findViewById(R.id.artistNameLayout);
            viewHolder.releaseDateLayout = convertView.findViewById(R.id.releaseDateLayout);
            convertView.setTag(viewHolder);
        }

        DataServices.App appData = getItem(position);

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.appNameLayout.setText(appData.name);
        viewHolder.artistNameLayout.setText(appData.artistName);
        viewHolder.releaseDateLayout.setText(appData.releaseDate);

        return convertView;
    }

    private static class ViewHolder{
        TextView appNameLayout, artistNameLayout, releaseDateLayout;
    }

}
