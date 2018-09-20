package br.edu.ifsp.sdm.denis.listacontatossdm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.sdm.denis.listacontatossdm.R;
import br.edu.ifsp.sdm.denis.listacontatossdm.model.Contato;

public class ContatoActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nomeEditText;
    private EditText enderecoEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;
    private Button cancelarButton;
    private Button salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        nomeEditText = findViewById(R.id.nomeEditText);
        enderecoEditText = findViewById(R.id.enderecoEditText);
        telefoneEditText = findViewById(R.id.telefoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        cancelarButton = findViewById(R.id.cancelarButton);
        salvarButton = findViewById(R.id.salvarButton);

        // Setando listeners dos botoes
        cancelarButton.setOnClickListener(this);
        salvarButton.setOnClickListener(this);

        String subtitulo;
        Contato contato = (Contato) getIntent().getSerializableExtra(ListaContatosActivity.CONTATO_EXTRA);
        String acao = getIntent().getStringExtra(ListaContatosActivity.ACAO_EXTRA);

        if(contato != null){

            if(acao == null){
                subtitulo = "Detalhes do contato";
                modoDetalhes(contato);
            }
            else{
                subtitulo = "Alterar contato";
                modoAlterar(contato);
            }
        }
        else{
            // Modo cadastro
            subtitulo = "Novo contato";
        }

        // Setando subtitulo
        getSupportActionBar().setSubtitle(subtitulo);
    }

    private void modoDetalhes(Contato contato){
        nomeEditText.setText(contato.getNome());
        nomeEditText.setEnabled(false);
        enderecoEditText.setText(contato.getEndereco());
        enderecoEditText.setEnabled(false);
        telefoneEditText.setText(contato.getTelefone());
        telefoneEditText.setEnabled(false);
        emailEditText.setText(contato.getEmail());
        emailEditText.setEnabled(false);

        salvarButton.setVisibility(View.GONE);
    }

    private void modoAlterar(Contato contato){
        nomeEditText.setText(contato.getNome());
        enderecoEditText.setText(contato.getEndereco());
        telefoneEditText.setText(contato.getTelefone());
        emailEditText.setText(contato.getEmail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelarButton:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.salvarButton:
                Contato novoContato = new Contato();
                novoContato.setNome(nomeEditText.getText().toString());
                novoContato.setEndereco(enderecoEditText.getText().toString());
                novoContato.setEmail(emailEditText.getText().toString());
                novoContato.setTelefone(telefoneEditText.getText().toString());

                Intent resultadoIntent = new Intent();
                resultadoIntent.putExtra(ListaContatosActivity.CONTATO_EXTRA, novoContato);

                setResult(RESULT_OK, resultadoIntent);

                finish();
                break;
        }
    }
}
