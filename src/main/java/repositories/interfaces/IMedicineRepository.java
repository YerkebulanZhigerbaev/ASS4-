package repositories.interfaces;

import entities.Medicine;

import java.util.List;


public interface IMedicineRepository {
    List<Medicine> searchMedicineName(String name);
    Medicine getMedicineid(int id);
    boolean addMedicine(Medicine medicine);
    boolean removeMedicineId(int id);

}
