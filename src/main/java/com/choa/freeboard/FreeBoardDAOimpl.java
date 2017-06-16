package com.choa.freeboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import com.choa.board.BoardDAO;
import com.choa.board.BoardDTO;
import com.choa.notice.NoticeDTO;
import com.choa.util.DBConnector;
import com.choa.util.RowMaker;


public class FreeBoardDAOimpl implements BoardDAO {
	
	@Inject
	private DataSource dataSource;
	
	//inject를 이용하기때문에, 생성자가 필요가 없다
	/*public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/

	
	@Override
	public void boardHit(int num)throws Exception{
		
	}

	@Override
	public List<BoardDTO> boardList(RowMaker rowMaker) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		List<BoardDTO> ar = new ArrayList<BoardDTO>();
		FreeBoardDTO freeBoardDTO = null;
		
		String sql = "select * from "
				+ "(select rownum R, F.* from "
				+ "(select * from freeboard order by by ref desc, step asc) F) "
				+ "where R between ? and ?";
		
		st = con.prepareStatement(sql);
		st.setInt(1, rowMaker.getStartRow());
		st.setInt(2, rowMaker.getLastRow());
		
		rs = st.executeQuery();
	
		while(rs.next()){
			freeBoardDTO = new FreeBoardDTO();
			freeBoardDTO.setNum(rs.getInt("num"));
			freeBoardDTO.setWriter(rs.getString("writer"));
			freeBoardDTO.setTitle(rs.getString("title"));
			freeBoardDTO.setContents(rs.getString("contents"));
			freeBoardDTO.setReg_date(rs.getDate("reg_date"));
			freeBoardDTO.setHit(rs.getInt("hit"));
			freeBoardDTO.setRef(rs.getInt("ref"));
			freeBoardDTO.setDepth(rs.getInt("depth"));
			freeBoardDTO.setStep(rs.getInt("step"));
			
			ar.add(freeBoardDTO);
		}
		DBConnector.disConnect(rs, st, con);
		
	
		return ar;
	}

	@Override
	public BoardDTO boardView(int num) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		BoardDTO boardDTO =new BoardDTO();
		
		String sql="select * from freeboard where num=?";
		st = con.prepareStatement(sql);
		st.setInt(1, num);
		rs = st.executeQuery();
		
		if(rs.next()){
			boardDTO.setNum(rs.getInt("num"));
			boardDTO.setWriter(rs.getString("writer"));
			boardDTO.setTitle(rs.getString("title"));
			boardDTO.setContents(rs.getString("contents"));
			boardDTO.setReg_date(rs.getDate("reg_date"));
			boardDTO.setHit(rs.getInt("hit"));
		}
		DBConnector.disConnect(rs, st, con);
		return boardDTO;
	}

	@Override
	public int boardWrite(BoardDTO boardDTO) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;
		
		String sql="insert into freeboard values(notice_seq.nextval,?,?,?,sysdate,0)";
		st = con.prepareStatement(sql);		
		
		st.setString(1, boardDTO.getWriter());
		st.setString(2, boardDTO.getTitle());
		st.setString(3, boardDTO.getContents());
		
		
		result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		
		return result;
	}

	@Override
	public int boardUpdate(BoardDTO boardDTO) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;
		
		String sql="update freeboard set title=?, contents=? where num=?";
		st = con.prepareStatement(sql);		
		
		
		st.setString(1, boardDTO.getTitle());
		st.setString(2, boardDTO.getContents());
		st.setInt(3, boardDTO.getNum());
		
		result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int boardDelete(int num) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;
		
		String sql="delete freeboard where num=?";
		st = con.prepareStatement(sql);		
		st.setInt(1, num);
		
		result = st.executeUpdate();
		
		return result;
	}

	@Override
	public int boardCount(int num) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int result = 0;
		
		String sql = "select nvl(count(num), 0) from freeboard";
		//count(num)이 null이면 0이라고 하자는 nvl(,)
		
		st = con.prepareStatement(sql);
		rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		
		DBConnector.disConnect(rs, st, con);
		return result;
	}
}


