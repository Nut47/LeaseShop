package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Soldrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String commid;

    private String commname;

    private String commdesc;

    private BigDecimal thinkmoney;

    private Date soldtime;

    private String userid;

    private Integer soldstatus;
}
