package org.dclar.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.subject.Subject;

public class IdentifyCredentialsMatcher implements CredentialsMatcher {
    /**
     * Returns {@code true} if the provided token credentials match the stored account credentials,
     * {@code false} otherwise.
     *
     * @param token the {@code AuthenticationToken} submitted during the authentication attempt
     * @param info  the {@code AuthenticationInfo} stored in the system.
     * @return {@code true} if the provided token credentials match the stored account credentials,
     * {@code false} otherwise.
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        String userId = ((UsernamePasswordToken) token).getUsername();
        String password = String.valueOf(((UsernamePasswordToken) token).getPassword());

        if ("darcula@gmail.com".equals(userId) && "password".equals(password)) {
            return true;
        }
        return false;
    }
}

