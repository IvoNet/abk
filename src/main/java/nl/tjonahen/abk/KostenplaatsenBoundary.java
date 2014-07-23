/*
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.abk;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.tjonahen.abk.business.ABKBusiness;
import nl.tjonahen.abk.domein.Kostenplaatsen;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/kostenplaatsen")
@Stateless
public class KostenplaatsenBoundary {

    @EJB
    private ABKBusiness abkBusiness;

    /**
     * 
     * @return - 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Kostenplaatsen getKostenplaatsen() {
        return abkBusiness.getKostenplaatsen();
    }

    /**
     * 
     * @param updated -
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(final Kostenplaatsen updated) {
        abkBusiness.update(updated);
    }
    
    /**
     * 
     * @param id - 
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") final Long id) {
        abkBusiness.removeKostenplaats(id);
    }
}