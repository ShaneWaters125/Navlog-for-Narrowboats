package solace.narrowboat

import junit.framework.Assert.assertEquals
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import solace.narrowboat.data.DatabaseHandler

class DatabaseHandlerTests {

    val appContext = InstrumentationRegistry.getInstrumentation().context

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addNewJourney() {
        val database = DatabaseHandler(appContext)
        database.emptyTable()
        database.addJourney("TestJourney")
        val journeys = database.viewJourneys()
        assertEquals("TestJourney", journeys[0].name)
        database.emptyTable()
    }

    @Test
    fun addNewSession() {
        val database = DatabaseHandler(appContext)
        database.emptyTable()
        database.addSession(1, 1, "1", "1", "1", "1", "test")
        val sessions = database.viewSessions(1)
        assertEquals(1, sessions[0].jid)
        database.emptyTable()
    }

    @Test
    fun addLogbook() {
        val database = DatabaseHandler(appContext)
        database.emptyTable()
        database.addLogbook("test", "test", "test", "test", "test", "test", "test", "test", "test", "test")
        val logbook = database.viewLogbook(database.getMostRecentLogbook())
        assertEquals("test", logbook.crew)
        database.emptyTable()
    }

    @Test
    fun addLocationMarker() {
        val database = DatabaseHandler(appContext)
        database.emptyTable()
        database.addMarker("1", "1", 1, "1")
        val markers = database.viewMarkers(1)
        assertEquals("1", markers[0].latitude)
        database.emptyTable()
    }

}