package com.adrielcafe.recifebomdebola.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.IconTextView;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;
import com.adrielcafe.recifebomdebola.model.Match;
import com.joanzapata.android.iconify.Iconify;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MatchAdapter extends ArrayAdapter<Match> {
    public MatchAdapter(Context context, List<Match> items) {
        super(context, R.layout.list_item_match, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Match match = getItem(position);
        String details;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_match, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(!Util.isNullOrEmpty(match.getField())){
            details = "NO " + match.getField().toUpperCase();
            if(!Util.isNullOrEmpty(match.getStartHour())){
                details += ", ÀS " + match.getStartHour() + "H";
            } else {
                details += "";
            }
        } else {
            details = null;
        }

        viewHolder.team1View.setText(Util.toCamelCase(match.getTeam1()));
        viewHolder.team2View.setText(Util.toCamelCase(match.getTeam2()));
        viewHolder.team1ScoreView.setText(match.getTeam1Goals()+"");
        viewHolder.team2ScoreView.setText(match.getTeam2Goals() + "");
        viewHolder.detailsView.setText(details);

        viewHolder.team1ScoreView.setVisibility(Util.isNullOrEmpty(match.getTeam1Goals()) ? View.GONE : View.VISIBLE);
        viewHolder.team2ScoreView.setVisibility(Util.isNullOrEmpty(match.getTeam2Goals()) ? View.GONE : View.VISIBLE);
        viewHolder.detailsView.setVisibility(Util.isNullOrEmpty(details) ? View.GONE : View.VISIBLE);

        viewHolder.versusView.setTypeface(Iconify.getTypeface(getContext()));

        if(match.getStatus().equals("CONFIRMADO")) {
            viewHolder.versusView.setTextColor(getContext().getResources().getColor(R.color.green));
        } else if(match.getStatus().equals("CANCELADO")){
            viewHolder.versusView.setTextColor(getContext().getResources().getColor(R.color.red));
        } else {
            viewHolder.versusView.setTextColor(viewHolder.versusView.getTextColors());
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.team1)
        TextView team1View;
        @Bind(R.id.team2)
        TextView team2View;
        @Bind(R.id.team1Score)
        TextView team1ScoreView;
        @Bind(R.id.team2Score)
        TextView team2ScoreView;
        @Bind(R.id.details)
        TextView detailsView;
        @Bind(R.id.versus)
        IconTextView versusView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}