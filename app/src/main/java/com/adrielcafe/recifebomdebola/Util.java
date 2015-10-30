package com.adrielcafe.recifebomdebola;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.adrielcafe.recifebomdebola.model.Player;
import com.adrielcafe.recifebomdebola.ui.adapter.PlayerAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;

public class Util {
    public static final String INSTAGRAM_MEDIA_RECENT_URL = "https://api.instagram.com/v1/users/515321534/media/recent/?access_token=" + App.INSTAGRAM_ACCESS_TOKEN;
    public static final String PDF_VIEWER_URL = "https://docs.google.com/gview?embedded=true&url=";

    private static Picasso picasso;
    private static ConnectivityManager connectivityManager;

    public static void openPlayersDialog(final Activity activity, final String teamName, String teamCategory, int teamRpa){
        Db.getTeamPlayers(activity, teamName, teamCategory, teamRpa, new FindCallback<Player>() {
            @Override
            public void done(final List<Player> list, ParseException e) {
                final View playersView = activity.getLayoutInflater().inflate(R.layout.dialog_players, null, false);
                final ListView playersList = (ListView) playersView.findViewById(android.R.id.list);
                IconTextView redCardsView = (IconTextView) playersView.findViewById(R.id.red_cards);
                IconTextView yellowCardsView = (IconTextView) playersView.findViewById(R.id.yellow_cards);

                redCardsView.setTypeface(Iconify.getTypeface(activity));
                yellowCardsView.setTypeface(Iconify.getTypeface(activity));

                ParseObject.pinAllInBackground(list);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playersList.setAdapter(new PlayerAdapter(activity, list));

	                    final NiftyDialogBuilder playerDialog = NiftyDialogBuilder.getInstance(activity);
                        playerDialog.setCustomView(playersView, activity)
                                .withTitle(teamName)
                                .withMessage(null)
                                .withDuration(300)
                                .withEffect(Effectstype.Fadein)
                                .withDialogColor(activity.getResources().getColor(R.color.primary_dark))
                                .withTitleColor(activity.getResources().getColor(R.color.accent))
                                .withButton1Text(activity.getString(R.string.close))
                                .setButton1Click(new View.OnClickListener() {
	                                @Override
	                                public void onClick(View v) {
		                                playerDialog.dismiss();
	                                }
                                });
	                    playerDialog.show();
                    }
                });
            }
        });
    }

    public static String getPdfViewerUrl(String pdfUrl){
        try {
            return PDF_VIEWER_URL + URLEncoder.encode(pdfUrl, "UTF-8");
        } catch (Exception e){
            return pdfUrl;
        }
    }

    public static void openUrl(Context context, String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void configMenuItem(Context context, MenuItem menuItem, Iconify.IconValue iconRes){
        menuItem.setIcon(new IconDrawable(context, iconRes)
		        .colorRes(android.R.color.white)
		        .actionBarSize());
    }

    public static SpannableString addCustomFont(CharSequence text){
        SpannableString title = new SpannableString(text);
        title.setSpan(new TypefaceSpan("Ubuntu-Regular.ttf"), 0, title.length(),
		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return title;
    }

    public static Picasso getImageLoader(Context context){
        if(picasso == null){
            OkHttpDownloader downloader = new OkHttpDownloader(context);
            picasso = new Picasso.Builder(context).downloader(downloader).build();
        }
        return picasso;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

	public static boolean isNumeric(String str){
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}

	public static String toCamelCase(String s){
        try {
            String[] parts = s.split("_");
            String camelCaseString = "";
            for (String part : parts){
                camelCaseString = camelCaseString + toProperCase(part);
            }
            return camelCaseString;
        } catch (Exception e){
            return s;
        }
    }

    private static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }

    public static View getEmptyView(Activity activity){
        View emptyView = activity.getLayoutInflater().inflate(R.layout.empty, null);
        IconTextView iconView = (IconTextView) emptyView.findViewById(android.R.id.icon);
        iconView.setTypeface(Iconify.getTypeface(activity));
        return emptyView;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        try {
            ListAdapter listAdapter = listView.getAdapter();
            int numberOfItems = listAdapter.getCount();

            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                float px = 200 * (listView.getResources().getDisplayMetrics().density);
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
        } catch (Exception e){ }
    }

    public static boolean isConnected(Context context){
        if(connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null && info.isConnected() && isConnectionFast(info.getType(), info.getSubtype()));
    }

    private static boolean isConnectionFast(int type, int subType){
        if(type == ConnectivityManager.TYPE_WIFI){
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE){
            switch (subType){
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return true; // ~ 10+ Mbps
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }
}