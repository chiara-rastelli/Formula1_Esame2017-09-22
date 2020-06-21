package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {
	
	public List<Adiacenza> getAllAdiacenze(int year, Map<Integer, Race> racesIdMap) {
		String sql = 	"	SELECT r1.raceId, r2.raceId, COUNT(DISTINCT(re1.driverId)) AS peso " + 
						"	FROM races r1, races r2, results re1, results re2  " + 
						"	WHERE r1.raceId < r2.raceId " + 
						"	AND re1.driverId = re2.driverId " + 
						"	AND re1.statusId = 1 " + 
						"	AND re2.statusId = 1 " + 
						"	AND re1.raceId = r1.raceId " + 
						"	AND re2.raceId = r2.raceId " + 
						"	AND r1.year = ? " + 
						"	AND r2.year = ? " + 
						"	GROUP BY r1.raceId, r2.raceId";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			st.setInt(2, year);
			ResultSet rs = st.executeQuery();
			List<Adiacenza> list = new ArrayList<>();
			while (rs.next()) {
				Race r1 = racesIdMap.get(rs.getInt("r1.raceId"));
				Race r2 = racesIdMap.get(rs.getInt("r2.raceId"));
				list.add(new Adiacenza(r1, r2, rs.getInt("peso")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Race> getAllRacesBySeason(int year) {
		String sql = "	SELECT * FROM races r WHERE r.year = ? ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();
			List<Race> list = new ArrayList<>();
			while (rs.next()) {
				Race rTemp = new Race(rs.getInt("raceId"), rs.getInt("year"), rs.getInt("round"), rs.getInt("circuitId"), rs.getString("name"), rs.getDate("date").toLocalDate(), 
						rs.getTime("time").toLocalTime(), rs.getString("url"));
				list.add(rTemp);
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getAllYears() {
		String sql = "SELECT  distinct YEAR FROM seasons ORDER BY YEAR asc";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Integer> list = new ArrayList<>();
			while (rs.next()) {
				list.add(rs.getInt("year"));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}

