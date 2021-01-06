package br.com.apiForum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//tratamento de erros
@RestControllerAdvice
public class erroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;// auxilia a detectar mensagens de erro;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)// altera o status code devolvido como resposta.
    @ExceptionHandler(MethodArgumentNotValidException.class)
//metodo sera chamado quando ocorrer alguma exception dentro de algum controller.
    public List<erroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
        List<erroDeFormularioDto> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
        String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());// descobre qual o locale atual de acordo com o que o cliente mandar para pegar a mensagem no idioma correto
        erroDeFormularioDto erro = new erroDeFormularioDto(e.getField(), mensagem);
        dto.add(erro);
        });
      return dto;
    }
}

