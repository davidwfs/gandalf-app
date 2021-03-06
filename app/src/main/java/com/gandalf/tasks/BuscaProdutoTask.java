package com.gandalf.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ListView;

import com.gandalf.SearchFragment;
import com.gandalf.R;
import com.gandalf.WebClient;
import com.gandalf.adapter.ProductAdapter;
import com.gandalf.models.Product;
import com.google.gson.Gson;

import java.util.Arrays;

/**
 * Created by Igor Ramos on 19/11/2017.
 */

public class BuscaProdutoTask extends AsyncTask<Void, Void, String> {

    private SearchFragment context;
    private ProgressDialog dialog;

    public BuscaProdutoTask(SearchFragment context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context.getContext(), "Aguarde", "Buscando Produto...", true, true);

    }

    @Override
    protected String doInBackground(Void... params) {
        String resposta;
        WebClient client = new WebClient();
        EditText editText = (EditText) context.getActivity().findViewById(R.id.txtBuscaProduto);
        resposta = client.get("/gandalf/rest/produto/like/" + recebendoValor(editText));
        dialog.dismiss();
        return resposta;
    }

    @Override
    protected void onPostExecute(final String resposta) {

        if (!resposta.equals("null")) {
            Product[] produtos = new Gson().fromJson(resposta, Product[].class);
            ProductAdapter adapter = new ProductAdapter(context.getContext(), Arrays.asList(produtos));
            final ListView listView = (ListView) context.getActivity().findViewById(R.id.fragment_product_list);
            listView.setAdapter(adapter);
        }
    }

    public String recebendoValor(EditText editText){

        String valor = editText.getText().toString();

        return valor;
    }

}
