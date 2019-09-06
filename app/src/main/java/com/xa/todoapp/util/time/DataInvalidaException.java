package com.xau.todoapp.util.time;

public class DataInvalidaException extends Exception {
    /**
     * Define a mensagem de erro
     * @param message Mensagem de erro
     */
    public DataInvalidaException(String message) {
        super(message);
    }

    /**
     * Retorna a mensagem de erro
     * @return Mensagem de erro
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
