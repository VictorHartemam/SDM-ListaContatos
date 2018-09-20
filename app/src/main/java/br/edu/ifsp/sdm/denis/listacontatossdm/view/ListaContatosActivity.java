package br.edu.ifsp.sdm.denis.listacontatossdm.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.sdm.denis.listacontatossdm.R;
import br.edu.ifsp.sdm.denis.listacontatossdm.adapter.ListaContatosAdapter;
import br.edu.ifsp.sdm.denis.listacontatossdm.model.Contato;

public class ListaContatosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //EEQUEST_CODE para abertura da tela ContatoActivity
    private final int NOVO_CONTATO_REQUEST_CODE = 0;

    private final int ALTERAR_CONTATO_REQUEST_CODE = 1;

    //constante para passar parametros para tela ContatoActivity - MODO DETALHES
    public static final String CONTATO_EXTRA = "CONTATO_EXTRA";
    public static final String ACAO_EXTRA = "ACAO_EXTRA";
    //referências para as views
    private ListView listaContatosListView;

    //lista de contatos usada para preencher a Listview
    private List<Contato> listaContatos;

    //adapter que preenche a ListView
    private ListaContatosAdapter listaContatosAdapter;

    private int posicaoItemAlterado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        listaContatosListView = findViewById(R.id.listaContatosListView);

        listaContatos = new ArrayList<>();

        //preencheListaContatos();

        listaContatosAdapter = new ListaContatosAdapter(this, listaContatos);
        listaContatosListView.setAdapter(listaContatosAdapter);

        registerForContextMenu(listaContatosListView); //quando clicar no ListView, queremos que o menu de contexto apareça

        listaContatosListView.setOnItemClickListener(this);
    }

    private void preencheListaContatos() {
        for(int i = 0; i < 20; i++) {
            listaContatos.add(new Contato("C"+i, "IFSP", "32112345", "emailcontato@servidor"));
        }
    }

    //Para criar o menu normal
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu); //estamos inflando o menu representado pelo nosso XML
        return true;
    }

    //Para tratar o evento de selecionar um item do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.configuracaoMenuItem:
                //carregar uma nova tela
                return true;
            case R.id.novoContatoMenuItem:
                //cabrindo tela de novo contato
                Intent novoContatoIntent = new Intent(this, ContatoActivity.class);
                startActivityForResult(novoContatoIntent, NOVO_CONTATO_REQUEST_CODE);
                return true;
            case R.id.sairMenuItem:
                finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case ALTERAR_CONTATO_REQUEST_CODE:
                //Toast.makeText(this,"Deveria ter alterado", Toast.LENGTH_SHORT).show();
                if(resultCode == RESULT_OK){
                    // recupera o contato da Intent data
                    Contato contatoAlterado = (Contato) data.getSerializableExtra(CONTATO_EXTRA);

                    // atualiza lista
                    if (contatoAlterado != null){
                        listaContatos.set(posicaoItemAlterado, contatoAlterado);
                        listaContatosAdapter.notifyDataSetChanged();
                        Toast.makeText(this,"Contato alterado!", Toast.LENGTH_SHORT).show();
                    }
                    // notifica o adapter
                }
                else if(resultCode == RESULT_CANCELED){
                    //nao faria nada
                    Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT).show();
                }
                break;

            case NOVO_CONTATO_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    // recupera o contato da Intent data
                    Contato novoContato = (Contato) data.getSerializableExtra(CONTATO_EXTRA);

                    // atualiza lista
                    if (novoContato != null){
                        listaContatos.add(novoContato);
                        listaContatosAdapter.notifyDataSetChanged();
                        Toast.makeText(this,"Novo contato adiconado!", Toast.LENGTH_SHORT).show();
                    }
                    // notifica o adapter
                }
                else if(resultCode == RESULT_CANCELED){
                    //nao faria nada
                    Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contexto, menu);
       // Toast.makeText(this, "AQUI <-2-> " + String.valueOf(v.getId()), Toast.LENGTH_LONG).show();


    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //return super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        Contato contato = listaContatos.get(menuInfo.position);

        switch(item.getItemId()) {
            case R.id.editarContatoMenuItem:
                //carregar uma nova tela
                //Toast.makeText(this, "AQUI <-->", Toast.LENGTH_SHORT).show();
                posicaoItemAlterado = menuInfo.position;
                Intent alterarContatoIntent = new Intent(this, ContatoActivity.class);
                alterarContatoIntent.putExtra(CONTATO_EXTRA, contato);
                alterarContatoIntent.putExtra(ACAO_EXTRA, "ALTERAR");
                startActivityForResult(alterarContatoIntent, ALTERAR_CONTATO_REQUEST_CODE);
                //detalhesContatoIntent.putExtra()
                return true;
            case R.id.ligarContatoMenuItem:
                //carregar uma nova tela
                return true;
            case R.id.verEnderecoMenuItem:
                //carregar uma nova tela
                return true;
            case R.id.enviarEmailMenuItem:
                //carregar uma nova tela
                return true;
            case R.id.removerContatoMenuItem:
                //carregar uma nova tela
                return true;
        }
        return false;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Contato contato = listaContatos.get(position);

        Intent detalhesContatoIntent = new Intent(this, ContatoActivity.class);

        detalhesContatoIntent.putExtra(CONTATO_EXTRA, contato);

        startActivity(detalhesContatoIntent);
    }
}