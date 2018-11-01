package mobiledev.pxl.be.triviaking;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import mobiledev.pxl.be.triviaking.data.DatabaseContract;
import mobiledev.pxl.be.triviaking.data.DbHelper;
import mobiledev.pxl.be.triviaking.fragmentsupport.Quiz;
import mobiledev.pxl.be.triviaking.fragmentsupport.QuizContent;
import mobiledev.pxl.be.triviaking.support.QRCodeSupporter;
import mobiledev.pxl.be.triviaking.support.Remembrance;

public class QuizDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private SQLiteDatabase mDb;

    private Cursor mItem;

    public QuizDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = new DbHelper(this.getContext()).getReadableDatabase();


        mItem =  mDb.query(DatabaseContract.Quiz.TABLE_NAME, null, DatabaseContract.Quiz._ID + "=" + getArguments().getInt(ARG_ITEM_ID), null, null, null, null, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_detail, container, false);

        if (mItem != null) {
            mItem.moveToFirst();
            ((TextView) rootView.findViewById(R.id.quiz_detail_category)).setText("Category: " + mItem.getString(mItem.getColumnIndex(DatabaseContract.Quiz.CATEGORY)));
            ((TextView) rootView.findViewById(R.id.quiz_detail_difficulty)).setText("Difficulty: " + mItem.getString(mItem.getColumnIndex(DatabaseContract.Quiz.DIFFICULTY)));
            ((TextView) rootView.findViewById(R.id.quiz_detail_num_questions)).setText("Number of questions:" + mItem.getInt(mItem.getColumnIndex(DatabaseContract.Quiz.QUESTIONS)) + "");
        }

        // generateQR(rootView);

        return rootView;
    }

    private void generateQR(View rootView){
        ImageView imageView = rootView.findViewById(R.id.quiz_detail_qr);
        String qrString = mItem.getString(mItem.getColumnIndex(DatabaseContract.Quiz.DATA));
        try {
            QRCodeSupporter.encodeAsBitmap(qrString, imageView);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
