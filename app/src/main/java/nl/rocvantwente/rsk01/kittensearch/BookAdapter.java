package nl.rocvantwente.rsk01.kittensearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mContext;
    private ArrayList<Book> mBookList;
    public OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public BookAdapter(Context context, ArrayList<Book> books) {
        this.mContext = context;
        this.mBookList = books;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        public ImageView mCoverView;
        public TextView mTitleView;
        public TextView mDescriptionView;
        public TextView mPublishedDate;
        public TextView mPageCount;

        public BookViewHolder(View itemView) {
            super(itemView);

            mCoverView = (ImageView)itemView.findViewById(R.id.cover_view);
            mTitleView = (TextView)itemView.findViewById(R.id.text_view_title);
            mDescriptionView = (TextView)itemView.findViewById(R.id.text_view_description);
            mPublishedDate = (TextView)itemView.findViewById(R.id.text_view_publisheddate);
            mPageCount = (TextView)itemView.findViewById(R.id.text_view_pages);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.book_item,parent , false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book item = mBookList.get(position);
        holder.mDescriptionView.setText("Pages: " + item.getVolumeInfo().getmDescription());
        holder.mTitleView.setText(item.getVolumeInfo().getmTitle());
        DateFormat df = DateFormat.getDateInstance();
        if(item.getVolumeInfo().getmPublishedDate() != null) {
            holder.mPublishedDate.setText(df.format(item.getVolumeInfo().getmPublishedDate()));
        }
        holder.mPageCount.setText("Pages: " + item.getVolumeInfo().getmPageCount());
        Picasso.with(mContext).load(item.getVolumeInfo().getmSmallThumbnail()).fit()
                .centerInside().into(holder.mCoverView);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
