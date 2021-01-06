package br.com.apiForum.config.validacao;


// classe que representará o erro de validação.
public class erroDeFormularioDto {
    private String campo;
    private String erro;

    public erroDeFormularioDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
