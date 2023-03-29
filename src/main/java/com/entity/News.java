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
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;

	private String newstitle;

	private String newsdesc;

	private String newscontent;

	private Date createtime;

	private String username;

    private String image;

	private Integer newsstatus;

	private Integer rednumber;


}
