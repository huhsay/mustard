package com.studiobethejustice.mustardcoupon.Member;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.studiobethejustice.mustardcoupon.ListViewItem.ClientManageItem;
import com.studiobethejustice.mustardcoupon.ListviewAdapter.ClientManageAdapter;
import com.studiobethejustice.mustardcoupon.R;
import com.studiobethejustice.mustardcoupon.ListViewItem.ClientManageItem;
import com.studiobethejustice.mustardcoupon.ListviewAdapter.ClientManageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by econo106 on 2017-07-03.
 */

public class Mycoupon extends Fragment{

    ListView listView; //상점목록을 나타낼 리스트
    Button SearchBtn;
    EditText SearchName;
    public static ClientManageAdapter adapter;
    public static ArrayList<ClientManageItem> listMember;


    private String userID;
    private String userPW;
    private String userNum;
    private String listurl ="http://168.131.35.106/main_store/store.php?seq=";
    private String usecouponurl ="";

    //json 데이터를 파싱할때 필요한 key값
    private static final String TAG_Response="response";
    private static final String TAG_Client="Id";
    private static final String TAG_ClientID="client";
    private static final String TAG_Coupon="coupon";
    private static final String TAG_Name="name";
    private static final String TAG_pw="pw";

    JSONArray posts=null;
    //이벤트에 따라 대화상자를 띄운다.
    boolean listState=false;
    boolean searchState=false;

    public Mycoupon() {
        // Required empty public constructot
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.client_management, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View v = view;
        Bundle extra = getArguments();
        userID = extra.getString("Id");
        userPW = extra.getString("Pw");
        userNum = extra.getString("Num");

        listView = (ListView) view.findViewById(R.id.clientlist);
        SearchBtn=(Button)view.findViewById(R.id.bt_search);
        SearchName = (EditText) view.findViewById(R.id.tb_search);

        //리스트뷰 정보 받아오기
        listurl=listurl+userNum;
        listState=true;
        //getData(listurl);
        listState=false;
        SearchBtn.setOnClickListener(searchlistener);
        SearchName.addTextChangedListener(changeListener);
    }

    //에디트박스 입력감지
    TextWatcher changeListener = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(SearchName.getText().toString().equals("")){
                adapter.delete();

                for(int j=0;j<listMember.size();j++){
                    adapter.addClient(listMember.get(j));
                    adapter.notifyDataSetChanged();  //리스트뷰 데이터 갱신
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //회원 검색
    View.OnClickListener searchlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //에디트박스 빈칸 체크
            if(!SearchName.getText().toString().equals("")){
                String searchName = SearchName.getText().toString();
                searchState=false;
                //    getData(searchurl+searchName+"&storeNum="+userNum);

                for(int i=0;i<listMember.size();i++){

                    if( listMember.get(i).getName().equals(searchName)){
                        searchState=true;
                        adapter.delete();
                        adapter.addClient(listMember.get(i));
                        adapter.notifyDataSetChanged() ;  //리스트뷰 데이터 갱신
                    }
                }

                if(!searchState){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("찾는 회원이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create()
                            .show();
                    searchState=false;
                }
            }

        }
    };

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

    public void makeList(String myJson){

        listMember = new ArrayList<ClientManageItem>();
        adapter = new ClientManageAdapter(getActivity().getApplicationContext(),userNum);
        try{
            JSONObject jsonObject = new JSONObject(myJson);
            posts = jsonObject.getJSONArray(TAG_Response);

            if(posts.length()==0){

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("등록한 회원이 아직 없습니다.")
                        .setNegativeButton("확인", null)
                        .create()
                        .show();
                searchState=false;

                return;
            }

            for(int i=0;i<posts.length();i++) {
                JSONObject member = posts.getJSONObject(i);
                int coupon =Integer.parseInt(member.getString(TAG_Coupon));
                listMember.add(new ClientManageItem(member.getString(TAG_Client),member.getString(TAG_ClientID),Integer.toString(coupon/10),Integer.toString(coupon%10),member.getString(TAG_Name),member.getString(TAG_pw)));
                Log.d("Id="+listMember.get(i).getId(),"coupon="+listMember.get(i).getStamp()+","+listMember.get(i).getCoupon()+listMember.get(i).getName()+listMember.get(i).getPw());
                adapter.addClient(listMember.get(i));
            }


            listMember.clear();

            for(int i=0;i<adapter.getMember().size();i++){
                listMember.add(adapter.getMember().get(i));
            }

            listView.setAdapter(adapter);

        }catch (JSONException e){


            e.printStackTrace();
        }
    }


}
