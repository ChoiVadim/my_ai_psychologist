package com.choivadim.my_ai_psychologist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JournalFragment extends Fragment {

    private JournalViewModel journalViewModel;
    private EditText journalEntryEditText;
    private Button saveEntryButton;
    private RecyclerView recyclerView;
    private JournalAdapter journalAdapter;
    private ApiService apiService;
    private String accessToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        journalEntryEditText = view.findViewById(R.id.journal_entry);
        saveEntryButton = view.findViewById(R.id.save_entry_button);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        journalAdapter = new JournalAdapter();
        recyclerView.setAdapter(journalAdapter);

        journalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
        journalViewModel.getAllEntries().observe(getViewLifecycleOwner(), new Observer<List<JournalEntry>>() {
            @Override
            public void onChanged(List<JournalEntry> journalEntries) {
                journalAdapter.setEntries(journalEntries);
            }
        });

        apiService = ApiClient.getClient().create(ApiService.class);
        accessToken = getActivity().getIntent().getStringExtra("ACCESS_TOKEN");

        saveEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = journalEntryEditText.getText().toString();
                if (!content.isEmpty()) {
                    JournalEntry entry = new JournalEntry(content, System.currentTimeMillis());
                    journalViewModel.insert(entry);
                    journalEntryEditText.setText("");
                    Toast.makeText(getContext(), "Journal entry added", Toast.LENGTH_SHORT).show();
                    addJournalEntry(accessToken, content);
                } else {
                    Toast.makeText(getContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        journalAdapter.setOnItemClickListener(new JournalAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(JournalEntry entry) {
                journalViewModel.delete(entry);
                Toast.makeText(getContext(), "Journal entry deleted", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void addJournalEntry(String token, String content) {
        JournalRequest request = new JournalRequest(content);
        Call<JournalResponse> call = apiService.addJournalEntry("Bearer " + token, request);

        call.enqueue(new Callback<JournalResponse>() {
            @Override
            public void onResponse(Call<JournalResponse> call, Response<JournalResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("Journal", "Success: " + response.body().getMessage());
                } else {
                    Log.d("Journal", "Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JournalResponse> call, Throwable t) {
                Log.e("Journal", "Error: " + t.getMessage());
            }
        });
    }
}
