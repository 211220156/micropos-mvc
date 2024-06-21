package com.micropos.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
//import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
//import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
//@Entity
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@Table(name = "user")
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
//    @Id
//    @IsKey
//    @IsAutoIncrement
//    @Column(name = "id",comment = "主键")
    private Long id;

    /**
     * 用户名
     */
//    @TableField(value = "username")
//    @Column(name = "username",comment = "用户名")
    private String username;

    /**
     * 密码，加密存储
     */
//    @TableField(value = "password")
//    @Column(name = "password",comment = "密码")
    private String password;

    /**
     * 注册手机号
     */
//    @TableField(value = "phone")
//    @Column(name = "phone",comment = "手机号")
    private String phone;



}