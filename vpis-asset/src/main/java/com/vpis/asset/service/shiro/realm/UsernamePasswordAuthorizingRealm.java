package com.vpis.asset.service.shiro.realm;

import com.vpis.asset.service.sys.SysUserService;
import com.vpis.common.constant.BusinessConstant;
import com.vpis.common.entity.TbUser;
import com.vpis.common.exception.BusinessException;
import com.vpis.common.utils.Preconditions;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: UsernamePasswordAuthorizingRealm
 *  @package: com.entertainment.asset.service.shiro.realm
 *  @Date: Created in 2018/7/19 下午7:26
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Service
public class UsernamePasswordAuthorizingRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthorizingRealm.class);

    @Autowired
    private SysUserService sysUserService;

    @Value("${shiro.credentialsSalt}")
    private String credentialsSalt;

    /**
     * 不用查询权限信息，放到jwt realm授权方法种处理
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) auth;

        String username = usernamePasswordToken.getUsername();
        if (StringUtils.isEmpty(username)) {
            throw new BusinessException(BusinessConstant.USER_NAME_IS_NULL);
        }

        TbUser sysUser = sysUserService.findByPhone(username);
        if (Preconditions.isBlank(sysUser)) {
            throw new BusinessException(BusinessConstant.USER_NAME_NOT_FOUND);
        }
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassWord(), ByteSource.Util.bytes(credentialsSalt), getName());
    }

    /**
     * 重写 setCredentialsMatcher 方法为自定义的 Realm 设置 hash 验证方法
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密类型
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        hashedCredentialsMatcher.setHashIterations(10);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }

}