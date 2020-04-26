package com.arvato.core.controller;


import com.arvato.core.annotation.RedisCache;
import com.arvato.core.entity.DbUser;
import com.arvato.core.pojo.ResultInfo;
import com.arvato.core.service.DbUserService;
import com.arvato.core.utils.NumUtil;
import com.arvato.core.utils.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DELL
 * @since 2020-04-23
 */
@Api(tags = "user信息")
@RestController
@RequestMapping("/db-user")
public class DbUserController extends BaseController {

	@Autowired
	private DbUserService dbUserController;

	@ApiOperation(value = "根据用户编号查询用户信息")
	@RequestMapping(value = "/findByUserid",method = RequestMethod.GET)
	public ResultInfo findByUserId(@RequestParam("userid") Long userId) {
		DbUser user = dbUserController.findByUserid(userId);
		return renderSuccess(user);
	}

	@ApiOperation(value = "查询所有用户信息")
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public ResultInfo findAll(){
		List<DbUser> list = dbUserController.findAll();
		return renderSuccess(list);
	}
	@ApiOperation(value = "添加用户信息")
	@RedisCache
	@RequestMapping(value = "/saveUser",method = RequestMethod.POST)
	public ResultInfo saveUser(
	@RequestParam("username") String username,@RequestParam("phone") String phone,
	@RequestParam("address") String address,@RequestParam("age") Integer age) throws InterruptedException {
		DbUser dbUser = new DbUser();
		dbUser.setUserid(NumUtil.nextPkId());
		dbUser.setUsername(username);
		dbUser.setPhone(phone);
		dbUser.setAddress(address);
		dbUser.setAge(age);
		dbUser.setCreatetime(LocalDateTime.now());
		dbUserController.save(dbUser);
		return renderSuccess();
	}

	@ApiOperation(value = "添加用户信息")
	@RedisCache
	@RequestMapping(value = "/delUser",method = RequestMethod.GET)
	public ResultInfo delUser(@RequestParam("userid") Integer userid){
		dbUserController.removeById(userid);
		return  renderSuccess();
	}

	@ApiOperation(value = "修改用户信息")
	@RedisCache
	@RequestMapping(value = "/updateUser",method = RequestMethod.GET)
	public ResultInfo updateUser(@RequestParam("userid") Long userid,
								 @RequestParam("username") String username,@RequestParam("phone") String phone,
								 @RequestParam("address") String address,@RequestParam("age") Integer age) {
		DbUser dbUser = dbUserController.findByUserid(userid);
		dbUser.setUserid(userid);
		dbUser.setUsername(username);
		dbUser.setPhone(phone);
		dbUser.setAddress(address);
		dbUser.setAge(age);
		dbUser.setUpdatetime(LocalDateTime.now());
		dbUserController.update(dbUser, new QueryWrapper<DbUser>().eq("userid",userid));
		return renderSuccess();
	}
}

