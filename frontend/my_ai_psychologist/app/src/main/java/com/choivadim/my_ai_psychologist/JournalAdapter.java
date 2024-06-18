package com.choivadim.my_ai_psychologist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private List<JournalEntry> journalEntries = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journal_entry, parent, false);
        return new JournalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        JournalEntry currentEntry = journalEntries.get(position);
        holder.textViewContent.setText(currentEntry.getContent());
        String formattedDate = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                .format(currentEntry.getTimestamp());
        holder.textViewTimestamp.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return journalEntries.size();
    }

    public void setEntries(List<JournalEntry> entries) {
        this.journalEntries = entries;
        notifyDataSetChanged();
    }

    public JournalEntry getEntryAt(int position) {
        return journalEntries.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class JournalViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewContent;
        private TextView textViewTimestamp;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.text_view_content);
            textViewTimestamp = itemView.findViewById(R.id.text_view_timestamp);

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(journalEntries.get(position));
                }
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onItemLongClick(JournalEntry entry);
    }
}
