package com.example.capstoneproject1.Exception;

//import com.example.capstoneproject1.dto.response.ResponseMessage;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        String message = "Lỗi";
//        if (ex.getBindingResult().hasErrors()) {
//            message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
//        }
//        System.out.println( ">>>>>>>>>>>>>>>>>>>>>>" + message);
//        return new ResponseEntity<>(new ResponseMessage(1, message, 400), HttpStatus.BAD_REQUEST);
//    }
//}

//@ControllerAdvice
//@RestController
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//        @Override
//        protected ResponseEntity<Object> handleMethodArgumentNotValid(
//                MethodArgumentNotValidException ex,
//                HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//            return new ResponseEntity<>(new ResponseMessage(1, ex.getBindingResult().toString(), 400), HttpStatus.BAD_REQUEST);
//        }
//
//}

