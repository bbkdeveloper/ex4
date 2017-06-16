package com.choa.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.choa.board.BoardDAO;
import com.choa.board.BoardDTO;
import com.choa.util.DBConnector;
import com.choa.util.RowMaker;

//Repository : NoticeDAO noticeDAO = new NoticeDAO();
@Repository
public class NoticeDAOimpl implements BoardDAO {

	@Inject
	private DataSource dataSource;
	
	//inject를 이용하기때문에, 생성자가 필요가 없다
	/*public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/

	
	//totalCount
	public int noticeCount()throws Exception{
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int result = 0;
		
		String sql = "select nvl(count(num), 0) from notice";
		//count(num)이 null이면 0이라고 하자는 nvl(,)
		
		st = con.prepareStatement(sql);
		rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		
		DBConnector.disConnect(rs, st, con);
		return result;
	}
	
	//NoticeList
	public List<BoardDTO> noticeList(RowMaker rowMaker)throws Exception{
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		List<BoardDTO> ar = new ArrayList<BoardDTO>();
		NoticeDTO noticeDTO = null;
		
		String sql = "select * from "
				+ "(select rownum R, N.* from "
				+ "(select * from notice order by num desc) N) "
				+ "where R between ? and ?";
		
		st = con.prepareStatement(sql);
		st.setInt(1, rowMaker.getStartRow());
		st.setInt(2, rowMaker.getLastRow());
		
		rs = st.executeQuery();
	
		while(rs.next()){
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			ar.add(noticeDTO);
		}
		DBConnector.disConnect(rs, st, con);
		
	
		return ar;
	}
	
	//NoticeView
	public BoardDTO noticeView(int num)throws Exception{
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		BoardDTO boardDTO =new BoardDTO();
		
		String sql="select * from notice where num=?";
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
	
	//NoticeWrite
	public int noticeWrite(BoardDTO boardDTO)throws Exception{
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;
		
		String sql="insert into notice values(notice_seq.nextval,?,?,?,sysdate,0)";
		st = con.prepareStatement(sql);		
		
		st.setString(1, boardDTO.getWriter());
		st.setString(2, boardDTO.getTitle());
		st.setString(3, boardDTO.getContents());
		
		
		result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		
		return result;
	}
	
	
	//NoticeUpdate
	public int noticeUpdate(BoardDTO boardDTO)throws Exception{
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;
		
		String sql="update notice set title=?, contents=? where num=?";
		st = con.prepareStatement(sql);		
		
		
		st.setString(1, boardDTO.getTitle());
		st.setString(2, boardDTO.getContents());
		st.setInt(3, boardDTO.getNum());
		
		result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		return result;
	}
	
	
	//NoticeDelete
	public int noticeDelete(int num)throws Exception{
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;
		
		String sql="delete notice where num=?";
		st = con.prepareStatement(sql);		
		st.setInt(1, num);
		
		result = st.executeUpdate();
		
		return result;
	}
	
	//hit
	@Override
	public void boardHit(int num)throws Exception{
		
	}

	@Override
	public List<BoardDTO> boardList(RowMaker rowMaker) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardDTO boardView(int num) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int boardWrite(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boardUpdate(BoardDTO boardDTO) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boardDelete(int num) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int boardCount(int num) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
