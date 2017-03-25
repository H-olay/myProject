package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.pojo.CreatePage;
import com.pojo.InfoSingle;

public class OpDB {
	public List<InfoSingle> OpListShow(ResultSet rs) throws Exception {
		List<InfoSingle> onelist = new ArrayList<InfoSingle>();
		if (rs != null) {
			while (rs.next()) {
				InfoSingle infoSingle = new InfoSingle();
				infoSingle.setId(rs.getInt("id"));
				infoSingle.setInfoType(rs.getInt("info_type"));
				infoSingle.setInfoTitle(rs.getString("info_title"));
				infoSingle.setInfoContent(rs.getString("info_content"));
				infoSingle.setInfoLinkman(rs.getString("info_linkman"));
				infoSingle.setInfoPhone(rs.getString("info_phone"));
				infoSingle.setInfoEmail(rs.getString("info_email"));
				infoSingle.setInfoDate(DoString.dateTimeChange(new Date(rs.getTimestamp("info_date").getTime())));
				infoSingle.setInfoState(rs.getString("info_state"));
				infoSingle.setInfoPayfor(rs.getString("info_payfor"));
				onelist.add(infoSingle);
			}
		}
		if (rs != null) {
			rs.close();
		}
		return onelist;
	}

	public TreeMap<Integer, String> OpGetListBox(ResultSet rs) throws Exception {
		TreeMap<Integer, String> typeMap = new TreeMap<Integer, String>();
		if (rs != null) {
			while (rs.next()) {
				Integer sign = Integer.valueOf(rs.getInt("type_sign"));
				String intro = rs.getString("type_intro");
				typeMap.put(sign, intro);
			}
		}
		return typeMap;
	}

	public InfoSingle OpSingleShow(ResultSet rs) throws Exception {
		InfoSingle infoSingle = null;
		if (rs.next()) {
			infoSingle = new InfoSingle();
			infoSingle.setId(rs.getInt("id"));
			infoSingle.setInfoType(rs.getInt("info_type"));
			infoSingle.setInfoTitle(rs.getString("info_title"));
			infoSingle.setInfoContent(rs.getString("info_content"));
			infoSingle.setInfoLinkman(rs.getString("info_linkman"));
			infoSingle.setInfoPhone(rs.getString("info_phone"));
			infoSingle.setInfoEmail(rs.getString("info_email"));
			infoSingle.setInfoDate(DoString.dateTimeChange(new Date(rs.getTimestamp("info_date").getTime())));
			infoSingle.setInfoState(rs.getString("info_state"));
			infoSingle.setInfoPayfor(rs.getString("info_payfor"));
		}
		if (rs != null) {
			rs.close();
		}
		return infoSingle;
	}

	public boolean LogOn(ResultSet rs) throws Exception {
		boolean mark = (rs == null || !rs.next() ? false : true);
		rs.close();
		return mark;
	}

	public int OpUdate(String sql, int type, String title, String content, String phone, String linkman, String email,
			String date, String state, String payfor) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBUtil.getConn();
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, type);
		pstmt.setString(2, title);
		pstmt.setString(3, content);
		pstmt.setString(4, phone);
		pstmt.setString(5, linkman);
		pstmt.setString(6, email);
		pstmt.setString(7, date);
		pstmt.setString(8, state);
		pstmt.setString(9, payfor);
		int i = -1;
		i = pstmt.executeUpdate();
		conn.commit();// 事务提交
		if (conn != null)
			conn.close();
		if (pstmt != null)
			pstmt.close();
		return i;
	}

	public CreatePage OpCreatePage(String sql, Object[] params, int perR, String strCurrentP, String gowhich)
			throws Exception {
		CreatePage createPage = new CreatePage();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		createPage.setPerR(perR);
		if (sql != null && !sql.equals("")) {
			conn = DBUtil.getConn();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				rs.last();
				createPage.setAllR(rs.getRow());
				createPage.setAllP();
				createPage.setCurrentP(strCurrentP);
				createPage.setPageInfo();
				createPage.setPageLink(gowhich);
				rs.close();
			}
		}
		return createPage;
	}

	public List<InfoSingle> OpListShow(String sql, Object[] params) throws Exception {
		List<InfoSingle> onelist = new ArrayList<InfoSingle>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (sql != null && !sql.equals("")) {
			conn = DBUtil.getConn();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					InfoSingle infoSingle = new InfoSingle();
					infoSingle.setId(rs.getInt("id"));
					infoSingle.setInfoType(rs.getInt("info_type"));
					infoSingle.setInfoTitle(rs.getString("info_title"));
					infoSingle.setInfoContent(rs.getString("info_content"));
					infoSingle.setInfoLinkman(rs.getString("info_linkman"));
					infoSingle.setInfoPhone(rs.getString("info_phone"));
					infoSingle.setInfoEmail(rs.getString("info_email"));
					infoSingle.setInfoDate(DoString.dateTimeChange(new Date(rs.getTimestamp("info_date").getTime())));
					infoSingle.setInfoState(rs.getString("info_state"));
					infoSingle.setInfoPayfor(rs.getString("info_payfor"));
					onelist.add(infoSingle);
				}
			}
			if (rs != null) {
				rs.close();
			}
		}
		return onelist;
	}

}
