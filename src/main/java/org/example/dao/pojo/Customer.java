package org.example.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {

    public Integer id;

    @NonNull
    public String name;

    @NonNull
    public String lastName;

    @NonNull
    public String secondName;

    @NonNull
    public String numDocument;

}
