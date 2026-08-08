package uz.uniSport.uni_sport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Biznes logika buzilganda (masalan, kort allaqachon band qilingan bo'lsa) otiladigan xatolik.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }
}
