package uz.quar.rssreadertest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.quar.rssreadertest.R;
import uz.quar.rssreadertest.db.feeds.FeedArticles;
import uz.quar.rssreadertest.db.news.NewsArticles;
import uz.quar.rssreadertest.utils.Utils;

public class FeedsListAdapter extends RecyclerView.Adapter<FeedsListAdapter.ViewHolder> {
    private List<FeedArticles> articles;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FeedArticles article);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FeedsListAdapter(List<FeedArticles> list) {
        this.articles = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feeds_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        FeedArticles currentArticle = articles.get(position);
        viewHolder.title.setText(currentArticle.getTitle());
        viewHolder.url.setText(currentArticle.getLink());
        viewHolder.logo.setText(Utils.getInitialsFromText(currentArticle.getTitle()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(articles.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView logo;
        TextView title;
        TextView url;

        public ViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.txt_Logo);
            title = itemView.findViewById(R.id.txt_FeedTitle);
            url = itemView.findViewById(R.id.txt_FeedUrl);
        }
    }
}