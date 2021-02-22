package controllers;

import entities.Medicine;
import repositories.interfaces.IMedicineRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("medicines")
public class MedicineController {
    @Inject
    private IMedicineRepository repo;
    @GET
    public String SayHello(){
        return "Hello World";
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMedicineName(@PathParam("name") String name) {
        List<Medicine> medicines;
        try {
            medicines = repo.searchMedicineName(name);
        } catch (ServerErrorException ex) {
            return Response
                    .status(500).entity(ex.getMessage()).build();
        }

        if (medicines == null) return Response
                .status(Response.Status.NOT_FOUND)
                .entity("Medicine does not exist!")
                .build();

        return Response
                .status(Response.Status.OK)
                .entity(medicines)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMedicine(Medicine medicine) {
        boolean created;
        try {
            created = repo.addMedicine(medicine);
        } catch (ServerErrorException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if (!created) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Medicine cannot be created!")
                    .build();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity("Medicine created successfully!")
                .build();
    }

//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getMedicineid(@PathParam("id") int id) {
//        Medicine medicine;
//        try {
//            medicine = repo.getMedicineid(id);
//        } catch (ServerErrorException ex) {
//            return Response
//                    .status(500).entity(ex.getMessage()).build();
//        }
//
//        if (medicine == null) return Response
//                .status(Response.Status.NOT_FOUND)
//                .entity("Medicine does not exist!")
//                .build();
//
//        return Response
//                .status(Response.Status.OK)
//                .entity(medicine)
//                .build();
//    }

//    @DELETE
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response removeMedicineId(Medicine medicine) {
//        boolean created;
//        try {
//            created = repo.addMedicine(medicine);
//        } catch (ServerErrorException ex) {
//            return Response.serverError().entity(ex.getMessage()).build();
//        }
//
//        if (!created) {
//            return Response
//                    .status(Response.Status.BAD_REQUEST)
//                    .entity("Medicine cannot be removed!")
//                    .build();
//        }
//
//        return Response
//                .status(Response.Status.CREATED)
//                .entity("Medicine removed successfully!")
//                .build();
//    }
}