package com.choa.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.choa.board.BoardDAO;
import com.choa.board.BoardDTO;
import com.choa.util.DBConnector;
import com.choa.util.RowMaker;

//Repository : NoticeDAO noticeDAO = new NoticeDAO();
@Repository
public class NoticeDAOimpl implements BoardDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String NAMESPACE="NoticeMapper.";
	
	//inject를 이용하기때문에, 생성자가 필요가 없다
	/*public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/


	//hit
	@Override
	public void boardHit(int num)throws Exception{
		
	}

	@Override
	public List<BoardDTO> boardList(RowMaker rowMaker) throws Exception {
		return sqlSession.selectList(NAMESPACE+"list", rowMaker);
	}

	@Override
	public BoardDTO boardView(int num) throws Exception {
		/*Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		BoardDTO boardDTO =new BoardDTO();*/
		
		//String sql="select * from notice where num=?";
		
		/*st = con.prepareStatement(sql);
		st.setInt(1, num);
		rs = st.executeQuery();*/
		
		/*if(rs.next()){
			boardDTO.setNum(rs.getInt("num"));
			boardDTO.setWriter(rs.getString("writer"));
			boardDTO.setTitle(rs.getString("title"));
			boardDTO.setContents(rs.getString("contents"));
			boardDTO.setReg_date(rs.getDate("reg_date"));
			boardDTO.setHit(rs.getInt("hit"));
		}*/
		//DBConnector.disConnect(rs, st, con);
		
		//sqlSession을 사용함으로써 SQL문을 사용하지 않아서 다 삭제
		
		//BoardDTO boardDTO = sqlSession.selectOne(NAMESPACE+"view", num);
		
		//mybatis mapper의 어떤타입을 보내겠느냐, 파라미터로 보낼 변수명
		//NoticeMapper + .view
		//return 타입은 DTO로 온다
		
		return sqlSession.selectOne(NAMESPACE+"view", num);
	}

	@Override
	public int boardWrite(BoardDTO boardDTO) throws Exception {
		return sqlSession.insert(NAMESPACE+"write", boardDTO);
		
	}

	@Override
	public int boardUpdate(BoardDTO boardDTO) throws Exception {
		return sqlSession.update(NAMESPACE+"update", boardDTO);
	}

	@Override
	public int boardDelete(int num) throws Exception {
		return sqlSession.delete(NAMESPACE+"delete", num);
	}

	@Override
	public int boardCount() throws Exception {
		return sqlSession.selectOne(NAMESPACE+"count");
	}
}
