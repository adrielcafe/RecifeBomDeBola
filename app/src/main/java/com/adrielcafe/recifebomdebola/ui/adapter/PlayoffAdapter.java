package com.adrielcafe.recifebomdebola.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.IconTextView;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;
import com.adrielcafe.recifebomdebola.model.Playoff;
import com.joanzapata.android.iconify.Iconify;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayoffAdapter extends ArrayAdapter<Playoff> {
    public PlayoffAdapter(Context context, List<Playoff> items) {
        super(context, R.layout.list_item_match, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Playoff match = getItem(position);
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
        viewHolder.detailsView.setText(details);

        viewHolder.team1ScoreView.setVisibility(View.GONE);
        viewHolder.team2ScoreView.setVisibility(View.GONE);
        viewHolder.detailsView.setVisibility(Util.isNullOrEmpty(details) ? View.GONE : View.VISIBLE);

        viewHolder.versusView.setTypeface(Iconify.getTypeface(getContext()));

	    viewHolder.team1View.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Util.openPlayersDialog((Activity) getContext(), match.getTeam1(), match.getCategory(), match.getRpa());
		    }
	    });
	    viewHolder.team2View.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Util.openPlayersDialog((Activity) getContext(), match.getTeam2(), match.getCategory(), match.getRpa());
		    }
	    });

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