package service;

import data.GeometricalObject;
import data.Point;
import jdbc.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class GeometryService {
    private static final Logger log = Logger.getLogger(GeometryService.class.getName());

    private static final String addObjectQuery =
            "INSERT INTO geometrical_objects(name, type, sides) VALUES (?, ?, ?)";
    private static final String addCoordinateQuery =
            "INSERT INTO coordinates(x, y, geometrical_object_id) VALUES (?, ?, ?)";
    private static final String removeCoordinatesQuery =
            "DELETE FROM coordinates WHERE geometrical_object_id = ?";
    private static final String updateObjectQuery =
            "UPDATE geometrical_objects SET name = ?, type = ?, sides = ? WHERE id = ?";
    private static final String removeObjectWithIdQuery =
            "DELETE FROM geometrical_objects WHERE id = ?";

    public static void addObject(GeometricalObject object) {
        if (object == null) {
            log.warning("Cannot add product because it was null.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement prepareStatement =
                    connection.prepareStatement(addObjectQuery);
            prepareStatement.setString(1, object.getName());
            prepareStatement.setString(2, object.getType());
            prepareStatement.setInt(3, object.getSides());
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int lastId = resultSet.getInt(1);
                for (Point point : object.getCoordinates().getPoint()) {
                    addCoordinates(point, lastId);
                }
            } else {
                log.warning("Cannot add item with id " + object.getId());
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCoordinates(Point point, int id) {
        if (point == null) {
            log.warning("Cannot add point because they were null.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement prepareStatement =
                    connection.prepareStatement(addCoordinateQuery);
            prepareStatement.setDouble(1, point.getX());
            prepareStatement.setDouble(2, point.getY());
            prepareStatement.setInt(3, id);
            if (prepareStatement.executeUpdate() <= 0)
                log.warning("Cannot add coordinates for object with id " + id);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCoordinatesForObject(int objectId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(removeCoordinatesQuery);
            prepareStatement.setInt(1, objectId);
            if (prepareStatement.executeUpdate() <= 0) log.warning("Cannot remove coordinates for object with id " + objectId);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editObject(GeometricalObject object) {
        if (object == null) {
            log.warning("Cannot add product because it was null.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(updateObjectQuery);
            prepareStatement.setString(1, object.getName());
            prepareStatement.setString(2, object.getType());
            prepareStatement.setInt(3, object.getSides());
            prepareStatement.setInt(4, object.getId());
            if (prepareStatement.executeUpdate() <= 0) log.warning("Cannot edit object.");
            removeCoordinatesForObject(object.getId());
            for (Point point : object.getCoordinates().getPoint()) {
                addCoordinates(point, object.getId());
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeObject(int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(removeObjectWithIdQuery);
            prepareStatement.setInt(1, id);
            if (prepareStatement.executeUpdate() <= 0) log.warning("Cannot remove object with id " + id);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
