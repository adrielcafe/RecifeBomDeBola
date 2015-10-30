package com.adrielcafe.recifebomdebola.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Player;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayerAdapter extends ArrayAdapter<Player> {
    public PlayerAdapter(Context context, List<Player> items) {
        super(context, R.layout.list_item_player, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Player player = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_player, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameView.setText(player.getName());
        viewHolder.goalsView.setText(player.getGoals() + "");
        viewHolder.yellowCardsView.setText(player.getYellowCards()+"");
        viewHolder.redCardsView.setText(player.getRedCards()+"");

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.name)
        TextView nameView;
        @Bind(R.id.goals)
        TextView goalsView;
        @Bind(R.id.yellow_cards)
        TextView yellowCardsView;
        @Bind(R.id.red_cards)
        TextView redCardsView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}