package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String commid;

    private String commname;

    private String commdesc;

    private String videourl;

    private BigDecimal orimoney;

    private BigDecimal thinkmoney;

    private String school;

    private Date createtime;

    private Date updatetime;

    private Date endtime;

    private Integer commstatus;

    private String common;

    private String common2;

    private List<String> otherimg;

    private Integer rednumber;

    private String category;

    private String image;

    private String userid;

}
