package ar.edu.undec.ar_edu_undec_proyecto.exception;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;
@RestControllerAdvice

public class GlobalExceptionHandler {
    private ProblemDetail problema(HttpStatus status, String titulo, String detalle) {
        ProblemDetail pd=ProblemDetail.forStatusAndDetail(status, detalle);
        pd.setTitle(titulo);
        return pd;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.putIfAbsent(e.getField(), e.getDefaultMessage()));
        ProblemDetail pd = problema(HttpStatus.BAD_REQUEST, "Datos inválidos", "La Petición tiene errores");
        pd.setProperty("erroes", errores);
        return pd;
    }
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    public ProblemDetail pedidoMalFormado(Exception ex) {
        return problema(HttpStatus.BAD_REQUEST, "Petición inválida",
                "El cuerpo, un parámetro o el id de la petición no tienen un formato válido");
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail badRequest(BadRequestException ex) {
        return problema(HttpStatus.BAD_REQUEST, "Datos inválidos", ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail noEncontrado(NotFoundException ex) {
        return problema(HttpStatus.NOT_FOUND, "No encontrado", ex.getMessage());
    }

    @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class})
    public ProblemDetail conflicto(Exception ex) {
        String detalle = ex instanceof ConflictException ? ex.getMessage()
                : "La operación viola una restricción de datos";
        return problema(HttpStatus.CONFLICT, "Conflicto", detalle);
    }
}

