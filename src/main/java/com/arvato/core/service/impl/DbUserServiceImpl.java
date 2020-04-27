package com.arvato.core.service.impl;


import com.arvato.core.annotation.RedisCache;
import com.arvato.core.entity.DbUser;
import com.arvato.core.mapper.DbUserMapper;
import com.arvato.core.service.DbUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
	public Page<DbUser> findAll(Page<DbUser> pages, Long userid, String address) {
		List<DbUser> list = dbUserMapper.selectList(new QueryWrapper<DbUser>().eq("userid",userid).eq("address",address));
		pages.setRecords(list);
		return pages;
	}
}
