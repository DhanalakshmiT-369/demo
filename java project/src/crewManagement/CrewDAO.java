package crewManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CrewDAO {
    //----------------------ADD CREW-------------------
    public void addCrewMember(CrewMember member) {
        String sql = "INSERT INTO crew (name, role, contact, past_shift, current_shift, future_shift, train_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnector.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getRole());
            ps.setString(3, member.getContact());
            ps.setString(4, member.getPastShift());
            ps.setString(5, member.getCurrentShift());
            ps.setString(6, member.getFutureShift());
            ps.setInt(7, member.getTrainId()); 
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //----------------------DELETE CREW----------------------
    public void deleteCrewMember(int id) {
        String sql = "DELETE FROM crew WHERE id = ?";
        try (Connection conn = DBConnector.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //----------------------UPDATE CREW----------------------
    public void updateCrewMember(CrewMember member) {
        String sql = "UPDATE crew SET name=?, role=?, contact=?, past_shift=?, current_shift=?, future_shift=?, train_id=? WHERE id=?";
        try (Connection conn = DBConnector.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getRole());
            ps.setString(3, member.getContact());
            ps.setString(4, member.getPastShift());
            ps.setString(5, member.getCurrentShift());
            ps.setString(6, member.getFutureShift());
            ps.setInt(7, member.getTrainId());
            ps.setInt(8, member.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //----------------------GET CREW----------------------
    public List<CrewMember> getAllCrewMembers() {
        List<CrewMember> list = new ArrayList<>();
        String sql = "SELECT * FROM crew";
        try (Connection conn = DBConnector.getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new CrewMember(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("contact"),
                        rs.getString("past_shift"),
                        rs.getString("current_shift"),
                        rs.getString("future_shift"),
                        rs.getInt("train_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public CrewMember getCrewMemberById(int id) {
    String sql = "SELECT * FROM crew WHERE id = ?";
    try (Connection conn = DBConnector.getDBConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new CrewMember(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("role"),
                rs.getString("contact"),
                rs.getString("past_shift"),
                rs.getString("current_shift"),
                rs.getString("future_shift"),
                rs.getInt("train_id")
            );
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return null;
}
//----------------------GET CREW BY ID----------------------
public static void assignCrewToTrain(int crewId, int trainId) {
    String sql = "UPDATE crew SET train_id = ? WHERE id = ?";
    try (Connection conn = DBConnector.getDBConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, trainId);
        ps.setInt(2, crewId);
        ps.executeUpdate();
    } catch (SQLException e) { e.printStackTrace(); }
}


    //----------------------GET CREW BY TRAIN ID----------------------
    public List<CrewMember> getCrewByTrain(int trainId) {
        List<CrewMember> list = new ArrayList<>();
        String sql = "SELECT * FROM crew WHERE train_id = ?";
        try (Connection conn = DBConnector.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trainId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CrewMember(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("contact"),
                        rs.getString("past_shift"),
                        rs.getString("current_shift"),
                        rs.getString("future_shift"),
                        rs.getInt("train_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //---------------------- UNASSIGN CREW----------------------
    public void unassignCrewFromTrain(int crewId) {
        String sql = "UPDATE crew SET train_id = 0 WHERE id = ?";
        try (Connection conn = DBConnector.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, crewId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}