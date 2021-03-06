package com.example.lista

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.room.Room

class TelaDetalhes : Fragment() {

    lateinit var db: AppDatabase
    override fun onAttach(context: Context) {
        super.onAttach(context)

        val database: String = "listadb"

        db = Room.databaseBuilder(context, AppDatabase::class.java, database)
            .allowMainThreadQueries().build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tela_detalhes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = db.afazerDao()

        val id = arguments?.getLong("id")

        if (id !== null) {
            val afazer = dao.get(id)

            val tituloInput: TextView = view.findViewById(R.id.tituloEdit)
            val descricaoInput: TextView = view.findViewById(R.id.descricaoEdit)

            tituloInput.text = afazer.titulo
            descricaoInput.text = afazer.descricao

            val updateBtn: Button = view.findViewById(R.id.atualizar)
            updateBtn.setOnClickListener {
                val titulo = tituloInput.text.toString()
                val descricao = descricaoInput.text.toString()

                val afazer = Afazer(titulo, descricao)
                afazer.id = id

                dao.update(afazer)

                hideKeyboard()

                view.findNavController().navigate(R.id.detalhes_para_lista)
            }
        }
    }

    fun hideKeyboard() {
        val inputManager: InputMethodManager = this.requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = this.requireActivity().currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}