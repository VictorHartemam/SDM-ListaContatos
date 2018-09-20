package br.edu.ifsp.sdm.denis.listacontatossdm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.sdm.denis.listacontatossdm.R;
import br.edu.ifsp.sdm.denis.listacontatossdm.model.Contato;

public class ListaContatosAdapter extends ArrayAdapter<Contato> {

    private LayoutInflater layoutInflater;

    public ListaContatosAdapter(Context context, List<Contato> listaContatos) {
        super(context, R.layout.layout_view_contato_adapter, listaContatos);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //O que faz um inflater? Gerar objetos a partir de arquivos XML
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;
        //se ainda não foi inflada
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_view_contato_adapter, null);

            //Cria um novo holder para célula nova
            holder = new Holder();
            holder.nomeContatoTextView = convertView.findViewById(R.id.nomeContatoTextView);
            holder.emailContatoTextView = convertView.findViewById(R.id.emailContatoTextView);

            convertView.setTag(holder); //associamos o holder ao convertView (própria célula)
        }
        else {
            holder = (Holder) convertView.getTag();
        }

        Contato contato = getItem(position); //Poderíamos criar a lista como atributo privado, mas o getItem() vai até a lista passada como parâmetro

        holder.nomeContatoTextView.setText(contato.getNome());
        holder.emailContatoTextView.setText(contato.getEmail());

        return convertView;
    }

    //Criamos esta classe Holder para não ter que recuperar cada TextView usando o método findViewById() a cada chamada para getView() conforme consta abaixo
    private class Holder {
        public TextView nomeContatoTextView;
        public TextView emailContatoTextView;
    }

    //Modo ineficiente
    //TextView nomeContatoTextView = convertView.findViewById(R.id.nomeContatoTextView);
    //TextView emailContatoTextView = convertView.findViewById(R.id.emailContatoTextView);
}