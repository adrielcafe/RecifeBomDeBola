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
import com.adrielcafe.recifebomdebola.model.Agenda;
import com.joanzapata.android.iconify.Iconify;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AgendaAdapter extends ArrayAdapter<Agenda> {
    public AgendaAdapter(Context context, List<Agenda> items) {
        super(context, R.layout.list_item_agenda, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Agenda agenda = getItem(position);
        DateTime date = new DateTime(agenda.getDate());

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_agenda, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.monthDayView.setText(date.monthOfYear().getAsShortText().toUpperCase()
                + "\n" + date.dayOfMonth().getAsText());
        viewHolder.dayWeekView.setText(date.dayOfWeek().getAsShortText().toUpperCase());
        viewHolder.hourView.setText(agenda.getStartHour() + " - " + agenda.getEndHour() + "h");
        viewHolder.team1View.setText(Util.toCamelCase(agenda.getTeam1()));
        viewHolder.team2View.setText(Util.toCamelCase(agenda.getTeam2()));
        viewHolder.fieldView.setText(Util.toCamelCase(agenda.getField()));
        viewHolder.circle1View.setTextColor(getContext().getResources().getColor(
                agenda.isConfirmed() ? R.color.accent : R.color.gray));
        viewHolder.circle1View.setTypeface(Iconify.getTypeface(getContext()));
        viewHolder.circle2View.setTypeface(Iconify.getTypeface(getContext()));
        viewHolder.versusView.setTypeface(Iconify.getTypeface(getContext()));

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.month_day)
        TextView monthDayView;
        @Bind(R.id.day_week)
        TextView dayWeekView;
        @Bind(R.id.hour)
        TextView hourView;
        @Bind(R.id.team1)
        TextView team1View;
        @Bind(R.id.team2)
        TextView team2View;
        @Bind(R.id.field)
        TextView fieldView;
        @Bind(R.id.circle1)
        IconTextView circle1View;
        @Bind(R.id.circle2)
        IconTextView circle2View;
        @Bind(R.id.versus)
        IconTextView versusView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}