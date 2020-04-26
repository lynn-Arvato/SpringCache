package com.arvato.core.service.impl;


import com.arvato.core.annotation.RedisCache;
import com.arvato.core.entity.DbUser;
import com.arvato.core.mapper.DbUserMapper;
import com.arvato.core.service.DbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DELL
 * @since 2020-04-23
 */
@Service
public class DbUserServiceImpl extends ServiceImpl<DbUserMapper, DbUser> implements DbUserService {

	@Autowired
	private DbUserMapper dbUserMapper;

	@Override
	@RedisCache
	public DbUser findByUserid(Long UserId) {
		return dbUserMapper.selectById(UserId);
	}

	@Override
	@RedisCache
	public List<DbUser> findAll() {
		return dbUserMapper.selectList(null);
	}


}
