package mobiledev.pxl.be.triviaking.fragmentsupport;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobiledev.pxl.be.triviaking.QuizDetailActivity;
import mobiledev.pxl.be.triviaking.QuizDetailFragment;
import mobiledev.pxl.be.triviaking.QuizListActivity;
import mobiledev.pxl.be.triviaking.R;

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final QuizListActivity mParentActivity;
    private final Cursor mValues;
    private final boolean mTwoPane;

    public SimpleItemRecyclerViewAdapter(QuizListActivity parent,
                                  Cursor items,
                                  boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(!mValues.moveToPosition(position)){
            return;
        }
        final int id = mValues.getInt(0);
        holder.mIdView.setText(mValues.getString(1));
        holder.mContentView.setText(mValues.getString(2));

        holder.itemView.setTag(mValues.getString(2));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(QuizDetailFragment.ARG_ITEM_ID, id);
                    QuizDetailFragment fragment = new QuizDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.quiz_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, QuizDetailActivity.class);
                    intent.putExtra(QuizDetailFragment.ARG_ITEM_ID, id);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }


    }
}