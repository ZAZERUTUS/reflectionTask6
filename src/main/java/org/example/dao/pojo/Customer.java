package org.example.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {

    /**
     * Не влияет при сохранении нового пользователя
     * БД рисваивает свой
     */
    public Integer id;

    /**
     * Не должно содержать пробелов
     */
    @NonNull
    public String name;

    /**
     * Не должно содержать пробелов
     */
    @NonNull
    public String lastName;

    /**
     * Не должно содержать пробелов
     */
    @NonNull
    public String secondName;

    /**
     * Должно соответствовать формату 'HBxxxxxxx'
     * где x это числа (7 чисел)
     */
    @NonNull
    public String numDocument;

}
