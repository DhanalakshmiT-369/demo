package utility;

import java.sql.*;
import java.util.*;

public class TrainRouteManager 
{

    private static final String URL = "jdbc:mysql://localhost:3306/train_network";
    private static final String USER = "root";
    private static final String PASSWORD = "harshu007@"; 

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //LOAD STATION CONNECTIONS FROM DATABASE
    public Map<String, List<Object[]>> loadConnectionsFromDatabase() {
        Map<String, List<Object[]>> connections = new HashMap<>();

        String query = "SELECT source_station, destination_station, distance FROM station_connections";

        try (Connection con = connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String source = rs.getString("source_station");
                String dest = rs.getString("destination_station");
                int distance = rs.getInt("distance");

                connections.putIfAbsent(source, new ArrayList<>());
                connections.get(source).add(new Object[]{dest, distance});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connections;
    }

    //BUILD GRAPH
    public Map<String, Station> buildStationNetwork(Map<String, List<Object[]>> connections) 
    {
        Map<String, Station> stations = new HashMap<>();
        for (String name : connections.keySet()) 
        {
            stations.put(name, new Station(name));
        }
        for (Map.Entry<String, List<Object[]>> entry : connections.entrySet()) {
            Station source = stations.get(entry.getKey());
            for (Object[] link : entry.getValue()) {
                String destName = (String) link[0];
                int distance = (int) link[1];
                stations.putIfAbsent(destName, new Station(destName));
                source.addNeighbour(stations.get(destName), distance);
                stations.get(destName).addNeighbour(source, distance);
            }
        }

        return stations;
    }

    //FIND AND STORE OPTIMIZED ROUTE
    public void processAndStoreRoute(String trainName, String startName, String endName,
                                     Map<String, List<Object[]>> connections) 
    {
        try 
        {
            Map<String, Station> stations = buildStationNetwork(connections);
            Station start = stations.get(startName);
            Station end = stations.get(endName);
            List<String> optimizedPath = DijkstraOptimization.findShortestPath(start, end);
            String routeString = String.join(",", optimizedPath);
            try (Connection con = connect()) {
                String sql = "INSERT INTO optimized_routes(train_name, start_station, end_station, optimized_path) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, trainName);
                ps.setString(2, startName);
                ps.setString(3, endName);
                ps.setString(4, routeString);
                ps.executeUpdate();
            }

            System.out.println("Train: " + trainName + " | Route: " + routeString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //MAIN METHOD 
    public static void main(String[] args) {
        TrainRouteManager manager = new TrainRouteManager();

        // Load network from database
        Map<String, List<Object[]>> connections = manager.loadConnectionsFromDatabase();

        // Process multiple trains dynamically
        manager.processAndStoreRoute("Train-101", "A", "C", connections);
        manager.processAndStoreRoute("Train-202", "A", "E", connections);
        manager.processAndStoreRoute("Train-303", "B", "D", connections);
    }
}

