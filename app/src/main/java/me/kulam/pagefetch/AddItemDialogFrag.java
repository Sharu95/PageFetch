package me.kulam.pagefetch;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddItemDialogFrag extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText categoryInput;
    private EditText pageTitle;
    private EditText pageDesc;
    private TextView pageCategory; //TODO: TMP TEXT
    private EditText pageUrl;

    private String category;
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
    public static AddItemDialogFrag newInstance(String title, String category) {
        AddItemDialogFrag fragment = new AddItemDialogFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            category = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button btn;
        View view;
        TextView addItemTitle;

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if(title.equalsIgnoreCase("New Category")){
            view = inflater.inflate(R.layout.add_item_frag, container);
            btn = (Button) view.findViewById(R.id.add_item_btn);
            categoryInput = (EditText) view.findViewById(R.id.add_item_input); //Global scope for onClick purpose
            categoryInput.requestFocus();
            addItemTitle = (TextView) view.findViewById(R.id.add_item_title);
        } else{
            view = inflater.inflate(R.layout.add_page_frag, container);
            btn = (Button) view.findViewById(R.id.add_page_btn);
            addItemTitle = (TextView) view.findViewById(R.id.add_page_title);

            pageTitle = (EditText) view.findViewById(R.id.add_page_input_title); //Global scope for onClick purpose;
            pageTitle.requestFocus();

            pageCategory = (TextView) view.findViewById(R.id.add_page_input_category); //Global scope for onClick purpose;
            pageCategory.setText(category);

            pageUrl = (EditText) view.findViewById(R.id.add_page_input_url); //Global scope for onClick purpose;

            pageDesc = (EditText) view.findViewById(R.id.add_page_input_desc); //Global scope for onClick purpose;
        }
        addItemTitle.setText(title); //Move out later

        //TODO: How to set one listener on multiple buttons
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int input = categoryInput.getId();
                if (title.equalsIgnoreCase("New Category")) {
                    int checkCategoryInput = mListener.handleUserInput(categoryInput.getText().toString(), null, null, null); //Callback
                    if (checkCategoryInput == -1) {
                        Toast.makeText(getDialog().getContext(), "Category already exists", Toast.LENGTH_SHORT).show();
                    } else if (checkCategoryInput == 0) { //TODO: Categoryfield empty is buggy. Fix issue on empty input
                        Toast.makeText(getDialog().getContext(), "Field is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        getDialog().dismiss();
                    }
                } else {
                    String inputTitle = pageTitle.getText().toString().trim();
                    String inputCategory = pageCategory.getText().toString().trim();
                    String inputUrl = pageUrl.getText().toString();
                    String inputDesc = pageDesc.getText().toString().trim();

                    String validUrlHTTP = "http://www." + inputUrl.toLowerCase().trim();

                    //TODO: Get image
                    //TODO: Check edit text input wrong input
                    if (inputTitle.length() == 0 || inputUrl.length() == 0) {
                        Toast.makeText(getDialog().getContext(), "Title or URL is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        int MAX_DESC_LENGTH = ((cardActivity) getActivity()).getMaxDescSize();
                        if (StringUsage.URLchecker(validUrlHTTP)) {
                            if (inputDesc.length() > MAX_DESC_LENGTH) {
                                Toast.makeText(getDialog().getContext(), "Description too long", Toast.LENGTH_SHORT).show();
                            } else {
                                mListener.handleUserInput(inputTitle, inputCategory, inputUrl, inputDesc); //Callback
                                getDialog().dismiss();
                            }
                            //Toast.makeText(getDialog().getContext(),"Valid HTTP", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getDialog().getContext(), "Web page/URL is invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
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
