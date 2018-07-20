package com.entertainment.asset.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.entertainment.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity{

    @JSONField(name = "user_name")
    @JsonProperty("user_name")
    @Column(name = "user_name")
    private String userName;

    @JSONField(name = "pass_word")
    @JsonProperty("pass_word")
    @Column(name = "pass_word")
    private String passWord;

    @JSONField(name = "email")
    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JSONField(name = "province_id")
    @JsonProperty("province_id")
    @Column(name = "province_id")
    private Long provinceId;

    @JSONField(name = "city_id")
    @JsonProperty("city_id")
    @Column(name = "city_id")
    private Long cityId;

    @JSONField(name = "area_id")
    @JsonProperty("area_id")
    @Column(name = "area_id")
    private Long areaId;

    @JSONField(name = "address")
    @JsonProperty("address")
    @Column(name = "address")
    private String address;

    @JSONField(name = "invite_code")
    @JsonProperty("invite_code")
    @Column(name = "invite_code")
    private String inviteCode;

    @JSONField(name = "status")
    @JsonProperty("status")
    @Column(name = "status")
    private Long status;
}