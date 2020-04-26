package com.arvato.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author DELL
 * @since 2020-04-23
 */
public class DbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userid")
    private Long userid;

    private String username;

    private String phone;

    private String address;

    private Integer age;

    private Integer userstatus;

    private LocalDateTime createtime;

    private LocalDateTime updatetime;


    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(Integer userstatus) {
        this.userstatus = userstatus;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "DbUser{" +
        "userid=" + userid +
        ", username=" + username +
        ", phone=" + phone +
        ", address=" + address +
        ", age=" + age +
        ", userstatus=" + userstatus +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        "}";
    }


}
