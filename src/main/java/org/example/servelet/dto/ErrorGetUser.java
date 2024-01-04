package org.example.servelet.dto;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ErrorGetUser {
    public String message;

    /**
     * Будет передаваться число заглушка
     * Должно использоваться для точной локализации ошибки в дальнейшем
     */
    public String businessCode;
}
