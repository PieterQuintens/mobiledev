package mobiledev.pxl.be.triviaking;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import mobiledev.pxl.be.triviaking.fragmentsupport.Quiz;
import mobiledev.pxl.be.triviaking.fragmentsupport.QuizContent;
import mobiledev.pxl.be.triviaking.support.QRCodeSupporter;

public class QuizDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Quiz mItem;

    public QuizDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = QuizContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.created.toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.quiz_detail_category)).setText(mItem.category);
            ((TextView) rootView.findViewById(R.id.quiz_detail_difficulty)).setText(mItem.difficulty);
            ((TextView) rootView.findViewById(R.id.quiz_detail_num_questions)).setText(mItem.questions + "");
            try {
                QRCodeSupporter.encodeAsBitmap(mItem.data, (ImageView) rootView.findViewById(R.id.quiz_detail_qr));
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        return rootView;
    }
}
