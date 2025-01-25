package com.and.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCallExecuter {

    public static  ResultSet executeQuery(String query) {
        Connection connection = DriverProvider.getConnection();

        if (connection != null) {
            try {
                // Create a PreparedStatement with the provided query
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                return preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection is not available.");
        }

        return null;
    }

}
