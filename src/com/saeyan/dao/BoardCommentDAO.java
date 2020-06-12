package com.saeyan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saeyan.dto.BoardCommentVO;

import util.DBManager;

public class BoardCommentDAO {
	private BoardCommentDAO() {
	}

	private static BoardCommentDAO instance = new BoardCommentDAO();

	public static BoardCommentDAO getInstance() {
		return instance;
	}
	
	public List<BoardCommentVO> selectAllBoards(String num) {
		String sql = "select * from boardComment where num=? order by num desc ";
		List<BoardCommentVO> list = new ArrayList<BoardCommentVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardCommentVO bVo = new BoardCommentVO();
				bVo.setNum(rs.getInt("num"));
				bVo.setId(rs.getString("id"));
				bVo.setcMemo(rs.getString("cMemo"));
				bVo.setCno(rs.getInt("cno"));
				bVo.setCdate(rs.getTimestamp("cdate"));
				list.add(bVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	public void insertCommentBoard(BoardCommentVO bVo) {
		String sql = "insert into boardComment (num,id,cMemo,cno) values (?,?,?,boardComment_seq.nextval)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bVo.getNum());
			pstmt.setString(2, bVo.getId());
			pstmt.setString(3, bVo.getcMemo());
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
	}
}
