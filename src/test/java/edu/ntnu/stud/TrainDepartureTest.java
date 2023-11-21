package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.DateTimeException;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

/**
 * Unit test to test the Departures.
 * <p>
 * The following is tested:
 * <ul>
 *   <li>Creating a departure with valid inputs.</li>
 *
 *   <li>Creating a departure with invalid inputs </li>
 *
 * </ul>
 */

class TrainDepartureTest {
  /**
   * <h6>Positive test</h6>
   * <p>
   * Test creation of object with valid parameters. This test verifies that
   * the class behaves as expected according to the requirements specification.
   * </p>
   * <p>When creating a departure we use only valid inputs, the object is created and the
   * fields of the objects are set to the values given by the parameter.
   * <ul>
   *   <li>Time is a valid 24hr format time.</li>
   *   <li>Line is not empty or null.</li>
   *    <li>train number is not negative.</li>
   *    <li>track is not negative.</li>
   *    <li>destination is not empty, null or blank.</li>
   *    <li>Delay is a valid LocalTime object</li>
   * </ul></p>
   */
  @Test
  public void testCreationOfDepartureWithValidInput() {
    LocalTime time1 = LocalTime.of(10, 20);
    LocalTime delay = LocalTime.of(0, 10);
    LocalTime DelayedBy = LocalTime.of(10, 30);
    TrainDeparture test = new TrainDeparture(time1, "L1", 10, 1,
        "Drammen", delay);

    assertEquals("L1", test.getLine());
    assertEquals(10, test.getTrainNr());
    assertEquals(1, test.getTrack());
    assertEquals("Drammen", test.getDestination());
    assertEquals(time1, test.getDepartureTime());
    assertEquals(delay, test.getDelay());
    assertEquals(DelayedBy, test.getDelayedDeparture());

  }

  /**
   * <h6>Negative test</h6>
   * <p>
   * Test creation of train departure with invalid input. This test verifies that the class
   * behaves as it should when given invalid inputs.
   * <p>When creating a departure we use mostly only invalid inputs, the object is created and the
   * fields of the objects are set to the values given by the parameter.
   * <ul>
   *   <li>Times are set to midnight because
   *   any value that is not in the 24hr format will throw an exception and I don't know how to
   *   handle that yet</li>
   *   <li>Line is a empty string.</li>
   *   <li>track is a negative number</li>
   *   <li></li>
   * </ul></p>
   */
  @Test
  public void testCreationOfDepartureWithInvalidInput() {
    LocalTime time1 = null;
    LocalTime delay = LocalTime.of(0, 0);
    LocalTime defaultTime = LocalTime.of(0, 0);
    LocalTime delayedBy = LocalTime.of(0, 0);
    TrainDeparture test = new TrainDeparture(time1, "", 10, -1,
        null, delay);

    assertEquals("ERROR", test.getLine());
    assertFalse(test.setTrack(-1));
    assertEquals(0, test.getTrack());
    assertEquals("ERROR", test.getDestination());
    assertEquals(defaultTime, test.getDepartureTime());
    assertEquals(delay, test.getDelay());
    assertEquals(delayedBy, test.getDelayedDeparture());
  }

  @Test
  public void testCreationOfDepartureWithDateTimeException(){
    try {
      TrainDeparture test = new TrainDeparture(LocalTime.of(-1, 0), "",
          10, -1, null, LocalTime.of(0, 10));
      fail();
    }catch (DateTimeException e){
      assertFalse(false);
    }
  }

}