package com.Spring_Board.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
					   "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
public class MemberTests 
{
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@Autowired
	private DataSource ds;
	
	
	
	@Test
	public void testInsertMember()
	{
		String sql = "insert into tbl_member(userid, userpw, username) values(?, ?, ?)";
		
		
		for(int i = 0; i < 100; i++)
		{
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try
			{
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(2, pwEncoder.encode("pw" + i));
				
				
				if(i < 80) // 1~79
				{
					pstmt.setString(1, "user" + i);
					pstmt.setString(3, "일반 사용자" + i);
				}
				else if(i < 90) // 80 ~ 89
				{
					pstmt.setString(1, "manager" + i);
					pstmt.setString(3, "운영자" + i);
				}
				else // 90 ~ 99
				{
					pstmt.setString(1, "admin" + i);
					pstmt.setString(3, "관리자" + i);
				}
				
				pstmt.executeUpdate();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(pstmt != null) { try { pstmt.close(); } catch(Exception e) {} }
				if(con != null) { try { con.close(); } catch(Exception e) {} }
			}
		}
	}
	
	
	
	@Test
	public void testInsertAuth()
	{
		String sql = "insert into tbl_member_auth(userid, auth) values(?, ?)";
		
		
		for(int i = 0; i < 100; i++)
		{
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try
			{
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				

				if(i < 80) // 1 ~ 79
				{
					pstmt.setString(1, "user" + i);
					pstmt.setString(2, "ROLE_USER");
				}
				else if(i < 90) // 80 ~ 89
				{
					pstmt.setString(1, "manager" + i);
					pstmt.setString(2, "ROLE_MEMBER");
				}
				else // 90 ~ 99
				{
					pstmt.setString(1, "admin" + i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				
				pstmt.executeUpdate();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(pstmt != null) { try { pstmt.close(); } catch(Exception e) {} }
				if(con != null) { try { con.close(); } catch(Exception e) {} }
			}
		}
		
	}
}
