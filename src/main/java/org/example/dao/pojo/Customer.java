package org.example.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {

    final Integer id;
    public String name;
    public String lastName;
    public String secondName;
    public String numDocument;

}
