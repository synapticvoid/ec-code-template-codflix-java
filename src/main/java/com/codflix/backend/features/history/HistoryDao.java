package com.codflix.backend.features.history;

import com.codflix.backend.core.Database;
import com.codflix.backend.models.History;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao {

    public List<History> getStreamsHistoryForUser(int userId) {
        List<History> histories = new ArrayList<>();
        Connection connection = Database.get().getConnection();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM stream_history WHERE user_id=?");

            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                History history = mapToStreamHistory(rs);
                histories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }

    private History mapToStreamHistory(ResultSet rs) throws SQLException {
        return new History(
                rs.getInt(1), // id
                rs.getInt(2), // user_id
                rs.getInt(3), // stream_id
                rs.getString(4), // startDate
                rs.getString(5), // endDate
                rs.getInt(6) // watchDuration
        );
    }
}
