package gitlet;

import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.*;

public class CommitTest {

    @Test
    public void testConstructor() {
        Commit c1 = new Commit("Initial Commit", null);
        assertEquals("Initial Commit", c1.get_message());
        assertEquals("", c1.get_parent());
        assertEquals(new Timestamp(new Date(Instant.EPOCH.getEpochSecond()).getTime()), c1.get_timeStamp());

        Commit c2 = new Commit("Initial Commit", null);
        assertEquals("Same SHA ID for the same contents.", c1.get_name(), c2.get_name());

        Commit c3 = new Commit("Different content", null);
        assertNotEquals("Different SHA ID for the same contents.", c1.get_name(), c3.get_name());
    }

    @Test
    public void testRefreshName() {
        Commit c1 = new Commit("Initial Commit", null);
        String prev_name = c1.get_name();
        c1.add("temp", "version1");
        c1.refreshName();
        assertNotEquals("SHA value must change due to change of contents.",prev_name, c1.get_name());
    }
}
