package com.taxicalls.billing.resources;

import com.taxicalls.billing.model.Billing;
import com.taxicalls.billing.model.Driver;
import com.taxicalls.billing.model.Passenger;
import com.taxicalls.billing.model.Trip;
import com.taxicalls.protocol.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/trips")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class TripsResource {

    private static final Logger LOGGER = Logger.getLogger(TripsResource.class.getName());

    private final EntityManager em;

    public TripsResource() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        env.keySet().forEach((envName) -> {
            if (envName.contains("DATABASE_USER")) {
                configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
            } else if (envName.contains("DATABASE_PASS")) {
                configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
            }
        });
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("billing", configOverrides);
        this.em = emf.createEntityManager();
    }

    @POST
    public Response createTrip(Trip trip) {
        LOGGER.log(Level.INFO, "createTrip() invoked");
        Billing billing = new Billing();
        if (trip == null) {
            return Response.successful();
        }
        if (trip.getAddressTo() == null) {
            return Response.successful();
        }
        if (trip.getAddressTo().getCoordinate() == null) {
            return Response.successful();
        }
        if (trip.getAddressFrom() == null) {
            return Response.successful();
        }
        if (trip.getAddressFrom().getCoordinate() == null) {
            return Response.successful();
        }
        if (trip.getPassengers() == null) {
            return Response.successful();
        }
        billing.setPrice(trip.getAddressTo().getCoordinate().getEuclidienDistance(trip.getAddressFrom().getCoordinate()) / trip.getPassengers().size());
        billing.setFromEntity(Driver.class.getSimpleName());
        billing.setFromId(trip.getDriver().getId());
        billing.setToEntity(Passenger.class.getSimpleName());
        em.getTransaction().begin();
        for (Passenger passenger : trip.getPassengers()) {
            billing.setToId(passenger.getId());
            em.persist(billing);
        }
        em.getTransaction().commit();
        return Response.successful(billing);
    }

}
