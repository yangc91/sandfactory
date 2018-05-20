package com.yc.sandfactory.shiro;

import com.yc.sandfactory.entity.User;
import com.yc.sandfactory.service.IUserService;
import java.util.UUID;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * TODO hsun 完成注释
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/19 上午11:32
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) super.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User admin = userService.getUserByUserName("admin");
//        Set<String> roles = shiroSampleDao.getRolesByUsername(username);
//        authorizationInfo.setRoles(roles);
//        roles.forEach(role -> {
//            Set<String> permissions = this.shiroSampleDao.getPermissionsByRole(role);
//            authorizationInfo.addStringPermissions(permissions);
//        });
        Set<String> roles = new HashSet<>();
        roles.add("hi");
        authorizationInfo.setRoles(roles);
        Set<String> permissions = new HashSet<>();
        permissions.add("/hi");
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.getUserByUserName(username);

        if (null == user) {
            throw new UnknownAccountException();
        }

        String msgId = UUID.randomUUID().toString().replace("-", "");

        user.setMsgId(msgId);

        Jedis jedis = jedisPool.getResource();
        jedis.lpush("sf_user_msg_id", msgId);
        jedis.close();
        //todo，删除策略

        SecurityUtils.getSubject().getSession().setAttribute("USER_INFO", user);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), getName());

        return authenticationInfo;
    }
}
