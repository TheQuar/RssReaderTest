package uz.quar.rssreadertest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.quar.rssreadertest.R;
import uz.quar.rssreadertest.db.news.NewsArticles;
import uz.quar.rssreadertest.utils.Utils;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {
    private List<NewsArticles> articles;

    private ArticleListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(NewsArticles article);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ArticleListAdapter(List<NewsArticles> list) {
        this.articles = list;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        NewsArticles currentArticle = articles.get(position);

        viewHolder.title.setText(currentArticle.getTitle());

        viewHolder.pubDate.setText(Utils.getFormattedTimeStamp(currentArticle.getPubDate()));

        viewHolder.category.setText(currentArticle.getSourceName());

        viewHolder.itemView.setOnClickListener(view -> {
            listener.onItemClick(currentArticle);
        });
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pubDate;
        TextView category;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_ArticleTitle);
            pubDate = itemView.findViewById(R.id.txt_ArticleTimestamp);
            category = itemView.findViewById(R.id.txt_FeedName);
        }
    }
}