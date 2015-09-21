package com.adrielcafe.recifebomdebola.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Team;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaderBoardAdapter extends ArrayAdapter<Team> {
    public LeaderBoardAdapter(Context context, List<Team> items) {
        super(context, R.layout.list_item_leaderboard, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Team team = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_leaderboard, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.positionView.setText(team.getPositon()+"");
        viewHolder.nameView.setText(team.getName());
//        viewHolder.matchesView.setText(team.getMatches()+"");
//        viewHolder.scoreView.setText(team.getScore()+"");
//        viewHolder.redCardsView.setText(team.getRedCards()+"");
//        viewHolder.yellowCardsView.setText(team.getYellowCards()+"");

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.position)
        TextView positionView;
        @Bind(R.id.name)
        TextView nameView;
        @Bind(R.id.matches)
        TextView matchesView;
        @Bind(R.id.score)
        TextView scoreView;
        @Bind(R.id.red_cards)
        TextView redCardsView;
        @Bind(R.id.yellow_cards)
        TextView yellowCardsView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}