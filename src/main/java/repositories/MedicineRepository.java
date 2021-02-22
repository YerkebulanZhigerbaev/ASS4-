package repositories;

import data.interfaces.IDB;
import entities.Medicine;
import repositories.interfaces.IMedicineRepository;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MedicineRepository implements IMedicineRepository {
    @Inject
    private IDB db;

    @Override
    public List<Medicine> searchMedicineName(String name) {
        Connection connection = null;
        name="%"+ name +"%";
        try {
            connection = db.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM  public.\"Medicine\" WHERE name LIKE ?");
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Medicine> medicines = new ArrayList<>();

            while (resultSet.next()) {
                Medicine medicine = new Medicine(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("expirationDate").toLocalDate(),
                        resultSet.getString("manufacturer"));

                medicines.add(medicine);
            }

            return medicines;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
        public Medicine getMedicineid(int id) {
            Connection connection = null;
            try {
                connection = db.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Medicine\" WHERE id=?");

                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                Medicine medicine = new Medicine();

                if (resultSet.next()) {
                    medicine.setId(resultSet.getInt("id"));
                    medicine.setName(resultSet.getString("name"));
                    medicine.setPrice(resultSet.getDouble("price"));
                    medicine.setExpirationDate(resultSet.getDate("expirationDate").toLocalDate());
                    medicine.setManufacturer(resultSet.getString("manufacturer"));
                }

                return medicine;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public boolean addMedicine(Medicine medicine) {
            Connection connection = null;

            try {
                connection = db.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"Medicine\" (name, price, expirationDate, manufacturer) VALUES(?,?,?,?)");

                preparedStatement.setString(1, medicine.getName());
                preparedStatement.setDouble(2, medicine.getPrice());
                preparedStatement.setDate(3, Date.valueOf(medicine.getExpirationDate()));
                preparedStatement.setString(4, medicine.getManufacturer());

                preparedStatement.execute();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }


        @Override
        public boolean removeMedicineId(int id) {
            Connection connection = null;

            try {
                connection = db.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Medicine\" WHERE id=?");

                preparedStatement.setInt(1, id);

                preparedStatement.execute();

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
}