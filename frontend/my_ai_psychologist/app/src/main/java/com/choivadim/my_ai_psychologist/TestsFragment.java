package com.choivadim.my_ai_psychologist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestsFragment extends Fragment {

    WebView web;
    ListView testsListView;
    ViewFlipper flipper;
    Button backButton;

    final String[] tests = { "Your Mental Health Today Test", "Depression Test", "Self-Esteem Test", "Anger Management Test"};
    final int[] icons = { R.drawable.mental, R.drawable.depr, R.drawable.self_es, R.drawable.anger };
    final String[] links = {"https://www.psychologytoday.com/us/tests/health/mental-health-assessment", "https://www.psychologytoday.com/us/tests/health/depression-test", "https://www.psychologytoday.com/us/tests/personality/self-esteem-test", "https://www.psychologytoday.com/us/tests/personality/anger-management-test"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, container, false);

        flipper = view.findViewById(R.id.flipperID);
        testsListView = view.findViewById(R.id.tests);
        web = view.findViewById(R.id.webView);
        backButton = view.findViewById(R.id.back_button);

        TestAdapter adapter = new TestAdapter(getContext(), tests, icons);
        testsListView.setAdapter(adapter);

        testsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flipper.showNext();
                web.setWebViewClient(new WebViewClient());
                web.loadUrl(links[position]);
            }
        });

        backButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.showPrevious();
            }
        });

        return view;
    }

}
