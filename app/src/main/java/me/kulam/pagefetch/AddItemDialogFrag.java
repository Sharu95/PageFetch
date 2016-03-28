package me.kulam.pagefetch;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddItemDialogFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemDialogFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemDialogFrag extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText categoryInput;
    private String title;

    private OnFragmentInteractionListener mListener;

    public AddItemDialogFrag() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @return A new instance of fragment AddItemDialogFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemDialogFrag newInstance(String title) {
        AddItemDialogFrag fragment = new AddItemDialogFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            Button btn;
            View view;
            TextView addItemTitle;

            view = inflater.inflate(R.layout.add_item_frag, container);
            btn = (Button) view.findViewById(R.id.add_item_btn);
            categoryInput = (EditText) view.findViewById(R.id.add_item_input); //Global scope for onClick purpose
            addItemTitle = (TextView) view.findViewById(R.id.add_item_title);

            addItemTitle.setText(title);
            btn.setText("Add");

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int input = mListener.handleUserInput(categoryInput.getText().toString(),null,null,null); //Callback

                if (input == -1) {
                    Toast.makeText(getDialog().getContext(), "Category already exists", Toast.LENGTH_SHORT).show();
                } else if (input == 0) {
                    Toast.makeText(getDialog().getContext(), "Field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    getDialog().dismiss();
                }
                }
            });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setWindowAnimations(R.style.searchDialogSlide);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onStart() {
        super.onStart();
        try{
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch(ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        int handleUserInput(String inputTitle, String inputCategory, String inputUrl, String inputDesc);
    }
}
