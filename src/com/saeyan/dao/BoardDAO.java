package com.saeyan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

import com.saeyan.dto.BoardVO;

public class BoardDAO {
	private BoardDAO() {
	}

	private static BoardDAO instance = new BoardDAO();

	public static BoardDAO getInstance() {
		return instance;
	}

	public int allCount() {
		String sql = "select count(*) from board";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return 0;

	}
	
	public List<BoardVO> selectAllBoards(int startNum, int endNum) {
		//String sql = "select * from board order by grp desc, seq asc";
		//String sql = "select k.* from (select * from board order by grp desc, seq asc) k where rownum between ? and ?";
		String sql = "select t.* from (select sub.*, rownum as rnum from (select * from board order by grp desc, seq asc) sub) t where rnum between ? and ?";
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num=-1;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardVO bVo = new BoardVO();
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setEmail(rs.getString("email"));
				bVo.setPass(rs.getString("pass"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setReadcount(rs.getInt("readcount"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
				bVo.setGrp(rs.getInt("grp"));
				bVo.setLvl(rs.getInt("lvl"));
				bVo.setFname(rs.getString("fname"));
				list.add(bVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	public int nowNum() {
		String sql = "select board_seq.nextval as nextNum from dual";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int num = -1;
		try {
			conn = DBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				num = Integer.valueOf(rs.getString("nextNum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, stmt, rs);
		}
		return num;
	}

	
	public int maxGrp(int grpNum) {
		String sql = "select max(seq) as maxNum from board where grp=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num=-1;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, grpNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = Integer.valueOf(rs.getString("maxNum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return num;
	}
	
	public int maxLevelGrp(int grpNum, int lvlNum) {
		String sql = "select max(seq) as maxNum from board where grp=? and lvl=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num=-1;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, grpNum);
			pstmt.setInt(2, lvlNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = Integer.valueOf(rs.getString("maxNum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return num;
	}

	public boolean depthLevelGrp(int grpNum, int seqNum, int lvlNum) {
		String sql = "select * from board where grp=? and seq=? and lvl > ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num=-1;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, grpNum);
			pstmt.setInt(2, seqNum);
			pstmt.setInt(3, lvlNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return false;
	}
	
	public void insertBoard(BoardVO bVo) {
		String sql = "insert into board(" + "num, name, email, pass, title, content, fname, grp, seq, lvl) "
				+ "values(?, ?, ?, ?, ?, ?,?,?,0,0)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bVo.getNum());
			pstmt.setString(2, bVo.getName());
			pstmt.setString(3, bVo.getEmail());
			pstmt.setString(4, bVo.getPass());
			pstmt.setString(5, bVo.getTitle());
			pstmt.setString(6, bVo.getContent());
			pstmt.setString(7, bVo.getFname());
			pstmt.setInt(8, bVo.getNum());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}


	public void updateReadCount(String num) {
		String sql = "update board set readcount=readcount+1 where num=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}

	public void updateSeqCount(int grpNum, int positioNum) {
		String sql = " update board set seq=seq+1 where grp=? and seq > ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, grpNum);
			pstmt.setInt(2, positioNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
	// 게시판 글 상세 내용 보기 :글번호로 찾아온다. : 실패 null,
	public BoardVO selectOneBoardByNum(String num) {
		String sql = "select * from board where num = ?";
		BoardVO bVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			System.out.println("원글 찾기 쿼리 실행" + num);
			if (rs.next()) {
				 System.out.println("원글 찾음");
				bVo = new BoardVO();
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setPass(rs.getString("pass"));
				bVo.setEmail(rs.getString("email"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
				bVo.setReadcount(rs.getInt("readcount"));
				bVo.setGrp(rs.getInt("grp"));
				bVo.setSeq(rs.getInt("seq"));
				bVo.setLvl(rs.getInt("lvl"));
				bVo.setFname(rs.getString("fname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return bVo;
	}

	public void updateBoard(BoardVO bVo) {
		String sql = "update board set name=?, email=?, pass=?, " + "title=?, content=? where num=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bVo.getName());
			pstmt.setString(2, bVo.getEmail());
			pstmt.setString(3, bVo.getPass());
			pstmt.setString(4, bVo.getTitle());
			pstmt.setString(5, bVo.getContent());
			pstmt.setInt(6, bVo.getNum());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}

	public BoardVO checkPassWord(String pass, String num) {
		String sql = "select * from board where pass=? and num=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO bVo = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pass);
			pstmt.setString(2, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bVo = new BoardVO();
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setEmail(rs.getString("email"));
				bVo.setPass(rs.getString("pass"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setReadcount(rs.getInt("readcount"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bVo;
	}

	public void deleteBoard(String num) {
		String sql = "delete board where num=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertReplyBoard(BoardVO bVo) {
		// TODO Auto-generated method stub
		String sql = "insert into board(" + "num, name, email, pass, title, content, grp, seq, lvl) "
				+ "values(?, ?, ?, ?, ?, ?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bVo.getNum());
			pstmt.setString(2, bVo.getName());
			pstmt.setString(3, bVo.getEmail());
			pstmt.setString(4, bVo.getPass());
			pstmt.setString(5, bVo.getTitle());
			pstmt.setString(6, bVo.getContent());
			pstmt.setInt(7, bVo.getGrp());
			pstmt.setInt(8, bVo.getSeq());
			pstmt.setInt(9, bVo.getLvl());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		//순번에 따라 차등적으로 조정
	}

	public int depthLevelInt(int grpNum, int seqNum, int lvlNum) {
		// TODO Auto-generated method stub
		String sql = "select count(*) as maxn from board where grp=? and seq > ? and lvl > ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, grpNum);
			pstmt.setInt(2, seqNum);
			pstmt.setInt(3, lvlNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = Integer.valueOf(rs.getString("maxn"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return num;
	}
}
