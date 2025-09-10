package com.kob.backend.service.impl.user.account;

import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.account.LoginService;
import com.kob.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        // 创建一个认证令牌，包含用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        
        // 使用认证管理器进行认证，这一步会自动调用UserDetailsService和PasswordEncoder来验证用户
        Authentication authenticate = authenticationManager.authenticate(authenticationToken); // 登录失败会自动处理
        
        // 认证成功后，获取认证后的用户信息
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();

        // 为用户生成JWT令牌
        String jwt = JwtUtil.createJWT(user.getId().toString());
        
        // 构造返回结果
        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("token", jwt);

        return map;
    }
}
