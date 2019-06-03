package lw.pers.blog.config;

import lw.pers.blog.service.security.MyAuthenticationSuccessHandler;
import lw.pers.blog.service.security.MyLogoutHandler;
import lw.pers.blog.service.security.UserDetailsServiceImpl;
import lw.pers.blog.util.Md5;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    AuthenticationSuccessHandler mySuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }

    @Bean
    UserDetailsService myUserDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    MyLogoutHandler myLogoutHandler(){
        return new MyLogoutHandler();
    }
    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((myUserDetailsService())).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                String passwd  = null;
                try {
                    passwd= Md5.encodeByMd5((String)charSequence);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return passwd;
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                System.out.println(encode(charSequence));
                return s.equals(encode(charSequence));
            }
        });
    }

//    授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //对于url是ajax请求的话,不能在这里进行.hasRole()拦截,因为这样会造成一个问题:就是hasRole拦截后,
        //会重定向到登录页面,因为是ajax请求,服务端不能进行重定向
        //登录成功后会到主页index
        http.formLogin().usernameParameter("userName")
                .passwordParameter("passwd")
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                //这里使用了successHandler,就不用defaultSuccessUrl,作用是登录成功了后初始化工作
                .successHandler(mySuccessHandler())
                .failureUrl("/login/error")
                .and().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/doRegister").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/user/**","/editor","/draft/**","/update/**","/pubSuccess/**").hasRole("USER");
        http.rememberMe().tokenValiditySeconds(3600*24*10).authenticationSuccessHandler(mySuccessHandler());



      //访问/logout表示用户注销,清空session,成功退出后访问/
        http.logout().addLogoutHandler(myLogoutHandler()); //logoutSuccessUrl就不用写了
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/image/**")
                .antMatchers("/webjars/**")
                .antMatchers("/lib/**")
                .antMatchers("/languages/**")
                .antMatchers("/plugins/**");
    }
}

