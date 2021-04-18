package service;

import data.Coordinates;
import data.GeometricalObject;
import data.Geometry;
import data.Point;
import jdbc.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class GeometryService {
    private static final Logger log = Logger.getLogger(GeometryService.class.getName());

    private static final String addObjectQuery =
            "INSERT INTO geometrical_objects(name, type, sides) VALUES (?, ?, ?) RETURNING id";
    private static final String addCoordinateQuery =
            "INSERT INTO coordinates(x, y, geometrical_object_id) VALUES (?, ?, ?)";
    private static final String removeCoordinatesQuery =
            "DELETE FROM coordinates WHERE geometrical_object_id = ?";
    private static final String updateObjectQuery =
            "UPDATE geometrical_objects SET name = ?, type = ?, sides = ? WHERE id = ?";
    private static final String removeObjectWithIdQuery =
            "DELETE FROM geometrical_objects WHERE id = ?";
    private static final String allObjectsQuery =
            "SELECT id, name, type, sides FROM geometrical_objects ORDER BY id";
    private static final String allCoordinatesQuery =
            "SELECT x, y FROM coordinates WHERE geometrical_object_id = ?";
    private static final String getObjectWithIdQuery =
            "SELECT id, name, type, sides FROM geometrical_objects WHERE id = ?";
    private static final String removeAllCoordinatesQuery =
            "DELETE FROM coordinates";
    private static final String removeAllObjectsQuery =
            "DELETE FROM geometrical_objects";

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
            ResultSet resultSet = prepareStatement.executeQuery();
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

    public static void removeAllObjects() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement prepareStatement =
                    connection.prepareStatement(removeAllCoordinatesQuery);
            if (prepareStatement.executeUpdate() <= 0)
                log.warning("Cannot remove all coordinates");

            prepareStatement =
                    connection.prepareStatement(removeAllObjectsQuery);
            if (prepareStatement.executeUpdate() <= 0)
                log.warning("Cannot remove all objects");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static GeometricalObject getObjectWithId(int id) {
        GeometricalObject object = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getObjectWithIdQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                object = getObjectFromResultSet(resultSet);
                object.setCoordinates(getAllCoordinates(object.getId()));
                log.info("Found object with id.");
            } else log.info("Couldn't find object with the given id.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return object;
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

    public static Geometry getAll() {
        log.info("Getting geometry from the database.");
        Geometry geometry = new Geometry();
        try (Connection connection = DatabaseConnection.getConnection()) {
            log.info("Connected to the database.");
            PreparedStatement preparedStatement = connection.prepareStatement(allObjectsQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                GeometricalObject object = getObjectFromResultSet(rs);
                object.setCoordinates(getAllCoordinates(object.getId()));
                geometry.getGeometricalObjects().add(object);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return geometry;
    }

    private static Coordinates getAllCoordinates(int objectId) {
        log.info("Getting coordinates from the database.");
        Coordinates geometry = new Coordinates();
        try (Connection connection = DatabaseConnection.getConnection()) {
            log.info("Connected to the database.");
            PreparedStatement preparedStatement = connection.prepareStatement(allCoordinatesQuery);
            preparedStatement.setInt(1, objectId);
            ResultSet rs = preparedStatement.executeQuery();
            return getCoordinatesFromResultSet(rs);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static GeometricalObject getObjectFromResultSet(ResultSet rs) throws SQLException {
        GeometricalObject result = new GeometricalObject();
        result.setId(rs.getInt(1));
        result.setName(rs.getString(2));
        result.setType(rs.getString(3));
        result.setSides(rs.getInt(4));
        return result;
    }

    private static Coordinates getCoordinatesFromResultSet(ResultSet rs) throws SQLException {
        Coordinates coordinates = new Coordinates();
        while (rs.next()) {
            Point point = new Point();
            point.setX(rs.getFloat(1));
            point.setY(rs.getFloat(2));
            coordinates.getPoint().add(point);
        }
        return coordinates;
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

    public static void addGeometry(Geometry geometry) {
        if (geometry == null) {
            log.warning("Cannot add geometry because it was null.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            for (GeometricalObject object : geometry.getGeometricalObjects()) {
                addObject(object);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
