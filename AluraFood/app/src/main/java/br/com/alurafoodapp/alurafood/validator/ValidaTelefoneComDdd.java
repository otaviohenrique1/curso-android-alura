package br.com.alurafoodapp.alurafood.validator;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import br.com.alurafoodapp.alurafood.formatter.FormataTelefoneComDdd;

public class ValidaTelefoneComDdd implements Validador {
    // public static final String TELEFONE_DEVE_TER_ENTRE_DEZ_A_ONZE_DIGITOS = "Telefone deve ter entre 10 a 11 digitos";
    private static final String TELEFONE_DEVE_TER_ENTRE_DEZ_A_ONZE_DIGITOS = "Telefone deve ter entre 10 a 11 digitos";
    private final TextInputLayout textInputTelefoneComDdd;
    private final EditText campoTelefoneComDdd;
    private final ValidacaoPadrao validacaoPadrao;
    private final FormataTelefoneComDdd formatador = new FormataTelefoneComDdd();

    public ValidaTelefoneComDdd(TextInputLayout textInputTelefoneComDdd) {
        this.textInputTelefoneComDdd = textInputTelefoneComDdd;
        this.campoTelefoneComDdd = textInputTelefoneComDdd.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textInputTelefoneComDdd);
    }

    private boolean validaEntreDezOuOnzeDigitos(String telefoneComDdd) {
        int digitos = telefoneComDdd.length();
        if(digitos < 10 || digitos > 11) {
            textInputTelefoneComDdd.setError(TELEFONE_DEVE_TER_ENTRE_DEZ_A_ONZE_DIGITOS);
            return false;
        }
        return true;
    }

    @Override
    public boolean estaValido() {
        if(!validacaoPadrao.estaValido()) {return false;}
        String telefoneComDdd = campoTelefoneComDdd.getText().toString();
        String telefoneComDddSemFormatacao = formatador.remove(telefoneComDdd);
        if(!validaEntreDezOuOnzeDigitos(telefoneComDddSemFormatacao)) {return false;}
        adicionaFormatacao(telefoneComDddSemFormatacao);
        return true;
    }

    private void adicionaFormatacao(String telefoneComDdd) {
        String telefoneComDddFormatado = formatador.formata(telefoneComDdd);

        campoTelefoneComDdd.setText(telefoneComDddFormatado);
    }



    /*private void adicionaFormatacao(String telefoneComDdd) {
        StringBuilder sb = new StringBuilder();
        int digitos = telefoneComDdd.length();
        for (int i = 0; i < digitos; i++) {
            if(i == 0) {
                sb.append("(");
            }
            char digito = telefoneComDdd.charAt(i);
            sb.append(digito);
            if(i == 1) {
                sb.append(") ");
            }
            if(digitos == 10 && i == 5 || digitos == 11 && i == 6) {
                sb.append("-");
            }
        }
        String telefoneComDddFormatado = sb.toString();
        campoTelefoneComDdd.setText(telefoneComDddFormatado);
    }*/
}
