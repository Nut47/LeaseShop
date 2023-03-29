package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Commimages implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;

	private String commid;

	private String image;

	private Date createtime;

    private Integer imagestatus;
}
