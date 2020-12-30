package com.arpan.alosproject.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arpan.alosproject.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class Invite extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_invite, container, false);

        ShapeableImageView facebook = view.findViewById(R.id.facebook);
        ShapeableImageView whatsapp = view.findViewById(R.id.whatsapp);
        ShapeableImageView twitter = view.findViewById(R.id.twitter);
        ShapeableImageView telegram = view.findViewById(R.id.telegram);

        facebook.setOnClickListener(v -> {
            String message = "Download this app and check out the best courses here.";
            shareOnFacebook(message);
        });

        whatsapp.setOnClickListener(v -> {
            String message = "Download this app and check out the best courses here.";
            shareOnWhatsApp(message);
        });

        twitter.setOnClickListener(v -> {
            String message = "Download this app and check out the best courses here.";
            shareOnTwitter(message);
        });

        telegram.setOnClickListener(v -> {
            String message = "Download this app and check out the best courses here.";
            shareOnTelegram(message);
        });

        return view;
    }

    private void shareOnFacebook(String message) {
        Intent facebookIntent = new Intent(Intent.ACTION_SEND);
        facebookIntent.putExtra(Intent.EXTRA_TEXT, message);
        facebookIntent.setType("text/plain");

        PackageManager pm = getContext().getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentActivities(facebookIntent, pm.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for(ResolveInfo info : resolveInfo) {
            if(info.activityInfo.packageName.startsWith("com.facebook")) {
                facebookIntent.setClassName(info.activityInfo.packageName,info.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if(resolved) {
            startActivity(facebookIntent);
        } else {
            Toast.makeText(getContext(),"Facebook app isn't found", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOnWhatsApp(String message) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, message);
        whatsappIntent.setType("text/plain");

        PackageManager pm = getContext().getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentActivities(whatsappIntent, pm.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for(ResolveInfo info : resolveInfo) {
            if(info.activityInfo.packageName.startsWith("com.whatsapp")) {
                whatsappIntent.setClassName(info.activityInfo.packageName,info.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if(resolved) {
            startActivity(whatsappIntent);
        } else {
            Toast.makeText(getContext(),"WhatsApp app isn't found", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOnTelegram(String message) {
        Intent telegramIntent = new Intent(Intent.ACTION_SEND);
        telegramIntent.putExtra(Intent.EXTRA_TEXT, message);
        telegramIntent.setType("text/plain");

        PackageManager pm = getContext().getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentActivities(telegramIntent, pm.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for(ResolveInfo info : resolveInfo) {
            if(info.activityInfo.packageName.startsWith("org.telegram.messenger")) {
                telegramIntent.setClassName(info.activityInfo.packageName,info.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if(resolved) {
            startActivity(telegramIntent);
        } else {
            Toast.makeText(getContext(),"Facebook app isn't found", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOnTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message);
        tweetIntent.setType("text/plain");

        PackageManager packManager = getContext().getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(getContext(), "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf("MY_TAG", "UTF-8 should always be supported", e);
            return "";
        }
    }
}