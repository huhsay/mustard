package com.studiobethejustice.mustardcoupon.Member;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.studiobethejustice.mustardcoupon.ListViewItem.StoreListItem;
import com.studiobethejustice.mustardcoupon.ListviewAdapter.CouponAdapter;
import com.studiobethejustice.mustardcoupon.ListviewAdapter.SearchAdapter;
import com.studiobethejustice.mustardcoupon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchStore extends Fragment {


    ListView listView;
    EditText SearchName;
    Button search;
    public static SearchAdapter adapter;

    private String userID;
    private String userPw;
    public static String userNum;
    private String searchurl ="http://168.131.35.106/main_member/search_store.php";

    private static final String TAG_Response="response";
    private static final String TAG_Id="Id";
    private static final String TAG_Store="Name";
    private static final String TAG_Address="address";
    private static final String TAG_Phone="phone";

    JSONArray posts=null;
    boolean searchState=false;
    public static ArrayList<StoreListItem> listMember;

    public SearchStore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search_store, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View v = view;
        Bundle extra = getArguments();

        userID = extra.getString("userID");
        userPw = extra.getString("userPassword");
        userNum = extra.getString("userNum");

        listView = (ListView) view.findViewById(R.id.storelist);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });


        SearchName = (EditText) view.findViewById(R.id.tb_StoreSearch);
        search = (Button) view.findViewById(R.id.bt_StoreSearch);




        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!SearchName.getText().toString().equals("")){
                    String searchName = SearchName.getText().toString();
                    searchState=false;
                    //    getData(searchurl+searchName+"&storeNum="+userNum);

                    for(int i=0;i<listMember.size();i++){

                        if( listMember.get(i).getStoreName().equals(searchName)){
                            searchState=true;
                            adapter.delete();
                            adapter.addStore(listMember.get(i));
                            adapter.notifyDataSetChanged() ;  //리스트뷰 데이터 갱신
                        }
                    }

                    if(!searchState){
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                        builder.setMessage("찾는 상점이 없습니다.")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();
                        searchState=false;
                    }
                }
            }
        });

        SearchName.addTextChangedListener(changeListener);
        getData(searchurl);
    }


    public void show(String Name,String Phone,String Address,String coupon) {

        DialogFragment newFragment = new MycouponDialog(Name, Phone, Address, coupon);
        newFragment.setTargetFragment(this,1234);
        newFragment.show(getFragmentManager(),"dialog");
    }


    TextWatcher changeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(SearchName.getText().toString().equals("")){
                adapter.delete();

                for(int j=0;j<listMember.size();j++){
                    adapter.addStore(listMember.get(j));
                    adapter.notifyDataSetChanged();  //리스트뷰 데이터 갱신
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public boolean check(String Id){

        for(int i=0;i<Mycoupon.adapter.getMember().size();i++){

            if(Mycoupon.adapter.getMember().get(i).getId().equals(Id)){
                Log.d("중복됩니다.","!"+Id);  return false;
            }
        }

        return true;
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String,Void,String> {
            //통신이 완료될때까지 프로그래스 다이어로그를 보여줌
            private ProgressDialog nDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                nDialog = new ProgressDialog(getActivity()); //Here I get an error: The constructor ProgressDialog(PFragment) is undefined
                nDialog.setMessage("Loading..");
                nDialog.setTitle("정보를 받아오고 있습니다.");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
            }

            //http를 이용하여 문자스트림을 받고 그것을 json데이터로 변환해주어 리스트뷰에 보여준다.
            @Override
            protected String doInBackground(String... params) {
                //JSON 받아온다.
                String uri = params[0];
                BufferedReader br = null;


                try {
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;
                    while((json = br.readLine()) != null) {
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();
                }catch (Exception e) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String myJSON) {

                makeList(myJSON); //리스트를 보여줌
                if (nDialog  != null && nDialog .isShowing()) {
                    nDialog .dismiss();
                }

            }
        }

        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    public void makeList(String myJson) {


        adapter = new SearchAdapter(getActivity().getApplicationContext());
        listMember =new ArrayList<StoreListItem>();

        try {
            JSONObject jsonObject = new JSONObject(myJson);
            posts = jsonObject.getJSONArray(TAG_Response);

            if (posts.length() == 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("상점을 찾을 수 없습니다..")
                        .setNegativeButton("확인", null)
                        .create()
                        .show();

                return;
            }

            for (int i = 0; i < posts.length(); i++) {
                JSONObject member = posts.getJSONObject(i);
                boolean state= check(member.getString("sNum"));
                if(state==true) {
                    adapter.addStore(new StoreListItem(member.getString("sNum"), member.getString(TAG_Id), member.getString(TAG_Store), member.getString(TAG_Phone), member.getString(TAG_Address)));
                    listMember.add(new StoreListItem(member.getString("sNum"), member.getString(TAG_Id), member.getString(TAG_Store), member.getString(TAG_Phone), member.getString(TAG_Address)));
                    Log.d("!", "!" + member.getString("sNum"));
                }
            }

            listView.setAdapter(adapter);

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}
