package com.bilet3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bilet3.R;
import com.bilet3.model.Articol;

import java.util.ArrayList;
import java.util.List;

public class ArticolAdapter extends ArrayAdapter {

    private Articol mArticol;
    private List list = new ArrayList();

    public ArticolAdapter(Context context, int resource) {
        super(context, resource);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ArticolHandler articolHandler;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.item_articol, parent, false);
            articolHandler = new ArticolHandler();
            articolHandler.tvTitlu = row.findViewById(R.id.titlu);
            articolHandler.tvAbstract = row.findViewById(R.id.tvabstract);
            articolHandler.tvAutori = row.findViewById(R.id.autori);

            row.setTag(articolHandler);
        } else {
            articolHandler = (ArticolHandler) row.getTag();
        }

        Articol mArticol = (Articol) this.getItem(position);
        articolHandler.tvTitlu.setText(mArticol.getTitlu());
        articolHandler.tvAbstract.setText(mArticol.getAbstractArticol());
        articolHandler.tvAutori.setText(mArticol.getAutori());

        return row;
    }

    class ArticolHandler {
        TextView tvTitlu, tvAbstract, tvAutori;
    }
}
