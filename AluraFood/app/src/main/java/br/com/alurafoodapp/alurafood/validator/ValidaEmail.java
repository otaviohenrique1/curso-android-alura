package br.com.alurafoodapp.alurafood.validator;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

public class ValidaEmail implements Validador {
    private final TextInputLayout textInputEmail;
    private final EditText campoEmail;
    private final ValidacaoPadrao validacaoPadrao;

    public ValidaEmail(TextInputLayout textInputEmail) {
        this.textInputEmail = textInputEmail;
        this.campoEmail = this.textInputEmail.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(this.textInputEmail);
    }

    private boolean validaPadrao(String email) {
        if(email.matches(".+@.+\\..+")) {
            return true;
        }
        textInputEmail.setError("E-mail inválido");
        return false;
    }

    @Override
    public boolean estaValido() {
        if(!validacaoPadrao.estaValido()) {return false;}
        String email = campoEmail.getText().toString();
        if(!validaPadrao(email)) {return false;}
        return true;
    }
}
