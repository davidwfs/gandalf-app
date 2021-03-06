package com.gandalf.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import com.gandalf.Fragments.ProductsFragment;
import com.gandalf.R;
import com.gandalf.WebClient;

import com.gandalf.adapter.ProductAdapter;
import com.gandalf.models.Product;
import com.google.gson.Gson;

import java.util.Arrays;

public class GetProductsTask extends AsyncTask<Void, Void, String> {

    private ProductsFragment context;
    private ProgressDialog dialog;

    public GetProductsTask(ProductsFragment context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(
                context.getContext(),
                context.getString(R.string.get_products_task_progress_title),
                context.getString(R.string.get_products_task_progress_message),
                true,
                true
        );
    }

    @Override
    protected String doInBackground(Void... params) {
        String response = new WebClient().get("/gandalf/rest/produto/");
        dialog.dismiss();
        return response;
    }

    @Override
    protected void onPostExecute(String resposta) {
        Product[] products = new Gson().fromJson(resposta, Product[].class);
        ProductAdapter adapter = new ProductAdapter(context.getContext(), Arrays.asList(products));
        ListView listView = (ListView) context.getActivity().findViewById(R.id.fragment_product_list);
        listView.setAdapter(adapter);
    }
}