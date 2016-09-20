package com.atto.developers.atto.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.atto.developers.atto.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPostcodeDialogFragment extends DialogFragment {

    private String key = "13156a02d459a7fdd1472444149179";
    ListView listView;

    private ArrayAdapter<String> mAdapter;
    private String keyword;
    private ArrayList<String> addressItems = new ArrayList<String>();

    public SearchPostcodeDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_postcode_dialog, container, false);

        final TextInputEditText inputAddress = (TextInputEditText) view.findViewById(R.id.edit_search_address);
        final String keyword = inputAddress.getText().toString();

        listView = (ListView) view.findViewById(R.id.listView);
        Button btn = (Button) view.findViewById(R.id.btn_search_address);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (!TextUtils.isEmpty(keyword)) {
                }*/
                getAddress(keyword);
                Toast.makeText(getActivity(), "Post", Toast.LENGTH_SHORT).show();
//                dismiss();

            }
        });
        return view;
    }

    public void getAddress(String keyword) {
        this.keyword = keyword;
        new GetAddressDataTask().execute(keyword);
    }

    private static final String POSTCODE_URL = "http://biz.epost.go.kr/KpostPortal/openapi?regkey=13156a02d459a7fdd1472444149179" +
            "&target=post&query=%s";

    private class GetAddressDataTask extends AsyncTask<String, Void, String> {

        HttpURLConnection conn = null;

        @Override
        protected String doInBackground(String... strings) {
            String keyword = strings[0];
            ArrayList<String> addressInfo = new ArrayList<String>();
            try {
                keyword = URLEncoder.encode(keyword, "EUC-KR");
                keyword = URLDecoder.decode(keyword, "ISO-8859-1");
                String urlText = String.format(POSTCODE_URL, URLEncoder.encode(keyword, "utf-8"));
                URL url = new URL(urlText);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("accept-language", "ko");
                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                byte[] bytes = new byte[4096];
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while (true) {
                    int red = in.read(bytes);
                    if (red < 0)
                        break;
                    baos.write(bytes, 0, red);
                }
                String xmlData = baos.toString("utf-8");
                baos.close();
                in.close();
                conn.disconnect();

                Document doc = docBuilder.parse(new InputSource(new StringReader(xmlData)));
                Element el = (Element) doc.getElementsByTagName("itemlist").item(0);
                for (int i = 0; i < ((Node) el).getChildNodes().getLength(); i++) {
                    Node node = ((Node) el).getChildNodes().item(i);
                    if (!node.getNodeName().equals("item")) {
                        continue;
                    }
                    String address = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                    String post = node.getChildNodes().item(3).getFirstChild().getNodeValue();
                    Log.w("postcode", "address = " + address);
                    addressInfo.add(address + "\n우편번호:" + post.substring(0, 3) + "-" + post.substring(3));
                }

                addressItems = addressInfo;
                publishProgress();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null)
                        conn.disconnect();
                } catch (Exception e) {
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);

            String[] addressStrArray = new String[addressItems.size()];
            addressStrArray = addressItems.toArray(addressStrArray);

            mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, addressStrArray);
            listView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }
    }
}
