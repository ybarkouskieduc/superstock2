package com.wldrmnd.superstock.controller;

import com.wldrmnd.superstock.exception.BankExchangeNotFoundException;
import com.wldrmnd.superstock.exception.NotEnoughMoneyOnAccountExistsException;
import com.wldrmnd.superstock.exception.NotEnoughStockToSellException;
import com.wldrmnd.superstock.exception.StockPriceIsNotExistsException;
import com.wldrmnd.superstock.exception.TransactionNotFound;
import com.wldrmnd.superstock.exception.UserAccountNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleIllegalExceptions(
            RuntimeException ex,
            WebRequest request
    ) {
        String bodyOfResponse = "Пожалуйста, убедитесь в корректности введенных данных";
        return handleExceptionInternal(
                ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request
        );
    }

    @ExceptionHandler(value = { BankExchangeNotFoundException.class })
    protected ResponseEntity<Object> handleBankExchangeNotFoundException(
            RuntimeException ex,
            WebRequest request
    ) {
        String bodyOfResponse = "Курс обмена заданного банка не найден";
        return handleExceptionInternal(
                ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = { NotEnoughMoneyOnAccountExistsException.class })
    protected ResponseEntity<Object> handleNotEnoughMoneyOnAccountExistsException(
            RuntimeException ex,
            WebRequest request
    ) {
        String bodyOfResponse = "На вашем банковском счете недостаточно средств";
        return handleExceptionInternal(
                ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = { NotEnoughStockToSellException.class })
    protected ResponseEntity<Object> handleNotEnoughStockToSellException(
            RuntimeException ex,
            WebRequest request
    ) {
        String bodyOfResponse = "На вашем акционном счете недостаточно средств для продажи";
        return handleExceptionInternal(
                ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = { StockPriceIsNotExistsException.class })
    protected ResponseEntity<Object> handleStockPriceIsNotExistsException(
            RuntimeException ex,
            WebRequest request
    ) {
        String bodyOfResponse = "Курс обменна для данной акции не существует, повторите попытку позже";
        return handleExceptionInternal(
                ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = { TransactionNotFound.class })
    protected ResponseEntity<Object> handleTransactionNotFound(
            RuntimeException ex,
            WebRequest request
    ) {
        String bodyOfResponse = "Транзакция не найдена";
        return handleExceptionInternal(
                ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = { UserAccountNotFoundException.class })
    protected ResponseEntity<Object> handleUserAccountNotFoundException(
            RuntimeException ex,
            WebRequest request
    ) {
        String bodyOfResponse = "Банковский аккаунт данного пользователя не найден";
        return handleExceptionInternal(
                ex,
                bodyOfResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}