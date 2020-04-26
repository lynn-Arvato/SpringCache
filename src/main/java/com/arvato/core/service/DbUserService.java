package com.arvato.core.service;

import com.arvato.core.entity.DbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DELL
 * @since 2020-04-23
 */
public interface DbUserService extends IService<DbUser> {

	DbUser findByUserid(Long UserId);
	List<DbUser> findAll();
}
