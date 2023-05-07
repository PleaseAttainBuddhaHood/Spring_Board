package com.Spring_Board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Spring_Board.domain.MemberVO;
import com.Spring_Board.mapper.MemberMapper;
import com.Spring_Board.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService 
{

	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException 
	{
		log.warn("사용자 이름 : " + userName);
		
		MemberVO vo = memberMapper.read(userName);
		
		log.warn("queried by 멤버 mapper : " + vo);
		
		
		return vo == null ? null : new CustomUser(vo);
	}

}
