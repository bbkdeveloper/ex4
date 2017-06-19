package com.choa.freeboard;

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
import com.choa.notice.NoticeDTO;
import com.choa.util.DBConnector;
import com.choa.util.RowMaker;

@Repository
public class FreeBoardDAOimpl implements BoardDAO {
	
	@Inject
	private SqlSession sqlSession;
	private static final String NAMESPACE="FreeBoardMapper.";
	//private DataSource dataSource;
	
	//inject를 이용하기때문에, 생성자가 필요가 없다
	/*public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/

	
	@Override
	public void boardHit(int num)throws Exception{
		
	}

	@Override
	public List<BoardDTO> boardList(RowMaker rowMaker) throws Exception {
		/*Connection con = dataSource.getConnection();
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
		return ar;*/
		return sqlSession.selectList(NAMESPACE+"list", rowMaker);
	}

	@Override
	public BoardDTO boardView(int num) throws Exception {
	
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


