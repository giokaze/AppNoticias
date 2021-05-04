package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.transacaoFragment
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiasFragment


class NoticiasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        if (savedInstanceState == null) {
            abreListaNoticias()
        }
    }

    private fun abreListaNoticias() {
        transacaoFragment {
            add(R.id.activity_noticias_container, ListaNoticiasFragment())
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when(fragment) {
            is ListaNoticiasFragment -> {
                fragment.quandoNoticiaSeleciona = this::abreVisualizadorNoticia
                fragment.quandoFabSalvaNoticiaClicado = this::abreFormularioModoCriacao
            }
            is VisualizaNoticiasFragment -> {
                fragment.quandoFinalizaTela = this::finish
                fragment.quandoSelecionaMenuEdicao = this::abreFormularioEdicao
            }
        }
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        val fragment = VisualizaNoticiasFragment()
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = dados
        transacaoFragment {
            addToBackStack(null)
            replace(R.id.activity_noticias_container, fragment)
        }
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }

}
