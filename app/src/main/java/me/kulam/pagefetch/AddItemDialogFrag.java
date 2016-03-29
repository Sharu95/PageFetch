package me.kulam.pagefetch;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

    private static ArrayList<String> validCategories;
    private EditText categoryInput;
    private EditText pageTitle;
    private EditText pageDesc;
    private EditText pageCategory;
    private EditText pageUrl;

    private AutoCompleteTextView autoFillCategory;

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
    public static AddItemDialogFrag newInstance(String title, ArrayList<String> validCategories) {
        AddItemDialogFrag fragment = new AddItemDialogFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putStringArrayList("categories",validCategories);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            validCategories = getArguments().getStringArrayList("categories");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button btn;
        View view;
        TextView addItemTitle;

        if(title.equalsIgnoreCase("New Category")){
            view = inflater.inflate(R.layout.add_item_frag, container);
            btn = (Button) view.findViewById(R.id.add_item_btn);
            categoryInput = (EditText) view.findViewById(R.id.add_item_input); //Global scope for onClick purpose
            addItemTitle = (TextView) view.findViewById(R.id.add_item_title);

        } else{
            view = inflater.inflate(R.layout.add_page_frag, container);
            btn = (Button) view.findViewById(R.id.add_page_btn);
            addItemTitle = (TextView) view.findViewById(R.id.add_page_title);

            pageTitle = (EditText) view.findViewById(R.id.add_page_input_title); //Global scope for onClick purpose;
            pageTitle.requestFocus(View.FOCUS_DOWN);

            //pageCategory = (EditText) view.findViewById(R.id.add_page_input_category); //Global scope for onClick purpose;
            //pageCategory.requestFocus(View.FOCUS_DOWN);

            //TODO: CHECK IF VALID CATEGORY

            String[] cat = new String[validCategories.size()];
            for(int i = 0; i < cat.length; i++){
                String s = validCategories.get(i);
                String finite = "";
                char first = s.charAt(0);
                first = Character.toUpperCase(first);
                finite += first;
                finite += s.substring(1,s.length());
                cat[i] = finite;
            }
            autoFillCategory = (AutoCompleteTextView) view.findViewById(R.id.add_page_input_category);
            ArrayAdapter<String> tmpAdapter = new ArrayAdapter<>(getDialog().getContext(),android.R.layout.simple_list_item_1,cat);
            autoFillCategory.setAdapter(tmpAdapter);
            autoFillCategory.setThreshold(1);

            pageUrl = (EditText) view.findViewById(R.id.add_page_input_url); //Global scope for onClick purpose;
            pageUrl.requestFocus(View.FOCUS_DOWN);

            pageDesc = (EditText) view.findViewById(R.id.add_page_input_desc); //Global scope for onClick purpose;
            pageDesc.requestFocus(View.FOCUS_DOWN);

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
                    } else if (checkCategoryInput == 0) {
                        Toast.makeText(getDialog().getContext(), "Field is empty", Toast.LENGTH_SHORT).show();
                    } else {
                        getDialog().dismiss();
                    }
                } else {
                    String inputTitle = pageTitle.getText().toString();
                    String inputCategory = pageCategory.getText().toString();
                    String inputUrl = pageUrl.getText().toString();
                    String inputDesc = pageDesc.getText().toString();

                    String validUrlHTTP = "http://www." + inputUrl.toLowerCase().trim();
                    //String validUrlHTTPS = "https://www."+inputUrl.toLowerCase().trim();

                    //TODO: Get image
                    //TODO: Check edit text input wrong input
                    if (inputTitle.length() == 0 || inputCategory.length() == 0 || inputUrl.length() == 0) {
                        Toast.makeText(getDialog().getContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                    } else {
                        int MAX_DESC_LENGTH = ((cardActivity) getActivity()).getMaxDescSize();
                        if (URLUtil.isValidUrl(validUrlHTTP)) {
                            if (inputDesc.length() > MAX_DESC_LENGTH) {
                                Toast.makeText(getDialog().getContext(), "Description too long", Toast.LENGTH_SHORT).show();
                            } else {
                                mListener.handleUserInput(inputTitle, inputCategory, validUrlHTTP, inputDesc); //Callback
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
