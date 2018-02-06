package org.dclar.shiro.realm.auth;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

public class LoginRealmImpl extends AuthorizingRealm {


    private List<String> roles;


    @Override
    public String getName() {
        return null;
    }


    /**
     * @return true if this authentication realm can process the submitted token instance of the class, false otherwise.
     * <p>
     * <p/>
     * 如果token被realm所支持则返回true，否则返回false
     * <p/>
     * 需要覆盖相应supports方法来进行token的支持，一般可以采用
     * <p/>
     * 1. 获取token信息 2. 进行数据库（或缓存、redis、mongoDB等）比对 3. 返回校验结果
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {

        String userId = ((UsernamePasswordToken) authenticationToken).getUsername();
        String password = String.valueOf(((UsernamePasswordToken) authenticationToken).getPassword());

        if ("darcula@gmail.com".equals(userId) && "password".equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves authentication data from an implementation-specific datasource (RDBMS, LDAP, etc) for the given
     * authentication token.
     * <p/>
     * For most datasources, this means just 'pulling' authentication data for an associated subject/user and nothing
     * more and letting Shiro do the rest.  But in some systems, this method could actually perform EIS specific
     * log-in logic in addition to just retrieving data - it is up to the Realm implementation.
     * <p/>
     * A {@code null} return value means that no account could be associated with the specified token.
     *
     * @param token the authentication token containing the user's principal and credentials.
     * @return an {@link AuthenticationInfo} object containing account data resulting from the
     * authentication ONLY if the lookup is successful (i.e. account exists and is valid, etc.)
     * @throws AuthenticationException if there is an error acquiring data or performing
     *                                 realm-specific authentication logic for the specified <tt>token</tt>
     *                                 <p>
     *                                 <p>
     *                                 <p/>
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


//        String userId = ((UsernamePasswordToken) token).getUsername();
//        String password = String.valueOf(((UsernamePasswordToken) token).getPassword());

        return new SimpleAuthenticationInfo(
                token.getPrincipal(),
                token.getCredentials(),
                this.getClass().getSimpleName());
    }


    /*
     *  如果直接继承AuthorizingRealm后就不需要进行get方法的实现
     *
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        String userId = ((UsernamePasswordToken) authenticationToken).getUsername();
        String password = String.valueOf(((UsernamePasswordToken) authenticationToken).getPassword());

        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(
                        authenticationToken.getPrincipal(),
                        authenticationToken.getCredentials(),
                        this.getClass().getSimpleName());

        return simpleAuthenticationInfo;
    }
     */

    /**
     * Retrieves the AuthorizationInfo for the given principals from the underlying data store.  When returning
     * an instance from this method, you might want to consider using an instance of
     * {@link SimpleAuthorizationInfo SimpleAuthorizationInfo}, as it is suitable in most cases.
     *
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return the AuthorizationInfo associated with this principals.
     * @see SimpleAuthorizationInfo
     * <p>
     * 处理逻辑：
     * <p/>
     * 在多个realms进行顺序调用处理，首先在realms中调用各自的supports，true的场合继续
     * 然后继续调用getAuthenticationInfo方法，此处只需要重写doGetAuthenticationInfo即可，
     * 如果authentication通过后开始设定permission，即，处理各个realm的doGetAuthorizationInfo，
     * 在这里用户自行设定role即可，可以考虑把权限写入xml中，配置realm的时候同时写入，权限通过String描述即可
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        List<Object> objectList = principals.asList();


        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user1");

        Object o = principals.getPrimaryPrincipal();
        return simpleAuthorizationInfo;
    }
}
