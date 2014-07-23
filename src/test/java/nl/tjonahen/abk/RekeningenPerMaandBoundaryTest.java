/*
 * Copyright (C) 2014 Philippe Tjon-A-Hen philippe@tjonahen.nl
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import nl.tjonahen.abk.business.ABKBusiness;
import nl.tjonahen.abk.domein.RekeningPerMaandRapport;
import nl.tjonahen.abk.domein.entity.Transactie;
import nl.tjonahen.abk.domein.value.Periode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class RekeningenPerMaandBoundaryTest {

    @Mock
    private ABKBusiness aBKBusiness;

    @InjectMocks
    private RekeningenPerMaandBoundary systemUnderTest;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of get method, of class RekeningenPerMaandBoundry.
     */
    @Test
    public void testGet() {
        final ArrayList<Transactie> transacties = new ArrayList<>();
        Transactie transactie = new Transactie();
        transactie.setDatum(new Date(LocalDate.of(2014, 1, 1).toEpochDay()));
        transactie.setBijAf("Af");
        transactie.setBedrag(100.0);
        transacties.add(transactie);
        
        transactie = new Transactie();
        transactie.setDatum(new Date(LocalDate.of(2014, 2, 1).toEpochDay()));
        transactie.setBijAf("Bij");
        transactie.setBedrag(40.0);
        transacties.add(transactie);
        
        when(aBKBusiness.findTransacties(any(Periode.class))).thenReturn(transacties);
        
        RekeningPerMaandRapport result = systemUnderTest.get();
        assertNotNull(result);
        assertEquals(32, result.getCurrent().length);
        assertEquals(-5, result.getKosten()[1].getBedrag(), 0);
    }

}