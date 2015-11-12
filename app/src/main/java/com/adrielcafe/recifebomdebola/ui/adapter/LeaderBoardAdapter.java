package com.adrielcafe.recifebomdebola.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.LeaderBoard;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaderBoardAdapter extends ArrayAdapter<LeaderBoard> {
    public LeaderBoardAdapter(Context context, List<LeaderBoard> items) {
        super(context, R.layout.list_item_leaderboard, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LeaderBoard leaderBoard = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_leaderboard, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.teamView.setText(leaderBoard.getTeam());
        viewHolder.pointsScoredView.setText(leaderBoard.getPointsScored()+"");
        viewHolder.matchesView.setText(leaderBoard.getMatches()+"");
        viewHolder.winsView.setText(leaderBoard.getWins()+"");
        viewHolder.drawsView.setText(leaderBoard.getDraws()+"");
        viewHolder.defeatsView.setText(leaderBoard.getDefeats()+"");

        if(position > 1){
            viewHolder.teamView.setTextColor(getContext().getResources().getColor(R.color.red));
        } else {
            viewHolder.teamView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.team)
        TextView teamView;
        @Bind(R.id.points_scored)
        TextView pointsScoredView;
        @Bind(R.id.matches)
        TextView matchesView;
        @Bind(R.id.wins)
        TextView winsView;
        @Bind(R.id.draws)
        TextView drawsView;
        @Bind(R.id.defeats)
        TextView defeatsView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}