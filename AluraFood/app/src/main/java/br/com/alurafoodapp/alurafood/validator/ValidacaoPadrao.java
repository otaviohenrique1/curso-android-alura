package br.com.alurafoodapp.alurafood.validator;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

public class ValidacaoPadrao implements Validador {
    private static final String CAMPO_OBRIGATORIO = "Campo obrigatório";
    private final TextInputLayout textInputCampo;
    private final EditText campo;

    public ValidacaoPadrao(TextInputLayout textInputCampo) {
        this.textInputCampo = textInputCampo;
        this.campo = this.textInputCampo.getEditText();
    }

    private boolean validaCampoObigatorio() {
        String texto = campo.getText().toString();
        if (texto.isEmpty()) {
            textInputCampo.setError(CAMPO_OBRIGATORIO);
            return false;
        }
        return true;
    }

    @Override
    public boolean estaValido() {
        if (!validaCampoObigatorio()) {return false;}
        removeErro();
        return true;
    }

    private void removeErro() {
        textInputCampo.setError(null);
        textInputCampo.setErrorEnabled(false);
    }
}
