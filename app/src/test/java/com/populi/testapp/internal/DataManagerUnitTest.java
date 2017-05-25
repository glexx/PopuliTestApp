package com.populi.testapp.internal;

import android.content.Context;

import com.populi.testapp.internal.model.Tour;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class DataManagerUnitTest {

    private static final String DATA_FILE_NEME = "countries_v2.json";

    @Mock
    Context mMockContext;

    DataManager mDataManager;

    @Before
    public void setup() {
        mDataManager = Mockito.spy(DataManager.class);
    }

    @Test
    public void testReadingSerializedData() throws Exception {
        InputStream inV1 = this.getClass().getClassLoader().getResourceAsStream(DATA_FILE_NEME);
        Mockito.doReturn(inV1).when(mDataManager).getDataFileInputStream();

        mDataManager.initialize(mMockContext);

        // Check Tour item fields
        Tour tour = mDataManager.getTour("7");
        assertNotNull(tour);
        assertEquals("Ostia Antica", tour.getTitle());
        assertEquals("ITROM1.jpg", tour.getImage());
        assertTrue(tour.getDescription().startsWith("Ostia Antica is an extraordinary"));
        assertTrue(tour.getDescription().endsWith("and eventual decline."));
        assertEquals(7, tour.getId().intValue());
        assertEquals("7", tour.getUid());

        // Check City fields
        assertEquals("ITROM", tour.getCity().getId());
        assertEquals("Rome", tour.getCity().getName());

        // Check Country fields
        assertEquals("IT", tour.getCountry().getId());
        assertEquals("Italy", tour.getCountry().getName());


        // Countries count
        assertEquals(2, mDataManager.getDataTree().size());
        assertEquals("UK", mDataManager.getDataTree().get(0).getId());
        assertEquals("IT", mDataManager.getDataTree().get(1).getId());

        // Cities count
        assertEquals(2, mDataManager.getDataTree().get(0).getCities().size());
        assertEquals(2, mDataManager.getDataTree().get(1).getCities().size());
    }

    @Test
    public void testReadingDataFromV1() throws Exception {
        InputStream inV1 = this.getClass().getClassLoader().getResourceAsStream("countries_v1.json");
        Mockito.doReturn(inV1).when(mDataManager).getDataFileInputStream();

        mDataManager.initialize(mMockContext);

        Tour tour = mDataManager.getTour("UKLON3");
        assertNotNull(tour);
        assertEquals("Natural History Museum", tour.getTitle());
        assertNull(tour.getId());
    }
}